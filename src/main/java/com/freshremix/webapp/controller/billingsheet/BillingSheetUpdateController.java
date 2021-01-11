package com.freshremix.webapp.controller.billingsheet;

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
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.BillingSheetService;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

public class BillingSheetUpdateController extends SimpleFormController {

	private BillingSheetService billingSheetService;
	
	public void setBillingSheetService(BillingSheetService billingSheetService) {
		this.billingSheetService = billingSheetService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
//		if (SessionHelper.isSessionExpired(request)) {
//			Map<String,Object> model = new HashMap<String,Object>();
//			model.put("isSessionExpired", Boolean.TRUE);
//			return new ModelAndView("json",model);
//		}
		
		String json = request.getParameter("_gt_json");
		
		Serializer serializer = new JsonSerializer();
		OrderForm orderForm = (OrderForm) serializer.deserialize(json, OrderForm.class);
		
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		OrderDetails od = new OrderDetails();
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		
		if (!osParam.isAllDatesView())
			orderForm.setBuyerColumnIds(buyerIds);
		else {
			List<String> deliveryDates = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
			orderForm.setDateColumnIds(deliveryDates);
		}
			
//		System.out.println("Inserted " + orderForm.getInsertedOrders());
//		System.out.println("Updated " + orderForm.getUpdatedOrders());
//		System.out.println("Deleted " + orderForm.getDeletedOrders());
		
		od.setBuyerIds(buyerIds);
		od.setUser(user);
		od.setDeliveryDate((String) SessionHelper.getAttribute(request, SessionParamConstants.DELIVERY_DATE_PARAM));
		od.setSellerId(user.getUserId().intValue());
		od.setSheetType(osParam.getSheetTypeId());
		od.setSkuCategoryId(osParam.getCategoryId());
		od.setStartDate(osParam.getStartDate());
		od.setEndDate(osParam.getEndDate());
		od.setAllDatesView(osParam.isAllDatesView());
		od.setDatesViewBuyerID(Integer.valueOf(osParam.getDatesViewBuyerID()));
		
		Map<Integer, AkadenSKU> skuObjMap = this.getSessionSKUObjMap(request);

		List<Order> allOrders =  getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		
		if(orderForm.getAction().equals(OrderConstants.ACTION_SAVE)) {
			billingSheetService.saveOrder(orderForm, od, OrderSheetUtil.convertToOrderMap(allOrders), skuObjMap);
		}
		
		ModelAndView mav = new ModelAndView("json");
		
		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		allOrders = billingSheetService.getBillingOrders(sellerIds, buyerIds, dateList, null);
		SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
		
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
	
	@SuppressWarnings("unchecked")
	private Map<Integer, AkadenSKU> getSessionSKUObjMap(HttpServletRequest request) {
		return (Map<Integer, AkadenSKU>)SessionHelper.getAttribute(request, SessionParamConstants.SKU_OBJ_MAP_PARAM);
	}

}