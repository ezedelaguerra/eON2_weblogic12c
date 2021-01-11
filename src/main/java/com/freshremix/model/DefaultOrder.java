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
 * Feb 22, 2010		gilwen		
 */
package com.freshremix.model;

import java.util.List;

import com.freshremix.ui.model.OrderItemUI;

/**
 * @author gilwen
 *
 */
public class DefaultOrder {

	private Integer orderId;
	private List<OrderItemUI> orderItemsFruits;
	private List<OrderItemUI> orderItemsVegetables;
	private List<OrderItemUI> orderItemsFish;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public List<OrderItemUI> getOrderItemsFruits() {
		return orderItemsFruits;
	}
	public void setOrderItemsFruits(List<OrderItemUI> orderItemsFruits) {
		this.orderItemsFruits = orderItemsFruits;
	}
	public List<OrderItemUI> getOrderItemsVegetables() {
		return orderItemsVegetables;
	}
	public void setOrderItemsVegetables(List<OrderItemUI> orderItemsVegetables) {
		this.orderItemsVegetables = orderItemsVegetables;
	}
	public List<OrderItemUI> getOrderItemsFish() {
		return orderItemsFish;
	}
	public void setOrderItemsFish(List<OrderItemUI> orderItemsFish) {
		this.orderItemsFish = orderItemsFish;
	}
}
