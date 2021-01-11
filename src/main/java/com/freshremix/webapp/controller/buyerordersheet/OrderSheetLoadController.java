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
 * Mar 5, 2010		raquino		
 * Dec 13, 2012     Mikes     Redmine 1225
 */
package com.freshremix.webapp.controller.buyerordersheet;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.BuyerOrderSheetService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.ui.model.TableParameter;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author raquino
 *
 */
public class OrderSheetLoadController implements Controller{

	private BuyerOrderSheetService buyerOrderSheetService;
	private SKUGroupService skuGroupService;

	public void setBuyerOrderSheetService(
			BuyerOrderSheetService buyerOrderSheetService) {
		this.buyerOrderSheetService = buyerOrderSheetService;
	}

	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}


	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		String json = request.getParameter("_gt_json");
		Serializer serializer = new JsonSerializer();
		TableParameter tableParam = (TableParameter) serializer.deserialize(json, TableParameter.class);
		
		List<Integer> selectedOrderIdList = this.getIntegerListFromSession(request, SessionParamConstants.SELECTED_ORDERS_PARAM);
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);

		// set list of sku group
		String skuGroupList = skuGroupService.getSKUGroupList(user.getUserId().intValue(), osParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
		Map<String, List<Integer>> mapOfMembersByDate = null;
		if (RolesUtil.isUserRoleBuyerAdmin(user)){
			mapOfMembersByDate = getMapOfMembersByDateFromSession(request);
		}
		
		Map<String, Object> resultMap = buyerOrderSheetService.getOrders(osParam, user, tableParam,
				selectedOrderIdList, allOrders,mapOfMembersByDate);

		
		SessionHelper.setAttribute(request,
				SessionParamConstants.SKU_OBJ_MAP_PARAM,
				resultMap.get("skuObjMap"));
		resultMap.remove("skuObjMap");
		ModelAndView mav = new ModelAndView("json");
		mav.addAllObjects(resultMap);
		mav.addObject("pageInfo", tableParam.getPageInfo());
		mav.addObject("skuGroup", skuGroupList);
		
		return mav;
	}

	@SuppressWarnings("unchecked")
	private List<Integer> getIntegerListFromSession(HttpServletRequest request, String paramName) {
		return (List<Integer>) SessionHelper.getAttribute(request, paramName);
	}
	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
	@SuppressWarnings("unchecked")
	private Map<String, List<Integer>> getMapOfMembersByDateFromSession(
			HttpServletRequest request) {
		return (Map<String, List<Integer>>) SessionHelper.getAttribute(request, SessionParamConstants.MAP_OF_MEMBERS_BY_DATE);
	}

	
}
