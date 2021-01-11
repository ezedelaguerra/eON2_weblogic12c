package com.freshremix.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.freshremix.model.TermsOfService;
import com.freshremix.model.User;
import com.freshremix.model.UsersTOS;
import com.freshremix.service.MailSenderService;
import com.freshremix.service.MessageI18NService;
import com.freshremix.service.TOSMailSender;

/**
 * This class handles email sending requirements for the TOS.
 * 
 * @author michael
 *
 */
public class TOSMailSenderImpl implements TOSMailSender {

	private static final Logger LOGGER = Logger.getLogger(TOSMailSenderImpl.class);
	
	private String vmTemplateFile;
	private String fromEmail;
	private MailSenderService mailSenderService;
	private MessageI18NService messageI18NService;

	
	public String getVmTemplateFile() {
		return vmTemplateFile;
	}

	@Required
	public void setVmTemplateFile(String vmTemplateFile) {
		this.vmTemplateFile = vmTemplateFile;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	@Required
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public MailSenderService getMailSenderService() {
		return mailSenderService;
	}

	@Required
	public void setMailSenderService(MailSenderService mailSenderService) {
		this.mailSenderService = mailSenderService;
	}

	public MessageI18NService getMessageI18NService() {
		return messageI18NService;
	}

	@Required
	public void setMessageI18NService(MessageI18NService messageI18NService) {
		this.messageI18NService = messageI18NService;
	}
	
	
	/* (non-Javadoc)
	 * @see com.freshremix.service.impl.TOSMailSender#sendEmailNotification(com.freshremix.model.UsersTOS, com.freshremix.model.User, com.freshremix.model.TermsOfService)
	 */
	@Override
	public void sendEmailNotification(UsersTOS usersTOS, User user, TermsOfService latestTOS) {
		
		LOGGER.info("Sending users TOS response to listed email recipients");

		
		Map<String, Object> labelMap = createLabelMap();
		Map<String, Object> valueMap = createValueMap(usersTOS, user);
		final Map<String, Object> emailBodyDetails = createEmailBodyMap(labelMap, valueMap);
		
		final List<String> toEmailList = MailSenderServiceImpl.convertCSVToList(latestTOS.getEmailList());
		
		Object[] objectArg = new Object[]{user.getUserName()};
		final String subject = messageI18NService.getPropertyMessage("tos.email.subject", objectArg);
		
		Runnable emailSendingTask = new Runnable(){
			@Override
			public void run() {
				LOGGER.info("Running separate thread for TOS email notification");
				mailSenderService.sendEmail(fromEmail, toEmailList, subject, vmTemplateFile, emailBodyDetails);
			}
		};
		
		Thread msThread = new Thread(emailSendingTask);
		msThread.start();
		
		LOGGER.info("Sending users TOS response to listed email recipients...completed");
	}

	private Map<String, Object> createEmailBodyMap(
			Map<String, Object> labelMap, Map<String, Object> valueMap) {
		
		Map<String, Object> emailBodyMap = new HashMap<String, Object>();
		
		emailBodyMap.put("label", labelMap);
		emailBodyMap.put("value", valueMap);
		
		return emailBodyMap;
	}

	private Map<String, Object> createValueMap(UsersTOS usersTOS, User user) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		Map<String, Object> emailBodyValueMap = new HashMap<String, Object>();

		emailBodyValueMap.put("userName", user.getUserName());
		emailBodyValueMap.put("flagDate", df.format(usersTOS.getFlagDate()));
		
		return emailBodyValueMap;
	}
	
	
	private Map<String, Object> createLabelMap() {
		Map<String, Object> labelMap = new HashMap<String, Object>();
		setEmailLabels(labelMap, "title");
		setEmailLabels(labelMap, "userName");
		setEmailLabels(labelMap, "flagDate");
		
		return labelMap;
	}

	private void setEmailLabels(Map<String, Object> emailLabelMap, String labelKey) {
		emailLabelMap.put(labelKey, messageI18NService.getPropertyMessage("tos.email.label."+labelKey));
	}


}
