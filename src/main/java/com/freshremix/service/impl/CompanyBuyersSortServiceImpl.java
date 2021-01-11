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
package com.freshremix.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.freshremix.dao.CompanyBuyersSortDao;
import com.freshremix.model.BuyersSort;
import com.freshremix.model.CompanySort;
import com.freshremix.model.User;
import com.freshremix.service.CompanyBuyersSortService;
import com.freshremix.ui.model.FilteredIDs;

/**
 * @author raquino
 *
 */
public class CompanyBuyersSortServiceImpl implements CompanyBuyersSortService {
	
	private CompanyBuyersSortDao companybuyersSortDao;

	public void setCompanybuyersSortDao(CompanyBuyersSortDao companybuyersSortDao) {
		this.companybuyersSortDao = companybuyersSortDao;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CompanyBuyersSortService#deleteSortBuyers(java.lang.Integer)
	 */
	@Override
	public void deleteSortBuyers(BuyersSort sortedBuyers) {
		companybuyersSortDao.deleteSortBuyers(sortedBuyers);

	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CompanyBuyersSortService#deleteSortCompany(java.lang.Integer)
	 */
	@Override
	public void deleteSortCompany(Integer userId) {
		companybuyersSortDao.deleteSortCompany(userId);

	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CompanyBuyersSortService#getSortedBuyers(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<BuyersSort> getSortedBuyersForUserPref(List<Integer> sellerIds, Integer userId, Integer companyId) {
		return companybuyersSortDao.getSortedBuyersForUserPref(sellerIds, userId, companyId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CompanyBuyersSortService#getSortedCompany(java.util.List, java.lang.Integer)
	 */
	@Override
	public List<CompanySort> getSortedCompanyForUserPref(List<Integer> sellerIds,
			Integer userId) {
		return companybuyersSortDao.getSortedCompanyForUserPref(sellerIds, userId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CompanyBuyersSortService#insertSortBuyers(com.freshremix.model.BuyersSort)
	 */
	@Override
	public void insertSortBuyers(Integer userId, List<BuyersSort> sortedBuyers, List<CompanySort> sortedCompanies) {
		
//		//check company sort if exist
//		Integer compExist = companybuyersSortDao.getSortedCompanyCnt(userId);
//		//if company sort not exist, insert company sort
//		User user = new User();
//		user.setUserId(userId);
//		if(compExist == 0){
//			int i = 0;
//			for ( CompanySort companySort: sortedCompanies) {
//				i++;
//				companySort.setSorting(i);
//				companySort.setUser(user);
//				companybuyersSortDao.insertSortCompany(companySort);
//			}
//		}
		
		for ( BuyersSort buyersSort: sortedBuyers) {
			this.deleteSortBuyers(buyersSort);
			companybuyersSortDao.insertSortBuyers(buyersSort);
		}

	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CompanyBuyersSortService#insertSortCompany(com.freshremix.model.CompanySort)
	 */
	@Override
	public void insertSortCompany(Integer userId, List<CompanySort> sortedCompanies) {
		
		this.deleteSortCompany(userId);
		for ( CompanySort companySort: sortedCompanies) {
			companybuyersSortDao.insertSortCompany(companySort);
		}

	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CompanyBuyersSortService#getSortedBuyers(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<FilteredIDs> getSortedBuyers(List<Integer> sellerIds, Integer userId, List<Integer> companyIds) {
		List<CompanySort> sortedCompanies = companybuyersSortDao.getSortedCompanyForUserPref(sellerIds, userId);
		List<FilteredIDs> sortedBuyers = new ArrayList<FilteredIDs>();
		for(CompanySort _companySort :sortedCompanies){
			List<BuyersSort>  buyersSorts= companybuyersSortDao.getSortedBuyersForUserPref(sellerIds, userId, 
					_companySort.getCompany().getCompanyId());
			for(BuyersSort _buyersSort :buyersSorts){
				sortedBuyers.add(new FilteredIDs(_buyersSort.getBuyer().getUserId().toString(),
						_buyersSort.getBuyer().getName()));
			}
		}
			
		return sortedBuyers;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CompanyBuyersSortService#getSortedCompany(java.util.List, java.lang.Integer)
	 */
	@Override
	public List<FilteredIDs> getSortedCompany(Integer userId) {
		return companybuyersSortDao.getSortedCompany(userId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CompanyBuyersSortService#getSortedMembersByBuyerAdminId(java.lang.Integer)
	 */
	@Override
	public List<BuyersSort> getSortedMembersByBuyerAdminId(Integer userId) {
		return companybuyersSortDao.getSortedMembersByBuyerAdminId(userId);
	}

}
