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
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author nvelasquez
 *
 */
public class CacheFilter implements Filter {
	
	  private FilterConfig filterConfig = null;
	  
	  public static final String KEY_EXPIRES_DAYS = "expires.days";
	  
	  private int expirationDays;
	  
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
	    HttpServletResponse httpResponse = (HttpServletResponse) response;
	    HttpServletRequest rq = (HttpServletRequest)request;
	    String url = rq.getRequestURI().toLowerCase();
	    
	    System.out.println("cache url:[" + url + "]");
	    

	    /*Then we just set the appropriate headers
	    and invoke the next filter in the chain:
	    */
	    long now = System.currentTimeMillis();
	    long one_day_sec = 86400; //seconds in one day
	    long one_day_millis = one_day_sec * 1000; //millis in one day
	    
	    this.expirationDays = Integer.parseInt(this.filterConfig.getInitParameter(KEY_EXPIRES_DAYS));
	    
	    httpResponse.setHeader("Cache-Control", "public,max-age=" + String.valueOf(one_day_sec * this.expirationDays)); //after 7days
	    httpResponse.setDateHeader("Expires", now + one_day_millis * this.expirationDays); //after 7days
	    httpResponse.setHeader("Pragma", "cache");
	    chain.doFilter(request, response);
	    /* this method calls other filters in the order they are 
	    written in web.xml
	    */
	  }
	} 
