package com.freshremix.service;

import com.freshremix.model.SKU;

public interface SKUService {

	void insertSKU(SKU sku);
	void updateSKU(SKU sku);
	void updateNewSKU(SKU sku);
	SKU selectSKU(Integer skuId);
	SKU findSKU(Integer skuId);
	boolean isSKUExisting(Integer sellerId, Integer skuId, Integer sheetType);
}
