package com.freshremix.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.freshremix.exception.ServiceException;
import com.freshremix.model.OrderItem;

public interface OrderItemDao {

	@SuppressWarnings("unchecked")
	Integer insertOrderItem(Map param);
	void deleteOrderItem(Map<String,Object> param);
	void deleteOrderItem(Integer orderId, Integer skuId);
	void deleteAllOrderItemsByOrderId(Integer orderId);
	Integer getSKUCountWithSavedOrder(Integer skuId, String deliveryDate, List<Integer> selectedBuyerIds);
	void updateOrderItem(Map<String, Object> param);
	OrderItem getOrderItem(Map<String, Object> param);
	List<OrderItem> getOrderItemByOrderId (Integer orderId);
	
	/*used for performance issues*/
	void insertBulkOrderItem(Map<String,Object> param);
	
	void updateOrderItem(Integer orderId, Integer skuId, Integer origSkuId, BigDecimal quantity, String sosVisFlag, boolean webServiceFlg, boolean updateNewSKU, boolean isMetaInfoChanged);

	void updateOrderItemSkuId(Integer orderId, Integer skuId, Integer origSkuId, boolean updateNewSKU);
	
	Integer insertOrderItem(Integer orderId, Integer skuId, Integer origSkuId, BigDecimal quantity, String sosVisFlag);
	
	List<OrderItem> getOrderItem(Integer sellerId, String deliveryDate, Integer skuId);
	
	BigDecimal getTotalQuantityBySkuId(Integer skuId, List<String> deliveryDate);
	
	void deleteOrderItemNoQuantity(Map<String,Object> param);
	
	void insertBulkOrderItemNewBuyer(Integer orderId, Integer sellerId, Integer newBuyerId, String deliveryDate, List<Integer> selectedBuyerIds);
	
	/**
	 * Gets the total quantity of ordered sku per date.
	 * @param skuIdList
	 * @param deliveryDates
	 * @return  A map containint SKU ID and Total Quantity for a given date
	 */
	Map<Integer, BigDecimal> getTotalQuantityBySkuIdListAndDate(
			List<Integer> skuIdList, List<String> deliveryDates);

	/**
	 * Bulk delete of orderItems with no quantities
	 * 
	 * @param ordersToDelete
	 * @param skuToDelete
	 * @throws ServiceException
	 */
	void deleteOrderItemNoQuantityBatch(List<Integer> ordersToDelete,
			List<Integer> skuToDelete) throws ServiceException;
	void insertOrderItemByBatch(List<OrderItem> oitems) throws ServiceException;
	void updateOrderItemByBatch(List<OrderItem> oitems, boolean webServiceFlg,
			boolean updateNewSKU, boolean isMetaInfoChanged) throws ServiceException;
	void deleteOrderItemByBatch(List<Integer> oitemIds) throws ServiceException;
	void deleteAllOrderItemsByOrderIds(List<Integer> orderIdList)throws ServiceException;
}
