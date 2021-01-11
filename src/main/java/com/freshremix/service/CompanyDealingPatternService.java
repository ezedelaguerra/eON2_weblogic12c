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

import com.freshremix.model.AdminCompanyInformation;
import com.freshremix.model.UserDealingPattern;

public interface CompanyDealingPatternService {
	Integer insertCompanyDealingPattern(String companyId, String selectedCompId, Integer relationId);
	Integer resetCDPByCompanyId(String companyId, String isActive);
	Integer insertUserDealingPattern(String cdpId, String user_01, String user_02, String dealingRelationId, String startDate, String endDate);
	Integer insertAdminDealingPattern(String admin_id, String member_id, String dpRelationId, String startDate, String endDate);
	List<UserDealingPattern> getAllUserDealingPattern(String companyId, Integer role,String userName,String companyType);
	List<UserDealingPattern> getAllUnderAdminUserDealingPattern(String companyId, String userId, Integer role);
	List<UserDealingPattern> getAllSelectedUserDealingPattern(String userId, Integer roleId);
	List<AdminCompanyInformation> searchCompDPByName(String companyId, String companyName, String companyType);
	List<Integer> getCDPByCompanyId(String sellerCompanyId,String buyerCompanyId);
	Integer getCDPIdFromCompany(String companyId, String userId);
	Integer checkIfSelectedUDP(Integer companyId, Integer selectedCompanyId);
	Integer checkIfCDPExist(String companyId, String selectedId);
	Integer updateCDPActive(String companyId, String selectedId, String isActive);
	String getLastDealingPatternExpiration(Integer sellerId, Integer buyerId, String expiryDate);
	void updateDealingPatternExpiration(Integer udpId, Integer roleId, String expiryDate);
	Map<String, Object> saveUserDealingPattern(UserDealingPattern udp, String companyId, String userId, String roleName, Integer newRole, Integer roleId, List<List> obj, List<List> removedItems, Map<String, Object> model) throws Exception;
	
	public Boolean hasAnyDealingPattern(Integer companyId, String dateFrom);
}
