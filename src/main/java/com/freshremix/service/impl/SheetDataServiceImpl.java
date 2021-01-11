package com.freshremix.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.dao.AkadenDao;
import com.freshremix.dao.AllocationDao;
import com.freshremix.dao.DealingPatternDao;
import com.freshremix.dao.OrderBillingDao;
import com.freshremix.dao.OrderDao;
import com.freshremix.dao.OrderSheetDao;
import com.freshremix.dao.OrderUnitDao;
import com.freshremix.dao.ReceivedSheetDao;
import com.freshremix.dao.UserDao;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.BillingItem;
import com.freshremix.model.Item;
import com.freshremix.model.Order;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.SKU;
import com.freshremix.model.SheetData;
import com.freshremix.model.User;
import com.freshremix.service.SheetDataService;
import com.freshremix.service.WebService;
import com.freshremix.util.CollectionUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SortingUtil;

public class SheetDataServiceImpl implements SheetDataService {

	private OrderDao orderDao;
	private DealingPatternDao dealingPatternDao;
	private OrderSheetDao orderSheetDao;
	private AllocationDao allocationDao;
	private ReceivedSheetDao receivedSheetDao;
	private UserDao usersInfoDaos;
	private AkadenDao akadenDao;
	private OrderBillingDao orderBillingDao;
	private WebService webService;
	private OrderUnitDao orderUnitDao;

	// Setters
	public void setOrderUnitDao(OrderUnitDao orderUnitDao) {
		this.orderUnitDao = orderUnitDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setDealingPatternDao(DealingPatternDao dealingPatternDao) {
		this.dealingPatternDao = dealingPatternDao;
	}

	public void setOrderSheetDao(OrderSheetDao orderSheetDao) {
		this.orderSheetDao = orderSheetDao;
	}

	public void setAllocationDao(AllocationDao allocationDao) {
		this.allocationDao = allocationDao;
	}

	public void setReceivedSheetDao(ReceivedSheetDao receivedSheetDao) {
		this.receivedSheetDao = receivedSheetDao;
	}

	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}
	
	public void setAkadenDao(AkadenDao akadenDao) {
		this.akadenDao = akadenDao;
	}

	public void setOrderBillingDao(OrderBillingDao orderBillingDao) {
		this.orderBillingDao = orderBillingDao;
	}
	
	public void setWebService(WebService webService) {
		this.webService = webService;
	}	
	
