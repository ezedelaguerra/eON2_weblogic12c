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
 * Jan 12, 2011		raquino		
 * 20121001	Rhoda	v12		Redmine #1084 – Allocation-Received Sheet different in quantity 
 */
package com.freshremix.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.dao.AllocationDao;
import com.freshremix.dao.CategoryDao;
import com.freshremix.dao.OrderDao;
import com.freshremix.dao.OrderItemDao;
import com.freshremix.dao.OrderSheetDao;
import com.freshremix.dao.OrderUnitDao;
import com.freshremix.dao.ReceivedSheetDao;
import com.freshremix.dao.SKUDao;
import com.freshremix.dao.SKUGroupDao;
import com.freshremix.dao.UserDao;
import com.freshremix.model.Category;
import com.freshremix.model.DefaultOrder;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.model.WSBuyerInformation;
import com.freshremix.model.WSInputDetails;
import com.freshremix.model.WSSKU;
import com.freshremix.service.WebService;
import com.freshremix.util.CategoryUtil;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.StringUtil;
import com.freshremix.util.WebServiceUtil;

/**
 * @author raquino
 * 
 */
public class WebServiceImpl implements WebService {

	private OrderDao orderDao;
	private OrderItemDao orderItemDao;
	private OrderSheetDao orderSheetDao;
	private CategoryDao categoryDaos;
	private UserDao usersInfoDaos;
	private OrderUnitDao orderUnitDao;
	private SKUGroupDao skuGroupDao;
	private SKUDao skuDao;
	private AllocationDao allocationDao;
	private ReceivedSheetDao receivedSheetDao;

	public void setReceivedSheetDao(ReceivedSheetDao receivedSheetDao) {
		this.receivedSheetDao = receivedSheetDao;
	}

	public void setAllocationDao(AllocationDao allocationDao) {
		this.allocationDao = allocationDao;
	}

	public void setSkuDao(SKUDao skuDao) {
		this.skuDao = skuDao;
	}

	public void setSkuGroupDao(SKUGroupDao skuGroupDao) {
		this.skuGroupDao = skuGroupDao;
	}

