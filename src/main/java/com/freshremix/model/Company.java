package com.freshremix.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Company implements Serializable {

	private Integer companyId;
	private String companyName;
	private String shortName;
	private CompanyType companyType;
	private String contactPerson;
	private String soxFlag;
	private String address1;
	private String address2;
	private String address3;
	private String telNumber;
	private String emailAdd;
	private String faxNumber;
	private String comments;
	private String verticalName;
	
	public String getVerticalName() {
		return this.verticalName;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getSoxFlag() {
		return soxFlag;
	}
	public void setSoxFlag(String soxFlag) {
		this.soxFlag = soxFlag;
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
	public String getAddress() {
		return address3;
	}
	public void setAddress(String address) {
		this.address3 = address;
	}
	public String getEmailAdd() {
		return emailAdd;
	}
	public void setEmailAdd(String emailAdd) {
		this.emailAdd = emailAdd;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

	public CompanyType getCompanyType() {
		return companyType;
	}

	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
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
	
}
