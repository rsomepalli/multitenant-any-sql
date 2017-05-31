package com.analogyx.samples.mysql;

import org.moveon.multitenant.sql.TenantAwareDataSource;

public class MysqlTeanantAwareDataSource extends TenantAwareDataSource{

	public MysqlTeanantAwareDataSource(){
		this.setTenantSessionCallback(new MySqlSessionVariableTenantSessionCallback());
	}
}
