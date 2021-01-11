package com.freshremix.webapp.controller.autodownload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.freshremix.model.AutoDownloadSchedule;
import com.freshremix.service.AutoDownloadService;

public class ListJobController extends AbstractJobController {

	private AutoDownloadService autoDownloadService;
	
	public void setAutoDownloadService(AutoDownloadService autoDownloadService) {
		this.autoDownloadService = autoDownloadService;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		List<AutoDownloadSchedule> list = autoDownloadService.getAllDownloadSchedule();
		
		model.put("list", list);
		return new ModelAndView("autodl/jsp/list", model); 
	}

}
