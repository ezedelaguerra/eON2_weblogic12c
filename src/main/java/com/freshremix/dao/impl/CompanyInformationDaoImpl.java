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
package com.freshremix.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.CompanyInformationDao;
import com.freshremix.model.AdminCompanyInformation;
import com.freshremix.model.Company;

public class CompanyInformationDaoImpl extends SqlMapClientDaoSupport implements CompanyInformationDao{

	@Override
	public AdminCompanyInformation getCompanyInformationByCompanyId(Map<String, String> param) {

		return (AdminCompanyInformation) getSqlMapClientTemplate().queryForObject("CompanyInfo.getCompanyInformationByCompanyId", param);
	}

	@Override
	public Integer insertNewCompany(Map<String, String> param) {
		
		Integer intRet = (Integer) getSqlMapClientTemplate().queryForObject("CompanyInfo.cntCompanynameShortnameForInsert", param);
		if (intRet > 0) return null; 
		try{
			intRet = (Integer) getSqlMapClientTemplate().insert("CompanyInfo.insertNewCompany", param);
			return intRet;
		}catch(DataIntegrityViolationException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminCompanyInformation> getAllCompanyName() {
		return getSqlMapClientTemplate().queryForList("CompanyInfo.getAllCompanyName");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminCompanyInformation> getCompanyNameByWildCard(
			String companyName) {
		
		return getSqlMapClientTemplate().queryForList("CompanyInfo.getCompanyNameByWildCard", companyName.toLowerCase());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminCompanyInformation> getAllCompanyNameByDealingPattern(String companyType) {
		return getSqlMapClientTemplate().queryForList("CompanyInfo.getAllCompanyNameByDealingPattern", companyType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminCompanyInformation> getCompanySelectedDealingPattern(String companyId,String roleType) {
		
		List<AdminCompanyInformation> returnList = new ArrayList<AdminCompanyInformation>();
		
		if(!roleType.equalsIgnoreCase("buyer")){
			returnList = getSqlMapClientTemplate().queryForList
					("CompanyInfo.getCompanySelectedDealingPattern", companyId);
		}else{
			returnList = getSqlMapClientTemplate().queryForList
			("CompanyInfo.getBuyerCompanySelectedDealingPattern", companyId);
		}
		
		return returnList;
	}

	@Override
	public Integer updateCompany(Map<String, String> param) {
		
		Integer intRet = (Integer) getSqlMapClientTemplate().queryForObject("CompanyInfo.cntCompanynameShortnameForUpdate", param);
		if (intRet > 0) return null; 
		try{
			intRet = (Integer) getSqlMapClientTemplate().update("CompanyInfo.updateCompany", param);
			return intRet;
		}catch(DataIntegrityViolationException e){
			return null;
		}
	}

	@Override
	public Integer checkCompanyIfExists(String companyName) {
		Integer intRet = (Integer) getSqlMapClientTemplate().queryForObject("CompanyInfo.checkCompanyIfExist", companyName);
		return intRet;
	}

	@Override
	public Company getCompanyById(Map<String, Object> param) {
		return (Company) getSqlMapClientTemplate().queryForObject("Company.getCompanyById", param);
	}
	
	@Override
	public Company getCompanyById(Integer companyId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		return (Company) getSqlMapClientTemplate().queryForObject("Company.getCompanyById", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CompanyInformationDao#getBuyerCompanyById(java.util.Map)
	 */
	@Override
	public Company getBuyerCompanyById(Map<String, Object> param) {
		return (Company) getSqlMapClientTemplate().queryForObject("Company.getBuyerCompanyById", param);
	}
	
	// ENHANCEMENT START 20120725: Lele - Redmine 131
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getCompanyList (List<Integer> companyId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		
		return getSqlMapClientTemplate().queryForList("Company.getCompanyList", param);
	}
	// ENHANCEMENT END 20120725:
}
