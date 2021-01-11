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
 * Jul 15, 2010		Jovino Balunan		
 */
package com.freshremix.webapp.controller.downloadcsv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author Jovino Balunan
 *
 */
public class DownloadCSVFilterBuyerAdminSellersController implements Controller {
	@SuppressWarnings("unused")
	private DealingPatternService dealingPatternService;
	@SuppressWarnings("unused")
	private UsersInformationService userInfoService;
	
	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		String buyerIds = request.getParameter("buyerIds");
		String startDate = request.getParameter("startDate").replaceAll("/", "");
		String endDate = request.getParameter("endDate").replaceAll("/", "");
		
		User users = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		Integer userId = users.getUserId();
		List<User> sellerIds = null;
		
		List<Integer> buyerList = OrderSheetUtil.toList(buyerIds);
		List<FilteredIDs> listSellerIds = new ArrayList<FilteredIDs>();
		if(buyerList != null && !buyerList.isEmpty()){
			sellerIds = dealingPatternService.getAllSellerIdsByBuyerIds(OrderSheetUtil.toList(buyerIds), startDate, endDate);	
			if (sellerIds != null && sellerIds.size() > 0) {
				listSellerIds.add(new FilteredIDs("0", "All"));
				for (User _user : sellerIds) {
					listSellerIds.add(new FilteredIDs(_user.getUserId().toString(), _user.getName()));
				}
			}
		}
		
		// List seller users
		model.put("response", listSellerIds);
		
		return new ModelAndView("json",model);
	}

}
