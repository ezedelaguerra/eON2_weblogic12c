package com.freshremix.dao;

import com.freshremix.model.TermsOfService;

public interface TermsOfServiceDao extends BaseDao<TermsOfService, String>{
	TermsOfService getLatestTOS();
}
