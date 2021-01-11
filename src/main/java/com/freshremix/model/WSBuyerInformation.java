package com.freshremix.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * webservice Object
 * buyerId --> refers to buyerId
 * quantity --> refers to the desired quantity
 * isVisible --> refers to the desired visibility 
 * @author Administrator
 *
 */
public class WSBuyerInformation {

	private Integer buyerId;
	private BigDecimal quantity;
	private boolean isVisible;
	
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
	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	@Override
	public boolean equals(Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		WSBuyerInformation wb = (WSBuyerInformation) o;
		return Objects.equals(this.buyerId, wb.buyerId);
	}
}
