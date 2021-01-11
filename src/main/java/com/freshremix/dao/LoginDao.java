package com.freshremix.dao;

import java.util.Map;

import com.freshremix.model.LoginInquiry;
import com.freshremix.model.User;

public interface LoginDao {
	Integer validateUsernameAndPassword(Map<String,String> param);
	User getUserByUsernameAndPassword(Map<String,String> param);
	Integer validateUsernameAndEmail(Map<String,String> param);
	User getUserByUsernameAndEmail(Map<String,String> param);
	void insertNonMemberInquiry(LoginInquiry nonMemberInquiry);
	User getUserByUsername(Map<String,String> param);
	void updateUserLastLoginDate (Map<String,String> param);
	User getUserByUserId(Integer userId);
	String getCityByZip(Integer zipCd);
	String getKenEmailAdd();
}
