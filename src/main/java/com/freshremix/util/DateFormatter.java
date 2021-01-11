package com.freshremix.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

public class DateFormatter implements InitializingBean {
	
	private MessageSource messageSource;
	private MessageSourceAccessor messageSourceAccessor;
	
	public static final long MILLIS_PER_DAY = 1000 * 60 * 60 * 24;
	public static final String DATE_FORMAT = "yyyyMMdd";
	
	// format to YYYYMMDD
	// expected input MM/DD/YYYY
	public static String formatToDBParameter(String strDate) {

		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		String newDateFormat = null;
		try {
			date = formatter.parse(strDate);
			formatter = new SimpleDateFormat("yyyyMMdd");
			newDateFormat = formatter.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDateFormat;
	}

	public static Date toDateObj(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dateObj = new Date();
		try {
			dateObj = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateObj;
	}

	public static int getDateDiff(Date startDateObj, Date endDateObj) {
		long diff = endDateObj.getTime() - startDateObj.getTime();

		return (int) (diff / MILLIS_PER_DAY);
	}

	public static int getDateDiff(String startDate, String endDate) {
		Date startDateObj = toDateObj(startDate);
		Date endDateObj = toDateObj(endDate);

		return getDateDiff(startDateObj, endDateObj);
	}

	
	/**
	 * Takes in start and end dates as string in yyyyMMdd format and returns a
	 * list of date formatted string in the same format.
	 * 
	 * The list is inclusive of start and end dates.
	 * 
	 * Eg. start="20130101", end="20130103" result="20130101", "20130102",
	 * "20130103".
	 * 
	 * If start date is null, white space, empty string or contains non digits,
	 * the result is null.
	 * 
	 * If the end date is null, white space, empty string or contains non
	 * digits, the end date takes the value of the start date
	 * 
	 * @param startDateStr
	 *            in yyyyMMdd format
	 * @param endDateStr in yyyyMMdd format
	 * @return List<String> - inclusive of start and end date
	 */
	public static List<String> getDateList(String startDateStr,
			String endDateStr) {

		if (StringUtils.isBlank(startDateStr)
				|| !NumberUtils.isDigits(startDateStr)) {
			return null;
		}
		
		 

		if (StringUtils.isBlank(endDateStr)
				|| !NumberUtils.isDigits(endDateStr)) {
			endDateStr = startDateStr.trim();
		}

		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime startDate = dtf.parseDateTime(startDateStr).withTimeAtStartOfDay();
		DateTime endDate = dtf.parseDateTime(endDateStr).withTimeAtStartOfDay();

		int days = Days.daysBetween(startDate, endDate).getDays();
		List<String> dateList = new ArrayList<String>(days);
		for (int i = 0; i <= days; i++) {
			dateList.add(startDate.plusDays(i).toString(dtf));
		}

		return dateList;
	}

	
	public static List<String> getDateList(String startDate, int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		List<String> dateList = new ArrayList<String>();

		for (int i = 0; i < days; i++) {
			long millis = toDateObj(startDate).getTime() + i * MILLIS_PER_DAY;

			String thisDate = sdf.format(new Date(millis));

			dateList.add(thisDate);
		}

		return dateList;
	}

	public static List<Integer> getDayList(String startDate, String endDate) {
		Calendar cal = Calendar.getInstance();
		List<Integer> dayList = new ArrayList<Integer>();
		int dateDiff = getDateDiff(startDate, endDate);

		for (int i = 0; i <= dateDiff; i++) {
			long millis = toDateObj(startDate).getTime() + i * MILLIS_PER_DAY;
			cal.setTimeInMillis(millis);

			Integer weekDay = Integer
					.valueOf(cal.get(Calendar.DAY_OF_WEEK) - 1);

			dayList.add(weekDay);
			/* 0:sun, 1:mon: 2:tue 3:wed 4:thu 5:fri 6:sat */
		}

		return dayList;
	}

	public static String getDateToday(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String thisDate = sdf.format(new Date());
		return thisDate;
	}

	public static String addDays(String startDate, int daysToAdd) {
		String newDate = "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		long millis = toDateObj(startDate).getTime() + daysToAdd
				* MILLIS_PER_DAY;
		newDate = sdf.format(new Date(millis));

		return newDate;
	}

	public static List<String> getDateListForExcel(String startDate,
			String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("M/d");
		List<String> dateList = new ArrayList<String>();
		int dateDiff = getDateDiff(startDate, endDate);

		for (int i = 0; i <= dateDiff; i++) {
			long millis = toDateObj(startDate).getTime() + i * MILLIS_PER_DAY;

			String thisDate = sdf.format(new Date(millis));

			dateList.add(thisDate);
		}

		return dateList;
	}

	/**
	 * same as the condition date > dateToCompare
	 * 
	 * @param date
	 * @param dateTocompare
	 * @return
	 */
	public static boolean isDateGreaterThan(String date, String dateTocompare) {

		Date dateOne = toDateObj(date);
		Date dateTwo = toDateObj(dateTocompare);

		boolean status = false;
		if (dateOne.after(dateTwo)) {
			status = true;
		}

		return status;
	}
	
	public static String formatToGUIParameter(String strDate) {

		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		String newDateFormat = null;
		try {
			date = formatter.parse(strDate);
			formatter = new SimpleDateFormat("yyyy/MM/dd");
			newDateFormat = formatter.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDateFormat;
	}
	
	public static Boolean isValidFormat(String strDate) {
		
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		
		try {
			formatter.parse(strDate);
		} catch (ParseException e) {
			return false;
		}
		
		return true;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	public MessageSourceAccessor getMessageSourceAccessor() {
		return messageSourceAccessor;
	}
	public void afterPropertiesSet() {
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}
	
	public static String convertToString(Date date) {
		String newDateFormat = null;
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		newDateFormat = formatter.format(date);
		return newDateFormat;
	}

}
