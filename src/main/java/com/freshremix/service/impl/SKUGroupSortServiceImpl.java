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
 * May 31, 2010		nvelasquez		
 */
package com.freshremix.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.freshremix.dao.SKUGroupSortDao;
import com.freshremix.model.SKUColumn;
import com.freshremix.model.SKUGroupSort;
import com.freshremix.service.SKUGroupSortService;

/**
 * @author nvelasquez
 *
 */
public class SKUGroupSortServiceImpl implements SKUGroupSortService {
	
	private SKUGroupSortDao skuGroupSortDao;

	public void setSkuGroupSortDao(SKUGroupSortDao skuGroupSortDao) {
		this.skuGroupSortDao = skuGroupSortDao;
	}

	@Override
	public List<SKUGroupSort> getSKUGroupSort(List<Integer> sellerIds,
			Integer skuCategoryId, Integer userId) {
		return skuGroupSortDao.getSKUGroupSort(sellerIds, skuCategoryId, userId);
	}
	
	@Override
	public List<Integer> getSKUGroupSortI(List<Integer> sellerIds,
			Integer skuCategoryId, Integer userId) {
		List<SKUGroupSort> skuGroupSorts =  skuGroupSortDao.getSKUGroupSort(sellerIds, skuCategoryId, userId);
		
		List<Integer> skuGroupSortI = new ArrayList<Integer>();
		
		for (SKUGroupSort skuGroupSort : skuGroupSorts) {
			Integer skuGroupId = skuGroupSort.getSkuGroupId();
			skuGroupSortI.add(skuGroupId);
		}
		
		return skuGroupSortI;
	}

	@Override
	public void insertSortSKUGroup(Integer userId, Integer skuCategoryId,
			List<Integer> skuGroupIds) {
		
		this.deleteSortSKUGroup(userId, skuCategoryId);
		for (int i=0; i<skuGroupIds.size(); i++) {
			Integer skuGroupId = skuGroupIds.get(i);
			skuGroupSortDao.insertSortSKUGroup(userId, skuCategoryId, skuGroupId, i+1);
		}
		
	}
	
	@Override
	public void deleteSortSKUGroup(Integer userId, Integer skuCategoryId) {
		skuGroupSortDao.deleteSortSKUGroup(userId, skuCategoryId);
	}
	
}
