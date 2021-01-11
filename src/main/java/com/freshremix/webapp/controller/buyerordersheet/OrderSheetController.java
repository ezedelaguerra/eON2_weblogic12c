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
 * 20120724	gilwen		v11			Redmine 797 - SellerAdmin can select seller who don't have category
 */

package com.freshremix.webapp.controller.buyerordersheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.BuyerOrderSheetService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author raquino
 *
 */
public class OrderSheetController extends SimpleFormController {

	private BuyerOrderSheetService buyerOrderSheetService;
	private SKUGroupService skuGroupService;
	private DealingPatternService dealingPatternService;	
	private OrderSheetService orderSheetService;
	private OrderUnitService orderUnitService;

	public void setBuyerOrderSheetService(
			BuyerOrderSheetService buyerOrderSheetService) {
		this.buyerOrderSheetService = buyerOrderSheetService;
	}

	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}

	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}
		
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	
	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		Map<String,Object> model = new HashMap<String,Object>();
//		if (SessionHelper.isSessionExpired(request)) {
//			model.put("isSessionExpired", Boolean.TRUE);
//		} else {
			
			OrderSheetParam orderSheetParam = (OrderSheetParam) command;
			User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
			String enableBAPublish = null;
			boolean isBAPublished = buyerOrderSheetService.isBuyerPublished(user);
			if (isBAPublished) 
				enableBAPublish=String.valueOf(isBAPublished);
			
			if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)){
				orderSheetParam.setSheetTypeId(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET);
				orderSheetParam.setCsvSheetTypeID(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET);
			}else{
				orderSheetParam.setSheetTypeId(SheetTypeConstants.BUYER_ORDER_SHEET);
				//Sheet Type used for CSV Download
				orderSheetParam.setCsvSheetTypeID(SheetTypeConstants.BUYER_ORDER_SHEET);
			}
			
			if (orderSheetParam.getSelectedBuyerID() == null) {
				orderSheetParam.setSelectedBuyerID(user.getUserId().toString());
				orderSheetParam.setSelectedBuyerCompany(user.getCompany().getCompanyId().toString());
				orderSheetParam.setDatesViewBuyerID(user.getUserId().toString());
			}
			String selectedBuyerID = orderSheetParam.getSelectedBuyerID();
			

			if (orderSheetParam.getEndDate().length() != 8)
				orderSheetParam.setEndDate(orderSheetParam.getStartDate());

			SessionHelper.setAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM, orderSheetParam);
			String selectedDate = orderSheetParam.getSelectedDate();
			SessionHelper.setAttribute(request, SessionParamConstants.DELIVERY_DATE_PARAM, selectedDate);
	
			/* get parameters from filter page to get All Orders*/
			String selectedSellerID = orderSheetParam.getSelectedSellerID();
			List<Integer> sellerIds = OrderSheetUtil.toList(selectedSellerID);
			String startDate = orderSheetParam.getStartDate();
			String endDate = orderSheetParam.getEndDate();
			List<Integer> buyerIds = OrderSheetUtil.toList(selectedBuyerID);
			List<String> dateList = DateFormatter.getDateList(startDate, endDate);

			
			
//			System.out.println("startDate:[" + startDate + "]");
//			System.out.println("endDate:[" + endDate + "]");
//			System.out.println("buyerIds:[" + buyerIds + "]");
//			
//			System.out.println("allDatesView:[" + orderSheetParam.isAllDatesView() + "]");
//			System.out.println("datesViewBuyerId:[" + orderSheetParam.getDatesViewBuyerID() + "]");
			
			List<Order> allOrders = null;
			allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);

			if (orderSheetParam.isCheckDBOrder() || allOrders==null) {
				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN))
					allOrders = buyerOrderSheetService.getPublishedOrders(buyerIds, dateList, sellerIds, null, null);
				else
					allOrders = buyerOrderSheetService.getPublishedOrders(buyerIds, dateList, sellerIds, user.getUserId(), enableBAPublish);
				SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
