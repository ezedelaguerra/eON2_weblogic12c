package com.freshremix.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import ws.freshremix.validator.WebServiceSellerValidator;

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.constants.WebServiceConstants;
import com.freshremix.dao.OrderItemDao;
import com.freshremix.exception.EONError;
import com.freshremix.model.AdminMember;
import com.freshremix.model.Category;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.EONUserPreference;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SKU;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.model.WSSellerBuyerDetails;
import com.freshremix.model.WSSellerCreateSheetRequest;
import com.freshremix.model.WSSellerCreateSheetResponse;
import com.freshremix.model.WSSellerSKUCreateRequest;
import com.freshremix.model.WSSellerSKURequest;
import com.freshremix.model.WSSellerDeleteOrderSheetRequest;
import com.freshremix.model.WSSellerDeleteOrderSheetResponse;
import com.freshremix.model.WSSellerDeleteSKU;
import com.freshremix.model.WSSellerGetOrderSheetRequest;
import com.freshremix.model.WSSellerGetOrderSheetResponse;
import com.freshremix.model.WSSellerResponse;
import com.freshremix.model.WSSellerReturnDetails;
import com.freshremix.model.WSSellerReturnSKU;
import com.freshremix.model.WSSellerSKU;
import com.freshremix.model.WSSellerSKUUpdateRequest;
import com.freshremix.model.WSSellerUpdateOrderSheetRequest;
import com.freshremix.model.WSSellerUpdateOrderSheetResponse;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.MessageI18NService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.service.WSSellerService;
import com.freshremix.ui.model.PageInfo;
import com.freshremix.ui.model.TableParameter;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;

public class WSSellerServiceImpl implements WSSellerService {

	public static final Logger LOGGER = Logger
			.getLogger(WSSellerServiceImpl.class);

	private WebServiceSellerValidator webServiceSellerValidator;
	private DealingPatternService dealingPatternService;
	private MessageI18NService messageI18NService;
	private OrderSheetService orderSheetService;
	private UserPreferenceService userPreferenceService;
	private UsersInformationService userInfoService;
	private OrderItemDao orderItemDao;
	
	
	public void setOrderItemDao(OrderItemDao orderItemDao) {
		this.orderItemDao = orderItemDao;
	}

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setUserPreferenceService(UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}

	public void setMessageI18NService(MessageI18NService messageI18NService) {
		this.messageI18NService = messageI18NService;
	}

	public void setDealingPatternService(
			DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}

	public void setWebServiceSellerValidator(
			WebServiceSellerValidator webServiceSellerValidator) {
		this.webServiceSellerValidator = webServiceSellerValidator;
	}

	@Override
	public WSSellerCreateSheetResponse createSheet(
			WSSellerCreateSheetRequest request) {
		WSSellerCreateSheetResponse response = new WSSellerCreateSheetResponse();
		WSSellerResponse result = webServiceSellerValidator
				.validateRequest(request);
		if (result != null) {
			response.setResult(result);
			return response;
		}
		
		result = webServiceSellerValidator
				.validateSystemName(request.getSystemName());
		if (result != null) {
			response.setResult(result);
			return response;
		}
		LOGGER.info("createSheet2 Request from "+ request.getSystemName());
		LOGGER.info("validating request parameters");

		Map<String, Object> mapResult = webServiceSellerValidator
				.validateSellerUser(request.getUsername(),
						request.getPassword());

		result = (WSSellerResponse) mapResult.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		}

		User user = (User) mapResult.get("user");
		request.setUser(user);

		// orderDate
		mapResult = webServiceSellerValidator.validateOrderDate(request
				.getOrderDate());

