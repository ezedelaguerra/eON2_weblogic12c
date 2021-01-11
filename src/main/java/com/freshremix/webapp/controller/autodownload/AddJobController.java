package com.freshremix.webapp.controller.autodownload;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.freshremix.autodownload.PasswordHandler;
import com.freshremix.model.AutoDownloadSchedule;
import com.freshremix.service.AutoDownloadService;
import com.freshremix.service.CategoryService;

public class AddJobController extends AbstractJobController {

	private AutoDownloadService autoDownloadService;
	private CategoryService categoryService;
	private PasswordHandler passwordHandler;
	
	public PasswordHandler getPasswordHandler() {
		return passwordHandler;
	}

	public void setPasswordHandler(PasswordHandler passwordHandler) {
		this.passwordHandler = passwordHandler;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setAutoDownloadService(AutoDownloadService autoDownloadService) {
		this.autoDownloadService = autoDownloadService;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		// Security Password Validation
		if(!passwordHandler.getSecuriryPassword().equals(request.getParameter("password"))){
			return new ModelAndView("autodl/jsp/invalid", model);
		}
		
		AutoDownloadSchedule ads = this.extractAutoDownloadSchedule(request);
		
		String res = 
			autoDownloadService.validateAutoDownloadSchedule(ads);
		
		this.setModelValues(ads, autoDownloadService, categoryService);
		
		model.put("ads", ads);
		model.put("response", res);
		
		return new ModelAndView("autodl/jsp/confirm", model); 
	}
	
}
