package com.freshremix.util;

import java.math.BigDecimal;

public class OrderUnitUtility {

	private static String ALLOW_DECIMAL_KEY = "*D";
	/**
	 * Checks if the given uom can have decimal values
	 * @param uomName
	 * @return
	 */
	public static boolean isDecimalAllowed(String uomName) {
		if("KG".equalsIgnoreCase(uomName) || uomName.trim().toUpperCase().endsWith( ALLOW_DECIMAL_KEY )){
			return true;
		}
		return false;
	}
	
	/**
	 * Convenience method that negates isDecimalAllowed
	 * @param uomName
	 * @return
	 */
	public static boolean isDecimalNotAllowed(String uomName) {
		return !isDecimalAllowed(uomName);
	}

	/**
	 * Checks if the given order unit qty is valid for the given uom name
	 * @param qty
	 * @param uomName
	 * @return
	 */
	public static boolean isValidOrderUnitQty(BigDecimal qty, String uomName) {

		if (NumberUtil.isNotWholeNumber(qty) && isDecimalNotAllowed(uomName)) {
			return false;
		}

		return true;
	}
	
	
	
	

}
