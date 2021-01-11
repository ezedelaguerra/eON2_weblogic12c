package com.freshremix.service.impl;
import java.util.List;

import com.freshremix.dao.RolesDao;
import com.freshremix.model.Role;
import com.freshremix.service.RolesService;

public class RolesServiceImpl implements RolesService {
	private RolesDao rolesDao;

	@Override
	public List<Role> getAllRoles(String companyFlagType) {
		return rolesDao.getAllRoles(companyFlagType);
	}

	public void setRolesDao(RolesDao rolesDao) {
		this.rolesDao = rolesDao;
	}

	@Override
	public List<Role> getRolesById(String roleId) {
		return rolesDao.getRolesById(roleId);
	}

}
