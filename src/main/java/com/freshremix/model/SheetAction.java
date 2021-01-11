package com.freshremix.model;
/**
 * 
 * Abstract class for getting sheet's state
 *
 */
public interface SheetAction {

	public Boolean isPublished(Order order);
	
	public Boolean isFinalized(Order order);
	
	/**
	 * Check if the sheet is finalized on the other end of user
	 * Example:
	 * User is seller, Check if sheet has been locked by all of seller's buyers 
	 */
	public Boolean isFinalizedOnOther(Order order);
	
}
