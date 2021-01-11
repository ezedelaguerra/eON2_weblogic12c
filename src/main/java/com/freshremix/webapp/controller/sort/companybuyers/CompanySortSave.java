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
 * Jul 23, 2010		raquino		
 */
package com.freshremix.webapp.controller.sort.companybuyers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.Company;
import com.freshremix.model.CompanySort;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.User;
import com.freshremix.service.CompanyBuyersSortService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author raquino
 *
 */
public class CompanySortSave extends SimpleFormController {
	
	
	private CompanyBuyersSortService companybuyersSortService;
	private EONLocale eonLocale;

	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}
	
	public void setCompanybuyersSortService(
			CompanyBuyersSortService companybuyersSortService) {
		this.companybuyersSortService = companybuyersSortService;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		System.out.println("saving company sort preference...");
		Map<String,Object> model = new HashMap<String,Object>();
		
		String json = request.getParameter("param");
		Serializer serializer = new JsonSerializer();
		List<Map<String,String>> updateList = (List<Map<String,String>>) serializer.deserialize(json, List.class);
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		List<CompanySort> companySortList = new ArrayList<CompanySort>();	
		for (Map<String,String> companySortMap:updateList){
			CompanySort companySort = new CompanySort();
			companySort.setUser(user);
			String sorting = String.valueOf(companySortMap.get("sorting"));
			companySort.setSorting(Integer.valueOf(sorting));
			String companyId = String.valueOf(companySortMap.get("companyId"));
			Company company = new Company();
			company.setCompanyId(Integer.valueOf(companyId));
			companySort.setCompany(company);
			companySortList.add(companySort);
		}
		
		try {
			companybuyersSortService.insertSortCompany(user.getUserId(), companySortList);
		} catch (Exception e) {
			e.printStackTrace();
			//String errorMsg = getMessageSourceAccessor().getMessage("ordersheet.skugroup.failedUpdate", request.getLocale());
			String errorMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
					getMessage("sort.failedSave", eonLocale.getLocale()));
			errors.addError(new ObjectError("error", errorMsg));
		}
		model.put("", companySortList);
		
		if (errors.hasErrors()) 
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json",model);
	}
	
	
	private List<Integer> toList(String str) {
		List<Integer> list = new ArrayList<Integer>();
		str = str.replaceAll("\\[", "");
		str = str.replaceAll("\\]", "");
		
		if (!StringUtil.isNullOrEmpty(str)) {
			if (str.indexOf(",") != -1) {
				StringTokenizer st = new StringTokenizer(str,",");
				while(st.hasMoreTokens()) {
					list.add(Integer.valueOf(st.nextToken().trim()));
				}
			}
			else {
				list.add(Integer.valueOf(str.trim()));
			}
		}
		
		return list;
	}
}
