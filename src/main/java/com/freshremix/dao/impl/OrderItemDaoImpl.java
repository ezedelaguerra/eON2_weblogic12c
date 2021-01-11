package com.freshremix.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.OrderItemDao;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.OrderItem;
import com.freshremix.util.CollectionUtil;
import com.ibatis.sqlmap.client.SqlMapClient;

public class OrderItemDaoImpl extends SqlMapClientDaoSupport implements
		OrderItemDao {

	private static final Logger LOGGER = Logger.getLogger(OrderItemDaoImpl.class);

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderItemDao#insertOrderItem(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer insertOrderItem(Map param) {
		return (Integer) getSqlMapClientTemplate().insert("OrderSheet.insertOrderItem", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderItemDao#deleteOrderItem(java.util.Map)
	 */
	@Override
	public void deleteOrderItem(Map<String, Object> param) {
		getSqlMapClientTemplate().delete("OrderSheet.deleteOrderItems", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderItemDao#getPreviousSKUCount(java.util.Map)
	 */
	@Override
	public Integer getSKUCountWithSavedOrder(Integer skuId, String deliveryDate, List<Integer> selectedBuyerIds) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("skuId", skuId);
		param.put("deliveryDate", deliveryDate);
		param.put("selectedBuyerIds", selectedBuyerIds);
		return (Integer)getSqlMapClientTemplate().queryForObject("OrderSheet.getSKUCountWithSavedOrder", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderItemDao#deleteAllOrderItemsByOrderId(java.util.Map)
	 */
	@Override
	public void deleteAllOrderItemsByOrderId(Integer orderId) {
		getSqlMapClientTemplate().delete("OrderSheet.deleteAllOrderItemsByOrderId", orderId);
	}

	@Override
	public void updateOrderItem(Map<String, Object> param) {
		getSqlMapClientTemplate().update("OrderSheet.updateOrderItem", param);
	}

	@Override
	public OrderItem getOrderItem(Map<String, Object> param) {
		return (OrderItem) getSqlMapClientTemplate().queryForObject("OrderSheet.getOrderItem", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderItemDao#getOrderItemByOrderId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItem> getOrderItemByOrderId(Integer orderId) {
		return getSqlMapClientTemplate().queryForList("OrderSheet.getOrderItemsByOrderId",orderId);
	}

	@Override
	public void insertBulkOrderItem(Map<String,Object> param) {
		getSqlMapClientTemplate().insert("OrderSheet.insertBulkOrderItem", param);
	}

	@Override
	public void updateOrderItem(Integer orderId, Integer skuId,
			Integer origSkuId, BigDecimal quantity, String sosVisFlag, boolean webServiceFlg, boolean updateNewSKU, boolean isMetaInfoChanged) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("origSkuId", origSkuId);
		param.put("quantity", quantity);
		param.put("sosVisFlag", sosVisFlag);
		if (updateNewSKU) {
			param.put("updateNewSKU", updateNewSKU);
		}
		
		if (isMetaInfoChanged) {
			// reset visibility to visible
			param.put("baosVisFlag", 1);
		}

		int updated = getSqlMapClientTemplate().update("OrderSheet.updateOrderItem", param);
		
		// if no order item to update 
		// need to insert an order item
		if(webServiceFlg && updated == 0){
			getSqlMapClientTemplate().insert("OrderSheet.insertOrderItem", param);
		}
	}

	@Override
	public void updateOrderItemByBatch(List<OrderItem> oitems, boolean webServiceFlg, boolean updateNewSKU, boolean isMetaInfoChanged) throws ServiceException {
		LOGGER.info("Update Order Item by Batch..... start");
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			
			
			List<List<?>> splitList = CollectionUtil.splitList(oitems, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
				for (OrderItem oitem : (List<OrderItem>)list) {
					updateOrderItem(oitem.getOrderId(), oitem.getSKUId(), oitem.getSKUId(), oitem.getQuantity(), oitem.getSosVisFlag(), webServiceFlg,updateNewSKU,isMetaInfoChanged);
				}
	
			}
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
		LOGGER.info("Update Order Item by Batch..... end");
		
	}
	
	@Override
	public void deleteOrderItemByBatch(List<Integer> oitemIds) throws ServiceException {
		LOGGER.info("Update Order Item by Batch..... start");
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			List<List<?>> splitList = CollectionUtil.splitList(oitemIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("orderitems", list);

				getSqlMapClientTemplate().delete("OrderSheet.deleteOrderItemsByOrderItemId", param);

	
			}
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
		LOGGER.info("Update Order Item by Batch..... end");
		
	}
	
	@Override
	public Integer insertOrderItem(Integer orderId, Integer skuId,
			Integer origSkuId, BigDecimal quantity, String sosVisFlag) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("origSkuId", origSkuId);
		param.put("quantity", quantity);
		param.put("sosVisFlag", sosVisFlag);
		return (Integer) getSqlMapClientTemplate().insert("OrderSheet.insertOrderItem", param);
	}

	@Override
	public void insertOrderItemByBatch(List<OrderItem> oitems) throws ServiceException {
		
		LOGGER.info("Insert Order Item by Batch..... start");
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			
			
			List<List<?>> splitList = CollectionUtil.splitList(oitems, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
				for (OrderItem oitem : (List<OrderItem>)list) {
				
					Map<String, Object> param = new HashMap<String,Object>();
					param.put("orderId", oitem.getOrder().getOrderId());
					param.put("skuId", oitem.getSKUId());
					//param.put("origSkuId", oitem.getSku().getOrigSkuId());
					param.put("quantity", oitem.getQuantity());
					param.put("sosVisFlag", oitem.getSosVisFlag());
					Integer oiId = (Integer) getSqlMapClientTemplate().insert("OrderSheet.insertOrderItem", param);
					oitem.setOrderItemId(oiId);
				}
	
			}
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
		LOGGER.info("Insert Order Item by Batch..... end");
		
		
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItem> getOrderItem(Integer sellerId, String deliveryDate,
			Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("deliveryDate", deliveryDate);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForList("OrderSheet.getOrderItemBySellerIdSkuIdDate", param);
	}

	@Override
	public BigDecimal getTotalQuantityBySkuId(Integer skuId, List<String> deliveryDate) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("skuId", skuId);
		param.put("deliveryDate", deliveryDate);
		return (BigDecimal)getSqlMapClientTemplate().queryForObject("OrderSheet.getTotalQuantityBySkuId", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, BigDecimal>getTotalQuantityBySkuIdListAndDate(List<Integer> skuIdList, List<String> deliveryDates) {
		Map<Integer, BigDecimal> returnMap = new HashMap<Integer, BigDecimal>();
		if (CollectionUtils.isEmpty(skuIdList)
				|| CollectionUtils.isEmpty(deliveryDates)) {
			return returnMap;
		}
		
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("skuIdList", skuIdList);
		param.put("deliveryDates", deliveryDates);
		List<HashMap<String, Number>> resultList = (List<HashMap<String, Number>>) getSqlMapClientTemplate()
				.queryForList("OrderSheet.getTotalQuantityBySkuIdList", param);

		if (CollectionUtils.isNotEmpty(resultList)) {
			for (HashMap<String, Number> queryResultMap : resultList) {
				returnMap.put((Integer)queryResultMap.get("SKU_ID"), (BigDecimal)queryResultMap.get("SKU_QTY"));
			}
		}
		return returnMap;
	}
	
	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderItemDao#updateOrderItemSkuId(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateOrderItemSkuId(Integer orderId, Integer skuId,
			Integer origSkuId, boolean updateNewSKU) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("origSkuId", origSkuId);
		param.put("updateNewSKU", updateNewSKU);
		getSqlMapClientTemplate().update("OrderSheet.updateOrderItemSkuId", param);
		
	}
	
	@Override
	public void deleteOrderItemNoQuantity(Map<String, Object> param) {
		getSqlMapClientTemplate().delete("OrderSheet.deleteOrderItemsNoQuantity", param);
	}
	
	@Override
	public void deleteOrderItemNoQuantityBatch(List<Integer> ordersToDelete, List<Integer> skuToDelete) throws ServiceException {
		
		
		LOGGER.info("Delete orders by Batch..... start");
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			
			
			List<List<?>> splitList = CollectionUtil.splitList(ordersToDelete, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("skuId", skuToDelete.toArray());
				param.put("orderIds", list);
				getSqlMapClientTemplate().update("OrderSheet.deleteOrderItemsNoQuantity2", param);
	
			}
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
		LOGGER.info("Delete orders by Batch..... end");
		
	}

	
	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderItemDao#insertBulkOrderItemNewBuyer(java.util.Map)
	 */
	@Override
	public void insertBulkOrderItemNewBuyer(Integer orderId, Integer sellerId, Integer newBuyerId, String deliveryDate, List<Integer> selectedBuyerIds) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		param.put("sellerId", sellerId);
		param.put("newBuyerId", newBuyerId);
		param.put("selectedBuyerIds", selectedBuyerIds);
		param.put("deliveryDate", deliveryDate);
		getSqlMapClientTemplate().insert("OrderSheet.insertBulkOrderItemNewBuyer", param);	
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderItemDao#deleteOrderItem(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void deleteOrderItem(Integer orderId, Integer skuId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		getSqlMapClientTemplate().delete("OrderSheet.deleteOrderItem", param);		
	}
	
	@Override
	public void deleteAllOrderItemsByOrderIds(List<Integer> orderIdList) throws ServiceException {
		
		LOGGER.info("Delete orders by Batch..... start");
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIdList, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("orderIds", list);
				getSqlMapClientTemplate().delete("OrderSheet.deleteAllOrderItemsByOrderIds", param);
	
			}
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
		LOGGER.info("Delete orders by Batch..... end");
		
	}
}
