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
 * Jun 17, 2010		raquino		
 */
package com.freshremix.model;

import java.util.Map;

/**
 * @author raquino
 *
 */
public class SKUGroupList {

	private Map<String,Object> skuGroup;

	public Map<String, Object> getSkuGroup() {
		return skuGroup;
	}

	public void setSkuGroup(Map<String, Object> skuGroup) {
		this.skuGroup = skuGroup;
	}
	
}
