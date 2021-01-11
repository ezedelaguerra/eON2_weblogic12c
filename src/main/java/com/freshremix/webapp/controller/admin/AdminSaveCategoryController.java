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
 * May 30, 2010		Jovino Balunan		
 */
package com.freshremix.webapp.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.Category;
import com.freshremix.service.CategoryService;

/**
 * @author Jovino Balunan
 *
 */
public class AdminSaveCategoryController extends SimpleFormController {

	private CategoryService categoryService;
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
			Object command, BindException errors)
			throws Exception {
		
		String categoryName = String.valueOf(request.getParameter("description"));
		categoryService.saveAdminCategoryDao(categoryName, "");
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}

}
