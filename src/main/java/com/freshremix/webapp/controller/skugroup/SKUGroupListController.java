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
 * Jun 16, 2010		raquino		
 */
package com.freshremix.webapp.controller.skugroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.CategoryConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.SortSKUGroup;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.treegrid.TreeGridItem;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author raquino
 *
 */
public class SKUGroupListController extends SimpleFormController {
	
	private SKUGroupService skuGroupService;
	private DealingPatternService dealingPatternService;
	
	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}

	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
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
		else if (userRoleId.equals(RoleConstants.ROLE_BUYER_ADMIN)) {
			sellerIds = dealingPatternService.getSellerIdsOfBuyerAdminId(user.getUserId());
		}
		else if (userRoleId.equals(RoleConstants.ROLE_BUYER)) {
			sellerIds = dealingPatternService.getSellerIdsOfBuyerId(user.getUserId());
		}
		else if (userRoleId.equals(RoleConstants.ROLE_SELLER)) {
			sellerIds.add(user.getUserId());
		}
		
		List<SortSKUGroup> sortSkuGrpList = skuGroupService.getSKUGroup(sellerIds, skuCategoryId, user.getUserId());
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, sortSkuGrpList);
		
		List<TreeGridItem> items = new ArrayList<TreeGridItem>();
		for (SortSKUGroup sortSkuGrp: sortSkuGrpList) {
			TreeGridItem it = new TreeGridItem();
			it.setId(Long.valueOf(sortSkuGrp.getSkuGroup().getSkuGroupId()));
			it.addCell("");
			it.addCell(sortSkuGrp.getSkuGroup().getSkuGroupId());
			it.addCell(StringUtil.nullToBlank(sortSkuGrp.getSkuGroup().getOrigSkuGroupId()));
			it.addCell(sortSkuGrp.getSkuGroup().getSellerId());
			it.addCell(sortSkuGrp.getSkuGroup().getSellerName());
			it.addCell(sortSkuGrp.getSkuGroup().getCategoryId());
			it.addCell(sortSkuGrp.getSkuGroup().getDescription());
			items.add(it);
		}
		
		model.put("skuGroupList", items);
		
		for (int i=0; i<sortSkuGrpList.size(); i++) {
			SortSKUGroup sortSkuGroup = (SortSKUGroup) sortSkuGrpList.get(i);
			Integer sellerId = sortSkuGroup.getSkuGroup().getSellerId();
			Integer groupId = sortSkuGroup.getSkuGroup().getSkuGroupId();
			System.out.println("sellerId:[" + sellerId + "]");
			System.out.println("groupId:[" + groupId + "]");
		}
		
		ModelAndView mav = new ModelAndView("json", model);
	    return mav;
	}
}
