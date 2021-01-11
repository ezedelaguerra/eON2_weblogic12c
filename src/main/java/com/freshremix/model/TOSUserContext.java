package com.freshremix.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TOSUserContext {

	private boolean allowedUsageExpired;
	private boolean userHasAgreed;
	private boolean displayTOS;
	private TermsOfService termsOfService;
	
	
	public boolean getUserHasAgreed() {
		return userHasAgreed;
	}
	
	public void setUserHasAgreed(boolean userHasAgreed) {
		this.userHasAgreed = userHasAgreed;
	}
	
	public boolean getAllowedUsageExpired() {
		return allowedUsageExpired;
	}
	
	public void setAllowedUsageExpired(boolean allowedUsageExpired) {
		this.allowedUsageExpired = allowedUsageExpired;
	}
	
	public TermsOfService getTermsOfService() {
		return termsOfService;
	}
	
	public void setTermsOfService(TermsOfService termsOfService) {
		this.termsOfService = termsOfService;
	}
	
	public boolean getDisplayTOS() {
		return displayTOS;
	}

	public void setDisplayTOS(boolean displayTOS) {
		this.displayTOS = displayTOS;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("userHasAgreed", this.getUserHasAgreed())
		.append("allowedUsageExpired", this.getAllowedUsageExpired())
		.append("displayTOS", this.getDisplayTOS())
		.toString();
	}
	
	
	
	
	
}
