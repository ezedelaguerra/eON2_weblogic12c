/**
\ * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
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
 * 2010/05/17		Jovino Balunan		
 */
package com.freshremix.dao;

import java.util.List;
import java.util.Map;

import com.freshremix.model.AkadenItem;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.OrderItem;
import com.freshremix.model.Sheets;

/**
 * @author Jovino Balunan
 *
 */
public interface DownloadCSVDao {
	
	List<AkadenSKU> loadSellerAkadenCSVDownload(List<Integer> orderIds, Integer categoryId, Integer sheetTypeId, String hasQuantity);

	Map<Integer, AkadenItem> getSellerAkadenItemsMapOfSkuDate(String deliveryDates, Integer buyerId, Integer akadenSKUId);
	
	Map<Integer, OrderItem> getSellerOrderItemsMapOfSkuDates(String deliveryDates, Integer skuId, Integer buyerId);
	
	List<String> getBuyerCSVUsersList(List<Integer> userId);
	
	List<Sheets> getSheetType(Integer roleId);
	
	List<Sheets> getAllSheetType();
	
	Sheets getSheetType(Long roleId, String desc);
}
