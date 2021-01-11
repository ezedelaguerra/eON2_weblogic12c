package com.freshremix.model;

public class Role {
	
	private Long roleId;
	private String roleName;
	private String sellerFlag;	
	private String sellerAdminFlag;
	private String buyerFlag;
	private String buyerAdminFlag;
	private String adminFlag;
	private String companyTypeFlag;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getSellerFlag() {
		return sellerFlag;
	}
	public void setSellerFlag(String sellerFlag) {
		this.sellerFlag = sellerFlag;
	}
	public String getSellerAdminFlag() {
		return sellerAdminFlag;
	}
	public void setSellerAdminFlag(String sellerAdminFlag) {
		this.sellerAdminFlag = sellerAdminFlag;
	}
	public String getBuyerFlag() {
		return buyerFlag;
	}
	public void setBuyerFlag(String buyerFlag) {
		this.buyerFlag = buyerFlag;
	}
	public String getBuyerAdminFlag() {
		return buyerAdminFlag;
	}
	public void setBuyerAdminFlag(String buyerAdminFlag) {
		this.buyerAdminFlag = buyerAdminFlag;
	}
	public String getAdminFlag() {
		return adminFlag;
	}
	public void setAdminFlag(String adminFlag) {
		this.adminFlag = adminFlag;
	}
	public String getCompanyTypeFlag() {
		return companyTypeFlag;
	}
	public void setCompanyTypeFlag(String companyTypeFlag) {
		this.companyTypeFlag = companyTypeFlag;
	}

}
