package com.freshremix.webapp.controller.admin;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.exception.EONError;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.Role;
import com.freshremix.model.TOSUserContext;
import com.freshremix.model.TermsOfService;
import com.freshremix.model.User;
import com.freshremix.model.UsersTOS;
import com.freshremix.service.MessageI18NService;
import com.freshremix.service.TOSAdminService;
import com.freshremix.service.TOSUserService;

public class AdminTOSControllerTest {

	private static final String LOGIN_REQUIRED_USERNAME_PASSWORD = "login.required.username.password";
	private static final String TERMS_OF_SERVICE_DATA_NOT_FOUND_OR_EMPTY = "Terms of Service data not found or empty";
	private static final String LOGIN_INVALID_USERNAME_PASSWORD = "login.invalid.username.password";
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockHttpSession session;

	@Mock
	private TOSUserService mockTOSUserService;
	@Mock
	private TOSAdminService mockTOSAdminService;
	@Mock
	private MessageI18NService mockMessageI18NService;

	AdminTOSController adminTosController;

	@BeforeMethod
	public void init() {
		MockitoAnnotations.initMocks(this);

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		adminTosController = new AdminTOSController();
		adminTosController.setTosAdminService(mockTOSAdminService);
		adminTosController.setTosUserService(mockTOSUserService);
		adminTosController.setMessageI18NService(mockMessageI18NService);
	}

	@AfterMethod
	public void teardown() {
		reset(mockTOSAdminService, mockTOSUserService, mockMessageI18NService);
		resetAuthentication();
	}

	private void setRequestParameters() {
		request.setParameter("content", "Joe");
		request.setParameter("emailList", "sss,ddd,fff,ggg");
		request.setParameter("resetTOSacceptance", "true");
		request.setParameter("version", "0");
		request.setMethod("POST");
	}

	private void setRequestParameters(User user) {
		setRequestParameters();
		session.setAttribute(SessionParamConstants.USER_PARAM, user);
		request.setSession(session);
	}

	private void setUserRequestParameters(String userName) {
		request.setParameter("username", userName);
		request.setParameter("password", "1");
		request.setMethod("POST");
	}
	private void setKenUserRequestParameters() {
		setUserRequestParameters("Ken");
	}

	private User createUser(String userName, String roleStr) {
	
		Role role = new Role();
		role.setRoleName(roleStr);
		User user = new User();
		user.setUserName(userName);
		user.setRole(role);
		
		return user;
	}

	private User createSellerUser(String role){
		String userName = "s_ca01";
		setAuthority(userName , role);
	
		return createUser(userName, "SELLER");
	}

	private User createSellerUser(){
		return createSellerUser("ROLE_SELLER");
	}

	private User createKenUser(){
		return createUser("Ken", "KEN");
	}

	@Test
	public void testTOSloadHappyFlow() throws Exception {
		setRequestParameters();

		when(mockTOSAdminService.getLatestTOS()).thenReturn(
				new TermsOfService());

		ModelAndView mv = adminTosController.TOSload(request, response);

		Assert.assertNull(mv.getModelMap().get("infoMsg"));
		Assert.assertNotNull(mv.getModelMap().get("tos"));
		verify(mockTOSAdminService).getLatestTOS();

	}

	@Test
	public void testTOSloadWithError() throws Exception {
		setRequestParameters();

		String mockedErrorCode = "mockedErrorCode";
		EONError err = new EONError(mockedErrorCode);
		ServiceException serviceException = new ServiceException(err);
		when(mockMessageI18NService.getErrorMessage(err)).thenReturn(err.getErrorCode());
		when(mockTOSAdminService.getLatestTOS()).thenThrow(
				serviceException);
		
		ModelAndView mv = adminTosController.TOSload(request, response);
		
		Assert.assertNull(mv.getModelMap().get("tos"));
		Assert.assertNotNull(mv.getModelMap().get("infoMsg"), mockedErrorCode);
		verify(mockMessageI18NService).getErrorMessage(err);
		verify(mockTOSAdminService).getLatestTOS();

	}

