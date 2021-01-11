package com.freshremix.model;

import java.util.Map;
import java.util.Set;

public class WSBuyerResponse {

	
	private String resultCode;
	private String resultMsg;
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public void setResultMsg(String resultMsg, Map<String,String> parameters) {
		StringBuffer params = new StringBuffer();
		Set<String> set = parameters.keySet();
		params.append("{");
		for (String key : set) {
			params.append(key);
			params.append("=");
			params.append(parameters.get(key));
			params.append(",");
		}
		params.append("}");
		
		this.resultMsg = resultMsg + params.toString();
		
	}
	public void setResultMsg(Exception e) {
		StringBuffer errMsg = new StringBuffer();			
		errMsg.append(": " + e.getCause());
		StackTraceElement[] stack = e.getStackTrace();
		if(stack != null && stack.length > 0){
			for (int i = 0; i < stack.length; i++) {
				errMsg.append("\n"); 
				errMsg.append(e.getStackTrace()[i]);
			}	
		}
		this.resultMsg = errMsg.toString();
	}
}
