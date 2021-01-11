package com.freshremix.webapp.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.Order;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.SessionHelper;

public class SellerPublishedDatesController extends SimpleFormController {

	private OrderSheetService orderSheetService;
	
	private DealingPatternService dealingPatternService;

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	
	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}
	

	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("json");

		String orderStatus = "[";
		boolean isFirstRecord = true;
		
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);

		if(StringUtils.equals(user.getEnableCalendarHighlight(), "0")){
			return mav;
		}

		String startDate = request.getParameter("dateFrom");
		String stringSellerId = request.getParameter("selectedSellers");
		String selectedBuyerId = request.getParameter("selectedBuyers");

		List<Integer> buyerIds = convertStringToList(selectedBuyerId);
		List<Integer> sellerIds = convertStringToList(stringSellerId);
		List<String> dateList = DateFormatter.getDateList(startDate, 30);
		List<Order> allOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);


		for (Iterator<Order> iterator = allOrders.iterator(); iterator
				.hasNext();) {
			Order order = (Order) iterator.next();
			orderStatus = orderStatus + getOrderStatus(order);
			if (iterator.hasNext()) {
				orderStatus = orderStatus+ ",";
			}
		}

		orderStatus = orderStatus + "]";

		mav.addObject("orderRecordsStatus", orderStatus);
		
		return mav;

	}



	private String getOrderStatus(Order order) {

		String orderStatus = "'X'";
		
		if (order.getOrderFinalizedBy() != null){
			//this means the record is published
			orderStatus = "'F'";
		} else if(order.getOrderPublishedBy()!=null){
			orderStatus = "'P'";
		}
			String orderRecordStatus = "{'orderId':'"+order.getOrderId()+"',"
				+ "'orderDate':'"+order.getDeliveryDate()+"',"
						+ "'status':"+ orderStatus+"}";
		return orderRecordStatus;
	}

	
	private List<Integer> convertStringToList(String ids){
		if(ids!=null){
			String[] numbers = ids.split("\\,");
			List<Integer> idList = new ArrayList<Integer>();
			
			for(String number : numbers) {
				if(StringUtils.isNotBlank(number) && StringUtils.isNumeric(number)){
					idList.add(Integer.parseInt(number.trim()));
			    }
			}
			
			if(CollectionUtils.isEmpty(idList)){
				return null;
			} else {
				return idList;
			}
		} else {
			return null;
		}
		
		
	}

}
