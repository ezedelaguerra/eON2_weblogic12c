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
 * Nov 18, 2011		raquino		
 */
package com.freshremix.webapp.controller.sort.companysellers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.BuyersSort;
import com.freshremix.model.CompanySort;
import com.freshremix.model.User;
import com.freshremix.service.CompanyBuyersSortService;
import com.freshremix.service.CompanySellersSortService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.MessageUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author raquino
 *
 */
public class SellersSortSave extends SimpleFormController {
	
	
	private CompanySellersSortService companySellersSortService;
	
	public void setCompanySellersSortService(
			CompanySellersSortService companySellersSortService) {
		this.companySellersSortService = companySellersSortService;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		
		String json = request.getParameter("param");
		Serializer serializer = new JsonSerializer();
		List<Map<String,String>> updateList = (List<Map<String,String>>) serializer.deserialize(json, List.class);
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		try {
			companySellersSortService.insertSortSellers(user, updateList);
			List<FilteredIDs> sortedSellers = companySellersSortService.getSortedSellers(user);
			user.getPreference().setSortedSellers(sortedSellers);
			SessionHelper.setAttribute(request, SessionParamConstants.USER_PARAM, user);
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = MessageUtil.getPropertyMessage("sort.failedSave");
			errors.addError(new ObjectError("error", errorMsg));
		}
		model.put("", updateList);
		
		if (errors.hasErrors()) 
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json",model);
	}

}
