package com.freshremix.model;

public class Category {
	
	private Integer categoryId;
	private String description;
	private String tabName;
	
	public Category() { }
	
	public Category(Integer categoryId, String description, String tabName) {
		this.categoryId = categoryId;
		this.description = description;
		this.tabName = tabName;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
}
