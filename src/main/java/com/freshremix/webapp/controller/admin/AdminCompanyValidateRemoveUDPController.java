package com.freshremix.webapp.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.service.CompanyInformationService;

public class AdminCompanyValidateRemoveUDPController implements Controller {

	private CompanyInformationService companyInfoService;
	
	public void setCompanyInfoService(CompanyInformationService companyInfoService) {
		this.companyInfoService = companyInfoService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		String roleId = request.getParameter("roleId");
		String userId1 = request.getParameter("userId1");
		String userId2 = request.getParameter("userId2");
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		boolean hasOrder = companyInfoService.hasOrder(Integer.valueOf(roleId),Integer.valueOf(userId1), Integer.valueOf(userId2), dateFrom, dateTo);
		
		if (hasOrder) model.put("result", false);
		else model.put("result", true);
		
		return new ModelAndView("json",model);
	}
}