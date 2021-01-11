package com.freshremix.comments;

import java.util.List;

import com.freshremix.model.Company;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;

public interface CompanyFetcher {

	List<Company> getCompanyFetcher(User user, DealingPatternService dealingPatternService);
	
}
