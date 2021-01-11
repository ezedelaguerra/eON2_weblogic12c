package com.freshremix.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.ForgotPasswordLinkDao;
import com.freshremix.model.ForgotPasswordLinkObject;

public class ForgotPasswordLinkDaoImpl extends SqlMapClientDaoSupport implements
		ForgotPasswordLinkDao {

	@Override
	public ForgotPasswordLinkObject getForgotPasswordObj(Integer validationId,
			Integer userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", validationId);
		param.put("userId", userId);
		return (ForgotPasswordLinkObject) getSqlMapClientTemplate()
				.queryForObject("ForgotPasswordLink.getGeneratedRecord",
						param);
	}

	@Override
	public Integer insertValidationId(Integer userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return (Integer) getSqlMapClientTemplate()
				.insert("ForgotPasswordLink.insertGeneratedRecord",
						param);
	}

	@Override
	public void updateStatus(Integer validationId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", validationId);
		getSqlMapClientTemplate().update("ForgotPasswordLink.updateStatus", param);
	}

}
