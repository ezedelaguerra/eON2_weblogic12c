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
 * Jun 22, 2010		Jovino Balunan		
 */
package com.freshremix.webapp.controller.downloadcsv;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.util.SessionHelper;

/**
 * @author Jovino Balunan
 *
 */
public class DownloadCSVCompanyInfoController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		if (SessionHelper.isSessionExpired(request)) {
			model.put("isSessionExpired", Boolean.TRUE);
		} else {
		}
		
		return new ModelAndView("csvusers", model);
	}

}
