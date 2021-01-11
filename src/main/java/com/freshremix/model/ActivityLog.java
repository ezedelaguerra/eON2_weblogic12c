package com.freshremix.model;

public class ActivityLog {
	
	
	private Integer activityLogId;
	private Integer userId;
	private String username;
	private String timestamp;
	private String date;
	private String sheetName;
	private String action;
	private String deliveryDate;
	private String targetBuyerId;
	private String targetSellerId;
	
	
	public Integer getActivityLogId() {
		return activityLogId;
	}
	public void setActivityLogId(Integer activityLogId) {
		this.activityLogId = activityLogId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getTargetBuyerId() {
		return targetBuyerId;
	}
	public void setTargetBuyerId(String targetBuyerId) {
		this.targetBuyerId = targetBuyerId;
	}
	public String getTargetSellerId() {
		return targetSellerId;
	}
	public void setTargetSellerId(String targetSellerId) {
		this.targetSellerId = targetSellerId;
	}
}