//				System.out.println("allOrders set:[" + allOrders.size()+ "]");
			}
			
			Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
			List<Integer> selectedOrderIds = orderSheetService.getSelectedOrderIds(allOrdersMap, orderSheetParam);
			List<Order> selectedOrders = orderSheetService.getSelectedOrders(allOrdersMap, orderSheetParam);

			// set list of sku group
			String skuGroupList = skuGroupService.getSKUGroupList(user.getUserId().intValue(), orderSheetParam.getCategoryId());
			SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
			
			// set order unit
			String orderUnit = orderUnitService.getOrderUnitByCategoryId(orderSheetParam.getCategoryId());
			SessionHelper.setAttribute(request, SessionParamConstants.ORDER_UNIT_LIST_PARAM, orderUnit);

			// set selling uom
			String sellingUom = orderUnitService.getSellingUomList(orderSheetParam.getCategoryId());
			SessionHelper.setAttribute(request, SessionParamConstants.SELLING_UOM_LIST_PARAM, sellingUom);
			
			// set order unit renderer
			String orderUnitRenderer = orderUnitService.getOrderUnitRenderer(orderSheetParam.getCategoryId());
			SessionHelper.setAttribute(request, SessionParamConstants.ORDER_UNIT_RENDERER_PARAM, orderUnitRenderer);
			
			// set selling uom renderer
			String sellUomRenderer = orderUnitService.getSellingUomRenderer(orderSheetParam.getCategoryId());
			SessionHelper.setAttribute(request, SessionParamConstants.SELLING_UOM_RENDERER_PARAM, sellUomRenderer);

			// set default order unit id
			Integer orderUnitId = orderUnitService.getDefaultOrderUnit(orderSheetParam.getCategoryId());
			SessionHelper.setAttribute(request, SessionParamConstants.DEFAULT_ORDER_UNIT_PARAM, orderUnitId);
			
			//set seller to model (sku group)
			model.put("response", new FilteredIDs(user.getUserId().toString(), user.getName()));
			
			

			List<Integer> sellers = new ArrayList<Integer>();
			
			if (RolesUtil.isUserRoleSeller(user)) { 
				sellers.add(user.getUserId());
				SessionHelper.setAttribute(request, SessionParamConstants.IS_ADMIN_PARAM, false);

			}
			else {
				//List<Integer> sellerId = OrderSheetUtil.toList(selectedSellerID);
				sellers.addAll(sellerIds);
				SessionHelper.setAttribute(request, SessionParamConstants.IS_ADMIN_PARAM, true);
				OrderSheetUtil.setAdminMembersByDateToSession(
						dealingPatternService,
						DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER,
						request, user, startDate, endDate);
			}
			
			
			
			// set sku group renderer
			String skuGroupRenderer = skuGroupService.getSKUGroupRenderer(sellers, orderSheetParam.getCategoryId());
			SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_RENDERER_PARAM, skuGroupRenderer);
			
			//String sellerNameList = orderSheetService.getSellerNames(seller);
			StringBuffer sellerName = new StringBuffer();
			StringBuffer sellerNameRenderer = new StringBuffer();
			// ENHANCEMENT START 20120724: Lele - Redmine 797
			//orderSheetService.setSellerNameAndRenderer(seller, sellerName, sellerNameRenderer);
			orderSheetService.setSellerNameAndRenderer(orderSheetService.filterSellerByCategoryId(sellers, orderSheetParam.getCategoryId()),
					sellerName,
					sellerNameRenderer);
			// ENHANCEMENT END 20120724: Lele
			SessionHelper.setAttribute(request, SessionParamConstants.SELLER_NAME_LIST_PARAM, sellerName.toString());
			SessionHelper.setAttribute(request, SessionParamConstants.SELLER_NAME_RENDERER_PARAM, sellerNameRenderer.toString());
						
			// create seller to buyer DP map
			Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap = dealingPatternService.getSellerToBuyerDPMap(sellers, orderSheetParam.getStartDate(), orderSheetParam.getEndDate());
			// create buyer to seller DP map
			Map<String, Map<String, List<Integer>>> buyerToSellerDPMap = dealingPatternService.getBuyerToSellerDPMap(sellerToBuyerDPMap);
			DealingPattern dp = new DealingPattern(sellerToBuyerDPMap,buyerToSellerDPMap);
			SessionHelper.setAttribute(request, SessionParamConstants.DEALING_PATTERN_PARAM, dp);
			
			List<Integer> memberUserIds = null;
			if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN))
				memberUserIds = this.getBuyerIdsOfBuyerAdmin(user.getUserId(),
						orderSheetParam.getSelectedDate());
