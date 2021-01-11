package com.freshremix.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.freshremix.model.OrderItem;
import com.freshremix.model.SKU;
import com.freshremix.ui.model.OrderItemUI;
import com.freshremix.ui.model.ProfitInfo;

public interface OrderSheetDao {

	List<OrderItemUI> loadOrderItems(Map<String,Object> param);
	
	List<OrderItemUI> loadPreviousOrderItems (Integer sellerId, Integer selectedBuyerId, Integer categoryId, Integer sheetTypeId, String deliveryDate);
	
	List<SKU> selectDistinctSKUs(List<Integer> selectedOrders, List<Integer> sellerId, List<Integer> buyerId, List<String> deliveryDate,Integer categoryId, Integer sheetTypeId, String hasQty);
	Map<Integer, OrderItem> getOrderItemsMapOfSkuBuyers(List<Integer> orderIds, Integer skuId);
	Map<Integer, OrderItem> getOrderItemsMapOfSkuDates(List<Integer> orderIds, Integer skuId);
	Map<Integer, OrderItem> getSumOrderItemsMapOfSkuBuyers(List<Integer> orderIds, Integer skuId);
	Map<Integer, OrderItem> getSumOrderItemsMapOfSkuDates(List<Integer> orderIds, Integer skuId);
	
	Map<Integer, OrderItem> getOrderItemsMapOfSkuDate(Map<String, Object> param);
	
	Map<Integer, OrderItem> getOrderItemsMapOfSkuDates(Map<String, Object> param);

	List<SKU> getPublishedSKUs(Map<String, Object> param);

	Map<Integer, OrderItem> getBuyerOrderItemsByBuyers(List<Integer> buyerIds, String deliveryDate, Integer skuId, Integer isBuyer);
	
	Map<Integer, OrderItem> getBuyerOrderItemsByDates(Integer buyerId, List<String> deliveryDates,  Integer skuId, Integer isBuyer);
	
	Map<Integer, OrderItem> getSumBuyerOrderItemsMapOfSkuBuyers(List<Integer> orderIds, Integer skuId, Integer isBuyer);
	Map<Integer, OrderItem> getSumBuyerOrderItemsMapOfSkuDates(List<Integer> orderIds, Integer skuId, Integer isBuyer);
	
	BigDecimal getTotalQuantityByOtherBuyers(Integer sellerId, List<Integer> buyerId, Integer skuId, String deliveryDate);
	
	Map<Integer, Map<String, Map<Integer, OrderItem>>> getSellerOrderItems(List<Integer> skuIds, List<String> deliveryDates, 
			List<Integer> buyerUserIds, boolean hasQty);
	List<OrderItem> getSellerOrderItemsBulk(List<Integer> skuIds, List<String> deliveryDates, List<Integer> buyerUserIds, boolean hasQty);
	List<OrderItem> getBuyerOrderItemsBulk(List<Integer> orderIds, List<Integer> skuIds, boolean hasQty);
	
	List<SKU> wsSelectDistinctSKUs(List<Integer> allOrderId);
	
	BigDecimal getSumOrderQuantities(List<Integer> orderIds);
	
	BigDecimal getAvailableQuantities(Integer sellerId, Integer buyerId, String deliveryDate, Integer skuId);
	
	ProfitInfo getProfitInfo(String deliveryDate, List<Integer> buyerId, List<Integer> sellerId, Integer categoryId, Double tax);
	
	List<ProfitInfo> getBuyerPricesPerSKU(String deliveryDate, List<Integer> buyerId, List<Integer> sellerId, Integer categoryId, Double tax);

	/**
	 * Retrieves the Order items by list of OrderId
	 * @param orderIds
	 * @return
	 */
	List<OrderItem> getOrderItemsByOrderIdBulk(List<Integer> orderIds);

	/**
	 * retrieves the list of order items by list of orderids and skuid
	 * @param orderIds
	 * @param skuId
	 * @return
	 */
	List<OrderItem> getOrderItemsListOfSkuBuyers(List<Integer> orderIds,
			Integer skuId);
	/**
	 * 
	 *  retrieves the list of order items by list of deliverydates and skuids
	 * 
	 * @param skuIds
	 * @param deliveryDates
	 * @return
	 */
	List<OrderItem> getOrderItemsListOfSkuId(List<Integer> skuIds,
			List<String> deliveryDates);
}
