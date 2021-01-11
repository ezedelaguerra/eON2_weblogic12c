package com.freshremix.webapp.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.Role;
import com.freshremix.model.TOSUserContext;
import com.freshremix.model.TermsOfService;
import com.freshremix.model.User;
import com.freshremix.model.UsersTOS;
import com.freshremix.service.MessageI18NService;
import com.freshremix.service.TOSAdminService;
import com.freshremix.service.TOSUserService;
import com.freshremix.util.SessionHelper;

public class AdminTOSController extends MultiActionController {

	private static final Logger LOGGER = Logger.getLogger(AdminTOSController.class);
	private static final String LOGIN_INVALID_USERNAME_PASSWORD = "login.invalid.username.password";
	private static final String LOGIN_REQUIRED_USERNAME_PASSWORD = "login.required.username.password";

	private TOSAdminService tosAdminService;
	private TOSUserService tosUserService;
	
	private MessageI18NService messageI18NService;
	
	

	public void setMessageI18NService(MessageI18NService messageI18NService) {
		this.messageI18NService = messageI18NService;
	}

	
	public TOSAdminService getTosAdminService() {
		return tosAdminService;
	}

	@Required
	public void setTosAdminService(TOSAdminService tosAdminService) {
		this.tosAdminService = tosAdminService;
	}

	public TOSUserService getTosUserService() {
		return tosUserService;
	}

	@Required
	public void setTosUserService(TOSUserService tosUserService) {
		this.tosUserService = tosUserService;
	}

	/**
	 * Loads the latest TOS
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView TOSload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("json");
		TermsOfService tos = null;

		try {
			tos = tosAdminService.getLatestTOS();
			mav.addObject("tos", tos);
		} catch (ServiceException se) {
			mav.addObject("infoMsg",
					messageI18NService.getErrorMessage(se.getErr()));
		}

		return mav;
	}

	/**
	 * Saves the changes to the TOS maintenance.
	 * 
	 * This currently handles Major and Minor change, but currently hard wired to
	 * treat all requests as Major change.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView TOSsave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		User user = (User) SessionHelper.getAttribute(request,
				SessionParamConstants.USER_PARAM);

		TermsOfService tos = new TermsOfService();
		tos.setTosId(request.getParameter("tosId"));
		tos.setContent(request.getParameter("content"));
		tos.setEmailList(request.getParameter("emailList"));
		/**
		 * TOS submit is always a major change
		 */
		tos.setIsMinorChange(false);
		tos.setCreatedBy(request.getParameter("createdBy"));
		String version = request.getParameter("version");

		boolean forUpdate = true;
		if (StringUtils.isBlank(tos.getTosId())) {
			tos.setCreatedBy(user.getUserName());
			forUpdate = false;
		} else {
			if (tos.getIsMinorChange()) {
				tos.setVersion(new Integer(version));
				tos.setModifiedBy(user.getUserName());
			} else {
				forUpdate = false;

			}
		}
		ModelAndView mav = new ModelAndView("json");

		try {
			if (!forUpdate) {
				tos = tosAdminService.createTermsOfService(tos);
			} else {
				tos = tosAdminService.updateTermsOfService(tos);
			}
			mav.addObject("tos", tos);
		} catch (ServiceException se) {
			mav.addObject("infoMsg",
					messageI18NService.getErrorMessage(se.getErr()));
		}

