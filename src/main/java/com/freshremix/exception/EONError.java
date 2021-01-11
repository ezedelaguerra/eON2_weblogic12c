package com.freshremix.exception;

import java.util.Arrays;

import org.apache.commons.lang.builder.ToStringBuilder;

public class EONError {

	private Object[] arguments;
	private String errorCode;
	
	public EONError(String errorCode){
		this.errorCode = errorCode;
	}

	public EONError(String errorCode, Object[] argument){
		this.errorCode = errorCode;
		this.arguments = argument;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("errorCode", this.errorCode)
		.append("arguments", this.arguments)
		.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(arguments);
		result = prime * result
				+ ((errorCode == null) ? 0 : errorCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EONError other = (EONError) obj;
		if (!Arrays.equals(arguments, other.arguments))
			return false;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		return true;
	}
	
	

}
