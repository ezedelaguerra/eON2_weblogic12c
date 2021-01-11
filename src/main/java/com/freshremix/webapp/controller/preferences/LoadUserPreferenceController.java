package com.freshremix.webapp.controller.preferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.EONUserPreference;
import com.freshremix.model.User;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.util.SessionHelper;

public class LoadUserPreferenceController implements Controller {

	private UserPreferenceService userPreferenceService;

	public void setUserPreferenceService(
			UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		User user = (User) SessionHelper.getAttribute(request,
				SessionParamConstants.USER_PARAM);

		EONUserPreference userPreference = userPreferenceService
				.getUserPreference(user.getUserId());

		boolean unpublishedAlloc = false;
		boolean unfinalizedBilling = false;
		boolean enableBAPublishOrder = false;

		if (userPreference != null) {
			unpublishedAlloc = strToBoolean(userPreference
					.getViewUnpublisheAlloc());
			unfinalizedBilling = strToBoolean(userPreference
					.getViewUnfinalizeBilling());
			enableBAPublishOrder = strToBoolean(userPreference
					.getEnableBAPublish());
		}

		ModelAndView mav = new ModelAndView("json");
		mav.addObject("unpublishedAlloc", unpublishedAlloc);
		mav.addObject("unfinalizedBilling", unfinalizedBilling);
		mav.addObject("enableBAPublishOrder", enableBAPublishOrder);
		return mav;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	private boolean strToBoolean(String str) {
		boolean status = false;

		if(str == null){
			status = false;
		}else if (str.equals("1")) {
			status = true;
		} else {
			status = false;
		}

		return status;
	}
}
