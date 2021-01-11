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
 * Nov 14, 2012     mstamaria     redmine 961		
 */
package com.freshremix.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.CompanySellersSortDao;
import com.freshremix.model.CompanySort;
import com.freshremix.model.SellersSort;
import com.freshremix.ui.model.FilteredIDs;

/**
 * @author raquino
 *
 */
public class CompanySellersSortDaoImpl  extends SqlMapClientDaoSupport implements CompanySellersSortDao {

	private static final Logger LOGGER = Logger.getLogger(CompanySellersSortDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanySort> getSortedCompanyForUserPref(String isBuyerAdmin, Integer userId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("isBuyerAdmin", isBuyerAdmin);
		param.put("userId", userId);
		return getSqlMapClientTemplate().queryForList("CompanySellersSort.getSortedCompanyForUserPref", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SellersSort> getSortedSellersForBuyersUserPref(String isBuyerAdmin, Integer userId, Integer companyId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("isBuyerAdmin", isBuyerAdmin);
		param.put("userId", userId);
		param.put("companyId", companyId);
		return getSqlMapClientTemplate().queryForList("CompanySellersSort.getSortedSellersForBuyersUserPref", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SellersSort> getSortedSellersForSellerAdminUserPref(Integer userId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Map <String, String> input = new HashMap<String, String>();
		input.put("userId", userId.toString());
		input.put("currDate", sdf.format(new Date()));
		return getSqlMapClientTemplate().queryForList("CompanySellersSort.getSortedSellersForSellerAdminUserPref", input);
	}
	
	@Override
	public void insertSortCompany(Integer userId, List<CompanySort> sortedCompanies) {
		getSqlMapClientTemplate().delete("CompanySellersSort.deleteSortCompany", userId);

		for ( CompanySort companySort: sortedCompanies) {
			getSqlMapClientTemplate().insert("CompanySellersSort.insertSortCompany", companySort);
		}
		
	}

	@Override
	public void insertSortSellers(Integer userId, List<SellersSort> sortedSellers) {

		for ( SellersSort sellersSort: sortedSellers) {
			getSqlMapClientTemplate().delete("CompanySellersSort.deleteSortSellers", sellersSort);
			getSqlMapClientTemplate().insert("CompanySellersSort.insertSortSellers", sellersSort);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FilteredIDs> getSortedCompany(Integer userId) {
		return getSqlMapClientTemplate().queryForList("CompanySellersSort.getSortedCompany", userId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FilteredIDs> getSortedSellers(Integer userId) {
		List<FilteredIDs> resultList = getSqlMapClientTemplate().queryForList("CompanySellersSort.getSortedSellers", userId);
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FilteredIDs> getSortedSellersForSellerAdmin(Integer userId) {
		return getSqlMapClientTemplate().queryForList("CompanySellersSort.getSortedSellersForSellerAdmin", userId);
	}

}