	public void setOrderUnitDao(OrderUnitDao orderUnitDao) {
		this.orderUnitDao = orderUnitDao;
	}

	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}

	public void setCategoryDaos(CategoryDao categoryDaos) {
		this.categoryDaos = categoryDaos;
	}

	public void setOrderSheetDao(OrderSheetDao orderSheetDao) {
		this.orderSheetDao = orderSheetDao;
	}

	public void setOrderItemDao(OrderItemDao orderItemDao) {
		this.orderItemDao = orderItemDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.freshremix.service.WebService#addSkuAllocation()
	 */
	@Override
	public void addSkuAllocation(User user, Map<Integer, User> sellerMap,
			WSInputDetails[] wsInputDetails, List<Integer> buyerIds,
			String orderDate, List<Order> orderList) throws Exception {

		List<Integer> sellerIds = new ArrayList<Integer>();

		Integer sellerId = null;

		boolean isAdmin = false;
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
			isAdmin = true;
			List<Integer> sellers = new ArrayList<Integer>(sellerMap.keySet());
			for (Integer _sellerId : sellers) {
				User sellerUser = sellerMap.get(_sellerId);
				sellerId = sellerUser.getUserId();
				sellerIds.add(sellerId);
			}
		} else {
			sellerId = user.getUserId();
			sellerIds.add(sellerId);
		}

		// Alloc SKU list of all orders of a given order date
		List<Integer> orderIdList = OrderSheetUtil.getOrderIdList(orderList);
		List<SKU> skuAllocList = allocationDao.selectDistinctSKUs(orderIdList);

		// This updates ALLOCATION_SAVED_BY of all order list
		Map<String, Order> allOrdersMap = OrderSheetUtil
				.convertToOrderMap(orderList);
		this.updateAllocationOrdersWS(orderDate, buyerIds, sellerIds,
				allOrdersMap, user);
		// Delete allocation item with List of SKUs from all orders
		if (skuAllocList != null && skuAllocList.size() != 0){
			List<Integer> skuToDelete = OrderSheetUtil.getSKUIds(skuAllocList);
			allocationDao.deleteAllocItemsByOrderIdsAndSkuIds(orderIdList,
					skuToDelete);
			receivedSheetDao.deleteReceiveItems(orderIdList, skuToDelete);
		}

		// Insert SKUs and Items from Web Service inputs
		this.insertAllocationSKUWS(orderDate, buyerIds, sellerIds,
				wsInputDetails, allOrdersMap, user, isAdmin, sellerMap);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.freshremix.service.WebService#getSellerOrderSheet()
	 */
	@Override
	public WSSKU[] getSellerOrderSheet(List<Integer> sellerIds,
			List<Integer> buyerIds, String orderDate,
			Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap)
			throws Exception {

		Map<Integer, User> buyersMap = usersInfoDaos.mapUserNames(buyerIds);
		List<String> dateList = new ArrayList<String>();
		dateList.add(orderDate);

		// Get existing orders or set previous orders if no existing orders
		this.insertDefaultOrders(sellerIds, orderDate, buyerIds,
				sellerToBuyerDPMap);
		List<Order> allOrders = this.getDefaultOrdersList(buyerIds, dateList,
				sellerIds);

		List<Integer> orderIdList = OrderSheetUtil.getOrderIdList(allOrders);
		List<SKU> skuList = this.wsGetDistinctSKUs(orderIdList);

		List<WSSKU> skus = new ArrayList<WSSKU>();

		for (SKU sku : skuList) {
			for (Integer id : buyerIds) {
				Map<String, List<Integer>> sellerBuyerMap = sellerToBuyerDPMap.get(orderDate);
				List<Integer> buyerDP = sellerBuyerMap.get(sku.getUser().getUserId().toString());
				if(buyerDP.contains(id)){
					String buyerName = buyersMap.get(id).getName();
					WSSKU skuOrderItem = createSKUObject(sku, id, buyerName, orderDate);
					if(skuOrderItem != null){
						skus.add(skuOrderItem);
					}
				}
			}

		}
		return skus.toArray(new WSSKU[skus.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.freshremix.service.WebService#createSheetWS(java.lang.Integer,
	 * java.lang.String, com.freshremix.model.WSInputDetails[])
	 */
	@Override
	public void createSheetWS(User user, Map<Integer, User> sellerMap,
			List<Integer> buyerIdList, String orderDate,
			WSInputDetails[] wsInputDetails, boolean isPublish,
			boolean isFinalize,
			Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap)
			throws Exception {

		List<Integer> sellerIds = new ArrayList<Integer>();

		Integer sellerId = null;

		// If userAdmin, get List of SellerName from inputs
		// which are BuyerList Partners
		boolean isAdmin = false;
		if (user.getRole().getSellerAdminFlag().equals("1")) {
			isAdmin = true;
			List<Integer> sellers = new ArrayList<Integer>(sellerMap.keySet());
			for (Integer _sellerId : sellers) {
				User sellerUser = sellerMap.get(_sellerId);
				sellerId = sellerUser.getUserId();
				sellerIds.add(sellerId);
			}
		} else {
			sellerId = user.getUserId();
			sellerIds.add(sellerId);
		}
		List<String> dateList = new ArrayList<String>();
		dateList.add(orderDate);

		// Get existing orders or set previous orders if no existing orders
		this.insertDefaultOrders(sellerIds, orderDate, buyerIdList,
				sellerToBuyerDPMap);
		List<Order> allOrders = this.getDefaultOrdersList(buyerIdList,
				dateList, sellerIds);

		// SKU list of default or existing orders
		List<SKU> skuList = new ArrayList<SKU>();
		List<Integer> orderIdList = new ArrayList<Integer>();
		orderIdList = OrderSheetUtil.getOrderIdList(allOrders);
		skuList = this.wsGetDistinctSKUs(orderIdList);

		// Match default or existing orders from web service input details
		Map<Integer, SKU> matchedSKUMap = new HashMap<Integer, SKU>();
		// Map<Integer, SKU> skuTodeleteMap = new HashMap<Integer, SKU>();

		List<WSInputDetails> insertDetails = new ArrayList<WSInputDetails>();
		List<WSInputDetails> updateDetails = new ArrayList<WSInputDetails>();
		List<Integer> deleteDetails = new ArrayList<Integer>();

		deleteDetails = OrderSheetUtil.getSkuIds(skuList);
		this.populateLists(wsInputDetails, skuList, matchedSKUMap,
				insertDetails, updateDetails, deleteDetails, dateList);

		WSInputDetails[] insertItems = (WSInputDetails[]) insertDetails
				.toArray(new WSInputDetails[insertDetails.size()]);

		WSInputDetails[] updateItems = (WSInputDetails[]) updateDetails
				.toArray(new WSInputDetails[updateDetails.size()]);

		Map<String, Order> allOrdersMap = OrderSheetUtil
				.convertToOrderMap(allOrders);

		// This updates OrderSavedBy from Order table - refactor???
		this.updateOrdersWS(orderDate, buyerIdList, sellerIds, allOrdersMap);

		// This Delete Order from Order table - refactor???
		// this.deleteOrdersWS(OrderSheetUtil.getSKUIds(skuList), orderDate,
		// buyerIdList,
		// sellerIds, allOrders, skuTodeleteMap);
		this.deleteOrdersWS(orderDate, buyerIdList, sellerIds, allOrdersMap,
				deleteDetails);

		// Update OrderItems as per WSInputs
		this.updateSKUWS(orderDate, buyerIdList, sellerIds, updateItems,
				matchedSKUMap, allOrdersMap);

		// Insert new SKUs and Order Items
		this.insertSKUWS(orderDate, buyerIdList, sellerIds, sellerMap,
				insertItems, allOrdersMap, user, isAdmin);

		if (isPublish)
			this.publishOrders(user.getUserId(), orderIdList);

		if (isFinalize)
			this.finalizeOder(user.getUserId(), orderIdList);
		
		// Added rules - 6/29/2011
		//1. Get future not yet publish orders (orders before a published order)
		//2. Delete these Orders and corresponding Items
		orderDao.deleteFutureOrdersBeforePulished(buyerIdList, sellerIds, orderDate);

	}

	@Override
	public void insertDefaultOrders(List<Integer> sellerIds, String orderDate,
			List<Integer> buyerIdList,
			Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap) {

		Order order = null;
		DefaultOrder dOrder = null;

		Map<String, List<Integer>> sellerToBuyerDP = sellerToBuyerDPMap
				.get(orderDate);
		Set<String> sellers = sellerToBuyerDP.keySet();
		for (String _seller : sellers) {
			Integer sellerId = Integer.valueOf(_seller);
			for (Integer buyerId : sellerToBuyerDP.get(_seller)) {
				order = this.getOrderByDeliveryDate(sellerId, buyerId,
						orderDate);
				Order oldOrder = orderDao.getPreviousOrder(sellerId, buyerId,
						orderDate);
				if (order == null) {
					dOrder = this.loadDefaultOrderItems(sellerId, buyerId,
							SheetTypeConstants.SELLER_ORDER_SHEET, orderDate,
							oldOrder);
					if (oldOrder == null) {
						List<Integer> selectedBuyerIds = sellerToBuyerDP
								.get(_seller);
						orderItemDao.insertBulkOrderItemNewBuyer(dOrder
								.getOrderId(), sellerId, buyerId, orderDate,
								selectedBuyerIds);
					} else {
						this.saveBulkOrders(dOrder.getOrderId(), sellerId,
								buyerId, orderDate);
					}
				} else {
					Integer savedBy = order.getOrderSavedBy();
					Boolean isNotSaved = savedBy == null || savedBy == 0 ? true
							: false;
					if (isNotSaved) {
						try {
							if (this.isCopiedDataStale(order, oldOrder)) {
								dOrder = this.loadDefaultOrderItems(sellerId,
										buyerId,
										SheetTypeConstants.SELLER_ORDER_SHEET,
										orderDate, oldOrder);
								// saveDefaultOrderItems(dOrder);
								this.saveBulkOrders(dOrder.getOrderId(),
										sellerId, buyerId, orderDate);
							}

						} catch (Exception e) {
							// e.printStackTrace();
							// System.out.println("this is fine.");
							// order is already existing but not yet saved.
							this.updateOrder(order.getOrderId(), oldOrder);
							dOrder = new DefaultOrder();
							dOrder.setOrderId(order.getOrderId().intValue());
							this.updateDefaultOrderItems(dOrder, sellerId,
									buyerId,
									SheetTypeConstants.SELLER_ORDER_SHEET,
									orderDate);
						}
					}
				}
			}
		}
	}

	private Order getOrderByDeliveryDate(Integer sellerId, Integer buyerId,
			String deliveryDate) {
		return orderDao.getOrderByDeliveryDate(sellerId, buyerId, deliveryDate);
	}

	private DefaultOrder loadDefaultOrderItems(Integer sellerId,
			Integer selectedBuyerId, Integer sheetTypeId, String deliveryDate,
			Order oldOrder) {

		Integer orderId = null;

		DefaultOrder dOrder = new DefaultOrder();

		Order order = new Order();
		order.setSellerId(sellerId);
		order.setBuyerId(selectedBuyerId);
		order.setDeliveryDate(deliveryDate);

		if (oldOrder != null) {
			order.setCopiedFromTimeStamp(oldOrder.getLastSavedOsTimeStamp());
			order.setCopiedFromOrderId(oldOrder.getOrderId());
		}

		orderId = orderDao.insertOrder(order);
		dOrder.setOrderId(orderId.intValue());

		return dOrder;
	}

	private void saveBulkOrders(Integer orderId, Integer sellerId,
			Integer buyerId, String deliveryDate) {
		Map<String, Object> _param = new HashMap<String, Object>();
		_param.put("orderId", orderId);
		_param.put("sellerId", sellerId);
		_param.put("selectedBuyerId", buyerId);
		_param.put("deliveryDate", deliveryDate);
		orderItemDao.insertBulkOrderItem(_param);
	}

	private boolean isCopiedDataStale(Order newOrder, Order oldOrder) {

		boolean status = false;

		if (oldOrder == null) {
			return false;
		}

		if (!(oldOrder.getOrderId().equals(newOrder.getCopiedFromOrderId()))) {
			status = true;
		} else if (!oldOrder.getLastSavedOsTimeStamp().equals(
				newOrder.getCopiedFromTimeStamp())) {
			status = true;
		}

		return status;
	}

	private void updateOrder(Integer orderId, Order oldOrder) {
		Order order = new Order();
		order.setOrderId(orderId);
		order.setCopiedFromOrderId(oldOrder.getOrderId());
		order.setCopiedFromTimeStamp(oldOrder.getLastSavedOsTimeStamp());
		orderDao.updateOrder(order);
	}

	private void updateDefaultOrderItems(DefaultOrder dOrder, Integer sellerId,
			Integer selectedBuyerId, Integer sheetTypeId, String deliveryDate) {

		this.deleteAllOrderItemsByOrderId(dOrder.getOrderId());

		this.saveBulkOrders(dOrder.getOrderId(), sellerId, selectedBuyerId,
				deliveryDate);
	}

	public void deleteAllOrderItemsByOrderId(Integer orderId) {
		orderItemDao.deleteAllOrderItemsByOrderId(orderId);
	}

	public List<Order> getDefaultOrdersList(List<Integer> buyerIds,
			List<String> dateList, List<Integer> sellerIds) {
		List<Order> allOrders = orderDao.getAllOrders(buyerIds, dateList,
				sellerIds);
		return allOrders;
	}

	private List<SKU> wsGetDistinctSKUs(List<Integer> selectedOrders) {
		return orderSheetDao.wsSelectDistinctSKUs(selectedOrders);
	}

	/**
	 * populate the ff: skuToinsert, updateDetails,skuMap
	 * 
	 * @param details
	 * @param skuList
	 * @return
	 */
	private void populateLists(WSInputDetails[] details, List<SKU> skuList,
			Map<Integer, SKU> matchedSKUMap,
			List<WSInputDetails> insertDetails,
			List<WSInputDetails> updateDetails, List<Integer> deleteList,
			List<String> dateList) {

		boolean isMatch = false;
		for (WSInputDetails input : details) {

			boolean inputHasMatch = false;
			for (SKU sku : skuList) {
				Integer skuId = sku.getSkuId();
				isMatch = isMatch(input, sku);

				if (isMatch) {
					if (!updateDetails.contains(input)) {
						matchedSKUMap.put(input.getSkuId(), sku);
						updateDetails.add(input);
					}
					deleteList.remove(skuId);
					if (!inputHasMatch) inputHasMatch = true;
					continue;
				} 
			}

			if (!inputHasMatch) {
				insertDetails.add(input);
			}
		}
	}

	/**
	 * Check if an existing SKU has the exact same fields as the incoming SKUS
	 * 
	 * @param input
	 * @param sku
	 * @return
	 */
	private boolean isMatch(WSInputDetails input, SKU sku) {

		if (!( StringUtil.nullToBlank(input.getSkuName()).equals(
				StringUtil.nullToBlank(sku.getSkuName())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getSkuCategoryName()).equals(
				StringUtil.nullToBlank(this.getCategoryName(sku.getSkuCategoryId()))) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getSkuGroupName()).equals(
				StringUtil.nullToBlank(sku.getSkuGroup().getDescription())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getLocation()).equals(
				StringUtil.nullToBlank(sku.getLocation())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getMarket()).equals(
				StringUtil.nullToBlank(sku.getMarket())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getClass1()).equals(
				StringUtil.nullToBlank(sku.getGrade())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getClass2()).equals( 
				StringUtil.nullToBlank(sku.getClazz())) )) {
			return false;
		} else if (!input.getPackageQuantity().toPlainString().equals(
				sku.getPackageQuantity().toPlainString())) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getPackageType()).equals(
				StringUtil.nullToBlank(sku.getPackageType())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getUnitOfOrder()).equals(
				StringUtil.nullToBlank(sku.getOrderUnit().getOrderUnitName())) )) {
			return false;
		} else if (!( StringUtil.nullToBlank(input.getSkuExternalID()).equals(
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

	private String getCategoryName(Integer categoryId) {
		List<Integer> categoryList = new ArrayList<Integer>();
		categoryList.add(categoryId);
		List<Category> categoryName = categoryDaos
				.getCategoryById(categoryList);
		Category cat = categoryName.get(0);
		return cat.getDescription();
	}

	private void updateOrdersWS(String deliveryDate, List<Integer> buyerIdList,
			List<Integer> sellerIds, Map<String, Order> allOrdersMap)
			throws Exception {

		// UPDATE ORDERS --->
		for (Integer buyerId : buyerIdList) {
			String sBuyerId = buyerId.toString();
			for (Integer sellerId : sellerIds) {
				String sSellerId = sellerId.toString();
				Order order = allOrdersMap.get(deliveryDate + "_" + sBuyerId
						+ "_" + sSellerId);

				if (order == null)
					continue;
				orderDao.updateSaveOrder(sellerId, order.getCreatedBy(), order
						.getOrderId());
			}
		}
	}

	private void deleteOrdersWS(String deliveryDate, List<Integer> buyerIds,
			List<Integer> sellerIds, Map<String, Order> allOrdersMap,
			List<Integer> deleteList) throws Exception {

		if (deleteList != null && deleteList.size() != 0) {
			for (Integer buyerId : buyerIds) {
				String sBuyerId = buyerId.toString();
				for (Integer sellerId : sellerIds) {
					String sSellerId = sellerId.toString();
					Order order = allOrdersMap.get(deliveryDate + "_"
							+ sBuyerId + "_" + sSellerId);
					if (order == null)
						continue;
					this.deleteOrderItems(order.getOrderId(), deleteList);
				}
			}
		}
	}

	private void deleteOrderItems(Integer orderId, List<Integer> skuId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId.toArray());
		orderItemDao.deleteOrderItem(param);
	}

	private void updateSKUWS(String deliveryDate, List<Integer> buyerIds,
			List<Integer> sellerIds, WSInputDetails[] details,
			Map<Integer, SKU> updateSKUMap, Map<String, Order> allOrdersMap)
			throws Exception {

		// UPDATED SKU
		for (WSInputDetails obj : details) {
			User user = usersInfoDaos.getUserById(sellerIds.get(0));
			SKU sku = WebServiceUtil.wstoSKU(obj, user);
			SKU origSku = updateSKUMap.get(sku.getSkuId());
			WSBuyerInformation[] buyerList = obj.getWsBuyerInformation();
			// update EON_ORDER_ITEM
			for (Integer sellerId : sellerIds) {
				String sSellerId = sellerId.toString();

				if (!sSellerId.equals(origSku.getUser().getUserId().toString()))
					continue;

				for (WSBuyerInformation wsBuyerInformation : buyerList) {

					String sBuyerId = wsBuyerInformation.getBuyerId()
							.toString();
					Order order = allOrdersMap.get(deliveryDate + "_"
							+ sBuyerId + "_" + sSellerId);

					if (order == null)
						continue;
					// ???
					if (!sSellerId.equals(origSku.getUser().getUserId()
							.toString()))
						continue;

					orderItemDao.updateOrderItem(order.getOrderId(), origSku
							.getSkuId(), origSku.getSkuId(), wsBuyerInformation
							.isVisible() ? wsBuyerInformation.getQuantity()
							: null, wsBuyerInformation.isVisible() ? "1" : "0", true, false, false);
				}
			}
		}

	}

	private void insertSKUWS(String deliveryDate, List<Integer> buyerIds,
			List<Integer> sellerIds, Map<Integer, User> sellerMap,
			WSInputDetails[] details, Map<String, Order> allOrdersMap,
			User seller, boolean isAdmin) throws Exception {

		// INSERT SKU
		for (WSInputDetails wsInput : details) {
			if (isAdmin)
				seller = sellerMap.get(wsInput.getSellerId());

			SKU sku = WebServiceUtil.wstoSKU(wsInput, seller);

			Category cat = this.getCategory(seller.getUserId(), wsInput.getSkuCategoryName());
			OrderUnit uom = this.getOrderUnit(cat.getCategoryId(), wsInput.getUnitOfOrder());

			if (uom == null) {
				uom = new OrderUnit();
				this.insertUOM(wsInput.getUnitOfOrder(), cat.getCategoryId(), uom);
			}

			SKUGroup skuGrp = this.getSkuGroup(seller.getUserId(), wsInput
					.getSkuGroupName(), cat.getCategoryId());

			if (skuGrp == null) {
				skuGrp = new SKUGroup();
				skuGrp.setCategoryId(cat.getCategoryId());
				skuGrp.setDescription(wsInput.getSkuGroupName());
				skuGrp.setSellerId(seller.getUserId());
				skuGroupDao.saveSKUGroup(skuGrp);
			}
			sku.setOrderUnit(uom);
			sku.setSkuCategoryId(cat.getCategoryId());
			sku.setSkuGroup(skuGrp);
			this.insertSKU(sku);
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
					Order order = allOrdersMap.get(deliveryDate + "_" + buyerId
							+ "_" + sSellerId);

					if (order == null)
						continue;
					if (!sSellerId.equals(sku.getUser().getUserId().toString()))
						continue;

					orderItemDao.insertOrderItem(order.getOrderId(), sku
							.getSkuId(), null,
							wsBuyerInformation.isVisible() ? wsBuyerInformation
									.getQuantity() : null, wsBuyerInformation
									.isVisible() ? "1" : "0");

				}
			}
		}

	}

	private Category getCategory(Integer userId, String categoryName) {

		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId.toString());
		List<UsersCategory> categoryList = categoryDaos
				.getCategoryListByUserId(param);
		Category cat = null;
		for (UsersCategory usersCategory : categoryList) {
			if (usersCategory.getCategoryAvailable().equals(categoryName)) {
				cat = new Category();
				cat.setCategoryId(usersCategory.getCategoryId());
				cat.setDescription(usersCategory.getCategoryAvailable());
				return cat;
			}
		}

		return cat;
	}

	private OrderUnit getOrderUnit(Integer categoryId, String unitValue) {
		List<OrderUnit> unit = orderUnitDao.getOrderUnitList(categoryId);
		OrderUnit value = new OrderUnit();
		for (OrderUnit uom : unit) {
			if (uom.getOrderUnitName().equals(unitValue)) {
				value = uom;
			}
		}
		return value;
	}

	private void insertUOM(String orderUnitName, Integer categoryId,
			OrderUnit uom) {
		Integer uomId = orderUnitDao.insertUOM(orderUnitName, categoryId);
		uom.setCategoryId(categoryId);
		uom.setOrderUnitName(orderUnitName);
		uom.setOrderUnitId(uomId);
	}

	private SKUGroup getSkuGroup(Integer sellerId, String grpName,
			Integer categoryId) {
		return skuGroupDao.getSKUgroup(sellerId, grpName, categoryId);
	}

	private void insertSKU(SKU sku) {
		Integer id = skuDao.insertSKU(sku);
		sku.setSkuId(id);
	}

	/**
	 * publish order
	 * 
	 * @param sellerIds
	 * @param buyerIds
	 * @param orderDate
	 * @param orderList
	 */
	private void publishOrders(Integer sellerId, List<Integer> selectedOrderId) {

		for (Integer orderId : selectedOrderId) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orderPublishedBy", sellerId);
			param.put("orderId", orderId);
			orderDao.publishOrder(param);
		}

	}

	/**
	 * finalize a selected date
	 * 
	 * @param pubOrderId
	 * @param sellerId
	 */
	private void finalizeOder(Integer sellerId, List<Integer> pubOrderId) {
		for (Integer orderId : pubOrderId) {
			orderDao.finalizeOrder(orderId, sellerId, null);
			List<OrderItem> orderItem = this.getOrderItems(orderId);
			for (OrderItem _orderItem : orderItem) {
				allocationDao.insertDefaultAllocItems(_orderItem);
			}
		}
	}

	private List<OrderItem> getOrderItems(Integer orderId) {
		return orderItemDao.getOrderItemByOrderId(orderId);
	}

	private void updateAllocationOrdersWS(String deliveryDate,
			List<Integer> buyerIds, List<Integer> sellerIds,
			Map<String, Order> allOrdersMap, User user) {
		// UPDATE ORDERS
		for (Integer buyerId : buyerIds) {
			String sBuyerId = buyerId.toString();
			for (Integer sellerId : sellerIds) {
				String sSellerId = sellerId.toString();
				Order order = allOrdersMap.get(deliveryDate + "_" + sBuyerId
						+ "_" + sSellerId);

				if (order == null)
					continue;

				orderDao.updateSaveAllocation(user.getUserId(), user
						.getUserId(), order.getOrderId());
			}
		}

	}

	public void insertAllocationSKUWS(String deliveryDate,
			List<Integer> buyerIds, List<Integer> sellerIds,
			WSInputDetails[] wsInputDetails, Map<String, Order> allOrdersMap,
			User seller, boolean isAdmin, Map<Integer, User> sellerMap) {

		// INSERT ALLOCATION SKU
		for (WSInputDetails obj : wsInputDetails) {
			if (isAdmin)
				seller = sellerMap.get(obj.getSellerId());

			SKU sku = WebServiceUtil.wstoSKU(obj, seller);

			// Category cat =
			// getCategory(user.getUserId(),obj.getSkuCategoryName());remove
			Category cat = this.getCategory(seller.getUserId(), obj
					.getSkuCategoryName());
			// OrderUnit uom =
			// getOrderUnit(cat.getCategoryId(),obj.getUnitOfOrder());remove
			OrderUnit uom = this.getOrderUnit(cat.getCategoryId(), obj
					.getUnitOfOrder());

			if (uom == null) {
				uom = new OrderUnit();
				// this.insertUOM(obj.getUnitOfOrder(), cat.getCategoryId(),
				// uom);//remove
				this.insertUOM(obj.getUnitOfOrder(), cat.getCategoryId(), uom);
			}

			SKUGroup skuGrp = this.getSkuGroup(seller.getUserId(), obj
					.getSkuGroupName(), cat.getCategoryId());

			if (skuGrp == null) {
				skuGrp = new SKUGroup();
				skuGrp.setCategoryId(cat.getCategoryId());
				skuGrp.setDescription(obj.getSkuGroupName());
				skuGrp.setSellerId(seller.getUserId());
				skuGroupDao.saveSKUGroup(skuGrp);
			}
			sku.setOrderUnit(uom);
			sku.setSkuCategoryId(cat.getCategoryId());
			sku.setSkuGroup(skuGrp);

			this.insertSKU(sku);
			Map<Integer, Object> buyerQuantityMap = WebServiceUtil
					.createBuyerMap(obj.getWsBuyerInformation());
			// save to EON_ORDER_ALLOCATION
			for (Integer buyerId : buyerIds) {
				String sBuyerId = buyerId.toString();

				WSBuyerInformation buyerInfo = new WSBuyerInformation();

				if (buyerQuantityMap.containsKey(buyerId)) {
					buyerInfo = (WSBuyerInformation) buyerQuantityMap
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
					this.insertUpdateOrderItem(order, sku.getSkuId(),
							new BigDecimal(1), buyerInfo.getQuantity(), sku
									.getSkuId(), false, false);
				}
			}
		}

	}

	private void insertUpdateOrderItem(Order order, Integer skuId,
			BigDecimal skuMaxLimit, BigDecimal quantity, Integer origSkuId,
			boolean updateSpecial, boolean insertNewAlloc) {

		Integer orderId = order.getOrderId();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		param.put("skuId", skuId);
		param.put("skuMaxLimit", skuMaxLimit);
		param.put("quantity", quantity);

		if (updateSpecial) {
			if (insertNewAlloc) {
				allocationDao.insertAllocItem(orderId, skuId, quantity,
						skuMaxLimit);
				/* reflected to received sheet if order was alloc published */
				if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy())) {
					receivedSheetDao.insertDefaultReceiveItems(orderId, skuId, null,
							null, quantity);
					// null part is supposed to be userId
				}
			} else {
				// update new sku id and quantity in alloc item, use old sku id
				allocationDao.updateSpecialAllocItem(orderId, skuId, origSkuId,
						quantity);
				/* reflected to received sheet if order was alloc published */
				if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy())) {
					receivedSheetDao.updateSpecialReceivedItem(orderId, skuId,
							origSkuId, quantity, null);
					// null part is supposed to be userId
				}
			}
		} else {
			OrderItem orderItem = allocationDao.getAllocationItem(param);
			if (insertNewAlloc) {
				allocationDao.insertAllocItem(orderId, skuId, quantity,
						skuMaxLimit);
				/* reflected to received sheet if order was alloc published */
				if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy())) {
					receivedSheetDao.insertDefaultReceiveItems(orderId, skuId, null,
							null, quantity);
				}
			} else {
				if (orderItem == null) {
					allocationDao.insertAllocItem(orderId, origSkuId, quantity,
							skuMaxLimit);
					/* reflected to received sheet if order was alloc published */
					if (!NumberUtil.isNullOrZero(order
							.getAllocationPublishedBy())) {
						receivedSheetDao.insertDefaultReceiveItems(orderId,
								origSkuId, null, null, quantity);
					}
				} else {
					allocationDao.updateAllocItem(orderId, origSkuId, null, quantity, null);
					/* reflected to received sheet if order was alloc published */
					if (!NumberUtil.isNullOrZero(order
							.getAllocationPublishedBy())) {
						// ENHANCEMENT START 20121001: Rhoda PROD ISSUE 
//						receivedSheetDao.updateReceived(orderId, origSkuId, null,
//								quantity, null, null, null);
						receivedSheetDao.updateReceivedFromAlloc(orderId, skuId, quantity, null);
						// ENHANCEMENT END 20121001: Rhoda PROD ISSUE
						// null part is isApproved and userId respectively
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param sku
	 * @param buyerId
	 * @param deliveryDate
	 * @return
	 */
	private WSSKU createSKUObject(SKU sku, Integer buyerId, String buyerName, String deliveryDate) {

		List<Category> list = categoryDaos.getAllCategory();
		Map<String,Category> categoryMap = new HashMap<String,Category>();
		
		for (Category cat : list) {
			categoryMap.put(cat.getCategoryId().toString(), cat);
		}
		
		WSSKU skuObj = new WSSKU();
		skuObj.setSkuId(String.valueOf(sku.getSkuId()));
		skuObj.setSkuName(sku.getSkuName());
		skuObj.setSkuCategoryId(String.valueOf(sku.getSkuCategoryId()));
		skuObj.setSkuCategoryName(CategoryUtil.getCategoryDesc(sku.getSkuCategoryId(), categoryMap));
		skuObj.setSkuGroupId(sku.getSkuGroup().getSkuGroupId().toString());
		skuObj.setSkuGroupName(sku.getSkuGroup().getDescription());
		skuObj.setSellerName(sku.getUser().getUserName());
		skuObj.setMarket(sku.getMarket());
		skuObj.setBuyerId(String.valueOf(buyerId));
		skuObj.setBuyerName(buyerName);
		skuObj.setSellerId(String.valueOf(sku.getUser().getUserId()));
		skuObj.setLocation(sku.getLocation());
		skuObj.setGrade(sku.getGrade());
		skuObj.setClazz(sku.getClazz());
		skuObj.setPrice1(sku.getPrice1());
		skuObj.setPrice2(sku.getPrice2());
		skuObj.setPriceWithoutTax(sku.getPriceWithoutTax());
		skuObj.setPriceWithTax(sku.getPriceWithTax());
		skuObj.setPackageQuantity(sku.getPackageQuantity());
		skuObj.setPackageType(sku.getPackageType());
		skuObj.setUnitOfOrder(sku.getOrderUnit().getOrderUnitName());
		skuObj.setSkuMaxLimit(sku.getSkuMaxLimit());
		skuObj.setExternalSkuId(sku.getExternalSkuId());
		try {
			// Too many calls on the DAO 
			// Should refactor this and do a single call 
			Map<String, Object> _param = new HashMap<String, Object>();
			_param.put("deliveryDate", deliveryDate);
			_param.put("skuId", skuObj.getSkuId());
			Map<Integer, OrderItem> orderItemMap = orderSheetDao
					.getOrderItemsMapOfSkuDate(_param);

			BigDecimal rowqty = new BigDecimal(0);
			
			// Only used 1 row of the returned query should refactor
			OrderItem orderItem = orderItemMap.get(buyerId);
			BigDecimal quantity = null;
			if (orderItem == null){
				return null;
			}
			quantity = orderItem.getQuantity();
			
			if (quantity != null) {
				rowqty = rowqty.add(quantity);
			}
			// set quantity to obj
			skuObj.setQty(quantity);
			skuObj.setVisibilityFlag(orderItem.getSosVisFlag());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return skuObj;
	}

}
