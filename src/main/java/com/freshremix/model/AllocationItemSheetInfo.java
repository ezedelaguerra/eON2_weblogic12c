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
 * Nov 9, 2010		raquino		
 */
package com.freshremix.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author raquino
 *
 */
public class AllocationItemSheetInfo extends ItemSheetInfoForAllocation {
    
    private BuyerEntityInfo[] buyerEntityInfo;  
    
    public BuyerEntityInfo[] getBuyerEntityInfo() {
		return buyerEntityInfo;
	}

	public void setBuyerEntityInfo(BuyerEntityInfo[] buyerEntityInfo) {
		this.buyerEntityInfo = buyerEntityInfo;
	}
    
    public Map BuyerEntityItemMap () {
		Map itemMap = new HashMap();
		
		if (buyerEntityInfo != null && buyerEntityInfo.length > 0) {
			for (int i=0; i<buyerEntityInfo.length; i++) {
				BuyerEntityInfo item = (BuyerEntityInfo)buyerEntityInfo[i];
				itemMap.put(item.getBuyerId().toString(), item);
			}
		}
		
		return itemMap;
	}
    
} 
