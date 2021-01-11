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

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.SessionHelper;

public class SKUGroupController extends SimpleFormController {
	
	private SKUGroupService skuGroupService;
	private DealingPatternService dealingPatternService;
	
	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}

	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}
		
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		
//		String isFromSheet = (String) command;
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
//		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		//set seller to model (sku group)
		Long userRoleId = user.getRole().getRoleId();
		List<User> sellerUsers = new ArrayList<User>();
		
//		if (isFromSheet.equalsIgnoreCase("Y")){
			if (userRoleId.equals(RoleConstants.ROLE_SELLER_ADMIN)) {
				sellerUsers = dealingPatternService.getMembersByAdminId(user.getUserId(), 
						DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER, "00000000", "99999999");
			}else if (userRoleId.equals(RoleConstants.ROLE_BUYER_ADMIN)) {
				sellerUsers = dealingPatternService.getSellerUsersOfBuyerAdminId(user.getUserId());
			}
			else if (userRoleId.equals(RoleConstants.ROLE_SELLER)) {
				sellerUsers.add(user);
			}
			List<FilteredIDs> sellerList = new ArrayList<FilteredIDs>();
			for (User _user : sellerUsers) {
				sellerList.add(new FilteredIDs(_user.getUserId().toString(), _user.getName()));
			}
//		}else{
//			osParam.getSelectedSellerID();
//		}
		model.put("response", sellerList);
		
		if (errors.hasErrors()) 
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json",model);
	}
}