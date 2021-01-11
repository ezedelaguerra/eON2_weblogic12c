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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.AkadenSessionConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.model.Company;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.AkadenService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.CompanyUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;
import com.freshremix.util.UserUtil;

/**
 * @author jabalunan
 */
public class AkadenSheetController extends SimpleFormController {
	private OrderSheetService orderSheetService;
	private AkadenService akadenService;
	private DealingPatternService dealingPatternService;
	private SKUGroupService skuGroupService;
	private OrderUnitService orderUnitService;

	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}

	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}

	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
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
		
		Map<String,Object> model = new HashMap<String,Object>();
//		if (SessionHelper.isSessionExpired(request)) {
//			model.put("isSessionExpired", Boolean.TRUE);
//		} else {
			OrderSheetParam akadenSheetParams = (OrderSheetParam) command;
			User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
			
			// get all dates view buyer id and set to selected buyers
			if (!StringUtil.isNullOrEmpty(akadenSheetParams.getDatesViewBuyerID()))
				akadenSheetParams.setSelectedBuyerID(akadenSheetParams.getDatesViewBuyerID());
			
			if (akadenSheetParams.getSelectedSellerID() == null) {
				akadenSheetParams.setSelectedSellerID(user.getUserId().toString());
			}
			
			if (akadenSheetParams.getEndDate().length() != 8)
				akadenSheetParams.setEndDate(akadenSheetParams.getStartDate());
			System.out.println("allDatesView:[" + akadenSheetParams.isAllDatesView() + "]");
			System.out.println("datesViewBuyerId:[" + akadenSheetParams.getDatesViewBuyerID() + "]");
			
			String selectedDated = akadenSheetParams.getSelectedDate();
			List<Integer> buyerIds = OrderSheetUtil.toList(akadenSheetParams.getSelectedBuyerID());
			List<String> dateList = DateFormatter.getDateList(selectedDated, selectedDated);
			List<Integer> sellerIds = OrderSheetUtil.toList(akadenSheetParams.getSelectedSellerID());
			
//			this.createCompanyColumns(request, akadenSheetParams);
			
			// set list of sku group
			String skuGroupList = skuGroupService.getSKUGroupList(user.getUserId().intValue(), akadenSheetParams.getCategoryId());
			SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
			
			// set order unit
            String orderUnit = orderUnitService.getOrderUnitByCategoryId(akadenSheetParams.getCategoryId());
            SessionHelper.setAttribute(request, SessionParamConstants.ORDER_UNIT_LIST_PARAM, orderUnit);
            
            // set order unit renderer
            String orderUnitRenderer = orderUnitService.getOrderUnitRenderer(akadenSheetParams.getCategoryId());
            SessionHelper.setAttribute(request, SessionParamConstants.ORDER_UNIT_RENDERER_PARAM, orderUnitRenderer);
    		
    		// set default order unit id
    		Integer orderUnitId = orderUnitService.getOrderUnitCaseId(akadenSheetParams.getCategoryId());
    		SessionHelper.setAttribute(request, SessionParamConstants.DEFAULT_ORDER_UNIT_PARAM, orderUnitId);
    		
