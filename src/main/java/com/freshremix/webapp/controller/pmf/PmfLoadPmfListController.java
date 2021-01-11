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
 * Feb 17, 2010		pamela		
 */
package com.freshremix.webapp.controller.pmf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.PmfList;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.ProductMasterFileService;
import com.freshremix.util.SessionHelper;

/**
 * @author Pammie
 *
 */

public class PmfLoadPmfListController implements Controller {
	ProductMasterFileService pmfService;
	private DealingPatternService dealingPatternService;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
//		TODO: users of admin login
		List<Integer> userIds = new ArrayList<Integer>();
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)){
//			check first if there is existing function for this
			OrderSheetParam osParam = (OrderSheetParam)SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM); 
			String dateFrom = osParam.getStartDate();
			String dateTo = osParam.getEndDate();
//			String dateFrom = "20100326";
//			String dateTo = null;
			
			List<User> users = dealingPatternService.getMembersByAdminId(user.getUserId(), 
					DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER, dateFrom, dateTo);
			for (int i=0;i<users.size();i++){
				userIds.add(users.get(i).getUserId());
			}
		} else {
			userIds.add(user.getUserId());
		}
		
		for(int i=0;i<userIds.size();i++){
			System.out.println("USERIDS: " + userIds.get(i));
		}
		List<PmfList> pmfList = pmfService.getPmfList(userIds);
		SessionHelper.setAttribute(request, SessionParamConstants.PMF_LIST_PARAM, pmfList);
		
//		List<PmfList> pmfList = pmfService.getPmfList(Integer.valueOf(user.getUserId()));

		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("response", pmfList);
		
		return new ModelAndView("json", model);
	}

	public void setPmfService(ProductMasterFileService pmfService) {
		this.pmfService = pmfService;
	}

	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}
}