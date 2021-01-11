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

import com.freshremix.dao.OrderBillingDao;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.BillingItem;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;
import com.freshremix.util.CollectionUtil;
import com.ibatis.sqlmap.client.SqlMapClient;

public class OrderBillingDaoImpl extends SqlMapClientDaoSupport implements OrderBillingDao {
	private static final Logger LOGGER = Logger.getLogger(OrderBillingDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getBillingOrders(List<Integer> sellerIds, List<Integer> buyerIds, List<String> deliveryDates, Integer isBuyer) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("sellerIds", sellerIds);
		param.put("buyerIds", buyerIds);
		param.put("deliveryDates", deliveryDates);
		param.put("isBuyer", isBuyer);
		return getSqlMapClientTemplate().queryForList("BillingSheet.getBillingOrders", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#getSelectedOrdersByDate(java.util.List, java.util.List, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getSelectedOrdersByDate(List<Integer> sellerIds,
			List<Integer> buyerIds, String selectedDate) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerIds", sellerIds);
		param.put("buyerIds", buyerIds);
		param.put("selectedDate", selectedDate);
		return getSqlMapClientTemplate().queryForList("BillingSheet.getSelectedOrdersByDate", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#getSelectedOrdersByBuyer(java.util.List, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getSelectedOrdersByBuyer(List<Integer> sellerIds,
			Integer buyerId, List<String> dates) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerIds", sellerIds);
		param.put("dates", dates);
		param.put("buyerId", buyerId);
		return getSqlMapClientTemplate().queryForList("BillingSheet.getSelectedOrdersByBuyer", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#selectDistinctSKUs(java.util.List, java.lang.Integer, java.lang.Integer, java.util.List, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AkadenSKU> selectDistinctSKUs(List<Integer> orderIds,
			Integer categoryId, Integer sheetTypeId, 
			Integer rowStart, Integer pageSize) {
		
		List<AkadenSKU> list = new ArrayList<AkadenSKU>();
		
		int orderCount = orderIds.size();
		int loopCount = (orderCount / 1000) + 1;
		/* SQL WHERE column IN (?,?,?...) IS LIMITED TO 1000 */
		for (int i = 0; i < loopCount; i++) {
			
			int startIdx = i * 1000;
			List<Integer> thisOrderIds = new ArrayList<Integer>();
			for (int j = startIdx; j < startIdx + 1000; j++) {
				if (orderCount > j)
					thisOrderIds.add(orderIds.get(j));
			}
			
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("orderCnt", thisOrderIds.size());
			param.put("orderIds", thisOrderIds);
			param.put("categoryId", categoryId);
			param.put("sheetTypeId", sheetTypeId);
			param.put("rowStart", rowStart);
			param.put("rowEnd", rowStart.intValue() + pageSize.intValue());
			list.addAll(getSqlMapClientTemplate().queryForList("BillingSheet.selectDistinctSKUs", param));
		}
		
		return list;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#getOrderItemsMapOfSkuDate(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, BillingItem> getBillingItemsByBuyers( List<Integer> buyerIds,
			String deliveryDate, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDate", deliveryDate);
		param.put("skuId", skuId);
		param.put("buyerIds", buyerIds);
		return getSqlMapClientTemplate().queryForMap("BillingSheet.getBillingItemsByBuyers", param, "order.buyerId");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#getOrderItemsMapOfSkuDates(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, BillingItem> getBillingItemsByDates(
			List<String> deliveryDates, Integer skuId, Integer buyerId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDates", deliveryDates);
		param.put("skuId", skuId);
		param.put("buyerId", buyerId);
		return getSqlMapClientTemplate().queryForMap("BillingSheet.getBillingItemsByDates", param, "order.deliveryDate");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#getAkadenOrigQtyBySkuDate(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, BillingItem> getAkadenOrigQtyBySkuDate(
			String deliveryDate, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDate", deliveryDate);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("BillingSheet.getAkadenOrigQtyBySkuDate", param, "buyerId");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#getAkadenOrigQtyBySkuDates(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, BillingItem> getAkadenOrigQtyBySkuDates(
			List<String> deliveryDates, Integer skuId, Integer buyerId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDates", deliveryDates);
		param.put("skuId", skuId);
		param.put("buyerId", buyerId);
		return getSqlMapClientTemplate().queryForMap("BillingSheet.getAkadenOrigQtyBySkuDates", param, "deliveryDate");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#getAkadenNegQtyBySkuDate(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, BillingItem> getAkadenNegQtyBySkuDate(
			String deliveryDate, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDate", deliveryDate);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("BillingSheet.getAkadenNegQtyBySkuDate", param, "buyerId");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#getAkadenNegQtyBySkuDates(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, BillingItem> getAkadenNegQtyBySkuDates(
			List<String> deliveryDates, Integer skuId, Integer buyerId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDates", deliveryDates);
		param.put("skuId", skuId);
		param.put("buyerId", buyerId);
		return getSqlMapClientTemplate().queryForMap("BillingSheet.getAkadenNegQtyBySkuDates", param, "deliveryDate");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#getAkadenCorrectedQtyBySkuDate(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, BillingItem> getAkadenCorrectedQtyBySkuDate(
			String deliveryDate, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDate", deliveryDate);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("BillingSheet.getAkadenCorrectedQtyBySkuDate", param, "buyerId");
	}
	
	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#getAkadenCorrectedtyBySkuDates(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, BillingItem> getAkadenCorrectedQtyBySkuDates(
			List<String> deliveryDates, Integer skuId, Integer buyerId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDates", deliveryDates);
		param.put("skuId", skuId);
		param.put("buyerId", buyerId);
		return getSqlMapClientTemplate().queryForMap("BillingSheet.getAkadenCorrectedQtyBySkuDates", param, "deliveryDate");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#getOrderByDeliveryDate(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Order getOrderByDeliveryDate(Integer sellerId, Integer buyerId, String deliveryDate) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("buyerId", buyerId);
		param.put("deliveryDate", deliveryDate);
		return (Order) getSqlMapClientTemplate().queryForObject("OrderSheet.selectOrderByDeliveryDate", param);
	}
	
	
	@Override
	public Integer insertOrder(BillingItem billingItem, Integer userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", billingItem.getOrder().getOrderId());
		param.put("skuId", billingItem.getSku().getSkuId());
		param.put("userId", userId);
		param.put("quantity", billingItem.getQuantity());
		return (Integer)getSqlMapClientTemplate().insert("BillingSheet.insertOrder", param);
	}	

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderDao#updateSaveOrder(java.util.Map)
	 */
	@Override
	public void updateSaveOrder(BillingItem billingItem, Integer userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", billingItem.getOrder().getOrderId());
		param.put("skuId", billingItem.getSku().getSkuId());
		param.put("userId", userId);
		param.put("quantity", billingItem.getQuantity());
		getSqlMapClientTemplate().update("BillingSheet.updateSaveOrder", param);
	}

