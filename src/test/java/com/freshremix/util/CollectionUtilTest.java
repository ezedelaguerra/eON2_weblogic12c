package com.freshremix.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CollectionUtilTest {

	@DataProvider(name="splitListDataProvider")
	public Object[][] splitListDataProvider(){
		return new Object[][] {
				{Arrays.asList(1,2,3,4,5,6,7,8,9,10), 2, 5},	
				{Arrays.asList("1","2","3","4","5","6","7","8","9","10"), 3, 4}	
		};
	}
	
	
	@Test(dataProvider="splitListDataProvider")
	public void testSplit(List<?> list, int splitBy, int expectedGroupCount){
		List<List<?>> splitList = CollectionUtil.splitList(list, splitBy);
		Assert.assertEquals(splitList.size(), expectedGroupCount);
		int totalCount=0;
		for (List<?> subList: splitList) {
			totalCount += subList.size();
			Assert.assertTrue(subList.size() <= splitBy);
		}
		Assert.assertEquals(list.size(), totalCount);
	}


	@DataProvider(name="emptySplitListDataProvider")
	public Object[][] emptySplitListDataProvider(){
		return new Object[][] {
				{null},	
				{new ArrayList<Integer>()},
				{Collections.emptyList()}
		};
	}

	@Test(dataProvider="emptySplitListDataProvider")
	public void testSplitEmptyList(List<?> list){
		List<List<?>> splitList = CollectionUtil.splitList(list, 1);
		Assert.assertTrue(splitList.size() == 0);
	}
	
	@DataProvider(name="invalidSplitBy")
	public Object[][] invalidSplitBy(){
		return new Object[][] {
				{-1},	
				{0}
		};
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "invalidSplitBy")
	public void testSplitInvalidSplit(int splitBy) {
		CollectionUtil.splitList(Arrays.asList("1"), splitBy);
	}

}
