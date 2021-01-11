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
 * Mar 25, 2010		Jovino Balunan		
 */
package com.freshremix.webapp.controller.akadensheet;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.AkadenSessionConstants;
import com.freshremix.constants.OrderConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.AkadenService;
import com.freshremix.ui.model.AkadenForm;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author jabalunan
 *
 */
public class AkadenSheetSaveController extends SimpleFormController {
	
	private AkadenService akadenService;

	public void setAkadenService(AkadenService akadenService) {
		this.akadenService = akadenService;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
//		if (SessionHelper.isSessionExpired(request)) {
//			Map<String,Object> model = new HashMap<String,Object>();
//			model.put("isSessionExpired", Boolean.TRUE);
//			return new ModelAndView("json",model);
//		}
		
		String json = request.getParameter("_gt_json");
		
		Serializer serializer = new JsonSerializer();
		AkadenForm akadenForm = (AkadenForm) serializer.deserialize(json, AkadenForm.class);
		OrderForm orderForm = (OrderForm) serializer.deserialize(json, OrderForm.class);
		
		OrderSheetParam akadenSheetParams = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		
		OrderDetails od = new OrderDetails();
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		List<Integer> buyerIds = OrderSheetUtil.toList(akadenSheetParams.getSelectedBuyerID());
		
		if (!akadenSheetParams.isAllDatesView()) {
			if (akadenSheetParams.getDatesViewBuyerID() == null)
				akadenSheetParams.setDatesViewBuyerID(buyerIds.get(0).toString());
			akadenForm.setBuyerColumnIds(buyerIds);
			orderForm.setBuyerColumnIds(buyerIds);
		}
		else {
			List<String> deliveryDates = DateFormatter.getDateList(akadenSheetParams.getStartDate(), akadenSheetParams.getEndDate());
			akadenForm.setDateColumnIds(deliveryDates);
			orderForm.setDateColumnIds(deliveryDates);
		}

//		System.out.println("Inserted " + akadenForm.getInsertedOrders());
//		System.out.println("Updated " + akadenForm.getUpdatedOrders());
//		System.out.println("Deleted " + akadenForm.getDeletedOrders());
		
		od.setBuyerIds(buyerIds);
		od.setUser(user);
		od.setDeliveryDate((String) SessionHelper.getAttribute(request, SessionParamConstants.DELIVERY_DATE_PARAM));
		od.setSellerId(user.getUserId().intValue());
		od.setSheetType(akadenSheetParams.getSheetTypeId());
		od.setSkuCategoryId(akadenSheetParams.getCategoryId());
		od.setStartDate(akadenSheetParams.getStartDate());
		od.setEndDate(akadenSheetParams.getEndDate());
		od.setAllDatesView(akadenSheetParams.isAllDatesView());
		od.setDatesViewBuyerID(Integer.valueOf(akadenSheetParams.getDatesViewBuyerID()));
		//od.setDatesViewBuyerID(Integer.valueOf(akadenSheetParams.getDatesViewBuyerID()));
		
		Map<Integer, AkadenSKU> akadenSkuObjMap = this.getSessionSKUObjMap(request);
		
		Map<Integer, AkadenSKU> akadenSkuUpdateMap = this.getSessionSkuUpdate(request);
		
//		Map<Integer, Integer> akadenOrderIds = this.getSessionOrderIds(request);
		
		Map<String, Order> allOrderMap = this.getSessionAllOrderMap(request);

		if(akadenForm.getAction().equals(OrderConstants.ACTION_SAVE)) {
			akadenService.saveAkaden(orderForm, akadenForm, od, akadenSkuObjMap, akadenSkuUpdateMap, akadenSheetParams, user, allOrderMap);
		}
		
		ModelAndView mav = new ModelAndView("json");
		return mav;
	}	
	
	@SuppressWarnings("unchecked")
	private Map<String, Order> getSessionAllOrderMap(HttpServletRequest request) {
		return (Map<String, Order>)SessionHelper.getAttribute(request, AkadenSessionConstants.AKADEN_MAP_ORDERS_PARAMS);
	}
	
	@SuppressWarnings("unchecked")
	private Map<Integer, AkadenSKU> getSessionSKUObjMap(HttpServletRequest request) {
		return (Map<Integer, AkadenSKU>)SessionHelper.getAttribute(request, AkadenSessionConstants.AKADEN_SKU_OBJ_MAP_PARAM);
	}
	
	@SuppressWarnings({ "unchecked" })
	private Map<Integer, AkadenSKU> getSessionSkuUpdate(HttpServletRequest request) {
		return (Map<Integer, AkadenSKU>) SessionHelper.getAttribute(request, AkadenSessionConstants.AKADEN_SKU_UPDATE_PARAM);
	}
	
//	@SuppressWarnings({ "unused", "unchecked" })
//	private Map<Integer, Integer> getSessionOrderIds(HttpServletRequest request) {
//		return (Map<Integer, Integer>) request.getSession().getAttribute(AkadenSessionConstants.AKADEN_MAP_ORDERS_IDS);
//	}
}
