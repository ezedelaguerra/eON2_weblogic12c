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
 * date       		name			version            changes
 * ------------------------------------------------------------------------------
 * Dec 1, 2011		raquino	
 * 20120608			Rhoda			v11			Redmine 68 - Order Sheet Concurrency
 * 20120727 		Rhoda			v11			Redmine 354 - Unfinalize Order and Finalize Alloc concurrency
 */
package com.freshremix.model;

import java.util.List;

import com.freshremix.ui.model.OrderItemUI;

/**
 * @author raquino
 *
 */
public class ConcurrencyResponse {
	
	private List<Order> forUpdateOrders;
	private List<OrderItemUI> oiUIforUpdate;
	private List<OrderItemUI> oiUIforDelete;

	private String concurrencyMsg;
	//ENHANCEMENT 20120727: Rhoda Redmine 68
	private boolean isSellerFinalized;
	//ENHANCEMENT START 20120727: Rhoda Redmine 354
	private boolean isSellerOrderUnFinalized;
	private boolean isReceivedFinalized;
	private boolean isAllocFinalized;
	private String action;
	//ENHANCEMENT END 20120727: Rhoda Redmine 354
	
	
	public String getConcurrencyMsg() {
		return concurrencyMsg;
	}
	public List<OrderItemUI> getOiUIforDelete() {
		return oiUIforDelete;
	}
	public void setOiUIforDelete(List<OrderItemUI> oiUIforDelete) {
		this.oiUIforDelete = oiUIforDelete;
	}
	public List<OrderItemUI> getOiUIforUpdate() {
		return oiUIforUpdate;
	}
	public void setOiUIforUpdate(List<OrderItemUI> oiUIforUpdate) {
		this.oiUIforUpdate = oiUIforUpdate;
	}
	public void setConcurrencyMsg(String concurrencyMsg) {
		this.concurrencyMsg = concurrencyMsg;
	}
	public List<Order> getForUpdateOrders() {
		return forUpdateOrders;
	}
	public void setForUpdateOrders(List<Order> forUpdateOrders) {
		this.forUpdateOrders = forUpdateOrders;
	}
	//ENHANCEMENT START 20120727: Rhoda Redmine 68
	public boolean isSellerFinalized() {
		return isSellerFinalized;
	}
	public void setSellerFinalized(boolean isSellerFinalized) {
		this.isSellerFinalized = isSellerFinalized;
	}
	//ENHANCEMENT END 20120727: Rhoda Redmine 68
	//ENHANCEMENT START 20120727: Rhoda Redmine 354
	public String getAction() {
		return action;
	}
	public boolean getIsSellerOrderUnFinalized() {
		return isSellerOrderUnFinalized;
	}
	public void setIsSellerOrderUnFinalized(boolean isSellerOrderUnFinalized) {
		this.isSellerOrderUnFinalized = isSellerOrderUnFinalized;
	}
	public boolean getIsReceivedFinalized() {
		return isReceivedFinalized;
	}
	public void setIsReceivedFinalized(boolean isReceivedFinalized) {
		this.isReceivedFinalized = isReceivedFinalized;
	}
	public boolean getIsAllocFinalized() {
		return isAllocFinalized;
	}
	public void setIsAllocFinalized(boolean isAllocFinalized) {
		this.isAllocFinalized = isAllocFinalized;
	}
	public void setAction(String action) {
		this.action = action;
	}
	//ENHANCEMENT END 20120727: Rhoda Redmine 354

}
