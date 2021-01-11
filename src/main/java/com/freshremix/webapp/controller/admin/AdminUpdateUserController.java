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
import com.freshremix.model.User;
import com.freshremix.service.CategoryService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

public class AdminUpdateUserController extends SimpleFormController {
	private UsersInformationService userInfoService;
	private CategoryService categoryService;
	private EONLocale eonLocale;

	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		User user = (User) SessionHelper.getAttribute(request,
				SessionParamConstants.USER_PARAM);
		AdminUsers admUsers = (AdminUsers) command;	    
		Map<String, Object> model = new HashMap<String, Object>();
		Integer iResult=0;
		iResult = userInfoService.updateUserById(admUsers,user);
		if (iResult == null){
			String errorMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
					getMessage("user.name.duplicate", eonLocale.getLocale()));
			errors.addError(new ObjectError("error", errorMsg));
		} 
		//else if (RolesUtil.isSellerByRoleId(admUsers.getRoleId())) {
		else {
			categoryService.deleteUserCategory(admUsers.getUserId().toString());
			List<Integer> categories = OrderSheetUtil.toList(admUsers.getCategories());
			for(int i=0; i<categories.size(); i++) {
				categoryService.insertUserCategory(categories.get(i), Integer.valueOf(admUsers.getUserId().toString()),i);
			}
		}
		
		model.put("status", iResult);
		
		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}
}

