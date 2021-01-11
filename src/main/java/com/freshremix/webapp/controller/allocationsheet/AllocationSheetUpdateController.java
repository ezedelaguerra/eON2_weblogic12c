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
 * 
 * 20120608			Rhoda		Redmine 68 - Concurrency 
 */
package com.freshremix.webapp.controller.allocationsheet;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.OrderConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.ConcurrencyResponse;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SKU;
import com.freshremix.model.User;
import com.freshremix.service.AllocationSheetService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.util.ConcurrencyUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

public class AllocationSheetUpdateController extends SimpleFormController {

	private AllocationSheetService allocationSheetService;
	//ENHANCEMENT START 20120608: Rhoda Redmine 68
	private OrderSheetService orderSheetService;

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	//ENHANCEMENT END 20120608: Rhoda Redmine 68
	
	public void setAllocationSheetService(
			AllocationSheetService allocationSheetService) {
		this.allocationSheetService = allocationSheetService;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String json = request.getParameter("_gt_json");
		
		Serializer serializer = new JsonSerializer();
		OrderForm orderForm = (OrderForm) serializer.deserialize(json, OrderForm.class);
		
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		OrderDetails od = new OrderDetails();
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		//ENHANCEMENT 20120608: Rhoda Redmine 68
		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
		
		if (!osParam.isAllDatesView())
			orderForm.setBuyerColumnIds(buyerIds);
		else {
			List<String> deliveryDates = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
			orderForm.setDateColumnIds(deliveryDates);
		}
			
		od.setBuyerIds(buyerIds);
		od.setUser(user);
		od.setDeliveryDate((String) SessionHelper.getAttribute(request, SessionParamConstants.DELIVERY_DATE_PARAM));
		od.setSellerId(user.getUserId().intValue());
		od.setSellerIds(sellerIds);
		od.setSheetType(osParam.getSheetTypeId());
		od.setSkuCategoryId(osParam.getCategoryId());
		od.setStartDate(osParam.getStartDate());
		od.setEndDate(osParam.getEndDate());
		od.setAllDatesView(osParam.isAllDatesView());
		od.setDatesViewBuyerID(Integer.valueOf(osParam.getDatesViewBuyerID()));
		
		// get dealing pattern
		DealingPattern dp = (DealingPattern) SessionHelper.getAttribute(request, SessionParamConstants.DEALING_PATTERN_PARAM);
		od.setDealingPattern(dp);
		
		Map<Integer, SKU> skuObjMap = this.getSessionSKUObjMap(request);
		List<Order> allOrders =  getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		allOrders = allocationSheetService.getToProcessOrders(allOrders);
		ConcurrencyResponse concurrencyResponse = new ConcurrencyResponse();
		
		if(orderForm.getAction().equals(OrderConstants.ACTION_SAVE)) {

			//ENHANCEMENT START 20120608: Rhoda Redmine 68
			List<Order> currentOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);
			
			concurrencyResponse = ConcurrencyUtil.validateSellerAllocationSheet(user, 
					allOrders, currentOrders, OrderConstants.ACTION_SAVE, null);
			

			if(concurrencyResponse.getForUpdateOrders() != null && 
					concurrencyResponse.getForUpdateOrders().size() > 0)
				allocationSheetService.saveOrder2(orderForm, od, OrderSheetUtil.convertToOrderMap(
						concurrencyResponse.getForUpdateOrders()), skuObjMap);
			//ENHANCEMENT END 20120608: Rhoda Redmine 68
		}
		
		ModelAndView mav = new ModelAndView("json");
		
		//DELETION 20120608: Rhoda Redmine 68
//		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
		allOrders = allocationSheetService.getAllOrders(buyerIds, dateList, sellerIds);
		SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
		
		//ENHANCEMENT START 20120727: Rhoda Redmine 354
		mav.addObject("concurrencyMsg", concurrencyResponse.getConcurrencyMsg());
		mav.addObject("action", OrderConstants.ACTION_SAVE);
		mav.addObject("isSellerOrderUnFinalized", concurrencyResponse.getIsSellerOrderUnFinalized());
		mav.addObject("isAllocFinalized", concurrencyResponse.getIsAllocFinalized());
		mav.addObject("isReceivedFinalized", concurrencyResponse.getIsReceivedFinalized());
		//ENHANCEMENT START 20120727: Rhoda Redmine 354
		return mav;
	}

	@SuppressWarnings("unchecked")
	private Map<Integer, SKU> getSessionSKUObjMap(HttpServletRequest request) {
		return (Map<Integer, SKU>)SessionHelper.getAttribute(request, SessionParamConstants.SKU_OBJ_MAP_PARAM);
	}

	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
}