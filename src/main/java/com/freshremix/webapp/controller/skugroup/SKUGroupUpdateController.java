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
 * May 31, 2010		raquino		
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

import com.freshremix.model.SKUGroup;
import com.freshremix.service.SKUGroupService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.StringUtil;

/**
 * @author raquino
 *
 */
public class SKUGroupUpdateController  extends SimpleFormController {

	private SKUGroupService skuGroupService;
	private EONLocale eonLocale;

	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}
	
	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Map<String,Object> model = new HashMap<String,Object>();
		
//		String json = request.getParameter("param");
//		Serializer serializer = new JsonSerializer();
//		List<Map<String,String>> updateList = (List<Map<String,String>>) serializer.deserialize(json, List.class);
//		
//		List<SKUGroup> skuGroupList = new ArrayList<SKUGroup>();
//		for (Map<String,String> skuGroupMap:updateList){
//			SKUGroup skuGroup = new SKUGroup();
//			skuGroup.setSkuGroupId(Integer.valueOf(skuGroupMap.get("skuGroupId")));
//			if (!skuGroupMap.get("origSkuGroupId").isEmpty())
//				skuGroup.setOrigSkuGroupId(Integer.valueOf(skuGroupMap.get("origSkuGroupId")));
//			skuGroup.setSellerId(Integer.valueOf(skuGroupMap.get("sellerId")));
//			skuGroup.setCategoryId(Integer.valueOf(skuGroupMap.get("categoryId")));
//			skuGroup.setDescription(skuGroupMap.get("description").trim());
//			skuGroupList.add(skuGroup);
//		}
		
		SKUGroup skuGroup = (SKUGroup) command;
		
		try {
			if(!skuGroupService.updateSKUGroup(skuGroup)){
				//String errorMsg = getMessageSourceAccessor().getMessage("ordersheet.skugroup.duplicate", request.getLocale());
				String errorMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
						getMessage("ordersheet.skugroup.duplicate", eonLocale.getLocale()));
				errors.addError(new ObjectError("error", errorMsg));
			}
		} catch (Exception e) {
			e.printStackTrace();
			//String errorMsg = getMessageSourceAccessor().getMessage("ordersheet.skugroup.failedUpdate", request.getLocale());
			String errorMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
					getMessage("ordersheet.skugroup.failedUpdate", eonLocale.getLocale()));
			errors.addError(new ObjectError("error", errorMsg));
		}
		//model.put("", skuGroupList);
		
		if (errors.hasErrors()) 
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json",model);
	}
	
}
