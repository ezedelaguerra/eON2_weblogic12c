/**
 * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
 * <BR>
 * <B>Description:</B><BR>
 * This utility class provides an adapter between Bea Weblogic pageflow objects
 * and the manager methods. It provides logic for dealing with web components
 * and translating them into calls to managers.
 * <p>
 * Revision History: <tt><PRE>
 * ============================================================================
 * Date             Author            Description
 * [MMM DD, YYYY]   [username]        [your modification]
 * Oct 11, 2010     Ogie              Class created.
 * ============================================================================
 * </PRE></tt>
 *
 * @author Ogie
 */
package com.freshremix.webapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.freshremix.constants.AuditTrailConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.constants.UserConstants;
import com.freshremix.model.AuditTrail;
import com.freshremix.model.User;
import com.freshremix.service.AuditTrailService;
import com.freshremix.util.DateFormatter;

public class AuditTrailFilter implements Filter {

	private AuditTrailService auditTrailService;
	
	private static Logger logger = Logger.getLogger(AuditTrailFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		AuditTrail audit = new AuditTrail();

		User user = (User) httpRequest.getSession().getAttribute(
				SessionParamConstants.USER_PARAM);

		if (user == null) {
			// ANOYNMOUS USER (not logged in)
			audit.setUser_id(UserConstants.ANONYMOUS_ID);
			audit.setUsername(UserConstants.ANONYMOUS_USERNAME);
		} else {
			audit.setUser_id(user.getUserId());
			audit.setUsername(user.getUserName());
		}
		audit.setIp_address(httpRequest.getRemoteAddr());
		audit.setUrl(httpRequest.getRequestURL().toString());
		audit.setUser_agent(httpRequest.getHeader(AuditTrailConstants.AUDIT_TRAIL_USER_AGENT));
		audit.setUrl_referer(httpRequest.getHeader(AuditTrailConstants.AUDIT_TRAIL_URL_REFER));
		audit.setSys_date(DateFormatter.getDateToday("yyyy/MM/dd HH:mm:ss.SSS"));
		audit.setLog_type(AuditTrailConstants.AUDIT_TRAIL_START);

		// Insert START audit trail
		long processId = auditTrailService.insertAudit(audit);
		
		
		long start = System.currentTimeMillis();
		
		// do process
		chain.doFilter(request, response);
		
		// compute the processing time
		long end = System.currentTimeMillis();
		long timeMs = end - start;
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(timeMs).append("ms] ");
		sb.append(httpRequest.getRequestURL().toString());
		logger.info(sb.toString());

		audit.setProcess_id(processId);
		audit.setSys_date(DateFormatter.getDateToday("yyyy/MM/dd HH:mm:ss.SSS"));
		audit.setLog_type(AuditTrailConstants.AUDIT_TRAIL_FINISH);
		// Insert FINISH audit trail
		auditTrailService.insertAudit(audit);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(filterConfig.getServletContext());
		auditTrailService = (AuditTrailService) wac.getBean("auditTrailService");
	}

	public void setAuditTrailService(AuditTrailService auditTrailService) {
		this.auditTrailService = auditTrailService;
	}

	public AuditTrailService getAuditTrailService() {
		return auditTrailService;
	}

}
