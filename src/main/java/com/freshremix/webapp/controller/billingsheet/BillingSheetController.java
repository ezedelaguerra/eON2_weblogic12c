package com.freshremix.webapp.controller.billingsheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.BillingSheetService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

public class BillingSheetController extends SimpleFormController {
	
	private BillingSheetService billingSheetService;
	private DealingPatternService dealingPatternService;
	private SKUGroupService skuGroupService;
	private OrderSheetService orderSheetService;
	private OrderUnitService orderUnitService;

	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}

	public void setBillingSheetService(BillingSheetService billingSheetService) {
		this.billingSheetService = billingSheetService;
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
			
			if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)){
				orderSheetParam.setSheetTypeId(SheetTypeConstants.SELLER_ADMIN_BILLING_SHEET);
				orderSheetParam.setCsvSheetTypeID(SheetTypeConstants.SELLER_ADMIN_BILLING_SHEET);
			}else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)){
				orderSheetParam.setSheetTypeId(SheetTypeConstants.SELLER_BILLING_SHEET);
				orderSheetParam.setCsvSheetTypeID(SheetTypeConstants.SELLER_BILLING_SHEET);
			}else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)){
				orderSheetParam.setSheetTypeId(SheetTypeConstants.BUYER_ADMIN_BILLING_SHEET);
				orderSheetParam.setCsvSheetTypeID(SheetTypeConstants.BUYER_ADMIN_BILLING_SHEET);
			}else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)){
				orderSheetParam.setSheetTypeId(SheetTypeConstants.BUYER_BILLING_SHEET);
				orderSheetParam.setCsvSheetTypeID(SheetTypeConstants.BUYER_BILLING_SHEET);
			}
			
			if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)){
				if (orderSheetParam.getSelectedSellerID() == null) {
					orderSheetParam.setSelectedSellerID(user.getUserId().toString());
				}
			}
			
			if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)){
				if (orderSheetParam.getSelectedBuyerID() == null){
					orderSheetParam.setSelectedBuyerID(user.getUserId().toString());
				}
			}
			
			if (orderSheetParam.getEndDate().length() != 8)
				orderSheetParam.setEndDate(orderSheetParam.getStartDate());

			SessionHelper.setAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM, orderSheetParam);
			SessionHelper.setAttribute(request, SessionParamConstants.DELIVERY_DATE_PARAM, orderSheetParam.getSelectedDate());
			
			/* get parameters from filter page to get All Orders*/
			List<Integer> sellerIds = OrderSheetUtil.toList(orderSheetParam.getSelectedSellerID());
			String startDate = orderSheetParam.getStartDate();
			String endDate = orderSheetParam.getEndDate();
			List<Integer> buyerIds = OrderSheetUtil.toList(orderSheetParam.getSelectedBuyerID());
			List<String> dateList = DateFormatter.getDateList(startDate, endDate);

			System.out.println("startDate:[" + startDate + "]");
			System.out.println("endDate:[" + endDate + "]");
			System.out.println("buyerIds:[" + buyerIds + "]");
			System.out.println("sellerIds:[" + sellerIds + "]");
			
			System.out.println("allDatesView:[" + orderSheetParam.isAllDatesView() + "]");
			System.out.println("datesViewBuyerId:[" + orderSheetParam.getDatesViewBuyerID() + "]");
			
			
			// set list of sku group
			String skuGroupList = skuGroupService.getSKUGroupList(user.getUserId().intValue(), orderSheetParam.getCategoryId());
			SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
			
			// set order unit
			String orderUnit = orderUnitService.getOrderUnitByCategoryId(orderSheetParam.getCategoryId());
			SessionHelper.setAttribute(request, SessionParamConstants.ORDER_UNIT_LIST_PARAM, orderUnit);
			
			// set order unit renderer
			String orderUnitRenderer = orderUnitService.getOrderUnitRenderer(orderSheetParam.getCategoryId());
			SessionHelper.setAttribute(request, SessionParamConstants.ORDER_UNIT_RENDERER_PARAM, orderUnitRenderer);
			
			// set default order unit id
			Integer orderUnitId = orderUnitService.getOrderUnitCaseId(orderSheetParam.getCategoryId());
			SessionHelper.setAttribute(request, SessionParamConstants.DEFAULT_ORDER_UNIT_PARAM, orderUnitId);
			
			//set seller to model (sku group)
			model.put("response", new FilteredIDs(user.getUserId().toString(), user.getName()));
			
			List<Integer> seller = new ArrayList<Integer>();
			if (user.getRole().getSellerFlag().equals("1")) { 
				seller.add(user.getUserId());
				SessionHelper.setAttribute(request, SessionParamConstants.IS_ADMIN_PARAM, false);
			}
			else {
				List<Integer> sellerId = OrderSheetUtil.toList(orderSheetParam.getSelectedSellerID());
				seller.addAll(sellerId);
				SessionHelper.setAttribute(request, SessionParamConstants.IS_ADMIN_PARAM, true);
			}
			
			// set sku group renderer
			String skuGroupRenderer = skuGroupService.getSKUGroupRenderer(seller, orderSheetParam.getCategoryId());
			SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_RENDERER_PARAM, skuGroupRenderer);
			
			String sellerNameList = orderSheetService.getSellerNames(seller);
			SessionHelper.setAttribute(request, SessionParamConstants.SELLER_NAME_LIST_PARAM, sellerNameList);
			
			// create seller to buyer DP map
			Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap = dealingPatternService.getSellerToBuyerDPMap(seller, orderSheetParam.getStartDate(), orderSheetParam.getEndDate());
			// create buyer to seller DP map
			Map<String, Map<String, List<Integer>>> buyerToSellerDPMap = dealingPatternService.getBuyerToSellerDPMap(sellerToBuyerDPMap);
			DealingPattern dp = new DealingPattern(sellerToBuyerDPMap,buyerToSellerDPMap);
			SessionHelper.setAttribute(request, SessionParamConstants.DEALING_PATTERN_PARAM, dp);
			
			/* create quantity columns */
			if (!orderSheetParam.isAllDatesView())
				//create buyer columns
				OrderSheetUtil.createCompanyColumns(request, orderSheetParam.getSelectedBuyerCompany(),
						orderSheetParam.getSelectedBuyerID(), orderSheetParam.getSelectedSellerID(),
						orderSheetParam.getSelectedDate(), sellerToBuyerDPMap);
			else {
				//create date columns
				OrderSheetUtil.createDateColumns(request, orderSheetParam, null, null);
				SessionHelper.setAttribute(request, "oneBuyerId", orderSheetParam.getDatesViewBuyerID());
			}
			
			List<Order> allOrders = null;
			if (orderSheetParam.isCheckDBOrder()) {
				
				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN) ||
						user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER) )
					allOrders = billingSheetService.getBillingOrders(sellerIds, buyerIds, dateList, null);
				else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN) ||
						user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER) )
					//get only finalized orders
					allOrders = billingSheetService.getBillingOrders(sellerIds, buyerIds, dateList, user.getUserId());
				
				SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
				System.out.println("allOrders set:[" + allOrders.size()+ "]");
			}
			else {
				allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
				System.out.println("allOrders get:[" + allOrders.size()+ "]");
			}	
			
			Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
			System.out.println(allOrdersMap);
			// Get set of order ids (from all Orders) based on selected display option and set to session
			//this.selectOrdersAndSetButtonFlag(request, orderSheetParam, allOrdersMap, user);
