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
 * Feb 22, 2010		raquino		
 */
package com.freshremix.webapp.controller.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.User;
import com.freshremix.service.LoginService;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.util.SessionHelper;

public class UserPreferencesController implements Controller {

	private LoginService loginService;
	private UserPreferenceService userPreferenceService;
	
	
	private ChainedControllerProcessor chainedControllerProcessor;


	public ChainedControllerProcessor getChainedControllerProcessor() {
		return chainedControllerProcessor;
	}

	public void setChainedControllerProcessor(
			ChainedControllerProcessor chainedControllerProcessor) {
		this.chainedControllerProcessor = chainedControllerProcessor;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	public void setUserPreferenceService(UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		// User object session
		if (request.getUserPrincipal()== null || StringUtils.isBlank(request.getUserPrincipal().getName())) {
			return new ModelAndView("redirect:/j_spring_security_logout");
		}
		String username = request.getUserPrincipal().getName();
		User user = loginService.getUserByUsername(username);
		this.setUserPreference(user);
		loginService.updateUserLastLoginDate(username);
		// user preference
		user.setPreference(userPreferenceService.getUserPreference(user));
		SessionHelper.setAttribute(request, SessionParamConstants.USER_PARAM, user);
		String role = user.getRole().getRoleName();

		String url = chainedControllerProcessor.getUrltoForward(role,  request.getParameterMap());
		if(url != null)
		{	
			
			return new ModelAndView(url);
		}
		else
		{
			return new ModelAndView("login/jsp/redirect", model); 
		}
		
	}
	
	private void setUserPreference(User user){
		
		if(!user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)){
			user.setViewUnfinalizeBilling(strToValue(user.getViewUnfinalizeBilling()));
			user.setViewUnpublisheAlloc(strToValue(user.getViewUnpublisheAlloc()));
			user.setEnableBAPublish(strToValue(user.getEnableBAPublish()));
			user.setAutoPublishAlloc(strToValue(user.getAutoPublishAlloc()));
		}
	}
	
	private String strToValue(String str) {
		String status = "false";

		if(str == null){
			status = "false";
		}else if (str.equals("1")) {
			status = "true";
		} else {
			status = "false";
		}

		return status;
	}

}
