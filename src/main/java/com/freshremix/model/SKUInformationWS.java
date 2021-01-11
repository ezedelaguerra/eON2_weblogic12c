package com.freshremix.model;

import java.math.BigDecimal;

public class SKUInformationWS {
	
	private Integer skuId;
	private String skuName;
	private String skuCategoryName;
	private String skuGroupName;
	private String sellerName;
	private Integer sellerId;
	private String location;
	private String market;
	private String class2;
	private String class1;
	private BigDecimal price1;
	private BigDecimal price2;
	private BigDecimal priceWithoutTax;
	private BigDecimal priceWithTax;
	private BigDecimal packageQuantity;
	private String packageType;
	private String unitOfOrder;
	private String skuExternalID;
	private BigDecimal skuMaxLimit;

	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getSkuGroupName() {
		return skuGroupName;
	}
	public void setSkuGroupName(String skuGroupName) {
		this.skuGroupName = skuGroupName;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
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
	public BigDecimal getPrice1() {
		return price1;
	}
	public void setPrice1(BigDecimal price1) {
		this.price1 = price1;
	}
	public BigDecimal getPrice2() {
		return price2;
	}
	public void setPrice2(BigDecimal price2) {
		this.price2 = price2;
	}
	public BigDecimal getPriceWithoutTax() {
		return priceWithoutTax;
	}
	public void setPriceWithoutTax(BigDecimal priceWithoutTax) {
		this.priceWithoutTax = priceWithoutTax;
	}
	public BigDecimal getPriceWithTax() {
		return priceWithTax;
	}
	public void setPriceWithTax(BigDecimal priceWithTax) {
		this.priceWithTax = priceWithTax;
	}
	public BigDecimal getPackageQuantity() {
		return packageQuantity;
	}
	public void setPackageQuantity(BigDecimal packageQuantity) {
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
	public BigDecimal getSkuMaxLimit() {
		return skuMaxLimit;
	}
	public void setSkuMaxLimit(BigDecimal skuMaxLimit) {
		this.skuMaxLimit = skuMaxLimit;
	}
}
