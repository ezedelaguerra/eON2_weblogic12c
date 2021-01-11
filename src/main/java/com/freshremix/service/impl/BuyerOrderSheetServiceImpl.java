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
 * Apr 14, 2010		raquino		
 */
package com.freshremix.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.dao.DealingPatternDao;
import com.freshremix.dao.OrderDao;
import com.freshremix.dao.OrderItemDao;
import com.freshremix.dao.OrderSheetDao;
import com.freshremix.dao.SKUBADao;
import com.freshremix.dao.UserDao;
import com.freshremix.dao.UserPreferenceDao;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.Category;
import com.freshremix.model.CompositeKey;
import com.freshremix.model.ConcurrencyResponse;
import com.freshremix.model.EONUserPreference;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Item;
import com.freshremix.model.LockButtonStatus;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.ProfitPreference;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUBA;
import com.freshremix.model.SKUBuyerQtyMap;
import com.freshremix.model.SheetData;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.model.WSBuyerAddOrderSheetDetails;
import com.freshremix.model.WSBuyerAddOrderSheetRequest;
import com.freshremix.model.WSBuyerSKUAdd;
import com.freshremix.model.WSBuyerSKUUpdate;
import com.freshremix.service.BuyerOrderSheetService;
import com.freshremix.service.CategoryService;
import com.freshremix.service.MessageI18NService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.SKUService;
import com.freshremix.service.SheetDataService;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.ui.model.OrderItemUI;
import com.freshremix.ui.model.PageInfo;
import com.freshremix.ui.model.ProfitInfo;
import com.freshremix.ui.model.ProfitInfoList;
import com.freshremix.ui.model.TableParameter;
import com.freshremix.util.ConcurrencyUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.StringUtil;
import com.freshremix.util.TaxUtil;

/**
 * @author raquino
 *
 */
public class BuyerOrderSheetServiceImpl implements BuyerOrderSheetService{
	private static final Logger LOGGER = Logger.getLogger(BuyerOrderSheetServiceImpl.class);

