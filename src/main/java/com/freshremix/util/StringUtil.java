package com.freshremix.util;

import java.io.UnsupportedEncodingException;

public class StringUtil {

	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null || obj.toString().trim().length() == 0)
			return true;
		return false;
	}

	/**
	 * converts NULL to blank("").
	 * @param obj
	 * @return
	 */
	public static String nullToBlank(Object obj) {
		if (obj == null ){	
			return "";
		}
		return obj.toString();
	}
	
	public static String nullToZero(Object obj) {
		if (obj == null ){	
			return "0";
		}
		return obj.toString();
	}
	
	public static String nullToDefaultValue(Object obj, String defaultValue) {
		if (obj == null ){	
			return defaultValue;
		}
		return obj.toString();
	}
	
	public static String toUTF8String(String uniCode) {
		String val = null;
		try {
			val = new String(uniCode.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return val;
	}
	
	public static String notEmptyOrNotNullToOne(Object obj) {
		if (obj == null || obj == "")
			return "0";
		return "1";
	}
	
	/**
	 * This function formats the text to work on CSV file
	 *  
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
    public static String formatCSVText(Object obj, String defaultValue){
		
		if(obj == null){
			return defaultValue;
		}else{
			String text = (String) obj.toString();			
			text = text.replaceAll("\"", "\"\"");
			text = text.replaceAll("\t", "");
			text = text.replaceAll("\n", "");
			text = text.replaceAll("\r", "");
			
			//if text contains a ',' or first charcter is zero (0)
			if(text.indexOf(',') != -1 || (text.indexOf('0') == 0) && text.length() > 1 ){ 
				text = "\"" + text + "\"";
			}
			
			return text;		
		}	
	}
}
