package com.freshremix.model;

import java.util.List;


public class OrderSheetParam {

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
	private Integer csvSheetTypeID;
	private List<Category> categoryList;
	
	public List<Category> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	public Integer getCsvSheetTypeID() {
		return csvSheetTypeID;
	}
	public void setCsvSheetTypeID(Integer csvSheetTypeID) {
		this.csvSheetTypeID = csvSheetTypeID;
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
