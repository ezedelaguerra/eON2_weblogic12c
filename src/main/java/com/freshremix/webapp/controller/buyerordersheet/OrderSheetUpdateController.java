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
package com.freshremix.webapp.controller.buyerordersheet;

import java.util.ArrayList;
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
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.CompositeKey;
import com.freshremix.model.ConcurrencyResponse;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SKUBA;
import com.freshremix.model.User;
import com.freshremix.service.BuyerOrderSheetService;
import com.freshremix.service.MessageI18NService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.util.ConcurrencyUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

public class OrderSheetUpdateController extends SimpleFormController {

	private BuyerOrderSheetService buyerOrderSheetService;
	private OrderSheetService orderSheetService;
	private MessageI18NService messageI18NService;

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	
	public void setBuyerOrderSheetService(
			BuyerOrderSheetService buyerOrderSheetService) {
		this.buyerOrderSheetService = buyerOrderSheetService;
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
		
		OrderSheetParam osParam = (OrderSheetParam) SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM);
		
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		String deliveryDate = (String) SessionHelper.getAttribute(request, SessionParamConstants.DELIVERY_DATE_PARAM);
		
		List<Order> allOrders =  getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);

		//ENHANCEMENT START 20120608: Rhoda Redmine 68
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
		//ENHANCEMENT END 20120608: Rhoda Redmine 68
		
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		
		if (!osParam.isAllDatesView()) {
			orderForm.setBuyerColumnIds(buyerIds);
		} else {
			orderForm.setDateColumnIds(dateList);
		}
			
		OrderDetails od = new OrderDetails();
		od.setUser(user);
		od.setDeliveryDate(deliveryDate);
		od.setBuyerId(new Integer(osParam.getDatesViewBuyerID()));
		od.setBuyerIds(buyerIds);
		od.setSellerIds(OrderSheetUtil.toList(osParam.getSelectedSellerID()));
		od.setSheetType(osParam.getSheetTypeId());
		od.setSkuCategoryId(osParam.getCategoryId());
		od.setStartDate(osParam.getStartDate());
		od.setEndDate(osParam.getEndDate());
		od.setAllDatesView(osParam.isAllDatesView());
		od.setDatesViewBuyerID(Integer.valueOf(osParam.getDatesViewBuyerID()));
		
		ConcurrencyResponse concurrencyResponse = new ConcurrencyResponse();
		
		boolean isOrderSheetModified = false;
		String sbMessage = "";

		if (orderForm.getAction().equals(OrderConstants.ACTION_SAVE)) {
			
			/*TODO removed BuyerList if sortedBuyers are already set to user preference */
			List<FilteredIDs> buyerList = new ArrayList<FilteredIDs>();
			if(RolesUtil.isUserRoleBuyerAdmin(user)){
				buyerList = (List<FilteredIDs>) 
					SessionHelper.getAttribute(request, SessionParamConstants.SORTED_BUYERS);
			} else {
				buyerList.add(new FilteredIDs(user.getUserId().toString(), user.getUserName()));
			}
			
			//ENHANCEMENT START 20120608: Rhoda Redmine 68
			List<Order> currentOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);
			
			concurrencyResponse = ConcurrencyUtil.validateBuyerOrderSheet(
					od.getUser(), allOrders, currentOrders,
					OrderConstants.ACTION_SAVE, buyerList);
			
			List<SKUBA> skuBAfromDB = buyerOrderSheetService.getPublishedSKUBA(null, sellerIds, buyerIds, dateList, od.getSkuCategoryId(), null, null);
			Map<CompositeKey<Integer>, SKUBA> skuBAMapFromDB = OrderSheetUtil.convertToSKUIdMap(skuBAfromDB);
			
			Map<CompositeKey<Integer>, SKUBA> skuBAMapFromSession = (Map<CompositeKey<Integer>, SKUBA>) SessionHelper
					.getAttribute(request,
							SessionParamConstants.SKU_OBJ_MAP_PARAM);

			
			
				
				try {
					if (StringUtils.isBlank(concurrencyResponse.getConcurrencyMsg())) {
						
						
						concurrencyResponse = buyerOrderSheetService
								.saveProcess(orderForm, od, concurrencyResponse
										.getForUpdateOrders(), skuBAMapFromDB, skuBAMapFromSession);

						if (!StringUtil.isNullOrEmpty((concurrencyResponse
								.getConcurrencyMsg()))) {
							String concurrencyMsg = messageI18NService
									.getPropertyMessage(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_SKU_ERROR)
									+ StringUtil
											.nullToBlank(concurrencyResponse
													.getConcurrencyMsg());
							concurrencyResponse
									.setConcurrencyMsg(concurrencyMsg);
							concurrencyResponse.setAction(OrderConstants.ACTION_SAVE);

						}
					}
				} catch (ServiceException e) {
					sbMessage = handleServiceException(request, user, e);
				}
			
			
			//ENHANCEMENT END 20120608: Rhoda Redmine 68
		}

		String enableBAPublish = null;
		boolean isBAPublished = buyerOrderSheetService.isBuyerPublished(user);
		if (isBAPublished) {
			enableBAPublish = String.valueOf(isBAPublished);
		}

		if (RolesUtil.isUserRoleBuyerAdmin(user)) {
			allOrders = buyerOrderSheetService.getPublishedOrders(buyerIds,
					dateList, sellerIds, null, null);
		} else {
			allOrders = buyerOrderSheetService.getPublishedOrders(buyerIds,
					dateList, sellerIds, user.getUserId(), enableBAPublish);
		}
		
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		OrderSheetParam orderSheetParam = (OrderSheetParam) SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM);
		List<Integer> selectedOrderIds = orderSheetService.getSelectedOrderIds(allOrdersMap, orderSheetParam);
		
		SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
		SessionHelper.setAttribute(request, SessionParamConstants.SELECTED_ORDERS_PARAM, selectedOrderIds);

		ModelAndView mav = new ModelAndView("json");		
		
		mav.addObject("concurrencyResp", concurrencyResponse);
//		if (isOrderSheetModified) {
//			mav.addObject("isOrderSheetModified", messageI18NService
//					.getPropertyMessage(ORDERSHEET_CONCURRENT_SAVE_SKUCHANGED));
//		}
		if (StringUtils.isNotBlank(sbMessage)){
			mav.addObject("fail", "true");
			mav.addObject("failMessage", sbMessage);
		}
		
		return mav;
	}


	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
	
	private String handleServiceException(HttpServletRequest request,
			User user, ServiceException e) {
		String errorCode = e.getErr().getErrorCode();
		StringBuilder sbMessage = new StringBuilder();
		
		if (errorCode.equals(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_SKU_HAS_QTY_FAILED)) {
			Map<String, Object> exceptionContext = e.getExceptionContext();
			String propertyMessage = messageI18NService.getPropertyMessage(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_SKU_HAS_QTY_FAILED);
			sbMessage.append(propertyMessage);
			sbMessage.append(exceptionContext.get(OrderConstants.FAILED_SKU));
		}else if (errorCode.equals(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_MAX_LIMIT_ERROR)) {
			Map<String, Object> exceptionContext = e.getExceptionContext();
			String propertyMessage = messageI18NService.getPropertyMessage(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_MAX_LIMIT_ERROR);
			sbMessage.append(propertyMessage);
			sbMessage.append(exceptionContext.get(OrderConstants.FAILED_SKU));
		}
		return sbMessage.toString();
	}	
}