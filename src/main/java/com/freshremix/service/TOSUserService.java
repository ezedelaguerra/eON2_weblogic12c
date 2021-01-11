package com.freshremix.service;

import com.freshremix.exception.ServiceException;
import com.freshremix.model.TOSUserContext;
import com.freshremix.model.User;
import com.freshremix.model.UsersTOS;

public interface TOSUserService {

	/**
	 * Saves the response of the user to the TOS.
	 * 
	 * This service can be called from either the Login display of TOS or when
	 * user clicks on the TOS icon on the upper right side of the screen
	 * 
	 * @param usersTOS
	 * @param user
	 * @throws ServiceException
	 */
	public void saveUsersTOSResponse(UsersTOS usersTOS, User user) throws ServiceException;
	

	/**
	 * Saves the Users TOS when the admin creates or updates the User Details screen
	 * This is similar to saveUsersTOSResponse except that this is intended for Admin use.
	 * 
	 * No email is sent
	 * 
	 * 
	 * @param usersTOS
	 * @throws ServiceException
	 */
	public void adminSaveUsersTOS(UsersTOS usersTOS) throws ServiceException;
	
	/**
	 * Replaces the hasUserAgreed and displayTOSOnLogIn methods.
	 * 
	 * Assumes that the user is already existing since it was validated on login.
	 * 
	 * Forms the TOSUserContext with the following rule:
	 * 
	 * To check if user has agreed
	 * userHasAgreed: 
	 *     TRUE if users TOS flag=1 
	 *     FALSE if users TOS record is null or flag=0
	 * 
	 * To check if user is still allowed to access the system
	 * allowedUsageExpired:
	 *     TRUE if users TOS flag=0 AND days difference between flag date and today > MAX_TRIAL_DAYS
	 *     FALSE if users TOS flag=1 OR days difference between flag date and today < MAX_TRIAL_DAYS
	 * 
	 * To check if the TOS needs to be displayed on login
	 * displayTOS:
	 *     TRUE if users TOS record is null.  This also sets the latest TOS record in the context
	 *     FALSE if users TOS record is NOT null.
	 *     
	 * @param user
	 * @return
	 * @throws ServiceException 
	 */
	public TOSUserContext getUserTOSContext(User user) throws ServiceException;


	/**
	 * Deletes the users TOS record
	 * @param userId
	 */
	public void deleteUsersTOS(Integer userId);

	/**
	 * Sets all users TOS acceptance flag to 0

	 * @param username
	 * @param password
	 * @throws ServiceException 
	 */
	void resetTOSAcceptance(String username, String password)
			throws ServiceException;



	
	
}
