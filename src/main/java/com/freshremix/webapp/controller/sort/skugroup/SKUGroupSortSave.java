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
 * Jun 1, 2010		nvelasquez		
 */
package com.freshremix.webapp.controller.sort.skugroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.SKUGroupSortParam;
import com.freshremix.model.User;
import com.freshremix.service.SKUGroupSortService;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author nvelasquez
 *
 */
public class SKUGroupSortSave extends SimpleFormController {
	
	private SKUGroupSortService skuGroupSortService;
	
	public void setSkuGroupSortService(SKUGroupSortService skuGroupSortService) {
		this.skuGroupSortService = skuGroupSortService;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		System.out.println("saving sku group sort preference...");
		
		Map<String, Object> model = new HashMap<String, Object>();
		SKUGroupSortParam skuGrpSortParam = (SKUGroupSortParam) command;
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		Integer skuCategoryId = Integer.valueOf(skuGrpSortParam.getSkuCategoryId());
		System.out.println(skuGrpSortParam.getSkuGroupIds());
		System.out.println(skuGrpSortParam.getSortOrder());
		
		List<Integer> skuGroupIds = this.toList(skuGrpSortParam.getSkuGroupIds());
		
		skuGroupSortService.insertSortSKUGroup(user.getUserId(), skuCategoryId, skuGroupIds);
		
		ModelAndView mav = new ModelAndView("json", model);
	    return mav;
	}
	
	private List<Integer> toList(String str) {
		List<Integer> list = new ArrayList<Integer>();
		str = str.replaceAll("\\[", "");
		str = str.replaceAll("\\]", "");
		
		if (!StringUtil.isNullOrEmpty(str)) {
			if (str.indexOf(",") != -1) {
				StringTokenizer st = new StringTokenizer(str,",");
				while(st.hasMoreTokens()) {
					list.add(Integer.valueOf(st.nextToken().trim()));
				}
			}
			else {
				list.add(Integer.valueOf(str.trim()));
			}
		}
		
		return list;
	}

}
