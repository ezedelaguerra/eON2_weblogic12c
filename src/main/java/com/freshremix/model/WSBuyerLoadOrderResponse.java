package com.freshremix.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class WSBuyerLoadOrderResponse  {
	
	private ArrayList<WSBuyerSKU> skuOrderList;
	//private ProfitInfo totals;
	//private ProfitInfo grandTotals;
	
	private WSBuyerResponse result;

	public WSBuyerResponse getResult() {
		return result;
	}

	public void setResult(WSBuyerResponse result) {
		this.result = result;
	}
	
	

	

	public ArrayList<WSBuyerSKU> getSkuOrderList() {
		return skuOrderList;
	}
	public void setSkuOrderList(ArrayList<WSBuyerSKU> skuOrderList) {
		this.skuOrderList = skuOrderList;
	}
//	public ProfitInfo getTotals() {
//		return totals;
//	}
//	public void setTotals(ProfitInfo totals) {
//		this.totals = totals;
//	}
//	public ProfitInfo getGrandTotals() {
//		return grandTotals;
//	}
//	public void setGrandTotals(ProfitInfo grandTotals) {
//		this.grandTotals = grandTotals;
//	}
	
}
