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
 * 20121120 mikes       v14.01      Redmine 1049 seller-selleradmin conccurency on finalize in order sheet
 */

package com.freshremix.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;

import com.freshremix.exception.ServiceException;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.DefaultOrder;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SKU;
import com.freshremix.model.User;
import com.freshremix.model.WSInputDetails;
import com.freshremix.model.WSSKU;
import com.freshremix.model.WSSellerSKU;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.ui.model.OrderItemUI;
import com.freshremix.ui.model.PageInfo;
import com.freshremix.ui.model.ProfitInfo;
import com.freshremix.model.ConcurrencyResponse;

public interface OrderSheetService {

	void insertDefaultOrders(OrderSheetParam orderSheetParam, User user, DealingPattern dp) throws Exception;
	
	List<Order> getSavedOrders (List<Integer> buyerIds, List<String> dateList, List<Integer> sellerIds);
	List<Order> getSavedOrders (List<Order> orders);
	List<Order> getPublishedOrders (List<Order> orders);
	List<Order> getFinalizedOrders (List<Order> orders);
	List<Order> getAllOrders (List<Integer> buyerIds, List<String> dateList, List<Integer> sellerIds);
	Order getOrdersByOrderId(List<Integer> orderId);
	Order getOrderByDeliveryDate(Integer sellerId, Integer buyerId, String deliveryDate);
	List<Order> getSelectedOrders (Map<String, Order> allOrdersMap, OrderSheetParam osp);
	List<Integer> getSelectedOrderIds (Map<String, Order> allOrdersMap, OrderSheetParam osp);
	ConcurrencyResponse saveOrder(OrderForm orderForm, OrderDetails orderDetails,
			List<Order> allOrders, Map<Integer, SKU> skuObjMap)
			throws ServiceException;
	void updatePublishOrder(Map<String, Object> param);
	void updateUnpublishOrder(Integer orderId, Integer publishBy, Integer orderUnfinalizedBy);
	void updateFinalizedOrder(Integer orderId, Integer finalizedBy, Integer unfinalizedBy);
	void updateUnfinalizedOrder(Integer orderId, Integer finalizedBy, Integer unfinalizedBy);
	//List<Map<String, Object>> loadOrderItems2(User user, OrderSheetParam osParam, PageInfo pageInfo);
	
	DefaultOrder loadDefualtOrderItems(Integer sellerId, Integer selectedBuyerId, Integer sheetTypeId, String deliveryDate);
	//void saveDefaultOrderItems(DefaultOrder dOrder);
	void updateDefaultOrderItems(DefaultOrder dOrder, Integer sellerId, Integer selectedBuyerId, Integer sheetTypeId, String deliveryDate);
	List<Integer> getSelectedOrdersByDate (List<Integer> seller, List<Integer> buyer, String selectedDate);
	List<Integer> getSelectedOrdersByBuyer (List<Integer> seller, Integer buyer, String startDate, String endDate);
	
	void deleteOrderItems(Integer orderId, List<Integer> skuId);
	void deleteAllOrderItemsByOrderId(Integer orderId);
	
	List<SKU> getDistinctSKUs(List<Integer> selectedOrders, List<Integer> sellerId, List<Integer> buyerId, List<String> deliveryDate,Integer categoryId, Integer sheetTypeId, String hasQty);
	
	void loadOrderItemQuantities(Map<String, Object> skuOrderMap, List<Integer> companyBuyerIds, String deliveryDate, SKU skuObj, JSONObject json)  throws Exception ;
	
	void loadOrderItemQuantities(Map<String, Object> skuOrderMap, List<String> deliveryDates, Integer buyerId, SKU skuObj, JSONObject json)  throws Exception ;
	
	public Order combineOrders(List<Order> orders);
	public Order combineSelectedOrders(List<Order> orders);
	
	void loadSumOrderItemQuantitiesAllBuyers(Map<String, Object> skuOrderMap, List<Integer> buyerIds, List<Integer> orderIds, SKU skuObj);
	void loadSumOrderItemQuantitiesAllDates(Map<String, Object> skuOrderMap, List<String> deliveryDates, List<Integer> orderIds, SKU skuObj);
	// ENHANCEMENT 20120724: Lele - Redmine 797
	@Deprecated
	String getSellerNames(List<Integer> sellerId) throws Exception;
	void setSellerNameAndRenderer(List<Integer> sellerId, StringBuffer sellerName, StringBuffer sellerNameRenderer) throws Exception;

	public boolean sendMailNotification(String orderDate, String state, String username, String fromAddress, String[] toAddress);
	public boolean sendMailNotification(String fromAddress, String[] toAddress, String subject, String message);
	
	BigDecimal getTotalQuantityByOtherBuyers(Integer sellerId, List<Integer> buyerId, Integer skuId, String deliveryDate);
	
	Map<Integer, Map<String, Map<Integer, OrderItem>>> getSellerOrderItems(List<Integer> skuList,
			List<String> deliveryDates, List<Integer> buyerUserIds, boolean hasQty)  throws Exception ;
	
	DefaultOrder loadDefaultOrderItems(Integer sellerId, Integer selectedBuyerId, Integer sheetTypeId, 
			String deliveryDate,Order oldOrder);
	
	void updateOrder(Integer orderId,Order order);
	
	void saveBulkOrders(Integer orderId,Integer sellerId,Integer buyerId,String deliveryDate);
	
	
	boolean skuHasQuantity(Integer skuId, List<String> deliveryDate);
	
	List<OrderItem> getOrderItem(Integer sellerId, String deliveryDate, Integer skuId);
	
