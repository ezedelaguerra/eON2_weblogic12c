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
import com.freshremix.model.BuyersSort;
import com.freshremix.model.CompanySort;
import com.freshremix.model.User;
import com.freshremix.service.CompanyBuyersSortService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author raquino
 *
 */
public class BuyersSortInit implements Controller {
	
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

		System.out.println("loading user buyer sort preference...");
		Map<String, Object> model = new HashMap<String, Object>();
		
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		String companyId = request.getParameter("companyId");
		List<Integer> sellerIds = new ArrayList<Integer>();
		
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)) {
			sellerIds = OrderSheetUtil.toList(user.getUserId().toString());
		}
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
			sellerIds = dealingPatternService.getSellerIdsOfSellerAdminId(user.getUserId());
		}
		
		List<BuyersSort> buyersSortList = new ArrayList<BuyersSort>();
		
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)) {
			buyersSortList = companybuyersSortService.getSortedMembersByBuyerAdminId(user.getUserId());
		}else{
			buyersSortList = companybuyersSortService.getSortedBuyersForUserPref(sellerIds, 
					user.getUserId(), Integer.parseInt(companyId));
		}
		
		model.put("buyersSorts", buyersSortList);
		
		for (int i=0; i<buyersSortList.size(); i++) {
			BuyersSort buyersSort = (BuyersSort) buyersSortList.get(i);
			//Integer userId = companySort.getUser().getUserId();
			Integer buyerId = buyersSort.getBuyer().getUserId();
			System.out.println("userId:[" + user.getUserId() + "]");
			System.out.println("buyerId:[" + buyerId + "]");
		}
		
		ModelAndView mav = new ModelAndView("json", model);
	    return mav;
	}
}
