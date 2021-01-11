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
 * date		name		version		changes
 * ------------------------------------------------------------------------------		
 * 20120724	gilwen		v11			Redmine 797 - SellerAdmin can select seller who don't have category
 * 20121120 mikes       v14.01      Redmine 1049 seller-selleradmin conccurency on finalize in order sheet
 */

package com.freshremix.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.mail.MethodNotSupportedException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.freshremix.constants.OrderConstants;
import com.freshremix.dao.AllocationDao;
import com.freshremix.dao.OrderDao;
import com.freshremix.dao.OrderItemDao;
import com.freshremix.dao.OrderSheetDao;
import com.freshremix.dao.ReceivedSheetDao;
import com.freshremix.dao.SKUDao;
import com.freshremix.dao.SKUGroupDao;
import com.freshremix.dao.UserDao;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.Category;
import com.freshremix.model.CompositeKey;
import com.freshremix.model.ConcurrencyResponse;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.DefaultOrder;
import com.freshremix.model.Item;
import com.freshremix.model.MailSender;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.SheetData;
import com.freshremix.model.User;
import com.freshremix.model.WSBuyerInformation;
import com.freshremix.model.WSInputDetails;
import com.freshremix.model.WSSKU;
import com.freshremix.model.WSSellerBuyerDetails;
import com.freshremix.model.WSSellerSKU;
import com.freshremix.service.AllocationSheetService;
import com.freshremix.service.CategoryService;
import com.freshremix.service.MessageI18NService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.SKUService;
import com.freshremix.service.SheetDataService;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.ui.model.OrderItemUI;
import com.freshremix.ui.model.PageInfo;
import com.freshremix.ui.model.ProfitInfo;
import com.freshremix.util.ConcurrencyUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.StringUtil;
import com.freshremix.util.WebServiceUtil;

public class OrderSheetServiceImpl implements OrderSheetService {

	private OrderSheetDao orderSheetDao;
	private AllocationDao allocationDao;
	private OrderDao orderDao;
	private SKUService skuService;
	private OrderItemDao orderItemDao;
	private UserDao usersInfoDaos;
	private SKUGroupDao skuGroupDao;
	private CategoryService categoryService; 
	private OrderUnitService orderUnitService;
	private ReceivedSheetDao receivedSheetDao;
	private AllocationSheetService allocationSheetService;
	private SKUDao skuDao;
	private SheetDataService sheetDataService;
	private MessageI18NService messageI18NService;

	private static final Logger LOGGER = Logger.getLogger(OrderSheetServiceImpl.class);
	
	
	public void setSheetDataService(SheetDataService sheetDataService) {
		this.sheetDataService = sheetDataService;
	}

	public void setSkuDao(SKUDao skuDao) {
		this.skuDao = skuDao;
	}

	public void setOrderSheetDao(OrderSheetDao orderSheetDao) {
		this.orderSheetDao = orderSheetDao;
	}
	public void setMessageI18NService(MessageI18NService messageI18NService) {
		this.messageI18NService = messageI18NService;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setSkuService(SKUService skuService) {
		this.skuService = skuService;
	}

	public void setOrderItemDao(OrderItemDao orderItemDao) {
		this.orderItemDao = orderItemDao;
	}

	public void setSkuGroupDao(SKUGroupDao skuGroupDao) {
		this.skuGroupDao = skuGroupDao;
	}

	public void setAllocationSheetService(AllocationSheetService allocationSheetService) {
		this.allocationSheetService = allocationSheetService;
	}

	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}

	public void setAllocationDao(AllocationDao allocationDao) {
		this.allocationDao = allocationDao;
	}

	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}

	// save button is clicked
	@Override
	public ConcurrencyResponse saveOrder(OrderForm orderForm, OrderDetails orderDetails, List<Order> allOrders, Map<Integer, SKU> skuObjMap) throws ServiceException {
		
		List<String> deliveryDates = null;
		List<Integer> buyerIds = null;
		List<Integer> sellerIds = orderDetails.getSellerIds();
		List<String> allDeliveryDates = null;
		List<Integer> allBuyerIds = null;
		
		String startDate = orderDetails.getStartDate();
		String endDate = orderDetails.getEndDate();
		if (orderDetails.isAllDatesView()) {
			deliveryDates = DateFormatter.getDateList(startDate, endDate);
			buyerIds = new ArrayList<Integer>();
			buyerIds.add(orderDetails.getDatesViewBuyerID());
		} else {
			deliveryDates = new ArrayList<String>();
			deliveryDates.add(orderDetails.getDeliveryDate());
			buyerIds = orderDetails.getBuyerIds();
		}

		allDeliveryDates = DateFormatter.getDateList(startDate, endDate);
		allBuyerIds = orderDetails.getBuyerIds();
		
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		//extract the orders from the Map
		List<Order> extractedFilteredOrders = OrderSheetUtil
				.extractFilteredOrders(allOrdersMap, sellerIds,
						allDeliveryDates, allBuyerIds);

		//concurrency checking if SKU for update or delete already has a quantity
		ConcurrencyResponse concurrencyResponse=  concurrentSaveSKUHasQty(orderForm.getUpdatedOrders(), orderForm.getDeletedOrders(), orderDetails, skuObjMap, deliveryDates);
		ConcurrencyResponse validateMaxQuantity = ConcurrencyUtil.validateMaxQuantity(concurrencyResponse.getOiUIforUpdate(), orderDetails,
				allOrders, orderSheetDao, true,messageI18NService);
		concurrencyResponse.setOiUIforUpdate(validateMaxQuantity.getOiUIforUpdate());
		String concurrencyMsg = StringUtil.nullToBlank(concurrencyResponse.getConcurrencyMsg()) + StringUtil.nullToBlank(validateMaxQuantity.getConcurrencyMsg()); 
		concurrencyResponse.setConcurrencyMsg(concurrencyMsg);
		
		if(orderForm.getAction().equals(OrderConstants.ACTION_SAVE))
		{
			//concurrentSaveOrderVisibility(orderForm, orderDetails, extractedFilteredOrders);
			ConcurrencyResponse concurrentSaveOrderVisibility = ConcurrencyUtil
					.concurrentSaveOrderVisibility(concurrencyResponse.getOiUIforUpdate(), orderDetails,
							extractedFilteredOrders, orderSheetDao, false,messageI18NService);
			concurrencyResponse.setOiUIforUpdate(concurrentSaveOrderVisibility.getOiUIforUpdate());
			concurrencyMsg = StringUtil.nullToBlank(concurrencyResponse.getConcurrencyMsg()) + StringUtil.nullToBlank(concurrentSaveOrderVisibility.getConcurrencyMsg()); 
			concurrencyResponse.setConcurrencyMsg(concurrencyMsg);
		}
		// UPDATE ORDERS
		List<Integer> orderIdsForSaving = new ArrayList<Integer>();
		for (Order order : extractedFilteredOrders) {
			if (order.getOrderFinalizedBy() == null) {
				orderIdsForSaving.add(order.getOrderId());
			}
		}
		
		try {
			orderDao.updateSaveOrderBatch( orderIdsForSaving,orderDetails.getSellerId());
		} catch (Exception e) {
			throw new ServiceException("Unable to perform database operation:"+ e.getMessage(),e);
		}
		// INSERT SKU
		Map<Integer,User> sellerUserObjMap = new HashMap<Integer,User>();
		insertSKU(orderForm, orderDetails, extractedFilteredOrders,
				sellerUserObjMap);
		

		// UPDATED SKU
		//extract the orders from the Map using buyerIds not allbuyerIds
		List<Order> extractedFilteredOrdersForUpdate = OrderSheetUtil
				.extractFilteredOrders(allOrdersMap, sellerIds,
						allDeliveryDates, buyerIds);
		

		updateSKU(concurrencyResponse.getOiUIforUpdate(), orderDetails, skuObjMap, buyerIds,
				sellerUserObjMap, extractedFilteredOrdersForUpdate);
		
		// DELETED SKU
		deleteSKU(concurrencyResponse.getOiUIforDelete(), extractedFilteredOrders);
		
		return concurrencyResponse;
	}

	private ConcurrencyResponse concurrentSaveSKUHasQty(List<OrderItemUI> updatedOrders,List<OrderItemUI> deletedOrders,
			OrderDetails orderDetails, Map<Integer, SKU> skuObjMap, List<String> deliveryDates) throws ServiceException {
		//concurrent save-finalized checking
		//checkForConcurrentSaveFinalizedOrders(extractedFilteredOrders);
		
		Map<Integer, BigDecimal> skuIdQuantityMap = this
				.getTotalQuantityBySKUIdListAndDate(
						updatedOrders, deliveryDates);

		//do not allow update or deletion of SKU if it has quantity Redmine 1063 
		ConcurrencyResponse concurrencyResult = ConcurrencyUtil
				.validateSellerOrderSheetSKUQuantity(OrderConstants.UPDATE,
						updatedOrders, orderDetails, skuIdQuantityMap, skuObjMap, messageI18NService);

		
		ConcurrencyResponse concurrencyResult2 = ConcurrencyUtil
				.validateSellerOrderSheetSKUQuantity(OrderConstants.DELETE,
						deletedOrders, orderDetails, skuIdQuantityMap, skuObjMap, messageI18NService);

		concurrencyResult.setOiUIforDelete(concurrencyResult2.getOiUIforUpdate());
		String msg = StringUtil.nullToBlank(concurrencyResult.getConcurrencyMsg()) + StringUtil.nullToBlank(concurrencyResult2.getConcurrencyMsg());
		concurrencyResult.setConcurrencyMsg(msg);
		
		return  concurrencyResult;
	}


	
	
	/*
	 * fix for saving a record that is already finalized by another user:
	 * 
	 * if order being saved is already finalized throw a Service Exception
	 * 
	 * To do this you need to compare the finalizedBy field from the session
	 * against that from the database. if finalizedBy field in session is
	 * not equal to the finalizedBy field in database then we captured the scenario
	 */
	private void checkForConcurrentSaveFinalizedOrders(
			List<Order> extractedFilteredOrders) throws ServiceException {
		
		//retrieve the finalized orders from the DB
		List<Integer> orderIdListFromSession = OrderSheetUtil.getOrderIdList(extractedFilteredOrders);
		List<Order> finalizedOrdersFromDB = getFinalizedOrderFromOrderIdList(orderIdListFromSession);
		Map<Integer, Order> orderMapFromDB = OrderSheetUtil.convertToOrderIdMap(finalizedOrdersFromDB);
		
		//storage for Orders that fails the check
		List<Order> failedOrders = new ArrayList<Order>();
		
		for (Order orderFromSession : extractedFilteredOrders) {
			//check if the order is part of the finalized order from the DB
			Order orderFromDB = orderMapFromDB.get(orderFromSession.getOrderId());
			if (orderFromDB == null){
				continue;
			}
			
			/*
			 *no need to handle situation where it is already finalized in session but not yet in DB
			 *succeeding codes after this method skips the order if it is already finalized 
			 */
			
			/*
			 * At this point Order is already finalized in the DB. Check the following:
			 * 1. If order from session is NOT yet finalized add the order in failed list.
			 * 2. If order from session is already finalized, then finalizedBy field should be equal with the DB, 
			 * if NOT then add the order in failed list
			 */
			if (orderFromSession.getOrderFinalizedBy() == null){
				failedOrders.add(orderFromSession);
				continue;
			}
			if (!orderFromSession.getOrderFinalizedBy().equals(orderFromDB.getOrderFinalizedBy())) {
				failedOrders.add(orderFromSession);
				continue;
			}
			
		}
		if(CollectionUtils.isNotEmpty(failedOrders)){
			Map<String, Object> exceptionContext = new HashMap<String, Object>();
			exceptionContext.put(OrderConstants.FAILED_ORDERS, failedOrders);
			throw new ServiceException(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_FINALIZED_FAILED, exceptionContext);
		}
	}
	
	/*
	 * Not used. Refer to ConcurrencyUtil.concurrentSaveOrderVisibility
	 * @param orderForm
	 * @param orderDetails
	 * @param extractedFilteredOrders
	 * @throws ServiceException
	 */
	
