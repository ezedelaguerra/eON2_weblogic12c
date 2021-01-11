package com.freshremix.webapp.controller.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.ForgotPasswordLinkObject;
import com.freshremix.service.ForgotPasswordLinkService;
import com.freshremix.service.UsersInformationService;

public class UpdatePasswordController extends SimpleFormController {

		
	private UsersInformationService userInfoService;
	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	private ForgotPasswordLinkService forgotPasswordLinkService;
	
	public void setForgotPasswordLinkService(
			ForgotPasswordLinkService forgotPasswordLinkService) {
		this.forgotPasswordLinkService = forgotPasswordLinkService;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		ForgotPasswordLinkObject fObj = (ForgotPasswordLinkObject) command;
		Integer iResult=userInfoService.updateUserPassword(fObj.getUserId().toString(), fObj.getUsername(), fObj.getPassword());
		forgotPasswordLinkService.updateStatus(fObj.getValidationId());
		
		if(iResult > 0){
			return new ModelAndView("login/jsp/success", model);
		}else{
			return new ModelAndView("login/jsp/error", model);
		}

	}

}