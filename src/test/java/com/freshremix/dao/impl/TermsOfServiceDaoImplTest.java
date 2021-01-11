package com.freshremix.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractDBUnitTest;
import com.freshremix.dao.TermsOfServiceDao;
import com.freshremix.exception.OptimisticLockException;
import com.freshremix.model.TermsOfService;

@Transactional
@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml" })
@AbstractDBUnitTest.FlatXMLDataSet(locations = { //
		//"daoTesting/UsersDataset.xml",
		"daoTesting/TermsOfServiceDataset.xml" 
		
})
public class TermsOfServiceDaoImplTest extends AbstractDBUnitTest {

	@Autowired
	private TermsOfServiceDao dao;

	@Test
	public void getLatestTOSTestWithRecords() {

		TermsOfService tos = dao.getLatestTOS();
		Assert.assertEquals(tos.getContent(), "Just another Content");

	}

	@AfterClass

	public void cleanUP()
	{
		//dao.deleteAll();
	}
	
	protected TermsOfService createNewTermsOfService() {
		TermsOfService tos = new TermsOfService();
		tos.setContent("test content");
		tos.setEmailList("sss@dfd.ccc");
		tos.setCreatedBy("Ken");
		tos.setCreatedDate(new Date());

		return tos;
	}

	protected TermsOfService createTermsOfService() {
		TermsOfService tos = new TermsOfService();
		tos.setTosId("F856E19EEEBD4307AF3E5729689F7633");
		tos.setContent("testng update content");
		tos.setEmailList("melissa@dfd.ccc");
		tos.setModifiedBy("Ken");
		tos.setModifiedDate(new Date());
		tos.setVersion(new Integer(3));

		return tos;
	}

	@NoSetUp
	@Test
	public void getLatestTOSTestEmptyTable() {
		TermsOfService tos = dao.getLatestTOS();
		Assert.assertNull(tos);
	}

	@NoSetUp(excludeLocations={"TermsOfServiceDataset.xml"})
	@Test
	public void saveTermsOfServiceTestHappyFlow() {
		TermsOfService tos = createNewTermsOfService();
		TermsOfService tosReturned = dao.save(tos);
		TermsOfService tosLatest = dao.getLatestTOS();
		Assert.assertNotNull(tosReturned.getTosId());
		Assert.assertEquals(tosReturned.getVersion().intValue(), 1);
		Assert.assertEquals(tosReturned.getTosId(), tosLatest.getTosId());
		Assert.assertEquals(tosReturned.getContent(), tosLatest.getContent());
		Assert.assertEquals(tosReturned.getEmailList(),
				tosLatest.getEmailList());
	}

	@NoSetUp(excludeLocations={"TermsOfServiceDataset.xml"})
	@Test(expectedExceptions = org.springframework.dao.DataIntegrityViolationException.class)
	public void saveTermsOfServiceTestEmptyContent() {
		TermsOfService tos = createNewTermsOfService();
		tos.setContent(null);
		dao.save(tos);

	}

	@NoSetUp(excludeLocations={"TermsOfServiceDataset.xml"})
	@Test(expectedExceptions = org.springframework.dao.DataIntegrityViolationException.class)
	public void saveTermsOfServiceTestEmptyEmail() {
		TermsOfService tos = createNewTermsOfService();
		tos.setEmailList(null);
		dao.save(tos);
	}

	@NoSetUp(excludeLocations={"TermsOfServiceDataset.xml"})
	@Test(expectedExceptions = org.springframework.dao.DataIntegrityViolationException.class)
	public void saveTermsOfServiceTestInvalidCreator() {
		TermsOfService tos = createNewTermsOfService();
		tos.setCreatedBy("Mel");
		dao.save(tos);
	}

	@NoSetUp(excludeLocations={"TermsOfServiceDataset.xml"})
	@Test(expectedExceptions = com.ibatis.common.beans.ProbeException.class)
	public void saveTermsOfServiceTestNullTOS() {
		dao.save(null);
	}

	@NoSetUp(excludeLocations={"TermsOfServiceDataset.xml"})
	@Test(expectedExceptions = org.springframework.dao.DataIntegrityViolationException.class)
	public void saveTermsOfServiceTestEmptyTOS() {

		dao.save(new TermsOfService());
	}

	@Test
	public void updateTermsOfServiceTestHappyFlow()
			throws OptimisticLockException {
		

		TermsOfService tos = createTermsOfService();
		tos = dao.update(tos);
		TermsOfService latest = dao.getLatestTOS();
		Assert.assertEquals(tos.getTosId(), latest.getTosId());
		Assert.assertEquals(tos.getContent(), latest.getContent());
		Assert.assertEquals(tos.getEmailList(), latest.getEmailList());
		Assert.assertEquals(tos.getVersion().intValue(), 4);

	}

	@Test(expectedExceptions = OptimisticLockException.class)
	public void updateTermsOfServiceTestInvalidId()
			throws OptimisticLockException {
		TermsOfService tos = createTermsOfService();
		tos.setTosId("F856E19EEEBD4307AF3E5729689F7653");
		dao.update(tos);

	}

	@Test(expectedExceptions = org.springframework.jdbc.UncategorizedSQLException.class)
	public void updateTermsOfServiceTestNullContent()
			throws OptimisticLockException {
		TermsOfService tos = createTermsOfService();
		tos.setContent(null);
		dao.update(tos);

	}

	@Test(expectedExceptions = org.springframework.jdbc.UncategorizedSQLException.class)
	public void updateTermsOfServiceTestNullEmail()
			throws OptimisticLockException {
		TermsOfService tos = createTermsOfService();
		tos.setEmailList(null);
		dao.update(tos);

	}

	@Test(expectedExceptions = NullPointerException.class)
	public void updateTermsOfServiceTestNullTOS()
			throws OptimisticLockException {

		dao.update(null);

	}

	@Test(expectedExceptions = NullPointerException.class)
	public void updateTermsOfServiceTestNullVersion()
			throws OptimisticLockException {
		TermsOfService tos = createTermsOfService();
		tos.setVersion(null);
		dao.update(tos);

	}
}
