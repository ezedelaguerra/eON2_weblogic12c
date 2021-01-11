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
package com.freshremix.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freshremix.constants.RoleConstants;
import com.freshremix.dao.UserDao;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.AdminUsers;
import com.freshremix.model.EONUserPreference;
import com.freshremix.model.User;
import com.freshremix.model.UserDealingPattern;
import com.freshremix.model.UsersTOS;
import com.freshremix.service.CategoryService;
import com.freshremix.service.RolesService;
import com.freshremix.service.TOSUserService;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.service.UsersInformationService;

public class UsersInformationServiceImpl implements UsersInformationService {
	private UserDao usersInfoDao;
	private TOSUserService tosUserService;
	private CategoryService categoryService;
	private RolesService roleService;
	private UserPreferenceService userPreferenceService;

	public void setTosUserService(TOSUserService tosUserService) {
		this.tosUserService = tosUserService;
	}

	public void setUserPreferenceService(
			UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setUsersInfoDao(UserDao usersInfoDao) {
		this.usersInfoDao = usersInfoDao;
	}

	public void setRoleService(RolesService roleService) {
		this.roleService = roleService;
	}

	@Override
	public List<AdminUsers> getAllUsersByCompanyId(Integer companyId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("companyId", companyId.toString());

		return usersInfoDao.getAllUsersByCompanyId(param);
	}

	@Override
	public AdminUsers getAllUsersByUserId(Integer userId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId.toString());

		AdminUsers admusers = (AdminUsers) usersInfoDao
				.getAllUsersByUserId(param);
		if (admusers.getRoleId() == RoleConstants.ROLE_SELLER.intValue()) {
			admusers.setCategoryList(categoryService
					.getCategoryListByUserId(admusers.getUserId().toString()));
		} else {
			admusers.setCategoryList(categoryService.getSortedCategory(userId));
		}
		admusers.setRolesList(roleService.getRolesById(admusers.getRoleId()
				.toString()));
		EONUserPreference userPref = userPreferenceService
				.getUserPreference(admusers.getUserId());
		if (userPref == null)
			admusers.setUnfinalizeReceived("0");
		else
			admusers.setUnfinalizeReceived(userPref.getUnfinalizeReceived() == null ? "0"
					: userPref.getUnfinalizeReceived());
		return admusers;
	}

	@Override
	public Integer insertUser(AdminUsers admUsers, User user)
			throws ServiceException {
		Map<String, String> param = new HashMap<String, String>();
		// param.put("userId", admUsers.getUserId().toString());
		param.put("userName", admUsers.getUserName());
		param.put("password", admUsers.getPassword());
		param.put("name", admUsers.getName());
		param.put("shortName", admUsers.getShortName());
		param.put("companyId", admUsers.getCompanyId());
		param.put("roleId", admUsers.getRoleId().toString());
		param.put("address1", admUsers.getAddress1());
		param.put("address2", admUsers.getAddress2());
		param.put("address3", admUsers.getAddress3());
		param.put("mobileNumber", admUsers.getMobileNumber());
		param.put("telNumber", admUsers.getTelNumber());
		param.put("faxNumber", admUsers.getFaxNumber());
		param.put("mobileEmail", admUsers.getMobileEmail());
		param.put("pcEmail", admUsers.getPcEmail());
		param.put("comments", admUsers.getComments());
		param.put("useBms", admUsers.getUseBms() == "true" ? "1" : "0");
		param.put("unfinalizeReceived", admUsers.getUnfinalizeReceived()
				.equals("true") ? "1" : "0");
		param.put("enableCalendarHighlight", admUsers.getEnableCalendarHighlight());
		// param.put("tosFlag", admUsers.getTosFlag());

		Integer userId = usersInfoDao.insertUser(param);
		if (userId != null) {
			UsersTOS usersTOS = new UsersTOS();
			usersTOS.setUserId(userId);
			usersTOS.setFlag(admUsers.getTosFlag());
			usersTOS.setFlagSetBy(user.getUserName());

			tosUserService.adminSaveUsersTOS(usersTOS);
		}
		return userId;
	}

	@Override
	public Integer deleteUserById(String userId) {
		tosUserService.deleteUsersTOS(new Integer(userId));
		return usersInfoDao.deleteUserById(userId);
	}

	@Override
	public Integer updateUserById(AdminUsers admUsers, User user)
			throws ServiceException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", admUsers.getUserId().toString());
		param.put("userName", admUsers.getUserName());
		param.put("password", admUsers.getPassword());
		param.put("name", admUsers.getName());
		param.put("shortName", admUsers.getShortName());
		param.put("companyId", admUsers.getCompanyId());
		param.put("roleId", admUsers.getRoleId().toString());
		param.put("address1", admUsers.getAddress1());
		param.put("address2", admUsers.getAddress2());
		param.put("address3", admUsers.getAddress3());
		param.put("mobileNumber", admUsers.getMobileNumber());
		param.put("telNumber", admUsers.getTelNumber());
		param.put("faxNumber", admUsers.getFaxNumber());
		param.put("mobileEmail", admUsers.getMobileEmail());
		param.put("pcEmail", admUsers.getPcEmail());
		param.put("comments", admUsers.getComments());
		param.put("useBms", admUsers.getUseBms() == "true" ? "1" : "0");
		param.put("unfinalizeReceived", admUsers.getUnfinalizeReceived()
				.equals("true") ? "1" : "0");
		param.put("enableCalendarHighlight", admUsers.getEnableCalendarHighlight());

