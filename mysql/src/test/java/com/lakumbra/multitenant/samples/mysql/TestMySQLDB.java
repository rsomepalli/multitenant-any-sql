package com.lakumbra.multitenant.samples.mysql;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.lakumbra.multitenant.mysql.MysqlTenantAwareDataSource;
import com.lakumbra.multitenant.sql.TenantAwareDataSource;
import com.lakumbra.multitenant.sql.TenantContext;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class TestMySQLDB {
	public static void main(String[] args) throws Exception{
		
		TenantContext tenantContext = new TenantContext();
		
		// typically does when application is started, like initialization of spring configuration etc.
		TenantAwareDataSource tds = buildTenantDataSource(tenantContext);
		
		//Thread
		tenantContext.set(1);
		Connection conn = tds.getConnection();
		conn.setAutoCommit(false);
		conn.createStatement().execute("insert into retailer(id, name, retailerTimeZone) values(seq('retailer', 1), 'testretailer','tz1')");
		conn.commit();
		conn.close();
		
		//Thread
		tenantContext.set(15);
		conn = tds.getConnection();
		conn.setAutoCommit(false);
		conn.createStatement().execute("insert into retailer(id, name, retailerTimeZone) values(seq('retailer', 1), 'testretailer','tz1')");
		conn.commit();
		conn.close();
		
		//Thread
		tenantContext.set(25);
		conn = tds.getConnection();
		conn.setAutoCommit(false);
		conn.createStatement().execute("insert into retailer(id, name, retailerTimeZone) values(seq('retailer', 1), 'testretailer','tz1')");
		conn.commit();
		conn.close();
		
	}

	private static TenantAwareDataSource buildTenantDataSource(TenantContext tenantContext) {
		MysqlTenantAwareDataSource tds = new MysqlTenantAwareDataSource();
		Map<String, DataSource> dbInstances = new HashMap<>();
		MysqlDataSource ds1 = new MysqlDataSource();
		ds1.setUrl("jdbc:mysql://localhost:3306/mt1");
		ds1.setUser("root");
		
		MysqlDataSource ds2 = new MysqlDataSource();
		ds2.setUrl("jdbc:mysql://localhost:3306/mt2");
		ds2.setUser("root");
		dbInstances.put("batch1", ds1);
		dbInstances.put("batch2", ds2);
		dbInstances.put("default", ds1);
		
		tds.setDbInstances(dbInstances);
		tds.setTenantContext(tenantContext);
		tds.setTenantDBInstanceProvider(tenantId->{
													if(tenantId<=10){
														return "batch1";
													}else if(tenantId <=20){
														return "batch2";
													}else{
														return "default";
													}});
		return tds;
	}
}
