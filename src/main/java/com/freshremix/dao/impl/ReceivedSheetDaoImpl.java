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
 * date		name	version	changes
 * ------------------------------------------------------------------------------
 * Mar 30, 2010		nvelasquez			
 * 20121001	Rhoda	v12		Redmine #1089 – Allocation-Received Sheet different in quantity 
 */
package com.freshremix.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.ReceivedSheetDao;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.Order;
import com.freshremix.model.OrderReceived;
import com.freshremix.model.SKUBA;
import com.freshremix.ui.model.ProfitInfo;
import com.freshremix.util.CollectionUtil;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author nvelasquez
 *
 */
public class ReceivedSheetDaoImpl extends SqlMapClientDaoSupport 
	implements ReceivedSheetDao {
	private static final Logger LOGGER = Logger.getLogger(ReceivedSheetDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<SKUBA> selectDistinctSKUBAs(List<Integer> orderIds, List<Integer> sellerId, List<Integer> buyerId,
			List<String> deliveryDate, Integer categoryId, String hasQty) {
		
		Map<String, Object> param = new HashMap<String,Object>();
		
		if (orderIds.size() > 1000) {
			param.put("sellerId", sellerId);
			param.put("buyerId", buyerId);
			param.put("deliveryDate", deliveryDate);
			param.put("categoryId", categoryId);
			param.put("hasQty", hasQty);
			return getSqlMapClientTemplate().queryForList("ReceivedSheet.selectDistinctSKUBAByOrderParams", param);
		}
		else {
			param.put("orderIds", orderIds);
			param.put("categoryId", categoryId);
			param.put("hasQty", hasQty);
			return getSqlMapClientTemplate().queryForList("ReceivedSheet.selectDistinctSKUBAs", param);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderReceived> getOrderReceivedMapOfSkuBuyers(
			List<Integer> orderIds, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("ReceivedSheet.getOrderReceivedMap", param, "order.buyerId");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderReceived> getSumOrderReceivedMapOfSkuBuyers(
			List<Integer> orderIds, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("ReceivedSheet.getSumOrderReceivedBuyerMap", param, "order.buyerId");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderReceived> getOrderReceivedMapOfSkuDates(
			List<Integer> orderIds, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("ReceivedSheet.getOrderReceivedMap", param, "order.deliveryDate");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderReceived> getSumOrderReceivedMapOfSkuDates(
			List<Integer> orderIds, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("ReceivedSheet.getSumOrderReceivedDateMap", param, "order.deliveryDate");
	}

	@Override
	//ENHANCEMENT END 20121001: Rhoda Redmine 1089
//	public void updateReceived(Integer orderId, Integer skuId, Integer skuBaId, BigDecimal quantity,
//			String isApproved, Integer userId, Integer oldSkuBaId) {
	public void updateReceived(Integer orderId, Integer skuId, Integer skuBaId,
			String isApproved, Integer userId, Integer oldSkuBaId) {	
	//ENHANCEMENT END 20121001: Rhoda Redmine 1089
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("skuBaId", skuBaId);
		//DELETE 20121001: Rhoda Redmine 1089
		//param.put("quantity", quantity);
		param.put("isApproved", isApproved);
		param.put("userId", userId);
		param.put("oldSkuBaId", oldSkuBaId);
		getSqlMapClientTemplate().update("ReceivedSheet.updateReceived", param);
	}
	
	@Override
	public void updateReceivedFromAlloc(Integer orderId, Integer skuId, BigDecimal quantity, Integer userId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("quantity", quantity);
		param.put("userId", userId);
		int ret = getSqlMapClientTemplate().update("ReceivedSheet.updateReceivedFromAlloc", param);
		if (ret == 0)
			this.insertDefaultReceiveItems(orderId, skuId, null, userId, quantity);
	}
	
	@Override
	public void updateSpecialReceivedItem(Integer orderId, Integer skuId, Integer origSkuId,
			BigDecimal quantity, Integer userId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("origSkuId", origSkuId);
		param.put("quantity", quantity);
		param.put("userId", userId);
		getSqlMapClientTemplate().update("ReceivedSheet.updateSpecialReceivedItem", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getPublishedOrdersForReceived(List<Integer> buyerIds,
			List<String> deliveryDates, List<Integer> sellerIds) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("buyerIds", buyerIds);
		param.put("deliveryDates", deliveryDates);
		param.put("sellerIds", sellerIds);
		return getSqlMapClientTemplate().queryForList("ReceivedSheet.getPublishedOrdersForReceived", param);
	}
	
	@Override
	public void lockReceived(List<Integer> orderIds, Integer userId) {
		
		LOGGER.info("orderId size:"+ ((orderIds == null)? "null" : orderIds.size()));

		List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
		
		LOGGER.info("Number of groups:"+splitList.size());
		for (List<?> list : splitList) {

			LOGGER.info("Group size:"+list.size());
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("receivedLockedBy", userId);
			param.put("orderIds", list);
			param.put("receivedUnlockedBy", null);
			param.put("userId", userId);
			getSqlMapClientTemplate().update("ReceivedSheet.lockUnlockReceived", param);

		}
	
	}

	@Override
	public void unlockReceived(List<Integer> orderIds, Integer userId) {

		LOGGER.info("orderId size:"+ ((orderIds == null)? "null" : orderIds.size()));

		List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
		
		LOGGER.info("Number of groups:"+splitList.size());
		for (List<?> list : splitList) {

			LOGGER.info("Group size:"+list.size());
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("receivedLockedBy", null);
			param.put("orderIds", list);
			param.put("receivedUnlockedBy", userId);
			param.put("userId", userId);
			getSqlMapClientTemplate().update("ReceivedSheet.lockUnlockReceived", param);

		}		
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.ReceivedSheetDao#insertOrder(com.freshremix.model.OrderReceived, java.lang.Integer)
	 */
	@Override
	public void insertDefaultReceiveItems(Integer orderId, Integer skuId, Integer skuBaId, 
			Integer userId, BigDecimal quantity) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("skuBaId", skuBaId);
		param.put("userId", userId);
		param.put("quantity", quantity);
		getSqlMapClientTemplate().update("ReceivedSheet.insertReceivedItem", param);
	}
	


	@Override
	public void bulkInsertReceivedItemFromAllocation(List<Integer> orderIdList, Integer savedAndPublishedBy) throws Exception {
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIdList, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("userId", savedAndPublishedBy);

				param.put("orderIds", list);
				getSqlMapClientTemplate().update("ReceivedSheet.bulkInsertReceivedItemFromAllocation2", param);
	
			}
			
			
			sqlMapClient.executeBatch();
		} catch (DataAccessException e) {
			throw new ServiceException("Unable to perform database operation:"+e.getMessage(), e);
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+e.getMessage(), e);
		}
	}
	
	

	/* (non-Javadoc)
	 * @see com.freshremix.dao.ReceivedSheetDao#deleteRecieveItems(java.lang.Integer)
	 */
	@Override
	public void deleteReceiveItems(Integer orderId) {
		getSqlMapClientTemplate().update("ReceivedSheet.deleteReceiveItemByOrderId", orderId);
	}
	
	@Override
	public void deleteReceivedItemByBatch(List<Integer> orderIdList) throws Exception {
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIdList, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();

				param.put("orderIds", list);
				getSqlMapClientTemplate().update("ReceivedSheet.deleteReceiveItemByOrderId2", param);
	
			}

			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+e.getMessage(), e);
		}
	}
	
	
	@Override
	public void deleteReceiveItems(Integer orderId, List<Integer> skuIds) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuIds", skuIds);
		getSqlMapClientTemplate().update("ReceivedSheet.deleteReceiveItemByOrderIdSkuId", param);
	
	
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.ReceivedSheetDao#getFinalizedOrdersForReceived(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getApprovedOrdersForReceived(List<Integer> orderId) {
		return getSqlMapClientTemplate().queryForList("ReceivedSheet.selectApprovedOrdersForReceived", orderId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderReceived> getReceivedItemsBulk(List<Integer> orderIds, List<Integer> skuIds, boolean hasQty) {
		
		List<OrderReceived> list = new ArrayList<OrderReceived>();
		
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
			
			list.addAll(getSqlMapClientTemplate().queryForList("ReceivedSheet.getReceivedItemsBulk", param));
		}
		
		
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.freshremix.dao.ReceivedSheetDao#deleteReceiveItems(java.util.List, java.util.List)
	 */
	@Override
	public void deleteReceiveItems(List<Integer> orderIds, List<Integer> skuIds) throws ServiceException {
		
		
		SqlMapClient sqlMapClient = getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			
			List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
			
			LOGGER.info("Number of groups:"+splitList.size());
			for (List<?> list : splitList) {
	
				LOGGER.info("Group size:"+list.size());
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("skuIds", skuIds);
				param.put("orderIds", list);
				getSqlMapClientTemplate().update("ReceivedSheet.deleteReceiveItemByOrderIdsSkuId", param);
	
			}

			sqlMapClient.executeBatch();
		} catch (SQLException e) {
			throw new ServiceException("Unable to perform database operation:"+e.getMessage(), e);
		}
		
	}

	@Override
	public void updateReceivedItemSkuBaId(Integer orderId, Integer skuId, Integer skuBaId, Integer oldSkuBaId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("skuBaId", skuBaId);
		param.put("oldSkuBaId", oldSkuBaId);
		getSqlMapClientTemplate().update("ReceivedSheet.updateReceivedItemSkuBaId", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProfitInfo> getBuyerPricesPerSKU(String deliveryDate,
			List<Integer> buyerId, List<Integer> sellerId, Integer categoryId, Double tax) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("buyerId", buyerId);
		param.put("deliveryDate", deliveryDate);
		param.put("categoryId", categoryId);
		param.put("tax", tax);
		
		return getSqlMapClientTemplate().queryForList("ReceivedSheet.getBuyerPricesPerSKU", param);
	}

}
