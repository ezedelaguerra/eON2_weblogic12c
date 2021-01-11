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
 * Nov 9, 2010		raquino		
 */
package com.freshremix.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author raquino
 * 
 */
public class ArrayUtil {

	/**
	 * Reallocates an array with a new size, and copies the contents of the old
	 * array to the new array.
	 * 
	 * @param oldArray
	 *            the old array, to be reallocated.
	 * @param newSize
	 *            the new array size.
	 * @return A new array with the same contents.
	 * 
	 */
	public static Object resizeArray(Object oldArray, int newSize) {

		int oldSize = Array.getLength(oldArray);
		Class elementType = oldArray.getClass().getComponentType();
		Object newArray = Array.newInstance(elementType, newSize);

		int preserveLength = Math.min(oldSize, newSize);

		if (preserveLength > 0)
			System.arraycopy(oldArray, 0, newArray, 0, preserveLength);

		return newArray;
	}

	public static List<Integer> convertStringArray(String[] arrString) {
		List<Integer> listInteger = new ArrayList<Integer>();

		for (String string : arrString) {
			listInteger.add(new Integer(string));
		}
		return listInteger;
	}
}