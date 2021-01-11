package com.freshremix.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Required;

import com.freshremix.dao.TermsOfServiceDao;
import com.freshremix.dao.UsersTOSDao;
import com.freshremix.exception.OptimisticLockException;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.TOSUserContext;
import com.freshremix.model.TermsOfService;
import com.freshremix.model.User;
import com.freshremix.model.UsersTOS;
import com.freshremix.service.LoginService;
import com.freshremix.service.TOSMailSender;
import com.freshremix.service.TOSUserService;

/**
 * This class is intended for Users TOS Maintenance
 * 
 *
 */
public class TOSUserServiceImpl implements TOSUserService {

	private static final Logger LOGGER = Logger.getLogger(TOSUserServiceImpl.class);
	private LoginService loginService;
	private static final String AGREE_FLAG = "1";
	private static final String DISAGREE_FLAG = "0";
	private static final String TOS_MESSAGE_ERROR_INVALID_USERNAME_PASSWORD = "login.invalid.username.password";
	private static final int MAX_TRIAL_DAYS = 30;

	private TermsOfServiceDao tosDao;
	private UsersTOSDao usersTOSDao;
	private TOSMailSender tosMailSender;
	
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public TermsOfServiceDao getTosDao() {
		return tosDao;
	}

	@Required
	public void setTosDao(TermsOfServiceDao tosDao) {
		this.tosDao = tosDao;
	}

	public UsersTOSDao getUsersTOSDao() {
		return usersTOSDao;
	}

	@Required
	public void setUsersTOSDao(UsersTOSDao usersTOSDao) {
		this.usersTOSDao = usersTOSDao;
	}


	public TOSMailSender getTosMailSender() {
		return tosMailSender;
	}

	@Required
	public void setTosMailSender(TOSMailSender tosMailSender) {
		this.tosMailSender = tosMailSender;
	}

	
	
	private void validateUser(User user) {
		//we assume that the user is a valid user

		if (user == null){
			throw new IllegalArgumentException("User is null");
		}

		if (user.getUserId() == null){
			throw new IllegalArgumentException("UserId is null");
		}
		
		if (StringUtils.isBlank(user.getUserName())){
			throw new IllegalArgumentException("UserName is null");
		}
		
		
	}

	@Override
	public void saveUsersTOSResponse(UsersTOS usersTOS, User user) throws ServiceException {
		LOGGER.info("Saving User's response to the TOS page...started");
		validateUsersTOS(usersTOS);
		
		UsersTOS queryResult = processSavingUsersTOS(usersTOS, false);
		
		//send the email to email recipients only when Disagree button is clicked.
		if (queryResult.getFlag().equals(DISAGREE_FLAG)){
			TermsOfService latestTOS = tosDao.getLatestTOS();
			tosMailSender.sendEmailNotification(queryResult, user, latestTOS );
		}
		
		LOGGER.info("Saving User's response to the TOS page...completed");
	}

	/*
	 * (non-Javadoc)
	 * @see com.freshremix.service.TOSUserService#adminSaveUsersTOS(com.freshremix.model.UsersTOS, com.freshremix.model.User)
	 */
	@Override
	public void adminSaveUsersTOS(UsersTOS usersTOS) throws ServiceException {
		LOGGER.info("Admin User Saving User's TOS record...started");
		validateUsersTOS(usersTOS);
		
		processSavingUsersTOS(usersTOS, true);
		
		LOGGER.info("Admin User Saving User's TOS record...completed");
	}
	
	@Override
	public void deleteUsersTOS(Integer userId){
		LOGGER.info("Deleting Users TOS record...started");
		
		if (userId == null){
			LOGGER.warn("Userid Passed is null");
			throw new IllegalArgumentException("User id is null");
		}
		
		// no need to query. this will not throw an exception if record is not
		// existing
		usersTOSDao.delete(userId);
		LOGGER.info("Deleting Users TOS record...completed");
	}

	private UsersTOS processSavingUsersTOS(UsersTOS usersTOS, boolean processInitiatedByAdmin)
			throws ServiceException {
		
		UsersTOS queryResult = usersTOSDao.getEntity(usersTOS.getPrimaryKey());

		
		if (queryResult == null){
			usersTOS.setFlagDate(new Date());
			queryResult = usersTOS;
			
			// save only when process is initiated by the User 
			// OR if initiated by Admin, given that there is no record yet, save
			// only when admin checks the TOS checkbox (=Agree). 
			if (!processInitiatedByAdmin || queryResult.getFlag().equals(AGREE_FLAG)) {
				usersTOSDao.save(queryResult);
			}
			
		} else {
			mergedUsersTOS(queryResult, usersTOS);
			try {
				usersTOSDao.update(queryResult);
			} catch (OptimisticLockException e) {
				//This should be an impossible scenario. But throw an exception nonetheless.
				LOGGER.error("The UsersTOS record was updated by another process.  UsersTOS record to be saved:"+queryResult);
				throw new ServiceException("Users TOS record was updated by another process");
			}
		}
		
		return queryResult;
	}
	


