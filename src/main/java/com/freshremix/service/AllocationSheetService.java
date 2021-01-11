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
 * date		name		version		changes
 * ------------------------------------------------------------------------------		
 * 20120724	gilwen		v11			Redmine 797 - SellerAdmin can select seller who don't have category
 */

package com.freshremix.service;

import java.util.List;
import java.util.Map;

import com.freshremix.exception.ServiceException;
import com.freshremix.model.AllocationInputDetails;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SKU;
import com.freshremix.model.User;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.ui.model.ProfitInfo;

/**
 * @author gilwen
 *
 */
public interface AllocationSheetService {
	
	List<SKU> getDistinctSKUs(List<Integer> selectedOrders, List<Integer> sellerId, List<Integer> buyerId, List<String> deliveryDate,Integer categoryId, String hasQty);
	
	void updatePublishOrder(Integer orderId, Integer publishBy);
	
	void updateUnpublishOrder(Integer orderId, Integer userId);
	
	//void updateFinalizedOrder(Integer orderId, Integer finalizedBy, Integer unfinalizedBy);
	
	void updateUnfinalizedOrder(Integer orderId, Integer finalizedBy, Integer unfinalizedBy);
	
	List<Order> getFinalizedOrdersForAlloc(List<Integer> buyerIds, List<String> deliveryDates, List<Integer> sellerIds);
	
	List<Order> getSelectedOrders (Map<String, Order> allOrdersMap, OrderSheetParam osp);
	
	Order combineOrders(List<Order> orders);
	
	// ENHANCEMENT 20120724: Lele - Redmine 797
	@Deprecated
	String getSellerNames(List<Integer> sellerId) throws Exception;
	
	List<Order> getAllOrders (List<Integer> buyerIds, List<String> dateList, List<Integer> sellerIds);
	
	List<Integer> getSelectedOrderIds (Map<String, Order> allOrdersMap, OrderSheetParam osp);
	
	void saveOrder2(OrderForm orderForm, OrderDetails orderDetails, Map<String, Order> allOrdersMap, Map<Integer, SKU> skuObjMap) throws ServiceException;
	
	void deleteOrderItems(Integer orderId, List<Integer> skuId);
	
	List<Order> getSavedOrders(List<Order> orders);
	
	List<Order> getToProcessOrders(List<Order> orders);
	
	boolean sendMailNotification(String orderDate, String state, String username, String fromAddress, String[] toAddress);
	
	boolean sendMailNotification(String fromAddress, String[] toAddress, String subject, String message);
	
	List<Order> getPublishedOrders(List<Order> orders);
	
	List<Order> getFinalizedOrders(List<Order> orders);
	
	boolean skuHasFinalizedAllocation(String deliveryDate, Integer sellerId, List<Integer> dbBuyers, Map<String, Order> allOrdersMap, List<Integer> finalizedBuyers);
	
	GrandTotalPrices getGTPriceAllOrders(List<Integer> orderIds);
	
	Map<Integer, Map<String, Map<Integer, OrderItem>>> getSellerAllocItemsBulk(List<Integer> orderIds, List<Integer> skuIds);
	
	List<OrderItem> getOrderItem(Integer sellerId, String deliveryDate, Integer skuId);
	
	boolean skuHasQuantities(List<Integer> companyBuyerIds, String deliveryDate, SKU skuObj)  throws Exception ;
	
	List<SKU> wsGetDistinctSKUs(List<Integer> orderIds);
	
	void deleteAllocationItems(List<Integer> skuToDelete, String deliveryDate,
			List<Integer> buyerIds, List<Integer> sellerIds,List<Order> allOrders);
	void updateAllocationOrdersWS(String deliveryDate,List<Integer> buyerIds, List<Integer> sellerIds,
			AllocationInputDetails[] allocationInputDetails,List<Order> orderList, User user);
	void insertAllocationSKUWS(String deliveryDate,List<Integer> buyerIds, List<Integer> sellerIds,
			AllocationInputDetails[] allocationInputDetails,List<Order> orderList);
	void markAsSaved(Integer allocationSavedBy, Integer updatedBy, Integer orderId);
	
	ProfitInfo getProfitInfo(User user, List<String> deliveryDate, List<Integer> sellerId, Map<String, List<Integer>> mapOfMembersByDate, List<Integer> buyerId, List<Integer> categoryId, Double tax);
	
	// ENHANCEMENT 20120724: Lele - Redmine 797
	String getSellerNames(List<Integer> sellerId, Integer categoryId) throws Exception;

	/**
	 * Performs a bulk save and publish allocation for each orderid in the list
	 * @param orderIdList
	 * @param savedAndPublishedBy
	 * @throws Exception 
	 */
	void saveAndPublishAllocation(List<Integer> orderIdList,
			Integer savedAndPublishedBy) throws Exception;

	/**
	 * Sends a consolidated email.
	 * 
	 * @param orderDateList
	 * @param state
	 * @param username
	 * @param fromAddress
	 * @param toAddress
	 * @return
	 */
	boolean sendConsolidatedMailNotification(List<String> orderDateList,
			String state, String username, String fromAddress,
			String[] toAddress);

	void updateFinalizeOrder(User user, List<Order> ordersForUpdate) throws ServiceException;

	void updatePublishOrder(User user, List<Order> savedOrders)
			throws Exception;

	void updateUnpublishOrder(List<Order> pubOrders, User user)
			throws Exception;

	void updateUnfinalizeOrder(List<Order> finOrder, User user)
			throws ServiceException;


}
