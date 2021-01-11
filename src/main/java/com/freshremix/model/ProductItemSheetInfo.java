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
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.freshremix.util.ArrayUtil;


/**
 * @author raquino
 *
 */
public class ProductItemSheetInfo extends ItemSheetInfo {

	private ItemInfoVisibility[] itemInfoVisibility; // ItemInfoVisibility object 
    private int itemInfoVisibilityIndex;

	public ItemInfoVisibility[] getItemInfoVisibility() {
		return itemInfoVisibility;
	}

	public void setItemInfoVisibility(ItemInfoVisibility[] itemInfoVisibility) {
		this.itemInfoVisibility = itemInfoVisibility;
	}
	
	public Map itemInfoVisibilityMap () {
		Map itemMap = new HashMap();
		
		if (itemInfoVisibility != null && itemInfoVisibility.length > 0) {
			for (int i=0; i<itemInfoVisibility.length; i++) {
				ItemInfoVisibility item = (ItemInfoVisibility)itemInfoVisibility[i];
				itemMap.put(item.getBuyerId().toString(), item);
			}
		}
		
		return itemMap;
	}
    
    public void completeItemInfoVisibility(List buyerEntityList) {
        
        if (itemInfoVisibility.length < buyerEntityList.size()) {
            Map itemMap = this.itemInfoVisibilityMap();
            Set itemKeys = itemMap.keySet();
            itemInfoVisibilityIndex = itemInfoVisibility.length;
            this.itemInfoVisibility = 
                (ItemInfoVisibility[]) ArrayUtil.resizeArray((Object) itemInfoVisibility, buyerEntityList.size());
            
            for (int i=0; i < buyerEntityList.size(); i++) {
                User ent = (User) buyerEntityList.get(i);
                Integer id = ent.getUserId();
                
                if (!itemKeys.contains(id.toString())) {
                    addItemInfoVisibility(this.createDefaultItemInfoVisibility(id), itemInfoVisibilityIndex);
                }
                
            }
        }
        
    }
    
    public void addItemInfoVisibility (ItemInfoVisibility item, int index) {
        this.itemInfoVisibility[index] = item;
        itemInfoVisibilityIndex++;
    }
    
    private ItemInfoVisibility createDefaultItemInfoVisibility(Integer buyerId) {
        ItemInfoVisibility item = new ItemInfoVisibility();
        item.setBuyerId(buyerId);
        item.setQuantity("0");
        item.setVisibilityFlag(true);
        
        return item;
    }
}
