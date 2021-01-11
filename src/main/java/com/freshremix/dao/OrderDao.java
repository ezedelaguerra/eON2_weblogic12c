package com.freshremix.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.freshremix.exception.ServiceException;
import com.freshremix.model.Order;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUBA;

public interface OrderDao {

	Integer insertOrder(Order order);
	void updateSaveOrder(Map<String, Object> param);
	void updateSaveOrder(Integer orderSavedBy, Integer createdBy, Integer orderId);
	void updateSaveAllocation(Integer allocationSavedBy, Integer updatedBy, Integer orderId);
	List<Order> getOrdersByOrderId(List<Integer> orderId);
	List<Integer> getSelectedOrdersByDate (Integer seller, List<Integer> buyer, String selectedDate);
	List<Integer> getSelectedOrdersByBuyer (Integer seller, Integer buyer, List<String> dates);
	List<Order> getSavedOrders(List<Integer> buyerIds, List<String> dateList, List<Integer> sellerIds);
	List<Order> getAllOrders(List<Integer> buyerIds, List<String> dateList, List<Integer> sellerIds);
	Order getOrderByDeliveryDate(Integer sellerId, Integer buyerId, String deliveryDate);
	void finalizeOrder(Integer orderId, Integer finalizedBy, Integer unfinalizedBy);
	void unfinalizeOrder(Integer orderId, Integer finalizedBy, Integer unfinalizedBy);
	void publishOrder(Map<String, Object> param);
	void unpublishOrder(Integer orderId, Integer publishBy, Integer orderUnfinalizedBy);
	
	void saveAllocation (Integer orderId, Integer updatedBy, Integer savedBy);
	void publishAllocation (Integer orderId, Integer updatedBy, Integer publishBy);
	void unpublishAllocation (Integer orderId, Integer updatedBy, Integer publishBy);
	void finalizeAllocation (Integer orderId, Integer finalizedBy, Integer unfinalizedBy, Integer updatedBy);
	void unfinalizeAllocation (Integer orderId, Integer finalizedBy, Integer unfinalizedBy, Integer updatedBy);
	
	List<Order> getPublishedOrders(List<Integer> buyerIds, List<String> deliveryDates, List<Integer> sellerIds, Integer isBuyer, String enableBAPublish);
	
	void lockOrders(List<Integer> orderIds, Integer userId) throws ServiceException;
	void unlockOrders(List<Integer> orderIds, Integer userId) throws ServiceException;
	void updateOrderQtyBySkuIdAndOrderId (BigDecimal quantity, Integer userId, Integer skuId, Integer orderId, Integer newSkuId, String baosVisFlag);
	void updateOrderQtyAllDatesBySKUBA (BigDecimal quantity, Integer userId, Integer skuId, Integer skuBaId, Integer orderId);
	int updateOrderItemQtySKUBA (BigDecimal quantity, Integer userId, Integer skuId, Integer skuBaId, Integer orderId, Integer newSkuId, String baosVisFlag);
	//int updateOrderItemSKUBA (Integer userId, Integer skuId, Integer skuBaId, Integer orderId, Integer newSkuId, String baosVisFlag);
	void updateOrderByOrderId (Integer userId, Integer orderId);
	void updatePublishOrder(Integer userId, Integer orderId);
	
	//START:JR
	List<SKU> getBAPublishedSKUs(List<Integer> orderIds, Integer categoryId, Integer sheetTypeId, Integer rowStart, Integer pageSize, Integer isBuyer);
	Order getPreviousOrder(Integer sellerId, Integer buyerId, String deliveryDate);
	void updateOrder(Order order);
	
	List<Order> getOrders(Integer sellerId, Integer buyerId, String dateFrom, String dateTo);
	void deleteFutureOrdersBeforePulished(List<Integer> buyerIds, List<Integer> sellerIds, String deliveryDate);
	
	List<SKUBA> getPublishedSKUBA(List<Integer> orderId, List<Integer> sellerId, List<Integer> buyerId, List<String> deliveryDate, Integer categoryId, Integer isBuyerUser, String hasQty);
	
