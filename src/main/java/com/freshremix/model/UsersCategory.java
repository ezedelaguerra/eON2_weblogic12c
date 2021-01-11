package com.freshremix.model;

public class UsersCategory {
	private Integer  categoryId;
	private Integer userId;
	private String categoryAvailable;
	
	public UsersCategory() { }
	
	public UsersCategory(Integer categoryId, Integer userId,
			String categoryAvailable) {
		super();
		this.categoryId = categoryId;
		this.userId = userId;
		this.categoryAvailable = categoryAvailable;
	}
	
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getCategoryAvailable() {
		return categoryAvailable;
	}
	public void setCategoryAvailable(String categoryAvailable) {
		this.categoryAvailable = categoryAvailable;
	}
}
