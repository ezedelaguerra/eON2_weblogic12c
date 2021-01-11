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

package com.freshremix.dao;

import java.util.List;

import com.freshremix.model.AdminMember;
import com.freshremix.model.Company;
import com.freshremix.model.User;

public interface DealingPatternDao {
	
	List<Company> getBuyerCompaniesBySellerCompanyIds (Integer userCompanyId);
	List<Company> getSellerCompaniesByBuyerCompanyIds (Integer userCompanyId);
	List<User> getUserSellersBySellerCompanyIds (Integer userId, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<User> getUserBuyersByBuyerCompanyIds (Integer userId, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<User> getMembersByAdminId (Integer userId, Integer dealingRelationId, String dateFrom, String dateTo);
	List<Company> getBuyerCompaniesByUserSellerIds (List<Integer> userId, String dateFrom, String dateTo);
	List<User> getUserBuyersByUserSellersAndBuyerCompanyIds (List<Integer> selectedSellerIds, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<Company> getSellerCompaniesByUserBuyerIds (List<Integer> selectedBuyerIds, String dateFrom, String dateTo);
	List<User> getUserSellersByUserBuyersAndSellerCompanyIds (List<Integer> selectedBuyerIds, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<Integer> getSellerIdsOfBuyerId(Integer userId);
	List<Integer> getSellerIdsOfSellerAdminId(Integer userId);
	List<Integer> getSellerIdsOfBuyerAdminId(Integer userId);
	List<Integer> getAdminUsersOfMember(Integer userId,Integer relation);
	List<User> getSellerUsersOfBuyerAdminId(Integer userId);
	List<User> getAllBuyerIdsBySellerIds(List<Integer> sellerIds, String dateto, String datefrom);
	List<User> getAllSellerIdsByBuyerIds(List<Integer> buyerIds, String datefrom, String dateto);
	
	/**
	 * Retrieves user members (buyer and seller users) give the admin Id (SA/BA)
	 * @param adminIdList
	 * @param dealingRelationIdList
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	List<User> getMembers(List<Integer> adminIdList, List<Integer> dealingRelationIdList, String dateFrom, String dateTo);
	
	// ENHCANCEMENT START 20120725: Lele - Redmine 131
	List<User> getUserBuyersByBuyerCompanyIds2 (Integer userId, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<User> getMembersByAdminId2(Integer userId, Integer dealingRelationId, String dateFrom, String dateTo);
	List<User> getUserBuyersByUserSellersAndBuyerCompanyIds2(List<Integer> selectedSellerIds, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<User> getUserSellersBySellerCompanyIds2 (Integer userId, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<User> getUserSellersByUserBuyersAndSellerCompanyIds2 (List<Integer> selectedBuyerIds, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	// ENHANCEMENT END 20120725:
	List<AdminMember> getMembersByAdminIdWithStartEndDates(Integer userId,
			Integer dealingRelationId, String dateFrom, String dateTo);
}