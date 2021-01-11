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
 * May 18, 2010		gilwen		
 */
package com.freshremix.webapp.controller.ordersheet;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author gilwen
 *
 */
public class AddSkuController implements Controller {

//	private SKUGroupService skuGroupService;
//	private UserDao usersInfoDaos;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("json");
		
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		OrderSheetParam param = (OrderSheetParam)SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM);
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		
		mav.addObject("sellerId", user.getUserId());
		mav.addObject("companyname", user.getCompany().getShortName());
		mav.addObject("lockflag", lockQuantities(request, param, user, allOrdersMap));
		
		return mav;
	}
	
	protected String lockQuantities(HttpServletRequest request, OrderSheetParam param, 
			User user, Map<String, Order> allOrdersMap) throws Exception {
		JSONObject json = new JSONObject();
		DealingPattern dp = (DealingPattern)SessionHelper.getAttribute(request, SessionParamConstants.DEALING_PATTERN_PARAM);
		Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap = dp.getSellerToBuyerDPMap();
		
		if (param.isAllDatesView()) {
			List<String> deliveryDates = DateFormatter.getDateList(param.getStartDate(), param.getEndDate());
			Integer selectedBuyer = Integer.valueOf(param.getDatesViewBuyerID());
			for (String deliveryDate : deliveryDates) {
				Map<String, List<Integer>> dateDP = sellerToBuyerDPMap.get(deliveryDate);
				List<Integer> buyersDP = dateDP.get(user.getUserId().toString());
				String key = "Q_" + deliveryDate;
				String key2 = deliveryDate + "_" + selectedBuyer.toString() + "_" + user.getUserId().toString();
				Order order = allOrdersMap.get(key2);
				boolean isOrderNotFinalized = NumberUtil.isNullOrZero(order.getOrderFinalizedBy());
				if (buyersDP.contains(selectedBuyer) && isOrderNotFinalized) json.put(key, "0");
				else json.put(key, "1");
			}
		} else {
			String deliveryDate = param.getSelectedDate();
			Map<String, List<Integer>> dateDP = sellerToBuyerDPMap.get(deliveryDate);
			List<Integer> buyersDP = dateDP.get(user.getUserId().toString());
			List<Integer> selectedBuyers = OrderSheetUtil.toList(param.getSelectedBuyerID());
			for (Integer buyer : selectedBuyers) {
				String key = "Q_" + buyer.toString();
				String key2 = deliveryDate + "_" + buyer.toString() + "_" + user.getUserId().toString();
				Order order = allOrdersMap.get(key2);
				if (order == null) {
					json.put(key, "1");
					continue;
				} else {
					boolean isOrderNotFinalized = NumberUtil.isNullOrZero(order.getOrderFinalizedBy());
					if (buyersDP.contains(buyer) && isOrderNotFinalized) json.put(key, "0");
					else json.put(key, "1");
				}
			}
		}
		return json.toString();
	}

//	public void setSkuGroupService(SKUGroupService skuGroupService) {
//		this.skuGroupService = skuGroupService;
//	}
//
//	public void setUsersInfoDaos(UserDao usersInfoDaos) {
//		this.usersInfoDaos = usersInfoDaos;
//	}
	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
}