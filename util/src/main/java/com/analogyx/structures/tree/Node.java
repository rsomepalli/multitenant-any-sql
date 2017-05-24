package com.analogyx.structures.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private T data;
	private Node<T> parent;
	private List<Node<T>> children = new ArrayList<Node<T>>();

	public Node(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public void setChildren(List<Node<T>> children) {
		this.children = children;

	}

	public Node<T> addChild(Node<T> child) {
		this.children.add(child);
		return child;

	}

	public Node<T> getParent() {
		return parent;
	}

	public void setParent(Node<T> parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Node [data=" + data + "]";
	}

	public boolean isEqual(Node<T> node) {
		return node.getData().equals(data);
	}

	public void print(String prefix) {
		System.out.println(prefix + this.getData());
		if(children.size()>0){
			for(Node<T> child: children){
				child.print(prefix+prefix);
			}
		}
	}
}
