TenantDBInstanceKeyProvider - return the ID of the db instance for the tenant, useful when we want to route tenants to corresponding db instances.
 

TenantContext - gives the id of the current tenant in the thread, TenantContext.set(int tenantId ) should be  called at application entry may be during login or other means of identification for the tenant e.g based on the url endpoint, the same instance of TenantContext should be shared between the application threads and the TenantAwareDataSource

TenantContext is thread safe and should be shared across all threads, TenantContext.set(tenantId) needs to be called at entry when a thread is working for a tenant.

TenantSessionCallback sets the connection session to tenant, this is Database Implementation specific and need not be implemented by the user, use the datasource specific TenantAwareDataSource like MysqlTenantAwareDataSource 
    
TenantAwareDataSource is initialized with an instance of TenantDBInstanceProvider(useful to route to different Database instances 