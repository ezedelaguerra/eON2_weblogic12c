package com.freshremix.webapp.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.freshremix.abstractControllers.AbstractCategoryTabs;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.util.SessionHelper;

public class CategoryTabsController extends AbstractCategoryTabs {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		User user = (User) SessionHelper.getAttribute(request,
				SessionParamConstants.USER_PARAM);
		
		OrderSheetParam osParam = new OrderSheetParam();
		String value ="";
		Map<String,Object> categories = new HashMap<String, Object>();
		if(user.getRole().getRoleId().intValue() != RoleConstants.ROLE_SELLER){
			 value = request.getParameter("obj").toString();
			 osParam.setSelectedSellerID(value);
			 
			 if(!value.equals("")){
					categories = super.getUserCategories(user, osParam);
			 }else{
					categories=this.emptyCategories();
			 } 
			
		}else{
			categories = super.getUserCategories(user, osParam);
		}
		
		Map<String,Object> model = new HashMap<String,Object>();
		
		
		model.put("index", categories.get("index"));
		model.put("categories",categories.get("categories"));
		
		return new ModelAndView("json",model);
	}
    	
	private Map<String,Object> emptyCategories(){
		
		Map<String,Object> categories = new HashMap<String,Object>();
		List<Map<String,String>>mapList = new ArrayList<Map<String,String>>();
		String index = "0";
		
		categories.put("index", index);
		categories.put("categories", mapList);
		return categories;
	}
	
	
	
}
