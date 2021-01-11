package com.freshremix.webapp.controller.ordersheet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.OrderSheetService;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

public class OrderSheetPublishController extends SimpleFormController {

	private OrderSheetService orderSheetService;
	
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		//ap<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		//List<Order> selectedOrders = orderSheetService.getSelectedOrders(allOrdersMap, osParam);
		//List<Order> savedOrders = orderSheetService.getSavedOrders(selectedOrders);
		List<Order> savedOrders = orderSheetService.getSavedOrders(allOrders);
		
		orderSheetService.updatePublishOrder(user, savedOrders);
		
		osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		//List<Order> allOrders = orderSheetService.getSavedOrders(buyerIds, dateList, sellerIds);
		allOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);
		SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
		
		return super.onSubmit(request, response, command, errors);
	}

	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
}
