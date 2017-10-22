package com.lakumbra.multitenant.mysql;

import com.lakumbra.multitenant.sql.TenantAwareDataSource;

public class MysqlTenantAwareDataSource extends TenantAwareDataSource{

	public MysqlTenantAwareDataSource(){
		this.setTenantSessionCallback(new MySqlSessionVariableTenantSessionCallback());
	}
}
