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
 * Jun 3, 2010		Jovino Balunan		
 */
package com.freshremix.webapp.controller.downloadcsv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.DownloadCSVService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.CategoryUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author Jovino Balunan
 *
 */
public class DownloadCSVParamsController implements Controller {

	private DownloadCSVService downloadCSVService;
	private UsersInformationService userInfoService;
	private DealingPatternService dealingPatternService;
	
	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setDownloadCSVService(DownloadCSVService downloadCSVService) {
		this.downloadCSVService = downloadCSVService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		if (SessionHelper.isSessionExpired(request)) {
			model.put("isSessionExpired", Boolean.TRUE);
		} else {
			OrderSheetParam osParam = (OrderSheetParam) SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM);
			User users = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
			if (osParam != null) {
				Integer roleId = Integer.valueOf(users.getRole().getRoleId().toString());
				List<String> lstCSVUsers = new ArrayList<String>();
				List<String> buyerList = new ArrayList<String>();
				List<Integer> lstUserId = new ArrayList<Integer>();
				List<Integer> selectedSellerIds = new ArrayList<Integer>();
				List<Integer> userIds = new ArrayList<Integer>();
				String selectedIds = "", selectedBuyerIds = "";
				List<User> selectedUserIds = null;
				
				if (roleId.equals(RolesUtil.ROLEID_SELLER)) {
					lstUserId.add(users.getUserId());
					selectedUserIds = dealingPatternService.getAllBuyerIdsBySellerIds(lstUserId, osParam.getStartDate(), osParam.getEndDate());
					for(User user : selectedUserIds) {
						selectedSellerIds.add(user.getUserId());
					}
					lstCSVUsers = downloadCSVService.getBuyerCSVUsersList(selectedSellerIds);
					selectedIds = osParam.getSelectedBuyerID();
				}
				else if (roleId.equals(RolesUtil.ROLEID_BUYER)) {
					List<User> lstUsers = dealingPatternService.getUserSellersBySellerCompanyIds(users.getUserId(), OrderSheetUtil.toList(osParam.getSelectedSellerCompany()), osParam.getStartDate(), osParam.getEndDate());
					for(User user : lstUsers) {
						userIds.add(user.getUserId());
					}
					lstCSVUsers = downloadCSVService.getBuyerCSVUsersList(userIds);
					selectedIds = osParam.getSelectedSellerID();
				}
				else if (roleId.equals(RolesUtil.ROLEID_SELLER_ADMIN)) {
					lstCSVUsers = this.listUsers(osParam.getSelectedSellerID());
					selectedIds = osParam.getSelectedSellerID();
					selectedBuyerIds = osParam.getSelectedBuyerID();
					List<User> ids = dealingPatternService.getUserBuyersByUserSellersAndBuyerCompanyIds(OrderSheetUtil.toList(osParam.getSelectedSellerID()), OrderSheetUtil.toList(osParam.getSelectedBuyerCompany()), osParam.getStartDate(), osParam.getEndDate());
					for(User user : ids) {
						userIds.add(user.getUserId());
					}
					buyerList = downloadCSVService.getBuyerCSVUsersList(OrderSheetUtil.toList(osParam.getSelectedBuyerID()));
				}
				else if (roleId.equals(RolesUtil.ROLEID_BUYER_ADMIN)){
					lstCSVUsers = this.listUsers(osParam.getSelectedSellerID());
					selectedIds = osParam.getSelectedSellerID();
					selectedBuyerIds = osParam.getSelectedBuyerID();
					List<Integer> allBuyerId = new ArrayList<Integer>();
					
					List<User> userList = dealingPatternService.getMembersByAdminId(users.getUserId(), 
							DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER, 
							osParam.getStartDate(), osParam.getEndDate());
					for(User user : userList) {
						allBuyerId.add(user.getUserId());
					}
					List<User> ids = dealingPatternService.getUserBuyersByUserSellersAndBuyerCompanyIds(OrderSheetUtil.toList(osParam.getSelectedSellerID()), OrderSheetUtil.toList(osParam.getSelectedBuyerCompany()), osParam.getStartDate(), osParam.getEndDate());
					for(User user : ids) {
						userIds.add(user.getUserId());
					}
					buyerList = downloadCSVService.getBuyerCSVUsersList(allBuyerId);
				}
				
				model.put("startdate", DateFormatter.formatToGUIParameter(osParam.getStartDate()));
				model.put("enddate", DateFormatter.formatToGUIParameter(osParam.getEndDate()));
				model.put("success", 1);
				model.put("usersList", lstCSVUsers);
				model.put("buyersList", buyerList);
				model.put("selectedIds", selectedIds);
				model.put("selectedBuyerIds", selectedBuyerIds);
				model.put("categoryName", osParam.getCategoryId());
				model.put("defaultsheet", osParam.getSheetTypeId());
			} else {
				model.put("startdate", "");
				model.put("enddate", "");
				model.put("success", 0);
				model.put("usersList", "");
				model.put("buyersList", "");
				model.put("selectedIds", "");
				model.put("selectedBuyerIds", "");
				model.put("categoryName", "");
				model.put("defaultsheet", "");
			}
		}
		return new ModelAndView("json", model);
	}
	
	@SuppressWarnings("unused")
	private List<String> listUsers(String userIds) {
		List<String> users = null;
		users = userInfoService.getListBuyerNamesById(OrderSheetUtil.toList(userIds));
		return users;
	}
	
	@SuppressWarnings("unused")
	private List<FilteredIDs> lstFilteredBuyers(List<User> ids) {
		List<FilteredIDs> filteredbuyers = new ArrayList<FilteredIDs>();
		
		if (ids != null && ids.size() > 0) {
			filteredbuyers.add(new FilteredIDs("0", "All"));
			for (User _user : ids) {
				filteredbuyers.add(new FilteredIDs(_user.getUserId().toString(), _user.getName()));
			}
		}
		return filteredbuyers;
	}
}
