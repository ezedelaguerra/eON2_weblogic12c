package com.freshremix.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionHelper {

	public static Object getAttribute(HttpServletRequest request, String param) {
		return request.getSession().getAttribute(param);
	}
	
	public static void setAttribute(HttpServletRequest request, String param, Object value) {
		request.getSession().setAttribute(param, value);
	}
	
	public static boolean isSessionExpired(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session == null ? true : false;
	}
	
}
