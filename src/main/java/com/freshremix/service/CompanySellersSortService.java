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
 * Nov 11, 2011		raquino		
 */
package com.freshremix.service;

import java.util.List;
import java.util.Map;

import com.freshremix.model.BuyersSort;
import com.freshremix.model.CompanySort;
import com.freshremix.model.SellersSort;
import com.freshremix.model.User;
import com.freshremix.ui.model.FilteredIDs;

/**
 * @author raquino
 *
 */
public interface CompanySellersSortService {
	
	List<CompanySort> getSortedCompanyForUserPref(User user);
	
	List<SellersSort> getSortedSellersForUserPref(User user, Integer companyId);
	
	void insertSortCompany(User user, List<Map<String,String>> updateList);
	
	void insertSortSellers(User user, List<Map<String,String>> updateList);
	
	List<FilteredIDs> getSortedCompany(Integer userId);
	
	List<FilteredIDs> getSortedSellers(User user);

}
