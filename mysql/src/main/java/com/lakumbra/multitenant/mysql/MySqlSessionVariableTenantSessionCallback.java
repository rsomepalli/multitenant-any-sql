package com.lakumbra.multitenant.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import com.lakumbra.multitenant.sql.TenantSessionCallback;

public class MySqlSessionVariableTenantSessionCallback implements TenantSessionCallback{

	@Override
	public void startTenantSession(Integer tenantId, Connection conn) throws SQLException{
		if(tenantId!=null){
			conn.createStatement().execute("set @tenant="+tenantId);
		}
	}


}
