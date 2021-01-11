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
 * Apr 2, 2010		raquino		
 */
package com.freshremix.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.OrderAkadenDao;
import com.freshremix.model.AkadenItem;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.OrderReceived;

/**
 * @author raquino
 *
 */
public class OrderAkadenDaoImpl extends SqlMapClientDaoSupport implements OrderAkadenDao{

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, AkadenItem> getAkadenItemsMapOfSkuDate(
			String deliveryDate, Integer skuId, Integer akadenSkuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDate", deliveryDate);
		param.put("skuId", skuId);
		param.put("akadenSkuId", akadenSkuId);
		return getSqlMapClientTemplate().queryForMap("Akaden.getAkadenItemsMapOfSkuDate", param, "order.buyerId");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, AkadenItem> getAkadenItemsMapOfSkuDates(
			List<String> deliveryDates, Integer skuId, Integer buyerId, Integer akadenSkuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDates", deliveryDates);
		param.put("skuId", skuId);
		param.put("buyerId", buyerId);
		param.put("akadenSkuId", akadenSkuId);
		return getSqlMapClientTemplate().queryForMap("Akaden.getAkadenItemsMapOfSkuDates", param, "order.deliveryDate");
	}

	@Override
	public List<Integer> getSelectedOrdersByBuyer(List<Integer> sellerIds,
			Integer buyerId, List<String> dates) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getSelectedOrdersByDate(List<Integer> sellerIds,
			List<Integer> buyerIds, String selectedDate) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderAkadenDao#selectDistinctSKUs(java.util.List, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<AkadenSKU> selectDistinctSKUs(List<Integer> orderIds,
			Integer categoryId, Integer sheetTypeId, Integer rowStart,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, AkadenItem> getSumAkadenMapOfSkuBuyers(
			List<Integer> orderIds, Integer skuId, Integer akadenSkuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		param.put("akadenSkuId", akadenSkuId);
		return getSqlMapClientTemplate().queryForMap("Akaden.getSumAkadenBuyerMap", param, "order.buyerId");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, AkadenItem> getSumAkadenMapOfSkuDates(
			List<Integer> orderIds, Integer skuId, Integer akadenSkuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		param.put("skuId", skuId);
		param.put("akadenSkuId", akadenSkuId);
		return getSqlMapClientTemplate().queryForMap("Akaden.getSumAkadenDateMap", param, "order.deliveryDate");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderAkadenDao#getAkadenItemsBulk(java.util.List)
	 */
	@Override
	public List<AkadenItem> getAkadenItemsBulk(List<Integer> orderIds) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		return getSqlMapClientTemplate().queryForList("Akaden.getAkadenItemsBulk", param);
	}

}
