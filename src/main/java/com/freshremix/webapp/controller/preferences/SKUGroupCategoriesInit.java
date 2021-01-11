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
package com.freshremix.webapp.controller.preferences;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.freshremix.abstractControllers.AbstractCategoryTabs;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.User;
import com.freshremix.service.SKUSortService;
import com.freshremix.util.SessionHelper;

/**
 * @author Jr
 *
 */
public class SKUGroupCategoriesInit extends AbstractCategoryTabs {
//	
//	private SKUSortService skuSortService;
//	
//	public void setSkuSortService(SKUSortService skuSortService) {
//		this.skuSortService = skuSortService;
//	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> model = new HashMap<String,Object>();
		User user = (User) SessionHelper.getAttribute
				(request, SessionParamConstants.USER_PARAM);
		Map categories = super.getUserCategories(user);
		model.put("categories",categories.get("categories"));
		model.put("index",categories.get("index"));
		ModelAndView mav = new ModelAndView("json", model);
	    return mav;
	}

}
