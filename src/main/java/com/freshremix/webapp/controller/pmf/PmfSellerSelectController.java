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
 * Jun 20, 2010		Pammie		
 */
package com.freshremix.webapp.controller.pmf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.dao.UserDao;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author Pammie
 *
 */
public class PmfSellerSelectController implements Controller{

	private SKUGroupService skuGroupService;
	private UserDao usersInfoDaos;
	private OrderSheetService orderSheetService;
	private OrderUnitService orderUnitService;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("json");
		String shortName = request.getParameter("sellerName");
		shortName = new String(shortName.getBytes("ISO-8859-1"),"UTF-8");
		
		User userSession = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		//TODO: Use seller user id not shortname
		//		Use same impl in UOM
		User user = usersInfoDaos.getUserByShortName(shortName, userSession.getCompany().getCompanyId());
		OrderSheetParam param = (OrderSheetParam)SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM);
		List<Map<String, Object>> skuGroupList = skuGroupService.getSKUGroupListViaSellerSelect(user.getUserId(), param.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
		Integer categoryId = Integer.valueOf(SessionHelper.getAttribute(request, "categoryId").toString());
		//List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		//Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		
		// set order unit
		String orderUnit = orderUnitService.getOrderUnitByCategoryId(categoryId);
		SessionHelper.setAttribute(request, SessionParamConstants.ORDER_UNIT_LIST_PARAM, orderUnit);
		
		// set order unit renderer
		String orderUnitRenderer = orderUnitService.getOrderUnitRenderer(categoryId);
		SessionHelper.setAttribute(request, SessionParamConstants.ORDER_UNIT_RENDERER_PARAM, orderUnitRenderer);

		List<Integer> seller = new ArrayList<Integer>();
		if (user.getRole().getSellerFlag().equals("1")) { 
			seller.add(user.getUserId());
			SessionHelper.setAttribute(request, SessionParamConstants.IS_ADMIN_PARAM, false);
		}
		else {
			List<Integer> sellerId = OrderSheetUtil.toList(param.getSelectedSellerID());
			seller.addAll(sellerId);
			SessionHelper.setAttribute(request, SessionParamConstants.IS_ADMIN_PARAM, true);
		}
		
		// set sku group renderer
		String skuGroupRenderer = skuGroupService.getSKUGroupRenderer(seller, categoryId);
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_RENDERER_PARAM, skuGroupRenderer);
		
		String sellerNameList = orderSheetService.getSellerNames(seller);
		SessionHelper.setAttribute(request, SessionParamConstants.SELLER_NAME_LIST_PARAM, sellerNameList);
		
		mav.addObject("sellerId", user.getUserId());
		mav.addObject("companyname", user.getCompany().getShortName());
		mav.addObject("skuGroup", skuGroupList);
		//mav.addObject("lockflag", super.lockQuantities(request, param, user, allOrdersMap));
		mav.addObject("lockflag", "{'allQuantities' : 1}");
		
		return mav;
	}
	
	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}

	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}
	
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	
	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}
}
