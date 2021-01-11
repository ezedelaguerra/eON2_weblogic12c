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
 * Mar 30, 2010		nvelasquez		
 */
package com.freshremix.webapp.controller.receivedsheet;

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
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.ReceivedSheetService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author nvelasquez
 *
 */
public class ReceivedSheetController extends SimpleFormController {
	
	private ReceivedSheetService receivedSheetService;
	private DealingPatternService dealingPatternService;
	private OrderUnitService orderUnitService;
	private SKUGroupService skuGroupService;
	
	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}	
	
	public void setReceivedSheetService(ReceivedSheetService receivedSheetService) {
		this.receivedSheetService = receivedSheetService;
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
		if (orderSheetParam.getSelectedBuyerID() == null) {
			orderSheetParam.setSelectedBuyerID(user.getUserId().toString());
			orderSheetParam.setSelectedBuyerCompany(user.getCompany().getCompanyId().toString());
			orderSheetParam.setDatesViewBuyerID(user.getUserId().toString());
		}
		String selectedBuyerID = orderSheetParam.getSelectedBuyerID();
		String selectedSellerID = orderSheetParam.getSelectedSellerID();

		if (orderSheetParam.getEndDate().length() != 8){
			orderSheetParam.setEndDate(orderSheetParam.getStartDate());
		}
		/* get parameters */
		List<Integer> sellerIds = OrderSheetUtil.toList(selectedSellerID);
		String startDate = orderSheetParam.getStartDate();
		String endDate = orderSheetParam.getEndDate();
		List<Integer> buyerIds = OrderSheetUtil.toList(selectedBuyerID);
		List<String> dateList = DateFormatter.getDateList(startDate, endDate);
		
		List<Order> allOrders = null;
		allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);

		if (orderSheetParam.isCheckDBOrder() || allOrders==null) {
			allOrders = receivedSheetService.getPublishedOrdersForReceived(buyerIds, dateList, sellerIds);
			SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
		}
		
		
		SessionHelper.setAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM, orderSheetParam);
		SessionHelper.setAttribute(request, SessionParamConstants.DELIVERY_DATE_PARAM, orderSheetParam.getSelectedDate());
		String selectedDate = orderSheetParam.getSelectedDate();
		SessionHelper.setAttribute(request, SessionParamConstants.DELIVERY_DATE_PARAM, selectedDate);

		// set list of sku group
		//String skuGroupList = skuGroupService.getSKUGroupList(user.getUserId().intValue(), orderSheetParam.getCategoryId());
		//SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
		
		//set seller to model (sku group)
		//model.put("response", new FilteredIDs(user.getUserId().toString(), user.getName()));
		List<Integer> seller = OrderSheetUtil.toList(orderSheetParam.getSelectedSellerID());

		// set sku group renderer
		String skuGroupRenderer = skuGroupService.getSKUGroupRenderer(seller, orderSheetParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_RENDERER_PARAM, skuGroupRenderer);
		
		// set order unit
		String orderUnit = orderUnitService.getOrderUnitByCategoryId(orderSheetParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.ORDER_UNIT_LIST_PARAM, orderUnit);
		
		// set order unit renderer
		String orderUnitRenderer = orderUnitService.getOrderUnitRenderer(orderSheetParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.ORDER_UNIT_RENDERER_PARAM, orderUnitRenderer);
		
		// set selling uom
		String sellingUom = orderUnitService.getSellingUomList(orderSheetParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SELLING_UOM_LIST_PARAM, sellingUom);
		
		// set selling uom renderer
		String sellUomRenderer = orderUnitService.getSellingUomRenderer(orderSheetParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SELLING_UOM_RENDERER_PARAM, sellUomRenderer);

		// create seller to buyer DP map
		Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap = dealingPatternService.getSellerToBuyerDPMap(seller, orderSheetParam.getStartDate(), orderSheetParam.getEndDate());
		// create buyer to seller DP map
		Map<String, Map<String, List<Integer>>> buyerToSellerDPMap = dealingPatternService.getBuyerToSellerDPMap(sellerToBuyerDPMap);
		
		DealingPattern dp = new DealingPattern(sellerToBuyerDPMap,buyerToSellerDPMap);
		SessionHelper.setAttribute(request, SessionParamConstants.DEALING_PATTERN_PARAM, dp);
		
		List<Integer> memberUserIds = null;
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)){
			memberUserIds = this.getBuyerIdsOfBuyerAdmin(user.getUserId(),
					orderSheetParam.getSelectedDate());
			//Sheet Type used for CSV Download
			orderSheetParam.setCsvSheetTypeID(SheetTypeConstants.BUYER_ADMIN_RECEIVED_SHEET);
			OrderSheetUtil.setAdminMembersByDateToSession(
					dealingPatternService,
					DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER,
					request, user, startDate, endDate);

		}else{
			//Sheet Type used for CSV Download
			orderSheetParam.setCsvSheetTypeID(SheetTypeConstants.BUYER_RECEIVED_SHEET);
		}
		
		
		
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
				validBuyerMemberList, sellerIds,
				selectedDate, null, sellerToBuyerDPMap);
		/* create quantity columns */
		//if (!orderSheetParam.isAllDatesView())
			//create buyer columns
//			OrderSheetUtil.createCompanyColumns(request, orderSheetParam.getSelectedBuyerCompany(),
//					orderSheetParam.getSelectedBuyerID(), orderSheetParam.getSelectedSellerID(),
//					orderSheetParam.getSelectedDate(), memberUserIds, sellerToBuyerDPMap);

		if (orderSheetParam.isAllDatesView()){	
			//create date columns
			OrderSheetUtil.createDateColumns(request, orderSheetParam, null, null);
			SessionHelper.setAttribute(request, "oneBuyerId", orderSheetParam.getDatesViewBuyerID());
		}
			
		
		
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
	@SuppressWarnings("unchecked")
	private Map<String, List<Integer>> getMapOfMembersByDateFromSession(
			HttpServletRequest request) {
		return (Map<String, List<Integer>>) SessionHelper.getAttribute(request, SessionParamConstants.MAP_OF_MEMBERS_BY_DATE);
	}

}
