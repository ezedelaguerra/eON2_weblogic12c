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
 */
package com.freshremix.webapp.controller.allocationsheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
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
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.ConcurrencyUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

public class AllocationSheetFinalizeController extends SimpleFormController {

	private AllocationSheetService allocationSheetService;
	private UserDao usersInfoDaos;
	
	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		List<Order> selectedOrders = allocationSheetService.getSelectedOrders(allOrdersMap, osParam);
		List<Order> pubOrder = allocationSheetService.getPublishedOrders(selectedOrders);
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		Integer finalizedBy = user.getUserId();

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
		
		concurrencyResponse = ConcurrencyUtil.validateSellerAllocationSheet(user, 
				pubOrder, currentOrders, OrderConstants.ACTION_FINALIZE, buyerList);

		//ENHANCEMENT END 20120727: Rhoda Redmine 354
		//DELETE 20120727: Rhoda Redmine 354
//		for (Order order : pubOrder) {
		
		//ENHANCEMENT START 20120727: Rhoda Redmine 354
		if(concurrencyResponse.getForUpdateOrders() != null && 
				concurrencyResponse.getForUpdateOrders().size() > 0){
			allocationSheetService.updateFinalizeOrder(user, concurrencyResponse.getForUpdateOrders());
		}
		//ENHANCEMENT END 20120727: Rhoda Redmine 354
		
		//DELETE START 20120727: Rhoda Redmine 354
//		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
//		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
//		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		//DELETE END 20120727: Rhoda Redmine 354
		allOrders = allocationSheetService.getAllOrders(buyerIds, dateList, sellerIds);
		SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
		
		//DELETE 20120727: Rhoda Redmine 354
//		return super.onSubmit(request, response, command, errors);
		//ENHANCEMENT START 20120727: Rhoda Redmine 354
		ModelAndView mav = new ModelAndView("json");
		mav.addObject("concurrencyMsg", concurrencyResponse.getConcurrencyMsg());
		mav.addObject("action", OrderConstants.ACTION_FINALIZE);
		mav.addObject("concurrencyResp", concurrencyResponse);
		mav.addObject("isReceivedFinalized", concurrencyResponse.getIsReceivedFinalized());
		mav.addObject("isAllocFinalized", concurrencyResponse.getIsAllocFinalized());
		return mav;
		//ENHANCEMENT END 20120727: Rhoda Redmine 354
	}

	
	public void setAllocationSheetService(
			AllocationSheetService allocationSheetService) {
		this.allocationSheetService = allocationSheetService;
	}
	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
	
	
}
