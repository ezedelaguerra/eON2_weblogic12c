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
 * Jul 2, 2010		raquino		
 */
package com.freshremix.webapp.controller.billingsheet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;
import com.freshremix.service.BillingSheetService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author raquino
 *
 */
public class BillingSheetGTPriceController implements Controller {

	private BillingSheetService billingSheetService;

	public void setBillingSheetService(BillingSheetService billingSheetService) {
		this.billingSheetService = billingSheetService;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		List<Integer> orderId = new ArrayList<Integer>();
		Map<String,Object> model = new HashMap<String,Object>();
		
		for (Order order : allOrders) {
			if (!NumberUtil.isNullOrZero(order.getAllocationFinalizedBy()))
				orderId.add(order.getOrderId());
		}
		if (orderId.size() > 0) {
			//List<Integer> buyerId = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
			//String startDate = osParam.getStartDate();
			//String endDate = osParam.getEndDate();
			//List<String> dateList = DateFormatter.getDateList(startDate, endDate);
			//BigDecimal totalPrice = orderSheetService.getGTPrice(orderId, dateList, buyerId);
			GrandTotalPrices grandTotalPrices = billingSheetService.getGTPriceAllOrders(orderId);
			
			if (grandTotalPrices != null) {
				model.put("gtPriceWOTax", grandTotalPrices.getPriceWithoutTax());
				model.put("gtPriceWTax", grandTotalPrices.getPriceWithTax());
			}
		}
		return new ModelAndView("json",model);
	}
	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
}
