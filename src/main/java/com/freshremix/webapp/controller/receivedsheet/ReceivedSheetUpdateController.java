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
 * Mar 31, 2010		nvelasquez		
 */
package com.freshremix.webapp.controller.receivedsheet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.OrderConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.ReceivedSheetService;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;


/**
 * @author nvelasquez
 *
 */
public class ReceivedSheetUpdateController extends SimpleFormController {
	
	private ReceivedSheetService receivedSheetService;
	
	public void setReceivedSheetService(ReceivedSheetService receivedSheetService) {
		this.receivedSheetService = receivedSheetService;
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

		/* get parameters */
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		Integer buyerId = new Integer(osParam.getDatesViewBuyerID());
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		String datesViewBuyerId = osParam.getDatesViewBuyerID();
		/* get parameters */
		
		if (!osParam.isAllDatesView()) {
			orderForm.setQuantityApprovalIds(buyerIds);
			orderForm.setLockQuantity(buyerIds);
		}
		else {
			List<String> deliveryDates = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
			orderForm.setDateColumnIds(deliveryDates);
			orderForm.setLockDates(deliveryDates);
		}
			
		od.setUser(user);
		od.setDeliveryDate((String) SessionHelper.getAttribute(request, SessionParamConstants.DELIVERY_DATE_PARAM));
		od.setBuyerId(buyerId);
		od.setBuyerIds(buyerIds);
		od.setSellerIds(sellerIds);
		od.setSheetType(osParam.getSheetTypeId());
		od.setSkuCategoryId(osParam.getCategoryId());
		od.setStartDate(osParam.getStartDate());
		od.setEndDate(osParam.getEndDate());
		od.setAllDatesView(osParam.isAllDatesView());
		od.setDatesViewBuyerID(Integer.valueOf(datesViewBuyerId));
		
		List<Order> allOrders =  getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
			
		if(orderForm.getAction().equals(OrderConstants.ACTION_SAVE)) {
			receivedSheetService.saveOrder(orderForm, od, OrderSheetUtil.convertToOrderMap(allOrders));
		}
		
		ModelAndView mav = new ModelAndView("json");
		
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
	
}
