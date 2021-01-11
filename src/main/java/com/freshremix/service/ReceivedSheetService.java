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
 * Mar 30, 2010		nvelasquez		
 */
package com.freshremix.service;

import java.util.List;
import java.util.Map;

import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;
import com.freshremix.model.OrderReceived;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUBA;
import com.freshremix.model.User;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.ui.model.ProfitInfo;

/**
 * @author nvelasquez
 *
 */
public interface ReceivedSheetService {
	
	List<SKUBA> getDistinctSKUs(List<Integer> orderIds, List<Integer> sellerId, List<Integer> buyerId, List<String> deliveryDate, Integer categoryId, String hasQty);
	
	void loadOrderReceivedQuantitiesAllBuyers(Map<String, Object> skuOrderMap, List<Integer> buyerIds, List<Integer> orderIds, SKU skuObj);
	
	void loadSumOrderReceivedQuantitiesAllBuyers(Map<String, Object> skuOrderMap, List<Integer> buyerIds, List<Integer> orderIds, SKU skuObj);
	
	void loadOrderReceivedQuantitiesAllDates(Map<String, Object> skuOrderMap, List<String> deliveryDates, List<Integer> orderIds, SKU skuObj);
	
	void loadSumOrderReceivedQuantitiesAllDates(Map<String, Object> skuOrderMap, List<String> deliveryDates, List<Integer> orderIds, SKU skuObj);
	
	void saveOrder(OrderForm orderForm, OrderDetails orderDetails, Map<String, Order> allOrdersMap);
	
	List<Order> getPublishedOrdersForReceived(List<Integer> buyerIds, List<String> deliveryDates, List<Integer> sellerIds);
	
	void updateLockReceived(List<Integer> orderIds, Integer userId);
	
	void updateUnlockReceived(List<Integer> orderIds, Integer userId);
	
	List<Order> getApprovedOrdersForReceived(List<Integer> orderId);
	
	GrandTotalPrices computeTotalPriceOnDisplay(List<Map<String, Object>> skuOrderList);
	
	Map<String, Map<String, Map<Integer, OrderReceived>>> getReceivedItemsBulk(List<Integer> orderIds, List<Integer> skuIds);
	

	ProfitInfo getBuyerTotalPrices(User user, List<String> deliveryDate,
			List<Integer> sellerId, List<Integer> buyerId,
			List<Integer> categoryId, Double tax, String priceWithTaxOpt,
			Map<String, List<Integer>> mapOfMembersByDate);
	
}
