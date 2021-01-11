package com.freshremix.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractDBUnitTest;
import com.freshremix.common.test.CSVTestUtil;
import com.freshremix.dao.DelegateDataRowHandler;
import com.freshremix.dao.UserDao;

@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml" })
@AbstractDBUnitTest.FlatXMLDataSet(locations = { //
		//"daoTesting/UsersDataset.xml", //
		"daoTesting/UsersTOSDataset.xml" //
		})
public class UserDaoImplTest extends AbstractDBUnitTest {

	@Resource(name = "usersInfoDaos")
	private UserDao usersDao;

	private class CountingRowDataHandler implements DelegateDataRowHandler<Map<String, String>>{
		private List<Map<String, String>> preservedRows = new ArrayList<Map<String, String>>();
		
		@Override
		public void handleRow(Map<String, String> row) throws Exception {
			preservedRows.add(row);
		}
		
		public int getRowCount(){
			return preservedRows.size();
		}
		
	}
	//private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private DelegatingRowHandler<Map<String, String>> delegatingRowHandler;
	private CountingRowDataHandler countingDelegate;
	
	@BeforeMethod
	public void setUpBeforeMethod(){
		
		delegatingRowHandler = new DelegatingRowHandler<Map<String, String>>();
		countingDelegate = new CountingRowDataHandler();
		delegatingRowHandler.addDelegate(countingDelegate);
	}

	@Test
	public void testGetAllNonAdminUsers() throws Exception{

		CSVUserRowDataHandler delegate = new CSVUserRowDataHandler();
		
		//init
		File file = delegate.initializeFile(null);
		Assert.assertTrue(file.exists());
		Assert.assertEquals(countingDelegate.getRowCount(), 0);

		delegatingRowHandler.addDelegate(delegate);
		
		usersDao.getAllNonAdminUsers(delegatingRowHandler);
		//check the file after
		long fileSizeAfter = FileUtils.sizeOf(file);
		Assert.assertTrue(fileSizeAfter > 0);
		Assert.assertTrue(countingDelegate.getRowCount() >0);
		Assert.assertEquals(CSVTestUtil.countLines(file), countingDelegate.getRowCount()+1);

		//cleanup
		CSVTestUtil.deleteFile(file);
	}

		@Test
		public void getNonCompanyMembersFromListTest() throws Exception{
			List<Integer> list = usersDao.getNonCompanyMembersFromList(4, Arrays.asList(15,14));
			Assert.assertEquals(list.size(), 0);
		}
		
		@Test
		public void getNonCompanyMembersFromListInvalidListTest() throws Exception{
			List<Integer> list = usersDao.getNonCompanyMembersFromList(4, Arrays.asList(21,14));
			Assert.assertEquals(list.size(), 1);
		}
}
