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

import com.freshremix.model.User;
import com.freshremix.service.UsersInformationService;

public class AdminUpdatePasswordController extends SimpleFormController {
	private UsersInformationService userInfoService;
	
	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}
	@Override
	protected ModelAndView onSubmit(Object command, BindException errors)
			throws Exception {
		User user = (User) command;
		Integer iResult=userInfoService.updateUserPassword(user.getUserId().toString(), user.getUserName(), user.getPassword());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("status", iResult);
		
		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}

}
