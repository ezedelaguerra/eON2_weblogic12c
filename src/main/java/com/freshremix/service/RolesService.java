package com.freshremix.service;

import java.util.List;

import com.freshremix.model.Role;

public interface RolesService {
	List<Role> getAllRoles(String companyFlagType);
	List<Role> getRolesById(String roleId);
}
