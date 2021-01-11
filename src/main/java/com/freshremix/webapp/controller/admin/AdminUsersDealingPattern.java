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

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.Const;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.AdminUsers;
import com.freshremix.model.UserDealingPattern;
import com.freshremix.service.CompanyDealingPatternService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.util.*;

public class AdminUsersDealingPattern extends SimpleFormController {
	private CompanyDealingPatternService compDPService;
	private UsersInformationService userInfoService;
	
	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setCompDPService(CompanyDealingPatternService compDPService) {
		this.compDPService = compDPService;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		List<UserDealingPattern> lstUsersDP, userSelDP;
		
		String companyId = request.getParameter("companyId").toString();
		String companyType = request.getParameter("companyType").trim();
		String userId = request.getParameter("userId").toString();
		Integer newDealingPattern = Integer.parseInt(request.getParameter("newRole").toString());
		String roleName = request.getParameter("roleName").trim();
		//username was declared as blank
		//since the query is for all dealing pattern
		String userName ="";
		Integer roleId = null; 
		if (newDealingPattern.equals(1))
			roleId = RolesUtil.getRoleId(roleName);
		else
			roleId = Integer.parseInt(request.getParameter("roleId").toString());
		
		boolean isAdmin = RolesUtil.roleAdmin(roleId);
		if(isAdmin) {
			lstUsersDP = compDPService.getAllUnderAdminUserDealingPattern(companyId, userId, RolesUtil.isBuyerType(companyType)?RolesUtil.roleBuyer():RolesUtil.roleSeller());
			userSelDP = compDPService.getAllSelectedUserDealingPattern(userId, roleId);
		}
		else {
			lstUsersDP = compDPService.getAllUserDealingPattern(companyId, RolesUtil.isBuyerType(companyType)?RolesUtil.roleSeller():RolesUtil.roleBuyer(),userName,companyType);
			userSelDP = compDPService.getAllSelectedUserDealingPattern(userId, roleId);
		}		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userSelDP", userSelDP);
		model.put("usersDPList", lstUsersDP);
		model.put("roleId", roleId);
		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}
}
