package com.freshremix.webapp.controller.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.User;
import com.freshremix.service.LoginService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.StringUtil;

public class ForgotPasswordController extends SimpleFormController {

	private LoginService loginService;
	private EONLocale eonLocale;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}
		
	
	
//	@Override
//	public ModelAndView handleRequest(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		System.out.println("start");
//		super.handleRequest(request, response);
//		BindException errors = getErrorsForNewForm(request);
//		Map<String, Object> model = new HashMap<String, Object>();
//		String message = null;
//		model.put("response", message);
//		return new ModelAndView("json",model);
//	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		User user = (User) command;
//		String value = getServletContext().getInitParameter("host"); 
		String value = "http://"+ request.getServerName() +":"+ request.getServerPort() + request.getContextPath();
		
		if (errors.hasErrors()) {
			ObjectError error = errors.getGlobalError();
			System.out.println(error.getDefaultMessage());
			System.out.println(errors);
			return super.onSubmit(command, errors);
		}
		else{

			Map<String, Object> model = new HashMap<String, Object>();
			boolean isValid = loginService.validateUsernameAndEmail(user.getUserName(), user.getPcEmail());
			String message;
			
			if (!isValid) {
				System.out.println("End Validate");
				message = StringUtil.toUTF8String(getMessageSourceAccessor().getMessage(
						"forgotpassword.invalid.user", eonLocale.getLocale()));
				System.out.println(message);
				model.put("response", message);
				return new ModelAndView("json",model);
			}
			user = loginService.getUserByUsernameAndEmail(user.getUserName(), user.getPcEmail());
			boolean isSuccess = false;
			
			isSuccess = loginService.sendMailForgotPassword(user,value);
			
			if (isSuccess) {
				message = StringUtil.toUTF8String(getMessageSourceAccessor().getMessage(
						"mail.sending.success", eonLocale.getLocale()));				
				System.out.println(message);
			} else {
				message = StringUtil.toUTF8String(getMessageSourceAccessor().getMessage(
						"mail.sending.error", eonLocale.getLocale()));
				System.out.println(message);
			}
			
			model.put("response", message);
			return new ModelAndView("json",model);
		}
	}
}