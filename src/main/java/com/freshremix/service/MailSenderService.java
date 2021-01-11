package com.freshremix.service;

import java.util.List;
import java.util.Map;

/**
 * @author mmaria
 * 
 */
public interface MailSenderService {

	public static final String EMAIL_SEMICOLON_SEPARATOR = ";";
	public static final String EMAIL_COMA_SEPARATOR = ",";
	public static final String REGEX_SPLIT_EMAIL = "\\s*" + "%s" + "\\s*";

	/**
	 * Sends an email using velocity template.
	 * 
	 * @param fromEmail
	 * @param toEmailList
	 * @param subject
	 * @param vmTemplateFile
	 * @param emailBodyDetails
	 * @return True if mail is sent; False otherwise
	 */
	Boolean sendEmail(String fromEmail, List<String> toEmailList, String subject,
			String vmTemplateFile, Map<String, Object> emailBodyDetails);
}
