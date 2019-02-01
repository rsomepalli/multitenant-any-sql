package com.lakumbra.multitenant.postgres;

import com.lakumbra.multitenant.sql.TenantAwareDataSource;

public class PostgresTenantAwareDataSource extends TenantAwareDataSource{

	public PostgresTenantAwareDataSource(){
		this.setTenantSessionCallback(new PostgresSessionVariableTenantSessionCallback());
	}
}
