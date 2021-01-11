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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.AdminCompanyInformation;
import com.freshremix.model.CompanyType;
import com.freshremix.service.CompanyInformationService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.StringUtil;

public class CompanySaveInformationController extends SimpleFormController {
	private CompanyInformationService companyInfoService;
	private EONLocale eonLocale;

	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}

	public void setCompanyInfoService(
			CompanyInformationService companyInfoService) {
		this.companyInfoService = companyInfoService;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		AdminCompanyInformation adminInfo = (AdminCompanyInformation) command;
		CompanyType companyType = new CompanyType();
		String companyTypeDesc = request.getParameter("description");
		companyType.setDescription(companyTypeDesc);
		companyType.setCompanyTypeId(getCompanyTypeId(companyTypeDesc));
		adminInfo.setCompanyType(companyType);
		
		Map<String, Object> model = new HashMap<String, Object>();
		Integer iRet = 0;
		if (adminInfo.getCompanyId() == null) {
			if (companyInfoService.checkCompanyIfExists(adminInfo.getCompanyName()) > 0){
				iRet = -1; //-1 - exists //1 - success				
			} else {
				iRet = companyInfoService.insertNewCompany(adminInfo);
				if (iRet == null){
					String errorMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
							getMessage("company.name.duplicate", eonLocale.getLocale()));
					errors.addError(new ObjectError("error", errorMsg));
				}
			}
		} else {
			iRet = companyInfoService.updateCompany(adminInfo);
			if (iRet == null){
				String errorMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
						getMessage("company.name.duplicate", eonLocale.getLocale()));
				errors.addError(new ObjectError("error", errorMsg));
			}
		}

		model.put("status", iRet);

		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	private Integer getCompanyTypeId(String str){
		Integer typeId = new Integer(1);
		
		if(str.equalsIgnoreCase("Seller")){
			typeId = 2;
		}else{
			typeId = 3;
		}
		return typeId; 
	}
}
