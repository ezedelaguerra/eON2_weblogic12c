package com.freshremix.webapp.controller.preferences;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.EONUserPreference;
import com.freshremix.model.User;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.util.SessionHelper;

public class SaveUserPreferences extends SimpleFormController {

	private UserPreferenceService userPreferenceService;

	public void setUserPreferenceService(
			UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String value = request.getParameter("value").toString();
		String chkBox = request.getParameter("chkBox").toString();
		User user = (User) SessionHelper.getAttribute(request,
				SessionParamConstants.USER_PARAM);
		EONUserPreference userPreference = userPreferenceService
				.getUserPreference(user.getUserId());

		EONUserPreference emptyUserPreference = new EONUserPreference();

		if (userPreference == null) {
			this.populateUserPreference(emptyUserPreference, value, chkBox,
					user);
			userPreferenceService.insertUserPreference(emptyUserPreference);
		} else {
			this.populateUserPreference(userPreference, emptyUserPreference,
					value, chkBox, user);
			userPreferenceService
					.updateUserPreferenceByPreferenceId(emptyUserPreference);
		}
		
		//Added by Rhoda Nov. 25, 2010
		if (emptyUserPreference.getViewUnpublisheAlloc() != null && 
				emptyUserPreference.getViewUnpublisheAlloc().equals("1"))
			user.setViewUnpublisheAlloc("true"); 
		else user.setViewUnpublisheAlloc("false");
		if (emptyUserPreference.getEnableBAPublish() != null && 
				emptyUserPreference.getEnableBAPublish().equals("1"))
			user.setEnableBAPublish("true"); 
		else user.setEnableBAPublish("false");
		if (emptyUserPreference.getViewUnfinalizeBilling() != null && 
				emptyUserPreference.getViewUnfinalizeBilling().equals("1"))
			user.setViewUnfinalizeBilling("true"); 
		else user.setViewUnfinalizeBilling("false");
		if (emptyUserPreference.getAutoPublishAlloc() != null && 
				emptyUserPreference.getAutoPublishAlloc().equals("1"))
			user.setAutoPublishAlloc("true"); 
		else user.setAutoPublishAlloc("false");
		
		user.setPreference(userPreferenceService.getUserPreference(user));
		request.getSession().setAttribute(SessionParamConstants.USER_PARAM, user);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("response", user);

		ModelAndView mav = new ModelAndView("json", model);
		return mav;
	}

	/**
	 * 
	 * @param userPreference
	 */
	private void populateUserPreference(EONUserPreference userPreference,
			String value, String chkBox, User user) {

		userPreference.setUserId(user.getUserId());
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)) {
			userPreference.setEnableBAPublish(value.equals("true") ? "1":"0");
		} else {

			if (chkBox.equals("0")) {
				userPreference.setViewUnpublisheAlloc(value.equals("true") ? "1":"0");
				userPreference.setViewUnfinalizeBilling("0");
				userPreference.setAutoPublishAlloc("0");
				userPreference.setDisplayAllocQty("0");
			} else if (chkBox.equals("1")) {
				userPreference.setViewUnfinalizeBilling(value.equals("true") ? "1":"0");
				userPreference.setViewUnpublisheAlloc("0");
				userPreference.setAutoPublishAlloc("0");
				userPreference.setDisplayAllocQty("0");
			} else if (chkBox.equals("2")) {
				userPreference.setAutoPublishAlloc(value.equals("true") ? "1":"0");
				userPreference.setViewUnpublisheAlloc("0");
				userPreference.setViewUnfinalizeBilling("0");
				userPreference.setDisplayAllocQty("0");
			} else if (chkBox.equals("3")) {
				userPreference.setDisplayAllocQty(value.equals("true") ? "1":"0");
				userPreference.setViewUnpublisheAlloc("0");
				userPreference.setViewUnfinalizeBilling("0");
				userPreference.setAutoPublishAlloc("0");
			}
		}
	}

	/**
	 * 
	 * @param userPreference
	 * @param emptyUserPreference
	 * @param value
	 * @param chkBox
	 * @param user
	 */
	private void populateUserPreference(EONUserPreference userPreference,
			EONUserPreference emptyUserPreference, String value, String chkBox,
			User user) {

		emptyUserPreference.setUserPreferenceId(userPreference
				.getUserPreferenceId());
		emptyUserPreference.setUserId(user.getUserId());
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)) {
			emptyUserPreference.setEnableBAPublish(value.equals("true") ? "1":"0");
		} else {

			if (chkBox.equals("0")) {
				emptyUserPreference.setViewUnpublisheAlloc(value.equals("true") ? "1":"0");
				emptyUserPreference.setViewUnfinalizeBilling(userPreference
						.getViewUnfinalizeBilling());
				emptyUserPreference.setAutoPublishAlloc(userPreference
						.getAutoPublishAlloc());
				emptyUserPreference.setDisplayAllocQty(userPreference.getDisplayAllocQty());
			} else if (chkBox.equals("1")) {
				emptyUserPreference.setViewUnfinalizeBilling(value.equals("true") ? "1":"0");
				emptyUserPreference.setViewUnpublisheAlloc(userPreference
						.getViewUnpublisheAlloc());
				emptyUserPreference.setAutoPublishAlloc(userPreference
						.getAutoPublishAlloc());
				emptyUserPreference.setDisplayAllocQty(userPreference.getDisplayAllocQty());
			} else if (chkBox.equals("2")) {
				emptyUserPreference.setAutoPublishAlloc(value.equals("true") ? "1":"0");
				emptyUserPreference.setViewUnpublisheAlloc(userPreference
						.getViewUnpublisheAlloc());
				emptyUserPreference.setViewUnfinalizeBilling(userPreference
						.getViewUnfinalizeBilling());
				emptyUserPreference.setDisplayAllocQty(userPreference.getDisplayAllocQty());
			} else if (chkBox.equals("3")) {
				emptyUserPreference.setDisplayAllocQty(value.equals("true") ? "1":"0");
				emptyUserPreference.setAutoPublishAlloc(userPreference
						.getAutoPublishAlloc());
				emptyUserPreference.setViewUnpublisheAlloc(userPreference
						.getViewUnpublisheAlloc());
				emptyUserPreference.setViewUnfinalizeBilling(userPreference
						.getViewUnfinalizeBilling());
			}
		}
	}

}
