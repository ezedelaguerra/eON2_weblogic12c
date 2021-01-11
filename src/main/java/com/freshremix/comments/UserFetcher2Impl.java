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

//SELLER ADMIN
public class UserFetcher2Impl implements UserFetcher {

	@Override
	public List<User> getUser(User user, UserFetcherHelper ufh,
			DealingPatternService dealingPatternService,
			UserDao usersInfoDaos) {
		
		List<User> list = new ArrayList<User>();
		
		List<Integer> sellerIdList = new ArrayList<Integer>();
		
		if (!CollectionUtil.isNullOrEmpty(ufh.getUserCompanyId())) {
			
			// (1) get all seller admin
			list.addAll(usersInfoDaos.getSellerAdminRoleByCompanyId(ufh.getUserCompanyId()));
			
			// (2) get all sellers
			// ENHANCEMENT START 20120725: Lele - Redmine 131
//			List<User> sellerList = 
//				dealingPatternService.getMembersByAdminId(
//					user.getUserId(), 
//					DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER, 
//					DateFormatter.getDateToday(DateFormatter.DATE_FORMAT), 
//					"");
			List<User> sellerList = 
				dealingPatternService.getMembersByAdminId2(
					user.getUserId(), 
					DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER, 
					DateFormatter.getDateToday(DateFormatter.DATE_FORMAT), 
					"");
			// ENHANCEMENT END 20120725:
			list.addAll(sellerList);
			
			for (User _user : sellerList) {
				sellerIdList.add(_user.getUserId());
			}
		}
		
		if (!CollectionUtil.isNullOrEmpty(ufh.getBuyerCompanyId())) {
			
			if (sellerIdList.size() == 0) {
				// (2) get all sellers
				List<User> sellerList = 
					dealingPatternService.getMembersByAdminId(
						user.getUserId(), 
						DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER, 
						DateFormatter.getDateToday(DateFormatter.DATE_FORMAT), 
						"");
				//list.addAll(sellerList);
				
				for (User _user : sellerList) {
					sellerIdList.add(_user.getUserId());
				}
			}
			
			if (sellerIdList.size() > 0) {
			
				// (3) get all buyers that has dealing pattern in (1)
				// ENHANCEMENT START 20120725: Lele - Redmine 131
//				List<User> buyerList = dealingPatternService.getUserBuyersByUserSellersAndBuyerCompanyIds(
//						sellerIdList, 
//						ufh.getBuyerCompanyId(), 
//						DateFormatter.getDateToday(DateFormatter.DATE_FORMAT), 
//						"");
				List<User> buyerList = dealingPatternService.getUserBuyersByUserSellersAndBuyerCompanyIds2(
						sellerIdList, 
						ufh.getBuyerCompanyId(), 
						DateFormatter.getDateToday(DateFormatter.DATE_FORMAT), 
						"");
				// ENHANCEMENT END 20120725:
				list.addAll(buyerList);
				
				// (4) get all buyer admins that has dealing pattern in (2)
				for (User _user : buyerList) {
					List<Integer> adminId = 
						dealingPatternService.getAdminUsersOfMember(_user.getUserId(), DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER);
					
					if (adminId != null && adminId.size() > 0)
						list.addAll(usersInfoDaos.getUserById(adminId));
					
				}
			}
		}
		
		return list;
	}

}
