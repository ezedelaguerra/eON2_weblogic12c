package com.freshremix.model;

import java.math.BigDecimal;

public class AllocationBuyerInformation {

	private Integer buyerId;
	private BigDecimal quantity;
	
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

}
