package com.freshremix.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WSSellerDeleteSKU {
	@XmlElement(required=true)
	private String sellerId;
	@XmlElement(required=true)
	private ArrayList<String> buyerIds;
	private String skuId;
	private String externalSKUId;
	@XmlTransient
	private User seller;
	@XmlTransient
	private List<User> buyerList;
	@XmlTransient
	private Integer SKUID;
	@XmlTransient
	private Map<CompositeKey<Integer>, Order> orderMap;

	
	

	public Map<CompositeKey<Integer>, Order> getOrderMap() {
		return orderMap;
	}
	public void setOrderMap(Map<CompositeKey<Integer>, Order> orderMap) {
		this.orderMap = orderMap;
	}
	public ArrayList<String> getBuyerIds() {
		return buyerIds;
	}
	public void setBuyerIds(ArrayList<String> buyerIds) {
		this.buyerIds = buyerIds;
	}

	
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getExternalSKUId() {
		return externalSKUId;
	}
	public void setExternalSKUId(String externalSKUId) {
		this.externalSKUId = externalSKUId;
	}
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
	
	public List<User> getBuyerList() {
		return buyerList;
	}
	public void setBuyerList(List<User> buyerList) {
		this.buyerList = buyerList;
	}
	public Integer getSKUID() {
		return SKUID;
	}
	public void setSKUID(Integer sKUID) {
		SKUID = sKUID;
	}
	
	
	

}
