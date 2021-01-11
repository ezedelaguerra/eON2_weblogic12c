package com.freshremix.dao;

import com.freshremix.model.SKU;

public interface SKUDao {

	Integer insertSKU(SKU sku);
	
	void updateSKU (SKU sku);
	
	SKU selectSKU(Integer skuId);
	
	SKU findSKU(Integer skuId);
	
	Integer selectSKU(Integer sellerId, Integer skuId, Integer sheetType);
	
}
