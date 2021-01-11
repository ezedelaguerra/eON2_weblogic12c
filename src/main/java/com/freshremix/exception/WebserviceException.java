package com.freshremix.exception;

public class WebserviceException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMsgCode;
	private String errorMsg;
	
	public WebserviceException(String errorCode, String errorMsgCode){
		this.errorCode = errorCode;
		this.errorMsgCode = errorMsgCode;
	}

	public WebserviceException(String errorCode, String errorMsgCode, String errorMsg){
		this.errorCode = errorCode;
		this.errorMsgCode = errorMsgCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsgCode() {
		return errorMsgCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}
