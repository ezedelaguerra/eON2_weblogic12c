/*
 * Copyright(c) 2005 Fresh Remix Asia Corporation.
 * All Rights Reserved.
 * 
 * This document is protected by Copyright laws. No duplication of this material
 * in any form is allowed without explicit approval from FRAC.
 */
package com.freshremix.webapp.filter.gzip;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class GZIPFilter implements Filter {
    /**
     * If present in request attribute, then will simply allow pass-through to
     * avoid gzipping a gzipped output stream
     */
    public static final String GZIPFILTER_CHAIN = "gzip.filter.chained";

    /**
     * Period that a resource should expire after it was loaded by the browser.
     */
    public static final String KEY_EXPIRES_PERIOD = "expires.period";

    /**
     * comma-delimitted list of extensions of resources that will have an
     * "Expired" header added to the response
     */
    public static final String KEY_EXPIRES_EXT = "expires.extensions";

    /** logger instance */
    protected Logger log;

    /** default period (in seconds) that static resources should expire */
    private int expirationPeriod;

    /** extensions of resources that should have an "Expires" header attached */
    private Collection expirableResources;

    /**
     * Compresses content if the browser supports gzip encoding
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
    	
    	//System.out.println("GZIP");

        // convert to Http Request
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        // store path of request
        final String requestURI = httpRequest.getRequestURI();

        if (request.getAttribute(GZIPFILTER_CHAIN) == Boolean.TRUE) {
            // mark the time that this method request started
            final long startTimestamp = System.currentTimeMillis();

            try {
                // simply chain and do not compress again
                // log.debug("skipping second request invocation...");
                filterChain.doFilter(request, response);
            } catch (IOException ioe) {
                // exception in JSP ?!?!?
                log.error("Error in GZIPFilter.doFilter()", ioe);

                // re-throw error
                throw ioe;
            } catch (ServletException se) {
                // exception in JSP ?!?!?
                log.error("Error in GZIPFilter.doFilter()", se);

                // re-throw error
                throw se;
            } catch (RuntimeException re) {
                // exception in JSP ?!?!?
                log.error("Error in GZIPFilter.doFilter()", re);

                // re-throw error
                throw re;
            }

            //if (FrameworkContext.performanceLog.isDebugEnabled()) {
                // create a log entry for performance measurement
              /*  FrameworkContext.performanceLog.debug((System
                        .currentTimeMillis() - startTimestamp)
                        + ",SUB_URI," + requestURI);
            }*/
        } else {
            // mark request to avoid double gzipping
            request.setAttribute(GZIPFILTER_CHAIN, Boolean.TRUE);

            // for static resources, mark the response with an "Expires" header
            //markExpires(requestURI, (HttpServletResponse) response);

            // mark the time that this method request started
            final long startTimestamp = System.currentTimeMillis();

            // convert to Http Response
            final HttpServletResponse httResponse = (HttpServletResponse) response;

            // get browser's accepted encodings
            final String acceptEncoding = httpRequest
                    .getHeader("accept-encoding");

            // verify that the browser supports gzip!
            if (acceptEncoding == null || acceptEncoding.indexOf("gzip") == -1) {
                // gzip not accepted, simply chain
                log.debug("NOT compressing...");
                filterChain.doFilter(request, response);
/*
                if (FrameworkContext.performanceLog.isDebugEnabled()) {
                    // create a log entry for performance measurement
                    FrameworkContext.performanceLog.debug((System
                            .currentTimeMillis() - startTimestamp)
                            + ",URI," + requestURI);
                }*/
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Compressing: " + requestURI);
                }

                final GZIPResponseWrapper gzrw = new GZIPResponseWrapper(
                        httResponse);

                try {
                    // Gzip acceptable! chain using a GZIP-wrapped response
                    filterChain.doFilter(request, gzrw);
                } catch (Exception ex) {
                    // exception in JSP ?!?!?
                    log.error("Error in GZIPFilter.doFilter()", ex);
                } finally {
                    // complete the response (inside a "finally" block to ensure
                    // that compression operation is completed even if an
                    // exception is thrown)
                    gzrw.complete();

                    if (log.isDebugEnabled() && gzrw.gzipOS != null
                            && gzrw.gzipOS.bytesWritten != 0) {
                        log
                                .debug((requestURI + " compression rate: "
                                        + gzrw.contentLength + "/")
                                        + (gzrw.gzipOS.bytesWritten + " = reduced to ")
                                        + ((100 * gzrw.contentLength / gzrw.gzipOS.bytesWritten) + "% of original size."));
                    }
/*
                    if (FrameworkContext.performanceLog.isDebugEnabled()) {
                        // create a log entry for performance measurement
                        FrameworkContext.performanceLog.debug((System
                                .currentTimeMillis() - startTimestamp)
                                + ",URI," + requestURI);
                    }*/
                }
            }
        }
    }

    /**
     * Simply initializes framework sub-system
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        // initialize framework sub-system
        //WebFrameworkInitializer.init(filterConfig.getServletContext());

        // now we can setup the logger since the framework is ready!
        this.log = Logger.getLogger(this.getClass());

        // prepare collection of expirable resources
        this.expirableResources = new HashSet();

        try {
            // load the expiration in milliseconds
            this.expirationPeriod = Integer.parseInt(filterConfig
                    .getInitParameter(KEY_EXPIRES_PERIOD)) * 1000;
            
            // get comma-delimited resources
            final String[] resources = filterConfig.getInitParameter(
                    KEY_EXPIRES_EXT).trim().toLowerCase().split("\\s*,\\s*");
            
            // store in set
            this.expirableResources.addAll(Arrays.asList(resources));
            
            if (log.isDebugEnabled()) {
                log.debug("Expiration interval: "
                        + (this.expirationPeriod / 1000)
                        + " milliseconds for resources: "
                        + this.expirableResources);
            }
        } catch (Exception ex) {
            // print out a warning
            //this.log.warn("Could not configure expiration policy", ex);
        	System.out.println("Could not configure expiration policy");
        }
    }

    /**
     * This method will add an "Expires" header to the response if the URI
     * extension ends in an string matching one of the ones defined in web.xml
     * configuration for this filter.
     * 
     * @param requestURI
     * @param response
     */
    private void markExpires(String requestURI, HttpServletResponse response) {
        // will be set to true if request should have an "Expires" header
        boolean expirable = false;

        // lowerize the requestURI to be sure
        requestURI = requestURI.toLowerCase();

        // check if this URI is a resource that should have an "Expires" header
        final Iterator it = this.expirableResources.iterator();
        while (it.hasNext()) {
            final String suffix = (String) it.next();
            
            System.out.println("suffix:[" + suffix + "]");
            System.out.println("requestedURI:[" + requestURI + "]");

            if (requestURI.endsWith(suffix)) {
                expirable = true;
                break;
            }
            
        }

        if (!expirable) {
            // no need to add expires header!
            return;
        }

        // add expires header since this URI is included as a resource to expire
        response.setDateHeader("Expires", System.currentTimeMillis()
                + this.expirationPeriod);
    }

    /**
     * Notify framework upon unload of this servlet
     * 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        // Notify framework upon unload of this servlet
    }
}
