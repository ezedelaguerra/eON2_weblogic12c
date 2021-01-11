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

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.CompanyRelationConstants;
import com.freshremix.model.CompanyDealingPattern;
import com.freshremix.service.CompanyDealingPatternService;
import com.freshremix.util.RelationPatternUtil;

public class AdminSaveCompDealingPatternController extends SimpleFormController {
	@SuppressWarnings("unused")
	private CompanyDealingPatternService compDPService;

	public void setCompDPService(CompanyDealingPatternService compDPService) {
		this.compDPService = compDPService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Serializer serializer = new JsonSerializer();
		CompanyDealingPattern cdp = (CompanyDealingPattern) command;
		String companyId = cdp.getCompanyId().toString();
		String roleType = request.getParameter("roleType");
		Integer relationId = RelationPatternUtil.dealingIdByRoleType(request.getParameter("roleType"));
		compDPService.resetCDPByCompanyId(companyId.trim(), "0");
		List<String> obj = (List<String>) serializer.deserialize(request.getParameter("selectedCompId"), List.class);
		Integer iResult = 0;
		for (int i = 0; i < obj.size(); i++) {
			String selectedId = (String) obj.get(i).toString();
			// check if companyId and selected company id exist if not then
			// insert
			try {
				
				String sellerCompanyId = companyId;
				String buyerCompanyId = selectedId;
				
				//if role is buyer
				//treat companyId as buyerCompanyId
				if(roleType.equalsIgnoreCase("buyer")){
					 sellerCompanyId = selectedId;
					 buyerCompanyId = companyId;
				}
				
				//check if company dealing pattern exists
				if (compDPService.checkIfCDPExist(sellerCompanyId, buyerCompanyId) < 1){
					compDPService.insertCompanyDealingPattern(sellerCompanyId, buyerCompanyId, relationId);
					
				}else{
					compDPService.updateCDPActive(sellerCompanyId, buyerCompanyId, "1");
				}
				iResult = 1;
			}
			catch(Exception ex) {
				throw new Exception("Error in executing query. " + ex.getMessage());				
			}
			
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("status", iResult);
		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}
}
