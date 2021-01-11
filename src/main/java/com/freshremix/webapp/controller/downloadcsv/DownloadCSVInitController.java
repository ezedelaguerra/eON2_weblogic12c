
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
 * March 31, 2010		Ogie Pulido		
 */
package com.freshremix.webapp.controller.downloadcsv;

import static com.freshremix.constants.SystemConstants.CATEGORY_MASTER_LIST;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.Category;
import com.freshremix.model.DownloadCSVSettings;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.DownloadCSVService;
import com.freshremix.util.SessionHelper;

public class DownloadCSVInitController implements Controller {

	private DownloadCSVService downloadCSVService;
	
	public void setDownloadCSVService(DownloadCSVService downloadCSVService) {
		this.downloadCSVService = downloadCSVService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//1. initialize the list of sheet type 
    	//2. initialize the list of category
		//3. initialize data from the sheet
    	//   3.1. Start date
    	//   3.2. End date   
    	//   3.3. Sheet Type
    	//   3.4. Seller/Buyer list
    	//   3.5. Category
		
		OrderSheetParam osParam = (OrderSheetParam) SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM);
		User user = (User) SessionHelper.getAttribute (request, SessionParamConstants.USER_PARAM);
		Map<String, Object> model = new HashMap<String, Object>();
		DownloadCSVSettings initCSV = new DownloadCSVSettings();
		
		initCSV = downloadCSVService.initializeDownloadCSV(user, osParam);
		initCSV.setCategoryMaster(extractCategoryMasterList(request));
		
		model.put("downloadCSVSettings", initCSV);
		
		return new ModelAndView("json", model);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Category> extractCategoryMasterList(HttpServletRequest request) {
		
		Map<String, Category> catMap = (Map<String, Category>)
			request.getSession().getServletContext().getAttribute(CATEGORY_MASTER_LIST);
			
		return catMap;
	}	
}
