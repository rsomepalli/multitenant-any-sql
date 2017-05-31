package com.analogyx.samples.mysql;

import org.moveon.multitenant.sql.TenantAwareDataSource;

public class MysqlTenantAwareDataSource extends TenantAwareDataSource{

	public MysqlTenantAwareDataSource(){
		this.setTenantSessionCallback(new MySqlSessionVariableTenantSessionCallback());
	}
}
