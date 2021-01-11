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
package com.freshremix.webapp.controller.ordersheet;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SKU;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.SKUService;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author gilwen
 *
 */
public class WeeklyQuantityLimitController implements Controller {
	
	private OrderSheetService orderSheetService;
	
	private SKUService skuService;
	
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	
	public void setSkuService(SKUService skuService) {
		this.skuService = skuService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("json");
		OrderSheetParam param = (OrderSheetParam)SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM);
		boolean isValid = false;
		String sellerId = request.getParameter("sellerId");
		String skuId = request.getParameter("skuId");
		String quantity = request.getParameter("quantity");
		String dateFieldId = request.getParameter("dateFieldId");
		
		if (!StringUtil.isNullOrEmpty(quantity) &&
			!skuId.equalsIgnoreCase("undefined")) {
			
			SKU sku = skuService.findSKU(Integer.valueOf(skuId));
			BigDecimal remCount = orderSheetService.getAvailableQuantities(
					Integer.valueOf(sellerId), 
					Integer.valueOf(param.getDatesViewBuyerID()), 
					dateFieldId.substring(dateFieldId.indexOf("_") + 1), 
					sku.getSkuId());
			
			isValid = orderSheetService.isQuantityValid(
					Integer.valueOf(sellerId), 
					Integer.valueOf(param.getDatesViewBuyerID()), 
					dateFieldId.substring(dateFieldId.indexOf("_") + 1), 
					sku,
					new BigDecimal(quantity),
					remCount);
			
			mav.addObject("skuMaxLimit", sku.getSkuMaxLimit());
			
			if (isValid == false) {
				mav.addObject("availableQty", remCount == null ? sku.getSkuMaxLimit() : sku.getSkuMaxLimit().subtract(remCount));
			}
			
		} else {
			isValid = true;
		}
		
		mav.addObject("isValid", isValid);
		return mav;
	}
}