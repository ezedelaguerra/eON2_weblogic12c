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
 * Mar 17, 2010		Jovino Balunan		
 */
package com.freshremix.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.AkadenDao;
import com.freshremix.model.AkadenItem;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;
import com.freshremix.model.SKU;
import com.freshremix.ui.model.OrderItemUI;

/**
 * @author jabalunan
 *
 */
public class AkadenDaoImpl extends SqlMapClientDaoSupport implements AkadenDao {

	@Override
	public Integer countDistinctSKU(List<String> deliveryDates, Integer categoryId, Integer sheetTypeId, Integer sellerId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDates",deliveryDates.toArray());
		param.put("categoryId",categoryId);
		param.put("sheetTypeId",sheetTypeId);
		param.put("sellerId",sellerId);
		Integer iret = (Integer)getSqlMapClientTemplate().queryForObject("Akaden.countDistinctSKU", param);
		return iret;
	}

	@Override
	public BigDecimal getGTPrice(Integer buyerId, String selectedDate) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("buyerId", buyerId);
		param.put("selectedDate", selectedDate);
		return (BigDecimal)getSqlMapClientTemplate().queryForObject("Akaden.getTotalPriceByDateAndBuyer", param);
	}

//	public Map<Integer, AkadenItem> getOrderItemsMapOfSkuDate(Map<String, Object> param) {
//		return getSqlMapClientTemplate().queryForMap("Akaden.getAkadenItemsMapOfSkuDate", param, "order.buyerId");
//	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, AkadenItem> getOrderItemsMapOfSkuDates(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForMap("Akaden.getAkadenItemsMapOfSkuDates", param, "order.deliveryDate");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItemUI> loadAkadenItems(Map<String, Object> param) {
		return  getSqlMapClientTemplate().queryForList("Akaden.loadAkadenItems", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AkadenSKU> getDistinctSKUs(List<Integer> orderIds, Integer categoryId, Integer rowStart, Integer pageSize) {
		
		List<AkadenSKU> list = new ArrayList<AkadenSKU>();
		
		int orderCount = orderIds.size();
		int loopCount = (orderCount / 1000) + 1;
		/* SQL WHERE column IN (?,?,?...) IS LIMITED TO 1000 */
		for (int i = 0; i < loopCount; i++) {
			
			int startIdx = i * 1000;
			List<Integer> thisOrderIds = new ArrayList<Integer>();
			for (int j = startIdx; j < startIdx + 1000; j++) {
				if (orderCount > j)
					thisOrderIds.add(orderIds.get(j));
			}
			
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("categoryId", categoryId);
			param.put("orderIds", thisOrderIds);
			param.put("rowStart", rowStart);
			param.put("rowEnd", rowStart.intValue() + pageSize.intValue());
			list.addAll(getSqlMapClientTemplate().queryForList("Akaden.getDistinctSKUs", param));
		}
		
		return list;
	}

	@Override
	public void saveOrderAkaden(Integer orderId, Integer skuId, Integer updateBy, String comments, Integer akaden_sku_id, String isnewsku, BigDecimal quantity, BigDecimal total_quantity, BigDecimal unitPrice) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("updateBy", updateBy);
		param.put("comments", comments);
		param.put("akadenSkuId", akaden_sku_id);
		param.put("isNewSKU", isnewsku);
		param.put("quantity", quantity);
		param.put("totalQuantity", total_quantity);
		param.put("unitPrice", unitPrice);
		getSqlMapClientTemplate().insert("Akaden.insertImportedOrderAkaden", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SKU> selectDistinctSKUsFromAllocation(List<String> deliveryDates, Integer categoryId,
			Integer sheetTypeId, Integer sellerId, Integer rowStart, Integer pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("deliveryDates", deliveryDates);
		param.put("categoryId", categoryId);
		param.put("sheetTypeId", sheetTypeId);
		param.put("sellerId", sellerId);
		param.put("rowStart", rowStart);
		param.put("rowEnd", rowStart.intValue() + pageSize.intValue());
		return getSqlMapClientTemplate().queryForList("Akaden.selectDistinctSKUsFromAllocation",param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AkadenSKU> getImportedAllocationData(List<Integer> orderIds, Integer categoryId, Integer sheetTypeId, Integer rowStart, Integer pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderIds", orderIds);
		param.put("categoryId", categoryId);
		param.put("sheetTypeId", sheetTypeId);
		param.put("rowStart", rowStart);
		param.put("rowEnd", pageSize);
		return getSqlMapClientTemplate().queryForList("Akaden.getImportedAllocationData",param);
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<AkadenSKU> getImportedAllocationDistinctSKUs(List<Integer> skuIds, Integer rowStart, Integer pageSize) {
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("skuIds", skuIds);
//		param.put("rowStart", rowStart);
//		param.put("rowEnd", pageSize);
//		return getSqlMapClientTemplate().queryForList("Akaden.getImportedAllocationDistinctSKUs",param);
//	}

	@Override
	public Integer insertAkadenSKU(AkadenSKU sku) {
		Integer iSaveResult = (Integer) getSqlMapClientTemplate().insert("Akaden.insertAkadenSKU", sku);
		return  iSaveResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getSelectedOrdersByBuyer(List<Integer> sellerIds,
			Integer buyerId, List<String> dates) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerIds", sellerIds);
		param.put("deliveryDate", dates);
		param.put("buyerId", buyerId);
		return getSqlMapClientTemplate().queryForList("Akaden.getSelectedOrdersUsingBuyer", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getSelectedOrdersByDate(List<Integer> sellerIds, List<Integer> buyerIds, String selectedDate) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerIds", sellerIds);
		param.put("buyerIds", buyerIds);
		param.put("deliveryDate", selectedDate);
		List<Order> orders = getSqlMapClientTemplate().queryForList("Akaden.getSelectedOrdersUsingDate", param);
		return orders;
	}

	@Override
	public Integer insertOrderByAkaden(Integer sellerId,
			Integer buyerId, String deliveryDate, Integer orderSavedBy,
			Integer createdBy) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sellerId", sellerId);
		param.put("buyerId", buyerId);
		param.put("deliveryDate", deliveryDate);
		param.put("akadenSavedBy", orderSavedBy);
		param.put("createdBy", createdBy);
		Integer iresult = (Integer) getSqlMapClientTemplate().insert("Akaden.insertOrderByAkaden", param);
		return iresult;
	}

	@Override
	public Integer insertImportedAllocation(AkadenSKU sku) {
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("typeFlag", typeFlag);
//		param.put("skuId", skuId);
//		param.put("skuName", skuName);		
//		param.put("categoryId", categoryId);
//		param.put("sheetTypeId", sheetTypeId);
//		param.put("sellerId", sellerId);
//		param.put("companyId", companyId);
//		param.put("location", location);
//		param.put("market", market);
//		param.put("grade", grade);
//		param.put("clazz", clazz);
//		param.put("price1", price1);
//		param.put("price2", price2);
//		param.put("pricewotax", pricewotax);
//		param.put("packageQty", packageQty);
//		param.put("packageType", packageType);
//		param.put("skuGroupId", skuGroupId);
//		param.put("unitOfOrder", unitOrder);
		return (Integer) getSqlMapClientTemplate().insert("Akaden.insertImportedAllocation", sku);
		
	}

	@Override
	public void insertImportedOrderAkaden(Integer orderId, Integer skuId,
			Integer updateBy, String comments, Integer akadenSkuId,
			String isnewsku, BigDecimal quantity, BigDecimal totalQuantity,
			BigDecimal unitPrice) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("updateBy", updateBy);
		param.put("comments", comments);
		param.put("akadenSkuId", akadenSkuId);
		param.put("isNewSKU", isnewsku);
		param.put("quantity", quantity);
		param.put("totalQuantity", totalQuantity);
		param.put("unitPrice", unitPrice);
		getSqlMapClientTemplate().insert("Akaden.insertImportedOrderAkaden", param);
	}

	@Override
	public Integer updateAkadenSKUTypeFlag(Integer akadenSKUId) {
		Integer iResult = (Integer) getSqlMapClientTemplate().update("Akaden.updateAkadenSKUTypeFlag", akadenSKUId);
		return iResult;
	}

	@Override
	public Integer updateAkadenSKU(AkadenSKU aSku) {
		Integer iResult = (Integer) getSqlMapClientTemplate().update("Akaden.updateAkadenSKU", aSku);
		return iResult;
	}

	@Override
	public Integer updateOrderAkaden(Integer orderId, Integer AkadenSKUId, String comments,
			BigDecimal quantity, BigDecimal totalQuantity, BigDecimal unitPrice) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		param.put("akadenSKUId", AkadenSKUId);
		param.put("comments", comments);
		param.put("quantity", quantity);
		param.put("totalQuantity", totalQuantity);
		param.put("unitPrice", unitPrice);		
		Integer iResult = (Integer) getSqlMapClientTemplate().update("Akaden.updateOrderAkaden", param);
		return iResult;
	}

	@Override
	public void deleteAkadenSKUByAkadenSKUId(Integer akadenSkuId) {
		 getSqlMapClientTemplate().delete("Akaden.deleteAkadenSKUByAkadenSKUId", akadenSkuId);
	}

	@Override
	public void deleteOrderAkadenByAkadenSkuId(Integer akadenSkuId) {
		getSqlMapClientTemplate().delete("Akaden.deleteOrderAkadenByAkadenSkuId", akadenSkuId);
	}

	@Override
	public Integer deleteSKUBySkuId(Integer SkuId) {
		Integer iDelete = getSqlMapClientTemplate().delete("Akaden.deleteSKUBySkuId", SkuId);
		return iDelete;
	}

	@Override
	public Order getOrderByDeliveryDate(Integer sellerId, Integer buyerId, String deliveryDate) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("buyerId", buyerId);
		param.put("deliveryDate", deliveryDate);
		return (Order) getSqlMapClientTemplate().queryForObject("Akaden.selectOrderByDeliveryDate", param);
	}

	@Override
	public void updateAkadenSaveBy(Integer sellerId, Integer orderId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerId", sellerId);
		param.put("orderId", orderId);
		getSqlMapClientTemplate().update("Akaden.updateAkadenSaveBy", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getAkadenOrders(List<Integer> sellerIds,
			List<Integer> buyerIds, List<String> deliveryDates) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerIds", sellerIds);
		param.put("deliveryDates", deliveryDates);
		param.put("buyerIds", buyerIds);
		return getSqlMapClientTemplate().queryForList("Akaden.getAkadenOrders", param);
	}

	@Override
	public Integer updateOrderAkadenByNewSKU(Integer orderId, Integer akadenSkuId,
			Integer isNewSKU, String comments, BigDecimal quantity,
			BigDecimal totalQuantity, BigDecimal unitPrice) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		param.put("akadenSkuId", akadenSkuId);
		param.put("comments", comments);
		param.put("quantity", quantity);
		param.put("totalQuantity", totalQuantity);
		param.put("unitPrice", unitPrice);		
		Integer iResult = (Integer) getSqlMapClientTemplate().update("Akaden.updateOrderAkadenByNewSKU", param);
		return iResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SKU> selectDistinctSKUs(List<Integer> allOrderId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", allOrderId);
		return getSqlMapClientTemplate().queryForList("Akaden.selectAllDistinctSKUs", param);
	}

	@Override
	public Integer checkImportedAllocIfExists(Integer orderId, Integer skuId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		Integer iResult = (Integer) getSqlMapClientTemplate().queryForObject("Akaden.checkImportedAllocIfExists", param);
		return iResult;
	}

	@Override
	public GrandTotalPrices getGTPriceAllOrders(List<Integer> orderIds) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderIds", orderIds);
		return (GrandTotalPrices) getSqlMapClientTemplate().queryForObject("Akaden.getGTPriceAllOrders", param);
	}

	@Override
	public void deleteAkadenSKUBySKUId(Integer skuId) {
		getSqlMapClientTemplate().delete("Akaden.deleteAkadenSKUByAkadenSKUId", skuId);		
	}

	@Override
	public void deleteOrderAkadenBySKUId(Integer skuId) {
		getSqlMapClientTemplate().delete("Akaden.deleteOrderAkadenBySKUId", skuId);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AkadenItem> getAkadenItemsBulkForBilling(List<Integer> orderIds,
			List<Integer> skuIds, boolean hasQty) {
		
		List<AkadenItem> list = new ArrayList<AkadenItem>();
		
		int orderCount = orderIds.size();
		int loopCount = (orderCount / 1000) + 1;
		/* SQL WHERE column IN (?,?,?...) IS LIMITED TO 1000 */
		for (int i = 0; i < loopCount; i++) {
			
			int startIdx = i * 1000;
			List<Integer> thisOrderIds = new ArrayList<Integer>();
			for (int j = startIdx; j < startIdx + 1000; j++) {
				if (orderCount > j)
					thisOrderIds.add(orderIds.get(j));
			}
			
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("orderIds", thisOrderIds);
			param.put("skuIds", skuIds);
			if(hasQty){
				param.put("hasQty", hasQty);
			}
			
			list.addAll(getSqlMapClientTemplate().queryForList("Akaden.getAkadenItemsBulkForBilling", param));
		}
		
		
		return list;
	}
}
