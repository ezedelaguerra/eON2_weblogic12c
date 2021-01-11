package com.freshremix.webapp.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.freshremix.model.User;
import com.freshremix.service.LoginService;

public class LoginSubmitValidator implements Validator {

	private LoginService loginService;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@Override
	public boolean supports(Class clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		User user = (User) object;
//		user = loginService.getUserByUsernameAndPassword(user.getUserName(), user.getPassword());
		boolean isValid = loginService.validateUsernameAndPassword(user.getUserName(), user.getPassword());
		if (!isValid) {
			errors.rejectValue("userName", "username.required", "ユーザ�??�?�パスワード�?�正�?��??�?�り�?��?�ん。");
		}		
	}
}