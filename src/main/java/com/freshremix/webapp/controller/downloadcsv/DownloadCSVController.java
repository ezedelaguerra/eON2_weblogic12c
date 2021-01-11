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
 * 2010/05/17		Jovino Balunan		
 */
package com.freshremix.webapp.controller.downloadcsv;

import static com.freshremix.constants.SystemConstants.EON_HEADER_NAMES;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.Category;
import com.freshremix.model.DownloadCSVSettings;
import com.freshremix.model.EONHeader;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SheetData;
import com.freshremix.model.User;
import com.freshremix.service.AppSettingService;
import com.freshremix.service.DownloadCSVService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.SheetDataService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.util.CategoryUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.DownloadCSVUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.SheetDataUtil;
import com.freshremix.util.StringUtil;

/**
 * @author Jovino Balunan
 *
 */
public class DownloadCSVController implements Controller {
	
	private SheetDataService sheetDataService;
	private AppSettingService appSettingService;
	
	public void setSheetDataService(SheetDataService sheetDataService) {
		this.sheetDataService = sheetDataService;
	}
	
	public void setAppSettingService(AppSettingService appSettingService) {
		this.appSettingService = appSettingService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
	    Object json = request.getParameter("_json");
	    Serializer serializer = new JsonSerializer();
	    DownloadCSVSettings downloadCSVSettings = (DownloadCSVSettings) serializer.deserialize(json, DownloadCSVSettings.class);
	    Map<String,Category> categoryMap = appSettingService.getCategoryMasterList();
	    
	    // Set selected seller/buyer depending on role type
	    User user = (User) SessionHelper.getAttribute (request, SessionParamConstants.USER_PARAM);
	    Long longRoleId = user.getRole().getRoleId();
		if(longRoleId.equals(RoleConstants.ROLE_SELLER)){
			List<Integer> sellerOnlyList = new ArrayList<Integer>();
			sellerOnlyList.add(user.getUserId());
			downloadCSVSettings.setSelectedSellerIds(sellerOnlyList);			
		}else if(longRoleId.equals(RoleConstants.ROLE_BUYER)){
			List<Integer> buyerOnlyList = new ArrayList<Integer>();
			buyerOnlyList.add(user.getUserId());
			downloadCSVSettings.setSelectedBuyerIds(buyerOnlyList);
		}
	    		
	    //call service
		String startDate = downloadCSVSettings.getStartDate().replaceAll("/", "");
		String endDate = downloadCSVSettings.getEndDate().replaceAll("/", "");
		List<Integer> selectedSellerIds = SheetDataUtil.convertStringToInteger(downloadCSVSettings.getSelectedSellerIds());
		List<Integer> selectedBuyerIds = SheetDataUtil.convertStringToInteger(downloadCSVSettings.getSelectedBuyerIds());
		List<Integer> selectedCategoryIds = SheetDataUtil.convertStringToInteger(downloadCSVSettings.getSelectedCategoryIds());
		Integer sheetTypeId = new Integer(downloadCSVSettings.getSheetTypeId());
		boolean hasQuantity = false;			
		if (downloadCSVSettings.getHasQty().equalsIgnoreCase("true"))hasQuantity = true;
		
		SheetData sheetData = new SheetData();
		String cvsListStr;

		ServletContext sc = request.getSession().getServletContext();
		EONHeader eonHeader = (EONHeader) sc.getAttribute(EON_HEADER_NAMES);
		
		/**
		 * Temporary used old implementation for AKADEN SHEETS
		 */
		
		if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_AKADEN_SHEET) || 
			sheetTypeId.equals(SheetTypeConstants.SELLER_AKADEN_SHEET) ){
			List<String> deliveryDates = DateFormatter.getDateList(startDate, endDate);
			Map<Integer, User> map_names_and_ids = userInfoService.mapUserNames(selectedBuyerIds);
			Map<Integer, String> mapUsernames = new HashMap<Integer, String>();
			for(Integer buyerId : selectedBuyerIds) {
				User users = map_names_and_ids.get(buyerId);
				mapUsernames.put(buyerId, users.getName());
			}
			cvsListStr = this.populateBillingAndAkadenData(mapUsernames, selectedSellerIds, selectedBuyerIds, user, deliveryDates, 
					sheetTypeId, selectedCategoryIds, "false", null, eonHeader);
		}else{
			
			sheetData = sheetDataService.loadSheetData(user, startDate, endDate, selectedSellerIds, 
					selectedBuyerIds, selectedCategoryIds, sheetTypeId, hasQuantity, true, null);
			cvsListStr = DownloadCSVUtil.createCSVList(sheetData, user, eonHeader, categoryMap);
			
		}
		
		model.put("list", cvsListStr);
		model.put("sheettype", SheetTypeConstants.getSheetFileNameDesc(sheetTypeId));
		model.put("startdate", startDate);
		model.put("enddate", endDate);
		model.put("category", this.getCategoryName(selectedCategoryIds, categoryMap));
		
		return new ModelAndView("csv", model);
	}

	private String getCategoryName(List<Integer> categoryList, Map<String,Category> categoryMap) {
		String categoryName = "";
		if (categoryList.size() > 1) { 
			categoryName = "all";
		}
		else {
			categoryName = CategoryUtil.getCategoryDesc(categoryList.get(0), categoryMap);
		}
		return categoryName;
	}
	
	
	
	/****************************************************************
	 * OLD DOWNLOAD CSV by jovs
	 ****************************************************************/	
	
	private static String CSV_DELIMETER = ",";
	private BigDecimal bdZero = new BigDecimal(0);
	private DownloadCSVService downloadCSVService;
	private OrderSheetService orderSheetService;
	private UsersInformationService userInfoService;
	public void setDownloadCSVService(DownloadCSVService downloadCSVService) {
		this.downloadCSVService = downloadCSVService;
	}
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	private List<Order> allOrders (List<Integer> sellerIds, List<Integer> buyerIds, List<String> deliverydate) {
		List<Order> allOrders = null;		
		allOrders = orderSheetService.getAllOrders(buyerIds, deliverydate, sellerIds);
		return allOrders;
	}
	
	private ArrayList<Integer> listOrderIds(List<Order> allOrders) {
		ArrayList<Integer> orderIds = new ArrayList<Integer>();
		for(Order order : allOrders) {
			orderIds.add(order.getOrderId());
		}
		return orderIds;
	}
	
	@SuppressWarnings("unused")
	private BigDecimal getPrice1(User user, BigDecimal price) {
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER))
			return null;
		else if (user.getRole().getRoleId().equals(RolesUtil.ROLEID_BUYER_ADMIN))
			return null;
		else
			return price;
	}
	
	@SuppressWarnings("unused")
	private BigDecimal getPrice2(User user, BigDecimal price) {
		if (user.getRole().getRoleId().equals(RolesUtil.ROLEID_BUYER))
			return null;
		else if (user.getRole().getRoleId().equals(RolesUtil.ROLEID_BUYER_ADMIN))
			return price;
		else 
			return price;
	}
	
	
	@SuppressWarnings({ "unused", "null" })
	private String populateBillingAndAkadenData(Map<Integer, String> mapIdNames, List<Integer> sellerIds, List<Integer> selectedBuyerIds, User user, List<String> deliveryDates, 
			Integer sheetTypeId, List<Integer> categories, String hasQuantity, OrderSheetParam osParam, EONHeader eonHeader) {
		
		Map<String,Category> categoryMap = appSettingService.getCategoryMasterList();
		String csvListStr = "";
		int cnt=0;
		for (String header: DownloadCSVUtil.CSV_COLUMNS_HEADER){
			cnt++;
			if (DownloadCSVUtil.CSV_COLUMNS_HEADER.length == cnt) {
				if (eonHeader.getCSVName(header) != null)
					csvListStr = csvListStr + eonHeader.getCSVName(header) + "\n";
				else
					csvListStr = csvListStr + header + "\n";
			}else{
				if (eonHeader.getCSVName(header) != null)
					csvListStr = csvListStr + eonHeader.getCSVName(header) + ",";
				else
					csvListStr = csvListStr + header + ",";
			}
		}
		
		int ctrSku, ctrBuyerId;
		String keyQuantity = "", keyComments = "", strQuantity = "", publishedBy = "", finalizedBy = "", lockedBy = "", approvedBy = "";
		StringBuilder sb = new StringBuilder();
		for (String deliverydate : deliveryDates) {
			for (Integer categoryId : categories) {
			List<Integer> listOrderIds = this.listOrderIds(this.allOrders(sellerIds, selectedBuyerIds, DateFormatter.getDateList(deliverydate, deliverydate)));
				if (listOrderIds.size() > 0) {
				List<AkadenSKU> csvAkadenDownloadList = downloadCSVService.loadBillingAndAkadenCSVDownload(listOrderIds, categoryId, sheetTypeId, hasQuantity, user);
				if (csvAkadenDownloadList.size() > 0) {
					//SortingUtil.sortAkadenSKUs(user, csvAkadenDownloadList, Integer.valueOf(categoryId));
					ctrBuyerId = 1;
					for (Integer buyerId : selectedBuyerIds) {
						ctrSku = 1;
						for (AkadenSKU sku : csvAkadenDownloadList) {
							//set priceWithoutTax to zero
							if(sku.getPriceWithoutTax() == null) sku.setPriceWithoutTax(new BigDecimal(0));
							Map<String, Object> quantityMapObj = downloadCSVService.getAkadenQuantiyMap(sku.getSkuId(), listOrderIds, buyerId, deliverydate, sku.getAkadenSkuId(), sheetTypeId, osParam==null?false:osParam.isAllDatesView());
							
							if (sheetTypeId.equals(SheetTypeConstants.SELLER_AKADEN_SHEET) || sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_AKADEN_SHEET)) {
								keyQuantity = "quantity";
								keyComments = "comments";
								
								if(hasQuantity != null) 
									if (hasQuantity.equals("true")) 
										if (quantityMapObj.get(keyQuantity) == null || quantityMapObj.get(keyQuantity) == "")
											continue;
								
							} else {
								if (osParam==null) {
									approvedBy = "A_" + buyerId.toString();
									keyQuantity = "Q_" + buyerId.toString();
								} else {
									if (osParam.isAllDatesView()) {
										approvedBy = "A_" + deliverydate.toString();
										keyQuantity = "Q_" + deliverydate.toString();
									}else{
										approvedBy = "A_" + buyerId.toString();
										keyQuantity = "Q_" + buyerId.toString();
									}
								}
							}
							
							if(hasQuantity != null) 
								if (hasQuantity.equals("true")) 
									if (keyQuantity == null || keyQuantity == "")
										continue;
							
							sb.append(StringUtil.nullToZero(sku.getSkuId()) + CSV_DELIMETER); // SKUID
					    	sb.append(StringUtil.formatCSVText(sku.getSkuName(), "") + CSV_DELIMETER); // DESCRIPTION
					    	sb.append(deliverydate + CSV_DELIMETER); // DELIVERY DATE
							sb.append(StringUtil.nullToBlank(quantityMapObj.get(keyQuantity)) + CSV_DELIMETER); // QUANTITY
							sb.append(StringUtil.formatCSVText(sku.getOrderUnit().getOrderUnitName(), "") + CSV_DELIMETER); // UNIT OF ORDER
							sb.append(StringUtil.nullToZero(sku.getPackageQuantity()) + CSV_DELIMETER); // PACKAGE QUANTITY
							sb.append(StringUtil.nullToZero(sku.getPriceWithoutTax()) + CSV_DELIMETER); // PRICE WITHOUT TAX
							sb.append(StringUtil.nullToBlank(sku.getSkuGroup().getSkuGroupId()) + CSV_DELIMETER); // SKU GROUP ID
					    	sb.append(StringUtil.formatCSVText(sku.getSkuGroup().getDescription(),"") + CSV_DELIMETER); // SKU GROUP NAME
							sb.append(buyerId.toString() + CSV_DELIMETER); // BUYER ID
							sb.append(mapIdNames.get(buyerId) + CSV_DELIMETER); // BUYER NAMES
							sb.append(sku.getUser().getUserId() + CSV_DELIMETER); // SELLER ID
							sb.append(sku.getUser().getUserName() + CSV_DELIMETER);	// SELLER NAME
					    	sb.append(StringUtil.formatCSVText(sku.getMarket(),"") + CSV_DELIMETER); // MARKET NAME
					    	sb.append(StringUtil.formatCSVText(sku.getLocation(),"") + CSV_DELIMETER); // AREA OF PRODUCTION
							sb.append(StringUtil.formatCSVText(sku.getClazz(),"") + CSV_DELIMETER);	// CLASS 1
							sb.append(StringUtil.formatCSVText(sku.getGrade(),"") + CSV_DELIMETER); // CLASS 2
							sb.append(StringUtil.formatCSVText(sku.getPackageType(),"") + CSV_DELIMETER); // PACKAGE TYPE
							sb.append(StringUtil.nullToZero(sku.getPriceWithTax().setScale(0, BigDecimal.ROUND_HALF_UP) + CSV_DELIMETER)); // PRICE WITH TAX
							sb.append(CategoryUtil.getCategoryDesc(categoryId, categoryMap) + CSV_DELIMETER); // CATEGORY ID
							sb.append(String.format("%06d", ctrSku) + CSV_DELIMETER); // SKU COUNTER
							sb.append(String.format("%06d", ctrBuyerId) + CSV_DELIMETER); // BUYER COUNTER
							sb.append("" + CSV_DELIMETER); // FINALIZATION FLAG
							sb.append(sheetTypeId + CSV_DELIMETER);	// SHEET TYPE ID
							sb.append(StringUtil.formatCSVText(sku.getExternalSkuId(), "") + CSV_DELIMETER);	// EXTERNAL SKU ID
							sb.append("" + CSV_DELIMETER);	// OPERATION FLAG
							sb.append("" + CSV_DELIMETER);	//DISPLAY FLAG
							sb.append(StringUtil.nullToZero(this.getPrice1(user, sku.getPrice1()==null?bdZero:sku.getPrice1()) + CSV_DELIMETER)); // PRICE 1
							sb.append(StringUtil.nullToZero(this.getPrice2(user, sku.getPrice2()==null?bdZero:sku.getPrice2()))  + CSV_DELIMETER); // PRICE 2
							sb.append(StringUtil.nullToZero(sku.getUser().getCompany().getCompanyId())  + CSV_DELIMETER); // COMPANY ID
							sb.append(StringUtil.nullToZero(sku.getUser().getCompany().getCompanyName())  + CSV_DELIMETER); // COMPANY NAME
							sb.append(StringUtil.nullToZero("") + CSV_DELIMETER); // SKU Max Limit
							sb.append(StringUtil.formatCSVText(quantityMapObj.get(keyComments), "") + CSV_DELIMETER); // COMMENTS
							sb.append(StringUtil.nullToBlank("") + CSV_DELIMETER); // FINALIZE
							sb.append(StringUtil.nullToBlank("") + CSV_DELIMETER); // PUBLISH
							sb.append(StringUtil.nullToBlank("") + CSV_DELIMETER); // LOCK
							sb.append(StringUtil.nullToBlank("")); // APPROVE
							sb.append("\n");
							csvListStr = csvListStr + sb.toString();
					    	sb.setLength(0);
					    	ctrSku = ctrSku + 1;
					    }						
						ctrBuyerId = ctrBuyerId + 1;
					}
				}else{
					if(categories.size() == 1 && deliveryDates.size() == 1)
						return csvListStr;
				}
				}else{
					if(categories.size() == 1 && deliveryDates.size() == 1)
						return csvListStr;
				}
			}
		}
		return csvListStr;
	}
	
	/****************************************************************
	 * OLD DOWNLOAD CSV by jovs
	 ****************************************************************/
}
