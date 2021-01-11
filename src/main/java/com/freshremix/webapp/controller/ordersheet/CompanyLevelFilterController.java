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
import com.freshremix.model.Company;
import com.freshremix.model.User;
import com.freshremix.service.CompanyBuyersSortService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.SortingUtil;
import com.freshremix.util.StringUtil;

public class CompanyLevelFilterController implements Controller {

	private DealingPatternService dealingPatternService;
	private CompanyBuyersSortService companybuyersSortService;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
//		if (SessionHelper.isSessionExpired(request)) {
//			model.put("isSessionExpired", Boolean.TRUE);
//		} else {
			User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
			String userCompanyId = request.getParameter("userCompanyId");
			String dateFrom = request.getParameter("dateFrom");
			String dateTo = request.getParameter("dateTo");
			if (StringUtils.isBlank(dateTo) || "NaNNaNNaN".equalsIgnoreCase(dateTo)) {
				dateTo = new String(dateFrom);
			}
			
			
			List<Company> ids = null;
			List<Integer> sellers = new ArrayList<Integer>();
			List<Integer> buyers = new ArrayList<Integer>();
			List<FilteredIDs> sortedIds = new ArrayList<FilteredIDs>();
			if (!StringUtil.isNullOrEmpty(userCompanyId)) {
				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)) {
					//ids = dealingPatternService.getBuyerCompaniesBySellerCompanyIds(Integer.valueOf(userCompanyId));
					sellers.add(user.getUserId());
					ids = dealingPatternService.getBuyerCompaniesByUserSellerIds(sellers, dateFrom, dateTo);
				}
				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
					sellers = OrderSheetUtil.toList(userCompanyId);
					ids = dealingPatternService.getBuyerCompaniesByUserSellerIds(sellers, dateFrom, dateTo);
				}
				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)) {
					//ids = dealingPatternService.getSellerCompaniesByBuyerCompanyIds(user.getCompany().getCompanyId());
					buyers.add(user.getUserId());
					ids = dealingPatternService.getSellerCompaniesByUserBuyerIds(buyers, dateFrom, dateTo);
				}
				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)) {
					buyers = OrderSheetUtil.toList(userCompanyId);
					ids = dealingPatternService.getSellerCompaniesByUserBuyerIds(buyers, dateFrom, dateTo);
				}
				//return immediately if ids list is empty
				if (CollectionUtils.isEmpty(ids)) {
					model.put("response", new ArrayList<FilteredIDs>());
					return new ModelAndView("json",model);
					
				}
				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER) ||
						user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
					sortedIds = companybuyersSortService.getSortedCompany(user.getUserId());
					SessionHelper.setAttribute(request, SessionParamConstants.SORTED_COMPANIES, sortedIds);
				}	
				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER) ||
						user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)) {
					sortedIds = user.getPreference().getSortedSellerCompanies();
					SessionHelper.setAttribute(request, SessionParamConstants.SORTED_COMPANIES, sortedIds);
				}
			}
			List<FilteredIDs> unsortedIds = new ArrayList<FilteredIDs>();
			for (Company _company : ids) {
				unsortedIds.add(new FilteredIDs(_company.getCompanyId().toString(), _company.getCompanyName()));
			}
			
			List<FilteredIDs> sortedCompanies = new ArrayList<FilteredIDs>();
			sortedCompanies = SortingUtil.sortList(unsortedIds, sortedIds);
			List<FilteredIDs> tmp = new ArrayList<FilteredIDs>();
			tmp.add(new FilteredIDs("0", "All"));
			tmp.addAll(sortedCompanies);
			
//			if (ids != null && ids.size() > 0) {
//				tmp.add(new FilteredIDs("0", "All"));
//				for (Company _company : ids) {
//					tmp.add(new FilteredIDs(_company.getCompanyId().toString(), _company.getCompanyName()));
//				}
//			}
			
			model.put("response", tmp);
//		}
		
		return new ModelAndView("json",model);
	}

	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}

	public void setCompanybuyersSortService(
			CompanyBuyersSortService companybuyersSortService) {
		this.companybuyersSortService = companybuyersSortService;
	}

}