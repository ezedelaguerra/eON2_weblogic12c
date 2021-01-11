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
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.service.OrderSheetService;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author gilwen
 *
 */
public class QuantityLimitController implements Controller {
	
	private OrderSheetService orderSheetService;

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("json");
		OrderSheetParam param = (OrderSheetParam)SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM);
		boolean isValid = false;
		String sellerId = request.getParameter("sellerId");
		String skuId = request.getParameter("skuId");
		String deliveryDate = param.getSelectedDate();
		String skuMaxLimit = request.getParameter("skuMaxLimit");
		String enteredQty = request.getParameter("cond1");
		String value = request.getParameter("value");
		BigDecimal qtyValue = new BigDecimal(StringUtil.isNullOrEmpty(value) == true ? "0" : value);
		BigDecimal remCount = new BigDecimal(0);
		BigDecimal availableQty = new BigDecimal(0);
		
		List<Integer> selectedBuyerId = new ArrayList<Integer>();
		BigDecimal totalQuantityUI = getTotalQtyAndPopulateBuyerList(enteredQty, selectedBuyerId);
		this.removeFromList(param.getDatesViewBuyerID(), selectedBuyerId);
		
		if (!StringUtil.isNullOrEmpty(skuMaxLimit) && 
			!skuMaxLimit.equalsIgnoreCase("undefined") && 
			!skuId.equalsIgnoreCase("undefined")) {
			remCount = orderSheetService.getTotalQuantityByOtherBuyers(Integer.valueOf(sellerId), selectedBuyerId, Integer.valueOf(skuId), deliveryDate);
			if (remCount == null)
				remCount = new BigDecimal(0);
			System.out.println("remcount:[" + remCount.toString() + "]");
			BigDecimal iSkuMaxLimit = new BigDecimal(skuMaxLimit);
			System.out.println("skuMaxLimit:[" + skuMaxLimit.toString() + "]");
				
			if ((remCount.add(totalQuantityUI)).compareTo(iSkuMaxLimit) <= 0)
				isValid = true;
			else {
				availableQty = iSkuMaxLimit.subtract(remCount.add(totalQuantityUI.subtract(qtyValue)));
				System.out.println("totalQuantityUI:[" + totalQuantityUI.toString() + "]");
				System.out.println("availableQty:[" + availableQty.toString() + "]");
			}
		} else {
			isValid = true;
		}
		
		mav.addObject("isValid", isValid);
		mav.addObject("availableQty", availableQty);
		return mav;
	}

	private BigDecimal getTotalQtyAndPopulateBuyerList(String enteredQty,
			List<Integer> selectedBuyerId) {
		
		BigDecimal totalQuantityUI = null;
		List<String> tmp = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(enteredQty,",");
		while (st.hasMoreTokens()) {
			String val = st.nextToken();
			tmp.add(val);
		}
		
		for (int i=0; i<tmp.size(); i++) {
			if (i < tmp.size() -1)
				selectedBuyerId.add(Integer.parseInt(tmp.get(i).substring(2)));
			else
				totalQuantityUI = new BigDecimal(tmp.get(i));
		}
		
		return totalQuantityUI;
	}
	
	private void removeFromList(String datesViewBuyerID,List<Integer> selectedBuyerId){
		if(!datesViewBuyerID.equals("0")){
			selectedBuyerId.clear();
			selectedBuyerId.add(Integer.valueOf(datesViewBuyerID));
			
		}
	}

}