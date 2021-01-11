package com.freshremix.webapp.controller.preferences;

import static com.freshremix.constants.SystemConstants.EON_HEADER_NAMES;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.EONHeader;
import com.freshremix.model.User;

public class LoadHideColumnController implements Controller {
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("json");
		User user = (User)
			request.getSession().getAttribute(SessionParamConstants.USER_PARAM);
		
		ServletContext sc = request.getSession().getServletContext();
		EONHeader header = (EONHeader) sc.getAttribute(EON_HEADER_NAMES);
		
		mav.addObject("hideColumn", user.getPreference().getHideColumn());
		mav.addObject("nameMap", header.getSheetMap());
		
		return mav;
	}

}
