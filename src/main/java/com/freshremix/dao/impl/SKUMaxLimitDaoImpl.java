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
 * Jun 3, 2010		gilwen		
 */
package com.freshremix.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.SKUMaxLimitDao;

/**
 * @author gilwen
 *
 */
public class SKUMaxLimitDaoImpl extends SqlMapClientDaoSupport implements SKUMaxLimitDao {

	/* (non-Javadoc)
	 * @see com.freshremix.dao.SKUMaxLimitDao#getSKUMaxLimit(java.lang.Integer, java.lang.String)
	 */
	@Override
	public BigDecimal getSKUMaxLimit(Integer skuId, String deliveryDate) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("skuId", skuId);
		param.put("deliveryDate", deliveryDate);
		return (BigDecimal) getSqlMapClientTemplate().queryForObject("SKUMaxLimit.selectSKUMaxLimit", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.SKUMaxLimitDao#insertSKUMaxLimit(java.lang.Integer, java.lang.String, java.math.BigDecimal)
	 */
	@Override
	public void insertSKUMaxLimit(Integer skuId, String deliveryDate,
			BigDecimal skuMaxLimit) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("skuId", skuId);
		param.put("deliveryDate", deliveryDate);
		param.put("skuMaxLimit", skuMaxLimit);
		getSqlMapClientTemplate().insert("SKUMaxLimit.insertSKUMaxLimit", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.SKUMaxLimitDao#updateSKUMaxLimit(java.lang.Integer, java.lang.String, java.math.BigDecimal)
	 */
	@Override
	public void updateSKUMaxLimit(Integer skuId, String deliveryDate,
			BigDecimal skuMaxLimit) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("skuId", skuId);
		param.put("deliveryDate", deliveryDate);
		param.put("skuMaxLimit", skuMaxLimit);
		getSqlMapClientTemplate().update("SKUMaxLimit.updateSKUMaxLimit", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.SKUMaxLimitDao#deleteSKUMaxLimit(java.lang.Integer, java.lang.String)
	 */
	@Override
	public void deleteSKUMaxLimit(Integer skuId, String deliveryDate) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("skuId", skuId);
		param.put("deliveryDate", deliveryDate);
		getSqlMapClientTemplate().delete("SKUMaxLimit.deleteSKUMaxLimit", param);
	}

}
