package com.freshremix.comments;

import java.util.ArrayList;
import java.util.List;

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.model.Company;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;
import com.freshremix.util.DateFormatter;

// BUYER ADMIN
public class CompanyFetcher4Impl implements CompanyFetcher {

	@Override
	public List<Company> getCompanyFetcher(
			User user, DealingPatternService dealingPatternService) {
		
		List<User> buyerUser = 
			dealingPatternService.getMembersByAdminId(
				user.getUserId(), 
				DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER, 
				DateFormatter.getDateToday(DateFormatter.DATE_FORMAT), 
				"");
		
		List<Integer> buyerIdList = new ArrayList<Integer>();
		for (User _user : buyerUser) {
			buyerIdList.add(_user.getUserId());
		}
			
		return dealingPatternService.
			getSellerCompaniesByUserBuyerIds(
				buyerIdList, 
				DateFormatter.getDateToday(DateFormatter.DATE_FORMAT),
				"");
	}

}
