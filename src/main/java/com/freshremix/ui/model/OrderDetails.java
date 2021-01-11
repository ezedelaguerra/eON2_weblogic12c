package com.freshremix.ui.model;

import java.util.List;

import com.freshremix.model.DealingPattern;
import com.freshremix.model.User;

public class OrderDetails {

	private Integer sellerId;
	private List<Integer> sellerIds;
	private Integer buyerId;
	private List<Integer> buyerIds;
	private String deliveryDate;
	private User user;
	private Integer sheetType;
	private Integer skuCategoryId;
	private String startDate;
	private String endDate;
	private boolean allDatesView;
	private Integer datesViewBuyerID;
	private DealingPattern dealingPattern;
	
	public List<Integer> getSellerIds() {
		return sellerIds;
	}
	public void setSellerIds(List<Integer> sellerIds) {
		this.sellerIds = sellerIds;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getDatesViewBuyerID() {
		return datesViewBuyerID;
	}
	public void setDatesViewBuyerID(Integer datesViewBuyerID) {
		this.datesViewBuyerID = datesViewBuyerID;
	}
	public boolean isAllDatesView() {
		return allDatesView;
	}
	public void setAllDatesView(boolean allDatesView) {
		this.allDatesView = allDatesView;
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
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public List<Integer> getBuyerIds() {
		return buyerIds;
	}
	public void setBuyerIds(List<Integer> buyerIds) {
		this.buyerIds = buyerIds;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Integer getSheetType() {
		return sheetType;
	}
	public void setSheetType(Integer sheetType) {
		this.sheetType = sheetType;
	}
	public Integer getSkuCategoryId() {
		return skuCategoryId;
	}
	public void setSkuCategoryId(Integer skuCategoryId) {
		this.skuCategoryId = skuCategoryId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public DealingPattern getDealingPattern() {
		return dealingPattern;
	}
	public void setDealingPattern(DealingPattern dealingPattern) {
		this.dealingPattern = dealingPattern;
	}
	
}