	public SheetData loadSheetData(
			User user, String startDate, String endDate, 
			List<Integer> selectedSellerIds, List<Integer> selectedBuyerIds, List<Integer> selectedCategoryIds, 
			Integer sheetTypeId, boolean hasQuantity, boolean csvCall, List<Integer> selectedOrderIdUI) {

		SheetData sheetData = new SheetData();
		String strHasQty = null;
		if(hasQuantity) strHasQty = "true";

		List<String> deliveryDates = DateFormatter.getDateList(startDate, endDate);
		sheetData.setStartDate(startDate);
		sheetData.setEndDate(endDate);
		sheetData.setDeliveryDates(deliveryDates);
		sheetData.setSelectedSellerIds(selectedSellerIds);
		sheetData.setSelectedBuyerIds(selectedBuyerIds);
		sheetData.setSelectedCategoryIds(selectedCategoryIds);
		sheetData.setSheetTypeId(sheetTypeId);
		
		if (CollectionUtils.isEmpty(selectedBuyerIds) || CollectionUtils.isEmpty(selectedSellerIds))  {
			return sheetData;
		}
		sheetData.setSelectedBuyers(usersInfoDaos.mapUserNames(selectedBuyerIds));
		sheetData.setSelectedSellers(usersInfoDaos.mapUserNames(selectedSellerIds));

		Map<String, Map<Integer, Map<Integer, Order>>> dateSellBuyOrderMap = new HashMap<String, Map<Integer, Map<Integer, Order>>>();
		/* dateSellBuyOrderMap
		 * <date>
		 *   <sellerId>
		 *     <buyerId>
		 *       <order>
		 */
		List<Order> orderList =  new ArrayList<Order>();
		List<Integer> orderIds = new ArrayList<Integer>();
		List<Object> skuList = new ArrayList<Object>();
		Map<Integer, List<Object>> categorySkuMap = new HashMap<Integer, List<Object>>();
		Map<String, Map<String, Map<Integer, Item>>> skuDateBuyOrderItemMap = new HashMap<String, Map<String, Map<Integer, Item>>>();
		/* skuDateBuyOrderItemMap
		 * <skuId>
		 *   <date>
		 *     <buyerId>
		 *       <orderItem>
		 */

		
		// Load all sheet data
		// 0. Insert default orders
		// 1. Dealing Pattern
		// 2. Order
		// 3. SKU
		// 4. Order Items
		
		
		// 0. Default Orders
		Map<String, Map<String, List<Integer>>> dateSellerToBuyerDPMap = new HashMap<String, Map<String, List<Integer>>>();

		if (csvCall) {
			// 1. Dealing Pattern
			// needs refactor
			for (String _date : deliveryDates) {
				Map<Integer, Map<Integer, Order>> sellerBuyerDPMap = new HashMap<Integer, Map<Integer, Order>>();
				Map<String, List<Integer>> sellerToBuyerDPMap = new HashMap<String, List<Integer>>();
				for (Integer sellerId : selectedSellerIds) {
					List<Integer> seller = new ArrayList<Integer>();
					seller.add(sellerId);
					List<User> users = dealingPatternDao
							.getAllBuyerIdsBySellerIds(seller, _date, _date);
					if (users != null && users.size() > 0) {
						Map<Integer, Order> buyers = new HashMap<Integer, Order>();
						List<Integer> buyerList = new ArrayList<Integer>();
						for (User _user : users) {
							buyers.put(_user.getUserId(), null);
							buyerList.add(_user.getUserId());
						} 
						sellerBuyerDPMap.put(sellerId, buyers);
						sellerToBuyerDPMap.put(sellerId.toString(), buyerList);
					}
				}
				dateSellBuyOrderMap.put(_date, sellerBuyerDPMap);
				dateSellerToBuyerDPMap.put(_date, sellerToBuyerDPMap);
				
				// per date check order/order item
				// needs refactor
				webService.insertDefaultOrders(selectedSellerIds, _date, selectedBuyerIds, dateSellerToBuyerDPMap);
			}
	
			// 2. Get Orders
			
			if (sheetTypeId.equals(SheetTypeConstants.SELLER_ORDER_SHEET) || 
				sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ORDER_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.BUYER_ORDER_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_ALLOCATION_SHEET) || 
				sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ALLOCATION_SHEET)){
				orderList = orderDao.getAllOrders(selectedBuyerIds, deliveryDates, selectedSellerIds);
				orderIds = OrderSheetUtil.getOrderIdList(orderList);			
			} else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_BILLING_SHEET) || 
					sheetTypeId.equals(SheetTypeConstants.SELLER_BILLING_SHEET)){
				orderList = orderBillingDao.getBillingOrders(selectedSellerIds, selectedBuyerIds, deliveryDates, null);
			} else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_AKADEN_SHEET) || 
					sheetTypeId.equals(SheetTypeConstants.SELLER_AKADEN_SHEET)){
				// TODO: 		
			} else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_RECEIVED_SHEET) || 
					sheetTypeId.equals(SheetTypeConstants.BUYER_RECEIVED_SHEET)){
				orderList = receivedSheetDao.getPublishedOrdersForReceived(selectedBuyerIds, deliveryDates, selectedSellerIds);
			} else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_BILLING_SHEET) || 
					sheetTypeId.equals(SheetTypeConstants.BUYER_BILLING_SHEET)){
				orderList = orderBillingDao.getBillingOrders(selectedSellerIds, selectedBuyerIds, deliveryDates, 0);		
			}
			
			// if no order list exit
			if(orderList.isEmpty()) return sheetData;
			orderIds = OrderSheetUtil.getOrderIdList(orderList);
			sheetData.setOrderList(orderList);
	
			// insert order into the dealing pattern map
			for (Order order : orderList) {
				String deliveryDate = order.getDeliveryDate();
				Integer buyerId = order.getBuyerId();
				Integer sellerId = order.getSellerId();
				//System.out.println("insert order into DPmap "+deliveryDate+" "+sellerId+" "+buyerId);
	
				Map<Integer, Order> buyerOrderMap = dateSellBuyOrderMap.get(deliveryDate).get(sellerId);
				if (buyerOrderMap != null)
					buyerOrderMap.put(buyerId, order);
			}
	
			sheetData.setDateSellBuyOrderMap(dateSellBuyOrderMap);
		} else {
			orderIds.addAll(selectedOrderIdUI);
		}

		// 3. SKU
		for (Integer categoryId : selectedCategoryIds) {
			if (orderIds != null && orderIds.size() > 0) {
				List<?> _skuUnsortedList = null;
				
				if (sheetTypeId.equals(SheetTypeConstants.SELLER_ORDER_SHEET) || sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ORDER_SHEET)){
					// Refactor unused parameters
					_skuUnsortedList = orderSheetDao.selectDistinctSKUs(orderIds, selectedSellerIds, selectedBuyerIds, deliveryDates, categoryId, sheetTypeId, strHasQty);
					SortingUtil.sortSKUs(user, _skuUnsortedList, categoryId);
				} else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ALLOCATION_SHEET) || sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ALLOCATION_SHEET)){
					// Refactor unused parameters
					if (user.getPreference().getDisplayAllocQty().equals("0") || csvCall){
						_skuUnsortedList = allocationDao.selectDistinctSKUs(orderIds, selectedSellerIds, selectedBuyerIds, deliveryDates, categoryId, strHasQty);
					} else{
						_skuUnsortedList = allocationDao.selectDistinctSKUs(orderIds, selectedSellerIds, selectedBuyerIds, deliveryDates, categoryId, "true");
					}
					SortingUtil.sortSKUs(user, _skuUnsortedList, categoryId);
				} else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_BILLING_SHEET) || sheetTypeId.equals(SheetTypeConstants.SELLER_BILLING_SHEET)){
					_skuUnsortedList = this.getSortedListForBilling(user, orderIds, categoryId, sheetTypeId);
				} else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_AKADEN_SHEET) || sheetTypeId.equals(SheetTypeConstants.SELLER_AKADEN_SHEET)){
					// Refactor unused parameters
					_skuUnsortedList = akadenDao.getDistinctSKUs(orderIds, categoryId, 0, 0);
					SortingUtil.sortSKUs(user, _skuUnsortedList, categoryId);
				} else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET)){
					// Refactor unused parameters
					_skuUnsortedList = orderDao.getPublishedSKUBA(orderIds, selectedSellerIds, selectedBuyerIds, deliveryDates, categoryId, null, strHasQty);
					SortingUtil.sortSKUs(user, _skuUnsortedList, categoryId);
				} else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ORDER_SHEET)){
					// Refactor unused parameters
					_skuUnsortedList = orderDao.getPublishedSKUBA(orderIds, selectedSellerIds, selectedBuyerIds, deliveryDates, categoryId, user.getUserId(), strHasQty);
					SortingUtil.sortSKUs(user, _skuUnsortedList, categoryId);
				} else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_RECEIVED_SHEET) || sheetTypeId.equals(SheetTypeConstants.BUYER_RECEIVED_SHEET)){
					// Refactor unused parameters
					//_skuUnsortedList = receivedSheetDao.selectDistinctSKUs(orderIds, categoryId, sheetTypeId,  0, 0, strHasQty);
					_skuUnsortedList = receivedSheetDao.selectDistinctSKUBAs(orderIds, selectedSellerIds, selectedBuyerIds, deliveryDates, categoryId, strHasQty);
					SortingUtil.sortSKUs(user, _skuUnsortedList, categoryId);
				} else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_BILLING_SHEET) || sheetTypeId.equals(SheetTypeConstants.BUYER_BILLING_SHEET)){
					_skuUnsortedList = this.getSortedListForBuyerBilling(user, orderIds, categoryId);
				}
				
				// Sorting of skus
				//SortingUtil.sortSKUs(user, _skuUnsortedList, categoryId);
				if (CollectionUtils.isEmpty(_skuUnsortedList)){
					_skuUnsortedList = new ArrayList<Object>();
				}
				skuList.addAll(_skuUnsortedList);
				categorySkuMap.put(categoryId, (List<Object>) _skuUnsortedList);
			}
		}
		
		// if no sku list exit
		if(skuList.isEmpty()) return sheetData;		
		sheetData.setSkuList(skuList);
		sheetData.setCategorySkuMap(categorySkuMap);

		// 4. Order Item

		List<List<?>> splitList = CollectionUtil.splitList(skuList, 1000);

		/* SQL WHERE column IN (?,?,?...) IS LIMITED TO 1000 */
		for (List<?> list : splitList) {
			List<Integer> thisSkuIds = new ArrayList<Integer>();
			for (int j = 0; j < list.size(); j++) {
					thisSkuIds.add(((SKU) list.get(j)).getSkuId());
			}
			List<Item> oiList = new ArrayList<Item>();
			if (sheetTypeId.equals(SheetTypeConstants.SELLER_ORDER_SHEET) || sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ORDER_SHEET)){
				oiList.addAll(orderSheetDao.getSellerOrderItemsBulk(thisSkuIds, deliveryDates, selectedBuyerIds, hasQuantity));
				skuDateBuyOrderItemMap.putAll(OrderSheetUtil.convertToItemsBulkMap(oiList, false));
			} else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ALLOCATION_SHEET) || sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ALLOCATION_SHEET)){
				if (user.getPreference().getDisplayAllocQty().equals("0") || csvCall){
					oiList.addAll(allocationDao.getSellerAllocItemsBulk(orderIds, thisSkuIds, hasQuantity));	
				} else { 
					oiList.addAll(allocationDao.getSellerAllocItemsBulk(orderIds, thisSkuIds, true));
				}	
				skuDateBuyOrderItemMap.putAll(OrderSheetUtil.convertToItemsBulkMap(oiList, false));
			} else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ORDER_SHEET) || sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET)){
				oiList.addAll(orderSheetDao.getBuyerOrderItemsBulk(orderIds, thisSkuIds, hasQuantity));
				skuDateBuyOrderItemMap.putAll(OrderSheetUtil.convertToItemsBulkMap(oiList, true));
			} else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_RECEIVED_SHEET) || sheetTypeId.equals(SheetTypeConstants.BUYER_RECEIVED_SHEET)){
				oiList.addAll(receivedSheetDao.getReceivedItemsBulk(orderIds, thisSkuIds, hasQuantity));
				skuDateBuyOrderItemMap.putAll(OrderSheetUtil.convertToItemsBulkMap(oiList, true));
			} else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_BILLING_SHEET) || sheetTypeId.equals(SheetTypeConstants.BUYER_BILLING_SHEET)) {
//				oiList.addAll(orderBillingDao.getBillingItemsBulk(orderIds, thisSkuIds, hasQuantity));
//				oiList.addAll(akadenDao.getAkadenItemsBulkForBilling(orderIds, thisSkuIds, hasQuantity));
				oiList.addAll(this.getAllBillingItems(orderIds, thisSkuIds, hasQuantity));
				skuDateBuyOrderItemMap.putAll(OrderSheetUtil.convertToItemsBulkMap(oiList, true));
			} else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_BILLING_SHEET) || sheetTypeId.equals(SheetTypeConstants.SELLER_BILLING_SHEET)) {
//				oiList.addAll(orderBillingDao.getBillingItemsBulk(orderIds, thisSkuIds, hasQuantity));
//				oiList.addAll(akadenDao.getAkadenItemsBulkForBilling(orderIds, thisSkuIds, hasQuantity));
				oiList.addAll(this.getAllBillingItems(orderIds, thisSkuIds, hasQuantity));
				skuDateBuyOrderItemMap.putAll(OrderSheetUtil.convertToItemsBulkMap(oiList, false));
			}
		}
		
		sheetData.setSkuDateBuyOrderItemMap(skuDateBuyOrderItemMap);

		if (csvCall) {
			List<OrderUnit> orderUnitList = new ArrayList<OrderUnit>();
			Map<Integer, OrderUnit> orderUnitMap = new HashMap<Integer, OrderUnit>();
			orderUnitList = orderUnitDao.getAllOrderUnit();
			for (OrderUnit uom: orderUnitList){
				orderUnitMap.put(uom.getOrderUnitId(), uom);
			}
			sheetData.setOrderUnitMap(orderUnitMap);
		}

		List<OrderUnit> sellingUomList = new ArrayList<OrderUnit>();
		Map<Integer, OrderUnit> sellingUomMap = new HashMap<Integer, OrderUnit>();
		sellingUomList = orderUnitDao.getAllSellingUomList();
		for (OrderUnit sellingUom : sellingUomList) {
			sellingUomMap.put(sellingUom.getOrderUnitId(), sellingUom);
		}
		sheetData.setSellingUomMap(sellingUomMap);
		
		return sheetData;
	}
	
	private List<AkadenSKU> getSortedListForBilling (User user, List<Integer> orderIds, Integer categoryId, Integer sheetTypeId) {
		List<AkadenSKU> list = 
			orderBillingDao.selectDistinctSKUs(orderIds, categoryId, sheetTypeId, 0, 0);
		List<AkadenSKU> billingSKU = new ArrayList<AkadenSKU>();
		List<AkadenSKU> akadenSKU = new ArrayList<AkadenSKU>();
		for (AkadenSKU allSkuObj: list){
			if(allSkuObj.getTypeFlag().equals("0")){
				billingSKU.add(allSkuObj);
			} else {
				akadenSKU.add(allSkuObj);
			}
		}
		SortingUtil.sortSKUs(user, billingSKU, categoryId);
		List<AkadenSKU> sortedSkuObjs = new ArrayList<AkadenSKU>();
		sortedSkuObjs.addAll(billingSKU);
		sortedSkuObjs.addAll(akadenSKU);
		return sortedSkuObjs;
	}

	private List<AkadenSKU> getSortedListForBuyerBilling (User user, List<Integer> orderIds, Integer categoryId) {
		List<AkadenSKU> list = 
			orderBillingDao.selectDistinctSKUBA(orderIds, categoryId);
		List<AkadenSKU> billingSKU = new ArrayList<AkadenSKU>();
		List<AkadenSKU> akadenSKU = new ArrayList<AkadenSKU>();
		for (AkadenSKU allSkuObj: list){
			if(allSkuObj.getTypeFlag().equals("0")){
				billingSKU.add(allSkuObj);
			} else {
				akadenSKU.add(allSkuObj);
			}
		}
		SortingUtil.sortSKUs(user, billingSKU, categoryId);
		List<AkadenSKU> sortedSkuObjs = new ArrayList<AkadenSKU>();
		sortedSkuObjs.addAll(billingSKU);
		sortedSkuObjs.addAll(akadenSKU);
		return sortedSkuObjs;
	}

	public List<Item> getAllBillingItems(List<Integer> orderIds, List<Integer> thisSkuIds, boolean hasQuantity) {
		
		List<Item> akadenItems = new ArrayList<Item>();
		List<Item> billingItems = new ArrayList<Item>();
		akadenItems.addAll(akadenDao.getAkadenItemsBulkForBilling(orderIds, thisSkuIds, hasQuantity));
		billingItems.addAll( this.removeBillingItemsWithAkaden(
			orderBillingDao.getBillingItemsBulk(orderIds, thisSkuIds, hasQuantity), 
			akadenItems));
		
		billingItems.addAll(akadenItems);
		
		return billingItems;
	}
	
	public List<Item> removeBillingItemsWithAkaden(List<BillingItem> billingList, 
			List<Item> akadenItems) {
		
		List<Item> newBillingItem = new ArrayList<Item>();
		
		for (Item item : billingList) {
			String skuId = item.getSKUId().toString();
			String deliveryDate = item.getDeliveryDate();
			Integer buyerId = item.getBuyerId();
			
			if (!OrderSheetUtil.isItemExisting(skuId, deliveryDate, buyerId, akadenItems)) {
				newBillingItem.add(item);
			}
		}
		
		return newBillingItem;
	}
}
