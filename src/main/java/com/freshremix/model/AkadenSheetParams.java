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
 * Mar 29, 2010		Jovino Balunan		
 */
package com.freshremix.model;

import java.util.List;

/**
 * @author Jovino Balunan
 *
 */
public class AkadenSheetParams {
	private String startDate;
	private String endDate;
	private String selectedSellerCompany;
	private String selectedSellerID;
	private String selectedBuyerCompany;
	private String selectedBuyerID;
	private String datesViewBuyerID;
	private Integer categoryId;
	private Integer sheetTypeId;
	private String selectedDate;
	private boolean allDatesView;
	private boolean checkDBOrder;
	private boolean importedAlloc;
	private List<Integer> skuIds;
	private List<Integer> orderIds;
	
	public List<Integer> getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(List<Integer> orderIds) {
		this.orderIds = orderIds;
	}
	public List<Integer> getSkuIds() {
		return skuIds;
	}
	public void setSkuIds(List<Integer> skuIds) {
		this.skuIds = skuIds;
	}
	public boolean isImportedAlloc() {
		return importedAlloc;
	}
	public void setImportedAlloc(boolean importedAlloc) {
		this.importedAlloc = importedAlloc;
	}
	public boolean isCheckDBOrder() {
		return checkDBOrder;	
	}
	public void setCheckDBOrder(boolean checkDBOrder) {
		this.checkDBOrder = checkDBOrder;
	}
	public String getDatesViewBuyerID() {
		return datesViewBuyerID;
	}
	public void setDatesViewBuyerID(String datesViewBuyerID) {
		this.datesViewBuyerID = datesViewBuyerID;
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
	public String getSelectedSellerCompany() {
		return selectedSellerCompany;
	}
	public void setSelectedSellerCompany(String selectedSellerCompany) {
		this.selectedSellerCompany = selectedSellerCompany;
	}
	public String getSelectedSellerID() {
		return selectedSellerID;
	}
	public void setSelectedSellerID(String selectedSellerID) {
		this.selectedSellerID = selectedSellerID;
	}
	public String getSelectedBuyerCompany() {
		return selectedBuyerCompany;
	}
	public void setSelectedBuyerCompany(String selectedBuyerCompany) {
		this.selectedBuyerCompany = selectedBuyerCompany;
	}
	public String getSelectedBuyerID() {
		return selectedBuyerID;
	}
	public void setSelectedBuyerID(String selectedBuyerID) {
		this.selectedBuyerID = selectedBuyerID;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getSheetTypeId() {
		return sheetTypeId;
	}
	public void setSheetTypeId(Integer sheetTypeId) {
		this.sheetTypeId = sheetTypeId;
	}
	public String getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}
	public boolean isAllDatesView() {
		return allDatesView;
	}
	public void setAllDatesView(boolean allDatesView) {
		this.allDatesView = allDatesView;
	}
}
