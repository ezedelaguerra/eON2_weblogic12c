package com.freshremix.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.RolesDao;
import com.freshremix.model.Role;

public class RolesDaoImpl extends SqlMapClientDaoSupport implements RolesDao  {

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRoles(String companyFlagType) {
		return getSqlMapClientTemplate().queryForList("Role.getAllRoles", companyFlagType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRolesById(String roleId) {
		return getSqlMapClientTemplate().queryForList("Role.getRolesById", roleId);
	}
}
