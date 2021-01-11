package com.freshremix.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Order {

	private Integer orderId;
	private Integer buyerId;
	private Integer sellerId;
	private String deliveryDate;
	private Integer orderSavedBy;
	private Integer orderPublishedBy;
	private Integer orderFinalizedBy;
	private Integer orderUnfinalizedBy;
	private Integer orderLockedBy;
	private Integer orderUnlockedBy;
	private Integer allocationSavedBy;
	private Integer allocationPublishedBy;
	private Integer allocationFinalizedBy;
	private Integer allocationUnfinalizedBy;
	private Integer receivedApprovedBy;
	private Integer receivedUnapprovedBy;
	private Integer receivedSavedBy;
	private Integer billingSavedBy;
	private Integer billingFinalizedBy;
	private Integer billingUnfinalizedBy;
	private Integer akadenSavedBy;
	private Integer createdBy;
	private Integer orderPublishedByBA;
	private Date lastSavedOsTimeStamp;
	private Integer copiedFromOrderId;
	private Date copiedFromTimeStamp;
	
	public Order() { }
	
	public Order(String deliveryDate, Integer sellerId, Integer buyerId) {
		this.deliveryDate = deliveryDate;
		this.sellerId = sellerId;
		this.buyerId = buyerId;
	}
	
	public Integer getOrderPublishedByBA() {
		return orderPublishedByBA;
	}
	public void setOrderPublishedByBA(Integer orderPublishedByBA) {
		this.orderPublishedByBA = orderPublishedByBA;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Integer getOrderSavedBy() {
		return orderSavedBy;
	}
	public void setOrderSavedBy(Integer orderSavedBy) {
		this.orderSavedBy = orderSavedBy;
	}
	public Integer getOrderPublishedBy() {
		return orderPublishedBy;
	}
	public void setOrderPublishedBy(Integer orderPublishedBy) {
		this.orderPublishedBy = orderPublishedBy;
	}
	public Integer getOrderFinalizedBy() {
		return orderFinalizedBy;
	}
	public void setOrderFinalizedBy(Integer orderFinalizedBy) {
		this.orderFinalizedBy = orderFinalizedBy;
	}
	public Integer getOrderUnfinalizedBy() {
		return orderUnfinalizedBy;
	}
	public void setOrderUnfinalizedBy(Integer orderUnfinalizedBy) {
		this.orderUnfinalizedBy = orderUnfinalizedBy;
	}
	public Integer getOrderLockedBy() {
		return orderLockedBy;
	}
	public void setOrderLockedBy(Integer orderLockedBy) {
		this.orderLockedBy = orderLockedBy;
	}
	public Integer getOrderUnlockedBy() {
		return orderUnlockedBy;
	}
	public void setOrderUnlockedBy(Integer orderUnlockedBy) {
		this.orderUnlockedBy = orderUnlockedBy;
	}
	public Integer getAllocationSavedBy() {
		return allocationSavedBy;
	}
	public void setAllocationSavedBy(Integer allocationSavedBy) {
		this.allocationSavedBy = allocationSavedBy;
	}
	public Integer getAllocationPublishedBy() {
		return allocationPublishedBy;
	}
	public void setAllocationPublishedBy(Integer allocationPublishedBy) {
		this.allocationPublishedBy = allocationPublishedBy;
	}
	public Integer getAllocationFinalizedBy() {
		return allocationFinalizedBy;
	}
	public void setAllocationFinalizedBy(Integer allocationFinalizedBy) {
		this.allocationFinalizedBy = allocationFinalizedBy;
	}
	public Integer getAllocationUnfinalizedBy() {
		return allocationUnfinalizedBy;
	}
	public void setAllocationUnfinalizedBy(Integer allocationUnfinalizedBy) {
		this.allocationUnfinalizedBy = allocationUnfinalizedBy;
	}
	public Integer getReceivedApprovedBy() {
		return receivedApprovedBy;
	}
	public void setReceivedApprovedBy(Integer receivedApprovedBy) {
		this.receivedApprovedBy = receivedApprovedBy;
	}
	public Integer getReceivedUnapprovedBy() {
		return receivedUnapprovedBy;
	}
	public void setReceivedUnapprovedBy(Integer receivedUnapprovedBy) {
		this.receivedUnapprovedBy = receivedUnapprovedBy;
	}
	public Integer getReceivedSavedBy() {
		return receivedSavedBy;
	}
	public void setReceivedSavedBy(Integer receivedSavedBy) {
		this.receivedSavedBy = receivedSavedBy;
	}
	public Integer getBillingSavedBy() {
		return billingSavedBy;
	}
	public void setBillingSavedBy(Integer billingSavedBy) {
		this.billingSavedBy = billingSavedBy;
	}
	public Integer getBillingFinalizedBy() {
		return billingFinalizedBy;
	}
	public void setBillingFinalizedBy(Integer billingFinalizedBy) {
		this.billingFinalizedBy = billingFinalizedBy;
	}
	public Integer getBillingUnfinalizedBy() {
		return billingUnfinalizedBy;
	}
	public void setBillingUnfinalizedBy(Integer billingUnfinalizedBy) {
		this.billingUnfinalizedBy = billingUnfinalizedBy;
	}
	public Integer getAkadenSavedBy() {
		return akadenSavedBy;
	}
	public void setAkadenSavedBy(Integer akadenSavedBy) {
		this.akadenSavedBy = akadenSavedBy;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getLastSavedOsTimeStamp() {
		return lastSavedOsTimeStamp;
	}
	public void setLastSavedOsTimeStamp(Date lastSavedOsTimeStamp) {
		this.lastSavedOsTimeStamp = lastSavedOsTimeStamp;
	}
	public Integer getCopiedFromOrderId() {
		return copiedFromOrderId;
	}
	public void setCopiedFromOrderId(Integer copiedFromOrderId) {
		this.copiedFromOrderId = copiedFromOrderId;
	}
	public Date getCopiedFromTimeStamp() {
		return copiedFromTimeStamp;
	}
	public void setCopiedFromTimeStamp(Date copiedFromTimeStamp) {
		this.copiedFromTimeStamp = copiedFromTimeStamp;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)//
				.append("orderId", this.orderId)//
				.append("buyerId", this.buyerId)//
				.append("sellerId", this.sellerId)//
				.append("deliveryDate", this.deliveryDate)//
				.append("orderSavedBy", this.orderSavedBy)//
				.append("orderPublishedBy", this.orderPublishedBy)//
				.append("orderFinalizedBy", this.orderFinalizedBy)//
				.append("orderUnfinalizedBy", this.orderUnfinalizedBy)//
				.append("orderLockedBy", this.orderLockedBy)//
				.append("orderUnlockedBy", this.orderUnlockedBy)//
				.append("allocationSavedBy", this.allocationSavedBy)//
				.append("allocationPublishedBy", this.allocationPublishedBy)//
				.append("allocationFinalizedBy", this.allocationFinalizedBy)//
				.append("allocationUnfinalizedBy", this.allocationUnfinalizedBy)//
				.append("receivedApprovedBy", this.receivedApprovedBy)//
				.append("receivedUnapprovedBy", this.receivedUnapprovedBy)//
				.append("receivedSavedBy", this.receivedSavedBy)//
				.append("billingSavedBy", this.billingSavedBy)//
				.append("billingFinalizedBy", this.billingFinalizedBy)//
				.append("billingUnfinalizedBy", this.billingUnfinalizedBy)//
				.append("akadenSavedBy", this.akadenSavedBy)//
				.append("createdBy", this.createdBy)//
				.append("orderPublishedByBA", this.orderPublishedByBA)//
				.append("lastSavedOsTimeStamp", this.lastSavedOsTimeStamp)//
				.append("copiedFromOrderId", this.copiedFromOrderId)//
				.append("copiedFromTimeStamp", this.copiedFromTimeStamp)//
				.toString();
	}

	
	
}