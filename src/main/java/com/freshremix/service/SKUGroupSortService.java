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
package com.freshremix.service;

import java.util.List;

import com.freshremix.model.SKUGroupSort;

/**
 * @author nvelasquez
 *
 */
public interface SKUGroupSortService {
	
	List<SKUGroupSort> getSKUGroupSort(List<Integer> sellerIds, Integer skuCategoryId, Integer userId);
	
	List<Integer> getSKUGroupSortI(List<Integer> sellerIds, Integer skuCategoryId, Integer userId);
	
	void insertSortSKUGroup(Integer userId, Integer skuCategoryId, List<Integer> skuGroupIds);
	
	void deleteSortSKUGroup(Integer userId, Integer skuCategoryId);

}
