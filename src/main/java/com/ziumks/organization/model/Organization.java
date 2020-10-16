package com.ziumks.organization.model;

import java.io.Serializable;

public class Organization implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	
	public Organization() {
		
	}
	
	public Organization(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
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