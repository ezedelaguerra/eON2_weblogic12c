package com.freshremix.model;

import java.math.BigDecimal;

/**
 * Abstraction for OrderItem, BillingItem, OrderReceived,
 * AkadenItem class
 * 
 * 
 * @author gilwen
 *
 */

public interface Item {

	Integer getSKUId();
	Integer getSKUBAId();
	Integer getAkadenSKUId();
	String getDeliveryDate();
	Integer getBuyerId();
	BigDecimal getQuantity();
	Integer getOrderId();
	String getBaosVisFlag();
	String getSosVisFlag();
	String getComments();
	String getIsApproved();
	Integer getOrderFinalizedBy();
	Integer getOrderPublishedBy();
	Integer getOrderLockedBy();
	Integer getAllocationPublishedBy();
	Integer getAllocationFinalizedBy();
	Integer getReceivedApprovedBy();
}
