package com.freshremix.webapp.controller.preferences;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.LockButtonStatus;
import com.freshremix.model.ProfitPreference;
import com.freshremix.model.User;
import com.freshremix.service.UserPreferenceService;

public class LoadLockButtonStatusController implements Controller {

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
		
		LockButtonStatus lockButtonStatus = userPreferenceService.getLockButtonStatus(user);
		
		mav.addObject("lockBtnStatusObj", lockButtonStatus);
		
		return mav;
	}

}
