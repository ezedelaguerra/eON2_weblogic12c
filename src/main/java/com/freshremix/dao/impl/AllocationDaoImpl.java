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
package com.freshremix.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.AllocationDao;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.SKU;
import com.freshremix.ui.model.ProfitInfo;
import com.freshremix.util.CollectionUtil;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gilwen
 *
 */
public class AllocationDaoImpl extends SqlMapClientDaoSupport implements AllocationDao {

	private static final Logger LOGGER = Logger.getLogger(AllocationDaoImpl.class);
	
	/* (non-Javadoc)
	 * @see com.freshremix.dao.AllocationDao#deleteAllocItems(java.util.List)
	 */
	@Override
	public void deleteAllocItems(Integer orderId) {
		getSqlMapClientTemplate().delete("Allocation.deleteAllocItems", orderId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.AllocationDao#insertDefaultAllocItems(java.util.List)
	 */
	@Override
	public void insertDefaultAllocItems(OrderItem orderItem) {
		getSqlMapClientTemplate().insert("Allocation.insertDefaultAllocItems", orderItem);
	}
	
	@Override
	public void insertDefaultAllocItemsBatch(List<Integer> orderIds, Integer createdBy) throws Exception {
		LOGGER.info("insertDefaultAllocItemsBatch.... start");
		try {
			SqlMapClient sqlMapClient = this.getSqlMapClient();
			sqlMapClient.startBatch();
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("createdBy", createdBy);

				param.put("orderIds", list);
				getSqlMapClientTemplate().update("Allocation.insertDefaultAllocItemsBatch", param);
	
			}
			
			
			sqlMapClient.executeBatch();
		} catch (Exception e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);

		}
		LOGGER.info("insertDefaultAllocItemsBatch.... end");

	}
	
