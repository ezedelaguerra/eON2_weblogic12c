package com.freshremix.service.impl;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

import java.util.Date;

import org.joda.time.DateTime;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.dao.TermsOfServiceDao;
import com.freshremix.dao.UsersTOSDao;
import com.freshremix.exception.OptimisticLockException;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.TOSUserContext;
import com.freshremix.model.TermsOfService;
import com.freshremix.model.User;
import com.freshremix.model.UsersTOS;
import com.freshremix.service.LoginService;
import com.freshremix.service.TOSMailSender;

public class TOSUserServiceImplTest {

	private TOSUserServiceImpl tosUserServiceImpl;

	private TermsOfServiceDao tosDaoMock = Mockito
			.mock(TermsOfServiceDao.class);
	private LoginService loginService = Mockito
			.mock(LoginService.class);
	private TOSMailSender  tosMailSenderMock = Mockito.mock(TOSMailSender.class);

	private UsersTOSDao usersTOSDaoMock = Mockito.mock(UsersTOSDao.class);

	@BeforeMethod
	public void setup() {
		tosUserServiceImpl = new TOSUserServiceImpl();
		tosUserServiceImpl.setTosDao(tosDaoMock);
		tosUserServiceImpl.setUsersTOSDao(usersTOSDaoMock);
		tosUserServiceImpl.setTosMailSender(tosMailSenderMock);
		tosUserServiceImpl.setLoginService(loginService);
	}

	@AfterMethod
	public void tearDown() {
		Mockito.reset(tosDaoMock);
		Mockito.reset(usersTOSDaoMock);
	}


	private TermsOfService createBasicTOSData() {
		TermsOfService tos = new TermsOfService();
		tos.setContent("This is the content of the TOS");
		tos.setCreatedBy("dummyUser");
		tos.setEmailList("email1@domain.com; email2@domain.com; ; email3@domain.com");
		return tos;
	}

	@DataProvider(name = "invalidUsers")
	private Object[][] createInvalidUsers() {

		User userWithNullUserId = createUser();
		userWithNullUserId.setUserId(null);

		User userWithNullUserName = createUser();
		userWithNullUserName.setUserName(null);

		User userWithEmptyUserName = createUser();
		userWithEmptyUserName.setUserName("");

		return new Object[][] { { null }, { userWithNullUserId },
				{ userWithNullUserName }, { userWithEmptyUserName } };
	}

	@DataProvider(name = "blankStrings")
	private Object[][] createblankStrings() {

		

		return new Object[][] { { null }, { "" },
				{ " " }};
	}
	
	private User createUser() {
		User user = new User();
		user.setUserId(2);
		user.setUserName("s_ca01");
		return user;
	}

	


	@DataProvider(name = "usersTOSResponse")
	private Object[][] createUsersTOSResponse() {

		UsersTOS usersTOSAgree = createUsersTOS();
		UsersTOS usersTOSDisAgree = createUsersTOS();
		usersTOSDisAgree.setFlag("0");
		
		UsersTOS usersTOSQueryResult = createUsersTOS();
		usersTOSQueryResult.setFlag("0");
		
		return new Object[][] { 
				// User agreed and there is no record
				{ usersTOSAgree, null }, //

				// User agreed and there is an existing record
				{ usersTOSAgree, usersTOSQueryResult }, //

				// User disagreed and there is no record
				{ usersTOSDisAgree, null }, //
				
				// User disagreed and there is an existing record
				{ usersTOSDisAgree, usersTOSQueryResult } //
		};//
	}

	@Test(dataProvider = "usersTOSResponse")
	public void testSaveUsersTOSResponseWithRecord(UsersTOS usersTOS, UsersTOS usersTOSQueryResult)
			throws ServiceException, OptimisticLockException {
		// tests the scenario when there is already a record in UsersTOS and user
		// agrees or disagrees with the TOS

		User user = createUser();

		TermsOfService latestTOSData = createBasicTOSData();

		Mockito.when(usersTOSDaoMock.getEntity(Mockito.anyInt())).thenReturn(
				usersTOSQueryResult);
		Mockito.when(tosDaoMock.getLatestTOS()).thenReturn(latestTOSData);

		tosUserServiceImpl.saveUsersTOSResponse(usersTOS, user);

		Mockito.verify(usersTOSDaoMock).getEntity(Mockito.anyInt());
		if (usersTOSQueryResult == null){
			Mockito.verify(usersTOSDaoMock).save(usersTOS);
		} else {
			Mockito.verify(usersTOSDaoMock).update(usersTOS);
		}

		if (usersTOS.getFlag().equals("0")){
			Mockito.verify(tosDaoMock).getLatestTOS();
			Mockito.verify(tosMailSenderMock).sendEmailNotification(usersTOS, user, latestTOSData);
		} else {
			Mockito.verifyZeroInteractions(tosDaoMock);
			Mockito.verifyZeroInteractions(tosMailSenderMock);
		}
		
	}

