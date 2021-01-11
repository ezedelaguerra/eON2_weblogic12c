package com.freshremix.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.freshremix.service.MailSenderService;

/**
 * @author mmaria
 * 
 */
public class MailSenderServiceImpl implements MailSenderService {

	private static final String UTF_8 = "UTF-8";


	private static Logger LOGGER = Logger
			.getLogger(MailSenderServiceImpl.class);


	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;

	@Required
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Required
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/*
	 * (non-Javadoc)
	 * @see com.freshremix.service.MailSenderService#sendEmail(java.lang.String, java.util.List, java.lang.String, java.lang.String, java.util.Map)
	 */
	public Boolean sendEmail(final String fromEmail, final List<String> toEmailList,
			final String subject, final String vmTemplateFile,
			final Map<String, Object> emailBodyDetails) {
		
		validateEmailParameters(fromEmail, toEmailList, subject);
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				LOGGER.info("Preparing email message");
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, UTF_8);

				for (String emailAddress : toEmailList) {
					if (StringUtils.isNotBlank(emailAddress)) {
						emailAddress = StringUtils.trim(emailAddress);
						InternetAddress to = new InternetAddress(emailAddress);
						message.addTo(to);
						LOGGER.info("Adding email address as recipient:"
								+ emailAddress);
					}
				}
				
				message.setFrom(fromEmail);
				message.setSubject(subject);
				
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, vmTemplateFile, UTF_8, emailBodyDetails);
				LOGGER.info("Using vm template file: " + vmTemplateFile);
				LOGGER.debug("Email Body:"+text);
				message.setText(text, true);
			}
		};

		try {
			LOGGER.info("Sending email to :" + toEmailList);
			this.mailSender.send(preparator);
			LOGGER.info("Email sent");
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
			LOGGER.error("Unable to send email message:" + ex.getMessage());
			return Boolean.FALSE;
		}
		return Boolean.TRUE;

	}

	private void validateEmailParameters(String fromEmail,
			List<String> toEmailList, String subject) {
		
		if (StringUtils.isBlank(fromEmail)) {
			throw new IllegalArgumentException("From email is blank");
		}
		
		if (CollectionUtils.isEmpty(toEmailList)){
			throw new IllegalArgumentException("To email list is empty");
		}
		
		if (StringUtils.isBlank(subject)) {
			throw new IllegalArgumentException("Email Subject is blank");
		}
		
	}
	
	
	/**
	 * Convenience method to split the character separated email values.
	 * @param emailList
	 * @return
	 * @see MailSenderServiceImpl.convertCSVToList(String emailList, String emailListSeparator) 
	 */
	public static List<String> convertCSVToList(String emailList) {
		return convertCSVToList(emailList, null);
	}
	

	/**
	 * Convenience method to split the character separated email values.
	 * Uses semi colon as default email separator
	 * @param emailList
	 * @param emailListSeparator
	 * @return
	 */
	public static List<String> convertCSVToList(String emailList, String emailListSeparator) {
		if (StringUtils.isBlank(emailList)) {
			return null;
		}
		if (StringUtils.isBlank(emailListSeparator)){
			emailListSeparator = EMAIL_SEMICOLON_SEPARATOR;
		}
		
		String[] emailArray = emailList.split(String.format(REGEX_SPLIT_EMAIL, emailListSeparator));

		if (ArrayUtils.isEmpty(emailArray)) {
			return null;
		}

		List<String> items = new ArrayList<String>(emailArray.length);
		for (String email : emailArray) {
			if (StringUtils.isBlank(email)) {
				continue;
			}
			items.add(email);
		}
		return items;
	}
	
}
