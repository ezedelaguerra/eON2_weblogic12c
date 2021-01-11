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
 * Feb 17, 2010		jabalunan		
 */
package com.freshremix.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.CompanyDealingPatternDao;
import com.freshremix.model.AdminCompanyInformation;
import com.freshremix.model.UserDealingPattern;
import com.freshremix.util.RolesUtil;

public class CompanyDealingPatternDaoImpl extends SqlMapClientDaoSupport
		implements CompanyDealingPatternDao {

	@Override
	public Integer insertCompanyDealingPattern(String companyId, String selectedCompId, Integer relationId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("companyId", companyId);
		map.put("selectedCompId", selectedCompId);
		map.put("relationId", relationId.toString());
		Integer iResult =  (Integer) getSqlMapClientTemplate().insert("CompanyInfo.insertCompanyDealingPattern", map);
		return iResult;
	}

	@Override
	public Integer resetCDPByCompanyId(String companyId, String isActive) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId.trim());
		map.put("isActive", isActive.trim());
		return getSqlMapClientTemplate().delete("CompanyInfo.resetCDPByCompanyId",map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDealingPattern> getAllUserDealingPattern(String companyId, Integer role,String userName,String companyType) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserDealingPattern> userDealingList = new ArrayList<UserDealingPattern>();
		map.put("roleId", role);
		map.put("companyId", companyId);
		map.put("userName", userName);
		
		if(companyType.equalsIgnoreCase("buyer")){
			userDealingList = getSqlMapClientTemplate().queryForList("UserDPInfo.getAllUserBuyerDealingPattern", map);
		}else{
			userDealingList = getSqlMapClientTemplate().queryForList("UserDPInfo.getAllUserDealingPattern", map);
		}
		
		return userDealingList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDealingPattern> getAllSelectedUserDealingPattern(String userId, Integer roleId) {
		if (RolesUtil.isBuyerByRoleId(roleId))
			return getSqlMapClientTemplate().queryForList("UserDPInfo.getAllSelectedBuyerUserDealingPattern", Integer.valueOf(userId));
		else if (RolesUtil.isSellerByRoleId(roleId))
			return getSqlMapClientTemplate().queryForList("UserDPInfo.getAllSelectedSellerUserDealingPattern", Integer.valueOf(userId));
		else
			return getSqlMapClientTemplate().queryForList("UserDPInfo.getAllSelectedAdminUserDealingPattern", Integer.valueOf(userId));
		
	}

	@Override
	public Integer insertUserDealingPattern(String cdpId, String user_01, String user_02, String dealingRelationId, String startDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compDPId", cdpId);
		map.put("user_01", user_01);
		map.put("user_02", user_02);
		map.put("dealingRelationId", dealingRelationId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		Integer iResult = (Integer) getSqlMapClientTemplate().insert("UserDPInfo.insertUserDealingPattern", map);
		return iResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminCompanyInformation> searchCompDPByName(String companyId, String companyName, String companyType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("companyName", companyName);
		map.put("companyType", companyType);
		return getSqlMapClientTemplate().queryForList("CompanyInfo.searchCompDPByName", map);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getCDPByCompanyId(String sellerCompanyId,String buyerCompanyId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("company01", sellerCompanyId);
		param.put("company02", buyerCompanyId);
		return getSqlMapClientTemplate().queryForList("CompanyInfo.getCDPByCompanyId", param);
	}

	@Override
	public Integer checkIfSelectedUDP(Integer companyId, Integer selectedCompanyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("selectedCompanyId", selectedCompanyId);
		Integer iResult = (Integer) getSqlMapClientTemplate().queryForObject("UserDPInfo.checkIfSelectedUDP", map);
		return iResult;
	}

	@Override
	public Integer checkIfCDPExist(String companyId, String selectedId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId.trim());
		map.put("selectedId", selectedId.trim());
		Integer iResult = (Integer) getSqlMapClientTemplate().queryForObject("CompanyInfo.checkIfCDPExist", map);
		return iResult;
	}

	@Override
	public Integer updateCDPActive(String companyId, String selectedId, String isActive) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId.trim());
		map.put("selectedId", selectedId.trim());
		map.put("isActive", isActive.trim());
		Integer iResult = (Integer) getSqlMapClientTemplate().update("CompanyInfo.updateCDPActive", map);
		return iResult;
	}

	@Override
	public Integer getCDPIdFromCompany(String companyId, String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId.trim());
		map.put("userId", userId.trim());
		Integer iResult = (Integer) getSqlMapClientTemplate().queryForObject("CompanyInfo.getCDPIdFromCompany", map);
		return iResult;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CompanyDealingPatternDao#getAllUnderAdminUserDealingPattern(java.lang.String, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserDealingPattern> getAllUnderAdminUserDealingPattern(String companyId, String userId, Integer role) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", role);
		map.put("companyId", companyId);
		map.put("userId", userId);
		return getSqlMapClientTemplate().queryForList("UserDPInfo.getAllUnderAdminUserDealingPattern", map);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CompanyDealingPatternDao#insertAdminDealingPattern(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Integer insertAdminDealingPattern(String admin_id, String member_id, String dpRelationId, String startDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("admin_id", admin_id);
		map.put("member_id", member_id);
		map.put("dpRelationId", dpRelationId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		Integer iResult = (Integer) getSqlMapClientTemplate().insert("UserDPInfo.insertAdminDealingPattern", map);
		return iResult;
	}

	@Override
	public Integer updateDealingPatternExpiration(Integer udpId, String expiryDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("udpId", udpId);
		map.put("expiryDate", expiryDate);
		
		return getSqlMapClientTemplate().update("UserDPInfo.updateDealingPatternExpiration", map);
	}

	@Override
	public Integer updateAdminDealingPatternExpiration(Integer adminMemId,
			String expiryDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adminMemId", adminMemId);
		map.put("expiryDate", expiryDate);
		
		return getSqlMapClientTemplate().update("UserDPInfo.updateAdminDealingPatternExpiration", map);
	}
}
