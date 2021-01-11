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
 * Feb 17, 2010		jabalunan		
 */
package com.freshremix.webapp.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.AdminUsers;
import com.freshremix.model.PmfList;
import com.freshremix.model.User;
import com.freshremix.service.CategoryService;
import com.freshremix.service.ProductMasterFileService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

public class UserSaveInformationController extends SimpleFormController {
	private UsersInformationService userInfoService;
	private ProductMasterFileService pmfService;
	private CategoryService categoryService;
	private EONLocale eonLocale;

	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setPmfService(ProductMasterFileService pmfService) {
		this.pmfService = pmfService;
	}

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		AdminUsers admUsers = (AdminUsers) command;	 
//		Serializer serializer = new JsonSerializer();
//		Enumeration<String> keys = request.getParameterNames();
//		AdminUsers admUsers = null;
//
//		while (keys.hasMoreElements()) {
//			String key = keys.nextElement();
//			admUsers = (AdminUsers) serializer.deserialize(key, AdminUsers.class);
//		}
		User user = (User) SessionHelper.getAttribute(request,
				SessionParamConstants.USER_PARAM);
		Integer iResult = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		int existing= userInfoService.checkUserIfExist(admUsers.getUserName().trim(), admUsers.getCompanyId()); 
		if ( existing > 0) {
			iResult = -1;
		} else {
			Integer iUserId = userInfoService.insertUser(admUsers,user);
			if (iUserId == null){
				String errorMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
						getMessage("user.name.duplicate", eonLocale.getLocale()));
				errors.addError(new ObjectError("error", errorMsg));
			}else{
				if (RolesUtil.isSellerByRoleId(admUsers.getRoleId())) {
					PmfList pmfList = new PmfList();
					pmfList.setPmfName("FRC Master File");
					pmfList.setPmfUserId(iUserId);
					pmfService.insertNewPmf(pmfList);
				}
				if (admUsers.getRoleId().equals(1) ) {
					List<Integer> categories = OrderSheetUtil.toList(admUsers.getCategories());
					for(int i=0; i<categories.size(); i++) {
						categoryService.insertUserCategory(categories.get(i), iUserId,i);
					}
				}
				iResult = iUserId;
			}
		}

		model.put("userid", iResult);

		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}

}