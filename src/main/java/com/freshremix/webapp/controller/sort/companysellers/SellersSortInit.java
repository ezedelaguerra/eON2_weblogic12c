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
 * Nov 16, 2011		raquino		
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
import com.freshremix.model.BuyersSort;
import com.freshremix.model.SellersSort;
import com.freshremix.model.User;
import com.freshremix.service.CompanyBuyersSortService;
import com.freshremix.service.CompanySellersSortService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author raquino
 *
 */
public class SellersSortInit implements Controller {
	
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
		String companyIdStr = request.getParameter("companyId");
		Integer companyId = Integer.parseInt(companyIdStr);
		
		List<SellersSort> sellersSortList = 
			companySellersSortService.getSortedSellersForUserPref(user, companyId);
		
		model.put("sellersSorts", sellersSortList);
		
		ModelAndView mav = new ModelAndView("json", model);
	    return mav;
	}
}
