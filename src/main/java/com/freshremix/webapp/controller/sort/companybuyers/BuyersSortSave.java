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
import com.freshremix.model.Company;
import com.freshremix.model.CompanySort;
import com.freshremix.model.User;
import com.freshremix.service.CompanyBuyersSortService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author raquino
 *
 */
public class BuyersSortSave extends SimpleFormController {
	
	
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
		
		System.out.println("saving buyer sort preference...");
		Map<String,Object> model = new HashMap<String,Object>();
		
		String json = request.getParameter("param");
		Serializer serializer = new JsonSerializer();
		List<Map<String,String>> updateList = (List<Map<String,String>>) serializer.deserialize(json, List.class);
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		List<BuyersSort> buyerSortList = new ArrayList<BuyersSort>();	
		for (Map<String,String> buyerSortMap:updateList){
			BuyersSort buyerSort = new BuyersSort();
			buyerSort.setUser(user);
			String sorting = String.valueOf(buyerSortMap.get("sorting"));
			buyerSort.setSorting(Integer.valueOf(sorting));
			//String buyerId = String.valueOf(buyerSortMap.get("buyerId"));
			//buyerSort.setCompanyId(Integer.valueOf(buyerSortMap.get("companyId")));
			User buyer = new User();
			buyer.setUserId(Integer.valueOf(buyerSortMap.get("buyerId")));
			buyerSort.setBuyer(buyer);
			buyerSortList.add(buyerSort);
		}
		
		try {
			List<CompanySort> companySorts = (List<CompanySort>) SessionHelper.getAttribute(request, SessionParamConstants.SORTED_COMPANIES);
			companybuyersSortService.insertSortBuyers(user.getUserId(), buyerSortList, companySorts);
		} catch (Exception e) {
			e.printStackTrace();
			//String errorMsg = getMessageSourceAccessor().getMessage("ordersheet.skugroup.failedUpdate", request.getLocale());
			String errorMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
					getMessage("sort.failedSave", eonLocale.getLocale()));
			errors.addError(new ObjectError("error", errorMsg));
		}
		model.put("", buyerSortList);
		
		if (errors.hasErrors()) 
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json",model);
	}
}