//	@Deprecated
//	private void concurrentSaveOrderVisibility(OrderForm orderForm,
//			OrderDetails orderDetails,List<Order> extractedFilteredOrders) throws ServiceException {
//		
//		List<OrderItemUI> orderItemList = orderForm.getUpdatedOrders();
//		List<Integer> orderIdListFromSession = OrderSheetUtil.getOrderIdList(extractedFilteredOrders);
//
//		StringBuilder concurrencyMsg = new StringBuilder(); 
//		
//		for (OrderItemUI oi : orderItemList) {
//			
//			Map<String, String> visMap = oi.getVisMap();
//			List< OrderItem> orderItemsFromDB = orderSheetDao.getOrderItemsListOfSkuBuyers(orderIdListFromSession, oi.getSkuId());
//
//			for (OrderItem oitem : orderItemsFromDB) {
//				
//				
//				
//				Order order = oitem.getOrder();
//				if (order == null) {
//				
//					continue;
//				}
//				Integer buyerId = order.getBuyerId();
//				
//				String deliveryDate = order.getDeliveryDate();
//				String quantityKey = orderDetails.isAllDatesView() == true ? deliveryDate
//						: buyerId.toString();
//
//				String vis =StringUtil.nullToBlank(visMap.get("V_" + quantityKey));
//				
//				if(!NumberUtil.isNullOrZero(oitem.getQuantity()) && vis.equals("0"))
//				{
//
//					concurrencyMsg.append(oi.getSkuId()+ "  ");
//					concurrencyMsg.append(oi.getSellername() + "  ");
//					concurrencyMsg.append(oi.getSkuname() + "\n");
//					break;
//				}
//				
//			}
//			
//		}
//		if(StringUtils.isNotBlank(concurrencyMsg.toString())){
//
//			Map<String, Object> exceptionContext = new HashMap<String, Object>();
//			exceptionContext.put(OrderConstants.FAILED_SKU, concurrencyMsg.toString());
//			
//			throw new ServiceException(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_SKU_HAS_QTY_FAILED, exceptionContext);
//
//		}
//	}

	private void insertSKU(OrderForm orderForm, OrderDetails orderDetails,
			List<Order> extractedFilteredOrders,
			Map<Integer, User> sellerUserObjMap) {
		
		List<OrderItemUI> orderItemList = orderForm.getInsertedOrders();
		
		for (OrderItemUI oi : orderItemList) {
			User user = getUserById(sellerUserObjMap, oi.getSellerId());
			SKU sku = OrderSheetUtil.toSKU(oi, orderDetails, user);
			sku.setOrigSkuId(null);
			Map<String, BigDecimal> qtyMap = oi.getQtyMap();
			Map<String, String> visMap = oi.getVisMap();
			skuService.insertSKU(sku);
			// save to EON_ORDER_ITEM
			for (Order order : extractedFilteredOrders) {
				
				Integer sellerId = order.getSellerId();
				Integer buyerId = order.getBuyerId();
				String deliveryDate = order.getDeliveryDate();

				if (!sellerId.equals(sku.getUser().getUserId())){
					continue;
				}
				
				if (order.getOrderFinalizedBy() != null) {
					continue;
				}
					
				if ((orderDetails.isAllDatesView() && orderDetails.getDatesViewBuyerID().equals(buyerId)) || 
					(!orderDetails.isAllDatesView() && orderDetails.getDeliveryDate().equals(deliveryDate))) {
					String quantityKey = orderDetails.isAllDatesView() == true ? deliveryDate : buyerId.toString();
					this.insertOrderItem(order.getOrderId(), sku.getSkuId(), null, qtyMap.get("Q_" + quantityKey), visMap.get("V_" + quantityKey));
				}else{
					this.insertOrderItem(order.getOrderId(), sku.getSkuId(), null, null, "1");
				}
				
			}

		}
	}

	private void updateSKU(List<OrderItemUI> oiUIforUpdate, OrderDetails orderDetails,
			Map<Integer, SKU> skuObjMap, List<Integer> buyerIds,
			Map<Integer, User> sellerUserObjMap,
			List<Order> extractedFilteredOrdersForUpdate) {
		
		List<OrderItemUI> orderItemList =oiUIforUpdate;

		if(CollectionUtils.isNotEmpty(orderItemList)){
		for (OrderItemUI oi : orderItemList) {
			User user = getUserById(sellerUserObjMap, oi.getSellerId());
			SKU sku = OrderSheetUtil.toSKU(oi, orderDetails, user);
			SKU origSku = skuObjMap.get(sku.getSkuId());
			Map<String, BigDecimal> qtyMap = oi.getQtyMap();
			Map<String, String> visMap = oi.getVisMap();
			// check for deleted items
			if (isOrderItemForDeletetion(oi)) {
				continue;
			}
			// update EON_ORDER_ITEM
			boolean isMetaInfoChanged = !origSku.equals(sku);
	
			boolean updateNewSKU = false;
	
			boolean skuCountWithSavedOrder = getSKUCountWithSavedOrder(oi.getSkuId(),
					orderDetails.getDeliveryDate(), buyerIds);
			
			if (isMetaInfoChanged){
				if (skuCountWithSavedOrder) {
					skuService.updateNewSKU(sku);
					updateNewSKU = true;
				} else {
					skuService.updateSKU(OrderSheetUtil.toSKU(oi, orderDetails));
				}
			}
			for (Order order : extractedFilteredOrdersForUpdate) {
				Integer sellerId = order.getSellerId();
				String sSellerId = sellerId.toString();
				Integer buyerId = order.getBuyerId();
				String deliveryDate = order.getDeliveryDate();
	
				if (!sellerId.equals(origSku.getUser().getUserId())) {
					continue;
				}
	
				List<Integer> dbBuyers = getDealingPattern(deliveryDate,
						sSellerId, orderDetails.getDealingPattern());
	
				// process only the selected buyers in the dealing pattern
				if (!dbBuyers.contains(buyerId)) {
					continue;
				}
	
				if (order.getOrderFinalizedBy() == null) {
					if ((orderDetails.isAllDatesView() && orderDetails.getDatesViewBuyerID().equals(buyerId))
					|| (!orderDetails.isAllDatesView() && orderDetails.getDeliveryDate().equals(deliveryDate))) {
	
						String quantityKey = orderDetails.isAllDatesView() == true ? deliveryDate
								: buyerId.toString();
						this.updateOrderItem(order.getOrderId(),
								sku.getSkuId(), sku.getOrigSkuId(),
								qtyMap.get("Q_" + quantityKey),
								visMap.get("V_" + quantityKey), updateNewSKU,
								isMetaInfoChanged);
					} else {
						this.updateOrderItemSkuId(order.getOrderId(),
								sku.getSkuId(), sku.getOrigSkuId(),
								updateNewSKU);
					}
				}
			}
		}
	}
	}

	private void deleteSKU(List<OrderItemUI> orderItemList,
			List<Order> extractedFilteredOrders) throws ServiceException {
		
	
		List<Integer> orderToDelete  = new ArrayList<Integer>();
		
		List<Integer> skuToDelete = new ArrayList<Integer>();
		if (CollectionUtils.isNotEmpty(orderItemList)) {
		for(OrderItemUI oi : orderItemList) {
			skuToDelete.add(oi.getSkuId().intValue());
		}
		}
		if (skuToDelete.size() > 0) {
			for (Order order : extractedFilteredOrders) {
				if (order.getOrderFinalizedBy() == null) {
					orderToDelete.add(order.getOrderId());
				}
			}
			
			if(CollectionUtils.isNotEmpty(orderToDelete)){
				orderItemDao.deleteOrderItemNoQuantityBatch(orderToDelete,skuToDelete);
			}
			
		}
		
		
	
	}


	
	private List<Integer> getDealingPattern(String deliveryDate, String sellerId, DealingPattern dealingPattern) {
		Map<String, List<Integer>> dp = dealingPattern.getSellerToBuyerDPMap().get(deliveryDate);
		return dp.get(sellerId);
	}



	/**
	 * Checks order item UI if it is for deletion.
	 *
	 * @return boolean
	 * @param oi
	 * @return
	 */
	
	private boolean isOrderItemForDeletetion(OrderItemUI oi) {
		//return StringUtil.isNullOrEmpty(oi.getMyselect()) ? false : true;
		return false;
	}

	/**
	 * Checks if sku is used in previous orders
	 *
	 * @return boolean
	 * @return
	 */
	
	private boolean getSKUCountWithSavedOrder(Integer skuId, String deliveryDate, List<Integer> selectedBuyerIds) {
		Integer result = orderItemDao.getSKUCountWithSavedOrder(skuId, deliveryDate, selectedBuyerIds);
		return result.intValue() > 0 ? true : false;
	}

	/*@Override
	public List<OrderItemUI> loadOrderItems(Map<String,Object> param) {
		List<OrderItemUI> list = orderSheetDao.loadOrderItems(param);
		return list;
	}*/

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#finalizedOrder(java.util.Map)
	 */
	@Override
	public void updateFinalizedOrder(Integer orderId, Integer finalizedBy, Integer unfinalizedBy) {
		orderDao.finalizeOrder(orderId, finalizedBy, unfinalizedBy);
		List<OrderItem> orderItem = getOrderItems(orderId);
		for (OrderItem _orderItem : orderItem) {
			allocationDao.insertDefaultAllocItems(_orderItem);
		}
	}

	private void finalizeOrderAndInsertDefaultAllocItems(List<Integer> orderIdList, Integer finalizedBy) throws Exception {
		orderDao.finalizeOrderBatch(orderIdList, finalizedBy);
		allocationDao.insertDefaultAllocItemsBatch(orderIdList,finalizedBy);
	}

	
	@Override
	public List<Order> updateFinalizeOrder(User user, List<Order> pubOrder,
			Map<Integer, User> sellerMap) throws Exception {
		LOGGER.info("Update Finalized Order...start");
		//before continuing with the process check if there are records that are already finalized
		List<Order> finalizedOrderFromOrderIdList = getFinalizedRecordsFromOrderList(pubOrder);
		List<Integer> finalizedOrderIdList = Collections.emptyList();

		if (CollectionUtils.isNotEmpty(finalizedOrderFromOrderIdList)){
			finalizedOrderIdList = OrderSheetUtil.getOrderIdList(finalizedOrderFromOrderIdList);
		}
		
		//temporary storage of orders for batch DB operations
		List<Order> ordersForFinalize = new ArrayList<Order>();
		List<Order> ordersForAutoPublishAllocation = new ArrayList<Order>();
		
		for (Order publishedOrder : pubOrder) {
			//skip the order that is already finalized
			if (finalizedOrderIdList.contains(Integer.valueOf(publishedOrder.getOrderId()))){
				continue;
			}
			
			ordersForFinalize.add(publishedOrder);
			
			User userOrder = sellerMap.get(publishedOrder.getSellerId());

            //Auto-Publish on Allocation Sheet by Ogie Dec. 08, 2010
			if (userOrder.getAutoPublishAlloc() != null && userOrder.getAutoPublishAlloc().equals("1")){
				ordersForAutoPublishAllocation.add(publishedOrder);
			}

		}
		
		String fromAddress = StringUtils.isNotBlank(user.getPcEmail())? user.getPcEmail() : user.getMobileEmail();

		//save Orders as finalized
		List<Integer> ordersForFinalizeIdList = OrderSheetUtil.getOrderIdList(ordersForFinalize);

		LOGGER.info("Orders for finalization: count:"+ordersForFinalizeIdList.size());
		if (CollectionUtils.isNotEmpty(ordersForFinalizeIdList)) {
			finalizeOrderAndInsertDefaultAllocItems(ordersForFinalizeIdList, user.getUserId());
		}

		//orders for autopublishing
		List<Integer> orderIdsForAutoPublishIdList = OrderSheetUtil.getOrderIdList(ordersForAutoPublishAllocation);

		LOGGER.info("Orders for autopublish: count:"+orderIdsForAutoPublishIdList.size());
		if (CollectionUtils.isNotEmpty(orderIdsForAutoPublishIdList)) {
			allocationSheetService.saveAndPublishAllocation(orderIdsForAutoPublishIdList, user.getUserId());
		}
		
		
		/*
		 * Notifications done in the end of the process to ensure that email is
		 * sent only when all DB operations are successful
		 */
		//send notifications for Finalized Orders
		if (CollectionUtils.isNotEmpty(ordersForFinalizeIdList)) {
			LOGGER.info("Sending consolidated email for Finalized Order Sheet");
			Map<String, Set<String>> mapOfEmailToOrderDates = OrderSheetUtil.consolidateOrdersForEmail(
					sellerMap, ordersForFinalize,"seller");
			for (Map.Entry<String, Set<String>> emailToOrderDates : mapOfEmailToOrderDates.entrySet()) {
				this.sendConsolidatedMailNotification(new ArrayList<String>(
						emailToOrderDates.getValue()), "Finalize", user
						.getUserName(), fromAddress,
						new String[] { emailToOrderDates.getKey() });
			}
		}
		
		//send notifications for Auto Publish Allocation Orders
		if (CollectionUtils.isNotEmpty(orderIdsForAutoPublishIdList)) {
			LOGGER.info("Sending consolidated email for Published Allocation Sheet");
			Map<String, Set<String>> mapOfEmailToOrderDates = OrderSheetUtil.consolidateOrdersForEmail(
					sellerMap, ordersForAutoPublishAllocation,"seller");

			for (Map.Entry<String, Set<String>> emailToOrderDates : mapOfEmailToOrderDates.entrySet()) {
				allocationSheetService.sendConsolidatedMailNotification(new ArrayList<String>(
						emailToOrderDates.getValue()), "Publish", user
						.getUserName(), fromAddress,
						new String[] { emailToOrderDates.getKey() });
			}
		}
		
		LOGGER.info("Update Finalized Order...end");
		return finalizedOrderFromOrderIdList;
	}

	
	
	
	
	private List<Order> getFinalizedRecordsFromOrderList(List<Order> pubOrder) {
		List<Integer> orderIdList = OrderSheetUtil.getOrderIdList(pubOrder);
		List<Order> finalizedOrderFromOrderIdList = this.getFinalizedOrderFromOrderIdList(orderIdList);
		return finalizedOrderFromOrderIdList;
	}

	
	@Override
	public List<Order> getFinalizedOrderFromOrderIdList(List<Integer> orderIdList) {
		if (CollectionUtils.isEmpty(orderIdList)){
			return Collections.emptyList();
		}
		return orderDao.getFinalizedOrdersFromOrderIdList(orderIdList);
	}
	

	private List<OrderItem> getOrderItems(Integer orderId) {
		return orderItemDao.getOrderItemByOrderId(orderId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#getOrderDetails(java.util.Map)
	 */
	@Override
	public Order getOrdersByOrderId(List<Integer> orderId) {
		List<Order> orders = orderDao.getOrdersByOrderId(orderId);
		return combineOrders(orders);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#publishOrder(java.util.Map)
	 */
	@Override
	public void updatePublishOrder(Map<String, Object> param) {
		orderDao.publishOrder(param);
	}
	
	@Override
	public void updatePublishOrder(User user, List<Order> savedOrders) throws Exception {
	
		if (CollectionUtils.isEmpty(savedOrders)) {
			return;
		}
		LOGGER.info("Update publish Order...start");
		
		//save Orders as unfinalized
		List<Integer> ordersForPublishIdList = OrderSheetUtil.getOrderIdList(savedOrders);

		LOGGER.info("Orders to publish count:"+ordersForPublishIdList.size());
		if (CollectionUtils.isNotEmpty(ordersForPublishIdList)) {
			orderDao.publishOrderBatch(ordersForPublishIdList, user.getUserId());
		}
		
		
		/*
		 * Notifications done in the end of the process to ensure that email is
		 * sent only when all DB operations are successful
		 */
		
		Map<Integer, User> buyerMap = new HashMap<Integer, User>();

		
		for (Order order : savedOrders) {
			
			if(buyerMap.containsKey(order.getBuyerId())){
				continue;
			}
			User userOrder = usersInfoDaos.getUserById(order.getBuyerId());

			buyerMap.put(userOrder.getUserId(), userOrder);
		}
		
		String fromAddress = StringUtils.isNotBlank(user.getPcEmail())? user.getPcEmail() : user.getMobileEmail();
		//send notifications for unfinalized Orders
		if (CollectionUtils.isNotEmpty(ordersForPublishIdList)) {
			LOGGER.info("Sending consolidated email for publish Order Sheet");
			Map<String, Set<String>> mapOfEmailToOrderDates = OrderSheetUtil.consolidateOrdersForEmail(
					buyerMap, savedOrders,"buyer");
			for (Map.Entry<String, Set<String>> emailToOrderDates : mapOfEmailToOrderDates.entrySet()) {
				this.sendConsolidatedMailNotification(new ArrayList<String>(
						emailToOrderDates.getValue()), "Publish", user
						.getUserName(), fromAddress,
						new String[] { emailToOrderDates.getKey() });
			}
		}
		

		LOGGER.info("Update publish Order...end");
	
	
	
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#unfinalizedOrder(java.util.Map)
	 */
	@Override
	public void updateUnfinalizedOrder(Integer orderId, Integer finalizedBy, Integer unfinalizedBy) {
		orderDao.unfinalizeOrder(orderId, finalizedBy, unfinalizedBy);
		allocationDao.deleteAllocItems(orderId);
		receivedSheetDao.deleteReceiveItems(orderId);
	}
	
	@Override
	public List<Order> updateUnFinalizeOrder(User user, List<Order> ordersForUnfinalize) throws Exception {
		LOGGER.info("Update Unfinalize Order...start");

		//save Orders as unfinalized
		List<Integer> ordersForUnFinalizeIdList = OrderSheetUtil.getOrderIdList(ordersForUnfinalize);

		LOGGER.info("Orders to Unfinalize: count:"+ordersForUnFinalizeIdList.size());
		if (CollectionUtils.isNotEmpty(ordersForUnFinalizeIdList)) {
			unfinalizeOrderAndDeleteAllocRcvItems(ordersForUnFinalizeIdList, user.getUserId());
		}
		
		
		/*
		 * Notifications done in the end of the process to ensure that email is
		 * sent only when all DB operations are successful
		 */
		
		Map<Integer, User> buyerMap = new HashMap<Integer, User>();

		
		for (Order order : ordersForUnfinalize) {
			
			if(buyerMap.containsKey(order.getBuyerId())){
				continue;
			}
			User userOrder = usersInfoDaos.getUserById(order.getBuyerId());

			buyerMap.put(userOrder.getUserId(), userOrder);
		}
		
		String fromAddress = StringUtils.isNotBlank(user.getPcEmail())? user.getPcEmail() : user.getMobileEmail();
		//send notifications for unfinalized Orders
		if (CollectionUtils.isNotEmpty(ordersForUnFinalizeIdList)) {
			LOGGER.info("Sending consolidated email for Unfinalize Order Sheet");
			Map<String, Set<String>> mapOfEmailToOrderDates = OrderSheetUtil.consolidateOrdersForEmail(
					buyerMap, ordersForUnfinalize,"buyer");
			for (Map.Entry<String, Set<String>> emailToOrderDates : mapOfEmailToOrderDates.entrySet()) {
				this.sendConsolidatedMailNotification(new ArrayList<String>(
						emailToOrderDates.getValue()), "Unfinalize", user
						.getUserName(), fromAddress,
						new String[] { emailToOrderDates.getKey() });
			}
		}
		

		LOGGER.info("Update Unfinalized Order...end");
		return ordersForUnfinalize;
	}
	
	private void unfinalizeOrderAndDeleteAllocRcvItems(List<Integer> orderIdList, Integer unfinalizedBy) throws Exception {
		LOGGER.info("Batch Unfinalized Order...start");
		orderDao.unfinalizeOrderBatch(orderIdList, unfinalizedBy);
		allocationDao.deleteAllocItemsBatch(orderIdList);
		receivedSheetDao.deleteReceivedItemByBatch(orderIdList);
		LOGGER.info("Batch Unfinalized Order...end");
		
	}


	
	public Order combineOrders(List<Order> orders) {
		//Combine Orders status with at least one occurrence
		Order order = new Order();
		for (Order _order : orders) {
			//for Publish
			if (!NumberUtil.isNullOrZero(_order.getOrderSavedBy())
				&& (NumberUtil.isNullOrZero(_order.getOrderPublishedBy()))
				&& (NumberUtil.isNullOrZero(order.getOrderPublishedBy()))) {
				order.setOrderPublishedBy(99999);
			}
			//for Finalize
			if (!NumberUtil.isNullOrZero(_order.getOrderPublishedBy())
				&& (NumberUtil.isNullOrZero(_order.getOrderFinalizedBy()))
				&& (NumberUtil.isNullOrZero(order.getOrderFinalizedBy()))) {
				order.setOrderFinalizedBy(99999);
			}
			//for UnFinalize (Allocation not Finalized)
			if (!NumberUtil.isNullOrZero(_order.getOrderFinalizedBy())
				&& NumberUtil.isNullOrZero(_order.getAllocationFinalizedBy())
				&& NumberUtil.isNullOrZero(order.getAllocationFinalizedBy())) {
				order.setAllocationFinalizedBy(99999);
			}
		}
		return order;
	}
	
	public Order combineSelectedOrders(List<Order> orders) {
		//Combine Orders with same status for all occurrence
		Order order = new Order();
		order.setOrderSavedBy(99999);
		order.setOrderLockedBy(99999);
		order.setOrderPublishedBy(99999);
		order.setOrderFinalizedBy(99999);
		order.setOrderUnfinalizedBy(99999);
		order.setAllocationFinalizedBy(99999);
		for (Order _order : orders) {
			if (NumberUtil.isNullOrZero(_order.getOrderSavedBy())) {
				order.setOrderSavedBy(null);
			}
			if (NumberUtil.isNullOrZero(_order.getOrderPublishedBy())) {
				order.setOrderPublishedBy(null);
			}
			if (NumberUtil.isNullOrZero(_order.getOrderLockedBy())) {
				order.setOrderLockedBy(null);
			}
			if (NumberUtil.isNullOrZero(_order.getOrderFinalizedBy())) {
				order.setOrderFinalizedBy(null);
			}
			if (NumberUtil.isNullOrZero(_order.getOrderUnfinalizedBy())) {
				order.setOrderUnfinalizedBy(null);
			}
			if (NumberUtil.isNullOrZero(_order.getAllocationFinalizedBy())) {
				order.setAllocationFinalizedBy(null);
			}
		}
		return order;
	}


	@Override
	public Order getOrderByDeliveryDate(Integer sellerId, Integer buyerId, String deliveryDate) {
		return orderDao.getOrderByDeliveryDate(sellerId, buyerId, deliveryDate);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#loadDefualtOrderItems(java.util.List, java.util.Map)
	 */
	@Override
	public DefaultOrder loadDefualtOrderItems(Integer sellerId, Integer selectedBuyerId, Integer sheetTypeId, String deliveryDate) {
		Integer orderId = null;
		
		DefaultOrder dOrder = new DefaultOrder();
		
		// get Fruits Order Items
		dOrder.setOrderItemsFruits(orderSheetDao.loadPreviousOrderItems(sellerId, selectedBuyerId,1,sheetTypeId,deliveryDate));
		// get Vegetables Order Items
		dOrder.setOrderItemsVegetables(orderSheetDao.loadPreviousOrderItems(sellerId, selectedBuyerId,2,sheetTypeId,deliveryDate));
		// get Fish Order Items
		dOrder.setOrderItemsFish(orderSheetDao.loadPreviousOrderItems(sellerId, selectedBuyerId,3,sheetTypeId,deliveryDate));
		
		Order order = new Order();
		order.setSellerId(sellerId);
		order.setBuyerId(selectedBuyerId);
		order.setDeliveryDate(deliveryDate);
		order.setCopiedFromOrderId(dOrder.getOrderId());
		orderId = orderDao.insertOrder(order);
		
		
		dOrder.setOrderId(orderId.intValue());
		
		return dOrder;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#updateDefaultOrderItems(com.freshremix.model.DefaultOrder, java.util.Map)
	 */
	@Override
	public void updateDefaultOrderItems(DefaultOrder dOrder, Integer sellerId, Integer selectedBuyerId, Integer sheetTypeId, String deliveryDate) {

		deleteAllOrderItemsByOrderId(dOrder.getOrderId());

		saveBulkOrders(dOrder.getOrderId(),sellerId, selectedBuyerId, deliveryDate);
		
	}



	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#deleteOrderItems(java.lang.Integer, java.util.List)
	 */
	@Override
	public void deleteOrderItems(Integer orderId, List<Integer> skuId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId.toArray());
		orderItemDao.deleteOrderItem(param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#deleteAllOrderItemsByOrderId(java.lang.Integer)
	 */
	@Override
	public void deleteAllOrderItemsByOrderId(Integer orderId) {
		orderItemDao.deleteAllOrderItemsByOrderId(orderId);
	}
	
	private void insertOrderItem(Integer orderId, Integer skuId, Integer origSkuId, BigDecimal quantity, String sosVisFlag) {
		orderItemDao.insertOrderItem(orderId, skuId, origSkuId, quantity, sosVisFlag);
	}
	
	private void updateOrderItem(Integer orderId, Integer skuId, Integer origSkuId, BigDecimal quantity, String sosVisFlag, boolean updateNewSKU, boolean isMetaInfoChanged) {
		orderItemDao.updateOrderItem(orderId, skuId, origSkuId, quantity, sosVisFlag, false, updateNewSKU, isMetaInfoChanged);
	}

	private void updateOrderItemSkuId(Integer orderId, Integer skuId, Integer origSkuId, boolean updateNewSKU) {
		orderItemDao.updateOrderItemSkuId(orderId, skuId, origSkuId, updateNewSKU);
	}
	
	@Override
	public void loadOrderItemQuantities(Map<String, Object> skuOrderMap,
			List<Integer> companyBuyerIds, String deliveryDate, SKU skuObj, JSONObject json) throws Exception {
		Map<String, Object> _param = new HashMap<String,Object>();
		_param.put("deliveryDate", deliveryDate);
		_param.put("skuId", skuObj.getSkuId());
		Map<Integer, OrderItem> orderItemMap = orderSheetDao.getOrderItemsMapOfSkuDate(_param);
		
		BigDecimal rowqty = new BigDecimal(0);
		//unlocked: 0, locked: 1
		int chkCtr = 0;
		for (Integer buyerId : companyBuyerIds) {
			OrderItem orderItem = orderItemMap.get(buyerId);
			BigDecimal quantity = null;
			//BigDecimal skuMaxLimit = null;
			String strQuantity = "";
			String sosVisFlag = "0";
			//String strSkuMaxLimit = "";
			String strLockFlag = "0";
			String skuFlag = "0";
			
			if (orderItem != null) {
				quantity = orderItem.getQuantity();
				//skuMaxLimit = orderItem.getSkuMaxLimit();
				
				if (orderItem.getSosVisFlag().equals("0")) {
					sosVisFlag = "0";
				} else {
					chkCtr++;
					sosVisFlag = "1";
				}
				
				System.out.println("quantity:[" + quantity + "]");
				if (quantity != null) {
					rowqty = rowqty.add(quantity);
					strQuantity = quantity.toPlainString();
				}
				
				// SKU finalized checking
				if (!NumberUtil.isNullOrZero(orderItem.getOrder().getOrderFinalizedBy())) {
					skuFlag = "1";
					strLockFlag = "1";
				}
			}
			else {
				// not visible
				//strQuantity = "-999";
				strLockFlag = "1";
			}
			
			json.put("Q_" + buyerId.toString(), strLockFlag);
			json.put("V_" + buyerId.toString(), strLockFlag);
			if (!json.has("sku") && skuFlag.equals("1")) {
				json.put("sku", skuFlag);
			}
			skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
			skuOrderMap.put("V_" + buyerId.toString(), sosVisFlag);
		}
		if (chkCtr == companyBuyerIds.size()) {
			skuOrderMap.put("visall", "1");
		} else {
			skuOrderMap.put("visall", "0");
		}
		
		setSkuLocking(orderItemMap, json);
		skuOrderMap.put("rowqty", rowqty);
	}
	
	private void setSkuLocking(Map<Integer, OrderItem> orderItemMap,
			JSONObject json) throws Exception {
		Set<Integer> keys = orderItemMap.keySet();
		Iterator<Integer> it = keys.iterator();
		while(it.hasNext()) {
			Integer key = it.next();
			OrderItem oi = orderItemMap.get(key);
			if (!NumberUtil.isNullOrZero(oi.getOrder().getOrderFinalizedBy())) {
				json.put("sku", "1");
			}
		}
	}

	@Override
	public void loadOrderItemQuantities(Map<String, Object> skuOrderMap,
			List<String> deliveryDates, Integer buyerId, SKU skuObj, JSONObject json)  throws Exception {
		Map<String, Object> _param = new HashMap<String,Object>();
		_param.put("deliveryDates", deliveryDates);
		_param.put("skuId", skuObj.getSkuId());
		_param.put("buyerId", buyerId);
		Map<Integer, OrderItem> orderItemMap = orderSheetDao.getOrderItemsMapOfSkuDates(_param);
		//unlocked: 0, locked: 1
		
		
		System.out.println(orderItemMap);
		
		BigDecimal rowqty = new BigDecimal(0);
		for (String deliveryDate : deliveryDates) {
			OrderItem orderItem = orderItemMap.get(deliveryDate);
			BigDecimal quantity = null;
			String strQuantity = "";
			String strLockFlag = "0";
			
			if (orderItem != null) {
				quantity = orderItem.getQuantity();
			
				System.out.println("quantity:[" + quantity + "]");
				if (quantity != null) {
					rowqty = rowqty.add(quantity);
					strQuantity = quantity.toPlainString();
				}
				
				// SKU finalized checking
				if (!NumberUtil.isNullOrZero(orderItem.getOrder().getOrderFinalizedBy())) {
					json.put("sku", 1);
					strLockFlag = "1";
				}
			}
			else {
				//strQuantity = "-999";
				strLockFlag = "1";
			}

			json.put("Q_" + deliveryDate, strLockFlag);
			skuOrderMap.put("Q_" + deliveryDate, strQuantity);
		}
		skuOrderMap.put("rowqty", rowqty);
		skuOrderMap.put("lockflag", json.toString());
	}
	
	@Override
	public void loadSumOrderItemQuantitiesAllBuyers(Map<String, Object> skuOrderMap,
			List<Integer> buyerIds, List<Integer> orderIds, SKU skuObj) {
		Map<Integer, OrderItem> orderItemMap = orderSheetDao.getSumOrderItemsMapOfSkuBuyers(orderIds, skuObj.getSkuId());
		
		for (Integer buyerId : buyerIds) {
			OrderItem orderItem = orderItemMap.get(buyerId);
			BigDecimal quantity = null;
			String strQuantity = "";
			
			if (orderItem != null) {
				quantity = orderItem.getQuantity();
				
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
	public void loadSumOrderItemQuantitiesAllDates(Map<String, Object> skuOrderMap,
			List<String> deliveryDates, List<Integer> orderIds, SKU skuObj) {
		
		Map<Integer, OrderItem> orderItemMap = orderSheetDao.getSumOrderItemsMapOfSkuDates(orderIds, skuObj.getSkuId());
		
		for (String deliveryDate : deliveryDates) {
			OrderItem orderItem = orderItemMap.get(deliveryDate);
			BigDecimal quantity = null;
			String strQuantity = "";
			 
			if (orderItem != null) {
				quantity = orderItem.getQuantity();
				
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
	

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#insertDefaultOrders()
	 */
	public void insertDefaultOrders(OrderSheetParam orderSheetParam, User user, DealingPattern dp) throws Exception {
		// loop buyer and insert default order if applicable
		//Integer sellerId = user.getUserId();
		Order order = null;
		DefaultOrder dOrder = null;
		//Map<String,Integer> buyerOrderIdMap = null;
		/* true only if (1) search from filter (2) clicking previous <<< or next >>> */
		if (orderSheetParam.isCheckDBOrder()) {
			//buyerOrderIdMap = new HashMap<String,Integer>();
			List<String> deliveryDates = DateFormatter.getDateList(orderSheetParam.getStartDate(), orderSheetParam.getEndDate());
			for (String deliveryDate : deliveryDates) {
				Map<String,List<Integer>> sellerToBuyerDP = dp.getSellerToBuyerDPMap().get(deliveryDate);
				Set<String> sellers = sellerToBuyerDP.keySet();
				for (String _seller : sellers) {
					Integer sellerId = Integer.valueOf(_seller);
					//for (Integer buyerId : OrderSheetUtil.toList(orderSheetParam.getSelectedBuyerID())) {
					for (Integer buyerId : sellerToBuyerDP.get(_seller)) {
						order = getOrderByDeliveryDate(sellerId, buyerId, deliveryDate);
						//transferred to where query is really needed so that it is not executed even for other conditions
						//Order oldOrder = orderDao.getPreviousOrder(sellerId, buyerId, deliveryDate);
						if (order == null) {
							Order oldOrder = orderDao.getPreviousOrder(sellerId, buyerId, deliveryDate);
							dOrder = loadDefaultOrderItems(sellerId, buyerId, orderSheetParam.getSheetTypeId(), deliveryDate,oldOrder);
							//saveDefaultOrderItems(dOrder);
							if (oldOrder == null){//Added code for newly added dealing pattern buyer - 2/15/11
								List<Integer> selectedBuyerIds = sellerToBuyerDP.get(_seller);
								orderItemDao.insertBulkOrderItemNewBuyer(dOrder.getOrderId(),sellerId, buyerId, deliveryDate, selectedBuyerIds);
							} else {
								saveBulkOrders(dOrder.getOrderId(),sellerId, buyerId, deliveryDate);
							}
//							buyerOrderIdMap.put(deliveryDate + "_" + sellerId.toString() +
//									"_" + buyerId.toString(), dOrder.getOrderId());
						} else {		
							Integer savedBy = order.getOrderSavedBy();
							Boolean isNotSaved = savedBy == null || savedBy == 0 ? true : false;
							if (isNotSaved) {
								Order oldOrder = orderDao.getPreviousOrder(sellerId, buyerId, deliveryDate);
								
								try {	
									if(isCopiedDataStale(order, oldOrder)){
										dOrder = loadDefaultOrderItems(sellerId, buyerId, orderSheetParam.getSheetTypeId(),
												deliveryDate,oldOrder);
										//saveDefaultOrderItems(dOrder);
										saveBulkOrders(dOrder.getOrderId(),sellerId, buyerId, deliveryDate);
									}
						
								} catch (Exception e) {
									//e.printStackTrace();
									// System.out.println("this is fine.");
									// order is already existing but not yet saved.
									updateOrder(order.getOrderId(),oldOrder);
									dOrder = new DefaultOrder();
									dOrder.setOrderId(order.getOrderId().intValue());
									updateDefaultOrderItems(dOrder, sellerId, buyerId, orderSheetParam.getSheetTypeId(), deliveryDate);
								}
							}
						}
					}
				}
			}
		}
	}

	
	@Override
	public void updateOrder(Integer orderId,Order oldOrder) {
		Order order = new Order();
		order.setOrderId(orderId);
		order.setCopiedFromOrderId(oldOrder.getOrderId());
		order.setCopiedFromTimeStamp(oldOrder.getLastSavedOsTimeStamp());
		orderDao.updateOrder(order);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#unpublishOrder(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateUnpublishOrder(Integer orderId, Integer publishBy, Integer orderUnfinalizedBy) {
		orderDao.unpublishOrder(orderId, publishBy, orderUnfinalizedBy);
	}
	
	@Override
	public void updateUnpublishOrder(User user, List<Order> publishedOrders) throws Exception {
		LOGGER.info("Update Unpublish Order...start");

		
		List<Integer> orderIdListToUnpublish = new ArrayList<Integer>();
		List<Order> orderListToUnpublish = new ArrayList<Order>();
		Map<Integer, User> buyerMap = new HashMap<Integer, User>();
		if(CollectionUtils.isNotEmpty(publishedOrders)){
			for(Order order: publishedOrders){
				if(NumberUtil.isNullOrZero(order.getOrderLockedBy())){
					orderIdListToUnpublish.add(order.getOrderId());
					orderListToUnpublish.add(order);
					//orderSheetService.updateUnpublishOrder(order.getOrderId(), null, user.getUserId());
					if(buyerMap.containsKey(order.getBuyerId())){
						continue;
					}
					User userOrder = usersInfoDaos.getUserById(order.getBuyerId());
	
					buyerMap.put(userOrder.getUserId(), userOrder);
			
					
				}
			}
		}
		
		//save Orders as unfinalized
		

		LOGGER.info("Orders for unpublish: count:"+orderIdListToUnpublish.size());
		if (CollectionUtils.isNotEmpty(orderIdListToUnpublish)) {
			orderDao.unpublishOrderByBatch(orderIdListToUnpublish, user.getUserId());
		}
		
		/*
		 * Notifications done in the end of the process to ensure that email is
		 * sent only when all DB operations are successful
		 */
		
		
		String fromAddress = StringUtils.isNotBlank(user.getPcEmail())? user.getPcEmail() : user.getMobileEmail();
		//send notifications for unfinalized Orders
		if (CollectionUtils.isNotEmpty(orderIdListToUnpublish)) {
			LOGGER.info("Sending consolidated email for Unpublish Order Sheet");
			Map<String, Set<String>> mapOfEmailToOrderDates = OrderSheetUtil.consolidateOrdersForEmail(
					buyerMap, orderListToUnpublish,"buyer");
			for (Map.Entry<String, Set<String>> emailToOrderDates : mapOfEmailToOrderDates.entrySet()) {
				this.sendConsolidatedMailNotification(new ArrayList<String>(
						emailToOrderDates.getValue()), "Unpublish", user
						.getUserName(), fromAddress,
						new String[] { emailToOrderDates.getKey() });
			}
		}
		

		LOGGER.info("Update Unpublish Order...end");
	}
	
	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#getSelectedOrdersByBuyer(java.util.List, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Integer> getSelectedOrdersByBuyer(List<Integer> seller,
			Integer buyer, String startDate, String endDate) {
		List<String> deliveryDates = DateFormatter.getDateList(startDate, endDate);
		List<Integer> selectedOrders = new ArrayList<Integer>();
		for (Integer _seller : seller) {
			selectedOrders.addAll(orderDao.getSelectedOrdersByBuyer(_seller, buyer, deliveryDates));
		}
		return selectedOrders;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#getSelectedOrdersByDate(java.util.List, java.util.List, java.lang.String)
	 */
	@Override
	public List<Integer> getSelectedOrdersByDate(List<Integer> seller,
			List<Integer> buyer, String selectedDate) {
		List<Integer> selectedOrders = new ArrayList<Integer>();
		for (Integer _seller : seller) {
			selectedOrders.addAll(orderDao.getSelectedOrdersByDate(_seller, buyer, selectedDate));
		}
		return selectedOrders;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#getOrders(java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public List<Order> getSavedOrders(List<Integer> buyerIds, List<String> dateList, List<Integer> sellerIds) {
		List<Order> allOrders = orderDao.getSavedOrders(buyerIds, dateList, sellerIds);
		return allOrders;
	}
	
	@Override
	public List<Order> getAllOrders(List<Integer> buyerIds, List<String> dateList, List<Integer> sellerIds) {
		// to prevent missing expression error in sql statements
		if (CollectionUtils.isEmpty(buyerIds)
				|| CollectionUtils.isEmpty(dateList)
				|| CollectionUtils.isEmpty(sellerIds)) {
			return new ArrayList<Order>();
		}
		List<Order> allOrders = orderDao.getAllOrders(buyerIds, dateList, sellerIds);
		if(allOrders==null) {
			allOrders = new ArrayList<Order>();
		}
		return allOrders;
	}
	
	@Override
	public List<OrderItem> getOrderItemsByOrderIdBulk(List<Integer> orderIds) {
		if (CollectionUtils.isEmpty(orderIds)) {
			throw new IllegalArgumentException("List of OrderIds is empty");
		}
		
		return orderSheetDao.getOrderItemsByOrderIdBulk(orderIds);
	}
	
	@Override
	public void saveWSData(User loginUser, List<WSSellerSKU> sellerSkuList, List<Integer> sellerIds, List<Integer> buyerIds, List<String> dateList, List<Order> orderList,List<SKU> dSKUList) throws Exception {
		Map<CompositeKey<Integer>, Order> sellerBuyerOrderMap = new HashMap<CompositeKey<Integer>, Order>();
		Map<String, OrderItem> dOrderItemsMap = new HashMap<String, OrderItem>();
		if(CollectionUtils.isNotEmpty(orderList)){
			//getDefaultOrders
			//List<Order> dOrders = getDefaultOrders(buyerIds, sellerIds, dateList);

			sellerBuyerOrderMap = OrderSheetUtil.convertToSellerBuyerOrderMap(orderList);
			
			
			
			List<OrderItem> allOrderItems = getOrderItemsByOrderIdBulk(OrderSheetUtil.getOrderIdList(orderList));
			dOrderItemsMap = OrderSheetUtil.convertToOrderItemsMap(allOrderItems);
			if(dOrderItemsMap==null){
				dOrderItemsMap = new HashMap<String, OrderItem>();
			}
		}
		
		List<OrderItem> oitemsForUpdate = new ArrayList<OrderItem>();
		List<OrderItem> oitemsForAdd = new ArrayList<OrderItem>();
		List<Integer> ordersToFinalize =  new ArrayList<Integer>();
		for(WSSellerSKU sellerSKU : sellerSkuList){
			//SKU
			SKU sku = sellerSKU.getSku();
			if(NumberUtil.isNullOrZero(sku.getSkuGroup().getSkuGroupId())){
				SKUGroup skugroup = sku.getSkuGroup();
				skuGroupDao.saveSKUGroup(skugroup);
				sku.setSkuGroup(skugroup);
			}
			boolean isMatch = false;
			SKU skuMatched = null;
			for(SKU skuDB : dSKUList){
				if(isMatch(sku, skuDB)){
					isMatch=true;
					skuMatched = skuDB;
					break;
				}
			}
			if(!isMatch){
				//insert new SKU
				Integer id = skuDao.insertSKU(sku);
				sku.setSkuId(id);
			}else{
				

				//setorderitem skuid
				sku.setSkuId(skuMatched.getSkuId());
			}
			Integer skuId = sku.getSkuId();
			
			//Order/OrderItems
			processWSDetails(loginUser, sellerBuyerOrderMap, dOrderItemsMap, oitemsForUpdate,
					oitemsForAdd,ordersToFinalize, sellerSKU);
				
		}
		try {

			orderItemDao.insertOrderItemByBatch(oitemsForAdd);
			orderItemDao.updateOrderItemByBatch(oitemsForUpdate, true, false, false);
			finalizeOrderAndInsertDefaultAllocItems(ordersToFinalize, loginUser.getUserId());
		} catch (Exception e) {
			throw e;
		}
	
	}
	
	private boolean isMatch(SKU input, SKU sku) {

		if (!( StringUtil.nullToBlank(input.getSkuName()).equals(
				StringUtil.nullToBlank(sku.getSkuName())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getSkuCategoryId()).equals(
				StringUtil.nullToBlank(sku.getSkuCategoryId())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getSkuGroup().getSkuGroupId()).equals(
				StringUtil.nullToBlank(sku.getSkuGroup().getSkuGroupId())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getLocation()).equals(
				StringUtil.nullToBlank(sku.getLocation())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getMarket()).equals(
				StringUtil.nullToBlank(sku.getMarket())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getGrade()).equals(
				StringUtil.nullToBlank(sku.getGrade())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getClazz()).equals( 
				StringUtil.nullToBlank(sku.getClazz())) )) {
			return false;
		} else if (!input.getPackageQuantity().toPlainString().equals(
				sku.getPackageQuantity().toPlainString())) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getPackageType()).equals(
				StringUtil.nullToBlank(sku.getPackageType())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getOrderUnit().getOrderUnitId()).equals(
				StringUtil.nullToBlank(sku.getOrderUnit().getOrderUnitId())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getExternalSkuId()).equals(
				StringUtil.nullToBlank(sku.getExternalSkuId())) )) {
			return false;
		}
		
		
		if (input.getPrice1() == null) {
			if (sku.getPrice1() != null){
				return false;
			}
		} else {
			if (sku.getPrice1() == null){
				return false;
			}else if(!input.getPrice1().toPlainString().equals(
					sku.getPrice1().toPlainString())){
				return false;
			}
		}
		
		if (input.getPrice2() == null) {
			if (sku.getPrice2() != null){
				return false;
			}			
		} else {
			if (sku.getPrice2() == null){
				return false;
			}else if(!input.getPrice2().toPlainString().equals(
					sku.getPrice2().toPlainString())){
				return false;
			}
		}
		
		if (input.getPriceWithoutTax() == null) {
			if (sku.getPriceWithoutTax() != null){
				return false;
			}			
		} else {
			if (sku.getPriceWithoutTax() == null){
				return false;
			}else if(!input.getPriceWithoutTax().toPlainString().equals(
					sku.getPriceWithoutTax().toPlainString())){
				return false;
			}
		}

		return true;
	}

	private void processWSDetails(User loginUser,
			Map<CompositeKey<Integer>, Order> sellerBuyerOrderMap,
			Map<String, OrderItem> dOrderItemsMap,
			List<OrderItem> oitemsForUpdate, List<OrderItem> oitemsForAdd,
			List<Integer> ordersToFinalize, WSSellerSKU sellerSKU) {
		
		SKU sku = sellerSKU.getSku();
		Integer skuId = sku.getSkuId();
		
		
		for(WSSellerBuyerDetails detail : sellerSKU.getDetails()){
			Order order = detail.getOrder();
			//if orderexists
			if(order!=null){
				
				String orderItemKey = OrderSheetUtil.formatOrderItemKey(order.getOrderId(), skuId);
				OrderItem dboitem = dOrderItemsMap.get(orderItemKey);
				//check if orderitem for skuid -order exists
				if(dboitem!=null){
					//if order item exists update order item
					dboitem.setQuantity(detail.getQtyDec());
					dboitem.setSosVisFlag(detail.getVisibility());
					oitemsForUpdate.add(dboitem);
				}else{
					//else add new orderitem
					//System.out.println(order.getOrderId()+" - "+ sku.getSkuId());
					OrderItem oitem = new OrderItem();
					oitem.setQuantity(detail.getQtyDec());
					oitem.setSosVisFlag(detail.getVisibility());
					oitem.setOrder(order);
					oitem.setSku(sku);
					oitemsForAdd.add(oitem);
					dOrderItemsMap.put(orderItemKey, oitem);
				}
			}else
			{
				//check if has previous order
				CompositeKey<Integer> key = new CompositeKey<Integer>(sellerSKU.getSeller().getUserId(),detail.getBuyer().getUserId());
				Order dOrder = sellerBuyerOrderMap.get(key);
				if(dOrder==null){
					order = new Order();
					order.setSellerId(sellerSKU.getSeller().getUserId());
					order.setBuyerId(detail.getBuyer().getUserId());
					order.setDeliveryDate(sellerSKU.getOrderDate());
					order.setOrderSavedBy(loginUser.getUserId());
					order.setCreatedBy(loginUser.getUserId());
					if(dOrder!=null && dOrder.getDeliveryDate().equals(sellerSKU.getOrderDate())){
						order.setCopiedFromTimeStamp(dOrder.getLastSavedOsTimeStamp());
						order.setCopiedFromOrderId(dOrder.getOrderId());
					}
					//insert order and insert new order item
					
					Integer orderId = orderDao.insertOrder(order);
					order.setOrderId(orderId.intValue());
					sellerBuyerOrderMap.put(key, order);
				}else
				{
					order=dOrder;
				}
				OrderItem oitem = new OrderItem();
				oitem.setQuantity(detail.getQtyDec());
				oitem.setSosVisFlag(detail.getVisibility());
				oitem.setSku(sku);
				oitem.setOrder(order);
				oitemsForAdd.add(oitem);
				//check dorders
				
			}
			
			if(sellerSKU.getFinalizeFlag() && !ordersToFinalize.contains(order.getOrderId())){
				ordersToFinalize.add(order.getOrderId());
			}

			
		}
	}
	
	
	private  List<Order> getDefaultOrders(List<Integer> buyerIds, List<Integer> sellerIds, List<String> dateList)
	{
		List<Order> result = new ArrayList<Order>();
		
		List<Order> allOrders = orderDao.getAllOrders(buyerIds, dateList, sellerIds);
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);

		for(String deliveryDate:dateList){
			for(Integer sellerId : sellerIds){
				for(Integer buyerId : buyerIds){
					String key = OrderSheetUtil.formatOrderMapKey(deliveryDate, buyerId, sellerId);
					Order order = allOrdersMap.get(key);
					if(order==null){
						//getPreviousOrder
						Order oldOrder = orderDao.getPreviousOrder(sellerId, buyerId,
								deliveryDate);
						if(oldOrder!=null){
							allOrdersMap.put(key, oldOrder);
							result.add(oldOrder);
						}
					}else{
						result.add(order);
					}
					
				}
			}
		}
		
		return result;
		
	}
	

	public List<SKU> getDistinctSKUs(
			List<Integer> selectedOrders, 
			List<Integer> sellerId,
			List<Integer> buyerId,
			List<String> deliveryDate,
			Integer categoryId, 
			Integer sheetTypeId, 
			String hasQty) {
		
		if (hasQty.equals("0")) {
			hasQty = null;
		}
		
		return orderSheetDao.selectDistinctSKUs(selectedOrders, sellerId, buyerId, deliveryDate, categoryId, sheetTypeId, hasQty);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#getSelectedOrders(java.util.Map)
	 */
	@Override
	public List<Order> getSelectedOrders(Map<String, Order> allOrdersMap, OrderSheetParam osp) {
		List<String> dateList = null;
		List<Integer> buyerIds = null;
		List<Integer> sellerIds = OrderSheetUtil.toList(osp.getSelectedSellerID());
		
		if (!osp.isAllDatesView()) {
			dateList = DateFormatter.getDateList(osp.getSelectedDate(), osp.getSelectedDate());
			buyerIds = OrderSheetUtil.toList(osp.getSelectedBuyerID());
		}
		else {
			dateList = DateFormatter.getDateList(osp.getStartDate(), osp.getEndDate());
			buyerIds = OrderSheetUtil.toList(osp.getDatesViewBuyerID());
		}
		
		List<Order> selectedOrders = new ArrayList<Order>();
		for (String thisDate : dateList) {
			for (Integer buyerId : buyerIds) {
				for (Integer sellerId : sellerIds) {
					String key = OrderSheetUtil.formatOrderMapKey(thisDate, buyerId.toString(), sellerId.toString());
					Order thisOrder = allOrdersMap.get(key);
					//if (thisOrder != null && !NumberUtil.isNullOrZero(thisOrder.getOrderSavedBy())) {
					if (thisOrder != null) {
						selectedOrders.add(thisOrder);
					}	
				}
			}
		}
		
		return selectedOrders;
		
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#getSelectedOrderIds(java.util.Map, com.freshremix.model.OrderSheetParam)
	 */
	@Override
	public List<Integer> getSelectedOrderIds(Map<String, Order> allOrdersMap, OrderSheetParam osp) {
		List<String> dateList = null;
		List<Integer> buyerIds = null;
		List<Integer> sellerIds = OrderSheetUtil.toList(osp.getSelectedSellerID());
		
		if (!osp.isAllDatesView()) {
			dateList = DateFormatter.getDateList(osp.getSelectedDate(), osp.getSelectedDate());
			buyerIds = OrderSheetUtil.toList(osp.getSelectedBuyerID());
		}
		else {
			dateList = DateFormatter.getDateList(osp.getStartDate(), osp.getEndDate());
			buyerIds = OrderSheetUtil.toList(osp.getDatesViewBuyerID());
		}
		
		List<Integer> selectedOrderIds = new ArrayList<Integer>();
		for (String thisDate : dateList) {
			for (Integer buyerId : buyerIds) {
				for (Integer sellerId : sellerIds) {
					String key = OrderSheetUtil.formatOrderMapKey(thisDate, buyerId.toString(), sellerId.toString());
					Order thisOrder = allOrdersMap.get(key);
//					if (thisOrder != null && !NumberUtil.isNullOrZero(thisOrder.getOrderSavedBy())) {
//						selectedOrderIds.add(thisOrder.getOrderId());
//					}
					if (thisOrder != null) {
						selectedOrderIds.add(thisOrder.getOrderId());
					}
				}
			}
		}
		
		return selectedOrderIds;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#getSellerNames(java.util.List)
	 */
	// ENHANCEMENT 20120724: Lele - Redmine 797
	@Deprecated
	@Override
	public String getSellerNames(List<Integer> sellerId) throws Exception {
		// FORDELETION START 20120724: Lele - Redmine 797
//		JSONObject json = new JSONObject();
//		for (Integer _sellerId : sellerId) {
//			User user = usersInfoDaos.getUserById(_sellerId);
//			json.put(user.getShortName(), user.getShortName());
//		}
//		return json.toString();
		// FORDELETION END 20120724:
		// ENCHANCEMENT 20120724: Lele - Redmine 797
		throw new MethodNotSupportedException("Method should not be used.");
	}
	
	// ENCHANCEMENT START 20120724: Lele - Redmine 797
	@Override
	public String getSellerNames(List<Integer> sellerId, Integer categoryId) throws Exception {
		JSONObject json = new JSONObject();
		for (User user : categoryService.filterSellerIdByCategory(sellerId, categoryId)) {
			json.put(user.getShortName(), user.getShortName());
		}
		return json.toString();
	}
	
	@Override
	public List<Integer> filterSellerByCategoryId (List<Integer> sellerId, Integer categoryId) {
		List<Integer> _seller = new ArrayList<Integer>();
		for (User user : categoryService.filterSellerIdByCategory(sellerId, categoryId)) {
			_seller.add(user.getUserId());
		}
		
		return _seller;
	}
	// ENHANCEMENT END 20120724: Lele
	
	public void setSellerNameAndRenderer(
			List<Integer> sellerId, 
			StringBuffer sellerName, 
			StringBuffer sellerNameRenderer) throws Exception {
		
		String delimeter = "";
		
		sellerNameRenderer.append("{ 'sellerNameRenderer' : ");
		sellerNameRenderer.append("[ { \\\"id\\\" : \\\"0\\\", \\\"caption\\\" : \\\" \\\" },");
		
		sellerName.append("{'0' : ' '");
		
		for (int i=0; i<sellerId.size(); i++) {
			Integer _sellerId = sellerId.get(i);
			User user = usersInfoDaos.getUserById(_sellerId);
			sellerName.append(",'" + user.getUserId() + "':'" + user.getShortName() + "'");
			sellerNameRenderer.append(" { \\\"id\\\" : \\\"" + user.getUserId() + "\\\", \\\"caption\\\" : \\\"" + user.getShortName() + "\\\" }");
			if (i < sellerId.size() -1) {
				sellerNameRenderer.append(",");
			}
		}
		
		sellerName.append("}");
		sellerNameRenderer.append(" ]" + delimeter + "}" );
		
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#getFinalizedOrders(java.util.List)
	 */
	@Override
	public List<Order> getFinalizedOrders(List<Order> orders) {
		List<Order> finOrders = new ArrayList<Order>();
		for (Order order : orders) {
			if (!NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) {
				finOrders.add(order);
			}
		}
		return finOrders;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#getPublishedOrders(java.util.List)
	 */
	@Override
	public List<Order> getPublishedOrders(List<Order> orders) {
		List<Order> _orders = new ArrayList<Order>();
		for (Order order : orders) {
			if (!NumberUtil.isNullOrZero(order.getOrderPublishedBy()) &&
				NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) {
				_orders.add(order);
			}
		}
		return _orders;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#getSavedOrders(java.util.List)
	 */
	@Override
	public List<Order> getSavedOrders(List<Order> orders) {
		List<Order> _orders = new ArrayList<Order>();
		for (Order _order : orders) {
			if (!NumberUtil.isNullOrZero(_order.getOrderSavedBy()) && 
				 NumberUtil.isNullOrZero(_order.getOrderFinalizedBy())) {
				_orders.add(_order);
			}
		}
		return _orders;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#sendMailNotification(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String[], java.lang.String, java.lang.String)
	 */
	@Override
	public boolean sendMailNotification(String orderDate, String state,
			String username, String fromAddress, String[] toAddress) {
		
		String subject = null;
		String message = null;
		String date = orderDate.substring(0,4) + "/" + orderDate.substring(4,6) + "/" + orderDate.substring(6,8);
		
		if (state.equals("Publish")) {
			subject = "Order Sheet for " + date + " Publish";
			message = "Order Sheet for " + date + " Publish by " + username + " as of " + DateFormatter.getDateToday("yyyy/MM/dd h:mm a") + ".";
			//message = message + "\nAlert message is available on your Home Page Alertsâ€™ section.";
		} else if (state.equals("Unpublish")) {
			subject = "Order Sheet for " + date + " Unpublish";
			message = "Order Sheet for " + date + " Unpublish by " + username + " as of " + DateFormatter.getDateToday("yyyy/MM/dd h:mm a") + ".";
		}
		else if (state.equals("Finalize")) {
			subject = "Order Sheet for " + date + " Finalized";
			message = "Order Sheet for " + date + " Finalized by " + username + " as of " + DateFormatter.getDateToday("yyyy/MM/dd h:mm a") + ".";
		} else if (state.equals("Unfinalize")) {
			subject = "Order Sheet for " + date + " Unfinalized";
			message = "Order Sheet for " + date + " Unfinalized by " + username + " as of " + DateFormatter.getDateToday("yyyy/MM/dd h:mm a") + ".";
		}
		
		return sendMailNotification(fromAddress, toAddress, subject, message);
	}

	@Override
	public boolean sendConsolidatedMailNotification(List<String> orderDateList,
			String state, String username, String fromAddress,
			String[] toAddress) {

		Collections.sort(orderDateList, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (String orderDate : orderDateList) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(orderDate.substring(0, 4) + "/"
					+ orderDate.substring(4, 6) + "/"
					+ orderDate.substring(6, 8));
		}

		String date = sb.toString();
		boolean multipleDates = orderDateList.size() > 1;
		String dateSubj = multipleDates ? " several dates " : date;

		String subject = String
				.format("Order Sheet for %s %s", dateSubj, state);
		String multipleDatesMessage = multipleDates ? " the following dates "
				: "";
		String message = String.format(
				"Order Sheet for %s: %s \n %s  by %s as of %s .",
				multipleDatesMessage, date, state, username,
				DateFormatter.getDateToday("yyyy/MM/dd h:mm a"));

		return sendMailNotification(fromAddress, toAddress, subject, message);
	}
	
	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#sendMailNotification(java.lang.String, java.lang.String[], java.lang.String, java.lang.String)
	 */
	@Override
	public boolean sendMailNotification(String fromAddress, String[] toAddress, String subject, String message) {
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
	 * @see com.freshremix.service.OrderSheetService#getTotalQuantityByOtherBuyers(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public BigDecimal getTotalQuantityByOtherBuyers(Integer sellerId,
			List<Integer> buyerId, Integer skuId, String deliveryDate) {
		return orderSheetDao.getTotalQuantityByOtherBuyers(sellerId, buyerId, skuId, deliveryDate);
	}

	@Override
	public 	Map<Integer, Map<String, Map<Integer, OrderItem>>> getSellerOrderItems(List<Integer> skuList, 
			List<String> deliveryDates, List<Integer> buyerUserIds, boolean hasQty) throws Exception {
		
		Map<Integer, Map<String, Map<Integer, OrderItem>>> sellerOrderItemsBulk =
			new HashMap<Integer, Map<String, Map<Integer, OrderItem>>>();
		int skuCount = skuList.size();
		int loopCount = (skuCount / 1000) + 1;
		
		/* SQL WHERE column IN (?,?,?...) IS LIMITED TO 1000 */
		for (int i=0; i<loopCount; i++) {
			int startIdx = i*1000;
			List<Integer> thisSkuIds = new ArrayList<Integer>();
			for (int j=startIdx; j<startIdx+1000; j++) {
				if (skuCount > j) {
					thisSkuIds.add(skuList.get(j));
				}
			}
			
			Map<String, Object> _param = new HashMap<String,Object>();
			_param.put("deliveryDates", deliveryDates);
			_param.put("skuIds", thisSkuIds);
			_param.put("buyerIds", buyerUserIds);
			
			sellerOrderItemsBulk.putAll(orderSheetDao.getSellerOrderItems(thisSkuIds, deliveryDates, buyerUserIds, hasQty));
		}
		
		return sellerOrderItemsBulk;
	}

	/**
	 * 
	 * @param newOrder
	 * @param oldOrder
	 * @return
	 */
	private boolean isCopiedDataStale(Order newOrder, Order oldOrder){
		
		boolean status = false;
		
		if(oldOrder == null){
			return false;
		}
		
		if(!(oldOrder.getOrderId().equals(newOrder.getCopiedFromOrderId()))){
			status = true;
		}else if(!oldOrder.getLastSavedOsTimeStamp().equals(newOrder.getCopiedFromTimeStamp())){
			status = true;
		}
		
		return status;
	}

	@Override
	public DefaultOrder loadDefaultOrderItems(Integer sellerId,
			Integer selectedBuyerId, Integer sheetTypeId, String deliveryDate,
			Order oldOrder) {
		Integer orderId = null;
		
		DefaultOrder dOrder = new DefaultOrder();
		
		Order order = new Order();
		order.setSellerId(sellerId);
		order.setBuyerId(selectedBuyerId);
		order.setDeliveryDate(deliveryDate);
		
		if(oldOrder != null){
			order.setCopiedFromTimeStamp(oldOrder.getLastSavedOsTimeStamp());
			order.setCopiedFromOrderId(oldOrder.getOrderId());
		}
		
		orderId = orderDao.insertOrder(order);
		dOrder.setOrderId(orderId.intValue());
		
		return dOrder;
	}

	@Override
	public void saveBulkOrders(Integer orderId, Integer sellerId,
			Integer buyerId, String deliveryDate) {
		Map<String,Object> _param = new HashMap<String, Object>();
		_param.put("orderId", orderId);
		_param.put("sellerId", sellerId);
		_param.put("selectedBuyerId", buyerId);
		_param.put("deliveryDate", deliveryDate);
		orderItemDao.insertBulkOrderItem(_param);
	}

		@Override
	public boolean skuHasQuantity(Integer skuId, List<String> deliveryDate) {
		boolean hasQuantity = false;
		BigDecimal tmp = orderItemDao.getTotalQuantityBySkuId(skuId, deliveryDate);
		if (!NumberUtil.isNullOrZero(tmp)) {
			hasQuantity = true;
		}
		return hasQuantity;
	}

	@Override
	public List<OrderItem> getOrderItem(Integer sellerId, String deliveryDate,
			Integer skuId) {
		return orderItemDao.getOrderItem(sellerId, deliveryDate, skuId);
	}

	@Override
	public Order getPreviousOrders(Integer sellerId, Integer buyerId,
			String deliveryDate) {
		return orderDao.getPreviousOrder(sellerId, buyerId, deliveryDate);
	}

	@Override
	public List<SKU> wsGetDistinctSKUs(List<Integer> selectedOrders) {
		return orderSheetDao.wsSelectDistinctSKUs(selectedOrders);
	}

	@Override
	public void wsLoadQuantities(Integer buyerId, String deliveryDate,
			SKU skuObj, WSSKU wsSKU) throws Exception {
		Map<String, Object> _param = new HashMap<String, Object>();
		_param.put("deliveryDate", deliveryDate);
		_param.put("skuId", skuObj.getSkuId());
		Map<Integer, OrderItem> orderItemMap = orderSheetDao
				.getOrderItemsMapOfSkuDate(_param);

		BigDecimal rowqty = new BigDecimal(0);
		OrderItem orderItem = orderItemMap.get(buyerId);
		BigDecimal quantity = null;
		if (orderItem != null) {
			quantity = orderItem.getQuantity();
			if (quantity != null) {
				rowqty = rowqty.add(quantity);
			}
		}
		// set quantity to obj
		wsSKU.setQty(quantity);
	}

	
	
	
	private SKUGroup getSkuGroup(Integer sellerId,String grpName,Integer categoryId){
		return skuGroupDao.getSKUgroup(sellerId, grpName,categoryId);	
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	

	@Override
	public void deleteOrdersWS(List<Integer> skuToDelete, String deliveryDate,
			List<Integer> buyerIds, List<Integer> sellerIds,
			List<Order> allOrders, Map<Integer, SKU> skusToDelete)
			throws Exception {
		Map<String, Order> allOrdersMap = OrderSheetUtil
		.convertToOrderMap(allOrders);
		List<Integer> skuIds = new ArrayList<Integer>(skusToDelete.keySet());
		
		if (skuIds !=null && skuIds.size() != 0) {
			for (Integer buyerId : buyerIds) {
				String sBuyerId = buyerId.toString();
				for (Integer sellerId : sellerIds) {
					String sSellerId = sellerId.toString();
					Order order = allOrdersMap.get(OrderSheetUtil.formatOrderMapKey(deliveryDate, sBuyerId, sSellerId));
					if (order == null) {
						continue;
					}
					this.deleteOrderItems(order.getOrderId(), skuIds);
				}
			}
		}		
	}

	@Override
	public void insertSKUWS(String deliveryDate, List<Integer> buyerIds,
			List<Integer> sellerIds, WSInputDetails[] details,
			List<Order> orderList) throws Exception {
		// INSERT SKU
		Map<String, Order> allOrdersMap = OrderSheetUtil
				.convertToOrderMap(orderList);
		for (WSInputDetails wsInput : details) {
			User user = usersInfoDaos.getUserById(sellerIds.get(0));
			SKU sku = WebServiceUtil.wsToSKU(wsInput, user,true);
			
			//Category cat = getCategory(user.getUserId(),wsInput.getSkuCategoryName());remove
			Category cat = categoryService.getCategory(user.getUserId(),wsInput.getSkuCategoryName());
			//OrderUnit uom = getOrderUnit(cat.getCategoryId(),wsInput.getUnitOfOrder());//remove
			OrderUnit uom = orderUnitService.getOrderUnit(cat.getCategoryId(),wsInput.getUnitOfOrder());
			
			if(uom ==null){
				uom = new OrderUnit();
				//this.insertUOM(wsInput.getUnitOfOrder(), cat.getCategoryId(), uom);remove
				orderUnitService.insertUOM(wsInput.getUnitOfOrder(), cat.getCategoryId(), uom);
			}
			
			SKUGroup skuGrp = this.getSkuGroup(user.getUserId(), wsInput.getSkuGroupName(),cat.getCategoryId());
			
			if(skuGrp ==null || skuGrp.getCategoryId() ==null){
				skuGrp = new SKUGroup();
				skuGrp.setCategoryId(cat.getCategoryId());
				skuGrp.setDescription(wsInput.getSkuGroupName());
				skuGrp.setSellerId(user.getUserId());
				skuGroupDao.insertSkuGroupToUpdateName(skuGrp);
			}
			sku.setOrderUnit(uom);
			sku.setSkuCategoryId(cat.getCategoryId());
			sku.setSkuGroup(skuGrp);
			skuService.insertSKU(sku);
			Map<Integer, Object> buyerQuantityMap = WebServiceUtil
					.createBuyerMap(wsInput.getWsBuyerInformation());
			for (Integer buyerId : buyerIds) {

				WSBuyerInformation wsBuyerInformation = new WSBuyerInformation();

				if (buyerQuantityMap.containsKey(buyerId)) {
					wsBuyerInformation = (WSBuyerInformation) buyerQuantityMap
							.get(buyerId);
				} else {
					wsBuyerInformation.setBuyerId(buyerId);
					wsBuyerInformation.setVisible(false);
				}
				for (Integer sellerId : sellerIds) {
					String sSellerId = sellerId.toString();
					Order order = allOrdersMap.get(OrderSheetUtil.formatOrderMapKey(deliveryDate, buyerId.toString(), sSellerId));

					if (order == null) {
						continue;
					}
					if (!sSellerId.equals(sku.getUser().getUserId().toString())) {
						continue;
					}
					this.insertOrderItem(order.getOrderId(), sku.getSkuId(),
							null, wsBuyerInformation.getQuantity(), wsBuyerInformation.isVisible() ? "1" : "0");
				}
			}
		}
		
	}

	@Override
	public void updateOrdersWS(String deliveryDate, List<Integer> buyerIds,
			List<Integer> sellerIds, List<Order> allOrders) throws Exception {
		Map<String, Order> allOrdersMap = OrderSheetUtil
		.convertToOrderMap(allOrders);
		// UPDATE ORDERS
		for (Integer buyerId : buyerIds) {
			String sBuyerId = buyerId.toString();
			for (Integer sellerId : sellerIds) {
				String sSellerId = sellerId.toString();
				Order order = allOrdersMap.get(OrderSheetUtil.formatOrderMapKey(deliveryDate, sBuyerId, sSellerId));
		
				if (order == null) {
					continue;
				}
				orderDao
						.updateSaveOrder(sellerId, sellerId, order.getOrderId());
			}
		}
		
	}

	@Override
	public void updateSKUWS(String deliveryDate, List<Integer> buyerIds,
			List<Integer> sellerIds, WSInputDetails[] details,
			Map<Integer, SKU> updateSKUMap, List<Order> orderList)
			throws Exception {
		Map<String, Order> allOrdersMap = OrderSheetUtil
		.convertToOrderMap(orderList);
		// UPDATED SKU
		for (WSInputDetails obj : details) {
			User user = usersInfoDaos.getUserById(sellerIds.get(0));
			SKU sku =WebServiceUtil.wsToSKU(obj, user,true);
			SKU origSku = updateSKUMap.get(sku.getSkuId());
			WSBuyerInformation[] buyerList = obj.getWsBuyerInformation();
			// update EON_ORDER_ITEM
			for (Integer sellerId : sellerIds) {
				String sSellerId = sellerId.toString();
								
				if (!sSellerId.equals(origSku.getUser().getUserId().toString())) {
					continue;
				}
		
				for (WSBuyerInformation wsBuyerInformation : buyerList) {
		
					String sBuyerId = wsBuyerInformation.getBuyerId()
							.toString();
					Order order = allOrdersMap.get(OrderSheetUtil.formatOrderMapKey(deliveryDate, sBuyerId, sSellerId));
		
					if (order == null) {
						continue;
					}
					if (!sSellerId.equals(origSku.getUser().getUserId().toString())) {
						continue;
					}

					this.updateOrderItem(order.getOrderId(), origSku.getSkuId(), origSku.getSkuId(), 
							wsBuyerInformation.getQuantity(), wsBuyerInformation.isVisible() ? "1" : "0", false, false);
				}
			}
		}
		
	}

	
	/**
	 * Updates list of SKU and List of order items
	 * used in updateOrdersheet webservice function
	 */
	@Override
	public void updateOrdersheetWS(List<WSSellerSKU> skuList, String deliveryDate) throws Exception{

		for(WSSellerSKU wsSKU : skuList){
			SKU sku = wsSKU.getSku();
			boolean isMetaInfoChanged = wsSKU.getHasMetaInfoChanged();
			
			boolean updateNewSKU = false;
			List<WSSellerBuyerDetails> details = wsSKU.getDetails();
			List<Integer> buyerIds = WebServiceUtil.extractBuyerIDFromDetails(details);
					
			
			boolean skuCountWithSavedOrder = getSKUCountWithSavedOrder(sku.getSkuId(),
					deliveryDate, buyerIds);
			if(NumberUtil.isNullOrZero(sku.getSkuGroup().getSkuGroupId())){
				SKUGroup skugroup = sku.getSkuGroup();
				skuGroupDao.saveSKUGroup(skugroup);
				sku.setSkuGroup(skugroup);
			}
			
			if (isMetaInfoChanged){
				if (skuCountWithSavedOrder) {
					skuService.updateNewSKU(sku);
					updateNewSKU = true;
				} else {

					skuService.updateSKU(sku);
				}
			}
			
			for(WSSellerBuyerDetails detail : details){
				OrderItem oi = detail.getoItem();
				this.updateOrderItem(oi.getOrderId(),
						sku.getSkuId(), sku.getOrigSkuId(),
						oi.getQuantity(),
						oi.getSosVisFlag(), updateNewSKU,
						isMetaInfoChanged);
			}
		}
	}
	public void setReceivedSheetDao(ReceivedSheetDao receivedSheetDao) {
		this.receivedSheetDao = receivedSheetDao;
	}

	/**
	 * Get the sum of Order item quantities
	 */
	@Override
	public BigDecimal getSumOrderQuantities(List<Integer> orderIds) {		
		return this.orderSheetDao.getSumOrderQuantities(orderIds);
	}

	@Override
	public BigDecimal getAvailableQuantities(Integer sellerId, Integer buyerId,
			String deliveryDate, Integer skuId) {
		
		return this.orderSheetDao.getAvailableQuantities(sellerId, buyerId, deliveryDate, skuId);
	}

	@Override
	public Boolean isQuantityValid(Integer sellerId, Integer buyerId,
			String deliveryDate, SKU sku, BigDecimal quantity, BigDecimal remCount) {
		
		
		remCount = remCount != null ? remCount.add(quantity) : quantity;

		if(sku.getSkuMaxLimit() == null) {
			return true;
		}
		
		return !(remCount.compareTo(sku.getSkuMaxLimit()) == 1);
	}

	/*
	 * This method is used to get either;
	 * 1. TOTALS of ProfitInfo if given parameter is only one category 
	 *    and one day for all buyers view or multiple days for all dates view
	 * 2. GRAND TOTALS of ProfitInfo if given parameter is more than one categories
	 *    and one day for all buyers view or multiple days for all dates view
	 *    
	 * Redmine 1761 additional fix.  Compute only for those valid members of admin   
	 */
	@Override
	public ProfitInfo getProfitInfo(User user, List<String> deliveryDate,
			List<Integer> sellerIdList,
			Map<String, List<Integer>> mapOfMembersByDateList,
			List<Integer> buyerIdList, List<Integer> categoryIdList, Double tax) {

		Set<Integer> categorySet = new TreeSet<Integer>(categoryIdList);

		ProfitInfo pi = new ProfitInfo();
		for (String _deliveryDate : deliveryDate) {
			
			List<Integer> validSellersForDate = new ArrayList<Integer>();
			
			if (RolesUtil.isUserRoleSellerAdmin(user)){
				List<Integer>  tmpvalidSellerMemberList = mapOfMembersByDateList.get(_deliveryDate);
				for (Integer integer : sellerIdList) {
					if(tmpvalidSellerMemberList.contains(integer)){
						validSellersForDate.add(integer);
					}
				}
				
			} else {
				validSellersForDate = sellerIdList;
			}
			if (CollectionUtils.isEmpty(validSellersForDate)) {
				continue;
			}
			Iterator<Integer> categoryI = categorySet.iterator();
			while (categoryI.hasNext()) {
				ProfitInfo _pi = orderSheetDao.getProfitInfo(_deliveryDate,
						buyerIdList, validSellersForDate, categoryI.next(), tax);
				if (_pi != null) {
					pi.add(_pi);
				}
			}
		}

		return pi;
	}
	
	private User getUserById(Map<Integer, User> sellerUserObjMap, Integer sellerId) {
		User user = sellerUserObjMap.get(sellerId);
		if (user == null) {
			user = usersInfoDaos.getUserById(sellerId);
			sellerUserObjMap.put(sellerId, user);
		}
		return user;
	}
	
	@Override
	public Map<Integer, BigDecimal> getTotalQuantityBySKUIdListAndDate(
			List<OrderItemUI> updatedOrders, List<String> deliveryDates) {
		//Get the total quantity of each SKU ids
		List<Integer> skuIdList = OrderSheetUtil.getSKUId(updatedOrders);
		Map<Integer, BigDecimal> skuIdListWithQty = orderItemDao.getTotalQuantityBySkuIdListAndDate(skuIdList,
				deliveryDates);
		return skuIdListWithQty;
	}

	
//	public void getWSOrderSheet(){
//		insertDefaultOrders(orderSheetParam, user, dp);
//	}
//	List<Integer> selectedOrders,  List<Integer>> mapOfMembersByDate
	@Override
	public Map<String, Object> loadOrderItems(List<Integer> selectedOrders, User user,
			OrderSheetParam osParam, PageInfo pageInfo, Map<String, List<Integer>> mapOfMembersByDate) throws Exception {
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		
		String deliveryDate = osParam.getSelectedDate();
		List<Integer> companyBuyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		//Integer categoryId = osParam.getCategoryId();
		boolean isAllDatesView = osParam.isAllDatesView();
		String startDate = osParam.getStartDate();
		String endDate = osParam.getEndDate();
		Integer datesViewBuyerId = Integer.valueOf((String) osParam.getDatesViewBuyerID());
				
		//List<SKU> skuObjs = orderSheetService.getDistinctSKUs(deliveryDates, categoryId, SheetTypeConstants.SELLER_ORDER_SHEET, sellerId, pageInfo.getStartRowNum(), pageInfo.getPageSize());
		//add dummy records for orders
		if (selectedOrders.size() == 0) selectedOrders.add(999999999);
		//List<SKU> allSkuObjs = orderSheetService.getDistinctSKUs(selectedOrders, categoryId, SheetTypeConstants.SELLER_ORDER_SHEET, pageInfo.getStartRowNum(), pageInfo.getPageSize());
		List<Integer> categoryList = new ArrayList<Integer>();
		categoryList.add(osParam.getCategoryId());
		
		SheetData data = new SheetData();
		List<String> deliveryDates = new ArrayList<String>();
		List<Integer> selectedSellerList = OrderSheetUtil.toList(osParam.getSelectedSellerID());

		
		
		if (isAllDatesView){
			deliveryDates = DateFormatter.getDateList(startDate, endDate);		
			data = sheetDataService.loadSheetData(
					user, startDate, endDate, 
					selectedSellerList, 
					OrderSheetUtil.toList(osParam.getDatesViewBuyerID()), 
					categoryList, 
					osParam.getSheetTypeId(), false, false, selectedOrders);

		}else{
			deliveryDates.add(deliveryDate);
			List<Integer> memberList = null;
			
			if (RolesUtil.isUserRoleSellerAdmin(user)) {
				memberList = mapOfMembersByDate.get(deliveryDate);
			} else {
				memberList = selectedSellerList;
			}
			
			data = sheetDataService.loadSheetData(
					user, deliveryDate, deliveryDate, 
					//OrderSheetUtil.toList(osParam.getSelectedSellerID()),
					memberList,
					OrderSheetUtil.toList(osParam.getSelectedBuyerID()), 
					categoryList, 
					osParam.getSheetTypeId(), false, false, selectedOrders);

		}
		
		List<?> allSkuObjs = data.getSkuList();
		List<SKU> skuObjs = new ArrayList<SKU>();
		int rowIdxStart = pageInfo.getStartRowNum() - 1;
		int rowIdxEnd = rowIdxStart + pageInfo.getPageSize() - 1;
		for (int i=rowIdxStart; i<=rowIdxEnd; i++) {
			if (i >= allSkuObjs.size()) break;
			//skuObjs.add(allSkuObjs.get(i));
			if (allSkuObjs.get(i) instanceof SKU)
				skuObjs.add((SKU)allSkuObjs.get(i));
		}
		pageInfo.setTotalRowNum(allSkuObjs.size());
		
		Map<Integer, SKU> skuObjMap = new HashMap<Integer, SKU>();
		Set<String> sellerNameSet = new TreeSet<String>();
		List<Integer> sellerUserIds = new ArrayList<Integer>();
		sellerUserIds.add(user.getUserId());
//		Map<Integer, Map<String, Map<Integer, OrderItem>>> sellerOrderItemMap = 
//			new HashMap<Integer,Map<String,Map<Integer,OrderItem>>>();
//		try {
//			if (skuObjs.size() > 0)
//				sellerOrderItemMap = orderSheetService.getSellerOrderItems(OrderSheetUtil.getSkuIds(skuObjs),
//						deliveryDates,companyBuyerIds, false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		Map<String, Map<String, Map<Integer, Item>>> sellerOrderItemMap = 
			data.getSkuDateBuyOrderItemMap();
			
		for (SKU skuObj : skuObjs) {
			JSONObject json = new JSONObject();
			skuObjMap.put(skuObj.getSkuId(), skuObj);
			Map<String, Object> skuOrderMap = new HashMap<String, Object>();
			
			skuOrderMap.put("skuId", 		skuObj.getSkuId());
			skuOrderMap.put("sellerId", 	skuObj.getUser().getUserId());
			skuOrderMap.put("groupname", 	skuObj.getSkuGroup().getSkuGroupId());
			skuOrderMap.put("group", 	skuObj.getSkuGroup().getDescription());
			if(skuObj.getProposedBy() != null) {
				json.put("proposed", "1");
				json.put("skumaxlimit", "0");
				skuOrderMap.put("companyname", skuObj.getProposedBy().getCompany().getShortName());
				skuOrderMap.put("sellername", skuObj.getProposedBy().getShortName());
			}else{
				skuOrderMap.put("companyname", skuObj.getUser().getCompany().getShortName());
				skuOrderMap.put("sellername", skuObj.getUser().getShortName());
			}
			skuOrderMap.put("marketname", 	StringUtil.nullToBlank(skuObj.getMarket()));
			skuOrderMap.put("column01", 	StringUtil.nullToBlank(skuObj.getColumn01()));
			skuOrderMap.put("column02", 	StringUtil.nullToBlank(skuObj.getColumn02()));
			skuOrderMap.put("column03", 	StringUtil.nullToBlank(skuObj.getColumn03()));
			skuOrderMap.put("column04", 	StringUtil.nullToBlank(skuObj.getColumn04()));
			skuOrderMap.put("column05", 	StringUtil.nullToBlank(skuObj.getColumn05()));
			skuOrderMap.put("skuname", 		StringUtil.nullToBlank(skuObj.getSkuName()));
			skuOrderMap.put("home", 		StringUtil.nullToBlank(skuObj.getLocation()));
			skuOrderMap.put("grade", 		StringUtil.nullToBlank(skuObj.getGrade()));
			skuOrderMap.put("clazzname", 	StringUtil.nullToBlank(skuObj.getClazz()));
			skuOrderMap.put("price1", 		NumberUtil.nullToZero((BigDecimal)skuObj.getPrice1()));
			skuOrderMap.put("price2", 		NumberUtil.nullToZero((BigDecimal)skuObj.getPrice2()));
			skuOrderMap.put("pricewotax", 	NumberUtil.nullToZero((BigDecimal)skuObj.getPriceWithoutTax()));
			skuOrderMap.put("pricewtax", 	skuObj.getPriceWithTax());
			skuOrderMap.put("packageqty", 	StringUtil.nullToBlank(skuObj.getPackageQuantity()));
			skuOrderMap.put("packagetype", 	StringUtil.nullToBlank(skuObj.getPackageType()));
			skuOrderMap.put("unitorder", 	skuObj.getOrderUnit().getOrderUnitId());
			skuOrderMap.put("unitorderName", 	skuObj.getOrderUnit().getOrderUnitName());
			skuOrderMap.put("column06", 	StringUtil.nullToBlank(skuObj.getColumn06()));
			skuOrderMap.put("column07", 	StringUtil.nullToBlank(skuObj.getColumn07()));
			skuOrderMap.put("column08", 	StringUtil.nullToBlank(skuObj.getColumn08()));
			skuOrderMap.put("column09", 	StringUtil.nullToBlank(skuObj.getColumn09()));
			skuOrderMap.put("column10", 	StringUtil.nullToBlank(skuObj.getColumn10()));
			skuOrderMap.put("column11", 	StringUtil.nullToBlank(skuObj.getColumn11()));
			skuOrderMap.put("column12", 	StringUtil.nullToBlank(skuObj.getColumn12()));
			skuOrderMap.put("column13", 	StringUtil.nullToBlank(skuObj.getColumn13()));
			skuOrderMap.put("column14", 	StringUtil.nullToBlank(skuObj.getColumn14()));
			skuOrderMap.put("column15", 	StringUtil.nullToBlank(skuObj.getColumn15()));
			skuOrderMap.put("column16", 	StringUtil.nullToBlank(skuObj.getColumn16()));
			skuOrderMap.put("column17", 	StringUtil.nullToBlank(skuObj.getColumn17()));
			skuOrderMap.put("column18", 	StringUtil.nullToBlank(skuObj.getColumn18()));
			skuOrderMap.put("column19", 	StringUtil.nullToBlank(skuObj.getColumn19()));
			skuOrderMap.put("column20", 	StringUtil.nullToBlank(skuObj.getColumn20()));
			skuOrderMap.put("skumaxlimit",  StringUtil.nullToBlank((BigDecimal)skuObj.getSkuMaxLimit()));
			skuOrderMap.put("externalSkuId",  StringUtil.nullToBlank(skuObj.getExternalSkuId()));
			
			if (isAllDatesView) {
				this.prepareOrderItemWeekly(skuObj, skuOrderMap, sellerOrderItemMap,
					deliveryDates, datesViewBuyerId,json, user, mapOfMembersByDate);
			}
			else {
				this.prepareOrderItemDaily(skuObj.getSkuId().toString(), skuOrderMap, sellerOrderItemMap,
						deliveryDate, companyBuyerIds,json);
			}
			
			lockSellerName(user, json);
			skuOrderMap.put("lockflag", json.toString());
			
			this.prepareProfitInfoColumn(skuOrderMap, skuObj);
			
			skuOrderMaps.add(skuOrderMap);
			sellerNameSet.add(skuObj.getUser().getShortName());
		}
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		returnMap.put("skuOrderMaps", skuOrderMaps);
		returnMap.put("skuObjMap", skuObjMap);
		returnMap.put("sellerNameSet", sellerNameSet);
		
		return returnMap;
		
	}
	
	private void lockSellerName(User user, JSONObject json) {
		try {
			json.put("sellername", "1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void prepareOrderItemDaily(String currentSKU, Map<String, Object> skuOrderMap, 
			Map<String, Map<String, Map<Integer, Item>>> sellerOrderItemMap, 
			String deliveryDate, List<Integer> companyBuyerIds, 
			JSONObject json) throws Exception {
		
		BigDecimal rowqty = new BigDecimal(0);
		//unlocked: 0, locked: 1
		String allChecked = "1";
		for (Integer buyerId : companyBuyerIds) {
			Item orderItem = null;
			try {
				orderItem = sellerOrderItemMap.get(currentSKU).get(deliveryDate).get(buyerId);
			} catch (NullPointerException npe) {
				orderItem = null;
			}
			BigDecimal quantity = null;
			String strQuantity = "";
			String sosVisFlag = "0";
			String strLockFlag = "0";
			String skuFlag = "0";
			
			if (orderItem != null) {
				quantity = orderItem.getQuantity();
				
				if (orderItem.getSosVisFlag().equals("0") /*|| orderItem.getBaosVisFlag().equals("0")*/ )
					sosVisFlag = "0";
				
				else
					sosVisFlag = "1";
				
				if (quantity != null) {
					rowqty = rowqty.add(quantity);
					strQuantity = quantity.toPlainString();
					//Redmine 1063- if has quantity, lock the sku meta info
					if (NumberUtil.isGreaterThanZero(quantity)) {
						skuFlag = "1";
					}
				}
				// SKU finalized checking
				if (!NumberUtil.isNullOrZero(orderItem.getOrderFinalizedBy())) {
					skuFlag = "1";
					strLockFlag = "1";
				}
				
				// check for locked quantities
				if (!NumberUtil.isNullOrZero(orderItem.getOrderLockedBy())) {
					//Redmine 1063 - if locked by buyer then lock sku meta info
					skuFlag = "1";
					strLockFlag = "1";
				}
				
				if (sosVisFlag.equals("0") && strLockFlag.equals("0"))
					allChecked = "0";
				
				String finalized = "0";
				if (!NumberUtil.isNullOrZero(orderItem.getOrderFinalizedBy())){
					finalized = "1";
				}
				
				skuOrderMap.put("OF_" + buyerId.toString(), finalized);
				/* OP=Order Published */
				
				String orderPublished = "0";
				if (!NumberUtil.isNullOrZero(orderItem.getOrderPublishedBy())){
					orderPublished = "1";
				}
				skuOrderMap.put("OP_" + buyerId.toString(), orderPublished);
				
				/* OL=OrderLocked */
				
				String locked = "0";
				if (!NumberUtil.isNullOrZero(orderItem.getOrderLockedBy())){
					locked = "1";
				}
				
				skuOrderMap.put("OL_" + buyerId.toString(), locked);
				
				/* AP=Allocation Published */
				
				String allocationPublished = "0";
				if (!NumberUtil.isNullOrZero(orderItem.getAllocationFinalizedBy())){
					allocationPublished = "1";
				}
				
				skuOrderMap.put("AP_" + buyerId.toString(), allocationPublished);
				
				/* AF=Allocation Finalized */
				
				String allocationFinalized = "0";
				if (!NumberUtil.isNullOrZero(orderItem.getAllocationFinalizedBy())){
					allocationFinalized = "1";
				}
				
				skuOrderMap.put("AF_" + buyerId.toString(), allocationFinalized);
				
				/* RA=Received Approved */
				
				String receivedApproved = "0";
				if (!NumberUtil.isNullOrZero(orderItem.getReceivedApprovedBy())){
					receivedApproved = "1";
				}
				
				skuOrderMap.put("RA_" + buyerId.toString(), receivedApproved);
				
			}
			else {
				// not visible
				//strQuantity = "-999";
				strLockFlag = "1";
			}
			
			json.put("Q_" + buyerId.toString(), strLockFlag);
			json.put("V_" + buyerId.toString(), strLockFlag);
			json.put("CQ_" + buyerId.toString(), sosVisFlag);
			if (!json.has("sku") && skuFlag.equals("1"))
				json.put("sku", skuFlag);
			skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
			skuOrderMap.put("V_" + buyerId.toString(), sosVisFlag);
		}
		skuOrderMap.put("visall", allChecked);
		
		setSkuLock(sellerOrderItemMap, json);
		skuOrderMap.put("rowqty", rowqty);
	}

	private void setSkuLock(Map<String, Map<String, Map<Integer, Item>>> sellerOrderItemMap,
			JSONObject json) throws Exception {
		Set<String> keys = sellerOrderItemMap.keySet();
		Iterator<String> it = keys.iterator();
		
		while(it.hasNext()) {
			String key = it.next();
			Map<String, Map<Integer, Item>> deliveryDateMap = sellerOrderItemMap.get(key);;
			
			Set<String> keySet = deliveryDateMap.keySet();
			Iterator<String> keyIte = keySet.iterator();	
			while(keyIte.hasNext()){
				String deliveryDate = keyIte.next();
				Map<Integer, Item> orderItemMap = deliveryDateMap.get(deliveryDate);
				
				Set<Integer> orderItemkeys = orderItemMap.keySet();
				Iterator<Integer> orderKeys = orderItemkeys.iterator();
				
				while(orderKeys.hasNext()){
					Integer orderId = orderKeys.next();
					Item oi = orderItemMap.get(orderId);
					if (!NumberUtil.isNullOrZero(oi.getOrderFinalizedBy())) {
						json.put("sku", "1");
					}
				}
			}
		}
	}
	
	private void prepareOrderItemWeekly(SKU skuObj,
			Map<String, Object> skuOrderMap,
			Map<String, Map<String, Map<Integer, Item>>> sellerOrderItemMap,
			List<String> deliveryDates, Integer buyerId, JSONObject json,
			User user, Map<String, List<Integer>> mapOfMembersByDate)
			throws JSONException {

		//unlocked: 0, locked: 1
		Integer sellerId = skuObj.getUser().getUserId();
		String currentSKU = skuObj.getSkuId().toString();
		
		BigDecimal rowqty = new BigDecimal(0);
		for (String deliveryDate : deliveryDates) {
			Item orderItem = null;
			try {
				orderItem = sellerOrderItemMap.get(currentSKU).get(deliveryDate).get(buyerId);
			} catch (NullPointerException npe) {
				orderItem = null;
			}
			
			if (RolesUtil.isUserRoleSellerAdmin(user)) {
				// seller id of sku is not a valid member of admin for the given
				// date: remove set Orderitem = null so that quantity field in
				// all dates view will be locked and quantity is not displayed.
				List<Integer> mapOfMembers = mapOfMembersByDate.get(deliveryDate);
				if (CollectionUtils.isEmpty(mapOfMembers) || !mapOfMembers.contains(sellerId)) {
					orderItem = null;
				}
			}
			
			BigDecimal quantity = null;
			String strQuantity = "";
			String strLockFlag = "0";
			
			if (orderItem != null) {
				quantity = orderItem.getQuantity();
			
				if (quantity != null) {
					rowqty = rowqty.add(quantity);
					strQuantity = quantity.toPlainString();
					//Redmine 1063- if has quantity, lock the sku meta info
					if (NumberUtil.isGreaterThanZero(quantity)) {
						json.put("sku", 1);
					}
					
				}
				
				// SKU finalized checking
				if (!NumberUtil.isNullOrZero(orderItem.getOrderFinalizedBy())) {
					json.put("sku", 1);
					strLockFlag = "1";
				}
				
				// check visibility
				if (orderItem.getSosVisFlag().equals("0") ) {
					strLockFlag = "1";
				}
				
				// check for locked quantities
				if (!NumberUtil.isNullOrZero(orderItem.getOrderLockedBy())) {
					//Redmine 1063 - if locked by buyer then lock sku meta info
					json.put("sku", 1);
					strLockFlag = "1";
				}
			}
			else {
				//strQuantity = "-999";
				strLockFlag = "1";
			}

			json.put("Q_" + deliveryDate, strLockFlag);
			skuOrderMap.put("Q_" + deliveryDate, strQuantity);
		}
		skuOrderMap.put("rowqty", rowqty);
		skuOrderMap.put("lockflag", json.toString());
	}
	
	private void prepareProfitInfoColumn(Map<String, Object> skuOrderMap, SKU skuObj) 
			throws JSONException {

			BigDecimal totalQty = new BigDecimal(skuOrderMap.get("rowqty").toString());
			JSONObject json = new JSONObject();
			BigDecimal pWithouTax = NumberUtil.nullToZero((BigDecimal)skuObj.getPriceWithoutTax());
			BigDecimal pWithTax = NumberUtil.nullToZero((BigDecimal)skuObj.getPriceWithTax());
			json.put("priceWithoutTax", pWithouTax.multiply(totalQty));
			json.put("priceWithTax", pWithTax.multiply(totalQty));
			skuOrderMap.put("profitInfo", json.toString());

		}
	@Override
	public void deleteAllOrderItems(List<Integer> orderItemIds) throws ServiceException {
		orderItemDao.deleteOrderItemByBatch(orderItemIds);
	}
	
	@Override
	public List<OrderItem> getOrderItemsListOfSkuId(List<Integer> skuIds, List<String> deliveryDates){
		return orderSheetDao.getOrderItemsListOfSkuId(skuIds, deliveryDates);
	}
	
}