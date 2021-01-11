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

import com.freshremix.model.SKUColumn;

/**
 * @author nvelasquez
 *
 */
public interface SKUSortService {
	
	List<SKUColumn> getDefaultColumns(Long roleId);
	
	List<SKUColumn> getSKUSort(Integer userId, List<SKUColumn> defCols);
	
	List<Integer> getSKUSort(Integer userId);
	
	Integer insertSortSKU(List<SKUColumn> skuSorts, Integer userId);
	
	void deleteSortSKU(Integer userId);
	
	public List<Integer> getDefaultList();
	
	List<Integer> getDefaultSortOrder();

}
