package com.netcracker.rest.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Agreement {
	private String name;

	private String signedBy;

	private List<Product> products;
	
	private String getDate() {
	    LocalDateTime myDateObj = LocalDateTime.now();   
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
	    String formattedDate = myDateObj.format(myFormatObj); 
	    return formattedDate;
	}

	public Agreement() {
		super();
	}

	public Agreement(String signedBy, List<Product> products) {
		this.name = "Agreement " + getDate();
		this.signedBy = signedBy;
		this.products = products;
	}
	
	public Agreement(String name,String signedBy, List<Product> products) {
		this.name = name;
		this.signedBy = signedBy;
		this.products = products;
	}
	
	public Agreement(String name,String signedBy) {
		this.name = name;
		this.signedBy = signedBy;
		this.products = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSignedBy() {
		return signedBy;
	}

	public void setSignedBy(String signedBy) {
		this.signedBy = signedBy;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}