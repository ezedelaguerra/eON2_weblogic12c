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
 * Mar 23, 2010		gilwen		
 * 20121001	Rhoda	v12		Redmine #1089 – Allocation-Received Sheet different in quantity 
 */
package com.freshremix.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.SKU;
import com.freshremix.ui.model.ProfitInfo;

/**
 * @author gilwen
 *
 */
public interface AllocationDao {

	void insertDefaultAllocItems(OrderItem orderInfo);
	
	void deleteAllocItems(Integer orderId);
	
	void insertAllocItem(Integer orderId, Integer skuId, BigDecimal quantity, BigDecimal skuMaxLimit);
	
	void updateAllocItem(Integer orderId, Integer skuId, Integer skuBaId, BigDecimal quantity, String isApproved);
	// ENHANCEMENT START 20121001: Rhoda Redmine 1089
	void updateAllocItemFromReceived(Integer orderId, Integer skuId, Integer skuBaId, String isApproved);
	// ENHANCEMENT END 20121001: Rhoda Redmine 1089
	
	Map<Integer,OrderItem> getAllocItems (String selectedDate, Integer skuId);
	
	List<OrderItem> getAllocItemsByOrderId (Integer orderId);
	
	List<Order> getFinalizedOrdersForAlloc(List<Integer> buyerIds, List<String> deliveryDates, List<Integer> sellerIds);

	List<SKU> selectDistinctSKUs(List<Integer> selectedOrders, List<Integer> sellerId, List<Integer> buyerId, List<String> deliveryDate,Integer categoryId, String hasQty);
	
	Map<Integer, OrderItem> getSumAllocationMapOfSkuBuyers(List<Integer> orderIds, Integer skuId);

	Map<Integer, OrderItem> getSumAllocationMapOfSkuDates(List<Integer> orderIds, Integer skuId);
	
	Map<Integer, OrderItem> getOrderItemsMapOfSkuDate(Map<String, Object> param);
	
	Map<Integer, OrderItem> getOrderItemsMapOfSkuDates(Map<String, Object> param);
	
	List<Order> getAllOrders(List<Integer> buyerIds, List<String> dateList, List<Integer> sellerIds);
	
	OrderItem getAllocationItem(Map<String, Object> param);
	
	void deleteAllocationItemsByOrderIdAndSkuId(Map<String,Object> param);
	
	int updateSpecialAllocItem(Integer orderId, Integer skuId, Integer origSkuId, BigDecimal quantity);
	
	List<SKU> selectDistinctSKUs(List<Integer> allOrderId);
	
	GrandTotalPrices getGTPriceAllOrders(List<Integer> orderIds);
	
	List<OrderItem> getSellerAllocItemsBulk(List<Integer> orderIds, List<Integer> skuIds, boolean hasQty);
	
	List<OrderItem> getOrderItem(Integer sellerId, String deliveryDate, Integer skuId);
	
	void deleteAllocItemsByOrderIdsAndSkuIds(List<Integer> orderIdList, List<Integer> skuToDelete);
	
	ProfitInfo getProfitInfo(String deliveryDate, List<Integer> buyerId, List<Integer> sellerId, Integer categoryId, Double tax);
	
	public void insertDefaultAllocItemsBatch(List<Integer> orderIds, Integer createdBy) throws Exception;

	void deleteAllocItemsBatch(List<Integer> orderIds) throws Exception;

	/**
	 * Retrieves the list of Order Allocation Items from a list of dates and skuIds
	 * @param skuIds
	 * @param dates
	 * @return
	 */
	List<OrderItem> getOrderItemsFromListOfSKUAndDate(List<Integer> skuIds,
			List<String> dates);
}
