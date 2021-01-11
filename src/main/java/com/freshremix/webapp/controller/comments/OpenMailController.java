package com.freshremix.webapp.controller.comments;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.service.CommentsService;

public class OpenMailController implements Controller {

	private CommentsService commentsService;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		
		String selectedId = request.getParameter("selectedId");
		commentsService.updateOpenMailStatus(Integer.valueOf(selectedId));
		
		return new ModelAndView("json",model);
	}

	public void setCommentsService(CommentsService commentsService) {
		this.commentsService = commentsService;
	}
}