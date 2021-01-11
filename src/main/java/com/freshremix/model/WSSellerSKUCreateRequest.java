package com.freshremix.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WSSellerSKUCreateRequest extends WSSellerSKURequest {

	@XmlElement(required=true)
	private String skuExternalID;
	private String finalizeFlag;
	@XmlElement(required = true)
	private String skuCategoryName;
	
	@XmlTransient
	private Category category;
	
	public String getFinalizeFlag() {
		return finalizeFlag;
	}

	public void setFinalizeFlag(String finalizeFlag) {
		this.finalizeFlag = finalizeFlag;
	}

	public String getSkuExternalID() {
		return skuExternalID;
	}

	public void setSkuExternalID(String skuExternalID) {
		this.skuExternalID = skuExternalID;
	}

	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getSkuCategoryName() {
		return skuCategoryName;
	}
	public void setSkuCategoryName(String skuCategoryName) {
		this.skuCategoryName = skuCategoryName;
	}
	
}
