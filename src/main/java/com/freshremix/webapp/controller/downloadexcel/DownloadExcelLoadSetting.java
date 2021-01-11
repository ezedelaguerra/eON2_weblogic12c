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
 * Apr 20, 2010		nvelasquez		
 */
package com.freshremix.webapp.controller.downloadexcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.AkadenSheetParams;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.DownloadExcelService;
import com.freshremix.ui.model.DownloadExcelSettingForm;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.DownloadExcelUtil;

import com.freshremix.util.SessionHelper;

/**
 * @author nvelasquez
 *
 */
public class DownloadExcelLoadSetting implements Controller {
	
	private DownloadExcelService downloadExcelService;
	
	public void setDownloadExcelService(DownloadExcelService downloadExcelService) {
		this.downloadExcelService = downloadExcelService;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//System.out.println("loading excel settings...");
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		Map<String, Object> model = new HashMap<String, Object>();
		
		String startDate = "";
		String endDate = "";
		String excelEndDate = "";
		String deliveryDate = "";
		boolean isAllDatesView = false;
		OrderSheetParam osParam = new OrderSheetParam();
		AkadenSheetParams akParam = new AkadenSheetParams();
		
		Object obj = request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		if (obj instanceof OrderSheetParam) {
			osParam = (OrderSheetParam) obj;
			startDate = osParam.getStartDate();
			endDate = osParam.getEndDate();
			excelEndDate = DateFormatter.addDays(startDate, 6);
			deliveryDate = osParam.getSelectedDate();
			isAllDatesView = osParam.isAllDatesView();
		}
		else if (obj instanceof AkadenSheetParams) {
			akParam = (AkadenSheetParams) obj;
			startDate = akParam.getStartDate();
			endDate = akParam.getEndDate();
			excelEndDate = DateFormatter.addDays(startDate, 6);
			deliveryDate = akParam.getSelectedDate();
			isAllDatesView = akParam.isAllDatesView();
		}
	    
		List<String> dateList = DateFormatter.getDateList(startDate, excelEndDate);
		List<String> dateMDList = DateFormatter.getDateListForExcel(startDate, excelEndDate);
		List<Integer> dayIndexList = DateFormatter.getDayList(startDate, excelEndDate);
		List<String> dayList = new ArrayList<String>();
		String[] weekDayNames = DownloadExcelUtil.getWeekDayNames();
		for (Integer index : dayIndexList) {
			dayList.add(weekDayNames[index.intValue()]);
		}
		
		DownloadExcelSettingForm dxls = (DownloadExcelSettingForm) SessionHelper.getAttribute(request, SessionParamConstants.DL_XLS_PARAM);
		if (dxls == null) {
			dxls = downloadExcelService.getExcelSetting(user.getUserId());
			if (dxls == null)
				/* default */
				dxls = new DownloadExcelSettingForm(startDate, endDate, deliveryDate, isAllDatesView);
		}
		
		System.out.println(dxls.toString());
		
	    //get day list mon, tue, wed, thu, fri
		model.put("dateList", dateList);
		model.put("dateMDList", dateMDList);
		model.put("dayIndexList", dayIndexList);
		model.put("dayList", dayList);
		model.put("dxls", dxls);
	    
		ModelAndView mav = new ModelAndView("json", model);
	    
	    return mav;
	}

}
