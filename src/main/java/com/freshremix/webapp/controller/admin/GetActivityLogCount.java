package com.freshremix.webapp.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.dao.ActivityLogDao;
import com.freshremix.model.User;
import com.freshremix.service.ActivityLogService;
import com.freshremix.util.SessionHelper;

public class GetActivityLogCount extends SimpleFormController  {
	
	ActivityLogDao activityLogDao;
	

	public void setActivityLogDao(ActivityLogDao activityLogDao) {
		this.activityLogDao = activityLogDao;
	}


	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		ModelAndView mav = new ModelAndView("json");
		
		//User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		//Integer userId = user.getUserId();
		String username = request.getParameter("username");
		String action = request.getParameter("action");
		String sheetName = request.getParameter("sheetName");
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		String userId = request.getParameter("userId");
		String targetSellerId = request.getParameter("sellerId");
		String targetBuyerId = request.getParameter("buyerId");
		String deliveryDate = request.getParameter("deliveryDate");
		
		
		
		Integer activityLogCount = activityLogDao.getActivityLogCount(username, dateFrom, dateTo, sheetName, action,userId,targetSellerId,targetBuyerId,deliveryDate);
		
		mav.addObject("activityLogCount", activityLogCount);
		
		
		return mav;
	}

}
