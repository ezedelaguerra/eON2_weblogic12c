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
 * May 31, 2010		Pammie		
 */
package com.freshremix.webapp.controller.uploadcsv;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.constants.UploadCsvMessages;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SKU;
import com.freshremix.model.User;
import com.freshremix.service.CategoryService;
import com.freshremix.service.CompanyInformationService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.treegrid.CommentsTreeGridItem;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.UploadCSVUtil;

/**
 * @author Pammie
 *
 */
public class ValidateCSVController implements Controller {

	private List skuCSVList = new ArrayList();

	private List categoryTabs;
	
	private List<Order> allOrdersMap;
	
	private Map<Integer, SKU> skuObjMap;
	
	private User user;
	
	private OrderSheetParam osParam;
	
	private DealingPattern dp;
	
	private CategoryService categoryService;
	
	private OrderSheetService orderSheetService;
	
	private UsersInformationService userInfoService;
	
	private CompanyInformationService companyInfoService;
	
	private OrderUnitService orderUnitService;
	
	private SKUGroupService skuGroupService;

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println("test - khole");
		List<CommentsTreeGridItem> rowError = new ArrayList<CommentsTreeGridItem>();
    	CommentsTreeGridItem it = new CommentsTreeGridItem();

		String fileName = "C:/test.csv";
    	Integer errorFlag = 1;
    	String validityDate ="";
    	
		this.skuCSVList = new ArrayList();
		this.categoryTabs = categoryService.getAllCategory();
		this.allOrdersMap = (List<Order>) SessionHelper.getAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM);
		this.skuObjMap = (Map<Integer, SKU>)SessionHelper.getAttribute(request, SessionParamConstants.SKU_OBJ_MAP_PARAM);
		this.user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		this.osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		this.dp = (DealingPattern) SessionHelper.getAttribute(request, SessionParamConstants.DEALING_PATTERN_PARAM);
		
		InputStream inputStream = request.getInputStream();
    	BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
		
		String line;
        validityDate = SessionHelper.getAttribute(request, SessionParamConstants.DELIVERY_DATE_PARAM).toString();
		Integer rowNum = 0;
		Integer lineNumber = 0;
		System.out.println("readline");
		while ((line = reader1.readLine()) != null) {
		
			if(line.indexOf(",") < 0){
				System.out.println("filename" + line);
				if(line.indexOf("filename") > 0){
					CommentsTreeGridItem cg = getErrorForFileName(line);
					if(cg !=null){
						rowError.add(cg);
						break;
					}					
				}
				
				rowNum++;
				continue;
			}
			System.out.println("line" + line);
			UploadCSVUtil skuCSV = new UploadCSVUtil(line);
			//rowError = skuCSV.validateFields(validityDate, this.user.getUserId(), lineNumber);
			
			if(lineNumber >0){
				rowError.addAll(skuCSV.validateFields(validityDate, this.user.getUserId(), lineNumber));
			}
			
			this.skuCSVList.add(skuCSV);
			lineNumber++;
			rowNum++;
		}

		//save values to session
		SessionHelper.setAttribute(request, "errorFlag", errorFlag);
		SessionHelper.setAttribute(request, "uploadError", rowError);
		SessionHelper.setAttribute(request, "skuCSVList",skuCSVList);
		SessionHelper.setAttribute(request, "validityDate",validityDate);
		SessionHelper.setAttribute(request, "categoryTabs",this.categoryTabs);
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("errorFlag", errorFlag);
		model.put("uploadError", rowError);
		model.put("skuCSVList",skuCSVList);
		model.put("validityDate",validityDate);
		System.out.println("csv file checking finished");
		return new ModelAndView("json",model);
	}
	
		
	private CommentsTreeGridItem getErrorForFileName(String line){
		CommentsTreeGridItem cg = null;
		int pos = line.indexOf("filename");
		String fileName = line.substring(pos,line.length());
		int lastPos = fileName.lastIndexOf(".");
		String ext = fileName.substring(lastPos,fileName.length()-1);
		System.out.println(fileName + "filename");
		System.out.println(fileName + "file" + ext +"ext");
		ext.replaceAll("''", "");
		if(!isCSV(ext)){
			String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_CSV;
			cg = new CommentsTreeGridItem();
			cg.addCell("");
			cg.addCell("");
			cg.addCell(errorMessage);
		}

		return cg;
	}	
		
	
	/**
	 * Checks if upload file extension is CSV.
	 * 
	 * @param fileName
	 */
	public boolean isCSV(String fileName) {

		int len = fileName.length();
		if (len == 0)
			return false;

		if (fileName.substring(len - 4).equalsIgnoreCase(".CSV"))
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if header is ok
	 * 
	 * @param line
	 */
	public boolean headerOk(String line) {

		String[] splits = line.split(",");

		if (splits.length != UploadCSVUtil.header.length)
			return false;

		for (int i = 0; i < UploadCSVUtil.header.length; i++) {
			if (!splits[i].trim().equalsIgnoreCase(UploadCSVUtil.header[i])) {
				return false;
			}
		}

		return true;
	}
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setCompanyInfoService(CompanyInformationService companyInfoService) {
		this.companyInfoService = companyInfoService;
	}

	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}
}
