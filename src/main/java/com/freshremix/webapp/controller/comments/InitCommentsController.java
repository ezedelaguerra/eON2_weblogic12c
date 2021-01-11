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
import com.freshremix.treegrid.CommentsTreeGridItem;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

public class InitCommentsController implements Controller {

	private CommentsService commentsService;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		Integer userId = user.getUserId();
		//Integer unreadMgs = commentsService.countUnreadMessages(userId);
		String selectedTab = request.getParameter("selectedTab");
		List<CommentsTreeGridItem> inboxItems = null;
		List<CommentsTreeGridItem> outboxItems = null;
		if (StringUtil.isNullOrEmpty(selectedTab)) {
			inboxItems = commentsService.getInboxMessages(userId);
			outboxItems = commentsService.getOutboxMessages(userId);
		} else if (selectedTab.equals("a")) {
			inboxItems = commentsService.getInboxMessages(userId);
		} else if (selectedTab.equals("b")) {
			outboxItems = commentsService.getOutboxMessages(userId);
		}
		
		//model.put("unreadMessages", unreadMgs);
		model.put("inboxItems", inboxItems);
		model.put("outboxItems", outboxItems);
		
		return new ModelAndView("json",model);
	}

	public void setCommentsService(CommentsService commentsService) {
		this.commentsService = commentsService;
	}
}