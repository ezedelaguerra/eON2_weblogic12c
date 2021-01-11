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
package com.freshremix.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
public interface AkadenDao {
	List<OrderItemUI> loadAkadenItems(Map<String,Object> param);
	
	List<AkadenSKU> getDistinctSKUs(List<Integer> orderIds, Integer categoryId, Integer rowStart, Integer pageSize);
	
	List<SKU> selectDistinctSKUsFromAllocation(List<String> deliveryDates, Integer categoryId, Integer sheetTypeId, Integer sellerId, Integer rowStart, Integer pageSize);

	Integer countDistinctSKU(List<String> deliveryDates, Integer categoryId, Integer sheetTypeId, Integer sellerId);

	//Map<Integer, OrderItem> getOrderItemsMapOfSkuDates(Map<String, Object> param);
	
	BigDecimal getGTPrice(Integer buyerId, String selectedDate);

	void saveOrderAkaden(Integer orderId, Integer skuId, Integer updateBy, String comments, Integer akaden_sku_id, String isnewsku, BigDecimal quantity, BigDecimal bigDecimal, BigDecimal unitPrice);
	
	List<AkadenSKU> getImportedAllocationData(List<Integer> orderIds, Integer categoryId, Integer sheetTypeId, Integer rowStart, Integer pageSize);
	
//	List<AkadenSKU> getImportedAllocationDistinctSKUs(List<Integer> skuIds, Integer rowStart, Integer pageSize);
	
	Integer insertAkadenSKU(AkadenSKU sku);
	
	List<Order> getSelectedOrdersByDate (List<Integer> sellerIds, List<Integer> buyerIds, String selectedDate);
	
	List<Order> getSelectedOrdersByBuyer (List<Integer> sellerIds, Integer buyerId, List<String> dates);
	
//	Map<Integer, AkadenItem> getOrderItemsMapOfSkuDate(Map<String, Object> param);
	
	Map<Integer, AkadenItem> getOrderItemsMapOfSkuDates(Map<String, Object> param);
	
	//Integer insertImportedAllocation(Integer skuId, String skuName, String typeFlag, Integer categoryId, Integer sheetTypeId, Integer companyId, String sellerId, String location, String market, String grade, String clazz, BigDecimal price1, BigDecimal price2,BigDecimal pricewotax, BigDecimal packageQty, String packageType, Integer skuGroupId, Integer unitOrder);
	Integer insertImportedAllocation(AkadenSKU sku);
	
	Integer insertOrderByAkaden(Integer seller_id, Integer buyer_id, String delivery_date, Integer akaden_saved_by, Integer created_by);
	
	void insertImportedOrderAkaden(Integer order_id, Integer sku_id, Integer update_by, String comments, Integer akaden_sku_id, String isnewsku, BigDecimal quantity, BigDecimal total_quantity, BigDecimal unit_price);
	
	Integer updateAkadenSKUTypeFlag(Integer akadenSKUId);
	
	//Integer updateAkadenSKU(Integer AkadenSKUId, String skuName, Integer companyId, Integer sellerId, String location, String market, String grade, String clazz, BigDecimal price1, BigDecimal price2, BigDecimal pricewotax, BigDecimal packageQty, String packageType, Integer skuGroupId, Integer unitOrder);
	Integer updateAkadenSKU(AkadenSKU aSku);
	
	Integer updateOrderAkaden(Integer orderId, Integer AkadenSKUId, String comments, BigDecimal quantity, BigDecimal totalQuantity, BigDecimal unitPrice);
	
	Integer updateOrderAkadenByNewSKU(Integer orderId, Integer akadenSkuId, Integer isNewSKU, String comments, BigDecimal quantity, BigDecimal totalQuantity, BigDecimal unitPrice);	
	
	void deleteOrderAkadenByAkadenSkuId(Integer akadenSkuId);
	
	void deleteAkadenSKUBySKUId(Integer skuId);
	
	void deleteOrderAkadenBySKUId(Integer skuId);
	
	void deleteAkadenSKUByAkadenSKUId(Integer akadenSkuId);
	
	Integer deleteSKUBySkuId(Integer SkuId);
	
	Order getOrderByDeliveryDate(Integer sellerId, Integer buyerId, String deliveryDate);
	
	void updateAkadenSaveBy(Integer sellerId, Integer orderId);
	
	List<Order> getAkadenOrders(List<Integer> sellerIds, List<Integer> buyerIds, List<String> deliveryDates);
	
	List<SKU> selectDistinctSKUs(List<Integer> allOrderId);
	
	Integer checkImportedAllocIfExists(Integer orderId, Integer skuId);
	
	GrandTotalPrices getGTPriceAllOrders(List<Integer> orderIds);
	
	List<AkadenItem> getAkadenItemsBulkForBilling (List<Integer> orderIds, List<Integer> skuIds, boolean hasQty);
}