	@Override
	public void finalizeBilling(Integer orderId, Integer finalizedBy) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderFinalizedBy", finalizedBy);
		param.put("orderId", orderId);
		param.put("orderUnfinalizedBy", null);
		getSqlMapClientTemplate().update("BillingSheet.finalizeBilling", param);
	}

	@Override
	public void unfinalizeBilling(Integer orderId, Integer unfinalizedBy) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderUnfinalizedBy", unfinalizedBy);
		param.put("orderFinalizedBy", null);
		param.put("orderId", orderId);
		getSqlMapClientTemplate().update("BillingSheet.unFinalizeBilling", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#insertDefaultBillingItems(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.math.BigDecimal)
	 */
	@Override
	public void insertDefaultBillingItems(Integer orderId, Integer skuId, Integer skuBaId,
			Integer userId, BigDecimal quantity) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("userId", userId);
		param.put("quantity", quantity);
		param.put("skuBaId", skuBaId);
		getSqlMapClientTemplate().update("BillingSheet.insertBillingItem", param);
	}
	
	@Override
	public void insertDefaultBillingItemsBatch(List<Integer> orderIds, Integer userId) throws ServiceException {
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());

				Map<String, Object> param = new HashMap<String,Object>();
				param.put("orderIds", list);
				param.put("userId", userId);
				getSqlMapClientTemplate().update("BillingSheet.insertBillingItemBatch", param);
			}
			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}

	}
	

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#deleteBillingItems(java.lang.Integer)
	 */
	@Override
	public void deleteBillingItems(Integer orderId) {
		getSqlMapClientTemplate().update("BillingSheet.deleteBillingItemByOrderId", orderId);
	}

	@Override
	public void deleteBillingItemsByBatch(List<Integer> orderIds) throws ServiceException {
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();


				param.put("orderIds", list);
				getSqlMapClientTemplate().update("BillingSheet.deleteBillingItemByOrderId2", param);
	
			}
			

			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(), e);
		}
	}
	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#updateBillingOrder(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateBillingOrder(Integer userId, Integer orderId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("userId", userId);
		getSqlMapClientTemplate().update("BillingSheet.updateBillingOrder", param);
		
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#updateBillingItem(java.lang.Integer, java.lang.Integer, java.math.BigDecimal, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateBillingItem(Integer skuId, Integer userId,
			BigDecimal quantity, Integer origSkuId, Integer orderId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("skuId", skuId);
		param.put("userId", userId);
		param.put("quantity", quantity);
		param.put("origSkuId", origSkuId);
		param.put("orderId", orderId);
		getSqlMapClientTemplate().update("BillingSheet.updateBillingItem", param);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, BillingItem> getSumOrderBillingMapOfSkuBuyers(
			List<Integer> orderIds, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("BillingSheet.getSumOrderBillingBuyerMap", param, "order.buyerId");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, BillingItem> getSumOrderBillingMapOfSkuDates(
			List<Integer> orderIds, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("BillingSheet.getSumOrderBillingDateMap", param, "order.deliveryDate");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#deleteBillingItemsByOrderIdAndSkuIds(java.lang.Integer, java.util.List)
	 */
	@Override
	public void deleteBillingItemsByOrderIdAndSkuIds(Integer orderId,
			List<Integer> skuIds) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuIds", skuIds);
		getSqlMapClientTemplate().update("BillingSheet.deleteBillingItemsByOrderIdAndSkuIds", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#insertBillingOrder(com.freshremix.model.Order)
	 */
	@Override
	public Integer insertBillingOrder(Order order) {
		return (Integer)getSqlMapClientTemplate().insert("BillingSheet.insertOrder", order);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#insertBillingItem(com.freshremix.model.BillingItem)
	 */
	@Override
	public void insertBillingItem(BillingItem billingItem) {
		getSqlMapClientTemplate().update("BillingSheet.insertBillingItemObject", billingItem);
		
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#getGTPriceAllOrders(java.util.List)
	 */
	@Override
	public GrandTotalPrices getGTPriceAllOrders(List<Integer> orderIds) {

		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("orderCnt", orderIds.size());
		return (GrandTotalPrices) getSqlMapClientTemplate().queryForObject("BillingSheet.getGTPriceAllOrders", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getApprovedOrdersForBilling(List<Integer> orderId) {

		
		LOGGER.info("orderId size:"+ ((orderId == null)? "null" : orderId.size()));

		List<List<?>> splitList = CollectionUtil.splitList(orderId, 1000);
		
		LOGGER.info("Number of groups:"+splitList.size());
		List<Order> returnList = new ArrayList<Order>();
		for (List<?> list : splitList) {

			LOGGER.info("Group size:"+list.size());
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("orderIds", list);
			List<Order> queryResutList = getSqlMapClientTemplate().queryForList("BillingSheet.selectApprovedOrdersForBilling", param);
			if (CollectionUtils.isNotEmpty(queryResutList)) {
				returnList.addAll(queryResutList);
				LOGGER.info("Result size:"+queryResutList.size());
			}
		}
		return returnList;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BillingItem> getBillingItemsBulk(List<Integer> orderIds, List<Integer> skuIds, boolean hasQty) {
		
		List<BillingItem> list = new ArrayList<BillingItem>();
		
		int orderCount = orderIds.size();
		int loopCount = (orderCount / 1000) + 1;
		/* SQL WHERE column IN (?,?,?...) IS LIMITED TO 1000 */
		for (int i = 0; i < loopCount; i++) {
			
			int startIdx = i * 1000;
			List<Integer> thisOrderIds = new ArrayList<Integer>();
			for (int j = startIdx; j < startIdx + 1000; j++) {
				if (orderCount > j)
					thisOrderIds.add(orderIds.get(j));
			}
			
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("orderIds", thisOrderIds);
			param.put("skuIds", skuIds);
			if(hasQty){
				param.put("hasQty", hasQty);
			}
			list.addAll(getSqlMapClientTemplate().queryForList("BillingSheet.getBillingItemsBulk", param));
		}
		
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BillingItem> getBillingItem(Integer sellerId, String deliveryDate,
			Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("deliveryDate", deliveryDate);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForList("BillingSheet.getBillingItemBySellerIdSkuIdDate", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderBillingDao#selectDistinctSKUBA(java.util.List, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AkadenSKU> selectDistinctSKUBA(List<Integer> orderIds, Integer categoryId) {
		
		List<AkadenSKU> list = new ArrayList<AkadenSKU>();
		
		int orderCount = orderIds.size();
		int loopCount = (orderCount / 1000) + 1;
		/* SQL WHERE column IN (?,?,?...) IS LIMITED TO 1000 */
		for (int i = 0; i < loopCount; i++) {
			
			int startIdx = i * 1000;
			List<Integer> thisOrderIds = new ArrayList<Integer>();
			for (int j = startIdx; j < startIdx + 1000; j++) {
				if (orderCount > j)
					thisOrderIds.add(orderIds.get(j));
			}
			
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("orderCnt", thisOrderIds.size());
			param.put("orderIds", thisOrderIds);
			param.put("categoryId", categoryId);
			list.addAll(getSqlMapClientTemplate().queryForList("BillingSheet.selectDistinctSKUBAs", param));
		}
		return list;
	}
}