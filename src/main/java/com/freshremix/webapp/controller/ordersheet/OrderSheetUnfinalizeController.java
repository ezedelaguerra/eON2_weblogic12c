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
 * date       		name        version	    changes
 * ------------------------------------------------------------------------------
 * 20120727			Rhoda		v11			354 - Unfinalize Order and Finalize Alloc concurrency
 * 20120919			Rhoda		v12			Redmine 1070 - Seller can unfinalize the Order Sheet after Seller finalized Allocation Sheet
 */
package com.freshremix.webapp.controller.ordersheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.OrderConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.dao.UserDao;
import com.freshremix.model.ConcurrencyResponse;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.AllocationSheetService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.ConcurrencyUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

public class OrderSheetUnfinalizeController extends SimpleFormController {

	private AllocationSheetService allocationSheetService;
	private OrderSheetService orderSheetService;
	private UserDao usersInfoDaos;
	private EONLocale eonLocale;
	
	public void setAllocationSheetService(
			AllocationSheetService allocationSheetService) {
		this.allocationSheetService = allocationSheetService;
	}
	
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	
	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}

	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		List<Order> selectedOrders = orderSheetService.getSelectedOrders(allOrdersMap, osParam);
		List<Order> finOrders = orderSheetService.getFinalizedOrders(selectedOrders);
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		//ENHANCEMENT START 20120727: Rhoda Redmine 354
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		List<Order> currentOrders = allocationSheetService.getAllOrders(buyerIds, dateList, sellerIds);
		
		/*TODO removed BuyerList if sortedBuyers are already set to user preference */
		List<FilteredIDs> buyerList = new ArrayList<FilteredIDs>();
		buyerList = (List<FilteredIDs>) 
			SessionHelper.getAttribute(request, SessionParamConstants.SORTED_BUYERS);
		
		ConcurrencyResponse concurrencyResponse = new ConcurrencyResponse();
		
		concurrencyResponse = ConcurrencyUtil.validateSellerOrderSheet(user, 
				selectedOrders, currentOrders, OrderConstants.ACTION_UNFINALIZE, buyerList);

		//ENHANCEMENT END 20120727: Rhoda Redmine 354
		//DELETE START 20120727: Rhoda Redmine 354
		//ENHANCEMENT START 20120919: Rhoda Redmine 1070
		Order _order = orderSheetService.combineSelectedOrders(finOrders);
		if (!NumberUtil.isNullOrZero(_order.getAllocationFinalizedBy())) {
			//String errorMsg = getMessageSourceAccessor().getMessage("ordersheet.unfinalizedFailed", request.getLocale());
			String errorMsg = StringUtil.toUTF8String(getMessageSourceAccessor().getMessage(
					"ordersheet.unfinalizedFailed", eonLocale.getLocale()));
			errors.addError(new ObjectError ("table", errorMsg));
			return super.onSubmit(request, response, command, errors);
		} else {
		//ENHANCEMENT END 20120919: Rhoda Redmine 1070
		//DELETE END 20120727: Rhoda Redmine 354
			//DELETE 20120727: Rhoda Redmine 354
//			for (Order order : finOrders) {
			//ENHANCEMENT START 20120727: Rhoda Redmine 354
			if (concurrencyResponse.getForUpdateOrders() != null &&
					concurrencyResponse.getForUpdateOrders().size() != 0){
				orderSheetService.updateUnFinalizeOrder(user, concurrencyResponse.getForUpdateOrders());
			}
			//ENHANCEMENT END 20120727: Rhoda Redmine 354
			//DELETE START 20120727: Rhoda Redmine 354
//			List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
//			List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
//			List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
			//DELETE END 20120727: Rhoda Redmine 354
			allOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);
			SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
			
		//DELETE 20120727: Rhoda Redmine 354
//		}
		
		//DELETION 20120727: Rhoda Redmine 68
//		return super.onSubmit(request, response, command, errors);
		//ENHANCEMENT START 20120727: Rhoda Redmine 68
		ModelAndView mav = new ModelAndView("json");
		mav.addObject("concurrencyResp", concurrencyResponse);
		return mav;
		//ENHANCEMENT END 20120727: Rhoda Redmine 68
		//ENHANCEMENT 20120919: Rhoda Redmine 1070
		}
	}


	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
}
