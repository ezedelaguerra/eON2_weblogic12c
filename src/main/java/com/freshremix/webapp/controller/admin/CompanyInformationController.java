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

import com.freshremix.model.AdminCompanyInformation;
import com.freshremix.model.AdminUsers;
import com.freshremix.service.CompanyInformationService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.treegrid.TreeGridItem;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.MessageUtil;
import com.freshremix.util.StringUtil;

public class CompanyInformationController extends SimpleFormController {
	private CompanyInformationService companyInfoService;
	private UsersInformationService userInfoService;

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setCompanyInfoService(
			CompanyInformationService companyInfoService) {
		this.companyInfoService = companyInfoService;
	}

	@Override
	protected ModelAndView onSubmit(Object command, BindException errors)
			throws Exception {

		AdminCompanyInformation companyId = (AdminCompanyInformation) command;
		AdminCompanyInformation companyInfo = companyInfoService.getCompanyDetailsByCompanyId(companyId.getCompanyId());
		List<AdminUsers> lstUsers = userInfoService.getAllUsersByCompanyId(companyId.getCompanyId());
		List<TreeGridItem> tgis = new ArrayList<TreeGridItem>();
		String btnLabel = MessageUtil.getPropertyMessage(MessageUtil.checkDetails);

		long counter = 1l;
		for (AdminUsers users : lstUsers) {
			TreeGridItem tgi = new TreeGridItem();
			tgi.setId(counter);
			//tgi.addCell(counter);
			tgi.addCell(users.getUserName());
			tgi.addCell(users.getRoleName());
			tgi.addCell(btnLabel);
			tgi.addCell(users.getUserId());
			tgis.add(tgi);
			counter++;
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("companyInfo", companyInfo);
		model.put("userslist", tgis);
		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}

}
