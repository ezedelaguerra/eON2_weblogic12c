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
 * Dec 1, 2011		raquino		
 * 20120604			Rhoda		Redmine 867 - Concurrency Finalized Alloc, cannot Lock Received
 * 20120608			Rhoda		Redmine 68 - Order Sheet Concurrency
 * 20120727			Rhoda		Redmine 354 - Unfinalize Order and Finalize Alloc concurrency
 * 20120919			Rhoda		v12			Redmine 1070 - Seller can unfinalize the Order Sheet after Seller finalized Allocation Sheet
 */
package com.freshremix.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.freshremix.constants.OrderConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.dao.OrderSheetDao;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.CompositeKey;
import com.freshremix.model.ConcurrencyResponse;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUBA;
import com.freshremix.model.SKUBuyerQtyMap;
import com.freshremix.model.User;
import com.freshremix.service.MessageI18NService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.ui.model.OrderItemUI;

/**
 * @author raquino
 *
 */
public class ConcurrencyUtil {
	
	//DELETION 20120727: Rhoda Redmine 354
//	private static final String ORDER_DAO = "orderDao";

	//ENHANCEMENT 20120727: Rhoda Redmine 68
//	public static ConcurrencyResponse validateBuyerOrderSheet(User user, List<Order> allOrders, String action, List<FilteredIDs> buyerList){
	public static ConcurrencyResponse validateBuyerOrderSheet(User user, List<Order> allOrders, List<Order> currentOrders, String action, List<FilteredIDs> buyerList){
		
		ConcurrencyResponse concurrencyResponse = new ConcurrencyResponse();
		List<Order> forUpdateOrders = new ArrayList<Order>();
		StringBuffer concurrencyMsg = new StringBuffer(); 

		Map<Integer,String> sellerNameMap = FilterIDUtil.toIDNameMap(user.getPreference().getSortedSellers());
		Map<Integer,String> buyerNameMap = FilterIDUtil.toIDNameMap(buyerList);
		
		//DELETION START 20120608: Rhoda Redmine 68
//		//Get Current Orders by Stale Order IDs
//		OrderDao orderDao = (OrderDao) SpringContext.getApplicationContext().getBean(ORDER_DAO);
//		List<Integer> allOrderIds = OrderSheetUtil.getOrderIdList(allOrders);
//		List<Order> currentOrders = orderDao.getOrdersByOrderId(allOrderIds);
		//DELETION END 20120608: Rhoda Redmine 68
		
		Map<String, Order> currentOrdersMap = OrderSheetUtil.convertToOrderMap(currentOrders);
		//ENHANCEMENT 20120727: Rhoda Redmine 68
		boolean isSellerFinalized = false;
		
		for(Order order: allOrders){
			String key = OrderSheetUtil.formatOrderMapKey(
					order.getDeliveryDate(), order.getBuyerId().toString(),
					order.getSellerId().toString());
			
			Order currentOrder = currentOrdersMap.get(key);

			/**
			 * 	BUYER ORDER SHEET
			 * 
			 * 			Publish(S/SA)	Finalized(S/SA)  Locked(B/BA)
			 *	SAVE 	      o              x				 x
			 *  UNLOCKED      o              x               o
			 */
			//Save and Lock has the same conditions
			if (action.equals(OrderConstants.ACTION_SAVE)
					|| action.equals(OrderConstants.ACTION_LOCK)) {
				/* previously published but is now unpublished */
				if((!NumberUtil.isNullOrZero(order.getOrderPublishedBy()) && 
					NumberUtil.isNullOrZero(currentOrder.getOrderPublishedBy())) || //Unpublished
					/* previously unlocked but now locked */
				  (NumberUtil.isNullOrZero(order.getOrderLockedBy()) && 
					!NumberUtil.isNullOrZero(currentOrder.getOrderLockedBy())) ||   //Locked
					/* previously unfinalized but now finalized*/
				  (NumberUtil.isNullOrZero(order.getOrderFinalizedBy()) && 
					!NumberUtil.isNullOrZero(currentOrder.getOrderFinalizedBy()) )  //Finalized
				){ 	
					concurrencyMsg.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate())+ "  ");
					concurrencyMsg.append(sellerNameMap.get(order.getSellerId()) + "  ");
					concurrencyMsg.append(buyerNameMap.get(order.getBuyerId()) + "<br>");
					continue;
				}
				forUpdateOrders.add(currentOrder);
			}
			//ENHANCEMENT START 20120727: Rhoda Redmine 68
			if (action.equals(OrderConstants.ACTION_UNLOCK)){
				/* previously published but now unpublished */
				if((!NumberUtil.isNullOrZero(order.getOrderPublishedBy()) &&
					NumberUtil.isNullOrZero(currentOrder.getOrderPublishedBy())) || //Unpublished
					/* previously locked but now unlocked */
				  (!NumberUtil.isNullOrZero(order.getOrderLockedBy()) && 
					NumberUtil.isNullOrZero(currentOrder.getOrderLockedBy())) || //Unlocked
					/* previously unfinalized but now finalized */
				  (NumberUtil.isNullOrZero(order.getOrderFinalizedBy()) && 
					!NumberUtil.isNullOrZero(currentOrder.getOrderFinalizedBy()))  //Finalized
				){ 	
					concurrencyMsg.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate())+ "  ");
					concurrencyMsg.append(sellerNameMap.get(order.getSellerId()) + "  ");
					concurrencyMsg.append(buyerNameMap.get(order.getBuyerId()) + "<br>");
					
