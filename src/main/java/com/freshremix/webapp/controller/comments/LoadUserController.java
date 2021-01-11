package com.freshremix.webapp.controller.comments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.User;
import com.freshremix.service.CommentsService;
import com.freshremix.ui.model.EmailFilter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

public class LoadUserController implements Controller {

	private CommentsService commentsService;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		String userCompanyId = request.getParameter("companyId");
		List<Integer> companyId = OrderSheetUtil.toList(userCompanyId);
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		List<EmailFilter> tmp = commentsService.getUsersByCompanyId(user, companyId);
		model.put("companies", tmp);
		
		return new ModelAndView("json",model);
	}

	public void setCommentsService(CommentsService commentsService) {
		this.commentsService = commentsService;
	}
}