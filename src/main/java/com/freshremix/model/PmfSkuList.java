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
 * Feb 17, 2010		pamela		
 */
package com.freshremix.model;

import java.math.BigDecimal;

import com.freshremix.util.TaxUtil;

/**
 * @author Pammie
 *
 */

public class PmfSkuList {
	
	private Integer skuId;
	private Integer pmfId;
	private Integer skuGroup;
	private String skuName;
	private String sellerProdCode;
	private String buyerProdCode;
	private String commentA;
	private String commentB;
	private String location;
	private String market;
	private String grade;
	private String pmfClass;
	private BigDecimal price1;
	private BigDecimal price2;
	private BigDecimal priceNoTax;
	private BigDecimal pricewTax;
	private BigDecimal pkgQuantity;
	private String pkgType;
	private Integer unitOrder;
	private String sellerName;
	private Integer categoryId;
	private Integer sellerId;
	private String companyName;
	
	public PmfSkuList(){}
	
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public void setPmfId(Integer pmfId) {
		this.pmfId = pmfId;
	}
	public void setSkuGroup(Integer skuGroup) {
		this.skuGroup = skuGroup;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public void setSellerProdCode(String sellerProdCode) {
		this.sellerProdCode = sellerProdCode;
	}
	public void setBuyerProdCode(String buyerProdCode) {
		this.buyerProdCode = buyerProdCode;
	}
	public void setCommentA(String commentA) {
		this.commentA = commentA;
	}
	public void setCommentB(String commentB) {
		this.commentB = commentB;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public void setPmfClass(String pmfClass) {
		this.pmfClass = pmfClass;
	}
	public void setPrice1(BigDecimal price1) {
		this.price1 = price1;
	}
	public void setPrice2(BigDecimal price2) {
		this.price2 = price2;
	}
	public void setPriceNoTax(BigDecimal priceNoTax) {
		this.priceNoTax = priceNoTax;
		this.pricewTax = priceNoTax;
		if(priceNoTax != null){
			this.pricewTax = TaxUtil.getPriceWithTax(priceNoTax, TaxUtil.ROUND);
		}
			
		
	}
	public void setPricewTax(BigDecimal pricewTax) {
		this.pricewTax = pricewTax;
	}
	public void setPkgQuantity(BigDecimal pkgQuantity) {
		this.pkgQuantity = pkgQuantity;
	}
	public void setPkgType(String pkgType) {
		this.pkgType = pkgType;
	}
	public void setUnitOrder(Integer unitOrder) {
		this.unitOrder = unitOrder;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getSkuId() {
		return skuId;
	}
	public Integer getPmfId() {
		return pmfId;
	}
	public Integer getSkuGroup() {
		return skuGroup;
	}
	public String getSkuName() {
		return skuName;
	}
	public String getSellerProdCode() {
		return sellerProdCode;
	}
	public String getBuyerProdCode() {
		return buyerProdCode;
	}
	public String getCommentA() {
		return commentA;
	}
	public String getCommentB() {
		return commentB;
	}
	public String getLocation() {
		return location;
	}
	public String getMarket() {
		return market;
	}
	public String getGrade() {
		return grade;
	}
	public String getPmfClass() {
		return pmfClass;
	}
	public BigDecimal getPrice1() {
		return price1;
	}
	public BigDecimal getPrice2() {
		return price2;
	}
	public BigDecimal getPriceNoTax() {
		return priceNoTax;
	}
	public BigDecimal getPricewTax() {
		return pricewTax;
	}
	public BigDecimal getPkgQuantity() {
		return pkgQuantity;
	}
	public String getPkgType() {
		return pkgType;
	}
	public Integer getUnitOrder() {
		return unitOrder;
	}
	public String getSellerName() {
		return sellerName;
	}
	public Integer getCategoryId() {
		return categoryId;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public String getCompanyName() {
		return companyName;
	}
}
