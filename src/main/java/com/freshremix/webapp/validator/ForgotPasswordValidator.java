package com.freshremix.webapp.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.freshremix.model.User;
import com.freshremix.service.LoginService;
import com.freshremix.util.StringUtil;

public class ForgotPasswordValidator implements Validator {

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
		System.out.println("Start Validate");
		if(user.getUserName().isEmpty()){
			//errors.rejectValue("userName", "forgotpassword.username.required", "Error!");
			//String errMsg = StringUtil.toUTF8String("ユーザーIDを入力して下さい");
			errors.rejectValue("userName", "forgotpassword.username.required", "Error!");
		}
		else if(user.getPcEmail().isEmpty()){
			//errors.rejectValue("userName", "forgotpassword.email.required", "Error!");
			errors.rejectValue("userName", "forgotpassword.email.required", "Error!");
		}
//		else{
//			boolean isValid = loginService.validateUsernameAndEmail(user.getUserName(), user.getPcEmail());
//			
//			if (!isValid) {
//				System.out.println("End Validate");
//				//errors.rejectValue("userName", "forgotpassword.invalid.user", "Error!");
//				errors.rejectValue("userName", "forgotpassword.invalid.user", "Error!");
//			}
//		}
	}
}
