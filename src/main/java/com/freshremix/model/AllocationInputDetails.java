package com.freshremix.model;

public class AllocationInputDetails extends AllocationSKUInformation{

	AllocationBuyerInformation[] allocationBuyerInformation;

	public AllocationBuyerInformation[] getAllocationBuyerInformation() {
		return allocationBuyerInformation;
	}

	public void setAllocationBuyerInformation(
			AllocationBuyerInformation[] allocationBuyerInformation) {
		this.allocationBuyerInformation = allocationBuyerInformation;
	} 
}