	private static final String ORDERSHEET_CONCURRENT_GET_UNPUBLISHED_FAILED = "ordersheet.concurrent.get.unpublishedFailed";
	private OrderSheetDao orderSheetDao;
	private OrderDao orderDao;
	private SKUService skuService;
	private OrderItemDao orderItemDao;
	private DealingPatternDao dealingPatternDao;
	private UserPreferenceDao userPreferenceDao;
	private UserDao usersInfoDaos;
	private SKUBADao skuBADao;
	private UsersInformationService userInfoService;
	private SheetDataService sheetDataService;
	private CategoryService categoryService;
	private MessageI18NService messageI18NService;
	private OrderSheetService orderSheetService;

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}

	public void setOrderSheetDao(OrderSheetDao orderSheetDao) {
		this.orderSheetDao = orderSheetDao;
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
	
	public void setDealingPatternDao(DealingPatternDao dealingPatternDao) {
		this.dealingPatternDao = dealingPatternDao;
	}

	public void setUserPreferenceDao(UserPreferenceDao userPreferenceDao) {
		this.userPreferenceDao = userPreferenceDao;
	}

	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}
	
	public void setSkuBADao(SKUBADao skuBADao) {
		this.skuBADao = skuBADao;
	}

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	public void setSheetDataService(SheetDataService sheetDataService) {
		this.sheetDataService = sheetDataService;
	}
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setMessageI18NService(MessageI18NService messageI18NService) {
		this.messageI18NService = messageI18NService;
	}



	/* (non-Javadoc)
	 * @see com.freshremix.service.BuyerOrderSheetService#getPublishedOrders(java.util.List, java.util.List, java.util.List)
	 */
	/**
	 * This is for buyer/buyer admin to get list of published orders from seller order sheet
	 *
	 * @param list of buyer ids, list of deliver dates, list of seller ids
	 * @return List of Orders
	 */	
	@Override
	public List<Order> getPublishedOrders(List<Integer> buyerIds,
			List<String> deliveryDates, List<Integer> sellerIds, Integer isBuyer, String enableBAPublish) {
		List<Order> publishedOrders= orderDao.getPublishedOrders(buyerIds, deliveryDates, sellerIds, isBuyer, enableBAPublish);
		
		return publishedOrders;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.BuyerOrderSheetService#saveQuantityByBuyer(com.freshremix.ui.model.OrderForm, com.freshremix.ui.model.OrderDetails, java.util.List)
	 */
	/**
	 * This is for buyer to save changed quantity
	 *
	 * @param OrderForm - list of updated orders, OrderDetails, and Map of all order list
	 * @return 
	 * @throws ServiceException 
	 */	
	@Override
	public void saveQuantityByBuyer(OrderForm orderForm, 
			OrderDetails orderDetails, Map<String, Order> allOrdersMap, List<OrderItemUI> oiUIforUpdate) throws ServiceException {
		
		List<Integer> sellerIds = orderDetails.getSellerIds();
		List<OrderItemUI> orderItemList = oiUIforUpdate;
		Integer userId = orderDetails.getUser().getUserId(); 
		List<Integer> orderIds = new ArrayList<Integer>();
		if (orderDetails.isAllDatesView()) {

			List<String> deliveryDates = DateFormatter.getDateList(orderDetails.getStartDate(), orderDetails.getEndDate());
			Integer buyerId = orderDetails.getDatesViewBuyerID();
			if(CollectionUtils.isNotEmpty(orderItemList)){

			for (OrderItemUI oi : orderItemList) {
//				System.out.println("Update by deliveryDate");
				Map<String, BigDecimal> qtyMap = oi.getQtyMap();
				for (String deliveryDate : deliveryDates) {
					for (Integer sellerId : sellerIds) {
						String sSellerId = sellerId.toString();
						Order order = allOrdersMap.get(deliveryDate + "_" + buyerId.toString() + "_" + sSellerId);
						
						if (order == null) continue;
						BigDecimal quantity = null;
						if (qtyMap.get("Q_" + deliveryDate) != null)
							quantity = qtyMap.get("Q_" + deliveryDate);
						Integer orderId = order.getOrderId();
						Integer skuId = oi.getSkuId();
						//orderDao.updateOrderQtyBySkuIdAndOrderId(quantity, userId, skuId, orderId, null, null);
						orderDao.updateOrderQtyAllDatesBySKUBA(quantity, userId, skuId, oi.getSkuBaId(), orderId);
						//orderDao.updateOrderByOrderId(userId, orderId);
						orderIds.add(orderId);
					}
				}
			}
		}
		}else{

			List<Integer> buyerIds = orderDetails.getBuyerIds();
			String deliveryDate = orderDetails.getDeliveryDate();
			if(CollectionUtils.isNotEmpty(orderItemList)){

				for (OrderItemUI oi : orderItemList) {
	//				System.out.println("Update by buyerId");
					Map<String, BigDecimal> qtyMap = oi.getQtyMap();
					for (Integer buyerId : buyerIds) {
						String sBuyerId = buyerId.toString();
						for (Integer sellerId : sellerIds) {
							String sSellerId = sellerId.toString();
							Order order = allOrdersMap.get(deliveryDate + "_" + sBuyerId + "_" + sSellerId);
							
							if (order == null) continue;
							BigDecimal quantity = null;
							if (qtyMap.get("Q_" + buyerId.toString())!= null)
								quantity = qtyMap.get("Q_" + buyerId.toString());
							Integer orderId = order.getOrderId();
							Integer skuId = oi.getSkuId();
							orderDao.updateOrderQtyBySkuIdAndOrderId(quantity, userId, skuId, orderId, null, null);	
							//orderDao.updateOrderByOrderId(userId, orderId);
							orderIds.add(orderId);
						}
					}
				}
			}
		}
		if(CollectionUtils.isNotEmpty(orderIds)){
			orderDao.updateOrderByOrderIds(userId, orderIds);
		}
			
		
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.BuyerOrderSheetService#updateLockOrders(java.util.List, java.lang.Integer)
	 */
	/**
	 * This is for buyer/buyer admin to get published orders from seller order sheet
	 *
	 * @param list of buyer ids, list of deliver dates, list of seller ids
	 * @return List of Orders
	 * @throws ServiceException 
	 */	
	@Override
	public void updateLockOrders(List<Integer> orderIds, Integer userId) throws ServiceException {
		orderDao.lockOrders(orderIds, userId);
		
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.BuyerOrderSheetService#updateUnlockOrders(java.util.List, java.lang.Integer)
	 */
	/**
	 * This is for buyer/buyer admin to get published orders from seller order sheet
	 *
	 * @param list of buyer ids, list of deliver dates, list of seller ids
	 * @return List of Orders
	 * @throws ServiceException 
	 */	
	@Override
	public void updateUnlockOrders(List<Integer> orderIds, Integer userId) throws ServiceException {
		orderDao.unlockOrders(orderIds, userId);
		
	}

	/*
	 * Extracted from OrderSheetUpdateController.  For common use of web and webservice
	 * (non-Javadoc)
	 * @see com.freshremix.service.BuyerOrderSheetService#processSave(com.freshremix.ui.model.OrderForm, com.freshremix.ui.model.OrderDetails, java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public ConcurrencyResponse saveProcess(OrderForm orderForm,
			OrderDetails od, List<Order> orderListForUpdate,Map<CompositeKey<Integer>, SKUBA> skuBAMapFromDB,
			Map<CompositeKey<Integer>, SKUBA> skuBAMapFromSession) throws ServiceException {

		
		if (CollectionUtils.isNotEmpty(orderListForUpdate)) {

			ConcurrencyResponse conResponse = ConcurrencyUtil.isBuyerOrderSheetModified(
					orderForm.getUpdatedOrders(), od, skuBAMapFromDB,
					skuBAMapFromSession,orderSheetDao, messageI18NService);
			//check for visibility turned off and there is quantity
			ConcurrencyResponse cResponse =  ConcurrencyUtil.concurrentSaveOrderVisibility(conResponse.getOiUIforUpdate(), od,
					orderListForUpdate, orderSheetDao, true,messageI18NService);
			conResponse.setOiUIforUpdate(cResponse.getOiUIforUpdate());
			String concurrencyMsg = StringUtil.nullToBlank(conResponse.getConcurrencyMsg()) + StringUtil.nullToBlank(cResponse.getConcurrencyMsg()); 
			conResponse.setConcurrencyMsg(concurrencyMsg);
			ConcurrencyResponse conResponse2 = ConcurrencyUtil.validateMaxQuantity(conResponse.getOiUIforUpdate(), od,
					orderListForUpdate, orderSheetDao, true,messageI18NService);
			conResponse.setOiUIforUpdate(conResponse2.getOiUIforUpdate());
			concurrencyMsg = StringUtil.nullToBlank(conResponse.getConcurrencyMsg()) + StringUtil.nullToBlank(conResponse2.getConcurrencyMsg()); 
			conResponse.setConcurrencyMsg(concurrencyMsg);
			if (RolesUtil.isUserRoleBuyer(od.getUser())) {
				this.saveQuantityByBuyer(orderForm, od, OrderSheetUtil
						.convertToOrderMap(orderListForUpdate),conResponse.getOiUIforUpdate() );
			}

			if (RolesUtil.isUserRoleBuyerAdmin(od.getUser())) {
				this.saveOrderByBuyerAdmin(orderForm, od, OrderSheetUtil
						.convertToOrderMap(orderListForUpdate),conResponse.getOiUIforUpdate() );
			}
			return conResponse;
		}
		return new ConcurrencyResponse();
	}	

	/* (non-Javadoc)
	 * @see com.freshremix.service.BuyerOrderSheetService#saveOrderByBuyerAdmin(com.freshremix.ui.model.OrderForm, com.freshremix.ui.model.OrderDetails, java.util.Map)
	 */
	/**
	 * This is for buyer admin to save added skus, quantity and visiility
	 *
	 * @param OrderForm - list of updated orders, OrderDetails, and Map of all order list
	 * @return 
	 * @throws ServiceException 
	 */	
	@Override
	public void saveOrderByBuyerAdmin(OrderForm orderForm,
			OrderDetails orderDetails, Map<String, Order> allOrdersMap, List<OrderItemUI> oiUIforUpdate) throws ServiceException {
		
		//List<Integer> sellerIds = orderDetails.getSellerIds();
		List<OrderItemUI> insertedItemList = orderForm.getInsertedOrders();
		List<OrderItemUI> updatedItemList = oiUIforUpdate;
		List<OrderItemUI> deletedItemList = orderForm.getDeletedOrders();
		Integer userId = orderDetails.getUser().getUserId(); 
		List<String> deliveryDates = DateFormatter.getDateList(orderDetails.getStartDate(), orderDetails.getEndDate());
		List<Integer> buyerIds = orderDetails.getBuyerIds();
		List<Integer> orderIds = new ArrayList<Integer>();

		//Insert - add proposed skus 
		if(CollectionUtils.isNotEmpty(insertedItemList)){

			for (OrderItemUI oi : insertedItemList) {
				
				User userSeller = usersInfoDaos.getUserById(oi.getSellerId());
				oi.setCompany(userSeller.getCompany());
				SKU sku = OrderSheetUtil.toSKU(oi, orderDetails, userId);
				sku.setOrigSkuId(null);
				skuService.insertSKU(sku);
				
				SKUBA skuBA = new SKUBA();
				skuBA.setSkuId(sku.getSkuId());
				skuBA.setPurchasePrice(oi.getPurchasePrice());
				skuBA.setSellingPrice(oi.getSellingPrice());
				OrderUnit uom = new OrderUnit();
				uom.setOrderUnitId(oi.getSellingUom());
				skuBA.setSellingUom(uom);
				skuBA.setSkuComment(oi.getSkuComments());
				skuBA.setSkuBAId(skuBADao.insertSKUBA(skuBA));
				
				Map<String, BigDecimal> qtyMap = oi.getQtyMap();
				Map<String, String> visMap = oi.getVisMap();
				for (String deliveryDate : deliveryDates) {
					for (Integer buyerId : buyerIds) {
						String sSellerId = oi.getSellerId().toString();
						Order order = new Order();
						order = allOrdersMap.get(deliveryDate + "_" + buyerId.toString() + "_" + sSellerId);
						
						if (order == null) continue;
						if (!NumberUtil.isNullOrZero(order.getOrderLockedBy())) continue;
						if (!NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) continue;
						
						BigDecimal quantity = null;
						String quantityKey = orderDetails.isAllDatesView() == true ? deliveryDate : buyerId.toString();
						if (qtyMap.get("Q_" + quantityKey) != null)
							quantity = qtyMap.get("Q_" + quantityKey);
						String isVisible = visMap.get("V_" + quantityKey);
						Integer orderId = order.getOrderId();
						Integer skuId = sku.getSkuId();
						Map<String, Object> param = new HashMap<String,Object>();
						param.put("orderId", orderId);
						param.put("skuId", skuId);
						param.put("quantity", quantity);
						param.put("baosVisFlag", isVisible);
						param.put("skuBAId", skuBA.getSkuBAId());
						orderItemDao.insertOrderItem(param); 
					}
				}
			}
		}
		//Update
		if(CollectionUtils.isNotEmpty(updatedItemList)){

			for (OrderItemUI oi : updatedItemList) {
				Map<String, BigDecimal> qtyMap = oi.getQtyMap();
				Map<String, String> visMap = oi.getVisMap();
				
				SKUBA skuBA = new SKUBA();
				boolean hasUpdatedBAFields = this.hasUpdatedBAFields(oi, skuBA);
				Integer oldSkuBaId = oi.getSkuBaId();
				if (hasUpdatedBAFields) {
					skuBA.setSkuId(oi.getSkuId());
					skuBA.setPurchasePrice(oi.getPurchasePrice());
					skuBA.setSellingPrice(oi.getSellingPrice());
					OrderUnit uom = new OrderUnit();
					uom.setOrderUnitId(oi.getSellingUom());
					skuBA.setSellingUom(uom);
					skuBA.setSkuComment(oi.getSkuComments());
					skuBA.setSkuBAId(skuBADao.insertSKUBA(skuBA));
				}
				
				for (String deliveryDate : deliveryDates) {
					for (Integer buyerId : buyerIds) {
						
						String sSellerId = oi.getSellerId().toString();
						Order order = allOrdersMap.get(deliveryDate + "_" + buyerId.toString() + "_" + sSellerId);
	
						if (order == null) continue;
						if (!NumberUtil.isNullOrZero(order.getOrderLockedBy())) continue;
						if (!NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) continue;
						
						BigDecimal quantity = null;
						
						Integer orderId = order.getOrderId();
						Integer skuId = oi.getSkuId();
						
						String quantityKey = orderDetails.isAllDatesView() == true ? deliveryDate : buyerId.toString();
						if (qtyMap.get("Q_" + quantityKey) != null)
							quantity = qtyMap.get("Q_" + quantityKey);
						String isVisible = visMap.get("V_" + quantityKey);
						int updatedCount = 0;
						if ((orderDetails.isAllDatesView() && orderDetails.getDatesViewBuyerID().equals(buyerId)) || 
								(!orderDetails.isAllDatesView() && orderDetails.getDeliveryDate().equals(deliveryDate))) {
							//Update skuba 4 new cols and quantity/visibility for selected buyer or date only	
							if (hasUpdatedBAFields){
								//update with new skuba
								updatedCount = orderDao.updateOrderItemQtySKUBA2(quantity, userId, skuId, skuBA.getSkuBAId(), oldSkuBaId, orderId,  isVisible);
							}else{
								//update quantity/visibility only
								//orderDao.updateOrderQtyBySkuIdAndOrderId(quantity, userId, skuId, orderId, null, isVisible);
								updatedCount = orderDao.updateOrderQtyBySkuIdOrderIdAndSkuBaId(quantity, userId, skuId, skuBA.getSkuBAId(), orderId, null, isVisible);
							}
						}else{
							//Update skuba 4 new cols for other buyers or dates
							if (hasUpdatedBAFields){
								updatedCount = orderDao.updateOrderItemSKUBA(userId, skuId, skuBA.getSkuBAId(), oldSkuBaId, orderId);
							}
						}
						if (updatedCount > 0) {
						orderIds.add(orderId);
						}
						//orderDao.updateOrderByOrderId(userId, orderId);	
					}
				}
				
			}
		}
		//Delete
		if(CollectionUtils.isNotEmpty(deletedItemList)){

			for (OrderItemUI oi : deletedItemList) {
				for (String deliveryDate : deliveryDates) {
					for (Integer buyerId : buyerIds) {
						String sSellerId = oi.getSellerId().toString();
						Order order = allOrdersMap.get(deliveryDate + "_" + buyerId.toString() + "_" + sSellerId);
						
						if (order == null) continue;
						Integer orderId = order.getOrderId();
						Integer skuId = oi.getSkuId();
						orderItemDao.deleteOrderItem(orderId, skuId);
						orderIds.add(orderId);
						//orderDao.updateOrderByOrderId(userId, orderId);
					}
				}
			}
		}
		if(CollectionUtils.isNotEmpty(orderIds)){
			orderDao.updateOrderByOrderIds(userId, orderIds);	
		}
	} 

	/* (non-Javadoc)
	 * @see com.freshremix.service.BuyerOrderSheetService#computeTotalPriceOnDisplay(java.util.List)
	 */
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

	/* (non-Javadoc)
	 * @see com.freshremix.service.BuyerOrderSheetService#getSelectedOrders(java.util.Map, com.freshremix.model.OrderSheetParam)
	 */
	@Override
	public List<Order> getSelectedOrders(Map<String, Order> allOrdersMap,
			OrderSheetParam osp) {
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
					if (thisOrder != null) {
						selectedOrders.add(thisOrder);
					}	
				}
			}
		}
		
		return selectedOrders;
		
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.BuyerOrderSheetService#combineOrders(java.util.List)
	 */
	@Override
	public Order combineOrders(List<Order> orders) {
		Order order = new Order();
		order.setOrderSavedBy(99999);
		order.setOrderPublishedBy(99999);
		order.setOrderLockedBy(99999);
		order.setOrderUnlockedBy(99999);
		order.setOrderFinalizedBy(99999);
		order.setOrderUnfinalizedBy(99999);
		order.setAllocationPublishedBy(99999);
		order.setOrderPublishedByBA(99999);
		for (Order _order : orders) {
			if (NumberUtil.isNullOrZero(_order.getOrderSavedBy()))
				order.setOrderSavedBy(null);
			if (NumberUtil.isNullOrZero(_order.getOrderPublishedBy()))
				order.setOrderPublishedBy(null);
			if (NumberUtil.isNullOrZero(_order.getOrderLockedBy()) &&
					NumberUtil.isNullOrZero(_order.getOrderFinalizedBy()))
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
		}
		return order;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.BuyerOrderSheetService#updatePublishOrder(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updatePublishOrderByBA(Integer userId, Integer orderId) {
		orderDao.updatePublishOrder(userId, orderId);
		
	}
	@Override
	public void updatePublishOrderByBA(User user, List<Order> savedOrders) throws Exception  {
		
		
		LOGGER.info("Update Publish Order...start");

		//save Orders as unfinalized
		List<Integer> ordersForPublishIdList = OrderSheetUtil.getOrderIdList(savedOrders);

		LOGGER.info("Orders for publish: count:"+ordersForPublishIdList.size());
		if (CollectionUtils.isNotEmpty(ordersForPublishIdList)) {
			orderDao.updatePublishOrderBatch(ordersForPublishIdList, user.getUserId());
			
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
			
			String fromAddress = StringUtil.isNullOrEmpty(user.getPcEmail()) == false ? user.getPcEmail() : user.getMobileEmail();
			//send notifications for unfinalized Orders
			if (CollectionUtils.isNotEmpty(ordersForPublishIdList)) {
				LOGGER.info("Sending consolidated email for Finalized Order Sheet");
				Map<String, Set<String>> mapOfEmailToOrderDates = OrderSheetUtil.consolidateOrdersForEmail(
						buyerMap, savedOrders,"buyer");
				for (Map.Entry<String, Set<String>> emailToOrderDates : mapOfEmailToOrderDates.entrySet()) {
					orderSheetService.sendConsolidatedMailNotification(new ArrayList<String>(
							emailToOrderDates.getValue()), "Unfinalize", user
							.getUserName(), fromAddress,
							new String[] { emailToOrderDates.getKey() });
				}
			}
		}
		
		
		
		

		LOGGER.info("Update Publish Order...end");
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.BuyerOrderSheetService#isBuyerPublished(com.freshremix.model.User)
	 */
	@Override
	public boolean isBuyerPublished(User user) {
		boolean status = false;
		List<Integer> adminList =dealingPatternDao.getAdminUsersOfMember(user.getUserId(), 
				DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER);
		
		EONUserPreference pref = new EONUserPreference();
		for(Integer id : adminList ){
			pref = userPreferenceDao.getUserPreference(id);
			if(pref != null){
				if(pref.getEnableBAPublish() != null && pref.getEnableBAPublish().equals("1")){
					status = true;
					break;
				}
			}
		}
		return status;
	}
	
	@Override
	public Map<String, Map<String, Map<Integer, OrderItem>>> getBuyerOrderItemsBulk(List<Integer> orderIds,
			List<Integer> skuIds) {
		
		//List<OrderItem> itemList = orderSheetDao.getBuyerOrderItemsBulk(orderIds, skuIds);
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
			
			orderItemBulk.addAll(orderSheetDao.getBuyerOrderItemsBulk(orderIds, thisSkuIds, false));
		}
		
		return OrderSheetUtil.convertToBuyerOrderItemsBulkMap(orderItemBulk);
		
	}
	
	
	
	@Override
	public List<SKUBA> getPublishedSKUBA(List<Integer> orderId, List<Integer> sellerId, 
			List<Integer> buyerId, List<String> deliveryDate, Integer categoryId, 
			Integer isBuyerUser, String hasQty) {

		if (hasQty != null && hasQty.equals("0")) hasQty = null;
		return orderDao.getPublishedSKUBA(orderId, sellerId, buyerId, deliveryDate,
				categoryId, isBuyerUser, hasQty);
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

	/*
	 * This method is used to get either;
	 * 1. TOTALS of ProfitInfo if given parameter is only one category 
	 *    and one day for all buyers view or multiple days for all dates view
	 * 2. GRAND TOTALS of ProfitInfo if given parameter is more than one categories
	 *    and one day for all buyers view or multiple days for all dates view
	 */
	@Override
	public ProfitInfo getBuyerTotalPrices(User user, List<String> deliveryDate, List<Integer> sellerId, 
			List<Integer> buyerId,Map<String, List<Integer>> mapOfMembersByDateList ,
			List<Integer> categoryId, Double tax, String priceWithTaxOpt) {

		Set<Integer> categorySet = new TreeSet<Integer>(categoryId);
		
		ProfitInfo total = new ProfitInfo();
		for (String _deliveryDate : deliveryDate) {
			List<Integer> validBuyersForDate = new ArrayList<Integer>();
			
			if (RolesUtil.isUserRoleBuyerAdmin(user)){

				List<Integer>  tmpvalidBuyerMemberList = mapOfMembersByDateList.get(_deliveryDate);
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
				List<ProfitInfo> list = orderSheetDao.getBuyerPricesPerSKU(_deliveryDate, 
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
	
	
	

	
	
	public Map<String, Object> getOrders(OrderSheetParam osParam, User user,
			TableParameter tableParam, List<Integer> selectedOrderIdList,
			List<Order> allOrders,Map<String, List<Integer>> mapOfMembersByDate) throws Exception,
			JSONException {

		List<Map<String, Object>> skuOrderList = this.loadOrderItems(selectedOrderIdList, user, osParam, tableParam.getPageInfo(),mapOfMembersByDate);

		// Total Prices
		List<Integer> categoryList = new ArrayList<Integer>();
		List<String> deliveryDates = null; 
		List<Integer> buyerId = null;
		List<Category> categories = osParam.getCategoryList();

		if (categories == null || categories.size() == 0) {
			categoryList.add(osParam.getCategoryId());
		} else {
			for (Category cat : categories) {
				categoryList.add(cat.getCategoryId());
			}
		}
		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());

		if (osParam.isAllDatesView()) {
			deliveryDates = new ArrayList<String>(dateList);
			buyerId = new ArrayList<Integer>();
			buyerId.add(Integer.parseInt(osParam.getDatesViewBuyerID()));
		}
		else {
			deliveryDates = new ArrayList<String>();
			deliveryDates.add(osParam.getSelectedDate());
			buyerId = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		}
		List<Integer> selectedSellerList = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		
		ProfitInfo pi = this.getBuyerTotalPrices(user, deliveryDates, selectedSellerList, buyerId, mapOfMembersByDate, categoryList, TaxUtil.getTAX_RATE().doubleValue(), user.getPreference().getProfitPreference().getPriceTaxOption());
		
		
		// Grand Totals
		List<UsersCategory> allCategory = categoryService.getCategoryList(user, osParam);
		List<Integer> allCategoryIds = new ArrayList<Integer>();
		
		
		for (UsersCategory _cat : allCategory) {
			allCategoryIds.add(_cat.getCategoryId());
		}
		
	
		
		ProfitInfo pi2 = this.getBuyerTotalPrices(user,
				dateList, 
				OrderSheetUtil.toList(osParam.getSelectedSellerID()),
				OrderSheetUtil.toList(osParam.getSelectedBuyerID()),mapOfMembersByDate,
				allCategoryIds,
				TaxUtil.getTAX_RATE().doubleValue(),
				user.getPreference().getProfitPreference().getPriceTaxOption());		
		
		
		String confirmMsg = "";
		if (hasNoPublishedOrder(allOrders)) {
			confirmMsg = messageI18NService.getPropertyMessage("ordersheet.buyer.noSkuOrderList");
		}
			
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		List<Order> selectedOrders = this.getSelectedOrders(allOrdersMap, osParam);
		JSONObject buttonFlags = getButtonFlags(selectedOrders);
		
		
		//not used
		//SessionHelper.getAttribute(request, SessionParamConstants.SELECTED_ORDERS_PARAM);
		
		//always returns null
		//Set<String> sellernameSet = extracted(request);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		returnMap.put("skuObjMap", extractSKUObjMap(skuOrderList));
		returnMap.put("data", skuOrderList);
		returnMap.put("buttonFlags", buttonFlags.toString());
		returnMap.put("confirmMsg", confirmMsg);
		returnMap.put("totals", pi);
		returnMap.put("grandTotals", pi2);
		returnMap.put("profitVisibility", user.getPreference().getProfitPreference());
		return returnMap;
	}
	
	private Map<CompositeKey<Integer>, SKUBA> extractSKUObjMap(List<Map<String, Object>> skuOrderList) {
		Map<CompositeKey<Integer>, SKUBA> skuObjMap = new HashMap<CompositeKey<Integer>, SKUBA>();
		if (CollectionUtils.isEmpty(skuOrderList)) {
			return skuObjMap;
		}
		
		for (Map<String, Object> map : skuOrderList) {
			SKUBA skuBa = (SKUBA)map.get("skuObj");
			if (skuBa != null) {
				CompositeKey<Integer> compositeKey = new CompositeKey<Integer>(
						skuBa.getSkuId(), skuBa.getSkuBAId());
				skuObjMap.put(compositeKey, skuBa);
			}
		}
		return skuObjMap;
	}

	private List<Map<String, Object>> loadOrderItems(List<Integer> selectedOrders, User user,
			OrderSheetParam osParam, PageInfo pageInfo, Map<String, List<Integer>> mapOfMembersByDate) throws Exception {
		
		//Consider BAOS visibility condition for Buyer only
		Integer isUserBuyer = null;
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)){
			isUserBuyer = user.getUserId();
		}
		
		String deliveryDate = osParam.getSelectedDate();
		List<Integer> companyBuyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		
		boolean isAllDatesView = osParam.isAllDatesView();
		String startDate = osParam.getStartDate();
		String endDate = osParam.getEndDate();
		Integer datesViewBuyerId = Integer.valueOf((String) osParam.getDatesViewBuyerID());
		List<Integer> selectedSellerList = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		List<Integer> selectedBuyerList = OrderSheetUtil.toList(osParam.getSelectedBuyerID());

		if (CollectionUtils.isEmpty(selectedOrders)) {
			selectedOrders = Arrays.asList(Integer.valueOf(0));
		}
		
		List<Integer> categoryList = Arrays.asList(osParam.getCategoryId());
		SheetData data = new SheetData();
		List<String> deliveryDates = new ArrayList<String>();

		
		
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
			List<Integer> memberList = new ArrayList<Integer>();
			
			if (RolesUtil.isUserRoleBuyerAdmin(user)) {
				
				List<Integer>  tmpvalidBuyerMemberList = mapOfMembersByDate.get(deliveryDate);
				for (Integer integer : selectedBuyerList) {
					if(tmpvalidBuyerMemberList.contains(integer)){
						memberList.add(integer);
					}
				}
				
			} else {
				memberList = selectedBuyerList;
			}
			data = sheetDataService.loadSheetData(
					user, deliveryDate, deliveryDate, 
					selectedSellerList, 
					memberList, 
					categoryList, 
					osParam.getSheetTypeId(), false, false, selectedOrders);
			
		}
		
		Map<String, Map<String, Map<Integer, Item>>> buyerOrderItemMap = 
				data.getSkuDateBuyOrderItemMap();
		List<?> allSkuObjs = data.getSkuList();
		List<SKUBA> skuObjs = new ArrayList<SKUBA>();
		
		int rowIdxStart = pageInfo.getStartRowNum() - 1;
		int rowIdxEnd = rowIdxStart + pageInfo.getPageSize() - 1;
		for (int i=rowIdxStart; i<=rowIdxEnd; i++) {
			if (i == allSkuObjs.size()) {
				break;
			}
			/*
			 * this condition is only possible if some of the original order
			 * list is unpublished. Throw an exception.
			 */
			if (i>allSkuObjs.size() || i>buyerOrderItemMap.size()){
				throw new ServiceException(ORDERSHEET_CONCURRENT_GET_UNPUBLISHED_FAILED);
			}

			if (allSkuObjs.get(i) instanceof SKUBA){
				skuObjs.add((SKUBA)allSkuObjs.get(i));
			}
		}
		pageInfo.setTotalRowNum(allSkuObjs.size());
		
		
	
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		
		//Not used
		//Set<String> sellerNameSet = new TreeSet<String>();
		

		Map<Integer, User> proposedUserCache = new HashMap<Integer, User>();
		for (SKUBA skuObj : skuObjs) {
			JSONObject json = new JSONObject();

			Map<String, Object> skuOrderMap = new HashMap<String, Object>();
			skuOrderMap.put("skuObj", skuObj);
			skuOrderMap.put("skuId", skuObj.getSkuId());
			skuOrderMap.put("skuBaId", skuObj.getSkuBAId());
			skuOrderMap.put("sellerId", skuObj.getUser().getUserId());

			if (skuObj.getProposedBy() != null) {
				json.put("proposed", "1");
			} else {
				json.put("sku", "1");
			}

			if(skuObj.getProposedBy() != null && isUserBuyer!= null) {
				User proposedByUser = null;
				if (!proposedUserCache.containsKey(skuObj.getProposedBy().getUserId())) {
					proposedByUser = userInfoService.getUserAndCompanyById(skuObj.getProposedBy().getUserId());
					proposedUserCache.put(skuObj.getProposedBy().getUserId(), proposedByUser);
				} else {
					proposedByUser = proposedUserCache.get(skuObj.getProposedBy().getUserId());
				}
				
				skuOrderMap.put("companyname", proposedByUser.getCompany().getShortName());
				skuOrderMap.put("companyid", proposedByUser.getCompany().getCompanyId());
				
				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)){
					skuOrderMap.put("sellername", proposedByUser.getShortName());
				} else {
					skuOrderMap.put("sellername", proposedByUser.getUserId());
				}
				
				
			} else {
				skuOrderMap.put("companyname", skuObj.getUser().getCompany().getShortName());
				skuOrderMap.put("companyid", skuObj.getUser().getCompany().getCompanyId());

				if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)){
					skuOrderMap.put("sellername", skuObj.getUser().getShortName());
				} else {
					skuOrderMap.put("sellername", skuObj.getUser().getUserId());
				}
			}
			/* start used for  webservices*/
			skuOrderMap.put("skuGroupName", skuObj.getSkuGroup().getDescription());
			skuOrderMap.put("skuSellerName", skuObj.getUser().getShortName());
			skuOrderMap.put("skuCategoryId", skuObj.getSkuCategoryId());
			skuOrderMap.put("unitorderName", skuObj.getOrderUnit().getOrderUnitName());
			/* end used for  webservices*/

			skuOrderMap.put("groupname", skuObj.getSkuGroup().getSkuGroupId());
			skuOrderMap.put("marketname", StringUtil.nullToBlank(skuObj.getMarket()));
			skuOrderMap.put("column01", 	StringUtil.nullToBlank(skuObj.getColumn01()));
			skuOrderMap.put("column02", 	StringUtil.nullToBlank(skuObj.getColumn02()));
			skuOrderMap.put("column03", 	StringUtil.nullToBlank(skuObj.getColumn03()));
			skuOrderMap.put("column04", 	StringUtil.nullToBlank(skuObj.getColumn04()));
			skuOrderMap.put("column05", 	StringUtil.nullToBlank(skuObj.getColumn05()));
			skuOrderMap.put("skuname", StringUtil.nullToBlank(skuObj.getSkuName()));
			skuOrderMap.put("home", StringUtil.nullToBlank(skuObj.getLocation()));
			skuOrderMap.put("grade", StringUtil.nullToBlank(skuObj.getGrade()));
			skuOrderMap.put("clazzname", StringUtil.nullToBlank(skuObj.getClazz()));
			skuOrderMap.put("price1", NumberUtil.nullToZero((BigDecimal)skuObj.getPrice1()));
			skuOrderMap.put("price2", NumberUtil.nullToZero((BigDecimal)skuObj.getPrice2()));
			skuOrderMap.put("pricewotax", NumberUtil.nullToZero((BigDecimal)skuObj.getPriceWithoutTax()));
			skuOrderMap.put("pricewtax", skuObj.getPriceWithTax());
			skuOrderMap.put("packageqty", StringUtil.nullToBlank(skuObj.getPackageQuantity()));
			skuOrderMap.put("packagetype", StringUtil.nullToBlank(skuObj.getPackageType()));
			skuOrderMap.put("unitorder", skuObj.getOrderUnit().getOrderUnitId());
			skuOrderMap.put("skumaxlimit", StringUtil.nullToBlank((BigDecimal)skuObj.getSkuMaxLimit()));
			skuOrderMap.put("B_purchasePrice", StringUtil.nullToBlank((BigDecimal)skuObj.getPurchasePrice()));
			skuOrderMap.put("B_sellingPrice", StringUtil.nullToBlank((BigDecimal)skuObj.getSellingPrice()));
			skuOrderMap.put("B_sellingUom", skuObj.getSellingUom() == null ? 0 : NumberUtil.nullToZero(skuObj.getSellingUom().getOrderUnitId()));
			OrderUnit sellingUomObj = getSellingUomObj(data, skuObj.getSellingUom());
			skuOrderMap.put("B_sellingUomName", sellingUomObj == null ? "" : StringUtil.nullToBlank(sellingUomObj.getOrderUnitName()));
			skuOrderMap.put("B_skuComment", StringUtil.nullToBlank(skuObj.getSkuComment()));
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
			
			if (isAllDatesView) {
				this.prepareOrderAllDates(skuOrderMap, deliveryDates,
						datesViewBuyerId, skuObj, isUserBuyer, json,
						buyerOrderItemMap, user, mapOfMembersByDate);
				

			} else {
				this.prepareOrderAllBuyers(skuOrderMap, companyBuyerIds,
						deliveryDate, skuObj, isUserBuyer, json,
						buyerOrderItemMap);
			}
			// Profit and Profit Percentage computations
			BigDecimal rowQty = (BigDecimal)skuOrderMap.get("rowqty");
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
			
			ProfitInfo profitInfo = OrderSheetUtil.computeProfitInfo(
						skuObj.getPriceWithoutTax(),
						skuObj.getPriceWithTax(),
						skuObj.getSellingPrice(),
						skuObj.getPackageQuantity(),
						rowQty,
						user.getPreference().getProfitPreference().getPriceTaxOption(),
						withPackageQuantity);	
			skuOrderMap.put("B_profitPercentage", profitInfo.getProfitPercentage());
			
			this.prepareProfitInfoColumn(skuOrderMap, profitInfo, 
					user.getPreference().getProfitPreference().getPriceTaxOption());

			lockSellerName(user, json);
			skuOrderMap.put("lockflag", json.toString());
			
			skuOrderMaps.add(skuOrderMap);
			//not used
			//sellerNameSet.add(skuObj.getUser().getShortName());
		}
		
		//Not being used
		//SessionHelper.setAttribute(request, SessionParamConstants.SKU_OBJ_MAP_PARAM, skuObjMap);
		
		return skuOrderMaps;
	}

	private OrderUnit getSellingUomObj(SheetData data, OrderUnit sellingUOM) {
		if (data == null){
			return null;
		}
		
		if (sellingUOM == null){
			return null;
		}
		
		Map<Integer, OrderUnit> sellingUomMap = data.getSellingUomMap();
		if (MapUtils.isEmpty(sellingUomMap)) {
			return null;
		}
		
		return sellingUomMap.get(sellingUOM.getOrderUnitId());
		
	}

	private void lockSellerName(User user, JSONObject json) {
		try {
			json.put("sellername", "1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private JSONObject getButtonFlags(List<Order> selectedOrders) throws JSONException {
		
		Order order = this.combineOrders(selectedOrders);
		JSONObject flags = new JSONObject();
		
		// enable/disable
		if (selectedOrders.size() == 0) {
			flags.put("btnSave", 1);
			flags.put("btnLock", 1);
			flags.put("btnUnlock", 1);
		} else {
			// first entry
			if (NumberUtil.isNullOrZero(order.getOrderSavedBy())) {
				flags.put("btnSave", 0);
				flags.put("btnLock", 0);
				flags.put("btnPublish", 0);
			}
			// saved
			if (!NumberUtil.isNullOrZero(order.getOrderSavedBy()) && NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) {
				flags.put("btnSave", 0);
				flags.put("btnLock", 0);
				flags.put("btnPublish", 0);
			}
			// published
			if (!NumberUtil.isNullOrZero(order.getOrderPublishedByBA())) {
				flags.put("btnFinalize", 0);
				flags.put("btnPublish", 1);
			}
			// locked
			if (!NumberUtil.isNullOrZero(order.getOrderLockedBy())) {
				flags.put("btnFinalize", 0);
				flags.put("btnLock", 1);
			}
			// finalized
			if (!NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) {
				flags.put("btnFinalize", 1);
				flags.put("btnPublish", 1);
				flags.put("btnLock", 1);
			}
		}
		
		return flags;
	}
	
	private void prepareOrderAllBuyers(Map<String, Object> skuOrderMap, List<Integer> buyerIds,
			String deliveryDate, SKUBA skuObj, Integer isBuyer, JSONObject json,
			Map<String, Map<String, Map<Integer, Item>>> buyerOrderItemMap)
				throws Exception {
		
		//Integer skuId = skuObj.getSkuId();
		String key1 = skuObj.getSkuId() + "_" + skuObj.getSkuBAId();
		Map<Integer, Item> orderItemMap = new HashMap<Integer, Item>();
		orderItemMap = buyerOrderItemMap.get(key1).get(deliveryDate);
		
		BigDecimal rowqty = null;
		//unlocked: 0, locked: 1
		String allChecked = "1";
		String hasLockCol = "0"; //flag if has 1 locked column
		boolean hasNoOrderItem = true;
		
		for (Integer buyerId : buyerIds) {
			Item orderItem = null;
			try {
				orderItem = buyerOrderItemMap.get(key1).get(deliveryDate).get(buyerId);
			} catch (NullPointerException npe) {
				orderItem = null;
			}
			
			BigDecimal quantity = null;
			String strQuantity = "";
			String strLockFlag = "0"; //unlocked
			String baosVisFlag = "0";
			
			if (orderItem != null) {
				hasNoOrderItem = false;

				if (orderItem.getSosVisFlag()==null || orderItem.getSosVisFlag().equals("0") || orderItem.getBaosVisFlag()==null ||orderItem.getBaosVisFlag().equals("0"))
					baosVisFlag = "0";
				else
					baosVisFlag = "1";
				
				if(orderItem.getQuantity() != null) {
					quantity = orderItem.getQuantity();
					strQuantity = quantity.toPlainString();
					if (rowqty == null) rowqty = new BigDecimal(0);
					rowqty = rowqty.add(quantity);
				}
				
				if(orderItem.getOrderLockedBy() != null || //locked
						orderItem.getOrderFinalizedBy() != null){//finalized
					strLockFlag = "1"; //locked
					hasLockCol = "1";
				}
				
				/* start: Used by webservice:  OF=OrderFinalized */
				
				String finalized = "0";
				if (!NumberUtil.isNullOrZero(orderItem.getOrderFinalizedBy())){
					finalized = "1";
				}
				
				skuOrderMap.put("OF_" + buyerId.toString(), finalized);
				
				
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
				/* end: Used by webservice:*/

				
			}
			if (baosVisFlag.equals("0") && strLockFlag.equals("0"))
				allChecked = "0";

			if (!json.has("sku") && strLockFlag.equals("1"))
				json.put("sku", strLockFlag);
			json.put("Q_" + buyerId.toString(), strLockFlag);
			json.put("V_" + buyerId.toString(), strLockFlag);
			// ENHANCEMENT LELE: added not visible marker in sku
			json.put("CQ_" + buyerId.toString(), baosVisFlag);
			skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
			skuOrderMap.put("V_" + buyerId.toString(), baosVisFlag);
			
			
		}
		
		if(isBuyer == null){
			
			if(hasNoOrderItem) hasLockCol = "1";
			
			json.put("B_purchasePrice", hasLockCol);
			json.put("B_sellingPrice", hasLockCol);
			json.put("B_sellingUom", hasLockCol);
			json.put("B_skuComment", hasLockCol);
			json.put("buyerCols", hasLockCol);
		}
		
		skuOrderMap.put("visall", allChecked);
		
		this.setSkuLocking(orderItemMap, json);
		skuOrderMap.put("rowqty", rowqty);
		
	}
	
	private void prepareOrderAllDates(Map<String, Object> skuOrderMap, List<String> deliveryDates,
			Integer buyerId, SKUBA skuObj, Integer isBuyer, JSONObject json,
			Map<String, Map<String, Map<Integer, Item>>> buyerOrderItemMap,User user, Map<String, List<Integer>> mapOfMembersByDate)
				throws Exception {

		//Integer skuId = skuObj.getSkuId();
		String key1 = skuObj.getSkuId() + "_" + skuObj.getSkuBAId();
		String hasLockCol = "0"; //flag if has 1 locked column
		boolean hasNoOrderItem = true;

		BigDecimal rowqty = null;
		for (String deliveryDate : deliveryDates) {
			Item orderItem = null;
			try {
				orderItem = buyerOrderItemMap.get(key1).get(deliveryDate).get(buyerId);
			} catch (NullPointerException npe) {
				orderItem = null;
			}
			if (RolesUtil.isUserRoleBuyerAdmin(user)) {
				
				List<Integer> mapOfMembers = mapOfMembersByDate.get(deliveryDate);
				if (CollectionUtils.isEmpty(mapOfMembers) || !mapOfMembers.contains(buyerId)) {
					orderItem = null;
				}
			}
			BigDecimal quantity = null;
			String strQuantity = "";
			String strLockFlag = "0"; //unlocked
			
			if (orderItem != null) {
				hasNoOrderItem = false;
				
				if(orderItem.getQuantity() != null) {
					quantity = orderItem.getQuantity();
					strQuantity = quantity.toPlainString();
					if (rowqty == null) rowqty = new BigDecimal(0);
					rowqty = rowqty.add(quantity);
				}
				if(orderItem.getOrderLockedBy() != null || //locked
						orderItem.getOrderFinalizedBy() != null){//finalized
					strLockFlag = "1"; //locked
					hasLockCol = "1";
				}
				
				// check visibility
				if (orderItem.getBaosVisFlag().equals("0")) {
					strLockFlag = "1";
				}
				
			}
			else {
				strLockFlag = "1"; 
			}
			json.put("Q_" + deliveryDate, strLockFlag);
			skuOrderMap.put("Q_" + deliveryDate, strQuantity);
			if (!json.has("sku") && strLockFlag.equals("1"))
				json.put("sku", strLockFlag);
		}

		if(isBuyer == null){
			
			if(hasNoOrderItem) hasLockCol = "1";
			
			json.put("B_purchasePrice", hasLockCol);
			json.put("B_sellingPrice", hasLockCol);
			json.put("B_sellingUom", hasLockCol);
			json.put("B_skuComment", hasLockCol);
			json.put("buyerCols", hasLockCol);
		}
		
		skuOrderMap.put("rowqty", rowqty);
		skuOrderMap.put("lockflag", json.toString());
		
	}
	
	private void setSkuLocking(Map<Integer, Item> orderItemMap,
			JSONObject json) throws Exception {
		Set<Integer> keys = orderItemMap.keySet();
		Iterator<Integer> it = keys.iterator();
		while(it.hasNext()) {
			Integer key = it.next();
			Item oi = orderItemMap.get(key);
			if (!NumberUtil.isNullOrZero(oi.getOrderFinalizedBy())) {
				json.put("sku", "1");
			}
		}
	}
	
	private void prepareProfitInfoColumn(Map<String, Object> skuOrderMap, ProfitInfo profitInfo, String priceTaxOpt) 
		throws JSONException {

		JSONObject json = new JSONObject();
		json.put("priceWithoutTax", profitInfo.getPriceWithoutTax());
		json.put("priceWithTax", profitInfo.getPriceWithTax());
		json.put("sellingPrice", profitInfo.getSellingPrice());
		json.put("profit", profitInfo.getProfit());
		json.put("profitPercentage", profitInfo.getProfitPercentage());
		json.put("priceTaxOpt",priceTaxOpt);
		skuOrderMap.put("profitInfo", json.toString());

	}
	
	private boolean hasNoPublishedOrder(List<Order> allOrders) {
		
		boolean hasNoFinalizedOrder = true;
		
		for (Order order: allOrders) {
			if (!NumberUtil.isNullOrZero(order.getOrderPublishedBy())) {
				hasNoFinalizedOrder = false;
				break;
			}
		}
		
		return hasNoFinalizedOrder;
	}

	
	/*
	 * Used by webservices
	 */
	@Override
	public void saveWSData(WSBuyerAddOrderSheetRequest wsBuyerAddOrderSheetRequest){

		User loginUser = wsBuyerAddOrderSheetRequest.getLoginUser();
		if (RolesUtil.isUserRoleBuyerAdmin(loginUser)) {
			saveWSInsert(wsBuyerAddOrderSheetRequest);
		}
		
		saveWSUpdate(wsBuyerAddOrderSheetRequest);
	}

	private void saveWSInsert(
			WSBuyerAddOrderSheetRequest wsBuyerAddOrderSheetRequest) {
		// assumes that concurrency is already checked
		ArrayList<WSBuyerSKUAdd> addSkuList = wsBuyerAddOrderSheetRequest.getAddSkuList();
		if (CollectionUtils.isEmpty(addSkuList)) {
			return;
		}

		User loginUser = wsBuyerAddOrderSheetRequest.getLoginUser();
		Category category = wsBuyerAddOrderSheetRequest.getCategory();
		
		for (WSBuyerSKUAdd wsBuyerSKUAdd : addSkuList) {
			
			if (wsBuyerSKUAdd == null) {
				continue;
			}
			
			/* save the proposed sku */
			SKU sku = OrderSheetUtil.convertToSKU(wsBuyerSKUAdd, category, loginUser);
			skuService.insertSKU(sku);
			
			/* save the sku ba */
			SKUBA skuBA = OrderSheetUtil.convertToSKUBA(sku, wsBuyerSKUAdd);
			skuBADao.insertSKUBA2(skuBA);

			ArrayList<WSBuyerAddOrderSheetDetails> buyerDetails = wsBuyerSKUAdd
					.getDetails();

			if (CollectionUtils.isEmpty(buyerDetails)) {
				continue;
			}
			
			for (WSBuyerAddOrderSheetDetails wsBuyerAddOrderSheetDetails : buyerDetails) {
				if (wsBuyerAddOrderSheetDetails == null) {
					continue;
				}
				Order order = wsBuyerAddOrderSheetDetails.getOrder();
				
				Map<String, Object> param = new HashMap<String,Object>();
				param.put("orderId", order.getOrderId());
				param.put("skuId", sku.getSkuId());
				BigDecimal qty = wsBuyerAddOrderSheetDetails.getQty();
				param.put("quantity", qty);
				String visibility = wsBuyerAddOrderSheetDetails.getVisibility();
				param.put("baosVisFlag", StringUtils.isBlank(visibility)?"1":visibility);
				param.put("skuBAId", skuBA.getSkuBAId());
				orderItemDao.insertOrderItem(param); 
			}
			
			
		}

	}

	private void saveWSUpdate(
			WSBuyerAddOrderSheetRequest wsBuyerAddOrderSheetRequest) {

		// assumes that concurrency is already checked
		ArrayList<WSBuyerSKUUpdate> updateSKUList = wsBuyerAddOrderSheetRequest
				.getUpdateSKUList();

		if (CollectionUtils.isEmpty(updateSKUList)) {
			return;
		}

		User loginUser = wsBuyerAddOrderSheetRequest.getLoginUser();
		Integer userId = loginUser.getUserId();
		
		Map<Integer, SKUBA> skuBAMap = convertToMap(updateSKUList);
			
		for (WSBuyerSKUUpdate wsBuyerSKUUpdate : updateSKUList) {
			if (wsBuyerSKUUpdate == null) {
				continue;
			}
			ArrayList<WSBuyerAddOrderSheetDetails> buyerDetails = wsBuyerSKUUpdate
					.getDetails();

			if (CollectionUtils.isEmpty(buyerDetails)) {
				continue;
			}

			Integer skuId = wsBuyerSKUUpdate.getSkuId();
			boolean hasSKUBAChanged = false;
			SKUBA skuBA = null;
			if (RolesUtil.isUserRoleBuyerAdmin(loginUser)) {
				/*
				 * check SKUBA fields if value has changed. Save if changed and
				 * reset the SKUBAId to the new one
				 */
				hasSKUBAChanged = hasSKUBAChanged(skuBAMap, wsBuyerSKUUpdate);
				if (hasSKUBAChanged){
					skuBA = wsBuyerSKUUpdate.getSkuBA();
					skuBADao.insertSKUBA2(skuBA);
				}
			}
			
			for (WSBuyerAddOrderSheetDetails wsBuyerAddOrderSheetDetails : buyerDetails) {

				if (wsBuyerAddOrderSheetDetails == null
						|| wsBuyerAddOrderSheetDetails.getOrder() == null) {
					continue;
				}

				BigDecimal quantity = wsBuyerAddOrderSheetDetails.getQty();
				Integer orderId = wsBuyerAddOrderSheetDetails.getOrder()
						.getOrderId();
				
				
				
				if (RolesUtil.isUserRoleBuyer(loginUser)) {
					/* only qty is updated */
					orderDao.updateOrderQtyBySkuIdAndOrderId(quantity, userId,
							skuId, orderId, null, null);
				} else if (RolesUtil.isUserRoleBuyerAdmin(loginUser)) {
					String isVisible  = wsBuyerAddOrderSheetDetails.getVisibility();
					
					if (hasSKUBAChanged){
						/* qty and skubaID field are updated */
						orderDao.updateOrderItemQtySKUBA(quantity, userId, skuId,
								skuBA.getSkuBAId(), orderId, null, isVisible);
					} else {
						/* only qty is updated */
						orderDao.updateOrderQtyBySkuIdAndOrderId(quantity, userId,
								skuId, orderId, null, isVisible);
					}

				}
				orderDao.updateOrderByOrderId(userId, orderId);
			}
		}

		//

	}

	private Map<Integer, SKUBA> convertToMap(
			ArrayList<WSBuyerSKUUpdate> updateSKUList) {
		Set<Integer> skuBAIdSet = extractSKUBAId(updateSKUList);
		List<SKUBA> skuBAList = null;
		if (CollectionUtils.isNotEmpty(skuBAIdSet)) {

			skuBAList = skuBADao.findSKUBA(new ArrayList<Integer>(
					skuBAIdSet));
		}

		Map<Integer, SKUBA> skuBAMap = OrderSheetUtil.convertSKUBAListToMap(skuBAList);
		return skuBAMap;
	}


	private Set<Integer> extractSKUBAId(ArrayList<WSBuyerSKUUpdate> updateSKUList) {
		
		if (CollectionUtils.isEmpty(updateSKUList)) {
			return null;
		}
		
		Set<Integer> skuBAIdSet = new HashSet<Integer>();
		for (WSBuyerSKUUpdate wsBuyerSKUUpdate : updateSKUList) {
			ArrayList<WSBuyerAddOrderSheetDetails> details = wsBuyerSKUUpdate.getDetails();
			
			if (details == null){
				continue;
			}
			
			for (WSBuyerAddOrderSheetDetails wsBuyerAddOrderSheetDetails : details) {
				OrderItem orderItem = wsBuyerAddOrderSheetDetails.getOrderItem();
				if (orderItem == null) {
					continue;
				}
				Integer skuBaId = orderItem.getSkuBaId();
				skuBAIdSet.add(skuBaId);
			}
		}
		return skuBAIdSet;
	}

	private boolean hasSKUBAChanged(Map<Integer, SKUBA>skuBAMap, WSBuyerSKUUpdate wsBuyerSKUUpdate) {
		
		ArrayList<WSBuyerAddOrderSheetDetails> details = wsBuyerSKUUpdate.getDetails();
		
		/* all buyer details should have the same skubaid */
		WSBuyerAddOrderSheetDetails wsBuyerAddOrderSheetDetails = details.get(0);
		OrderItem orderItem = wsBuyerAddOrderSheetDetails.getOrderItem();
		
		SKUBA skuBA = null;
		/* it is possible for an order item to have no equivalent SKU BA ID*/
		Integer skuBaId = orderItem.getSkuBaId();
		if (skuBaId == null) {
			skuBA = new SKUBA();
			
		} else {
			skuBA = skuBAMap.get(skuBaId);
		}
		skuBA.setSkuId(orderItem.getSKUId());

		/* set the SKUBA field if not null and has changed */
		boolean hasChanged = false;
		BigDecimal b_purchasePrice = wsBuyerSKUUpdate.getB_purchasePrice();
		if (b_purchasePrice != null
				&& !b_purchasePrice.equals(skuBA.getPurchasePrice())) {
			skuBA.setPurchasePrice(b_purchasePrice);
			hasChanged = true;
		}
		
		BigDecimal b_sellingPrice = wsBuyerSKUUpdate.getB_sellingPrice();
		if (b_sellingPrice != null
				&& (skuBA.getSellingPrice()==null || !b_sellingPrice.equals(skuBA.getSellingPrice()))) {
			skuBA.setSellingPrice(b_sellingPrice);
			hasChanged = true;
		}

		Integer orderUnitId = null;
		OrderUnit b_sellingUom = wsBuyerSKUUpdate.getB_sellingUom();
		if (b_sellingUom != null) {
			orderUnitId = b_sellingUom.getOrderUnitId();
		}
		
		if (orderUnitId != null
				&& (skuBA.getSellingUom()==null || !orderUnitId.equals(skuBA.getSellingUom().getOrderUnitId())  )) {
			skuBA.setSellingUom(b_sellingUom);
			hasChanged = true;
		}
		
		String b_skuComment = wsBuyerSKUUpdate.getB_skuComment();
		if (b_skuComment != null && !b_skuComment.equals(skuBA.getSkuComment())) {
			skuBA.setSkuComment(b_skuComment);
			hasChanged = true;
		}
		wsBuyerSKUUpdate.setSkuBA(skuBA);
		return hasChanged;
	}
	
	@Override
	public List<OrderItem> getOrderItemsListOfSkuId(List<Integer> skuIds, List<String> deliveryDates){
		return orderSheetDao.getOrderItemsListOfSkuId(skuIds, deliveryDates);
	}
	
	
	
	
}
