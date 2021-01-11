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
package com.freshremix.webapp.controller.sort.sku;

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
import com.freshremix.model.SKUColumn;
import com.freshremix.model.SKUSortParam;
import com.freshremix.model.User;
import com.freshremix.service.SKUSortService;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author nvelasquez
 *
 */
public class SKUSortSave extends SimpleFormController {
	
	private SKUSortService skuSortService;
	
	public void setSkuSortService(SKUSortService skuSortService) {
		this.skuSortService = skuSortService;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		SKUSortParam skuSortParam = (SKUSortParam) command;
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		List<SKUColumn> skuSorts = this.constructSKUSortOrder(skuSortParam);
		/*
		for (int i=0; i<skuSorts.size(); i++) {
			SKUColumn skuSort = skuSorts.get(i);
			System.out.println("sorting:[" + skuSort.getSorting() + "]");
			System.out.println("colId:[" + skuSort.getSkuColumnId() + "]");
		}*/
		skuSortService.insertSortSKU(skuSorts, user.getUserId());
		
		List<Integer> skuColIds = this.toList(skuSortParam.getSkuColIds());
		user.getPreference().setSkuSort(skuColIds);
		SessionHelper.setAttribute(request, SessionParamConstants.USER_PARAM, user);
		
		ModelAndView mav = new ModelAndView("json", model);
	    return mav;
	}
	
	private List<SKUColumn> constructSKUSortOrder(SKUSortParam skuSortParam) {
		List<SKUColumn> skuSorts = new ArrayList<SKUColumn>();
		
		List<Integer> skuColIds = this.toList(skuSortParam.getSkuColIds());
		
		for (int i=0; i<skuColIds.size(); i++) {
			SKUColumn skuSort = new SKUColumn();
			skuSort.setSkuColumnId(skuColIds.get(i));
			//skuSort.setSorting(i+1);
			skuSorts.add(skuSort);
		}
		
		return skuSorts;
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
