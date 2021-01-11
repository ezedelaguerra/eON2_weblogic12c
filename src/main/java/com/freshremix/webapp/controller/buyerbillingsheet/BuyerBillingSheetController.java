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
import com.freshremix.service.BillingSheetService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author Pammie
 *
 */
public class BuyerBillingSheetController  extends SimpleFormController {
	
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
		OrderSheetParam orderSheetParam = (OrderSheetParam) command;
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)){
			orderSheetParam.setSheetTypeId(SheetTypeConstants.BUYER_ADMIN_BILLING_SHEET);
			orderSheetParam.setCsvSheetTypeID(SheetTypeConstants.BUYER_ADMIN_BILLING_SHEET);
		}else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)){
			orderSheetParam.setSheetTypeId(SheetTypeConstants.BUYER_BILLING_SHEET);
			orderSheetParam.setCsvSheetTypeID(SheetTypeConstants.BUYER_BILLING_SHEET);
		}
		
		if (orderSheetParam.getSelectedBuyerID() == null) {
			orderSheetParam.setSelectedBuyerID(user.getUserId().toString());
			orderSheetParam.setSelectedBuyerCompany(user.getCompany().getCompanyId().toString());
			orderSheetParam.setDatesViewBuyerID(user.getUserId().toString());
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

		// set list of sku group
		String skuGroupList = skuGroupService.getSKUGroupList(user.getUserId().intValue(), orderSheetParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
		
		// set order unit
		String orderUnit = orderUnitService.getOrderUnitByCategoryId(orderSheetParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.ORDER_UNIT_LIST_PARAM, orderUnit);
		
		// set order unit renderer
		String orderUnitRenderer = orderUnitService.getOrderUnitRenderer(orderSheetParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.ORDER_UNIT_RENDERER_PARAM, orderUnitRenderer);
		
		// set selling uom renderer
		String sellUomRenderer = orderUnitService.getSellingUomRenderer(orderSheetParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SELLING_UOM_RENDERER_PARAM, sellUomRenderer);

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
		List<Order> allOrders = null;
		if (orderSheetParam.isCheckDBOrder()) {
			allOrders = billingSheetService.getBillingOrders(sellerIds, buyerIds, dateList, user.getUserId());
			
			SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
		}
		else {
			allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		}	
		
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		
		List<Integer> memberUserIds = null;
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN))
			memberUserIds = this.getBuyerIdsOfBuyerAdmin(user.getUserId(),
					orderSheetParam.getSelectedDate());
		
		/* create quantity columns */
		//create buyer columns
		OrderSheetUtil.createCompanyColumns(request, orderSheetParam.getSelectedBuyerCompany(),
				orderSheetParam.getSelectedBuyerID(), orderSheetParam.getSelectedSellerID(),
				orderSheetParam.getSelectedDate(), memberUserIds, sellerToBuyerDPMap);
		if (orderSheetParam.isAllDatesView()){	
			//create date columns
			OrderSheetUtil.createDateColumns(request, orderSheetParam, null, null);
			SessionHelper.setAttribute(request, "oneBuyerId", orderSheetParam.getDatesViewBuyerID());
		}

		List<Integer> selectedOrderIds = orderSheetService.getSelectedOrderIds(allOrdersMap, orderSheetParam);
		
		SessionHelper.setAttribute(request, SessionParamConstants.SELECTED_ORDERS_PARAM, selectedOrderIds);
			
		if (errors.hasErrors()) 
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json",model);
	}

	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
	
	private List<Integer> getBuyerIdsOfBuyerAdmin (Integer userId, String selectedDate) {
		List<Integer> memberIds = new ArrayList<Integer>(); 
		List <User> buyerAdminUsers = dealingPatternService.getMembersByAdminId(userId,
				DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER, selectedDate, selectedDate);
		
		for (User user : buyerAdminUsers) {
			memberIds.add(user.getUserId());
		}
		return memberIds;
	}
}
