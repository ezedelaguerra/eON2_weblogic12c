package com.freshremix.dao.impl;

import com.freshremix.dao.AbstractBaseDao;
import com.freshremix.dao.TermsOfServiceDao;
import com.freshremix.model.TermsOfService;

public class TermsOfServiceDaoImpl extends AbstractBaseDao<TermsOfService, String> implements TermsOfServiceDao{


	public TermsOfServiceDaoImpl() {
		super(TermsOfService.class);

	}

	@Override
	public TermsOfService getLatestTOS() {
		return (TermsOfService) getSqlMapClientTemplate().queryForObject("TermsOfService.getLatestTOS");
	}



}
