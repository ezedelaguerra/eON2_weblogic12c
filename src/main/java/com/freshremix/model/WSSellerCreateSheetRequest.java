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
public class WSSellerCreateSheetRequest {

	@XmlElement(required = true)
	private String username;
	@XmlElement(required = true)
	private String password;
	@XmlElement(required = true)
	private String orderDate;	

	@XmlElement(required = true)
	private String systemName;
	private ArrayList<WSSellerSKUCreateRequest> sku;

	@XmlTransient
	private User user;

	@XmlTransient
	private Date deliveryDate;
	
	
	
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}


	public ArrayList<WSSellerSKUCreateRequest> getSku() {
		return sku;
	}

	public void setSku(ArrayList<WSSellerSKUCreateRequest> sku) {
		this.sku = sku;
	}
	
	
	
}
