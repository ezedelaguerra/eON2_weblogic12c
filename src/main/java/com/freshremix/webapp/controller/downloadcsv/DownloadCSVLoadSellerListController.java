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
 * April 30, 2011		Rhoda Aquino	
 */
package com.freshremix.webapp.controller.downloadcsv;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.DownloadCSVSettings;
import com.freshremix.model.User;
import com.freshremix.service.DownloadCSVService;
import com.freshremix.util.ArrayUtil;
import com.freshremix.util.SessionHelper;

public class DownloadCSVLoadSellerListController implements Controller {

	private DownloadCSVService downloadCSVService;

	public void setDownloadCSVService(DownloadCSVService downloadCSVService) {
		this.downloadCSVService = downloadCSVService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);

		DownloadCSVSettings downloadCSVSettings = new DownloadCSVSettings();

		downloadCSVSettings.setStartDate(request.getParameter("startDate").replaceAll("/", ""));
		downloadCSVSettings.setEndDate(request.getParameter("endDate").replaceAll("/", ""));
		
		Serializer serializer = new JsonSerializer();
		String[] arrBuyerIds = (String[])serializer.deserialize(request.getParameter("selectedBuyerIds"), String[].class);
		if(arrBuyerIds != null){
			downloadCSVSettings.setSelectedBuyerIds(ArrayUtil.convertStringArray(arrBuyerIds));
		}

		// call service
		downloadCSVSettings = downloadCSVService.loadSellerList(user, downloadCSVSettings);

		model.put("downloadCSVSettings", downloadCSVSettings);
		return new ModelAndView("json", model);
	}
}
