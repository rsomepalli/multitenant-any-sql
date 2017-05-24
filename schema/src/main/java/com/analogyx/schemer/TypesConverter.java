package com.analogyx.schemer;

import com.analogyx.schemer.domain.Columntype;

public interface TypesConverter {
	
	/**
	 * These are used to generate native sql based on the generic xml definition that is stored in the Columntype structure.
	 * @param ct jaxb generated Columntype
	 * @return Native string representation of the type.
	 */
	public String schemaTypeToNative(Columntype ct);
	public String schemaScaleToNative(Columntype ct);
	public String schemaSequenceToColumnDef(Columntype ct);
	public String schemaDefaultToNative(Columntype ct);
	public String schemaDataToNative(Columntype ct, String data);
	public String schemaNullToNative(Columntype ct);
	
	/**
	 * Take a native column type definition, and create a generic Columntype view of it, usefull for comparing the view
	 * with the schema.
	 * @param table
	 * @param column
	 * @param type
	 * @param scale
	 * @param nulls
	 * @param def
	 * @return generic view of column as a Columntype.
	 */
	public Columntype nativeToSchemaColumnDesc(String table, String column, short type, int scale, boolean nulls, String def);

    String functionToNative(Columntype columnDefinition, String dbValue);
};