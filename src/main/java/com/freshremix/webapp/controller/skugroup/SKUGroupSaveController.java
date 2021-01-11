package com.freshremix.webapp.controller.skugroup;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class SKUGroupSaveController extends SimpleFormController {

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
		
		SKUGroup skuGroup = (SKUGroup) command;
		//skuGroup.setCompanySellerId(getSellerID((String)request.getSession().getAttribute("sellerNameIDMap"), skuGroup.getCompanySellerId()));
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		System.out.println("skuGroup.getSellerId()--->>>>>>"+skuGroup.getSellerId());
		//skuGroup.setCompanySellerId(user.getUserId().toString());
		try {
			if(!skuGroupService.saveSKUGroup(skuGroup)){
				//String errorMsg = getMessageSourceAccessor().getMessage("ordersheet.skugroup.duplicate", request.getLocale());
				String errorMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
						getMessage("ordersheet.skugroup.duplicate", eonLocale.getLocale()));
				errors.addError(new ObjectError("error", errorMsg));
			}
		} catch (Exception e) {
			e.printStackTrace();
			//String errorMsg = getMessageSourceAccessor().getMessage("ordersheet.skugroup.failedInsert", request.getLocale());
			String errorMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
					getMessage("ordersheet.skugroup.failedInsert", eonLocale.getLocale()));
			errors.addError(new ObjectError("error", errorMsg));
		}
		model.put("", skuGroup);
		
		if (errors.hasErrors()) 
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json",model);
	}
	
//	@SuppressWarnings("unchecked")
//	private String getSellerID(String jsonNameIDMap, String sellerName) {
//		Serializer serializer = new JsonSerializer();
//		Map<String,String> nameIDMap = (Map<String,String>) serializer.deserialize(jsonNameIDMap , Map.class);
//		return (String) nameIDMap.get(sellerName);
//	}
}
