package com.lakumbra.multitenant.sql;

@FunctionalInterface
public interface TenantDBInstanceKeyProvider {

	String getDBInstanceKey(Integer tenantId);
}
