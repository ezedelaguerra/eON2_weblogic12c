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
 * Feb 23, 2010		nvelasquez		
 */
package com.freshremix.util;

/**
 * @author nvelasquez
 *
 */
import java.util.ArrayList;
import java.util.List;

import com.freshremix.model.User;
import com.freshremix.service.UsersInformationService;

public class UserUtil {
	
	private static UsersInformationService userInfoService;
	
	public void setUserInfoService(UsersInformationService userInfoService) {
		UserUtil.userInfoService = userInfoService;
	}

	public static List<User> toUserObjs(List<Integer> userIdList) {
		
		List<User> userObjs = new ArrayList<User>();
		for (Integer userId : userIdList) {
			User userObj = userInfoService.getUserById(userId);
			userObjs.add(userObj);
		}
		
		return userObjs;
	}
	
	public static List<User> toUserObjs(List<Integer> userIdList, Integer companyId) {
		List<User> userListResult = userInfoService.getUserById(userIdList);
		List<User> userObjs = new ArrayList<User>();
		for (User user : userListResult) {
			if (user!= null && user.getCompany().getCompanyId().equals(companyId)) {
				userObjs.add(user);
			}
		}
		
		return userObjs;
	}

	public static List<User> toUserObjs(List<Integer> userIdList, Integer companyId, List<Integer> sortedIdList) {
		List<User> userListResult = userInfoService.getUserById(userIdList);
		List<User> userObjs = new ArrayList<User>();
		for (Integer sortedId : sortedIdList) {
			for (User user : userListResult) {
				if (sortedId == null || !sortedId.equals(user.getUserId())) {
					continue;
				}
				if (user!= null && user.getCompany().getCompanyId().equals(companyId)) {
					userObjs.add(user);
				}
			}
		} 
		
		return userObjs;
	}

}
