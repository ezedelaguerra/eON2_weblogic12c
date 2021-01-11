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
 * Apr 13, 2010		Pammie		
 */
package com.freshremix.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.OrderUnitDao;
import com.freshremix.model.OrderUnit;

/**
 * @author Pammie
 *
 */
public class OrderUnitDaoImpl extends SqlMapClientDaoSupport implements OrderUnitDao{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderUnit> getOrderUnitList(Integer categoryId){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("categoryId", categoryId);
		
		return getSqlMapClientTemplate().queryForList("OrderUnit.getOrderUnitList",param);
	}
	
	@Override
	public void insertOrderUnit(String orderUnitName,Integer categoryId) {
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("orderUnitName", orderUnitName);
		param.put("categoryId", categoryId);
		
		getSqlMapClientTemplate().insert("OrderUnit.insertOrderUnit", param);
	}
	
	@Override
	public void deleteOrderUnit(Integer orderUnitId) {
		getSqlMapClientTemplate().delete("OrderUnit.deletePmfSkuById", orderUnitId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderUnit> getOrderUnitByCategoryId(Integer categoryId) {
		return getSqlMapClientTemplate().queryForList("OrderUnit.getOrderUnitByCategoryId", categoryId);
	}
	
	@Override
	public Integer getOrderUnitCaseId(Integer categoryId) {
		return (Integer)getSqlMapClientTemplate().queryForObject("OrderUnit.getOrderUnitCaseId", categoryId);
	}
	
	public Integer getOrderUnitIdByName(String orderUnitName){
		return (Integer)getSqlMapClientTemplate().queryForObject("OrderUnit.getOrderUnitIdByName", orderUnitName);
	}
	
	public Integer checkOrderUnitIfExist(String orderUnitName,Integer categoryId){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("orderUnitName", orderUnitName);
		param.put("categoryId", categoryId);
		return (Integer)getSqlMapClientTemplate().queryForObject("OrderUnit.checkOrderUnitIfExist", param);
	}
	
	public Integer checkOrderUnitIfInUse(Integer orderUnitId){
		return (Integer)getSqlMapClientTemplate().queryForObject("OrderUnit.checkOrderUnitIfInUse", orderUnitId);
	} 
	
	public Integer checkOrderUnitInPmfSku(Integer orderUnitId){
		return (Integer)getSqlMapClientTemplate().queryForObject("OrderUnit.checkOrderUnitInPmfSku", orderUnitId);
	}

	@Override
	public Integer insertUOM(String orderUnitName, Integer categoryId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("orderUnitName", orderUnitName);
		param.put("categoryId", categoryId);
		
		return (Integer)getSqlMapClientTemplate().insert("OrderUnit.insertUOM", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderUnit> getAllOrderUnit() {
		return getSqlMapClientTemplate().queryForList("OrderUnit.getAllOrderUnitList");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderUnitDao#getSellingUomList(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderUnit> getSellingUomList(Integer categoryId) {
		
		return getSqlMapClientTemplate().queryForList("OrderUnit.getSellingUomList",categoryId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.OrderUnitDao#getAllSellingUomList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderUnit> getAllSellingUomList() {
		return getSqlMapClientTemplate().queryForList("OrderUnit.getAllSellingUomList");
	}
}
