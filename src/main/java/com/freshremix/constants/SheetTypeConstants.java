package com.freshremix.constants;

import java.util.Arrays;
import java.util.List;

public class SheetTypeConstants {

	/** Sheet Type Code for Buyer Order Sheet */
	public static final Integer BUYER_ORDER_SHEET = new Integer(10000); 

    /** Sheet Type Code for Seller Order Sheet */
	public static final Integer SELLER_ORDER_SHEET = new Integer(10001); 

    /** Sheet Type Code for Seller Product Sheet */
	public static final Integer SELLER_PRODUCT_SHEET = new Integer(10002);

    /** Sheet Type Code for Seller Allocations Sheet */
	public static final Integer SELLER_ALLOCATION_SHEET = new Integer(10003);

    /** Sheet Type Code for Buyer Received Sheet */
	public static final Integer BUYER_RECEIVED_SHEET = new Integer(10004);

    /** Sheet Type Code for Seller Billing Sheet */
	public static final Integer SELLER_BILLING_SHEET = new Integer(10005);

    /** Sheet Type Code for Buyer Billing Sheet */
	public static final Integer BUYER_BILLING_SHEET = new Integer(10006);

    /** Sheet Type Code for Buyer Admin Sheet */
	public static final Integer BUYER_ADMIN_ORDER_SHEET = new Integer(10007);

    /** Sheet Type Code for Buyer Admin Product Sheet */
	public static final Integer BUYER_ADMIN_PRODUCT_SHEET = new Integer(10008);

    /** Sheet Type Code for Seller Admin Order Sheet */
	public static final Integer SELLER_ADMIN_ORDER_SHEET = new Integer(10009);

    /** Sheet Type Code for Seller Admin Allocation Sheet */
	public static final Integer SELLER_ADMIN_ALLOCATION_SHEET = new Integer(10010);

    /** Sheet Type Code for Seller Admin Billing Sheet */
	public static final Integer SELLER_ADMIN_BILLING_SHEET = new Integer(10011);

    /** Sheet Type Code for Buyer Admin Received Sheet */
	public static final Integer BUYER_ADMIN_RECEIVED_SHEET = new Integer(10012);

    /** Sheet Type Code for Buyer Admin Billing Sheet */
	public static final Integer BUYER_ADMIN_BILLING_SHEET = new Integer(10013);

    /** Sheet Type Code for Proposed SKUs for BuyerOrderSheet */
	public static final Integer PROPOSED_BUYER_ORDER_SHEET = new Integer(10014);

    /** Sheet Type Code for Proposed SKUs not finalized in ProductSheet */
	public static final Integer PRE_SELLER_PRODUCT_SHEET = new Integer(10015);

    /** Sheet Type Code for CHOUAI Order Sheet */
	public static final Integer CHOUAI_ORDER_SHEET = new Integer(10017);

    /** Sheet Type Code for CHOUAI Allocation Sheet */
	public static final Integer CHOUAI_ALLOCATION_SHEET = new Integer(10018);

    /** Sheet Type Code for CHOUAI Billing Sheet */
	public static final Integer CHOUAI_BILLING_SHEET = new Integer(10019);
	
	/** Sheet Type Code for AKADEN Sheet */
	public static final Integer SELLER_AKADEN_SHEET = new Integer(10020);
	
	/** Sheet Type Code for AKADEN ADMIN Sheet */
	public static final Integer SELLER_ADMIN_AKADEN_SHEET = new Integer(10021);
	
	/** List of Buyer Order Sheet Types (Admin/Non-Admin)*/
	public static final List<Integer> BUYER_ORDER_SHEET_TYPE_LIST = Arrays.asList(BUYER_ORDER_SHEET, BUYER_ADMIN_ORDER_SHEET);
	
	
	public static final String sellerOrder = "SellerOrderSheet";
	public static final String sellerAlloc = "SellerAllocationSheet";
	public static final String sellerBill = "SellerBillingSheet";
	public static final String sellerAkaden = "SellerAkadenSheet";
	public static final String buyerOrder = "BuyerOrderSheet";
	public static final String buyerRec = "BuyerReceivedSheet";
	public static final String buyerBill = "BuyerBillingSheet";
	public static final String sellerAdminOrder = "SellerAdminOrderSheet";
	public static final String sellerAdminAlloc = "SellerAdminAllocationSheet";
	public static final String sellerAdminBill = "SellerAdminBillingSheet";
	public static final String sellerAdminAkaden = "SellerAdminAkadenSheet";
	public static final String buyerAdminOrder = "BuyerAdminOrderSheet";
	public static final String buyerAdminRec = "BuyerAdminReceivedSheet";
	public static final String buyerAdminBill = "BuyerAdminBillingSheet";
	
