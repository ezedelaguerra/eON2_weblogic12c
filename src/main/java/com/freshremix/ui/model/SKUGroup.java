package com.freshremix.ui.model;

public class SKUGroup {

	private Long skuGroupID;
	private String companySellerId;
	private Integer categoryId;
	private String status;
	private String description;
	
	public Long getSkuGroupId() {
		return skuGroupID;
	}
	public void setSkuGroupId(Long skuGroupID) {
		this.skuGroupID = skuGroupID;
	}
	public String getCompanySellerId() {
		return companySellerId;
	}
	public void setCompanySellerId(String companySellerId) {
		this.companySellerId = companySellerId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
