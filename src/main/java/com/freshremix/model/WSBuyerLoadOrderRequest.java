package com.freshremix.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WSBuyerLoadOrderRequest {

	@XmlElement(required = true)
	private String username;

	@XmlElement(required = true)
	private String password;

	@XmlElement(required = true)
	private Date orderDate;
	
	@XmlElement(required = true)
	private List<Integer> sellerIds;

	private List<Integer> buyerIds;
	
	@XmlElement(defaultValue="120")
	private Integer pageSize;

	@XmlElement(defaultValue="1")
	private Integer pageNum;
	
	private String category;
	

	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public List<Integer> getBuyerIds() {
		return buyerIds;
	}

	public void setBuyerIds(List<Integer> buyerIds) {
		this.buyerIds = buyerIds;
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
	public List<Integer> getSellerIds() {
		return sellerIds;
	}
	public void setSellerIds(List<Integer> sellerIds) {
		this.sellerIds = sellerIds;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

}
