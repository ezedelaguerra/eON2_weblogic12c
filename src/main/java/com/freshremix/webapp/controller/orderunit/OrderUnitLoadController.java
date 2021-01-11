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
 * Apr 14, 2010		Pammie		
 */
package com.freshremix.webapp.controller.orderunit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.AdminCompanyInformation;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.OrderUnit;
import com.freshremix.service.OrderUnitService;
import com.freshremix.treegrid.TreeGridItem;

/**
 * @author Pammie
 *
 */
public class OrderUnitLoadController extends SimpleFormController{
	private OrderUnitService orderUnitService;
	
	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<OrderUnit> orderUnitList;
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		orderUnitList = orderUnitService.getOrderUnitList(osParam.getCategoryId());
		
		List<TreeGridItem> tgis = new ArrayList<TreeGridItem>();
		
		for (OrderUnit orderUnit : orderUnitList) {
			TreeGridItem tgi = new TreeGridItem();
			tgi.addCell(orderUnit.getOrderUnitName());
			tgi.addCell(orderUnit.getOrderUnitId());
			tgis.add(tgi);
		}

		model.put("orderUnitList", tgis);

		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);	
	}
}
