package com.freshremix.comments;

import java.util.ArrayList;
import java.util.List;

import com.freshremix.constants.CompanyConstants;
import com.freshremix.dao.CompanyInformationDao;
import com.freshremix.model.Company;
import com.freshremix.model.User;

public class UserFetcherHelper {

	private List<Integer> sellerCompanyId;
	private List<Integer> buyerCompanyId;
	private List<Integer> companyId;
	private List<Integer> userCompanyId;
	private User user;
	private CompanyInformationDao companyInfoDaos;
	
	public List<Integer> getSellerCompanyId() {
		return sellerCompanyId;
	}

	public void setSellerCompanyId(List<Integer> sellerCompanyId) {
		this.sellerCompanyId = sellerCompanyId;
	}

	public List<Integer> getBuyerCompanyId() {
		return buyerCompanyId;
	}

	public void setBuyerCompanyId(List<Integer> buyerCompanyId) {
		this.buyerCompanyId = buyerCompanyId;
	}

	public List<Integer> getCompanyId() {
		return companyId;
	}

	public void setCompanyId(List<Integer> companyId) {
		this.companyId = companyId;
	}

	public List<Integer> getUserCompanyId() {
		return userCompanyId;
	}

	public void setUserCompanyId(List<Integer> userCompanyId) {
		this.userCompanyId = userCompanyId;
	}

	private UserFetcherHelper() { }
	
	public UserFetcherHelper(
			User user, 
			List<Integer> companyId, 
			CompanyInformationDao companyInfoDaos) {
		this();
		this.companyId = companyId;
		this.user = user;
		this.sellerCompanyId = new ArrayList<Integer>();
		this.buyerCompanyId = new ArrayList<Integer>();
		this.companyInfoDaos = companyInfoDaos;
		initialize();
	}
	
	private void initialize() {
		for (Integer id : companyId) {
			Company company = companyInfoDaos.getCompanyById(id);
			if (user.getCompany().getCompanyId().equals(id)) {
				userCompanyId = new ArrayList<Integer>();
				userCompanyId.add(id);
			} else if (company.getCompanyType().getCompanyTypeId().equals(CompanyConstants.SELLER_COMPANY)) {
				sellerCompanyId.add(company.getCompanyId());
			} else if (company.getCompanyType().getCompanyTypeId().equals(CompanyConstants.BUYER_COMPANY)) {
				buyerCompanyId.add(company.getCompanyId());
			}
		}
	}
	
}
