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
 * Mar 29, 2010		gilwen		
 */
package com.freshremix.model;

import java.math.BigDecimal;

/**
 * @author gilwen
 *
 */
public class AllocItem {

	private Integer allocationId;
	private Order order;
	private SKU sku;
	private BigDecimal quantity;
	private BigDecimal totalQuantity;
	private BigDecimal unitPrice;
	private BigDecimal skuMaxLimit;
	
	public Integer getAllocationId() {
		return allocationId;
	}
	public void setAllocationId(Integer allocationId) {
		this.allocationId = allocationId;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public SKU getSku() {
		return sku;
	}
	public void setSku(SKU sku) {
		this.sku = sku;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getSkuMaxLimit() {
		return skuMaxLimit;
	}
	public void setSkuMaxLimit(BigDecimal skuMaxLimit) {
		this.skuMaxLimit = skuMaxLimit;
	}
}
