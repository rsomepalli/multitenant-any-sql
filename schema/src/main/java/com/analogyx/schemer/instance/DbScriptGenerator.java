package com.analogyx.schemer.instance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;

import com.analogyx.schemer.SQLGenerator;
import com.analogyx.schemer.TableMapper;
import com.analogyx.schemer.domain.Database;
import com.analogyx.schemer.domain.Tabletype;
import com.analogyx.schemer.instance.mysql.MySQLGenerator;
import com.analogyx.schemer.util.SchemerFileLoader;

public class DbScriptGenerator {
	private final String JAXB_PATH = "com.analogyx.schemer.domain";
	private SQLGenerator sqlGen;
	
	public DbScriptGenerator(SQLGenerator sqlGen) {
		super();
		this.sqlGen = sqlGen;
	}

	public Optional<SQLScriptSet> generateSQL(String filename) throws Exception {
		javax.xml.bind.Unmarshaller u = JAXBContext.newInstance(JAXB_PATH).createUnmarshaller();
		File schemaFile = new SchemerFileLoader().findResourceAsFile(filename);
		u.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
		Database db = (Database) u.unmarshal(schemaFile);
		if (db != null) {
			List<Tabletype> inputTables = db.getTable();
			// generate table meta data table and insert metadata into it.
			// like where the table has version, whether it has history/audit etc..
			return Optional.of(generateScripts(inputTables));
		}else{
			return Optional.empty();
		}
	}
	
	public Optional<SQLScriptSet> generateSQL(String...files) throws Exception {
		List<Tabletype> tables = new ArrayList<Tabletype>();
		for(String file: files){
			javax.xml.bind.Unmarshaller u = JAXBContext.newInstance(JAXB_PATH).createUnmarshaller();
			File schemaFile = new SchemerFileLoader().findResourceAsFile(file);
			u.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			Database db = (Database) u.unmarshal(schemaFile);
			if (db != null) {
				List<Tabletype> inputTables = db.getTable();
				tables.addAll(inputTables);
			}
		}
		
		if(!tables.isEmpty()){
			return Optional.of(generateScripts(tables));
		}else{
			return Optional.empty();
		}
	}
	public SQLScriptSet generateScripts(Collection<Tabletype> inputTables) {
		return generateScripts(inputTables, true);
	}
	public SQLScriptSet generateScripts(Collection<Tabletype> inputTables, boolean generateSequences) {
		SQLScriptSet scripts = new SQLScriptSet();
		Collection<Tabletype> generatedTables = 
				Stream.of(new HistoryTableMapper())
				.flatMap(mapper -> inputTables.stream().flatMap(table -> mapper.map(table).stream()))
				.collect(Collectors.toList());

		Stream.of(
				//new TimeStampColumnsTableMutator(),
				//new VersionColumnTableMutator(),
				new TenantColumnsTableMutator())
				.forEach(mutator -> generatedTables.forEach(table -> mutator.mutate(table)));
		generatedTables.forEach(tableType -> {
			scripts.addDropStatements(sqlGen.generateDropTables(tableType));
			scripts.addAllTables(sqlGen.generateCreateTable(tableType));
			scripts.addAllView(sqlGen.generateCreateView(tableType));
			if(generateSequences) {
				scripts.addData(sqlGen.generateSeqInitSQL(tableType));
			}
		});
		return scripts;
	}

	public List<Tabletype> generateTables(List<Tabletype> inputTables, List<TableMapper> tableHandlers) {
		List<Tabletype> tables = tableHandlers.stream()
				.flatMap(mapper -> inputTables.stream().flatMap(table -> mapper.map(table).stream()))
				.collect(Collectors.toList());
		return tables;
	}

	public static void main(String[] args) throws Exception {
		if (args !=null && args.length!=0) {
			String fileName = args[0];
			DbScriptGenerator scriptGen = new DbScriptGenerator(new MySQLGenerator());
			Optional<SQLScriptSet> generateSQL = scriptGen.generateSQL(fileName);
			PrintStream p = System.out;
			if(args.length>1){
				String outputFileName = args[1];
				p = new PrintStream(new FileOutputStream(outputFileName));
			}
			final PrintStream p1=p;
			generateSQL.ifPresent(script -> script.print(p1));
		} else {
			System.out.println("Usage : java com.analogyx.schemer.instance.DbScriptGenerator path/to/SchemaBase.xml");
		}
	}
}
