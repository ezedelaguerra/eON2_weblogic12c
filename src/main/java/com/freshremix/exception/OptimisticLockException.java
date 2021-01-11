package com.freshremix.exception;

public class OptimisticLockException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorString;
	
	public OptimisticLockException(String errorString)
	{
		this.errorString=errorString;
	}
	
	public String getErrorString() {
		return errorString;
	}
	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}

	
	
	
}
