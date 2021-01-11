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
 * Sept 20, 2011		gilwen		
 */
package com.freshremix.webapp.controller.ordersheet;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.dao.UserDao;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.SKUGroupService;
import com.freshremix.util.SessionHelper;

/**
 * @author gilwen
 *
 */
public class SellerIdSelectController extends AddSkuController {

	private SKUGroupService skuGroupService;
	private UserDao usersInfoDaos;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("json");
		String sellerId = request.getParameter("sellerId");
		User user = usersInfoDaos.getUserById2(Integer.parseInt(sellerId));
		OrderSheetParam param = (OrderSheetParam)SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM);
		List<Map<String, Object>> skuGroupList = skuGroupService.getSKUGroupListViaSellerSelect(user.getUserId(), param.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
		
		mav.addObject("sellerId", user.getUserId());
		mav.addObject("companyname", user.getCompany().getShortName());
		mav.addObject("groupname", skuGroupList);
		mav.addObject("lockflag", "{'allQuantities' : 0}");
		
		return mav;
	}
	
	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}

	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}
}