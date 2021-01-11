package com.freshremix.dao;

import java.util.List;

import com.freshremix.model.SKUGroup;
import com.freshremix.model.SortSKUGroup;

public interface SKUGroupDao {

	List<SKUGroup> getAllSKUGroup(Integer sellerId, Integer categoryId);
	List<SKUGroup> getSKUGroupList(Integer sellerId, Integer categoryId);
	void saveSKUGroup(SKUGroup skuGroup);
	void updateSkuGroup(SKUGroup skuGroup);
	void insertSkuGroupToUpdateName(SKUGroup skuGroup);
	void deleteSKUGroup(List<Integer> historyIds);
	List<SortSKUGroup> getSKUGroupList(List<Integer> sellerIds, Integer categoryId, Integer userId);
	List getAllSKUGroupList(Integer sellerId);
	Integer getSKUGroupNameCount(SKUGroup skuGroup);
	SKUGroup getSKUgroup(Integer sellerId, String grpName,Integer categoryId);
}
