MySQL approach to achieve multitenancy is to add a column to every table that hold tenant data and create a view on the table that gets the tenant from a session variable that is set by the MySqlSessionVariableTenantSessionCallback.
 for eg. In a web application the tenantId could should be set after validating the user credentials and knowing the tenant of the user.
 ```
 
 CREATE TABLE tbl_person ( tenant int NULL,  id int(100) NOT NULL,  name varchar(256) NOT NULL,  age number(256) NOT NULL,  last_updated timestamp DEFAULT now() NOT NULL,  date_created timestamp DEFAULT now() NOT NULL ) ;
CREATE TRIGGER before_insert_tbl_person BEFORE INSERT ON tbl_person FOR EACH ROW SET new.tenant = gettenant();
CREATE TRIGGER before_update_tbl_person BEFORE UPDATE ON tbl_person FOR EACH ROW SET new.last_updated = now();
ALTER TABLE tbl_person ADD CONSTRAINT ct_primarykey PRIMARY KEY CLUSTERED ( tenant, id );
CREATE OR REPLACE VIEW person ( id,  name,  age,  last_updated,  date_created ) AS SELECT  tbl_person.id,  tbl_person.name,  tbl_person.age,  tbl_person.last_updated,  tbl_person.date_created from tbl_person where (tbl_person.tenant = gettenant());
 
 
 You could use the schema mysql generation tool to generate the table, 
 //TODO : Link to schema readme.md
 ```
 sample schema xml looks like below, produces the ddl above.
 ```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<database xmlns="http://www.analogyx.com/schemer/domain">
	<table name="person">
		<column name="id" nullable="false" type="int" scale="100"/>
		<column name="name" nullable="false" type="string" scale="256"/>
		<column name="age" nullable="false" type="number" scale="256" />
		<index primary="true">
			<on>id</on>
		</index>
	</table>
</database>
```	

 
 sql function to get the tenant from session variable is 
 ```
 	CREATE FUNCTION gettenant() RETURNS INT 
	BEGIN
		RETURN @tenant;
	END
 ```
 
 Once the tables and  are created, you can test it at the db level by 
 ```
 set @tenant=1
 insert into person(id, name, age) values(1, 'test', 24);
 ```
 select * from tbl_person; // should have the tenant column populated with 1.
 When you are writing application level code you would typically never use tbl_person, that way tenant related code is only in the layer that validates the user and set the tenant.
 
 Now to do at the application level..
 
 1. All tenants are in the same DB
  - Build a TenantAwareDataSource.
  		Initial application setup
  		TenantAwareDataSource tds = new TenantAwareDataSource();
  		DataSource ds = //Build datasource of your choice, like Basic DataSource etc.
  		tds.addDbInstance("default", ds);
  		tds.setTenantContext(tenantContext);
		tds.setTenantSessionCallback(new MySqlSessionVariableTenantSessionCallback());
		tds.setTenantDBInstanceProvider(tenantId->"default"); // always return the same datasource irrespective of the tenant.
	 -- Make sure the TenantContext instance that you set into TenantAwareDataSource is shared with the code that sets the tenant at user authorization time/entry time.
	 //Code at entry while validating user auth
	 ```	tenantContext.set(1);
		//Code executing in each thread.
		some where in the code at entry during user authentication
		Connection conn = tds.getConnection();
		conn.createStatement().execute("insert into person(id, name, age) values(1),'testname',24)");
		conn.commit();
		conn.close();
	```	
  		
  - 
  
 2. You want to distribute tenant across multiple DB server instances
 
