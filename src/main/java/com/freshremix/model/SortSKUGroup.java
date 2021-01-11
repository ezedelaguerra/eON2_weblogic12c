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
 * Jun 19, 2010		raquino		
 */
package com.freshremix.model;

/**
 * @author raquino
 *
 */
public class SortSKUGroup {
	
	private User user;
	private SKUGroup skuGroup;
	private Integer sorting;
	private Category category;

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public SKUGroup getSkuGroup() {
		return skuGroup;
	}
	public void setSkuGroup(SKUGroup skuGroup) {
		this.skuGroup = skuGroup;
	}
	public Integer getSorting() {
		return sorting;
	}
	public void setSorting(Integer sorting) {
		this.sorting = sorting;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

}
