package com.freshremix.service.impl;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.dao.TermsOfServiceDao;
import com.freshremix.exception.OptimisticLockException;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.TermsOfService;
import com.freshremix.model.TermsOfService.TOSState;

//Uncomment if you want to test integration with SpringContext
//@ContextConfiguration (locations={"classpath:applicationContextTest.xml"})
//public class TOSServiceImplTest extends AbstractTestNGSpringContextTests{
public class TOSAdminServiceImplTest {

	private TOSAdminServiceImpl tosAdminServiceImpl;

	private TermsOfServiceDao tosDaoMock = Mockito.mock(TermsOfServiceDao.class);

	private final SimpleDateFormat SDF = new SimpleDateFormat("yyy/MM/dd");

	private static final String AUDIT_FIELDS_NOT_PROPERLY_SET = "Audit fields not properly set.";
	private static final String RECORD_ID_FOR_UPDATE_IS_NULL_OR_EMPTY = "Record ID for update is null or empty";
	private static final String TERMS_OF_SERVICE_DATA_NOT_FOUND_OR_EMPTY = "Terms of Service data not found or empty";
	
	@BeforeMethod
	public void setup() {
		tosAdminServiceImpl = new TOSAdminServiceImpl();
		tosAdminServiceImpl.setTosDao(tosDaoMock);
	}

	@AfterMethod
	public void tearDown() {
		Mockito.reset(tosDaoMock);
	}

	@Test
	public void getLatestTOSNewState() throws ServiceException{
		TermsOfService tos = createBasicTOSData();
		Mockito.when(tosDaoMock.getLatestTOS()).thenReturn(tos);
		
		TermsOfService latestTOS = tosAdminServiceImpl.getLatestTOS();
		TOSState tosState = tosAdminServiceImpl.getTOSState(latestTOS);
		assertEquals(TermsOfService.TOSState.NEW, tosState);
		Mockito.verify(tosDaoMock).getLatestTOS();
	}

	@Test
	public void getLatestTOSOldState() throws ServiceException{
		TermsOfService tos = createBasicTOSData();
		Date createdDate = (new DateTime()).minusDays(31).toDate();
		tos.setCreatedDate(createdDate );
		Mockito.when(tosDaoMock.getLatestTOS()).thenReturn(tos);
		
		TermsOfService latestTOS = tosAdminServiceImpl.getLatestTOS();
		TOSState tosState = tosAdminServiceImpl.getTOSState(latestTOS);
		assertEquals(TermsOfService.TOSState.OLD, tosState);
		Mockito.verify(tosDaoMock).getLatestTOS();
	}
	
	@Test
	public void testSaveTOSHappyFlow() throws ServiceException {
		TermsOfService tos = createBasicTOSData();
		Mockito.when(tosDaoMock.save(tos)).thenReturn(tos);
		tos = tosAdminServiceImpl.createTermsOfService(tos);

		assertNotNull(tos.getCreatedDate());
		assertNull(tos.getModifiedBy());
		assertNull(tos.getModifiedDate());

		Mockito.verify(tosDaoMock).save(tos);

	}

	@Test
	public void testUpdateTOSHappyFlow() throws ServiceException,
			OptimisticLockException {
		TermsOfService tos = createBasicTOSData();
		String ID = "ID";
		tos.setTosId(ID);
		tos.setCreatedDate(new Date());
		tos.setModifiedBy("dummyUser");
		tos.setVersion(1);

		tos = tosAdminServiceImpl.updateTermsOfService(tos);

		assertEquals(ID, tos.getTosId());
		assertNotNull(tos.getCreatedDate());
		assertNotNull(tos.getModifiedBy());
		assertNotNull(tos.getModifiedDate());

		Mockito.verify(tosDaoMock).update(tos);

	}

	@Test
	public void testSaveTermsOfServiceNullInput() throws ServiceException {
		try {
			tosAdminServiceImpl.createTermsOfService(null);
		} catch (IllegalArgumentException e) {
			assertEquals(TERMS_OF_SERVICE_DATA_NOT_FOUND_OR_EMPTY, e.getMessage());
		}
		Mockito.verifyZeroInteractions(tosDaoMock);

	}

