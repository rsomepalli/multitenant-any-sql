package com.lakumbra.multitenant.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import com.lakumbra.multitenant.sql.TenantSessionCallback;

public class MySqlSessionVariableTenantSessionCallback implements TenantSessionCallback{

	@Override
	public void startTenantSession(int tenantId, Connection conn) throws SQLException{
		conn.createStatement().execute("set @tenant="+tenantId);
	}

	
}
