package com.freshremix.comments;

import java.util.List;

import com.freshremix.model.Company;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;
import com.freshremix.util.DateFormatter;

// SELLER ADMIN
public class CompanyFetcher2Impl implements CompanyFetcher {

	@Override
	public List<Company> getCompanyFetcher(
			User user, DealingPatternService dealingPatternService) {
		
		List<Integer> sellerIdList = 
			dealingPatternService.getSellerIdsOfSellerAdminId(user.getUserId());
		
		return dealingPatternService.
			getBuyerCompaniesByUserSellerIds(
				sellerIdList, 
				DateFormatter.getDateToday(DateFormatter.DATE_FORMAT), 
				"");
	}

}
