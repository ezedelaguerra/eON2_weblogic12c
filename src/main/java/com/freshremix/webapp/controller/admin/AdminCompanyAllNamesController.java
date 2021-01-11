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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.AdminCompanyInformation;
import com.freshremix.service.CompanyInformationService;
import com.freshremix.treegrid.TreeGridItem;

public class AdminCompanyAllNamesController extends SimpleFormController {
	@SuppressWarnings("unused")
	private CompanyInformationService companyInfoService;

	public void setCompanyInfoService(
			CompanyInformationService companyInfoService) {
		this.companyInfoService = companyInfoService;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		AdminCompanyInformation adm = (AdminCompanyInformation) command;
		String paramValue = request.getParameter("isDealingPattern");
		String companyType = request.getParameter("descr");
		List<AdminCompanyInformation> lstNames;
		if (paramValue.equalsIgnoreCase("1")) {
			lstNames = companyInfoService.getAllCompanyNameByDealingPattern(companyType.trim().toLowerCase());
			model.put("selectedCDPattern", companyInfoService.getCompanySelectedDealingPattern(adm.getCompanyId().toString(),companyType));
		} else {
			lstNames = companyInfoService.getAllCompanyName();
		}
		List<TreeGridItem> tgis = new ArrayList<TreeGridItem>();

		for (AdminCompanyInformation names : lstNames) {
			TreeGridItem tgi = new TreeGridItem();
			tgi.addCell(names.getCompanyName());
			tgi.addCell(names.getCompanyId());
			tgis.add(tgi);
		}

		model.put("companyNames", tgis);

		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}
}
