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
 * Feb 19, 2010		gilwen		
 */
package com.freshremix.model;

import java.math.BigDecimal;

/**
 * @author gilwen
 *
 */
public class OrderItem implements Item {

	private Integer orderItemId;
	private Order order;
	private SKU sku;
	private BigDecimal quantity;
//	private BigDecimal totalQuantity;
//	private BigDecimal unitPrice;
//	private BigDecimal skuMaxLimit;
	private String sosVisFlag;
	private String baosVisFlag;
	private Integer akadenSkuId;
	private String isApproved;
	private Integer skuBaId;

	public SKU getSku() {
		return sku;
	}
	public void setSku(SKU sku) {
		this.sku = sku;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Integer getAkadenSkuId() {
		return akadenSkuId;
	}
	public void setAkadenSkuId(Integer akadenSkuId) {
		
		this.akadenSkuId = akadenSkuId;
	}
	public String getSosVisFlag() {
		return sosVisFlag;
	}
	public void setSosVisFlag(String sosVisFlag) {
		this.sosVisFlag = sosVisFlag;
	}
	public String getBaosVisFlag() {
		return baosVisFlag;
	}
	public void setBaosVisFlag(String baosVisFlag) {
		this.baosVisFlag = baosVisFlag;
	}
	public Integer getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
//	public BigDecimal getTotalQuantity() {
//		return totalQuantity;
//	}
//	public void setTotalQuantity(BigDecimal totalQuantity) {
//		this.totalQuantity = totalQuantity;
//	}
//	public BigDecimal getUnitPrice() {
//		return unitPrice;
//	}
//	public void setUnitPrice(BigDecimal unitPrice) {
//		this.unitPrice = unitPrice;
//	}
//	public BigDecimal getSkuMaxLimit() {
//		return skuMaxLimit;
//	}
//	public void setSkuMaxLimit(BigDecimal skuMaxLimit) {
//		this.skuMaxLimit = skuMaxLimit;
//	}
	@Override
	public Integer getBuyerId() {
		return getOrder().getBuyerId();
	}
	@Override
	public String getDeliveryDate() {
		return getOrder().getDeliveryDate();
	}
	@Override
	public Integer getSKUId() {
		return getSku().getSkuId();
	}
	@Override
	public Integer getOrderId() {
		return getOrder().getOrderId();
	}
	/* (non-Javadoc)
	 * @see com.freshremix.model.Item#getComments()
	 */
	@Override
	public String getComments() {
		return null;
	}
	public String getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}
	@Override
	public Integer getAkadenSKUId() {
		return null;
	}
	public Integer getSkuBaId() {
		return skuBaId;
	}
	public void setSkuBaId(Integer skuBaId) {
		this.skuBaId = skuBaId;
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
