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
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author Pammie
 * 
 */
public class PmfCancelNewPmfController extends SimpleFormController {
	ProductMasterFileService pmfService;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		if (null != SessionHelper.getAttribute(request, "newPmfSave") 
				&& SessionHelper.getAttribute(request, "newPmfSave") == "true") {
			System.out.println("TO DELETE: " + SessionHelper.getAttribute(request, "pmfId").toString());
			pmfService.deletePmf(Integer.parseInt(SessionHelper.getAttribute(request, "pmfId").toString()));
			model.put("response", request.getParameter("btnClicked"));
		} else {
			model.put("response", "");
		}

		return new ModelAndView("json", model);
	}

	public void setPmfService(ProductMasterFileService pmfService) {
		this.pmfService = pmfService;
	}
}
