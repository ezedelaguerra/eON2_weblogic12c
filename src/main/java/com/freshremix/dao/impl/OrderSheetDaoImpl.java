package com.freshremix.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.OrderSheetDao;
import com.freshremix.model.OrderItem;
import com.freshremix.model.SKU;
import com.freshremix.ui.model.OrderItemUI;
import com.freshremix.ui.model.ProfitInfo;
import com.freshremix.util.CollectionUtil;
import com.freshremix.util.NumberUtil;

public class OrderSheetDaoImpl extends SqlMapClientDaoSupport
	implements OrderSheetDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItemUI> loadOrderItems(Map<String,Object> param) {
		return  getSqlMapClientTemplate().queryForList("OrderSheet.loadOrderItems", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItemUI> loadPreviousOrderItems(Integer sellerId, Integer selectedBuyerId, Integer categoryId, Integer sheetTypeId, String deliveryDate) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId",sellerId);
		param.put("selectedBuyerId",selectedBuyerId);
		param.put("categoryId",categoryId);
		param.put("sheetTypeId",sheetTypeId);
		param.put("deliveryDate",deliveryDate);
		return getSqlMapClientTemplate().queryForList("OrderSheet.loadPreviousOrderItems", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getOrderItemsMapOfSkuBuyers(
			List<Integer> orderIds, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("OrderSheet.getOrderItemsMap", param, "order.buyerId");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getSumOrderItemsMapOfSkuBuyers(
			List<Integer> orderIds, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("OrderSheet.getSumOrderItemsBuyerMap", param, "order.buyerId");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getOrderItemsMapOfSkuDates(
			List<Integer> orderIds, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("OrderSheet.getOrderItemsMap", param, "order.deliveryDate");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getSumOrderItemsMapOfSkuDates(
			List<Integer> orderIds, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		return getSqlMapClientTemplate().queryForMap("OrderSheet.getSumOrderItemsDateMap", param, "order.deliveryDate");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getOrderItemsMapOfSkuDate(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForMap("OrderSheet.getOrderItemsMapOfSkuDate", param, "order.buyerId");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getOrderItemsMapOfSkuDates(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForMap("OrderSheet.getOrderItemsMapOfSkuDates", param, "order.deliveryDate");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderSheetDao#getPublishedSKUs(java.util.Map)getPublishedOrders
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SKU> getPublishedSKUs(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("OrderSheet.getPublishedSKUs", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderSheetDao#getBuyerOrderItemsMapOfSkuDate(java.util.List, java.lang.String, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getBuyerOrderItemsByBuyers(
			List<Integer> buyerIds, String deliveryDate, Integer skuId, Integer isBuyer) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("buyerIds",buyerIds);
		param.put("deliveryDate",deliveryDate);
		param.put("skuId",skuId);
		param.put("isBuyer",isBuyer);
		return getSqlMapClientTemplate().queryForMap("OrderSheet.getBuyerOrderItemsByBuyers", param, "order.buyerId");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderSheetDao#getBuyerOrderItemsMapOfSkuDates(java.lang.Integer, java.util.List, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getBuyerOrderItemsByDates(
			Integer buyerId, List<String> deliveryDates, Integer skuId, Integer isBuyer) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("buyerId",buyerId);
		param.put("deliveryDates",deliveryDates);
		param.put("skuId",skuId);
		param.put("isBuyer",isBuyer);
		return getSqlMapClientTemplate().queryForMap("OrderSheet.getBuyerOrderItemsByDates", param, "order.deliveryDate");
	}

	@SuppressWarnings("unchecked")
	public List<SKU> selectDistinctSKUs(
			List<Integer> selectedOrders, 
			List<Integer> sellerId,
			List<Integer> buyerId,
			List<String> deliveryDate,
			Integer categoryId, 
			Integer sheetTypeId, 
			String hasQty) {
		
		Map<String, Object> param = new HashMap<String,Object>();
		

		if (selectedOrders.size() > 1000) {
			param.put("sellerId", sellerId);
			param.put("buyerId", buyerId);
			param.put("deliveryDate", deliveryDate);
			param.put("categoryId", categoryId);
			param.put("sheetTypeId", sheetTypeId);
			param.put("hasQty", hasQty);
			return getSqlMapClientTemplate().queryForList("OrderSheet.selectDistinctSKUs2", param);
		}
		else {
			param.put("orderIds", selectedOrders);
			param.put("categoryId", categoryId);
			param.put("sheetTypeId", sheetTypeId);
			param.put("hasQty", hasQty);
			return getSqlMapClientTemplate().queryForList("OrderSheet.selectDistinctSKUs", param);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getSumBuyerOrderItemsMapOfSkuBuyers(
			List<Integer> orderIds, Integer skuId, Integer isBuyer) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		param.put("isBuyer",isBuyer);
		return getSqlMapClientTemplate().queryForMap("OrderSheet.getSumOrderItemsBuyerMap", param, "order.buyerId");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getSumBuyerOrderItemsMapOfSkuDates(
			List<Integer> orderIds, Integer skuId, Integer isBuyer) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		param.put("isBuyer",isBuyer);
		return getSqlMapClientTemplate().queryForMap("OrderSheet.getSumOrderItemsDateMap", param, "order.deliveryDate");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderSheetDao#getTotalQuantityByOtherBuyers(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public BigDecimal getTotalQuantityByOtherBuyers(Integer sellerId,
			List<Integer> buyerId, Integer skuId, String deliveryDate) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("buyerId", buyerId);
		param.put("skuId", skuId);
		param.put("deliveryDate", deliveryDate);
		return (BigDecimal)getSqlMapClientTemplate().queryForObject("OrderSheet.getTotalQuantityByOtherBuyers", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, Map<String, Map<Integer, OrderItem>>> getSellerOrderItems(List<Integer> skuIds,
			List<String> deliveryDates, List<Integer> buyerUserIds, boolean hasQty) {
		
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDates", deliveryDates);
		param.put("skuIds", skuIds);
		param.put("buyerIds", buyerUserIds);
		if(hasQty){
			param.put("hasQty", hasQty);
		}
		
		List<OrderItem> orderItemList = getSqlMapClientTemplate().queryForList("OrderSheet.getSellerOrderItemsMap", param);
		
		Map<Integer, Map<String, Map<Integer, OrderItem>>> sellerOrderItemMap = 
			new HashMap<Integer,Map<String,Map<Integer,OrderItem>>>();
		
		for(OrderItem oi : orderItemList){
			
			Map<String, Map<Integer, OrderItem>> deliveryDateMap = null;
			if ( ! sellerOrderItemMap.containsKey(oi.getSku().getSkuId()) ) {
				deliveryDateMap = new HashMap<String, Map<Integer,OrderItem>>();
			} else {
				deliveryDateMap = sellerOrderItemMap.get(oi.getSku().getSkuId());
			}
			Map<Integer, OrderItem> buyerMap = null;
			if ( ! deliveryDateMap.containsKey(oi.getOrder().getDeliveryDate()) ) {
				buyerMap = new HashMap<Integer,OrderItem>();				
			} else {
				buyerMap = deliveryDateMap.get(oi.getOrder().getDeliveryDate());
			}
			
			buyerMap.put(oi.getOrder().getBuyerId(), oi);
			deliveryDateMap.put(oi.getOrder().getDeliveryDate(), buyerMap);
			sellerOrderItemMap.put(oi.getSku().getSkuId(), deliveryDateMap);
		}
		return sellerOrderItemMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItem> getSellerOrderItemsBulk(List<Integer> skuIds, List<String> deliveryDates, 
			List<Integer> buyerUserIds, boolean hasQty) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDates", deliveryDates);
		param.put("skuIds", skuIds);
		param.put("buyerIds", buyerUserIds);
		if(hasQty){
			param.put("hasQty", hasQty);
		}
		return getSqlMapClientTemplate().queryForList("OrderSheet.getSellerOrderItemsMap", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItem> getBuyerOrderItemsBulk(List<Integer> orderIds, List<Integer> skuIds, boolean hasQty) {
		
		List<OrderItem> list = new ArrayList<OrderItem>();
		
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
			
			list.addAll(getSqlMapClientTemplate().queryForList("OrderSheet.getBuyerOrderItemsBulk", param));
		}
		
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItem> getOrderItemsByOrderIdBulk(List<Integer> orderIds) {
		
		List<OrderItem> resultOrderItemList = new ArrayList<OrderItem>();

		if (CollectionUtils.isEmpty(orderIds)) {
			return resultOrderItemList;
		}
		
		List<List<?>> splitOrderIdList = CollectionUtil.splitList(orderIds, 1000);
		
		for (List<?> batchesOfOrderIds : splitOrderIdList) {

			Map<String, Object> param = new HashMap<String,Object>();
			param.put("orderIds", batchesOfOrderIds);
			List<OrderItem> tempOrderItemResultList = getSqlMapClientTemplate().queryForList("OrderSheet.getOrderItemsByOrderIdBulk", param);
			if (CollectionUtils.isNotEmpty(tempOrderItemResultList)){
				resultOrderItemList.addAll(tempOrderItemResultList);
			}
		}

		return resultOrderItemList;
		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<SKU> wsSelectDistinctSKUs(List<Integer> allOrderId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", allOrderId);
		return getSqlMapClientTemplate().queryForList("OrderSheet.wsSelectDistinctSKUs", param);
	}
	
	/**
	 * Get the sum of Order item quantities
	 */
	@Override
	public BigDecimal getSumOrderQuantities(List<Integer> orderIds) {
		List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
		BigDecimal sum = new BigDecimal("0");
		
		for (List<?> list : splitList) {
			sum.add(NumberUtil.nullToZero((BigDecimal)getSqlMapClientTemplate().queryForObject("OrderSheet.getSumOrderQuantities", list)));
		}		
		return sum;
	}

	@Override
	public BigDecimal getAvailableQuantities(Integer sellerId, Integer buyerId,
			String deliveryDate, Integer skuId) {
		
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("buyerId", buyerId);
		param.put("skuId", skuId);
		param.put("deliveryDate", deliveryDate);
		return (BigDecimal)getSqlMapClientTemplate().queryForObject("OrderSheet.getAvailableQuantities", param);
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
		
		return (ProfitInfo) getSqlMapClientTemplate().queryForObject("OrderSheet.getTotalPrice", param);
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
		
		return getSqlMapClientTemplate().queryForList("OrderSheet.getBuyerPricesPerSKU", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List< OrderItem> getOrderItemsListOfSkuBuyers(
			List<Integer> orderIds, Integer skuId) {
		
		
		List<OrderItem> result = new ArrayList<OrderItem>();
		List<List<?>> splitList = CollectionUtil.splitList(orderIds, 1000);
		
		for (List<?> list : splitList) {

			Map<String,Object> param = new HashMap<String,Object>();
			param.put("orderIds", list);
			param.put("skuId", skuId);
			result.addAll(getSqlMapClientTemplate().queryForList("OrderSheet.getOrderItemsMap", param));

		}
		
		
		return result;
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List< OrderItem> getOrderItemsListOfSkuId(
			List<Integer> skuIds, List<String> deliveryDates) {
		
		
		List<OrderItem> result = new ArrayList<OrderItem>();
		List<List<?>> splitList = CollectionUtil.splitList(skuIds, 1000);
		
		for (List<?> list : splitList) {

			Map<String,Object> param = new HashMap<String,Object>();
			param.put("deliveryDates", deliveryDates);
			param.put("skuIds", skuIds);
			result.addAll(getSqlMapClientTemplate().queryForList("OrderSheet.getOrderItemsMapByDate", param));

		}
		
		
		return result;
		
	}

}