//			Map<String, Integer> mapOrderId = new HashMap<String, Integer>();
//			List<Integer> listOrderIds = new ArrayList<Integer>();
//			for(Order orders : allOrders) {
//				mapOrderId.put(orders.getBuyerId()+"_"+orders.getSellerId(), orders.getOrderId());
//				listOrderIds.add(orders.getOrderId());
//			}
			
			List<Integer> seller = new ArrayList<Integer>();
			if (user.getRole().getSellerFlag().equals("1")) { 
				seller.add(user.getUserId());
				SessionHelper.setAttribute(request, SessionParamConstants.IS_ADMIN_PARAM, false);
				//Sheet Type used for CSV Download
				akadenSheetParams.setCsvSheetTypeID(SheetTypeConstants.SELLER_AKADEN_SHEET);
			}
			else {
				//List<Integer> sellerId = OrderSheetUtil.toList(akadenSheetParams.getSelectedSellerID());
				seller.addAll(sellerIds);
				SessionHelper.setAttribute(request, SessionParamConstants.IS_ADMIN_PARAM, true);
				//Sheet Type used for CSV Download
				akadenSheetParams.setCsvSheetTypeID(SheetTypeConstants.SELLER_ADMIN_AKADEN_SHEET);
			}
			
			String sellerNameList = orderSheetService.getSellerNames(seller);
			SessionHelper.setAttribute(request, SessionParamConstants.SELLER_NAME_LIST_PARAM, sellerNameList);
			
			// create seller to buyer DP map
			Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap = dealingPatternService.getSellerToBuyerDPMap(seller, akadenSheetParams.getStartDate(), akadenSheetParams.getEndDate());
			// create buyer to seller DP map
			Map<String, Map<String, List<Integer>>> buyerToSellerDPMap = dealingPatternService.getBuyerToSellerDPMap(sellerToBuyerDPMap);
			DealingPattern dp = new DealingPattern(sellerToBuyerDPMap,buyerToSellerDPMap);
			SessionHelper.setAttribute(request, SessionParamConstants.DEALING_PATTERN_PARAM, dp);
			
			/* create buyer columns */
			OrderSheetUtil.createCompanyColumns(request, akadenSheetParams.getSelectedBuyerCompany(),
					akadenSheetParams.getSelectedBuyerID(), akadenSheetParams.getSelectedSellerID(),
					akadenSheetParams.getSelectedDate(), sellerToBuyerDPMap);
			
			List<Order> allOrders = null;
			allOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);
			SessionHelper.setAttribute(request, SessionParamConstants.ALL_ORDERS_PARAM, allOrders);
			
			Map<String, Order> mapOrdersParam = OrderSheetUtil.convertToOrderMap(allOrders);
			SessionHelper.setAttribute(request, AkadenSessionConstants.AKADEN_MAP_ORDERS_PARAMS, mapOrdersParam);
			
			List<Integer> selectedOrderIds = orderSheetService.getSelectedOrderIds(mapOrdersParam, akadenSheetParams);
			SessionHelper.setAttribute(request, AkadenSessionConstants.AKADEN_LIST_ORDER_IDS, selectedOrderIds);
			
			// set sku group renderer
            String skuGroupRenderer = skuGroupService.getSKUGroupRenderer(seller, akadenSheetParams.getCategoryId());
            SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_RENDERER_PARAM, skuGroupRenderer);
            
            SessionHelper.setAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM, akadenSheetParams);
			
			//set seller to model (sku group)
			model.put("response", new FilteredIDs(user.getUserId().toString(), user.getName()));
//		}
		if (errors.hasErrors()) 
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json",model);
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
	
	private void createCompanyColumns(HttpServletRequest request, OrderSheetParam akp) {
		String buyerCompanyIds = akp.getSelectedBuyerCompany();
//		List<Integer> tmpBuyerId = OrderSheetUtil.toList(akp.getSelectedBuyerID());
		List<Integer> buyerId = OrderSheetUtil.toList(akp.getSelectedBuyerID());
//		buyerId.add(tmpBuyerId.get(0));
		Map<Integer, List<User>> companyMap = new HashMap<Integer, List<User>>();
		Map<String, User> buyerMap = new HashMap<String, User>();
		List<Company> companyList = new ArrayList<Company>();
		List<Company> companyObjs = CompanyUtil.toCompanyObjs(OrderSheetUtil.toList(buyerCompanyIds));
		for (Company companyObj : companyObjs) {
			Integer companyId = companyObj.getCompanyId();
			String companyName = companyObj.getCompanyName();
			System.out.println("companyName:[" + companyName + "]");
			
			List<User> buyerObjs = UserUtil.toUserObjs(buyerId,
					companyId);
			
			for (User buyerObj : buyerObjs) {
				String buyerName = buyerObj.getUserName();
				buyerMap.put(buyerObj.getUserId().toString(), buyerObj);
				System.out.println("buyerName:[" + buyerName + "]");
			}
			if (buyerObjs.size() > 0) {
				companyMap.put(companyId, buyerObjs);
				companyList.add(companyObj);
			}
		}
		SessionHelper.setAttribute(request, "companyMap", companyMap);
		SessionHelper.setAttribute(request, "companyList", companyList);
		SessionHelper.setAttribute(request, "buyerMap", buyerMap);
	}
}
