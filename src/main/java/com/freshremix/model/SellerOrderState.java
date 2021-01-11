package com.freshremix.model;

import com.freshremix.util.NumberUtil;

/**
 * Class that get the status of Seller Order sheet 
 */
public class SellerOrderState extends SheetState {

	@Override
	public Boolean isFinalized(Order order) {
		return !NumberUtil.isNullOrZero(order.getOrderFinalizedBy());
	}

	@Override
	public Boolean isPublished(Order order) {
		return !NumberUtil.isNullOrZero(order.getOrderPublishedBy());
	}
	
	@Override
	public Boolean isFinalizedOnOther(Order order) {
		return !NumberUtil.isNullOrZero(order.getOrderLockedBy());
	}

}