	public static final String SELLER_ORDER_SHEET_DESC = "Order Sheet";
	public static final String SELLER_ALLOCATION_SHEET_DESC = "Allocation Sheet";
	public static final String SELLER_BILLING_SHEET_DESC = "Billing Sheet";
	public static final String SELLER_AKADEN_SHEET_DESC = "Akaden Sheet";
	
	public static final String BUYER_ORDER_SHEET_DESC = "Order Sheet";
	public static final String BUYER_RECEIVED_SHEET_DESC = "Received Sheet";
	public static final String BUYER_BILLING_SHEET_DESC = "Billing Sheet";
	
	public static final String SELLER_ADMIN_ORDER_SHEET_DESC = "Order Sheet";
	public static final String SELLER_ADMIN_BILLING_SHEET_DESC = "Billing Sheet";
	
	public static final String BUYER_ADMIN_ORDER_SHEET_DESC = "Order Sheet";
	public static final String BUYER_ADMIN_BILLING_SHEET_DESC = "Billing Sheet";
	
	public static final String getSheetDesc(Integer sheetId) {
		if (sheetId.equals(SELLER_ORDER_SHEET))
			return SELLER_ORDER_SHEET_DESC;
		else if (sheetId.equals(SELLER_ALLOCATION_SHEET))
			return SELLER_ALLOCATION_SHEET_DESC;
		else if (sheetId.equals(SELLER_BILLING_SHEET))
			return SELLER_BILLING_SHEET_DESC;
		else if (sheetId.equals(SELLER_AKADEN_SHEET))
			return SELLER_AKADEN_SHEET_DESC;
		else if (sheetId.equals(BUYER_ORDER_SHEET))
			return BUYER_ORDER_SHEET_DESC;
		else if (sheetId.equals(BUYER_RECEIVED_SHEET))
			return BUYER_RECEIVED_SHEET_DESC;
		else if (sheetId.equals(BUYER_BILLING_SHEET))
			return BUYER_BILLING_SHEET_DESC;
		else if (sheetId.equals(SELLER_ADMIN_ORDER_SHEET)) 
			return SELLER_ADMIN_ORDER_SHEET_DESC;
		else if (sheetId.equals(BUYER_ADMIN_ORDER_SHEET))
			return BUYER_ADMIN_ORDER_SHEET_DESC;
		else if (sheetId.equals(SELLER_ADMIN_BILLING_SHEET))
			return SELLER_ADMIN_BILLING_SHEET_DESC;
		else if (sheetId.equals(BUYER_ADMIN_BILLING_SHEET))
			return BUYER_ADMIN_BILLING_SHEET_DESC;
		return "";
	}
	
	public static final String getSheetFileNameDesc(Integer sheetId) {
		if (sheetId.equals(SELLER_ORDER_SHEET))
			return sellerOrder;
		else if (sheetId.equals(SELLER_ALLOCATION_SHEET))
			return sellerAlloc;
		else if (sheetId.equals(SELLER_BILLING_SHEET))
			return sellerBill;
		else if (sheetId.equals(SELLER_AKADEN_SHEET))
			return sellerAkaden;
		else if (sheetId.equals(BUYER_ORDER_SHEET))
			return buyerOrder;
		else if (sheetId.equals(BUYER_RECEIVED_SHEET))			
			return buyerRec;		
		else if (sheetId.equals(BUYER_BILLING_SHEET))
			return buyerBill;
		else if (sheetId.equals(SELLER_ADMIN_ORDER_SHEET)) 
			return sellerAdminOrder;
		else if (sheetId.equals(SELLER_ADMIN_ALLOCATION_SHEET)) 
			return sellerAdminAlloc;
		else if (sheetId.equals(SELLER_ADMIN_BILLING_SHEET)) 
			return sellerAdminBill;
		else if (sheetId.equals(SELLER_ADMIN_AKADEN_SHEET)) 
			return sellerAdminAkaden;
		else if (sheetId.equals(BUYER_ADMIN_ORDER_SHEET))			
			return buyerAdminOrder;
		else if (sheetId.equals(BUYER_ADMIN_RECEIVED_SHEET))
			return buyerAdminRec;
		else if (sheetId.equals(SELLER_ADMIN_BILLING_SHEET))
			return sellerAdminBill;
		else if (sheetId.equals(BUYER_ADMIN_BILLING_SHEET))
			return buyerAdminBill;
		return "";
	}
}
