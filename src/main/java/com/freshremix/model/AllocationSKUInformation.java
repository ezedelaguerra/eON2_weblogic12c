package com.freshremix.model;

import java.math.BigDecimal;

public class AllocationSKUInformation {
	
	private String skuId;
	private String skuName;
	private String origSkuId;
	private String skuCategoryName;
	private String skuGroupName;
	private String sellerName;
	private BigDecimal skuMaxLimit;
	private String location;//10
	private String market;
	private String class2;
	private String class1;
	private String price1;
	private String price2;
	private String priceWithoutTax;
	private String priceWithTax;
	private String packageQuantity;
	private String packageType;
	private String unitOfOrder;
	private String skuExternalID;
	
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getOrigSkuId() {
		return origSkuId;
	}
	public void setOrigSkuId(String origSkuId) {
		this.origSkuId = origSkuId;
	}
	public String getSkuGroupName() {
		return skuGroupName;
	}
	public void setSkuGroupName(String skuGroupName) {
		this.skuGroupName = skuGroupName;
	}
	public BigDecimal getSkuMaxLimit() {
		return skuMaxLimit;
	}
	public void setSkuMaxLimit(BigDecimal skuMaxLimit) {
		this.skuMaxLimit = skuMaxLimit;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getPrice1() {
		return price1;
	}
	public void setPrice1(String price1) {
		this.price1 = price1;
	}
	public String getPrice2() {
		return price2;
	}
	public void setPrice2(String price2) {
		this.price2 = price2;
	}
	public String getPriceWithoutTax() {
		return priceWithoutTax;
	}
	public void setPriceWithoutTax(String priceWithoutTax) {
		this.priceWithoutTax = priceWithoutTax;
	}
	public String getPriceWithTax() {
		return priceWithTax;
	}
	public void setPriceWithTax(String priceWithTax) {
		this.priceWithTax = priceWithTax;
	}
	public String getPackageQuantity() {
		return packageQuantity;
	}
	public void setPackageQuantity(String packageQuantity) {
		this.packageQuantity = packageQuantity;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public String getUnitOfOrder() {
		return unitOfOrder;
	}
	public void setUnitOfOrder(String unitOfOrder) {
		this.unitOfOrder = unitOfOrder;
	}
	
	public String getClass2() {
		return class2;
	}
	public void setClass2(String class2) {
		this.class2 = class2;
	}
	public String getClass1() {
		return class1;
	}
	public void setClass1(String class1) {
		this.class1 = class1;
	}
	
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSkuExternalID() {
		return skuExternalID;
	}
	public void setSkuExternalID(String skuExternalID) {
		this.skuExternalID = skuExternalID;
	}
	public String getSkuCategoryName() {
		return skuCategoryName;
	}
	public void setSkuCategoryName(String skuCategoryName) {
		this.skuCategoryName = skuCategoryName;
	}
}
