package com.freshremix.service;

import java.util.Map;

import com.freshremix.exception.EONError;

public interface MessageI18NService {

	/**
	 * Plainly retrieves the message from the properties file
	 * @param message
	 * @return
	 */
	public abstract String getPropertyMessage(String key);

	/**
	 * Retrieves the message from the properties file and applies the arguments.
	 * Use curly braces to create a pattern
	 * e.g.  messageKey = My name is {0}. I live in {1}
	 * 
	 * Object[] objectArg = new Object[2];
	 * objectArg[0]='Elmo'
	 * objectArg[1]='Sesame Street'
	 * 
	 * MessageFormat.format('messageKey', objectArg);
	 * 
	 * See java.text.MessageFormat for more details.
	 * 
	 * @param key
	 * @param arguments
	 * @return
	 */
	public abstract String getPropertyMessage(String key, Object[] arguments);

	/**
	 * Creates the error message based from the Error Object
	 * 
	 * @param error
	 * @return
	 */
	public abstract String getErrorMessage(EONError error);

	/**
	 * Retrieves the message from the properties file and applies the arguments.
	 * Use place holders {:placeholdername} in the message
	 * 
	 * e.g. messageKey = My name is {:name}. I live in {:place}
	 * 
	 * Map<String, String> parameters = new HashMap<String, String>();
	 * parameters.put("name", "Elmo");
	 * parameters.put("place", "Sesame Street");
	 * 
	 * Results to the following text:
	 * 
	 * My name is Elmo. I live in Sesame Street.
	 * 
	 * see StrSubstitutor from apache commons lang package
	 * 
	 * @param key
	 * @param arguments
	 * @return
	 */
	String getPropertyMessage(String key, Map<String, String> arguments);

}