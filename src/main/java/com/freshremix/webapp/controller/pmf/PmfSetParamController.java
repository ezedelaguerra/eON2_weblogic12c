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
 * Mar 1, 2010		pamela		
 */
package com.freshremix.webapp.controller.pmf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.dao.UserDao;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.ProductMasterFileService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author Pammie
 *
 */
public class PmfSetParamController extends SimpleFormController {
	SKUGroupService skuGroupService;
	ProductMasterFileService pmfService;
	OrderSheetService orderSheetService;
	UsersInformationService userInfoService;
	private UserDao usersInfoDaos;
	
	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}

	public void setPmfService(ProductMasterFileService pmfService) {
		this.pmfService = pmfService;
	}
	
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		User user = (User)SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		OrderSheetParam orderSheetParam = (OrderSheetParam) SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM);
		Integer categoryId = Integer.valueOf(request.getParameter("categoryId"));
		SessionHelper.setAttribute(request, "categoryId", categoryId);
		
		if (!StringUtil.isNullOrEmpty(request.getParameter("btnClicked"))){
			if(request.getParameter("btnClicked").equalsIgnoreCase("copyPMF")){
				SessionHelper.setAttribute(request, "btnClicked", request.getParameter("btnClicked"));
			} else if(request.getParameter("btnClicked").equalsIgnoreCase("findSKU") ||
					request.getParameter("btnClicked").equalsIgnoreCase("masterList")){
				SessionHelper.setAttribute(request, "searchName", request.getParameter("searchName"));
			} else {
				SessionHelper.setAttribute(request, "pmfName", request.getParameter("pmfName"));
				SessionHelper.setAttribute(request, "pmfId", request.getParameter("pmfId"));
				SessionHelper.setAttribute(request, "searchName", request.getParameter("searchName"));
				SessionHelper.setAttribute(request, "newPmfSave", "");
			}
		}
		
		//for new pmf
		if (!StringUtil.isNullOrEmpty(SessionHelper.getAttribute(request, "newPmfSave"))){
			String newPmf = SessionHelper.getAttribute(request, "newPmfSave").toString();
			if (newPmf == "true"){
				SessionHelper.setAttribute(request, "pmfName", (String)SessionHelper.getAttribute(request, "pmfNewName"));
				SessionHelper.setAttribute(request, "pmfId", (String)SessionHelper.getAttribute(request, "pmfNewId"));
			}
		}
		
		String pmfId = StringUtil.isNullOrEmpty(request.getParameter("pmfId")) ? (String)SessionHelper.getAttribute(request, "pmfId") : request.getParameter("pmfId");
		Integer userId = pmfService.getUserIdByPmfId(pmfId != null ? Integer.valueOf(pmfId) : 0);
		SessionHelper.setAttribute(request, SessionParamConstants.PMF_USER_PARAM, userId);
		String skuGroupList = skuGroupService.getSKUGroupList(userId, categoryId);
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);

		// set sku group renderer
		List<Integer> seller = new ArrayList<Integer>();
		if (user.getRole().getSellerFlag().equals("1")) { 
			seller.add(user.getUserId());
			SessionHelper.setAttribute(request, SessionParamConstants.IS_ADMIN_PARAM, false);
		}
		else {
			List<Integer> sellerId = OrderSheetUtil.toList(orderSheetParam.getSelectedSellerID());
			seller.addAll(sellerId);
			SessionHelper.setAttribute(request, SessionParamConstants.IS_ADMIN_PARAM, true);
		}
		
		String skuGroupRenderer = skuGroupService.getSKUGroupRenderer(seller, categoryId);
        SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_RENDERER_PARAM, skuGroupRenderer);

        String sellerNameList = orderSheetService.getSellerNames(seller);
		SessionHelper.setAttribute(request, SessionParamConstants.SELLER_NAME_LIST_PARAM, sellerNameList);
		
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)){
			User pmfUser = userInfoService.getUserById(userId);
			String sellerName = pmfUser.getUserName();
			SessionHelper.setAttribute(request, SessionParamConstants.PMF_SELLER_NAME, sellerName);
			//TODO: Use seller user id not shortname
			//		Use same impl in UOM
			User pmfUserCompany = usersInfoDaos.getUserByShortName(sellerName, pmfUser.getCompany().getCompanyId());
			SessionHelper.setAttribute(request, SessionParamConstants.PMF_COMPANY_NAME, pmfUserCompany.getCompany().getShortName());
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("response", SessionHelper.getAttribute(request, "pmfName"));
		return new ModelAndView("json", model);
	}
}
