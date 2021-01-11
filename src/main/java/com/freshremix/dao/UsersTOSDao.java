package com.freshremix.dao;


import java.util.Date;

import com.freshremix.model.UsersTOS;

public interface UsersTOSDao extends BaseDao<UsersTOS, Integer>{


	public int countAcceptanceFlag(String flag);

	public void resetAll(String username, Date flagDate);

}
