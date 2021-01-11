package com.freshremix.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DateFormatterTest {

	@DataProvider(name = "dateListDataProvider")
	public Object[][] dateListDataProvider() {
		return new Object[][] { //
		{ "20121230", "20130101", 3 }, //
				{ "20130101", "20130107", 7 }, //
				{ "20130101", "20130101", 1 }, //
				{ "20130101", null, 1 }, //
				{ "20130101", " ", 1 }, //
				{ "20130101", "a", 1 }, //
				{ "20130101", "", 1 } //
		};
	}

	@Test(dataProvider = "dateListDataProvider")
	public void testGetDateList(String startDateStr, String endDateStr,
			Integer expectedCount) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime startDate = dtf.parseDateTime(startDateStr);

		List<String> dateList = DateFormatter.getDateList(startDateStr,
				endDateStr);
		Assert.assertEquals(dateList.size(), expectedCount.intValue());
		int addDays = 0;
		for (String dateString : dateList) {
			Assert.assertEquals(dateString, startDate.plusDays(addDays)
					.toString(dtf));
			addDays++;
		}

	}

	@DataProvider(name = "invalidEntryDataProvider")
	public Object[][] invalidEntryDataProvider() {
		return new Object[][] { //
		{ "", "20130101" }, //
				{ "  ", "20130101" }, //
				{ "a", "20130101" }, //
				{ null, "20130101" } //
		};
	}

	@Test(dataProvider = "invalidEntryDataProvider")
	public void testGetDateListInvalidEntry(String startDateStr,
			String endDateStr) {
		List<String> dateList = DateFormatter.getDateList(startDateStr,
				endDateStr);
		Assert.assertNull(dateList);

	}
	
	@Test
	public void testGetDateDiffDateType(){
		Date startDateObj = DateTime.now().toDate();
		Date endDateObj = DateTime.now().plusDays(1).toDate();
		int dateDiff = DateFormatter.getDateDiff(startDateObj, endDateObj);
		Assert.assertEquals(dateDiff, 1);
	}

	@Test
	public void testGetDateDiffStringType(){
		String startDateStr = "20130101";
		String endDateStr = "20130102";
		int dateDiff = DateFormatter.getDateDiff(startDateStr, endDateStr);
		Assert.assertEquals(dateDiff, 1);
	}

}
