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
 * Apr 25, 2010		raquino		
 */
package com.freshremix.webapp.controller.admin;

//import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import net.sf.sojo.interchange.Serializer;
//import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.AdminUsers;
import com.freshremix.model.User;
import com.freshremix.service.CategoryService;
import com.freshremix.service.UsersInformationService;

/**
 * @author raquino
 *
 */
public class UserDetails extends SimpleFormController {
	private UsersInformationService userInfoService;
	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
//		Serializer serializer = new JsonSerializer();
//		Enumeration<String> keys = request.getParameterNames();
//		User user = null;
//		
//		while (keys.hasMoreElements()) {
//			String key = keys.nextElement();			
//			user = (User) serializer.deserialize(key, User.class);
//		}
		
		User user = (User) command;
		Map<String, Object> model = new HashMap<String, Object>();
		Integer iResult=0;
		//iResult = userInfoService.updateUserById(admUsers);
		iResult = userInfoService.updateUserDetails(user);
		
		model.put("status", iResult);
		
		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}
}
