package com.freshremix.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.freshremix.exception.ServiceException;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.BillingItem;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;

public interface OrderBillingDao {

	Integer insertOrder(BillingItem billingItem, Integer userId);
	Integer insertBillingOrder(Order order);
	void updateSaveOrder(BillingItem billingItem, Integer userId);
	List<Order> getBillingOrders(List<Integer> sellerIds, List<Integer> buyerIds, List<String> deliveryDates, Integer isBuyer);
	List<Order> getSelectedOrdersByDate (List<Integer> sellerIds, List<Integer> buyerIds, String selectedDate);
	List<Order> getSelectedOrdersByBuyer (List<Integer> sellerIds, Integer buyerId, List<String> dates);
	List<AkadenSKU> selectDistinctSKUs(List<Integer> orderIds, Integer categoryId, Integer sheetTypeId, Integer rowStart, Integer pageSize);
	Map<Integer, BillingItem> getBillingItemsByBuyers(List<Integer> buyerIds, String deliveryDate, Integer skuId);
	Map<Integer, BillingItem> getBillingItemsByDates(List<String> deliveryDates, Integer skuId, Integer buyerId);
	Map<Integer, BillingItem> getAkadenOrigQtyBySkuDate(String deliveryDate, Integer skuId);
	Map<Integer, BillingItem> getAkadenOrigQtyBySkuDates(List<String> deliveryDates, Integer skuId, Integer buyerId);
	Map<Integer, BillingItem> getAkadenNegQtyBySkuDate(String deliveryDate, Integer skuId);
	Map<Integer, BillingItem> getAkadenNegQtyBySkuDates(List<String> deliveryDates, Integer skuId, Integer buyerId);
	Map<Integer, BillingItem> getAkadenCorrectedQtyBySkuDate(String deliveryDate, Integer skuId);
	Map<Integer, BillingItem> getAkadenCorrectedQtyBySkuDates(List<String> deliveryDates, Integer skuId, Integer buyerId);
	
	Order getOrderByDeliveryDate(Integer sellerId, Integer buyerId, String deliveryDate);
	void finalizeBilling(Integer orderId, Integer finalizedBy);
	void unfinalizeBilling(Integer orderId, Integer unfinalizedBy);
	
	void insertDefaultBillingItems (Integer orderId, Integer skuId, Integer skuBaId, Integer userId, BigDecimal quantity);
	void deleteBillingItems (Integer orderId);
	void insertBillingItem (BillingItem billingItem);
	void deleteBillingItemsByOrderIdAndSkuIds (Integer orderId, List<Integer> skuId);
	void updateBillingOrder(Integer orderId, Integer userId);
	void updateBillingItem(Integer skuId, Integer userId, BigDecimal quantity, Integer origSkuId, Integer orderId);
	
	Map<Integer, BillingItem> getSumOrderBillingMapOfSkuBuyers(List<Integer> orderIds, Integer skuId);
	Map<Integer, BillingItem> getSumOrderBillingMapOfSkuDates(List<Integer> orderIds, Integer skuId);
	GrandTotalPrices getGTPriceAllOrders(List<Integer> orderIds);
	List<Order> getApprovedOrdersForBilling(List<Integer> orderId);
	
	List<BillingItem> getBillingItemsBulk(List<Integer> orderIds, List<Integer> skuIds, boolean hasQty);
	
	List<BillingItem> getBillingItem(Integer sellerId, String deliveryDate, Integer skuId);

	List<AkadenSKU> selectDistinctSKUBA(List<Integer> orderIds, Integer categoryId);
	void deleteBillingItemsByBatch(List<Integer> orderIds)
			throws ServiceException;
	/**
	 * Batch insert for Billing items
	 * @param orderIds
	 * @param userId
	 * @throws ServiceException 
	 */
	void insertDefaultBillingItemsBatch(List<Integer> orderIds, Integer userId) throws ServiceException;
}
