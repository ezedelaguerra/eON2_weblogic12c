package com.freshremix.webapp.controller.comments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.Company;
import com.freshremix.model.User;
import com.freshremix.service.CommentsService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.SessionHelper;

public class LoadCompaniesController implements Controller {

	private CommentsService commentsService;
	
	public void setCommentsService(CommentsService commentsService) {
		this.commentsService = commentsService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		Company _company = user.getCompany();
		
		List<Company> ids = commentsService.getAssociatedCompany(user);
		
		List<FilteredIDs> tmp = new ArrayList<FilteredIDs>();
		for (Company tmpCompany : ids) {
			tmp.add(new FilteredIDs(tmpCompany.getCompanyId().toString(), tmpCompany.getCompanyName()));
		}
		
		List<FilteredIDs> list = new ArrayList<FilteredIDs>();
		list.add(new FilteredIDs("0", "All"));
		list.add(new FilteredIDs(_company.getCompanyId().toString(), _company.getCompanyName()));
		list.addAll(tmp);
		
		model.put("companies", list);
		return new ModelAndView("json",model);
	}
}