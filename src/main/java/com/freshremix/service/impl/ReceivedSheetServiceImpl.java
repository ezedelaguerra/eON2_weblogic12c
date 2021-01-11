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
package com.freshremix.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;

import com.freshremix.dao.AllocationDao;
import com.freshremix.dao.ReceivedSheetDao;
import com.freshremix.dao.SKUBADao;
import com.freshremix.dao.UserPreferenceDao;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;
import com.freshremix.model.OrderReceived;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.ProfitPreference;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUBA;
import com.freshremix.model.User;
import com.freshremix.service.ReceivedSheetService;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.ui.model.OrderItemUI;
import com.freshremix.ui.model.ProfitInfo;
import com.freshremix.ui.model.ProfitInfoList;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.StringUtil;
import com.freshremix.util.TaxUtil;

/**
 * @author nvelasquez
 *
 */
public class ReceivedSheetServiceImpl implements ReceivedSheetService {
	
	private ReceivedSheetDao receivedSheetDao;
	private AllocationDao allocationDao;
	private SKUBADao skuBADao;	
	private UserPreferenceDao userPreferenceDao;

	public void setReceivedSheetDao(ReceivedSheetDao receivedSheetDao) {
		this.receivedSheetDao = receivedSheetDao;
	}
	
	public void setAllocationDao(AllocationDao allocationDao) {
		this.allocationDao = allocationDao;
	}
	
	public void setSkuBADao(SKUBADao skuBADao) {
		this.skuBADao = skuBADao;
	}

	public void setUserPreferenceDao(UserPreferenceDao userPreferenceDao) {
		this.userPreferenceDao = userPreferenceDao;
	}

	@Override
	public List<SKUBA> getDistinctSKUs(List<Integer> orderIds,  List<Integer> sellerId, 
			List<Integer> buyerId, List<String> deliveryDate,Integer categoryId, String hasQty) {
		if (hasQty.equals("0")) hasQty = null;
		List<SKUBA> skuObjs = receivedSheetDao.selectDistinctSKUBAs(orderIds, sellerId, buyerId, 
				deliveryDate,categoryId, hasQty);
		
		return skuObjs;
	}
	
	@Override
	public void loadOrderReceivedQuantitiesAllBuyers(Map<String, Object> skuOrderMap,
			List<Integer> buyerIds, List<Integer> orderIds, SKU skuObj) {
		Map<Integer, OrderReceived> orderReceivedMap = receivedSheetDao.getOrderReceivedMapOfSkuBuyers(orderIds, skuObj.getSkuId());
		
		StringBuffer lockflag = new StringBuffer("{'sku':'1'");
		BigDecimal rowqty = new BigDecimal(0);
		for (Integer buyerId : buyerIds) {
			OrderReceived orderReceived = orderReceivedMap.get(buyerId);
			BigDecimal quantity = null;
			String strQuantity = "";
			String strApprvFlag = "1";
			String strLockFlag = "0"; //unlocked
			
			if (orderReceived != null) {
				quantity = orderReceived.getQuantity();
				
				if (orderReceived.getOrder().getReceivedApprovedBy() != null || //approved
					orderReceived.getOrder().getAllocationFinalizedBy() != null) //finalized
						strLockFlag = "1"; //locked
				
				if (orderReceived.getIsApproved() == null || orderReceived.getIsApproved().equals("0"))
					strApprvFlag = "0";
				
				System.out.println("quantity:[" + quantity + "]");
				if (quantity != null) {
					rowqty = rowqty.add(quantity);
					strQuantity = quantity.toPlainString();
				}
				
			}
			else {
				//strQuantity = "-999";
				strLockFlag = "1"; //locked
			}
			
			skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
			skuOrderMap.put("A_" + buyerId.toString(), strApprvFlag);
			skuOrderMap.put("L_" + buyerId.toString(), strLockFlag);
			lockflag.append(",'Q_");
			lockflag.append(buyerId.toString());
			lockflag.append("':'");
			lockflag.append(strLockFlag);
			lockflag.append("'");
		}
		skuOrderMap.put("rowqty", rowqty);
		lockflag.append("}");
		skuOrderMap.put("lockflag", lockflag);
	}
	
