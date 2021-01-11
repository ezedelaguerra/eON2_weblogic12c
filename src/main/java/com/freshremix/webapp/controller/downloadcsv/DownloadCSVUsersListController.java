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
 * May 21, 2010		Jovino Balunan		
 */
package com.freshremix.webapp.controller.downloadcsv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.Company;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.DownloadCSVService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author Jovino Balunan
 *
 */
public class DownloadCSVUsersListController extends SimpleFormController {

	private DownloadCSVService downloadCSVService;
	private DealingPatternService dealingPatternService;
	private UsersInformationService userInfoService;

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}

	public void setDownloadCSVService(DownloadCSVService downloadCSVService) {
		this.downloadCSVService = downloadCSVService;
	}

	@SuppressWarnings("null")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		String startDate = (String) String.valueOf(request.getParameter("startDate").replaceAll("/", ""));
		String endDate = (String) String.valueOf(request.getParameter("endDate").replaceAll("/", ""));
		
		User users = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		Integer userId = Integer.valueOf(users.getRole().getRoleId().toString());
		List<Integer> selectedSellerIds = new ArrayList<Integer>();
		List<Integer> selectedBuyerIds = new ArrayList<Integer>();
		List<Integer> lstUserId = new ArrayList<Integer>();
		List<Integer> lstCompanyId = new ArrayList<Integer>();
		List<String> lstSellerUsers = null;
		List<String> lstBuyerUsers = null;
		List<User> userIds = null;
		List<User> selectedUserIds = null;
		
		
		if (userId.equals(RolesUtil.ROLEID_SELLER)) {
			lstUserId.add(users.getUserId());
			userIds = dealingPatternService.getAllBuyerIdsBySellerIds(lstUserId, startDate, endDate);
			for(User user : userIds) {
				selectedSellerIds.add(user.getUserId());
			}
			lstSellerUsers = downloadCSVService.getBuyerCSVUsersList(selectedSellerIds);
			
			//Buyer users list
			model.put("usersList", lstSellerUsers);
			
		}
		else if (userId.equals(RolesUtil.ROLEID_BUYER)) {
			lstUserId.add(users.getUserId());
			List<Company> lstCompany = dealingPatternService.getSellerCompaniesByUserBuyerIds(lstUserId, startDate, endDate);
			for(Company company : lstCompany) {
				lstCompanyId.add(company.getCompanyId());
			}
			List<User> lstUsers = dealingPatternService.getUserSellersBySellerCompanyIds(users.getUserId(), lstCompanyId, startDate, endDate);
			for(User user : lstUsers) {
				selectedSellerIds.add(user.getUserId());
			}
			lstSellerUsers = downloadCSVService.getBuyerCSVUsersList(selectedSellerIds);
			
			// List buyer users
			model.put("buyersList", lstBuyerUsers);
			
			//Seller users list
			model.put("usersList", lstSellerUsers);
			//set session for download csv controller
			SessionHelper.setAttribute(request, SessionParamConstants.SELECTED_SELLERS_PARAM, selectedSellerIds);
		}
		else if (userId.equals(RolesUtil.ROLEID_SELLER_ADMIN)) {
			Integer dealingRelationId = DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER;
			userIds = dealingPatternService.getMembersByAdminId(users.getUserId(), dealingRelationId, startDate, endDate);
			for(User user : userIds) {
				selectedSellerIds.add(user.getUserId());
			}
			// List seller users
			lstSellerUsers = userInfoService.getListBuyerNamesById(selectedSellerIds);
			model.put("sellersList", lstSellerUsers);
			model.put("buyersList", lstBuyerUsers);
			//set session for download csv controller
			SessionHelper.setAttribute(request, SessionParamConstants.SELECTED_SELLERS_PARAM, selectedSellerIds);
		}
		else if (userId.equals(RolesUtil.ROLEID_BUYER_ADMIN)){
			Integer dealingRelationId = DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER;
			userIds = dealingPatternService.getMembersByAdminId(users.getUserId(), dealingRelationId, startDate, endDate);
			for(User user : userIds) {
				selectedBuyerIds.add(user.getUserId());
			}
//			selectedUserIds = userInfoService.getSellerUsersBySelectedBuyerId(selectedBuyerIds, startDate, endDate);
//			for(User user : selectedUserIds) {
//				selectedSellerIds.add(user.getUserId());
//			}
//			SessionHelper.setAttribute(request, SessionParamConstants.SELECTED_SELLERS_PARAM, selectedSellerIds);
//			lstSellerUsers = downloadCSVService.getBuyerCSVUsersList(selectedSellerIds);
			lstBuyerUsers = downloadCSVService.getBuyerCSVUsersList(selectedBuyerIds);
			
			//buyer users list
			//model.put("sellersList", lstSellerUsers);
			model.put("buyersList", lstBuyerUsers);
			
		}
		else{
			model.put("sellersList", "");
			model.put("buyersList", "");
		}
		
		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}
}
