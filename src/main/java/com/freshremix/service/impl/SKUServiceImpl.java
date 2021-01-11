package com.freshremix.service.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.SKUDao;
import com.freshremix.model.SKU;
import com.freshremix.service.SKUService;

public class SKUServiceImpl extends SqlMapClientDaoSupport implements
		SKUService {

	private SKUDao skuDao;
	
	@Override
	public void insertSKU(SKU sku) {
		Integer id = skuDao.insertSKU(sku);
		sku.setSkuId(id);
	}

	@Override
	public void updateSKU(SKU sku) {
		skuDao.updateSKU(sku);
	}
	
	@Override
	public void updateNewSKU(SKU sku) {
		// set the original SKU id before inserting
		sku.setOrigSkuId(sku.getSkuId());
		sku.setSkuId(null);
		this.insertSKU(sku);
	}

	public void setSkuDao(SKUDao skuDao) {
		this.skuDao = skuDao;
	}

	@Override
	public SKU selectSKU(Integer skuId) {
		return skuDao.selectSKU(skuId);
	}

	@Override
	public boolean isSKUExisting(Integer sellerId, Integer skuId,
			Integer sheetType) {
		return skuDao.selectSKU(sellerId,skuId,sheetType).intValue() > 0 ? true : false;
	}
	
	@Override
	public SKU findSKU(Integer skuId) {
		return skuDao.findSKU(skuId);
	}
}