	@Test (expectedExceptions = ServiceException.class)
	public void testSaveUsersTOSResponseOptimisticLockingException() throws OptimisticLockException, ServiceException{

		UsersTOS usersTOS = createUsersTOS();
		usersTOS.setFlag("1");
		
		UsersTOS usersTOSQueryResult = createUsersTOS();
		usersTOSQueryResult.setFlag("0");
		
		User user = createUser();

		TermsOfService latestTOSData = createBasicTOSData();

		Mockito.when(usersTOSDaoMock.getEntity(Mockito.anyInt())).thenReturn(
				usersTOSQueryResult);
		Mockito.when(usersTOSDaoMock.update(usersTOS)).thenThrow( new OptimisticLockException("Error"));
		Mockito.when(tosDaoMock.getLatestTOS()).thenReturn(latestTOSData);

		tosUserServiceImpl.saveUsersTOSResponse(usersTOS, user);

		Mockito.verify(usersTOSDaoMock).getEntity(Mockito.anyInt());
		Mockito.verify(usersTOSDaoMock).update(usersTOS);

		Mockito.verifyZeroInteractions(tosDaoMock);
		Mockito.verifyZeroInteractions(tosMailSenderMock);
	}
	
	@DataProvider(name = "invalidUsersTOSResponse")
	private Object[][] createInvalidUsersTOSResponse() {

		UsersTOS nullUseridUsersTOS = createUsersTOS();
		nullUseridUsersTOS.setUserId(null);

		UsersTOS emptyFlagUsersTOS = createUsersTOS();
		emptyFlagUsersTOS.setFlag("");

		UsersTOS spaceFlagUsersTOS = createUsersTOS();
		spaceFlagUsersTOS.setFlag("    ");

		UsersTOS nullFlagUsersTOS = createUsersTOS();
		nullFlagUsersTOS.setFlag(null);

		UsersTOS invalidValueFlagUsersTOS = createUsersTOS();
		invalidValueFlagUsersTOS.setFlag("2");

		UsersTOS nullFlagSetByFlagUsersTOS = createUsersTOS();
		nullFlagSetByFlagUsersTOS.setFlagSetBy(null);

		UsersTOS emptyFlagSetByFlagUsersTOS = createUsersTOS();
		emptyFlagSetByFlagUsersTOS.setFlagSetBy("");

		UsersTOS spaceFlagSetByFlagUsersTOS = createUsersTOS();
		spaceFlagSetByFlagUsersTOS.setFlagSetBy("   ");

		return new Object[][] { { null }, { nullUseridUsersTOS },
				{ emptyFlagUsersTOS }, { spaceFlagUsersTOS },
				{ nullFlagUsersTOS }, { invalidValueFlagUsersTOS },
				{ nullFlagSetByFlagUsersTOS }, { emptyFlagSetByFlagUsersTOS },
				{ spaceFlagSetByFlagUsersTOS }

		};
	}

	@Test(dataProvider = "invalidUsersTOSResponse", expectedExceptions = IllegalArgumentException.class)
	public void testSaveUsersTOSResponseInvalidUsersTOS(UsersTOS usersTOS)
			throws ServiceException {

		User user = createUser();

		tosUserServiceImpl.saveUsersTOSResponse(usersTOS, user);

		Mockito.verifyZeroInteractions(usersTOSDaoMock);
	}

	@DataProvider(name = "hasUsersAgreedTestValues")
	private Object[][] createHasUsersAgreedValues() {

		UsersTOS usersTOSAgree = createUsersTOS();
		UsersTOS usersTOSDisAgree = createUsersTOS();
		usersTOSDisAgree.setFlag("0");

		return new Object[][] { { null, Boolean.FALSE },
				{ usersTOSAgree, Boolean.TRUE },
				{ usersTOSDisAgree, Boolean.FALSE }, };
	}


	private UsersTOS createUsersTOS() {
		UsersTOS entity = new UsersTOS();
		entity.setFlag("1");
		entity.setFlagDate(new Date());
		entity.setFlagSetBy("s_ca01");
		entity.setUserId(2);
		entity.setVersion(1);
		return entity;
	}
	
	@Test
	public void testGetUserContextUserHasAgreed() throws ServiceException{
		
		User user = createUser();
		UsersTOS usersTOS = createUsersTOS();

		Mockito.when(usersTOSDaoMock.getEntity(Mockito.anyInt())).thenReturn(usersTOS);
		
		TOSUserContext userTOSContext = tosUserServiceImpl.getUserTOSContext(user);
		
		assertEquals(true, userTOSContext.getUserHasAgreed());
		assertEquals(false, userTOSContext.getAllowedUsageExpired());
		assertEquals(false, userTOSContext.getDisplayTOS());
		assertNull(userTOSContext.getTermsOfService());
		
		Mockito.verify(usersTOSDaoMock).getEntity(Mockito.anyInt());
		Mockito.verifyZeroInteractions(tosDaoMock);
	}

