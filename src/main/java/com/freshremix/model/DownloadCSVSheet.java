/**
 * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
 * <BR>
 * <B>Description:</B><BR>
 * Describe class or interface.<BR>
 * <BR>
 * <B>Known Bugs:</B>
 * none<BR>
 * <BR>
 * <B>History:</B>
 * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
 * date       name            changes
 * ------------------------------------------------------------------------------
 * 2010/05/17		Jovino Balunan		
 */
package com.freshremix.model;

import java.math.BigDecimal;

import com.freshremix.util.TaxUtil;

/**
 * @author Jovino Balunan
 *
 */
public class DownloadCSVSheet {
	private String isNewSKU;
	private String typeFlag;
	private Integer akadenSkuId;
	private Integer skuId;
	private String skuName;
	private Integer skuCategoryId;
	private String skuGroupName;
	private Company company;
	private User user;
	private String location;
	private String market;
	private String grade;
	private String clazz;
	private BigDecimal price1;
	private BigDecimal price2;
	private BigDecimal priceWithoutTax;
	private BigDecimal priceWithTax;
	private BigDecimal packageQuantity;
	private String packageType;
	private OrderUnit orderUnit;
	//	private Integer quantity;
	private BigDecimal totalQuantity;
	private BigDecimal unitPrice;
//	private String comments;
//	private String buyerId;

//	public String getBuyerId() {
//		return buyerId;
//	}
//	public void setBuyerId(String buyerId) {
//		this.buyerId = buyerId;
//	}
	public String getIsNewSKU() {
		return isNewSKU;
	}
	public void setIsNewSKU(String isNewSKU) {
		this.isNewSKU = isNewSKU;
	}
	public Integer getAkadenSkuId() {
		return akadenSkuId;
	}
	public void setAkadenSkuId(Integer akadenSkuId) {
		this.akadenSkuId = akadenSkuId;
	}
//	public String getComments() {
//		return comments;
//	}
//	public void setComments(String comments) {
//		this.comments = comments;
//	}
	public BigDecimal getPriceWithTax() {
		return priceWithTax;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
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
	public String getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
	public Integer getSkuCategoryId() {
		return skuCategoryId;
	}
	public void setSkuCategoryId(Integer skuCategoryId) {
		this.skuCategoryId = skuCategoryId;
	}
	public String getSkuGroupName() {
		return skuGroupName;
	}
	public void setSkuGroupName(String skuGroupName) {
		this.skuGroupName = skuGroupName;
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
		this.priceWithTax = TaxUtil.getPriceWithTax(priceWithoutTax, TaxUtil.ROUND);
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
//	public Integer getQuantity() {
//		return quantity;
//	}
//	public void setQuantity(Integer quantity) {
//		this.quantity = quantity;
//	}
	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public OrderUnit getOrderUnit() {
		return orderUnit;
	}
	public void setOrderUnit(OrderUnit orderUnit) {
		this.orderUnit = orderUnit;
	}
}