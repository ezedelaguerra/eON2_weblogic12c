package com.freshremix.webapp.controller.preferences;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.HideColumn;
import com.freshremix.model.User;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.util.SessionHelper;

public class SaveHideColumnController extends SimpleFormController {
	
	private UserPreferenceService userPreferenceService;
	
	public void setUserPreferenceService(UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}
	
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		HideColumn hc = (HideColumn) command;
		User user = (User)
			request.getSession().getAttribute(SessionParamConstants.USER_PARAM);
		
		hc.setUserId(user.getUserId());
		
		userPreferenceService.saveHideColumnPreference(hc);
		
		user.getPreference().setHideColumn(hc);
		SessionHelper.setAttribute(request, SessionParamConstants.USER_PARAM, user);
		
		return new ModelAndView("json", model);
	}

}
