/**
 * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
 * <BR>
 * <B>Description:</B><BR>
 * Describe class or interface.<BR>
 * <BR>
 * <B>Known Bugs:</B>
 * none<BR>
 * <BR>
 * <B>History:</B>
 * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
 * date       name            changes
 * ------------------------------------------------------------------------------
 * Feb 22, 2010		raquino		
 */
package com.freshremix.webapp.controller.autodownload;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.freshremix.model.AutoDownloadSchedule;
import com.freshremix.service.AutoDownloadService;
import com.freshremix.service.CategoryService;

public class ConfirmJobController extends AbstractJobController {

	
	private AutoDownloadService autoDownloadService;
	private CategoryService categoryService;
	
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
		
		AutoDownloadSchedule newAds = this.extractAutoDownloadSchedule(request);
		this.setModelValues(newAds, autoDownloadService, categoryService);
		
		this.markNewAutoDownloadSchedule(newAds);
		
		autoDownloadService.saveJob(newAds);
		autoDownloadService.scheduleJob(newAds);
		
		List<AutoDownloadSchedule> list = autoDownloadService.getAllDownloadScheduleExcludeSchedule(newAds.getScheduleCsvId());
		list.add(newAds);
		
		Collections.sort(list);
		
		model.put("list", list);
		
		return new ModelAndView("autodl/jsp/list", model); 
	}

}