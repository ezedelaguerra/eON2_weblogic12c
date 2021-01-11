package com.freshremix.model;

import java.util.Date;
import java.util.List;

public class AdminUsers {
	private Integer userId;
	private String userName;
	private String password;
	private String name;
	private String shortName;
	private String companyId;
	private List<UsersCategory> categoryList;
	private List<Role> rolesList;
	private String categories;
	private Integer roleId;
	private String roleName;
	private String address1;
	private String address2;
	private String address3;
	private String mobileNumber;
	private String telNumber;
	private String faxNumber;
	private String mobileEmail;
	private String pcEmail;
	private String comments;
	private String useBms;
	private Long displayCodeId;
	private Date dateLastLogin = new Date();
	private Date datePasswordChange = new Date();
	private String unfinalizeReceived;
	private String	tosFlag;
	private String enableCalendarHighlight; //1 = Enabled     0 = Disabled [DEFAULT = 0]
	
	public String getTosFlag() {
		return tosFlag;
	}
	public void setTosFlag(String tosFlag) {
		this.tosFlag = tosFlag;
	}
	public List<Role> getRolesList() {
		return rolesList;
	}
	public void setRolesList(List<Role> rolesList) {
		this.rolesList = rolesList;
	}
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public List<UsersCategory> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<UsersCategory> categoryList) {
		this.categoryList = categoryList;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getMobileEmail() {
		return mobileEmail;
	}
	public void setMobileEmail(String mobileEmail) {
		this.mobileEmail = mobileEmail;
	}
	public String getPcEmail() {
		return pcEmail;
	}
	public void setPcEmail(String pcEmail) {
		this.pcEmail = pcEmail;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getUseBms() {
		return useBms;
	}
	public void setUseBms(String useBms) {
		this.useBms = useBms;
	}
	public Long getDisplayCodeId() {
		return displayCodeId;
	}
	public void setDisplayCodeId(Long displayCodeId) {
		this.displayCodeId = displayCodeId;
	}
	public Date getDateLastLogin() {
		return dateLastLogin;
	}
	public void setDateLastLogin(Date dateLastLogin) {
		this.dateLastLogin = dateLastLogin;
	}
	public Date getDatePasswordChange() {
		return datePasswordChange;
	}
	public void setDatePasswordChange(Date datePasswordChange) {
		this.datePasswordChange = datePasswordChange;
	}
	public String getUnfinalizeReceived() {
		return unfinalizeReceived;
	}
	public void setUnfinalizeReceived(String unfinalizeReceived) {
		this.unfinalizeReceived = unfinalizeReceived;
	}
	public String getEnableCalendarHighlight() {
		return enableCalendarHighlight;
	}

	public void setEnableCalendarHighlight(String enableCalendarHighlight) {
		this.enableCalendarHighlight = enableCalendarHighlight;
	}
}
