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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.UserDealingPattern;
import com.freshremix.service.CompanyDealingPatternService;
import com.freshremix.util.RolesUtil;

public class AdminSaveUsersDealingPatternController extends
		SimpleFormController {
	private CompanyDealingPatternService compDPService;

	public void setCompDPService(CompanyDealingPatternService compDPService) {
		this.compDPService = compDPService;
	}

	@SuppressWarnings( { "unchecked" })
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		Serializer serializer = new JsonSerializer();
		String companyId = request.getParameter("companyId").trim();
		String userId = request.getParameter("userId").trim();
		String roleName = request.getParameter("roleName").trim();
		Integer newRole = Integer.parseInt(request.getParameter("newRole").toString());
		Integer roleId = null;
		if (newRole.equals(1))
			roleId = RolesUtil.getRoleId(roleName.trim());			
		else
			roleId = Integer.parseInt(request.getParameter("roleId").trim());
		
		List<List> obj = (List<List>) serializer.deserialize(request.getParameter("selectedUserId"), List.class);
		List<List> removedItems = (List<List>) serializer.deserialize(request.getParameter("removedUserId"));
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			compDPService.saveUserDealingPattern((UserDealingPattern)command, companyId, userId, roleName, newRole, roleId, obj, removedItems, model);
		} catch (Exception ex) {
			model.put("status", 0);
		}
		
		if (errors.hasErrors())
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json", model);
	}
}
