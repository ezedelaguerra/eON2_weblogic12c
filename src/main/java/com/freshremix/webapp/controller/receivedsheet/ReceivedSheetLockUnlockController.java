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
 * date       		name            version		changes
 * ------------------------------------------------------------------------------
 * Mar 30, 2010		nvelasquez		
 * 20120608			Rhoda			v11			Redmine 68 - Concurrency 
 */
package com.freshremix.webapp.controller.receivedsheet;

import java.util.ArrayList;
import java.util.List;

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
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.ReceivedSheetService;
import com.freshremix.util.ConcurrencyUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author nvelasquez
 *
 */
public class ReceivedSheetLockUnlockController extends SimpleFormController {
	
	private ReceivedSheetService receivedSheetService;
	private AllocationSheetService allocationSheetService;
	private UserDao usersInfoDaos;
	//ENHANCEMENT START 20120608: Rhoda Redmine 68
	private OrderSheetService orderSheetService;

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	//ENHANCEMENT END 20120608: Rhoda Redmine 68
	
	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}

	public void setAllocationSheetService(
			AllocationSheetService allocationSheetService) {
		this.allocationSheetService = allocationSheetService;
	}
	
	public void setReceivedSheetService(ReceivedSheetService receivedSheetService) {
		this.receivedSheetService = receivedSheetService;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		Integer userId = user.getUserId().intValue();
		
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		osParam.setCheckDBOrder(true);
		SessionHelper.setAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM, osParam);
		
		List<Integer> selectedOrders = this.getIntegerListFromSession(request, SessionParamConstants.SELECTED_ORDERS_PARAM);
		List<Order> notFinalizedOrders = (List<Order>) SessionHelper.getAttribute(request, SessionParamConstants.NOT_FINALIZED_ORDERS_PARAM);
		
		String lockFLag = request.getParameter("lock");
		ConcurrencyResponse concurrencyResponse = new ConcurrencyResponse();

		//ENHANCEMENT START 20120608: Rhoda Redmine 68
		String startDate = osParam.getStartDate();
		String endDate = osParam.getEndDate();
		List<String> dateList = DateFormatter.getDateList(startDate, endDate);
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		//ENHANCEMENT END 20120608: Rhoda Redmine 68
		
		if(lockFLag.equals("Y")){
			
			//ENHANCEMENT START 20120608: Rhoda Redmine 68
			List<Order> currentOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);
			
			concurrencyResponse = ConcurrencyUtil.validateBuyerReceivedSheet(user, 
					notFinalizedOrders, currentOrders, OrderConstants.ACTION_FINALIZE);
			//ENHANCEMENT END 20120608: Rhoda Redmine 68
			
			if (concurrencyResponse.getForUpdateOrders() != null &&
					concurrencyResponse.getForUpdateOrders().size() != 0){
				receivedSheetService.updateLockReceived(OrderSheetUtil.getOrderIdList(
						concurrencyResponse.getForUpdateOrders()), userId);


				allocationSheetService.updateFinalizeOrder(user, getOrdersToFinalize(concurrencyResponse.getForUpdateOrders()));

				
			}
			
		}
		else if (lockFLag.equals("N")){
			receivedSheetService.updateUnlockReceived(selectedOrders, userId);
		}
		
		//DELETION START 20120608: Rhoda Redmine 68
//		String startDate = osParam.getStartDate();
//		String endDate = osParam.getEndDate();
//		List<String> dateList = DateFormatter.getDateList(startDate, endDate);
//		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
//		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		//DELETION START 20120608: Rhoda Redmine 68
		/* reload allOrders */
		List<Order>  allOrders = receivedSheetService.getPublishedOrdersForReceived(buyerIds, dateList, sellerIds);
		SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
		
		ModelAndView mav = new ModelAndView("json");
		
		mav.addObject("concurrencyMsg", concurrencyResponse.getConcurrencyMsg());
		return mav;
	}

	private List<Order> getOrdersToFinalize(List<Order> orders)
	{
		List<Order> ordersToFinalize = new ArrayList<Order>();
		for(Order order: orders){
			if(NumberUtil.isNullOrZero(order.getAllocationFinalizedBy())){
				ordersToFinalize.add(order);
			}
		}
		return ordersToFinalize;
	}
	
	@SuppressWarnings("unchecked")
	private List<Integer> getIntegerListFromSession(HttpServletRequest request, String paramName) {
		return (List<Integer>) SessionHelper.getAttribute(request, paramName);
	}
	
}
