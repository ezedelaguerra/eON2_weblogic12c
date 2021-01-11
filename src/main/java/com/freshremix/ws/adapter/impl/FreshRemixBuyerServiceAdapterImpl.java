package com.freshremix.ws.adapter.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import ws.freshremix.validator.WebServiceBuyerValidator;

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.constants.WebServiceConstants;
import com.freshremix.exception.EONError;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.AdminMember;
import com.freshremix.model.Category;
import com.freshremix.model.EONUserPreference;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.model.WSBuyerAddOrderSheetRequest;
import com.freshremix.model.WSBuyerAddOrderSheetResponse;
import com.freshremix.model.WSBuyerLoadOrderRequest;
import com.freshremix.model.WSBuyerLoadOrderResponse;
import com.freshremix.model.WSBuyerResponse;
import com.freshremix.model.WSBuyerSKU;
import com.freshremix.model.WSBuyerSKUAdd;
import com.freshremix.model.WSBuyerSKUDetails;
import com.freshremix.model.WSBuyerSKUUpdate;
import com.freshremix.service.BuyerOrderSheetService;
import com.freshremix.service.CategoryService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.MessageI18NService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.ui.model.PageInfo;
import com.freshremix.ui.model.TableParameter;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.ws.adapter.FreshRemixBuyerServiceAdapter;

public class FreshRemixBuyerServiceAdapterImpl implements FreshRemixBuyerServiceAdapter {
	

	private static final Logger LOGGER = Logger.getLogger(FreshRemixBuyerServiceAdapterImpl.class);

	private BuyerOrderSheetService buyerOrderSheetService;
	private OrderSheetService orderSheetService;
	private UserPreferenceService userPreferenceService;
	private MessageI18NService messageI18NService;
	private WebServiceBuyerValidator webServiceBuyerValidator;
	private UsersInformationService userInfoService;
	private DealingPatternService dealingPatternService;
	
	
	
	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setWebServiceBuyerValidator(
			WebServiceBuyerValidator webServiceBuyerValidator) {
		this.webServiceBuyerValidator = webServiceBuyerValidator;
	}

	public void setMessageI18NService(MessageI18NService messageI18NService) {
		this.messageI18NService = messageI18NService;
	}

	
	public void setUserPreferenceService(UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}

	public void setBuyerOrderSheetService(
			BuyerOrderSheetService buyerOrderSheetService) {
		this.buyerOrderSheetService = buyerOrderSheetService;
	}

	@SuppressWarnings("unchecked")
	public WSBuyerLoadOrderResponse loadOrders(WSBuyerLoadOrderRequest request) {
		WSBuyerLoadOrderResponse response = new WSBuyerLoadOrderResponse();

		LOGGER.info("processing webservice getBuyerOrderSheet request");
		
		LOGGER.info("validating request parameters");
		WSBuyerResponse response2 = webServiceBuyerValidator.validateRequest(request);
		if (response2!= null) {
			 response.setResult(response2);
			 return response;
		}
		
		Map<String, Object> userValResult =  webServiceBuyerValidator.validateBuyerUser(request.getUsername(),
				request.getPassword());
		
		response2 = (WSBuyerResponse)userValResult.get("response");
		if (response2!= null) {
			 response.setResult(response2);
			 return response;		}
		
		User user = (User) userValResult.get("user");
		
		Date startDate = request.getOrderDate();
		Date endDate = request.getOrderDate();

//		/* default the end date before validating*/
//		if (endDate == null){
//			endDate = new Date(startDate.getTime());
//		}
			
		response2 = webServiceBuyerValidator.validateDates(startDate, endDate);
		if (response2 != null) {
			 response.setResult(response2);
			 return response;		}
		
		List<Integer> sellerIds = request.getSellerIds();
		response2 = webServiceBuyerValidator.validateSellerIdList(sellerIds);
		if (response2 != null) {
			response.setResult(response2);
			return response;
		}
		
		List<Integer> buyerIds = request.getBuyerIds();
		if(buyerIds==null || buyerIds.size()==0){
			buyerIds = new ArrayList<Integer>();
			buyerIds.add(user.getUserId());
		}

		response2 = webServiceBuyerValidator.validateBuyerIdList(buyerIds, user);
		if(response2 != null){
			 response.setResult(response2);
			 return response;		}
		
		List<String> dateList = DateFormatter.getDateList(
				DateFormatter.convertToString(startDate),
				DateFormatter.convertToString(endDate));

		List<Order> allOrders = buyerOrderSheetService.getPublishedOrders(
				buyerIds, dateList, sellerIds, null, null);
		response2 = webServiceBuyerValidator.validateBuyerPublishedOrders(allOrders);
		if (response2 != null) {
			 response.setResult(response2);
			 return response;		}

		LOGGER.info("completed validating request parameters");
		
		LOGGER.info("setting up input parameters for service call");
		request.setBuyerIds(buyerIds);
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);

