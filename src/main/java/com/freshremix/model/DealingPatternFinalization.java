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
 * Mar 29, 2010		nvelasquez		
 */
package com.freshremix.model;

/**
 * @author nvelasquez
 *
 */
public class DealingPatternFinalization {
	
	private String deliveryDate;
	private Integer sellerId;
	private Integer buyerId;
	
	private boolean withDealingPattern;
	private boolean sellerOrderFinalized;
	private boolean buyerOrderFinalized;
	private boolean sellerAllocFinalized;
	private boolean buyerReceivedFinalized;
	private boolean sellerBillingFinalized;
	
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
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
	public boolean isWithDealingPattern() {
		return withDealingPattern;
	}
	public void setWithDealingPattern(boolean withDealingPattern) {
		this.withDealingPattern = withDealingPattern;
	}
	public boolean isSellerOrderFinalized() {
		return sellerOrderFinalized;
	}
	public void setSellerOrderFinalized(boolean sellerOrderFinalized) {
		this.sellerOrderFinalized = sellerOrderFinalized;
	}
	public boolean isBuyerOrderFinalized() {
		return buyerOrderFinalized;
	}
	public void setBuyerOrderFinalized(boolean buyerOrderFinalized) {
		this.buyerOrderFinalized = buyerOrderFinalized;
	}
	public boolean isSellerAllocFinalized() {
		return sellerAllocFinalized;
	}
	public void setSellerAllocFinalized(boolean sellerAllocFinalized) {
		this.sellerAllocFinalized = sellerAllocFinalized;
	}
	public boolean isBuyerReceivedFinalized() {
		return buyerReceivedFinalized;
	}
	public void setBuyerReceivedFinalized(boolean buyerReceivedFinalized) {
		this.buyerReceivedFinalized = buyerReceivedFinalized;
	}
	public boolean isSellerBillingFinalized() {
		return sellerBillingFinalized;
	}
	public void setSellerBillingFinalized(boolean sellerBillingFinalized) {
		this.sellerBillingFinalized = sellerBillingFinalized;
	}

}
