package com.analogyx.schemer.instance;

import java.util.List;

import com.analogyx.schemer.TableMutator;
import com.analogyx.schemer.domain.Columntype;
import com.analogyx.schemer.domain.Indextype;
import com.analogyx.schemer.domain.Tabletype;

public class TenantColumnsTableMutator implements TableMutator {

	@Override
	public void mutate(Tabletype tableDef) {
		if (tableDef.isTenantScoped()) {
			addTenantColumn(tableDef);
			addTenantIndex(tableDef);
		}
	}

	private void addTenantIndex(Tabletype tableDef) {
		List<Indextype> indexes = tableDef.getIndex();
		for (Indextype index : indexes) {
			index.getOn().add(0, "tenant");
		}
	}

	private void addTenantColumn(Tabletype tableDef) {
		Columntype tenantColumn = new Columntype();
		tenantColumn.setName("tenant");
		tenantColumn.setScale(1);
		tenantColumn.setType("int");
		tenantColumn.setNullable(true);
		tenantColumn.setIncludeInView(false);
		tableDef.getColumn().add(0, tenantColumn);
	}
}
