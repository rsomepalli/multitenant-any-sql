package com.analogyx.samples.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import org.moveon.multitenant.sql.TenantSessionCallback;

public class MySqlSessionVariableTenantSessionCallback implements TenantSessionCallback{

	@Override
	public void startTenantSession(int tenantId, Connection conn) throws SQLException{
		conn.createStatement().execute("set @tenant="+tenantId);
	}

	
}
