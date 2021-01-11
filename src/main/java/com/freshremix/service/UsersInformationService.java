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
package com.freshremix.service;

import java.util.List;
import java.util.Map;

import com.freshremix.exception.ServiceException;
import com.freshremix.model.AdminUsers;
import com.freshremix.model.User;
import com.freshremix.model.UserDealingPattern;

public interface UsersInformationService {
	Integer deleteUserById(String userId);
	List<AdminUsers> getAllUsersByCompanyId(Integer companyId);
	AdminUsers getAllUsersByUserId(Integer userId);
	Integer insertUser(AdminUsers admUsers, User user) throws ServiceException;
	Integer checkUserIfExist(String userName, String companyId);
	User getUserById(Integer userId);
	List<User> getUserById(List<Integer> userId);
	User getUserAndCompanyById(Integer userId);
	Integer getCompanyIdByUserId(String userId);
	User getUserByCompanyId(Integer userId, Integer companyId);
	Integer updateUserPassword(String userId, String companyId, String password);
	List<String> getUsernameByUserId(String userId);
	Integer checkIfUDPExist(String userId, String selectedUserId);
	Integer checkIfUserAdminExist(String adminId, String memberId);
	Integer updateUDPActive(String userId, String selectedUserId, String expiryDateFrom, String expiryDateTo,String isActive);
	Integer updateActiveUserAdmin(String adminId, String memberId, String startDate, String endDate,String isActive);
	Integer resetuDPByCompanyId(String userId, Integer roleId);
	Integer resetAdminDealingPattern(String userId);	
	List<UserDealingPattern> searchUserDPById(String companyId, String userId, String userName);
	Integer updateUserDetails(User user);
	List<String> getListBuyerNamesById(List<Integer> buyerIds);
	Map<Integer, User> mapUserNames(List<Integer> buyerIds);
	List<String> getUserBuyersByCompanyIds(List<Integer> selectedSellerIds, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<String> getUsersByAdminId(Integer userId, Integer dealingRelationId, String dateFrom, String dateTo);
	List<User> getSellerUsersBySelectedBuyerId(List<Integer> selectedBuyerId, String dateFrom, String dateTo);
	void deleteAdminDealingPattern(String adminId, String memberId, String startDate);
	void deleteUserDealingPattern(String userId, String selectedUserId, String startDate);
	Integer updateUserById(AdminUsers admUsers, User user) throws ServiceException;
	List<Integer> getNonCompanyMembersFromList(Integer companyId,
			List<Integer> buyerList);

}