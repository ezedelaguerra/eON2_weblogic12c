package com.freshremix.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freshremix.ui.model.FilteredIDs;

public class SheetData {

	//For selected inputs
	private String startDate;
	private String endDate;
	private List<String> deliveryDates;
	private Integer sheetTypeId;
	private List<Integer> selectedSellerIds;
	private List<Integer> selectedBuyerIds;
	private List<Integer> selectedCategoryIds;
	
	//For combo box & lists population
	private List<FilteredIDs> sellersList;
	private List<FilteredIDs> buyersList;
	
	//Sheet Data
	private Map<Integer, User> selectedBuyers;
	private Map<Integer, User> selectedSellers;
	private List<Order> orderList;
	private List<Integer> selectedOrderIds;
	private List<Object> skuList;
	private Map<Integer, List<Object>> categorySkuMap;
	private List<AkadenSKU> skuAkadenList;
	private List<OrderItem> orderItemList;
	private Map<String, Map<String, Map<Integer, Item>>> skuDateBuyOrderItemMap;
	//private Map<Integer, Map<String, Map<Integer, OrderReceived>>> skuDateBuyOrderReceivedMap;
	/* skuDateBuyOrderItemMap
	 * <skuId>
	 *   <date>
	 *     <buyerId>
	 *       <orderItem>
	 */
	private Map<String, Map<Integer, Map<Integer, Order>>> dateSellBuyOrderMap;
	/* dateSellBuyOrderMap
	 * <date>
	 *   <sellerId>
	 *     <buyerId>
	 *       <order>
	 */	
	Map<Integer, OrderUnit> orderUnitMap;
	Map<Integer, OrderUnit> sellingUomMap;
	
	public SheetData() {
		super();
		startDate = "";
		endDate = "";
		deliveryDates = new ArrayList<String>();
		sheetTypeId = new Integer(0);
		selectedSellerIds = new ArrayList<Integer>();
		selectedBuyerIds = new ArrayList<Integer>();
		selectedCategoryIds = new ArrayList<Integer>();
		sellersList = new ArrayList<FilteredIDs>();
		buyersList = new ArrayList<FilteredIDs>();
		selectedBuyers = new HashMap<Integer, User>();
		selectedSellers = new HashMap<Integer, User>();
		orderList = new ArrayList<Order>();
		selectedOrderIds = new ArrayList<Integer>();
		skuList = new ArrayList<Object>();
		categorySkuMap = new HashMap<Integer, List<Object>>();
		skuAkadenList = new ArrayList<AkadenSKU>();
		orderItemList = new ArrayList<OrderItem>();
		skuDateBuyOrderItemMap = new HashMap<String, Map<String, Map<Integer, Item>>>();
		dateSellBuyOrderMap = new HashMap<String, Map<Integer, Map<Integer, Order>>>();
		orderUnitMap = new HashMap<Integer, OrderUnit>();
		sellingUomMap = new HashMap<Integer, OrderUnit>();
	}
	
	public List<SKU> getListOfSku() {
		
		List<SKU> list = new ArrayList<SKU>();
		
		for (Object obj : this.skuList) {
			if (obj instanceof SKU)
				list.add((SKU)obj);
		}
		
		return list;
	}
	
	public List<SKUBA> getListOfSkuBa() {
		
		List<SKUBA> list = new ArrayList<SKUBA>();
		
		for (Object obj : this.skuList) {
			if (obj instanceof SKUBA)
				list.add((SKUBA)obj);
		}
		
		return list;
	}

