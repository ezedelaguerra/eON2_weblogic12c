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
 * date		name		version		changes
 * ------------------------------------------------------------------------------
 * 20120725	gilwen		v11			Redmine 131 - Change of display in address bar of Comments
 */

package com.freshremix.comments;

import java.util.ArrayList;
import java.util.List;

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.dao.UserDao;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;
import com.freshremix.util.CollectionUtil;
import com.freshremix.util.DateFormatter;

//SELLER
public class UserFetcher1Impl implements UserFetcher {

	@Override
	public List<User> getUser(User user, UserFetcherHelper ufh,
			DealingPatternService dealingPatternService,
			UserDao usersInfoDaos) {
		
		List<User> list = new ArrayList<User>();
		
		if (!CollectionUtil.isNullOrEmpty(ufh.getUserCompanyId())) {
			// list.addAll(usersInfoDaos.getUserRoleOnlyByCompanyId(ufh.getUserCompanyId()));
			list.addAll(usersInfoDaos.getUsersByCompanyId(ufh.getUserCompanyId()));
		}
		
		if (!CollectionUtil.isNullOrEmpty(ufh.getBuyerCompanyId())) {
			// users who has dealing pattern with the users
			// ENHANCEMENT START 20120725: Lele - Redmine 131
//			List<User> buyerList = dealingPatternService.getUserBuyersByBuyerCompanyIds(
//					user.getUserId(), 
//					ufh.getBuyerCompanyId(), 
//					DateFormatter.getDateToday(DateFormatter.DATE_FORMAT), 
//					"");
			List<User> buyerList = dealingPatternService.getUserBuyersByBuyerCompanyIds2(
					user.getUserId(), 
					ufh.getBuyerCompanyId(), 
					DateFormatter.getDateToday(DateFormatter.DATE_FORMAT), 
					"");
			// ENHANCEMENT END 20120725:
			list.addAll(buyerList);
			
			// user admin who has dealing pattern with users
			for (User _user : buyerList) {
				List<Integer> adminId = 
					dealingPatternService.getAdminUsersOfMember(_user.getUserId(), DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER);
				
				if (adminId != null && adminId.size() > 0)
					list.addAll(usersInfoDaos.getUserById(adminId));
				
			}
		}
		
		return list;
	}

}
