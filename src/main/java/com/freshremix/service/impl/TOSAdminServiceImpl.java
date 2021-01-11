package com.freshremix.service.impl;

import java.util.Date;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Required;

import com.freshremix.dao.TermsOfServiceDao;
import com.freshremix.exception.OptimisticLockException;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.TermsOfService;
import com.freshremix.service.MailSenderService;
import com.freshremix.service.TOSAdminService;

/**
 * This class is intended for TOS admin Maintenance Page
 * 
 *
 */
public class TOSAdminServiceImpl implements TOSAdminService {

	private static final Logger LOGGER = Logger.getLogger(TOSAdminServiceImpl.class);

	private static final String TOS_MESSAGE_ERROR_EMAIL_LIST_INVALID = "tos.message.error.emailListInvalid";
	private static final String TOS_MESSAGE_ERROR_EMAIL_LIST_EMPTY = "tos.message.error.emailListEmpty";
	private static final String TOS_MESSAGE_ERROR_TOS_CONTENT_NOT_FOUND = "tos.message.error.tosContentNotFound";
	private static final String TOS_MESSAGE_ERROR_OPTIMISTIC_LOCK_FAILED = "tos.message.error.optimisticLockFailed";
	private static final String TOS_MESSAGE_ERROR_EXCEED_MAX_EMAIL_CHAR = "tos.message.error.exceedMaxEmailChar";
	private static final String TERMS_OF_SERVICE_DATA_NOT_FOUND_OR_EMPTY = "Terms of Service data not found or empty";
	private static final String RECORD_ID_FOR_UPDATE_IS_NULL_OR_EMPTY = "Record ID for update is null or empty";
	private static final String AUDIT_FIELDS_NOT_PROPERLY_SET = "Audit fields not properly set.";
	public static final int MAX_EMAIL_CHAR = 300;
	public static final int MAX_DAYS_TOS_NEW_STATE = 30;


	private TermsOfServiceDao tosDao;

	public TermsOfServiceDao getTosDao() {
		return tosDao;
	}

