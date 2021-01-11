/**
 * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
 * <BR>
 * <B>Description:</B><BR>
 * Describe class or interface.<BR>
 * <BR>
 * <B>Known Bugs:</B>
 * none<BR>
 * <BR>
 * <B>History:</B>
 * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
 * date       name            changes
 * ------------------------------------------------------------------------------
 * Feb 25, 2010		gilwen		
 */
package com.freshremix.util;

import java.math.BigDecimal;

/**
 * @author gilwen
 *
 */
public class NumberUtil {

	public static boolean isNullOrZero(Integer value) {
		if (value == null || value.intValue() == 0)
			return true;
		return false;
	}
	
	public static boolean isNullOrZero(BigDecimal value) {
		if (value == null || value.doubleValue() == 0)
			return true;
		return false;
	}
	
	public static BigDecimal nullToZero(BigDecimal value) {
		if (value == null) return new BigDecimal(0);
		else return value;
	}
	
	public static boolean isGreaterThan(BigDecimal value, BigDecimal compare) {
		if (!NumberUtil.isNullOrZero(value)) {
			return value.compareTo(compare) == 1;
		}
		return false;
	}
	
	public static Integer nullToZero(Integer value) {
		if (value == null) return new Integer(0);
		else return value;
	}
	
	public static Integer nullToZero(String value) {
		if (value == null) return new Integer(0);
		else return Integer.valueOf(value);
	}
	public static boolean isNumeric(Object obj){
		try{
			Integer.parseInt((String)obj);
		}catch(NumberFormatException ne){
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the value is a whole number. A value is NOT a whole number if
	 * it contains significant decimal digits. This means 1.0 is a whole number
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isWholeNumber(BigDecimal value){
		/* value should have been validated for null before using this method */
		if (value == null) {
			throw new IllegalArgumentException("Null BigDecimal parameter");
		}
		
		/* quick check if zero */
		if (BigDecimal.ZERO.compareTo(value) == 0) {
			return true;
		}
		
		/* quick check using scale */
		if (value.scale() <=0) {
			return true;
		}
		
		/*
		 * new BigDecimal("1.00") will fail the quick checks.
		 * Strip the zeroes and check the scale again. Note: stripping zeroes is
		 * expensive which is why it is done as last resort
		 */
		if (value.stripTrailingZeros().scale() <=0) {
			return true;
		}
		
		return false;
	}

	/**
	 * Convenience method that negates isWholeNumber
	 * @param value
	 * @return
	 */
	public static boolean isNotWholeNumber(BigDecimal value){
		return !(isWholeNumber(value));
	}
	
	/**
	 * Checks if the BigDecimal value is negative
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNegative(BigDecimal value) {

		if (value == null) {
			throw new IllegalArgumentException("Null BigDecimal value");
		}

		return (value.compareTo(BigDecimal.ZERO) == -1);
	}

	/**
	 * Convenience method that negates the isNegative 
	 * @param value
	 * @return
	 */
	public static boolean isNotNegative(BigDecimal value) {
		return !isNegative(value);
	}
	

	/**
	 * Convenience method to check if the value is greater than zero
	 * Assumes that the value passed is not null.
	 * @param value
	 */
	public static boolean isGreaterThanZero(BigDecimal value) {
		if (value == null) {
			throw new IllegalArgumentException("Null BigDecimal value");
		}
		
		if (value.compareTo(BigDecimal.ZERO)==1){
			return true;
		}
		return false;
	}
	
	/**
	 * Null safe equals checking for 2 numbers.
	 * 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static boolean isEqual(Number number1, Number number2){
		if (number1 != null && number2 !=null) {
			return number1.equals(number2);
		}
		return false;
	}

	/**
	 * Convenience method
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static boolean isNotEqual(Number number1, Number number2){
		return !isEqual(number1, number2);
	}
}
