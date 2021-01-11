package com.freshremix.webapp.controller.preferences;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.LockButtonStatus;
import com.freshremix.model.ProfitPreference;
import com.freshremix.model.User;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.util.SessionHelper;

/**
 * @author raquino
 *
 */
public class SaveLockButtonStatusController extends SimpleFormController {

	private UserPreferenceService userPreferenceService;
	
	public void setUserPreferenceService(UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}
	
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		LockButtonStatus lockButtonStatus = (LockButtonStatus) command;		
		lockButtonStatus.setUserId(user.getUserId());
		
		userPreferenceService.saveLockButtonStatus(lockButtonStatus);
		user.getPreference().setLockButtonStatus(lockButtonStatus);
		SessionHelper.setAttribute(request, SessionParamConstants.USER_PARAM, user);
		
		Map<String,Object> model = new HashMap<String,Object>();
		return new ModelAndView("json", model);
	}

}