	@Test
	public void testTOSPopUpDisplayHappyFlow() throws Exception {
		User createUser = createSellerUser();
		setRequestParameters(createUser);

		when(mockTOSAdminService.getLatestTOS()).thenReturn(
				new TermsOfService());
		
		TOSUserContext tosUserContext = new TOSUserContext();
		tosUserContext.setUserHasAgreed(true);
		when(mockTOSUserService.getUserTOSContext(createUser)).thenReturn(tosUserContext);
		
		ModelAndView mv = adminTosController.TOSPopUpDisplay(request, response);

		Assert.assertNull(mv.getModelMap().get("infoMsg"));
		Assert.assertNotNull(mv.getModelMap().get("tos"));
		Assert.assertEquals(mv.getModelMap().get("displayBtn"), false);
		
		verify(mockTOSAdminService).getLatestTOS();
		verify(mockTOSUserService).getUserTOSContext(createUser);

	}

	@Test
	public void testTOSsaveHappyFlow() throws Exception {
		setRequestParameters(createKenUser());
		
		when(
				mockTOSAdminService
						.createTermsOfService(any(TermsOfService.class)))
				.thenReturn(new TermsOfService());

		ModelAndView mv = adminTosController.TOSsave(request, response);
		Assert.assertNotNull(mv.getModelMap().get("tos"));
		Assert.assertNull(mv.getModelMap().get("infoMsg"));
		verify(mockTOSAdminService).createTermsOfService(
				any(TermsOfService.class));
	}

	@Test
	public void testTOSsaveWithError() throws Exception {
		setRequestParameters(createKenUser());

		when(
				mockTOSAdminService
						.createTermsOfService(any(TermsOfService.class)))
				.thenThrow(
						new IllegalArgumentException(
								TERMS_OF_SERVICE_DATA_NOT_FOUND_OR_EMPTY));

		ModelAndView mv = null;
		try {
			mv = adminTosController.TOSsave(request, response);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(TERMS_OF_SERVICE_DATA_NOT_FOUND_OR_EMPTY,
					e.getMessage());
		}
		Assert.assertNull(mv);

		verify(mockTOSAdminService).createTermsOfService(
				any(TermsOfService.class));

	}

	@Test
	public void testTOSsaveMajorChange() throws Exception {

		setRequestParameters(createKenUser());
		request.setParameter("tosId", "F856E19EEEBD4307AF3E5729689F7653");
		when(
				mockTOSAdminService
						.createTermsOfService(any(TermsOfService.class)))
				.thenReturn(new TermsOfService());
		when(
				mockTOSAdminService
						.updateTermsOfService(any(TermsOfService.class)))
				.thenReturn(new TermsOfService());

		ModelAndView mv = adminTosController.TOSsave(request, response);
		Assert.assertNotNull(mv.getModelMap().get("tos"));
		Assert.assertNull(mv.getModelMap().get("infoMsg"));

		verify(mockTOSAdminService, never()).updateTermsOfService(
				any(TermsOfService.class));
		verify(mockTOSAdminService).createTermsOfService(
				any(TermsOfService.class));
	}

	
	@Test
	public void testTOSAgreementWithoutTOSDisplay() throws Exception {
		
		User user = createSellerUser();
		setRequestParameters(user);

		TOSUserContext tosUserContext = new TOSUserContext();
		tosUserContext.setAllowedUsageExpired(false);
		tosUserContext.setDisplayTOS(false);
		tosUserContext.setTermsOfService(null);
		tosUserContext.setUserHasAgreed(true);

		when(mockTOSUserService.getUserTOSContext(user)).thenReturn(
				tosUserContext);
		when(mockTOSAdminService.getLatestTOS()).thenReturn(
				new TermsOfService());

		ModelAndView mv = adminTosController.TOSAgreement(request, response);

		Assert.assertEquals("ROLE_SELLER", getAuthority());
		Assert.assertNull(mv.getModelMap().get("tos"));
		Assert.assertNull(mv.getModelMap().get("infoMsg"));
		verify(mockTOSUserService).getUserTOSContext(user);

	}

