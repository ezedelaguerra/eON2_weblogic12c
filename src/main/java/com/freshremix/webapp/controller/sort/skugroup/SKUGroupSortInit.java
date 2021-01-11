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
 * May 31, 2010		nvelasquez		
 */
package com.freshremix.webapp.controller.sort.skugroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.CategoryConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.SKUGroupSort;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.SKUGroupSortService;
import com.freshremix.util.SessionHelper;

/**
 * @author nvelasquez
 *
 */
public class SKUGroupSortInit implements Controller {
	
	private SKUGroupSortService skuGroupSortService;
	private DealingPatternService dealingPatternService;
	
	public void setSkuGroupSortService(SKUGroupSortService skuGroupSortService) {
		this.skuGroupSortService = skuGroupSortService;
	}

	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println("loading user sku group sort preference...");
		Map<String, Object> model = new HashMap<String, Object>();
		
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		/* IMPORTANT: CATEGORYID MUST ALWAYS BE PASSED BY GUI */
		String sCat = request.getParameter("categoryId");
		Integer skuCategoryId;
		if (sCat == null)
			skuCategoryId = CategoryConstants.FRUITS;
		else
			skuCategoryId = Integer.valueOf(sCat);
		List<Integer> sellerIds = new ArrayList<Integer>();
		Long userRoleId = user.getRole().getRoleId();
		
		if (userRoleId.equals(RoleConstants.ROLE_SELLER_ADMIN)) {
			sellerIds = dealingPatternService.getSellerIdsOfSellerAdminId(user.getUserId());
		}
		else if (userRoleId.equals(RoleConstants.ROLE_SELLER)) {
			sellerIds.add(user.getUserId());
		}
		else if (userRoleId.equals(RoleConstants.ROLE_BUYER_ADMIN)) {
			sellerIds = dealingPatternService.getSellerIdsOfBuyerAdminId(user.getUserId());
		}
		else if (userRoleId.equals(RoleConstants.ROLE_BUYER)) {
			sellerIds = dealingPatternService.getSellerIdsOfBuyerId(user.getUserId());
		}
		
		List<SKUGroupSort> skuGroupSorts = skuGroupSortService.getSKUGroupSort(sellerIds, skuCategoryId, user.getUserId());
		
		model.put("skuGroupSorts", skuGroupSorts);
		
		for (int i=0; i<skuGroupSorts.size(); i++) {
			SKUGroupSort skuGroupSort = (SKUGroupSort) skuGroupSorts.get(i);
			Integer sellerId = skuGroupSort.getUserId();
			Integer groupId = skuGroupSort.getSkuGroupId();
			System.out.println("sellerId:[" + sellerId + "]");
			System.out.println("groupId:[" + groupId + "]");
		}
		
		ModelAndView mav = new ModelAndView("json", model);
	    return mav;
	}

}
