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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.AdminUsers;
import com.freshremix.service.CategoryService;
import com.freshremix.service.ProductMasterFileService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.treegrid.TreeGridItem;

public class AdminDeleteUserController extends SimpleFormController {
	private CategoryService categoryService;
	private ProductMasterFileService pmfService;
	
	public void setPmfService(ProductMasterFileService pmfService) {
		this.pmfService = pmfService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	private UsersInformationService userInfoService;

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	@Override
	protected ModelAndView onSubmit(Object command, BindException errors)
			throws Exception {
		AdminUsers comm = (AdminUsers) command;
		Map<String, Object> model = new HashMap<String, Object>();
		String userId = comm.getUserId().toString().trim();
		categoryService.deleteUserCategory(userId);
		Integer retDelete = pmfService.deletePmfbyUserId(Integer.valueOf(userId));
		Integer iResult = 0;
//		if (retDelete == 1) 
		iResult = userInfoService.deleteUserById(userId);
		List<AdminUsers> lstUsers = userInfoService.getAllUsersByCompanyId(Integer.parseInt(comm.getCompanyId().trim()));
		List<TreeGridItem> tgis = new ArrayList<TreeGridItem>();

		long counter = 1l;
		for (AdminUsers users : lstUsers) {
			TreeGridItem tgi = new TreeGridItem();
			tgi.setId(counter);
			//tgi.addCell(counter);
			tgi.addCell(users.getUserName());
			tgi.addCell(users.getRoleName());
			tgi.addCell("Check details");
			tgi.addCell(users.getUserId());
			tgis.add(tgi);
			counter++;
		}
		model.put("status", iResult);
		model.put("userslist", tgis);
		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}
}
