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

package com.freshremix.webapp.controller.allocationsheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.AllocationSheetService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.SessionHelper;

public class AllocationSheetController extends SimpleFormController {
	
	private AllocationSheetService allocationSheetService;
	private DealingPatternService dealingPatternService;
	private SKUGroupService skuGroupService;
	private OrderUnitService orderUnitService;
	
	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}
	
	public void setAllocationSheetService(AllocationSheetService allocationSheetService) {
		this.allocationSheetService = allocationSheetService;
	}	
	
	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}
	
	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		OrderSheetParam orderSheetParam = (OrderSheetParam) command;
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);

		if (orderSheetParam.getSelectedSellerID() == null) {
			orderSheetParam.setSelectedSellerID(user.getUserId().toString());
		}
		
		String selectedSellerID = orderSheetParam.getSelectedSellerID();
		
		if (orderSheetParam.getEndDate().length() != 8)
			orderSheetParam.setEndDate(orderSheetParam.getStartDate());

		String startDate = orderSheetParam.getStartDate();
		String endDate = orderSheetParam.getEndDate();
		String selectedBuyerID = orderSheetParam.getSelectedBuyerID();
		List<Integer> buyerIds = OrderSheetUtil.toList(selectedBuyerID);
		List<String> dateList = DateFormatter.getDateList(startDate, endDate);
		List<Integer> sellerIds = OrderSheetUtil.toList(selectedSellerID);
		
		SessionHelper.setAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM, orderSheetParam);
		String selectedDate = orderSheetParam.getSelectedDate();
		SessionHelper.setAttribute(request, SessionParamConstants.DELIVERY_DATE_PARAM, selectedDate);
		
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
		Integer orderUnitId = orderUnitService.getDefaultOrderUnit(orderSheetParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.DEFAULT_ORDER_UNIT_PARAM, orderUnitId);
		
		//set seller to model (sku group)
		model.put("response", new FilteredIDs(user.getUserId().toString(), user.getName()));
		
		List<Integer> sellers = new ArrayList<Integer>();

		if (RolesUtil.isUserRoleSeller(user)) {
			sellers.add(user.getUserId());
			SessionHelper.setAttribute(request, SessionParamConstants.IS_ADMIN_PARAM, false);
		} else {
			//List<Integer> sellerId = OrderSheetUtil.toList(selectedSellerID);
			sellers.addAll(sellerIds);
			SessionHelper.setAttribute(request, SessionParamConstants.IS_ADMIN_PARAM, true);
			OrderSheetUtil.setAdminMembersByDateToSession(
					dealingPatternService,
					DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER,
					request, user, startDate, endDate);
		}
		
		// set sku group renderer
		String skuGroupRenderer = skuGroupService.getSKUGroupRenderer(sellers, orderSheetParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_RENDERER_PARAM, skuGroupRenderer);
		
		// FORDELETION 20120724: Lele - Redmine 797
		//String sellerNameList = allocationSheetService.getSellerNames(seller);
		// ENHANCEMENT 20120724: Lele - Redmine 797
		String sellerNameList = allocationSheetService.getSellerNames(sellers, orderSheetParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SELLER_NAME_LIST_PARAM, sellerNameList);
		
		// create seller to buyer DP map
		Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap = dealingPatternService.getSellerToBuyerDPMap(sellers, orderSheetParam.getStartDate(), orderSheetParam.getEndDate());
		// create buyer to seller DP map
		Map<String, Map<String, List<Integer>>> buyerToSellerDPMap = dealingPatternService.getBuyerToSellerDPMap(sellerToBuyerDPMap);
		DealingPattern dp = new DealingPattern(sellerToBuyerDPMap,buyerToSellerDPMap);
		SessionHelper.setAttribute(request, SessionParamConstants.DEALING_PATTERN_PARAM, dp);
		
		/* create quantity columns */
		if (orderSheetParam.isAllDatesView()){
			//create date columns
			OrderSheetUtil.createDateColumns(request, orderSheetParam, null, null);
			SessionHelper.setAttribute(request, "oneBuyerId", orderSheetParam.getDatesViewBuyerID());
		} else {
			//create buyer columns
			List<Integer> sortedValidSellerMemberList = new ArrayList<Integer>();
			
			if (RolesUtil.isUserRoleSeller(user)) {
				sortedValidSellerMemberList = sellers;
			} else {
				
				if (CollectionUtils.isNotEmpty(sellers)) {
					Map<String, List<Integer>> mapOfMembersByDate = getMapOfMembersByDateFromSession(request);
					List<Integer> referenceMemberList = mapOfMembersByDate.get(selectedDate);
					for (Integer seller : sellers) {
						if (referenceMemberList.contains(seller)) {
							sortedValidSellerMemberList.add(seller);
						}
					}
				}
			}
			
			String selectedBuyerCompany = orderSheetParam.getSelectedBuyerCompany();
			OrderSheetUtil.createCompanyColumns(request, selectedBuyerCompany,
					buyerIds, sortedValidSellerMemberList,
					selectedDate, null, sellerToBuyerDPMap);
		}
		
		// create/update default orders order item (fruits, vegetables, fish)
		// Note: ORDER_SAVED_BY field is not populated
		orderSheetParam.setSheetTypeId(SheetTypeConstants.SELLER_ALLOCATION_SHEET);
		
		//Sheet Type used for CSV Download
		if (RolesUtil.isUserRoleSellerAdmin(user)){
			orderSheetParam.setCsvSheetTypeID(SheetTypeConstants.SELLER_ADMIN_ALLOCATION_SHEET);
		} else {
			orderSheetParam.setCsvSheetTypeID(SheetTypeConstants.SELLER_ALLOCATION_SHEET);
		}
		
		List<Order> allOrders = null;
		allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);

		if (orderSheetParam.isCheckDBOrder() || allOrders==null) {
			allOrders = allocationSheetService.getAllOrders(buyerIds, dateList, sellerIds);
			SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);	
		}
		
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		List<Integer> selectedOrderIds = allocationSheetService.getSelectedOrderIds(allOrdersMap, orderSheetParam);
		
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
	
	@SuppressWarnings("unchecked")
	private Map<String, List<Integer>> getMapOfMembersByDateFromSession(
			HttpServletRequest request) {
		return (Map<String, List<Integer>>) SessionHelper.getAttribute(request, SessionParamConstants.MAP_OF_MEMBERS_BY_DATE);
	}
	
}