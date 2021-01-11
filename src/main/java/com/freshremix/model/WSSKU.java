package com.freshremix.model;

import java.math.BigDecimal;

public class WSSKU {
	
	private String skuId;
	private String skuName;
	private String origSkuId;
	private String sheetType;
	private String skuCategoryId;
	private String skuCategoryName;
	private String skuGroupId;
	private String skuGroupName;
	private String status;
	private String sellerName;
	private String sellerId;
	private String buyerId;
	private String buyerName;
	private String company;
	private String location;
	private String market;
	private String grade;
	private String clazz;
	private BigDecimal skuMaxLimit;
	private BigDecimal price1;
	private BigDecimal price2;
	private BigDecimal priceWithoutTax;
	private BigDecimal priceWithTax;
	private BigDecimal packageQuantity;
	private String packageType;
	private String unitOfOrder;
	private String pmfFlag;
	private BigDecimal qty;
	private String externalSkuId;
	private String visibilityFlag;
	
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
	
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
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
	public String getSheetType() {
		return sheetType;
	}
	public void setSheetType(String sheetType) {
		this.sheetType = sheetType;
	}
	public String getSkuCategoryId() {
		return skuCategoryId;
	}
	public void setSkuCategoryId(String skuCategoryId) {
		this.skuCategoryId = skuCategoryId;
	}
	public String getSkuGroupName() {
		return skuGroupName;
	}
	public void setSkuGroupName(String skuGroupName) {
		this.skuGroupName = skuGroupName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
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
	public String getPmfFlag() {
		return pmfFlag;
	}
	public void setPmfFlag(String pmfFlag) {
		this.pmfFlag = pmfFlag;
	}
	public String getExternalSkuId() {
		return externalSkuId;
	}
	public void setExternalSkuId(String externalSkuId) {
		this.externalSkuId = externalSkuId;
	}
	public void setSkuGroupId(String skuGroupId) {
		this.skuGroupId = skuGroupId;
	}
	public String getSkuGroupId() {
		return skuGroupId;
	}
	public String getVisibilityFlag() {
		return visibilityFlag;
	}
	public void setVisibilityFlag(String visibilityFlag) {
		this.visibilityFlag = visibilityFlag;
	}
}
