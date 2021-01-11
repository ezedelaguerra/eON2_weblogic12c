package com.freshremix.dao;

import com.freshremix.model.ForgotPasswordLinkObject;

public interface ForgotPasswordLinkDao {
	Integer insertValidationId(Integer userId);
	ForgotPasswordLinkObject getForgotPasswordObj(Integer validationId,Integer userId);
	void updateStatus(Integer validationId);
	
}
