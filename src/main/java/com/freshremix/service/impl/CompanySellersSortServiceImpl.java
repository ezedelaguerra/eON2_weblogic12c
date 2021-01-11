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
package com.freshremix.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.freshremix.constants.RoleConstants;
import com.freshremix.dao.CompanySellersSortDao;
import com.freshremix.model.Company;
import com.freshremix.model.CompanySort;
import com.freshremix.model.SellersSort;
import com.freshremix.model.User;
import com.freshremix.service.CompanySellersSortService;
import com.freshremix.ui.model.FilteredIDs;

/**
 * @author raquino
 *
 */
public class CompanySellersSortServiceImpl implements CompanySellersSortService {

	private CompanySellersSortDao companySellersSortDao;

	public void setCompanySellersSortDao(CompanySellersSortDao companySellersSortDao) {
		this.companySellersSortDao = companySellersSortDao;
	}
	
	@Override
	public List<CompanySort> getSortedCompanyForUserPref(User user) {

		Long userRoleId = user.getRole().getRoleId();
		List<CompanySort> companySorts = null;
		
		if (userRoleId.equals(RoleConstants.ROLE_BUYER_ADMIN)) {
			companySorts = companySellersSortDao.getSortedCompanyForUserPref("isBuyerAdmin", user.getUserId());
		}
		else if (userRoleId.equals(RoleConstants.ROLE_BUYER)) {
			companySorts = companySellersSortDao.getSortedCompanyForUserPref(null, user.getUserId());
		}
		return companySorts;
	}
	
	@Override
	public List<SellersSort> getSortedSellersForUserPref(User user, Integer companyId) {

		Long userRoleId = user.getRole().getRoleId();
		List<SellersSort> sellersSort = null;
		
		if (userRoleId.equals(RoleConstants.ROLE_SELLER_ADMIN)) {
			sellersSort = companySellersSortDao.getSortedSellersForSellerAdminUserPref(user.getUserId());
		}
		else if (userRoleId.equals(RoleConstants.ROLE_BUYER_ADMIN)) {
			sellersSort = companySellersSortDao.getSortedSellersForBuyersUserPref("isBuyerAdmin", user.getUserId(), companyId);
		}
		else if (userRoleId.equals(RoleConstants.ROLE_BUYER)) {
			sellersSort = companySellersSortDao.getSortedSellersForBuyersUserPref(null, user.getUserId(), companyId);
		}
		return sellersSort;
	}

	@Override
	public void insertSortCompany(User user, List<Map<String, String>> updateList) {
		
		List<CompanySort> companySortList = new ArrayList<CompanySort>();	
		for (Map<String,String> companySortMap:updateList){
			CompanySort companySort = new CompanySort();
			companySort.setUser(user);
			String sorting = String.valueOf(companySortMap.get("sorting"));
			companySort.setSorting(Integer.valueOf(sorting));
			String companyId = String.valueOf(companySortMap.get("companyId"));
			Company company = new Company();
			company.setCompanyId(Integer.valueOf(companyId));
			companySort.setCompany(company);
			companySortList.add(companySort);
		}
		
		companySellersSortDao.insertSortCompany(user.getUserId(), companySortList);
	}

	@Override
	public void insertSortSellers(User user, List<Map<String, String>> updateList) {

		List<SellersSort> sellerSortList = new ArrayList<SellersSort>();	
		for (Map<String,String> sellerSortMap:updateList){
			SellersSort sellersSort = new SellersSort();
			sellersSort.setUser(user);
			String sorting = String.valueOf(sellerSortMap.get("sorting"));
			sellersSort.setSorting(Integer.valueOf(sorting));
			User seller = new User();
			seller.setUserId(Integer.valueOf(sellerSortMap.get("sellerId")));
			sellersSort.setSeller(seller);
			sellerSortList.add(sellersSort);
		}
		
		
		companySellersSortDao.insertSortSellers(user.getUserId(), sellerSortList);
		
	}

	@Override
	public List<FilteredIDs> getSortedCompany(Integer userId) {
		return companySellersSortDao.getSortedCompany(userId);
	}

	@Override
	public List<FilteredIDs> getSortedSellers(User user) {
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
			return companySellersSortDao.getSortedSellersForSellerAdmin(user.getUserId());
		}
		else
		return companySellersSortDao.getSortedSellers(user.getUserId());
	}
}
