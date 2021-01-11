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

import com.freshremix.abstractControllers.AbstractCategoryTabs;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.Category;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.Sheets;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.service.CategoryService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.DownloadCSVService;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author Jovino Balunan
 *
 */
public class DownloadCSVLoadSheetsController extends AbstractCategoryTabs {

	private DownloadCSVService downloadCSVService;
	private CategoryService categoryService;	
	private DealingPatternService dealingPatternService;
	
	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setDownloadCSVService(DownloadCSVService downloadCSVService) {
		this.downloadCSVService = downloadCSVService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String startdate = request.getParameter("startdate");
		OrderSheetParam osParam = (OrderSheetParam) SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM);
		User user = (User) SessionHelper.getAttribute (request, SessionParamConstants.USER_PARAM);
		Map<String, Object> model = new HashMap<String, Object>();
		
		if (SessionHelper.isSessionExpired(request)) {
			model.put("isSessionExpired", Boolean.TRUE);
		} else {				
			User users = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
			Integer roleId = Integer.valueOf(users.getRole().getRoleId().toString());
			List<Sheets> sheetsList = downloadCSVService.getSheetType(roleId);
			List <UsersCategory> categoryList =  this.getCategoryList(user, osParam, startdate);
			List<Integer> checkList = new ArrayList<Integer>();
			for (UsersCategory category : categoryList) {
				if(!checkList.contains(category.getCategoryId())){
					checkList.add(category.getCategoryId());
				}
			}
			List<Category> categories = categoryService.getCategoryById(checkList);
			model.put("sheetsList", sheetsList);			
			model.put("categoryList", categories);
		}
		return new ModelAndView("json", model);
	}
	
	/**
	 * retrieves registered categories for a user
	 * @param user
	 * @param osParam
	 * @return
	 */
	@SuppressWarnings({ "unused", "null" })
	private List<UsersCategory> getCategoryList(User user,OrderSheetParam osParam, String startDate){
		
		List<UsersCategory> categoryList = new ArrayList<UsersCategory>();
		if(user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)){
			categoryList = 
				categoryService.getCategoryListByUserId(user.getUserId().toString());
		}else{
			List<Integer> sellerIds = new ArrayList<Integer>();
			if (osParam == null) {
				Integer dealingRelationId = null;
				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
//					dealingRelationId = DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER;
					sellerIds = dealingPatternService.getSellerIdsOfSellerAdminId(user.getUserId());
				}else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)) {
//					dealingRelationId = DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER;
					sellerIds = dealingPatternService.getSellerIdsOfBuyerAdminId(user.getUserId());
				}else {
					sellerIds = dealingPatternService.getSellerIdsOfBuyerId(user.getUserId());
				}
			}else{
				sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
			}
			categoryList = 
				categoryService.getCategoryListBySelectedIds(sellerIds);
		}
		
		return categoryList;
	}
	
}
