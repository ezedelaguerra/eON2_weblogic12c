package com.freshremix.util;

import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.constants.RoleConstants;
import com.freshremix.model.Role;
import com.freshremix.model.User;

public class RolesUtilTest {

	@Test
	public void testRoleBuyer(){
		User userBuyer = createUser(RoleConstants.ROLE_BUYER);
		boolean isUserRoleBuyer = RolesUtil.isUserRoleBuyer(userBuyer);
		Assert.assertTrue(isUserRoleBuyer);

		User userBuyerAdmin = createUser(RoleConstants.ROLE_BUYER_ADMIN);
		isUserRoleBuyer = RolesUtil.isUserRoleBuyer(userBuyerAdmin);
		Assert.assertFalse(isUserRoleBuyer);
	}

	
	@DataProvider (name="invalidUserDP")
	public Object[][] invalidUserDP(){
		User userWithNullRoleId = new User();
		userWithNullRoleId.setRole(new Role());
		return new Object[][] {
				{null},
				{new User()},
				{userWithNullRoleId}
		};
	}
	
	@Test (dataProvider = "invalidUserDP", expectedExceptions = IllegalArgumentException.class)
	public void testRoleBuyerInvalidUser(User user){
		RolesUtil.isUserRoleBuyer(user);
	}
	
	@Test
	public void testRoleBuyerAdmin(){
		User userBuyerAdmin = createUser(RoleConstants.ROLE_BUYER_ADMIN);
		boolean isUserRoleBuyerAdmin = RolesUtil.isUserRoleBuyerAdmin(userBuyerAdmin);
		Assert.assertTrue(isUserRoleBuyerAdmin);

		User userBuyer = createUser(RoleConstants.ROLE_BUYER);
		isUserRoleBuyerAdmin = RolesUtil.isUserRoleBuyerAdmin(userBuyer);
		Assert.assertFalse(isUserRoleBuyerAdmin);
	}
	
	@Test (dataProvider = "invalidUserDP", expectedExceptions = IllegalArgumentException.class)
	public void testRoleBuyerAdminInvalidUser(User user){
		RolesUtil.isUserRoleBuyerAdmin(user);
	}
	
	private User createUser(Long roleId) {
		Role role = new Role();
		role.setRoleId(roleId);
		User user = new User();
		user.setRole(role);
		return user;
	}
	
}
