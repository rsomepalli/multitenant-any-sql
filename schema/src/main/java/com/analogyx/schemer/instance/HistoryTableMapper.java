package com.analogyx.schemer.instance;

import java.util.ArrayList;
import java.util.List;

import com.analogyx.schemer.TableMapper;
import com.analogyx.schemer.domain.ObjectFactory;
import com.analogyx.schemer.domain.Tabletype;

public class HistoryTableMapper implements TableMapper {

	@Override
	public List<Tabletype> map(Tabletype table) {
		List<Tabletype> asList = new ArrayList<>();
		asList.add(table);
		if (table.isHistoryMaintained()) {
			Tabletype historyTable = new ObjectFactory().createTabletype();
			if (table.getHistoryTableName() == null || table.getHistoryTableName().trim().equals("")) {
				historyTable.setName(table.getName() + "_hist");
			} else {
				historyTable.setName(table.getHistoryTableName());
			}
			historyTable.getColumn().addAll(table.getColumn());
			historyTable.getIndex().addAll(table.getIndex());
			historyTable.setGenerateView(table.isGenerateView());
			asList.add(historyTable);
		}

		return asList;
	}

}
