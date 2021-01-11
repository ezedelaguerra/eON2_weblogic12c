package com.freshremix.model;

import java.math.BigDecimal;

public class WSBuyerSKUDetails {

	private Integer buyerId;
	private String buyerName;
	private String visibility;
	private BigDecimal qty;
	private String orderFinalizedFlag;
	private String orderLockedFlag;
	private String allocationPublishedFlag;
	private String allocationFinalizedFlag;
	private String receiveApprovedFlag;
	
	
	
	

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

	public String getAllocationPublishedFlag() {
		return allocationPublishedFlag;
	}

	public void setAllocationPublishedFlag(String allocationPublishedFlag) {
		this.allocationPublishedFlag = allocationPublishedFlag;
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

	public String getVisibility() {
		return visibility;
	}
	
	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	
}
