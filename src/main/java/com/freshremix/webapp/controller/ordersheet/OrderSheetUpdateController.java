package com.freshremix.webapp.controller.ordersheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.OrderConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.ConcurrencyResponse;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SKU;
import com.freshremix.model.User;
import com.freshremix.service.MessageI18NService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.util.ConcurrencyUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.FilterIDUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

public class OrderSheetUpdateController extends SimpleFormController {

	private OrderSheetService orderSheetService;
	private MessageI18NService messageI18NService;
	
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	
	public void setMessageI18NService(MessageI18NService messageI18NService) {
		this.messageI18NService = messageI18NService;
	}

	
	@SuppressWarnings("unchecked")
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
		
		if (!osParam.isAllDatesView())
			orderForm.setBuyerColumnIds(buyerIds);
		else {
			List<String> deliveryDates = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
			orderForm.setDateColumnIds(deliveryDates);
		}
			
		System.out.println("Inserted " + orderForm.getInsertedOrders());
		System.out.println("Updated " + orderForm.getUpdatedOrders());
		System.out.println("Deleted " + orderForm.getDeletedOrders());
		
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
		
		String sbMessage = null;
		
		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());

		List<FilteredIDs> buyerList = (List<FilteredIDs>) SessionHelper
				.getAttribute(request, SessionParamConstants.SORTED_BUYERS);

		ConcurrencyResponse concurrencyResponse = new ConcurrencyResponse();
		if(orderForm.getAction().equals(OrderConstants.ACTION_SAVE)) {
			List<Order> currentOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);

			concurrencyResponse = ConcurrencyUtil
					.validateSellerOrderSheet(user, allOrders, currentOrders,
							OrderConstants.ACTION_SAVE, buyerList);
			
			if (StringUtils.isBlank(concurrencyResponse.getConcurrencyMsg())) {
				try {
					concurrencyResponse = orderSheetService.saveOrder(orderForm, od, concurrencyResponse
									.getForUpdateOrders(), skuObjMap);
					
					if (!StringUtil.isNullOrEmpty((concurrencyResponse.getConcurrencyMsg()) )){
						String concurrencyMsg = messageI18NService.getPropertyMessage(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_SKU_ERROR)+ StringUtil.nullToBlank(concurrencyResponse.getConcurrencyMsg()); 
						concurrencyResponse.setConcurrencyMsg(concurrencyMsg);
						concurrencyResponse.setAction(OrderConstants.ACTION_SAVE);
					}
				} catch (ServiceException e) {
					sbMessage = handleServiceException(request, user, e);
				}
			}
		}
		
		ModelAndView mav = new ModelAndView("json");
		mav.addObject("concurrencyResp", concurrencyResponse);

		allOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);
		SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
		if (StringUtils.isNotBlank(sbMessage)){
			mav.addObject("fail", "true");
			mav.addObject("failMessage", sbMessage);
		}
		return mav;
	}

	@SuppressWarnings("unchecked")
	private String handleServiceException(HttpServletRequest request,
			User user, ServiceException e) {
		String errorCode = e.getErr().getErrorCode();
		StringBuilder sbMessage = new StringBuilder();
		
		if (errorCode.equals(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_FINALIZED_FAILED)) {
			Map<String, Object> exceptionContext = e.getExceptionContext();
			Map<Integer, String> sellerNameMap = getSellerNameMap(user);
			Map<Integer, String> buyerNameMap = getBuyerNameMap(request);
			
			List<Order> failedOrders = (List<Order>)exceptionContext.get(OrderConstants.FAILED_ORDERS);
			sbMessage = OrderSheetUtil.createOrderListMessage(errorCode, failedOrders,
					sellerNameMap, buyerNameMap);
			
		}
		if (errorCode.equals(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_SKU_HAS_QTY_FAILED)) {
			Map<String, Object> exceptionContext = e.getExceptionContext();
			String propertyMessage = messageI18NService.getPropertyMessage(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_SKU_HAS_QTY_FAILED);
			sbMessage.append(propertyMessage);
			sbMessage.append(exceptionContext.get(OrderConstants.FAILED_SKU));
		}
		return sbMessage.toString();
	}

	@SuppressWarnings("unchecked")
	private Map<Integer, String> getBuyerNameMap(HttpServletRequest request) {
		Map<Integer,String> buyerNameMap = FilterIDUtil.toIDNameMap((List<FilteredIDs>) 
				SessionHelper.getAttribute(request, SessionParamConstants.SORTED_BUYERS));
		return buyerNameMap;
	}

	private Map<Integer, String> getSellerNameMap(User user) {
		Map<Integer,String> sellerNameMap = new HashMap<Integer,String>();
		if(user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)){
			sellerNameMap = FilterIDUtil.toIDNameMap(user.getPreference().getSortedSellers());
		} else {
			sellerNameMap.put(user.getUserId(), user.getName());
		}
		return sellerNameMap;
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