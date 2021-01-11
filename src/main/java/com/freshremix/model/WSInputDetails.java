package com.freshremix.model;

import java.util.Objects;

/**
 * wsBuyerInformation --> see WSBuyerInformation class
 * @author Administrator
 *
 */
public class WSInputDetails extends SKUInformationWS {

	private WSBuyerInformation[] wsBuyerInformation;

	public WSBuyerInformation[] getWsBuyerInformation() {
		return wsBuyerInformation;
	}

	public void setWsBuyerInformation(WSBuyerInformation[] wsBuyerInformation) {
		this.wsBuyerInformation = wsBuyerInformation;
	}

	@Override
	public boolean equals(Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		WSInputDetails ws = (WSInputDetails) o;
		return Objects.equals(this.getSkuName(), ws.getSkuName()) &&
				Objects.equals(this.getPackageQuantity(), ws.getPackageQuantity()) &&
				Objects.equals(this.getPriceWithoutTax(), ws.getPriceWithoutTax()) &&
				Objects.equals(this.getSkuGroupName(), ws.getSkuGroupName()) &&
				Objects.equals(this.getMarket(), ws.getMarket()) &&
				Objects.equals(this.getLocation(), ws.getLocation()) &&
				Objects.equals(this.getClass1(), ws.getClass1()) &&
				Objects.equals(this.getClass2(), ws.getClass2());
	}
	
}