	@Test
	public void testTOSAgreementWithoutTOSDisplayNullTOS() throws Exception {
		User user = createSellerUser();

		setRequestParameters(user);

		when(mockTOSUserService.getUserTOSContext(user)).thenReturn(
				new TOSUserContext());
		when(mockTOSAdminService.getLatestTOS()).thenReturn(null);

		ModelAndView mv = adminTosController.TOSAgreement(request, response);

		Assert.assertEquals("ROLE_SELLER", getAuthority());
		Assert.assertNull(mv.getModelMap().get("tos"));
		Assert.assertNull(mv.getModelMap().get("infoMsg"));
		verify(mockTOSUserService).getUserTOSContext(user);

	}

	@Test
	public void testTOSAgreementWithTOSDisplay() throws Exception {
		User user =  createSellerUser();

		setRequestParameters(user);
		
		TermsOfService tos = new TermsOfService();
		tos.setContent("just a content");

		TOSUserContext tosUserContext = new TOSUserContext();
		tosUserContext.setAllowedUsageExpired(false);
		tosUserContext.setDisplayTOS(true);
		tosUserContext.setTermsOfService(tos);
		tosUserContext.setUserHasAgreed(false);

		when(mockTOSUserService.getUserTOSContext(user)).thenReturn(
				tosUserContext);
		when(mockTOSAdminService.getLatestTOS()).thenReturn(
				new TermsOfService());

		ModelAndView mv = adminTosController.TOSAgreement(request, response);

		Assert.assertEquals("ROLE_ANONYMOUS", getAuthority());
		Assert.assertNotNull(mv.getModelMap().get("tos"));
		Assert.assertNull(mv.getModelMap().get("infoMsg"));
		verify(mockTOSUserService).getUserTOSContext(user);
	}

	@Test
	public void testTOSAgreementResponseHappyFlow() throws Exception {

		User user = createSellerUser("ROLE_ANONYMOUS");
		session.setAttribute(SessionParamConstants.USER_PARAM, user);

		request.setParameter("isAgree", "1");
		request.setMethod("POST");
		request.setSession(session);

		ModelAndView mv = adminTosController.TOSAgreementResponse(request,
				response);

		Assert.assertEquals("ROLE_SELLER", getAuthority());
		Assert.assertNull(mv.getModelMap().get("tos"));
		Assert.assertNull(mv.getModelMap().get("infoMsg"));
		verify(mockTOSUserService).saveUsersTOSResponse(any(UsersTOS.class),
				any(User.class));
	}

	private void setAuthority(String userName, String role) {
		GrantedAuthority[] authorities = new GrantedAuthority[] { (GrantedAuthority) new GrantedAuthorityImpl(
				role) };

		Authentication newAuth = new UsernamePasswordAuthenticationToken(
				userName, "password", authorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}

	private String getAuthority() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		GrantedAuthority[] authorities = auth.getAuthorities();
		return authorities[0].getAuthority();
	}

	private void resetAuthentication() {
		SecurityContextHolder.getContext().setAuthentication(null);

	}

	@Test
	public void TOSStateTestNEW() throws Exception {

		when(mockTOSAdminService.getLatestTOS()).thenReturn(
				new TermsOfService());
		when(mockTOSAdminService.getTOSState(any(TermsOfService.class)))
				.thenReturn(TermsOfService.TOSState.NEW);

		ModelAndView mv = adminTosController.TOSState(request, response);

		Assert.assertEquals(mv.getModelMap().get("isTOSNew"), "NEW");
		Assert.assertNull(mv.getModelMap().get("infoMsg"));

		verify(mockTOSAdminService).getLatestTOS();
		verify(mockTOSAdminService).getTOSState(any(TermsOfService.class));
	}

	@Test
	public void TOSStateTestOLD() throws Exception {

		request.setMethod("POST");

		when(mockTOSAdminService.getLatestTOS()).thenReturn(
				new TermsOfService());
		when(mockTOSAdminService.getTOSState(any(TermsOfService.class)))
				.thenReturn(TermsOfService.TOSState.OLD);

		ModelAndView mv = adminTosController.TOSState(request, response);

		Assert.assertEquals(mv.getModelMap().get("isTOSNew"), "OLD");
		Assert.assertNull(mv.getModelMap().get("infoMsg"));
		verify(mockTOSAdminService).getLatestTOS();
		verify(mockTOSAdminService).getTOSState(any(TermsOfService.class));
	}

