package com.analogyx.schemer.typeconverter;

import java.sql.Types;

import com.analogyx.schemer.GenericTypesConverter;
import com.analogyx.schemer.domain.Columntype;

public class MySQLTypesConverter extends GenericTypesConverter {

    public String schemaTypeToNative(Columntype columnType) {
        if (columnType.getType().equals("string"))
            return "varchar";
        else if (columnType.getType().equals("timestamp"))
            return "timestamp";
        else if (columnType.getType().equals("binary"))
            return "image";
        else
            return super.schemaTypeToNative(columnType);
    }

    @Override
    public String schemaSequenceToColumnDef(Columntype ct) {
        if (ct != null && ct.isSequence())
            return "AUTO_INCREMENT";
        else
            return "";
    }

    @Override
    public Columntype nativeToSchemaColumnDesc(String table, String column, short type, int scale, boolean nulls, String def) {
        Columntype columntype = super.nativeToSchemaColumnDesc(table, column, type, scale, nulls, def);
        if (type == java.sql.Types.DECIMAL || type == Types.INTEGER)
            columntype.setType("int");
        return columntype;
    }
    
    @Override
    public String schemaDataToNative(Columntype columnType, String data) {
        if( "timestamp".equals(columnType.getType()) ) {
            return "'"+data+"'";
        }
        else {
            return super.schemaDataToNative(columnType, data);
        }
    }

};