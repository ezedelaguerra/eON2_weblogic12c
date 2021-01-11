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
 * Jan 30, 2012		raquino		
 */
package com.freshremix.webapp.controller.preferences;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.ProfitPreference;
import com.freshremix.model.User;
import com.freshremix.service.UserPreferenceService;

/**
 * @author raquino
 *
 */
public class LoadProfitPreferenceController implements Controller {

	private UserPreferenceService userPreferenceService;

	public void setUserPreferenceService(
			UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("json");
		User user = (User)
			request.getSession().getAttribute(SessionParamConstants.USER_PARAM);
		
		ProfitPreference profitPref = userPreferenceService.getProfitPreference(user);
		
		mav.addObject("profitPref", profitPref);
		
		return mav;
	}

}
