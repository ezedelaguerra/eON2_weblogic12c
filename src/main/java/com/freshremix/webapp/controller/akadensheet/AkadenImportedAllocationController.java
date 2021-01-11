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
 * Mar 27, 2010		Jovino Balunan		
 */
package com.freshremix.webapp.controller.akadensheet;

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
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.AkadenSessionConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.AkadenSheetParams;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.AkadenService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.ui.model.TableParameter;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author jabalunan
 *
 */
public class AkadenImportedAllocationController extends SimpleFormController implements InitializingBean{
	private OrderSheetService orderSheetService;
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
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	
	public void setAkadenService(AkadenService akadenService) {
		this.akadenService = akadenService;
	}
	
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		AkadenSheetParams akadenSheetParams = (AkadenSheetParams) request.getSession().getAttribute(AkadenSessionConstants.AKADEN_IMPORT_ALLOC_PARAMS);
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		String json = request.getParameter("_gt_json");
		Serializer serializer = new JsonSerializer();
		TableParameter tableParam = (TableParameter) serializer.deserialize(json, TableParameter.class);
		
		List<Map<String, Object>> skuAllocationList = akadenService.loadAllocationItems(user, akadenSheetParams, tableParam.getPageInfo(), request);

		ModelAndView mav = new ModelAndView("json");

		String confirmMsg = "";
		if(skuAllocationList.size()==0)
			confirmMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
				getMessage("billingsheet.seller.noSkuOrderList", eonLocale.getLocale()));	
		
		// set list of sku group
		String skuGroupList = akadenService.getSKUGroupList(user.getUserId().intValue(), akadenSheetParams.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
		
		Set<String> sellernameSet = extracted(request);
		
		//mav.addObject("data", orderList);
		mav.addObject("data", skuAllocationList);
		mav.addObject("pageInfo", tableParam.getPageInfo());
		mav.addObject("skuGroup", skuGroupList);
		mav.addObject("sellernameSet", sellernameSet);
		mav.addObject("confirmMsg", confirmMsg);
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	private Set<String> extracted(HttpServletRequest request) {
		return (Set<String>)SessionHelper.getAttribute(request, "sellerNameSet");
	}
}
