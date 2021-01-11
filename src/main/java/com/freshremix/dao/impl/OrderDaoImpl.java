package com.freshremix.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.OrderDao;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.Order;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUBA;
import com.freshremix.util.CollectionUtil;
import com.ibatis.sqlmap.client.SqlMapClient;


public class OrderDaoImpl extends SqlMapClientDaoSupport implements OrderDao {

	private static final Logger LOGGER = Logger.getLogger(OrderDaoImpl.class);
	
	@Override
	public Integer insertOrder(Order order) {
		LOGGER.debug("Order class contents:"+ order);
		return (Integer)getSqlMapClientTemplate().insert("OrderSheet.insertOrder", order);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrdersByOrderId(List<Integer> orderId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		return getSqlMapClientTemplate().queryForList("OrderSheet.selectOrdersByOrderId", param);
	}

	@Override
	public void finalizeOrder(Integer orderId, Integer finalizedBy, Integer unfinalizedBy) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderFinalizedBy", finalizedBy);
		param.put("orderId", orderId);
		param.put("orderUnfinalizedBy", null);
		getSqlMapClientTemplate().update("OrderSheet.finalizeOrder", param);
	}
	
	@Override
	public void finalizeOrderBatch(List<Integer> orderIds, Integer finalizedBy)
			throws Exception {
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("orderFinalizedBy", finalizedBy);
				param.put("orderUnfinalizedBy", null);

				param.put("orderIds", list);
				getSqlMapClientTemplate().update("OrderSheet.finalizeOrder2", param);
	
			}
			
			
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
	}
	
	@Override
	public void unfinalizeOrderBatch(List<Integer> orderIds,Integer unfinalizedBy)
			throws Exception {
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("orderUnfinalizedBy", unfinalizedBy);
				param.put("orderFinalizedBy", null);

				param.put("orderIds", list);
				getSqlMapClientTemplate().update("OrderSheet.unfinalizeOrder2", param);
	
			}
			
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
	}
	

	@Override
	public void publishOrder(Map<String, Object> param) {
		getSqlMapClientTemplate().update("OrderSheet.publishOrder", param);
	}

	@Override
	public void publishOrderBatch(List<Integer> orderIds,Integer publishedBy)
			throws Exception {
		LOGGER.info("Update Publish Order Batch..... start");
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("orderPublishedBy",publishedBy);

				param.put("orderIds", list);
				getSqlMapClientTemplate().update("OrderSheet.publishOrder2", param);
	
			}
		
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
		LOGGER.info("Update Publish Order Batch..... end");

	}
	@Override
	public void unfinalizeOrder(Integer orderId, Integer finalizedBy, Integer unfinalizedBy) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderUnfinalizedBy", unfinalizedBy);
		param.put("orderFinalizedBy", finalizedBy);
		param.put("orderId", orderId);
		getSqlMapClientTemplate().update("OrderSheet.unfinalizeOrder", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#getOrderByDeliveryDate(java.util.Map)
	 */
	@Override
	public Order getOrderByDeliveryDate(Integer sellerId, Integer buyerId, String deliveryDate) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("buyerId", buyerId);
		param.put("deliveryDate", deliveryDate);
		return (Order) getSqlMapClientTemplate().queryForObject("OrderSheet.selectOrderByDeliveryDate", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#updateSaveOrder(java.util.Map)
	 */
	@Override
	public void updateSaveOrder(Map<String, Object> param) {
		getSqlMapClientTemplate().update("OrderSheet.updateSaveOrder", param);
	}
	
	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#updateSaveOrder(java.util.Map)
	 */
	@Override
	public void updateSaveOrder(Integer orderSavedBy, Integer createdBy, Integer orderId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderSavedBy", orderSavedBy);
		param.put("createdBy", createdBy);
		param.put("orderId", orderId);
		getSqlMapClientTemplate().update("OrderSheet.updateSaveOrder", param);
	}
	
	@Override
	public void updateSaveOrderBatch(List<Integer> orderIds,Integer savedBy)
			throws Exception {
		LOGGER.info("Update Save Order Batch..... start");
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			LOGGER.info("Unpublish Orders By Batch..... start: ");
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("orderSavedBy", savedBy);
				param.put("createdBy", savedBy);
				param.put("orderIds", list);
				getSqlMapClientTemplate().update("OrderSheet.updateSaveOrder2", param);
	
			}
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
		LOGGER.info("Update Save Order Batch..... end");

	}
	
	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#updateSaveOrder(java.util.Map)
	 */
	@Override
	public void updateSaveAllocation(Integer allocationSavedBy, Integer updatedBy, Integer orderId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("allocationSavedBy", allocationSavedBy);
		param.put("updatedBy", updatedBy);
		param.put("orderId", orderId);
		getSqlMapClientTemplate().update("OrderSheet.updateSaveAllocation", param);
	}
	@Override
	public void updateSaveAllocationBatch(Integer allocationSavedBy, List<Integer> orderIds) throws ServiceException {

		
		LOGGER.info("Update Save Allocation Batch..... start");
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			LOGGER.info("Unpublish Orders By Batch..... start: ");
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("allocationSavedBy", allocationSavedBy);
				param.put("updatedBy", allocationSavedBy);
				param.put("orderIds", list);
				getSqlMapClientTemplate().update("OrderSheet.updateSaveAllocation2", param);
	
			}
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
		LOGGER.info("Update Save Allocation Batch..... end");
	}
	
		/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#getPublishedOrders(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getPublishedOrders(List<Integer> buyerIds, List<String> deliveryDates, List<Integer> sellerIds, Integer isBuyer, String enableBAPublish) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("buyerIds", buyerIds);
		param.put("deliveryDates", deliveryDates);
		param.put("sellerIds", sellerIds);
		param.put("isBuyer", isBuyer);
		param.put("enableBAPublish", enableBAPublish);
		return getSqlMapClientTemplate().queryForList("OrderSheet.getPublishedOrders", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#unpublishOrder(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void unpublishOrder(Integer orderId, Integer publishBy, Integer orderUnfinalizedBy) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("publishBy", publishBy);
		param.put("orderUnfinalizedBy", orderUnfinalizedBy);
		getSqlMapClientTemplate().update("OrderSheet.unpublishOrder", param);
	}

	@Override
	public void unpublishOrderByBatch(List<Integer> orderIds,Integer unpublishedBy)
			throws Exception {
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			LOGGER.info("Unpublish Orders By Batch..... start: ");
	
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("publishBy", null);
				param.put("orderUnfinalizedBy", unpublishedBy);
				param.put("orderIds", list);
				getSqlMapClientTemplate().update("OrderSheet.unpublishOrder2", param);
	
			}
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}		

		LOGGER.info("Unpublish Orders By Batch..... end: ");
		
	}
	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#getSelectedOrdersByBuyer(java.util.List, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getSelectedOrdersByBuyer(Integer seller,
			Integer buyer, List<String> dates) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", seller);
		param.put("dates", dates);
		param.put("buyerId", buyer);
		return getSqlMapClientTemplate().queryForList("OrderSheet.getSelectedOrdersByBuyer", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#getSelectedOrdersByDate(java.util.List, java.util.List, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getSelectedOrdersByDate(Integer seller,
			List<Integer> buyer, String selectedDate) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", seller);
		param.put("buyerId", buyer);
		param.put("selectedDate", selectedDate);
		return getSqlMapClientTemplate().queryForList("OrderSheet.getSelectedOrdersByDate", param);
	}
	
	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#finalizeAllocation(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void finalizeAllocation(Integer orderId, Integer finalizedBy,
			Integer unfinalizedBy, Integer updatedBy) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("allocationFinalizeBy", finalizedBy);
		param.put("allocationUnfinalizeBy", unfinalizedBy);
		param.put("updatedBy", updatedBy);
		param.put("orderId", orderId);
		getSqlMapClientTemplate().update("Allocation.finalizeAllocationOrder", param);
	}

	@Override
	public void finalizeAllocationBatch(List<Integer> orderIds, Integer finalizedBy
			) throws ServiceException {
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			LOGGER.info("Finalize Allocation By Batch..... start: ");

			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {

				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("allocationFinalizeBy", finalizedBy);
				param.put("allocationUnfinalizeBy", null);
				param.put("updatedBy", finalizedBy);
				param.put("orderIds", list);
				getSqlMapClientTemplate().update("Allocation.finalizeAllocationOrderBatch", param);

			}
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}		

		LOGGER.info("Finalize Allocation By Batch..... end: ");
	}
	

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#publishAllocation(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void publishAllocation(Integer orderId, Integer updatedBy,
			Integer publishBy) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("allocationPublishBy", publishBy);
		param.put("updatedBy", updatedBy);
		param.put("orderId", orderId);
		getSqlMapClientTemplate().update("Allocation.publishAllocationOrder", param);
	}
	@Override
	public void publishAllocationByBatch(List<Integer> orderIds,
			Integer publishBy) throws ServiceException{
		
		LOGGER.info("Update Publish Allocation Batch..... start");

		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("allocationPublishBy",publishBy);
				param.put("updatedBy", publishBy);

				param.put("orderIds", list);
				getSqlMapClientTemplate().update("Allocation.publishAllocationOrder2", param);
	
			}
			
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
		

		LOGGER.info("Update Publish Allocation Batch..... end");
	}

	@Override
	public void saveAndPublishAllocation(List<Integer> orderIdList,
			Integer savedAndPublishedBy) throws Exception {

		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIdList, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("allocationSavedBy", savedAndPublishedBy);
				param.put("allocationPublishBy", savedAndPublishedBy);
				param.put("updatedBy", savedAndPublishedBy);
				param.put("orderIds", list);
				getSqlMapClientTemplate().update("Allocation.updateSaveAndPublishAllocation2", param);
	
			}
			
			
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#saveAllocation(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void saveAllocation(Integer orderId, Integer updatedBy,
			Integer savedBy) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("allocationSavedBy", savedBy);
		param.put("updatedBy", updatedBy);
		param.put("orderId", orderId);
		getSqlMapClientTemplate().update("Allocation.saveAllocationOrder", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#unfinalizeAllocation(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void unfinalizeAllocation(Integer orderId, Integer finalizedBy,
			Integer unfinalizedBy, Integer updatedBy) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("allocationFinalizeBy", finalizedBy);
		param.put("allocationUnfinalizeBy", unfinalizedBy);
		param.put("updatedBy", updatedBy);
		param.put("orderId", orderId);
		getSqlMapClientTemplate().update("Allocation.unfinalizeAllocationOrder", param);
	}
	@Override
	public void unfinalizeAllocationByBatch(List<Integer> orderIds, 
			Integer unfinalizedBy) throws ServiceException {
		
		
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("allocationFinalizeBy", null);
				param.put("allocationUnfinalizeBy", unfinalizedBy);
				param.put("updatedBy", unfinalizedBy);

				param.put("orderIds", list);
				getSqlMapClientTemplate().update("Allocation.unfinalizeAllocationOrder2", param);
	
			}
			
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#unpublishAllocation(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void unpublishAllocation(Integer orderId, Integer updatedBy,
			Integer publishBy) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("allocationPublishBy", publishBy);
		param.put("updatedBy", updatedBy);
		param.put("orderId", orderId);
		getSqlMapClientTemplate().update("Allocation.unpublishAllocationOrder", param);
	}

	public void unpublishAllocationByBatch(List<Integer> orderIds, Integer user) throws ServiceException {

	
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("allocationPublishBy", null);
				param.put("updatedBy", user);

				param.put("orderIds", list);
				getSqlMapClientTemplate().update("Allocation.unpublishAllocationOrder2", param);
	
			}
			
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
		
		

	
	}
	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#lockOrders(java.util.List, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void lockOrders(List<Integer> orderIds, Integer userId) throws ServiceException {
		
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
		
			LOGGER.info("orderId size:"+ ((orderIds == null)? "null" : orderIds.size()));
	
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String, Object> param = new HashMap<String,Object>();
				param.put("orderLockedBy", userId);
				param.put("orderIds", list);
				param.put("orderUnlockedBy", null);
				getSqlMapClientTemplate().update("OrderSheet.lockUnlockOrders", param);
	
			}
		sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#unlockOrders(java.util.List, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void unlockOrders(List<Integer> orderIds, Integer userId) throws ServiceException {

		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
		

			LOGGER.info("orderId size:"+ ((orderIds == null)? "null" : orderIds.size()));

			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {

				LOGGER.info("Group size:"+list.size());
				Map<String, Object> param = new HashMap<String,Object>();
				param.put("orderLockedBy", null);
				param.put("orderIds", list);
				param.put("orderUnlockedBy", userId);
				getSqlMapClientTemplate().update("OrderSheet.lockUnlockOrders", param);

			}
	
			
		sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}

		
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#updateOrderQtyBySkuIdAndOrderId(java.math.BigDecimal, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateOrderQtyBySkuIdAndOrderId(BigDecimal quantity,
			Integer userId, Integer skuId, Integer orderId, Integer newSkuId, String baosVisFlag) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("quantity", quantity);
		param.put("userId", userId);
		param.put("skuId", skuId);
		param.put("orderId", orderId);
		param.put("newSkuId", newSkuId);
		param.put("baosVisFlag", baosVisFlag);
		getSqlMapClientTemplate().update("OrderSheet.updateOrderQtyBySkuIdAndOrderId", param);
		
	}
	
	@Override
	public int updateOrderQtyBySkuIdOrderIdAndSkuBaId(BigDecimal quantity,
			Integer userId, Integer skuId, Integer skuBaId, Integer orderId, Integer newSkuId, String baosVisFlag) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("quantity", quantity);
		param.put("userId", userId);
		param.put("skuId", skuId);
		param.put("skuBaId", skuBaId);
		param.put("orderId", orderId);
		param.put("newSkuId", newSkuId);
		param.put("baosVisFlag", baosVisFlag);
		
		return getSqlMapClientTemplate().update("OrderSheet.updateOrderQtyBySkuIdOrderIdAndSkuBaId", param);
		
	}
		
	
	public void updateOrderQtyAllDatesBySKUBA (BigDecimal quantity, Integer userId, Integer skuId, Integer skuBaId, Integer orderId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("quantity", quantity);
		param.put("userId", userId);
		param.put("skuId", skuId);
		param.put("orderId", orderId);
		param.put("skuBaId", skuBaId);
		getSqlMapClientTemplate().update("OrderSheet.updateOrderQtybySKUBA", param);
	}
	
	public int updateOrderItemQtySKUBA (BigDecimal quantity, Integer userId, Integer skuId, Integer skuBaId, Integer orderId, Integer newSkuId, String baosVisFlag) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("quantity", quantity);
		param.put("userId", userId);
		param.put("skuId", skuId);
		param.put("orderId", orderId);
		param.put("newSkuId", newSkuId);
		param.put("baosVisFlag", baosVisFlag);
		param.put("skuBaId", skuBaId);
		return getSqlMapClientTemplate().update("OrderSheet.updateOrderItemQtySKUBA", param);
	}

	public int updateOrderItemQtySKUBA2 (BigDecimal quantity, Integer userId, Integer skuId, Integer skuBaId, Integer oldSkuBaId,  Integer orderId, String baosVisFlag) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("quantity", quantity);
		param.put("userId", userId);
		param.put("skuId", skuId);
		param.put("orderId", orderId);
		param.put("baosVisFlag", baosVisFlag);
		param.put("skuBaId", skuBaId);
		param.put("oldSkuBaId", oldSkuBaId);
		return getSqlMapClientTemplate().update("OrderSheet.updateOrderItemQtySKUBA2", param);
	}
	
	public int updateOrderItemSKUBA (Integer userId, Integer skuId, Integer skuBaId, Integer oldSkuBaId, Integer orderId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("skuId", skuId);
		param.put("orderId", orderId);
		param.put("skuBaId", skuBaId);
		param.put("oldSkuBaId", oldSkuBaId);
		
		return getSqlMapClientTemplate().update("OrderSheet.updateOrderItemSKUBA", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#updateOrderByOrderId(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateOrderByOrderId(Integer userId, Integer orderId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("orderId", orderId);
		getSqlMapClientTemplate().update("OrderSheet.updateOrderByOrderId", param);
		
	}
	
	@Override
	public void updateOrderByOrderIds(Integer userId, List<Integer> orderIds) throws ServiceException {

		
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
		

			LOGGER.info("orderId size:"+ ((orderIds == null)? "null" : orderIds.size()));

			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {

				LOGGER.info("Group size:"+list.size());
				Map<String, Object> param = new HashMap<String,Object>();
				param.put("userId", userId);
				param.put("orderIds", list);
				getSqlMapClientTemplate().update("OrderSheet.updateOrderByOrderId2", param);

			}
	
			
		sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}

		
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#getSavedOrders(java.util.List, java.util.List, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getSavedOrders(List<Integer> buyerIds,
			List<String> dateList, List<Integer> sellerIds) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("buyerIds", buyerIds);
		param.put("deliveryDates", dateList);
		param.put("sellerIds", sellerIds);
		return getSqlMapClientTemplate().queryForList("OrderSheet.getSavedOrders", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getAllOrders(List<Integer> buyerIds,
			List<String> dateList, List<Integer> sellerIds) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("buyerIds", buyerIds);
		param.put("deliveryDates", dateList);
		param.put("sellerIds", sellerIds);
		return getSqlMapClientTemplate().queryForList("OrderSheet.getAllOrders", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SKU> getBAPublishedSKUs(List<Integer> orderIds,
			Integer categoryId, Integer sheetTypeId, Integer rowStart,
			Integer pageSize, Integer isBuyer) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("categoryId", categoryId);
		param.put("rowStart", rowStart);
		param.put("rowEnd", rowStart.intValue() + pageSize.intValue());
		param.put("isBuyer", isBuyer);
		return getSqlMapClientTemplate().queryForList("OrderSheet.getBAPublishedSKUs", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#updatePublishOrder(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updatePublishOrder(Integer userId, Integer orderId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderPublishedBy", userId);
		param.put("orderId", orderId);
		getSqlMapClientTemplate().update("OrderSheet.publishOrderByBA", param);
		
	}
	
	@Override
	public void updatePublishOrderBatch(List<Integer> orderIds,Integer publishedBy)
			throws Exception {
		LOGGER.info("Update Publish Order Batch..... start");
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("orderPublishedBy",publishedBy);
				param.put("orderIds", list);
				getSqlMapClientTemplate().update("OrderSheet.publishOrderByBA2", param);
	
			}
			
			
			
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
		LOGGER.info("Update Publish Order Batch..... end");

	}

	@Override
	public Order getPreviousOrder(Integer sellerId, Integer buyerId,
			String deliveryDate) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("buyerId", buyerId);
		param.put("deliveryDate", deliveryDate);
		return (Order) getSqlMapClientTemplate().queryForObject("OrderSheet.selectPreviousOrder", param);
	}

	@Override
	public void updateOrder(Order order) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", order.getOrderId());
		param.put("copiedFromTimeStamp", order.getCopiedFromTimeStamp());
		param.put("copiedFromOrderId", order.getCopiedFromOrderId());
		getSqlMapClientTemplate().update("OrderSheet.updateSaveOrderOnChangePreviousOrder", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrders(Integer sellerId, Integer buyerId,
			String dateFrom, String dateTo) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("buyerId", buyerId);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("OrderSheet.getOrders", param);
	}
	
	@Override
	public void deleteFutureOrdersBeforePulished(List<Integer> buyerIds, List<Integer> sellerIds, String deliveryDate) {
		
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerIds", sellerIds);
		param.put("buyerIds", buyerIds);
		param.put("deliveryDate", deliveryDate);
		
		getSqlMapClientTemplate().update("OrderSheet.deleteFutureOrderItemsBeforePulished", param);
		getSqlMapClientTemplate().update("OrderSheet.deleteFutureOrdersBeforePulished", param);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SKUBA> getPublishedSKUBA(
			List<Integer> orderIds, 
			List<Integer> sellerId,
			List<Integer> buyerId,
			List<String> deliveryDate,
			Integer categoryId, 
			Integer isUserBuyer, 
			String hasQty) {
		
		Map<String, Object> param = new HashMap<String,Object>();

//		if (orderIds.size() > 1000) {
//			param.put("sellerId", sellerId);
//			param.put("buyerId", buyerId);
//			param.put("deliveryDate", deliveryDate);
//			param.put("categoryId", categoryId);
//			param.put("hasQty", hasQty);
//			return getSqlMapClientTemplate().queryForList("OrderSheet.getPublishedSKUBAByOrderParams", param);
//		}
//		else {
			// param.put("orderId", orderIds);
			param.put("sellerId", sellerId);
			param.put("buyerId", buyerId);
			param.put("deliveryDate", deliveryDate);
			param.put("categoryId", categoryId);
			param.put("isUserBuyer", isUserBuyer);
			param.put("hasQty", hasQty);
			return getSqlMapClientTemplate().queryForList("OrderSheet.getPublishedSKUBAs", param);
//		}
	}

	@Override
	public Order getLastOrder(Integer sellerId, Integer buyerId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("buyerId", buyerId);
		
		return (Order)getSqlMapClientTemplate().queryForObject("OrderSheet.getLastOrder", param);
	}

	@Override
	public Order getLastOrderWithQuantity(Integer sellerId, Integer buyerId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("buyerId", buyerId);
		
		return (Order)getSqlMapClientTemplate().queryForObject("OrderSheet.getLastSavedOrderWithQuantity", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getFinalizedOrdersFromOrderIdList(List<Integer> orderId) {
		LOGGER.info("orderId size:"+ ((orderId == null)? "null" : orderId.size()));

		List<List<?>> splitList = CollectionUtil.splitList(orderId, 1000);
		
		LOGGER.info("Number of groups:"+splitList.size());
		List<Order> returnList = new ArrayList<Order>();
		for (List<?> list : splitList) {

			LOGGER.info("Group size:"+list.size());
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("orderId", list);
			List<Order> queryResutList = getSqlMapClientTemplate().queryForList("OrderSheet.selectFinalizedOrdersFromOrderIdList", param);
			if (CollectionUtils.isNotEmpty(queryResutList)) {
				returnList.addAll(queryResutList);
				LOGGER.info("Result size:"+queryResutList.size());
			}
		}
		return returnList;
	}
 
}