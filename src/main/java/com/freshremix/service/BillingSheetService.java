package com.freshremix.service;

import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;

import com.freshremix.model.AkadenItem;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.BillingItem;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;

public interface BillingSheetService {

//	void insertDefaultOrders(OrderSheetParam orderSheetParam, User user, DealingPattern dp) throws Exception;

	List<Order> getSelectedOrdersByDate (List<Integer> seller, List<Integer> buyer, String selectedDate);
	List<Order> getSelectedOrdersByBuyer (List<Integer> seller, Integer buyer, String startDate, String endDate);
	List<Order> getBillingOrders (List<Integer> sellerIds, List<Integer> buyerIds, List<String> deliveryDates, Integer isBuyer);
	List<Order> getOrdersByOrderIds(List<Integer> orderId);
	Order combineOrdersStatus(List<Order> orders);
	List<AkadenSKU> getDistinctSKUs(List<Integer> orderIds, Integer categoryId, Integer sheetTypeId, Integer rowStart, Integer pageSize);
	void loadOrderItemQuantities(Map<String, Object> skuOrderMap, List<Integer> companyBuyerIds, String deliveryDate, AkadenSKU skuObj, JSONObject json) throws Exception;
	void loadOrderItemQuantities(Map<String, Object> skuOrderMap, List<String> deliveryDates, Integer buyerId, AkadenSKU skuObj, JSONObject json) throws Exception;
	void saveOrder(OrderForm orderForm, OrderDetails orderDetails, Map<String, Order> allOrdersMap, Map<Integer, AkadenSKU> skuObjMap);
	
	void loadSumOrderBillingQuantitiesAllBuyers(Map<String, Object> skuOrderMap, List<Integer> buyerIds, List<Integer> orderIds, AkadenSKU skuObj);
	void loadSumOrderBillingQuantitiesAllDates(Map<String, Object> skuOrderMap, List<String> deliveryDates, List<Integer> orderIds, AkadenSKU skuObj);
	
	void updateFinalizeBilling(Integer orderId, Integer finalizedBy);
	void updateUnfinalizeBilling(Integer orderId, Integer unfinalizedBy);
	boolean sendMailNotification(String orderDate, String state, String username, String fromAddress, String[] toAddress);
	boolean sendMailNotification(String fromAddress, String[] toAddress, String subject, String message);
	
	Order combineOrders(List<Order> orders);
	
	GrandTotalPrices getGTPriceAllOrders(List<Integer> orderIds);
	//Order getOrderByDeliveryDate(Integer sellerId, Integer buyerId, String deliveryDate);
	//void saveOrder(OrderForm orderForm, OrderDetails orderDetails, Map<Integer, SKU> skuObjMap);
	//void updateFinalizedOrder(Integer orderId, Integer finalizedBy, Integer unfinalizedBy);
	//void updateUnfinalizedOrder(Integer orderId, Integer finalizedBy, Integer unfinalizedBy);
	
	/*void deleteOrderItems(Integer orderId, List<Integer> skuId);
	void deleteAllOrderItemsByOrderId(Integer orderId);
	
	
	BigDecimal getGTPriceByOrderId(List<Integer> buyerId, String startDate, String endDate);
	
	List<Order> getPublishedOrders(List<Integer> buyerIds, List<String> deliveryDates, List<Integer> sellerIds);	
	
	Order getBuyerOrderStatus(OrderSheetParam orderSheetParam);
	
	List<SKU> getPublishedSKUs(List<Integer> buyerIds, List<String> deliveryDates, List<Integer> sellerIds, Integer categoryId, Integer rowStart, Integer pageSize);	
	
	List<Map<String, Object>> loadPublishedOrders(User user, OrderSheetParam orderSheetParam, PageInfo pageInfo);

	List<SKU> getDistinctSKUs(List<String> deliveryDates, Integer categoryId, Integer sheetTypeId, List<Integer> sellerId, Integer rowStart, Integer pageSize);
	void loadOrderItemQuantities(Map<String, Object> skuOrderMap, List<String> deliveryDates, Integer buyerId, SKU skuObj);*/
	
	List<Order> getApprovedOrdersForBilling(List<Integer> orderId);
	
	Map<Integer, Map<String, Map<Integer, BillingItem>>> getBillingItemsBulk(List<Integer> orderIds, List<Integer> skuIds);
	
	List<BillingItem> getBillingItem(Integer sellerId, String deliveryDate, Integer skuId);
	
	List<AkadenItem> getAkadenItemsBulk(List<Integer> orderIds);
	
	GrandTotalPrices computeTotalPricesOnDisplay(List<Map<String, Object>> skuOrderList);
	
	List<AkadenSKU> getDistinctSKUBA(List<Integer> orderIds, Integer categoryId);
}