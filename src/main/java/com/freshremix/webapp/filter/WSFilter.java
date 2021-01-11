package com.freshremix.webapp.filter;

/**
 * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
 * <BR>
 * <B>Description:</B><BR>
 * Describe class or interface.<BR>
 * <BR>
 * <B>Known Bugs:</B>
 * none<BR>
 * <BR>
 * <B>History:</B>
 * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
 * date       name            changes
 * ------------------------------------------------------------------------------
 * Mar 11, 2010		raquino		
 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author raquino
 *
 */
public class WSFilter implements Filter {
	  private FilterConfig filterConfig = null;
	  
	  public void init(FilterConfig filterConfig) {
	    this.filterConfig = filterConfig;
	  }
	  
	  public void destroy() {
	    this.filterConfig = null;
	  }

	  /*The real work happens in doFilter(). 
	  The reference to the response object is of type ServletResponse,
	  so we need to cast it to HttpServletResponse:
	  */

	  public void doFilter(ServletRequest request, ServletResponse response,
	      FilterChain chain)
	      throws IOException, ServletException {

	    String uri = ((HttpServletRequest) request).getRequestURI();
	    uri  = uri.replaceFirst("^.*eON/", "");
	    if (uri.startsWith("FreshRemix")) {
	        request.getRequestDispatcher("/ws/" + uri).forward(request, response); // Pass to CXF Servlet.

	    } else {
	        chain.doFilter(request, response); // Just let it go 

	    }
	    

	    
	  }
	} 

