package com.freshremix.webapp.controller.billingsheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.dao.UserDao;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.AllocationSheetService;
import com.freshremix.service.BillingSheetService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

public class BillingSheetUnfinalizeController extends SimpleFormController {

	private BillingSheetService billingSheetService;
	private OrderSheetService orderSheetService;
	private UserDao usersInfoDaos;
	
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	
	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}

	public void setBillingSheetService(BillingSheetService billingSheetService) {
		this.billingSheetService = billingSheetService;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		List<Order> selectedOrders = orderSheetService.getSelectedOrders(allOrdersMap, osParam);
		//List<Order> pubOrder = orderSheetService.getPublishedOrders(selectedOrders);
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		Integer unFinalizedBy = user.getUserId();
		
		for (Order order : selectedOrders) {
			billingSheetService.updateUnfinalizeBilling(order.getOrderId(), unFinalizedBy);
			
			User userOrder = usersInfoDaos.getUserById(order.getBuyerId());
			String fromAddress = StringUtil.isNullOrEmpty(user.getPcEmail()) == false ? user.getPcEmail() : user.getMobileEmail();
			String toAddress[] = new String[1];
			toAddress[0] = StringUtil.isNullOrEmpty(userOrder.getPcEmail()) == false ? userOrder.getPcEmail() : userOrder.getMobileEmail();
			if (!StringUtil.isNullOrEmpty(toAddress[0]))
				billingSheetService.sendMailNotification(order.getDeliveryDate(), "Unfinalize", user.getUserName(), fromAddress, toAddress);
		}
		
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		allOrders = billingSheetService.getBillingOrders(sellerIds, buyerIds, dateList, null);
		SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
		
		return super.onSubmit(request, response, command, errors);
	}

	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
}
