package com.freshremix.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.SKUDao;
import com.freshremix.model.SKU;

public class SKUDaoImpl extends SqlMapClientDaoSupport 
	implements SKUDao {

	@Override
	public Integer insertSKU(SKU sku) {
		Integer ireturn = (Integer) getSqlMapClientTemplate().insert("SKU.insertSKU", sku);
		return ireturn;
	}

	@Override
	public SKU selectSKU(Integer skuId) {
		return (SKU) getSqlMapClientTemplate().queryForObject("SKU.selectSKU", skuId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.SKUDao#updateSKU(com.freshremix.model.SKU)
	 */
	@Override
	public void updateSKU(SKU sku) {
		getSqlMapClientTemplate().update("SKU.updateSKU", sku);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.SKUDao#isSKUExisting(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Integer selectSKU(Integer sellerId, Integer skuId,
			Integer sheetType) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("skuId", skuId);
		param.put("sheetType", sheetType);
		return (Integer)getSqlMapClientTemplate().queryForObject("SKU.isSKUExisting", param);
	}

	@Override
	public SKU findSKU(Integer skuId) {
		return (SKU) getSqlMapClientTemplate().queryForObject("SKU.findSKU", skuId);
	}
}