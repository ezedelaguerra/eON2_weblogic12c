/**
 * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
 * <BR>
 * <B>Description:</B><BR>
 * Describe class or interface.<BR>
 * <BR>
 * <B>Known Bugs:</B>
 * none<BR>
 * <BR>
 * <B>History:</B>
 * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
 * date       name            changes
 * ------------------------------------------------------------------------------
 * 20120730			Rhoda		Redmine 68 - Order Sheet Concurrency
 */
package com.freshremix.constants;

public class OrderConstants {

	public static final String ACTION_SAVE = "save";
	public static final String ACTION_FINALIZE = "finalize";
	//ENHANCEMENT 20120727: Rhoda Redmine 68
	public static final String ACTION_UNFINALIZE = "unfinalize";
	public static final String ACTION_UNPUBLISH = "unpublish";
	public static final String ACTION_UNLOCK = "unlock";
	public static final String ACTION_LOCK = "lock";
	public static final String DELETE = "delete";
	public static final String UPDATE = "update";
	
	
	public static final String ORDERSHEET_CONCURRENT_SAVE_FINALIZED_FAILED = "ordersheet.concurrent.save.finalizedFailed";
	public static final String ORDERSHEET_CONCURRENT_SAVE_SKU_HAS_QTY_FAILED = "ordersheet.concurrent.save.skuhasqty";
	public static final String FAILED_ORDERS = "FAILED_ORDERS";
	public static final String FAILED_SKU = "FAILED_SKU";
	public static final String ORDERSHEET_CONCURRENT_SAVE_MAX_LIMIT_ERROR = "ordersheet.concurrent.save.skumaxqtyerror";
	public static final String ORDERSHEET_CONCURRENT_SAVE_SKUVISIBILITY_ERROR= "ordersheet.concurrent.save.reason.skuvisibilityerror";
	public static final String ORDERSHEET_CONCURRENT_SAVE_SKUMAXLIMIT_ERROR= "ordersheet.concurrent.save.reason.skumaxqtyerror";
	public static final String ORDERSHEET_CONCURRENT_SAVE_SKUHASQTY_ERROR= "ordersheet.concurrent.save.reason.skuhasqtyerror";
	public static final String ORDERSHEET_CONCURRENT_SAVE_SKU_ERROR= "ordersheet.concurrent.save.reason.skuupdateerror";
	public static final String ORDERSHEET_CONCURRENT_SAVE_SKUCHANGED = "ordersheet.concurrent.save.skuchanged";

	

}
