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
 * 20120727			Rhoda		Redmine 68 - Order Sheet Concurrency
 */

package com.freshremix.webapp.controller.buyerordersheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.OrderConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.ConcurrencyResponse;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.BuyerOrderSheetService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.ConcurrencyUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

public class OrderSheetLockUnlockController extends SimpleFormController {

	private BuyerOrderSheetService buyerOrderSheetService;
	private OrderSheetService orderSheetService;
	
	public void setBuyerOrderSheetService(
			BuyerOrderSheetService buyerOrderSheetService) {
		this.buyerOrderSheetService = buyerOrderSheetService;
	}

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
//		if (SessionHelper.isSessionExpired(request)) {
//			Map<String,Object> model = new HashMap<String,Object>();
//			model.put("isSessionExpired", Boolean.TRUE);
//			return new ModelAndView("json",model);
//		}
		
		
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		Integer userId = user.getUserId().intValue();
		
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		osParam.setCheckDBOrder(true);
		SessionHelper.setAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM, osParam);

		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		List<Integer> selectedOrders = this.getIntegerListFromSession(request, SessionParamConstants.SELECTED_ORDERS_PARAM);
		String lockFLag = request.getParameter("lock");
		//ENHANCEMENT START 20120727: Rhoda Redmine 68
		ConcurrencyResponse concurrencyResponse = new ConcurrencyResponse();

		String startDate = osParam.getStartDate();
		String endDate = osParam.getEndDate();
		List<String> dateList = DateFormatter.getDateList(startDate, endDate);
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		List<Order> selectedStaleOrders = new ArrayList<Order>();
		for (Integer _orderId: selectedOrders){
			for (Order _order: allOrders){
				if(_order.getOrderId().equals(_orderId))selectedStaleOrders.add(_order);
			}
		}
		/*TODO removed BuyerList if sortedBuyers are already set to user preference */
		List<FilteredIDs> buyerList = new ArrayList<FilteredIDs>();
		if(user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)){
			buyerList = (List<FilteredIDs>) 
				SessionHelper.getAttribute(request, SessionParamConstants.SORTED_BUYERS);
		}else{
			buyerList.add(new FilteredIDs(user.getUserId().toString(), user.getUserName()));
		}
		
		//ENHANCEMENT END 20120727: Rhoda Redmine 68
		
		List<Order> currentOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);

		if(lockFLag.equals("Y")){
			concurrencyResponse = ConcurrencyUtil.validateBuyerOrderSheet(user, 
					selectedStaleOrders, currentOrders, OrderConstants.ACTION_LOCK, buyerList);

			List<Order> forUpdateOrders = concurrencyResponse
					.getForUpdateOrders();
			
			if (CollectionUtils.isNotEmpty(forUpdateOrders)) {
				List<Integer> orderIdList = OrderSheetUtil
						.getOrderIdList(forUpdateOrders);
				buyerOrderSheetService.updateLockOrders(orderIdList,userId);
			}

		} else if (lockFLag.equals("N")) {
			
			concurrencyResponse = ConcurrencyUtil.validateBuyerOrderSheet(user, 
					selectedStaleOrders, currentOrders, OrderConstants.ACTION_UNLOCK, buyerList);

			//DELETION 20120727: Rhoda Redmine 68
//			buyerOrderSheetService.updateUnlockOrders(selectedOrders,userId);
			List<Order> forUpdateOrders = concurrencyResponse
					.getForUpdateOrders();
			
			if (CollectionUtils.isNotEmpty(forUpdateOrders)) {
				List<Integer> orderIdList = OrderSheetUtil
						.getOrderIdList(forUpdateOrders);
				buyerOrderSheetService.updateUnlockOrders(orderIdList, userId);
			}
			//ENHANCEMENT END 20120727: Rhoda Redmine 68
		}
		/* get parameters from filter page to get All Orders*/
		//DELETION START 20120727: Rhoda Redmine 68
//		osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
//		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
//		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
//		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		//DELETION END 20120727: Rhoda Redmine 68
		//List<Order> allOrders = orderSheetService.getSavedOrders(buyerIds, dateList, sellerIds);
		//allOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);
		String enableBAPublish = null;
		boolean isBAPublished = buyerOrderSheetService.isBuyerPublished(user);
		if (isBAPublished) 
			enableBAPublish=String.valueOf(isBAPublished);
		
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN))
			allOrders = buyerOrderSheetService.getPublishedOrders(buyerIds, dateList, sellerIds, null, null);
		else
			allOrders = buyerOrderSheetService.getPublishedOrders(buyerIds, dateList, sellerIds, user.getUserId(), enableBAPublish);
		SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
		/*List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		String startDate = osParam.getStartDate();
		String endDate = osParam.getEndDate();
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		List<String> dateList = DateFormatter.getDateList(startDate, endDate);		
		/* reload allOrders */
		/*List<Order>  allOrders = null;
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN))
			allOrders = buyerOrderSheetService.getPublishedOrders(buyerIds, dateList, sellerIds, null, null);
		else
			allOrders = buyerOrderSheetService.getPublishedOrders(buyerIds, dateList, sellerIds, user.getUserId(), user.getEnableBAPublish());
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		
		this.selectOrdersAndSetButtonFlag(request, osParam, allOrdersMap);*/
		
		//DELETION 20120727: Rhoda Redmine 68
//		return super.onSubmit(request, response, command, errors);
		//ENHANCEMENT START 20120727: Rhoda Redmine 68
		ModelAndView mav = new ModelAndView("json");
		mav.addObject("concurrencyResp", concurrencyResponse);
		mav.addObject("isSellerFinalized", concurrencyResponse.isSellerFinalized());
		return mav;
		//ENHANCEMENT END 20120727: Rhoda Redmine 68
	}
	
	/**
	 * This is will get set of order ids from all Orders based on display option selection and set to session
	 * This will also set button flags
	 *
	 */	
	private void selectOrdersAndSetButtonFlag(HttpServletRequest request, OrderSheetParam osp,
			Map<String,Order> allOrdersMap) {
		
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
						
						if (thisOrder.getOrderLockedBy() != null) {
							//unlockButtonEnabled = Boolean.TRUE;
							lockedCount++;
						}	
						else
							;//lockButtonEnabled = Boolean.TRUE;
					}	
				}
			}
		}

		if (notFinalizedOrders.size() == 0) {
			System.out.println("A");
			saveButtonEnabled = Boolean.FALSE;
			lockButtonEnabled = Boolean.FALSE;
			unlockButtonEnabled = Boolean.FALSE;
		}
		else if (lockedCount == notFinalizedOrders.size()) { // selectedOrders not zero
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
		}
		
		request.getSession().setAttribute(SessionParamConstants.SELECTED_ORDERS_PARAM, selectedOrders);
		request.getSession().setAttribute(SessionParamConstants.SAVE_BUTTON_ENABLED, saveButtonEnabled);
		request.getSession().setAttribute(SessionParamConstants.ORDER_LOCKED_BUTTON_PARAM, lockButtonEnabled);
		request.getSession().setAttribute(SessionParamConstants.ORDER_UNLOCKED_BUTTON_PARAM, unlockButtonEnabled);
		request.getSession().setAttribute(SessionParamConstants.NOT_FINALIZED_ORDERS_PARAM, notFinalizedOrders);
	}
	
	
	@SuppressWarnings("unchecked")
	private List<Integer> getIntegerListFromSession(HttpServletRequest request, String paramName) {
		return (List<Integer>) SessionHelper.getAttribute(request, paramName);
	}
	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
}
