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
 * May 31, 2010		nvelasquez		
 */
package com.freshremix.webapp.controller.sort.sku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.SKUColumn;
import com.freshremix.model.User;
import com.freshremix.service.SKUSortService;
import com.freshremix.util.SessionHelper;

/**
 * @author nvelasquez
 *
 */
public class SKUSortInit implements Controller {
	
	private SKUSortService skuSortService;
	
	public void setSkuSortService(SKUSortService skuSortService) {
		this.skuSortService = skuSortService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		List<SKUColumn> allDefCols = skuSortService.getDefaultColumns(user.getRole().getRoleId());
		List<SKUColumn> defCols = new ArrayList<SKUColumn>(allDefCols);
		List<SKUColumn> sortCols = skuSortService.getSKUSort(user.getUserId(), defCols);
		
		model.put("allDefCols", allDefCols);
		model.put("defCols", defCols);
		model.put("sortCols", sortCols);
		
		ModelAndView mav = new ModelAndView("json", model);
	    return mav;
	}

}
