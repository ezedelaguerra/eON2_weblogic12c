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
 * Jan 12, 2011		raquino		
 */
package com.freshremix.service;

import java.util.List;
import java.util.Map;

import com.freshremix.model.Order;
import com.freshremix.model.User;
import com.freshremix.model.WSInputDetails;
import com.freshremix.model.WSSKU;

/**
 * @author raquino
 * 
 */
public interface WebService {

	void createSheetWS(User user, Map<Integer, User> sellerMap,
			List<Integer> buyerIdList, String orderDate,
			WSInputDetails[] wsInputDetails, boolean isPublish,
			boolean isFinalize, Map<String, Map<String, 
			 	List<Integer>>> sellerToBuyerDPMap) throws Exception;

	WSSKU[] getSellerOrderSheet(List<Integer> sellerIds, List<Integer> buyerIds,
			String orderDate,Map<String, Map<String, 
		 	List<Integer>>> sellerToBuyerDPMap) throws Exception;

	void addSkuAllocation(User user, Map<Integer, User> sellerMap,
			WSInputDetails[] wsInputDetails, List<Integer> buyerIds,
			String orderDate, List<Order> orderList) throws Exception;
	
	void insertDefaultOrders(List<Integer> sellerIds, String orderDate,
			List<Integer> buyerIdList,
			Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap);
}
