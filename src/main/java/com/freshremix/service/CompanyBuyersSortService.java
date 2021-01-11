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
package com.freshremix.service;

import java.util.List;

import com.freshremix.model.BuyersSort;
import com.freshremix.model.CompanySort;
import com.freshremix.ui.model.FilteredIDs;

/**
 * @author raquino
 *
 */
public interface CompanyBuyersSortService {
	
	List<CompanySort> getSortedCompanyForUserPref(List<Integer> sellerIds, Integer userId);
	
	void insertSortCompany(Integer userId, List<CompanySort> sortedCompanies);
	
	void deleteSortCompany(Integer userId);

	List<BuyersSort> getSortedBuyersForUserPref(List<Integer> sellerIds, Integer userId, Integer companyId);
	
	void insertSortBuyers(Integer userId, List<BuyersSort> sortedBuyers, List<CompanySort> sortedCompanies);
	
	void deleteSortBuyers(BuyersSort sortedBuyers);
	
	List<FilteredIDs> getSortedCompany(Integer userId);
	
	List<FilteredIDs> getSortedBuyers(List<Integer> sellerIds, Integer userId, List<Integer> companyIds);
	
	List<BuyersSort> getSortedMembersByBuyerAdminId(Integer userId);
}