	// Getters and Setters
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<String> getDeliveryDates() {
		return deliveryDates;
	}
	public void setDeliveryDates(List<String> deliveryDates) {
		this.deliveryDates = deliveryDates;
	}
	public Integer getSheetTypeId() {
		return sheetTypeId;
	}
	public void setSheetTypeId(Integer sheetTypeId) {
		this.sheetTypeId = sheetTypeId;
	}
	public List<Integer> getSelectedSellerIds() {
		return selectedSellerIds;
	}
	public void setSelectedSellerIds(List<Integer> selectedSellerIds) {
		this.selectedSellerIds = selectedSellerIds;
	}
	public List<Integer> getSelectedBuyerIds() {
		return selectedBuyerIds;
	}
	public void setSelectedBuyerIds(List<Integer> selectedBuyerIds) {
		this.selectedBuyerIds = selectedBuyerIds;
	}
	public List<Integer> getSelectedCategoryIds() {
		return selectedCategoryIds;
	}
	public void setSelectedCategoryIds(List<Integer> selectedCategoryIds) {
		this.selectedCategoryIds = selectedCategoryIds;
	}
	public List<FilteredIDs> getSellersList() {
		return sellersList;
	}
	public void setSellersList(List<FilteredIDs> sellersList) {
		this.sellersList = sellersList;
	}
	public List<FilteredIDs> getBuyersList() {
		return buyersList;
	}
	public void setBuyersList(List<FilteredIDs> buyersList) {
		this.buyersList = buyersList;
	}
	public Map<Integer, User> getSelectedBuyers() {
		return selectedBuyers;
	}
	public void setSelectedBuyers(Map<Integer, User> selectedBuyers) {
		this.selectedBuyers = selectedBuyers;
	}
	public Map<Integer, User> getSelectedSellers() {
		return selectedSellers;
	}
	public void setSelectedSellers(Map<Integer, User> selectedSellers) {
		this.selectedSellers = selectedSellers;
	}
	public List<Order> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	public List<Integer> getSelectedOrderIds() {
		return selectedOrderIds;
	}
	public void setSelectedOrderIds(List<Integer> selectedOrderIds) {
		this.selectedOrderIds = selectedOrderIds;
	}
	public List<Object> getSkuList() {
		return skuList;
	}
	public List<Object> getSkuList(Integer categoryId) {
		return categorySkuMap.get(categoryId);
	}
	public void setSkuList(List<Object> skuList) {
		this.skuList = skuList;
	}
	public List<AkadenSKU> getSkuAkadenList() {
		return skuAkadenList;
	}
	public void setSkuAkadenList(List<AkadenSKU> skuAkadenList) {
		this.skuAkadenList = skuAkadenList;
	}
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public Map<String, Map<String, Map<Integer, Item>>> getSkuDateBuyOrderItemMap() {
		return skuDateBuyOrderItemMap;
	}
	public void setSkuDateBuyOrderItemMap(
			Map<String, Map<String, Map<Integer, Item>>> skuDateBuyOrderItemMap) {
		this.skuDateBuyOrderItemMap = skuDateBuyOrderItemMap;
	}
	public Map<String, Map<Integer, Map<Integer, Order>>> getDateSellBuyOrderMap() {
		return dateSellBuyOrderMap;
	}
	public void setDateSellBuyOrderMap(
			Map<String, Map<Integer, Map<Integer, Order>>> dateSellBuyOrderMap) {
		this.dateSellBuyOrderMap = dateSellBuyOrderMap;
	}
	public Map<Integer, List<Object>> getCategorySkuMap() {
		return categorySkuMap;
	}
	public void setCategorySkuMap(Map<Integer, List<Object>> categorySkuMap) {
		this.categorySkuMap = categorySkuMap;
	}
	public Map<Integer, OrderUnit> getOrderUnitMap() {
		return orderUnitMap;
	}
	public void setOrderUnitMap(Map<Integer, OrderUnit> orderUnitMap) {
		this.orderUnitMap = orderUnitMap;
	}
	public Map<Integer, OrderUnit> getSellingUomMap() {
		return sellingUomMap;
	}
	public void setSellingUomMap(Map<Integer, OrderUnit> sellingUomMap) {
		this.sellingUomMap = sellingUomMap;
	}

}