					//separate this logic due to different error message requested
					if(NumberUtil.isNullOrZero(order.getOrderFinalizedBy()) && 
							!NumberUtil.isNullOrZero(currentOrder.getOrderFinalizedBy()) )  //Finalized
						isSellerFinalized = true;
					
					continue;
				}
				
				forUpdateOrders.add(currentOrder);
			}
			//ENHANCEMENT END 20120727: Rhoda Redmine 68
			
		}
		
		concurrencyResponse.setForUpdateOrders(forUpdateOrders);
		concurrencyResponse.setConcurrencyMsg(concurrencyMsg.toString());
		//ENHANCEMENT 20120727: Rhoda Redmine 68
		concurrencyResponse.setSellerFinalized(isSellerFinalized);		
		
		return concurrencyResponse;
	}
	
	//ENHANCEMENT 20120727: Rhoda Redmine 68
//	public static ConcurrencyResponse validateSellerAllocationSheet(User user, List<Order> allOrders, String action){
	public static ConcurrencyResponse validateSellerAllocationSheet(User user, List<Order> allOrders, List<Order> currentOrders, String action, List<FilteredIDs> buyerList){
		
		ConcurrencyResponse concurrencyResponse = new ConcurrencyResponse();
		List<Order> forUpdateOrders = new ArrayList<Order>();
		StringBuffer concurrencyMsg = new StringBuffer(); 

		if (allOrders == null){
			concurrencyMsg.append("No Orders");
			return concurrencyResponse;
		}
		Map<Integer,String> sellerNameMap = new HashMap<Integer,String>();
		if(user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)){
			sellerNameMap = FilterIDUtil.toIDNameMap(user.getPreference().getSortedSellers());
		} else {
			sellerNameMap.put(user.getUserId(), user.getName());
		}
		//DELETION 20120727: Rhoda Redmine 354
//		Map<Integer,String> buyerNameMap = FilterIDUtil.toIDNameMap(buyerList);
		
		//DELETION START 20120608: Rhoda Redmine 68
