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
 * 20120605	Rhoda			Redmine #236 – Allocation Published Rule to restrict only with Quantity
 * 20120627	Rhoda			PROD ISSUE – SKU doesn't appear on Received after SKU Qty input on a published Alloc.
 * 20120704 Rhoda			Redmine #906 – SKU doesn't appear on Received after SKU Qty input and metainfo edit on a published Alloc.
 * 20120724	Lele	v11		Redmine 797 - SellerAdmin can select seller who don't have category
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
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.dao.AllocationDao;
import com.freshremix.dao.OrderBillingDao;
import com.freshremix.dao.OrderDao;
import com.freshremix.dao.ReceivedSheetDao;
import com.freshremix.dao.SKUGroupDao;
import com.freshremix.dao.UserDao;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.AllocationBuyerInformation;
import com.freshremix.model.AllocationInputDetails;
import com.freshremix.model.Category;
import com.freshremix.model.CompositeKey;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.MailSender;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.User;
import com.freshremix.service.AllocationSheetService;
import com.freshremix.service.CategoryService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.SKUService;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.ui.model.OrderItemUI;
import com.freshremix.ui.model.ProfitInfo;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.StringUtil;
import com.freshremix.util.WebServiceUtil;

/**
 * @author gilwen
 *
 */
public class AllocationSheetServiceImpl implements AllocationSheetService {

	private static final Logger LOGGER = Logger.getLogger(AllocationSheetServiceImpl.class);
	private OrderDao orderDao;
	private AllocationDao allocationDao;
	private OrderBillingDao orderBillingDao;
	private SKUService skuService;
	private ReceivedSheetDao receivedSheetDao;
	private UserDao usersInfoDaos;
	private SKUGroupDao skuGroupDao;
	private CategoryService categoryService;
	//private SKUMaxLimitDao skuMaxLimitDao;
	private OrderUnitService orderUnitService;
	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}

	public SKUGroupDao getSkuGroupDao() {
		return skuGroupDao;
	}

	public void setSkuGroupDao(SKUGroupDao skuGroupDao) {
		this.skuGroupDao = skuGroupDao;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	public SKUService getSkuService() {
		return skuService;
	}

	public UserDao getUsersInfoDaos() {
		return usersInfoDaos;
	}

	public void setAllocationDao(AllocationDao allocationDao) {
		this.allocationDao = allocationDao;
	}
	
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	public void setOrderBillingDao(OrderBillingDao orderBillingDao) {
		this.orderBillingDao = orderBillingDao;
	}
	
	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}
	
	private boolean skuNotSaved(Integer sellerId, Integer skuId, Integer sheetType) {
		return !skuService.isSKUExisting(sellerId, skuId, sheetType);
	}
	
	public void setSkuService(SKUService skuService) {
		this.skuService = skuService;
	}
	
