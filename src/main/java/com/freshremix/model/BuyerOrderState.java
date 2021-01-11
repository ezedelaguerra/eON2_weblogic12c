package com.freshremix.model;

import com.freshremix.util.NumberUtil;
/**
 *	Class that get the state of Buyer Order sheet
 */
public class BuyerOrderState extends SheetState {

	@Override
	public Boolean isFinalized(Order order) {
		return !NumberUtil.isNullOrZero(order.getOrderLockedBy());
	}

	@Override
	public Boolean isPublished(Order order) {
		return false;
	}

	@Override
	public Boolean isFinalizedOnOther(Order order) {
		return !NumberUtil.isNullOrZero(order.getOrderFinalizedBy());
	}

}