//		//Get Current Orders by Stale Order IDs
//		OrderDao orderDao = (OrderDao) SpringContext.getApplicationContext().getBean(ORDER_DAO);
//		List<Integer> allOrderIds = OrderSheetUtil.getOrderIdList(allOrders);
//		List<Order> currentOrders = orderDao.getOrdersByOrderId(allOrderIds);
		//DELETION END 20120608: Rhoda Redmine 68
		Map<String, Order> currentOrdersMap = OrderSheetUtil.convertToOrderMap(currentOrders);
		//ENHANCEMENT START 20120727: Rhoda Redmine 354
		boolean isSellerOrderUnFinalized = false;
		boolean isReceivedFinalized = false;
		boolean isAllocFinalized = false;
		//ENHANCEMENT END 20120727: Rhoda Redmine 354
		
		for(Order order: allOrders){
			String key = order.getDeliveryDate() + "_" + order.getBuyerId().toString() + "_" + order.getSellerId().toString();
			Order currentOrder = currentOrdersMap.get(key);

			/**
			 * 	SELLER ALLOC SHEET
			 * 
			 * 			Finalized Order(S/SA)  Locked Received(B/BA)	Published Alloc(S/SA)	 Finalized Alloc(S/SA)
			 *	SAVE 	         o                       x				         							x
			 *  FINALIZE		 												  o							x
			 */
			if (action.equals(OrderConstants.ACTION_SAVE)){
				//DELETE START 20120727: Rhoda Redmine 354
//				if((!NumberUtil.isNullOrZero(order.getOrderFinalizedBy()) &&
//					NumberUtil.isNullOrZero(currentOrder.getOrderFinalizedBy())) || //Order Sheet UnFinalized
//				  (NumberUtil.isNullOrZero(order.getReceivedApprovedBy()) && 
//					!NumberUtil.isNullOrZero(currentOrder.getReceivedApprovedBy())) ||   //Received Sheet Finalized
//				  (NumberUtil.isNullOrZero(order.getAllocationFinalizedBy()) && 
//					!NumberUtil.isNullOrZero(currentOrder.getAllocationFinalizedBy()) )  //Alloc Sheet Finalized
//				){ 	
//					concurrencyMsg.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate())+ "  ");
//					concurrencyMsg.append(sellerNameMap.get(order.getSellerId()) + "\n");
//					continue;
//				}
				//DELETE END 20120727: Rhoda Redmine 354
				//ENHANCEMENT START 20120727: Rhoda Redmine 354
				if((!NumberUtil.isNullOrZero(order.getOrderFinalizedBy()) &&
					NumberUtil.isNullOrZero(currentOrder.getOrderFinalizedBy())) //Order Sheet UnFinalizeded
				){ 	
					concurrencyMsg.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate())+ "  ");
					concurrencyMsg.append(sellerNameMap.get(order.getSellerId()) + "\n");
					isSellerOrderUnFinalized = true;
					continue;
				}
				if((NumberUtil.isNullOrZero(order.getReceivedApprovedBy()) && 
					!NumberUtil.isNullOrZero(currentOrder.getReceivedApprovedBy()))   //Received Sheet Finalized
				){ 	
					concurrencyMsg.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate())+ "  ");
					concurrencyMsg.append(sellerNameMap.get(order.getSellerId()) + "<br>");
					isReceivedFinalized = true;
					continue;
				}
				if((NumberUtil.isNullOrZero(order.getAllocationFinalizedBy()) && 
					!NumberUtil.isNullOrZero(currentOrder.getAllocationFinalizedBy()) )  //Alloc Sheet Finalized
				){ 	
					concurrencyMsg.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate())+ "  ");
					concurrencyMsg.append(sellerNameMap.get(order.getSellerId()) + "<br>");
					isAllocFinalized = true;
					continue;
				}
				//ENHANCEMENT END 20120727: Rhoda Redmine 354
				forUpdateOrders.add(currentOrder);
			}

			//ENHANCEMENT START 20120727: Rhoda Redmine 354
			if (action.equals(OrderConstants.ACTION_FINALIZE) || action.equals(OrderConstants.ACTION_UNPUBLISH)){
				
				Map<Integer,String> buyerNameMap = FilterIDUtil.toIDNameMap(buyerList);
				
				if((!NumberUtil.isNullOrZero(order.getAllocationPublishedBy()) &&
					NumberUtil.isNullOrZero(currentOrder.getAllocationPublishedBy())) //Alloc Sheet Unpublished
				){ 	
					concurrencyMsg.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate())+ "  ");
					concurrencyMsg.append(sellerNameMap.get(order.getSellerId()) + "  ");
					concurrencyMsg.append(buyerNameMap.get(order.getBuyerId()) + "<br>");
					forUpdateOrders.clear();
					break;
				}

				if((NumberUtil.isNullOrZero(order.getReceivedApprovedBy()) && 
					!NumberUtil.isNullOrZero(currentOrder.getReceivedApprovedBy()))   //Received Sheet Finalized
				){ 	
					concurrencyMsg.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate())+ "  ");
					concurrencyMsg.append(sellerNameMap.get(order.getSellerId()) + "<br>");
					isReceivedFinalized = true;
					forUpdateOrders.clear();
					break;
					
				}else if(NumberUtil.isNullOrZero(order.getAllocationFinalizedBy()) && 
						!NumberUtil.isNullOrZero(currentOrder.getAllocationFinalizedBy()) //Alloc Sheet Finalized
				){ 	
					concurrencyMsg.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate())+ "  ");
					concurrencyMsg.append(sellerNameMap.get(order.getSellerId()) + "  ");
					concurrencyMsg.append(buyerNameMap.get(order.getBuyerId()) + "<br>");
					isAllocFinalized = true;
					forUpdateOrders.clear();
					break;
				}
					
				
				forUpdateOrders.add(currentOrder);
			}
			//ENHANCEMENT END 20120727: Rhoda Redmine 354
			
			
			if (action.equals(OrderConstants.ACTION_UNFINALIZE)){
				isAllocFinalized=true;
				Map<Integer,String> buyerNameMap = FilterIDUtil.toIDNameMap(buyerList);
				
				

				if((NumberUtil.isNullOrZero(order.getReceivedApprovedBy()) && 
					!NumberUtil.isNullOrZero(currentOrder.getReceivedApprovedBy()))   //Received Sheet Finalized
				){ 	
					concurrencyMsg.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate())+ "  ");
					concurrencyMsg.append(sellerNameMap.get(order.getSellerId()) + "<br>");
					isReceivedFinalized = true;
					forUpdateOrders.clear();
					break;
					
				}else if(NumberUtil.isNullOrZero(order.getAllocationUnfinalizedBy()) && 
						!NumberUtil.isNullOrZero(currentOrder.getAllocationUnfinalizedBy()) //Alloc Sheet UnFinalized
				){ 	
					concurrencyMsg.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate())+ "  ");
					concurrencyMsg.append(sellerNameMap.get(order.getSellerId()) + "  ");
					concurrencyMsg.append(buyerNameMap.get(order.getBuyerId()) + "<br>");
					isAllocFinalized = false;
					forUpdateOrders.clear();
					break;
				}
					
				
				forUpdateOrders.add(currentOrder);
			}
			
			
		
		
		}

	
		concurrencyResponse.setForUpdateOrders(forUpdateOrders);
		concurrencyResponse.setConcurrencyMsg(concurrencyMsg.toString());
		//ENHANCEMENT START 20120727: Rhoda Redmine 354
		concurrencyResponse.setIsSellerOrderUnFinalized(isSellerOrderUnFinalized);
		concurrencyResponse.setIsReceivedFinalized(isReceivedFinalized);
		concurrencyResponse.setIsAllocFinalized(isAllocFinalized);
		concurrencyResponse.setAction(action);
		//ENHANCEMENT END 20120727: Rhoda Redmine 354
		
		return concurrencyResponse;
	}
	
	//ENHANCEMENT 20120727: Rhoda Redmine 68
