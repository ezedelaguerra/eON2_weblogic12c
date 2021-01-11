package com.freshremix.webapp.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.freshremix.model.ThreadContext;
import com.freshremix.util.TaxUtil;
import com.freshremix.util.ThreadContextUtil;

/**
 * Filter that intercepts the request and retrieves the request locale.
 * 
 * The locale is set in the ThreadContext and is available throughout the request.
 * 
 * Note:  Different servlet containers handle threads differently(i.e. some might use a thread pool).
 * 
 * If adding values to the ThreadContext, be sure to erase/delete the value after the request.
 * 
 * @author michael
 *
 */
public class LocaleInterceptorFilter implements Filter {

	private static final Logger LOGGER = Logger.getLogger(LocaleInterceptorFilter.class);
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		//do nothing
	}


	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		ThreadContext threadContext = ThreadContextUtil.getThreadContext();
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		/*
		 * Retrieve and set only the language code part of the locale. For some
		 * reason Spring is unable to resolve en_US to its base name en, so
		 * we'll trim the locale to the language part in this filter
		 */
		Locale requestLocale = httpRequest.getLocale();
		Locale languageOnlyLocale = new Locale(requestLocale.getLanguage());
		threadContext.setContextLocale(languageOnlyLocale);
		StringBuilder sb = new StringBuilder();
		sb.append("Setting Context locale: ");
		sb.append(languageOnlyLocale);
		sb.append(" for request: ");
		sb.append(httpRequest.getRequestURL());
		
		LOGGER.info(sb.toString());
		try {
			chain.doFilter(request, response);
		} finally {
			LOGGER.info("cleanup");
			threadContext.setContextLocale(null);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//do nothing
	}

}
