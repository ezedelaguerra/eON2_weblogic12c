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
 * May 18, 2010		gilwen		
 */
package com.freshremix.webapp.controller.preferences;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.User;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.util.SessionHelper;

/**
 * @author gilwen
 *
 */
public class SaveColumnWidthController implements Controller {
	
	private UserPreferenceService userPreferenceService;
	
	public void setUserPreferenceService(UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		Map<String,Object> model = new HashMap<String,Object>();

		String widthStr = StringUtils.substringBefore(request.getParameter("width"), ".");
		String columnId = request.getParameter("columnId");
		if (StringUtils.isNotBlank(columnId) && StringUtils.isNotBlank(widthStr)) {
			
			int widthInt = Integer.parseInt(widthStr);
			
			userPreferenceService.saveWidthColumn(columnId, widthInt, user);
		}
		
		return new ModelAndView("json",model);
	}
}