		return mav;
	}

	/**
	 * Checks if the TOS needs to be displayed.
	 * 
	 * If TOS needs to be displayed, the ROLE of the user is replaced with
	 * ROLE_ANONYMOUS to prevent the user from directly going to a secured page
	 * by typing the address.
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView TOSAgreement(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = (User) SessionHelper.getAttribute(request,
				SessionParamConstants.USER_PARAM);

		TermsOfService tos;
		try {

			TOSUserContext userTOSContext = tosUserService
					.getUserTOSContext(user);
			TermsOfService tosLatest = tosAdminService.getLatestTOS();
			if (userTOSContext.getAllowedUsageExpired()) {
				request.getSession().invalidate();
				return new ModelAndView(
						"redirect:/app/login/jsp/login.jsp?errorMsg=tos_user_expired");
			}

			else if (tosLatest==null || (userTOSContext!=null && !userTOSContext.getDisplayTOS())) {
				return new ModelAndView("../afterLogin.do?TOS=true");
			} else {
				// revoke the role so that user cannot navigate to other pages
				setAuthority("ROLE_ANONYMOUS");

				ModelAndView mav = new ModelAndView("common/jsp/termsofservice");
				tos = userTOSContext.getTermsOfService();
				mav.addObject("tos", tos);
				return mav;
			}
		} catch (ServiceException e) {
			return (new ModelAndView("common/jsp/termsofservice", "infoMsg",
					messageI18NService.getErrorMessage(e.getErr())));
		}
	}

	private void setAuthority(String role) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		GrantedAuthority[] authorities = new GrantedAuthority[] { (GrantedAuthority) new GrantedAuthorityImpl(
				role) };
		Authentication newAuth = new UsernamePasswordAuthenticationToken(
				auth.getPrincipal(), auth.getCredentials(), authorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}

	/**
	 * Saves the response of the User to the TOS screen upon login
	 * 
	 * Initially when control is passed to this method, the ROLE of the user is
	 * revoked (i.e. set with ROLE_ANONYMOUS).
	 * 
	 * After agreeing or disagreeing to the TOS, the role is reinstated so that
	 * user can proceed to other pages.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView TOSAgreementResponse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("json");

		User user = (User) SessionHelper.getAttribute(request,
				SessionParamConstants.USER_PARAM);

		// reinstate the role
		Role role = user.getRole();
		setAuthority("ROLE_" + role.getRoleName());

		String isAgree = request.getParameter("isAgree");

		UsersTOS usersTOS = new UsersTOS();
		usersTOS.setUserId(user.getUserId());
		usersTOS.setFlag(isAgree);
		usersTOS.setFlagSetBy(user.getUserName());
		try {
			tosUserService.saveUsersTOSResponse(usersTOS, user);
		} catch (ServiceException e) {
			mav.addObject("infoMsg",
					messageI18NService.getErrorMessage(e.getErr()));
		}

		return mav;
	}

	/**
	 * Handles the displaying of the popup screen for the TOS
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView TOSPopUpDisplay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("json");

		User user = (User) SessionHelper.getAttribute(request,
				SessionParamConstants.USER_PARAM);

		try {
			TermsOfService tos = tosAdminService.getLatestTOS();
			TOSUserContext userTOSContext = tosUserService
					.getUserTOSContext(user);
			boolean userHasAgreed = userTOSContext.getUserHasAgreed();
			mav.addObject("tos", tos);
			mav.addObject("displayBtn", !userHasAgreed);

		} catch (ServiceException se) {
			mav.addObject("infoMsg",
					messageI18NService.getErrorMessage(se.getErr()));
			mav.addObject("displayBtn", false);

		}

		return mav;
	}

	public ModelAndView TOSState(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("json");
		TermsOfService tos = null;

	
			tos = tosAdminService.getLatestTOS();
			TermsOfService.TOSState state;
			try {
				state = tosAdminService.getTOSState(tos);
				mav.addObject("isTOSNew", state.toString());
			} catch (Exception e) {
				LOGGER.error("TOSState error : "+e.getMessage());
			}
			
				
			
		return mav;
	}
	
	
	public ModelAndView TOSreset(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("json");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		org.springframework.security.userdetails.User principal = (org.springframework.security.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		/*
		 * early validation for username password done in controller since
		 * service does not have any idea of the logged in user
		 */
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			mav.addObject("infoMsg", messageI18NService.getPropertyMessage(LOGIN_REQUIRED_USERNAME_PASSWORD));
			return mav;
		}
		
		if(!username.equals(principal.getUsername())){
			mav.addObject("infoMsg", messageI18NService.getPropertyMessage(LOGIN_INVALID_USERNAME_PASSWORD));
			return mav;
		}

		try {
			
			tosUserService.resetTOSAcceptance(username, password);
		} catch (ServiceException e) {
			
			mav.addObject("infoMsg",
					messageI18NService.getErrorMessage(e.getErr()));
		}
		return mav;
	}


	
	

}
