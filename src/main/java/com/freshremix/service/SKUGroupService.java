package com.freshremix.service;

import java.util.List;
import java.util.Map;

import com.freshremix.model.SKUGroup;

public interface SKUGroupService {

	//String getAllSKUGroup(List<Integer> sellerIds, Integer categoryId);
	
	boolean saveSKUGroup(SKUGroup skuGroup);
	
	String getSKUGroupList(Integer seller, Integer categoryId);
	
	List<Map<String, Object>> getSKUGroupListViaSellerSelect(Integer seller, Integer categoryId);
	
	boolean updateSKUGroup(List<SKUGroup> skuGroupList);
	
	boolean updateSKUGroup(SKUGroup skuGroup);
	
	void deleteSKUGroup(List<Integer> historyIds);
	
	List getSKUGroup(List<Integer> sellerIds, Integer categoryId, Integer userId);
	
	//List<Map<String,Object>> getSKUGroupListViaSellerSelect2(Integer seller, Integer categoryId);
	
	String getSKUGroupRenderer(List<Integer> sellerId, Integer categoryId);

	List<Map<String, Object>> getAllSKUGroupList(Integer sellerId);
	
	SKUGroup getSKUgrouGroup(Integer seller, String grpName,Integer categoryId);

	SKUGroup getSKUGroupByName(Integer sellerId, Integer categoryId,
			String skuGroupName);
}