//		}

			List<Integer> selectedOrderIds = orderSheetService.getSelectedOrderIds(allOrdersMap, orderSheetParam);
			//List<Order> selectedOrders = orderSheetService.getSelectedOrders(allOrdersMap, orderSheetParam);
			
			SessionHelper.setAttribute(request, SessionParamConstants.SELECTED_ORDERS_PARAM, selectedOrderIds);
			
		if (errors.hasErrors()) 
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json",model);
	}

	/**
	 * This is will get set of order ids from all Orders based on display option selection and set to session
	 * This will also set button flags
	 *
	 */	
	private void selectOrdersAndSetButtonFlag(HttpServletRequest request, OrderSheetParam osp,
			Map<String,Order> allOrdersMap, User user) {
		
		Boolean saveButtonEnabled = Boolean.FALSE;
		Boolean lockButtonEnabled = Boolean.FALSE;
		Boolean unlockButtonEnabled = Boolean.FALSE;
		List<String> dateList = null;
		List<Integer> buyerIds = null;
		List<Integer> sellerIds = OrderSheetUtil.toList(osp.getSelectedSellerID());
		
		if (osp.isAllDatesView()) {
			dateList = DateFormatter.getDateList(osp.getStartDate(), osp.getEndDate());
			buyerIds = OrderSheetUtil.toList(osp.getDatesViewBuyerID());
		}
		else {
			dateList = DateFormatter.getDateList(osp.getSelectedDate(), osp.getSelectedDate());
			buyerIds = OrderSheetUtil.toList(osp.getSelectedBuyerID());
		}
		System.out.println("dateList:[" + dateList +"]");
		System.out.println("buyerIds:[" + buyerIds +"]");
		System.out.println("sellerIds:[" + sellerIds +"]");
		
		List<Integer> selectedOrders = new ArrayList<Integer>();
		List<Integer> notFinalizedOrders = new ArrayList<Integer>();
		int lockedCount = 0;
		for (String thisDate : dateList) {
			for (Integer buyerId : buyerIds) {
				for (Integer sellerId : sellerIds) {
					String key = thisDate + "_" + buyerId.toString() + "_" + sellerId.toString();
					Order thisOrder = allOrdersMap.get(key);
					
					if (thisOrder != null) {
						selectedOrders.add(thisOrder.getOrderId());

						if (thisOrder.getOrderFinalizedBy() != null) { //skip if finalized
							System.out.println("order finalized:[" + key + "]");
							continue;
						}
						
						notFinalizedOrders.add(thisOrder.getOrderId());
						
						/*if (thisOrder.getOrderLockedBy() != null) {
							//unlockButtonEnabled = Boolean.TRUE;
							lockedCount++;
						}	
						else
							;//lockButtonEnabled = Boolean.TRUE;*/
					}	
				}
			}
		}

		if (notFinalizedOrders.size() == 0) {//all finalized
			System.out.println("A");
			saveButtonEnabled = Boolean.FALSE;
			//lockButtonEnabled = Boolean.FALSE;
			//unlockButtonEnabled = Boolean.FALSE;
		}else if(selectedOrders.size() != 0){
			System.out.println("B");
			saveButtonEnabled = Boolean.TRUE;
		}
		/*else if (lockedCount == notFinalizedOrders.size()) { // selectedOrders not zero
			System.out.println("B");
			saveButtonEnabled = Boolean.FALSE;
			lockButtonEnabled = Boolean.FALSE;
			unlockButtonEnabled = Boolean.TRUE;
		}
		else if (lockedCount == 0) {
			System.out.println("C");
			saveButtonEnabled = Boolean.TRUE;
			lockButtonEnabled = Boolean.TRUE;
			unlockButtonEnabled = Boolean.FALSE;
		}
		else if (lockedCount > 0) { // but not equal to selectedOrders count
			System.out.println("D");
			saveButtonEnabled = Boolean.TRUE;
			lockButtonEnabled = Boolean.TRUE;
			unlockButtonEnabled = Boolean.TRUE;
		}*/
	
		request.getSession().setAttribute(SessionParamConstants.SELECTED_ORDERS_PARAM, selectedOrders);
		request.getSession().setAttribute(SessionParamConstants.SAVE_BUTTON_ENABLED, saveButtonEnabled);
		//request.getSession().setAttribute(SessionParamConstants.ORDER_LOCKED_BUTTON_PARAM, lockButtonEnabled);
		//request.getSession().setAttribute(SessionParamConstants.ORDER_UNLOCKED_BUTTON_PARAM, unlockButtonEnabled);
		request.getSession().setAttribute(SessionParamConstants.NOT_FINALIZED_ORDERS_PARAM, notFinalizedOrders);
	}
	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
}