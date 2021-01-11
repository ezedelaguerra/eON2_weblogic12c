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
 * Mar 3, 2010		pamela		
 */
package com.freshremix.webapp.controller.pmf;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.PmfList;
import com.freshremix.model.User;
import com.freshremix.service.ProductMasterFileService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author pamela
 *
 */
public class PmfNewPmfController extends SimpleFormController {
	ProductMasterFileService pmfService;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

//		if (request.getParameter("btnClicked").equalsIgnoreCase("newPmfSave")){
			SessionHelper.setAttribute(request, "newPmfSave", "true");

			String newPmfName = request.getParameter("pmfName");
			Integer sellerId = Integer.parseInt(StringUtil.nullToZero(request.getParameter("pmfUserId")));
			PmfList pmfList = new PmfList();
			pmfList.setPmfName(newPmfName);
			pmfList.setPmfUserId(sellerId);
			
			Integer pmfCount = pmfService.getPmfNameCount(sellerId, newPmfName);

			if(pmfCount==0){
				Integer pmfId = pmfService.insertNewPmf(pmfList);
				
				SessionHelper.setAttribute(request, "pmfNewName", request.getParameter("pmfName"));
				SessionHelper.setAttribute(request, "pmfNewId", pmfId.toString());
				SessionHelper.setAttribute(request, "searchName", request.getParameter("searchName"));

				model.put("response", request.getParameter("pmfName"));
			} else {
				String msg = "Product Master File exists.";
				model.put("response", msg);
			}
//		}
		
		return new ModelAndView("json", model);
	}

	public void setPmfService(ProductMasterFileService pmfService) {
		this.pmfService = pmfService;
	}
}
