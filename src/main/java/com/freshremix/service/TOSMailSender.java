package com.freshremix.service;

import com.freshremix.model.TermsOfService;
import com.freshremix.model.User;
import com.freshremix.model.UsersTOS;

public interface TOSMailSender {

	/**
	 * Handles sending of email notification to the email list in the TOS
	 * Maintenance, when the user accepts or declines the terms of service
	 * 
	 * @param usersTOS
	 * @param user
	 * @param latestTOS
	 */
	public abstract void sendEmailNotification(UsersTOS usersTOS, User user,
			TermsOfService latestTOS);

}