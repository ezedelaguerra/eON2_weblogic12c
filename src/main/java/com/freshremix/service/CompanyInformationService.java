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

import com.freshremix.model.AdminCompanyInformation;
import com.freshremix.model.Company;

public interface CompanyInformationService {
	List<AdminCompanyInformation> getAllCompanyName();
	List<AdminCompanyInformation> getAllCompanyNameByDealingPattern(String companyType);
	List<AdminCompanyInformation> getCompanySelectedDealingPattern(String companyId,String roleType);
	List<AdminCompanyInformation> getCompanyNameByWildCard(String companyName);
	AdminCompanyInformation getCompanyDetailsByCompanyId(Integer companyId);
	Integer insertNewCompany(AdminCompanyInformation admnCompanyInfo);
	Integer updateCompany(AdminCompanyInformation admnCompanyInfo);
	Integer checkCompanyIfExists(String companyName);
	Company getCompanyById(Integer companyId);
	Company getBuyerCompanyById(Integer companyId);
	boolean hasOrder(Integer roleId, Integer userId1, Integer userId2, String dateFrom, String dateTo);
	boolean hasOrder(Integer sellerId, Integer buyerId, String dateFrom, String dateTo);
}
