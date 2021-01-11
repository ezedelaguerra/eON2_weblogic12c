package com.freshremix.dao.setup;


import com.freshremix.dao.TermsOfServiceDao;
import com.freshremix.dao.UserDao;
import com.freshremix.dao.UsersTOSDao;
import com.freshremix.exception.OptimisticLockException;
import com.freshremix.model.UsersTOS;



public interface TOSWebDBSetup{

	public TermsOfServiceDao getTosDao();
	public UserDao getUserDao();
	public UsersTOSDao getUsersTOSDao();
	public void prepareTOSLabelData(int days);
	public void prepareUserExpiration(String username, int days) throws OptimisticLockException;
	public void prepareTOSData();
	public void tearDownAllTOSData();
	public UsersTOS getUsersTOS(String username);
	public void deleteUser(String username);
	public void prepareResetTOSData(String username, String flag);

}
