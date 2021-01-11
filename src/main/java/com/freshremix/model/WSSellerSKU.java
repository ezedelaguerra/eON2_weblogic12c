package com.freshremix.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

/**
 * @author melissa
 *
 */
public class WSSellerSKU {

	private SKU sku;
	private User loginUser;
	private String orderDate;
	private User seller;
	private List<WSSellerBuyerDetails> details;
	private Boolean finalizeFlag;
	private SKU skuDB;
	private boolean hasMetaInfoChanged;
	
	




	public boolean getHasMetaInfoChanged() {
		return hasMetaInfoChanged;
	}


	public void setHasMetaInfoChanged(boolean hasMetaInfoChanged) {
		this.hasMetaInfoChanged = hasMetaInfoChanged;
	}


	public SKU getSkuDB() {
		return skuDB;
	}


	public void setSkuDB(SKU skuDB) {
		this.skuDB = skuDB;
	}





	public WSSellerSKU(){
		sku = new SKU();

	}


	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}



	public SKU getSku() {
		return sku;
	}
	public void setSku(SKU sku) {
		this.sku = sku;
	}
	

	public List<WSSellerBuyerDetails> getDetails() {
		return details;
	}

	public void setDetails(List<WSSellerBuyerDetails> details) {
		this.details = details;
	}

	public Boolean getFinalizeFlag() {
		return finalizeFlag;
	}
	public void setFinalizeFlag(Boolean finalizeFlag) {
		this.finalizeFlag = finalizeFlag;
	}
	
	
	
	
}