	@Test
	public void testUpdateTermsOfServiceNullInput() throws ServiceException {
		try {
			tosAdminServiceImpl.updateTermsOfService(null);
		} catch (IllegalArgumentException e) {
			assertEquals(TERMS_OF_SERVICE_DATA_NOT_FOUND_OR_EMPTY, e.getMessage());
		}
		Mockito.verifyZeroInteractions(tosDaoMock);

	}

	@DataProvider(name = "emptyEmailLListData")
	private Object[][] createEmptyEmailListTOS() {
		return new Object[][] { 
				{null}, 
				{ "" }, 
				{ ";" }, 
				{ " " }
		};
	}

	@Test(dataProvider = "emptyEmailLListData")
	public void testSaveTermsOfServiceEmptyEmailList(String emailList) {
		try {
			TermsOfService tos = createBasicTOSData();
			tos.setEmailList(emailList);
			tosAdminServiceImpl.createTermsOfService(tos);
		} catch (ServiceException e) {
			assertEquals("tos.message.error.emailListEmpty", e.getErr()
					.getErrorCode());
		}
		Mockito.verifyZeroInteractions(tosDaoMock);

	}

	@DataProvider(name = "invalidEmailFormatListData")
	private Object[][] createInvalidEmailFormatList() {
		return new Object[][] { 
				{ "testNoATSign.com" },
				{ "@nothignBeforeTheAtSign" }, 
				{ "@" } 
		};
	}

	@Test(dataProvider = "invalidEmailFormatListData")
	public void testSaveTermsOfServiceInValidEmailFormat(String emailList) {
		try {
			TermsOfService tos = createBasicTOSData();
			tos.setEmailList(emailList);
			tosAdminServiceImpl.createTermsOfService(tos);
		} catch (ServiceException e) {
			assertEquals("tos.message.error.emailListInvalid", e.getErr()
					.getErrorCode());
		}

		Mockito.verifyZeroInteractions(tosDaoMock);

	}

	@Test
	public void testSaveTermsOfServiceEmailExceedChar() {
		try {
			TermsOfService tos = createBasicTOSData();
			//more than 300 characters
			String emailList = "email.test1@test.com;email.test1@test.com;email.test1@test.com;" +
					"email.test1@test.com;email.test1@test.com;email.test1@test.com;email.test1@test.com;" +
					"email.test1@test.com;email.test1@test.com;email.test1@test.com;email.test1@test.com;" +
					"email.test1@test.com;email.test1@test.com;email.test1@test.com;email.test1@test.com;";
			tos.setEmailList(emailList);
			tosAdminServiceImpl.createTermsOfService(tos);
		} catch (ServiceException e) {
			assertEquals("tos.message.error.exceedMaxEmailChar", e.getErr()
					.getErrorCode());
		}

		Mockito.verifyZeroInteractions(tosDaoMock);

	}
	
	@DataProvider(name = "invalidTOSContentListData")
	private Object[][] createInvalidTOSContentList() {
		return new Object[][] { 
				{ "" }, 
				{ "   " }, 
				{ "\n" }, 
				{ "\t" }, 
				{ null } 
		};
	}

	@Test(dataProvider = "invalidTOSContentListData")
	public void testSaveTermsOfServiceEmptyTOSContent(String tosContent) {
		try {
			TermsOfService tos = new TermsOfService();
			tos.setEmailList("validEmail@Format.com");
			tos.setContent(tosContent);
			tosAdminServiceImpl.createTermsOfService(tos);
		} catch (ServiceException e) {
			assertEquals("tos.message.error.tosContentNotFound", e.getErr()
					.getErrorCode());
		}

		Mockito.verifyZeroInteractions(tosDaoMock);

	}

	@DataProvider(name = "invalidAuditFieldSaveTOSListData")
	private Object[][] createInvalidAuditFieldSaveTOSList() {
		return new Object[][] { 
				{ "dummyUser", null, "dummyUser", null },
				{ "dummyUser", null, "dummyUser", "2012/12/12" },
				{ null, null, null, null } 
		};
	}

