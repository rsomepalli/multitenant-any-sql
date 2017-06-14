# Abstract
This modules helps build a db for multitenant applications and builds a DataSource/Connection that removes the burden from programmers to have to deal with the tenantId in the code and pushes this reposnsibility to the entry point, may be a filter or authentication layer.

# Schema Module
- Schema module is used to generate tables/views based on xml
- run com.analogyx.schemer.instance.DbScriptGenerator filename to generate the tables
- 
# Multitenant SQL DataSource implementation
- Sample code for usage is available in  file [TestMySQLDB.java](/mysql/src/test/java/com/analogyx/samples/mysql/TestMySQLDB.java)

# API Overview
- [API Overview](/core/README.md)
