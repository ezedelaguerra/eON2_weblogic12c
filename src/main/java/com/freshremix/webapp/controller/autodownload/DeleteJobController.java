package com.freshremix.webapp.controller.autodownload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.freshremix.autodownload.PasswordHandler;
import com.freshremix.model.AutoDownloadSchedule;
import com.freshremix.service.AutoDownloadService;

public class DeleteJobController extends AbstractJobController {

	private AutoDownloadService autoDownloadService;
    private PasswordHandler passwordHandler;
	
	public PasswordHandler getPasswordHandler() {
		return passwordHandler;
	}

	public void setPasswordHandler(PasswordHandler passwordHandler) {
		this.passwordHandler = passwordHandler;
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
		
		Integer scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
		
		autoDownloadService.deleteAutoDownloadSchedule(scheduleId);
		List<AutoDownloadSchedule> list = autoDownloadService.getAllDownloadSchedule();
		
		model.put("list", list);
		
		return new ModelAndView("autodl/jsp/list", model); 
	}

}
