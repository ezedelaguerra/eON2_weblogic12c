package com.freshremix.exception;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> exceptionContext;
	private EONError err;

	public ServiceException(EONError err, Throwable ex,
			Map<String, Object> exceptionContext) {

		super("ServiceException code:" //
				+ StringUtils.trimToEmpty(err.getErrorCode()) //
				+ (ex == null ? "" : //
						" Original Exception Message:" + ex.getMessage()), ex);
		this.err = err;
		this.exceptionContext = exceptionContext;
	}

	public ServiceException(EONError err) {
		this(err, null, null);
	}

	public ServiceException(String errorCode, Throwable ex) {
		this(new EONError(errorCode), ex, null);
	}

	public ServiceException(String errorCode) {
		this(new EONError(errorCode), null, null);
	}

	public ServiceException(String errorCode,
			Map<String, Object> exceptionContext) {
		this(new EONError(errorCode), null, exceptionContext);
	}

	public EONError getErr() {
		return err;
	}

	public void setErr(EONError err) {
		this.err = err;
	}

	public Map<String, Object> getExceptionContext() {
		return this.exceptionContext;
	}

}
