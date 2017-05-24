package com.analogyx.schemer;

import java.util.List;

import com.analogyx.schemer.domain.Tabletype;

public interface SQLGenerator {
	public List<String> generateCreateTable(Tabletype tabletype);
	public List<String> generateCreateView(Tabletype tabletype);
	public List<String> generateDropTables(Tabletype tabletype);
	public String generateSeqInitSQL(Tabletype tabletype);
}
