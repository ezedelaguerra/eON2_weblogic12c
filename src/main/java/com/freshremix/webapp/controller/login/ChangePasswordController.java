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

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.ForgotPasswordLinkObject;
import com.freshremix.model.User;
import com.freshremix.service.ForgotPasswordLinkService;
import com.freshremix.service.LoginService;

/**
 * @author raquino
 *
 */
public class ChangePasswordController extends SimpleFormController{

	private LoginService loginService;
	private ForgotPasswordLinkService forgotPasswordLinkService;

	public void setForgotPasswordLinkService(
			ForgotPasswordLinkService forgotPasswordLinkService) {
		this.forgotPasswordLinkService = forgotPasswordLinkService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
		
	protected Object formBackingObject(HttpServletRequest request){
		
		Integer userId =Integer.parseInt(request.getParameter("j_userId"));
		Integer code =Integer.parseInt(request.getParameter("v_id"));
		User user = loginService.getUserByUserId(userId);
		
		ForgotPasswordLinkObject fObj = 
			forgotPasswordLinkService.getForgotPasswordObj(code, userId);
		
		if(fObj == null || fObj.getStatus() == 0 ){
			fObj = new ForgotPasswordLinkObject();
			request.getSession().setAttribute("error", "1");
		}else{
			fObj.setUsername(user.getUserName());
			request.getSession().setAttribute("error", "0");
		}
			
		return fObj;
		
	}
	
}
