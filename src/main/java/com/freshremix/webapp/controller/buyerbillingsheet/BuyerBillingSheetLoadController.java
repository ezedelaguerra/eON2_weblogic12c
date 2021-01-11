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
 * Jun 29, 2010		Pammie		
 */
package com.freshremix.webapp.controller.buyerbillingsheet;

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
import com.freshremix.util.StringUtil;

/**
 * @author Pammie
 *
 */
public class BuyerBillingSheetLoadController implements Controller, InitializingBean{

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
		
		List<Map<String, Object>> skuOrderList = this.loadOrderItems(request, user, osParam, tableParam.getPageInfo());
				
		// set list of sku group
		String skuGroupList = skuGroupService.getSKUGroupList(user.getUserId().intValue(), osParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);

		GrandTotalPrices grandTotalPrices = billingSheetService.computeTotalPricesOnDisplay(skuOrderList);
		BigDecimal totalPriceWOTax = grandTotalPrices.getPriceWithoutTax();
		BigDecimal totalPriceWTax = grandTotalPrices.getPriceWithTax();
		
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);

		String confirmMsg = "";
		if (hasNoFinalizedSellerBilling(allOrders)) {
			confirmMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
				getMessage("billingsheet.buyer.noSkuOrderList", eonLocale.getLocale()));
		}
		
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		List<Order> selectedOrders = orderSheetService.getSelectedOrders(allOrdersMap, osParam);
		JSONObject buttonFlags = getButtonFlags(selectedOrders);

		SessionHelper.getAttribute(request, SessionParamConstants.SELECTED_ORDERS_PARAM);
		Set<String> sellernameSet = extracted(request);
		
		ModelAndView mav = new ModelAndView("json");
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
		
		Order order = orderSheetService.combineOrders(selectedOrders);
		JSONObject flags = new JSONObject();
		
		// enable/disble
		if (selectedOrders.size() == 0) {
			flags.put("btnSave", 0);
			flags.put("btnFinalize", 1);
		} else {
			// first entry
			if (NumberUtil.isNullOrZero(order.getOrderSavedBy())) {
				flags.put("btnSave", 0);
			}
			// saved
			if (!NumberUtil.isNullOrZero(order.getOrderSavedBy()) && NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) {
				flags.put("btnSave", 0);
				flags.put("btnFinalize", 0);
			}
			// finalized
			if (!NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) {
				flags.put("btnFinalize", 1);
			}
		}
		
		return flags;
	}
		
	private List<Map<String, Object>> loadOrderItems(HttpServletRequest request, User user,
			OrderSheetParam osParam, PageInfo pageInfo) throws Exception {
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		
		String deliveryDate = osParam.getSelectedDate();
		List<Integer> companyBuyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		Integer categoryId = osParam.getCategoryId();
//		Integer sheetTypeId = osParam.getSheetTypeId();
		boolean isAllDatesView = osParam.isAllDatesView();
		String startDate = osParam.getStartDate();
		String endDate = osParam.getEndDate();
		Integer datesViewBuyerId = Integer.valueOf((String) osParam.getDatesViewBuyerID());
		
		List<String> deliveryDates = new ArrayList<String>();
		if (isAllDatesView)
			deliveryDates = DateFormatter.getDateList(startDate, endDate);
		else
			deliveryDates.add(deliveryDate);
		
//		System.out.println(deliveryDates);
		
		List<Integer> selectedOrders = getSelectedOrders(request);
		if (selectedOrders.size() == 0) selectedOrders.add(999999999);
		List<AkadenSKU> allSkuObjs = billingSheetService.getDistinctSKUBA(selectedOrders, categoryId);
		
		List<AkadenSKU> skuObjs = new ArrayList<AkadenSKU>();
		int rowIdxStart = pageInfo.getStartRowNum() - 1;
		int rowIdxEnd = rowIdxStart + pageInfo.getPageSize() - 1;
		for (int i=rowIdxStart; i<=rowIdxEnd; i++) {
			if (i == allSkuObjs.size()) break;
			skuObjs.add(allSkuObjs.get(i));
		}
		pageInfo.setTotalRowNum(allSkuObjs.size());
		
		Map<Integer, Map<String, Map<Integer, BillingItem>>> buyerBillingItemMap = 
			new HashMap<Integer, Map<String, Map<Integer, BillingItem>>>();
		try {
			if (skuObjs.size() > 0)
				buyerBillingItemMap = billingSheetService.getBillingItemsBulk(selectedOrders,
						OrderSheetUtil.getAkadenSkuIds(skuObjs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<Integer, AkadenSKU> skuObjMap = new HashMap<Integer, AkadenSKU>();
		Set<String> sellerNameSet = new TreeSet<String>();
		for (AkadenSKU skuObj : skuObjs) {
			JSONObject json = new JSONObject();
			skuObjMap.put(skuObj.getSkuId(), skuObj);
			Map<String, Object> skuOrderMap = new HashMap<String, Object>();
			
//			String skuName = skuObj.getSkuName();
//			System.out.println("skuName:[" + skuName + "]");
			if(Integer.valueOf(skuObj.getTypeFlag())!=0)
				json.put("sku", "1");
			
			skuOrderMap.put("sellerId", skuObj.getUser().getUserId());
			
			if(skuObj.getProposedBy() != null) {
				skuOrderMap.put("companyname", skuObj.getProposedBy().getCompany().getShortName());
				skuOrderMap.put("sellername", skuObj.getProposedBy().getShortName());
			}else{
				skuOrderMap.put("companyname", skuObj.getUser().getCompany().getShortName());
				skuOrderMap.put("sellername", skuObj.getUser().getShortName());
			}
			
			skuOrderMap.put("groupname", skuObj.getSkuGroup().getSkuGroupId()); 
			skuOrderMap.put("marketname", skuObj.getMarket());
			skuOrderMap.put("column01", 	StringUtil.nullToBlank(skuObj.getColumn01()));
			skuOrderMap.put("column02", 	StringUtil.nullToBlank(skuObj.getColumn02()));
			skuOrderMap.put("column03", 	StringUtil.nullToBlank(skuObj.getColumn03()));
			skuOrderMap.put("column04", 	StringUtil.nullToBlank(skuObj.getColumn04()));
			skuOrderMap.put("column05", 	StringUtil.nullToBlank(skuObj.getColumn05()));
			skuOrderMap.put("skuname", skuObj.getSkuName());
			skuOrderMap.put("home", skuObj.getLocation());
			skuOrderMap.put("grade", skuObj.getGrade());
			skuOrderMap.put("clazzname", skuObj.getClazz());
			skuOrderMap.put("price2", skuObj.getPrice2());
			skuOrderMap.put("pricewotax", skuObj.getPriceWithoutTax());
			skuOrderMap.put("pricewtax", skuObj.getPriceWithTax());
			skuOrderMap.put("packageqty", skuObj.getPackageQuantity());
			skuOrderMap.put("packagetype", skuObj.getPackageType());
			skuOrderMap.put("unitorder", skuObj.getOrderUnit().getOrderUnitId());
			skuOrderMap.put("B_purchasePrice", StringUtil.nullToBlank((BigDecimal)skuObj.getPurchasePrice()));
			skuOrderMap.put("B_sellingPrice", StringUtil.nullToBlank((BigDecimal)skuObj.getSellingPrice()));
			skuOrderMap.put("B_sellingUom", skuObj.getSellingUom() != null ? skuObj.getSellingUom().getOrderUnitId() : "");
			skuOrderMap.put("B_skuComments", StringUtil.nullToBlank(skuObj.getSkuComment()));
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
				this.prepareOrderAllDates(skuOrderMap, deliveryDates, datesViewBuyerId, skuObj,
						json, buyerBillingItemMap);
			else
				this.prepareOrderAllBuyers(skuOrderMap, companyBuyerIds, deliveryDate, skuObj, json,
					buyerBillingItemMap);
			
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
			Map<Integer, Map<String, Map<Integer, BillingItem>>> sellerBillingItemMap)
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
				} catch (NullPointerException npe) {
					billingItem = null;
				}
				
				BigDecimal quantity = null;
				String strQuantity = "";
				String strLockFlag = "0";
				String skuFlag = "0";
				
				if (billingItem != null) {
					
					comments = billingItem.getComments();
					if(billingItem.getQuantity() != null) {
						quantity = billingItem.getQuantity();
						strQuantity = quantity.toPlainString();
						rowqty = rowqty.add(quantity);
					}
					if(billingItem.getOrder().getOrderFinalizedBy() != null){
						skuFlag = "1";
						strLockFlag = "1";
					}
					
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
			akadenItemMap = akadenService.getAkadenItemsMapOfSkuDate(deliveryDate, skuId, skuObj.getAkadenSkuId());
			
			for (Integer buyerId : buyerIds) {
				AkadenItem akadenItem = akadenItemMap.get(buyerId);
				BigDecimal quantity = null;
				String strQuantity = "";
				String strLockFlag = "0";
				String skuFlag = "0";
				
				if (akadenItem != null) {
					
					comments = akadenItem.getComments();
					if(akadenItem.getQuantity() != null) {
						quantity = akadenItem.getQuantity();
						strQuantity = quantity.toPlainString();
						rowqty = rowqty.add(quantity);
					}

					skuFlag = "1";
					strLockFlag = "1";
					
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
			Map<Integer, Map<String, Map<Integer, BillingItem>>> sellerBillingItemMap)
				throws Exception {
		
		Integer skuId = skuObj.getSkuId();
		BigDecimal rowqty = new BigDecimal(0);
		String comments = "";
		
		if(skuObj.getTypeFlag().equals(BillingAkadenSkuIdConstants.BILLING_TYPE_FLAG)){
			
			for (String deliveryDate : deliveryDates) {
				BillingItem billingItem = null;
				try {
					billingItem = sellerBillingItemMap.get(skuId).get(deliveryDate).get(buyerId);
				} catch (NullPointerException npe) {
					billingItem = null;
				}
				
				BigDecimal quantity = null;
				String strQuantity = "";
				String strLockFlag = "0";
				
				if (billingItem != null) {
					comments = billingItem.getComments();
					if(billingItem.getQuantity() != null) {
						quantity = billingItem.getQuantity();
						strQuantity = quantity.toPlainString();
						rowqty = rowqty.add(quantity);
					}
					if(billingItem.getOrder().getOrderFinalizedBy() != null){
						json.put("sku", 1);
						strLockFlag = "1";
					}
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
			AkadenItemMap = akadenService.getAkadenItemsMapOfSkuDates(deliveryDates, skuId, buyerId, skuObj.getAkadenSkuId());
			
			for (String deliveryDate : deliveryDates) {
				AkadenItem akadenItem = AkadenItemMap.get(deliveryDate);
				BigDecimal quantity = null;
				String strQuantity = "";
				String strLockFlag = "0";
				
				if (akadenItem != null) {
					comments = akadenItem.getComments();
					if(akadenItem.getQuantity() != null) {
						quantity = akadenItem.getQuantity();
						strQuantity = quantity.toPlainString();
						rowqty = rowqty.add(quantity);
					}

					json.put("sku", 1);
					strLockFlag = "1";
					
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
	
	private boolean hasNoFinalizedSellerBilling(List<Order> allOrders) {
		
		boolean hasNoFinalizedAllocation = true;
		
		for (Order order: allOrders) {
			if (!NumberUtil.isNullOrZero(order.getBillingFinalizedBy())) {
				hasNoFinalizedAllocation = false;
				break;
			}
		}
		
		return hasNoFinalizedAllocation;
	}
}
