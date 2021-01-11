package com.freshremix.webapp.controller.ordersheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.User;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.SessionHelper;

public class AdminCompanyLevelFilterController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		List<FilteredIDs> tmp = new ArrayList<FilteredIDs>();
		tmp.add(new FilteredIDs("0", "All"));
		tmp.add(new FilteredIDs(user.getCompany().getCompanyId().toString(), user.getCompany().getCompanyName()));
		model.put("response", tmp);
		
		return new ModelAndView("json",model);
	}
}