	@Override
	public void deleteAllocItemsBatch(List<Integer> orderIds) throws Exception {

		try {
			SqlMapClient sqlMapClient = this.getSqlMapClient();
			sqlMapClient.startBatch();
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();

				param.put("orderIds", list);
				getSqlMapClientTemplate().update("Allocation.deleteAllocItems2", param);
	
			}
			
		
			sqlMapClient.executeBatch();
		} catch (Exception e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);

		}
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.AllocationDao#getAllocItems(java.lang.String, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getAllocItems(String selectedDate,
			Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDate", selectedDate);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("Allocation.getAllocItemsMapByDate", param, "order.buyerId");
	}
	
	/* (non-Javadoc)
	 * @see com.freshremix.dao.AllocationDao#insertAllocItems(java.lang.Integer, java.lang.Integer, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public void insertAllocItem(Integer orderId, Integer skuId, BigDecimal quantity, BigDecimal skuMaxLimit) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("quantity", quantity);
		param.put("skuMaxLimit", skuMaxLimit);
		
		// Add logging for WS calls
		StringBuffer sb = new StringBuffer();
		sb.append("AllocationDaoImpl.insertAllocItem -- ");
		sb.append(" Order Id: ").append(": ").append(orderId);
		sb.append(" SKU Id: ").append(": ").append(skuId);
		sb.append(" Quantity: ").append(": ").append(quantity);
		sb.append(" SKU Max Limit: ").append(": ").append(skuMaxLimit);
		LOGGER.info(sb.toString());
		
		getSqlMapClientTemplate().insert("Allocation.insertAllocItem", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.AllocationDao#updateAllocItem(java.lang.Integer, java.lang.Integer, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public void updateAllocItem(Integer orderId, Integer skuId, Integer skuBaId, BigDecimal quantity, String isApproved) {
 		Map<String, Object> param = new HashMap<String,Object>();
 		param.put("orderId", orderId);
 		param.put("skuId", skuId);
 		param.put("skuBaId", skuBaId);
 		param.put("quantity", quantity);
		param.put("isApproved", isApproved);
 		getSqlMapClientTemplate().update("Allocation.updateAllocItem", param);
 	}

	// ENHANCEMENT START 20121001: Rhoda Redmine 1089
	@Override
	public void updateAllocItemFromReceived(Integer orderId, Integer skuId,
			Integer skuBaId, String isApproved) {
		Map<String, Object> param = new HashMap<String,Object>();
 		param.put("orderId", orderId);
 		param.put("skuId", skuId);
 		param.put("skuBaId", skuBaId);
		param.put("isApproved", isApproved);
 		getSqlMapClientTemplate().update("Allocation.updateAllocItemFromReceived", param);
	}
	// ENHANCEMENT END 20121001: Rhoda Redmine 1089
	
	/* (non-Javadoc)
	 * @see com.freshremix.dao.AllocationDao#getAllocItemsByOrderId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItem> getAllocItemsByOrderId(Integer orderId) {
		return getSqlMapClientTemplate().queryForList("Allocation.getAllocItemsByOrderId", orderId);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getFinalizedOrdersForAlloc(List<Integer> buyerIds,
			List<String> deliveryDates, List<Integer> sellerIds) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("buyerIds", buyerIds);
		param.put("deliveryDates", deliveryDates);
		param.put("sellerIds", sellerIds);
		return getSqlMapClientTemplate().queryForList("Allocation.getFinalizedOrdersForAlloc", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<SKU> selectDistinctSKUs(
			List<Integer> selectedOrders, 
			List<Integer> sellerId,
			List<Integer> buyerId,
			List<String> deliveryDate,
			Integer categoryId, 
			String hasQty) {
		
		
//		List<List<?>> splitList = CollectionUtil.splitList(selectedOrders, 1000);
//		List<SKU> resultList = new ArrayList<SKU>();
//		
//		LOGGER.info("Number of groups:"+splitList.size());
//		for (List<?> list : splitList) {
//			Map<String, Object> param = new HashMap<String,Object>();
//			param.put("orderIds", list);
//			param.put("categoryId", categoryId);
//			param.put("hasQty", hasQty);
//			List<SKU> queryForList = getSqlMapClientTemplate().queryForList("Allocation.selectDistinctSKUs2", param);
//			if (CollectionUtils.isNotEmpty(queryForList)) {
//				resultList.addAll(queryForList);
//			}
//		}
//		return resultList;
		
		Map<String, Object> param = new HashMap<String,Object>();

		if (selectedOrders.size() > 1000) {
			param.put("sellerId", sellerId);
			param.put("buyerId", buyerId);
			param.put("deliveryDate", deliveryDate);
			param.put("categoryId", categoryId);
			param.put("hasQty", hasQty);
			return getSqlMapClientTemplate().queryForList("Allocation.selectDistinctSKUs3", param);
		}
		else {
			param.put("orderIds", selectedOrders);
			param.put("categoryId", categoryId);
			param.put("hasQty", hasQty);
			return getSqlMapClientTemplate().queryForList("Allocation.selectDistinctSKUs2", param);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getSumAllocationMapOfSkuBuyers(
			List<Integer> orderIds, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("Allocation.getSumOrderAllocBuyerMap", param, "order.buyerId");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getSumAllocationMapOfSkuDates(
			List<Integer> orderIds, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("Allocation.getSumOrderAllocDateMap", param, "order.deliveryDate");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getOrderItemsMapOfSkuDate(
			Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForMap("Allocation.getOrderItemsMapOfSkuDate", param, "order.buyerId");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getOrderItemsMapOfSkuDates(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForMap("Allocation.getOrderItemsMapOfSkuDates", param, "order.deliveryDate");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getAllOrders(List<Integer> buyerIds,
			List<String> dateList, List<Integer> sellerIds) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("buyerIds", buyerIds);
		param.put("deliveryDates", dateList);
		param.put("sellerIds", sellerIds);
		return getSqlMapClientTemplate().queryForList("Allocation.getAllOrders", param);
	}

	@Override
	public void deleteAllocationItemsByOrderIdAndSkuId(Map<String, Object> param) {
		getSqlMapClientTemplate().delete("Allocation.deleteAllocationItemsByOrderIdAndSkuId", param);
	}

	@Override
	public void deleteAllocItemsByOrderIdsAndSkuIds(List<Integer> orderIdList, List<Integer> skuToDelete) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderIds", orderIdList);
		param.put("skuIds", skuToDelete);
		getSqlMapClientTemplate().delete("Allocation.deleteAllocItemsByOrderIdsAndSkuIds", param);
	}

	@Override
	public OrderItem getAllocationItem(Map<String, Object> param) {
		return (OrderItem) getSqlMapClientTemplate().queryForObject("Allocation.getAllocationItem", param);
	}
	
	@Override
	public int updateSpecialAllocItem(Integer orderId, Integer skuId, Integer origSkuId, BigDecimal quantity) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("origSkuId", origSkuId);
		param.put("quantity", quantity);
		return getSqlMapClientTemplate().update("Allocation.updateSpecialAllocItem", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SKU> selectDistinctSKUs(List<Integer> allOrderId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", allOrderId);
		return getSqlMapClientTemplate().queryForList("Allocation.wsSelectDistinctSKUs2", param);
	}
	
	@Override
	public GrandTotalPrices getGTPriceAllOrders(List<Integer> orderIds) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		return (GrandTotalPrices) getSqlMapClientTemplate().queryForObject("Allocation.getGTPriceAllOrders", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
		
	public List<OrderItem> getSellerAllocItemsBulk(List<Integer> orderIds, List<Integer> skuIds, boolean hasQty) {
		
		List<OrderItem> resultList = new ArrayList<OrderItem>();
		List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
		
		LOGGER.info("Number of groups:"+splitList.size());
		for (List<?> list : splitList) {
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("orderIds", list);
			param.put("skuIds", skuIds);
			if(hasQty){
				param.put("hasQty", hasQty);
			}
			List<OrderItem> queryForList = getSqlMapClientTemplate().queryForList("Allocation.getSellerAllocItemsBulk", param);
			if (CollectionUtils.isNotEmpty(queryForList)) {
				resultList.addAll(queryForList);
			}
		}
		return resultList;
		
//		List<OrderItem> resultList = new ArrayList<OrderItem>();
//		
//		int orderCount = orderIds.size();
//		int loopCount = (orderCount / 1000) + 1;
//		/* SQL WHERE column IN (?,?,?...) IS LIMITED TO 1000 */
//		for (int i = 0; i < loopCount; i++) {
//			
//			int startIdx = i * 1000;
//			List<Integer> thisOrderIds = new ArrayList<Integer>();
//			for (int j = startIdx; j < startIdx + 1000; j++) {
//				if (orderCount > j)
//					thisOrderIds.add(orderIds.get(j));
//			}
//			
//			Map<String, Object> param = new HashMap<String,Object>();
//			param.put("orderIds", thisOrderIds);
//			param.put("skuIds", skuIds);
//			if(hasQty){
//				param.put("hasQty", hasQty);
//			}
//			list.addAll(getSqlMapClientTemplate().queryForList("Allocation.getSellerAllocItemsBulk", param));
//			
//		}
//		
//		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItem> getOrderItem(Integer sellerId, String deliveryDate,
			Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("deliveryDate", deliveryDate);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForList("Allocation.getOrderItemBySellerIdSkuIdDate", param);
	}
	
	@Override
	public ProfitInfo getProfitInfo(String deliveryDate,
			List<Integer> buyerId, List<Integer> sellerId, Integer categoryId, Double tax) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("buyerId", buyerId);
		param.put("deliveryDate", deliveryDate);
		param.put("categoryId", categoryId);
		param.put("tax", tax);
		
		return (ProfitInfo) getSqlMapClientTemplate().queryForObject("Allocation.getTotalPrice", param);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<OrderItem> getOrderItemsFromListOfSKUAndDate(List<Integer> skuIds, List<String>dates) {
		
		List<OrderItem> returnList = new ArrayList<OrderItem>();
		
		if (CollectionUtils.isEmpty(skuIds) || CollectionUtils.isEmpty(dates)) {
			return returnList;
		}
		
		List<List<?>> splitDates = CollectionUtil.splitList(dates, 1000);
		List<List<?>> splitSKUIds = CollectionUtil.splitList(skuIds, 1000);

		List<OrderItem> tempReturnList = new ArrayList<OrderItem>();
		for (List<?> listOfDates : splitDates) {
			for (List<?> listOfSKUIds : splitSKUIds) {
				Map<String, Object> param = new HashMap<String,Object>();
				param.put("deliveryDates", listOfDates);
				param.put("skuIds", listOfSKUIds);
				List<OrderItem> queryResult = (List<OrderItem>) getSqlMapClientTemplate()
						.queryForList(
								"Allocation.getOrderItemsFromListOfSKUAndDate",
								param);
				if (CollectionUtils.isNotEmpty(queryResult)) {
					tempReturnList.addAll(queryResult);
				}
			}
		}
		
		//get distinct
		List<Integer> tempKeyList = new ArrayList<Integer>();
		for (OrderItem tempResult : tempReturnList) {
			Integer orderItemId = tempResult.getOrderItemId();
			if (tempKeyList.contains(orderItemId)) {
				continue;
			}
			tempKeyList.add(orderItemId);
			returnList.add(tempResult);
		}
		return returnList;
	}
	
}
