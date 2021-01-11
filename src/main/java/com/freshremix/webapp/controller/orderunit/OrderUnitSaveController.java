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
 * Apr 20, 2010		Pammie		
 */
package com.freshremix.webapp.controller.orderunit;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.service.OrderUnitService;

/**
 * @author Pammie
 *
 */
public class OrderUnitSaveController extends SimpleFormController{
	private OrderUnitService orderUnitService;
	
	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		String orderUnitName = request.getParameter("orderUnitName");
		Integer categoryId = osParam.getCategoryId();
		Integer errorSave = 0;
		if (orderUnitService.checkOrderUnitIfExist(orderUnitName.toUpperCase(),categoryId) > 0){
			errorSave = 1;
		} else {
			orderUnitService.insertOrderUnit(orderUnitName, osParam.getCategoryId());
			errorSave = 0;
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("status", errorSave);
		
		return new ModelAndView("json", model);
	}
}
