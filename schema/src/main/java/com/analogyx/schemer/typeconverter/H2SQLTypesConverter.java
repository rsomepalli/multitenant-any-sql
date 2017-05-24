package com.analogyx.schemer.typeconverter;

import com.analogyx.schemer.domain.Columntype;

/**
 * Created by emilkirschner on 09/03/16.
 */
public class H2SQLTypesConverter extends SybaseTypesConverter {
    @Override
    public String schemaTypeToNative(Columntype columnType) {
        if ((columnType.getType()).equals("int"))
            return "integer";
        else {
            String st = super.schemaTypeToNative(columnType);
            if ("image".equals(st)) st = "binary";
            if ("text".equals(st)) st = "varchar(2048)"; // kludge
            return st;
        }
    }
    
    @Override
    public String schemaSequenceToColumnDef(Columntype ct) {
        return "";
    }

    @Override
    public String schemaDataToNative(Columntype columnType, String data) {
        return super.schemaDataToNative(columnType, data);
    }
}
