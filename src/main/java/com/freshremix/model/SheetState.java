package com.freshremix.model;

import com.freshremix.constants.SheetTypeConstants;


public abstract class SheetState implements SheetAction {

	public static SheetState createSheet (Integer sheetTypeId) {
		
		SheetState sheet = null;
		if (sheetTypeId.equals(SheetTypeConstants.SELLER_ORDER_SHEET)) {
			sheet = new BuyerOrderState();
		} else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ORDER_SHEET)) {
			sheet = new SellerOrderState();
		} else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ALLOCATION_SHEET)) {
			sheet = new ReceiveState();
		} else if (sheetTypeId.equals(SheetTypeConstants.BUYER_RECEIVED_SHEET) || sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_RECEIVED_SHEET)) {
			sheet = new AllocationState();
		}
		
		return sheet;
		
	}
	
	public static SheetState createAdminSheet (Integer sheetTypeId) {
		
		SheetState sheet = null;
		if (sheetTypeId.equals(SheetTypeConstants.SELLER_ORDER_SHEET)) {
			sheet = new SellerOrderState();
		} else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ORDER_SHEET)) {
			sheet = new BuyerOrderState();
		} else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ALLOCATION_SHEET) || sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ALLOCATION_SHEET)) {
			sheet = new AllocationState();
		} else if (sheetTypeId.equals(SheetTypeConstants.BUYER_RECEIVED_SHEET) || sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_RECEIVED_SHEET)) {
			sheet = new ReceiveState();
		}
		
		return sheet;
		
	}

}
