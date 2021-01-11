package com.freshremix.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.freshremix.ui.model.FilteredIDs;

public class DownloadCSVSettings {
	
	//For selected inputs
	private String startDate;
	private String endDate;
	private String sheetTypeId;
	private List<Integer> selectedSellerIds;
	private List<Integer> selectedBuyerIds;
	private List<Integer> selectedCategoryIds;
	private String hasQty;
	
	//For combo box & lists population
	private List<Sheets> sheetTypeList;
	private List<FilteredIDs> sellersList;
	private List<FilteredIDs> buyersList;
	private List<String> categoryList;
	private Map<String,Category> categoryMaster;
	
	//Getters and Setters
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public String getSheetTypeId() {
		return sheetTypeId;
	}
	public List<Integer> getSelectedSellerIds() {
		return selectedSellerIds;
	}
	public List<Integer> getSelectedBuyerIds() {
		return selectedBuyerIds;
	}
	public List<Integer> getSelectedCategoryIds() {
		return selectedCategoryIds;
	}
	public String getHasQty() {
		return hasQty;
	}
	public List<Sheets> getSheetTypeList() {
		return sheetTypeList;
	}
	public List<FilteredIDs> getSellersList() {
		return sellersList;
	}
	public List<FilteredIDs> getBuyersList() {
		return buyersList;
	}
	public List<String> getCategoryList() {
		return categoryList;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setSheetTypeId(String sheetTypeId) {
		this.sheetTypeId = sheetTypeId;
	}
	public void setSelectedSellerIds(List<Integer> selectedSellerIds) {
		this.selectedSellerIds = selectedSellerIds;
	}
	public void setSelectedBuyerIds(List<Integer> selectedBuyerIds) {
		this.selectedBuyerIds = selectedBuyerIds;
	}
	public void setSelectedCategoryIds(List<Integer> selectedCategoryIds) {
		this.selectedCategoryIds = selectedCategoryIds;
	}
	public void setHasQty(String hasQty) {
		this.hasQty = hasQty;
	}
	public void setSheetTypeList(List<Sheets> sheetTypeList) {
		this.sheetTypeList = sheetTypeList;
	}
	public void setSellersList(List<FilteredIDs> sellersList) {
		this.sellersList = sellersList;
	}
	public void setBuyersList(List<FilteredIDs> buyersList) {
		this.buyersList = buyersList;
	}
	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}
	public Map<String, Category> getCategoryMaster() {
		return categoryMaster;
	}
	public void setCategoryMaster(Map<String, Category> categoryMaster) {
		this.categoryMaster = categoryMaster;
	}
	public DownloadCSVSettings() {
		super();
		
		//For selected inputs
		this.startDate = "";
		this.endDate = "";
		this.sheetTypeId = "";
		this.selectedSellerIds = new ArrayList<Integer>();
		this.selectedBuyerIds = new ArrayList<Integer>();
		this.selectedCategoryIds = new ArrayList<Integer>();
		this.hasQty = "";
		
		//For combo box & lists population
		this.sheetTypeList = new ArrayList<Sheets>();
		this.sellersList = new ArrayList<FilteredIDs>();
		this.buyersList = new ArrayList<FilteredIDs>();
		this.categoryList = new ArrayList<String>();
	}
	
	

}
