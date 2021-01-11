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
 * Feb 24, 2010		nvelasquez		
 */
package com.freshremix.util;

import java.util.ArrayList;
import java.util.List;

import com.freshremix.model.Company;
import com.freshremix.service.CompanyInformationService;


/**
 * @author nvelasquez
 *
 */
public class CompanyUtil {
	
	private static CompanyInformationService companyInfoService;
	
	public void setCompanyInfoService(CompanyInformationService companyInfoService) {
		CompanyUtil.companyInfoService = companyInfoService;
	}

	public static List<Company> toCompanyObjs(List<Integer> companyIdList) {
		
		List<Company> companyObjs = new ArrayList<Company>();
		for (Integer companyId : companyIdList) {
			Company companyObj = companyInfoService.getCompanyById(companyId);
			companyObjs.add(companyObj);
		}
		
		return companyObjs;
	}


	public static List<Company> toBuyerCompanyObjs(List<Integer> companyIdList) {
		
		List<Company> companyObjs = new ArrayList<Company>();
		for (Integer companyId : companyIdList) {
			Company companyObj = companyInfoService.getBuyerCompanyById(companyId);
			companyObjs.add(companyObj);
		}
		
		return companyObjs;
	}

}
