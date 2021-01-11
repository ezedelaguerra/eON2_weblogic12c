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
 * Nov 14, 2011		raquino		
 */
package com.freshremix.dao;

import java.util.List;

import com.freshremix.model.CompanySort;
import com.freshremix.model.SellersSort;
import com.freshremix.ui.model.FilteredIDs;

/**
 * @author raquino
 *
 */
public interface CompanySellersSortDao {

	List<CompanySort> getSortedCompanyForUserPref(String isBuyerAdmin, Integer userId);
	
	List<SellersSort> getSortedSellersForBuyersUserPref(String isBuyerAdmin, Integer userId, Integer companyId);
	
	List<SellersSort> getSortedSellersForSellerAdminUserPref(Integer userId);
	
	void insertSortCompany(Integer userId, List<CompanySort> sortedCompanies);
	
	void insertSortSellers(Integer userId, List<SellersSort> sortedSellers);
	
	List<FilteredIDs> getSortedCompany(Integer userId);
	
	List<FilteredIDs> getSortedSellers(Integer userId);
	
	List<FilteredIDs> getSortedSellersForSellerAdmin(Integer userId);

}
