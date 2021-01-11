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
 * Jul 23, 2010		raquino		
 */
package com.freshremix.webapp.controller.sort.companybuyers;

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
import com.freshremix.service.CompanyBuyersSortService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.util.SessionHelper;

/**
 * @author raquino
 *
 */
public class CompanySortInit implements Controller {
	
	private CompanyBuyersSortService companybuyersSortService;
	private DealingPatternService dealingPatternService;
	
	public void setCompanybuyersSortService(
			CompanyBuyersSortService companybuyersSortService) {
		this.companybuyersSortService = companybuyersSortService;
	}

	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println("loading user company sort preference...");
		Map<String, Object> model = new HashMap<String, Object>();
		
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		List<Integer> sellerIds = new ArrayList<Integer>();
		Long userRoleId = user.getRole().getRoleId();
		
		if (userRoleId.equals(RoleConstants.ROLE_SELLER_ADMIN)) {
			sellerIds = dealingPatternService.getSellerIdsOfSellerAdminId(user.getUserId());
		}
		else if (userRoleId.equals(RoleConstants.ROLE_SELLER)) {
			sellerIds.add(user.getUserId());
		}
		
		
		List<CompanySort> companySorts = new ArrayList<CompanySort>();
		if (userRoleId.equals(RoleConstants.ROLE_BUYER_ADMIN)) {
			CompanySort companySort = new CompanySort();
			companySort.setCompany(user.getCompany());
			companySorts.add(companySort);
		}else{
			companySorts = companybuyersSortService.getSortedCompanyForUserPref(sellerIds, user.getUserId());
		}
		
		SessionHelper.setAttribute(request, SessionParamConstants.SORTED_COMPANIES, companySorts);
		model.put("companySorts", companySorts);
		
		for (int i=0; i<companySorts.size(); i++) {
			CompanySort companySort = (CompanySort) companySorts.get(i);
			//Integer userId = companySort.getUser().getUserId();
			Integer companyId = companySort.getCompany().getCompanyId();
			System.out.println("userId:[" + user.getUserId() + "]");
			System.out.println("companyId:[" + companyId + "]");
		}
		
		ModelAndView mav = new ModelAndView("json", model);
	    return mav;
	}
}
