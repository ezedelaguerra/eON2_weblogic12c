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
 * Feb 17, 2010		jabalunan		
 */
package com.freshremix.webapp.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.Category;
import com.freshremix.service.CategoryService;

public class AdminCategoryController extends SimpleFormController {

	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	protected ModelAndView onSubmit(Object command, BindException errors)
			throws Exception {
		List<Category> lstCategory = categoryService.getAllCategory();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", lstCategory);
		
		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}
}