	Order getLastOrder(Integer sellerId, Integer buyerId);
	Order getLastOrderWithQuantity(Integer sellerId, Integer buyerId);
	List<Order> getFinalizedOrdersFromOrderIdList(List<Integer> orderId);
	public void finalizeOrderBatch(List<Integer> orderIds, Integer finalizedBy) throws Exception;
	
	/**
	 * Bulk publishing of allocation.  
	 * This marks all the orderIds as saved and published by a single user.
	 * 
	 * @param orderIdList
	 * @param savedAndPublishedBy
	 * @throws Exception 
	 */
	void saveAndPublishAllocation(List<Integer> orderIdList, Integer savedAndPublishedBy) throws Exception;
	/**
	 * Bulk unfinalization of orders .  
	 * This marks all the orderIds as unfinalized, deletes all allocation and receive items.
	 * 
	 * @param orderIdList
	 * @param unfinalizedBy
	 * @throws Exception 
	 */
	void unfinalizeOrderBatch(List<Integer> orderIds, Integer unfinalizedBy)
			throws Exception;
	/**
	 * Bulk unpublish of orders .  
	 * This marks all orderIds as unpublished.
	 * 
	 * @param orderIdList
	 * @param unpublishedBy
	 * @throws Exception 
	 */
	void unpublishOrderByBatch(List<Integer> orderIds, Integer unpublishedBy)
			throws Exception;
	/**
	 * Bulk publish of orders .  
	 * This marks all orderIds as published.
	 * 
	 * @param orderIdList
	 * @param publishedBy
	 * @throws Exception 
	 */
	void publishOrderBatch(List<Integer> orderIds, Integer publishedBy)
			throws Exception;
	/**
	 * Bulk saving of Orders.
	 * 
	 * @param orderIds
	 * @param savedBy
	 * @throws Exception
	 */
	void updateSaveOrderBatch(List<Integer> orderIds, Integer savedBy)
			throws Exception;
	void updatePublishOrderBatch(List<Integer> orderIds, Integer publishedBy)
			throws Exception;
	/**
	 * Bulk publish of allocation.
	 * @param orderIds
	 * @param publishBy
	 * @throws ServiceException
	 */
	void publishAllocationByBatch(List<Integer> orderIds, Integer publishBy)
			throws ServiceException;
	/**
	 * Bulk unpublish of allocation.
	 * @param orderIds
	 * @param updatedBy
	 * @throws ServiceException
	 */
	void unpublishAllocationByBatch(List<Integer> orderIds, Integer user)
			throws ServiceException;
	/**
	 * Bulk unfinalize of allocation.
	 * @param orderIds
	 * @param unfinalizedBy
	 * @throws ServiceException
	 */
	void unfinalizeAllocationByBatch(List<Integer> orderIds, Integer unfinalizedBy)
			throws ServiceException;
	
	/**
	 * Bulk update of orders. 
	 * Updates savedby and updatedby fields 
	 * @param userId
	 * @param orderIds
	 * @throws ServiceException
	 */
	void updateOrderByOrderIds(Integer userId, List<Integer> orderIds)
			throws ServiceException;
	/**
	 * Bulk update of allocation. 
	 * Updates allocationsavedby and updatedby fields 
	 * @param allocationSavedBy
	 * @param orderIds
	 * @throws ServiceException
	 */
	void updateSaveAllocationBatch(Integer allocationSavedBy,
			List<Integer> orderIds) throws ServiceException;
	void finalizeAllocationBatch(List<Integer> orderIds, Integer finalizedBy)
			throws ServiceException;
	
	int updateOrderQtyBySkuIdOrderIdAndSkuBaId(BigDecimal quantity,
			Integer userId, Integer skuId, Integer skuBaId, Integer orderId,
			Integer newSkuId, String baosVisFlag);
	
	int updateOrderItemSKUBA(Integer userId, Integer skuId, Integer skuBaId,
			Integer oldSkuBaId, Integer orderId);
	
	int updateOrderItemQtySKUBA2(BigDecimal quantity, Integer userId,
			Integer skuId, Integer skuBaId, Integer orderId, Integer newSkuId,
			String baosVisFlag);

}
