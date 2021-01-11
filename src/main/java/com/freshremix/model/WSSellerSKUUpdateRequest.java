package com.freshremix.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class WSSellerSKUUpdateRequest  extends WSSellerSKURequest{

	
	public String skuId;
	@XmlTransient
	public Integer SKUID;
	
	@XmlElement(required=false)
	private String skuExternalID;

	public String getSkuExternalID() {
		return skuExternalID;
	}

	public void setSkuExternalID(String skuExternalID) {
		this.skuExternalID = skuExternalID;
	}

	public Integer getSKUID() {
		return SKUID;
	}

	public void setSKUID(Integer sKUID) {
		SKUID = sKUID;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
}
