/**
 * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
 * <BR>
 * <B>Description:</B><BR>
 * These constants are being used for Billing Sheet to identify if items comes from Billing table or 
 * from Akaden table (contains items with adjustments). And by the given values this will enable
 * to sort items accordingly, by billing sku first then akaden sku - with original qty, negative qty and corrected
 * qty, or by original sku order details and corrected sku, or by newly added sku.<BR>
 * <BR>
 * <B>Known Bugs:</B>
 * none<BR>
 * <BR>
 * <B>History:</B>
 * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
 * date       name            changes
 * ------------------------------------------------------------------------------
 * Mar 30, 2010		raquino		
 */
package com.freshremix.constants;

/**
 * @author raquino
 *
 */
public class BillingAkadenSkuIdConstants {

	public static final String BILLING_TYPE_FLAG = "0";
	public static final String AKADEN_TYPE_FLAG_ORIG_QTY = "1";
	public static final String AKADEN_TYPE_FLAG_NEG_QTY = "2";
	public static final String AKADEN_TYPE_FLAG_NEW_QTY = "3";
	public static final String AKADEN_TYPE_FLAG_NEW_SKU = "9";
	
}
