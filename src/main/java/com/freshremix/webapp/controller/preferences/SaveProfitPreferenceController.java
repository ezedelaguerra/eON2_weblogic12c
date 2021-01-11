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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.ProfitPreference;
import com.freshremix.model.User;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.util.SessionHelper;

/**
 * @author raquino
 *
 */
public class SaveProfitPreferenceController extends SimpleFormController {

	private UserPreferenceService userPreferenceService;
	
	public void setUserPreferenceService(UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}
	
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		ProfitPreference profitPref = (ProfitPreference) command;		
		profitPref.setUserId(user.getUserId());
		
		userPreferenceService.saveProfitPreference(profitPref);
		user.getPreference().setProfitPreference(profitPref);
		SessionHelper.setAttribute(request, SessionParamConstants.USER_PARAM, user);
		
		Map<String,Object> model = new HashMap<String,Object>();
		return new ModelAndView("json", model);
	}

}
