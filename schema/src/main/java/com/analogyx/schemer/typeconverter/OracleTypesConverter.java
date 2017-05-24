package com.analogyx.schemer.typeconverter;

import com.analogyx.schemer.domain.Columntype;

public class OracleTypesConverter extends GenericTypesConverter {
	
	public String schemaTypeToNative(Columntype columnType) {
		if( columnType.getType().equals("string"))
			return "varchar2";
		else if( columnType.getType().equals("timestamp"))
			return "timestamp";
		else if( columnType.getType().equals("binary")) 	
			return "blob";
		else if( columnType.getType().equals("text")) 	
			return "clob";
		else 
			return super.schemaTypeToNative(columnType);
	}

    @Override
    public String schemaSequenceToColumnDef(Columntype ct) {
        return "";
    }

    @Override
	public String schemaDataToNative(Columntype columnType, String data) {
		if( "timestamp".equals(columnType.getType()) ) {
			return "TIMESTAMP'"+data+"'";
		}
		else {
			return super.schemaDataToNative(columnType, data);
		}
	}
}