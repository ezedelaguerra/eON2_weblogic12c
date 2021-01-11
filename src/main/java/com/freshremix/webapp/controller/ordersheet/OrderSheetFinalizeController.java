package com.freshremix.webapp.controller.ordersheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.AllocationSheetService;
import com.freshremix.service.LoginService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.FilterIDUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

public class OrderSheetFinalizeController extends SimpleFormController {

	private OrderSheetService orderSheetService;
	private AllocationSheetService allocationSheetService;
	private LoginService loginService;
	
	public void setLoginService(LoginService loginServiceImpl) {
		this.loginService = loginServiceImpl;
	}

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}

	public void setAllocationSheetService(
			AllocationSheetService allocationSheetService) {
		this.allocationSheetService = allocationSheetService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		//Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		//List<Order> selectedOrders = orderSheetService.getSelectedOrders(allOrdersMap, osParam);
		//List<Order> pubOrder = orderSheetService.getPublishedOrders(selectedOrders);
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		List<Order> pubOrder = orderSheetService.getPublishedOrders(allOrders);
		
		//Get all Auto-Publish settings of selected sellers
		Map<Integer, User> sellerMap = new HashMap<Integer, User>();
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)){
			List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
			for (Integer sellerId : sellerIds) {
				User seller = loginService.getUserByUserId(sellerId);				
				sellerMap.put(sellerId, seller);
			}
		}else if(user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)){
			sellerMap.put(user.getUserId(), loginService.getUserByUserId(user.getUserId()));
		}
		
		Map<Integer, String> sellerNameMap = getSellerNameMap(sellerMap);
		Map<Integer, String> buyerNameMap = getBuyerNameMap(request);

//		//before continuing with the process check if there are records that are already finalized
//		List<Order> finalizedOrderFromOrderIdList = getFinalizedRecordsFromOrderList(pubOrder);
//		List<Integer> finalizedOrderIdList = Collections.EMPTY_LIST;
//		StringBuilder sbMessage = null;
//		if (CollectionUtils.isNotEmpty(finalizedOrderFromOrderIdList)){
//			//you have some records in the list that are already finalized.
//			//report the situation to the user
//			Map<Integer, String> sellerNameMap = getSellerNameMap(user);
//			Map<Integer, String> buyerNameMap = getBuyerNameMap(request);
//			
//			sbMessage = OrderSheetUtil.createOrderListMessage("ordersheet.concurrent.finalize.finalizedFailed", 
//					finalizedOrderFromOrderIdList, sellerNameMap, buyerNameMap);
//
//			finalizedOrderIdList = OrderSheetUtil.getOrderIdList(finalizedOrderFromOrderIdList);
//		}
//		
//		for (Order order : pubOrder) {
//			//skip the order that is already finalized
//			if (finalizedOrderIdList.contains(Integer.valueOf(order.getOrderId()))){
//				continue;
//			}
//			
//			orderSheetService.updateFinalizedOrder(order.getOrderId(),finalizedBy,null);
//			
//			User userOrder = sellerMap.get(order.getSellerId().toString());
//			String fromAddress = StringUtil.isNullOrEmpty(user.getPcEmail()) == false ? user.getPcEmail() : user.getMobileEmail();
//			String toAddress[] = new String[1];
//			toAddress[0] = StringUtil.isNullOrEmpty(userOrder.getPcEmail()) == false ? userOrder.getPcEmail() : userOrder.getMobileEmail();
//			if (!StringUtil.isNullOrEmpty(toAddress[0]))
//				orderSheetService.sendMailNotification(order.getDeliveryDate(), "Finalize", user.getUserName(), fromAddress, toAddress);
//
//            //Auto-Publish on Allocation Sheet by Ogie Dec. 08, 2010
//			if (userOrder.getAutoPublishAlloc() != null && userOrder.getAutoPublishAlloc().equals("1")){
//				allocationSheetService.markAsSaved(user.getUserId(), user.getUserId(), order.getOrderId());
//				allocationSheetService.updatePublishOrder(order.getOrderId(),finalizedBy);
//				
//				String fromAddress2 = StringUtil.isNullOrEmpty(user.getPcEmail()) == false ? user.getPcEmail() : user.getMobileEmail();
//				String toAddress2[] = new String[1];
//				toAddress2[0] = StringUtil.isNullOrEmpty(userOrder.getPcEmail()) == false ? userOrder.getPcEmail() : userOrder.getMobileEmail();
//				if (!StringUtil.isNullOrEmpty(toAddress2[0]))
//					allocationSheetService.sendMailNotification(order.getDeliveryDate(), "Publish", user.getUserName(), fromAddress2, toAddress2);
//			}
//
//		}
		
		List<Order> finalizedOrderFromOrderIdList = orderSheetService.updateFinalizeOrder(user,
				pubOrder, sellerMap);
		
		StringBuilder sbMessage = null;
		if (CollectionUtils.isNotEmpty(finalizedOrderFromOrderIdList)){
			//you have some records that are already finalized.
			//report the situation to the user
			sbMessage = OrderSheetUtil.createOrderListMessage("ordersheet.concurrent.finalize.finalizedFailed", 
					finalizedOrderFromOrderIdList, sellerNameMap, buyerNameMap);
		}
		
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		allOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);
		SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
		
		//return super.onSubmit(request, response, command, errors);
		ModelAndView mav = new ModelAndView("json");		
		if (sbMessage != null){
			mav.addObject("infoMsg", sbMessage.toString());
		}
        return mav;
	}



	@SuppressWarnings("unchecked")
	private Map<Integer, String> getBuyerNameMap(HttpServletRequest request) {
		Map<Integer,String> buyerNameMap = FilterIDUtil.toIDNameMap((List<FilteredIDs>) 
				SessionHelper.getAttribute(request, SessionParamConstants.SORTED_BUYERS));
		return buyerNameMap;
	}

//	private Map<Integer, String> getSellerNameMap(User user) {
//		Map<Integer,String> sellerNameMap = new HashMap<Integer,String>();
//		if(user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)){
//			sellerNameMap = FilterIDUtil.toIDNameMap(user.getPreference().getSortedSellers());
//		} else {
//			sellerNameMap.put(user.getUserId(), user.getName());
//		}
//		return sellerNameMap;
//	}

	private Map<Integer, String> getSellerNameMap(Map<Integer, User> sellerMap) {
		Map<Integer, String> sellerNameMap = new HashMap<Integer, String>();
		if (MapUtils.isEmpty(sellerMap)) {
			return sellerNameMap;
		}
		for (Map.Entry<Integer, User> entry : sellerMap.entrySet()) {
			sellerNameMap.put(entry.getKey(),
					StringUtils.trimToEmpty(entry.getValue().getName()));
		}
		return sellerNameMap;
	}


	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
}
