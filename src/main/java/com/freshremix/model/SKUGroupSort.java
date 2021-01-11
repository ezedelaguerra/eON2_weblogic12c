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
 * Jun 2, 2010		nvelasquez		
 */
package com.freshremix.model;

/**
 * @author nvelasquez
 *
 */
public class SKUGroupSort {
	
	Integer userId;
	String userName;
	Integer skuCategoryId;
	Integer skuGroupId;
	String skuGroupName;
	Integer sorting;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getSkuCategoryId() {
		return skuCategoryId;
	}
	public void setSkuCategoryId(Integer skuCategoryId) {
		this.skuCategoryId = skuCategoryId;
	}
	public Integer getSkuGroupId() {
		return skuGroupId;
	}
	public void setSkuGroupId(Integer skuGroupId) {
		this.skuGroupId = skuGroupId;
	}
	public String getSkuGroupName() {
		return skuGroupName;
	}
	public void setSkuGroupName(String skuGroupName) {
		this.skuGroupName = skuGroupName;
	}
	public Integer getSorting() {
		return sorting;
	}
	public void setSorting(Integer sorting) {
		this.sorting = sorting;
	}

}
