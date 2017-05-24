package org.moveon.multitenant.sql;

@FunctionalInterface
public interface TenantDBInstanceKeyProvider {

	String getDBInstanceKey(int tenantId);
}
