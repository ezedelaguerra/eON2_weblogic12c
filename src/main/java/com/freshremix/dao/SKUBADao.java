package com.freshremix.dao;

import java.util.List;

import com.freshremix.model.SKUBA;


public interface SKUBADao {

	Integer insertSKUBA(SKUBA sku);
	
	SKUBA findSKUBA(Integer skuBuyerId);

	/**
	 * Retrieves the SKUBA records from the collection of SKUBAIds
	 * @param skuBAIdCollection
	 * @return
	 */
	List<SKUBA> findSKUBA(List<Integer> skuBAIds);

	/**
	 * Uses insertSKUBA and sets the PK
	 * @param skuBuyer
	 */
	void insertSKUBA2(SKUBA skuBuyer);
	
}
