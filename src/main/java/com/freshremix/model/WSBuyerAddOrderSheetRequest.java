package com.freshremix.model;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WSBuyerAddOrderSheetRequest {
	@XmlElement(required = true)
	private String username;
	@XmlElement(required = true)
	private String password;
	@XmlTransient
	private User loginUser;
	@XmlElement(required = true)
	private Date orderDate;
	private String skuCategoryName;
	@XmlTransient
	private Category category;
	private ArrayList<WSBuyerSKUAdd> addSkuList;
	private ArrayList<WSBuyerSKUUpdate> updateSKUList;
	
	
	
	public User getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
	public String getSkuCategoryName() {
		return skuCategoryName;
	}
	public void setSkuCategoryName(String skuCategoryName) {
		this.skuCategoryName = skuCategoryName;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<WSBuyerSKUAdd> getAddSkuList() {
		return addSkuList;
	}
	public void setAddSkuList(ArrayList<WSBuyerSKUAdd> addSkuList) {
		this.addSkuList = addSkuList;
	}
	public ArrayList<WSBuyerSKUUpdate> getUpdateSKUList() {
		return updateSKUList;
	}
	public void setUpdateSKUList(ArrayList<WSBuyerSKUUpdate> updateSKUList) {
		this.updateSKUList = updateSKUList;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	

}
