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
import com.freshremix.util.SessionHelper;

/**
 * @author Pammie
 *
 */
public class BuyerBillingSheetGTPriceController implements Controller {
	private BillingSheetService billingSheetService;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		List<Integer> orderIds = new ArrayList<Integer>();
		Map<String,Object> model = new HashMap<String,Object>();
		
		for (Order order : allOrders) {
			orderIds.add(order.getOrderId());
		}
		
		if (orderIds.size() > 0) {
			GrandTotalPrices grandTotalPrices = billingSheetService.getGTPriceAllOrders(orderIds);
			
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

	public void setBillingSheetService(BillingSheetService billingSheetService) {
		this.billingSheetService = billingSheetService;
	}
}
