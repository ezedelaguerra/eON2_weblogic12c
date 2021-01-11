package com.freshremix.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.common.test.CSVTestUtil;
import com.freshremix.dao.impl.UserDaoImpl;
import com.freshremix.exception.ServiceException;

public class DownloadUserDetailsServiceImplTest {

	
	private class UserDaoImplStubbed extends UserDaoImpl{
		private List<Map<String, String>> rowData;
		public void setUpReturnData(List<Map<String, String>> returnObjectsList){
			rowData=returnObjectsList;
		}
		@Override
		public void getAllNonAdminUsers(com.ibatis.sqlmap.client.event.RowHandler rowHandler) {
			if(rowData == null){
				rowHandler.handleRow(null);
			} else {
				for (Map<String, String> row : rowData) {
					rowHandler.handleRow(row);
				}
			}
		};
	};
	
	private UserDaoImplStubbed userDaoStubbed = new UserDaoImplStubbed();
	private DownloadUserDetailsServiceImpl downloadService;
	
	@BeforeClass
	public void setUpClass(){
		downloadService = new DownloadUserDetailsServiceImpl();
		downloadService.setUserDao(userDaoStubbed);
	}
	
	@BeforeMethod
	public void setUpMethod(){
		
		userDaoStubbed.setUpReturnData(setUpReturnData());
	}
	
	private List<Map<String, String>> setUpReturnData(){
		List<Map<String, String>> rowReturnList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < 10; i++) {
			rowReturnList.add(createRowMap());
		}
		return rowReturnList;
	}
	
	private Map<String, String> createRowMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("COMPANY_ID","2");
		map.put("COMPANY_NAME","FRASC_S_CA");
		map.put("COMPANY_SHORT_NAME","S_CA");
		map.put("COMPANY_TYPE_ID","2");
		map.put("COMPANY_CONTACT_PERSON","Contact Person");
		map.put("COMPANY_SOX_FLAG","0");
		map.put("COMPANY_ADDRESS1","Company Address 1");
		map.put("COMPANY_ADDRESS2","Company Address 2");
		map.put("COMPANY_ADDRESS3","Company Address 3");
		map.put("COMPANY_TELEPHONE_NUMBER","7527263");
		map.put("COMPANY_FAX_NUMBER","7527261");
		map.put("COMPANY_EMAIL_ADDRESS","eon@farmind.com.ph");
		map.put("COMPANY_COMMENTS","Comments");
		map.put("COMPANY_CREATE_TIMESTAMP","23-JAN-13 04.57.10.000000 PM");
		map.put("COMPANY_CREATED_BY","");
		map.put("COMPANY_UPDATE_TIMESTAMP","");
		map.put("COMPANY_UPDATED_BY","");
		map.put("USER_ID","2");
		map.put("USER_ROLE_ID","1");
		map.put("USER_USERNAME","s_ca01");
		map.put("USER_PASSWORD","1");
		map.put("USER_NAME","s_ca01");
		map.put("USER_SHORTNAME","s_ca01");
		map.put("USER_ADDRESS1","Address 1");
		map.put("USER_ADDRESS2","Address 2");
		map.put("USER_ADDRESS3","Address 3");
		map.put("USER_MOBILE_NUMBER","01-2345-6789");
		map.put("USER_TELEPHONE_NUMBER","01-2345-6789");
		map.put("USER_FAX_NUMBER","01-2345-6789");
		map.put("USER_MOBILE_EMAIL_ADDRESS","rainier.s@farmind.com.ph");
		map.put("USER_PC_EMAIL_ADDRESS","rainier.s@farmind.com.ph");
		map.put("USER_COMMENTS","Comments");
		map.put("USER_USE_BMS","0");
		map.put("USER_DATE_LAST_LOGIN","08-FEB-13 09.44.30.000000 AM");
		map.put("USER_DATE_PASSWORD_CHANGE","");
		map.put("USER_CREATE_TIMESTAMP","23-JAN-13 04.57.11.000000 PM");
		map.put("USER_CREATED_BY","");
		map.put("USER_UPDATE_TIMESTAMP","");
		map.put("USER_UPDATED_BY","");
		map.put("USER_USER_ID_OLD","");
		map.put("USER_TOS_FLAG","");
		map.put("USER_TOS_FLAG_BY","");
		map.put("USER_TOS_TIMESTAMP","");
		return map;
	}

	@Test
	public void testHappyFlow() throws ServiceException, IOException{
		String generatedFile = downloadService.downloadUserDetails("CSV");
		Assert.assertNotNull(generatedFile);
		File file = new File(generatedFile);
		Assert.assertTrue(file.exists());
		Assert.assertEquals(CSVTestUtil.countLines(file), 11);
		CSVTestUtil.deleteFile(file);
	}
	
	@DataProvider(name="invalidFormatDataProvider")
	public Object[][] invalidFormatDataProvider(){
		return new Object[][]{
				{""},
				{null},
				{"InvalidFormat"}
		};
	}

	@Test(expectedExceptions=IllegalArgumentException.class, dataProvider="invalidFormatDataProvider")
	public void testInvalidFormat(String format) throws ServiceException, IOException{
		downloadService.downloadUserDetails(format);
	}

	@Test
	public void testNoResult() throws ServiceException, IOException{
		userDaoStubbed.setUpReturnData(null);

		String generatedFile = downloadService.downloadUserDetails("CSV");
		Assert.assertNotNull(generatedFile);
		File file = new File(generatedFile);
		Assert.assertTrue(file.exists());
		Assert.assertEquals(CSVTestUtil.countLines(file), 1);
		CSVTestUtil.deleteFile(file);
	}
	
}
