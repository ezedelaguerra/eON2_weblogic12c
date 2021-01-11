package com.freshremix.model;

import java.util.Date;

public class LoginInquiry {
	
	private Integer inquiryId;
	private String inquireEon;
	private String inquireNsystem;
	private String inquireOthers;
	private String applyDetails;
	private String docsDetails;
	private String explainDetails;
	private String otherDetails;
	private String surname;
	private String firstname;
	private String furiganaSurname;
	private String furiganaFirstname;
	private String companyName;
	private String storeName;
	private String deptName;
	private Integer zipcode;
	private String address;
	private Long contactNo;
	private Long mobileNo;
	private String email;
	private Date dateCreated = new Date();
	
	/**
	 * TODO Returns the dateCreated. 
	 * @return dateCreated
	 */
	
	public Date getDateCreated() {
		return dateCreated;
	}
	/**
	 * TODO Sets the dateCreated. 
	 * @param dateCreated
	 */
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Integer getInquiryId() {
		return inquiryId;
	}
	public void setInquiryId(Integer inquiryId) {
		this.inquiryId = inquiryId;
	}
	public String getInquireEon() {
		return inquireEon;
	}
	public void setInquireEon(String inquireEon) {
		this.inquireEon = inquireEon;
	}
	public String getInquireNsystem() {
		return inquireNsystem;
	}
	public void setInquireNsystem(String inquireNsystem) {
		this.inquireNsystem = inquireNsystem;
	}
	public String getInquireOthers() {
		return inquireOthers;
	}
	public void setInquireOthers(String inquireOthers) {
		this.inquireOthers = inquireOthers;
	}
	public String getApplyDetails() {
		return applyDetails;
	}
	public void setApplyDetails(String applyDetails) {
		this.applyDetails = applyDetails;
	}
	public String getDocsDetails() {
		return docsDetails;
	}
	public void setDocsDetails(String docsDetails) {
		this.docsDetails = docsDetails;
	}
	public String getExplainDetails() {
		return explainDetails;
	}
	public void setExplainDetails(String explainDetails) {
		this.explainDetails = explainDetails;
	}
	public String getOtherDetails() {
		return otherDetails;
	}
	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getFuriganaSurname() {
		return furiganaSurname;
	}
	public void setFuriganaSurname(String furiganaSurname) {
		this.furiganaSurname = furiganaSurname;
	}
	public String getFuriganaFirstname() {
		return furiganaFirstname;
	}
	public void setFuriganaFirstname(String furiganaFirstname) {
		this.furiganaFirstname = furiganaFirstname;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Integer getZipcode() {
		return zipcode;
	}
	public void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getContactNo() {
		return contactNo;
	}
	public void setContactNo(Long contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String toString (){
		String nsystem = inquireNsystem;
		String inqOthers = inquireOthers;
		String appDtl = applyDetails;
		String docDtl = docsDetails;
		String expDtl = explainDetails;
		String dtlOthers = otherDetails;
		String sname = surname;
		String fname = firstname;
		String furSname = furiganaSurname;
		String furFname = furiganaFirstname;
		String compName = companyName;
		String strName = storeName;
		String depName=deptName;
		String code = zipcode.toString();
		String add = address;
		String tel = contactNo.toString();
		String mail = email;
		String date = dateCreated.toString();
		
		return nsystem+" "+inqOthers+" "+appDtl+" "+docDtl+" "+expDtl+" "+dtlOthers+" "+sname+" "+fname+" "+
			furSname+" "+furFname+" "+compName+" "+strName+" "+depName+" "+code+" "+add+" "+tel+" "+mail+" "+date;
	}
	
}
