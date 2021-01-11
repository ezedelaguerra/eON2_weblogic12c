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
 * Mar 29, 2010		Jovino Balunan		
 */
package com.freshremix.webapp.controller.akadensheet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.AkadenSessionConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.service.AkadenService;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author Jovino Balunan
 *
 */
public class AkadenSheetGTPriceController implements Controller {

	private AkadenService akadenService;

	public void setAkadenService(AkadenService akadenService) {
		this.akadenService = akadenService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		OrderSheetParam akadenSheetParams = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		List<Integer> orderIds = new ArrayList<Integer>();
		Map<String,Object> model = new HashMap<String,Object>();
		
		for (Order order : allOrders) {
			if (!NumberUtil.isNullOrZero(order.getAkadenSavedBy()))
				orderIds.add(order.getOrderId());
		}
		if (orderIds.size() > 0) {
			List<Integer> buyerId = OrderSheetUtil.toList(akadenSheetParams.getSelectedBuyerID());
			String startDate = akadenSheetParams.getStartDate();
			String endDate = akadenSheetParams.getEndDate();
			List<String> dateList = DateFormatter.getDateList(startDate, endDate);
//			BigDecimal totalPrice = akadenService.getGTPriceByOrderId(orderIds, dateList, buyerId);
			GrandTotalPrices grandTotalPrices = akadenService.getGTPriceAllOrders(orderIds);
			
			if (grandTotalPrices != null) {
				model.put("gtPriceWOTax", grandTotalPrices.getPriceWithoutTax());
				model.put("gtPriceWTax", grandTotalPrices.getPriceWithTax());
			}else {
				model.put("gtPriceWOTax", 0);
				model.put("gtPriceWTax", 0);
			}
		}else{
			model.put("gtPriceWOTax", 0);
			model.put("gtPriceWTax", 0);
		}
		
		return new ModelAndView("json",model);
	}

	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
}