		result = (WSSellerResponse) mapResult.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		}

		Date orderDate = (Date) mapResult.get("orderDate");
		request.setDeliveryDate(orderDate);


		List<Integer> sellerAdminUsers = new ArrayList<Integer>();
		if (RolesUtil.isUserRoleSellerAdmin(user)) {
			List<User> members = dealingPatternService.getMembersByAdminId(
					user.getUserId(),
					DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER,
					request.getOrderDate(), request.getOrderDate());
			sellerAdminUsers = OrderSheetUtil.getUserIdList(members);
		} else {
			sellerAdminUsers.add(user.getUserId());
		}

		List<WSSellerSKU> skuList = new ArrayList<WSSellerSKU>();
		if (CollectionUtils.isNotEmpty(request.getSku())) {
			Map<Integer, List<UsersCategory>> userCats = new HashMap<Integer, List<UsersCategory>>();
			for (WSSellerSKUCreateRequest sku : request.getSku()) {
				result = webServiceSellerValidator.validateSKUForInsert(user,
						request.getOrderDate(),  sku, sellerAdminUsers, userCats);
				if (result != null) {
					response.setResult(result);
					return response;
				}
				skuList.add(sku.getSellerSKU());

			}
		}

		Map<String, Set<?>> extractionResult = extractSellerBuyerCombination(
				request.getDeliveryDate(), skuList);
		Set<Integer> sellerSet = (Set<Integer>) extractionResult.get("SELLER");
		Set<Integer> buyerSet = (Set<Integer>) extractionResult.get("BUYER");
		Set<String> orderMapKeySet = (Set<String>) extractionResult.get("ORDERMAPKEY");
		// Set<String> orderMapKey = (Set<String>)
		// extractionResult.get("ORDERMAPKEY");
		List<Integer> sellerIds = new ArrayList<Integer>(sellerSet);
		List<Integer> buyerIds = new ArrayList<Integer>(buyerSet);
		List<String> dateList = Arrays.asList(DateFormatter
				.convertToString(orderDate));
		List<String> orderkeys = new ArrayList<String>(orderMapKeySet);
		
		
		Map<String, Object> map  = webServiceSellerValidator
				.processConcurencyCheckForAdd(skuList, request.getOrderDate(),
						sellerIds, buyerIds, dateList);
		result = (WSSellerResponse) map.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		} 
		List<SKU> dSKUList = new ArrayList<SKU>();

		List<Order> orderList = (List<Order>)map.get("orderList");
		if(CollectionUtils.isNotEmpty(orderList)){
			List<Integer> dOrderIdList = OrderSheetUtil.getOrderIdList(orderList);
			dSKUList = orderSheetService.wsGetDistinctSKUs(dOrderIdList);
			try {
				//delete existing order items
				orderItemDao.deleteAllOrderItemsByOrderIds(dOrderIdList);
			} catch (Exception e1) {
				result = new WSSellerResponse();

				result.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
				result.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.TECHNICAL_ERR_MSG));
				response.setResult(result);
				return response;
			}
			
		}
		/* extract the seller, buyer and ordermapkey */

		try {
			orderSheetService.saveWSData(user, skuList, sellerIds, buyerIds,
					dateList, orderList,dSKUList);
		} catch (Exception e) {
			LOGGER.error("Generic Exception encountered", e);
			result = new WSSellerResponse();

			result.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
			result.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.TECHNICAL_ERR_MSG));
			response.setResult(result);
			return response;
		}
		
		result = new WSSellerResponse();
		result.setResultCode(WebServiceConstants.WS_ERROR_CODE_0);
		result.setResultMsg(messageI18NService
				.getPropertyMessage(WebServiceConstants.WS_ERROR_MSG_0));

		response.setResult(result);
		LOGGER.info("completed processing webservice createsheet request");
		return response;

	}

	private Map<String, Set<?>> extractSellerBuyerCombination(
			Date deliveryDate, List<?> skuList) {

		/* Assumes that updateSKUList is not null */
		Map<String, Set<?>> sellerBuyerMap = new HashMap<String, Set<?>>();

		Set<Integer> sellerSet = new HashSet<Integer>();
		Set<Integer> buyerSet = new HashSet<Integer>();
		Set<String> orderMapKeySet = new HashSet<String>();
		
		
		for (Object wsSKU : skuList) {
			if (wsSKU == null) {
				continue;
			}
			Integer sellerId = null;
			if(wsSKU instanceof WSSellerSKU){
				WSSellerSKU sku  = (WSSellerSKU) wsSKU;
				sellerId = sku.getSeller().getUserId();
				
				List<WSSellerBuyerDetails> details = sku.getDetails();
				if (CollectionUtils.isNotEmpty(details)) {
					for (WSSellerBuyerDetails wsDetails : details) {
						if (wsDetails == null) {
							continue;
						}
						Integer buyerId = wsDetails.getBuyer().getUserId();
						buyerSet.add(buyerId);

						String formatOrderMapKey = OrderSheetUtil
								.formatOrderMapKey(deliveryDate, buyerId, sellerId);
						orderMapKeySet.add(formatOrderMapKey);

					}
				}
			}else{
				WSSellerDeleteSKU sku  = (WSSellerDeleteSKU) wsSKU;
				sellerId = sku.getSeller().getUserId();
				List<User> buyers = sku.getBuyerList();
				for(User buyer: buyers){
					Integer buyerId = buyer.getUserId();
					buyerSet.add(buyerId);

					String formatOrderMapKey = OrderSheetUtil
							.formatOrderMapKey(deliveryDate, buyerId, sellerId);
					orderMapKeySet.add(formatOrderMapKey);
					
				}
			}
			

			
			sellerSet.add(sellerId);
		}
		sellerBuyerMap.put("SELLER", sellerSet);
		sellerBuyerMap.put("BUYER", buyerSet);
		sellerBuyerMap.put("ORDERMAPKEY", orderMapKeySet);

		return sellerBuyerMap;
	}

	@Override
	public WSSellerGetOrderSheetResponse getOrderSheet(
			WSSellerGetOrderSheetRequest request) {

		WSSellerGetOrderSheetResponse response = new WSSellerGetOrderSheetResponse();
		WSSellerResponse result = webServiceSellerValidator
				.validateRequest(request);
		if (result != null) {
			response.setResult(result);
			return response;
		}
		result = webServiceSellerValidator
				.validateSystemName(request.getSystemName());
		if (result != null) {
			response.setResult(result);
			return response;
		}
		LOGGER.info("getOrderSheet2 Request from "+ request.getSystemName());
		LOGGER.info("validating request parameters");
		Map<String, Object> mapResult = webServiceSellerValidator
				.validateSellerUser(request.getUsername(),
						request.getPassword());

		result = (WSSellerResponse) mapResult.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		}

		User user = (User) mapResult.get("user");
		EONUserPreference userPreference = userPreferenceService.getUserPreference(user);
		user.setPreference(userPreference);
		request.setUser(user);

		// orderDate
		String strOrderDate = request.getOrderDate();
		mapResult = webServiceSellerValidator.validateOrderDate(strOrderDate);

		result = (WSSellerResponse) mapResult.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		}

		Date orderDate = (Date) mapResult.get("orderDate");
		request.setDeliveryDate(orderDate);

		// category
		Map<String, Object> map = webServiceSellerValidator
				.validateCategoryName(request.getSkuCategoryName());

		result = (WSSellerResponse) map.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		} else {
			request.setCategory((Category) map.get("category"));
		}



		// validate sellerids

		map = webServiceSellerValidator
				.validateSellerIdList(user, request.getSellerIds(),
						strOrderDate);
		result = (WSSellerResponse) map.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		}

		List<Integer> sellerIds = (List<Integer>) map.get("sellerIdList");
		request.setSellerIdList(sellerIds);

		// category membership
		result = webServiceSellerValidator
				.validateCategoryMembership(request.getCategory().getCategoryId(), sellerIds);
		if (result != null) {
			response.setResult(result);
			return response;
		}
		
		
		
		// validate buyerids

		map = webServiceSellerValidator
				.validateBuyerIdList(request.getBuyerIds(), strOrderDate,
						sellerIds);
		result = (WSSellerResponse) map.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		}
		List<Integer> buyerIds = (List<Integer>) map.get("buyerIdList");
		request.setBuyerIdList(buyerIds);

		List<String> dateList = DateFormatter.getDateList(strOrderDate,
				strOrderDate);

		Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap = dealingPatternService
				.getDealingPatternMap(dateList, sellerIds, null);
		// create buyer to seller DP map
		Map<String, Map<String, List<Integer>>> buyerToSellerDPMap = dealingPatternService
				.getBuyerToSellerDPMap(sellerToBuyerDPMap);
		DealingPattern dp = new DealingPattern(sellerToBuyerDPMap,
				buyerToSellerDPMap);

		
		map = webServiceSellerValidator.validatePageNum(request.getPageNum());
		result = (WSSellerResponse) map.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		}

		Integer pageNum = (Integer) map.get("pageNum");
		request.setPageNumInt(pageNum);

		map = webServiceSellerValidator.validatePageSize(request.getPageSize());
		result = (WSSellerResponse) map.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		}
		Integer pageSize = (Integer) map.get("pageSize");
		request.setPageSizeInt(pageSize);
		
		
		
		OrderSheetParam orderSheetParam = setupOrderSheetParam(request);

		try {
			orderSheetService.insertDefaultOrders(orderSheetParam, user, dp);
		} catch (JSONException r) {
			LOGGER.error("JSONException encountered", r);
			result = new WSSellerResponse();
			result.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
			result.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.TECHNICAL_ERR_MSG));
			response.setResult(result);
			return response;
		} catch (Exception e) {
			LOGGER.error("Generic Exception encountered", e);
			result = new WSSellerResponse();
			result.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
			result.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.TECHNICAL_ERR_MSG));
			response.setResult(result);
			return response;
		}

		List<Order> allOrders = orderSheetService.getAllOrders(buyerIds,
				dateList, sellerIds);

		Map<String, Order> allOrdersMap = OrderSheetUtil
				.convertToOrderMap(allOrders);
		List<Integer> selectedOrderIds = orderSheetService.getSelectedOrderIds(
				allOrdersMap, orderSheetParam);
		TableParameter tableParam = setupTableParam(pageSize, pageNum);
		Map<String, Object> loadOrderItems = null;
		
		
		List<AdminMember> adminMemberList = dealingPatternService
				.getMembersByAdminIdWithStartEndDates(user.getUserId(),
						DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER, strOrderDate, strOrderDate);

		Map<String, List<Integer>> mapOfMembersByDate = OrderSheetUtil
				.convertToMapOfMembersByDate(adminMemberList, strOrderDate,
						strOrderDate);


		try {
			loadOrderItems = orderSheetService.loadOrderItems(selectedOrderIds,
					user, orderSheetParam, tableParam.getPageInfo(),
					mapOfMembersByDate);

		} catch (JSONException r) {
			LOGGER.error("JSONException encountered", r);
			result = new WSSellerResponse();
			result.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
			result.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.TECHNICAL_ERR_MSG));
			response.setResult(result);
			return response;
		} catch (Exception e) {
			LOGGER.error("Generic Exception encountered", e);
			result = new WSSellerResponse();
			result.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
			result.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.TECHNICAL_ERR_MSG));
			response.setResult(result);
			return response;
		}
		result = webServiceSellerValidator.validateOrderMap(loadOrderItems);
		if (result != null) {
			response.setResult(result);
			return response;
		}

		List<Map<String, Object>> skuOrderList = (List<Map<String, Object>>) loadOrderItems
				.get("skuOrderMaps");

		result = webServiceSellerValidator.validateOrderList(skuOrderList);
		if (result != null) {
			response.setResult(result);
			return response;
		}
		
		try {
			LOGGER.info("converting service call result to webservice output parameter");

			List<User> buyers = userInfoService.getUserById(buyerIds);
			ArrayList<WSSellerReturnSKU> convertListToWSSKUList = convertListToWSSKUList(skuOrderList, buyers,user, request.getCategory(), strOrderDate);
			response.setSkuOrderList(convertListToWSSKUList);
			LOGGER.info("completed converting service call result to webservice output parameter");
		} catch (JSONException r){
			LOGGER.error("JSONException encountered", r);
			result = new WSSellerResponse(); 
			result.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
			result.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.TECHNICAL_ERR_MSG));
			 response.setResult(result);
			 return response;
		}
		catch (Exception e) {
			LOGGER.error("Generic Exception encountered", e);
			result = new WSSellerResponse(); 
			result.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
			result.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.TECHNICAL_ERR_MSG));
			 response.setResult(result);
			 return response;
		}
		
		result = new WSSellerResponse();
		result.setResultCode(WebServiceConstants.WS_ERROR_CODE_0);
		result.setResultMsg(messageI18NService
				.getPropertyMessage(WebServiceConstants.WS_ERROR_MSG_0));

		// response.setTotals((ProfitInfo) resultMap.get("totals"));
		// response.setGrandTotals((ProfitInfo) resultMap.get("grandTotals"));
		LOGGER.info("completed processing webservice getOrderSheet2 request");
		response.setResult(result);
		return response;
	}

	private OrderSheetParam setupOrderSheetParam(
			WSSellerGetOrderSheetRequest request) {
		String orderDate = request.getOrderDate();
		User user = request.getUser();

		OrderSheetParam orderSheetParam = new OrderSheetParam();
		orderSheetParam.setStartDate(orderDate);
		orderSheetParam.setEndDate(orderDate);
		orderSheetParam.setAllDatesView(false);
		orderSheetParam.setCheckDBOrder(true);

		orderSheetParam.setSelectedDate(orderDate);
		orderSheetParam.setSelectedSellerID(StringUtils.join(
				request.getSellerIdList(), ";"));

		if (RolesUtil.isUserRoleSeller(user)) {
			orderSheetParam
					.setSheetTypeId(SheetTypeConstants.SELLER_ORDER_SHEET);
		} else if (RolesUtil.isUserRoleSellerAdmin(user)) {
			orderSheetParam
					.setSheetTypeId(SheetTypeConstants.SELLER_ADMIN_ORDER_SHEET);
		}

		orderSheetParam.setSelectedBuyerID(StringUtils.join(
				request.getBuyerIdList(), ";"));
		orderSheetParam.setDatesViewBuyerID("0");

		orderSheetParam.setCategoryId(request.getCategory().getCategoryId());

		return orderSheetParam;
	}

	private TableParameter setupTableParam(Integer pageSize, Integer pageNum) {
		TableParameter tableParam = new TableParameter();
		PageInfo pageInfo = getPageInfo(pageSize, pageNum);
		tableParam.setPageInfo(pageInfo);
		tableParam.setAction("load");
		return tableParam;
	}

	private PageInfo getPageInfo(Integer pageSize, Integer pageNum) {
		PageInfo info = new PageInfo();

		if (NumberUtil.isNullOrZero(pageSize)) {
			pageSize = 120;
		}
		if (NumberUtil.isNullOrZero(pageNum)) {
			pageNum = 1;
		}

		info.setPageNum(pageNum);
		info.setPageSize(pageSize);
		int startRow = (pageSize * (pageNum - 1)) + 1;
		int endRow = pageSize * pageNum;

		info.setEndRowNum(endRow);
		info.setStartRowNum(startRow);
		return info;
	}
	
	public ArrayList<WSSellerReturnSKU> convertListToWSSKUList(List<Map<String, Object>> dataList, List<User> buyers, User user, Category category, String orderDate) throws JSONException{
		ArrayList<WSSellerReturnSKU> result = new ArrayList<WSSellerReturnSKU>();
		
		for(Map<String, Object> map:dataList){
			WSSellerReturnSKU sku = new WSSellerReturnSKU();
			sku.setOrderDate(orderDate);
			sku.setSkuId(String.valueOf(map.get("skuId")));
			sku.setSkuName((String)map.get("skuname"));
			//sku.setSheetType("Order");
			sku.setSkuCategoryId(category.getCategoryId().toString());
			sku.setSkuCategoryName(category.getDescription());
			sku.setSkuGroupId(String.valueOf(map.get("groupname")));
			sku.setSkuGroupName(String.valueOf(map.get("group")));
			sku.setSellerName(String.valueOf(map.get("sellername")));
			sku.setSellerId(String.valueOf(map.get("sellerId")));
			sku.setUom(String.valueOf(map.get("unitorderName")));
			//sku.setUnitOrderName((String)map.get("unitorderName"));
			BigDecimal tmp = (BigDecimal)map.get("rowqty");
			if(!NumberUtil.isNullOrZero(tmp)){
				sku.setQty(tmp.toString());
			}else{
				sku.setQty("0");
			}
			//sku.setBuyerId(String.valueOf(user.getUserId()));
			//sku.setBuyerName((String)user.getName());
			sku.setCompany((String)map.get("companyname"));
			//sku.setCompanyId(String.valueOf(map.get("companyid")));
			sku.setLocation((String)map.get("home"));
			sku.setMarket((String)map.get("marketname"));
			sku.setGrade((String)map.get("grade"));
			sku.setClazz((String)map.get("clazzname"));
			sku.setSkuMaxLimit((String)map.get("skumaxlimit"));
			tmp = (BigDecimal)map.get("price1");
			if(!NumberUtil.isNullOrZero(tmp)){
				sku.setPrice1(tmp.toString());
			}else{
				sku.setPrice1("0");
			}
			tmp = (BigDecimal)map.get("price2");
			if(!NumberUtil.isNullOrZero(tmp)){
				sku.setPrice2(tmp.toString());
			}else{
				sku.setPrice2("0");
			}
			tmp = (BigDecimal)map.get("pricewotax");
			if(!NumberUtil.isNullOrZero(tmp)){
				sku.setPriceWithoutTax(tmp.toString());
			}else{
				sku.setPriceWithoutTax("0");
			}
			tmp = (BigDecimal)map.get("pricewtax");
			if(!NumberUtil.isNullOrZero(tmp)){
				sku.setPriceWithTax(tmp.toString());
			}else{
				sku.setPriceWithTax("0");
			}
			sku.setPackageQuantity((String)map.get("packageqty"));
			sku.setPackageType((String)map.get("packagetype"));
			sku.setExternalSKUId((String)(map.get("externalSkuId")));
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
		
			//JSONObject lockflags =  new JSONObject((String)map.get("lockflag"));
			ArrayList<WSSellerReturnDetails> buyerSkuDetailList = new ArrayList<WSSellerReturnDetails>();
			for(User buyer : buyers){
				WSSellerReturnDetails details = new WSSellerReturnDetails();
					String qty = (String)map.get("Q_"+buyer.getUserId().toString());
					if (NumberUtils.isNumber(qty)){
						details.setQty(qty);
					}else{
						details.setQty("0");
					} 

				details.setVisibility((String)map.get("V_"+buyer.getUserId().toString()));			
				details.setOrderPublishedFlag((String)map.get("OP_"+buyer.getUserId().toString()));
				details.setOrderFinalizedFlag((String)map.get("OF_"+buyer.getUserId().toString()));
				details.setOrderLockedFlag((String)map.get("OL_"+buyer.getUserId().toString()));
				details.setAllocationPublishedFlag((String)map.get("AP_"+buyer.getUserId().toString()));
				details.setAllocationFinalizedFlag((String)map.get("AF_"+buyer.getUserId().toString()));
				details.setReceiveApprovedFlag((String)map.get("RA_"+buyer.getUserId().toString()));

				if(details.getQty()!=null || details.getVisibility()!=null || details.getOrderFinalizedFlag()!=null){
					details.setBuyerId(buyer.getUserId().toString());
					details.setBuyerName(buyer.getName());
					buyerSkuDetailList.add(details);
				}
			}
			sku.setBuyerDetails(buyerSkuDetailList);
			
			
			
			result.add(sku);
		}
		return result;
	}
	
	@Override
	public WSSellerDeleteOrderSheetResponse deleteOrderSheet(WSSellerDeleteOrderSheetRequest request){
		WSSellerDeleteOrderSheetResponse response = new WSSellerDeleteOrderSheetResponse();
		WSSellerResponse result = webServiceSellerValidator
				.validateRequest(request);
		if (result != null) {
			response.setResult(result);
			return response;
		}
		
		result = webServiceSellerValidator
				.validateSystemName(request.getSystemName());
		if (result != null) {
			response.setResult(result);
			return response;
		}
		
		
		LOGGER.info("deleteordersheet Request from "+ request.getSystemName());
		LOGGER.info("validating request parameters");
		Map<String, Object> mapResult = webServiceSellerValidator
				.validateSellerUser(request.getUsername(),
						request.getPassword());

		result = (WSSellerResponse) mapResult.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		}

		User user = (User) mapResult.get("user");
		EONUserPreference userPreference = userPreferenceService.getUserPreference(user);
		user.setPreference(userPreference);
		request.setUser(user);

		// orderDate
		String strOrderDate = request.getOrderDate();
		mapResult = webServiceSellerValidator.validateOrderDate(strOrderDate);

		result = (WSSellerResponse) mapResult.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		}

		Date orderDate = (Date) mapResult.get("orderDate");
		request.setDeliveryDate(orderDate);

		// category
		Map<String, Object> map = webServiceSellerValidator
				.validateCategoryName(request.getSkuCategoryName());

		result = (WSSellerResponse) map.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		} else {
			request.setCategory((Category) map.get("category"));
		}

		List<Integer> sellerAdminUsers = new ArrayList<Integer>();
		if (RolesUtil.isUserRoleSellerAdmin(user)) {
			List<User> members = dealingPatternService.getMembersByAdminId(
					user.getUserId(),
					DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER,
					request.getOrderDate(), request.getOrderDate());
			sellerAdminUsers = OrderSheetUtil.getUserIdList(members);
		} else {
			sellerAdminUsers.add(user.getUserId());
		}

		
		if (CollectionUtils.isNotEmpty(request.getSku())) {
			Map<Integer, List<UsersCategory>> userCats = new HashMap<Integer, List<UsersCategory>>();
			for (WSSellerDeleteSKU sku : request.getSku()) {
				result = webServiceSellerValidator.validateSKUForDelete(user,
						request.getOrderDate(), request.getCategory()
								.getCategoryId(), sku, sellerAdminUsers, userCats);
				if (result != null) {
					response.setResult(result);
					return response;
				}
				

			}
		}
		
		Map<String, Set<?>> extractionResult = extractSellerBuyerCombination(
				request.getDeliveryDate(), request.getSku());
		Set<Integer> sellerSet = (Set<Integer>) extractionResult.get("SELLER");
		Set<Integer> buyerSet = (Set<Integer>) extractionResult.get("BUYER");
		// Set<String> orderMapKey = (Set<String>)
		// extractionResult.get("ORDERMAPKEY");
		List<Integer> sellerIds = new ArrayList<Integer>(sellerSet);
		List<Integer> buyerIds = new ArrayList<Integer>(buyerSet);
		List<String> dateList = Arrays.asList(DateFormatter
				.convertToString(orderDate));
		
		/* retrieve the corresponding order records */
		List<Order> allOrders = orderSheetService.getAllOrders(buyerIds,
				dateList, sellerIds);
		Map<String, Order> allOrdersMap = OrderSheetUtil
				.convertToOrderMap(allOrders);

		
		result = webServiceSellerValidator.validateSKUForDelete(request.getSku(), allOrders,  request.getCategory().getCategoryId());
		if (result != null) {
			response.setResult(result);
			return response;
		}
		
		
		
		
		mapResult = webServiceSellerValidator.processConcurencyCheckForDelete(request.getSku(), strOrderDate, allOrders, allOrdersMap, dateList, request.getCategory().getCategoryId());
		result = (WSSellerResponse) mapResult.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		}
		
		List<OrderItem> oiToDelete = (List<OrderItem>)mapResult.get("oiToDelete");
		List<Integer> orderItemIdList = OrderSheetUtil.getOrderItemIdList(oiToDelete);

		try {
			orderSheetService.deleteAllOrderItems(orderItemIdList);
		} catch (Exception e) {
			LOGGER.error("Generic Exception encountered", e);
			result = new WSSellerResponse();

			result.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
			result.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.TECHNICAL_ERR_MSG));
			response.setResult(result);
			return response;
		}
		
				
		result = new WSSellerResponse();
		result.setResultCode(WebServiceConstants.WS_ERROR_CODE_0);
		result.setResultMsg(messageI18NService
				.getPropertyMessage(WebServiceConstants.WS_ERROR_MSG_0));

		response.setResult(result);
		LOGGER.info("completed processing webservice delete ordersheet request");
		return response;
	}
	
	@Override
	public WSSellerUpdateOrderSheetResponse updateOrderSheet(WSSellerUpdateOrderSheetRequest request){
		WSSellerUpdateOrderSheetResponse response = new WSSellerUpdateOrderSheetResponse();
		WSSellerResponse result = webServiceSellerValidator
				.validateRequest(request);
		if (result != null) {
			response.setResult(result);
			return response;
		}
		
		result = webServiceSellerValidator
				.validateSystemName(request.getSystemName());
		if (result != null) {
			response.setResult(result);
			return response;
		}
		LOGGER.info("updateOrderSheet Request from "+ request.getSystemName());
		LOGGER.info("validating request parameters");

		Map<String, Object> mapResult = webServiceSellerValidator
				.validateSellerUser(request.getUsername(),
						request.getPassword());

		result = (WSSellerResponse) mapResult.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		}

		User user = (User) mapResult.get("user");
		request.setUser(user);

		// orderDate
		mapResult = webServiceSellerValidator.validateOrderDate(request
				.getOrderDate());

		result = (WSSellerResponse) mapResult.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		}

		Date orderDate = (Date) mapResult.get("orderDate");
		request.setDeliveryDate(orderDate);

		// category
		Map<String, Object> map = webServiceSellerValidator
				.validateCategoryName(request.getSkuCategoryName());

		result = (WSSellerResponse) map.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		} else {
			request.setCategory((Category) map.get("category"));
		}

		List<Integer> sellerAdminUsers = new ArrayList<Integer>();
		if (RolesUtil.isUserRoleSellerAdmin(user)) {
			List<User> members = dealingPatternService.getMembersByAdminId(
					user.getUserId(),
					DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER,
					request.getOrderDate(), request.getOrderDate());
			sellerAdminUsers = OrderSheetUtil.getUserIdList(members);
		} else {
			sellerAdminUsers.add(user.getUserId());
		}

		List<WSSellerSKU> skuList = new ArrayList<WSSellerSKU>();
		if (CollectionUtils.isNotEmpty(request.getSku())) {
			Map<Integer, List<UsersCategory>> userCats = new HashMap<Integer, List<UsersCategory>>();
			for (WSSellerSKUUpdateRequest sku : request.getSku()) {
				result = webServiceSellerValidator.validateSKUForUpdate(user,
						request.getOrderDate(), request.getCategory()
								.getCategoryId(), sku, sellerAdminUsers, userCats);
						
				if (result != null) {
					response.setResult(result);
					return response;
				}
				skuList.add(sku.getSellerSKU());

			}
		}

		Map<String, Set<?>> extractionResult = extractSellerBuyerCombination(
				request.getDeliveryDate(), skuList);
		Set<Integer> sellerSet = (Set<Integer>) extractionResult.get("SELLER");
		Set<Integer> buyerSet = (Set<Integer>) extractionResult.get("BUYER");
		// Set<String> orderMapKey = (Set<String>)
		// extractionResult.get("ORDERMAPKEY");
		List<Integer> sellerIds = new ArrayList<Integer>(sellerSet);
		List<Integer> buyerIds = new ArrayList<Integer>(buyerSet);
		List<String> dateList = Arrays.asList(DateFormatter
				.convertToString(orderDate));

		/* retrieve the corresponding order records */
		List<Order> allOrders = orderSheetService.getAllOrders(buyerIds,
				dateList, sellerIds);
		Map<String, Order> allOrdersMap = OrderSheetUtil
				.convertToOrderMap(allOrders);


		
		result = webServiceSellerValidator.validateSKUForUpdate(skuList, allOrders, request.getCategory().getCategoryId());
		if (result != null) {
			response.setResult(result);
			return response;
		}
		
		
		
		
		
		Map<String, Object> processConcurencyCheckForUpdate = webServiceSellerValidator
				.processConcurencyCheckForUpdate(skuList, request.getOrderDate(), allOrdersMap, dateList, request.getCategory().getCategoryId());
		result = (WSSellerResponse)processConcurencyCheckForUpdate.get("response");
		if (result != null) {
			response.setResult(result);
			return response;
		}
		
		
		/*
		 * update skus and orderitems
		 */
		
		
		
		try {
			orderSheetService.updateOrdersheetWS(skuList, request.getOrderDate());
		} catch (Exception e) {
			LOGGER.error("Generic Exception encountered", e);
			result = new WSSellerResponse();

			result.setResultCode(WebServiceConstants.TECHNICAL_ERR_CODE);
			result.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.TECHNICAL_ERR_MSG));
			response.setResult(result);
			return response;
		}
		result = new WSSellerResponse();
		result.setResultCode(WebServiceConstants.WS_ERROR_CODE_0);
		result.setResultMsg(messageI18NService
				.getPropertyMessage(WebServiceConstants.WS_ERROR_MSG_0));

		response.setResult(result);
		LOGGER.info("completed processing webservice update ordersheet request");
		return response;
	}
}
