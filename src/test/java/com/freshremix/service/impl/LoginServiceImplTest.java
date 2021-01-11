package com.freshremix.service.impl;

import static org.mockito.Mockito.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.dao.LoginDao;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.Role;
import com.freshremix.model.User;

public class LoginServiceImplTest {
	
	private LoginServiceImpl loginService;
	private LoginDao mockLoginDao = mock(LoginDao.class);
	
	@BeforeMethod
	public void setup()
	{
		loginService = new LoginServiceImpl();
		loginService.setLoginDao(mockLoginDao);
	}
	
	private User createUser()
	{
		Role role = new Role();
		role.setRoleName("Ken");
		role.setRoleId(10l);

		User user = new User();
		user.setUserName("Ken");
		user.setRole(role);
		return user;
		
	}
	
	@DataProvider(name = "blankStrings")
	private Object[][] createblankStrings() {

		

		return new Object[][] { { null }, { "" },
				{ " " }};
	}
	
	@Test
	public void testValidateAdminUserHappyFlow() throws ServiceException{

		when(mockLoginDao.getUserByUsernameAndPassword(anyMap())).thenReturn(createUser());
		loginService.validateAdminUser("Ken", "1");
		verify(mockLoginDao).getUserByUsernameAndPassword(anyMap());
	}
	@Test(expectedExceptions=ServiceException.class, expectedExceptionsMessageRegExp="ServiceException code:login.invalid.username.password")
	public void testValidateAdminUserNoUser()throws ServiceException{

		when(mockLoginDao.getUserByUsernameAndPassword(anyMap())).thenReturn(null);
		loginService.validateAdminUser("sss", "1");
		verify(mockLoginDao).getUserByUsernameAndPassword(anyMap());
	}
	
	@Test(expectedExceptions=ServiceException.class, expectedExceptionsMessageRegExp="ServiceException code:login.invalid.username.password")
	public void testValidateAdminUserNotAdmin()throws ServiceException{
		User user = createUser();
		user.getRole().setRoleId(2l);
		when(mockLoginDao.getUserByUsernameAndPassword(anyMap())).thenReturn(user);
		loginService.validateAdminUser("s_ca01", "1");
		verify(mockLoginDao).getUserByUsernameAndPassword(anyMap());
	}
	
	@Test(dataProvider="blankStrings", expectedExceptions=ServiceException.class, expectedExceptionsMessageRegExp="ServiceException code:login.required.username.password")
	public void testValidateAdminUserMissingUsername(String username)throws ServiceException{
		loginService.validateAdminUser(username, "1");
		verifyZeroInteractions(mockLoginDao);
	}
	
	@Test(dataProvider="blankStrings", expectedExceptions=ServiceException.class, expectedExceptionsMessageRegExp="ServiceException code:login.required.username.password")
	public void testValidateAdminUserMissingPassword(String password)throws ServiceException{
		loginService.validateAdminUser(password, "1");
		verifyZeroInteractions(mockLoginDao);
	}
}