	@Test
	public void TOSStateTestWithError() throws Exception {

		request.setMethod("POST");

		when(mockTOSAdminService.getLatestTOS()).thenReturn(null);
		when(mockTOSAdminService.getTOSState(any(TermsOfService.class)))
				.thenThrow(new IllegalArgumentException("Null TOS"));

		ModelAndView mv = new ModelAndView();
		try {
			mv = adminTosController.TOSState(request, response);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("Null TOS", e.getMessage());
		}
		Assert.assertNull(mv.getModelMap().get("isTOSNew"));

		verify(mockTOSAdminService).getLatestTOS();
		verify(mockTOSAdminService).getTOSState(any(TermsOfService.class));
	}

	private void setRoleKenAuthority() {
		GrantedAuthority[] authorities = new GrantedAuthority[] { (GrantedAuthority) new GrantedAuthorityImpl(
				"ROLE_KEN") };
		org.springframework.security.userdetails.User user = new org.springframework.security.userdetails.User(
				"Ken", "1", true, true, true, true, authorities);
		Authentication newAuth = new UsernamePasswordAuthenticationToken(user,
				null);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}

	@Test
	public void testTOSResetloadHappyFlow() throws Exception {
		setRoleKenAuthority();
		setKenUserRequestParameters();

		ModelAndView mv = adminTosController.TOSreset(request, response);
		Assert.assertNull(mv.getModelMap().get("infoMsg"));
		verify(mockTOSUserService).resetTOSAcceptance(anyString(), anyString());

	}

	@Test
	public void testTOSResetloadWithError() throws Exception {
		setRoleKenAuthority();
		setKenUserRequestParameters();

		EONError err = new EONError(LOGIN_REQUIRED_USERNAME_PASSWORD);
		ServiceException serviceException = new ServiceException(err);
		when(mockMessageI18NService.getErrorMessage(err)).thenReturn(err.getErrorCode());
		doThrow(
				serviceException).when(
				mockTOSUserService)
				.resetTOSAcceptance(anyString(), anyString());

		ModelAndView mv = adminTosController.TOSreset(request, response);
		Assert.assertNotNull(mv.getModelMap().get("infoMsg"));
		Assert.assertEquals(mv.getModelMap().get("infoMsg"),
				LOGIN_REQUIRED_USERNAME_PASSWORD);
		verify(mockTOSUserService).resetTOSAcceptance(anyString(), anyString());
	}

	@DataProvider(name = "invalidUserNameDataProvider")
	public Object[][] invalidUserNameDataProvider() {
		return new Object[][] { { null }, { "" }, { " " } };
	}

	@Test(dataProvider = "invalidUserNameDataProvider")
	public void testTOSResetUserNameValidation(String userName)
			throws Exception {
		String expectedMessage = "MockedMessage:UsernameBlank";
		String expectedErrorCode = LOGIN_REQUIRED_USERNAME_PASSWORD;

		setRoleKenAuthority();
		setUserRequestParameters(userName);

		when(mockMessageI18NService.getPropertyMessage(expectedErrorCode))
				.thenReturn(expectedMessage);

		ModelAndView mv = adminTosController.TOSreset(request, response);
		Assert.assertEquals(mv.getModelMap().get("infoMsg"), expectedMessage);
		verify(mockMessageI18NService).getPropertyMessage(expectedErrorCode);
		verifyZeroInteractions(mockTOSUserService);

	}

	@Test
	public void testTOSResetUserNameNotValid()
			throws Exception {
		String expectedMessage = "MockedMessage:InvalidUserName";
		String expectedErrorCode = LOGIN_INVALID_USERNAME_PASSWORD;

		setRoleKenAuthority();
		setUserRequestParameters("OtherUser");

		when(mockMessageI18NService.getPropertyMessage(expectedErrorCode))
				.thenReturn(expectedMessage);

		ModelAndView mv = adminTosController.TOSreset(request, response);
		Assert.assertEquals(mv.getModelMap().get("infoMsg"), expectedMessage);
		verify(mockMessageI18NService).getPropertyMessage(expectedErrorCode);
		verifyZeroInteractions(mockTOSUserService);

	}

}
