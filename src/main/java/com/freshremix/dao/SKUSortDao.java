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
package com.freshremix.dao;

import java.util.List;

import com.freshremix.model.SKUColumn;
import com.freshremix.model.SKUSort;

/**
 * @author nvelasquez
 *
 */
public interface SKUSortDao {
	
	List<SKUColumn> getDefaultColumns(List<Integer> excludeCols);
	
	SKUSort getSKUSort(Integer userId);
	
	Integer insertSortSKU(Integer userId, String skuColumnIds);
	
	void deleteSortSKU(Integer userId);
	
	List<SKUColumn> getAllColumns();
}