	@Test
	public void testGetUserContextUserHasNOTAgreedNOTExpired() throws ServiceException{
		
		User user = createUser();
		UsersTOS usersTOS = createUsersTOS();
		usersTOS.setFlag("0");
		usersTOS.setFlagDate(new Date());
		
		Mockito.when(usersTOSDaoMock.getEntity(Mockito.anyInt())).thenReturn(usersTOS);
		
		TOSUserContext userTOSContext = tosUserServiceImpl.getUserTOSContext(user);
		
		assertEquals(false, userTOSContext.getUserHasAgreed());
		assertEquals(false, userTOSContext.getAllowedUsageExpired());
		assertEquals(false, userTOSContext.getDisplayTOS());
		assertNull(userTOSContext.getTermsOfService());
		
		Mockito.verify(usersTOSDaoMock).getEntity(Mockito.anyInt());
		Mockito.verifyZeroInteractions(tosDaoMock);
		
	}

	@Test
	public void testGetUserContextUserHasNOTAgreedALREADYExpired() throws ServiceException{
		
		User user = createUser();
		UsersTOS usersTOS = createUsersTOS();
		usersTOS.setFlag("0");
		DateTime flagDate = new DateTime();
		flagDate = flagDate.minusDays(31);
		usersTOS.setFlagDate(flagDate.toDate());
		
		Mockito.when(usersTOSDaoMock.getEntity(Mockito.anyInt())).thenReturn(usersTOS);
		
		TOSUserContext userTOSContext = tosUserServiceImpl.getUserTOSContext(user);
		
		assertEquals(false, userTOSContext.getUserHasAgreed());
		assertEquals(true, userTOSContext.getAllowedUsageExpired());
		assertEquals(false, userTOSContext.getDisplayTOS());
		assertNull(userTOSContext.getTermsOfService());

		Mockito.verify(usersTOSDaoMock).getEntity(Mockito.anyInt());
		Mockito.verifyZeroInteractions(tosDaoMock);
		
	}
	
	@Test
	public void testGetUserContextUserFirstTimeTOSDisplay() throws ServiceException{
		
		User user = createUser();
		UsersTOS usersTOS = null;
		TermsOfService latestTOS = createBasicTOSData();
		
		Mockito.when(usersTOSDaoMock.getEntity(Mockito.anyInt())).thenReturn(usersTOS);
		Mockito.when(tosDaoMock.getLatestTOS()).thenReturn(latestTOS);
		
		TOSUserContext userTOSContext = tosUserServiceImpl.getUserTOSContext(user);
		
		assertEquals(false, userTOSContext.getUserHasAgreed());
		assertEquals(false, userTOSContext.getAllowedUsageExpired());
		assertEquals(true, userTOSContext.getDisplayTOS());
		assertNotNull(userTOSContext.getTermsOfService());
		
		Mockito.verify(usersTOSDaoMock).getEntity(Mockito.anyInt());
		Mockito.verify(tosDaoMock).getLatestTOS();
	}
	
	@Test(dataProvider = "invalidUsers")
	public void testGetUserContextValidateErrors(User user)
			throws ServiceException {
		TOSUserContext tosUserContext = null;
		try {
			tosUserContext = tosUserServiceImpl.getUserTOSContext(user);
			Assert.fail("Expected an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
		assertNull(tosUserContext);
		Mockito.verifyZeroInteractions(usersTOSDaoMock);
		Mockito.verifyZeroInteractions(tosDaoMock);
	}
	
	@Test
	public void testAdminSaveUsersTOSAgree() throws ServiceException{
		UsersTOS usersTOS = createUsersTOS();
		usersTOS.setFlagSetBy("Ken");

		Mockito.when(usersTOSDaoMock.getEntity(Mockito.anyInt())).thenReturn(null);
		
		tosUserServiceImpl.adminSaveUsersTOS(usersTOS);
		Mockito.verify(usersTOSDaoMock).getEntity(Mockito.anyInt());
		Mockito.verify(usersTOSDaoMock).save(usersTOS);
	}

	@Test
	public void testAdminSaveUsersTOSDisAgree() throws ServiceException{
		UsersTOS usersTOS = createUsersTOS();
		usersTOS.setFlag("0");
		usersTOS.setFlagSetBy("Ken");

		Mockito.when(usersTOSDaoMock.getEntity(Mockito.anyInt())).thenReturn(null);
		
		tosUserServiceImpl.adminSaveUsersTOS(usersTOS);
		Mockito.verify(usersTOSDaoMock).getEntity(Mockito.anyInt());
		Mockito.verifyNoMoreInteractions(usersTOSDaoMock);
	}
	
	@Test
	public void testResetUsersTOSHappyFlow() throws ServiceException{
		tosUserServiceImpl.resetTOSAcceptance("Ken", "1");
		Mockito.verify(usersTOSDaoMock).resetAll(Mockito.anyString(),
				Mockito.any(Date.class));
		Mockito.verifyNoMoreInteractions(usersTOSDaoMock);
	}
	
	
	
	
}
