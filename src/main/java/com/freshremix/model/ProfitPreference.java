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
 * Jan 30, 2012		raquino		
 */
package com.freshremix.model;

import java.math.BigDecimal;

/**
 * @author raquino
 *
 */
public class ProfitPreference {
	
	private Integer userId;
	private String totalSellingPrice;  //UI visibility of Total Selling Price: 1 = Show  0 = Hide
	private String totalProfit;        //UI visibility of Total Profit: 1 = Show  0 = Hide
	private String totalProfitPercent; //UI visibility of Total Profit Percentage: 1 = Show  0 = Hide
	private String priceTaxOption;     //Option to compute Profit: 1 = Price with Tax    0 = Price without Tax
	private String withPackageQuantity; //Option to compute Selling Price Total : 1 = With Package Quantity     0 = Without Package Quantity [DEFAULT = 1]

	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTotalSellingPrice() {
		return totalSellingPrice;
	}
	public void setTotalSellingPrice(String totalSellingPrice) {
		this.totalSellingPrice = totalSellingPrice;
	}
	public String getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(String totalProfit) {
		this.totalProfit = totalProfit;
	}
	public String getTotalProfitPercent() {
		return totalProfitPercent;
	}
	public void setTotalProfitPercent(String totalProfitPercent) {
		this.totalProfitPercent = totalProfitPercent;
	}
	public String getPriceTaxOption() {
		return priceTaxOption;
	}
	public void setPriceTaxOption(String priceTaxOption) {
		this.priceTaxOption = priceTaxOption;
	}
	public String getWithPackageQuantity() {
		return withPackageQuantity;
	}
	public void setWithPackageQuantity(String withPackageQuantity) {
		this.withPackageQuantity = withPackageQuantity;
	}
}

