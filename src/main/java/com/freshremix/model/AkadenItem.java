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
 * Apr 2, 2010		raquino		
 */
package com.freshremix.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author raquino
 *
 */
public class AkadenItem implements Item {
	
	private Integer akadenItemId;
	private Order order;
	private SKU sku;
	private Integer updateBy;
	private Date updateTimestamp; 
	private String comments;
	private AkadenSKU akadenSku;
	private String isNewSku;
	private BigDecimal quantity;

	public Integer getAkadenId() {
		return akadenItemId;
	}
	public void setAkadenId(Integer akadenItemId) {
		this.akadenItemId = akadenItemId;
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
	public Integer getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}
	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public AkadenSKU getAkadenSku() {
		return akadenSku;
	}
	public void setAkadenSku(AkadenSKU akadenSku) {
		this.akadenSku = akadenSku;
	}
	public String getIsNewSku() {
		return isNewSku;
	}
	public void setIsNewSku(String isNewSku) {
		this.isNewSku = isNewSku;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	@Override
	public String getBaosVisFlag() {
		return null;
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
	public String getIsApproved() {
		return null;
	}
	@Override
	public Integer getOrderId() {
		return getOrder().getOrderId();
	}
	@Override
	public Integer getSKUId() {
		return getSku().getSkuId();
	}
	@Override
	public String getSosVisFlag() {
		return null;
	}
	@Override
	public Integer getAkadenSKUId() {
		return getAkadenSku().getAkadenSkuId();
	}
	@Override
	public Integer getSKUBAId() {
		return null;
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
