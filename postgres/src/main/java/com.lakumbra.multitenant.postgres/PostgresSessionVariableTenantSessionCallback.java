package com.lakumbra.multitenant.postgres;

import java.sql.Connection;
import java.sql.SQLException;

import com.lakumbra.multitenant.sql.TenantSessionCallback;

public class PostgresSessionVariableTenantSessionCallback implements TenantSessionCallback{

	@Override
	public void startTenantSession(Integer tenantId, Connection conn) throws SQLException{
		if(tenantId !=null){
			conn.createStatement().execute("set my.tenant_id="+tenantId);
		}
	}


}
