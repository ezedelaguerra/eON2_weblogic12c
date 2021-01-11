package com.freshremix.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.freshremix.dao.AbstractBaseDao;
import com.freshremix.dao.UsersTOSDao;
import com.freshremix.model.UsersTOS;

public class UsersTOSDaoImpl extends AbstractBaseDao<UsersTOS, Integer>  implements UsersTOSDao{

	public UsersTOSDaoImpl(){
		super(UsersTOS.class);
	}

	@Override
	public void resetAll(String flagBy, Date flagDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flagSetBy", flagBy);
		map.put("flagDate", flagDate);
		getSqlMapClientTemplate().update(
				formStatementName("resetAll"),map);
		
	}

	@Override
	public int countAcceptanceFlag(String flag) {
		int i = (Integer) getSqlMapClientTemplate().queryForObject(formStatementName("countAcceptance"),flag);
		return i;
	}
	
	

}
