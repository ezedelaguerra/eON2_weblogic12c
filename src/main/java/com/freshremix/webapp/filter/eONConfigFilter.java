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

public class eONConfigFilter  implements Filter {

	private static final Logger LOGGER = Logger.getLogger(eONConfigFilter.class);
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
		 * Set eON Tax Rate  
		 */

		String taxRate =  TaxUtil.getTAX_RATE().toString();
		StringBuilder sb = new StringBuilder();
		sb.append("Setting Tax Rate: ");
		sb.append(taxRate);
		sb.append(" for request: ");
		sb.append(httpRequest.getRequestURL());
		
		LOGGER.info(sb.toString());
		request.setAttribute("TAX_RATE", taxRate);
		try {
			chain.doFilter(request, response);
		} finally {
			LOGGER.info("cleanup");
			
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
