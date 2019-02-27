package com.lakumbra.multitenant.postgres;

import javax.sql.DataSource;
import com.lakumbra.multitenant.sql.TenantDBInstanceKeyProvider;

public class PostgresSimpleTenantAwareDataSource extends PostgresTenantAwareDataSource {

	public PostgresSimpleTenantAwareDataSource(){
		this.setTenantDBInstanceProvider(new TenantDBInstanceKeyProvider(){
				public String getDBInstanceKey(Integer tenantId){
					return DEFAULT;
				}
		});
	}

	private static final String DEFAULT="default";

	public void setDataSource(DataSource ds){
		this.addDbInstance(DEFAULT, ds);
	}
}
