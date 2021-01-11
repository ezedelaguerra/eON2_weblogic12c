package com.freshremix.dao.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.common.test.CSVTestUtil;

public class CSVUserRowDataHandlerTest {
	
	
	@Test
	public void testHandleRow() throws Exception{
		CSVUserRowDataHandler csvUserRowDataHandler = new CSVUserRowDataHandler();
		
		File file = csvUserRowDataHandler.initializeFile(null);
		Assert.assertTrue(file.exists());
		Map<String, String> row = prepareRowData(csvUserRowDataHandler.getFieldOrder());
		csvUserRowDataHandler.handleRow(row);

		Assert.assertTrue(file.exists());
		Assert.assertEquals(CSVTestUtil.countLines(file), 2);
		CSVTestUtil.deleteFile(file);
	}

	@DataProvider(name="emptyRowDataProvider")
	public  Object[][] emptyRowDataProvider(){
		return new Object[][] { 
			{ null },
			{ new HashMap<String, String>()}
			};
	}
	
	
	@Test(dataProvider="emptyRowDataProvider")
	public void testNullRow(Map<String, String> row) throws Exception{
		
		CSVUserRowDataHandler csvUserRowDataHandler = new CSVUserRowDataHandler();

		File file = csvUserRowDataHandler.initializeFile(null);
		Assert.assertTrue(file.exists());
		
		csvUserRowDataHandler.handleRow(row);
		Assert.assertTrue(file.exists());
		Assert.assertEquals(CSVTestUtil.countLines(file), 1);
		CSVTestUtil.deleteFile(file);
	}
	
	
	private Map<String, String> prepareRowData(List<String> fieldOrderList) {
		Map<String, String> row = new HashMap<String, String>();
		for (String field : fieldOrderList) {
			row.put(field, "This is a comma(,). This is a doublequote(\"). These are japanese character(下記のとおりメンテナンスをおこないます。ご不便おかけし申し訳ございません。) This is a CRLF \n This should be on the next line.");
		}
		return row;
	}
	
}