//	public static ConcurrencyResponse validateBuyerReceivedSheet(User user, List<Order> allOrders, String action){
	public static ConcurrencyResponse validateBuyerReceivedSheet(User user, List<Order> allOrders, List<Order> currentOrders, String action){
		
		ConcurrencyResponse concurrencyResponse = new ConcurrencyResponse();
		List<Order> forUpdateOrders = new ArrayList<Order>();
		StringBuffer concurrencyMsg = new StringBuffer(); 

		Map<Integer,String> sellerNameMap = FilterIDUtil.toIDNameMap(user.getPreference().getSortedSellers());
		
		//DELETION START 20120608: Rhoda Redmine 68
//		//Get Current Orders by Stale Order IDs
//		OrderDao orderDao = (OrderDao) SpringContext.getApplicationContext().getBean(ORDER_DAO);
//		List<Integer> allOrderIds = OrderSheetUtil.getOrderIdList(allOrders);
//		List<Order> currentOrders = orderDao.getOrdersByOrderId(allOrderIds);
		//DELETION END 20120608: Rhoda Redmine 68
		Map<String, Order> currentOrdersMap = OrderSheetUtil.convertToOrderMap(currentOrders);
		
		for(Order order: allOrders){
			String key = order.getDeliveryDate() + "_" + order.getBuyerId().toString() + "_" + order.getSellerId().toString();
			Order currentOrder = currentOrdersMap.get(key);

			/**
			 * 	BUYER RECEIVED SHEET
			 * 
			 * 			    Published Alloc(S/SA)  Locked Received(B/BA)	Finalized Alloc(S/SA)
			 *	FINALIZE 	         o                       x				         x
			 */
			if (action.equals(OrderConstants.ACTION_FINALIZE)){
				if((!NumberUtil.isNullOrZero(order.getAllocationPublishedBy()) &&
					NumberUtil.isNullOrZero(currentOrder.getAllocationPublishedBy())) || //Alloc Sheet Unpublished
				  (NumberUtil.isNullOrZero(order.getReceivedApprovedBy()) && 
					!NumberUtil.isNullOrZero(currentOrder.getReceivedApprovedBy())) //|| //Received Sheet Finalized
					// DELETION START 20120604: Rhoda Redmine 867
//				  (NumberUtil.isNullOrZero(order.getAllocationFinalizedBy()) && 
//					!NumberUtil.isNullOrZero(currentOrder.getAllocationFinalizedBy()) )  //Alloc Sheet Finalized
					// DELETION START 20120604: Rhoda Redmine 867
				){ 	
					concurrencyMsg.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate())+ "  ");
					concurrencyMsg.append(sellerNameMap.get(order.getSellerId()) + "<br>");
					continue;
				}
				forUpdateOrders.add(currentOrder);
			}
			
		}
		
		concurrencyResponse.setForUpdateOrders(forUpdateOrders);
		concurrencyResponse.setConcurrencyMsg(concurrencyMsg.toString());
		
		return concurrencyResponse;
	}
	
	//ENHANCEMENT START 20120727: Rhoda Redmine 354
	public static ConcurrencyResponse validateSellerOrderSheet(User user, List<Order> allOrders, List<Order> currentOrders, String action, List<FilteredIDs> buyerList){
		
		ConcurrencyResponse concurrencyResponse = new ConcurrencyResponse();
		List<Order> forUpdateOrders = new ArrayList<Order>();
		StringBuffer concurrencyMsg = new StringBuffer(); 

		//Map<Integer,String> sellerNameMap = FilterIDUtil.toIDNameMap(user.getPreference().getSortedSellers());
		Map<Integer,String> buyerNameMap = FilterIDUtil.toIDNameMap(buyerList);

		if (allOrders == null){
			concurrencyMsg.append("No Orders");
			return concurrencyResponse;
		}
		Map<Integer,String> sellerNameMap = new HashMap<Integer,String>();
		if(user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)){
			sellerNameMap = FilterIDUtil.toIDNameMap(user.getPreference().getSortedSellers());
		} else {
			sellerNameMap.put(user.getUserId(), user.getName());
		}
				
		Map<String, Order> currentOrdersMap = OrderSheetUtil.convertToOrderMap(currentOrders);
		
		for(Order order: allOrders){
			String key = OrderSheetUtil.formatOrderMapKey(
					order.getDeliveryDate(), order.getBuyerId().toString(),
					order.getSellerId().toString());
			Order currentOrder = currentOrdersMap.get(key);

			/**
			 * 	SELLER ORDER SHEET
			 * 
			 * 			   	Finalized Order(S/SA)  Finalized Alloc(S/SA)
			 *  UNFINALIZED           o            			 x            
			 */
			if (action.equals(OrderConstants.ACTION_UNFINALIZE)){
				if((!NumberUtil.isNullOrZero(order.getOrderFinalizedBy()) && 
					NumberUtil.isNullOrZero(currentOrder.getOrderFinalizedBy()) )|| //Finalized
				  (NumberUtil.isNullOrZero(order.getAllocationFinalizedBy()) && 
					!NumberUtil.isNullOrZero(currentOrder.getAllocationFinalizedBy()) )
				){ 	
					/* used to set the proper message in js */
					if (!NumberUtil.isNullOrZero(currentOrder.getAllocationFinalizedBy()) ) {
						concurrencyResponse.setIsAllocFinalized(true);
					}
	
					concurrencyMsg.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate())+ "  ");
					concurrencyMsg.append(sellerNameMap.get(order.getSellerId()) + "  ");
					concurrencyMsg.append(buyerNameMap.get(order.getBuyerId()) + "<br>");
					continue;
				}
				//ENHANCEMENT 20120919: Rhoda Redmine 1070
				if(NumberUtil.isNullOrZero(currentOrder.getAllocationFinalizedBy())) {
					forUpdateOrders.add(currentOrder);
				}
			}

			if (action.equals(OrderConstants.ACTION_SAVE)){
				/* previously unfinalized but now finalized */
				if((NumberUtil.isNullOrZero(order.getOrderFinalizedBy()) && 
					!NumberUtil.isNullOrZero(currentOrder.getOrderFinalizedBy()) )|| 
					/* previously unlocked but now locked */
				  (NumberUtil.isNullOrZero(order.getOrderLockedBy()) && 
					!NumberUtil.isNullOrZero(currentOrder.getOrderLockedBy()) )
				){ 	
					concurrencyMsg.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate())+ "  ");
					concurrencyMsg.append(sellerNameMap.get(order.getSellerId()) + "  ");
					concurrencyMsg.append(buyerNameMap.get(order.getBuyerId()) + "<br>");
					continue;
				}
				forUpdateOrders.add(currentOrder);
			}
			
			
		}
		
		concurrencyResponse.setForUpdateOrders(forUpdateOrders);
		concurrencyResponse.setConcurrencyMsg(concurrencyMsg.toString());
		
		return concurrencyResponse;
	}
	//ENHANCEMENT END 20120727: Rhoda Redmine 354
	
	public static ConcurrencyResponse validateSellerOrderSheetSKUQuantity(
			String action, List<OrderItemUI> orderItemList,
			OrderDetails orderDetails,
			Map<Integer, BigDecimal> skuIdQuantityMap,
			Map<Integer, SKU> skuObjMap,  MessageI18NService messageI18NService) {
		
		ConcurrencyResponse concurrencyResponse = new ConcurrencyResponse();
		StringBuilder concurrencyMsg = new StringBuilder(); 
		List<OrderItemUI> oiUIforUpdate = new ArrayList<OrderItemUI>();

		if (CollectionUtils.isEmpty(orderItemList)) {
			return concurrencyResponse;
		}
		
		for (OrderItemUI orderItemUI : orderItemList) {
			SKU sku = OrderSheetUtil.toSKU(orderItemUI, orderDetails);

			SKU origSku = skuObjMap.get(sku.getSkuId());
			
			boolean isMetaInfoChanged = !origSku.equals(sku);

			BigDecimal quantity = skuIdQuantityMap.get(orderItemUI.getSkuId());
			boolean hasQuantity = (quantity != null && (NumberUtil.isGreaterThanZero(quantity))); 
			 
			if ((OrderConstants.DELETE.equalsIgnoreCase(action) && hasQuantity) || 
			    (OrderConstants.UPDATE.equalsIgnoreCase(action) && hasQuantity && isMetaInfoChanged)){

				
				concurrencyMsg.append(orderItemUI.getSkuId() + "  ");
				concurrencyMsg.append(orderItemUI.getSellername() + "  ");
				concurrencyMsg.append(orderItemUI.getSkuname() + "  ");
				concurrencyMsg.append(messageI18NService.getPropertyMessage(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_SKUHASQTY_ERROR)+"<br>");

			
				continue;
			}
			oiUIforUpdate.add(orderItemUI);
		}
		concurrencyResponse.setOiUIforUpdate(oiUIforUpdate);
		concurrencyResponse.setConcurrencyMsg(concurrencyMsg.toString());
		return concurrencyResponse;
	}

	/**
	 * Checks if the updated records have been modified by another user by comparing sku meta info
	 * @param orderForm
	 * @param od
	 * @param skuBAMapFromDB 
	 * @return 
	 */
	public static ConcurrencyResponse isBuyerOrderSheetModified(
			List<OrderItemUI> orderItemList, OrderDetails od,
			Map<CompositeKey<Integer>, SKUBA> skuBAMapFromDB,
			Map<CompositeKey<Integer>, SKUBA> skuBAMapFromSession, 
			OrderSheetDao orderSheetDao, MessageI18NService messageI18NService) {
		
		if (CollectionUtils.isEmpty(orderItemList)) {
			return new ConcurrencyResponse();
		}
		Map<Integer,String> sellerNameMap = new HashMap<Integer,String>();
		User user = od.getUser();
		List<OrderItemUI> oiUIforUpdate = new ArrayList<OrderItemUI>();

		if(user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)){
			sellerNameMap.put(user.getUserId(), user.getName());
		} else {
			sellerNameMap = FilterIDUtil.toIDNameMap(user.getPreference().getSortedSellers());
		}
		StringBuilder concurrencyMsg = new StringBuilder(); 

		for (OrderItemUI orderItemUI : orderItemList) {
			//Integer skuId = orderItemUI.getSkuId();
			CompositeKey<Integer> compositeKey = new CompositeKey<Integer>(
					orderItemUI.getSkuId(), orderItemUI.getSkuBaId());
			SKUBA skuBAfromSession = skuBAMapFromSession.get(compositeKey);
			SKUBA skubafromDB = skuBAMapFromDB.get(compositeKey);
			//sku is not anymore existing in DB or visibility is off
			if (skubafromDB == null){
				concurrencyMsg.append(orderItemUI.getSkuId()+ "  ");
				concurrencyMsg.append(getSellerName(orderItemUI.getSellerId(), sellerNameMap) + "  ");
				concurrencyMsg.append(orderItemUI.getSkuname() + "  ");
				concurrencyMsg.append(messageI18NService.getPropertyMessage(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_SKUCHANGED)+"<br>");
				continue;
			}
			
			
			//check if sku and sku ba specific meta info was changed
			if (!skubafromDB.equals(skuBAfromSession)) {
				concurrencyMsg.append(orderItemUI.getSkuId()+ "  ");
				concurrencyMsg.append(getSellerName(orderItemUI.getSellerId(), sellerNameMap) + "  ");
				concurrencyMsg.append(orderItemUI.getSkuname() + "  ");
				concurrencyMsg.append(messageI18NService.getPropertyMessage(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_SKUCHANGED)+"<br>");
				continue;

			}
			oiUIforUpdate.add(orderItemUI);
			
		}
		ConcurrencyResponse concurrencyResponse = new ConcurrencyResponse();
		concurrencyResponse.setOiUIforUpdate(oiUIforUpdate);

		if(StringUtils.isNotBlank(concurrencyMsg.toString())){
			concurrencyResponse.setConcurrencyMsg(concurrencyMsg.toString());

			Map<String, Object> exceptionContext = new HashMap<String, Object>();
			exceptionContext.put(OrderConstants.FAILED_SKU, concurrencyMsg.toString());
			
			//throw new ServiceException(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_MAX_LIMIT_ERROR, exceptionContext);

		}
		return concurrencyResponse;		
		
	}
	
	/**
	 * Method to check setting of quantity vs visibility
	 * 
	 * @param orderForm
	 * @param orderDetails
	 * @param extractedFilteredOrders
	 * @param orderSheetDao
	 * @param buyerProcess
	 * @throws ServiceException
	 */
	public static ConcurrencyResponse concurrentSaveOrderVisibility(List<OrderItemUI> orderItemList,
			OrderDetails orderDetails,List<Order> extractedFilteredOrders, OrderSheetDao orderSheetDao, boolean buyerProcess, MessageI18NService messageI18NService) throws ServiceException {
		if (CollectionUtils.isEmpty(orderItemList)) {
			return  new ConcurrencyResponse();
		}

		List<OrderItemUI> oiUIforUpdate = new ArrayList<OrderItemUI>();

		List<Integer> orderIdListFromSession = OrderSheetUtil
				.filterOrderIdListFromOrderDetails(extractedFilteredOrders,
						orderDetails);
		
		StringBuilder concurrencyMsg = new StringBuilder(); 
		User user = orderDetails.getUser();
		Map<Integer,String> sellerNameMap = new HashMap<Integer,String>();

		if(user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)){
			sellerNameMap.put(user.getUserId(), user.getName());
		} else {
			sellerNameMap = FilterIDUtil.toIDNameMap(user.getPreference().getSortedSellers());
		}
		for (OrderItemUI oi : orderItemList) {
			
			Map<String, String> visMap = oi.getVisMap();
			Map<String, BigDecimal> qtyMap = oi.getQtyMap();
			List< OrderItem> orderItemsFromDB = orderSheetDao.getOrderItemsListOfSkuBuyers(orderIdListFromSession, oi.getSkuId());
			boolean toAdd = true;
			for (OrderItem oitem : orderItemsFromDB) {
				
				Order order = oitem.getOrder();
				if (order == null) {
					continue;
				}
				
				/*
				 * For Buyer Sheets: 2 rows of SKU can have same SKU ID but
				 * different SKUBAID. If different SKUBAID, skip.
				 * 
				 * For Seller Sheets: SKUBAID taken from screen is usually
				 * blank/null so do not perform the checking
				 */
				if (SheetTypeConstants.BUYER_ORDER_SHEET_TYPE_LIST
						.contains(orderDetails.getSheetType())
						&& ((oi.getSkuBaId()!=null || oitem.getSkuBaId()!=null) && NumberUtil.isNotEqual(oi.getSkuBaId(), oitem.getSkuBaId()))) {
					continue;
				}
				
				Integer buyerId = order.getBuyerId();
				
				String deliveryDate = order.getDeliveryDate();
				String quantityKey = orderDetails.isAllDatesView() == true ? deliveryDate
						: buyerId.toString();

				String vis =StringUtil.nullToBlank(visMap.get("V_" + quantityKey));
				
				//Quantity >0 in DB but user is still trying to save visibility=0  : ERROR
				if(!NumberUtil.isNullOrZero(oitem.getQuantity()) && vis.equals("0"))
				{

					concurrencyMsg.append(oi.getSkuId()+ "  ");
					concurrencyMsg.append(getSellerName(oi.getSellerId(), sellerNameMap) + "  ");
					concurrencyMsg.append(oi.getSkuname() + "  ");
					concurrencyMsg.append(messageI18NService.getPropertyMessage(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_SKUHASQTY_ERROR)+"<br>");
					visMap.remove("V_" + quantityKey);
					visMap.put("Q_" + quantityKey, oitem.getQuantity().toString());
					qtyMap.put("Q_" + quantityKey, oitem.getQuantity());
					qtyMap.remove("V_" + quantityKey);
					break;
				}
				
				BigDecimal qty =qtyMap.get("Q_" + quantityKey);
				
				//BA/SA Visibility=0 in DB  but user is still trying to set quantity >= 0 :ERROR
				boolean baVisibleOffDB = "0".equalsIgnoreCase(oitem.getBaosVisFlag());
				boolean saVisibleOffDB = "0".equalsIgnoreCase(oitem.getSosVisFlag());
				
				boolean visibilityOffDB = false;
				if (buyerProcess) {
					
					if(RolesUtil.isUserRoleBuyerAdmin(user)){
						visibilityOffDB = saVisibleOffDB;
					}else{
						visibilityOffDB = baVisibleOffDB || saVisibleOffDB;

					}
				
					if (visibilityOffDB && qty != null
							&& NumberUtil.isNotNegative(qty) ) {
						concurrencyMsg.append(oi.getSkuId() + "  ");
						concurrencyMsg.append(getSellerName(oi.getSellerId(), sellerNameMap) + "  ");
						concurrencyMsg.append(oi.getSkuname() + "  ");
						concurrencyMsg.append(messageI18NService.getPropertyMessage(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_SKUHASQTY_ERROR)+"<br>");
						visMap.remove("V_" + quantityKey);
						visMap.remove("Q_" + quantityKey);
						qtyMap.remove("Q_" + quantityKey);
						qtyMap.remove("V_" + quantityKey);
						break;
					}
				}
			}
			
			oiUIforUpdate.add(oi);
			

		}
