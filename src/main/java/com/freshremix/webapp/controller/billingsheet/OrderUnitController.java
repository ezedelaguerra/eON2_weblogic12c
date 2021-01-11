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
 * May 18, 2010		gilwen		
 */
package com.freshremix.webapp.controller.billingsheet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.BillingItem;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.service.BillingSheetService;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author gilwen
 *
 */
public class OrderUnitController implements Controller {
	
	private BillingSheetService billingSheetService;

	public void setBillingSheetService(BillingSheetService billingSheetService) {
		this.billingSheetService = billingSheetService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("json");
		boolean isValid = true;
		String orderUnit = request.getParameter("orderUnit");
		
		if (!orderUnit.equalsIgnoreCase("KG")) {
			OrderSheetParam param = (OrderSheetParam)SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM);
			String sellerId = request.getParameter("sellerId");
			String skuId = request.getParameter("skuId");
			String deliveryDate = param.getSelectedDate();
			List<Integer> sBuyer = OrderSheetUtil.toList(param.getSelectedBuyerID());
			List<BillingItem> oi = billingSheetService.getBillingItem(Integer.valueOf(sellerId), deliveryDate, Integer.valueOf(skuId));
			if (oi != null) {
				for (BillingItem _oi : oi) {
					if(_oi.getQuantity() != null){
						String str = _oi.getQuantity().toPlainString();
						if (!sBuyer.contains(_oi.getOrder().getBuyerId()) &&
							str.indexOf(".") > -1) {
							isValid = false;
							break;
						}
					}
				}
			}
		}
		mav.addObject("isValid", isValid);
		return mav;
	}
}