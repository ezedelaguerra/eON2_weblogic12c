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
 * Jun 17, 2010		raquino		
 */
package com.freshremix.webapp.controller.skugroup;

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
import com.freshremix.model.SKUGroup;
import com.freshremix.model.User;
import com.freshremix.service.SKUGroupService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author raquino
 *
 */
public class SKUGroupDeleteController  extends SimpleFormController {

	private SKUGroupService skuGroupService;
	private EONLocale eonLocale;
	
	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}

	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Map<String,Object> model = new HashMap<String,Object>();
		
		String json = request.getParameter("param");
		Serializer serializer = new JsonSerializer();
		List<Map<String,String>> updateList = (List<Map<String,String>>) serializer.deserialize(json, List.class);
		
		List<Integer> skuGroupIds = new ArrayList<Integer>();
		for (Map<String,String> skuGroupMap:updateList){
			Integer skuGroupId = Integer.valueOf(skuGroupMap.get("skuGroupId"));
			skuGroupIds.add(skuGroupId);
		}
		
		try {
			skuGroupService.deleteSKUGroup(skuGroupIds);
		} catch (Exception e) {
			e.printStackTrace();
			//String errorMsg = getMessageSourceAccessor().getMessage("ordersheet.skugroup.failedDelete", request.getLocale());
			String errorMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
					getMessage("ordersheet.skugroup.failedDelete", eonLocale.getLocale()));
			errors.addError(new ObjectError("error", errorMsg));
		}
		model.put("", skuGroupIds);
		
		if (errors.hasErrors()) 
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json",model);
	}
}
