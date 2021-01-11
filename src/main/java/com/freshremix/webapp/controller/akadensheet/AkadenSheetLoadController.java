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
 * Mar 17, 2010		Jovino Balunan		
 */
package com.freshremix.webapp.controller.akadensheet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.AkadenService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.ui.model.TableParameter;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author jabalunan
 *
*/

public class AkadenSheetLoadController implements Controller, InitializingBean{
	
	private AkadenService akadenService;
	private MessageSourceAccessor messageSourceAccessor;
	private EONLocale eonLocale;
	private MessageSource messageSource;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}

	
	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}

	public MessageSourceAccessor getMessageSourceAccessor() {
		return messageSourceAccessor;
	}

	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		this.messageSourceAccessor = messageSourceAccessor;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public void setAkadenService(AkadenService akadenService) {
		this.akadenService = akadenService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		OrderSheetParam akadenSheetParams = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		//List<Order> orders = (ArrayList<Order>) request.getSession().getAttribute(AkadenSessionConstants.AKADEN_ORDERS_PARAMS);
		
		String json = request.getParameter("_gt_json");
		Serializer serializer = new JsonSerializer();
		TableParameter tableParam = (TableParameter) serializer.deserialize(json, TableParameter.class);
		
		List<Map<String, Object>> skuOrderList = akadenService.loadAkadenItems(request, user, akadenSheetParams, tableParam.getPageInfo());
		
		ModelAndView mav = new ModelAndView("json");
		
		String confirmMsg = "";
		if (skuOrderList.size() == 0) {
			confirmMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
				getMessage("akadensheet.seller.noSkuOrderList", eonLocale.getLocale()));
		}
		
		// set list of sku group
		String skuGroupList = "";
		skuGroupList = akadenService.getSKUGroupList(user.getUserId().intValue(), akadenSheetParams.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
		
		GrandTotalPrices grandTotalPrices = akadenService.computeTotalPricesOnDisplay(skuOrderList);
		BigDecimal totalPriceWOTax = grandTotalPrices.getPriceWithoutTax();
		BigDecimal totalPriceWTax = grandTotalPrices.getPriceWithTax();
//		SessionHelper.getAttribute(request, SessionParamConstants.SELECTED_ORDERS_PARAM);
		Set<String> sellernameSet = extracted(request);
		
		mav.addObject("data", skuOrderList);
		mav.addObject("pageInfo", tableParam.getPageInfo());
		mav.addObject("skuGroup", skuGroupList);
		mav.addObject("totalPriceWOTaxOnDisplay", totalPriceWOTax);
		mav.addObject("totalPriceWTaxOnDisplay", totalPriceWTax);
		mav.addObject("sellernameSet", sellernameSet);
		mav.addObject("confirmMsg", confirmMsg);
		
		
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	private Set<String> extracted(HttpServletRequest request) {
		return (Set<String>)SessionHelper.getAttribute(request, "sellerNameSet");
	}
	
	
}
