package com.freshremix.webapp.controller.comments;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.User;
import com.freshremix.service.CommentsService;
import com.freshremix.util.SessionHelper;

public class CountUnreadMsgController implements Controller {

	private CommentsService commentsService;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		Integer userId = user.getUserId();
		Integer unreadMgs = commentsService.countUnreadMessages(userId);
		
		model.put("count", unreadMgs);
		return new ModelAndView("json",model);
	}

	public void setCommentsService(CommentsService commentsService) {
		this.commentsService = commentsService;
	}
}