//			
//			/* create quantity columns */
//			if (!orderSheetParam.isAllDatesView())
//				//create buyer columns
//				OrderSheetUtil.createCompanyColumns(request, orderSheetParam.getSelectedBuyerCompany(),
//						orderSheetParam.getSelectedBuyerID(), orderSheetParam.getSelectedSellerID(),
//						orderSheetParam.getSelectedDate(), memberUserIds, sellerToBuyerDPMap);
//			else {
//				//create buyer columns
//				OrderSheetUtil.createCompanyColumns(request, orderSheetParam.getSelectedBuyerCompany(),
//						orderSheetParam.getSelectedBuyerID(), orderSheetParam.getSelectedSellerID(),
//						orderSheetParam.getSelectedDate(), memberUserIds, sellerToBuyerDPMap);
//				//create date columns
//				OrderSheetUtil.createDateColumns(request, orderSheetParam, null, null);
//				SessionHelper.setAttribute(request, "oneBuyerId", orderSheetParam.getDatesViewBuyerID());
//			}

			if (orderSheetParam.isAllDatesView()){
				//create date columns
				OrderSheetUtil.createDateColumns(request, orderSheetParam, selectedOrders, dp);
				SessionHelper.setAttribute(request, "oneBuyerId", orderSheetParam.getDatesViewBuyerID());
			}else {
				//create buyer columns
				String selectedBuyerCompany = orderSheetParam.getSelectedBuyerCompany();
				List<Integer> buyers = OrderSheetUtil.toList(selectedBuyerID);
				List<Integer> validBuyerMemberList = new ArrayList<Integer>();
				
				if (RolesUtil.isUserRoleBuyer(user)) {
					validBuyerMemberList = buyers;
				} else {
					Map<String, List<Integer>> mapOfMembersByDate = getMapOfMembersByDateFromSession(request);
					List<Integer>  tmpvalidBuyerMemberList = mapOfMembersByDate.get(selectedDate);
					for (Integer integer : buyerIds) {
						if(tmpvalidBuyerMemberList.contains(integer)){
							validBuyerMemberList.add(integer);
						}
					}
				}

				OrderSheetUtil.createCompanyColumns(request, selectedBuyerCompany,
						validBuyerMemberList, sellers,
						selectedDate, null, sellerToBuyerDPMap,
						selectedOrders, buyerToSellerDPMap);
				
				
			}
			
			//List<Order> selectedOrders = orderSheetService.getSelectedOrders(allOrdersMap, orderSheetParam);
			
			SessionHelper.setAttribute(request, SessionParamConstants.SELECTED_ORDERS_PARAM, selectedOrderIds);
			
			//System.out.println(allOrdersMap);
			// Get set of order ids (from all Orders) based on selected display option and set to session
			//this.selectOrdersAndSetButtonFlag(request, orderSheetParam, allOrdersMap, user);
			
//			List<Integer> companySellerIds = OrderSheetUtil.toList(orderSheetParam.getSelectedSellerCompany());
//			String skuGroupList = skuGroupService.getAllSKUGroup(companySellerIds, orderSheetParam.getCategoryId());
//			SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
			
//		}
		
		if (errors.hasErrors()) 
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json",model);
	}
	
