package com.freshremix.util;

import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OrderSheetUtilTest {

	@DataProvider(name="toListDataProvider")
	public Object[][] toListDataProvider(){
		return new Object[][] {
				{null, 0},
				{"a", 0},
				{"   ", 0},
				{"", 0},
				{";", 0},
				{"1;", 1},
				{";1;", 1},
				{" 1 ", 1},
				{"1;2;3", 3},
				{"1;2;3;", 3},
				{" 1 ; 2 ; 3 ", 3},
				{" 1 ; 2 ; ; 3 ", 3},
				{" 12 ; ; 3 ", 2},
				{" 12 ;0; 3 ", 2},
				{" 12a ; ; 3 ", 1}
		};
	}
	@Test(dataProvider="toListDataProvider")
	public void testToList(String input, Integer expectedCount){
		List<Integer> nullable = Collections.emptyList();
		Assert.assertNotNull(nullable);
		List<Integer> result = OrderSheetUtil.toList(input);
		Assert.assertEquals(Integer.valueOf(result.size()), expectedCount);
	}
}
