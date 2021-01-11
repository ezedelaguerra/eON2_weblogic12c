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
 * Jan 12, 2012		raquino		
 */
package com.freshremix.webapp.controller.autodownload;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.freshremix.autodownload.PasswordHandler;
import com.freshremix.model.AutoDownloadSchedule;
import com.freshremix.service.AutoDownloadService;

/**
 * @author raquino
 *
 */
public class ViewJobDetailsController extends AbstractJobController {

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
		
		Integer scheduleCsvId = Integer.parseInt(request.getParameter("scheduleId"));
		
		AutoDownloadSchedule autoDl = autoDownloadService.getDownloadScheduleDetails(scheduleCsvId);
		autoDl.setDateLastDownloadStr(this.convertDateToString(autoDl.getDateLastDownload()));
		autoDl.setExceptDate(this.convertDateListToString(autoDl.getExceptDateList()));
		
		model.put("autoDl", autoDl);
		return new ModelAndView("autodl/jsp/details", model); 
	}
}
