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
 * Jul 23, 2010		raquino		
 */
package com.freshremix.dao;

import java.util.List;

import com.freshremix.model.BuyersSort;
import com.freshremix.model.CompanySort;
import com.freshremix.ui.model.FilteredIDs;

/**
 * @author raquino
 *
 */
public interface CompanyBuyersSortDao {
	
	List<CompanySort> getSortedCompanyForUserPref(List<Integer> sellerIds, Integer userId);
	
	void insertSortCompany(CompanySort companySort);
	
	void deleteSortCompany(Integer userId);

	List<BuyersSort> getSortedBuyersForUserPref(List<Integer> sellerIds, Integer userId, Integer companyId);
	
	void insertSortBuyers(BuyersSort buyerSort);
	
	void deleteSortBuyers(BuyersSort sortedBuyers);
	
	List<FilteredIDs> getSortedCompany(Integer userId);
	
	List<BuyersSort> getSortedMembersByBuyerAdminId(Integer userId);
	
	List<FilteredIDs> getSortedBuyers(Integer userId);
	
//	Integer getSortedCompanyCnt(Integer userId);
}
