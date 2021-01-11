package com.freshremix.common.test;

import com.freshremix.model.Company;
import com.freshremix.model.Role;
import com.freshremix.model.User;

public class UserTestUtil {

	
	public static User createBuyerAdminUser(String userName, Integer userId){
		return createUser(userName, userId, 4);
	}
	
	public static User createBuyerUser(String userName, Integer userId){
		return createUser(userName, userId, 3);
	}
	public static User createSellerUser(String userName, Integer userId) {
		return createUser(userName, userId, 1);
	}

	public static User createSellerAdminUser(String userName, Integer userId) {
		return createUser(userName, userId, 2);

	}
	public static User createUser(String userName, Integer userId, Integer roleId) {
		
		Role role = new Role();
		role.setRoleId(new Long(roleId));
		role.setBuyerAdminFlag("1");
		User user = new User();
		user.setUserName(userName);
		Company company = new Company();
		company.setCompanyId(2);
		company.setCompanyName("FRASC_BA");
		user.setCompany(company);
		user.setUserId(userId);
		user.setRole(role);
		
		return user;
	}
}