//		if(StringUtils.isNotBlank(concurrencyMsg.toString())){
//
//			Map<String, Object> exceptionContext = new HashMap<String, Object>();
//			exceptionContext.put(OrderConstants.FAILED_SKU, concurrencyMsg.toString());
//			
//			throw new ServiceException(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_SKU_HAS_QTY_FAILED, exceptionContext);
//
//		}
		
		ConcurrencyResponse concurrencyResponse = new ConcurrencyResponse();
		concurrencyResponse.setOiUIforUpdate(oiUIforUpdate);

		if(StringUtils.isNotBlank(concurrencyMsg.toString())){
			concurrencyResponse.setConcurrencyMsg(concurrencyMsg.toString());

			Map<String, Object> exceptionContext = new HashMap<String, Object>();
			exceptionContext.put(OrderConstants.FAILED_SKU, concurrencyMsg.toString());
			
			//throw new ServiceException(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_MAX_LIMIT_ERROR, exceptionContext);

		}
		return concurrencyResponse;
	}
	private static String getSellerName(Integer sellerId,
			Map<Integer, String> sellerNameMap) {
		String sellerName = sellerNameMap.get(sellerId);
		if (StringUtils.isBlank(sellerName)) {
			sellerName = sellerId.toString();
		}
		return sellerName;
	}	
	
	
	public static ConcurrencyResponse validateMaxQuantity(List<OrderItemUI> orderItemList,
			OrderDetails orderDetails,List<Order> extractedFilteredOrders, OrderSheetDao orderSheetDao, boolean buyerProcess, MessageI18NService messageI18NService) {
		
		
		if (CollectionUtils.isEmpty(orderItemList)) {
			return  new ConcurrencyResponse();
		}
		List<OrderItemUI> oiUIforUpdate = new ArrayList<OrderItemUI>();

		List<Integer> skuIdList= OrderSheetUtil.getSKUId(orderItemList);
		List<String> deliveryDates = DateFormatter.getDateList(orderDetails.getStartDate(), orderDetails.getEndDate());
		List<OrderItem> allOrderItems = orderSheetDao.getOrderItemsListOfSkuId(skuIdList,deliveryDates);
		SKUBuyerQtyMap skuBuyerQtyMap= OrderSheetUtil.convertToSKUBuyerQtyMap(allOrderItems);

		List<Integer> orderIdListFromSession = OrderSheetUtil
				.filterOrderIdListFromOrderDetails(extractedFilteredOrders,
						orderDetails);
		Map<Integer,String> sellerNameMap = new HashMap<Integer,String>();
		User user = orderDetails.getUser();

		if(user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)){
			sellerNameMap.put(user.getUserId(), user.getName());
		} else {
			sellerNameMap = FilterIDUtil.toIDNameMap(user.getPreference().getSortedSellers());
		}
		/*
		 * Map of total qty per sku-deliverydate from ui
		 */
		Map<CompositeKey<String>, List> uiQtySumMap= new HashMap<CompositeKey<String>, List>();

		
		
		StringBuilder concurrencyMsg = new StringBuilder(); 
		
		
		/*
		 * Store sum of qty from ui 
		 */
		for (OrderItemUI oi : orderItemList) {
				Map<String, BigDecimal> qtyMap = oi.getQtyMap();
				List< OrderItem> orderItemsFromDB = orderSheetDao.getOrderItemsListOfSkuBuyers(orderIdListFromSession, oi.getSkuId());
	
				for (OrderItem oitem : orderItemsFromDB) {
					
					Order order = oitem.getOrder();
					Integer buyerId = order.getBuyerId();				
					String deliveryDate = order.getDeliveryDate();
					/*
					 * For Buyer Sheets: 2 rows of SKU can have same SKU ID but
					 * different SKUBAID. If different SKUBAID, skip.
					 * 
					 * For Seller Sheets: SKUBAID taken from screen is usually
					 * blank/null so do not perform the checking
					 */
					if (SheetTypeConstants.BUYER_ORDER_SHEET_TYPE_LIST
							.contains(orderDetails.getSheetType())
							&& ((oi.getSkuBaId()!=null || oitem.getSkuBaId()!=null) && NumberUtil.isNotEqual(oi.getSkuBaId(), oitem.getSkuBaId()))) {
						continue;
					}
					
					
					String quantityKey = orderDetails.isAllDatesView() == true ? deliveryDate
							: buyerId.toString();
					
					
					
					BigDecimal qty =qtyMap.get("Q_" + quantityKey);
					qty =NumberUtil.nullToZero(qty);
					CompositeKey<String> key = new CompositeKey<String>(oi.getSkuId().toString(), deliveryDate);
					List skuQtyList =uiQtySumMap.get(key);
					BigDecimal skuTotal=null;
					List<Integer> skuBuyerList = null;
					if(CollectionUtils.isEmpty(skuQtyList)){
						skuQtyList = new ArrayList();
						skuQtyList.add(oi);
						skuBuyerList = new ArrayList<Integer>();
					}else
					{
						skuTotal = (BigDecimal)skuQtyList.get(1);
						skuBuyerList = (List<Integer>) skuQtyList.get(2);
						
					}
					if(!skuBuyerList.contains(buyerId)){
						skuBuyerList.add(buyerId);
					}
					if(skuTotal==null){
						skuTotal = new BigDecimal(0);
					}
					//System.out.println(qty.toString()+" "+skuTotal.toString());

					skuTotal= skuTotal.add(qty);
					skuQtyList.add(1,skuTotal);
					skuQtyList.add(2,skuBuyerList);
					uiQtySumMap.put(key, skuQtyList);
				}
				
			}
		
			/*Iterate over sku's with qty updates
			 * 
			 */
			for (Map.Entry entry : uiQtySumMap.entrySet()) { 
				CompositeKey<String> key = (CompositeKey<String>)entry.getKey();
				List skuQtyList = (List)entry.getValue();
				OrderItemUI oitem = (OrderItemUI)skuQtyList.get(0);
				BigDecimal uiTotal = (BigDecimal)skuQtyList.get(1);
				List<Integer> buyerList = (List<Integer>)skuQtyList.get(2);
				BigDecimal maxQty = oitem.getSkumaxlimit();
				BigDecimal dbTotal = new BigDecimal(0); 
				/*
				 * get total of orders from other buyers.
				 * exclude selected buyers
				 */
				Map<Integer, BigDecimal> buyerQtyList = skuBuyerQtyMap.getBuyerQtyList(key.getKey(1), new Integer(key.getKey(0)));
				
				if (MapUtils.isNotEmpty(buyerQtyList)) {
					for (Map.Entry buyerEntry : buyerQtyList.entrySet()) {
						Integer buyerId = (Integer)buyerEntry.getKey();
						if(buyerList.contains(buyerId)){
							continue;
						}
						BigDecimal qty = (BigDecimal)buyerEntry.getValue();
						dbTotal = dbTotal.add(qty);
					}
				}
				//grandtotal = orders from other buyers + orders from screen
				BigDecimal grandTotal = dbTotal.add(uiTotal);

				if(maxQty!=null && grandTotal.compareTo(maxQty)>0){
					concurrencyMsg.append(oitem.getSkuId() + "  ");
					concurrencyMsg.append(getSellerName(oitem.getSellerId(), sellerNameMap) + "  ");
					concurrencyMsg.append(oitem.getSkuname() + "  ");
					concurrencyMsg.append(messageI18NService.getPropertyMessage(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_SKUMAXLIMIT_ERROR)+"入力可能数： "+maxQty.subtract(dbTotal).toString() + "<br>");

					continue;
				}
				oiUIforUpdate.add(oitem);
			}
			ConcurrencyResponse concurrencyResponse = new ConcurrencyResponse();
			concurrencyResponse.setOiUIforUpdate(oiUIforUpdate);

			if(StringUtils.isNotBlank(concurrencyMsg.toString())){
				concurrencyResponse.setConcurrencyMsg(concurrencyMsg.toString());

				Map<String, Object> exceptionContext = new HashMap<String, Object>();
				exceptionContext.put(OrderConstants.FAILED_SKU, concurrencyMsg.toString());
				
				//throw new ServiceException(OrderConstants.ORDERSHEET_CONCURRENT_SAVE_MAX_LIMIT_ERROR, exceptionContext);
	
			}
			return concurrencyResponse;
		}
	
}