		UsersTOS usersTOS = new UsersTOS();
		usersTOS.setUserId(admUsers.getUserId());
		usersTOS.setFlag(admUsers.getTosFlag());
		usersTOS.setFlagSetBy(user.getUserName());

		tosUserService.adminSaveUsersTOS(usersTOS);

		return usersInfoDao.updateUserById(param);
	}

	@Override
	public Integer checkUserIfExist(String userName, String companyId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uName", userName);
		// temporarily removed by jr
		// param.put("companyId", companyId);
		// end:jr
		return usersInfoDao.checkUserIfExist(param);
	}

	@Override
	public User getUserById(Integer userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return usersInfoDao.getUserById(param);
	}

	@Override
	public List<User> getUserById(List<Integer> userId) {
		return usersInfoDao.getUserById(userId);
	}

	public User getUserAndCompanyById(Integer userId) {
		return usersInfoDao.getUserById2(userId);
	}

	@Override
	public User getUserByCompanyId(Integer userId, Integer companyId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("companyId", companyId);
		return usersInfoDao.getUserByCompanyId(param);
	}

	@Override
	public Integer updateUserPassword(String userId, String userName,
			String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("userName", userName);
		map.put("password", password);
		return usersInfoDao.updateUserPassword(map);
	}

	@Override
	public Integer getCompanyIdByUserId(String userId) {
		return usersInfoDao.getCompanyIdByUserId(userId);
	}

	@Override
	public List<String> getUsernameByUserId(String userId) {
		return usersInfoDao.getUsernameByUserId(userId);
	}

	@Override
	public Integer checkIfUDPExist(String seller, String buyer) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", seller);
		map.put("selectedUserId", buyer);
		return usersInfoDao.checkIfUDPExist(seller, buyer);
	}

	@Override
	public Integer updateUDPActive(String userId, String selectedUserId,
			String expiryDateFrom, String expiryDateTo, String isActive) {
		return (Integer) usersInfoDao.updateUDPActive(userId, selectedUserId,
				expiryDateFrom, expiryDateTo, isActive);
	}

	@Override
	public Integer resetuDPByCompanyId(String userId, Integer roleId) {
		return usersInfoDao.resetuDPByCompanyId(userId, roleId);
	}

	@Override
	public List<UserDealingPattern> searchUserDPById(String companyId,
			String userId, String userName) {
		return usersInfoDao.searchUserDPById(companyId, userId, userName);
	}

	@Override
	public Integer checkIfUserAdminExist(String adminId, String memberId) {
		return usersInfoDao.checkIfUserAdminExist(adminId, memberId);
	}

	@Override
	public Integer updateActiveUserAdmin(String adminId, String memberId,
			String startDate, String endDate, String isActive) {
		return usersInfoDao.updateActiveUserAdmin(adminId, memberId, startDate,
				endDate, isActive);
	}

	@Override
	public Integer resetAdminDealingPattern(String userId) {
		return usersInfoDao.resetAdminDealingPattern(userId);
	}

	@Override
	public Integer updateUserDetails(User user) {
		return usersInfoDao.updateUserDetails(user);
	}

	@Override
	public List<String> getListBuyerNamesById(List<Integer> userIds) {
		return usersInfoDao.getListBuyerNamesById(userIds);
	}

	@Override
	public Map<Integer, User> mapUserNames(List<Integer> buyerIds) {
		return usersInfoDao.mapUserNames(buyerIds);
	}

	@Override
	public List<String> getUserBuyersByCompanyIds(
			List<Integer> selectedSellerIds, List<Integer> selectedCompanyIds,
			String dateFrom, String dateTo) {
		return usersInfoDao.getUserBuyersByCompanyIds(selectedSellerIds,
				selectedCompanyIds, dateFrom, dateTo);
	}

	@Override
	public List<String> getUsersByAdminId(Integer userId,
			Integer dealingRelationId, String dateFrom, String dateTo) {
		return usersInfoDao.getUsersByAdminId(userId, dealingRelationId,
				dateFrom, dateTo);
	}

	@Override
	public List<User> getSellerUsersBySelectedBuyerId(
			List<Integer> selectedBuyerId, String dateFrom, String dateTo) {
		return usersInfoDao.getSellerUsersBySelectedBuyerId(selectedBuyerId,
				dateFrom, dateTo);
	}

	@Override
	public void deleteAdminDealingPattern(String adminId, String memberId,
			String startDate) {
		usersInfoDao.deleteAdminDealingPattern(adminId, memberId, startDate);

	}

	@Override
	public void deleteUserDealingPattern(String userId, String selectedUserId,
			String startDate) {
		usersInfoDao
				.deleteUserDealingPattern(userId, selectedUserId, startDate);

	}
	
	@Override
	public List<Integer> getNonCompanyMembersFromList(Integer companyId, List<Integer> buyerList){
		return usersInfoDao.getNonCompanyMembersFromList(companyId, buyerList);
	}
}