//	/**
//	 * This is will get set of order ids from all Orders based on display option selection and set to session
//	 * This will also set button flags
//	 *
//	 */	
//	private void selectOrdersAndSetButtonFlag(HttpServletRequest request, OrderSheetParam osp,
//			Map<String,Order> allOrdersMap, User user) {
//		
//		Boolean saveButtonEnabled = Boolean.FALSE;
//		Boolean lockButtonEnabled = Boolean.FALSE;
//		Boolean unlockButtonEnabled = Boolean.FALSE;
//		List<String> dateList = null;
//		List<Integer> buyerIds = null;
//		List<Integer> sellerIds = OrderSheetUtil.toList(osp.getSelectedSellerID());
//		
//		if (osp.isAllDatesView()) {
//			dateList = DateFormatter.getDateList(osp.getStartDate(), osp.getEndDate());
//			buyerIds = OrderSheetUtil.toList(osp.getDatesViewBuyerID());
//		}
//		else {
//			dateList = DateFormatter.getDateList(osp.getSelectedDate(), osp.getSelectedDate());
//			buyerIds = OrderSheetUtil.toList(osp.getSelectedBuyerID());
//		}
//		System.out.println("dateList:[" + dateList +"]");
//		System.out.println("buyerIds:[" + buyerIds +"]");
//		System.out.println("sellerIds:[" + sellerIds +"]");
//		
//		List<Integer> selectedOrders = new ArrayList<Integer>();
//		List<Integer> notFinalizedOrders = new ArrayList<Integer>();
//		int lockedCount = 0;
//		for (String thisDate : dateList) {
//			for (Integer buyerId : buyerIds) {
//				for (Integer sellerId : sellerIds) {
//					String key = thisDate + "_" + buyerId.toString() + "_" + sellerId.toString();
//					Order thisOrder = allOrdersMap.get(key);
//					
//					if (thisOrder != null) {
//						selectedOrders.add(thisOrder.getOrderId());
//
//						if (thisOrder.getOrderFinalizedBy() != null) { //skip if finalized
//							System.out.println("order finalized:[" + key + "]");
//							continue;
//						}
//						
//						notFinalizedOrders.add(thisOrder.getOrderId());
//						
//						if (thisOrder.getOrderLockedBy() != null) {
//							//unlockButtonEnabled = Boolean.TRUE;
//							lockedCount++;
//						}	
//						else
//							;//lockButtonEnabled = Boolean.TRUE;
//					}	
//				}
//			}
//		}
//
//		if (notFinalizedOrders.size() == 0) {
//			System.out.println("A");
//			saveButtonEnabled = Boolean.FALSE;
//			lockButtonEnabled = Boolean.FALSE;
//			unlockButtonEnabled = Boolean.FALSE;
//		}
//		else if (lockedCount == notFinalizedOrders.size()) { // selectedOrders not zero
//			System.out.println("B");
//			saveButtonEnabled = Boolean.FALSE;
//			lockButtonEnabled = Boolean.FALSE;
//			unlockButtonEnabled = Boolean.TRUE;
//		}
//		else if (lockedCount == 0) {
//			System.out.println("C");
//			saveButtonEnabled = Boolean.TRUE;
//			lockButtonEnabled = Boolean.TRUE;
//			unlockButtonEnabled = Boolean.FALSE;
//		}
//		else if (lockedCount > 0) { // but not equal to selectedOrders count
//			System.out.println("D");
//			saveButtonEnabled = Boolean.TRUE;
//			lockButtonEnabled = Boolean.TRUE;
//			unlockButtonEnabled = Boolean.TRUE;
//		}
//	
//		request.getSession().setAttribute(SessionParamConstants.SELECTED_ORDERS_PARAM, selectedOrders);
//		request.getSession().setAttribute(SessionParamConstants.SAVE_BUTTON_ENABLED, saveButtonEnabled);
//		request.getSession().setAttribute(SessionParamConstants.ORDER_LOCKED_BUTTON_PARAM, lockButtonEnabled);
//		request.getSession().setAttribute(SessionParamConstants.ORDER_UNLOCKED_BUTTON_PARAM, unlockButtonEnabled);
//		request.getSession().setAttribute(SessionParamConstants.NOT_FINALIZED_ORDERS_PARAM, notFinalizedOrders);
//	}
	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}

	/*private void setupButtonFlags(HttpServletRequest request, List<Order> orders) {
		Order order = this.getBuyerOrderStatus(orders);
		// always show save button
		Boolean saveButtonEnabled = Boolean.TRUE;
		Boolean lockButtonEnabled = Boolean.TRUE;
		Boolean unlockButtonEnabled = Boolean.FALSE;
		
		if (order == null) {
			saveButtonEnabled = Boolean.FALSE;
			lockButtonEnabled = Boolean.FALSE;
			unlockButtonEnabled = Boolean.FALSE;
		}else{

			if (NumberUtil.isNullOrZero(order.getOrderLockedBy())  && 
					!NumberUtil.isNullOrZero(order.getOrderUnlockedBy())) {
				lockButtonEnabled = Boolean.TRUE;
				unlockButtonEnabled = Boolean.FALSE;
			}
			if (!NumberUtil.isNullOrZero(order.getOrderLockedBy())  && 
					NumberUtil.isNullOrZero(order.getOrderUnlockedBy())) {
				lockButtonEnabled = Boolean.FALSE;
				unlockButtonEnabled = Boolean.TRUE;
			}
			if (!NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) {
				saveButtonEnabled = Boolean.FALSE;
				lockButtonEnabled = Boolean.FALSE;
				unlockButtonEnabled = Boolean.FALSE;
			}
		}
		request.getSession().setAttribute(SessionParamConstants.SAVE_BUTTON_ENABLED, saveButtonEnabled);
		request.getSession().setAttribute(SessionParamConstants.ORDER_LOCKED_BUTTON_PARAM, lockButtonEnabled);
		request.getSession().setAttribute(SessionParamConstants.ORDER_UNLOCKED_BUTTON_PARAM, unlockButtonEnabled);
	}
	
	public Order getBuyerOrderStatus(List<Order> orders) {

		Order order = new Order();
		Order orderStatus = new Order();
		orderStatus = (Order) orders.get(1);
		if(!NumberUtil.isNullOrZero(orderStatus.getOrderFinalizedBy())){
			if (NumberUtil.isNullOrZero(orderStatus.getOrderLockedBy()))
				orderStatus.setOrderLockedBy(orderStatus.getOrderFinalizedBy());
			if (NumberUtil.isNullOrZero(orderStatus.getOrderUnlockedBy()))
				orderStatus.setOrderUnlockedBy(orderStatus.getOrderFinalizedBy());
		}
		
		for (int i=2 ; orders.size()> i ; i++) {
			order = (Order) orders.get(i);
			if(!NumberUtil.isNullOrZero(order.getOrderFinalizedBy())){
				if (NumberUtil.isNullOrZero(order.getOrderLockedBy()))
					order.setOrderLockedBy(order.getOrderFinalizedBy());
				if (NumberUtil.isNullOrZero(order.getOrderUnlockedBy()))
					order.setOrderUnlockedBy(order.getOrderFinalizedBy());
			}
			if (NumberUtil.isNullOrZero(orderStatus.getOrderLockedBy()) && 
					!NumberUtil.isNullOrZero(order.getOrderLockedBy()))
			{
				orderStatus.setOrderLockedBy(null);
			} 
			if (NumberUtil.isNullOrZero(orderStatus.getOrderUnlockedBy()) && 
					!NumberUtil.isNullOrZero(order.getOrderUnlockedBy()))
			{
				orderStatus.setOrderUnlockedBy(null);
			}
			if (NumberUtil.isNullOrZero(orderStatus.getOrderFinalizedBy()) && 
					!NumberUtil.isNullOrZero(order.getOrderFinalizedBy()))
			{
				orderStatus.setOrderFinalizedBy(null);
			}
		}
		
		return orderStatus;
		
		//return combineOrders(orders);
		//return orderStatus;
	}	*/
	
	private List<Integer> getBuyerIdsOfBuyerAdmin (Integer userId, String selectedDate) {
		List<Integer> memberIds = new ArrayList<Integer>(); 
		List <User> buyerAdminUsers = dealingPatternService.getMembersByAdminId(userId,
				DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER, selectedDate, selectedDate);
		
		for (User user : buyerAdminUsers) {
			memberIds.add(user.getUserId());
		}
		return memberIds;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, List<Integer>> getMapOfMembersByDateFromSession(
			HttpServletRequest request) {
		return (Map<String, List<Integer>>) SessionHelper.getAttribute(request, SessionParamConstants.MAP_OF_MEMBERS_BY_DATE);
	}

}
