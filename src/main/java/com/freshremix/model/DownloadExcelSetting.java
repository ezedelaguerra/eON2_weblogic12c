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
 * Jul 26, 2010		nvelasquez		
 */
package com.freshremix.model;

/**
 * @author nvelasquez
 *
 */
public class DownloadExcelSetting {
	
	private Integer userId;
	private String weeklyFlag;
	private	String dateOpt;
	private String sellerOpt;
	private String buyerOpt;
	private String hasQty;
	private String priceComputeOpt;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getWeeklyFlag() {
		return weeklyFlag;
	}
	public void setWeeklyFlag(String weeklyFlag) {
		this.weeklyFlag = weeklyFlag;
	}
	public String getDateOpt() {
		return dateOpt;
	}
	public void setDateOpt(String dateOpt) {
		this.dateOpt = dateOpt;
	}
	public String getSellerOpt() {
		return sellerOpt;
	}
	public void setSellerOpt(String sellerOpt) {
		this.sellerOpt = sellerOpt;
	}
	public String getBuyerOpt() {
		return buyerOpt;
	}
	public void setBuyerOpt(String buyerOpt) {
		this.buyerOpt = buyerOpt;
	}
	public String getHasQty() {
		return hasQty;
	}
	public void setHasQty(String hasQty) {
		this.hasQty = hasQty;
	}
	public String getPriceComputeOpt() {
		return priceComputeOpt;
	}
	public void setPriceComputeOpt(String priceComputeOpt) {
		this.priceComputeOpt = priceComputeOpt;
	}
	
}
