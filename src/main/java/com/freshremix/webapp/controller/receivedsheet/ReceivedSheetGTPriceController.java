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
 * Mar 30, 2010		nvelasquez		
 */
package com.freshremix.webapp.controller.receivedsheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.service.CategoryService;
import com.freshremix.service.ReceivedSheetService;
import com.freshremix.util.SessionHelper;

/**
 * @author nvelasquez
 *
 */
public class ReceivedSheetGTPriceController implements Controller {
	
	private ReceivedSheetService receivedSheetService;
	private CategoryService categoryService;
	
	public void setReceivedSheetService(ReceivedSheetService receivedSheetService) {
		this.receivedSheetService = receivedSheetService;
	}
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
//		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
//		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
//		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
//		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
//		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
//		List<UsersCategory> catList = categoryService.getCategoryList(user, osParam);
//		List<Integer> categoryId = new ArrayList<Integer>();
//		
//		for (UsersCategory _cat : catList) {
//			categoryId.add(_cat.getCategoryId());
//		}
//		Map<String, List<Integer>> mapOfMembersByDate = null;
//		if (RolesUtil.isUserRoleBuyerAdmin(user)){
//			mapOfMembersByDate = getMapOfMembersByDateFromSession(request);
//		}
//		ProfitInfo pi = new ProfitInfo();
//		
//		pi = receivedSheetService.getBuyerTotalPrices(user,
//				dateList, 
//				sellerIds,
//				buyerIds,
//				categoryId,
//				new Double(1.05),
//				user.getPreference().getProfitPreference().getPriceTaxOption(),mapOfMembersByDate );	
//		
//		model.put("priceInfo", pi);
		
		return new ModelAndView("json",model);
	}
	private Map<String, List<Integer>> getMapOfMembersByDateFromSession(
			HttpServletRequest request) {
		return (Map<String, List<Integer>>) SessionHelper.getAttribute(request, SessionParamConstants.MAP_OF_MEMBERS_BY_DATE);
	}
}
