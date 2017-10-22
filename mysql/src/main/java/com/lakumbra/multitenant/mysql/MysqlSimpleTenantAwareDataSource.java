package com.lakumbra.multitenant.mysql;

import javax.sql.DataSource;

public class MysqlSimpleTenantAwareDataSource extends MysqlTenantAwareDataSource {

	public MysqlSimpleTenantAwareDataSource(){
		this.setTenantDBInstanceProvider(tenant->DEFAULT);
	}

	private static final String DEFAULT="default";
	
	public void setDataSource(DataSource ds){
		this.addDbInstance(DEFAULT, ds);
	}
}
