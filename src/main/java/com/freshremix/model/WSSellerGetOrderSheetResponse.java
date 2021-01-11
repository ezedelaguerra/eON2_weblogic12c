package com.freshremix.model;

import java.util.ArrayList;

public class WSSellerGetOrderSheetResponse {
	private WSSellerResponse result;
	private ArrayList<WSSellerReturnSKU> skuOrderList;
	
	
	public ArrayList<WSSellerReturnSKU> getSkuOrderList() {
		return skuOrderList;
	}

	public void setSkuOrderList(ArrayList<WSSellerReturnSKU> skuOrderList) {
		this.skuOrderList = skuOrderList;
	}

	public WSSellerResponse getResult() {
		return result;
	}

	public void setResult(WSSellerResponse result) {
		this.result = result;
	}
}
