package com.freshremix.webapp.controller.autodownload;

import static com.freshremix.constants.SystemConstants.CATEGORY_MASTER_LIST;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.AutoDownloadSchedule;
import com.freshremix.model.Category;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.service.AutoDownloadService;
import com.freshremix.service.CategoryService;
import com.freshremix.util.StringUtil;

public class AbstractJobController extends SimpleFormController {

	private static final String USER_NAME = "username";
	private static final String USER_ID = "userId";
	private static final String NEW_JOB_COLOR = "lightgreen";
	private static final String EXECUTION_TIME = "executionTime";
	private static final String DATE_LAST_DOWNLOADED = "dateLastDownloadStr";
	private static final String LEAD_TIME = "leadTime";
	private static final String SHEET_TYPE_ID = "sheetTypeId";
	private static final String SHEET_TYPE_DESC = "sheetTypeDesc";
	private static final String CATEGORY_ID = "categoryId";
	//private static final String CATEGORY_DESC = "categoryDesc";
	private static final String HAS_QUANTITY = "hasQtyUI";
	private static final String FTP_IP = "ftpIp";
	private static final String FTP_ID = "ftpId";
	private static final String FTP_PASSWD = "ftpPw";
	private static final String CONFIRM_FTP_PASSWD = "confirmFtpPw";
	private static final String SELLER = "seller";
	private static final String BUYER = "buyer";
	private static final String EXCEPT_DATE = "exceptDate";
	
	@SuppressWarnings("unchecked")
	protected List<Category> extractCategories(HttpServletRequest request) {
		Map<String, Category> map = 
			(Map<String, Category>) request.getSession().getServletContext().getAttribute(CATEGORY_MASTER_LIST);
		
		Map<Integer, Category> map2 = new HashMap<Integer, Category>();
		for (String s : map.keySet()) {
			map2.put(Integer.parseInt(s), map.get(s));
		}
		
		List<Category> list = new ArrayList<Category>();
		list.add(new Category(0, "All", "All"));
		for (Integer i : map2.keySet()) {
			list.add(map.get(i.toString()));
		}
		
		return list;
	}
	
	protected AutoDownloadSchedule extractAutoDownloadSchedule(HttpServletRequest request) {
		AutoDownloadSchedule ads = new AutoDownloadSchedule();
		ads.setColor("");
		ads.setUsername(request.getParameter(USER_NAME).trim());
		String userId = request.getParameter(USER_ID);
		ads.setUserId(!StringUtil.isNullOrEmpty(userId) ? Integer.parseInt(userId) : null);
		ads.setExecutionTime(request.getParameter(EXECUTION_TIME).trim());
		ads.setDateLastDownloadStr(request.getParameter(DATE_LAST_DOWNLOADED).trim());
		ads.setLeadTime(Integer.parseInt(request.getParameter(LEAD_TIME).trim()));
		String sheetTypeId = request.getParameter(SHEET_TYPE_ID);
		ads.setSheetTypeId(sheetTypeId != null ? Integer.parseInt(sheetTypeId) : 0);
		ads.setSheetTypeDesc(request.getParameter(SHEET_TYPE_DESC));
		ads.setCategoryId(Integer.parseInt(request.getParameter(CATEGORY_ID)));
		ads.setHasQtyUI(request.getParameter(HAS_QUANTITY));
		ads.setFtpIp(request.getParameter(FTP_IP));
		ads.setFtpId(request.getParameter(FTP_ID));
		ads.setFtpPw(request.getParameter(FTP_PASSWD));
		ads.setConfirmFtpPw(request.getParameter(CONFIRM_FTP_PASSWD));
		ads.setSeller(request.getParameter(SELLER).trim());
		ads.setBuyer(request.getParameter(BUYER).trim());
		ads.setExceptDate(request.getParameter(EXCEPT_DATE).trim());
		
		List<Category> catList = this.extractCategories(request);
		for (Category cat : catList) {
			if (cat.getCategoryId().equals(ads.getCategoryId())) {
				ads.setCategoryDesc(cat.getDescription());
				ads.setCategoryId(cat.getCategoryId());
			}
		}
		
		return ads;
	}
	
	protected void markNewAutoDownloadSchedule(AutoDownloadSchedule ads) {
		ads.setColor(NEW_JOB_COLOR);
	}
	
	protected void setModelValues(AutoDownloadSchedule ads,
			AutoDownloadService autoDownloadService, 
			CategoryService categoryService) throws ParseException {
		
		/**
		 * Set list of:
		 * 	exception dates
		 * 	sellers
		 * 	buyers
		 *  category
		 *  sheet type id
		 *  date last download
		 *  has Quantity
		 */
		List<Date> dateList = new ArrayList<Date>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		for (String str : this.parseMultipleEntry(ads.getExceptDate())) {
			Date d = formatter.parse(str.trim());
			dateList.add(d);
		}
		
		ads.setExceptDateList(dateList);
		ads.setSellerList(autoDownloadService.getUserId(this.parseMultipleEntry(ads.getSeller())));
		ads.setBuyerList(autoDownloadService.getUserId(this.parseMultipleEntry(ads.getBuyer())));
		
		User user = autoDownloadService.getUser(ads.getUsername());
		List<UsersCategory> catList = 
			categoryService.getCategoryListByUserId(user.getUserId().toString());
		
		if (!StringUtil.isNullOrEmpty(ads.getSheetTypeDesc())) {
			for (UsersCategory uc : catList) {
				if (ads.getSheetTypeDesc().equalsIgnoreCase(uc.getCategoryAvailable()))
					ads.setSheetTypeDesc(uc.getCategoryAvailable());
			}
		}
		
		ads.setUserId(user.getUserId());
		Integer sheetTypeId = autoDownloadService.getSheetTypeId(user.getRole().getRoleId(), ads.getSheetTypeDesc());
		ads.setSheetTypeId(sheetTypeId);
		
		ads.setDateLastDownload(formatter.parse(ads.getDateLastDownloadStr()));
		if (!StringUtil.isNullOrEmpty(ads.getRawHasQtyUI()))
			ads.setHasQty("1");
		else
			ads.setHasQty("0");
	}
	
	protected void validateAutoDownloadSchedule(AutoDownloadSchedule ads) {
		
	}
	
	private List<String> parseMultipleEntry(String input) {
		List<String> list = new ArrayList<String>();
		
		StringTokenizer st = new StringTokenizer(input,",");
		while(st.hasMoreTokens()) {
			list.add(st.nextToken().trim());
		}
			
		return list;
	}

	protected String convertDateToString(Date date) {
		String newDateFormat = null;
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		newDateFormat = formatter.format(date);
		return newDateFormat;
	}
	
	protected String convertDateListToString(List<Date> dateList){
		String newDateFormat = "";
		for(Date date: dateList){
			newDateFormat = newDateFormat + convertDateToString(date) + ",";
		}
		return newDateFormat;
	}

}
