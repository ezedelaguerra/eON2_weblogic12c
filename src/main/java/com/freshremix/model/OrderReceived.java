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
 * Mar 31, 2010		nvelasquez		
 */
package com.freshremix.model;

import java.math.BigDecimal;

/**
 * @author nvelasquez
 *
 */
public class OrderReceived implements Item {
	
	private Integer orderReceivedId;
	private Order order;
	private Integer skuId;
	private Integer skuBaId;
	private BigDecimal quantity;
	private BigDecimal totalQuantity;
	private BigDecimal unitPrice;
	private String isApproved;
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Integer getOrderReceivedId() {
		return orderReceivedId;
	}
	public void setOrderReceivedId(Integer orderReceivedId) {
		this.orderReceivedId = orderReceivedId;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public Integer getSkuBaId() {
		return skuBaId;
	}
	public void setSkuBaId(Integer skuBaId) {
		this.skuBaId = skuBaId;
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
	public String getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}
	@Override
	public Integer getBuyerId() {
		return getOrder().getBuyerId();
	}
	@Override
	public String getDeliveryDate() {
		return getOrder().getDeliveryDate();
	}
	@Override
	public Integer getOrderId() {
		return getOrder().getOrderId();
	}
	@Override
	public Integer getSKUId() {
		return getSkuId();
	}
	@Override
	public String getBaosVisFlag() {
		return null;
	}
	@Override
	public String getSosVisFlag() {
		return null;
	}
	/* (non-Javadoc)
	 * @see com.freshremix.model.Item#getComments()
	 */
	@Override
	public String getComments() {
		return null;
	}
	@Override
	public Integer getAkadenSKUId() {
		return null;
	}
	@Override
	public Integer getSKUBAId() {
		return this.skuBaId;
	}
	@Override
	public Integer getOrderFinalizedBy() {
		return this.order.getOrderFinalizedBy();
	}
	@Override
	public Integer getOrderLockedBy() {
		return this.order.getOrderLockedBy();
	}
	@Override
	public Integer getAllocationPublishedBy() {
		return this.order.getAllocationPublishedBy();
	}
	@Override
	public Integer getAllocationFinalizedBy() {
		return this.order.getAllocationFinalizedBy();
	}
	@Override
	public Integer getReceivedApprovedBy() {
		return this.order.getReceivedApprovedBy();
	}
	@Override
	public Integer getOrderPublishedBy() {
		return this.order.getOrderPublishedBy();
	}
}
