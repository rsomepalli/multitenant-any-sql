package com.analogyx.schemer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.analogyx.schemer.domain.Columntype;
import com.analogyx.schemer.domain.ObjectFactory;

public abstract class GenericTypesConverter implements TypesConverter {

 	protected final Logger log = LoggerFactory.getLogger("GenericTypesConverter");

    public String schemaTypeToNative(Columntype ct) {
        log.warn("Conversion of schema type from "
                 + ct.getType()
                 + " to native not specificaly made.");
        return ct.getType();        
    }

    public String schemaScaleToNative(Columntype columnType) {
        if( columnType.getType().equals("binary") ||
                columnType.getType().equals("timestamp") ) return "";
        if( columnType.getScale() == 1 && 
                !columnType.getType().equals("string") ) return "";
        return "("+new Integer(columnType.getScale()).toString()+")";
    }

    public  String schemaDefaultToNative(Columntype columnType) {
    	 if(columnType.getDefault() != null && columnType.isDefaultExpression() ){
    		 return columnType.getDefault();
    	 }else if( columnType.getDefault() != null ){
   			 return schemaDataToNative(columnType, columnType.getDefault());
    	 }else{
    		 return "";
    	 }
    }

    public String schemaNullToNative(Columntype ct) {
        return ct.isNullable()?"NULL":"NOT NULL";
    }

    public String schemaDataToNative(Columntype columnType, String data) {
        if (data == null || data.length()==0)               return "null";
        else if( columnType.getType().equals("int"))             return data;
        else if( columnType.getType().equals("float") )     return data;
        else if( columnType.getType().equals("string") )    return "'"+data.replaceAll("'","''")+"'";
        else if( columnType.getType().equals("timestamp") ) return "{ts '"+data+"'}";
        else return data;
    }

    public Columntype nativeToSchemaColumnDesc(String table, String column, short type, int scale, boolean nulls, String def) {
        String finaltype = null;
        if( scale <= 0 ) {
            scale = 1;
            log.warn("Assuming scale of 1 for column: "+column+" on table: "+table);
        }
        if( type == java.sql.Types.ARRAY)               finaltype = "array";
        else if( type == java.sql.Types.BINARY )        finaltype = "binary";
        else if( type == java.sql.Types.BIT)            finaltype = "boolean";
        else if( type == java.sql.Types.BLOB )          finaltype = "binary";
        else if( type == java.sql.Types.BOOLEAN)        finaltype = "boolean";
        else if( type == java.sql.Types.CHAR)           finaltype = "string";
        else if( type == java.sql.Types.CLOB )          finaltype = "text";
        else if( type == java.sql.Types.DATALINK)       finaltype = "datalink";
        else if( type == java.sql.Types.DATE)           finaltype = "date";
        else if( type == java.sql.Types.DISTINCT)       finaltype = "distinct";
        else if( type == java.sql.Types.DOUBLE)         finaltype = "float";
        else if( type == java.sql.Types.FLOAT )         finaltype = "float";
        else if( type == java.sql.Types.JAVA_OBJECT)    finaltype = "object";
        else if( type == java.sql.Types.LONGVARBINARY)  finaltype = "binary";
        else if( type == java.sql.Types.LONGVARCHAR)    finaltype = "text";
        else if( type == java.sql.Types.NULL)           finaltype = "null";
        else if( type == java.sql.Types.OTHER )         finaltype = "other";
        else if( type == java.sql.Types.REAL )          finaltype = "float";
        else if( type == java.sql.Types.REF)            finaltype = "ref";
        else if( type == java.sql.Types.STRUCT)         finaltype = "struct";
        else if( type == java.sql.Types.TIME)           finaltype = "time";
        else if( type == java.sql.Types.TIMESTAMP)      finaltype = "timestamp";
        else if( type == java.sql.Types.VARBINARY)      finaltype = "binary";
        else if( type == java.sql.Types.VARCHAR)        finaltype = "string";
        else if( type == java.sql.Types.BIGINT )        finaltype = "bigint";
        else if( type == java.sql.Types.DECIMAL)        finaltype = "int";
        else if( type == java.sql.Types.INTEGER)        finaltype = "integer";
        else if( type == java.sql.Types.TINYINT )       finaltype = "tinyint";
        else if( type == java.sql.Types.NUMERIC)        finaltype = "int";
        else if( type == java.sql.Types.SMALLINT)       finaltype = "smallint";
        else {
            finaltype = "unknown";
            log.error("Assuming type of unknown for column: "+column+" on table: "+table);
        }
        if( finaltype.equals("int") ||
                finaltype.equals("float") ||
                finaltype.equals("binary") ||
                finaltype.equals("timestamp") ||
                finaltype.equals("struct") ||
                finaltype.equals("ref") ||
                finaltype.equals("other") ||
                finaltype.equals("null") ||
                finaltype.equals("textual") ||
                finaltype.equals("object") ||
                finaltype.equals("distinct") ||
                finaltype.equals("datalink") ||
                finaltype.equals("array") ||             
                finaltype.equals("smallint") ||
                finaltype.equals("tinyint") ||
                finaltype.equals("integer") ||
                finaltype.equals("decimal") ) {
            scale = 1;
        }
        return newColumntype(column, finaltype, scale, nulls, def);
    }

    private Columntype newColumntype(String name, String type, int scale, boolean nullb, String def) {
        Columntype ct = null;
        try {
            ct = (Columntype) new ObjectFactory().createColumntype();
            ct.setName(name);
            ct.setType(type);
            ct.setScale(scale);
            ct.setNullable(nullb);
            ct.setDefault(def);
        } catch (Exception e) {
            log.error("Can not create new column type. "+e);
        }
        return ct;
    }

    @Override
    public String functionToNative(Columntype columnDefinition, String dbValue) {
        if ("@@max+1".equals(dbValue))
            return "max(" + columnDefinition.getName() + ")+1";
        throw new IllegalStateException("unknown function: " + dbValue);
    }
};