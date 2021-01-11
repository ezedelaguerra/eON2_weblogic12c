package com.freshremix.webapp.controller.admin;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.model.Role;
import com.freshremix.model.User;
import com.freshremix.service.LoginService;
import com.freshremix.service.MessageI18NService;

public class ProxyLoginControllerTest {

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private final String URL = "/eON/ProxyLogin.json";
	
	@Mock
	private LoginService mockLoginService;
	@Mock
	private MessageI18NService mockMessageI18NService;
	
	private ProxyLoginController controller;
	@BeforeMethod	
	public void init() {
		MockitoAnnotations.initMocks(this);

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		controller = new ProxyLoginController();
		
	}
	
	@DataProvider(name = "userList")
	private Object[][] createUserList() {
		return new Object[][] { { "s_ca01", 1 }, { "sa_ca01", 2 },
				{ "b_ca02", 3 }, { "ba_ca01", 4 } };
	}
	@DataProvider(name = "nonUserList")
	private Object[][] createNonUserList() {
		return new Object[][] { { "errUser"}, { ""},
				{ null} };
	}
	
	
	
	private User createUser(String username, int roleId )
	{
		Role role = new Role();
		role.setRoleId(new Long(roleId));

		User user = new User();
		String userName = username;
		user.setUserName(userName);
		user.setPassword("1");
		user.setRole(role);

		return user;
		
	}
	
	@Test(dataProvider="userList")
	public void proxyLoginTestHappyFlow(String username, int roleId) throws Exception
	{	
		request.addParameter("username", username);
		request.setRequestURI(URL);
		request.setSession(new MockHttpSession());
		
		when(mockLoginService.getUserByUsername(username)).thenReturn(createUser(username, roleId));
		controller.setLoginService(mockLoginService);
		ModelAndView mv = controller.handleRequest(request, response);
		
		String user = (String) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Assert.assertEquals(user, username);
		Assert.assertNull(mv.getModelMap().get("infoMsg"));
		verify(mockLoginService).getUserByUsername(username);
		
		
	}
	
	@Test(dataProvider="nonUserList")
	public void proxyLoginUserDoesNotExist(String username) throws Exception
	{
		request.addParameter("username", username);
		request.setRequestURI(URL);
		
		when(mockLoginService.getUserByUsername(username)).thenReturn(null);
		when(mockMessageI18NService.getPropertyMessage("proxy.username.notexist")).thenReturn("User does not exist.");
		controller.setLoginService(mockLoginService);
		controller.setMessageI18NService(mockMessageI18NService);
		ModelAndView mv = controller.handleRequest(request, response);

		Assert.assertEquals(mv.getModelMap().get("infoMsg"), "User does not exist.");
		Assert.assertNotNull(mv.getModelMap().get("infoMsg"));
		
	}
	@Test
	public void proxyLoginUserisAdmin() throws Exception
	{
		String username = "Ken";
		int roleId = 10;
		request.addParameter("username", username);
		request.setRequestURI(URL);
		
		when(mockLoginService.getUserByUsername(username)).thenReturn(createUser(username, roleId));
		when(mockMessageI18NService.getPropertyMessage("proxy.admin.error")).thenReturn("Admin Username is not allowed for proxy login.");
		controller.setLoginService(mockLoginService);
		controller.setMessageI18NService(mockMessageI18NService);
		ModelAndView mv = controller.handleRequest(request, response);

		Assert.assertEquals(mv.getModelMap().get("infoMsg"), "Admin Username is not allowed for proxy login.");
		Assert.assertNotNull(mv.getModelMap().get("infoMsg"));
		
	}

}
