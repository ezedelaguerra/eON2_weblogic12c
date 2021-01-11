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

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.AdminUsers;
import com.freshremix.service.UsersInformationService;

public class AdminUsersController extends SimpleFormController {
	private UsersInformationService userInfoService;

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	@Override
	protected ModelAndView onSubmit(Object command, BindException errors)
			throws Exception {
		AdminUsers userId = (AdminUsers) command;
		AdminUsers lstUsers = userInfoService.getAllUsersByUserId(userId.getUserId());
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("userinfo", lstUsers);
		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}

}
