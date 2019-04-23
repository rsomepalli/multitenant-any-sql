package com.analogyx.schemer.instance;

import java.util.ArrayList;
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
		List<Indextype> tenantScoped = new ArrayList<>();

		for (Indextype index : indexes) {
			if(!index.isPrimary()) {
				Indextype tIndex = new Indextype();
				tIndex.getOn().add(0, "tenant");
				tIndex.getOn().addAll(index.getOn());
				tIndex.setIndexname("t_" + index.getIndexname());
				tIndex.setPrimary(false);
				tIndex.setUnique(false);
				tenantScoped.add(tIndex);
			}
		}

		Indextype tenantColIndex = new Indextype();
		tenantColIndex.getOn().add(0, "tenant");
		tenantColIndex.setIndexname("tenant_" + tableDef.getName().toLowerCase());
		tenantColIndex.setPrimary(false);
		tenantColIndex.setUnique(false);
		tenantScoped.add(tenantColIndex);

		indexes.addAll(tenantScoped);
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
