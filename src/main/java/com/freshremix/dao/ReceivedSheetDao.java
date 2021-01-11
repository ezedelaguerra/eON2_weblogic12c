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
 * date		name	version	changes
 * ------------------------------------------------------------------------------
 * Mar 30, 2010		nvelasquez			
 * 20121001	Rhoda	v12		Redmine #1089 – Allocation-Received Sheet different in quantity 
 */
package com.freshremix.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.freshremix.exception.ServiceException;
import com.freshremix.model.Order;
import com.freshremix.model.OrderReceived;
import com.freshremix.model.SKUBA;
import com.freshremix.ui.model.ProfitInfo;

/**
 * @author nvelasquez
 *
 */
public interface ReceivedSheetDao {
	
	List<SKUBA> selectDistinctSKUBAs(List<Integer> orderIds, List<Integer> sellerId, List<Integer> buyerId, List<String> deliveryDate, Integer categoryId, String hasQty);
	
	Map<Integer, OrderReceived> getOrderReceivedMapOfSkuBuyers(List<Integer> orderIds, Integer skuId);
	
	Map<Integer, OrderReceived> getSumOrderReceivedMapOfSkuBuyers(List<Integer> orderIds, Integer skuId);
	
	Map<Integer, OrderReceived> getOrderReceivedMapOfSkuDates(List<Integer> orderIds, Integer skuId);
	
	Map<Integer, OrderReceived> getSumOrderReceivedMapOfSkuDates(List<Integer> orderIds, Integer skuId);
	// ENHANCEMENT START 20121001: Rhoda Redmine 1089
//	void updateReceived(Integer orderId, Integer skuId, Integer skuBaId, BigDecimal quantity, String isApproved, Integer userId, Integer oldSkuBaId);
	void updateReceived(Integer orderId, Integer skuId, Integer skuBaId, String isApproved, Integer userId, Integer oldSkuBaId);
	// ENHANCEMENT END 20121001: Rhoda Redmine 1089
	void updateReceivedFromAlloc(Integer orderId, Integer skuId, BigDecimal quantity, Integer userId);
	
	void updateSpecialReceivedItem(Integer orderId, Integer skuId, Integer origSkuId, BigDecimal quantity, Integer userId);
	
	List<Order> getPublishedOrdersForReceived(List<Integer> buyerIds, List<String> deliveryDates, List<Integer> sellerIds);
	
	void lockReceived(List<Integer> orderIds, Integer userId);
	
	void unlockReceived(List<Integer> orderIds, Integer userId);
	
	void insertDefaultReceiveItems(Integer orderId, Integer skuId, Integer skuBaId, Integer userId, BigDecimal quantity);
	
	void deleteReceiveItems(Integer orderId);
	
	void deleteReceiveItems(Integer orderId, List<Integer> skuIds);
	
	List<Order> getApprovedOrdersForReceived(List<Integer> orderId);
	
	List<OrderReceived> getReceivedItemsBulk(List<Integer> orderIds, List<Integer> skuIds, boolean hasQty);
	
	void deleteReceiveItems(List<Integer> orderIds, List<Integer> skuIds) throws ServiceException;
	
	void updateReceivedItemSkuBaId(Integer orderId, Integer skuId, Integer skuBaId, Integer oldSkuBaId);
	
	List<ProfitInfo> getBuyerPricesPerSKU(String deliveryDate, List<Integer> buyerId, List<Integer> sellerId, Integer categoryId, Double tax);

	void bulkInsertReceivedItemFromAllocation(List<Integer> orderIdList,
			Integer savedAndPublishedBy) throws Exception;

	void deleteReceivedItemByBatch(List<Integer> orderIdList) throws Exception;
}
