package com.freshremix.webapp.controller.ordersheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.BAFilterMarker;
import com.freshremix.model.BuyerFilterMarker;
import com.freshremix.model.FilterMarker;
import com.freshremix.model.Order;
import com.freshremix.model.SellerFilterMarker;
import com.freshremix.model.SheetState;
import com.freshremix.model.User;
import com.freshremix.service.CompanyBuyersSortService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.SortingUtil;
import com.freshremix.util.StringUtil;

public class UserLevelFilterController implements Controller {

	private DealingPatternService dealingPatternService;
	private CompanyBuyersSortService companybuyersSortService;
	private OrderSheetService orderSheetService;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			Map<String,Object> model = new HashMap<String,Object>();
			User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
			String companies = request.getParameter("companies");
			String dateFrom = request.getParameter("dateFrom");
			String dateTo = request.getParameter("dateTo");

			if (StringUtils.isBlank(dateTo) || "NaNNaNNaN".equalsIgnoreCase(dateTo)) {
				dateTo = new String(dateFrom);
			}
			
			List<User> ids = null;
			List<FilteredIDs> sortedIds = new ArrayList<FilteredIDs>();
			List<Integer> buyerIdList = new ArrayList<Integer>();
			List<Integer> sellerIdList = new ArrayList<Integer>();
			List<FilteredIDs> tmp = new ArrayList<FilteredIDs>();

			if (!StringUtil.isNullOrEmpty(companies)) {
				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)) {
					ids = dealingPatternService.getUserBuyersByBuyerCompanyIds(user.getUserId(), OrderSheetUtil.toList(companies), dateFrom, dateTo);
					sortedIds = companybuyersSortService.getSortedBuyers(OrderSheetUtil.toList(user.getUserId().toString()), user.getUserId(), OrderSheetUtil.toList(companies));

					SessionHelper.setAttribute(request, SessionParamConstants.SORTED_BUYERS, sortedIds);
					
					sellerIdList.add(user.getUserId());
					buyerIdList.addAll(userToList(ids));
				}
				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
					String selectedSellerIds = request.getParameter("sellerIds");
					if (!StringUtil.isNullOrEmpty(selectedSellerIds)){
						ids = dealingPatternService.getUserBuyersByUserSellersAndBuyerCompanyIds(OrderSheetUtil.toList(selectedSellerIds), OrderSheetUtil.toList(companies), dateFrom, dateTo);
						sortedIds = companybuyersSortService.getSortedBuyers(OrderSheetUtil.toList(selectedSellerIds), user.getUserId(), OrderSheetUtil.toList(companies));

						SessionHelper.setAttribute(request, SessionParamConstants.SORTED_BUYERS, sortedIds);
					}
					
					sellerIdList.addAll(OrderSheetUtil.toList(selectedSellerIds));
					buyerIdList.addAll(filteredIDsToList(sortedIds));
				}
				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)) {
					ids = dealingPatternService.getUserSellersBySellerCompanyIds(user.getUserId(), OrderSheetUtil.toList(companies), dateFrom, dateTo);
					sortedIds = user.getPreference().getSortedSellers();
					
					sellerIdList.addAll(userToList(ids));
					buyerIdList.add(user.getUserId());
				}
				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)) {
					String selectedBuyerIds = request.getParameter("buyerIds");
					if (!StringUtil.isNullOrEmpty(selectedBuyerIds))
						ids = dealingPatternService.getUserSellersByUserBuyersAndSellerCompanyIds(OrderSheetUtil.toList(selectedBuyerIds), OrderSheetUtil.toList(companies), dateFrom, dateTo);
					sortedIds = user.getPreference().getSortedSellers();
					
					sellerIdList.addAll(userToList(ids));
					buyerIdList.addAll(OrderSheetUtil.toList(selectedBuyerIds));
				}			
			}
			
			if (CollectionUtils.isEmpty(ids)) {
				model.put("response", tmp);
				return new ModelAndView("json",model);
			}

			List<FilteredIDs> unsortedIds = new ArrayList<FilteredIDs>();
			for (User _user : ids) {
				unsortedIds.add(new FilteredIDs(_user.getUserId().toString(), _user.getName()));
			}

			List<FilteredIDs> sortedBuyers = SortingUtil.sortList(unsortedIds, sortedIds);
			tmp.add(new FilteredIDs("0", "All"));
			tmp.addAll(sortedBuyers);
			
			String sheetTypeId = request.getParameter("sheetTypeId");
			List<Order> allOrder = orderSheetService.getAllOrders(buyerIdList, DateFormatter.getDateList(dateFrom, dateTo), sellerIdList);
			setMarks(user, tmp, sellerIdList, buyerIdList, DateFormatter.getDateList(dateFrom, dateTo), allOrder, sheetTypeId);
			
			model.put("response", tmp);
		
		return new ModelAndView("json",model);
	}

	private void setMarks(
			User user,
			List<FilteredIDs> filterList, 
			List<Integer> sellerIdList, 
			List<Integer> buyerIdList, 
			List<String> selectedDate,
			List<Order> allOrder,
			String sheetTypeId) {
		
		SheetState sheet = SheetState.createSheet(Integer.valueOf(sheetTypeId));
		Map<String, Map<String, List<Integer>>> dealingPattern = 
			dealingPatternService.getDealingPatternMap(selectedDate, sellerIdList, buyerIdList);
		FilterMarker fm = null;
		
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)) {
			fm = new SellerFilterMarker(filterList, sellerIdList, buyerIdList, selectedDate, allOrder, sheet, dealingPattern);
			fm.setFinalizedMarks();
		} else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
			fm = new BAFilterMarker(filterList, sellerIdList, buyerIdList, selectedDate, allOrder, sheet, dealingPattern);
			fm.setFinalizedMarks();
		} else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)) {
			fm = new BuyerFilterMarker(filterList, sellerIdList, buyerIdList, selectedDate, allOrder, sheet, dealingPattern);
			fm.setPublishedMarks();
			fm.setFinalizedMarks();
		} else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)) {
			fm = new BuyerFilterMarker(filterList, sellerIdList, buyerIdList, selectedDate, allOrder, sheet, dealingPattern);
			fm.setPublishedMarks();
			fm.setFinalizedMarks();
		}
		
		for (FilteredIDs filter : filterList) {
			filter.updateCaption();
		}
		
	}
	
	private List<Integer> filteredIDsToList(List<FilteredIDs> sortedIds) {
		List<Integer> tmp = new ArrayList<Integer>();
		
		for (FilteredIDs id : sortedIds) {
			tmp.add(Integer.valueOf(id.getId()));
		}
		
		return tmp;
	}
	
	private List<Integer> userToList(List<User> userList) {
		List<Integer> tmp = new ArrayList<Integer>();
		
		for (User user : userList) {
			tmp.add(Integer.valueOf(user.getUserId()));
		}
		
		return tmp;
	}

	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}

	public void setCompanybuyersSortService(
			CompanyBuyersSortService companybuyersSortService) {
		this.companybuyersSortService = companybuyersSortService;
	}
	
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}

}