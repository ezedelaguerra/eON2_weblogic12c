package com.freshremix.model;

import java.math.BigDecimal;

public class SKUBA extends SKU {

	private Integer skuId;
	private Integer skuBAId;
	private BigDecimal purchasePrice;
	private BigDecimal sellingPrice;
	private OrderUnit sellingUom;
	private String skuComment;
	
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public Integer getSkuBAId() {
		return skuBAId;
	}
	public void setSkuBAId(Integer skuBAId) {
		this.skuBAId = skuBAId;
	}
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public OrderUnit getSellingUom() {
		return sellingUom;
	}
	public void setSellingUom(OrderUnit sellingUom) {
		this.sellingUom = sellingUom;
	}
	public String getSkuComment() {
		return skuComment;
	}
	public void setSkuComment(String skuComment) {
		this.skuComment = skuComment;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((purchasePrice == null) ? 0 : purchasePrice.hashCode());
		result = prime * result
				+ ((sellingPrice == null) ? 0 : sellingPrice.hashCode());
		result = prime * result
				+ ((sellingUom == null) ? 0 : sellingUom.hashCode());
		result = prime * result + ((skuBAId == null) ? 0 : skuBAId.hashCode());
		result = prime * result
				+ ((skuComment == null) ? 0 : skuComment.hashCode());
		result = prime * result + ((skuId == null) ? 0 : skuId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SKUBA other = (SKUBA) obj;
		
		if (!super.equals((SKU)other)){
			return false;
		}
		
		if (purchasePrice == null) {
			if (other.purchasePrice != null)
				return false;
		} else if (!purchasePrice.equals(other.purchasePrice))
			return false;
		if (sellingPrice == null) {
			if (other.sellingPrice != null)
				return false;
		} else if (!sellingPrice.equals(other.sellingPrice))
			return false;
		if (sellingUom == null) {
			if (other.sellingUom != null)
				return false;
		} else if (!sellingUom.equals(other.sellingUom))
			return false;
		if (skuBAId == null) {
			if (other.skuBAId != null)
				return false;
		} else if (!skuBAId.equals(other.skuBAId))
			return false;
		if (skuComment == null) {
			if (other.skuComment != null)
				return false;
		} else if (!skuComment.equals(other.skuComment))
			return false;
		if (skuId == null) {
			if (other.skuId != null)
				return false;
		} else if (!skuId.equals(other.skuId))
			return false;
		return true;
	}
	
	
}
