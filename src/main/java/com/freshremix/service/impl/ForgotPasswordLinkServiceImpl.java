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
 * Mar 17, 2010		Jovino Balunan		
 */
package com.freshremix.service.impl;

import com.freshremix.dao.ForgotPasswordLinkDao;
import com.freshremix.model.ForgotPasswordLinkObject;
import com.freshremix.service.ForgotPasswordLinkService;

/**
 * @author jabalunan
 * 
 */
public class ForgotPasswordLinkServiceImpl implements ForgotPasswordLinkService {

	private ForgotPasswordLinkDao forgotPasswordLinkDao; 
	
	public ForgotPasswordLinkDao getForgotPasswordLinkDao() {
		return forgotPasswordLinkDao;
	}

	public void setForgotPasswordLinkDao(ForgotPasswordLinkDao forgotPasswordLinkDao) {
		this.forgotPasswordLinkDao = forgotPasswordLinkDao;
	}

	@Override
	public ForgotPasswordLinkObject getForgotPasswordObj(Integer validationId,
			Integer userId) {
		return forgotPasswordLinkDao.getForgotPasswordObj(validationId, userId);
	}

	@Override
	public Integer insertValidationId(Integer userId) {
		// TODO Auto-generated method stub
		return forgotPasswordLinkDao.insertValidationId(userId);
	}

	@Override
	public void updateStatus(Integer validationId) {
		forgotPasswordLinkDao.updateStatus(validationId);
	}
	
}
