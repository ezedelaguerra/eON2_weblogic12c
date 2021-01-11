package com.freshremix.webapp.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.model.User;
import com.freshremix.service.LoginService;
import com.freshremix.service.MessageI18NService;
import com.freshremix.util.RolesUtil;

public class ProxyLoginController implements Controller {

	private LoginService loginService;
	private MessageI18NService messageI18NService;
	
	
	public void setMessageI18NService(MessageI18NService messageI18NService) {
		this.messageI18NService = messageI18NService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String username = request.getParameter("username");

		ModelAndView mav = new ModelAndView("json");

		try {
			setAuthority(username, request);
		} catch (AuthenticationException e) {
			mav.addObject("infoMsg", e.getMessage());
			return mav;
		}

		return mav;

	}

	private void setAuthority(String username, HttpServletRequest request) throws AuthenticationException {
		if (StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException(
					messageI18NService.getPropertyMessage("proxy.username.notexist"));

		}

		User user = loginService.getUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(
					messageI18NService.getPropertyMessage("proxy.username.notexist"));
		} else if (RolesUtil.iseONAdmin(user.getRole().getRoleId())) {
			throw new BadCredentialsException(
					messageI18NService.getPropertyMessage("proxy.admin.error"));

		}
		// need to invalidate previous session so that any attributes stored
		// will not cause problems
		request.getSession(false).invalidate();
		
		request.getSession();

		GrantedAuthority[] authorities = new GrantedAuthority[] { (GrantedAuthority) new GrantedAuthorityImpl(
				"ROLE_" + user.getRole().getRoleName()) };
		Authentication newAuth = new UsernamePasswordAuthenticationToken(
				user.getUserName(), user.getPassword(), authorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}
}
