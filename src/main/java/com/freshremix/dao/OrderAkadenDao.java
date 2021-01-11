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
 * Apr 2, 2010		raquino		
 */
package com.freshremix.dao;

import java.util.List;
import java.util.Map;

import com.freshremix.model.AkadenItem;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.BillingItem;
import com.freshremix.model.OrderReceived;

/**
 * @author raquino
 *
 */
public interface OrderAkadenDao {
	
	List<Integer> getSelectedOrdersByDate (List<Integer> sellerIds, List<Integer> buyerIds, String selectedDate);
	List<Integer> getSelectedOrdersByBuyer (List<Integer> sellerIds, Integer buyerId, List<String> dates);
	List<AkadenSKU> selectDistinctSKUs(List<Integer> orderIds, Integer categoryId, Integer sheetTypeId, Integer rowStart, Integer pageSize);
	Map<Integer, AkadenItem> getAkadenItemsMapOfSkuDate(String deliveryDate, Integer skuId, Integer akadenSkuId);
	Map<Integer, AkadenItem> getAkadenItemsMapOfSkuDates(List<String> deliveryDates, Integer skuId, Integer buyerId, Integer akadenSkuId);
	
	Map<Integer, AkadenItem> getSumAkadenMapOfSkuBuyers(List<Integer> orderIds, Integer skuId, Integer akadenSkuId);
	Map<Integer, AkadenItem> getSumAkadenMapOfSkuDates(List<Integer> orderIds, Integer skuId, Integer akadenSkuId);
	
	List<AkadenItem> getAkadenItemsBulk(List<Integer> orderIds);
	
}
