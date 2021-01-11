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
 * Mar 27, 2010		gilwen		
 */
package com.freshremix.model;

import java.util.List;
import java.util.Map;

/**
 * @author gilwen
 *
 */
public class DealingPattern {

	private Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap;
	private Map<String, Map<String, List<Integer>>> buyerToSellerDPMap;
	
	public DealingPattern (Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap, Map<String, Map<String, List<Integer>>> buyerToSellerDPMap) {
		this.sellerToBuyerDPMap = sellerToBuyerDPMap;
		this.buyerToSellerDPMap = buyerToSellerDPMap;
	}
	public Map<String, Map<String, List<Integer>>> getSellerToBuyerDPMap() {
		return sellerToBuyerDPMap;
	}
	public void setSellerToBuyerDPMap(Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap) {
		this.sellerToBuyerDPMap = sellerToBuyerDPMap;
	}
	public Map<String, Map<String, List<Integer>>> getBuyerToSellerDPMap() {
		return buyerToSellerDPMap;
	}
	public void setBuyerToSellerDPMap(Map<String, Map<String, List<Integer>>> buyerToSellerDPMap) {
		this.buyerToSellerDPMap = buyerToSellerDPMap;
	}
	
}
