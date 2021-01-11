package com.freshremix.webapp.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.service.DownloadUserDetailsService;

public class DownloadUserDetailsController implements Controller{

	private  DownloadUserDetailsService downloadUserDetailsService;
	
	
	
	public void setDownloadUserDetailsService(
			DownloadUserDetailsService downloadUserDetailsService) {
		this.downloadUserDetailsService = downloadUserDetailsService;
	}



	/**
	 * Downloads the user details data
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String filename = "UserData_"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".csv";
		Map<String, Object> model = new HashMap<String, Object>();
		ModelAndView mav = new ModelAndView("genericcsv", model);
		String filePath = downloadUserDetailsService.downloadUserDetails("CSV");
		mav.addObject("filePath",filePath);
		mav.addObject("fileName", filename);

		return mav;
	}
}
