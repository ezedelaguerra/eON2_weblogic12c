package com.freshremix.webapp.controller.autodownload;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.freshremix.service.AutoDownloadService;

public class InitJobController extends AbstractJobController {

	private AutoDownloadService autoDownloadService;
	
	public void setAutoDownloadService(AutoDownloadService autoDownloadService) {
		this.autoDownloadService = autoDownloadService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sheets", autoDownloadService.getAllSheetType());
		model.put("categories", this.extractCategories(request));
		
		return new ModelAndView("autodl/jsp/input", model); 
	}

	
}
