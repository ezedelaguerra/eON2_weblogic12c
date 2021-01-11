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
 * May 19, 2010		raquino		
 */
package com.freshremix.webapp.filter;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.json.JsonStringWriter;
import org.springframework.web.servlet.view.json.JsonWriterConfiguratorTemplateRegistry;
import org.springframework.web.servlet.view.json.writer.sojo.SojoJsonStringWriter;

import com.freshremix.util.SessionHelper;

/**
 * @author raquino
 *
 */
public class SessionFilter implements Filter{

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		if (SessionHelper.isSessionExpired(req)) {
			System.out.println("----SessionFilter-------");
			handleJsonSessionExpired(req, res);
		} else {
			chain.doFilter(request, response);
		}
		
	}

	/**returns a JSON response that can be handled by the UI if session is inactive**/
	private void handleJsonSessionExpired(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("isSessionExpired", true);

		JsonStringWriter jsonWriter = new SojoJsonStringWriter();
		JsonWriterConfiguratorTemplateRegistry registry = getRegistry(request);

		jsonWriter.convertAndWrite(model, registry, response.getWriter(), null);

		response.setContentType("text/plain");
	}

	private JsonWriterConfiguratorTemplateRegistry getRegistry(
			HttpServletRequest request) {
		return JsonWriterConfiguratorTemplateRegistry.load(request);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}
	
	

}
