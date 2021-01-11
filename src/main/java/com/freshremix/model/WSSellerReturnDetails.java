package com.freshremix.model;

public class WSSellerReturnDetails {

	
	private String buyerId;
	private String buyerName;
	private String qty;
	private String visibility;
	private String orderFinalizedFlag;
	private String orderPublishedFlag;
	private String orderLockedFlag;
	private String allocationPublishedFlag;
	private String allocationFinalizedFlag;
	private String receiveApprovedFlag;
	
	
	public String getOrderPublishedFlag() {
		return orderPublishedFlag;
	}
	public void setOrderPublishedFlag(String orderPublishedFlag) {
		this.orderPublishedFlag = orderPublishedFlag;
	}
	public String getAllocationPublishedFlag() {
		return allocationPublishedFlag;
	}
	public void setAllocationPublishedFlag(String allocationPublishedFlag) {
		this.allocationPublishedFlag = allocationPublishedFlag;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getOrderFinalizedFlag() {
		return orderFinalizedFlag;
	}
	public void setOrderFinalizedFlag(String orderFinalizedFlag) {
		this.orderFinalizedFlag = orderFinalizedFlag;
	}
	public String getOrderLockedFlag() {
		return orderLockedFlag;
	}
	public void setOrderLockedFlag(String orderLockedFlag) {
		this.orderLockedFlag = orderLockedFlag;
	}
	public String getAllocationFinalizedFlag() {
		return allocationFinalizedFlag;
	}
	public void setAllocationFinalizedFlag(String allocationFinalizedFlag) {
		this.allocationFinalizedFlag = allocationFinalizedFlag;
	}
	public String getReceiveApprovedFlag() {
		return receiveApprovedFlag;
	}
	public void setReceiveApprovedFlag(String receiveApprovedFlag) {
		this.receiveApprovedFlag = receiveApprovedFlag;
	}
	
	
}
