package com.freshremix.model;

import com.freshremix.util.NumberUtil;

/**
 * Class that get the status of Allocation order 
 */
public class AllocationState extends SheetState {

	@Override
	public Boolean isFinalized(Order order) {
		return !NumberUtil.isNullOrZero(order.getAllocationFinalizedBy());
	}

	@Override
	public Boolean isPublished(Order order) {
		return !NumberUtil.isNullOrZero(order.getAllocationPublishedBy());
	}

	@Override
	public Boolean isFinalizedOnOther(Order order) {
		return !NumberUtil.isNullOrZero(order.getReceivedApprovedBy());
	}
	
}
