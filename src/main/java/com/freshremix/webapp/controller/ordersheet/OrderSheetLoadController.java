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
 * date		name		version		changes
 * ------------------------------------------------------------------------------		
 * 20121121	melissa		v14			Redmine 1029 - seller can change quantity in seller order sheet after Buyer Order Sheet finalize
 */

package com.freshremix.webapp.controller.ordersheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SKU;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.service.CategoryService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.service.SheetDataService;
import com.freshremix.ui.model.ProfitInfo;
import com.freshremix.ui.model.TableParameter;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.TaxUtil;

public class OrderSheetLoadController implements Controller {

	private OrderSheetService orderSheetService;
	private SKUGroupService skuGroupService;
	private CategoryService categoryService;
	
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
		//List<Map<String, Object>> skuOrderList = this.loadOrderItems(request, user, osParam, tableParam.getPageInfo());
		
		List<Integer> selectedOrdersFromSession = getIntegerListFromSession(request, SessionParamConstants.SELECTED_ORDERS_PARAM);

		Map<String, List<Integer>> mapOfMembersByDate = null;
		
		if (RolesUtil.isUserRoleSellerAdmin(user)) {
			mapOfMembersByDate = getMapOfMembersByDateFromSession(request);
		}
		
		Map<String, Object> loadOrderItems = orderSheetService.loadOrderItems(selectedOrdersFromSession, user, osParam, tableParam.getPageInfo(), mapOfMembersByDate);
		List<Map<String, Object>> skuOrderList = (List<Map<String, Object>>)loadOrderItems.get("skuOrderMaps");
		Set<String> sellerNameSet =(Set<String>)loadOrderItems.get("sellerNameSet");
		Map<Integer, SKU> skuObjMap = (Map<Integer, SKU>) loadOrderItems.get("skuObjMap");
		
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_OBJ_MAP_PARAM, skuObjMap);
		SessionHelper.setAttribute(request, "sellerNameSet", sellerNameSet);
		
		// set list of sku group
		String skuGroupList = skuGroupService.getSKUGroupList(user.getUserId().intValue(), osParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
		
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		List<Order> selectedOrders = orderSheetService.getSelectedOrders(allOrdersMap, osParam);
		JSONObject buttonFlags = getButtonFlags(allOrders, selectedOrders);
		
		SessionHelper.getAttribute(request, SessionParamConstants.SELECTED_ORDERS_PARAM);
		Set<String> sellernameSet = extracted(request);
		//rhoda redmine 1113 start
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER) && sellernameSet.isEmpty()){
			sellernameSet.add(user.getShortName());
		}
		//rhoda redmine 1113 end
		
		// Total Prices
		List<Integer> categoryList = new ArrayList<Integer>();
		List<String> deliveryDates = null; 
		List<Integer> buyerId = null;
		categoryList.add(osParam.getCategoryId());
		
		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
		if (osParam.isAllDatesView()) {
			deliveryDates = new ArrayList<String>(dateList);
			buyerId = new ArrayList<Integer>();
			buyerId.add(Integer.parseInt(osParam.getDatesViewBuyerID()));
		}
		else {
			deliveryDates = new ArrayList<String>();
			deliveryDates.add(osParam.getSelectedDate());
			buyerId = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		}
		
		//get the list of valid members
		List<Integer> selectedSellerList = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		
		
		//add only those with valid seller members
		ProfitInfo pi = orderSheetService.getProfitInfo(user, deliveryDates,
				selectedSellerList, mapOfMembersByDate, buyerId, categoryList,
				TaxUtil.getTAX_RATE().doubleValue());
		
		// Grand Totals
		List<UsersCategory> allCategory = categoryService.getCategoryList(user, osParam);
		List<Integer> allCategoryIds = new ArrayList<Integer>();
		
		for (UsersCategory _cat : allCategory) {
			allCategoryIds.add(_cat.getCategoryId());
		}
		
		ProfitInfo pi2 = orderSheetService.getProfitInfo(user, 
				dateList, selectedSellerList,
				mapOfMembersByDate,
				OrderSheetUtil.toList(osParam.getSelectedBuyerID()),
				allCategoryIds, TaxUtil.getTAX_RATE().doubleValue());
		
		ModelAndView mav = new ModelAndView("json");
		//mav.addObject("data", orderList);
		mav.addObject("data", skuOrderList);
		mav.addObject("pageInfo", tableParam.getPageInfo());
		mav.addObject("skuGroup", skuGroupList);
		mav.addObject("totals", pi);
		mav.addObject("grandTotals", pi2);
		mav.addObject("buttonFlags", buttonFlags.toString());
		mav.addObject("sellernameSet", sellernameSet);

		return mav;
	}
	
	private JSONObject getButtonFlags(List<Order> allOrders, List<Order> selectedOrders) throws JSONException {
		
		JSONObject flags = new JSONObject();
		// 3 buttons save, publish, finalize 
		// for weekly
		Order allOrder = orderSheetService.combineOrders(allOrders);
		Order pubOrder = orderSheetService.combineSelectedOrders(allOrders);

		flags.put("btnSave", 0);
		flags.put("btnPublish", 1);
		flags.put("btnFinalize", 1);
		flags.put("btnUnpublish", 1);
		flags.put("btnUnfinalize", 1);
		
		if (allOrders.size() == 0) {
			flags.put("btnSave", 0);
			flags.put("btnPublish", 1);
			flags.put("btnFinalize", 1);
		} else {
			if (!NumberUtil.isNullOrZero(allOrder.getOrderPublishedBy()))
				flags.put("btnPublish", 0);
			if (!NumberUtil.isNullOrZero(allOrder.getOrderFinalizedBy())) {
				flags.put("btnFinalize", 0);
			}
			if (!NumberUtil.isNullOrZero(allOrder.getAllocationFinalizedBy()))
				flags.put("btnUnfinalize", 0);
			
			if (!NumberUtil.isNullOrZero(pubOrder.getOrderFinalizedBy())) {
				flags.put("btnSave", 1);
				flags.put("btnPublish", 1);
				flags.put("btnFinalize", 1);
			}
			if (!NumberUtil.isNullOrZero(pubOrder.getOrderPublishedBy())) {
				flags.put("btnPublish", 1);
			}
			if (!NumberUtil.isNullOrZero(pubOrder.getAllocationFinalizedBy())) 
				flags.put("btnUnfinalize", 1);
		}
		// 2 buttons unpublish, unfinalize
		// for view/page
		Order selectedOrder = orderSheetService.combineSelectedOrders(selectedOrders);
		
		if (selectedOrders.size() == 0) {
			flags.put("btnUnpublish", 1);
			flags.put("btnUnfinalize", 1);
		} else {
			if (NumberUtil.isNullOrZero(allOrder.getOrderPublishedBy()))
				flags.put("btnUnpublish", 1);
			if (!NumberUtil.isNullOrZero(selectedOrder.getOrderPublishedBy()) 
				&& NumberUtil.isNullOrZero(selectedOrder.getOrderFinalizedBy())) {
					flags.put("btnUnpublish", 0);
					flags.put("btnUnfinalize", 1);
			}
			if (!NumberUtil.isNullOrZero(selectedOrder.getOrderFinalizedBy())) {
				flags.put("btnUnpublish", 1);
				flags.put("btnUnfinalize", 0);
			}
			// locked
			if (!NumberUtil.isNullOrZero(selectedOrder.getOrderLockedBy()))
				flags.put("btnUnpublish", 1);

			if (allOrders.size() != 0 
				&& !NumberUtil.isNullOrZero(pubOrder.getOrderSavedBy())
				&& !NumberUtil.isNullOrZero(selectedOrder.getOrderFinalizedBy())) {
				flags.put("btnSave", 1);
			}
			if (!NumberUtil.isNullOrZero(selectedOrder.getAllocationFinalizedBy())) 
				flags.put("btnUnfinalize", 1);
		}
		
		return flags;
	}
	
	

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}

	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
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

	@SuppressWarnings("unchecked")
	private Map<String, List<Integer>> getMapOfMembersByDateFromSession(
			HttpServletRequest request) {
		return (Map<String, List<Integer>>) SessionHelper.getAttribute(request, SessionParamConstants.MAP_OF_MEMBERS_BY_DATE);
	}

	


	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
}
