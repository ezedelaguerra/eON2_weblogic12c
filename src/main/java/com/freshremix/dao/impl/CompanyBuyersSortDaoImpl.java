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
package com.freshremix.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.CompanyBuyersSortDao;
import com.freshremix.model.BuyersSort;
import com.freshremix.model.CompanySort;
import com.freshremix.ui.model.FilteredIDs;

/**
 * @author raquino
 *
 */
public class CompanyBuyersSortDaoImpl extends SqlMapClientDaoSupport implements CompanyBuyersSortDao {

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CompanyBuyersSortDao#deleteSortBuyers(java.lang.Integer)
	 */
	@Override
	public void deleteSortBuyers(BuyersSort sortedBuyers) {
		getSqlMapClientTemplate().delete("CompanyBuyersSort.deleteSortBuyers", sortedBuyers);

	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CompanyBuyersSortDao#deleteSortCompany(java.lang.Integer)
	 */
	@Override
	public void deleteSortCompany(Integer userId) {
		getSqlMapClientTemplate().delete("CompanyBuyersSort.deleteSortCompany", userId);

	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CompanyBuyersSortDao#getSortedBuyers(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<BuyersSort> getSortedBuyersForUserPref(List<Integer> sellerIds, Integer userId, Integer companyId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerIds", sellerIds);
		param.put("companyId", companyId);
		param.put("userId", userId);
		return getSqlMapClientTemplate().queryForList("CompanyBuyersSort.getSortedBuyersForUserPref", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CompanyBuyersSortDao#getSortedCompany(java.util.List, java.lang.Integer)
	 */
	@Override
	public List<CompanySort> getSortedCompanyForUserPref(List<Integer> sellerIds,
			Integer userId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerIds", sellerIds);
		param.put("userId", userId);
		return getSqlMapClientTemplate().queryForList("CompanyBuyersSort.getSortedCompanyForUserPref", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CompanyBuyersSortDao#insertSortBuyers(com.freshremix.model.BuyersSort)
	 */
	@Override
	public void insertSortBuyers(BuyersSort buyerSort) {
		getSqlMapClientTemplate().insert("CompanyBuyersSort.insertSortBuyers", buyerSort);

	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CompanyBuyersSortDao#insertSortCompany(com.freshremix.model.CompanySort)
	 */
	@Override
	public void insertSortCompany(CompanySort companySort) {
		getSqlMapClientTemplate().insert("CompanyBuyersSort.insertSortCompany", companySort);

	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CompanyBuyersSortDao#getSortedBuyers(java.lang.Integer, java.lang.Integer)
	 */
//	@Override
//	public List<FilteredIDs> getSortedBuyers(List<Integer> sellerIds, Integer userId, List<Integer> companyIds) {
//
//		Map<String, Object> param = new HashMap<String,Object>();
//		param.put("sellerIds", sellerIds);
//		param.put("companyIds", companyIds);
//		param.put("userId", userId);
//		return getSqlMapClientTemplate().queryForList("CompanyBuyersSort.getSortedBuyers", param);
//	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CompanyBuyersSortDao#getSortedCompany(java.util.List, java.lang.Integer)
	 */
	@Override
	public List<FilteredIDs> getSortedCompany(Integer userId) {
		return getSqlMapClientTemplate().queryForList("CompanyBuyersSort.getSortedCompany", userId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CompanyBuyersSortDao#getSortedMembersByBuyerAdminId(java.lang.Integer)
	 */
	@Override
	public List<BuyersSort> getSortedMembersByBuyerAdminId(Integer userId) {
		return getSqlMapClientTemplate().queryForList("CompanyBuyersSort.getSortedMembersByBuyerAdminId", userId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CompanyBuyersSortDao#getSortedBuyers(java.lang.Integer)
	 */
	@Override
	public List<FilteredIDs> getSortedBuyers(Integer userId) {
		return getSqlMapClientTemplate().queryForList("CompanyBuyersSort.getSortedBuyers", userId);
	}

//	/* (non-Javadoc)
//	 * @see com.freshremix.dao.CompanyBuyersSortDao#getSortedCompanyCnt(java.lang.Integer)
//	 */
//	@Override
//	public Integer getSortedCompanyCnt(Integer userId) {
//		return (Integer) getSqlMapClientTemplate().queryForObject("CompanyBuyersSort.getSortedCompanyCnt", userId);
//	}

}
