package com.analogyx.schemer.instance;

import com.analogyx.schemer.TableMutator;
import com.analogyx.schemer.domain.Columntype;
import com.analogyx.schemer.domain.Tabletype;

public class VersionColumnTableMutator implements TableMutator {



	public void mutate(Tabletype tableDef) {
		if(tableDef.isVersionMaintained()){
			addTimeStampColumn(tableDef, "version");
		}
	}
	
	private void addTimeStampColumn(Tabletype tableDef, String columnName) {
		Columntype column = new Columntype();
		column.setName(columnName);
		column.setScale(1);
		column.setType("int");
		column.setNullable(false);
		column.setIncludeInView(true);
		column.setDefault("0");
		column.setDefaultExpression(false);
		tableDef.getColumn().add(column);
	}
}
