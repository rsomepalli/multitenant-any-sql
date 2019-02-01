package com.analogyx.schemer.instance;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.ImmutableList;

public class SQLScriptSet {

	private Map<STMTTYPE, List<String>> data = new TreeMap<>();
	
	public enum STMTTYPE {
		DROP, TABLE, INDEXES, TRIGGERS, VIEWS, DATA;
	}
	
	public SQLScriptSet(){
		data.put(STMTTYPE.DROP, new ArrayList<>());
		data.put(STMTTYPE.TABLE, new ArrayList<>());
		data.put(STMTTYPE.INDEXES, new ArrayList<>());
		data.put(STMTTYPE.TRIGGERS, new ArrayList<>());
		data.put(STMTTYPE.VIEWS, new ArrayList<>());
		data.put(STMTTYPE.DATA, new ArrayList<>());
	}
	

	public List<String> getTables() {
		return ImmutableList.copyOf(data.get(STMTTYPE.TABLE));
	}

	public List<String> getTriggers() {
		return ImmutableList.copyOf(data.get(STMTTYPE.TRIGGERS));
	}
	
	public List<String> getIndexes() {
		return ImmutableList.copyOf(data.get(STMTTYPE.INDEXES));
	}

	public List<String> getViews() {
		return ImmutableList.copyOf(data.get(STMTTYPE.VIEWS));
	}

	public List<String> getData() {
		return ImmutableList.copyOf(data.get(STMTTYPE.DATA));
	}

	
	public List<String> getDropStatements() {
		return ImmutableList.copyOf(data.get(STMTTYPE.DROP));
	}

	public void addTable(String table) {
		this.data.get(STMTTYPE.TABLE).add(table);
	}

	public void addTrigger(String trigger){
		this.data.get(STMTTYPE.TRIGGERS).add(trigger);
	}
	public void addIndex(String index) {
		this.data.get(STMTTYPE.INDEXES).add(index);
	}

	public void addView(String view) {
		this.data.get(STMTTYPE.VIEWS).add(view);
	}

	public void addData(String data) {
		this.data.get(STMTTYPE.DATA).add(data);
	}
	
	public void addDropStatement(String stmt){
		this.data.get(STMTTYPE.DROP).add(stmt);
	}
	
	public void addAllTables(List<String> tables) {
		this.data.get(STMTTYPE.TABLE).addAll(tables);
	}

	public void addAllTrigger(List<String> triggers){
		this.data.get(STMTTYPE.TRIGGERS).addAll(triggers);
	}
	public void addAllIndexs(List<String> indexes) {
		this.data.get(STMTTYPE.INDEXES).addAll(indexes);
	}

	public void addAllView(List<String> views) {
		this.data.get(STMTTYPE.VIEWS).addAll(views);
	}

	public void addAllData(List<String> data) {
		this.data.get(STMTTYPE.DATA).addAll(data);
	}
	
	public void addDropStatements(List<String> stmts){
		this.data.get(STMTTYPE.DROP).addAll(stmts);
	}
	
	public List<String> getDDL(){
		List<String> ddl = new ArrayList<>();
		ddl.addAll(this.data.get(STMTTYPE.DROP));
		ddl.addAll(this.data.get(STMTTYPE.TABLE));
		ddl.addAll(this.data.get(STMTTYPE.TRIGGERS));
		ddl.addAll(this.data.get(STMTTYPE.INDEXES));
		ddl.addAll(this.data.get(STMTTYPE.VIEWS));
		return ddl;
	}
	public void print(PrintStream writer){
		data.keySet().forEach(key -> this.data.get(key).forEach(stmt ->writer.println(stmt+";")));
	}
}
