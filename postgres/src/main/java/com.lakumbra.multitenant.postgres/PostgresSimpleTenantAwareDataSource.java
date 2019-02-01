package com.lakumbra.multitenant.postgres;

import javax.sql.DataSource;

public class PostgresSimpleTenantAwareDataSource extends PostgresTenantAwareDataSource {

	public PostgresSimpleTenantAwareDataSource(){
		this.setTenantDBInstanceProvider(tenant->DEFAULT);
	}

	private static final String DEFAULT="default";
	
	public void setDataSource(DataSource ds){
		this.addDbInstance(DEFAULT, ds);
	}
}
