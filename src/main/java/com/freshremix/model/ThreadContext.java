package com.freshremix.model;

import java.util.Locale;

/**
 * Context that is shared within a Thread of Execution.
 * 
 * If you want to access a thread specific-globally available values, you may
 * add to the fields.
 * 
 * @author michael
 * 
 */
public class ThreadContext {

	private Locale contextLocale;

	public Locale getContextLocale() {
		return contextLocale;
	}

	public void setContextLocale(Locale contextLocale) {
		this.contextLocale = contextLocale;
	}

	
}
