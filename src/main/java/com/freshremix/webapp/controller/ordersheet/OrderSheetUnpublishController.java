package com.freshremix.webapp.controller.ordersheet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class OrderSheetUnpublishController extends SimpleFormController {

	private OrderSheetService orderSheetService;
	
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		List<Order> selectedOrders = orderSheetService.getSelectedOrders(allOrdersMap, osParam);
		List<Order> pubOrder = orderSheetService.getPublishedOrders(selectedOrders);
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		List<Integer> selectedOrderIds = orderSheetService.getSelectedOrderIds(allOrdersMap, osParam);
		
		//check if sheet has order quantities
		//assuming no negative quantities
		BigDecimal sumQuantities = orderSheetService.getSumOrderQuantities(selectedOrderIds);
		BigDecimal zero = new BigDecimal(0);

		//check if sum == 0
		if(sumQuantities == null || sumQuantities.compareTo(zero) == 0 ){
			model.put("hasQuantities", "0");
		}else{
			model.put("hasQuantities", "1");
			return new ModelAndView("json",model);
		}
		
		orderSheetService.updateUnpublishOrder(user,pubOrder);
		
		
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		allOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);
		SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
		
		return new ModelAndView("json",model);
		//return super.onSubmit(request, response, command, errors);
	}

	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}

}