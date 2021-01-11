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
import java.util.Map;

import com.freshremix.model.AdminCompanyInformation;
import com.freshremix.model.Company;


public interface CompanyInformationDao {
	List<AdminCompanyInformation> getAllCompanyName();
	List<AdminCompanyInformation> getAllCompanyNameByDealingPattern(String companyType);
	List<AdminCompanyInformation> getCompanySelectedDealingPattern(String companyId,String roleType);
	List<AdminCompanyInformation> getCompanyNameByWildCard(String companyName);
	AdminCompanyInformation getCompanyInformationByCompanyId(Map<String, String> param);
	Integer insertNewCompany(Map<String, String> param);
	Integer updateCompany(Map<String, String> param);
	Integer checkCompanyIfExists(String companyName);
	Company getCompanyById(Map<String, Object> param);
	Company getCompanyById(Integer companyId);
	Company getBuyerCompanyById(Map<String, Object> param);
	// ENHANCEMENT 20120725: Lele - Redmine 131
	List<Company> getCompanyList (List<Integer> companyId);
}