	@Override
	public void loadSumOrderReceivedQuantitiesAllBuyers(Map<String, Object> skuOrderMap,
			List<Integer> buyerIds, List<Integer> orderIds, SKU skuObj) {
		Map<Integer, OrderReceived> orderReceivedMap = receivedSheetDao.getSumOrderReceivedMapOfSkuBuyers(orderIds, skuObj.getSkuId());
		
		for (Integer buyerId : buyerIds) {
			OrderReceived orderReceived = orderReceivedMap.get(buyerId);
			BigDecimal quantity = null;
			String strQuantity = "";
			
			if (orderReceived != null) {
				quantity = orderReceived.getQuantity();
				
				System.out.println("quantity:[" + quantity + "]");
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
	
	@Override
	public void loadOrderReceivedQuantitiesAllDates(Map<String, Object> skuOrderMap,
			List<String> deliveryDates, List<Integer> orderIds, SKU skuObj) {
		
		Map<Integer, OrderReceived> orderReceivedMap = receivedSheetDao.getOrderReceivedMapOfSkuDates(orderIds, skuObj.getSkuId());
		
		StringBuffer lockflag = new StringBuffer("{'sku':'1'");
		BigDecimal rowqty = new BigDecimal(0);
		for (String deliveryDate : deliveryDates) {
			OrderReceived orderReceived = orderReceivedMap.get(deliveryDate);
			BigDecimal quantity = null;
			String strQuantity = "";
			String strLockFlag = "0"; //unlocked
			 
			if (orderReceived != null) {
				quantity = orderReceived.getQuantity();
				
				if (orderReceived.getOrder().getReceivedApprovedBy() != null || //approved
					orderReceived.getOrder().getAllocationFinalizedBy() != null) //finalized
						strLockFlag = "1"; //locked
			
				System.out.println("quantity:[" + quantity + "]");
				if (quantity != null) {
					rowqty = rowqty.add(quantity);
					strQuantity = quantity.toPlainString();
				}
			}
			else {
				//strQuantity = "-999";
				strLockFlag = "1"; //locked
			}
			skuOrderMap.put("Q_" + deliveryDate, strQuantity);
			skuOrderMap.put("L_" + deliveryDate, strLockFlag);
			lockflag.append(",'Q_");
			lockflag.append(deliveryDate);
			lockflag.append("':'");
			lockflag.append(strLockFlag);
			lockflag.append("'");
		}
		skuOrderMap.put("rowqty", rowqty);
		lockflag.append("}");
		skuOrderMap.put("lockflag", lockflag);
	}
	
	@Override
	public void loadSumOrderReceivedQuantitiesAllDates(Map<String, Object> skuOrderMap,
			List<String> deliveryDates, List<Integer> orderIds, SKU skuObj) {
		
		Map<Integer, OrderReceived> orderReceivedMap = receivedSheetDao.getSumOrderReceivedMapOfSkuDates(orderIds, skuObj.getSkuId());
		
		for (String deliveryDate : deliveryDates) {
			OrderReceived orderReceived = orderReceivedMap.get(deliveryDate);
			BigDecimal quantity = null;
			String strQuantity = "";
			 
			if (orderReceived != null) {
				quantity = orderReceived.getQuantity();
				
				System.out.println("quantity:[" + quantity + "]");
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

	// save button is clicked
	/**
	 * Save based on filter page selection
	 * Delivery Dates - Seller - Buyer
	 */
	@Override
	public void saveOrder(OrderForm orderForm, OrderDetails orderDetails, Map<String, Order> allOrdersMap) {
		
		if (orderDetails.isAllDatesView()) {
			this.saveOrderAllDates(orderForm, orderDetails, allOrdersMap);
			return;
		}
		
		List<String> deliveryDateList = DateFormatter.getDateList(orderDetails.getStartDate(), orderDetails.getEndDate());
		List<Integer> buyerIds = orderDetails.getBuyerIds();
		List<Integer> sellerIds = orderDetails.getSellerIds();
		Integer userId = orderDetails.getUser().getUserId();
		
		// save to EON_SKU
		List<OrderItemUI> updateList = orderForm.getUpdatedOrders();
		for (OrderItemUI oi : updateList) {
			SKU sku = OrderSheetUtil.toSKU(oi, orderDetails);
			Map<String, BigDecimal> qtyMap = oi.getQtyMap();
			Map<String, String> apprvMap = oi.getApprvMap();
			//Map<String, String> lockMap = oi.getLockMap();
			
			SKUBA skuBA = new SKUBA();
			boolean hasUpdatedBAFields = this.hasUpdatedBAFields(oi, skuBA);
			if (hasUpdatedBAFields) {
				skuBA.setSkuId(sku.getSkuId());
				skuBA.setPurchasePrice(oi.getPurchasePrice());
				skuBA.setSellingPrice(oi.getSellingPrice());
				OrderUnit uom = new OrderUnit();
				uom.setOrderUnitId(oi.getSellingUom());
				skuBA.setSellingUom(uom);
				skuBA.setSkuComment(oi.getSkuComments());
				skuBA.setSkuBAId(skuBADao.insertSKUBA(skuBA));
			}
			
			// update EON_ORDER_RECEIVED
			for (String deliveryDate : deliveryDateList) {
				boolean currentDay = false;
				for (Integer buyerId : buyerIds) {
					String sBuyerId = buyerId.toString();
					for (Integer sellerId : sellerIds) {
						String sSellerId = sellerId.toString();
						Order order = allOrdersMap.get(deliveryDate + "_" + sBuyerId + "_" + sSellerId);
						
						if (order == null) continue;

						/*
						 * If no order, do not process
						 * If order is Apporved or Finalized (Alloc), continue to process
						 * 
						 */
						
						Integer orderId = order.getOrderId();
						Integer skuId = sku.getSkuId();
						BigDecimal quantity = qtyMap.get("Q_" + sBuyerId);
						String isApproved = apprvMap.get("A_" + sBuyerId);
						// String isLock = lockMap.get("L_" + sBuyerId);
						
						if (deliveryDate.equals(orderDetails.getDeliveryDate())) currentDay = true;
						
						/*
						 * Locking is ignored here. The User cannot update the lock quantities in UI
						 * So, update SKU BA ID and Quantity
						 */
						if (currentDay) {
							// ENHANCEMENT START 20121001: Rhoda Redmine 1089
//							this.updateReceived(orderId, skuId, skuBA.getSkuBAId(), quantity, isApproved, userId, oi.getSkuBaId());
							this.updateReceived(orderId, skuId, skuBA.getSkuBAId(), isApproved, userId, oi.getSkuBaId());
							// ENHANCEMENT END 20121001: Rhoda Redmine 1089
						} else {
							// just update the skuBaId of the received item
							receivedSheetDao.updateReceivedItemSkuBaId(orderId, skuId, skuBA.getSkuBAId(), oi.getSkuBaId());
						}
					}
				}
			}
			
		}
		
	}
	
	private boolean hasUpdatedBAFields(OrderItemUI oi, SKUBA skuBA) {
		
		SKUBA tmp = skuBADao.findSKUBA(oi.getSkuBaId());
		
		if (tmp == null) {
			tmp = new SKUBA();
			tmp.setSellingUom(new OrderUnit());
		} else if (tmp.getSellingUom() == null) {
			tmp.setSellingUom(new OrderUnit());
		}
		tmp.setPurchasePrice(tmp.getPurchasePrice());
		tmp.setSellingPrice(tmp.getSellingPrice());
		tmp.getSellingUom().setOrderUnitId(NumberUtil.nullToZero(tmp.getSellingUom().getOrderUnitId()));
		tmp.setSkuComment(StringUtil.nullToBlank(tmp.getSkuComment()));
		
		oi.setPurchasePrice(oi.getPurchasePrice());
		oi.setSellingPrice(oi.getSellingPrice());
		oi.setSellingUom(NumberUtil.nullToZero(oi.getSellingUom()));
		oi.setSkuComments(StringUtil.nullToBlank(oi.getSkuComments()));

		if (oi.getSellingPrice() == null) {
			if (tmp.getSellingPrice() != null){
				return true;
			}
		} else {
			if (tmp.getSellingPrice() == null){
				return true;
			}else if(!oi.getSellingPrice().toPlainString().equals(
					tmp.getSellingPrice().toPlainString())){
				return true;
			}
		}

		if (oi.getPurchasePrice() == null) {
			if (tmp.getPurchasePrice() != null){
				return true;
			}
		} else {
			if (tmp.getPurchasePrice() == null){
				return true;
			}else if(!oi.getPurchasePrice().toPlainString().equals(
					tmp.getPurchasePrice().toPlainString())){
				return true;
			}
		}
						
		if (oi.getSellingUom().equals(tmp.getSellingUom().getOrderUnitId()) &&
			oi.getSkuComments().equals(tmp.getSkuComment())) {
			skuBA.setSkuBAId(tmp.getSkuBAId());
			return false;
		}else{
			return true;
		}
	}
	
	@Override
	public List<Order> getPublishedOrdersForReceived(List<Integer> buyerIds, List<String> deliveryDates, List<Integer> sellerIds) {
		List<Order> publishedOrders = receivedSheetDao.getPublishedOrdersForReceived(buyerIds, deliveryDates, sellerIds);
		if(CollectionUtils.isEmpty(publishedOrders)){
			publishedOrders = new ArrayList<Order>();
		}
		return publishedOrders;
	}
	
	@Override
	public void updateLockReceived(List<Integer> orderIds, Integer userId) {
		receivedSheetDao.lockReceived(orderIds, userId);
	}

	@Override
	public void updateUnlockReceived(List<Integer> orderIds, Integer userId) {
		receivedSheetDao.unlockReceived(orderIds, userId);
	}

	private void saveOrderAllDates(OrderForm orderForm, OrderDetails orderDetails, Map<String, Order> allOrdersMap) {
		String startDate = orderDetails.getStartDate();
		String endDate = orderDetails.getEndDate();
		
		List<String> deliveryDates = DateFormatter.getDateList(startDate, endDate);
		//Integer buyerId = orderDetails.getDatesViewBuyerID();
		List<Integer> sellerIds = orderDetails.getSellerIds();
		Integer userId = orderDetails.getUser().getUserId();
		
		// save to EON_SKU
		List<OrderItemUI> updateList = orderForm.getUpdatedOrders();
		for (OrderItemUI oi : updateList) {
			SKU sku = OrderSheetUtil.toSKU(oi, orderDetails);
			Map<String, BigDecimal> qtyMap = oi.getQtyMap();
//			Map<String, String> lockMap = oi.getLockMap();
			
			SKUBA skuBA = new SKUBA();
			boolean hasUpdatedBAFields = this.hasUpdatedBAFields(oi, skuBA);
			if (hasUpdatedBAFields) {
				skuBA.setSkuId(sku.getSkuId());
				skuBA.setPurchasePrice(oi.getPurchasePrice());
				skuBA.setSellingPrice(oi.getSellingPrice());
				OrderUnit uom = new OrderUnit();
				uom.setOrderUnitId(oi.getSellingUom());
				skuBA.setSellingUom(uom);
				skuBA.setSkuComment(oi.getSkuComments());
				skuBA.setSkuBAId(skuBADao.insertSKUBA(skuBA));
			}
			
			// update EON_ORDER_ITEM
			for (String deliveryDate : deliveryDates) {
				for (Integer sellerId : sellerIds) {
					String sSellerId = sellerId.toString();
					for (Integer buyerId : orderDetails.getBuyerIds()) {
						boolean currentBuyer = false;
						Order order = allOrdersMap.get(deliveryDate + "_" + buyerId.toString() + "_" + sSellerId);
						
						if (order == null) continue;
						
						/*
						 * If no order, do not process
						 * If order is Apporved or Finalized (Alloc), continue to process
						 * 
						 */
						
						Integer orderId = order.getOrderId();
						Integer skuId = sku.getSkuId();
						BigDecimal quantity = qtyMap.get("Q_" + deliveryDate);
						String isApproved = null;
//						String isLock = lockMap.get("Q_" + deliveryDate);
						
						if (buyerId == orderDetails.getDatesViewBuyerID()) currentBuyer = true;
						
						/*
						 * Locking is ignored here. The User cannot update the lock quantities in UI
						 * So, update SKU BA ID and Quantity
						 */
						
						if (currentBuyer) {
							// ENHANCEMENT START 20121001: Rhoda Redmine 1089
//							this.updateReceived(orderId, skuId, skuBA.getSkuBAId(), quantity, isApproved, userId, oi.getSkuBaId());
							this.updateReceived(orderId, skuId, skuBA.getSkuBAId(), isApproved, userId, oi.getSkuBaId());
							// ENHANCEMENT END 20121001: Rhoda Redmine 1089
						} else if (!currentBuyer) {
							// just update the skuBaId of the received item
							receivedSheetDao.updateReceivedItemSkuBaId(orderId, skuId, skuBA.getSkuBAId(), oi.getSkuBaId());
						}
					}
				}
			}
					
		}
		
	}
	
	@Override
	public GrandTotalPrices computeTotalPriceOnDisplay(
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
				BigDecimal priceWTax = rowQty.multiply(TaxUtil.getPriceWithTax(priceWOTax, TaxUtil.ROUND));
				computeTotalPriceWTax = computeTotalPriceWTax.add( 
						priceWTax.setScale(0,BigDecimal.ROUND_HALF_UP));
			}
		}
		
		grandTotalPrices.setPriceWithoutTax(computeTotalPriceWOTax);
		grandTotalPrices.setPriceWithTax(computeTotalPriceWTax);
		
		return grandTotalPrices;
	}

	// DELETE START 20121001: Rhoda Redmine 1089
//	private void updateReceived(Integer orderId, Integer skuId, Integer skuBaId,
//			BigDecimal quantity, String isApproved, Integer userId, Integer oldSkuBaId) {
//		
//		if(quantity != null && quantity.equals(new BigDecimal("-999")))
//			return;
//		
//		receivedSheetDao.updateReceived(orderId, skuId, skuBaId, quantity, isApproved, userId, oldSkuBaId);
//		allocationDao.updateAllocItem(orderId, skuId, null, quantity, isApproved);
//	}
	// DELETE End 20121001: Rhoda Redmine 1089
	// ENHANCEMENT START 20121001: Rhoda Redmine 1089
	private void updateReceived(Integer orderId, Integer skuId, Integer skuBaId,
			String isApproved, Integer userId, Integer oldSkuBaId) {
		
		receivedSheetDao.updateReceived(orderId, skuId, skuBaId, isApproved, userId, oldSkuBaId);
		allocationDao.updateAllocItemFromReceived(orderId, skuId, null, isApproved);
	}
	// ENHANCEMENT End 20121001: Rhoda Redmine 1089

	@Override
	public List<Order> getApprovedOrdersForReceived(List<Integer> orderId) {
		return receivedSheetDao.getApprovedOrdersForReceived(orderId);
	}
	
	@Override
	public Map<String, Map<String, Map<Integer, OrderReceived>>> getReceivedItemsBulk(List<Integer> orderIds,
			List<Integer> skuIds) {
		
		//List<OrderReceived> itemList = receivedSheetDao.getReceivedItemsBulk(orderIds, skuIds);
		//return OrderSheetUtil.convertToReceivedItemsBulkMap(itemList);
		
		List<OrderReceived> orderItemBulk = new ArrayList<OrderReceived>();
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
			
			orderItemBulk.addAll(receivedSheetDao.getReceivedItemsBulk(orderIds, thisSkuIds, false));
		}
		
		return OrderSheetUtil.convertToReceivedItemsBulkMap(orderItemBulk);
	}

	/*
	 * This method is used to get either;
	 * 1. TOTALS of ProfitInfo if given parameter is only one category 
	 *    and one day for all buyers view or multiple days for all dates view
	 * 2. GRAND TOTALS of ProfitInfo if given parameter is more than one categories
	 *    and one day for all buyers view or multiple days for all dates view
	 */
	@Override
	public ProfitInfo getBuyerTotalPrices(User user,List<String> deliveryDate, List<Integer> sellerId, 
			List<Integer> buyerId, List<Integer> categoryId, Double tax, String priceWithTaxOpt, Map<String, List<Integer>> mapOfMembersByDate) {
		
		Set<Integer> categorySet = new TreeSet<Integer>(categoryId);
		
		ProfitInfo total = new ProfitInfo();
		for (String _deliveryDate : deliveryDate) {
			List<Integer> validBuyersForDate = new ArrayList<Integer>();
			
			if (RolesUtil.isUserRoleBuyerAdmin(user)){

				List<Integer>  tmpvalidBuyerMemberList = mapOfMembersByDate.get(_deliveryDate);
				for (Integer integer : buyerId) {
					if(tmpvalidBuyerMemberList.contains(integer)){
						validBuyersForDate.add(integer);
					}
				}
			} else {
				validBuyersForDate = buyerId;
			}
			if (CollectionUtils.isEmpty(validBuyersForDate)) {
				continue;
			}
			
			Iterator<Integer> categoryI = categorySet.iterator();
			while (categoryI.hasNext()) {
				List<ProfitInfo> list = receivedSheetDao.getBuyerPricesPerSKU(_deliveryDate, 
						validBuyersForDate, sellerId, categoryI.next(), tax);
				ProfitInfoList pil = new ProfitInfoList(list, priceWithTaxOpt);
				ProfitPreference profitPreference = userPreferenceDao.getProfitPreference(user.getUserId());
				boolean withPackageQuantity = true;
				
				if(profitPreference != null){
					if(profitPreference.getWithPackageQuantity().equalsIgnoreCase("1")){
						withPackageQuantity = true;
					} else {
						withPackageQuantity = false;
					}
				} else {
					withPackageQuantity = true;
				}
				
				ProfitInfo subTotal = pil.computeForTotals(withPackageQuantity);
				subTotal.roundOffTotals();
				subTotal.setProfitPercentage(subTotal.calculateProfitPercentage());
				if (subTotal != null)
					total.add(subTotal);
			}
		}
		
		return total;
	}
}
