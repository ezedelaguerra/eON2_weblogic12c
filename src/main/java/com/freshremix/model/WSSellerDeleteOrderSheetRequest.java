package com.freshremix.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WSSellerDeleteOrderSheetRequest extends WSSellerRequest{
	@XmlElement(required=true)
	private ArrayList<WSSellerDeleteSKU> sku;

	public ArrayList<WSSellerDeleteSKU> getSku() {
		return sku;
	}

	public void setSku(ArrayList<WSSellerDeleteSKU> sku) {
		this.sku = sku;
	}
	
	
	
}
