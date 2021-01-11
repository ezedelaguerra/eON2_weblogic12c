package com.freshremix.webapp.controller.billingsheet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.BillingAkadenSkuIdConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.AkadenItem;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.BillingItem;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.AkadenService;
import com.freshremix.service.BillingSheetService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.ui.model.PageInfo;
import com.freshremix.ui.model.TableParameter;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.SortingUtil;
import com.freshremix.util.StringUtil;

public class BillingSheetLoadController implements Controller, InitializingBean{

	private BillingSheetService billingSheetService;
	private SKUGroupService skuGroupService;
	private OrderSheetService orderSheetService;
	private AkadenService akadenService;
	private MessageSourceAccessor messageSourceAccessor;
	private EONLocale eonLocale;
	private MessageSource messageSource;

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

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	
	public void setAkadenService(AkadenService akadenService) {
		this.akadenService = akadenService;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		String json = request.getParameter("_gt_json");
		Serializer serializer = new JsonSerializer();
		TableParameter tableParam = (TableParameter) serializer.deserialize(json, TableParameter.class);
		
		//List<OrderItemUI> orderList = orderSheetService.loadOrderItems(param);
		//List<Map<String, Object>> skuOrderList = orderSheetService.loadOrderItems2(param);
		List<Map<String, Object>> skuOrderList = this.loadOrderItems(request, user, osParam, tableParam.getPageInfo());
		
		ModelAndView mav = new ModelAndView("json");
		
		// set list of sku group
		String skuGroupList = skuGroupService.getSKUGroupList(user.getUserId().intValue(), osParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
		
		GrandTotalPrices grandTotalPrices = billingSheetService.computeTotalPricesOnDisplay(skuOrderList);
		BigDecimal totalPriceWOTax = grandTotalPrices.getPriceWithoutTax();
		BigDecimal totalPriceWTax = grandTotalPrices.getPriceWithTax();
		
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);

		String confirmMsg = "";
		if (hasNoFinalizedAllocation(allOrders)) {
			confirmMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
				getMessage("billingsheet.seller.noSkuOrderList", eonLocale.getLocale()));
		}
		
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		List<Order> selectedOrders = orderSheetService.getSelectedOrders(allOrdersMap, osParam);
		JSONObject buttonFlags = getButtonFlags(selectedOrders);

		SessionHelper.getAttribute(request, SessionParamConstants.SELECTED_ORDERS_PARAM);
		Set<String> sellernameSet = extracted(request);
		
		//mav.addObject("data", orderList);
		mav.addObject("data", skuOrderList);
		mav.addObject("pageInfo", tableParam.getPageInfo());
		mav.addObject("skuGroup", skuGroupList);
		mav.addObject("totalPriceWOTaxOnDisplay", totalPriceWOTax);
		mav.addObject("totalPriceWTaxOnDisplay", totalPriceWTax);
		mav.addObject("buttonFlags", buttonFlags.toString());
		mav.addObject("sellernameSet", sellernameSet);
		mav.addObject("confirmMsg", confirmMsg); 
		
