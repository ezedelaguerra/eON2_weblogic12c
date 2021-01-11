package com.freshremix.service;

import com.freshremix.exception.ServiceException;
import com.freshremix.model.LoginInquiry;
import com.freshremix.model.User;

public interface LoginService {

	boolean validateUsernameAndPassword(String username, String password);
	User getUserByUsernameAndPassword(String username, String password);
	boolean validateUsernameAndEmail(String username, String password);
	User getUserByUsernameAndEmail(String username, String password);
	void insertNonMemberInquiry(LoginInquiry nonMemberInquiry);
	boolean sendMailForgotPassword(User user,String hostName);
	boolean sendMailNonMemberInquiry(LoginInquiry nonMemberInquiry);
	User getUserByUsername(String username);
	void updateUserLastLoginDate(String username);
	User getUserByUserId(Integer userId);
	String getCityByZip(Integer zipCd);
	void validateAdminUser(String username, String password) throws ServiceException;
}