	Order getPreviousOrders(Integer sellerId,Integer buyerId,String deliveryDate);
	
	List<SKU> wsGetDistinctSKUs(List<Integer> selectedOrders);
	
	public void wsLoadQuantities(Integer buyerId, String deliveryDate,
			SKU skuObj, WSSKU wsSKU) throws Exception ;
	
	void updateOrdersWS(String deliveryDate,List<Integer> buyerIds,List<Integer> sellerIds,List<Order> allOrders)throws Exception;
	
	void deleteOrdersWS(List<Integer> skuToDelete,String deliveryDate,List<Integer> buyerIds,List<Integer> sellerIds,
			List<Order> allOrders,Map<Integer,SKU> skusToDelete)throws Exception;
	
	void updateSKUWS(String deliveryDate,List<Integer> buyerIds,
			List<Integer> sellerIds,WSInputDetails[] details,Map<Integer,SKU> updateSKUMap,List<Order> orderList)throws Exception;
	
	void insertSKUWS(String deliveryDate,List<Integer> buyerIds,
			List<Integer> sellerIds,WSInputDetails[] details,List<Order> orderList)throws Exception;
	
	BigDecimal getSumOrderQuantities(List<Integer> orderIds);
	
	BigDecimal getAvailableQuantities(Integer sellerId, Integer buyerId, String deliveryDate, Integer skuId);
	
	Boolean isQuantityValid (Integer sellerId, Integer buyerId, String deliveryDate, SKU sku, BigDecimal quantity, BigDecimal remCount);
	
	
	// ENHANCEMENT START 20120724: Lele - Redmine 797
	String getSellerNames(List<Integer> sellerId, Integer categoryId) throws Exception;
	List<Integer> filterSellerByCategoryId(List<Integer> sellerId, Integer categoryId);
	// ENHANCEMENT END 20120724:

	List<Order> getFinalizedOrderFromOrderIdList(List<Integer> orderIdList);

	/**
	 * Updates the Order as finalized.
	 * 
	 * Returns a subset of the published orders, containing those records that
	 * were already finalized in another session (not this current session). The
	 * returned sub list will be used to create a message to be alerted to the
	 * user.
	 * 
	 * @param user
	 *            - User object (taken from session)
	 * @param pubOrder
	 *            - List of Orders to be finalized (taken from session)
	 * @param sellerMap
	 *            map of sellers Map<Integer(userid), User>
	 * @return list of Orders that were not finalized.
	 * @throws Exception 
	 */
	List<Order> updateFinalizeOrder(User user, List<Order> pubOrder,
			Map<Integer, User> sellerMap) throws Exception;

	/**
	 * Bulk email notification.  Sends out 1 email for all the delivery dates for a given email address
	 * @param orderDate
	 * @param state
	 * @param username
	 * @param fromAddress
	 * @param toAddress
	 * @return
	 */
	boolean sendConsolidatedMailNotification(List<String> orderDate, String state,
			String username, String fromAddress, String[] toAddress);



	/**
	 * Retrieves all order items by the list of order ids
	 * @param orderIds
	 * @return
	 */
	List<OrderItem> getOrderItemsByOrderIdBulk(List<Integer> orderIds);

	/**
	 * Retrieves the total quantity of an SKU on a given date
	 * @param updatedOrders
	 * @param deliveryDates
	 * @return
	 */
	Map<Integer, BigDecimal> getTotalQuantityBySKUIdListAndDate(
			List<OrderItemUI> updatedOrders, List<String> deliveryDates);
	/**
	 * Bulk unfinalization of orders
	 * 
	 * @param user
	 * @param ordersForUnfinalize
	 * @return
	 * @throws Exception
	 */
	List<Order> updateUnFinalizeOrder(User user, List<Order> ordersForUnfinalize)
			throws Exception;
	/**
	 * Bulk unpublish of orders
	 * @param user
	 * @param publishedOrders
	 * @throws Exception
	 */
	void updateUnpublishOrder(User user, List<Order> publishedOrders)
			throws Exception;

	/**
	 * Bulk publish of orders
	 * 
	 * @param user
	 * @param savedOrders
	 * @throws Exception
	 */
	void updatePublishOrder(User user, List<Order> savedOrders)  throws Exception;

	/**
	 * 
	 * @param user 
	 * @param deliveryDate
	 * @param sellerId
	 * @param mapOfMembersByDateList
	 * @param buyerId
	 * @param categoryId
	 * @param tax
	 * @return
	 */
	ProfitInfo getProfitInfo(User user, List<String> deliveryDate, List<Integer> sellerId,
			Map<String, List<Integer>> mapOfMembersByDateList,
			List<Integer> buyerId, List<Integer> categoryId, Double tax);



	Map<String, Object> loadOrderItems(List<Integer> selectedOrders, User user,
			OrderSheetParam osParam, PageInfo pageInfo,
			Map<String, List<Integer>> mapOfMembersByDate) throws Exception;

	void deleteAllOrderItems(List<Integer> orderItemIds) throws ServiceException;

	List<OrderItem> getOrderItemsListOfSkuId(List<Integer> skuIds,
			List<String> deliveryDates);

	void updateOrdersheetWS(List<WSSellerSKU> skuList, String deliveryDate)
			throws  Exception;



	void saveWSData(User loginUser, List<WSSellerSKU> sellerSkuList,
			List<Integer> sellerIds, List<Integer> buyerIds,
			List<String> dateList, List<Order> orderList, List<SKU> dSKUList)
			throws Exception;

	
}