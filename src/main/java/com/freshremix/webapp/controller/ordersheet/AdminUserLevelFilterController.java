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

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.BAFilterMarker;
import com.freshremix.model.BuyersSort;
import com.freshremix.model.FilterMarker;
import com.freshremix.model.Order;
import com.freshremix.model.SAFilterMarker;
import com.freshremix.model.SheetState;
import com.freshremix.model.User;
import com.freshremix.service.CompanyBuyersSortService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.SortingUtil;
import com.freshremix.util.StringUtil;

public class AdminUserLevelFilterController implements Controller {

	private DealingPatternService dealingPatternService;
	private CompanyBuyersSortService companybuyersSortService;
	private OrderSheetService orderSheetService;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		Map<String,Object> model = new HashMap<String,Object>();
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		if (StringUtils.isBlank(dateTo) || "NaNNaNNaN".equalsIgnoreCase(dateTo)) {
			dateTo = new String(dateFrom);
		}
		String company = request.getParameter("company");

		List<FilteredIDs> tmp = new ArrayList<FilteredIDs>();
		List<Integer> buyerIdList = new ArrayList<Integer>();
		List<Integer> sellerIdList = new ArrayList<Integer>();
		
		if (!StringUtil.isNullOrEmpty(company)) {
			Integer dealingRelationId = null;
			if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
				dealingRelationId = DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER;
			}
			if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)) {
				dealingRelationId = DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER;
			}			
			List<User> ids = dealingPatternService.getMembersByAdminId(user.getUserId(), dealingRelationId, dateFrom, dateTo);
			if (CollectionUtils.isEmpty(ids)) {
				model.put("response", tmp);
				return new ModelAndView("json",model);
			}

			if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)) {
				List<FilteredIDs> unsortedIds = new ArrayList<FilteredIDs>();
				for (User _user : ids) {
					unsortedIds.add(new FilteredIDs(_user.getUserId().toString(), _user.getName()));
				}
				List<BuyersSort> buyersSorts = companybuyersSortService.getSortedMembersByBuyerAdminId(user.getUserId());
				List<FilteredIDs> sortedIds = new ArrayList<FilteredIDs>();
				for (BuyersSort _buyersSort : buyersSorts) {
					sortedIds.add(new FilteredIDs(_buyersSort.getBuyer().getUserId().toString(), _buyersSort.getBuyer().getName()));
				}
	
				List<FilteredIDs> sortedBuyers = SortingUtil.sortList(unsortedIds, sortedIds);
				tmp.add(new FilteredIDs("0", "All"));
				tmp.addAll(sortedBuyers);
				
				buyerIdList = filteredIDsToList(sortedIds);
				sellerIdList = userToList(
						dealingPatternService.getAllSellerIdsByBuyerIds(buyerIdList, dateFrom, dateTo));
				
				SessionHelper.setAttribute(request, SessionParamConstants.SORTED_BUYERS, sortedBuyers);
			}
			else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
				
				List<FilteredIDs> sortedIds = user.getPreference().getSortedSellers();
				List<Object> unsortedList = new ArrayList<Object>();
				unsortedList.addAll(ids);				
				List<FilteredIDs> sortedSellers = SortingUtil.sort(unsortedList, sortedIds);

				tmp.add(new FilteredIDs("0", "All"));
				tmp.addAll(sortedSellers);
				
				sellerIdList = filteredIDsToList(sortedIds);
				buyerIdList = userToList(
						dealingPatternService.getAllBuyerIdsBySellerIds(sellerIdList, dateFrom, dateTo));
			}
			else{
			
				if (ids != null && ids.size() > 0) {
					tmp.add(new FilteredIDs("0", "All"));
					for (User _user : ids) {
						tmp.add(new FilteredIDs(_user.getUserId().toString(), _user.getName()));
					}
				}
			}
		}
		
		String sheetTypeId = request.getParameter("sheetTypeId");
		List<Order> allOrder = orderSheetService.getAllOrders(buyerIdList, DateFormatter.getDateList(dateFrom, dateTo), sellerIdList);
		if (CollectionUtils.isNotEmpty(allOrder)){
			setMarks(user, tmp, sellerIdList, buyerIdList, DateFormatter.getDateList(dateFrom, dateTo), allOrder, sheetTypeId);
		}
		
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
		
		SheetState sheet = SheetState.createAdminSheet(Integer.valueOf(sheetTypeId));
		Map<String, Map<String, List<Integer>>> dealingPattern = 
			dealingPatternService.getDealingPatternMap(selectedDate, sellerIdList, buyerIdList);
		FilterMarker fm = null;
		
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
			fm = new SAFilterMarker(filterList, sellerIdList, buyerIdList, selectedDate, allOrder, sheet, dealingPattern);
			fm.setPublishedMarks();
			fm.setFinalizedMarks();
		} else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)) {
			fm = new BAFilterMarker(filterList, sellerIdList, buyerIdList, selectedDate, allOrder, sheet, dealingPattern);
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
			tmp.add(user.getUserId());
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