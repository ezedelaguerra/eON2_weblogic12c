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
 * date       	name            changes
 * ------------------------------------------------------------------------------
 * 20120702		Rhoda		PROD ISSUE – SKU w/ maxlimit doesn't appear on Received after SKU Qty input on a published Alloc.
 */
package com.freshremix.webapp.controller.allocationsheet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.Item;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SKU;
import com.freshremix.model.SheetData;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.service.AllocationSheetService;
import com.freshremix.service.CategoryService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.service.SheetDataService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.ui.model.PageInfo;
import com.freshremix.ui.model.ProfitInfo;
import com.freshremix.ui.model.TableParameter;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;
import com.freshremix.util.TaxUtil;

public class AllocationSheetLoadController implements Controller, InitializingBean{

	private AllocationSheetService allocationSheetService;
	private SKUGroupService skuGroupService;
	private MessageSourceAccessor messageSourceAccessor;
	private EONLocale eonLocale;
	private MessageSource messageSource;
	private SheetDataService sheetDataService;
	private CategoryService categoryService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}

	
	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}

	public MessageSourceAccessor getMessageSourceAccessor() {
		return messageSourceAccessor;
	}

	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		this.messageSourceAccessor = messageSourceAccessor;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}

	public void setAllocationSheetService(
			AllocationSheetService allocationSheetService) {
		this.allocationSheetService = allocationSheetService;
	}
	
	public void setSheetDataService(SheetDataService sheetDataService) {
		this.sheetDataService = sheetDataService;
	}
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		String json = request.getParameter("_gt_json");
		Serializer serializer = new JsonSerializer();
		TableParameter tableParam = (TableParameter) serializer.deserialize(json, TableParameter.class);
		
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		
		List<Map<String, Object>> skuOrderList = this.loadOrderItems(request, user, osParam, tableParam.getPageInfo(), buyerIds, sellerIds);
		
		ModelAndView mav = new ModelAndView("json");
		
		// set list of sku group
		String skuGroupList = skuGroupService.getSKUGroupList(user.getUserId().intValue(), osParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
		

		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		if (CollectionUtils.isEmpty(allOrders)) {
			allOrders = allocationSheetService.getAllOrders(buyerIds, dateList, sellerIds);
			SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);	
		}
		String confirmMsg = "";
		if (hasNoFinalizedOrder(allOrders)) {
			confirmMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
				getMessage("allocationsheet.seller.noSkuOrderList", eonLocale.getLocale()));
		}
		
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		List<Order> selectedOrders = allocationSheetService.getSelectedOrders(allOrdersMap, osParam);
		JSONObject buttonFlags = getButtonFlags(selectedOrders);
		
		SessionHelper.getAttribute(request, SessionParamConstants.SELECTED_ORDERS_PARAM);
		Set<String> sellernameSet = extracted(request);
		//rhoda redmine 1113 start
		if (RolesUtil.isUserRoleSeller(user) && sellernameSet.isEmpty()) {
			sellernameSet.add(user.getShortName());
		}
		//rhoda redmine 1113 end
		
		// Total Prices
		List<Integer> categoryList = new ArrayList<Integer>();
		List<String> deliveryDates = null; 
		List<Integer> buyerId = null;
		categoryList.add(osParam.getCategoryId());
		
		if (osParam.isAllDatesView()) {
			deliveryDates = new ArrayList<String>(dateList);
			buyerId = new ArrayList<Integer>();
			buyerId.add(Integer.parseInt(osParam.getDatesViewBuyerID()));
		}
		else {
			deliveryDates = new ArrayList<String>();
			deliveryDates.add(osParam.getSelectedDate());
			buyerId = new ArrayList<Integer>(buyerIds);
		}
		
		//get the list of valid seller members
		Map<String, List<Integer>> mapOfMembersByDate = null;
		if (RolesUtil.isUserRoleSellerAdmin(user)){
			mapOfMembersByDate = getMapOfMembersByDateFromSession(request);
		}
		
		ProfitInfo pi = allocationSheetService.getProfitInfo(user,
				deliveryDates, sellerIds, mapOfMembersByDate, buyerId,
				categoryList, TaxUtil.getTAX_RATE().doubleValue());
		
		// Grand Totals
		List<UsersCategory> allCategory = categoryService.getCategoryList(user, osParam);
		List<Integer> categoryId = new ArrayList<Integer>();
		
		for (UsersCategory _cat : allCategory) {
			categoryId.add(_cat.getCategoryId());
		}
		
		ProfitInfo pi2 = allocationSheetService.getProfitInfo(user, dateList,
				sellerIds, mapOfMembersByDate, buyerIds, categoryId,
				TaxUtil.getTAX_RATE().doubleValue());
		
		mav.addObject("data", skuOrderList);
		mav.addObject("pageInfo", tableParam.getPageInfo());
		mav.addObject("skuGroup", skuGroupList);
		mav.addObject("totals", pi);
		mav.addObject("grandTotals", pi2);
		mav.addObject("buttonFlags", buttonFlags.toString());
		mav.addObject("sellernameSet", sellernameSet);
		mav.addObject("confirmMsg", confirmMsg);
		return mav;
	}
	
	private boolean hasNoFinalizedOrder(List<Order> allOrders) {
		
		boolean hasNoFinalizedOrder = true;
		
		for (Order order: allOrders) {
			if (!NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) {
				hasNoFinalizedOrder = false;
				break;
			}
		}
		
		return hasNoFinalizedOrder;
	}


	private JSONObject getButtonFlags(List<Order> selectedOrders) throws JSONException {
		
		Order order = allocationSheetService.combineOrders(selectedOrders);
		JSONObject flags = new JSONObject();
		
		// 0 - enable / 1 - disable
		boolean isOrderFinalized = !NumberUtil.isNullOrZero(order.getOrderFinalizedBy());
		boolean isSaved = !NumberUtil.isNullOrZero(order.getAllocationSavedBy());
		boolean isPublished = !NumberUtil.isNullOrZero(order.getAllocationPublishedBy());
		boolean isFinalized = !NumberUtil.isNullOrZero(order.getAllocationFinalizedBy());
		
		if (!isSaved && !isPublished && !isFinalized) {
			flags.put("btnSave", 0);
			flags.put("btnPublish", 1);
			flags.put("btnFinalize", 1);
		} else if (!isSaved && !isPublished && isFinalized) {
			flags.put("btnUnfinalize", 0);
		} else if (!isSaved && isPublished && !isFinalized) {
			flags.put("btnSave", 0);
			flags.put("btnPublish", 1);
			flags.put("btnFinalize", 0);
		} else if (!isSaved && isPublished && isFinalized) {
			flags.put("btnSave", 0);
			flags.put("btnPublish", 1);
			flags.put("btnFinalize", 0);
			flags.put("btnUnfinalize", 0);
		} else if (isSaved && !isPublished && !isFinalized) {
			flags.put("btnSave", 0);
			flags.put("btnPublish", 0);
			flags.put("btnFinalize", 1);
		} else if (isSaved && !isPublished && isFinalized) {
			flags.put("btnSave", 0);
			flags.put("btnPublish", 0);
			flags.put("btnFinalize", 1);
			flags.put("btnUnfinalize", 0);
		} else if (isSaved && isPublished && !isFinalized) {
			flags.put("btnSave", 0);
			flags.put("btnPublish", 0);
			flags.put("btnFinalize", 0);
		} else if (isSaved && isPublished && isFinalized) {
			flags.put("btnSave", 0);
			flags.put("btnPublish", 0);
			flags.put("btnFinalize", 0);
			flags.put("btnUnfinalize", 0);
		}
		
		if (isOrderFinalized) {
			flags.put("orderFinalized", true);
		}
		
		if (allOrdersAreFinalized(selectedOrders)) {
			flags.put("btnSave", 1);
			flags.put("btnPublish", 1);
			flags.put("btnUnpublish", 1);
		}
		
		// locked
		if (!NumberUtil.isNullOrZero(order.getReceivedApprovedBy())) {
			flags.put("btnLock", 1);
			
		}else {
			flags.put("btnLock", 0);
			
		}
		
		return flags;
	}
	
	private boolean allOrdersAreFinalized(List<Order> selectedOrders) {
		for (Order order : selectedOrders) {
			if (NumberUtil.isNullOrZero(order.getAllocationFinalizedBy()))
				return false;
		}
		return true;
	}
	
	private List<Map<String, Object>> loadOrderItems(HttpServletRequest request, User user,
			OrderSheetParam osParam, PageInfo pageInfo, List<Integer> companyBuyerIds, List<Integer> sellerIds) throws Exception {
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		
		String deliveryDate = osParam.getSelectedDate();
		//List<Integer> companyBuyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		//Integer categoryId = osParam.getCategoryId();
		boolean isAllDatesView = osParam.isAllDatesView();
		String startDate = osParam.getStartDate();
		String endDate = osParam.getEndDate();
		Integer datesViewBuyerId = Integer.valueOf((String) osParam.getDatesViewBuyerID());
		
		List<String> deliveryDates = new ArrayList<String>();
		if (isAllDatesView) {
			deliveryDates = DateFormatter.getDateList(startDate, endDate);
		} else {
			deliveryDates.add(deliveryDate);
		}
		
		Map<String, List<Integer>> mapOfMembersByDate = null;

		if (RolesUtil.isUserRoleSellerAdmin(user)) {
			mapOfMembersByDate = getMapOfMembersByDateFromSession(request);
		}

		
		List<Integer> selectedOrders = getIntegerListFromSession(request, SessionParamConstants.SELECTED_ORDERS_PARAM);
		
		if (selectedOrders.size() == 0){
			selectedOrders.add(999999999);
		}
		
//		List<SKU> allSkuObjs = allocationSheetService.getDistinctSKUs(selectedOrders, categoryId,
//				pageInfo.getStartRowNum(), pageInfo.getPageSize());
		List<Integer> categoryList = new ArrayList<Integer>();
		categoryList.add(osParam.getCategoryId());
		SheetData data = sheetDataService.loadSheetData(
				user, startDate, endDate, 
				sellerIds, 
				companyBuyerIds, 
				categoryList, 
				osParam.getSheetTypeId(), false, false, selectedOrders);
		
		List<?> allSkuObjs = data.getListOfSku();
		List<SKU> skuObjs = new ArrayList<SKU>();
		int rowIdxStart = pageInfo.getStartRowNum() - 1;
		int rowIdxEnd = rowIdxStart + pageInfo.getPageSize() - 1;
		for (int i=rowIdxStart; i<=rowIdxEnd; i++) {
			if (i >= allSkuObjs.size()) break;
			//skuObjs.add(allSkuObjs.get(i));
			if (allSkuObjs.get(i) instanceof SKU)
				skuObjs.add((SKU)allSkuObjs.get(i));
		}
		pageInfo.setTotalRowNum(allSkuObjs.size());
		
//		Map<Integer, Map<String, Map<Integer, OrderItem>>> sellerAllocItemMap = 
//			new HashMap<Integer, Map<String, Map<Integer, OrderItem>>>();
//		try {
//			if (skuObjs.size() > 0)
//				sellerAllocItemMap = allocationSheetService.getSellerAllocItemsBulk(selectedOrders,
//						OrderSheetUtil.getSkuIds(skuObjs));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		Map<String, Map<String, Map<Integer, Item>>> sellerAllocItemMap = 
			data.getSkuDateBuyOrderItemMap();
		
		Map<Integer, SKU> skuObjMap = new HashMap<Integer, SKU>();
		Set<String> sellerNameSet = new TreeSet<String>();
		JSONObject defaultLock = null;
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		Map<String, Order> convertToOrderMap = OrderSheetUtil.convertToOrderMap(allOrders);
		DealingPattern dp = (DealingPattern)SessionHelper.getAttribute(request, SessionParamConstants.DEALING_PATTERN_PARAM);

		for (SKU skuObj : skuObjs) {
			
			JSONObject json = new JSONObject();
			skuObjMap.put(skuObj.getSkuId(), skuObj);
			Map<String, Object> skuOrderMap = new HashMap<String, Object>();
			Integer sellerId = skuObj.getUser().getUserId();
			
			skuOrderMap.put("skuId", 		skuObj.getSkuId());
			skuOrderMap.put("sellerId", 	sellerId);
			skuOrderMap.put("groupname", 	StringUtil.nullToBlank(skuObj.getSkuGroup().getSkuGroupId()));
			
			if(skuObj.getProposedBy() != null) {
				json.put("sku", "1");
				skuOrderMap.put("companyname", skuObj.getProposedBy().getCompany().getShortName());
				skuOrderMap.put("sellername", skuObj.getProposedBy().getShortName());
			}
			else {
				skuOrderMap.put("companyname", 	skuObj.getUser().getCompany().getShortName());
				skuOrderMap.put("sellername", 	skuObj.getUser().getShortName());
			}
			
			skuOrderMap.put("marketname", 	StringUtil.nullToBlank(skuObj.getMarket()));
			skuOrderMap.put("column01", 	StringUtil.nullToBlank(skuObj.getColumn01()));
			skuOrderMap.put("column02", 	StringUtil.nullToBlank(skuObj.getColumn02()));
			skuOrderMap.put("column03", 	StringUtil.nullToBlank(skuObj.getColumn03()));
			skuOrderMap.put("column04", 	StringUtil.nullToBlank(skuObj.getColumn04()));
			skuOrderMap.put("column05", 	StringUtil.nullToBlank(skuObj.getColumn05()));
			skuOrderMap.put("skuname", 		StringUtil.nullToBlank(skuObj.getSkuName()));
			skuOrderMap.put("home", 		StringUtil.nullToBlank(skuObj.getLocation()));
			skuOrderMap.put("grade", 		StringUtil.nullToBlank(skuObj.getGrade()));
			skuOrderMap.put("clazzname", 	StringUtil.nullToBlank(skuObj.getClazz()));
			skuOrderMap.put("price1", 		NumberUtil.nullToZero((BigDecimal)skuObj.getPrice1()));
			skuOrderMap.put("price2", 		NumberUtil.nullToZero((BigDecimal)skuObj.getPrice2()));
			skuOrderMap.put("pricewotax", 	NumberUtil.nullToZero((BigDecimal)skuObj.getPriceWithoutTax()));
			skuOrderMap.put("pricewtax", 	skuObj.getPriceWithTax());
			skuOrderMap.put("packageqty", 	StringUtil.nullToBlank(skuObj.getPackageQuantity()));
			skuOrderMap.put("packagetype", 	StringUtil.nullToBlank(skuObj.getPackageType()));
			skuOrderMap.put("unitorder", 	skuObj.getOrderUnit().getOrderUnitId());
			skuOrderMap.put("column06", 	StringUtil.nullToBlank(skuObj.getColumn06()));
			skuOrderMap.put("column07", 	StringUtil.nullToBlank(skuObj.getColumn07()));
			skuOrderMap.put("column08", 	StringUtil.nullToBlank(skuObj.getColumn08()));
			skuOrderMap.put("column09", 	StringUtil.nullToBlank(skuObj.getColumn09()));
			skuOrderMap.put("column10", 	StringUtil.nullToBlank(skuObj.getColumn10()));
			skuOrderMap.put("column11", 	StringUtil.nullToBlank(skuObj.getColumn11()));
			skuOrderMap.put("column12", 	StringUtil.nullToBlank(skuObj.getColumn12()));
			skuOrderMap.put("column13", 	StringUtil.nullToBlank(skuObj.getColumn13()));
			skuOrderMap.put("column14", 	StringUtil.nullToBlank(skuObj.getColumn14()));
			skuOrderMap.put("column15", 	StringUtil.nullToBlank(skuObj.getColumn15()));
			skuOrderMap.put("column16", 	StringUtil.nullToBlank(skuObj.getColumn16()));
			skuOrderMap.put("column17", 	StringUtil.nullToBlank(skuObj.getColumn17()));
			skuOrderMap.put("column18", 	StringUtil.nullToBlank(skuObj.getColumn18()));
			skuOrderMap.put("column19", 	StringUtil.nullToBlank(skuObj.getColumn19()));
			skuOrderMap.put("column20", 	StringUtil.nullToBlank(skuObj.getColumn20()));
			skuOrderMap.put("externalSkuId",  StringUtil.nullToBlank(skuObj.getExternalSkuId()));
			// ENHANCEMENT 20120702: Rhoda PROD ISSUE 
			skuOrderMap.put("skumaxlimit",  StringUtil.nullToBlank((BigDecimal)skuObj.getSkuMaxLimit()));
			
			if (isAllDatesView) {
				Set<String> disabledDates = getDisabledDates(deliveryDates, sellerId,
						Integer.valueOf(osParam.getDatesViewBuyerID()), convertToOrderMap);
				this.prepareOrderAllDates(skuOrderMap, deliveryDates, datesViewBuyerId,
						skuObj, json, disabledDates, sellerAllocItemMap, user, mapOfMembersByDate);
			}
			else {
				Set<Integer> finalizedBuyers = getDisabledBuyers(deliveryDate,
						sellerId, companyBuyerIds, convertToOrderMap, dp);
				this.prepareOrderAllBuyers(skuOrderMap, companyBuyerIds, deliveryDate, skuObj,
						json, finalizedBuyers, sellerAllocItemMap);
			}
			
			lockSellerName(user, json);
			skuOrderMap.put("lockflag", json.toString());
			
			this.prepareProfitInfoColumn(skuOrderMap, skuObj);
			
			skuOrderMaps.add(skuOrderMap);
			sellerNameSet.add(skuObj.getUser().getShortName());
			
			if (defaultLock == null) defaultLock = json;
			
		}
		
		if (defaultLock != null) {
			if (user.getRole().getSellerAdminFlag().equals("1")) defaultLock.put("adminAddSKU", "1");
			else defaultLock.put("sellername", "1");
		} else {
			defaultLock = new JSONObject();
		}
		
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_OBJ_MAP_PARAM, skuObjMap);
		SessionHelper.setAttribute(request, "sellerNameSet", sellerNameSet);
		SessionHelper.setAttribute(request, "defaultLock", defaultLock.toString());
		
		return skuOrderMaps;
	}
	
	private void prepareOrderAllBuyers(Map<String, Object> skuOrderMap, List<Integer> buyerIds,
			String deliveryDate, SKU skuObj, JSONObject json, Set<Integer> disabledBuyers,
			Map<String, Map<String, Map<Integer, Item>>> sellerAllocItemMap)
				throws Exception {
		
		String skuId = skuObj.getSkuId().toString();
		BigDecimal rowqty = new BigDecimal(0);
		int finalizedBuyerCtr = 0;
		for (Integer buyerId : buyerIds) {
			Item orderItem = null;
			try {
				orderItem = sellerAllocItemMap.get(skuId).get(deliveryDate).get(buyerId);
			} catch (NullPointerException npe) {
				orderItem = null;
			}
			
			BigDecimal quantity = null;
			String strQuantity = "";
			String strLockFlag = "0";
			
			if (orderItem != null) {
				quantity = orderItem.getQuantity();
				
				if (quantity != null) {
					rowqty = rowqty.add(quantity);
					strQuantity = quantity.toPlainString();
				}
				// SKU finalized checking
				if (!NumberUtil.isNullOrZero(orderItem.getAllocationFinalizedBy())) {
					//skuFlag = "1";
					finalizedBuyerCtr++;
					strLockFlag = "1";
					json.put("sku", "1");
				}
				
				// received sheet approved
				if (!NumberUtil.isNullOrZero(orderItem.getReceivedApprovedBy())) {
					finalizedBuyerCtr++;
					strLockFlag = "1";
					json.put("sku", "1");
				}
			}
			else {
				// always editable except for finalized buyers, unfinalized orders, locked buyers
				// and buyers with no dealing pattern
				if (disabledBuyers.contains(buyerId)) {
					finalizedBuyerCtr++;
					strLockFlag = "1";
				}
			}
			
			json.put("Q_" + buyerId.toString(), strLockFlag);
			skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
		}
		
		if (finalizedBuyerCtr == buyerIds.size()) {
			json.put("sku", "1");
		}
		
		skuOrderMap.put("rowqty", rowqty);
	}
	
	private void prepareOrderAllDates(Map<String, Object> skuOrderMap,
			List<String> deliveryDates, Integer buyerId, SKU skuObj,
			JSONObject json, Set<String> disabledDates,
			Map<String, Map<String, Map<Integer, Item>>> sellerAllocItemMap,
			User user, Map<String, List<Integer>> mapOfMembersByDate)
			throws Exception {
		
		String skuId = skuObj.getSkuId().toString();
		Integer sellerId = skuObj.getUser().getUserId();
		
		BigDecimal rowqty = new BigDecimal(0);
		for (String deliveryDate : deliveryDates) {
			Item orderItem = null;
			try {
				orderItem = sellerAllocItemMap.get(skuId).get(deliveryDate).get(buyerId);
			} catch (NullPointerException npe) {
				orderItem = null;
			}
			
			if (RolesUtil.isUserRoleSellerAdmin(user)) {
				// seller id of sku is not a valid member of admin for the given
				// date: remove set Orderitem = null so that quantity field in
				// all dates view will be locked and quantity is not displayed.
				List<Integer> mapOfMembers = mapOfMembersByDate.get(deliveryDate);
				if (CollectionUtils.isEmpty(mapOfMembers) || !mapOfMembers.contains(sellerId)) {
					orderItem = null;
				}
			}
			
			BigDecimal quantity = null;
			String strQuantity = "";
			String strLockFlag = "0";
			
			if (orderItem != null) {
				quantity = orderItem.getQuantity();
			
				if (quantity != null) {
					rowqty = rowqty.add(quantity);
					strQuantity = quantity.toPlainString();
				}
				
				// SKU finalized checking
				if (!NumberUtil.isNullOrZero(orderItem.getAllocationFinalizedBy())) {
					json.put("sku", 1);
					strLockFlag = "1";
				}
				
				// received sheet approved
				if (!NumberUtil.isNullOrZero(orderItem.getReceivedApprovedBy())) {
					json.put("sku", 1);
					strLockFlag = "1";
				}
			}
			else {
				// always editable except for finalized buyers and unfinalized orders
				if (disabledDates.contains(deliveryDate))
					strLockFlag = "1";
			}

			json.put("Q_" + deliveryDate, strLockFlag);
			skuOrderMap.put("Q_" + deliveryDate, strQuantity);
		}
		skuOrderMap.put("rowqty", rowqty);
		skuOrderMap.put("lockflag", json.toString());
	}

	private void lockSellerName(User user, JSONObject json) {
		try {
			json.put("sellername", "1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private List<Integer> getIntegerListFromSession(HttpServletRequest request, String paramName) {
		return (List<Integer>) SessionHelper.getAttribute(request, paramName);
	}
	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
	
	@SuppressWarnings("unchecked")
	private Set<String> extracted(HttpServletRequest request) {
		return (Set<String>)SessionHelper.getAttribute(request, "sellerNameSet");
	}
	
	private List<Integer> getDealingPattern(String deliveryDate, String sellerId, DealingPattern dealingPattern) {
		Map<String, List<Integer>> dp = dealingPattern.getSellerToBuyerDPMap().get(deliveryDate);
		return dp.get(sellerId);
	}
	
	private Set<Integer> getDisabledBuyers(String deliveryDate, Integer sellerId, List<Integer> companyBuyerIds, Map<String, Order> convertToOrderMap, DealingPattern dp) {
		List<Integer> dpBuyers = getDealingPattern(deliveryDate, sellerId.toString(), dp);
		Map<String, Set<Integer>> categorizedBuyers = this.categorizeBuyers(deliveryDate, sellerId, dpBuyers, convertToOrderMap);
		Set<Integer> finalizedBuyers = categorizedBuyers.get("finalized");
		Set<Integer> unfinalizedBuyerOrder = categorizedBuyers.get("unfinalized");
		Set<Integer> lockedBuyers = categorizedBuyers.get("locked");

//		Set<Integer> finalizedBuyers = this.getFinalizedBuyers(deliveryDate, sellerId, dpBuyers, convertToOrderMap);
//		Set<Integer> unfinalizedBuyerOrder = this.getUnfinalizedBuyerOrder(deliveryDate, sellerId, dpBuyers, convertToOrderMap);;
//		Set<Integer> lockedBuyers = this.getLockedBuyers(deliveryDate, sellerId, dpBuyers, convertToOrderMap);
		
		Set<Integer> buyersWithNoDealingPattern = this.getBuyersWithNoDealingPattern(dpBuyers, companyBuyerIds);
		finalizedBuyers.addAll(unfinalizedBuyerOrder);
		finalizedBuyers.addAll(lockedBuyers);
		finalizedBuyers.addAll(buyersWithNoDealingPattern);
		return finalizedBuyers;
	}
	
//	private Set<String> getDisabledDates(List<String> deliveryDates,
//			Integer sellerId, Integer buyerId, Map<String, Order> orderMap) {
//		Set<String> disabledDates = this.getDisabledDates(deliveryDates, sellerId, buyerId, orderMap);
//		return disabledDates;
//	}
	
	private Map<String, Set<Integer>> categorizeBuyers(String deliveryDate,
			Integer sellerId, List<Integer> dbBuyers,
			Map<String, Order> allOrdersMap) {
		Map<String, Set<Integer>> mapOfCategorizedBuyerIds = new HashMap<String, Set<Integer>>();
		
		Set<Integer> finalizedBuyers = new TreeSet<Integer>();
		Set<Integer> lockedBuyers = new TreeSet<Integer>();
		Set<Integer> unfinalizedBuyerOrder = new TreeSet<Integer>();
		
		for (Integer buyerId : dbBuyers) {
			String key = OrderSheetUtil.formatOrderMapKey(deliveryDate, buyerId, sellerId);
			Order order = allOrdersMap.get(key);
			if (order == null){
				continue;
			}
			if (!NumberUtil.isNullOrZero(order.getAllocationFinalizedBy())) {
				finalizedBuyers.add(buyerId);
			}
			if (!NumberUtil.isNullOrZero(order.getReceivedApprovedBy())) {
				lockedBuyers.add(buyerId);
			}
			if (NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) {
				unfinalizedBuyerOrder.add(buyerId);
			}
		}
		mapOfCategorizedBuyerIds.put("finalized", finalizedBuyers);
		mapOfCategorizedBuyerIds.put("locked", lockedBuyers);
		mapOfCategorizedBuyerIds.put("unfinalized", unfinalizedBuyerOrder);
		
		return mapOfCategorizedBuyerIds;
	}
	
//	private Set<Integer> getFinalizedBuyers(String deliveryDate, Integer sellerId, List<Integer> dbBuyers, Map<String, Order> allOrdersMap) {
//		Set<Integer> finalizedBuyers = new TreeSet<Integer>();
//		for (Integer buyerId : dbBuyers) {
//			String key = deliveryDate + "_" + buyerId + "_" + sellerId;
//			Order order = allOrdersMap.get(key);
//			if (order == null) continue;
//			if (!NumberUtil.isNullOrZero(order.getAllocationFinalizedBy())) {
//				finalizedBuyers.add(buyerId);
//			}
//		}
//		return finalizedBuyers;
//	}
	
//	private Set<Integer> getLockedBuyers(String deliveryDate, Integer sellerId, List<Integer> dbBuyers, Map<String, Order> allOrdersMap) {
//		Set<Integer> lockedBuyers = new TreeSet<Integer>();
//		for (Integer buyerId : dbBuyers) {
//			String key = deliveryDate + "_" + buyerId + "_" + sellerId;
//			Order order = allOrdersMap.get(key);
//			if (order == null) continue;
//			if (!NumberUtil.isNullOrZero(order.getReceivedApprovedBy())) {
//				lockedBuyers.add(buyerId);
//			}
//		}
//		return lockedBuyers;
//	}
	
	private Set<Integer> getBuyersWithNoDealingPattern(List<Integer> dpBuyers, List<Integer> selectedBuyers) {
		Set<Integer> buyerWithNoDP = new TreeSet<Integer>();
		for (Integer selectedBuyer : selectedBuyers) {
			if (!dpBuyers.contains(selectedBuyer)){
				buyerWithNoDP.add(selectedBuyer);
			}
		}
		return buyerWithNoDP;
	}
	
//	private Set<Integer> getUnfinalizedBuyerOrder(String deliveryDate, Integer sellerId, List<Integer> dbBuyers, Map<String, Order> allOrdersMap) {
//		Set<Integer> unfinalizedBuyerOrder = new TreeSet<Integer>();
//		for (Integer buyerId : dbBuyers) {
//			String key = deliveryDate + "_" + buyerId + "_" + sellerId;
//			Order order = allOrdersMap.get(key);
//			if (order == null) continue;
//			if (NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) {
//				unfinalizedBuyerOrder.add(buyerId);
//			}
//		}
//		return unfinalizedBuyerOrder;
//	}
	
	private Set<String> getDisabledDates(List<String> deliveryDates, Integer sellerId, Integer buyerId, Map<String, Order> orderMap) {
		Set<String> disabledDates = new TreeSet<String>();
		for (String deliveryDate : deliveryDates) {
			//String key = deliveryDate + "_" + buyerId + "_" + sellerId;
			String key = OrderSheetUtil.formatOrderMapKey(deliveryDate, buyerId, sellerId);
			Order order = orderMap.get(key);
			if (order == null) {
				disabledDates.add(deliveryDate);
				continue;
			}
			if (NumberUtil.isNullOrZero(order.getOrderFinalizedBy()) || 
				!NumberUtil.isNullOrZero(order.getAllocationFinalizedBy())) {
				disabledDates.add(deliveryDate);
			}
		}
		return disabledDates;
	}
	
	private void prepareProfitInfoColumn(Map<String, Object> skuOrderMap, SKU skuObj) 
		throws JSONException {
	
		BigDecimal totalQty = new BigDecimal(skuOrderMap.get("rowqty").toString());
		JSONObject json = new JSONObject();
		BigDecimal pWithouTax = NumberUtil.nullToZero((BigDecimal)skuObj.getPriceWithoutTax());
		BigDecimal pWithTax = NumberUtil.nullToZero((BigDecimal)skuObj.getPriceWithTax());
		json.put("priceWithoutTax", pWithouTax.multiply(totalQty));
		json.put("priceWithTax", pWithTax.multiply(totalQty));
		skuOrderMap.put("profitInfo", json.toString());
	
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, List<Integer>> getMapOfMembersByDateFromSession(
			HttpServletRequest request) {
		return (Map<String, List<Integer>>) SessionHelper.getAttribute(request, SessionParamConstants.MAP_OF_MEMBERS_BY_DATE);
	}
	
}
