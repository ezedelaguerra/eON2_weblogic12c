package com.freshremix.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.LoginDao;
import com.freshremix.model.LoginInquiry;
import com.freshremix.model.User;

public class LoginDaoImpl extends SqlMapClientDaoSupport 
	implements LoginDao {

	@Override
	public User getUserByUsernameAndPassword(Map<String, String> param) {
		return (User)getSqlMapClientTemplate().queryForObject("Login.getUserByUsernameAndPassword", param);
	}

	@Override
	public Integer validateUsernameAndPassword(Map<String, String> param) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Login.validateUsernameAndPassword", param);
	}

	@Override
	public User getUserByUsernameAndEmail(Map<String, String> param) {
		return (User)getSqlMapClientTemplate().queryForObject("Login.getUserByUsernameAndEmail", param);
	}

	@Override
	public Integer validateUsernameAndEmail(Map<String, String> param) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Login.validateUsernameAndEmail", param);
	}

	@Override
	public void insertNonMemberInquiry(LoginInquiry nonMemberInquiry) {
		getSqlMapClientTemplate().update("Login.insertNonMemberInquiry", nonMemberInquiry);
		
	}

	@Override
	public User getUserByUsername(Map<String, String> param) {
		return (User)getSqlMapClientTemplate().queryForObject("Login.getUserByUsername", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.LoginDao#updateUserLastLoginDate(com.freshremix.model.User)
	 */
	@Override
	public void updateUserLastLoginDate(Map<String, String> param) {
		getSqlMapClientTemplate().update("Login.updateUserLastLoginDate", param);
		
	}

	@Override
	public User getUserByUserId(Integer userId) {
		return (User)getSqlMapClientTemplate().queryForObject("Login.getUserByUserId", userId);
	}
	
	@Override
	public String getCityByZip(Integer zipCd) {
		return (String)getSqlMapClientTemplate().queryForObject("Login.getCityByZip", zipCd);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.LoginDao#getKenEmailAdd()
	 */
	@Override
	public String getKenEmailAdd() {
		return (String)getSqlMapClientTemplate().queryForObject("Login.getKenEmailAdd");
	}

}
