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
 * Nov 9, 2010		raquino		
 */
package com.freshremix.model;

/**
 * @author raquino
 *
 */
public class ItemInfoVisibility {

	private Integer buyerId; // visible buyers
	private String quantity;
	private boolean visibilityFlag; // 1-visible; 0-not visible; true-visible; false-not visible
	
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public boolean getVisibilityFlag() {
		return visibilityFlag;
	}
	public void setVisibilityFlag(boolean visibilityFlag) {
		this.visibilityFlag = visibilityFlag;
	}
}
