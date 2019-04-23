package com.analogyx.schemer.instance.mysql;

import com.analogyx.schemer.SQLGenerator;
import com.analogyx.schemer.TypesConverter;
import com.analogyx.schemer.domain.Columntype;
import com.analogyx.schemer.domain.Indextype;
import com.analogyx.schemer.domain.Tabletype;
import com.analogyx.schemer.typeconverter.PostgresTypesConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class PostgresSQLGenerator implements SQLGenerator {
	private TypesConverter convertor = new PostgresTypesConverter();

	@Override
	public List<String> generateCreateTable(Tabletype tabletype) {
		List<String> sqlStatements = new ArrayList<String>();

		String tableName = getTableName(tabletype);
		String createSQL = generateTable(tabletype);
		sqlStatements.add(createSQL.toString());
		if (tabletype.isTenantScoped()) {
			sqlStatements.add(generateTenantColumnTrigger(tabletype));
			// FORCE does not have any effect without ENABLE first
			// sqlStatements.add(String.format("ALTER TABLE %s ENABLE ROW LEVEL SECURITY ", tableName));
			// sqlStatements.add(String.format("ALTER TABLE %s FORCE ROW LEVEL SECURITY ", tableName));
			// sqlStatements.add(String.format("CREATE POLICY %s_select_policy ON %s FOR SELECT USING (getTenant()=-1 or tenant=getTenant())", tableName, tableName));
			// sqlStatements.add(String.format("CREATE POLICY %s_mod_policy ON %s for UPDATE USING (tenant=getTenant())", tableName, tableName));
			// sqlStatements.add(String.format("CREATE POLICY %s_del_policy ON %s for DELETE USING (tenant=getTenant())", tableName, tableName));
			// sqlStatements.add(String.format("CREATE POLICY %s_insert_policy ON %s for INSERT WITH CHECK(true)", tableName, tableName));

		}
		sqlStatements.add(generateSetTimeStampColumnTrigger(tabletype));
		sqlStatements.add(generateUpdateColumnTrigger(tabletype));
		for (Indextype it : tabletype.getIndex()) {
			if (it.isPrimary()) {
				List<String> on = it.getOn();
				boolean identityPk = on.size() == 1 && isIdentityColumn(tabletype.getColumn(), on.get(0));
				if (!identityPk) {
					String indexStmt = generateCreateIndexPK(tableName, it);
					sqlStatements.add(indexStmt);
				}
			} else if (it.isUnique()) {
				String indexStmt = generateCreateIndexUnique(tableName, it);
				sqlStatements.add(indexStmt);
			} else {
				String indexStmt = generateCreateIndex(tableName, it);
				sqlStatements.add(indexStmt);
			}
		}
		//sqlStatements.add(generateSeqInitSQL(tabletype.getName()));

		return sqlStatements;
	}

	private String generateTable(Tabletype tabletype) {
		HashMap<String, Columntype> columnNameToColumnType = new HashMap<>();

		StringBuffer createSQL = new StringBuffer("CREATE TABLE " + getTableName(tabletype) + " (");
		System.out.println(createSQL.toString());
		int i = 0;
		for (Columntype ct : tabletype.getColumn()) {
			if (i != 0) {
				createSQL.append(", ");
			}
			i += 1;
			columnNameToColumnType.put(ct.getName(), ct);
			createSQL.append(" " + ct.getName() + "");
			createSQL.append(" " + convertor.schemaTypeToNative(ct));
			createSQL.append(convertor.schemaScaleToNative(ct));
			if (ct.getDefault() != null && ct.getDefault().length() > 0){
				createSQL.append(" DEFAULT " + convertor.schemaDefaultToNative(ct));
			}
			createSQL.append(" " + convertor.schemaNullToNative(ct));

		}
		createSQL.append(" ) ");
		return createSQL.toString();
	}

	public String generateSeqInitSQL(Tabletype tabletype) {
		String seqDataInsert = "insert into seq(name, val) values('" + tabletype.getName()+ "', 1)";
		return seqDataInsert;
	}

	private String generateTenantColumnTrigger(Tabletype tabletype) {
		String triggerSQL = "CREATE TRIGGER before_insert_" + getTableName(tabletype) + " BEFORE INSERT ON "
				+ getTableName(tabletype) + " FOR EACH ROW execute procedure set_tenant_column()";
		return triggerSQL;
	}
	private String generateSetTimeStampColumnTrigger(Tabletype tabletype) {
			String triggerSQL = "CREATE TRIGGER before_insert_timestamp" + getTableName(tabletype) + " BEFORE INSERT ON "
					+ getTableName(tabletype) + " FOR EACH ROW execute procedure set_timestamp_column()";
			return triggerSQL;
	}
	private String generateUpdateColumnTrigger(Tabletype tabletype) {
		String triggerSQL = "CREATE TRIGGER before_update_" + getTableName(tabletype) + " BEFORE UPDATE ON "
				+ getTableName(tabletype) + " FOR EACH ROW execute procedure update_timestamp_column() ";
		return triggerSQL;
	}

	public List<String> generateCreateView(Tabletype tabletype) {
		List<String> views = new ArrayList<>();
		if(tabletype.isGenerateView()){
			StringBuffer viewSQL = new StringBuffer("CREATE OR REPLACE VIEW " + tabletype.getName() + " (");
			int i = 0;
			for (Columntype ct : tabletype.getColumn()) {
				if (ct.isIncludeInView()) {
					if (i != 0) {
						viewSQL.append(", ");
					}
					i += 1;
					viewSQL.append(ct.getName());
				}
			}
			String tableNamePrefix = "tbl_" + tabletype.getName() + ".";
			viewSQL.append(" ) AS SELECT ");
			i = 0;
			for (Columntype ct : tabletype.getColumn()) {
				if (ct.isIncludeInView()) {
					if (i != 0) {
						viewSQL.append(", ");
					}
					i += 1;
					viewSQL.append(" " + tableNamePrefix  +  ct.getName() );
				}
			}
			viewSQL.append(" from " + getTableName(tabletype));
			// + (tabletype.isTenantScoped()? " where (" + tableNamePrefix + "tenant = gettenant())":""));

			views.add(viewSQL.toString());
		}
		return views;

	}

	protected String generateDropIndexPK(String tableName, Indextype indexType) {
		String stmt = "ALTER TABLE " + tableName + " DROP CONSTRAINT " + indexType.getPrikeyname();
		return stmt;
	}

	protected String generateDropIndexUnique(String tableName, Indextype indexType) {
		return generateDropIndex(tableName, indexType);
	}

	protected String generateDropIndex(String tableName, Indextype indexType) {
		String stmt = "DROP INDEX " + tableName + "." + indexType.getIndexname();
		return stmt;
	}

	protected String generateCreateIndex(String tableName, Indextype indexType) {
		String indexName = getIndexName("idx_", indexType);
		String stmt = "CREATE INDEX " + indexName + " ON " + tableName + " ( "
				+ toCommaSeparatedList(indexType.getOn()) + " )";
		return stmt;
	}

	protected String generateCreateIndexPK(String tableName, Indextype indexType) {
		String stmt = "ALTER TABLE " + tableName + " ADD "
				//+ "  CONSTRAINT " +tableName + "_pkey "
				+  " PRIMARY KEY ( "
				+ toCommaSeparatedList(indexType.getOn()) + " )";
		return stmt;
	}

	protected String generateCreateIndexUnique(String tableName, Indextype indexType) {
		String indexName = getIndexName("unq_", indexType);
		String stmt = "CREATE UNIQUE INDEX  ON " + tableName + " ( "
				+ toCommaSeparatedList(indexType.getOn()) + " )";
		return stmt;
	}

	protected String toCommaSeparatedList(List<String> values) {
		return String.join(", ", values);
	}

	protected String getIndexName(String prefix, Indextype indexType) {
		Optional<String> indexNameOption = Optional.ofNullable(indexType.getIndexname());
		if (!indexNameOption.isPresent()) {
			StringBuffer temp = new StringBuffer();
			temp.append(prefix);
			String indexName = String.join("_", indexType.getOn());
			return  indexName.substring(0, indexName.length() < 20 ? indexName.length() : 20);
		}else{
			return indexNameOption.get();
		}
	}

	protected boolean isIdentityColumn(List<Columntype> columns, String column) {
		try {
			for (Columntype columntype : columns)
				if (columntype.getName().toLowerCase().equals(column.toLowerCase())) {
					Boolean sequence = columntype.isSequence();
					return sequence != null && sequence;
				}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private String getTableName(Tabletype tabletype){
		String prefix = "";
		if(tabletype.isGenerateView()){
			prefix = "tbl_";
		}
		return prefix + tabletype.getName();
	}

	public List<String> generateDropTables(Tabletype tabletype){
		List<String> dropStmts = new ArrayList<>();
		if(tabletype.isGenerateView()){
			dropStmts.add("drop view if exists " + tabletype.getName());
		}
		dropStmts.add("drop table if exists " + getTableName(tabletype));
		return dropStmts;
	}
}
