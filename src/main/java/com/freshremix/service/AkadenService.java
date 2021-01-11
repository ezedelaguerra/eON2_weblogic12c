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
package com.freshremix.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.freshremix.model.AkadenItem;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.AkadenSheetParams;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SKU;
import com.freshremix.model.User;
import com.freshremix.ui.model.AkadenForm;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.ui.model.PageInfo;

/**
 * @author Jovino Balunan
 *
 */
public interface AkadenService {
	List<Map<String, Object>> loadAkadenItems(HttpServletRequest request, User user, OrderSheetParam akadenSheetParams, PageInfo pageInfo);
	
	List<SKU> selectDistinctSKUsFromAllocation(List<String> deliveryDates, Integer categoryId,Integer sheetTypeId, Integer sellerId, Integer rowStart, Integer pageSize);
	
	void saveAkaden(OrderForm orderForm, AkadenForm akadenForm, OrderDetails orderDetails, Map<Integer, AkadenSKU> skuObjMap, Map<Integer, AkadenSKU> skuObjUpdateMap, OrderSheetParam akadenParams, User users, Map<String, Order> allOrdersMap);
	
	List<Map<String, Object>> loadAllocationItems(User user, AkadenSheetParams akadenSheetParams, PageInfo pageInfo, HttpServletRequest request);	

	String getSKUGroupList(Integer seller, Integer categoryId);
	
	BigDecimal getGTPriceByOrderId(List<Integer> orderId, List<String> deliveryDates, List<Integer> buyerId);
	
	List<AkadenSKU> getDistinctSKUs(List<Integer> orderIds, Integer categoryId, Integer rowStart, Integer pageSize);
	
//	List<AkadenSKU> getImportedAllocationDistinctSKUs(List<Integer> skuIds, Integer rowStart, Integer pageSize);
	
	void loadOrderItemQuantities(Map<String, Object> skuOrderMap, List<Integer> companyBuyerIds, String deliveryDate, AkadenSKU skuObj, boolean isRed, OrderSheetParam osParams);
	
	//void loadOrderItemQuantities(Map<String, Object> skuOrderMap, List<String> deliveryDates, Integer buyerId, AkadenSKU skuObj, boolean isRed);
	
	void insertAkadenSKU(AkadenForm akadenForm, OrderDetails orderDetails, User users);
	
	void insertAkadenSKU(AkadenSKU sku);
	
	List<Order> getSelectedOrdersByDate (List<Integer> sellerIds, List<Integer> buyerIds, String selectedDate);
	
	List<Order> getSelectedOrdersByBuyer (List<Integer> sellerIds, Integer buyerIds, String startDate, String endDate);
	
	//Integer insertImportedAllocation(Integer skuId, String skuName, String typeFlag, Integer categoryId, Integer sheetTypeId, Integer companyId, String sellerId, String location, String market, String grade, String clazz, BigDecimal price1, BigDecimal price2,BigDecimal pricewotax, BigDecimal packageQty, String packageType, Integer skuGroupId, Integer unitOrder);
	Integer insertImportedAllocation(AkadenSKU sku);
	
	void insertImportedOrderAkaden(Integer order_id, Integer sku_id, Integer update_by, String comments, Integer akaden_sku_id, String isnewsku, BigDecimal quantity, BigDecimal total_quantity, BigDecimal unit_price);
	
	Integer insertOrderByAkaden(Integer seller_id, Integer buyer_id, String delivery_date, Integer akaden_saved_by, Integer created_by);
	
	void loadAllocationQuantities(Map<String, Object> skuOrderMap, Integer buyerId, String deliveryDate, SKU skuObj);
	
	Integer updateAkadenSKUTypeFlag(Integer akadenSKUId);
	
	//Integer updateAkadenSKU(Integer AkadenSKUId, String skuName, Integer companyId, Integer sellerId, String location, String market, String grade, String clazz, BigDecimal price1, BigDecimal price2, BigDecimal pricewotax, BigDecimal packageQty, String packageType, Integer skuGroupId, Integer unitOrder);
	Integer updateAkadenSKU(AkadenSKU aSku);
	
	Integer updateOrderAkaden(Integer orderId, Integer AkadenSKUId, String comments, BigDecimal quantity, BigDecimal totalQuantity, BigDecimal unitPrice);
	
	Integer updateOrderAkadenByNewSKU(Integer orderId, Integer akadenSkuId, Integer isNewSKU, String comments, BigDecimal quantity, BigDecimal totalQuantity, BigDecimal unitPrice);
	
	void deleteOrderAkadenByAkadenSkuId(Integer akadenSkuId);
	
	void deleteAkadenSKUByAkadenSKUId(Integer akadenSkuId);
	
	void deleteAkadenSKUBySKUId(Integer skuId);
	
	void deleteOrderAkadenBySKUId(Integer skuId);
	
	Integer deleteSKUBySkuId(Integer SkuId);
	
	Order getOrderByDeliveryDate(Integer sellerId, Integer buyerId, String deliveryDate);
	
	List<Order> getAkadenOrders(List<Integer> buyerIds, List<String> deliveryDates, List<Integer> sellerIds);
	
	void loadSumAkadenQuantitiesAllDates(Map<String, Object> skuOrderMap, List<String> deliveryDates, List<Integer> orderIds, AkadenSKU skuObj);
	
	void loadSumAkadenQuantitiesAllBuyers(Map<String, Object> skuOrderMap, List<Integer> buyerIds, List<Integer> orderIds, AkadenSKU skuObj);
	
	List<SKU> getDistinctSKUs(List<Integer> allOrderId);
	
	Integer checkImportedAllocIfExists(Integer orderId, Integer skuId);
	
	GrandTotalPrices computeTotalPricesOnDisplay(List<Map<String, Object>> skuOrderList);
	
	GrandTotalPrices getGTPriceAllOrders(List<Integer> orderIds);
	
	Map<Integer, AkadenItem> getAkadenItemsMapOfSkuDate(String deliveryDate, Integer skuId,
			Integer akadenSkuId);
	
	Map<Integer, AkadenItem> getAkadenItemsMapOfSkuDates(List<String> deliveryDates,
			Integer skuId, Integer buyerId, Integer akadenSkuId);
}
