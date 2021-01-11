package com.freshremix.webapp.controller.allocationsheet;

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
import com.freshremix.service.BillingSheetService;
import com.freshremix.service.ReceivedSheetService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.ConcurrencyUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

public class AllocationSheetUnfinalizeController extends SimpleFormController{

	private AllocationSheetService allocationSheetService;
	private UserDao usersInfoDaos;
	private ReceivedSheetService receivedSheetService;
	private BillingSheetService billingSheetService;
	private EONLocale eonLocale;
	
	public void setBillingSheetService(BillingSheetService billingSheetService) {
		this.billingSheetService = billingSheetService;
	}

	public void setReceivedSheetService(ReceivedSheetService receivedSheetService) {
		this.receivedSheetService = receivedSheetService;
	}
	
	public void setAllocationSheetService(
			AllocationSheetService allocationSheetService) {
		this.allocationSheetService = allocationSheetService;
	}
	
	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}

	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		List<Order> selectedOrders = allocationSheetService.getSelectedOrders(allOrdersMap, osParam);
		List<Order> finOrder = allocationSheetService.getFinalizedOrders(selectedOrders);
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		Integer unfinalizeBy = user.getUserId();
		List<Order> pubOrder = allocationSheetService.getPublishedOrders(selectedOrders);

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
				selectedOrders, currentOrders, OrderConstants.ACTION_UNFINALIZE, buyerList);


		//List<Order> approvedOrdersForReceived = getApprovedOrdersForReceived(finOrder);
		List<Order> approvedOrdersForBilling = getApprovedOrdersForBilling(finOrder);
		if (approvedOrdersForBilling != null && approvedOrdersForBilling.size() > 0) {
			//String errorMsg = getMessageSourceAccessor().getMessage("allocationsheet.unfinalizedFailed", request.getLocale());
			String errorMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
					getMessage("allocationsheet.unfinalizedFailed", eonLocale.getLocale()));	
			errors.addError(new ObjectError ("table", errorMsg));
		} else {
			if(concurrencyResponse.getForUpdateOrders() != null && 
					concurrencyResponse.getForUpdateOrders().size() > 0){
				allocationSheetService.updateUnfinalizeOrder(finOrder, user);
			}
		

			allOrders = allocationSheetService.getAllOrders(buyerIds, dateList, sellerIds);
			SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
		}
		
		//return super.onSubmit(request, response, command, errors);
		ModelAndView mav = new ModelAndView("json");
		mav.addObject("concurrencyMsg", concurrencyResponse.getConcurrencyMsg());
		mav.addObject("action", OrderConstants.ACTION_UNFINALIZE);
		mav.addObject("concurrencyResp", concurrencyResponse);
		mav.addObject("isReceivedFinalized", concurrencyResponse.getIsReceivedFinalized());
		mav.addObject("isAllocUnFinalized", !concurrencyResponse.getIsAllocFinalized());
		return mav;
	}


	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
	
	private List<Order> getApprovedOrdersForReceived(List<Order> finOrder) {
		List<Integer> orderId = new ArrayList<Integer>();
		for (Order order : finOrder) {
			orderId.add(order.getOrderId());
		}
		return receivedSheetService.getApprovedOrdersForReceived(orderId);
	}
	
	
	private List<Order> getApprovedOrdersForBilling(List<Order> finOrder) {
		List<Integer> orderId = new ArrayList<Integer>();
		for (Order order : finOrder) {
			orderId.add(order.getOrderId());
		}
		return billingSheetService.getApprovedOrdersForBilling(orderId);
	}

}