	@Required
	public void setTosDao(TermsOfServiceDao tosDao) {
		this.tosDao = tosDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.freshremix.service.TOSAdminService#getLatestTOS()
	 */
	@Override
	public TermsOfService getLatestTOS() throws ServiceException {
		LOGGER.info("Getting latest Terms of Service Start");
		TermsOfService tos = tosDao.getLatestTOS();
		LOGGER.info("Getting of latest Terms of Service End");
		return tos;
	}

	@Override
	public TermsOfService.TOSState getTOSState(TermsOfService tos) {
		if (tos == null) {
			throw new IllegalArgumentException("Null TOS");
		}

		DateTime createdDate = new DateTime(tos.getCreatedDate());
		Days daysBetween = Days.daysBetween(createdDate, DateTime.now());
		if (daysBetween.getDays() <= MAX_DAYS_TOS_NEW_STATE) {
			return TermsOfService.TOSState.NEW;
		}
		return TermsOfService.TOSState.OLD;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.freshremix.service.TOSService#createTermsOfService(com.freshremix
	 * .model.TermsOfService)
	 */
	@Override
	public TermsOfService createTermsOfService(TermsOfService tos)
			throws ServiceException {
		LOGGER.info("Saving TOS with parameter:"
				+ (tos != null ? tos.toString() : "null"));

		validate(tos, false);
		processEmailList(tos);
		setAuditFields(tos, false);
		tos = tosDao.save(tos);

		LOGGER.info("Completed saving TOS with parameter:" + tos.toString());
		return tos;
	}

	// unnecessary semicolons, this assumes that emails have been validated
	private void processEmailList(TermsOfService tos) {
		List<String> emailList = MailSenderServiceImpl.convertCSVToList(tos
				.getEmailList());
		StringBuilder sb = new StringBuilder();

		for (String email : emailList) {
			if (StringUtils.isNotBlank(email)) {
				if (sb.length() > 0) {
					sb.append(MailSenderService.EMAIL_SEMICOLON_SEPARATOR);
				}
				sb.append(email);
			}
		}
		tos.setEmailList(sb.toString());
	}

	private void setAuditFields(TermsOfService tos,
			boolean setAuditFieldsForUpdate) {
		if (!setAuditFieldsForUpdate) {
			tos.setCreatedDate(new Date());
		} else {
			tos.setModifiedDate(new Date());
		}

	}

	protected void validate(TermsOfService tos, boolean validateForUpdate)
			throws ServiceException {

		if (tos == null) {
			// programming error
			throw new IllegalArgumentException(
					TERMS_OF_SERVICE_DATA_NOT_FOUND_OR_EMPTY);
		}

		if (validateForUpdate) {
			validateId(tos);
		}

		validateTOSContent(tos.getContent());
		validateEmails(tos.getEmailList());
		validateAuditFields(tos, validateForUpdate);
	}

	private void validateId(TermsOfService tos) throws ServiceException {

		if (StringUtils.isBlank(tos.getTosId())) {
			// If this happens, then it is a program error
			LOGGER.error("TOS ID is Blank");
			throw new IllegalArgumentException(
					RECORD_ID_FOR_UPDATE_IS_NULL_OR_EMPTY);
		}

	}

	private void validateAuditFields(TermsOfService tos,
			boolean validateForUpdate) throws ServiceException {

		// created by field should have a value whether it is create or update
		if (StringUtils.isBlank(tos.getCreatedBy())) {
			LOGGER.error("Created by field is blank");
			throw new IllegalArgumentException(AUDIT_FIELDS_NOT_PROPERLY_SET);
		}

		if (!validateForUpdate) {

			// modified by and date field should be null
			if (StringUtils.isNotBlank(tos.getModifiedBy())
					|| tos.getModifiedDate() != null) {
				LOGGER.error("Modified by or date field is blank");
				throw new IllegalArgumentException(
						AUDIT_FIELDS_NOT_PROPERLY_SET);
			}

		} else {
			// modified by field should have a value
			if (StringUtils.isBlank(tos.getModifiedBy())) {
				LOGGER.error("Modified by or date field is blank");
				throw new IllegalArgumentException(
						AUDIT_FIELDS_NOT_PROPERLY_SET);
			}

			if (tos.getVersion() == null) {
				LOGGER.error("Version field is blank");
				throw new IllegalArgumentException(
						AUDIT_FIELDS_NOT_PROPERLY_SET);
			}
		}

	}

	private void validateTOSContent(String tosContent) throws ServiceException {
		if (StringUtils.isBlank(tosContent)) {
			throw new ServiceException(TOS_MESSAGE_ERROR_TOS_CONTENT_NOT_FOUND);
		}
	}

	private void validateEmails(String emailListString) throws ServiceException {
		
		if (StringUtils.isBlank(emailListString)){
			LOGGER.warn("Email list is empty");
			throw new ServiceException(TOS_MESSAGE_ERROR_EMAIL_LIST_EMPTY);
		}
		
		if (emailListString.length() > MAX_EMAIL_CHAR){
			throw new ServiceException(TOS_MESSAGE_ERROR_EXCEED_MAX_EMAIL_CHAR);
		}
		List<String> emailList = MailSenderServiceImpl
				.convertCSVToList(emailListString);

		if (CollectionUtils.isEmpty(emailList)) {
			LOGGER.warn("Email list is empty");
			throw new ServiceException(TOS_MESSAGE_ERROR_EMAIL_LIST_EMPTY);
		}
		for (String email : emailList) {
			if (!isValidEmail(email)) {
				LOGGER.warn("Email list contains an invalid email");
				throw new ServiceException(TOS_MESSAGE_ERROR_EMAIL_LIST_INVALID);
			}
		}
	}

	private boolean isValidEmail(String email) {
		try {
			InternetAddress address = new InternetAddress(email);
			address.validate();
		} catch (AddressException e) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.freshremix.service.TOSAdminService#updateTermsOfService(com.freshremix
	 * .model.TermsOfService)
	 */
	@Override
	public TermsOfService updateTermsOfService(TermsOfService tos)
			throws ServiceException {
		LOGGER.info("Updating TOS with parameter:"
				+ (tos != null ? tos.toString() : "null"));

		validate(tos, true);
		setAuditFields(tos, true);

		try {
			tosDao.update(tos);
		} catch (OptimisticLockException e) {
			LOGGER.warn("Optimistic locking failed");
			throw new ServiceException(
					TOS_MESSAGE_ERROR_OPTIMISTIC_LOCK_FAILED, e);
		}

		LOGGER.info("Completed updating TOS with parameter:" + tos.toString());
		return tos;
	}
}