		Map<String, Object> map = webServiceBuyerValidator.validateCategoryName(request.getCategory(), user.getUserId());
		Category category = null;
		
		response2 = (WSBuyerResponse)map.get("response");	
		if (response2!= null) {
			response.setResult(response2);
			return response;
		}else{
			category= (Category)map.get("category");
		}
		
		
		OrderSheetParam orderSheetParam = setupOrderSheetParam(request, user,category);
		
		TableParameter tableParam = setupTableParam(request);

		List<Integer> selectedOrderIds = orderSheetService.getSelectedOrderIds(allOrdersMap, orderSheetParam );
		Map<String, Object> resultMap =null;
		
		EONUserPreference userPreference = userPreferenceService.getUserPreference(user);
		user.setPreference(userPreference);
		String dateFrom = DateFormatter.convertToString(startDate);
		String dateTo = DateFormatter.convertToString(endDate);
		LOGGER.info("completed setting up input parameters for service call");
		List<AdminMember> adminMemberList = dealingPatternService.getMembersByAdminIdWithStartEndDates(user.getUserId(), DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER, dateFrom, dateTo);
		
		
		Map<String, List<Integer>> mapOfMembersByDate = OrderSheetUtil.convertToMapOfMembersByDate(adminMemberList, dateFrom,  dateTo);
		try {
			resultMap = buyerOrderSheetService.getOrders(orderSheetParam, user, tableParam,
					selectedOrderIds, allOrders,mapOfMembersByDate);
		}catch (JSONException r){
			LOGGER.error("JSONException encountered", r);
			response2 = new WSBuyerResponse(); 
			response2.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
			response2.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.TECHNICAL_ERR_MSG));
			 response.setResult(response2);
			 return response;
		}
		catch (ServiceException e) {
			LOGGER.error("Service Exception encountered", e);
			if (e.getErr().getErrorCode()
					.equals("ordersheet.concurrent.get.unpublishedFailed")) {
				response2 = new WSBuyerResponse(); 
				response2.setResultCode(WebServiceConstants.ZERO_RESULTS_CODE);
				response2.setResultMsg(
						messageI18NService.getPropertyMessage(
								WebServiceConstants.ZERO_RESULTS_MSG));
				 response.setResult(response2);
				 return response;
			}
			response2 = new WSBuyerResponse(); 
			response2.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
			response2.setResultMsg(e.getMessage());
			 response.setResult(response2);
			 return response;
		}
		catch (Exception e) {
			LOGGER.error("Generic Exception encountered", e);
			response2 = new WSBuyerResponse(); 
			response2.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
			response2.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.TECHNICAL_ERR_MSG));
			 response.setResult(response2);
			 return response;
		}
		
		response2 = webServiceBuyerValidator.validateOrderMap(resultMap);
		if(response2!=null)
		{
			 response.setResult(response2);
			 return response;
		}
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) resultMap.get("data");
		response2 = webServiceBuyerValidator.validateOrderMap(dataList);
		if(response2!=null)
		{
			 response.setResult(response2);
			 return response;
		}
		
		try {
			LOGGER.info("converting service call result to webservice output parameter");

			List<User> buyers = userInfoService.getUserById(buyerIds);
			ArrayList<WSBuyerSKU> convertListToWSSKUList = convertListToWSSKUList(dataList, buyers,user, category);
			response.setSkuOrderList(convertListToWSSKUList);
			LOGGER.info("completed converting service call result to webservice output parameter");
		} catch (JSONException r){
			LOGGER.error("JSONException encountered", r);
			response2 = new WSBuyerResponse(); 
			response2.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
			response2.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.TECHNICAL_ERR_MSG));
			 response.setResult(response2);
			 return response;
		}
		catch (Exception e) {
			LOGGER.error("Generic Exception encountered", e);
			response2 = new WSBuyerResponse(); 
			response2.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
			response2.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.TECHNICAL_ERR_MSG));
			 response.setResult(response2);
			 return response;
		}
		response2 = new WSBuyerResponse();
		response2.setResultCode(WebServiceConstants.WS_ERROR_CODE_0);
		response2.setResultMsg(
				messageI18NService.getPropertyMessage(
						WebServiceConstants.WS_ERROR_MSG_0));

		//response.setTotals((ProfitInfo) resultMap.get("totals"));
		//response.setGrandTotals((ProfitInfo) resultMap.get("grandTotals"));
		LOGGER.info("completed processing webservice getBuyerOrderSheet request");
		 response.setResult(response2);
		 return response;
	}

	

	
	private OrderSheetParam setupOrderSheetParam(WSBuyerLoadOrderRequest request, User user, Category category) {
		Date startDate = request.getOrderDate();
		Date endDate = request.getOrderDate();
		OrderSheetParam orderSheetParam = new OrderSheetParam();
		orderSheetParam.setSelectedSellerID(StringUtils.join(request.getSellerIds(),";"));

		/* view all dates is always false since WS only accepts 1 date input */
		orderSheetParam.setAllDatesView(false);
		
		orderSheetParam.setSelectedDate(
				DateFormatter.convertToString(startDate));
		orderSheetParam.setStartDate(DateFormatter.convertToString(startDate));
		orderSheetParam.setEndDate(DateFormatter.convertToString(endDate));
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)){
			orderSheetParam.setSheetTypeId(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET);
			orderSheetParam.setCsvSheetTypeID(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET);
		}else{
			orderSheetParam.setSheetTypeId(SheetTypeConstants.BUYER_ORDER_SHEET);
			//Sheet Type used for CSV Download
			orderSheetParam.setCsvSheetTypeID(SheetTypeConstants.BUYER_ORDER_SHEET);
		}
		

		orderSheetParam.setSelectedBuyerID(StringUtils.join(request.getBuyerIds(),";"));
		orderSheetParam.setSelectedBuyerCompany(user.getCompany().getCompanyId().toString());
		orderSheetParam.setDatesViewBuyerID(user.getUserId().toString());
		

		orderSheetParam.setCategoryId(category.getCategoryId());
		return orderSheetParam;
	}

	private TableParameter setupTableParam(WSBuyerLoadOrderRequest request) {
		TableParameter tableParam = new TableParameter();
		PageInfo pageInfo = getPageInfo(request.getPageSize(), request.getPageNum());
		tableParam.setPageInfo(pageInfo);
		tableParam.setAction("load");
		return tableParam;
	}
	
	private PageInfo getPageInfo(Integer pageSize, Integer pageNum)
	{
		PageInfo info = new PageInfo();
		
		if (NumberUtil.isNullOrZero(pageSize)){
			pageSize = 120;
		}
		if (NumberUtil.isNullOrZero(pageNum)){
			pageNum =1;
		}
		
		info.setPageNum(pageNum);
		info.setPageSize(pageSize);
		int startRow = (pageSize * (pageNum-1)) +1;
		int endRow = pageSize * pageNum;
		
		info.setEndRowNum(endRow);
		info.setStartRowNum(startRow);
		return info;
	}
	
	public ArrayList<WSBuyerSKU> convertListToWSSKUList(List<Map<String, Object>> dataList, List<User> buyers, User user, Category category) throws JSONException{
		ArrayList<WSBuyerSKU> result = new ArrayList<WSBuyerSKU>();
		
		for(Map<String, Object> map:dataList){
			WSBuyerSKU sku = new WSBuyerSKU();
			sku.setSkuId(String.valueOf(map.get("skuId")));
			sku.setSkuName((String)map.get("skuname"));
			//sku.setSheetType("Order");
			sku.setSkuCategoryId(category.getCategoryId().toString());
			sku.setSkuCategoryName(category.getDescription());
			sku.setSkuGroupId(String.valueOf(map.get("groupname")));
			sku.setSkuGroupName(String.valueOf(map.get("skuGroupName")));
			sku.setSellerName(String.valueOf(map.get("skuSellerName")));
			sku.setSellerId(String.valueOf(map.get("sellerId")));
			//sku.setBuyerId(String.valueOf(user.getUserId()));
			//sku.setBuyerName((String)user.getName());
			sku.setCompany((String)map.get("companyname"));
			sku.setCompanyId(String.valueOf(map.get("companyid")));
			sku.setLocation((String)map.get("home"));
			sku.setMarket((String)map.get("marketname"));
			sku.setGrade((String)map.get("grade"));
			sku.setClazz((String)map.get("clazzname"));
			try {
				sku.setSkuMaxLimit(new BigDecimal((String)map.get("skumaxlimit")));
			} catch (Exception e) {
			}
			//sku.setPrice1((BigDecimal)map.get("price1"));
			sku.setPrice2((BigDecimal)map.get("price2"));
			sku.setPriceWithoutTax((BigDecimal)map.get("pricewotax"));
			sku.setPriceWithTax((BigDecimal)map.get("pricewtax"));
			try {
				sku.setPackageQuantity(new BigDecimal((String)map.get("packageqty")));
			} catch (Exception e) {

			}
			sku.setPackageType((String)map.get("packagetype"));
			sku.setUnitOfOrder(String.valueOf(map.get("unitorder")));
			sku.setUnitOrderName((String)map.get("unitorderName"));
			sku.setQty((BigDecimal)map.get("rowqty"));
			
			
			try {
				sku.setB_purchasePrice(new BigDecimal((String)map.get("B_purchasePrice")));
			} catch (Exception e) {

			}
			sku.setB_profitPercentage((BigDecimal)map.get("B_profitPercentage"));
			//sku.setVisall((String)map.get("visall"));
			sku.setSkuBaId(String.valueOf(map.get("skuBaId")));
			sku.setB_sellingUom(String.valueOf(map.get("B_sellingUom")));
			sku.setB_sellingUomName(String.valueOf(map.get("B_sellingUomName")));
			sku.setB_skuComment((String)map.get("B_skuComment"));
			
			try {
				sku.setB_sellingPrice(new BigDecimal((String)map.get("B_sellingPrice")));
			} catch (Exception e) {

			}
			

			
			//JSONObject lockflags =  new JSONObject((String)map.get("lockflag"));
			ArrayList<WSBuyerSKUDetails> buyerSkuDetailList = new ArrayList<WSBuyerSKUDetails>();
			for(User buyer : buyers){
				WSBuyerSKUDetails details = new WSBuyerSKUDetails();
					String qty = (String)map.get("Q_"+buyer.getUserId().toString());
					if (NumberUtils.isNumber(qty)){
						details.setQty(new BigDecimal(qty));
					} 

				details.setVisibility((String)map.get("V_"+buyer.getUserId().toString()));			
				details.setOrderFinalizedFlag((String)map.get("OF_"+buyer.getUserId().toString()));
				details.setOrderLockedFlag((String)map.get("OL_"+buyer.getUserId().toString()));
				details.setAllocationPublishedFlag((String)map.get("AP_"+buyer.getUserId().toString()));
				details.setAllocationFinalizedFlag((String)map.get("AF_"+buyer.getUserId().toString()));
				details.setReceiveApprovedFlag((String)map.get("RA_"+buyer.getUserId().toString()));

				if(details.getQty()!=null || details.getVisibility()!=null || details.getOrderFinalizedFlag()!=null){
					details.setBuyerId(buyer.getUserId());
					details.setBuyerName(buyer.getName());
					buyerSkuDetailList.add(details);
				}
			}
			sku.setBuyerSkuDetails(buyerSkuDetailList);
			sku.setCenter((String)(map.get("column01")));
			sku.setDelivery((String)(map.get("column02")));
			sku.setSale((String)(map.get("column03")));
			sku.setJan((String)(map.get("column04")));
			sku.setPackFee((String)(map.get("column05")));
			sku.setColumn01((String)(map.get("column06")));
			sku.setColumn02((String)(map.get("column07")));
			sku.setColumn03((String)(map.get("column08")));
			sku.setColumn04((String)(map.get("column09")));
			sku.setColumn05((String)(map.get("column10")));
			sku.setColumn06((String)(map.get("column11")));
			sku.setColumn07((String)(map.get("column12")));
			sku.setColumn08((String)(map.get("column13")));
			sku.setColumn09((String)(map.get("column14")));
			sku.setColumn10((String)(map.get("column15")));
			sku.setColumn11((String)(map.get("column16")));
			sku.setColumn12((String)(map.get("column17")));
			sku.setColumn13((String)(map.get("column18")));
			sku.setColumn14((String)(map.get("column19")));
			sku.setColumn15((String)(map.get("column20")));
			
			
			result.add(sku);
		}
		return result;
	}
	
	public WSBuyerAddOrderSheetResponse addOrders(WSBuyerAddOrderSheetRequest request) {
		LOGGER.info("processing webservice AddOrderSheet request");
		WSBuyerAddOrderSheetResponse response = new WSBuyerAddOrderSheetResponse();
		LOGGER.info("validating request parameters");
		WSBuyerResponse result = webServiceBuyerValidator.validateInsertRequest(request);
		if (result!= null) {
			response.setResult(result);
			return response;
		}
		Map<String, Object> userValResult =  webServiceBuyerValidator.validateBuyerUser(request.getUsername(),
				request.getPassword());
		
		result = (WSBuyerResponse) userValResult.get("response");
		if (result!= null) {
			response.setResult(result);
			return response;
		}
		
		User user = (User) userValResult.get("user");
		request.setLoginUser(user);
		result = webServiceBuyerValidator.validateOrderDate(request.getOrderDate());
		if (result!= null) {
			response.setResult(result);
			return response;
		}
	 // category
		Map<String, Object> map = webServiceBuyerValidator.validateCategoryName(request.getSkuCategoryName(), user.getUserId());
		
		result = (WSBuyerResponse)map.get("response");	
		if (result!= null) {
			response.setResult(result);
			return response;
		}else{
			request.setCategory((Category)map.get("category"));
		}
		result = webServiceBuyerValidator.validateAddOrderSheetLists(user, request.getUpdateSKUList(), request.getAddSkuList());
		if (result!= null) {
			response.setResult(result);
			return response;
		}
		
		ArrayList<WSBuyerSKUAdd> addSkuList = request.getAddSkuList();
		ArrayList<WSBuyerSKUUpdate> updateSKUList = request.getUpdateSKUList();

		if(RolesUtil.isUserRoleBuyerAdmin(user)){
			if(CollectionUtils.isNotEmpty(addSkuList)){
				for(WSBuyerSKUAdd sku : addSkuList){
					result = webServiceBuyerValidator.validateWSSKUFieldsforInsert(user, request.getOrderDate(),sku, request.getCategory());
					if (result!= null) {
						response.setResult(result);
						return response;
					}
				}
			}
		}
		if(CollectionUtils.isNotEmpty(updateSKUList)){
			for(WSBuyerSKUUpdate sku : updateSKUList){
				result = webServiceBuyerValidator.validateWSSKUFieldsforUpdate(user, request.getOrderDate(), sku,request.getCategory().getCategoryId());
				if (result!= null) {
					response.setResult(result);
					return response;
				}
			}
		}
		
		
		List<EONError> concurrencyErrors = webServiceBuyerValidator.processConcurrencyCheckForWS(request);
		if(CollectionUtils.isNotEmpty(concurrencyErrors)){
			result = new WSBuyerResponse();
			result.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
			StringBuilder errMsg = new StringBuilder();
			for(EONError err : concurrencyErrors){
				errMsg.append(messageI18NService.getErrorMessage(err) + "\n");
			}
			result.setResultMsg(errMsg.toString());
			response.setResult(result);
			return response;
		}
		try {
			buyerOrderSheetService.saveWSData(request);
		} catch (Exception e) {
			LOGGER.error("Generic Exception encountered", e);
			result = new WSBuyerResponse();

			result.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
			result.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.TECHNICAL_ERR_MSG));
			 response.setResult(result);
			 return response;
		}
		result = new  WSBuyerResponse();
		result.setResultCode(WebServiceConstants.WS_ERROR_CODE_0);
		result.setResultMsg(
				messageI18NService.getPropertyMessage(
						WebServiceConstants.WS_ERROR_MSG_0));

		 response.setResult(result);
		LOGGER.info("completed processing webservice addBuyerOrderSheet request");
		return response;
	}
	
	

	
}
