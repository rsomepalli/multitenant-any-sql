package com.lakumbra.multitenant.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface TenantSessionCallback {
	void startTenantSession(int tenantId, Connection conn) throws SQLException;
}
