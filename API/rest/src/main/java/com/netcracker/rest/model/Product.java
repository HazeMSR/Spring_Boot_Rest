package com.netcracker.rest.model;

import java.util.List;

public class Product {
	private String name;
	
	private Float price;
	
	private String parent;
	
	private List<Product> children;

	public Product() {
		super();
	}
	
	public Product(String name, Float price, String parent, List<Product> children) {
		this.name = name;
		this.price = price;
		this.parent = parent;
		this.children = children;
	}
	
	public Product(String name, Float price, String parent) {
		this.name = name;
		this.price = price;
		this.parent = parent;
		this.children = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<Product> getChildren() {
		return children;
	}

	public void setChildren(List<Product> children) {
		this.children = children;
	}
}
