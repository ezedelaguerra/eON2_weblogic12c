package com.freshremix.model;

import com.freshremix.util.NumberUtil;

public class SKUGroup {

	private Integer skuGroupId;
	private Integer origSkuGroupId;
	private Integer sellerId;
	private String sellerName;
	private Integer categoryId;
	private String description;
	private String startDate;
	private String endDate;

	public Integer getSkuGroupId() {
		return skuGroupId;
	}
	public void setSkuGroupId(Integer skuGroupId) {
		this.skuGroupId = skuGroupId;
	}
	public Integer getOrigSkuGroupId() {
		return origSkuGroupId;
	}
	public void setOrigSkuGroupId(Integer origSkuGroupId) {
		this.origSkuGroupId = origSkuGroupId;
	}	
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((skuGroupId == null) ? 0 : skuGroupId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SKUGroup other = (SKUGroup) obj;
		if (skuGroupId == null) {
			if (other.skuGroupId != null)
				return false;
		} else if (!equally(skuGroupId,other.skuGroupId))
			return false;
		return true;
	}
	
	private boolean equally (Integer value1, Integer value2) {
		return NumberUtil.nullToZero(value1).equals(NumberUtil.nullToZero(value2));
	}
}
