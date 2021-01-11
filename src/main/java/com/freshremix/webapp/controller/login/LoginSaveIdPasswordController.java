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

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.User;
import com.freshremix.service.LoginService;

/**
 * @author raquino
 *
 */
public class LoginSaveIdPasswordController implements Controller{

	private LoginService loginService;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
		
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String j_username =request.getParameter("j_username");
		System.out.println("username = "+j_username);
		User user = loginService.getUserByUsername(j_username);
		
		if (user == null){
			request.getSession().setAttribute(SessionParamConstants.AUTO_LOGIN_PARAM, "0");
		}
		else {
			request.getSession().setAttribute(SessionParamConstants.AUTO_LOGIN_PARAM, "1");
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", user);

		return new ModelAndView("login/jsp/login", model);
	}

}
