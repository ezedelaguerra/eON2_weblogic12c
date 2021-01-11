package com.freshremix.dao;

import java.util.List;

import com.freshremix.model.Role;

public interface RolesDao {
	List<Role> getAllRoles(String companyFlagType);
	List<Role> getRolesById(String roleId);
}
