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
 * May 18, 2010		gilwen		
 */
package com.freshremix.webapp.controller.allocationsheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.dao.UserDao;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.AllocationSheetService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author gilwen
 *
 */
public class SellerSelectController implements Controller {

	private SKUGroupService skuGroupService;
	private UserDao usersInfoDaos;
	private AllocationSheetService allocationSheetService;
	
	public void setAllocationSheetService(
			AllocationSheetService allocationSheetService) {
		this.allocationSheetService = allocationSheetService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("json");
		String shortName = request.getParameter("sellerName");
		shortName = new String(shortName.getBytes("ISO-8859-1"),"UTF-8");
		User userSession = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		//TODO: Use seller user id not shortname
		//		Use same impl in UOM
		User user = usersInfoDaos.getUserByShortName(shortName, userSession.getCompany().getCompanyId());
		OrderSheetParam param = (OrderSheetParam)SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM);
		List<Map<String, Object>> skuGroupList = skuGroupService.getSKUGroupListViaSellerSelect(user.getUserId(), param.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
		boolean isAllDatesView = param.isAllDatesView();
		List<Integer> companyBuyerIds = OrderSheetUtil.toList(param.getSelectedBuyerID());
		List<String> deliveryDates = new ArrayList<String>();
		String deliveryDate = param.getSelectedDate();
		String startDate = param.getStartDate();
		String endDate = param.getEndDate();
		JSONObject defaultLock = new JSONObject();
		
		if (isAllDatesView) {
			deliveryDates = DateFormatter.getDateList(startDate, endDate);
			Set<String> disabledDates = getDisabledDates(request, deliveryDates, user.getUserId(), Integer.valueOf(param.getDatesViewBuyerID()));
			lockQuantitiesDates(disabledDates, defaultLock);
		}
		else {
			deliveryDates.add(deliveryDate);
			Set<Integer> finalizedBuyers = getDisabledBuyers(request,deliveryDate, user.getUserId(), companyBuyerIds);
			lockQuantitiesBuyers(finalizedBuyers, defaultLock);
		}
		
		mav.addObject("sellerId", user.getUserId());
		mav.addObject("companyname", user.getCompany().getShortName());
		mav.addObject("groupname", skuGroupList);
		mav.addObject("lockflag", defaultLock.toString());
		
		return mav;
	}
	
	private void lockQuantitiesBuyers(Set<Integer> finalizedBuyers, JSONObject defaultLock) throws JSONException {
		for (Integer buyer : finalizedBuyers) {
			String field = "Q_" + buyer.toString();
			defaultLock.put(field, "1");
		}
	}

	private void lockQuantitiesDates(Set<String> disabledDates, JSONObject defaultLock) throws JSONException {
		for (String date : disabledDates) {
			String field = "Q_" + date;
			defaultLock.put(field, "1");
		}
	}

	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}

	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}
	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
	
	private Set<Integer> getDisabledBuyers(HttpServletRequest request, String deliveryDate, Integer sellerId, List<Integer> companyBuyerIds) {
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		DealingPattern dp = (DealingPattern)SessionHelper.getAttribute(request, SessionParamConstants.DEALING_PATTERN_PARAM);
		List<Integer> dpBuyers = getDealingPattern(deliveryDate, sellerId.toString(), dp);
		
		Map<String, Order> convertToOrderMap = OrderSheetUtil.convertToOrderMap(allOrders);
		Map<String, Set<Integer>> categorizedBuyers = this.categorizeBuyers(deliveryDate, sellerId, dpBuyers, convertToOrderMap);
		Set<Integer> finalizedBuyers = categorizedBuyers.get("finalized");
		Set<Integer> unfinalizedBuyerOrder = categorizedBuyers.get("unfinalized");
		
		//Set<Integer> finalizedBuyers = this.getFinalizedBuyers(deliveryDate, sellerId, dpBuyers, OrderSheetUtil.convertToOrderMap(allOrders));
		//Set<Integer> unfinalizedBuyerOrder = this.getUnfinalizedBuyerOrder(deliveryDate, sellerId, dpBuyers, OrderSheetUtil.convertToOrderMap(allOrders));;
		Set<Integer> buyersWithNoDealingPattern = this.getBuyersWithNoDealingPattern(dpBuyers, companyBuyerIds);
		finalizedBuyers.addAll(unfinalizedBuyerOrder);
		finalizedBuyers.addAll(buyersWithNoDealingPattern);
		return finalizedBuyers;
	}
	
	private Set<String> getDisabledDates(HttpServletRequest request, List<String> deliveryDates, Integer sellerId, Integer buyerId) {
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		Map<String, Order> orderMap = OrderSheetUtil.convertToOrderMap(allOrders);
		Set<String> disabledDates = this.getDisabledDates(deliveryDates, sellerId, buyerId, orderMap);;
		return disabledDates;
	}
	
	private List<Integer> getDealingPattern(String deliveryDate, String sellerId, DealingPattern dealingPattern) {
		Map<String, List<Integer>> dp = dealingPattern.getSellerToBuyerDPMap().get(deliveryDate);
		return dp.get(sellerId);
	}
	
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
	
	private Set<Integer> getBuyersWithNoDealingPattern(List<Integer> dpBuyers, List<Integer> selectedBuyers) {
		Set<Integer> buyerWithNoDP = new TreeSet<Integer>();
		for (Integer selectedBuyer : selectedBuyers) {
			if (!dpBuyers.contains(selectedBuyer))
				buyerWithNoDP.add(selectedBuyer);
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
			String key = deliveryDate + "_" + buyerId + "_" + sellerId;
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
}