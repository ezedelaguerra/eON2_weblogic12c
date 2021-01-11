package com.freshremix.dao.setup;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.freshremix.dao.CategoryDao;
import com.freshremix.dao.ProductMasterFileDao;
import com.freshremix.dao.TermsOfServiceDao;
import com.freshremix.dao.UserDao;
import com.freshremix.dao.UsersTOSDao;
import com.freshremix.exception.OptimisticLockException;
import com.freshremix.model.TermsOfService;
import com.freshremix.model.User;
import com.freshremix.model.UsersTOS;

@Transactional(propagation=Propagation.REQUIRES_NEW)
@TransactionConfiguration(defaultRollback=false)
public class TOSWebDBSetupImpl implements TOSWebDBSetup{

	private static final Logger LOGGER = Logger.getLogger(TOSWebDBSetup.class);
	
	@Resource(name = "usersInfoDaos")
	private UserDao userDao;	
	
	@Resource(name = "tosDaos" )
	private TermsOfServiceDao tosDao;
	
	@Resource(name = "usersTOSDao")
	private UsersTOSDao usersTOSDao;

	@Resource(name="categoryDaos")
	private CategoryDao categoryDao;
	
	@Resource(name="pmfDao")
	private ProductMasterFileDao pmfDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public UsersTOSDao getUsersTOSDao() {
		return usersTOSDao;
	}

	public TermsOfServiceDao getTosDao() {
		return tosDao;
	}

	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void prepareTOSLabelData(int days)
	{
		LOGGER.info("prepare data");
		Date thirtydaysbefore = addDays(days);
		TermsOfService tos = tosDao.getLatestTOS();
		tosDao.deleteAll();
		tos.setCreatedDate(thirtydaysbefore);
		tosDao.save(tos);
	}
	
	private Date addDays(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void prepareUserExpiration(String username, int days) throws OptimisticLockException
	{
		Date thirtydaysbefore = addDays(days);
		

		User user = userDao.getUserByUsername(username);
		UsersTOS usersTOSRecord = usersTOSDao.getEntity(user.getUserId());
		usersTOSRecord.setFlagDate(thirtydaysbefore);
		usersTOSDao.saveOrUpdateEntity(usersTOSRecord);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void prepareTOSData(){
		tearDownAllTOSData();
		TermsOfService entity = new TermsOfService();
		entity.setContent("TOS Content");
		entity.setCreatedBy("Ken");
		entity.setCreatedDate(new Date());
		entity.setEmailList("dummy@email.com");
		tosDao.save(entity);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void tearDownAllTOSData(){
		tosDao.deleteAll();
		usersTOSDao.deleteAll();
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public UsersTOS getUsersTOS(String username){
		User user = userDao.getUserByUsername(username);
		return usersTOSDao.getEntity(user.getUserId());
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteUser(String username){
		
		User user = userDao.getUserByUsername(username);
		if (user != null){
			Integer userId = user.getUserId();
			categoryDao.deleteUserCategory(userId.toString());
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", userId);
			pmfDao.deletePmfbyUserId(param);
			usersTOSDao.delete(userId);
			userDao.deleteUserById(userId.toString());
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void prepareResetTOSData(String username, String flag)
	{
		
		UsersTOS tos = new UsersTOS();
		tos.setUserId(userDao.getUserByUsername(username).getUserId());
		tos.setFlag(flag);
		tos.setVersion(1);
		tos.setFlagDate(new Date());
		tos.setFlagSetBy("Ken");
		usersTOSDao.save(tos);
	}

}
