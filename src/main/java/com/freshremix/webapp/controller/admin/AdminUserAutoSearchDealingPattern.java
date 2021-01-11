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
 * Feb 17, 2010		jabalunan		
 */
package com.freshremix.webapp.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.CompanyType;
import com.freshremix.model.UserDealingPattern;
import com.freshremix.service.CompanyDealingPatternService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.util.RolesUtil;

public class AdminUserAutoSearchDealingPattern extends SimpleFormController {
	private UsersInformationService userInfoService;
	private CompanyDealingPatternService compDPService;

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setCompDPService(CompanyDealingPatternService compDPService) {
		this.compDPService = compDPService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String companyId = request.getParameter("companyId");
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String companyType = request.getParameter("companyType").trim();
		String roleType = request.getParameter("roleName").trim();
		Integer roleId = Integer.parseInt(request.getParameter("roleId").toString());
		
		boolean isAdmin = RolesUtil.roleAdmin(roleId);
		List<UserDealingPattern> listUsers = new ArrayList<UserDealingPattern>();
		
		if(isAdmin) {
			listUsers = compDPService.getAllUnderAdminUserDealingPattern(companyId, userId, 
					RolesUtil.isBuyerType(companyType)?RolesUtil.roleBuyer():RolesUtil.roleSeller());
		}
		else {
			listUsers =compDPService.getAllUserDealingPattern(companyId,
					RolesUtil.isBuyerType(companyType)?RolesUtil.roleSeller():RolesUtil.roleBuyer(),userName,roleType);
		}		
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", listUsers);
		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}
}