		return mav;
	}

	private JSONObject getButtonFlags(List<Order> selectedOrders) throws JSONException {
		
		Order order = billingSheetService.combineOrders(selectedOrders);
		JSONObject flags = new JSONObject();
		
		// enable/disble
		if (selectedOrders.size() == 0) {
			flags.put("btnSave", 1);
			flags.put("btnFinalize", 0);
		} else {
			// first entry
			if (NumberUtil.isNullOrZero(order.getAllocationFinalizedBy())) {
				flags.put("btnSave", 0);
			}
			// saved
			if (!NumberUtil.isNullOrZero(order.getAllocationFinalizedBy()) && NumberUtil.isNullOrZero(order.getBillingFinalizedBy())) {
				flags.put("btnSave", 0);
				flags.put("btnFinalize", 0);
			}
			// finalized
			if (!NumberUtil.isNullOrZero(order.getBillingFinalizedBy())) {
				flags.put("btnSave", 1);
				flags.put("btnFinalize", 1);
			}
		}
		
		return flags;
	}
		
	private List<Map<String, Object>> loadOrderItems(HttpServletRequest request, User user,
			OrderSheetParam osParam, PageInfo pageInfo) throws Exception {
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		
		String deliveryDate = osParam.getSelectedDate();
//		String selectedBuyerCompany = osParam.getSelectedBuyerCompany();
		List<Integer> companyBuyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		Integer categoryId = osParam.getCategoryId();
		Integer sheetTypeId = osParam.getSheetTypeId();
		boolean isAllDatesView = osParam.isAllDatesView();
		String startDate = osParam.getStartDate();
		String endDate = osParam.getEndDate();
		Integer datesViewBuyerId = Integer.valueOf((String) osParam.getDatesViewBuyerID());
//		List<Integer> sellerId = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		
//		System.out.println("deliveryDate:[" + deliveryDate +"]");
//		System.out.println("selectedBuyerCompany:[" + selectedBuyerCompany +"]");
//		System.out.println("companyBuyerIds:[" + companyBuyerIds +"]");
//		System.out.println("categoryId:[" + categoryId +"]");
//		System.out.println("sheetTypeId:[" + sheetTypeId +"]");
//		System.out.println("allDatesView:[" + isAllDatesView + "]");
//		System.out.println("startDate:[" + startDate + "]");
//		System.out.println("endDate:[" + endDate + "]");
//		System.out.println("datesViewBuyerId:[" + datesViewBuyerId + "]");
		
		List<String> deliveryDates = new ArrayList<String>();
		if (isAllDatesView)
			deliveryDates = DateFormatter.getDateList(startDate, endDate);
		else
			deliveryDates.add(deliveryDate);
		
//		System.out.println(deliveryDates);
		
		List<Integer> selectedOrders = getSelectedOrders(request);
		if (selectedOrders.size() == 0) selectedOrders.add(999999999);
		List<AkadenSKU> allSkuObjs = billingSheetService.getDistinctSKUs(selectedOrders, categoryId,
				sheetTypeId, pageInfo.getStartRowNum(), pageInfo.getPageSize());

		List<AkadenSKU> noAkadenSkuObjs = new ArrayList<AkadenSKU>();
		List<AkadenSKU> allAkadenSkuObjs = new ArrayList<AkadenSKU>();
		for (AkadenSKU allSkuObj: allSkuObjs){
			if(allSkuObj.getTypeFlag().equals("0")){
				noAkadenSkuObjs.add(allSkuObj);
			}else{
				allAkadenSkuObjs.add(allSkuObj);
			}
		}
		SortingUtil.sortAkadenSKUs(user, noAkadenSkuObjs, Integer.valueOf(categoryId));
		List<AkadenSKU> sortedSkuObjs = new ArrayList<AkadenSKU>();
		sortedSkuObjs.addAll(noAkadenSkuObjs);
		sortedSkuObjs.addAll(allAkadenSkuObjs);
//		List<AkadenSKU> skuObjs = new ArrayList<AkadenSKU>();
//		int rowIdxStart = pageInfo.getStartRowNum() - 1;
//		int rowIdxEnd = rowIdxStart + pageInfo.getPageSize() - 1;
//		for (int i=rowIdxStart; i<=rowIdxEnd; i++) {
//			if (i == sortedSkuObjs.size()) break;
//			skuObjs.add(sortedSkuObjs.get(i));
//		}
//		pageInfo.setTotalRowNum(sortedSkuObjs.size());
		
		Map<Integer, Map<String, Map<Integer, BillingItem>>> sellerBillingItemMap = 
			new HashMap<Integer, Map<String, Map<Integer, BillingItem>>>();
//		try {
//			if (skuObjs.size() > 0){
				List<Integer> skuIds = OrderSheetUtil.getAkadenSkuIds(sortedSkuObjs);
				sellerBillingItemMap = billingSheetService.getBillingItemsBulk(selectedOrders, skuIds);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
				
		List<AkadenItem> akadenItems = billingSheetService.getAkadenItemsBulk(selectedOrders);
		
		Map<Integer, AkadenSKU> skuObjMap = new HashMap<Integer, AkadenSKU>();
		Set<String> sellerNameSet = new TreeSet<String>();
		for (AkadenSKU skuObj : sortedSkuObjs) {
			JSONObject json = new JSONObject();
			skuObjMap.put(skuObj.getSkuId(), skuObj);
			Map<String, Object> skuOrderMap = new HashMap<String, Object>();
			
//			String skuName = skuObj.getSkuName();
//			System.out.println("skuName:[" + skuName + "]");
//			String tempId = "0";
//			if(!skuObj.getTypeFlag().equals(tempId))
//				tempId = skuObj.getSkuId()+"_"+skuObj.getTypeFlag();
//			System.out.println("typeflag:[" + tempId + "]");

//			boolean isRed = false;
//			if(Integer.valueOf(skuObj.getTypeFlag())==2)
//				isRed = true;
			if(Integer.valueOf(skuObj.getTypeFlag())!=0)
				json.put("sku", "1");
			
			//skuOrderMap.put("typeflag", tempId);
			skuOrderMap.put("typeflag", skuObj.getTypeFlag());
			skuOrderMap.put("akadenSkuId", skuObj.getAkadenSkuId());
			skuOrderMap.put("sellerId", skuObj.getUser().getUserId());
			skuOrderMap.put("skuId", skuObj.getSkuId());
			
			if(skuObj.getProposedBy() != null) {
				json.put("sku", "1");
				skuOrderMap.put("companyname", skuObj.getProposedBy().getCompany().getShortName());
				skuOrderMap.put("sellername", skuObj.getProposedBy().getShortName());
			}else{
				skuOrderMap.put("companyname", skuObj.getUser().getCompany().getShortName());
				skuOrderMap.put("sellername", skuObj.getUser().getShortName());
			}
			
			skuOrderMap.put("groupname", skuObj.getSkuGroup().getSkuGroupId()); 
			skuOrderMap.put("marketname", StringUtil.nullToBlank(skuObj.getMarket()));
			skuOrderMap.put("column01", 	StringUtil.nullToBlank(skuObj.getColumn01()));
			skuOrderMap.put("column02", 	StringUtil.nullToBlank(skuObj.getColumn02()));
			skuOrderMap.put("column03", 	StringUtil.nullToBlank(skuObj.getColumn03()));
			skuOrderMap.put("column04", 	StringUtil.nullToBlank(skuObj.getColumn04()));
			skuOrderMap.put("column05", 	StringUtil.nullToBlank(skuObj.getColumn05()));
			skuOrderMap.put("skuname", StringUtil.nullToBlank(skuObj.getSkuName()));
			skuOrderMap.put("home", StringUtil.nullToBlank(skuObj.getLocation()));
			skuOrderMap.put("grade", StringUtil.nullToBlank(skuObj.getGrade()));
			skuOrderMap.put("clazzname", StringUtil.nullToBlank(skuObj.getClazz()));
			skuOrderMap.put("price1", NumberUtil.nullToZero((BigDecimal)skuObj.getPrice1()));
			skuOrderMap.put("price2", NumberUtil.nullToZero((BigDecimal)skuObj.getPrice2()));
			skuOrderMap.put("pricewotax", NumberUtil.nullToZero((BigDecimal)skuObj.getPriceWithoutTax()));
			skuOrderMap.put("pricewtax", skuObj.getPriceWithTax());
			skuOrderMap.put("packageqty", StringUtil.nullToBlank(skuObj.getPackageQuantity()));
			skuOrderMap.put("packagetype", StringUtil.nullToBlank(skuObj.getPackageType()));
			skuOrderMap.put("unitorder", skuObj.getOrderUnit().getOrderUnitId());
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
			
			if (isAllDatesView)
				//billingSheetService.loadOrderItemQuantities(skuOrderMap, deliveryDates, datesViewBuyerId, skuObj, json);
				this.prepareOrderAllDates(skuOrderMap, deliveryDates, datesViewBuyerId, skuObj,
						json, sellerBillingItemMap, akadenItems);
			else
				//billingSheetService.loadOrderItemQuantities(skuOrderMap, companyBuyerIds, deliveryDate, skuObj, json);
				this.prepareOrderAllBuyers(skuOrderMap, companyBuyerIds, deliveryDate, skuObj, json,
						sellerBillingItemMap, akadenItems);
		
			json.put("sellername", "1");
			skuOrderMap.put("lockflag", json.toString());
			skuOrderMaps.add(skuOrderMap);
			sellerNameSet.add(skuObj.getUser().getShortName());
		}
		
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_OBJ_MAP_PARAM, skuObjMap);
		
		return skuOrderMaps;
	}
	
	private void prepareOrderAllBuyers(Map<String, Object> skuOrderMap,	List<Integer> buyerIds,
			String deliveryDate, AkadenSKU skuObj, JSONObject json,
			Map<Integer, Map<String, Map<Integer, BillingItem>>> sellerBillingItemMap,
			List<AkadenItem> akadenItems)
				throws Exception {
		
		Integer skuId = skuObj.getSkuId();
		BigDecimal rowqty = new BigDecimal(0);
		String comments = "";
		int chkCtr = 0;
		if(skuObj.getTypeFlag().equals(BillingAkadenSkuIdConstants.BILLING_TYPE_FLAG)){
			
			Map<Integer, BillingItem> billingItemMap = new HashMap <Integer, BillingItem>();		
			billingItemMap = sellerBillingItemMap.get(skuId).get(deliveryDate);
			
			for (Integer buyerId : buyerIds) {
				BillingItem billingItem = null;
				try {
					billingItem = sellerBillingItemMap.get(skuId).get(deliveryDate).get(buyerId);
					for (AkadenItem akadenItem: akadenItems){
						if(akadenItem.getSku().getSkuId().equals(skuId) &&
								akadenItem.getOrder().getBuyerId().equals(buyerId)){
							billingItem = null;
							break;
						}
					}
					
				} catch (NullPointerException npe) {
					billingItem = null;
				}
				
				BigDecimal quantity = null;
//				String strOrderId = "";
				String strQuantity = "";
//				String strFinalizedBy = "";
				String strLockFlag = "0";
				String skuFlag = "0";
				
				if (billingItem != null) {
					
//					strOrderId = billingItem.getOrder().getOrderId().toString();
					comments = billingItem.getComments();
					if(billingItem.getQuantity() != null) {
						quantity = billingItem.getQuantity();
						strQuantity = quantity.toPlainString();
						rowqty = rowqty.add(quantity);
					}
					if(billingItem.getOrder().getOrderFinalizedBy() != null){
//						strFinalizedBy = billingItem.getOrder().getOrderFinalizedBy().toString();
						skuFlag = "1";
						strLockFlag = "1";
					}
					
					/*System.out.println("orderId:[" + strOrderId + "]");
					System.out.println("quantity:[" + quantity + "]");
					System.out.println("finalizedBy:[" + strFinalizedBy + "]");*/
					
				}
				else {
					strLockFlag = "1";
				}

				json.put("Q_" + buyerId.toString(), strLockFlag);
				json.put("V_" + buyerId.toString(), strLockFlag);
				if (!json.has("sku") && skuFlag.equals("1"))
					json.put("sku", skuFlag);
				skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
				skuOrderMap.put("C_" + buyerId.toString(), StringUtil.nullToBlank(comments));
			}
			if (chkCtr == buyerIds.size()) skuOrderMap.put("visall", "1");
			else skuOrderMap.put("visall", "0");
			
			skuOrderMap.put("comments", StringUtil.nullToBlank(comments));
			this.setSkuLockingB(billingItemMap, json);
			skuOrderMap.put("rowqty", rowqty);
			
		}
		else {
			
			Map<Integer, AkadenItem> akadenItemMap = new HashMap <Integer, AkadenItem>();
			//akadenItemMap = orderAkadenDao.getAkadenItemsMapOfSkuDate(deliveryDate, skuId, skuObj.getAkadenSkuId());
			akadenItemMap = akadenService.getAkadenItemsMapOfSkuDate(deliveryDate, skuId, skuObj.getAkadenSkuId());
			
			for (Integer buyerId : buyerIds) {
				AkadenItem akadenItem = akadenItemMap.get(buyerId);
				BigDecimal quantity = null;
//				String strOrderId = "";
				String strQuantity = "";
//				String strFinalizedBy = "";
				String strLockFlag = "0";
				String skuFlag = "0";
				
				if (akadenItem != null) {
					
//					strOrderId = akadenItem.getOrder().getOrderId().toString();
					comments = akadenItem.getComments();
					if(akadenItem.getQuantity() != null) {
						quantity = akadenItem.getQuantity();
						strQuantity = quantity.toPlainString();
						rowqty = rowqty.add(quantity);
					}

					skuFlag = "1";
					strLockFlag = "1";
					
					/*System.out.println("orderId:[" + strOrderId + "]");
					System.out.println("quantity:[" + quantity + "]");
					System.out.println("finalizedBy:[" + strFinalizedBy + "]");*/
					
				}
				else {
					strLockFlag = "1";
				}

				json.put("Q_" + buyerId.toString(), strLockFlag);
				json.put("V_" + buyerId.toString(), strLockFlag);
				if (!json.has("sku") && skuFlag.equals("1"))
					json.put("sku", skuFlag);
				skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
				skuOrderMap.put("C_" + buyerId.toString(), StringUtil.nullToBlank(comments));
			}
			if (chkCtr == buyerIds.size()) skuOrderMap.put("visall", "1");
			else skuOrderMap.put("visall", "0");
			
			skuOrderMap.put("comments", StringUtil.nullToBlank(comments));
			setSkuLockingA(akadenItemMap, json);
			skuOrderMap.put("rowqty", rowqty);
		}
	}
	
	private void prepareOrderAllDates(Map<String, Object> skuOrderMap, List<String> deliveryDates,
			Integer buyerId, AkadenSKU skuObj, JSONObject json,
			Map<Integer, Map<String, Map<Integer, BillingItem>>> sellerBillingItemMap,
			List<AkadenItem> akadenItems)
				throws Exception {
		
		Integer skuId = skuObj.getSkuId();
		BigDecimal rowqty = new BigDecimal(0);
		String comments = "";
		
		if(skuObj.getTypeFlag().equals(BillingAkadenSkuIdConstants.BILLING_TYPE_FLAG)){
			
			for (String deliveryDate : deliveryDates) {
				BillingItem billingItem = null;
				try {
					billingItem = sellerBillingItemMap.get(skuId).get(deliveryDate).get(buyerId);
					for (AkadenItem akadenItem: akadenItems){
						if(akadenItem.getSku().getSkuId().equals(skuId) &&
								akadenItem.getOrder().getDeliveryDate().equals(deliveryDate)){
							billingItem = null;
							break;
						}
					}
				} catch (NullPointerException npe) {
					billingItem = null;
				}
				
				//BillingItem billingItem = billingItemMap.get(deliveryDate);
				BigDecimal quantity = null;
				String strQuantity = "";
//				String strOrderId = "";
//				String strFinalizedBy = "";
				String strLockFlag = "0";
				
				if (billingItem != null) {
//					strOrderId = billingItem.getOrder().getOrderId().toString();
					comments = billingItem.getComments();
					if(billingItem.getQuantity() != null) {
						quantity = billingItem.getQuantity();
						strQuantity = quantity.toPlainString();
						rowqty = rowqty.add(quantity);
					}
					if(billingItem.getOrder().getOrderFinalizedBy() != null){
//						strFinalizedBy = billingItem.getOrder().getOrderFinalizedBy().toString();
						json.put("sku", 1);
						strLockFlag = "1";
					}
//					System.out.println("orderId:[" + strOrderId + "]");
//					System.out.println("quantity:[" + quantity + "]");
//					System.out.println("finalizedBy:[" + strFinalizedBy + "]");
				}
				else {
					strLockFlag = "1";
				}
				json.put("Q_" + deliveryDate, strLockFlag);
				skuOrderMap.put("Q_" + deliveryDate, strQuantity);
				skuOrderMap.put("C_" + deliveryDate, StringUtil.nullToBlank(comments));
			}

			skuOrderMap.put("comments", StringUtil.nullToBlank(comments));
			skuOrderMap.put("rowqty", rowqty);
			skuOrderMap.put("lockflag", json.toString());
			
			
		}else{
			Map<Integer, AkadenItem> AkadenItemMap = new HashMap <Integer, AkadenItem>();
			
			//if (!NumberUtil.isNullOrZero(skuObj.getSkuId())){
				AkadenItemMap = akadenService.getAkadenItemsMapOfSkuDates(deliveryDates, skuId, buyerId, skuObj.getAkadenSkuId());
			//}else{
				//AkadenItemMap = orderAkadenDao.getAkadenItemsMapOfSkuDates(deliveryDates, skuId, buyerId, null);
			//}
			
			for (String deliveryDate : deliveryDates) {
				AkadenItem akadenItem = AkadenItemMap.get(deliveryDate);
				BigDecimal quantity = null;
				String strQuantity = "";
//				String strOrderId = "";
//				String strFinalizedBy = "";
				String strLockFlag = "0";
				
				if (akadenItem != null) {
//					strOrderId = akadenItem.getOrder().getOrderId().toString();
					comments = akadenItem.getComments();
					if(akadenItem.getQuantity() != null) {
						quantity = akadenItem.getQuantity();
						strQuantity = quantity.toPlainString();
						rowqty = rowqty.add(quantity);
					}

					json.put("sku", 1);
					strLockFlag = "1";
//					if(akadenItem.getOrder().getOrderFinalizedBy() != null){
//						strFinalizedBy = akadenItem.getOrder().getOrderFinalizedBy().toString();
//						json.put("sku", 1);
//						strLockFlag = "1";
//					}
					
//					System.out.println("orderId:[" + strOrderId + "]");
//					System.out.println("quantity:[" + quantity + "]");
//					System.out.println("finalizedBy:[" + strFinalizedBy + "]");
				}
				else {
					strLockFlag = "1";
				}
				json.put("Q_" + deliveryDate, strLockFlag);
				skuOrderMap.put("Q_" + deliveryDate, strQuantity);
				skuOrderMap.put("C_" + deliveryDate, StringUtil.nullToBlank(comments));
			}
			skuOrderMap.put("comments", StringUtil.nullToBlank(comments));
			skuOrderMap.put("rowqty", rowqty);
			skuOrderMap.put("lockflag", json.toString());
		}
	}
	
	private void setSkuLockingB(Map<Integer, BillingItem> orderItemMap,
			JSONObject json) throws Exception {
		Set<Integer> keys = orderItemMap.keySet();
		Iterator<Integer> it = keys.iterator();
		while(it.hasNext()) {
			Integer key = it.next();
			BillingItem oi = orderItemMap.get(key);
			if (!NumberUtil.isNullOrZero(oi.getOrder().getOrderFinalizedBy())) {
				json.put("sku", "1");
			}
		}
	}
	
	private void setSkuLockingA(Map<Integer, AkadenItem> orderItemMap,
			JSONObject json) throws Exception {
		Set<Integer> keys = orderItemMap.keySet();
		Iterator<Integer> it = keys.iterator();
		while(it.hasNext()) {
			Integer key = it.next();
			AkadenItem oi = orderItemMap.get(key);
			if (!NumberUtil.isNullOrZero(oi.getOrder().getOrderFinalizedBy())) {
				json.put("sku", "1");
			}
		}
	}

	public void setBillingSheetService(BillingSheetService billingSheetService) {
		this.billingSheetService = billingSheetService;
	}

	@SuppressWarnings("unchecked")
	private List<Integer> getSelectedOrders(HttpServletRequest request) {
		return (List<Integer>) SessionHelper.getAttribute(request, SessionParamConstants.SELECTED_ORDERS_PARAM);
	}
	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
	
	@SuppressWarnings("unchecked")
	private Set<String> extracted(HttpServletRequest request) {
		return (Set<String>)SessionHelper.getAttribute(request, "sellerNameSet");
	}
	
	private boolean hasNoFinalizedAllocation(List<Order> allOrders) {
		
		boolean hasNoFinalizedAllocation = true;
		
		for (Order order: allOrders) {
			if (!NumberUtil.isNullOrZero(order.getAllocationFinalizedBy())) {
				hasNoFinalizedAllocation = false;
				break;
			}
		}
		
		return hasNoFinalizedAllocation;
	}
}
