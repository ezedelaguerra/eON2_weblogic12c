package com.freshremix.model;




public class UserDealingPattern {
	
	// generated id
	private Integer userDPId;
	private String userName;
	private Integer compDPId;
	private Integer userId;
	private String selUserIds;
	private Integer dpRelationId;
	private String description;
	private String expiryDateFrom;
	private String expiryDateTo;
	private String userList;
	
	public String getUserList() {
		return userList;
	}
	public void setUserList(String userList) {
		this.userList = userList;
	}
	public Integer getUserDPId() {
		return userDPId;
	}
	public void setUserDPId(Integer userDPId) {
		this.userDPId = userDPId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSelUserIds() {
		return selUserIds;
	}
	public void setSelUserIds(String selUserIds) {
		this.selUserIds = selUserIds;
	}
	public Integer getCompDPId() {
		return compDPId;
	}
	public void setCompDPId(Integer compDPId) {
		this.compDPId = compDPId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getDpRelationId() {
		return dpRelationId;
	}
	public void setDpRelationId(Integer dpRelationId) {
		this.dpRelationId = dpRelationId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExpiryDateFrom() {
		return expiryDateFrom;
	}
	public void setExpiryDateFrom(String expiryDateFrom) {
		this.expiryDateFrom = expiryDateFrom;
	}
	public String getExpiryDateTo() {
		return expiryDateTo;
	}
	public void setExpiryDateTo(String expiryDateTo) {
		this.expiryDateTo = expiryDateTo;
	}
}
