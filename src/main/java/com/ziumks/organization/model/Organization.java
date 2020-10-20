package com.ziumks.organization.model;

import java.io.Serializable;

public class Organization implements Serializable{
	
	private static final int serialVersionUID = 1;

	private int id;
	private String name;
	
	public Organization() {
		
	}
	
	public Organization(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Organization [id=" + getId() + ", name=" + getName() + "]";
	}
}