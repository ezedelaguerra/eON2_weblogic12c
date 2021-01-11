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
 * Jun 21, 2010		raquino		
 */
package com.freshremix.webapp.controller.buyerordersheet;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.dao.UserDao;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.BuyerOrderSheetService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author raquino
 *
 */
public class OrderSheetPublishController extends SimpleFormController {

	private BuyerOrderSheetService buyerOrderSheetService;
	private OrderSheetService orderSheetService;
	private UserDao usersInfoDaos;
	
	public void setBuyerOrderSheetService(
			BuyerOrderSheetService buyerOrderSheetService) {
		this.buyerOrderSheetService = buyerOrderSheetService;
	}
	
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	
	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		Integer publishBy = user.getUserId();
		
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		List<Order> selectedOrders = buyerOrderSheetService.getSelectedOrders(allOrdersMap, osParam);
		List<Order> savedOrders = orderSheetService.getSavedOrders(selectedOrders);
		
		buyerOrderSheetService.updatePublishOrderByBA(user, savedOrders);
		
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
