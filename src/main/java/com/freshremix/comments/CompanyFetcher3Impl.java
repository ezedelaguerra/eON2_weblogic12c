package com.freshremix.comments;

import java.util.List;

import com.freshremix.model.Company;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;

// BUYER
public class CompanyFetcher3Impl implements CompanyFetcher {

	@Override
	public List<Company> getCompanyFetcher(
			User user, DealingPatternService dealingPatternService) {
		return dealingPatternService.
			getSellerCompaniesByBuyerCompanyIds(
				Integer.valueOf(user.getCompany().getCompanyId()));
	}

}
