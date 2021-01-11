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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.OrderConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.Category;
import com.freshremix.model.Company;
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
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.ui.model.OrderItemUI;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.FilterIDUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.UploadCSVUtil;

/**
 * @author Pammie
 *
 */
public class UploadCSVController implements Controller {

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

		
		
		this.allOrdersMap = (List<Order>) SessionHelper.getAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM);
		this.skuObjMap = (Map<Integer, SKU>)SessionHelper.getAttribute(request, SessionParamConstants.SKU_OBJ_MAP_PARAM);
		this.user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		this.osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		this.dp = (DealingPattern) SessionHelper.getAttribute(request, SessionParamConstants.DEALING_PATTERN_PARAM);
		this.categoryTabs = (List)SessionHelper.getAttribute(request, "categoryTabs");
		
		List<CommentsTreeGridItem> rowError = new ArrayList<CommentsTreeGridItem>();
		rowError =(List<CommentsTreeGridItem>) SessionHelper.getAttribute(request, "uploadError");
		String validityDate = (String)SessionHelper.getAttribute(request, "validityDate"); 
		skuCSVList = (List)SessionHelper.getAttribute(request, "skuCSVList"); 
		Integer errorFlag =(Integer)SessionHelper.getAttribute(request, "errorFlag");
		 
		if (rowError.size() == 0){
			separateAddEditDeleteSKUs(validityDate);
			errorFlag = 0;
		}

		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("errorFlag", errorFlag);
		model.put("uploadError", rowError);
		
		System.out.println("upload finished");
		return new ModelAndView("json",model);
	}
	
	private void separateAddEditDeleteSKUs(String validityDate) throws ServiceException{
		
		
		System.out.println("separateAddEditDeleteSKUs");
		for (int i = 1; i < this.skuCSVList.size(); i++) {
			UploadCSVUtil skuCSV = (UploadCSVUtil) this.skuCSVList.get(i);

			Integer skuId = new Integer(0);
			Integer sellerId = Integer.parseInt(skuCSV.getSellerId());
			String sellerName = skuCSV.getSellerLongName();
			String marketCondition = skuCSV.getMarketCond();
			String areaOfProduction = skuCSV.getAreaProd();
			String class1 = skuCSV.getClazz();
			String eonPackageQuantity = skuCSV.getpQuantity();
			String skuGroup = skuCSV.getSkuGroupId();
			String packingType = skuCSV.getPackingType();
			String priceWithoutTax = skuCSV.getPrice();
			Integer orderUnitID = orderUnitService.getOrderUnitIdByName(skuCSV.getUom());
			String eonUOM = orderUnitID.toString();
			String extSkuId = skuCSV.getExtSkuId();
			String price1 = skuCSV.getPrice1();
            String price2 = skuCSV.getPrice2();
            String skuName = skuCSV.getSkuName();
            String sheetType = skuCSV.getSheetType();
            if (!skuCSV.getOpFlag().equalsIgnoreCase(UploadCSVUtil.ADD)) {
            	skuId = Integer.parseInt(skuCSV.getSkuId());
            }
            String grade = skuCSV.getGrade();
            
            int categoryId = 0;
			Map categoryMap = new HashMap();
            for (int j = 0; j < this.categoryTabs.size(); j++) {
            	Category categoryDesc = (Category) categoryTabs.get(j); //((CommonDescription) categoryTabs.get(j)).getDescription();
            	String categoryName = categoryDesc.getDescription();
            	categoryMap.put(categoryName, new Integer(categoryDesc.getCategoryId()));
            }
            
            if (categoryMap.containsKey(skuCSV.getCategory())) {
            	categoryId = ((Integer) categoryMap.get(skuCSV.getCategory())).intValue();
            }

            prepareSku(validityDate, categoryId, sellerId, eonPackageQuantity, eonUOM,
            		marketCondition, areaOfProduction, class1, packingType, priceWithoutTax, 
            		skuGroup, extSkuId, price1, price2, sellerName, skuName, sheetType, skuId, grade, skuCSV.getOpFlag());
				/*} else if (skuCSV.getOpFlag()
					.equals(UploadCSVUtil.VISIBILITYFLAG)) {
				Integer buyerEntityId = new Integer(skuCSV.getBuyerEntityId());
				String visibility = skuCSV.getVisibility();

				this.preparedVisibilitySku(skuKey, validityDate, buyerEntityId,visibility);
			} else {
				this.prepareDeleteSku(skuKey, validityDate);
			}*/
		}
	}

	private void prepareSku(String validityDate, int categoryId, Integer sellerId,
			String eonPackageQuantity, String eonUOM, String marketCondition, 
			String areaOfProduction, String class1, String packingType, String priceWithoutTax, 
			String skuGroup, String extSkuId, String price1, String price2, String sellerName, 
			String skuName, String sheetType, Integer skuId, String grade, String opFlag) throws ServiceException {
		
		OrderForm orderForm = new OrderForm();
		orderForm.setAction("save");
		List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
		Map<String, Object> orderFormDetails = new HashMap<String, Object>();
		orderFormDetails.put("skuId", skuId);
		orderFormDetails.put("sellername", sellerName);
		Integer companyId = userInfoService.getCompanyIdByUserId(sellerId.toString());
		Company companyDetail = companyInfoService.getCompanyById(companyId);
		orderFormDetails.put("companyname", companyDetail.getCompanyName());
		orderFormDetails.put("groupname", skuGroup);
		orderFormDetails.put("marketname", marketCondition);
		orderFormDetails.put("skuname", skuName);
		orderFormDetails.put("home", areaOfProduction);
		orderFormDetails.put("grade", grade);
		orderFormDetails.put("clazzname", class1);
		orderFormDetails.put("packagetype", packingType);
		orderFormDetails.put("pricewotax", priceWithoutTax);
		orderFormDetails.put("price1", price1);
		orderFormDetails.put("price2", price2);
		orderFormDetails.put("price2", price2);
		orderFormDetails.put("packageqty", eonPackageQuantity);
		orderFormDetails.put("unitorder", eonUOM);
		orderFormDetails.put("sellerId", sellerId);
		
		records.add(orderFormDetails);

		if (opFlag.equalsIgnoreCase(UploadCSVUtil.ADD)){
			orderForm.setInsertedRecords(records);
		} else  if (opFlag.equalsIgnoreCase(UploadCSVUtil.EDIT)){
			orderForm.setUpdatedRecords(records);
		}  if (opFlag.equalsIgnoreCase(UploadCSVUtil.DELETE)){
			orderForm.setDeletedRecords(records);
		}
		
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setSellerId(sellerId);
		orderDetails.setSheetType(Integer.parseInt(sheetType));
		orderDetails.setSkuCategoryId(new Integer(categoryId));
		orderDetails.setDeliveryDate(validityDate);
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		
		if (!osParam.isAllDatesView())
			orderForm.setBuyerColumnIds(buyerIds);
		else {
			List<String> deliveryDates = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
			orderForm.setDateColumnIds(deliveryDates);
		}
		
		orderDetails.setBuyerIds(buyerIds);
		orderDetails.setSellerIds(sellerIds);
		orderDetails.setUser(this.user);
		orderDetails.setStartDate(osParam.getStartDate());
		orderDetails.setEndDate(osParam.getEndDate());
		orderDetails.setAllDatesView(osParam.isAllDatesView());
		orderDetails.setDatesViewBuyerID(Integer.valueOf(osParam.getDatesViewBuyerID()));
		orderDetails.setDealingPattern(this.dp);
		
		OrderItemUI oi = orderForm.toOrderItem(orderFormDetails);
		
		SKU newSKU = OrderSheetUtil.toSKU(oi, orderDetails, user);
		if (opFlag.equals("1")){
			newSKU.setSkuId(null);
			newSKU.setOrigSkuId(null);
		}
		this.skuObjMap.put(skuId, newSKU);
	
		orderSheetService.saveOrder(orderForm, orderDetails, this.allOrdersMap, this.skuObjMap);
		System.out.println("save finished");
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
	
	/*
	 *Use this method if you want to display the formed error message. 
	 *as of v16.02 there is no need to do this because the function is not enabled 
	 */
	@SuppressWarnings("unchecked")
	private StringBuilder handleServiceException(HttpServletRequest request,
			User user, ServiceException e) {
		String errorCode = e.getErr().getErrorCode();
		StringBuilder sbMessage = null;
		if (errorCode.equals(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_FINALIZED_FAILED)) {
			Map<String, Object> exceptionContext = e.getExceptionContext();
			Map<Integer, String> sellerNameMap = getSellerNameMap(user);
			Map<Integer, String> buyerNameMap = getBuyerNameMap(request);
			
			List<Order> failedOrders = (List<Order>)exceptionContext.get(OrderConstants.FAILED_ORDERS);
			sbMessage = OrderSheetUtil.createOrderListMessage(errorCode, failedOrders,
					sellerNameMap, buyerNameMap);
			
		}
		return sbMessage;
	}
	
	@SuppressWarnings("unchecked")
	private Map<Integer, String> getBuyerNameMap(HttpServletRequest request) {
		Map<Integer,String> buyerNameMap = FilterIDUtil.toIDNameMap((List<FilteredIDs>) 
				SessionHelper.getAttribute(request, SessionParamConstants.SORTED_BUYERS));
		return buyerNameMap;
	}

	private Map<Integer, String> getSellerNameMap(User user) {
		Map<Integer,String> sellerNameMap = new HashMap<Integer,String>();
		if(user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)){
			sellerNameMap = FilterIDUtil.toIDNameMap(user.getPreference().getSortedSellers());
		} else {
			sellerNameMap.put(user.getUserId(), user.getName());
		}
		return sellerNameMap;
	}

}
