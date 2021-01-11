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
 * Mar 29, 2010		Jovino Balunan		
 */
package com.freshremix.webapp.controller.akadensheet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.AkadenSessionConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.AkadenSheetParams;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.service.AkadenService;
import com.freshremix.util.OrderSheetUtil;

/**
 * @author Jovino Balunan
 *
 */
public class AkadenSheetTotalPriceController implements Controller {
	private AkadenService akadenService;

	public void setAkadenService(AkadenService akadenService) {
		this.akadenService = akadenService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 OrderSheetParam akadenSheetParams = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		
		BigDecimal totalPrice = null;
		if (akadenSheetParams.isAllDatesView()) {
			List<Integer> buyerId = OrderSheetUtil.toList(akadenSheetParams.getSelectedBuyerID());
			String startDate = akadenSheetParams.getStartDate();
			String endDate = akadenSheetParams.getEndDate();
			
			totalPrice = new BigDecimal(100);//akadenService.getGTPriceByOrderId(buyerId, startDate, endDate);
		} else {
			List<Integer> buyerId = OrderSheetUtil.toList(akadenSheetParams.getSelectedBuyerID());
			String startDate = akadenSheetParams.getStartDate();
			totalPrice = new BigDecimal(100) ;//akadenService.getGTPriceByOrderId(buyerId, startDate, startDate);
		}
		
		Map<String,Object> model = new HashMap<String,Object>();
		if (totalPrice != null) {
			model.put("totalPriceWOTax", totalPrice);
			model.put("totalPriceWTax", totalPrice.multiply(BigDecimal.valueOf(1.05)));
		}
		return new ModelAndView("json",model);
	}
}
