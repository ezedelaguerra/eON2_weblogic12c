package com.freshremix.autodownload;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

public class PasswordHandler implements InitializingBean {

	private String securiryPassword;

	public String getSecuriryPassword() {
		return securiryPassword;
	}

	public void setSecuriryPassword(String securiryPassword) {
		this.securiryPassword = securiryPassword;
	}

	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isValidPassword(String password) {
		if (StringUtils.isBlank(password)){
			return false;
		}
		return securiryPassword.equals(password);
	}
	public boolean isNotValidPassword(String password) {
		return !isValidPassword(password);
	}

}
