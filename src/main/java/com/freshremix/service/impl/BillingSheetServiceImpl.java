package com.freshremix.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jettison.json.JSONObject;

import com.freshremix.constants.BillingAkadenSkuIdConstants;
import com.freshremix.dao.OrderAkadenDao;
import com.freshremix.dao.OrderBillingDao;
import com.freshremix.model.AkadenItem;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.BillingItem;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.MailSender;
import com.freshremix.model.Order;
import com.freshremix.model.SKU;
import com.freshremix.service.BillingSheetService;
import com.freshremix.service.SKUService;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.ui.model.OrderItemUI;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.StringUtil;

public class BillingSheetServiceImpl implements BillingSheetService {

	private OrderBillingDao orderBillingDao;
	private OrderAkadenDao orderAkadenDao;
	private SKUService skuService;
//	private OrderDao orderDao;

	public void setSkuService(SKUService skuService) {
		this.skuService = skuService;
	}
	public void setOrderAkadenDao(OrderAkadenDao orderAkadenDao) {
		this.orderAkadenDao = orderAkadenDao;
	}
	public void setOrderBillingDao(OrderBillingDao orderBillingDao) {
		this.orderBillingDao = orderBillingDao;
	}
//	public void setOrderDao(OrderDao orderDao) {
//		this.orderDao = orderDao;
//	}
	
	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#getBillingOrders(java.util.List, java.util.List, java.util.List, java.lang.Integer)
	 */
	@Override
	public List<Order> getBillingOrders(List<Integer> sellerIds,
			List<Integer> buyerIds, List<String> deliveryDates, Integer isBuyer) {
		return orderBillingDao.getBillingOrders(sellerIds, buyerIds, deliveryDates, isBuyer);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#getOrdersByOrderId(java.util.List)
	 */
	@Override
	public List<Order> getOrdersByOrderIds(List<Integer> orderIds) {
		return null;//orderBillingDao.getOrdersByOrderIds(orderIds);
	}
	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#getSelectedOrdersByBuyer(java.util.List, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Order> getSelectedOrdersByBuyer(List<Integer> sellerIds,
			Integer buyerId, String startDate, String endDate) {
		List<String> deliveryDates = DateFormatter.getDateList(startDate, endDate);
		return orderBillingDao.getSelectedOrdersByBuyer(sellerIds, buyerId, deliveryDates);
	}
	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#getSelectedOrdersByDate(java.util.List, java.util.List, java.lang.String)
	 */
	@Override
	public List<Order> getSelectedOrdersByDate(List<Integer> sellerIds,
			List<Integer> buyerIds, String selectedDate) {
		return orderBillingDao.getSelectedOrdersByDate(sellerIds, buyerIds, selectedDate);
	}
	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#insertDefaultOrders(com.freshremix.model.OrderSheetParam, com.freshremix.model.User, com.freshremix.model.DealingPattern)
	 */
//	@Override
//	public void insertDefaultOrders(OrderSheetParam orderSheetParam, User user,
//			DealingPattern dp) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
	public Order combineOrdersStatus(List<Order> orders) {
		Order order = new Order();
		order.setOrderSavedBy(99999);
		order.setOrderPublishedBy(99999);
		order.setOrderLockedBy(99999);
		order.setOrderUnlockedBy(99999);
		order.setOrderFinalizedBy(99999);
		order.setOrderUnfinalizedBy(99999);
		order.setAllocationPublishedBy(99999);
		order.setOrderPublishedByBA(99999);
		order.setBillingFinalizedBy(99999);
		order.setBillingUnfinalizedBy(99999);
		for (Order _order : orders) {
			if (NumberUtil.isNullOrZero(_order.getOrderSavedBy()))
				order.setOrderSavedBy(null);
			if (NumberUtil.isNullOrZero(_order.getOrderPublishedBy()))
				order.setOrderPublishedBy(null);
			if (NumberUtil.isNullOrZero(_order.getOrderLockedBy()))
				order.setOrderLockedBy(null);
			if (NumberUtil.isNullOrZero(_order.getOrderUnlockedBy()))
				order.setOrderUnlockedBy(null);
			if (NumberUtil.isNullOrZero(_order.getOrderFinalizedBy()))
				order.setOrderFinalizedBy(null);
			if (NumberUtil.isNullOrZero(_order.getOrderUnfinalizedBy()))
				order.setOrderUnfinalizedBy(null);
			if (NumberUtil.isNullOrZero(_order.getAllocationPublishedBy()))
				order.setAllocationPublishedBy(null);
			if (NumberUtil.isNullOrZero(_order.getOrderPublishedByBA()))
				order.setOrderPublishedByBA(null);
			if (NumberUtil.isNullOrZero(_order.getBillingFinalizedBy()))
				order.setOrderFinalizedBy(null);
			if (NumberUtil.isNullOrZero(_order.getBillingUnfinalizedBy()))
				order.setOrderUnfinalizedBy(null);
		}
		return order;
	}
	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#getDistinctSKUs(java.util.List, java.lang.Integer, java.lang.Integer, java.util.List, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<AkadenSKU> getDistinctSKUs(List<Integer> orderIds,
			Integer categoryId, Integer sheetTypeId, 
			Integer rowStart, Integer pageSize) {
		return orderBillingDao.selectDistinctSKUs(orderIds, categoryId, sheetTypeId, rowStart, pageSize);
	}
	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#loadOrderItemQuantities(java.util.Map, java.util.List, java.lang.String, com.freshremix.model.SKU)
	 */
	@Override
	public void loadOrderItemQuantities(Map<String, Object> skuOrderMap,
			List<Integer> companyBuyerIds, String deliveryDate, AkadenSKU skuObj, JSONObject json) throws Exception{
		
		Integer skuId = skuObj.getSkuId();
		BigDecimal rowqty = new BigDecimal(0);
		String comments = "";
		int chkCtr = 0;
		if(skuObj.getTypeFlag().equals(BillingAkadenSkuIdConstants.BILLING_TYPE_FLAG)){
			
			Map<Integer, BillingItem> billingItemMap = new HashMap <Integer, BillingItem>();
			billingItemMap = orderBillingDao.getBillingItemsByBuyers(companyBuyerIds, deliveryDate, skuId);
			
			for (Integer buyerId : companyBuyerIds) {
				BillingItem billingItem = billingItemMap.get(buyerId);
				BigDecimal quantity = null;
//				String strOrderId = "";
				String strQuantity = "";
//				String strFinalizedBy = "";
				String strLockFlag = "0";
				String skuFlag = "0";
				
				if (billingItem != null) {
					
//					strOrderId = billingItem.getOrder().getOrderId().toString();
					comments = billingItem.getComments();
					if(billingItem.getQuantity() != null) {
						quantity = billingItem.getQuantity();
						strQuantity = quantity.toPlainString();
						rowqty = rowqty.add(quantity);
					}
					if(billingItem.getOrder().getOrderFinalizedBy() != null){
//						strFinalizedBy = billingItem.getOrder().getOrderFinalizedBy().toString();
						skuFlag = "1";
						strLockFlag = "1";
					}
					
//					System.out.println("orderId:[" + strOrderId + "]");
//					System.out.println("quantity:[" + quantity + "]");
//					System.out.println("finalizedBy:[" + strFinalizedBy + "]");
					
				}
				else {
					strLockFlag = "1";
				}

				json.put("Q_" + buyerId.toString(), strLockFlag);
				json.put("V_" + buyerId.toString(), strLockFlag);
				if (!json.has("sku") && skuFlag.equals("1"))
					json.put("sku", skuFlag);
				skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
				skuOrderMap.put("C_" + buyerId.toString(), StringUtil.nullToBlank(comments));
			}
			if (chkCtr == companyBuyerIds.size()) skuOrderMap.put("visall", "1");
			else skuOrderMap.put("visall", "0");
			
			skuOrderMap.put("comments", StringUtil.nullToBlank(comments));
			setSkuLockingB(billingItemMap, json);
			skuOrderMap.put("rowqty", rowqty);
			
		}else{
			
			Map<Integer, AkadenItem> akadenItemMap = new HashMap <Integer, AkadenItem>();
			
			//if (!NumberUtil.isNullOrZero(skuObj.getAkadenSkuId())){
				akadenItemMap = orderAkadenDao.getAkadenItemsMapOfSkuDate(deliveryDate, skuId, skuObj.getAkadenSkuId());
			//}else{
				//akadenItemMap = orderAkadenDao.getAkadenItemsMapOfSkuDate(deliveryDate, skuId, null);
			//}
			
			for (Integer buyerId : companyBuyerIds) {
				AkadenItem akadenItem = akadenItemMap.get(buyerId);
				BigDecimal quantity = null;
//				String strOrderId = "";
				String strQuantity = "";
//				String strFinalizedBy = "";
				String strLockFlag = "0";
				String skuFlag = "0";
				
				if (akadenItem != null) {
					
//					strOrderId = akadenItem.getOrder().getOrderId().toString();
					comments = akadenItem.getComments();
					if(akadenItem.getQuantity() != null) {
						quantity = akadenItem.getQuantity();
						strQuantity = quantity.toPlainString();
						rowqty = rowqty.add(quantity);
					}

					skuFlag = "1";
					strLockFlag = "1";
//					if(akadenItem.getOrder().getOrderFinalizedBy() != null){
//						strFinalizedBy = akadenItem.getOrder().getOrderFinalizedBy().toString();
//						skuFlag = "1";
//						strLockFlag = "1";
//					}
//					System.out.println("orderId:[" + strOrderId + "]");
//					System.out.println("quantity:[" + quantity + "]");
//					System.out.println("finalizedBy:[" + strFinalizedBy + "]");
					
				}
				else {
					strLockFlag = "1";
				}

				json.put("Q_" + buyerId.toString(), strLockFlag);
				json.put("V_" + buyerId.toString(), strLockFlag);
				if (!json.has("sku") && skuFlag.equals("1"))
					json.put("sku", skuFlag);
				skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
				skuOrderMap.put("C_" + buyerId.toString(), StringUtil.nullToBlank(comments));
			}
			if (chkCtr == companyBuyerIds.size()) skuOrderMap.put("visall", "1");
			else skuOrderMap.put("visall", "0");
			
			skuOrderMap.put("comments", StringUtil.nullToBlank(comments));
			setSkuLockingA(akadenItemMap, json);
			skuOrderMap.put("rowqty", rowqty);
		}
	}
	
	private void setSkuLockingB(Map<Integer, BillingItem> orderItemMap,
			JSONObject json) throws Exception {
		Set<Integer> keys = orderItemMap.keySet();
		Iterator<Integer> it = keys.iterator();
		while(it.hasNext()) {
			Integer key = it.next();
			BillingItem oi = orderItemMap.get(key);
			if (!NumberUtil.isNullOrZero(oi.getOrder().getOrderFinalizedBy())) {
				json.put("sku", "1");
			}
		}
	}
	
	private void setSkuLockingA(Map<Integer, AkadenItem> orderItemMap,
			JSONObject json) throws Exception {
		Set<Integer> keys = orderItemMap.keySet();
		Iterator<Integer> it = keys.iterator();
		while(it.hasNext()) {
			Integer key = it.next();
			AkadenItem oi = orderItemMap.get(key);
			if (!NumberUtil.isNullOrZero(oi.getOrder().getOrderFinalizedBy())) {
				json.put("sku", "1");
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#loadOrderItemQuantities(java.util.Map, java.util.List, java.lang.Integer, com.freshremix.model.SKU)
	 */
	@Override
	public void loadOrderItemQuantities(Map<String, Object> skuOrderMap,
			List<String> deliveryDates, Integer buyerId, AkadenSKU skuObj, JSONObject json) throws Exception{
		
		Integer skuId = skuObj.getSkuId();
		BigDecimal rowqty = new BigDecimal(0);
		String comments = "";
		
		if(skuObj.getTypeFlag().equals(BillingAkadenSkuIdConstants.BILLING_TYPE_FLAG)){
			
			Map<Integer, BillingItem> billingItemMap = new HashMap <Integer, BillingItem>();
			billingItemMap = orderBillingDao.getBillingItemsByDates(deliveryDates, skuId, buyerId);
			
			for (String deliveryDate : deliveryDates) {
				BillingItem billingItem = billingItemMap.get(deliveryDate);
				BigDecimal quantity = null;
				String strQuantity = "";
//				String strOrderId = "";
//				String strFinalizedBy = "";
				String strLockFlag = "0";
				
				if (billingItem != null) {
//					strOrderId = billingItem.getOrder().getOrderId().toString();
					comments = billingItem.getComments();
					if(billingItem.getQuantity() != null) {
						quantity = billingItem.getQuantity();
						strQuantity = quantity.toPlainString();
						rowqty = rowqty.add(quantity);
					}
					if(billingItem.getOrder().getOrderFinalizedBy() != null){
//						strFinalizedBy = billingItem.getOrder().getOrderFinalizedBy().toString();
						json.put("sku", 1);
						strLockFlag = "1";
					}
//					System.out.println("orderId:[" + strOrderId + "]");
//					System.out.println("quantity:[" + quantity + "]");
//					System.out.println("finalizedBy:[" + strFinalizedBy + "]");
				}
				else {
					strLockFlag = "1";
				}
				json.put("Q_" + deliveryDate, strLockFlag);
				skuOrderMap.put("Q_" + deliveryDate, strQuantity);
				skuOrderMap.put("C_" + deliveryDate, StringUtil.nullToBlank(comments));
			}

			skuOrderMap.put("comments", StringUtil.nullToBlank(comments));
			skuOrderMap.put("rowqty", rowqty);
			skuOrderMap.put("lockflag", json.toString());
			
			
		}else{
			Map<Integer, AkadenItem> AkadenItemMap = new HashMap <Integer, AkadenItem>();
			
			//if (!NumberUtil.isNullOrZero(skuObj.getSkuId())){
				AkadenItemMap = orderAkadenDao.getAkadenItemsMapOfSkuDates(deliveryDates, skuId, buyerId, skuObj.getAkadenSkuId());
			//}else{
				//AkadenItemMap = orderAkadenDao.getAkadenItemsMapOfSkuDates(deliveryDates, skuId, buyerId, null);
			//}
			
			for (String deliveryDate : deliveryDates) {
				AkadenItem akadenItem = AkadenItemMap.get(deliveryDate);
				BigDecimal quantity = null;
				String strQuantity = "";
//				String strOrderId = "";
//				String strFinalizedBy = "";
				String strLockFlag = "0";
				
				if (akadenItem != null) {
//					strOrderId = akadenItem.getOrder().getOrderId().toString();
					comments = akadenItem.getComments();
					if(akadenItem.getQuantity() != null) {
						quantity = akadenItem.getQuantity();
						strQuantity = quantity.toPlainString();
						rowqty = rowqty.add(quantity);
					}

					json.put("sku", 1);
					strLockFlag = "1";
//					if(akadenItem.getOrder().getOrderFinalizedBy() != null){
//						strFinalizedBy = akadenItem.getOrder().getOrderFinalizedBy().toString();
//						json.put("sku", 1);
//						strLockFlag = "1";
//					}
					
//					System.out.println("orderId:[" + strOrderId + "]");
//					System.out.println("quantity:[" + quantity + "]");
//					System.out.println("finalizedBy:[" + strFinalizedBy + "]");
				}
				else {
					strLockFlag = "1";
				}
				json.put("Q_" + deliveryDate, strLockFlag);
				skuOrderMap.put("Q_" + deliveryDate, strQuantity);
				skuOrderMap.put("C_" + deliveryDate, StringUtil.nullToBlank(comments));
			}
			skuOrderMap.put("comments", StringUtil.nullToBlank(comments));
			skuOrderMap.put("rowqty", rowqty);
			skuOrderMap.put("lockflag", json.toString());
		}
	}
	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#saveOrder(com.freshremix.ui.model.OrderForm, com.freshremix.ui.model.OrderDetails, java.util.Map, java.util.List)
	 */
	@Override
	public void saveOrder(OrderForm orderForm, OrderDetails orderDetails,
			Map<String, Order> allOrdersMap, Map<Integer, AkadenSKU> skuObjMap) {

		List<Integer> sellerIds = orderDetails.getSellerIds();
		List<OrderItemUI> insertedItemList = orderForm.getInsertedOrders();
		List<OrderItemUI> updatedItemList = orderForm.getUpdatedOrders();
		List<OrderItemUI> deletedItemList = orderForm.getDeletedOrders();
		Integer userId = orderDetails.getUser().getUserId(); 
		
		if (orderDetails.isAllDatesView()) {

			List<String> deliveryDates = DateFormatter.getDateList(orderDetails.getStartDate(), orderDetails.getEndDate());
			Integer buyerId = orderDetails.getDatesViewBuyerID();

			//Insert
			for (OrderItemUI oi : insertedItemList) {
//				System.out.println("Insert by deliveryDate");
				
				SKU sku = OrderSheetUtil.toSKU(oi, orderDetails);
				sku.setOrigSkuId(null);
				skuService.insertSKU(sku);
				
				Map<String, BigDecimal> qtyMap = oi.getQtyMap();
				
				for (String deliveryDate : deliveryDates) {
					//for (Integer sellerId : sellerIds) {
					String sSellerId = oi.getSellerId().toString();
					Order order = allOrdersMap.get(deliveryDate + "_" + buyerId.toString() + "_" + sSellerId);
					
//					Integer orderId;
					if (order == null) continue;

					BigDecimal quantity = null;
					if (qtyMap.get("Q_" + deliveryDate) != null)
						quantity = qtyMap.get("Q_" + deliveryDate);
//					orderId = order.getOrderId();
//					Integer skuId = sku.getSkuId();
					BillingItem billingItem = new BillingItem();
					billingItem.setOrder(order);
					billingItem.setQuantity(quantity);
					billingItem.setSku(sku);
					billingItem.setUpdateBy(userId);
					orderBillingDao.insertBillingItem(billingItem);
				}
			}
			//Update
			for (OrderItemUI oi : updatedItemList) {
//				System.out.println("Update by deliveryDate");
				
				SKU sku = OrderSheetUtil.toSKU(oi, orderDetails);
//				//to do: check if with previous order
//				if (skuObjMap.containsKey(sku.getSkuId())){
//					AkadenSKU akadenSku= skuObjMap.get(sku.getSkuId());
//					SKU origSku = OrderSheetUtil.toSKU(akadenSku);
//					if(!origSku.equals(sku)){
//						if(origSku.getSheetType().equals(sku.getSheetType()))
//							skuService.updateNewSKU(sku);
//						else skuService.insertSKU(sku);
//					}
//				}
				skuService.updateNewSKU(sku);
				
				Map<String, BigDecimal> qtyMap = oi.getQtyMap();
				for (String deliveryDate : deliveryDates) {
					//for (Integer sellerId : sellerIds) {
					String sSellerId = oi.getSellerId().toString();
					Order order = allOrdersMap.get(deliveryDate + "_" + buyerId.toString() + "_" + sSellerId);
					
					if (order == null) continue;
					BigDecimal quantity = null;
					if (qtyMap.get("Q_" + deliveryDate) != null)
						quantity = qtyMap.get("Q_" + deliveryDate);
					Integer orderId = order.getOrderId();
					Integer origSkuId = oi.getSkuId();
					Integer skuId = sku.getSkuId();
					orderBillingDao.updateBillingItem(skuId, userId, quantity, origSkuId, orderId);	
					orderBillingDao.updateBillingOrder(userId, orderId);
					//}
				}
			}
			//Delete
			List<Integer> skuToDelete = new ArrayList<Integer>();
			for(OrderItemUI oi : deletedItemList) {
				skuToDelete.add(oi.getSkuId().intValue());
			}
			
			if (skuToDelete.size() > 0) {
				for (String deliveryDate : deliveryDates) {
//					String sBuyerId = buyerId.toString();
					for (Integer sellerId : sellerIds) {
						String sSellerId = sellerId.toString();
						Order order = allOrdersMap.get(deliveryDate + "_" + buyerId.toString() + "_" + sSellerId);
						
						if (order == null) continue;
						
						orderBillingDao.deleteBillingItemsByOrderIdAndSkuIds(order.getOrderId(), skuToDelete);
					}
				}
			}
		}else{ //save by buyer ids view
			

			List<Integer> buyerIds = orderDetails.getBuyerIds();
			String deliveryDate = orderDetails.getDeliveryDate();
			
			//Insert
			for (OrderItemUI oi : insertedItemList) {
//				System.out.println("Insert by buyerId");
				SKU sku = OrderSheetUtil.toSKU(oi, orderDetails);
				sku.setOrigSkuId(null);
				skuService.insertSKU(sku);
				Map<String, BigDecimal> qtyMap = oi.getQtyMap();
				for (Integer buyerId : buyerIds) {
					String sBuyerId = buyerId.toString();
					//for (Integer sellerId : sellerIds) {
					String sSellerId = oi.getSellerId().toString();
					Order order = allOrdersMap.get(deliveryDate + "_" + sBuyerId + "_" + sSellerId);
					
//					Integer orderId;
					if (order == null) continue;
//					{
//						order.setSellerId(oi.getSellerId());
//						order.setBuyerId(buyerId);
//						order.setDeliveryDate(deliveryDate);
//						order.setOrderSavedBy(userId);
//						order.setAllocationFinalizedBy(userId);
//						orderId = orderBillingDao.insertBillingOrder(order);
//					}
					BigDecimal quantity = null;
					if (qtyMap.get("Q_" + buyerId.toString())!= null)
						quantity = qtyMap.get("Q_" + buyerId.toString());
//					orderId = order.getOrderId();
//					Integer skuId = oi.getSkuId();
					BillingItem billingItem = new BillingItem();
					billingItem.setOrder(order);
					billingItem.setQuantity(quantity);
					billingItem.setSku(sku);
					billingItem.setUpdateBy(userId);
					orderBillingDao.insertBillingItem(billingItem);
					//}
				}
			}
			//Update
			for (OrderItemUI oi : updatedItemList) {
//				System.out.println("Update by buyerId");
				
				SKU sku = OrderSheetUtil.toSKU(oi, orderDetails);
				//to do: check if with previous order
//				if (skuObjMap.containsKey(sku.getSkuId())){
//					AkadenSKU akadenSku= skuObjMap.get(sku.getSkuId());
//					SKU origSku = OrderSheetUtil.toSKU(akadenSku);
//					if(!origSku.equals(sku)){
//						if(origSku.getSheetType().equals(sku.getSheetType()))
//							skuService.updateNewSKU(sku);
//						else skuService.insertSKU(sku);
//					}
//				}
				skuService.updateNewSKU(sku);
				
				Map<String, BigDecimal> qtyMap = oi.getQtyMap();
				for (Integer buyerId : buyerIds) {
					String sBuyerId = buyerId.toString();
					//for (Integer sellerId : sellerIds) {
						String sSellerId = oi.getSellerId().toString();
						Order order = allOrdersMap.get(deliveryDate + "_" + sBuyerId + "_" + sSellerId);
						
						if (order == null) continue;
						BigDecimal quantity = null;
						if (qtyMap.get("Q_" + buyerId.toString())!= null)
							quantity = qtyMap.get("Q_" + buyerId.toString());
						Integer orderId = order.getOrderId();
						Integer origSkuId = oi.getSkuId();
						Integer skuId = sku.getSkuId();
						orderBillingDao.updateBillingItem(skuId, userId, quantity, origSkuId, orderId);	
						orderBillingDao.updateBillingOrder(userId, orderId);
					//}
				}
			}
			//Delete
			List<Integer> skuToDelete = new ArrayList<Integer>();
			for(OrderItemUI oi : deletedItemList) {
				skuToDelete.add(oi.getSkuId().intValue());
			}
			
			//if (skuToDelete.size() > 0) {
			for(OrderItemUI oi : deletedItemList) {
				for (Integer buyerId : buyerIds) {
					String sBuyerId = buyerId.toString();
					//for (Integer sellerId : sellerIds) {
						String sSellerId = oi.getSellerId().toString();
						Order order = allOrdersMap.get(deliveryDate + "_" + sBuyerId + "_" + sSellerId);
						
						if (order == null) continue;
						orderBillingDao.deleteBillingItemsByOrderIdAndSkuIds(order.getOrderId(), skuToDelete);
					//}
				}
			}
		}
		
		//if (orderDetails.isAllDatesView()) {
			//this.saveOrderAllDates(orderForm, orderDetails);
			//return;
		//}
		
		/*for (OrderItemUI oi : insertItemList) {
			System.out.println("Insert by buyerId");
			SKU sku = OrderSheetUtil.toSKU(oi, orderDetails);
			skuService.insertSKU(sku);
			Map<String, BigDecimal> qtyMap = oi.getQtyMap();
			for (Order order: orders) {
				for (Integer buyerId : buyerIds) {
					if (order.getBuyerId().equals(buyerId)) {
						if (qtyMap.get("Q_" + buyerId.toString())== null) continue;
						BigDecimal quantity = qtyMap.get("Q_" + buyerId.toString());
						Integer skuId = oi.getSkuId();
						Integer orderId = order.getOrderId();
						//orderDao.updateOrderQtyBySkuIdAndOrderId(quantity, userId, skuId, orderId);	
					}
				}
			}
		}
		Integer sellerId = orderDetails.getSellerId();
		Map<String, Object> param = new HashMap<String,Object>();
		Map<Integer, Integer> buyerOrderIdMap = new HashMap<Integer, Integer>();
		// save to EON_ORDER, EON_ORDER_ITEM
		for (Integer buyerId : orderDetails.getBuyerIds()) {
			Order order = this.getOrderByDeliveryDate(sellerId, buyerId, orderDetails.getDeliveryDate());
			if (order != null) {
				param.put("orderSavedBy", orderDetails.getSellerId());
				param.put("createdBy", orderDetails.getSellerId());
				param.put("orderId", order.getOrderId());
				orderDao.updateSaveOrder(param);
				buyerOrderIdMap.put(buyerId, order.getOrderId());
			}
		}
		
		// save to EON_SKU
		List<OrderItemUI> orderItemList = orderForm.getInsertedOrders();
		for (OrderItemUI oi : orderItemList) {
			SKU sku = OrderSheetUtil.toSKU(oi, orderDetails);
			Map<String, BigDecimal> qtyMap = oi.getQtyMap();
			Map<String, String> visMap = oi.getVisMap();
			skuService.insertSKU(sku);
			// save to EON_ORDER_ITEM
			for (Integer buyerId : orderDetails.getBuyerIds()) {
				param = new HashMap<String,Object>();
				if (buyerOrderIdMap.get(buyerId) == null) continue;
				param.put("orderId", buyerOrderIdMap.get(buyerId));
				param.put("skuId", sku.getSkuId());
				param.put("skuMaxLimit", oi.getSkumaxlimit());
				param.put("quantity", qtyMap.get("Q_" + buyerId.toString()));
				param.put("sosVisFlag", visMap.get("V_" + buyerId.toString()));
				this.insertUpdateOrderItem(param);
			}
		}
		
		orderItemList = orderForm.getUpdatedOrders();
		for (OrderItemUI oi : orderItemList) {
			SKU sku = OrderSheetUtil.toSKU(oi, orderDetails);
			SKU origSku = skuObjMap.get(sku.getSkuId());
			Map<String, BigDecimal> qtyMap = oi.getQtyMap();
			Map<String, String> visMap = oi.getVisMap();
			// check for deleted items
			if (isOrderItemForDeletetion(oi)) {
				// apply implementation
			} else {
				if (skuHasPreviousOrder(oi.getSkuId(), orderDetails.getDeliveryDate()) &&
					!origSku.equals(sku)) {
					skuService.updateNewSKU(sku);
					
					// update EON_ORDER_ITEM
					System.out.println("Update special");
					for (Integer buyerId : orderDetails.getBuyerIds()) {
						param = new HashMap<String,Object>();
						if (buyerOrderIdMap.get(buyerId) == null) continue;
						param.put("orderId", buyerOrderIdMap.get(buyerId));
						param.put("skuId", sku.getSkuId());
						param.put("skuMaxLimit", oi.getSkumaxlimit());
						param.put("quantity", qtyMap.get("Q_" + buyerId.toString()));
						param.put("sosVisFlag", visMap.get("V_" + buyerId.toString()));
						param.put("origSkuId", sku.getOrigSkuId());
						this.insertUpdateOrderItem(param);
					}
				}
				else {
					skuService.updateSKU(OrderSheetUtil.toSKU(oi, orderDetails));
					// update EON_ORDER_ITEM
					System.out.println("Update normal");
					for (Integer buyerId : orderDetails.getBuyerIds()) {
						param = new HashMap<String,Object>();
						if (buyerOrderIdMap.get(buyerId) == null) continue;
						param.put("orderId", buyerOrderIdMap.get(buyerId));
						param.put("skuId", sku.getSkuId());
						param.put("skuMaxLimit", oi.getSkumaxlimit());
						param.put("quantity", qtyMap.get("Q_" + buyerId.toString()));
						param.put("sosVisFlag", visMap.get("V_" + buyerId.toString()));
						param.put("origSkuId", sku.getSkuId());
						this.insertUpdateOrderItem(param);	
					}
				}
			}
		}
		
		orderItemList = orderForm.getDeletedOrders();
		List<Integer> skuToDelete = new ArrayList<Integer>();
		for(OrderItemUI oi : orderItemList) {
			skuToDelete.add(oi.getSkuId().intValue());
		}
		if (skuToDelete.size() > 0) {
			for (Integer buyerId : orderDetails.getBuyerIds()) {
				Integer orderId = buyerOrderIdMap.get(buyerId);
				if (orderId == null) continue;
				this.deleteOrderItems(orderId, skuToDelete);
			}
		}*/
	}
	
	@Override
	public void loadSumOrderBillingQuantitiesAllBuyers(Map<String, Object> skuOrderMap,
			List<Integer> buyerIds, List<Integer> orderIds, AkadenSKU skuObj) {
		
		if(skuObj.getTypeFlag().equals(BillingAkadenSkuIdConstants.BILLING_TYPE_FLAG)){
		
			Map<Integer, BillingItem> orderBillingMap = orderBillingDao.getSumOrderBillingMapOfSkuBuyers(orderIds, skuObj.getSkuId());
		
			for (Integer buyerId : buyerIds) {
				BillingItem orderBilling = orderBillingMap.get(buyerId);
				BigDecimal quantity = null;
				String strQuantity = "";
				
				if (orderBilling != null) {
					quantity = orderBilling.getQuantity();
					
//					System.out.println("quantity:[" + quantity + "]");
					if (quantity != null) {
						strQuantity = quantity.toPlainString();
					}
				}
				else {
					//strQuantity = "-999";
					//strLockFlag = "1"; //locked
				}
				
				skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
			}
		}
		else { //akaden
			Map<Integer, AkadenItem> AkadenItemMap = new HashMap <Integer, AkadenItem>();
			
			if (!NumberUtil.isNullOrZero(skuObj.getAkadenSkuId())){
				AkadenItemMap = orderAkadenDao.getSumAkadenMapOfSkuBuyers(orderIds, skuObj.getSkuId(), skuObj.getAkadenSkuId());
			}
			else {
				AkadenItemMap = orderAkadenDao.getSumAkadenMapOfSkuBuyers(orderIds, skuObj.getSkuId(), null);
			}
			
			for (Integer buyerId : buyerIds) {
				AkadenItem akadenItem = AkadenItemMap.get(buyerId);
				BigDecimal quantity = null;
				String strQuantity = "";
				
				if (akadenItem != null) {
					quantity = akadenItem.getQuantity();
					if (quantity != null) {
						strQuantity = quantity.toPlainString();
					}
				}
				else {
					//strQuantity = "-999";
					//strLockFlag = "1"; //locked
				}
				skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
			}
		}
			
	}
	
	@Override
	public void loadSumOrderBillingQuantitiesAllDates(Map<String, Object> skuOrderMap,
			List<String> deliveryDates, List<Integer> orderIds, AkadenSKU skuObj) {
		
		if(skuObj.getTypeFlag().equals(BillingAkadenSkuIdConstants.BILLING_TYPE_FLAG)){
			
			Map<Integer, BillingItem> orderBillingMap = orderBillingDao.getSumOrderBillingMapOfSkuDates(orderIds, skuObj.getSkuId());
			
			for (String deliveryDate : deliveryDates) {
				BillingItem orderBilling = orderBillingMap.get(deliveryDate);
				
				BigDecimal quantity = null;
				String strQuantity = "";
				
				if (orderBilling != null) {
					quantity = orderBilling.getQuantity();
					
//					System.out.println("quantity:[" + quantity + "]");
					if (quantity != null) {
						strQuantity = quantity.toPlainString();
					}
				}
				else {
					//strQuantity = "-999";
					//strLockFlag = "1"; //locked
				}
				
				skuOrderMap.put("Q_" + deliveryDate, strQuantity);
			}
		}
		else { //akaden
			Map<Integer, AkadenItem> AkadenItemMap = new HashMap <Integer, AkadenItem>();
			
			if (!NumberUtil.isNullOrZero(skuObj.getAkadenSkuId())){
				AkadenItemMap = orderAkadenDao.getSumAkadenMapOfSkuDates(orderIds, skuObj.getSkuId(), skuObj.getAkadenSkuId());
			}
			else {
				AkadenItemMap = orderAkadenDao.getSumAkadenMapOfSkuDates(orderIds, skuObj.getSkuId(), null);
			}
			
			for (String deliveryDate : deliveryDates) {
				AkadenItem akadenItem = AkadenItemMap.get(deliveryDate);
				BigDecimal quantity = null;
				String strQuantity = "";
				
				if (akadenItem != null) {
					quantity = akadenItem.getQuantity();
					if (quantity != null) {
						strQuantity = quantity.toPlainString();
					}
				}
				else {
					//strQuantity = "-999";
					//strLockFlag = "1"; //locked
				}
				
				skuOrderMap.put("Q_" + deliveryDate, strQuantity);
			}
			
		}
		
	}

//	/**
//	 * Checks order item UI if it is for deletion.
//	 *
//	 * @return boolean
//	 * @param oi
//	 * @return
//	 */
//	
//	private boolean isOrderItemForDeletetion(OrderItemUI oi) {
//		//return StringUtil.isNullOrEmpty(oi.getMyselect()) ? false : true;
//		return false;
//	}
	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#finalizeBilling(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateFinalizeBilling(Integer orderId, Integer finalizedBy) {
		orderBillingDao.finalizeBilling(orderId, finalizedBy);
		
	}
	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#unfinalizeBilling(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateUnfinalizeBilling(Integer orderId, Integer unfinalizedBy) {
		orderBillingDao.unfinalizeBilling(orderId, unfinalizedBy);
		
	}
	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#sendMailNotification(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean sendMailNotification(String orderDate, String state,
			String username, String fromAddress, String[] toAddress) {
		
		String subject = null;
		String message = null;
		String date = orderDate.substring(0,4) + "/" + orderDate.substring(4,6) + "/" + orderDate.substring(6,8);
		
		if (state.equals("Finalize")) {
			subject = "Billing Sheet for " + date + " Finalized";
			message = "Billing Sheet for " + date + " Finalized by " + username + " as of " + DateFormatter.getDateToday("yyyy/MM/dd h:mm a") + ".";
		} else if (state.equals("Unfinalize")) {
			subject = "Billing Sheet for " + date + " Unfinalized";
			message = "Billing Sheet for " + date + " Unfinalized by " + username + " as of " + DateFormatter.getDateToday("yyyy/MM/dd h:mm a") + ".";
		}
		
		return sendMailNotification(fromAddress, toAddress, subject, message);
	}
	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#sendMailNotification(java.lang.String, java.lang.String[], java.lang.String, java.lang.String)
	 */
	@Override
	public boolean sendMailNotification(String fromAddress, String[] toAddress,
			String subject, String message) {
		MailSender ms = new MailSender();
		ms.setFromAddress(fromAddress);
		ms.setMessage(message);
		ms.setSubject(subject);
		ms.setToAddress(toAddress);
		Thread msThread = new Thread(ms);
		msThread.start();
		return ms.isSuccess();
	}
	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#combineOrders(java.util.List)
	 */
	@Override
	public Order combineOrders(List<Order> orders) {
		Order order = new Order();
		order.setAllocationFinalizedBy(99999);
		order.setBillingFinalizedBy(99999);
		order.setBillingUnfinalizedBy(99999);
		for (Order _order : orders) {
			if (NumberUtil.isNullOrZero(_order.getAllocationFinalizedBy()))
				order.setAllocationFinalizedBy(null);
			if (NumberUtil.isNullOrZero(_order.getBillingFinalizedBy()))
				order.setBillingFinalizedBy(null);
			if (NumberUtil.isNullOrZero(_order.getBillingUnfinalizedBy()))
				order.setBillingUnfinalizedBy(null);
		}
		return order;

	}
	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#getGTPriceAllOrders(java.util.List)
	 */
	@Override
	public GrandTotalPrices getGTPriceAllOrders(List<Integer> orderIds) {
		return orderBillingDao.getGTPriceAllOrders(orderIds);
	}
	@Override
	public List<Order> getApprovedOrdersForBilling(List<Integer> orderId) {
		return orderBillingDao.getApprovedOrdersForBilling(orderId);
	}

	@Override
	public Map<Integer, Map<String, Map<Integer, BillingItem>>> getBillingItemsBulk(List<Integer> orderIds,
			List<Integer> skuIds) {
		
		//List<BillingItem> itemList = orderBillingDao.getBillingItemsBulk(orderIds, skuIds);
		//return OrderSheetUtil.convertToBillingItemsBulkMap(itemList);
		
		List<BillingItem> orderItemBulk = new ArrayList<BillingItem>();
		int skuCount = skuIds.size();
		int loopCount = (skuCount / 1000) + 1;
		
		/* SQL WHERE column IN (?,?,?...) IS LIMITED TO 1000 */
		for (int i=0; i<loopCount; i++) {
			int startIdx = i*1000;
			List<Integer> thisSkuIds = new ArrayList<Integer>();
			for (int j=startIdx; j<startIdx+1000; j++) {
				if (skuCount > j)
					thisSkuIds.add(skuIds.get(j));
			}
			
			orderItemBulk.addAll(orderBillingDao.getBillingItemsBulk(orderIds, thisSkuIds, false));
		}
		
		return OrderSheetUtil.convertToBillingItemsBulkMap(orderItemBulk);
	}
	
	@Override
	public List<BillingItem> getBillingItem(Integer sellerId, String deliveryDate,
			Integer skuId) {
		return orderBillingDao.getBillingItem(sellerId, deliveryDate, skuId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#getAkadenItemsBulk(java.util.List)
	 */
	@Override
	public List<AkadenItem> getAkadenItemsBulk(
			List<Integer> orderIds) {
		
		List<AkadenItem> itemList = orderAkadenDao.getAkadenItemsBulk(orderIds);
		//return OrderSheetUtil.convertToAkadenItemsBulkMap(itemList);
		return itemList;
	}
	/* (non-Javadoc)
	 * @see com.freshremix.service.BillingSheetService#computeTotalPriceOnDisplay(java.util.List)
	 */
	@Override
	public GrandTotalPrices computeTotalPricesOnDisplay(
			List<Map<String, Object>> skuOrderList) {
		GrandTotalPrices grandTotalPrices = new GrandTotalPrices();
		BigDecimal computeTotalPriceWOTax = new BigDecimal(0);
		BigDecimal computeTotalPriceWTax = new BigDecimal(0);
		
		for (Map<String, Object> sku : skuOrderList) {
			BigDecimal rowQty = (BigDecimal)sku.get("rowqty");
			BigDecimal priceWOTax = (BigDecimal)sku.get("pricewotax");
			if (rowQty != null && priceWOTax != null){
				BigDecimal price = rowQty.multiply(priceWOTax);
				computeTotalPriceWOTax = computeTotalPriceWOTax.add(
						price.setScale(0,BigDecimal.ROUND_HALF_UP));
				BigDecimal priceWTax = rowQty.multiply(priceWOTax.multiply(
						new BigDecimal(1.05)).setScale(0, BigDecimal.ROUND_HALF_UP));
				computeTotalPriceWTax = computeTotalPriceWTax.add( 
						priceWTax.setScale(0,BigDecimal.ROUND_HALF_UP));
			}
		}
		
		grandTotalPrices.setPriceWithoutTax(computeTotalPriceWOTax);
		grandTotalPrices.setPriceWithTax(computeTotalPriceWTax);
		
		return grandTotalPrices;
	}

	@Override
	public List<AkadenSKU> getDistinctSKUBA(List<Integer> orderIds, Integer categoryId) {
		return orderBillingDao.selectDistinctSKUBA(orderIds, categoryId);
	}
}