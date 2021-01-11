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
import com.freshremix.service.CompanyInformationService;
import com.freshremix.treegrid.TreeGridItem;

public class AdminCompanyAutoSearchController extends SimpleFormController {
	private CompanyInformationService companyInfoService;

	public void setCompanyInfoService(CompanyInformationService companyInfoService) {
		this.companyInfoService = companyInfoService;
	}

	@Override
	protected ModelAndView onSubmit(Object command, BindException errors)
			throws Exception {
		AdminCompanyInformation info = (AdminCompanyInformation) command;
		List<AdminCompanyInformation> lstNames = companyInfoService.getCompanyNameByWildCard(info.getCompanyName().trim());
		List<TreeGridItem> tgis = new ArrayList<TreeGridItem>();

		for (AdminCompanyInformation names : lstNames) {
			TreeGridItem tgi = new TreeGridItem();
			tgi.addCell(names.getCompanyName());
			tgi.addCell(names.getCompanyId());
			tgis.add(tgi);
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("searcResults", tgis);

		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}
}
