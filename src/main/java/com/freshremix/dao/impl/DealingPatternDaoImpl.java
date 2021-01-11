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
 * date		name		version		changes
 * ------------------------------------------------------------------------------
 * 20120725	gilwen		v11			Redmine 131 - Change of display in address bar of Comments
 */

package com.freshremix.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.DealingPatternDao;
import com.freshremix.model.AdminMember;
import com.freshremix.model.Company;
import com.freshremix.model.User;

public class DealingPatternDaoImpl extends SqlMapClientDaoSupport
	implements DealingPatternDao {

	/* (non-Javadoc)
	 * @see com.freshremix.dao.DealingPatternDao#getBuyerCompaniesBySellerCompanyIds(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getBuyerCompaniesBySellerCompanyIds(
			Integer userCompanyId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userCompanyId", userCompanyId);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getBuyerCompaniesBySellerCompanyIds",param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.DealingPatternDao#getBuyerCompaniesByUserSellerIds(java.util.List, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getBuyerCompaniesByUserSellerIds(List<Integer> selectedSellerIds,
			String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("selectedSellerIds", selectedSellerIds);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getBuyerCompaniesByUserSellerIds",param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.DealingPatternDao#getMembersByAdminId(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getMembersByAdminId(Integer userId,
			Integer dealingRelationId, String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("dealingRelationId", dealingRelationId);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getMembersByAdminId",param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminMember> getMembersByAdminIdWithStartEndDates(Integer userId,
			Integer dealingRelationId, String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("dealingRelationId", dealingRelationId);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getMembersByAdminIdWithStartEndDates",param);
	}
	
	// ENHANCEMENT START 20120725: Lele - Redmine 131
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getMembersByAdminId2(Integer userId,
			Integer dealingRelationId, String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("dealingRelationId", dealingRelationId);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getMembersByAdminId2",param);
	}
	// ENHANCEMENT END 20120725:

	/* (non-Javadoc)
	 * @see com.freshremix.dao.DealingPatternDao#getSellerCompaniesByBuyerCompanyIds(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getSellerCompaniesByBuyerCompanyIds(
			Integer userCompanyId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userCompanyId", userCompanyId);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getSellerCompaniesByBuyerCompanyIds",param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.DealingPatternDao#getUserBuyersByBuyerCompanyIds(java.lang.Integer, java.util.List, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserBuyersByBuyerCompanyIds(Integer userId,
			List<Integer> selectedCompanyIds, String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("selectedCompanyIds", selectedCompanyIds);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getUserBuyersByBuyerCompanyIds",param);
	}
	
	// ENHANCEMENT START 20120725: Lele - Redmine 131
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserBuyersByBuyerCompanyIds2(Integer userId,
			List<Integer> selectedCompanyIds, String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("selectedCompanyIds", selectedCompanyIds);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getUserBuyersByBuyerCompanyIds2",param);
	}
	// ENHANCEMENT END 20120725:

	/* (non-Javadoc)
	 * @see com.freshremix.dao.DealingPatternDao#getUserBuyersByUserSellersAndBuyerCompanyIds(java.util.List, java.util.List, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserBuyersByUserSellersAndBuyerCompanyIds(
			List<Integer> selectedSellerIds, List<Integer> selectedCompanyIds,
			String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("selectedSellerIds", selectedSellerIds);
		param.put("selectedCompanyIds", selectedCompanyIds);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getUserBuyersByUserSellersAndBuyerCompanyIds",param);
	}
	
	// ENHANCEMENT START 20120725: Lele - Redmine 131
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserBuyersByUserSellersAndBuyerCompanyIds2(
			List<Integer> selectedSellerIds, List<Integer> selectedCompanyIds,
			String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("selectedSellerIds", selectedSellerIds);
		param.put("selectedCompanyIds", selectedCompanyIds);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getUserBuyersByUserSellersAndBuyerCompanyIds2",param);
	}
	// ENHANCEMENT END 20120725:

	/* (non-Javadoc)
	 * @see com.freshremix.dao.DealingPatternDao#getUserSellersBySellerCompanyIds(java.lang.Integer, java.util.List, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserSellersBySellerCompanyIds(Integer userId,
			List<Integer> selectedCompanyIds, String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("selectedCompanyIds", selectedCompanyIds);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getUserSellersBySellerCompanyIds",param);
	}
	
	// ENHANCMENT START 20120725: Lele - Redmine 131
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserSellersBySellerCompanyIds2(Integer userId,
			List<Integer> selectedCompanyIds, String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("selectedCompanyIds", selectedCompanyIds);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getUserSellersBySellerCompanyIds2",param);
	}
	// ENHANCEMENT END 20120725:

	/* (non-Javadoc)
	 * @see com.freshremix.dao.DealingPatternDao#getSellerCompaniesByUserBuyerIds(java.util.List, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getSellerCompaniesByUserBuyerIds(
			List<Integer> selectedBuyerIds, String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("selectedBuyerIds", selectedBuyerIds);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getSellerCompaniesByUserBuyerIds",param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.DealingPatternDao#getUserSellersByUserBuyersAndSellerCompanyIds(java.util.List, java.util.List, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserSellersByUserBuyersAndSellerCompanyIds(
			List<Integer> selectedBuyerIds, List<Integer> selectedCompanyIds,
			String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("selectedBuyerIds", selectedBuyerIds);
		param.put("selectedCompanyIds", selectedCompanyIds);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getUserSellersByUserBuyersAndSellerCompanyIds",param);
	}
	
	// ENHANCEMENT START 20120725: Lele - Redmine 131
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserSellersByUserBuyersAndSellerCompanyIds2(
			List<Integer> selectedBuyerIds, List<Integer> selectedCompanyIds,
			String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("selectedBuyerIds", selectedBuyerIds);
		param.put("selectedCompanyIds", selectedCompanyIds);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getUserSellersByUserBuyersAndSellerCompanyIds2",param);
	}
	// ENHANCEMENT END 20120725: 

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getSellerIdsOfBuyerAdminId(Integer userId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getSellerIdsOfBuyerAdminId",param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getSellerIdsOfBuyerId(Integer userId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getSellerIdsOfBuyerId",param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getSellerIdsOfSellerAdminId(Integer userId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getSellerIdsOfSellerAdminId",param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAdminUsersOfMember(Integer userId,Integer relation) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("dealingPatternId", relation);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getAdminUsersOfMember",param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getSellerUsersOfBuyerAdminId(Integer userId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getSellerUsersOfBuyerAdminId",param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllBuyerIdsBySellerIds(List<Integer> sellerIds, String datefrom, String dateto) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("sellerIds", sellerIds);
		param.put("dateFrom", datefrom);
		param.put("dateTo", dateto);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getAllBuyerIdsBySellerIds",param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllSellerIdsByBuyerIds(List<Integer> buyerIds, String datefrom, String dateto) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("buyerIds", buyerIds);
		param.put("dateFrom", datefrom);
		param.put("dateTo", dateto);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getAllSellerIdsByBuyerIds",param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getMembers(List<Integer> adminIdList, List<Integer> dealingRelationIdList, String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("adminIdList", adminIdList);
		param.put("dealingRelationIdList", dealingRelationIdList);
		param.put("dateFrom", dateFrom);
		return getSqlMapClientTemplate().queryForList("DealingPattern.getAllMembers", param);
	}
}