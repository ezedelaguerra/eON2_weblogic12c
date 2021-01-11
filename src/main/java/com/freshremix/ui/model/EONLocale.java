package com.freshremix.ui.model;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.freshremix.util.ThreadContextUtil;

public class EONLocale implements InitializingBean {

	private static final Logger LOGGER = Logger.getLogger(EONLocale.class);

	private static final List<Locale> SUPPORTED_BASE_LOCALE = Arrays.asList(
			new Locale("en"), new Locale("ja"));

	private String localization;
	private Locale locale;

	public void afterPropertiesSet() {
		if (localization == null)
			localization = "ja"; // set defualt locale if not specified
		locale = new Locale(localization);
	}

	public void setLocalization(String localization) {
		this.localization = localization;
	}

	/**
	 * If the ThreadContext Locale field is available and supported by the
	 * system, it will return that value, otherwise, it will return what ever
	 * locale was set in the application context
	 * 
	 * @return Locale
	 */
	public Locale getLocale() {
		Locale contextLocale = ThreadContextUtil.getThreadContext()
				.getContextLocale();
		
		if (contextLocale != null) {
			if (SUPPORTED_BASE_LOCALE.contains(new Locale(contextLocale.getLanguage()))) {
				return contextLocale;
			}
		}
		LOGGER.info("Thread Context Locale :" + (contextLocale == null? "null" :contextLocale)
				+ " is null or not supported, returning default set locale:"
				+ locale);
		return locale;
	}

}
