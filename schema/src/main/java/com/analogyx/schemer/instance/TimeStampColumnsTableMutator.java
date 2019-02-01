package com.analogyx.schemer.instance;

import com.analogyx.schemer.TableMutator;
import com.analogyx.schemer.domain.Columntype;
import com.analogyx.schemer.domain.Tabletype;

public class TimeStampColumnsTableMutator implements TableMutator {



	public void mutate(Tabletype tableDef) {
		if(tableDef.isAddTimeStampColumns()){
			addTimeStampColumn(tableDef, "last_updated");
			addTimeStampColumn(tableDef, "date_created");
		}
	}
	
	private void addTimeStampColumn(Tabletype tableDef, String columnName) {
		Columntype column = new Columntype();
		column.setName(columnName);
		column.setScale(1);
		column.setType("timestamp");
		column.setNullable(false);
		column.setIncludeInView(false);
		column.setDefault("CURRENT_TIMESTAMP");
		column.setDefaultExpression(true);
		tableDef.getColumn().add(column);
	}
}