	private void mergedUsersTOS(UsersTOS finalObject, UsersTOS updateObject) {
		//set the values to the finalObject
		finalObject.setFlag(updateObject.getFlag());
		finalObject.setFlagSetBy(updateObject.getFlagSetBy());
		finalObject.setFlagDate(new Date());
		
	}

	private void validateUsersTOS(UsersTOS usersTOS) {
		
		if (usersTOS == null) {
			throw new IllegalArgumentException("UsersTOS is null");
		}
		
		if (usersTOS.getUserId() == null){
			throw new IllegalArgumentException("User ID is null");
		}
		
		if (StringUtils.isBlank(usersTOS.getFlag())){
			throw new IllegalArgumentException("TOS flag is blank");
		}
		
		if (!usersTOS.getFlag().equalsIgnoreCase(DISAGREE_FLAG)
				&& !usersTOS.getFlag().equalsIgnoreCase(AGREE_FLAG)) {
			throw new IllegalArgumentException("Invalid TOS flag value");
		}

		if (StringUtils.isBlank(usersTOS.getFlagSetBy())){
			throw new IllegalArgumentException("TOS Flag Set By is blank");
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.freshremix.service.TOSUserService#getUserTOSContext(com.freshremix.model.User)
	 */
	@Override
	public TOSUserContext getUserTOSContext(User user) throws ServiceException{
		LOGGER.info("Retrieving TOS Context for the user");
		
		//init new context
		TOSUserContext tosUserContext = new TOSUserContext();
		
		validateUser(user);
		
		//get the Users TOS record for the user
		UsersTOS usersTOSRecord = usersTOSDao.getEntity(user.getUserId());
		
		setUserHasAgreedField(tosUserContext, usersTOSRecord);
		setAllowedUsageExpired(tosUserContext, usersTOSRecord);
		setDisplayTOS(tosUserContext, usersTOSRecord);
		
		LOGGER.info("Retrieving TOS Context for the user..completed");
		return tosUserContext;
		
	}

	private void setDisplayTOS(TOSUserContext tosUserContext,
			UsersTOS usersTOSRecord) {

		tosUserContext.setDisplayTOS(false);
		//null means first time access: display
		//not null means the user has already agreed or disagreed to the TOS; do not display
		if (usersTOSRecord == null){
			tosUserContext.setDisplayTOS(true);
			TermsOfService latestTOS = tosDao.getLatestTOS();
			tosUserContext.setTermsOfService(latestTOS);
		}
	}

	private void setAllowedUsageExpired(TOSUserContext tosUserContext,
			UsersTOS usersTOSRecord) {
		
		if(usersTOSRecord == null){
			//null means first time access
			//user is still allowed to access.
			tosUserContext.setAllowedUsageExpired(false);
			return;
		}
		
		//check if the user has agreed.
		String agreeFlag = usersTOSRecord.getFlag();
		
		if (agreeFlag.equals(AGREE_FLAG)) {
			tosUserContext.setAllowedUsageExpired(false);
		} else {
			//calc the number of days since clicking the disagree button
			//check if the user is still allowed to use the application
			DateTime flagDate = new DateTime(usersTOSRecord.getFlagDate());
			
			Days daysElapsed = Days.daysBetween(flagDate, DateTime.now());
			
			if (daysElapsed.getDays() > MAX_TRIAL_DAYS){
				tosUserContext.setAllowedUsageExpired(true);
			} else {
				tosUserContext.setAllowedUsageExpired(false);
			}
			
		}		
		
	}

	private void setUserHasAgreedField(TOSUserContext tosUserContext,
			UsersTOS usersTOSRecord) {
		
		if (usersTOSRecord == null){
			tosUserContext.setUserHasAgreed(false);
		} else {
			tosUserContext.setUserHasAgreed(usersTOSRecord.getFlag().equals(AGREE_FLAG));
		}
	}
	@Override
	public void resetTOSAcceptance(String username, String password) throws ServiceException{
		
		loginService.validateAdminUser(username, password);
		usersTOSDao.resetAll(username, new Date());
	}


	
	
	
	
	
}