	@Test(dataProvider = "invalidAuditFieldSaveTOSListData")
	public void testSaveTermsOfServiceInvalidAuditFields(String createdBy,
			String createdDate, String modifiedBy, String modifiedDate)
			throws ParseException, ServiceException {
		TermsOfService tos = createBasicTOSData();
		tos.setCreatedBy(createdBy);
		tos.setCreatedDate(StringUtils.isBlank(createdDate) ? null : SDF
				.parse(createdDate));
		tos.setModifiedBy(modifiedBy);
		tos.setModifiedDate(StringUtils.isBlank(modifiedDate) ? null : SDF
				.parse(modifiedDate));

		try {
			tosAdminServiceImpl.createTermsOfService(tos);
		} catch (IllegalArgumentException e) {
			assertEquals(AUDIT_FIELDS_NOT_PROPERLY_SET, e.getMessage());
		}
		Mockito.verifyZeroInteractions(tosDaoMock);
	}

	@DataProvider(name = "invalidAuditFieldUpdateTOSListData")
	private Object[][] createInvalidAuditFieldUpdateTOSList() {
		return new Object[][] { 
				{ null, null, "dummyUser", null, 1 },
				{ "dummyUser", null, null, null, 1 },
				{ "dummyUser", "2012/12/12", "dummyUser", "2012/12/12", null } 
		};
	}

	@Test(dataProvider = "invalidAuditFieldUpdateTOSListData")
	public void testUpdateTermsOfServiceInvalidAuditFields(String createdBy,
			String createdDate, String modifiedBy, String modifiedDate,
			Integer version) throws ParseException, ServiceException {

		TermsOfService tos = createBasicTOSData();
		tos.setTosId("ID");
		tos.setCreatedBy(createdBy);
		tos.setCreatedDate(StringUtils.isBlank(createdDate) ? null : SDF
				.parse(createdDate));
		tos.setModifiedBy(modifiedBy);
		tos.setModifiedDate(StringUtils.isBlank(modifiedDate) ? null : SDF
				.parse(modifiedDate));
		tos.setVersion(version);
		try {
			tosAdminServiceImpl.updateTermsOfService(tos);
		} catch (IllegalArgumentException e) {
			assertEquals(AUDIT_FIELDS_NOT_PROPERLY_SET, e.getMessage());
		}
		Mockito.verifyZeroInteractions(tosDaoMock);
	}

	private TermsOfService createBasicTOSData() {
		TermsOfService tos = new TermsOfService();
		tos.setContent("This is the content of the TOS");
		tos.setCreatedBy("dummyUser");
		tos.setEmailList("email1@domain.com; email2@domain.com; ; email3@domain.com");
		tos.setCreatedDate(new Date());
		return tos;
	}

	@DataProvider(name = "invalidIDUpdateTOSListData")
	private Object[][] createInvalidIDUpdateTOSList() {
		return new Object[][] { 
				{ null }, 
				{ "" }, 
				{ "  " }, 
				{ "\t" }, 
				{ "\n" }
				};
	}

	@Test(dataProvider = "invalidIDUpdateTOSListData", enabled = true)
	public void testUpdateTermsOfServiceIDField(String id)
			throws ParseException, ServiceException {

		TermsOfService tos = createBasicTOSData();
		tos.setTosId(id);

		try {
			tosAdminServiceImpl.updateTermsOfService(tos);
		} catch (IllegalArgumentException e) {
			assertEquals(RECORD_ID_FOR_UPDATE_IS_NULL_OR_EMPTY, e.getMessage());
		}
		Mockito.verifyZeroInteractions(tosDaoMock);
	}

	@Test(enabled = true)
	public void testUpdateTOSThrowOptimisticLockingException()
			throws OptimisticLockException {
		TermsOfService tos = createBasicTOSData();
		String ID = "ID";
		tos.setTosId(ID);
		tos.setCreatedDate(new Date());
		tos.setModifiedBy("dummyUser");
		tos.setVersion(1);

		Mockito.doThrow(new OptimisticLockException("Error")).when(tosDaoMock)
				.update(tos);
		try {
			tos = tosAdminServiceImpl.updateTermsOfService(tos);
		} catch (ServiceException e) {
			assertEquals("tos.message.error.optimisticLockFailed", e.getErr()
					.getErrorCode());
		}
		Mockito.verify(tosDaoMock).update(tos);
	}



}
