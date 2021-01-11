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
 * Mar 19, 2010		Jovino Balunan		
 */
package com.freshremix.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jabalunan
 *
 */
public class Akaden {
	private Integer orderId;
	private Integer skuId;
	private Integer updateBy;
	private Date updateTimestamp; 
	private String comments;
	private Integer akadenSkuId;
	private String isNewSku;
	private BigDecimal quantity;
	private BigDecimal totalQuantity;
	private BigDecimal unitPrice;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
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
	public Integer getAkadenSkuId() {
		return akadenSkuId;
	}
	public void setAkadenSkuId(Integer akadenSkuId) {
		this.akadenSkuId = akadenSkuId;
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
}