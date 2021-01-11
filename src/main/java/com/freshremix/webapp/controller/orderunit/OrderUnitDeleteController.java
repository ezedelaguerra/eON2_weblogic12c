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
 * Apr 28, 2010		Pammie		
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
public class OrderUnitDeleteController extends SimpleFormController {
	private OrderUnitService orderUnitService;
	
	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Integer orderUnitId = Integer.parseInt(request.getParameter("orderUnitId"));
		Integer orderUnitInUse = 0;
		Integer checkOrderUnit = orderUnitService.checkOrderUnitIfInUse(orderUnitId); 
		Integer checkOrderUnitPmf = orderUnitService.checkOrderUnitInPmfSku(orderUnitId);
		if (checkOrderUnit > 0 || checkOrderUnitPmf > 0){
			orderUnitInUse = 1;
		} else {
			orderUnitService.deleteOrderUnit(orderUnitId);
			orderUnitInUse = 0;
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("status", orderUnitInUse);
		
		return new ModelAndView("json", model);
	}
}
