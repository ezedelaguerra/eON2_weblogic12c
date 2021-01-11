package com.freshremix.dao.impl;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractDBUnitTest;
import com.freshremix.dao.UsersTOSDao;
import com.freshremix.exception.OptimisticLockException;
import com.freshremix.model.UsersTOS;

@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml" })
@AbstractDBUnitTest.FlatXMLDataSet(locations = { //
		//"daoTesting/UsersDataset.xml",
		"daoTesting/UsersTOSDataset.xml"
		})
public class UsersTOSDaoImplTest extends AbstractDBUnitTest {

	@Resource(name = "usersTOSDao")
	private UsersTOSDao usersTOSDao;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	

	@Test
	public void saveHappyFlow() {
		UsersTOS entity = createUsersTOS();

		// assert that the record is not found prior to insert
		UsersTOS beforeInsertResult = usersTOSDao.getEntity(entity
				.getPrimaryKey());
		assertNull(beforeInsertResult);

		UsersTOS saveResult = usersTOSDao.save(entity);
		UsersTOS afterInsertResult = usersTOSDao.getEntity(saveResult
				.getPrimaryKey());

		// assert that the record is found after insert and that it is equal to
		// the returned save result.
		assertEqualObject(saveResult, afterInsertResult);

		// check that the field defaults is properly set
		assertEquals(Integer.valueOf(1), saveResult.getVersion());
		assertEquals("0", afterInsertResult.getFlag());
	}
	
	@Test
	public void updateHappyFlow() throws OptimisticLockException {
		Integer userId = 2;
		UsersTOS queryResult = usersTOSDao.getEntity(userId); 
		assertNotNull(queryResult);

		Date flagDate = new Date();
		String flag = "1";
		String flagSetBy = "s_ca01";

		queryResult.setFlag(flag);
		queryResult.setFlagDate(flagDate);
		queryResult.setFlagSetBy(flagSetBy);
		
		UsersTOS resultAfterUpdate = usersTOSDao.update(queryResult);
		
		assertNotNull(resultAfterUpdate);
		assertEquals(flag, resultAfterUpdate.getFlag());
		assertEquals(0, flagDate.compareTo(resultAfterUpdate.getFlagDate()));
		assertEquals(flagSetBy, resultAfterUpdate.getFlagSetBy());
		assertEquals(userId, resultAfterUpdate.getUserId());
		assertEquals(Integer.valueOf(queryResult.getVersion() + 1),
				resultAfterUpdate.getVersion());		
		
		UsersTOS queryResultAfterUpdate = usersTOSDao.getEntity(userId); 
		assertEqualObject(resultAfterUpdate, queryResultAfterUpdate);
		
	}
	

	@Test(expectedExceptions = OptimisticLockException.class)
	public void updateOptimisticLockingException() throws OptimisticLockException {
		Integer userId = 2;
		UsersTOS queryResult = usersTOSDao.getEntity(userId); 
		assertNotNull(queryResult);

		Date flagDate = new Date();
		String flag = "1";
		String flagSetBy = "s_ca01";

		queryResult.setFlag(flag);
		queryResult.setFlagDate(flagDate);
		queryResult.setFlagSetBy(flagSetBy);
		
		UsersTOS resultAfterUpdate = usersTOSDao.update(queryResult);

		assertNotNull(resultAfterUpdate);
		assertEquals(flag, resultAfterUpdate.getFlag());
		assertEquals(0, flagDate.compareTo(resultAfterUpdate.getFlagDate()));
		assertEquals(flagSetBy, resultAfterUpdate.getFlagSetBy());
		assertEquals(userId, resultAfterUpdate.getUserId());
		assertEquals(Integer.valueOf(queryResult.getVersion() + 1),
				resultAfterUpdate.getVersion());		
		
		//should throw an exception
		usersTOSDao.update(queryResult);
		
	}

	
	@Test
	public void getEntityHappyFlow() throws ParseException {
		UsersTOS queryResult = usersTOSDao.getEntity(2); 
		
		assertNotNull(queryResult);
		assertEquals(Integer.valueOf(2), queryResult.getUserId());
		assertEquals(Integer.valueOf(1), queryResult.getVersion());
		assertEquals("0", queryResult.getFlag());
		assertEquals("Ken", queryResult.getFlagSetBy());
		assertEquals(sdf.parse("2013-01-02"), queryResult.getFlagDate());
	}
	
	@Test
	public void getEntityNonExistingRecord() {
		UsersTOS queryResult = usersTOSDao.getEntity(1); 
		assertNull(queryResult);
	}
	
	

	private void assertEqualObject(UsersTOS expected, UsersTOS actual) {
		assertEquals(expected.getFlag(), actual.getFlag());
		assertEquals(expected.getFlagDate(), actual.getFlagDate());
		assertEquals(expected.getFlagSetBy(), actual.getFlagSetBy());
		assertEquals(expected.getUserId(), actual.getUserId());
		assertEquals(expected.getVersion(), actual.getVersion());
	}

	private UsersTOS createUsersTOS() {
		UsersTOS entity = new UsersTOS();
//		entity.setFlag(null);
		entity.setFlagDate(new Date());
		entity.setFlagSetBy("Ken");
		entity.setUserId(3);
//		entity.setVersion(null);
		return entity;
	}

	@Test
	public void testDelete(){
		Integer pk = 2;
		UsersTOS entity = usersTOSDao.getEntity(pk);
		assertEquals(entity.getPrimaryKey(), pk);
		
		usersTOSDao.delete(pk);

		entity = usersTOSDao.getEntity(pk);
		assertNull(entity);
	}

	@Test
	public void testDeleteNonExistingRecord(){
		//should be ok to delete non existing records
		Integer pk = 3;
		UsersTOS entity = usersTOSDao.getEntity(pk);
		assertNull(entity);
		
		usersTOSDao.delete(pk);

		entity = usersTOSDao.getEntity(pk);
		assertNull(entity);
	}
	
	@Test
	public void resetTermsOfServiceAcceptance()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm");
		UsersTOS user = usersTOSDao.getEntity(4);
		Assert.assertEquals(user.getFlag(), "1");
		
		Date flagDate = new Date();
		
		usersTOSDao.resetAll("Ken", flagDate);
		user = usersTOSDao.getEntity(4);
		Assert.assertEquals(user.getFlag(), "0");
		Assert.assertEquals(user.getFlagSetBy(), "Ken");
		Assert.assertEquals(sdf.format(user.getFlagDate()), sdf.format(flagDate));
	}
	
	
	@Test
	public void countTermsOfServiceAcceptance()
	{
		
		int count = usersTOSDao.countAcceptanceFlag("1"); 
		Assert.assertEquals(count, 1);
		

	}
}
