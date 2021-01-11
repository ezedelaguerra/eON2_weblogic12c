package com.freshremix.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WSSellerUpdateOrderSheetRequest  extends WSSellerRequest{

	private ArrayList<WSSellerSKUUpdateRequest> sku;


	public ArrayList<WSSellerSKUUpdateRequest> getSku() {
		return sku;
	}

	public void setSku(ArrayList<WSSellerSKUUpdateRequest> sku) {
		this.sku = sku;
	}
	
	
	
}
