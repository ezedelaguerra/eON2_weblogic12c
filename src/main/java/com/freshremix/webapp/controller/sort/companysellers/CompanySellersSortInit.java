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
 * Nov 11, 2011		raquino		
 */
package com.freshremix.webapp.controller.sort.companysellers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.CompanySort;
import com.freshremix.model.User;
import com.freshremix.service.CompanySellersSortService;
import com.freshremix.util.SessionHelper;

/**
 * @author raquino
 *
 */
public class CompanySellersSortInit implements Controller {
	
	private CompanySellersSortService companySellersSortService;
	
	public void setCompanySellersSortService(
			CompanySellersSortService companySellersSortService) {
		this.companySellersSortService = companySellersSortService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		Map<String, Object> model = new HashMap<String, Object>();
		
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		List<CompanySort> companySorts = new ArrayList<CompanySort>();
		
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
			CompanySort companySort = new CompanySort();
			companySort.setCompany(user.getCompany());
			companySorts.add(companySort);
		}else{
			companySorts = companySellersSortService.getSortedCompanyForUserPref(user);
		}
		
		SessionHelper.setAttribute(request, SessionParamConstants.SORTED_COMPANIES, companySorts);
		model.put("companySorts", companySorts);
		
		ModelAndView mav = new ModelAndView("json", model);
	    return mav;
	}

}
