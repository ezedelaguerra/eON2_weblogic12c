package com.freshremix.model;

import java.util.Map;
import java.util.Set;


public class EONWebServiceResponse {

	private String errorCode;
	private String errorMsg;
	private WSSKU[] skuObjectReturn;
	private SKUInfoWithAltPrice[] sku;
	
	public WSSKU[] getSkuObjectReturn() {
		return skuObjectReturn;
	}
	public void setSkuObjectReturn(WSSKU[] skuObjectReturn) {
		this.skuObjectReturn = skuObjectReturn;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public void setErrorMsg(String errorMsg, Map<String,String> parameters) {
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
		
		this.errorMsg = errorMsg + params.toString();
		
	}
	public void setErrorMsg(Exception e) {
		StringBuffer errMsg = new StringBuffer();			
		errMsg.append("ERROR: " + e.getCause());
		StackTraceElement[] stack = e.getStackTrace();
		if(stack != null && stack.length > 0){
			for (int i = 0; i < stack.length; i++) {
				errMsg.append("\n"); 
				errMsg.append(e.getStackTrace()[i]);
			}	
		}
		this.errorMsg = errMsg.toString();
	}
	public SKUInfoWithAltPrice[] getSku() {
		return sku;
	}
	public void setSku(SKUInfoWithAltPrice[] sku) {
		this.sku = sku;
	}
}
