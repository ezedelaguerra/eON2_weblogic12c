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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.service.CompanyDealingPatternService;
import com.freshremix.util.DateFormatter;

public class AdminRemoveCDPController extends SimpleFormController {
	
	private CompanyDealingPatternService compDPService;

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Integer companyId = Integer.parseInt(request.getParameter("companyId")
				.toString());
		Integer selectedCompanyId = Integer.parseInt(request.getParameter(
				"selectedCompanyId").toString());
		Boolean hasAnyDealingPattern = Boolean.FALSE;
//		iTrue = compDPService.checkIfSelectedUDP(companyId, selectedCompanyId);
//		String roleType = request.getParameter("companyType");
		
		String sellerCompanyId = companyId.toString();
		String buyerCompanyId = selectedCompanyId.toString();

//		if (roleType.equalsIgnoreCase("buyer")) {
//			sellerCompanyId = selectedCompanyId.toString();
//			buyerCompanyId = companyId.toString();
//		}
//
//		 List<Integer> iList =  compDPService.getCDPByCompanyId(sellerCompanyId,buyerCompanyId);
//
//		 for (int i = 0; i < iList.size(); i++) {
//			 Integer value = iList.get(i);
//			 if(value > 0){
//				 iTrue = 1;
//				 break;
//			 }
//		 }

		/**
		 *  Get company id
			Get all user id based on company id (date = today onwards)
			After getting all the user id
			  -- get admin > buyer/seller > store
			  -- get user > store
			  -- store check if each user has dealing pattern
			
			if there are any dealing pattern, throw error
		 */
		hasAnyDealingPattern = 
			compDPService.hasAnyDealingPattern(selectedCompanyId, DateFormatter.convertToString(new Date()));
		
		 //update active flg
		 //if user dealing pattern members are equal to none
		 if(!hasAnyDealingPattern){
			 compDPService.updateCDPActive(sellerCompanyId, buyerCompanyId, "0");
		 }
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", !hasAnyDealingPattern);

		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}

	public void setCompDPService(CompanyDealingPatternService compDPService) {
		this.compDPService = compDPService;
	}

}
