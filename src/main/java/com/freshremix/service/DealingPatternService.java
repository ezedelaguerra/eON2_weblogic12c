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

package com.freshremix.service;

import java.util.List;
import java.util.Map;

import com.freshremix.model.AdminMember;
import com.freshremix.model.Company;
import com.freshremix.model.User;

public interface DealingPatternService {

	List<Company> getBuyerCompaniesBySellerCompanyIds (Integer userCompanyId);
	List<Company> getSellerCompaniesByBuyerCompanyIds (Integer userCompanyId);
	List<User> getUserSellersBySellerCompanyIds (Integer userId, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<User> getUserBuyersByBuyerCompanyIds (Integer userId, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<User> getMembersByAdminId (Integer userId, Integer dealingRelationId, String dateFrom, String dateTo);
	List<Company> getBuyerCompaniesByUserSellerIds (List<Integer> userId, String dateFrom, String dateTo);
	List<User> getUserBuyersByUserSellersAndBuyerCompanyIds (List<Integer> selectedSellerIds, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<Company> getSellerCompaniesByUserBuyerIds (List<Integer> selectedBuyerIds, String dateFrom, String dateTo);
	List<User> getUserSellersByUserBuyersAndSellerCompanyIds (List<Integer> selectedBuyerIds, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	
	Map<String, Map<String, List<Integer>>> getSellerToBuyerDPMap (List<Integer> sellerIds, String dateFrom, String dateTo);
	Map<String, Map<String, List<Integer>>> getBuyerToSellerDPMap (Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap);
	
	List<Integer> getSellerIdsOfBuyerId(Integer userId);
	List<Integer> getSellerIdsOfSellerAdminId(Integer userId);
	List<Integer> getSellerIdsOfBuyerAdminId(Integer userId);
	List<Integer> getAdminUsersOfMember(Integer userId,Integer relation);
	List<User> getSellerUsersOfBuyerAdminId(Integer userId);
	List<User> getAllBuyerIdsBySellerIds(List<Integer> sellerIds, String datefrom, String dateto);
	List<User> getAllSellerIdsByBuyerIds(List<Integer> buyerIds, String datefrom, String dateto);
	
	Map<String, Map<String, List<Integer>>> getDealingPatternMap(List<String> date, List<Integer> sellerList, List<Integer> buyerList);
	
	// ENHANCEMENT START 20120725: Lele - Redmine 131
	List<User> getUserBuyersByBuyerCompanyIds2 (Integer userId, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<User> getMembersByAdminId2 (Integer userId, Integer dealingRelationId, String dateFrom, String dateTo);
	List<User> getUserBuyersByUserSellersAndBuyerCompanyIds2 (List<Integer> selectedSellerIds, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<User> getUserSellersBySellerCompanyIds2 (Integer userId, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	List<User> getUserSellersByUserBuyersAndSellerCompanyIds2 (List<Integer> selectedBuyerIds, List<Integer> selectedCompanyIds, String dateFrom, String dateTo);
	// ENHANCEMENT END 20120725:
	
	List<AdminMember> getMembersByAdminIdWithStartEndDates(Integer userId,
			Integer dealingRelationId, String dateFrom, String dateTo);
}
