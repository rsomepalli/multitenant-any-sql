package com.lakumbra.multitenant.sql;

public class TenantContext {
	private InheritableThreadLocal<Integer> tenant = new InheritableThreadLocal<>();
	
	public void set(int tenantId ){
		tenant.set(tenantId);
	}

	public int getTenantId() {
		return tenant.get();
	}
}
