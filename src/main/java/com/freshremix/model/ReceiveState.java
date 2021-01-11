package com.freshremix.model;

import com.freshremix.util.NumberUtil;
/**
 * Class that get the status of Receive sheet 
 */
public class ReceiveState extends SheetState {

	@Override
	public Boolean isFinalized(Order order) {
		return !NumberUtil.isNullOrZero(order.getReceivedApprovedBy());
	}

	@Override
	public Boolean isPublished(Order order) {
		return false;
	}

	@Override
	public Boolean isFinalizedOnOther(Order order) {
		return !NumberUtil.isNullOrZero(order.getAllocationFinalizedBy());
	}
}
