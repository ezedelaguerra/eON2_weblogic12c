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

import org.apache.commons.collections.CollectionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.UserDao;
import com.freshremix.model.AdminUsers;
import com.freshremix.model.EONUserPreference;
import com.freshremix.model.User;
import com.freshremix.model.UserDealingPattern;
import com.freshremix.util.CollectionUtil;
import com.freshremix.util.RolesUtil;
import com.ibatis.sqlmap.client.event.RowHandler;

public class UserDaoImpl extends SqlMapClientDaoSupport 
	implements UserDao {
	
	@SuppressWarnings("unchecked")
	/* (non-Javadoc)
	 * @see com.freshremix.dao.UserDao#getAllUsers(java.util.List)
	 */
	@Override
	public List<User> getAllUsers(List<Integer> sellerIds) {
		return getSqlMapClientTemplate().queryForList("User.getAllUsers", sellerIds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminUsers> getAllUsersByCompanyId(Map<String, String> param) {
		return getSqlMapClientTemplate().queryForList("AdminUsers.getAllUsersByCompanyId", param);
	}

	@Override
	public AdminUsers getAllUsersByUserId(Map<String, String> param) {
		return (AdminUsers) getSqlMapClientTemplate().queryForObject("AdminUsers.getAllUsersByUserId", param);
	}

	@Override
	public Integer insertUser(Map<String, String> param) {
		
		Integer iResult = (Integer) getSqlMapClientTemplate().queryForObject("AdminUsers.cntUsernameNameShortnameForInsert", param);
		if (iResult > 0) return null;
		try{
			Integer iRet = (Integer) getSqlMapClientTemplate().insert("AdminUsers.insertUserDetails", param);
			if(param.get("unfinalizeReceived") == "1"){
				EONUserPreference userPreference = new EONUserPreference();
				userPreference.setUserId(iRet);
				userPreference.setUnfinalizeReceived("1");
				getSqlMapClientTemplate().insert("UserPreference.insertUserPrefUnfinalizeReceived", userPreference);
			}
				
			return iRet;
		}catch(DataIntegrityViolationException e){
			return null;
		}
	}

	@Override
	public Integer deleteUserById(String userId) {
		Integer iResult = getSqlMapClientTemplate().delete("AdminUsers.deleteUserById", userId);
		return iResult;
	}

	@Override
	public Integer updateUserById(Map<String, String> param) {
		
		Integer iResult = (Integer) getSqlMapClientTemplate().queryForObject("AdminUsers.cntUsernameNameShortnameForUpdate", param);
		if (iResult > 0) return null; 
		try{
			iResult = getSqlMapClientTemplate().update("AdminUsers.updateUserById", param);
			EONUserPreference userPreference = new EONUserPreference();
			userPreference.setUserId(Integer.parseInt(param.get("userId")));
			userPreference.setUnfinalizeReceived(param.get("unfinalizeReceived"));
			int iRet = getSqlMapClientTemplate().update("UserPreference.updateUserPrefUnfinalizeReceived", userPreference);
			if(iRet==0 && param.get("unfinalizeReceived") == "1")
				getSqlMapClientTemplate().insert("UserPreference.insertUserPrefUnfinalizeReceived", userPreference);
			return iResult;
		}catch(DataIntegrityViolationException e){
			return null;
		}
		
	}

	@Override
	public Integer checkUserIfExist(Map<String, Object> param) {
		Integer iResult = (Integer) getSqlMapClientTemplate().queryForObject("AdminUsers.checkUserIfExist", param); 
		return iResult;
	}

	@Override
	public User getUserById(Map<String, Object> param) {
		return (User) getSqlMapClientTemplate().queryForObject("User.getUserById", param);
	}
	
	@Override
	public User getUserById(Integer userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return (User) getSqlMapClientTemplate().queryForObject("User.getUserById", param);
	}
	
	@Override
	public User getUserById2(Integer userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return (User) getSqlMapClientTemplate().queryForObject("User.getUserById2", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserById(List<Integer> userId) {
		
		List<List<?>> splitList = CollectionUtil.splitList(userId, 1000);
		List<User> returnList = new ArrayList<User>();

		for (List<?> list : splitList) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", list);
			List<User> queryResutList = getSqlMapClientTemplate().queryForList("User.getUserByIdList", param);
			if (CollectionUtils.isNotEmpty(queryResutList)) {
				returnList.addAll(queryResutList);
			}
		}
		
		return returnList;
	}
	
	@Override
	public User getUserByCompanyId(Map<String, Object> param) {
		return (User) getSqlMapClientTemplate().queryForObject("User.getUserByCompanyId", param);
	}

	@Override
	public Integer updateUserPassword(Map<String, Object> param) {
		int iRet = getSqlMapClientTemplate().update("User.updateUserPassword", param);
		return (Integer) iRet;
	}

	@Override
	public Integer getCompanyIdByUserId(String userId) {
		Integer iReturn = (Integer) getSqlMapClientTemplate().queryForObject("UserDPInfo.getCompanyIdByUserId", userId.trim());
		return iReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUsernameByUserId(String userId) {
		return getSqlMapClientTemplate().queryForList("UserDPInfo.getUsernameByUserId", userId);
	}

	@Override
	public Integer checkIfUDPExist(String seller, String buyer) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", seller);
		map.put("selectedUserId", buyer);
		Integer iResult = (Integer) getSqlMapClientTemplate().queryForObject("UserDPInfo.checkIfUDPExist", map);
		return iResult;
	}

	@Override
	public Integer updateUDPActive(String userId, String selectedUserId, String expiryDateFrom, 
			String expiryDateTo,String isActive) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("selectedUserId", selectedUserId);
		map.put("startDate", expiryDateFrom);
		map.put("endDate", expiryDateTo);
		map.put("isActive", isActive);
		Integer iResult = (Integer) getSqlMapClientTemplate().update("UserDPInfo.updateUDPActive", map);
		return iResult;
	}

	@Override
	public Integer resetuDPByCompanyId(String userId, Integer roleId) {
		if (RolesUtil.isBuyerByRoleId(roleId))
			return (Integer) getSqlMapClientTemplate().update("UserDPInfo.resetBuyerByCompanyId", userId);
		else
			return (Integer) getSqlMapClientTemplate().update("UserDPInfo.resetSellerByCompanyId", userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDealingPattern> searchUserDPById(String companyId, String userId, String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("companyId", companyId);
		map.put("userName", userName);
		return getSqlMapClientTemplate().queryForList("UserDPInfo.searchUserDPById",map);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.UserDao#checkIfAdminExist(java.lang.String, java.lang.String)
	 */
	@Override
	public Integer checkIfUserAdminExist(String adminId, String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adminId", adminId);
		map.put("memberId", memberId);
		return (Integer) getSqlMapClientTemplate().queryForObject("UserDPInfo.checkIfUserAdminExist",map);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.UserDao#updateActiveUserAdmin(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Integer updateActiveUserAdmin(String adminId, String memberId, String startDate, String endDate,String isActive) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adminId", adminId);
		map.put("memberId", memberId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("isActive", isActive);
		Integer iResult = (Integer) getSqlMapClientTemplate().update("UserDPInfo.updateActiveUserAdmin", map);
		return iResult;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.UserDao#resetAdminDealingPattern(java.lang.String)
	 */
	@Override
	public Integer resetAdminDealingPattern(String userId) {
		Integer iRet = (Integer) getSqlMapClientTemplate().update("UserDPInfo.resetAdminDealingPattern", userId);
		return iRet;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.UserDao#updateUserDetails(com.freshremix.model.User)
	 */
	@Override
	public Integer updateUserDetails(User user) {

		Integer iResult = getSqlMapClientTemplate().update("User.updateUserDetails", user);
		return iResult;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.UserDao#getUsersByCompanyId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByCompanyId(List<Integer> companyId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("companyId", companyId);
		return getSqlMapClientTemplate().queryForList("User.getUsersByCompanyId", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserRoleOnlyByCompanyId(List<Integer> companyId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("companyId", companyId);
		
		return getSqlMapClientTemplate().queryForList("User.getUserRoleOnlyByCompanyId", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getSellerAdminRoleByCompanyId(List<Integer> companyId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("companyId", companyId);
		
		return getSqlMapClientTemplate().queryForList("User.getSellerAdminRoleByCompanyId", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getBuyerAdminRoleByCompanyId(List<Integer> companyId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("companyId", companyId);
		
		return getSqlMapClientTemplate().queryForList("User.getBuyerAdminRoleByCompanyId", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.UserDao#getUserByName(java.lang.String)
	 */
	@Override
	public User getUserByName(String name) {
		return (User) getSqlMapClientTemplate().queryForObject("User.getUserByName", name);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.UserDao#getUserByEmail(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserByEmail(String email) {
		return getSqlMapClientTemplate().queryForList("User.getUserByEmail", email);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.UserDao#getUserByShortName(java.lang.String)
	 */
	@Override
	public User getUserByShortName(String shortName, Integer companyId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("shortName", shortName);
		param.put("companyId", companyId);
		return (User) getSqlMapClientTemplate().queryForObject("User.getUserByShortName", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getListBuyerNamesById(List<Integer> userIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userIds", userIds);
		return (List<String>) getSqlMapClientTemplate().queryForList("User.getListBuyerNamesById", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, User> mapUserNames(List<Integer> userIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userIds", userIds);
		return getSqlMapClientTemplate().queryForMap("User.mapUserNames", map, "userId");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUserBuyersByCompanyIds(List<Integer> selectedSellerIds, List<Integer> selectedCompanyIds, String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("selectedSellerIds", selectedSellerIds);
		param.put("selectedCompanyIds", selectedCompanyIds);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("User.getUserBuyersByCompanyIds",param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUsersByAdminId(Integer userId,
			Integer dealingRelationId, String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("dealingRelationId", dealingRelationId);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("User.getUsersByAdminId",param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getSellerUsersBySelectedBuyerId(
			List<Integer> selectedBuyerId, String dateFrom, String dateTo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("selectedBuyerIds", selectedBuyerId);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		return getSqlMapClientTemplate().queryForList("User.getSellerUsersBySelectedBuyerId", param);
	}

	@Override
	public void deleteAdminDealingPattern(String adminId, String memberId,
			String startDate) {
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("adminId", adminId);
		param.put("memberId", memberId);
		param.put("startDate", startDate);	
		getSqlMapClientTemplate().delete("UserDPInfo.deleteAdminDealingPattern", param);
	}

	@Override
	public void deleteUserDealingPattern(String userId, String selectedUserId,
			String startDate) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("selectedUserId", selectedUserId);
		param.put("startDate", startDate);	
		getSqlMapClientTemplate().delete("UserDPInfo.deleteUserDealingPattern", param);
		
	}

	@Override
	public User getUserResultByName(String name) {
		return (User) getSqlMapClientTemplate().queryForObject("User.getUserResultByName", name);
	}

	@Override
	public User getUserByUsername(String username) {
		return (User) getSqlMapClientTemplate().queryForObject("User.getUserByUsername", username);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUser(Integer companyId) {
		return (List<User>) getSqlMapClientTemplate().queryForList("User.getUserByCompanyIdOnly", companyId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.freshremix.dao.UserDao#getAllNonAdminUsers(com.ibatis.sqlmap.client.event.RowHandler)
	 */
	@Override
	public void getAllNonAdminUsers(RowHandler rowHandler){
		SqlMapClientTemplate sqlMapClientTemplate = getSqlMapClientTemplate();
		sqlMapClientTemplate.queryWithRowHandler("User.getAllNonAdminUsers", rowHandler);
	}
	
	@Override
	public List<Integer> getNonCompanyMembersFromList(Integer companyId, List<Integer> buyerList){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("userIds", buyerList);
		return (List<Integer>) getSqlMapClientTemplate().queryForList("User.getNonCompanyMembersFromList", param);

	}
}
