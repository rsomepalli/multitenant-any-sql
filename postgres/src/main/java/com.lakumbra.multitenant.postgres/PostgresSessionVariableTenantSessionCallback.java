package com.lakumbra.multitenant.postgres;

import java.sql.Connection;
import java.sql.SQLException;

import com.lakumbra.multitenant.sql.TenantSessionCallback;

public class PostgresSessionVariableTenantSessionCallback implements TenantSessionCallback{

	@Override
	public void startTenantSession(int tenantId, Connection conn) throws SQLException{
		conn.createStatement().execute("set my.tenant_id="+tenantId);
	}

	
}
