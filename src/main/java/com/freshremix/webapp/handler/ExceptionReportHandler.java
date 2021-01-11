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
 * Oct 7, 2010		gilwen		
 */
package com.freshremix.webapp.handler;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.json.JsonExceptionHandler;

import com.freshremix.constants.AuditTrailConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.MailSender;
import com.freshremix.model.Order;
import com.freshremix.model.User;
import com.freshremix.util.RecursiveToStringStyle;
import com.freshremix.util.SessionHelper;

/**
 * @author gilwen
 *
 */
public class ExceptionReportHandler implements JsonExceptionHandler {

	private final static String END_OF_LINE = "\n";
	private final static String TAB = "\t";
	private String emailTo;

	private static Logger logger = Logger.getLogger("weblogs");
	
	public String getEmailTo() {
		return emailTo;
	}
	
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.json.JsonExceptionHandler#triggerException(java.lang.Exception, java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void triggerException(Exception exception, Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StringBuffer content = new StringBuffer();
		User user = (User)request.getSession().getAttribute(SessionParamConstants.USER_PARAM);
		content.append("Local Address: " + request.getLocalAddr() + END_OF_LINE);
		content.append("Remote Address: " + StringUtils.trimToEmpty(request.getRemoteAddr()) + END_OF_LINE);
		content.append("User Agent: " + StringUtils.trimToEmpty(request.getHeader(AuditTrailConstants.AUDIT_TRAIL_USER_AGENT)) + END_OF_LINE);
		content.append("URL Referer: " + StringUtils.trimToEmpty(request.getHeader(AuditTrailConstants.AUDIT_TRAIL_URL_REFER)) + END_OF_LINE);
		content.append("User ID: " + user.getUserId() + END_OF_LINE);
		content.append("User Name: " + user.getUserName() + END_OF_LINE);
		
		boolean isSessionExpired = SessionHelper.isSessionExpired(request);
		content.append("Session Expired?:"+ isSessionExpired + END_OF_LINE);
		
		List<Order> allOrders = getAllOrders(request);
		if (allOrders == null || allOrders.size() == 0) {
			content.append("Order not selected" + END_OF_LINE + END_OF_LINE);
		} else {
			for (Order order : allOrders) {
				content.append("Order ID: " + order.getOrderId() + END_OF_LINE + END_OF_LINE);
			}
		}
		
		// get parameters if any
		content.append("HTTP REQUEST PARAMETERS:" + END_OF_LINE);
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String param = paramNames.nextElement();
			content.append(param + ": " + request.getParameter(param) + END_OF_LINE);
		}
		
		
		content.append(END_OF_LINE);
		content.append("SESSION ATTRIBUTES: Please check logfile for session attributes");
		content.append(END_OF_LINE);
		
		// get cause message
		content.append(END_OF_LINE);
		content.append("CAUSED BY" + END_OF_LINE);
		content.append(exception.getMessage() + END_OF_LINE);
		content.append(exception.toString() + END_OF_LINE);
		
		// get stacktrace
		content.append(END_OF_LINE);
		List<StackTraceElement> list = Arrays.asList(exception.getStackTrace());
		content.append("STACKTRACE" + END_OF_LINE);
		for (StackTraceElement ste : list) {
			content.append(TAB + ste.toString() + END_OF_LINE);
		}
		
		String prefix = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + " ";
		sendMail(content.toString(), prefix);
		
		//print session contents in logfile only
		if (!isSessionExpired) {
			printSessionAttributes(content, request);
		}
		logger.error(content);
	}
	
	private void sendMail(String content, String pref) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
		String[] address = new String[1];
		address[0] = this.getEmailTo();
		MailSender ms = new MailSender();
		ms.setFromAddress("eonExceptionReport");
		ms.setMessage(content);
		ms.setSubject(pref + "Error Report " + sdf.format(new Date()));
		ms.setToAddress(address);
		Thread msThread = new Thread(ms);
		msThread.start();
	}

	@SuppressWarnings("unchecked")
	private List<Order> getAllOrders(HttpServletRequest request) {
		return (List<Order>) SessionHelper.getAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM);
	}

	@SuppressWarnings("unchecked")
	private void printSessionAttributes(StringBuffer content,
			HttpServletRequest request) {
		content.append(END_OF_LINE);
		content.append("SESSION ATTRIBUTES:" + END_OF_LINE);
		
		try {
			Enumeration<String> attributeNames = request.getSession().getAttributeNames();
			if (attributeNames == null){
				content.append("No session attributes:"+END_OF_LINE);
			}
			while (attributeNames.hasMoreElements()) {
				String attributeName = (String) attributeNames.nextElement();
				Object sessionObject = SessionHelper.getAttribute(request, attributeName);

				content.append(attributeName + ":"
						+ getObjectToString(sessionObject)
						+ END_OF_LINE);
			}
		} catch (Exception e) {
			content.append("Unable to retrieve session parameters for error reporting");
		}
	}

	private String getObjectToString(Object sessionObject) {
		String toString = "";
		if (sessionObject == null) {
			return "session object null";
		}
		
		try {
			toString = ToStringBuilder.reflectionToString(
					sessionObject, new RecursiveToStringStyle(), true);
		} catch (Exception e) {
			toString = sessionObject.toString();
		}
		return toString;
	}

}
