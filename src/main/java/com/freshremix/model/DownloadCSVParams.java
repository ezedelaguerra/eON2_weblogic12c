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
 * May 19, 2010		Jovino Balunan		
 */
package com.freshremix.model;

import java.util.List;

/**
 * @author Jovino Balunan
 *
 */
public class DownloadCSVParams {

	private String startDate;
	private String endDate;
	private String sheetTypeId;
	private String usersList;
	private String sellersList;
	private String buyersList;
	private List<Integer> userId;
	private List<Integer> categoryId;
	private String hasQty;
	private String isOneDay;
	
	public String getSellersList() {
		return sellersList;
	}
	public void setSellersList(String sellersList) {
		this.sellersList = sellersList;
	}
	public String getBuyersList() {
		return buyersList;
	}
	public void setBuyersList(String buyersList) {
		this.buyersList = buyersList;
	}
	public List<Integer> getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(List<Integer> categoryId) {
		this.categoryId = categoryId;
	}
	public String getUsersList() {
		return usersList;
	}
	public void setUsersList(String usersList) {
		this.usersList = usersList;
	}
	public List<Integer> getUserId() {
		return userId;
	}
	public void setUserId(List<Integer> userId) {
		this.userId = userId;
	}
	public String getIsOneDay() {
		return isOneDay;
	}
	public void setIsOneDay(String isOneDay) {
		this.isOneDay = isOneDay;
	}
	public String getSheetTypeId() {
		return sheetTypeId;
	}
	public void setSheetTypeId(String sheetTypeId) {
		this.sheetTypeId = sheetTypeId;
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
	public String getHasQty() {
		return hasQty;
	}
	public void setHasQty(String hasQty) {
		this.hasQty = hasQty;
	}
	public String getIsAkaden() {
		return isAkaden;
	}
	public void setIsAkaden(String isAkaden) {
		this.isAkaden = isAkaden;
	}
	private String isAkaden;
	
}
