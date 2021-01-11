package com.freshremix.comments;

import java.util.List;

import com.freshremix.model.Company;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;

// SELLER
public class CompanyFetcher1Impl implements CompanyFetcher {

	@Override
	public List<Company> getCompanyFetcher(
			User user, DealingPatternService dealingPatternService) {
		return dealingPatternService.
			getBuyerCompaniesBySellerCompanyIds(
				Integer.valueOf(user.getCompany().getCompanyId()));
	}

}
