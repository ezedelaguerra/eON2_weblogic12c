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
package com.freshremix.dao;

import java.util.List;
import java.util.Map;

import com.freshremix.model.AdminUsers;
import com.freshremix.model.User;
import com.freshremix.model.UserDealingPattern;
import com.ibatis.sqlmap.client.event.RowHandler;

public interface UserDao {
	List<User> getAllUsers(List<Integer> sellerIds);
	Integer deleteUserById(String userId);
	Integer updateUserById(Map<String, String> param);
	Integer insertUser(Map<String, String> param);
	List<AdminUsers> getAllUsersByCompanyId(Map<String, String> param);
	AdminUsers getAllUsersByUserId(Map<String, String> param);
	User getUserById(Map<String, Object> param);
	User getUserById(Integer userId);
	User getUserById2(Integer userId);
	List<User> getUserById(List<Integer> userId);
	User getUserByShortName(String shortName, Integer companyId);
	User getUserByCompanyId(Map<String, Object> param);
	Integer getCompanyIdByUserId(String userId);
	Integer checkUserIfExist(Map<String, Object> param);
	Integer updateUserPassword(Map<String, Object> param);
	List<String> getUsernameByUserId(String userId);
	Integer checkIfUDPExist(String userId, String selectedUserId);
	Integer checkIfUserAdminExist(String adminId, String member_id);
	Integer updateUDPActive(String userId, String selectedUserId, String expiryDateFrom, String expiryDateTo,String isActive);
	Integer updateActiveUserAdmin(String adminId, String memberId, String startDate, String endDate,String isActive);
	Integer resetuDPByCompanyId(String userId, Integer roleId);
	Integer resetAdminDealingPattern(String userId);
	List<UserDealingPattern> searchUserDPById(String companyId, String userId, String userName);
	Integer updateUserDetails(User user);
	List<User> getUsersByCompanyId(List<Integer> companyId);
	List<User> getUserRoleOnlyByCompanyId(List<Integer> companyId);
	List<User> getSellerAdminRoleByCompanyId(List<Integer> companyId);
	List<User> getBuyerAdminRoleByCompanyId(List<Integer> companyId);
	User getUserByName(String name);
	List<User> getUserByEmail(String email);
	List<String> getListBuyerNamesById(List<Integer> buyerIds);
	Map<Integer, User> mapUserNames(List<Integer> buyerIds);
	List<String> getUserBuyersByCompanyIds(List<Integer> selectedSellerIds, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<String> getUsersByAdminId(Integer userId, Integer dealingRelationId, String dateFrom, String dateTo);
	List<User> getSellerUsersBySelectedBuyerId(List<Integer> selectedBuyerId, String dateFrom, String dateTo);
	void deleteAdminDealingPattern(String adminId, String memberId, String startDate);
	void deleteUserDealingPattern(String userId, String selectedUserId, String startDate);
	User getUserResultByName(String name);
	User getUserByUsername(String username);
	
	/**
	 * Retrieves list of User given company id
	 * @param companyId
	 * @return
	 */
	List<User> getUser(Integer companyId);
	
	/**
	 * Retrieves all the Non Admin Users (Role id=1,2,3,4) and delegates the row
	 * handling to another class.
	 * 
	 * When creating a RowHandler class please make sure that any ibatis
	 * package/classes does not go beyond the DAO level packages so that
	 * dependencies on Ibatis does not leak out to the service class.
	 * 
	 * See DelegatingRowHandler for a sample RowHandler.
	 * 
	 * @param rowHandler
	 */
	void getAllNonAdminUsers(RowHandler rowHandler);
	
	/**
	 * Retrieves all user ids that are not part of the company from the list
	 * @param companyId
	 * @param buyerList
	 * @return
	 */
	List<Integer> getNonCompanyMembersFromList(Integer companyId,
			List<Integer> buyerList);
	
	
}
