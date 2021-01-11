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
 * Jun 1, 2010		nvelasquez		
 */
package com.freshremix.model;

/**
 * @author nvelasquez
 *
 */
public class SKUGroupSortParam {
	
	private String skuGroupIds;
	private String sortOrder;
	private String skuCategoryId;
	
	public String getSkuGroupIds() {
		return skuGroupIds;
	}
	public void setSkuGroupIds(String skuGroupIds) {
		this.skuGroupIds = skuGroupIds;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getSkuCategoryId() {
		return skuCategoryId;
	}
	public void setSkuCategoryId(String skuCategoryId) {
		this.skuCategoryId = skuCategoryId;
	}
	
}
