package com.freshremix.webapp.controller.ordersheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.service.OrderSheetService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.StringUtil;

public class OrderSheetValidateController extends SimpleFormController {

	private OrderSheetService orderSheetService;
	private EONLocale eonLocale;

	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("json");
		String skuIds = request.getParameter("skuIds");
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		
		boolean skuHasQty = false;
		List<String> deliveryDate = null;
		if (osParam.isAllDatesView()) {
			deliveryDate = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
		} else {
			deliveryDate = new ArrayList<String>();
			deliveryDate.add(osParam.getSelectedDate());
		}
		List<String> skuIdArr = Arrays.asList(skuIds.split(","));
		for (String skuId : skuIdArr) {
			skuHasQty = orderSheetService.skuHasQuantity(Integer.valueOf(skuId), deliveryDate);
			if (skuHasQty) break;
		}
		
		if (skuHasQty) {
			//String errorMsg = getMessageSourceAccessor().getMessage("ordersheet.sku.delete.error", request.getLocale());
			String errorMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
						getMessage("ordersheet.sku.delete.error", eonLocale.getLocale()));
			mav.addObject("error", errorMsg);
		} else {
			mav.addObject("error", false);
		}
		return mav;
	}

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
}