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
import com.freshremix.model.AkadenSheetParams;
import com.freshremix.model.Company;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.Order;
import com.freshremix.model.User;
import com.freshremix.service.AkadenService;
import com.freshremix.service.AllocationSheetService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.AkadenSheetUtil;
import com.freshremix.util.CompanyUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.UserUtil;

/**
 * @author jabalunan
 *
 */
public class AkadenSheetAllocationController extends SimpleFormController {
	private OrderSheetService orderSheetService;
	private AkadenService akadenService;
	private AllocationSheetService allocationSheetService;
	private DealingPatternService dealingPatternService;
	private SKUGroupService skuGroupService;
	private OrderUnitService orderUnitService;

	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}
	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}

	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}
	
	public void setAllocationSheetService(
			AllocationSheetService allocationSheetService) {
		this.allocationSheetService = allocationSheetService;
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
			AkadenSheetParams akadenSheetParams = (AkadenSheetParams) command;
			User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
			
			if (akadenSheetParams.getSelectedSellerID() == null)
				akadenSheetParams.setSelectedSellerID(user.getUserId().toString());
			
			if (akadenSheetParams.getEndDate().length() != 8)
				akadenSheetParams.setEndDate(akadenSheetParams.getStartDate());
			
			List<Integer> selectedBuyerIds = OrderSheetUtil.toList(akadenSheetParams.getSelectedBuyerID());
			
			if (akadenSheetParams.getDatesViewBuyerID() == null)
				akadenSheetParams.setDatesViewBuyerID(selectedBuyerIds.get(0).toString());
			
			String selectedDated = akadenSheetParams.getSelectedDate();
			List<Integer> buyerIds = OrderSheetUtil.toList(akadenSheetParams.getSelectedBuyerID());
			List<String> dateList = DateFormatter.getDateList(selectedDated, selectedDated);
			List<Integer> sellerIds = OrderSheetUtil.toList(akadenSheetParams.getSelectedSellerID());
			
			/*this.createCompanyColumns(request, akadenSheetParams.getSelectedBuyerCompany(),
					akadenSheetParams.getDatesViewBuyerID());*/
			
			// set list of sku group
			String skuGroupList = skuGroupService.getSKUGroupList(user.getUserId().intValue(), akadenSheetParams.getCategoryId());
			SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
			
			// set order unit
            String orderUnit = orderUnitService.getOrderUnitByCategoryId(akadenSheetParams.getCategoryId());
            SessionHelper.setAttribute(request, SessionParamConstants.ORDER_UNIT_LIST_PARAM, orderUnit);
            
            // set order unit renderer
            String orderUnitRenderer = orderUnitService.getOrderUnitRenderer(akadenSheetParams.getCategoryId());
            SessionHelper.setAttribute(request, SessionParamConstants.ORDER_UNIT_RENDERER_PARAM, orderUnitRenderer);
            
            List<Integer> seller = new ArrayList<Integer>();
			if (user.getRole().getSellerFlag().equals("1")) { 
				seller.add(user.getUserId());
				SessionHelper.setAttribute(request, SessionParamConstants.IS_ADMIN_PARAM, false);
			}
			else {
				List<Integer> sellerId = OrderSheetUtil.toList(akadenSheetParams.getSelectedSellerID());
				seller.addAll(sellerId);
				SessionHelper.setAttribute(request, SessionParamConstants.IS_ADMIN_PARAM, true);
			}
			
			List<Order> orders = null;
			orders = allocationSheetService.getFinalizedOrdersForAlloc(buyerIds, dateList, sellerIds);
			
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
			
			// set sku group renderer
            String skuGroupRenderer = skuGroupService.getSKUGroupRenderer(seller, akadenSheetParams.getCategoryId());
            SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_RENDERER_PARAM, skuGroupRenderer);
            
            Map<String, Order> allocationOrderMap = OrderSheetUtil.convertToOrderMap(orders);
//			List<Integer> selectedOrderIds = orderSheetService.getSelectedOrderIds(allOrdersMap, akadenSheetParams);
			
			Map<Integer, Integer> selectedOrderIds = new HashMap<Integer, Integer>();
			for (Order order : orders) {
				selectedOrderIds.put(order.getBuyerId(), order.getOrderId());
			}
			
			SessionHelper.setAttribute(request, AkadenSessionConstants.AKADEN_IMPORT_ALLOC_PARAMS, akadenSheetParams);
			SessionHelper.setAttribute(request, AkadenSessionConstants.AKADEN_ALLOC_MAP_ORDERS_IDS, selectedOrderIds);
			SessionHelper.setAttribute(request, AkadenSessionConstants.AKADEN_ALLOC_MAP_ORDERS, allocationOrderMap);
			
			
			//set seller to model (sku group)
			model.put("response", new FilteredIDs(user.getUserId().toString(), user.getName()));
//		}
		
		if (errors.hasErrors()) 
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json",model);
	}

	@SuppressWarnings("unused")
	private void createCompanyColumns(HttpServletRequest request, String buyerCompanyIds,
			String buyerUserIds) {
		/**/
		System.out.println("selected buyer company:[" + buyerCompanyIds + "]");
		System.out.println("selected buyer id:[" + buyerUserIds + "]");
		
		Map<Integer, List<User>> companyMap = new HashMap<Integer, List<User>>();
		Map<String, User> buyerMap = new HashMap<String, User>();
		List<Company> companyList = new ArrayList<Company>();
		List<Company> companyObjs = CompanyUtil.toCompanyObjs(OrderSheetUtil.toList(buyerCompanyIds));
		for (Company companyObj : companyObjs) {
			Integer companyId = companyObj.getCompanyId();
			String companyName = companyObj.getCompanyName();
			System.out.println("companyName:[" + companyName + "]");
			
			List<User> buyerObjs = UserUtil.toUserObjs(OrderSheetUtil.toList(buyerUserIds),
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
