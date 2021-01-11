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
 * 2010/05/17		Jovino Balunan		
 */
package com.freshremix.service;

import java.util.List;
import java.util.Map;

import com.freshremix.model.AkadenItem;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.DownloadCSVSettings;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SKU;
import com.freshremix.model.Sheets;
import com.freshremix.model.User;

/**
 * @author Jovino Balunan
 *
 */
public interface DownloadCSVService {

	List<AkadenSKU> loadBillingAndAkadenCSVDownload(List<Integer> orderIds, Integer categoryId, Integer sheetTypeId, String hasQuantity, User user);
	
	List<String> getBuyerCSVUsersList(List<Integer> userIds);
	
	Map<Integer, AkadenItem> getAkadenItemsMapOfSkuDates(List<String> deliveryDates, Integer skuId, Integer buyerId, Integer akadenSkuId);
	
	Map<Integer, AkadenItem> getAkadenItemsMapOfSkuDate(String deliveryDate, Integer skuId, Integer akadenSkuId);
	
	Map<String, Object> getOrdersQuantiyMap(Integer buyerId, String deliveryDate, Integer skuId, Integer sheetTypeId, boolean isAllDatesView, List<Integer> orderIds);
	
	Map<String, Object> getAkadenQuantiyMap(Integer skuId, List<Integer> orderIds, Integer buyerId, String deliveryDate, Integer akadenSkuId, Integer sheetTypeId, boolean isAllDatesView);
	
//	List<Map<String, Object>> loadOrderItemQuantities(List<String> deliveryDates, Integer buyerId, DownloadCSVSheet skuObj);
//	
//	List<Map<String, Object>> loadOrderItemQuantities(List<Integer> companyBuyerIds, String deliveryDate, DownloadCSVSheet skuObj);
	
	List<Sheets> getSheetType(Integer roleId);
	
	DownloadCSVSettings initializeDownloadCSV(User user, OrderSheetParam osParam);
	
	DownloadCSVSettings loadBuyerList(User user, DownloadCSVSettings downloadCSVSettings);
	
	DownloadCSVSettings loadSellerList(User user, DownloadCSVSettings downloadCSVSettings);
}
