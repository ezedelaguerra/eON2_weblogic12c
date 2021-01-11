package com.freshremix.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * 
 * @author raquino
 */
public class MailSenderUtil {

	private static JavaMailSender mailHost;

	public void setMailHost(JavaMailSenderImpl mailHost) {
		
		Properties mailProperties = mailHost.getSession().getProperties();

		String mailUser = mailProperties.getProperty("mail.user");
		String mailPassword = mailProperties.getProperty("mail.password");

		mailHost.setUsername(mailUser);
		mailHost.setPassword(mailPassword);

		MailSenderUtil.mailHost = mailHost;
	}


	public static boolean sendMail(String subject, String body, String from,
			String to) {
		String[] toArray = { to };
		return sendMail(subject, body, from, toArray, null);
	}

	public static boolean sendMail(final String subject, final String body,
			String from, String[] to, String filename) {
		boolean isMailSent = false;
		int sendCnt = 0;
		try {
			//System.out.println("MailSenderUtil.from "+ MailSenderUtil.from);
		    //final InternetAddress addressFrom = new InternetAddress(MailSenderUtil.from);
			final InternetAddress[] addressTo = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				addressTo[i] = new InternetAddress(to[i]);
			}

			while (!isMailSent && sendCnt <= 3) {
				sendCnt++;
				try {
					MimeMessagePreparator preparator = new MimeMessagePreparator() {
						public void prepare(MimeMessage mimeMessage)
								throws Exception {
							try {
								mimeMessage.setRecipients(
										Message.RecipientType.TO, addressTo);
								//mimeMessage.setFrom(addressFrom);
								mimeMessage.setSubject(subject);
								mimeMessage.setText(body);
							} catch (javax.mail.MessagingException e) {
								System.err.println(e.getMessage());
							}
						}
					};
					mailHost.send(preparator);
					// Email sending status
					isMailSent = true;
				} catch (MailException ex) {
					// log it and go on
					System.err.println(ex.getMessage());
					isMailSent = false;
				}
			}

		} catch (AddressException ex) {
			// log it and go on
			System.err.println(ex.getMessage());
			isMailSent = false;
		}
		return isMailSent;
	}
}