//	public void setSkuMaxLimitDao(SKUMaxLimitDao skuMaxLimitDao) {
//		this.skuMaxLimitDao = skuMaxLimitDao;
//	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.AllocationSheetService#publishOrder(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updatePublishOrder(Integer orderId, Integer publishBy) {
		orderDao.publishAllocation(orderId, publishBy, publishBy);
		List<OrderItem> allocItem = allocationDao.getAllocItemsByOrderId(orderId);
		for (OrderItem _allocItem : allocItem) {
			// DELETION 20120704: Rhoda Redmine 906
//			if (_allocItem.getQuantity() != null && !_allocItem.getQuantity().equals(new BigDecimal(0))) 
			receivedSheetDao.insertDefaultReceiveItems(orderId,_allocItem.getSku().getSkuId(),_allocItem.getSkuBaId(), publishBy,_allocItem.getQuantity());
		}
	}
	
	@Override
	public void updatePublishOrder(User user, List<Order> savedOrders) throws Exception  {
		
		
		LOGGER.info("Update Publish Allocation...start");

		//save Orders as unfinalized
		List<Integer> allcnForPublishIdList = new ArrayList<Integer>();
		List<Order> allcnForPublishList = new ArrayList<Order>();
		Map<Integer, User> buyerMap = new HashMap<Integer, User>();

		if (CollectionUtils.isNotEmpty(savedOrders)) {
			
			for (Order order : savedOrders) {
				if (order.getAllocationPublishedBy() == null){
					allcnForPublishList.add(order)	;
					allcnForPublishIdList.add(order.getOrderId());
					
					if(buyerMap.containsKey(order.getBuyerId())){
						continue;
					}
					User userOrder = usersInfoDaos.getUserById(order.getBuyerId());
	
					buyerMap.put(userOrder.getUserId(), userOrder);
					}
			}
			LOGGER.info("Allocations to publish: count:"+allcnForPublishIdList.size());

			orderDao.publishAllocationByBatch(allcnForPublishIdList, user.getUserId());
			receivedSheetDao.bulkInsertReceivedItemFromAllocation(allcnForPublishIdList, user.getUserId());
			
			
			
			
			/*
			 * Notifications done in the end of the process to ensure that email is
			 * sent only when all DB operations are successful
			 */
			
			
			
			
			
			String fromAddress = StringUtil.isNullOrEmpty(user.getPcEmail()) == false ? user.getPcEmail() : user.getMobileEmail();
			//send notifications for unfinalized Orders
			if (CollectionUtils.isNotEmpty(allcnForPublishIdList)) {
				LOGGER.info("Sending consolidated email for Publish Allocation Sheet");
				Map<String, Set<String>> mapOfEmailToOrderDates = OrderSheetUtil.consolidateOrdersForEmail(
						buyerMap, allcnForPublishList,"buyer");
				for (Map.Entry<String, Set<String>> emailToOrderDates : mapOfEmailToOrderDates.entrySet()) {
					sendConsolidatedMailNotification(new ArrayList<String>(
							emailToOrderDates.getValue()), "Publish", user
							.getUserName(), fromAddress,
							new String[] { emailToOrderDates.getKey() });
				}
			}
		}
		
		
		
		

		LOGGER.info("Update Publish Allocation...end");
	}
	

	@Override
	public void saveAndPublishAllocation(List<Integer> orderIdList, Integer savedAndPublishedBy) throws Exception {
		orderDao.saveAndPublishAllocation(orderIdList, savedAndPublishedBy);
		receivedSheetDao.bulkInsertReceivedItemFromAllocation(orderIdList, savedAndPublishedBy);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.AllocationSheetService#updateFinalizedOrder(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
///	public void updateFinalizedOrder(Integer orderId, Integer finalizedBy,
//			Integer unfinalizedBy) {
//		orderDao.finalizeAllocation(orderId, finalizedBy, unfinalizedBy, finalizedBy);
//		List<OrderItem> allocItem = allocationDao.getAllocItemsByOrderId(orderId);
//		for (OrderItem _allocItem : allocItem) {
//			orderBillingDao.insertDefaultBillingItems(orderId,_allocItem.getSku().getSkuId(), _allocItem.getSkuBaId(), finalizedBy,_allocItem.getQuantity());
//		}
//		orderBillingDao.insertDefaultBillingItemsBatch(Arrays.asList(orderId), finalizedBy);
//
//	}
	
	
	@Override
	public void updateFinalizeOrder(User user,
			List<Order> ordersForUpdate) throws ServiceException {
		
		if (CollectionUtils.isNotEmpty(ordersForUpdate)) {
			List<Integer> orderIds = OrderSheetUtil.getOrderIdList(ordersForUpdate);

			orderDao.finalizeAllocationBatch(orderIds, user.getUserId());
			orderBillingDao.insertDefaultBillingItemsBatch(orderIds,user.getUserId() );
			
			
			Map<Integer, User> buyerMap = new HashMap<Integer, User>();
			for (Order order : ordersForUpdate) {

				if (buyerMap.containsKey(order.getBuyerId())) {
					continue;
				}
				User userOrder = usersInfoDaos.getUserById(order.getBuyerId());

				buyerMap.put(userOrder.getUserId(), userOrder);

			}
			String fromAddress = StringUtil.isNullOrEmpty(user.getPcEmail()) == false ? user
					.getPcEmail() : user.getMobileEmail();
			//send notifications for finalize Orders
			if (CollectionUtils.isNotEmpty(orderIds)) {
				LOGGER.info("Sending consolidated email for Finalize Allocation Sheet");
				Map<String, Set<String>> mapOfEmailToOrderDates = OrderSheetUtil
						.consolidateOrdersForEmail(buyerMap, ordersForUpdate,
								"buyer");
				for (Map.Entry<String, Set<String>> emailToOrderDates : mapOfEmailToOrderDates
						.entrySet()) {
					sendConsolidatedMailNotification(new ArrayList<String>(
							emailToOrderDates.getValue()), "Finalize",
							user.getUserName(), fromAddress,
							new String[] { emailToOrderDates.getKey() });
				}
			}
		}
		
		
	}


	/* (non-Javadoc)
	 * @see com.freshremix.service.AllocationSheetService#updateUnfinalizedOrder(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateUnfinalizedOrder(Integer orderId, Integer finalizedBy,
			Integer unfinalizedBy) {
		orderDao.unfinalizeAllocation(orderId, finalizedBy, unfinalizedBy, unfinalizedBy);
		orderBillingDao.deleteBillingItems(orderId);
	}
	
	@Override
	public void updateUnfinalizeOrder(List<Order> finOrder, User user) throws ServiceException {
		
		
		LOGGER.info("Update Unfinalize Allocation...start");
		
		//save Orders as unfinalized
		List<Integer> allcnForUnUnfinalizeIdList = OrderSheetUtil.getOrderIdList(finOrder);
		LOGGER.info("Allocations to Unfinalize: count:"+allcnForUnUnfinalizeIdList.size());
		if (CollectionUtils.isNotEmpty(allcnForUnUnfinalizeIdList)) {
			
				
				orderDao.unfinalizeAllocationByBatch(allcnForUnUnfinalizeIdList, user.getUserId());
				orderBillingDao.deleteBillingItemsByBatch(allcnForUnUnfinalizeIdList);
				
				/*
				 * Notifications done in the end of the process to ensure that email is
				 * sent only when all DB operations are successful
				 */
				Map<Integer, User> buyerMap = new HashMap<Integer, User>();

				for (Order order : finOrder) {
					
						if(buyerMap.containsKey(order.getBuyerId())){
							continue;
						}
						User userOrder = usersInfoDaos.getUserById(order.getBuyerId());
		
						buyerMap.put(userOrder.getUserId(), userOrder);
					
				}	
				String fromAddress = StringUtil.isNullOrEmpty(user.getPcEmail()) == false ? user.getPcEmail() : user.getMobileEmail();
				//send notifications for unfinalized Orders
				if (CollectionUtils.isNotEmpty(allcnForUnUnfinalizeIdList)) {
					LOGGER.info("Sending consolidated email for Unfinalize Allocation Sheet");
					Map<String, Set<String>> mapOfEmailToOrderDates = OrderSheetUtil.consolidateOrdersForEmail(
							buyerMap, finOrder,"buyer");
					for (Map.Entry<String, Set<String>> emailToOrderDates : mapOfEmailToOrderDates.entrySet()) {
						sendConsolidatedMailNotification(new ArrayList<String>(
								emailToOrderDates.getValue()), "Unfinalize", user
								.getUserName(), fromAddress,
								new String[] { emailToOrderDates.getKey() });
					}
				}
			}
		
		
		
		
		

		LOGGER.info("Update Unfinalize Allocation...end");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.AllocationSheetService#updateUnpublishOrder(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateUnpublishOrder(Integer orderId, Integer userId) {
		orderDao.unpublishAllocation(orderId, userId, null);
		receivedSheetDao.deleteReceiveItems(orderId);
	}

	@Override
	public void updateUnpublishOrder(List<Order> pubOrders, User user) throws Exception{
		
		LOGGER.info("Update Unpublish Allocation...start");

		//save Orders as unfinalized
		List<Integer> allcnForUnPublishIdList = new ArrayList<Integer>();
		List<Order> allcnForUnPublishList = new ArrayList<Order>();
		Map<Integer, User> buyerMap = new HashMap<Integer, User>();
		
		if (CollectionUtils.isNotEmpty(pubOrders)) {
			
				for (Order order : pubOrders) {
					if (NumberUtil.isNullOrZero(order.getReceivedApprovedBy())) {
					
						allcnForUnPublishIdList.add(order.getOrderId());
						allcnForUnPublishList.add(order);
						if(buyerMap.containsKey(order.getBuyerId())){
							continue;
						}
						User userOrder = usersInfoDaos.getUserById(order.getBuyerId());
		
						buyerMap.put(userOrder.getUserId(), userOrder);
					}
				}
				LOGGER.info("Allocations to Unpublish: count:"+allcnForUnPublishIdList.size());
				orderDao.unpublishAllocationByBatch(allcnForUnPublishIdList, user.getUserId());
				receivedSheetDao.deleteReceivedItemByBatch(allcnForUnPublishIdList);
				
				/*
				 * Notifications done in the end of the process to ensure that email is
				 * sent only when all DB operations are successful
				 */
					
				String fromAddress = StringUtil.isNullOrEmpty(user.getPcEmail()) == false ? user.getPcEmail() : user.getMobileEmail();
				//send notifications for unfinalized Orders
				if (CollectionUtils.isNotEmpty(allcnForUnPublishIdList)) {
					LOGGER.info("Sending consolidated email for Unpublish Allocation Sheet");
					Map<String, Set<String>> mapOfEmailToOrderDates = OrderSheetUtil.consolidateOrdersForEmail(
							buyerMap, allcnForUnPublishList,"buyer");
					for (Map.Entry<String, Set<String>> emailToOrderDates : mapOfEmailToOrderDates.entrySet()) {
						sendConsolidatedMailNotification(new ArrayList<String>(
								emailToOrderDates.getValue()), "Unpublish", user
								.getUserName(), fromAddress,
								new String[] { emailToOrderDates.getKey() });
					}
				}
			}
		
		
		
		LOGGER.info("Update Publish Allocation...end");
	}
	
	
	@Override
	public List<Order> getFinalizedOrdersForAlloc(List<Integer> buyerIds, List<String> deliveryDates, List<Integer> sellerIds) {
		List<Order> publishedOrders = allocationDao.getFinalizedOrdersForAlloc(buyerIds, deliveryDates, sellerIds);
		return publishedOrders;
	}
	
	public List<SKU> getDistinctSKUs(
			List<Integer> selectedOrders, 
			List<Integer> sellerId,
			List<Integer> buyerId,
			List<String> deliveryDate,
			Integer categoryId, 
			String hasQty) {
		
		if (hasQty.equals("0")) hasQty = null;
		
		return allocationDao.selectDistinctSKUs(selectedOrders, sellerId, buyerId, deliveryDate, categoryId, hasQty);	
	}

	public void setReceivedSheetDao(ReceivedSheetDao receivedSheetDao) {
		this.receivedSheetDao = receivedSheetDao;
	}
	
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
					String key = thisDate + "_" + buyerId.toString() + "_" + sellerId.toString();
					Order thisOrder = allOrdersMap.get(key);
					//if (thisOrder != null && !NumberUtil.isNullOrZero(thisOrder.getOrderSavedBy())) {
					if (thisOrder != null && !NumberUtil.isNullOrZero(thisOrder.getOrderFinalizedBy())) {
						selectedOrders.add(thisOrder);
					}	
				}
			}
		}
		
		return selectedOrders;
		
	}
	
	public Order combineOrders(List<Order> orders) {
		Order order = new Order();
		//order.setReceivedApprovedBy(99999);
		for (Order _order : orders) {
			if (!NumberUtil.isNullOrZero(_order.getAllocationSavedBy()) &&
				NumberUtil.isNullOrZero(_order.getAllocationPublishedBy()) &&
				NumberUtil.isNullOrZero(_order.getAllocationFinalizedBy()))
				order.setAllocationSavedBy(99999);
			if (!NumberUtil.isNullOrZero(_order.getAllocationPublishedBy()) &&
				NumberUtil.isNullOrZero(_order.getAllocationFinalizedBy()))
				order.setAllocationPublishedBy(99999);
			if (!NumberUtil.isNullOrZero(_order.getAllocationFinalizedBy()) &&
					NumberUtil.isNullOrZero(_order.getReceivedApprovedBy()))
				order.setAllocationFinalizedBy(99999);
			if (!NumberUtil.isNullOrZero(_order.getAllocationUnfinalizedBy()))
				order.setAllocationUnfinalizedBy(99999);
			if (!NumberUtil.isNullOrZero(_order.getOrderFinalizedBy()))
				order.setOrderFinalizedBy(99999);
			if (order.getReceivedApprovedBy()==null && NumberUtil.isNullOrZero(_order.getReceivedApprovedBy()))
				order.setReceivedApprovedBy(null);
			if (order.getReceivedApprovedBy()==null && !NumberUtil.isNullOrZero(_order.getReceivedApprovedBy()))
				order.setReceivedApprovedBy(99999);
		}
		return order;
	}
	

	@Override
	public List<Order> getAllOrders(List<Integer> buyerIds, List<String> dateList, List<Integer> sellerIds) {
		List<Order> allOrders = orderDao.getAllOrders(buyerIds, dateList, sellerIds);
		if(CollectionUtils.isEmpty(allOrders)){
			allOrders = new ArrayList<Order>();
		}
		return allOrders;
	}

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
					String key = thisDate + "_" + buyerId.toString() + "_" + sellerId.toString();
					Order thisOrder = allOrdersMap.get(key);
					if (thisOrder != null)
						selectedOrderIds.add(thisOrder.getOrderId());
				}
			}
		}
		
		return selectedOrderIds;
	}

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
	// ENHANCEMENT END 20120724: Lele
	
	@Override
	public void saveOrder2(OrderForm orderForm, OrderDetails orderDetails, Map<String, Order> allOrdersMap, Map<Integer, SKU> skuObjMap) throws ServiceException {
		
		List<String> deliveryDates = null;
		List<Integer> buyerIds = null;
		List<Integer> sellerIds = orderDetails.getSellerIds();
		List<OrderItemUI> orderItemList = null;
		List<Integer> orderIds = new ArrayList<Integer>();
		if (orderDetails.isAllDatesView()) {
			String startDate = orderDetails.getStartDate();
			String endDate = orderDetails.getEndDate();
			deliveryDates = DateFormatter.getDateList(startDate, endDate);
			buyerIds = new ArrayList<Integer>();
			buyerIds.add(orderDetails.getDatesViewBuyerID());
		} else {
			deliveryDates = new ArrayList<String>();
			deliveryDates.add(orderDetails.getDeliveryDate());
			buyerIds = orderDetails.getBuyerIds();
		}
		
		// UPDATE ORDERS
		for (String deliveryDate : deliveryDates) {
			for (Integer buyerId : buyerIds) {
				String sBuyerId = buyerId.toString();
				for (Integer sellerId : sellerIds) {
					String sSellerId = sellerId.toString();
					Order order = allOrdersMap.get(deliveryDate + "_" + sBuyerId + "_" + sSellerId);
					
					if (order == null) continue;
					orderIds.add(order.getOrderId());
					//orderDao.updateSaveAllocation(orderDetails.getSellerId(), orderDetails.getSellerId(), order.getOrderId());
				}
			}
		}
		if(CollectionUtils.isNotEmpty(orderIds)){
			orderDao.updateSaveAllocationBatch(orderDetails.getSellerId(), orderIds);

		}
		
		// INSERT SKU
		orderItemList = orderForm.getInsertedOrders();
		Map<Integer,User> sellerUserObjMap = new HashMap<Integer,User>();
		for (OrderItemUI oi : orderItemList) {
			User user = sellerUserObjMap.get(oi.getSellerId());
			if (user == null) {
				user = usersInfoDaos.getUserById(oi.getSellerId());
				sellerUserObjMap.put(oi.getSellerId(), user);
			}
			SKU sku = OrderSheetUtil.toSKU(oi, orderDetails, user, SheetTypeConstants.SELLER_ALLOCATION_SHEET);
			sku.setOrigSkuId(null);
			Map<String, BigDecimal> qtyMap = oi.getQtyMap();
			skuService.insertSKU(sku);
			// save to EON_ORDER_ALLOCATION
			for (String deliveryDate : deliveryDates) {
				//skuMaxLimitDao.insertSKUMaxLimit(sku.getSkuId(), deliveryDate, oi.getSkumaxlimit());
				for (Integer buyerId : buyerIds) {
					String sBuyerId = buyerId.toString();
					for (Integer sellerId : sellerIds) {
						String sSellerId = sellerId.toString();
						Order order = allOrdersMap.get(deliveryDate + "_" + sBuyerId + "_" + sSellerId);
						
						if (order == null) continue;
						
						/*
						 * If the order is already finalized, do not save the
						 * order item as part of the orders
						 */
						if (!NumberUtil.isNullOrZero(order.getAllocationFinalizedBy())) {
							continue;
						}
 
							
						if (!sSellerId.equals(sku.getUser().getUserId().toString())) continue;
						String quantityKey = orderDetails.isAllDatesView() == true ? deliveryDate : buyerId.toString();
						this.insertUpdateOrderItem(
										order, 
										sku.getSkuId(), 
										oi.getSkumaxlimit(), 
										qtyMap.get("Q_" + quantityKey), 
										null,
										false,
										true);
					}
				}
			}
		}
		
		// UPDATED SKU

		orderItemList = orderForm.getUpdatedOrders();
		
		List<Integer> skuIdList = Collections.emptyList();
		Map<CompositeKey<Integer>, OrderItem> mapOfAllocationItems = null;
		Map<CompositeKey<Object>, List<OrderItem>> allOrderItemMap = new HashMap<CompositeKey<Object>, List<OrderItem>>();
		if (CollectionUtils.isNotEmpty(orderItemList)) {
			skuIdList = OrderSheetUtil.getSKUId(orderItemList);
			List<OrderItem> allocationOrderItemList = allocationDao
					.getOrderItemsFromListOfSKUAndDate(skuIdList, deliveryDates);
			mapOfAllocationItems = convertToMap(allocationOrderItemList);
			//forUpdateOrdersMapFromDB =OrderSheetUtil.extractOrderFromOrderItem(allocationOrderItemList);
			allOrderItemMap = convertToOrderItemMap(allocationOrderItemList);
		}
		
		
		
		for (OrderItemUI oi : orderItemList) {
			User user = sellerUserObjMap.get(oi.getSellerId());
			if (user == null) {
				user = usersInfoDaos.getUserById(oi.getSellerId());
				sellerUserObjMap.put(oi.getSellerId(), user);
			}

			SKU sku = OrderSheetUtil.toSKU(oi, orderDetails, user, SheetTypeConstants.SELLER_ALLOCATION_SHEET);
			SKU origSku = skuObjMap.get(sku.getSkuId());
			Map<String, BigDecimal> qtyMap = oi.getQtyMap();
			boolean updateSpecial = false;
			boolean insertNewAlloc = false;
			// update EON_ORDER_ALLOCATION
			for (Integer sellerId : sellerIds) {
				boolean skuCheck = false;
				String sSellerId = sellerId.toString();
				for (String deliveryDate : deliveryDates) {
					List<Integer> dbBuyers = getDealingPattern(deliveryDate, sSellerId, orderDetails.getDealingPattern());
					List<Integer> finalizedBuyers = new ArrayList<Integer>();
					if (!sSellerId.equals(origSku.getUser().getUserId().toString())) continue;
					if (!skuCheck) {
						/*
						 * Get all buyer ids from all the allocation items that has been finalized
						 */
						List<Integer> finalizedAllBuyersList = getFinalizedAllocationBuyers(deliveryDate,sku.getSkuId(), allOrderItemMap);
						/*
						 * get all buyer ids that have a dealing pattern with the seller
						 */
						finalizedBuyers = filterBuyersDP(dbBuyers, finalizedAllBuyersList);
						
//						boolean hasFinalized = skuHasFinalizedAllocation(
//								deliveryDate, sellerId, dbBuyers, forUpdateOrdersMapFromDB,
//								finalizedBuyers);
						boolean hasFinalized = false;
						if(!CollectionUtils.isEmpty(finalizedAllBuyersList)){
							hasFinalized = true;
						}
						
						// if sku is edited and not saved, update special
						// if sku is edited or one buyer quantity edited and has other buyer quantity finalized, update special
						// if sku is edited and already saved, update normal 
						if (!origSku.equals(sku) &&
							skuNotSaved(sellerId, sku.getSkuId(), sku.getSheetType())) {
							// Update special
							updateSpecial = true;
							sku.setSheetType(SheetTypeConstants.SELLER_ALLOCATION_SHEET);
							skuService.updateNewSKU(sku);
//							if (!orderDetails.isAllDatesView())
//								skuMaxLimitDao.insertSKUMaxLimit(sku.getSkuId(), deliveryDate, oi.getSkumaxlimit());
						} else if (!origSku.equals(sku) && hasFinalized) {
							// Update special
							updateSpecial = true;
							insertNewAlloc = true;
							sku.setSheetType(SheetTypeConstants.SELLER_ALLOCATION_SHEET);
							skuService.updateNewSKU(sku);
//							if (!orderDetails.isAllDatesView())
//								skuMaxLimitDao.insertSKUMaxLimit(sku.getSkuId(), deliveryDate, oi.getSkumaxlimit());
						}
						else {
							// Update normal
							skuService.updateSKU(OrderSheetUtil.toSKU(oi, orderDetails));
//							if (!orderDetails.isAllDatesView())
//								skuMaxLimitDao.updateSKUMaxLimit(sku.getSkuId(), deliveryDate, oi.getSkumaxlimit());
						}
						skuCheck = true;
					}
					
					//for (Integer buyerId : buyerIds) {
					for (Integer buyerId : dbBuyers) {
						
						// process only the selected buyers
						if (!buyerIds.contains(buyerId)) continue;
						
						String sBuyerId = buyerId.toString();
						Order order = allOrdersMap.get(deliveryDate + "_" + sBuyerId + "_" + sSellerId);
						
						if (order == null) continue;
						if (!sSellerId.equals(origSku.getUser().getUserId().toString())) continue;
						if (finalizedBuyers.contains(buyerId)) continue;
						if (!NumberUtil.isNullOrZero(order.getAllocationFinalizedBy())) continue;
						String quantityKey = orderDetails.isAllDatesView() == true ? deliveryDate : buyerId.toString();
						this.insertUpdateOrderItem(
								order,
								sku.getSkuId(),
								oi.getSkumaxlimit(),
								qtyMap.get("Q_" + quantityKey),
								sku.getOrigSkuId(),
								updateSpecial,
								insertNewAlloc);
					}
				}
			}
		}
		
		// DELETED SKU
		List<Integer> orderItemsToDelete = new ArrayList<Integer>();
		List<Integer> receiveItemsToDelete = new ArrayList<Integer>();

		orderItemList = orderForm.getDeletedOrders();
		List<Integer> skuToDelete = new ArrayList<Integer>();
		for(OrderItemUI oi : orderItemList) {
			skuToDelete.add(oi.getSkuId().intValue());
		}
		
		if (skuToDelete.size() > 0) {
			for (String deliveryDate : deliveryDates) {
				for (Integer buyerId : buyerIds) {
					String sBuyerId = buyerId.toString();
					for (Integer sellerId : sellerIds) {
						String sSellerId = sellerId.toString();
						Order order = allOrdersMap.get(deliveryDate + "_" + sBuyerId + "_" + sSellerId);
						
						if (order == null) continue;
						
						//this.deleteOrderItems(order.getOrderId(), skuToDelete);
						orderItemsToDelete.add(order.getOrderId());
						/* reflected to received sheet if order was alloc published */
						if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy())) {
							//receivedSheetDao.deleteReceiveItems(order.getOrderId(), skuToDelete);
							receiveItemsToDelete.add(order.getOrderId());
						}
					}
				}
			}
		}
		if(CollectionUtils.isNotEmpty(orderItemsToDelete)){
			allocationDao.deleteAllocItemsByOrderIdsAndSkuIds(orderItemsToDelete, skuToDelete);
		}
		if(CollectionUtils.isNotEmpty(receiveItemsToDelete)){
			receivedSheetDao.deleteReceiveItems(orderItemsToDelete, skuToDelete);
		}
	}
	
	
	
	private List<Integer> getFinalizedAllocationBuyers(String deliveryDate, Integer skuId, Map<CompositeKey<Object>, List<OrderItem>> orderItemMap){
		List<Integer> result = new ArrayList<Integer>();
		CompositeKey<Object> key = new CompositeKey<Object>(
				deliveryDate, skuId);
		List<OrderItem> oiList = orderItemMap.get(key);
		for(OrderItem oi : oiList){
			if (!NumberUtil.isNullOrZero(oi.getAllocationFinalizedBy())) {
				result.add(oi.getBuyerId());
			}
		}
		
		return result;
		
	}
	
	private List<Integer> filterBuyersDP(List<Integer> dpBuyers, List<Integer> allBuyers){
		List<Integer> result = new ArrayList<Integer>();

		for(Integer buyerId: dpBuyers){
			if (allBuyers.contains(buyerId)) {
				result.add(buyerId);
			}
		}
		
		return result;
		
	}

	private boolean finalizedBuyersHasSKUItem(String deliveryDate,
			Integer sellerId, List<Integer> finalizedBuyers,
			Map<String, Order> allOrdersMap,
			Map<CompositeKey<Integer>, OrderItem> mapOfAllocationItems, SKU sku) {

		boolean finalizedBuyerHasSKUItem = false;
        
		if (CollectionUtils.isEmpty(finalizedBuyers)) {
        	return finalizedBuyerHasSKUItem;
        }
        
		for (Integer buyerId : finalizedBuyers) {
			String key = OrderSheetUtil.formatOrderMapKey(deliveryDate,
					buyerId, sellerId);
			Order order = allOrdersMap.get(key);
			if (order == null) {
				continue;
			}
			CompositeKey<Integer> compositeKey = new CompositeKey<Integer>(
					order.getOrderId(), sku.getSkuId());
			OrderItem orderItem = mapOfAllocationItems.get(compositeKey);
			if (orderItem != null) {
				finalizedBuyerHasSKUItem = true;
				break;
			}

		}

		return finalizedBuyerHasSKUItem;
	}

	private Map<CompositeKey<Integer>, OrderItem> convertToMap(
			List<OrderItem> allocationOrderItemList) {
		
		Map<CompositeKey<Integer>, OrderItem> returnMap = new HashMap<CompositeKey<Integer>, OrderItem>();
		
		if (CollectionUtils.isEmpty(allocationOrderItemList)) {
			return returnMap;
		}
		
		for (OrderItem orderItem : allocationOrderItemList) {
			CompositeKey<Integer> key = new CompositeKey<Integer>(
					orderItem.getOrderId(), orderItem.getSKUId());
			returnMap.put(key, orderItem);
		}
		
		return returnMap;
	}
	
	private Map<CompositeKey<Object>, List<OrderItem>> convertToOrderItemMap(
			List<OrderItem> allocationOrderItemList) {
		
		Map<CompositeKey<Object>, List<OrderItem>> returnMap = new HashMap<CompositeKey<Object>, List<OrderItem>>();
		
		if (CollectionUtils.isEmpty(allocationOrderItemList)) {
			return returnMap;
		}
		
		for (OrderItem orderItem : allocationOrderItemList) {
			CompositeKey<Object> key = new CompositeKey<Object>(
					orderItem.getDeliveryDate(), orderItem.getSKUId());
			List<OrderItem> oiList = returnMap.get(key);
			if(oiList==null){
				oiList = new ArrayList<OrderItem>();
			}
			oiList.add(orderItem);
			returnMap.put(key, oiList);
		}
		
		return returnMap;
	}
	
	

	@Override
	public boolean skuHasFinalizedAllocation(String deliveryDate, Integer sellerId, List<Integer> dbBuyers, Map<String, Order> allOrdersMap, List<Integer> finalizedBuyers) {
		for (Integer buyerId : dbBuyers) {
			String key = deliveryDate + "_" + buyerId + "_" + sellerId;
			Order order = allOrdersMap.get(key);
			if (order == null) continue;
			if (!NumberUtil.isNullOrZero(order.getAllocationFinalizedBy())) {
				finalizedBuyers.add(buyerId);
			}
		}
		return finalizedBuyers.size() > 0 ? true : false;
	}
	
	

	private void insertUpdateOrderItem(Order order, Integer skuId, BigDecimal skuMaxLimit, BigDecimal quantity, Integer origSkuId, boolean updateSpecial, boolean insertNewAlloc) {
		
		Integer orderId = order.getOrderId();
		
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("skuMaxLimit", skuMaxLimit);
		param.put("quantity", quantity);

		Map<String, Object> param2 = new HashMap<String,Object>();
		param2.put("orderId", orderId);
		param2.put("skuId", origSkuId);
		param2.put("skuMaxLimit", skuMaxLimit);
		param2.put("quantity", quantity);
		
		if (updateSpecial) {
			if (insertNewAlloc) {
				allocationDao.insertAllocItem(orderId, skuId, quantity, skuMaxLimit);
				/* reflected to received sheet if order was alloc published */
				// DELETION START 20120605: Rhoda Redmine 236
				//if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy()) && 
				//		quantity != null && !quantity.equals(new BigDecimal(0))) {
				// DELETION END 20120605: Rhoda Redmine 236
				// EMHANCEMENT 20120605: Rhoda Redmine 236
				if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy())){
					receivedSheetDao.insertDefaultReceiveItems(orderId, skuId, null, null, quantity);
					//null part is supposed to be userId
				}
			} else {
				OrderItem orderItem = allocationDao.getAllocationItem(param2);
				if (orderItem == null) {
					allocationDao.insertAllocItem(orderId, skuId, quantity, skuMaxLimit);
					/* reflected to received sheet if order was alloc published and quantity is not zero/null*/
					// DELETION START 20120605: Rhoda Redmine 236
					//if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy()) && 
					//		quantity != null && !quantity.equals(new BigDecimal(0))) {
					// DELETION END 20120605: Rhoda Redmine 236
					// EMHANCEMENT 20120605: Rhoda Redmine 236
					if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy())){
						receivedSheetDao.insertDefaultReceiveItems(orderId, skuId, null, null, quantity);
					}
				}else {
					// update new sku id and quantity in alloc item, use old sku id
					allocationDao.updateSpecialAllocItem(orderId, skuId, origSkuId, quantity);
					/* reflected to received sheet if order was alloc published and quantity is not zero/null
					 * and delete received item if order was alloc published and quantity is updated to zero or null*/
					if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy())) {
						// DELETION START 20120605: Rhoda Redmine 236
						//if (quantity != null && !quantity.equals(new BigDecimal(0))){
						//	receivedSheetDao.updateSpecialReceivedItem(orderId, skuId, origSkuId, quantity, null);
						//}
						// DELETION END 20120605: Rhoda Redmine 236
						// EMHANCEMENT 20120605: Rhoda Redmine 236
						receivedSheetDao.updateSpecialReceivedItem(orderId, skuId, origSkuId, quantity, null);
						//null part is supposed to be userId
					}
				}
			}
		} else {
			OrderItem orderItem = allocationDao.getAllocationItem(param);
			if (insertNewAlloc) {
				allocationDao.insertAllocItem(orderId, skuId, quantity, skuMaxLimit);
				/* reflected to received sheet if order was alloc published and quantity is not zero/null*/
				// DELETION START 20120605: Rhoda Redmine 236
				//if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy()) && 
				//		quantity != null && !quantity.equals(new BigDecimal(0))) {
				// DELETION END 20120605: Rhoda Redmine 236
				// EMHANCEMENT 20120605: Rhoda Redmine 236
				if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy())){
					receivedSheetDao.insertDefaultReceiveItems(orderId, skuId, null, null, quantity);
				}
			} else {
				if (orderItem == null) {
					allocationDao.insertAllocItem(orderId, skuId, quantity, skuMaxLimit);
					/* reflected to received sheet if order was alloc published and quantity is not zero/null*/
					// DELETION START 20120605: Rhoda Redmine 236
					//if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy()) && 
					//		quantity != null && !quantity.equals(new BigDecimal(0))) {
					// DELETION END 20120605: Rhoda Redmine 236
					// EMHANCEMENT 20120605: Rhoda Redmine 236
					if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy())){
						receivedSheetDao.insertDefaultReceiveItems(orderId, skuId, null, null, quantity);
					}
				}
				else {
					allocationDao.updateAllocItem(orderId, skuId, null, quantity, null);
					/* reflected to received sheet if order was alloc published and quantity is not zero/null*/
					if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy())) {
						// DELETION START 20120605: Rhoda Redmine 236
						//if (quantity != null && !quantity.equals(new BigDecimal(0))){
						//	receivedSheetDao.updateReceivedFromAlloc(orderId, skuId, quantity, null);
						//}
						// DELETION START 20120605: Rhoda Redmine 236
						// EMHANCEMENT 20120605: Rhoda Redmine 236
						// DELETION 20120627: Rhoda PROD ISSUE 
						//receivedSheetDao.updateSpecialReceivedItem(orderId, skuId, origSkuId, quantity, null);
						// ENHANCEMENT 20120627: Rhoda PROD ISSUE 
						receivedSheetDao.updateReceivedFromAlloc(orderId, skuId, quantity, null);
						//null part is isApproved and userId respectively
					}
				}
			}
		}
	}
	
	private List<Integer> getDealingPattern(String deliveryDate, String sellerId, DealingPattern dealingPattern) {
		Map<String, List<Integer>> dp = dealingPattern.getSellerToBuyerDPMap().get(deliveryDate);
		return dp.get(sellerId);
	}
	
	@Override
	public void deleteOrderItems(Integer orderId, List<Integer> skuId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId.toArray());
		allocationDao.deleteAllocationItemsByOrderIdAndSkuId(param);
	}

	@Override
	public List<Order> getSavedOrders(List<Order> orders) {
		List<Order> _order = new ArrayList<Order>();
		for (Order order : orders) {
			if (!NumberUtil.isNullOrZero(order.getAllocationSavedBy()) && 
				 NumberUtil.isNullOrZero(order.getAllocationFinalizedBy())) {
				_order.add(order);
			}
		}
		return _order;
	}
	
	@Override
	public List<Order> getToProcessOrders(List<Order> orders) {
		List<Order> _order = new ArrayList<Order>();
		for (Order order : orders) {
			if (!NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) {
				_order.add(order);
			}
		}
		return _order;
	}

	@Override
	public boolean sendMailNotification(String orderDate, String state,
			String username, String fromAddress, String[] toAddress) {
		
		String subject = null;
		String message = null;
		String date = orderDate.substring(0,4) + "/" + orderDate.substring(4,6) + "/" + orderDate.substring(6,8);
		
		if (state.equals("Publish")) {
			subject = "Allocation Sheet for " + date + " Publish";
			message = "Allocation Sheet for " + date + " Publish by " + username + " as of " + DateFormatter.getDateToday("yyyy/MM/dd h:mm a") + ".";
			//message = message + "\nAlert message is available on your Home Page Alerts’ section.";
		}  else if (state.equals("Unpublish")) {
			subject = "Allocation Sheet for " + date + " Unpublish";
			message = "Allocation Sheet for " + date + " Unpublish by " + username + " as of " + DateFormatter.getDateToday("yyyy/MM/dd h:mm a") + ".";
		} else if (state.equals("Finalize")) {
			subject = "Allocation Sheet for " + date + " Finalized";
			message = "Allocation Sheet for " + date + " Finalized by " + username + " as of " + DateFormatter.getDateToday("yyyy/MM/dd h:mm a") + ".";
		} else if (state.equals("Unfinalize")) {
			subject = "Allocation Sheet for " + date + " Unfinalized";
			message = "Allocation Sheet for " + date + " Unfinalized by " + username + " as of " + DateFormatter.getDateToday("yyyy/MM/dd h:mm a") + ".";
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

		String subject = String.format("Allocation Sheet for %s %s", dateSubj,
				state);
		String multipleDatesMessage = multipleDates ? " the following dates "
				: "";
		String message = String.format(
				"Allocation Sheet for %s: %s \n %s  by %s as of %s .",
				multipleDatesMessage, date, state, username,
				DateFormatter.getDateToday("yyyy/MM/dd h:mm a"));

		return sendMailNotification(fromAddress, toAddress, subject, message);
	}
	
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
	
	@Override
	public List<Order> getPublishedOrders(List<Order> orders) {
		List<Order> _orders = new ArrayList<Order>();
		for (Order order : orders) {
			if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy()) &&
				NumberUtil.isNullOrZero(order.getAllocationFinalizedBy())) {
				_orders.add(order);
			}
		}
		return _orders;
	}
	
	@Override
	public List<Order> getFinalizedOrders(List<Order> orders) {
		List<Order> _orders = new ArrayList<Order>();
		for (Order order : orders) {
			if (!NumberUtil.isNullOrZero(order.getAllocationFinalizedBy()) &&
					NumberUtil.isNullOrZero(order.getReceivedApprovedBy())) {
				_orders.add(order);
			}
		}
		return _orders;
	}
	
	@Override
	public GrandTotalPrices getGTPriceAllOrders(List<Integer> orderIds) {
		return allocationDao.getGTPriceAllOrders(orderIds);
	}
	
	@Override
	public Map<Integer, Map<String, Map<Integer, OrderItem>>> getSellerAllocItemsBulk(List<Integer> orderIds,
			List<Integer> skuIds) {
		
		//List<OrderItem> itemList = allocationDao.getSellerAllocItemsBulk(orderIds, skuIds);
		//return OrderSheetUtil.convertToOrderItemsBulkMap(itemList);
		
		List<OrderItem> orderItemBulk = new ArrayList<OrderItem>();
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
			
			orderItemBulk.addAll(allocationDao.getSellerAllocItemsBulk(orderIds, thisSkuIds, false));
		}
		
		return OrderSheetUtil.convertToOrderItemsBulkMap(orderItemBulk);
		
	}
	
	@Override
	public List<OrderItem> getOrderItem(Integer sellerId, String deliveryDate,
			Integer skuId) {
		return allocationDao.getOrderItem(sellerId, deliveryDate, skuId);
	}

	@Override
	public boolean skuHasQuantities(List<Integer> companyBuyerIds,
			String deliveryDate, SKU skuObj) throws Exception {
		Map<String, Object> _param = new HashMap<String, Object>();
		_param.put("deliveryDate", deliveryDate);
		_param.put("skuId", skuObj.getSkuId());
		Map<Integer, OrderItem> orderItemMap = allocationDao
				.getOrderItemsMapOfSkuDate(_param);

		boolean status = false;
		for (Integer buyerId : companyBuyerIds) {
			OrderItem orderItem = orderItemMap.get(buyerId);
			BigDecimal quantity = null;

			if (orderItem != null) {
				quantity = orderItem.getQuantity();
				if (quantity != null) {
					status = true;
					break;
				}
			}
		}
		return status;
	}

	@Override
	public List<SKU> wsGetDistinctSKUs(List<Integer> orderIds) {
		// TODO Auto-generated method stub
		return allocationDao.selectDistinctSKUs(orderIds);
	}

	@Override
	public void updateAllocationOrdersWS(String deliveryDate,
			List<Integer> buyerIds, List<Integer> sellerIds,
			AllocationInputDetails[] allocationInputDetails,
			List<Order> orderList, User user) {
		// UPDATE ORDERS
		Map<String, Order> allOrdersMap = OrderSheetUtil
				.convertToOrderMap(orderList);
		for (Integer buyerId : buyerIds) {
			String sBuyerId = buyerId.toString();
			for (Integer sellerId : sellerIds) {
				String sSellerId = sellerId.toString();
				Order order = allOrdersMap.get(deliveryDate + "_" + sBuyerId
						+ "_" + sSellerId);

				if (order == null)
					continue;

				orderDao.updateSaveAllocation(user.getUserId(), user.getUserId()
						, order.getOrderId());
			}
		}
		
	}

	@Override
	public void deleteAllocationItems(List<Integer> skuToDelete,
			String deliveryDate, List<Integer> buyerIds,
			List<Integer> sellerIds, List<Order> allOrders) {
		Map<String, Order> allOrdersMap = OrderSheetUtil
		.convertToOrderMap(allOrders);

		if (skuToDelete.size() > 0) {
			for (Integer buyerId : buyerIds) {
				String sBuyerId = buyerId.toString();
				for (Integer sellerId : sellerIds) {
					String sSellerId = sellerId.toString();
					Order order = allOrdersMap.get(deliveryDate + "_"
							+ sBuyerId + "_" + sSellerId);
		
					if (order == null)
						continue;
		
					this.deleteOrderItems(order.getOrderId(), skuToDelete);
				}
			}
		}
		
	}

	@Override
	public void insertAllocationSKUWS(String deliveryDate,
			List<Integer> buyerIds, List<Integer> sellerIds,
			AllocationInputDetails[] allocationInputDetails,
			List<Order> orderList) {
		// INSERT ALLOCATION SKU
		Map<String, Order> allOrdersMap = OrderSheetUtil
				.convertToOrderMap(orderList);
		for (AllocationInputDetails obj : allocationInputDetails) {
			User user = usersInfoDaos.getUserResultByName(obj.getSellerName());
			SKU sku = WebServiceUtil.wsToSKU(obj, user, false);
			
			
//			Category cat = getCategory(user.getUserId(),obj.getSkuCategoryName());remove
			Category cat = categoryService.getCategory(user.getUserId(),obj.getSkuCategoryName());
//			OrderUnit uom = getOrderUnit(cat.getCategoryId(),obj.getUnitOfOrder());remove
			OrderUnit uom = orderUnitService.getOrderUnit(cat.getCategoryId(),obj.getUnitOfOrder());
			
			if(uom == null ){
				uom=new OrderUnit();
				//this.insertUOM(obj.getUnitOfOrder(), cat.getCategoryId(), uom);//remove
				orderUnitService.insertUOM(obj.getUnitOfOrder(), cat.getCategoryId(), uom);
			}
			
			SKUGroup skuGrp = this.getSkuGroup(user.getUserId(), obj.getSkuGroupName(),cat.getCategoryId());
			
			if(skuGrp ==null || skuGrp.getCategoryId() ==null){
				skuGrp = new SKUGroup();
				skuGrp.setCategoryId(cat.getCategoryId());
				skuGrp.setDescription(obj.getSkuGroupName());
				skuGrp.setSellerId(user.getUserId());
				skuGroupDao.insertSkuGroupToUpdateName(skuGrp);
			}
			sku.setOrderUnit(uom);
			sku.setSkuCategoryId(cat.getCategoryId());
			sku.setSkuGroup(skuGrp);
			
			skuService.insertSKU(sku);
			Map<Integer, Object> buyerQuantityMap = WebServiceUtil
					.createBuyerMap(obj.getAllocationBuyerInformation());
			// save to EON_ORDER_ALLOCATION
			for (Integer buyerId : buyerIds) {
				String sBuyerId = buyerId.toString();

				AllocationBuyerInformation buyerInfo = new AllocationBuyerInformation();

				if (buyerQuantityMap.containsKey(buyerId)) {
					buyerInfo = (AllocationBuyerInformation) buyerQuantityMap
							.get(buyerId);
				}

				for (Integer sellerId : sellerIds) {
					String sSellerId = sellerId.toString();
					Order order = allOrdersMap.get(deliveryDate + "_"
							+ sBuyerId + "_" + sSellerId);

					if (order == null)
						continue;
					if (!sSellerId.equals(sku.getUser().getUserId().toString()))
						continue;
					this.insertUpdateOrderItem(order, sku
							.getSkuId(), new BigDecimal(1), buyerInfo.getQuantity(), sku
							.getSkuId(), false, false);
				}
			}
		}
		
	}
	
	public void markAsSaved(Integer allocationSavedBy, Integer updatedBy, Integer orderId){
		orderDao.updateSaveAllocation(allocationSavedBy, updatedBy, orderId);
	}
	
	private SKUGroup getSkuGroup(Integer sellerId,String grpName,Integer categoryId){
		return skuGroupDao.getSKUgroup(sellerId, grpName,categoryId);	
	}

	/*
	 * This method is used to get either;
	 * 1. TOTALS of ProfitInfo if given parameter is only one category 
	 *    and one day for all buyers view or multiple days for all dates view
	 * 2. GRAND TOTALS of ProfitInfo if given parameter is more than one categories
	 *    and one day for all buyers view or multiple days for all dates view
	 */
	@Override
	public ProfitInfo getProfitInfo(User user, List<String> deliveryDate,
			List<Integer> sellerId,
			Map<String, List<Integer>> mapOfMembersByDate,
			List<Integer> buyerId, List<Integer> categoryId, Double tax) {
		
		Set<Integer> categorySet = new TreeSet<Integer>(categoryId);
		
		ProfitInfo pi = new ProfitInfo();
		for (String _deliveryDate : deliveryDate) {
			List<Integer> validSellersForDate = new ArrayList<Integer>();
			
			if (RolesUtil.isUserRoleSellerAdmin(user)){
				List<Integer>  tmpvalidSellerMemberList = mapOfMembersByDate.get(_deliveryDate);
				for (Integer integer : sellerId) {
					if(tmpvalidSellerMemberList.contains(integer)){
						validSellersForDate.add(integer);
					}
				}
			} else {
				validSellersForDate = sellerId;
			}
			if (CollectionUtils.isEmpty(validSellersForDate)) {
				continue;
			}
			
			Iterator<Integer> categoryI = categorySet.iterator();
			while (categoryI.hasNext()) {
				ProfitInfo _pi = allocationDao.getProfitInfo(_deliveryDate, buyerId, validSellersForDate, categoryI.next(), tax);
				if (_pi != null)
					pi.add(_pi);
			}
		}
		
		return pi;
	}
}