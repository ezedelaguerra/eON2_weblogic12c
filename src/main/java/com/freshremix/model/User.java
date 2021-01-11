package com.freshremix.model;

import java.util.Date;

public class User {

	private Integer userId;
	private String userName;
	private String password;
	private String name;
	private String shortName;
	private Company company;
	private Role role;
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
	private Date dateLastLogin;
	private Date datePasswordChange;
	private String verticalName;
	private String verticalNameWithMark;
	private String dateLastLoginStr;
	private String enableCalendarHighlight; //1 = Enabled     0 = Disabled [DEFAULT = 0]

	//userPreference
	// Ogie: 12062010 Auto-publish
	// findings: Design problem User object contruction 
	// todo: This should be an object reference to 'EONUserPreference'
	// 
	
	private EONUserPreference preference;
	private String viewUnpublisheAlloc;
	private String viewUnfinalizeBilling;
	private String enableBAPublish;
	private String autoPublishAlloc;
	
	public String getViewUnpublisheAlloc() {
		return viewUnpublisheAlloc;
	}

	public void setViewUnpublisheAlloc(String viewUnpublisheAlloc) {
		this.viewUnpublisheAlloc = viewUnpublisheAlloc;
	}

	public String getViewUnfinalizeBilling() {
		return viewUnfinalizeBilling;
	}

	public void setViewUnfinalizeBilling(String viewUnfinalizeBilling) {
		this.viewUnfinalizeBilling = viewUnfinalizeBilling;
	}

	public String getEnableBAPublish() {
		return enableBAPublish;
	}

	public void setEnableBAPublish(String enableBAPublish) {
		this.enableBAPublish = enableBAPublish;
	}
	
	public String getDateLastLoginStr() {
		return dateLastLoginStr;
	}

	public void setDateLastLoginStr(String dateLastLoginStr) {
		this.dateLastLoginStr = dateLastLoginStr;
	}

	public String getVerticalName() {
		return this.verticalName;
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
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
		
		StringBuffer vertical = new StringBuffer();
		for (int i=0; i<this.shortName.length(); i++) {
			String tmpStr = this.shortName.substring(i, i+1);
			vertical.append(tmpStr);
			vertical.append("<br/>");
		}
		
		this.verticalName = vertical.toString();
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
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
	public String getVerticalNameWithMark() {
		return verticalNameWithMark;
	}
	public void setVerticalNameWithMark(String verticalNameWithMark) {
		this.verticalNameWithMark = verticalNameWithMark;
	}

	public String getAutoPublishAlloc() {
		return autoPublishAlloc;
	}

	public void setAutoPublishAlloc(String autoPublishAlloc) {
		this.autoPublishAlloc = autoPublishAlloc;
	}

	public EONUserPreference getPreference() {
		return preference;
	}

	public void setPreference(EONUserPreference preference) {
		this.preference = preference;
	}

	public String getEnableCalendarHighlight() {
		return enableCalendarHighlight;
	}

	public void setEnableCalendarHighlight(String enableCalendarHighlight) {
		this.enableCalendarHighlight = enableCalendarHighlight;
	}
}
