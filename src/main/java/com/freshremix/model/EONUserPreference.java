package com.freshremix.model;

import java.util.List;

import com.freshremix.ui.model.FilteredIDs;


public class EONUserPreference {

	private Integer userPreferenceId;
	private Integer userId;
	private String viewUnpublisheAlloc;
	private String viewUnfinalizeBilling;
	private String enableBAPublish;
	private String autoPublishAlloc;
	private String displayAllocQty;
	private String unfinalizeReceived;

	// Hide/Show column
	private HideColumn hideColumn;

	// Sort SKU column
	private List<Integer> skuSort;
	
	// Width column
	private WidthColumn widthColumn;
	
	// Sorted Categories
	private List<UsersCategory> sortedCategories;
	
	// Sorted Dealing Pattern Seller Companies
	private List<FilteredIDs> sortedSellerCompanies;
	
	// Sorted Dealing Pattern Sellers
	private List<FilteredIDs> sortedSellers;
	
	// Gross Profit Computation Preference
	private ProfitPreference profitPreference;
	
	// Lock Button Status for Buyer and Buyer Admin
	private LockButtonStatus lockButtonStatus;
	
	public Integer getUserPreferenceId() {
		return userPreferenceId;
	}

	public void setUserPreferenceId(Integer userPreferenceId) {
		this.userPreferenceId = userPreferenceId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getViewUnpublisheAlloc() {
		return viewUnpublisheAlloc;
	}

	public void setViewUnpublisheAlloc(String viewUnpublisheAlloc) {
		this.viewUnpublisheAlloc = viewUnpublisheAlloc;
	}

	public String getViewUnfinalizeBilling() {
		return viewUnfinalizeBilling;
	}

	public void setViewUnfinalizeBilling(String viewUnfinalizeBilling) {
		this.viewUnfinalizeBilling = viewUnfinalizeBilling;
	}

	public String getEnableBAPublish() {
		return enableBAPublish;
	}

	public void setEnableBAPublish(String enableBAPublish) {
		this.enableBAPublish = enableBAPublish;
	}

	public String getAutoPublishAlloc() {
		return autoPublishAlloc;
	}

	public void setAutoPublishAlloc(String autoPublishAlloc) {
		this.autoPublishAlloc = autoPublishAlloc;
	}

	public HideColumn getHideColumn() {
		return hideColumn;
	}

	public void setHideColumn(HideColumn hideColumn) {
		this.hideColumn = hideColumn;
	}

	public List<Integer> getSkuSort() {
		return skuSort;
	}

	public void setSkuSort(List<Integer> skuSort) {
		this.skuSort = skuSort;
	}

	public WidthColumn getWidthColumn() {
		return widthColumn;
	}

	public void setWidthColumn(WidthColumn widthColumn) {
		this.widthColumn = widthColumn;
	}

	public List<UsersCategory> getSortedCategories() {
		return sortedCategories;
	}

	public void setSortedCategories(List<UsersCategory> sortedCategories) {
		this.sortedCategories = sortedCategories;
	}

	public List<FilteredIDs> getSortedSellerCompanies() {
		return sortedSellerCompanies;
	}

	public void setSortedSellerCompanies(List<FilteredIDs> sortedSellerCompanies) {
		this.sortedSellerCompanies = sortedSellerCompanies;
	}

	public List<FilteredIDs> getSortedSellers() {
		return sortedSellers;
	}

	public void setSortedSellers(List<FilteredIDs> sortedSellers) {
		this.sortedSellers = sortedSellers;
	}

	public ProfitPreference getProfitPreference() {
		return profitPreference;
	}

	public void setProfitPreference(ProfitPreference profitPreference) {
		this.profitPreference = profitPreference;
	}

	public String getDisplayAllocQty() {
		return displayAllocQty;
	}

	public void setDisplayAllocQty(String displayAllocQty) {
		this.displayAllocQty = displayAllocQty;
	}

	public String getUnfinalizeReceived() {
		return unfinalizeReceived;
	}

	public void setUnfinalizeReceived(String unfinalizeReceived) {
		this.unfinalizeReceived = unfinalizeReceived;
	}
	
	public LockButtonStatus getLockButtonStatus() {
		return lockButtonStatus;
	}

	public void setLockButtonStatus(LockButtonStatus lockButtonStatus) {
		this.lockButtonStatus = lockButtonStatus;
	}
	
}
