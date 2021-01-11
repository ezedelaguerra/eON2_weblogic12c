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
 * date       		name        version	    changes
 * ------------------------------------------------------------------------------
 * 20120913			Lele		chrome		880 - Chrome compatibility
 */
package com.freshremix.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.model.AdminMember;
import com.freshremix.model.AkadenItem;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.BillingItem;
import com.freshremix.model.Category;
import com.freshremix.model.Company;
import com.freshremix.model.CompositeKey;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.Item;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderReceived;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUBA;
import com.freshremix.model.SKUBuyerQtyMap;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.User;
import com.freshremix.model.WSBuyerAddOrderSheetSKU;
import com.freshremix.model.WSBuyerSKUAdd;
import com.freshremix.service.DealingPatternService;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderItemUI;
import com.freshremix.ui.model.ProfitInfo;

public class OrderSheetUtil {

	private static final Integer INTEGER_ZERO = Integer.valueOf(0);

	
	public static SKUBA toSKUBA(OrderItemUI oi, OrderDetails orderDetails) {
		
		SKUBA skuBA = new SKUBA();
		User sellerUser = new User();
		SKUGroup skuGroup = new SKUGroup();
		OrderUnit orderUnit = new OrderUnit();
		
		sellerUser.setUserId(oi.getSellerId());
		skuGroup.setSkuGroupId(oi.getSkuGroupId());
		orderUnit.setOrderUnitId(oi.getUnitorder());
		
		OrderUnit sellingUom = null;
		if (oi.getSellingUom()!=null && oi.getSellingUom()>0) {
			sellingUom = new OrderUnit();
			sellingUom.setOrderUnitId(oi.getSellingUom());
		}
		
		skuBA.setSkuId(oi.getSkuId());
		skuBA.setClazz(oi.getClazzname());
		skuBA.setUser(sellerUser);
		skuBA.setGrade(oi.getGrade());
		skuBA.setLocation(oi.getHome());
		skuBA.setMarket(oi.getMarketname());
		skuBA.setOrigSkuId(oi.getSkuId());
		skuBA.setPackageQuantity(oi.getPackageqty());
		skuBA.setPackageType(oi.getPackagetype());
		skuBA.setPrice1(oi.getPrice1());
		skuBA.setPrice2(oi.getPrice2());
		skuBA.setPriceWithoutTax(oi.getPricewotax());
		skuBA.setSheetType(orderDetails.getSheetType());
		skuBA.setSkuCategoryId(orderDetails.getSkuCategoryId());
		skuBA.setSkuName(oi.getSkuname());
		skuBA.setSkuGroup(skuGroup);
		skuBA.setOrderUnit(orderUnit);
		skuBA.setComments(oi.getComments());
		skuBA.setSkuMaxLimit(oi.getSkumaxlimit());
		skuBA.setComments(oi.getSkuComments());
		skuBA.setExternalSkuId(oi.getExternalSkuId());
		
		skuBA.setColumn01(oi.getColumn01());
		skuBA.setColumn02(oi.getColumn02());
		skuBA.setColumn03(oi.getColumn03());
		skuBA.setColumn04(oi.getColumn04());
		skuBA.setColumn05(oi.getColumn05());
		skuBA.setColumn06(oi.getColumn06());
		skuBA.setColumn07(oi.getColumn07());
		skuBA.setColumn08(oi.getColumn08());
		skuBA.setColumn09(oi.getColumn09());
		skuBA.setColumn10(oi.getColumn10());
		skuBA.setColumn11(oi.getColumn11());
		skuBA.setColumn12(oi.getColumn12());
		skuBA.setColumn13(oi.getColumn13());
		skuBA.setColumn14(oi.getColumn14());
		skuBA.setColumn15(oi.getColumn15());
		skuBA.setColumn16(oi.getColumn16());
		skuBA.setColumn17(oi.getColumn17());
		skuBA.setColumn18(oi.getColumn18());
		skuBA.setColumn19(oi.getColumn19());
		skuBA.setColumn20(oi.getColumn20());
		
		//skuBA specific fields
		skuBA.setPurchasePrice(oi.getPurchasePrice());
		skuBA.setSellingPrice(oi.getSellingPrice());
		skuBA.setSellingUom(sellingUom);
		skuBA.setSkuComment(StringUtils.trimToNull(oi.getSkuComments()));
		skuBA.setSkuBAId(oi.getSkuBaId());
		return skuBA;
	}
	
	public static SKU toSKU (OrderItemUI oi, OrderDetails orderDetails) {
		SKU sku = new SKU();
		User sellerUser = new User();
		SKUGroup skuGroup = new SKUGroup();
		OrderUnit orderUnit = new OrderUnit();
		OrderUnit sellingUom = new OrderUnit();
		
		sellerUser.setUserId(oi.getSellerId());
		skuGroup.setSkuGroupId(oi.getSkuGroupId());
		orderUnit.setOrderUnitId(oi.getUnitorder());
		sellingUom.setOrderUnitId(oi.getSellingUom());
		
		sku.setSkuId(oi.getSkuId());
		sku.setClazz(oi.getClazzname());
		sku.setUser(sellerUser);
		sku.setGrade(oi.getGrade());
		sku.setLocation(oi.getHome());
		sku.setMarket(oi.getMarketname());
		sku.setOrigSkuId(oi.getSkuId());
		sku.setPackageQuantity(oi.getPackageqty());
		sku.setPackageType(oi.getPackagetype());
		sku.setPrice1(oi.getPrice1());
		sku.setPrice2(oi.getPrice2());
		sku.setPriceWithoutTax(oi.getPricewotax());
		sku.setSheetType(orderDetails.getSheetType());
		sku.setSkuCategoryId(orderDetails.getSkuCategoryId());
		sku.setSkuName(oi.getSkuname());
		sku.setSkuGroup(skuGroup);
		sku.setOrderUnit(orderUnit);
		sku.setComments(oi.getComments());
		sku.setSkuMaxLimit(oi.getSkumaxlimit());
		sku.setComments(oi.getSkuComments());
		sku.setExternalSkuId(oi.getExternalSkuId());
		
		sku.setColumn01(oi.getColumn01());
		sku.setColumn02(oi.getColumn02());
		sku.setColumn03(oi.getColumn03());
		sku.setColumn04(oi.getColumn04());
		sku.setColumn05(oi.getColumn05());
		sku.setColumn06(oi.getColumn06());
		sku.setColumn07(oi.getColumn07());
		sku.setColumn08(oi.getColumn08());
		sku.setColumn09(oi.getColumn09());
		sku.setColumn10(oi.getColumn10());
		sku.setColumn11(oi.getColumn11());
		sku.setColumn12(oi.getColumn12());
		sku.setColumn13(oi.getColumn13());
		sku.setColumn14(oi.getColumn14());
		sku.setColumn15(oi.getColumn15());
		sku.setColumn16(oi.getColumn16());
		sku.setColumn17(oi.getColumn17());
		sku.setColumn18(oi.getColumn18());
		sku.setColumn19(oi.getColumn19());
		sku.setColumn20(oi.getColumn20());
		
		return sku;
	}
	
	public static SKU toSKU (OrderItemUI oi, OrderDetails orderDetails, User user) {
		SKU sku = toSKU(oi, orderDetails);
		sku.setUser(user);
		return sku;
	}

	public static SKU toSKU (OrderItemUI oi, OrderDetails orderDetails, Integer proposedBy) {
		SKU sku = toSKU(oi, orderDetails);
		User userSeller = new User();
		User userBuyer = new User();
		
		userSeller.setUserId(oi.getSellerId());
		userBuyer.setUserId(proposedBy);
		
		sku.setUser(userSeller);
		sku.setProposedBy(userBuyer);
		
		return sku;
	}

	public static SKU toSKU (OrderItemUI oi, OrderDetails orderDetails, User user, Integer sheetType) {
		SKU sku = toSKU(oi, orderDetails);
		sku.setUser(user);
		return sku;
	}

	/**
	 * Parses semicolon separated list of numbers.
	 * 
	 * Any entry that is whitespace, zero or not a valid digit is ignored.
	 * 
	 * If null or empty string is entered, a default size(1) List is returned,
	 * else it will return an ArrayList with size == split size
	 * 
	 * @param str
	 * @return
	 */
	public static  List<Integer> toList(String str) {
		
		if (StringUtils.isBlank(str)){
			return new ArrayList<Integer>(1);
		}
		
		String[] splitArray = StringUtils.split(str, ";");
		List<Integer> list = new ArrayList<Integer>(splitArray.length);
		for (int i = 0; i < splitArray.length; i++) {
			String token = StringUtils.trimToNull(splitArray[i]);
			Integer intToken = NumberUtils.isDigits(token) ? Integer.valueOf(token): INTEGER_ZERO;
			if (intToken >0){
				list.add(intToken);
			}
			
		}
		return list;
	}
	
	//converts order list to map (key = deliveryDate_buyerId_sellerId)
	public static Map<String, Order> convertToOrderMap(List<Order> orders) {
		Map<String, Order> orderMap = new HashMap<String, Order>();
		
		if (orders == null) return orderMap;
		
		for (Order order : orders) {
			String deliveryDate = order.getDeliveryDate();
			String buyerId = order.getBuyerId().toString();
			String sellerId = order.getSellerId().toString();
			String key = formatOrderMapKey(deliveryDate, buyerId, sellerId);
			orderMap.put(key, order);
		}
		return orderMap;
		
	}
	
	//converts order list to map (key = sellerid_buyerId)
	public static Map<CompositeKey<Integer>, Order> convertToSellerBuyerOrderMap(List<Order> orders) {
			Map<CompositeKey<Integer>, Order> orderMap = new HashMap<CompositeKey<Integer>,Order>();
			
			if (orders == null) return orderMap;
			
			for (Order order : orders) {
				Integer buyerId = order.getBuyerId();
				Integer sellerId = order.getSellerId();
				CompositeKey<Integer> key = new CompositeKey<Integer>(sellerId, buyerId);
				orderMap.put(key, order);
			}
			return orderMap;
			
		}
	
	/**
	 * Converts an SKUBA list to a Map
	 * @param skuBAList
	 * @return
	 */
	public static Map<Integer, SKUBA> convertSKUBAListToMap(List<SKUBA> skuBAList) {
		if (CollectionUtils.isEmpty(skuBAList)) {
			return null;
		}
		
		Map<Integer, SKUBA> skuBAMap = new HashMap<Integer, SKUBA>(
				skuBAList.size());

		for (SKUBA skuba : skuBAList) {
			skuBAMap.put(skuba.getSkuBAId(), skuba);
		}
		return skuBAMap;
	}

	/**
	 * Converts an SKUBA list to a Map
	 * @param skuBAList
	 * @return
	 */
	public static Map<CompositeKey<Integer>, SKUBA> convertToSKUIdMap(
			List<SKUBA> skuBAList) {
		Map<CompositeKey<Integer>, SKUBA> skuBAMap = new HashMap<CompositeKey<Integer>, SKUBA>();

		if (CollectionUtils.isEmpty(skuBAList)) {
			return skuBAMap;
		}

		for (SKUBA skuba : skuBAList) {
			if (skuba != null) {
				CompositeKey<Integer> compositeKey = new CompositeKey<Integer>(
						skuba.getSkuId(), skuba.getSkuBAId());
				skuBAMap.put(compositeKey, skuba);
			}
		}
		return skuBAMap;
	}
	
	/**
	 * Converts a list of orderItems to a Map<keyString, OrderItem> where
	 * keyString = Order ID concatenated with SKU ID
	 * 
	 * @param orderItems
	 * @return
	 */
	public static Map<String, OrderItem> convertToOrderItemsMap(List<OrderItem> orderItems) {
		Map<String, OrderItem> orderItemMap = new HashMap<String, OrderItem>();
		
		if (CollectionUtils.isEmpty(orderItems)) {
			return null;
		}
		
		for (OrderItem orderItem : orderItems) {
			orderItemMap.put(formatOrderItemKey(orderItem.getOrderId(), orderItem.getSKUId()), orderItem);
		}
		return orderItemMap;
	}
	
	/**
	 * Formats a given order id and sku id as key to a map  <orderid>+"_"+<skuid>
	 * @param orderId
	 * @param skuId
	 * @return
	 */
	public static String formatOrderItemKey(Integer orderId, Integer skuId){
		return String.format("%s_%s", orderId.toString(), skuId.toString());
	}

	/**
	 * Converts the list of Orders into a  Map with Key=Id
	 * @param orders
	 * @return
	 */
	public static Map<Integer, Order> convertToOrderIdMap(List<Order> orders) {
		Map<Integer, Order> orderMap = new HashMap<Integer, Order>();
		if (orders == null) return orderMap;
		for (Order order : orders) {
			orderMap.put(order.getOrderId(), order);
		}
		return orderMap;
	}
	
	
	public static void createCompanyColumns(HttpServletRequest request, String buyerCompanyIds,
			String buyerUserIds, String sellerUserIds, String deliveryDate,
			Map<String, Map<String, List<Integer>>> dateSellerBuyersDPMap) {
		OrderSheetUtil.createCompanyColumns(request, buyerCompanyIds, buyerUserIds,
				sellerUserIds, deliveryDate, null, dateSellerBuyersDPMap);
	}

	public static void createCompanyColumns(HttpServletRequest request, String buyerCompanyIds,
			String buyerUserIds, String sellerUserIds, String deliveryDate, List<Integer> memberUserIds,
			Map<String, Map<String, List<Integer>>> dateSellerBuyersDPMap) {
		List<Integer> buyerIds = OrderSheetUtil.toList(buyerUserIds);
		List<Integer> sellerIds = OrderSheetUtil.toList(sellerUserIds);

		OrderSheetUtil.createCompanyColumns(request, buyerCompanyIds, buyerIds,
				sellerIds, deliveryDate, null, dateSellerBuyersDPMap);
		
	}
	public static void createCompanyColumns(HttpServletRequest request, String buyerCompanyIds,
			List<Integer> buyerIds, List<Integer> sellerIds, String deliveryDate, List<Integer> memberUserIds,
			Map<String, Map<String, List<Integer>>> dateSellerBuyersDPMap) {

		Map<Integer, List<User>> companyMap = new HashMap<Integer, List<User>>();
		Map<String, User> buyerMap = new HashMap<String, User>();
		List<Company> companyList = new ArrayList<Company>();
		List<Company> companyObjs = CompanyUtil.toCompanyObjs(OrderSheetUtil.toList(buyerCompanyIds));

		//List<Integer> buyerIds = OrderSheetUtil.toList(buyerUserIds);
		//List<Integer> sellerIds = OrderSheetUtil.toList(sellerUserIds);
		Map<String, List<Integer>> sellerBuyersDPMap = dateSellerBuyersDPMap.get(deliveryDate);
		
		for (Company companyObj : companyObjs) {
			Integer companyId = companyObj.getCompanyId();
			
			/* start check dealing pattern status of buyer */
			List<Integer> validBuyerIds = new ArrayList<Integer>();
			//move out of the loop to prevent frequent creation
//			List<Integer> buyerIds = OrderSheetUtil.toList(buyerUserIds);
//			List<Integer> sellerIds = OrderSheetUtil.toList(sellerUserIds);
//			Map<String, List<Integer>> sellerBuyersDPMap = dateSellerBuyersDPMap.get(deliveryDate);
			for (Integer sellerId : sellerIds) {
				List<Integer> DPbuyerIds = sellerBuyersDPMap.get(sellerId.toString());
				
				if(DPbuyerIds != null){
					for (Integer buyerId : buyerIds) {
						if (DPbuyerIds.contains(buyerId))
							if (memberUserIds == null ) {
								/* add to valid buyers list */
								if (!validBuyerIds.contains(buyerId))
									validBuyerIds.add(buyerId);
							}
							else if (memberUserIds.contains(buyerId)) { //not null and contains buyerId
								/* add to valid buyers list */
								if (!validBuyerIds.contains(buyerId))
									validBuyerIds.add(buyerId);
							}
					}	
				}
					
			}
			/* end check dealing pattern status - buyer is not included no DP */
			
			List<User> buyerObjs = UserUtil.toUserObjs(validBuyerIds, companyId, buyerIds);
			
			for (User buyerObj : buyerObjs) {
				buyerMap.put(buyerObj.getUserId().toString(), buyerObj);
			}
			if (buyerObjs.size() > 0) {
				companyMap.put(companyId, buyerObjs);
				companyList.add(companyObj);
			}
		}
				
		SessionHelper.setAttribute(request, "companyMap", companyMap);
		SessionHelper.setAttribute(request, "companyList", companyList);
		SessionHelper.setAttribute(request, "buyerMap", buyerMap);
		/**/
	}
	
	
	public static void createCompanyColumns(HttpServletRequest request, String buyerCompanyIds,
			String buyerUserIds, String sellerUserIds, String deliveryDate,  List<Integer> memberUserIds,
			Map<String, Map<String, List<Integer>>> dateSellerBuyersDPMap, List<Order> selectedOrders,
			Map<String, Map<String, List<Integer>>> buyerToSellerDPMap) {

		List<Integer> buyerIds = OrderSheetUtil.toList(buyerUserIds);
		List<Integer> sellerIds = OrderSheetUtil.toList(sellerUserIds);
		createCompanyColumns(request, buyerCompanyIds, buyerIds, sellerIds,
				deliveryDate, memberUserIds, dateSellerBuyersDPMap,
				selectedOrders, buyerToSellerDPMap);

	}
	/**
	 * Create company columns by storing the data in the Session. 
	 * This method includes publish indicators
	 * 
	 * @param request
	 * @param buyerCompanyIds
	 * @param buyerUserIds
	 * @param sellerUserIds
	 * @param deliveryDate
	 * @param memberUserIds
	 * @param dateSellerBuyersDPMap
	 * @param selectedOrders
	 * @param buyerToSellerDPMap
	 */
	public static void createCompanyColumns(HttpServletRequest request, String buyerCompanyIds,
			List<Integer> buyerIds, List<Integer> sellerIds, String deliveryDate,  List<Integer> memberUserIds,
			Map<String, Map<String, List<Integer>>> dateSellerBuyersDPMap, List<Order> selectedOrders,
			Map<String, Map<String, List<Integer>>> buyerToSellerDPMap) {
		
		Map<Integer, List<User>> companyMap = new HashMap<Integer, List<User>>();
		Map<String, User> buyerMap = new HashMap<String, User>();
		List<Company> companyList = new ArrayList<Company>();
		List<Company> companyObjs = CompanyUtil.toCompanyObjs(OrderSheetUtil.toList(buyerCompanyIds));
		//List<Integer> buyerIds = OrderSheetUtil.toList(buyerUserIds);
		//List<Integer> sellerIds = OrderSheetUtil.toList(sellerUserIds);
		Map<String, List<Integer>> sellerBuyersDPMap = dateSellerBuyersDPMap.get(deliveryDate);

		for (Company companyObj : companyObjs) {
			Integer companyId = companyObj.getCompanyId();
			
			/* start check dealing pattern status of buyer */
			List<Integer> validBuyerIds = new ArrayList<Integer>();
			//moved out of the loop to prevent frequent creation for each company
			//List<Integer> buyerIds = OrderSheetUtil.toList(buyerUserIds);
			//List<Integer> sellerIds = OrderSheetUtil.toList(sellerUserIds);
			//Map<String, List<Integer>> sellerBuyersDPMap = dateSellerBuyersDPMap.get(deliveryDate);
			for (Integer sellerId : sellerIds) {
				List<Integer> DPbuyerIds = sellerBuyersDPMap.get(sellerId.toString());
				
				if(DPbuyerIds != null){
					for (Integer buyerId : buyerIds) {
						if (DPbuyerIds.contains(buyerId))
							if (memberUserIds == null ) {
								/* add to valid buyers list */
								if (!validBuyerIds.contains(buyerId))
									validBuyerIds.add(buyerId);
							}
							else if (memberUserIds.contains(buyerId)) { //not null and contains buyerId
								/* add to valid buyers list */
								if (!validBuyerIds.contains(buyerId))
									validBuyerIds.add(buyerId);
							}
					}	
				}
					
			}
			/* end check dealing pattern status - buyer is not included no DP */
			
			List<User> buyerObjs = UserUtil.toUserObjs(validBuyerIds, companyId, buyerIds);
			
			for (User buyerObj : buyerObjs) {
				
				/* ogie: add publish mark - nov172010 */
				Map<String, List<Integer>> buyerSellerDPMap = buyerToSellerDPMap.get(deliveryDate);
				Integer buyerId = buyerObj.getUserId();
				List<Integer> sellers = buyerSellerDPMap.get(buyerObj.getUserId().toString());
								
				if(OrderSheetUtil.isBuyerPublished(selectedOrders, sellers, buyerId, null)){
					buyerObj.setVerticalNameWithMark("style='background-repeat: repeat' " 
							+ "background='../../common/img/published.gif'");
							//+ "<span class='publishedMark'>"
							//+ MessageUtil.getPropertyMessage(MessageUtil.publishMark)
							//+ "</span>");
				}				
				buyerMap.put(buyerObj.getUserId().toString(), buyerObj);
				/* ogie: add publish mark - nov172010 */
				
			}
			
			if (buyerObjs.size() > 0) {
				companyMap.put(companyId, buyerObjs);
				companyList.add(companyObj);
			}
		}
		
		SessionHelper.setAttribute(request, "companyMap", companyMap);
		SessionHelper.setAttribute(request, "companyList", companyList);
		SessionHelper.setAttribute(request, "buyerMap", buyerMap);
		/**/
		
	}
	
	/**
	 * Create date columns by storing the data in the Session. 
	 * This method includes publish indicators.
	 * 
	 * @param request
	 * @param osp
	 * @param selectedOrders
	 * @param dp
	 */
	public static void createDateColumns(HttpServletRequest request, OrderSheetParam osp, 
			List<Order> selectedOrders, DealingPattern dp) {
		String startDate = osp.getStartDate();
		String endDate = osp.getEndDate();
		if (endDate.length() != 8) endDate = startDate;
		
		//System.out.println("startDate:[" + startDate +"]");
		//System.out.println("endDate:[" + endDate +"]");
		
		Map<Object, Object> dateMap = new HashMap<Object, Object>();
		Map<Object, Object> dateMarkMap = new HashMap<Object, Object>();
		List<String> dateList = DateFormatter.getDateList(startDate, endDate);
		
		if(dateList !=null && !dateList.isEmpty()){
			for (String date: dateList) {
				String value = date.substring(4, 6) + "/" + date.substring(6);
				
				/* ogie: add publish mark - nov172010 */
				if(osp.getSheetTypeId().equals(SheetTypeConstants.SELLER_ORDER_SHEET) ||
				   osp.getSheetTypeId().equals(SheetTypeConstants.SELLER_ADMIN_ORDER_SHEET)){
					
					Integer buyerId = new Integer(osp.getDatesViewBuyerID());
					Map<String, List<Integer>> buyerSellerDPMap = dp.getBuyerToSellerDPMap().get(date);
					List<Integer> sellers = buyerSellerDPMap.get(osp.getDatesViewBuyerID());				
									
					if(OrderSheetUtil.isBuyerPublished(selectedOrders, sellers, buyerId, date)){						 
//						value = value + "<br><span class='publishedMark'>" 
//						              + MessageUtil.getPropertyMessage(MessageUtil.publishMark)
//						              + "</span>";
						dateMarkMap.put(date, 
								"style='background-repeat: repeat'"
								+ "background='../../common/img/published.gif'");
					}
				}
				/* ogie: add publish mark - nov172010 */
				dateMap.put(date, value);
			}
			
		}
		
		SessionHelper.setAttribute(request, "dateList", dateList);
		SessionHelper.setAttribute(request, "dateMap", dateMap);
		SessionHelper.setAttribute(request, "dateMarkMap", dateMarkMap);
	}
	
	public static List<Integer> getSkuIds(List<? extends SKU> objList){
		List<Integer> skuIdList = new ArrayList<Integer>();
		
		for(SKU sku:objList){
			skuIdList.add(sku.getSkuId());
		}
		
		if (skuIdList.size() == 0) skuIdList.add(999999999);
		
		return skuIdList;
	}
	
	public static List<Integer> getAkadenSkuIds(List<AkadenSKU> objList){
		List<Integer> skuIdList = new ArrayList<Integer>();
		
		for(AkadenSKU sku:objList){
			skuIdList.add(sku.getSkuId());
		}
		
		if (skuIdList.size() == 0) skuIdList.add(999999999);
		
		return skuIdList;
	}
	
	/**
	 * Method is used accross all sheet, function and modules.
	 * 
	 * TODO: May need refactoring during Billing and Akaden Impl
	 * 
	 * One key (w/o skuBaId concatination) [Seller]:
	 * itemMap
	 * <skuId> (String)
	 *   <delivery date> (String)
	 *     <buyer> (Integer)
	 *       Item
	 * 
	 * Two key [Buyer]:
	 * itemMap
	 * <skuId>_<skuBaId> (String)
	 *   <delivery date> (String)
	 *     <buyer> (Integer)
	 *       Item
	 * */
	
	public static Map<String, Map<String, Map<Integer, Item>>>
		convertToItemsBulkMap(List<Item> itemList, Boolean twoKey) {
		
		if (twoKey == null) {
			twoKey = Boolean.FALSE;
		}
		
		Map<String, Map<String, Map<Integer, Item>>> itemMap =
			new HashMap<String, Map<String, Map<Integer, Item>>>();
		
		for(Item oi : itemList) {
			String key1 = (twoKey == true) ? oi.getSKUId() + "_" + oi.getSKUBAId() : oi.getSKUId().toString();

			String deliveryDate = oi.getDeliveryDate();
			Integer buyerId = oi.getBuyerId();
			
			Map<String, Map<Integer, Item>> deliveryDateMap = null;
			if (!itemMap.containsKey(key1)) {
				deliveryDateMap = new HashMap<String, Map<Integer, Item>>();
			} else {
				deliveryDateMap = itemMap.get(key1);
			}
			Map<Integer, Item> buyerMap = null;
			if (!deliveryDateMap.containsKey(deliveryDate)) {
				buyerMap = new HashMap<Integer, Item>();				
			} else {
				buyerMap = deliveryDateMap.get(deliveryDate);
			}
			
			buyerMap.put(buyerId, oi);
			deliveryDateMap.put(deliveryDate, buyerMap);
			itemMap.put(key1, deliveryDateMap);
		}
		
		return itemMap;
	}
	
	/**
	 * @deprecated To be removed. Follow SheetDataServiceImpl.
	 * @param itemList
	 * @return
	 */
	public static Map<Integer, Map<String, Map<Integer, OrderItem>>>
	convertToOrderItemsBulkMap(List<OrderItem> itemList) {
		
		Map<Integer, Map<String, Map<Integer, OrderItem>>> itemMap =
			new HashMap<Integer, Map<String, Map<Integer, OrderItem>>>();
		/*itemMap
		 * <skuId>
		 *   <delivery date>
		 *     <buyer>
		 *       OrderItem
		 * */
		
		for(OrderItem oi : itemList){
			Integer skuId = oi.getSku().getSkuId();
			String deliveryDate = oi.getOrder().getDeliveryDate();
			Integer buyerId = oi.getOrder().getBuyerId();
			
			Map<String, Map<Integer, OrderItem>> deliveryDateMap = null;
			if (!itemMap.containsKey(skuId)) {
				deliveryDateMap = new HashMap<String, Map<Integer, OrderItem>>();
			} else {
				deliveryDateMap = itemMap.get(skuId);
			}
			Map<Integer, OrderItem> buyerMap = null;
			if (!deliveryDateMap.containsKey(deliveryDate)) {
				buyerMap = new HashMap<Integer, OrderItem>();				
			} else {
				buyerMap = deliveryDateMap.get(deliveryDate);
			}
			
			buyerMap.put(buyerId, oi);
			deliveryDateMap.put(deliveryDate, buyerMap);
			itemMap.put(skuId, deliveryDateMap);
		}
		
		return itemMap;
	}
	
	/**
	 * @deprecated To be removed. Follow SheetDataServiceImpl.
	 * @param itemList
	 * @return
	 */
	public static Map<String, Map<String, Map<Integer, OrderItem>>>
		convertToBuyerOrderItemsBulkMap(List<OrderItem> itemList) {
		
		Map<String, Map<String, Map<Integer, OrderItem>>> itemMap =
			new HashMap<String, Map<String, Map<Integer, OrderItem>>>();
		/*itemMap
		 * <skuId>_<skuBaId>
		 *   <delivery date>
		 *     <buyer>
		 *       OrderItem
		 * */
		
		for(OrderItem oi : itemList) {
			//Integer skuId = oi.getSku().getSkuId();
			String key1 = oi.getSku().getSkuId() + "_" + oi.getSkuBaId();
			String deliveryDate = oi.getOrder().getDeliveryDate();
			Integer buyerId = oi.getOrder().getBuyerId();
			
			Map<String, Map<Integer, OrderItem>> deliveryDateMap = null;
			if (!itemMap.containsKey(key1)) {
				deliveryDateMap = new HashMap<String, Map<Integer, OrderItem>>();
			} else {
				deliveryDateMap = itemMap.get(key1);
			}
			Map<Integer, OrderItem> buyerMap = null;
			if (!deliveryDateMap.containsKey(deliveryDate)) {
				buyerMap = new HashMap<Integer, OrderItem>();				
			} else {
				buyerMap = deliveryDateMap.get(deliveryDate);
			}
			
			buyerMap.put(buyerId, oi);
			deliveryDateMap.put(deliveryDate, buyerMap);
			itemMap.put(key1, deliveryDateMap);
		}
		
		return itemMap;
	}
	
	/**
	 * @deprecated To be removed. Follow SheetDataServiceImpl.
	 * @param itemList
	 * @return
	 */
	public static Map<String, Map<String, Map<Integer, Item>>>
		convertToOrderItemsBulkMapCSV(List<Item> itemList) {
		
		Map<String, Map<String, Map<Integer, Item>>> itemMap =
			new HashMap<String, Map<String, Map<Integer, Item>>>();
		/*itemMap
		 * <skuId>
		 *   <delivery date>
		 *     <buyer>
		 *       OrderItem
		 * */
		
		for(Item oi : itemList){
			String skuId = oi instanceof AkadenItem ? oi.getAkadenSKUId().toString() : oi.getSKUId().toString();
			String key = oi instanceof AkadenItem ? "A_" + oi.getAkadenSKUId() : skuId;
			String deliveryDate = oi.getDeliveryDate();
			Integer buyerId = oi.getBuyerId();
			
			Map<String, Map<Integer, Item>> deliveryDateMap = null;
			if (!itemMap.containsKey(skuId)) {
				deliveryDateMap = new HashMap<String, Map<Integer, Item>>();
			} else {
				deliveryDateMap = itemMap.get(skuId);
			}
			Map<Integer, Item> buyerMap = null;
			if (!deliveryDateMap.containsKey(deliveryDate)) {
				buyerMap = new HashMap<Integer, Item>();				
			} else {
				buyerMap = deliveryDateMap.get(deliveryDate);
			}
			
			buyerMap.put(buyerId, oi);
			deliveryDateMap.put(deliveryDate, buyerMap);
			
//			String key = skuId;
//			if (oi instanceof AkadenItem) {
//				key = "A_" + oi.getAkadenSKUId();
//			}
			itemMap.put(key, deliveryDateMap);
		}
		
		return itemMap;
	}
	
	/**
	 * @deprecated To be removed. Follow SheetDataServiceImpl.
	 * @param itemList
	 * @return
	 */
	// TODO: Merge to seller's convertToOrderItemsBulkMapCSV
	public static Map<String, Map<String, Map<Integer, Item>>>
		convertToOrderItemsBulkMapCSVBuyer(List<Item> itemList) {
		
		Map<String, Map<String, Map<Integer, Item>>> itemMap =
			new HashMap<String, Map<String, Map<Integer, Item>>>();
		
		/*itemMap
		 * <skuId>_<skuBaId>
		 *   <delivery date>
		 *     <buyer>
		 *       OrderItem
		 * */
		
		for(Item oi : itemList){
			String key1 = oi.getSKUId() + "_" + oi.getSKUBAId();
			
			String deliveryDate = oi.getDeliveryDate();
			Integer buyerId = oi.getBuyerId();
			
			Map<String, Map<Integer, Item>> deliveryDateMap = null;
			if (!itemMap.containsKey(key1)) {
				deliveryDateMap = new HashMap<String, Map<Integer, Item>>();
			} else {
				deliveryDateMap = itemMap.get(key1);
			}
			Map<Integer, Item> buyerMap = null;
			if (!deliveryDateMap.containsKey(deliveryDate)) {
				buyerMap = new HashMap<Integer, Item>();				
			} else {
				buyerMap = deliveryDateMap.get(deliveryDate);
			}
			
			buyerMap.put(buyerId, oi);
			deliveryDateMap.put(deliveryDate, buyerMap);
			
			itemMap.put(key1, deliveryDateMap);
		}
		
		return itemMap;
	}
	
	/**
	 * @deprecated To be removed. Follow SheetDataServiceImpl.
	 * @param itemList
	 * @return
	 */
	public static Map<Integer, Map<String, Map<Integer, BillingItem>>>
	convertToBillingItemsBulkMap(List<BillingItem> itemList) {
		
		Map<Integer, Map<String, Map<Integer, BillingItem>>> itemMap =
			new HashMap<Integer, Map<String, Map<Integer, BillingItem>>>();
		/*itemMap
		 * <skuId>
		 *   <delivery date>
		 *     <buyer>
		 *       BillingItem
		 * */
		
		for(BillingItem oi : itemList){
			Integer skuId = oi.getSku().getSkuId();
			String deliveryDate = oi.getOrder().getDeliveryDate();
			Integer buyerId = oi.getOrder().getBuyerId();
			
			Map<String, Map<Integer, BillingItem>> deliveryDateMap = null;
			if (!itemMap.containsKey(skuId)) {
				deliveryDateMap = new HashMap<String, Map<Integer, BillingItem>>();
			} else {
				deliveryDateMap = itemMap.get(skuId);
			}
			Map<Integer, BillingItem> buyerMap = null;
			if (!deliveryDateMap.containsKey(deliveryDate)) {
				buyerMap = new HashMap<Integer, BillingItem>();				
			} else {
				buyerMap = deliveryDateMap.get(deliveryDate);
			}
			
			buyerMap.put(buyerId, oi);
			deliveryDateMap.put(deliveryDate, buyerMap);
			itemMap.put(skuId, deliveryDateMap);
		}
		
		return itemMap;
	}
	
	/**
	 * @deprecated To be removed. Follow SheetDataServiceImpl.
	 * @param itemList
	 * @return
	 */
	public static Map<String, Map<String, Map<Integer, OrderReceived>>>
	convertToReceivedItemsBulkMap(List<OrderReceived> itemList) {
		
		Map<String, Map<String, Map<Integer, OrderReceived>>> itemMap =
			new HashMap<String, Map<String, Map<Integer, OrderReceived>>>();
		/*itemMap
		 * <skuId>_<skuBaId>
		 *   <delivery date>
		 *     <buyer>
		 *       OrderReceived
		 * */
		
		for(OrderReceived oi : itemList){
			String key1 = oi.getSkuId() + "_" + oi.getSkuBaId();
			String deliveryDate = oi.getOrder().getDeliveryDate();
			Integer buyerId = oi.getOrder().getBuyerId();
			
			Map<String, Map<Integer, OrderReceived>> deliveryDateMap = null;
			if (!itemMap.containsKey(key1)) {
				deliveryDateMap = new HashMap<String, Map<Integer, OrderReceived>>();
			} else {
				deliveryDateMap = itemMap.get(key1);
			}
			Map<Integer, OrderReceived> buyerMap = null;
			if (!deliveryDateMap.containsKey(deliveryDate)) {
				buyerMap = new HashMap<Integer, OrderReceived>();				
			} else {
				buyerMap = deliveryDateMap.get(deliveryDate);
			}
			
			buyerMap.put(buyerId, oi);
			deliveryDateMap.put(deliveryDate, buyerMap);
			itemMap.put(key1, deliveryDateMap);
		}
		
		return itemMap;
	}
	
	/**
	 * @deprecated To be removed. Follow SheetDataServiceImpl.
	 * @param itemList
	 * @return
	 */
	public static Map<Integer, Map<String, Map<Integer, AkadenItem>>>
	convertToAkadenItemsBulkMap(List<AkadenItem> itemList) {
		
		Map<Integer, Map<String, Map<Integer, AkadenItem>>> itemMap =
			new HashMap<Integer, Map<String, Map<Integer, AkadenItem>>>();
		/*itemMap
		 * <skuId>
		 *   <delivery date>
		 *     <buyer>
		 *       AkadenItem
		 * */
		
		for(AkadenItem oi : itemList){
			Integer skuId = oi.getAkadenSku().getAkadenSkuId();
			String deliveryDate = oi.getOrder().getDeliveryDate();
			Integer buyerId = oi.getOrder().getBuyerId();
			
			Map<String, Map<Integer, AkadenItem>> deliveryDateMap = null;
			if (!itemMap.containsKey(skuId)) {
				deliveryDateMap = new HashMap<String, Map<Integer, AkadenItem>>();
			} else {
				deliveryDateMap = itemMap.get(skuId);
			}
			Map<Integer, AkadenItem> buyerMap = null;
			if (!deliveryDateMap.containsKey(deliveryDate)) {
				buyerMap = new HashMap<Integer, AkadenItem>();				
			} else {
				buyerMap = deliveryDateMap.get(deliveryDate);
			}
			
			buyerMap.put(buyerId, oi);
			deliveryDateMap.put(deliveryDate, buyerMap);
			itemMap.put(skuId, deliveryDateMap);
		}
		
		return itemMap;
	}
	
	/**
	 * Check if buyer is published for all given orders, sellers and/or a date
	 * 
	 * 
	 * @param selectedOrders
	 * @param sellers
	 * @param buyerId
	 * @param date
	 * @return true or false
	 */
	public static boolean isBuyerPublished(List<Order> selectedOrders, List<Integer> sellers, 
			Integer buyerId, String date){
		boolean allPublished = true;
		
		// ENHANCEMENT START 20120913: Lele - Redmine 880
		//if(sellers.isEmpty()) return false;
		if(sellers == null || sellers.isEmpty()) return false;
		// ENHANCEMENT END 20120913:
		
		for (Integer sellerId : sellers){
			for (Order order : selectedOrders) {
				
				// check an order if matched with buyer and seller
				if(order.getBuyerId().equals(buyerId) &&
				   order.getSellerId().equals(sellerId)){
					
					// skip if no date given
					if(date != null){
						if(!order.getDeliveryDate().equals(date)) continue;
					}
					
					// check the particular order if published
					boolean published = !NumberUtil.isNullOrZero(order.getOrderPublishedBy());
					
					// if an order is unpublished then exits and return false
					if(!published) {
						allPublished = false;
						break;
					}
				}
			}
			if(allPublished == false) return allPublished;
		}
		return allPublished;
	}

	public static List<Integer> getOrderIdList(List<Order> allOrders) {

		List<Integer> orderIds = new ArrayList<Integer>();
		if (CollectionUtils.isEmpty(allOrders)) {
			return orderIds;
		}
		for (Order order : allOrders) {
			Integer orderId = order.getOrderId();
			if (!orderIds.contains(orderId)) {
				orderIds.add(orderId);
			}
		}
		return orderIds;
	}

	public static List<Integer> filterOrderIdListFromOrderDetails(List<Order> allOrders, OrderDetails orderDetails) {

		List<Integer> orderIds = new ArrayList<Integer>();
		if (CollectionUtils.isEmpty(allOrders)) {
			return orderIds;
		}
		
		for (Order order : allOrders) {
			if (orderDetails.isAllDatesView()) {
				// all dates view: get the buyer id and skip records that are
				// not equivalent to buyer id
				Integer datesViewBuyerID = orderDetails.getDatesViewBuyerID();
				if (!datesViewBuyerID.equals(order.getBuyerId())) {
					continue;
				}
			} else {
				// if not all dates view filter based on orderdate. Skip records
				// that are not equivalent to delivery date
				String deliveryDate = orderDetails.getDeliveryDate();
				if (!deliveryDate.equalsIgnoreCase(order.getDeliveryDate())) {
					continue;
				}
			}
			Integer orderId = order.getOrderId();
			if (!orderIds.contains(orderId)) {
				orderIds.add(orderId);
			}
		}
		return orderIds;
	}

	/**
	 * returns a list that contains the skuIds only
	 * 
	 * @param skuList
	 * @return
	 */
	public static List<Integer> getSKUIds(List<SKU> skuList) {
		List<Integer> skuIds = new ArrayList<Integer>();

		for (SKU sku : skuList) {
			skuIds.add(sku.getSkuId());
		}
		return skuIds;
	}

	public static List<Integer> getUserIdList(List<User> userIds){
		List<Integer> userIdList = new ArrayList<Integer>();
		for (User userId : userIds) {
			userIdList.add(userId.getUserId());
		} 
		return userIdList;
	}
	
	public static boolean isItemExisting(String skuId, String deliveryDate, Integer buyerId,
			List<Item> akadenItems) {
		
		boolean result = false;
		
		for (Item item : akadenItems) {
			AkadenItem _item = (AkadenItem) item;
			if (_item.getSKUId().toString().equals(skuId) &&
				_item.getDeliveryDate().equals(deliveryDate) &&
				_item.getBuyerId().equals(buyerId)) {
				
				result = true;
				break;
			}
				
		}
		
		return result;
	}

	/*
	 * This method is used to get either:
	 * 1. PROFIT PERCENTAGE display per SKU; if called from load controller
	 * 2. TOTALS or GRAND TOTALS display of PriceWithoutTax, PriceWithTax, SellingPrice, 
	 *    Profit and Profit Percentage; if called from BuyerOrderService
	 */
	public static ProfitInfo computeProfitInfo(
			BigDecimal priceWithoutTax,
			BigDecimal priceWithTax,
			BigDecimal sellingPrice,
			BigDecimal packageQuantity,
			BigDecimal rowQty,
			String userPriceTaxOpt,
			boolean withPackageQuantity) {
		
		BigDecimal price = new BigDecimal(0);
		BigDecimal profit = new BigDecimal(0);
		BigDecimal profitPercent = new BigDecimal(0);
		ProfitInfo profitInfo = new ProfitInfo();
		BigDecimal sellPrice = new BigDecimal(0);
		
		if (rowQty != null){
			//Total Price w/o Tax
			BigDecimal pWithouTax = NumberUtil.nullToZero(priceWithoutTax);
			profitInfo.setPriceWithoutTax((pWithouTax.multiply(rowQty).setScale(0, BigDecimal.ROUND_HALF_UP)));
			
			//Total Price w/ Tax
			BigDecimal pWithTax = NumberUtil.nullToZero(priceWithTax);
			profitInfo.setPriceWithTax((pWithTax.multiply(rowQty)).setScale(0, BigDecimal.ROUND_HALF_UP));
		
			//For PRICE_TAX_OPTION >>> 1 = Price with Tax    0 = Price without Tax
			if(userPriceTaxOpt.equals("1")){
				price = NumberUtil.nullToZero(priceWithTax);
			}else{
				price = NumberUtil.nullToZero(priceWithoutTax);
			}
			
			if (sellingPrice != null){
			
				sellingPrice = NumberUtil.nullToZero(sellingPrice);
				packageQuantity = NumberUtil.nullToZero(packageQuantity);
				
				//FORMULA: Profit = (Selling Price - Price / Packing Quantity) * Packing Quantity * Row Quantity)
				if(packageQuantity.compareTo(new BigDecimal(0)) == 1){
					/*Round off every time multiplication and division was applied
					profit = (((sellingPrice.subtract(price.divide(packageQuantity, 1, BigDecimal.ROUND_HALF_UP).setScale(1,BigDecimal.ROUND_HALF_UP)))
									.multiply(packageQuantity).setScale(1,BigDecimal.ROUND_HALF_UP))
									.multiply(rowQty)).setScale(0,BigDecimal.ROUND_HALF_UP);*/
					/*Round off at the very end*/
					/*profit = (((sellingPrice.subtract(price.divide(packageQuantity)))
									.multiply(packageQuantity))
									.multiply(rowQty));*/
					profit = new BigDecimal (
							(sellingPrice.doubleValue() - price.doubleValue() / packageQuantity.doubleValue()) *
							packageQuantity.doubleValue() * rowQty.doubleValue()
						).setScale(0, BigDecimal.ROUND_HALF_UP);
			
				}
				
				//FORMULA: Profit Percentage = gross profit amount/(selling price * packing quantity * Row Quantity)*100
				/*Round off every time multiplication and division was applied
				sellPrice = (sellingPrice.multiply(packageQuantity).setScale(1,BigDecimal.ROUND_HALF_UP))
										.multiply(rowQty).setScale(0,BigDecimal.ROUND_HALF_UP);*/
				/*Round off at the very end*/
				if(withPackageQuantity){
					sellPrice = sellingPrice.multiply(packageQuantity).multiply(rowQty).setScale(0,BigDecimal.ROUND_HALF_UP);
				} else {
					sellPrice = sellingPrice.multiply(rowQty).setScale(0,BigDecimal.ROUND_HALF_UP);
				}
			
				
				if(sellPrice.compareTo(new BigDecimal(0)) == 1){
					Double divProfit = (profit.doubleValue()/sellPrice.doubleValue())*100;
					profitPercent = new BigDecimal(divProfit).setScale(1,BigDecimal.ROUND_HALF_UP);
					/*BigDecimal sellPrice = sellingPrice.multiply(packingQty).multiply(rowQty);
					profitPercent = (profit.divide((sellingPrice.multiply(packingQty).multiply(rowQty)), 4, BigDecimal.ROUND_HALF_UP))
									.multiply(new BigDecimal(100)).setScale(1,BigDecimal.ROUND_HALF_UP);*/
				}

				profitInfo.setProfit(profit);
				profitInfo.setSellingPrice(sellPrice);
				profitInfo.setProfitPercentage(profitPercent);
			}else{
				profitInfo.setProfitPercentage(null);
			}
		}else{
			profitInfo.setProfitPercentage(null);
		}
		
		return profitInfo;
	}

	public enum OrderMapField {
		DATE(0), BUYER(1), SELLER(2);
		
		private int position;
		private OrderMapField(int pos){
			position = pos;
		}
		public int getPosition(){
			return position;
		}
	};
	
	public static String extractDataFromOrderMapKey(String orderMapKey, OrderMapField field) {
		if (StringUtils.isBlank(orderMapKey)){
			return StringUtils.EMPTY;
		}
		
		String[] split = StringUtils.split(orderMapKey, "_");
		return split[field.getPosition()];
	}

	/**
	 * Central convenience method that formats the Order Map keys
	 * @param deliveryDate
	 * @param buyerId
	 * @param sellerId
	 * @return
	 */
	public static String formatOrderMapKey(String deliveryDate,
			String buyerId, String sellerId) {
		return String.format("%s_%s_%s", deliveryDate, buyerId, sellerId);
	}

	/**
	 * Variation of convenience method that formats the Order Map keys
	 * @param deliveryDate
	 * @param buyerId
	 * @param sellerId
	 * @return
	 */
	public static String formatOrderMapKey(Date deliveryDate,
			Integer buyerId, Integer sellerId) {
		
		String deliveryDateString = DateFormatter.convertToString(deliveryDate);
		String buyerIdString = buyerId.toString();
		String sellerIdString = sellerId.toString();
		return formatOrderMapKey(deliveryDateString, buyerIdString, sellerIdString);
	}

	/**
	 * Variation of convenience method that formats the Order Map keys
	 * @param deliveryDate
	 * @param buyerId
	 * @param sellerId
	 * @return
	 */
	public static String formatOrderMapKey(String deliveryDate,
			Integer buyerId, Integer sellerId) {
		
		String buyerIdString = buyerId.toString();
		String sellerIdString = sellerId.toString();
		return formatOrderMapKey(deliveryDate, buyerIdString, sellerIdString);
	}

	
	public static List<Order> extractFilteredOrders(Map<String, Order> allOrdersMap,
			List<Integer> sellerIds, List<String> allDeliveryDates,
			List<Integer> allBuyerIds) {
		
		List<Order> filteredOrderList = new ArrayList<Order>();
		for (String deliveryDate : allDeliveryDates) {
			for (Integer buyerId : allBuyerIds) {
				String sBuyerId = buyerId.toString();
				for (Integer sellerId : sellerIds) {
					String sSellerId = sellerId.toString();
					Order order = allOrdersMap.get(OrderSheetUtil.formatOrderMapKey(deliveryDate, sBuyerId, sSellerId));
					if (order != null){
						filteredOrderList.add(order);
					}
				}
			}
		}
		return filteredOrderList;
	}
	
	public static StringBuilder formatListOfOrders(List<Order> orderList, Map<Integer, String> sellerMap, Map<Integer, String> buyerMap) {
		StringBuilder sbDataList = new StringBuilder();
		if (CollectionUtils.isEmpty(orderList)) {
			return sbDataList;
		}
		String delimeterStr = " ";
		for (Order order : orderList) {
			sbDataList.append(DateFormatter.formatToGUIParameter(order.getDeliveryDate()));
			sbDataList.append(delimeterStr);
			sbDataList.append(getUserName(order.getSellerId(), sellerMap));
			sbDataList.append(delimeterStr);
			sbDataList.append(getUserName(order.getBuyerId(), buyerMap));
			sbDataList.append("\n");
		}
		return sbDataList;
	}
	
	private static String getUserName(Integer sellerId, Map<Integer, String> nameMap) {
		if (MapUtils.isEmpty(nameMap)){
			return sellerId.toString();
		}
		String name = nameMap.get(sellerId);
		return StringUtil.isNullOrEmpty(name)? sellerId.toString() : name;
	}
	
	public static StringBuilder createOrderListMessage(String messageCode, List<Order> orderList,
			Map<Integer, String> sellerNameMap,
			Map<Integer, String> buyerNameMap) {
		StringBuilder sbMessage = new StringBuilder();
		String errorMsg = MessageUtil.getPropertyMessage(messageCode);
		sbMessage.append(errorMsg);
		sbMessage.append(OrderSheetUtil.formatListOfOrders(orderList, sellerNameMap,
				buyerNameMap));
		return sbMessage;
	}
	
	
	public static SKU convertToSKU(WSBuyerSKUAdd wsBuyerSKUAdd, Category category, User loginUser) {
		SKUGroup skuGroup = new SKUGroup();
		skuGroup.setSkuGroupId(wsBuyerSKUAdd.getSkuGroupId());

		SKU sku = new SKU();
		sku.setSkuId(null);
		sku.setOrigSkuId(null);
		sku.setUser(wsBuyerSKUAdd.getSeller());
		sku.setSkuGroup(skuGroup);
		sku.setOrderUnit(wsBuyerSKUAdd.getUnitOrder());
		sku.setSheetType(10007);
		sku.setSkuCategoryId(category.getCategoryId());
		sku.setProposedBy(loginUser);
		sku.setSkuName(wsBuyerSKUAdd.getSkuName());
		sku.setLocation(wsBuyerSKUAdd.getAreaOfProduction());
		sku.setMarket(wsBuyerSKUAdd.getMarket());
		sku.setGrade(wsBuyerSKUAdd.getGrade());
		sku.setClazz(wsBuyerSKUAdd.getClazz());
		sku.setPrice2(wsBuyerSKUAdd.getPrice2());
		sku.setPriceWithoutTax(wsBuyerSKUAdd.getPriceWithoutTax());
		sku.setPackageQuantity(wsBuyerSKUAdd.getPackageQuantity());
		sku.setPackageType(wsBuyerSKUAdd.getPackageType());
		//sku.setSkuMaxLimit(wsBuyerSKUAdd.getSkuMaxLimit());
		BigDecimal column01 = wsBuyerSKUAdd.getCenter();
		sku.setColumn01(column01 == null ? null : column01.intValue());
		sku.setColumn02(wsBuyerSKUAdd.getDelivery());
		sku.setColumn03(wsBuyerSKUAdd.getSale());
		sku.setColumn04(wsBuyerSKUAdd.getJan());
		sku.setColumn05(wsBuyerSKUAdd.getPackFee());
		sku.setColumn06(wsBuyerSKUAdd.getColumn01());
		sku.setColumn07(wsBuyerSKUAdd.getColumn02());
		sku.setColumn08(wsBuyerSKUAdd.getColumn03());
		sku.setColumn09(wsBuyerSKUAdd.getColumn04());
		sku.setColumn10(wsBuyerSKUAdd.getColumn05());
		sku.setColumn11(wsBuyerSKUAdd.getColumn06());
		sku.setColumn12(wsBuyerSKUAdd.getColumn07());
		sku.setColumn13(wsBuyerSKUAdd.getColumn08());
		sku.setColumn14(wsBuyerSKUAdd.getColumn09());
		sku.setColumn15(wsBuyerSKUAdd.getColumn10());
		sku.setColumn16(wsBuyerSKUAdd.getColumn11());
		sku.setColumn17(wsBuyerSKUAdd.getColumn12());
		sku.setColumn18(wsBuyerSKUAdd.getColumn13());
		sku.setColumn19(wsBuyerSKUAdd.getColumn14());
		sku.setColumn20(wsBuyerSKUAdd.getColumn15());
		return sku;
		
	}
	
	
	public static SKUBA convertToSKUBA(SKU sku, WSBuyerAddOrderSheetSKU wsBuyerSKUAdd){
		SKUBA skuBA = new SKUBA();
		skuBA.setSkuId(sku.getSkuId());
		skuBA.setPurchasePrice(wsBuyerSKUAdd.getB_purchasePrice());
		skuBA.setSellingPrice(wsBuyerSKUAdd.getB_sellingPrice());
		skuBA.setSellingUom(wsBuyerSKUAdd.getB_sellingUom());
		skuBA.setSkuComment(wsBuyerSKUAdd.getB_skuComment());
		return skuBA;
	}
	
	
	public static List<Integer> getSKUId(List<OrderItemUI> orderItemUIList) {
		if (CollectionUtils.isEmpty(orderItemUIList)) {
			return Collections.emptyList();
		}
		
		Set<Integer> skuIdSet = new HashSet<Integer>();
		for (OrderItemUI orderItemUI : orderItemUIList) {
			skuIdSet.add(orderItemUI.getSkuId());
		}
		return new ArrayList<Integer>(skuIdSet);
	}
	
	public static List<Integer> getOrderItemIdList(List<OrderItem> orderItemList) {
		if (CollectionUtils.isEmpty(orderItemList)) {
			return Collections.emptyList();
		}
		
		Set<Integer> oiIdSet = new HashSet<Integer>();
		for (OrderItem oi : orderItemList) {
			oiIdSet.add(oi.getOrderItemId());
		}
		return new ArrayList<Integer>(oiIdSet);
	}
	
	public static Map<String, Set<String>> consolidateOrdersForEmail(
			Map<Integer, User> receiverUserMap, List<Order> ordersForConsolidation, String sendTo) {
		Map<String, Set<String>> mapOfEmailToOrderDates = new HashMap<String, Set<String>>();
		//consolidate the dates per email address
		for (Order order : ordersForConsolidation) {
			Integer sendToId = null;
			if(sendTo.equalsIgnoreCase("seller")){
				sendToId= order.getSellerId();
			}else{
				sendToId= order.getBuyerId();
			}
			
			User userOrder = receiverUserMap.get(sendToId);
			String toAddress = StringUtils.isNotBlank(userOrder.getPcEmail()) ? userOrder
					.getPcEmail() : userOrder.getMobileEmail();
			
			if (StringUtils.isNotBlank(toAddress)){
				Set<String> orderDateSet = mapOfEmailToOrderDates.get(toAddress);
				if (orderDateSet == null){
					orderDateSet = new HashSet<String>();
				} 
				orderDateSet.add(order.getDeliveryDate());
				mapOfEmailToOrderDates.put(toAddress, orderDateSet);
			}
		}
		return mapOfEmailToOrderDates;
	}

	public static Map<String, List<Integer>> convertToMapOfMembersByDate(
			List<AdminMember> adminMemberList, String dateFrom, String dateTo) {
		Map<String, List<Integer>> returnMap = new HashMap<String, List<Integer>>();
		

		List<String> dateList = DateFormatter.getDateList(dateFrom, dateTo);
		for (String date : dateList) {
			Long dateLong = Long.valueOf(date);
			List<Integer> memberList = new ArrayList<Integer>();

			if (CollectionUtils.isNotEmpty(adminMemberList)) {
				for (AdminMember adminMember : adminMemberList) {
					Long startLong = Long.valueOf(adminMember.getStartDate());
					Long endLong = Long.valueOf(adminMember.getEndDate());
					if ((startLong<=dateLong)  && (dateLong <endLong)) {
						memberList.add(adminMember.getUser().getUserId());
					}
				}
			}
			
			returnMap.put(date, memberList);
			
		}
		return returnMap;
	}

	public static List<User> convertAdminMembersToUsers(
			List<AdminMember> adminMemberList) {
		List<User> returnList = new ArrayList<User>();
		
		if (CollectionUtils.isEmpty(adminMemberList)) {
			return returnList;
		}
		List<Integer> temporaryLookUp = new ArrayList<Integer>();
		for (AdminMember adminMember : adminMemberList) {
			User user = adminMember.getUser();
			Integer userId = user.getUserId();
			if (!temporaryLookUp.contains(userId)){
				temporaryLookUp.add(userId);
				returnList.add(user);
			}
		}
		return returnList;
	}	
	
	public static void setAdminMembersByDateToSession(
			DealingPatternService dealingPatternService,
			Integer dealingPatternRelationId, HttpServletRequest request,
			User user, String startDate, String endDate) {

		List<AdminMember> adminMemberList = dealingPatternService
				.getMembersByAdminIdWithStartEndDates(user.getUserId(),
						dealingPatternRelationId, startDate, endDate);

		Map<String, List<Integer>> mapOfMembersByDate = OrderSheetUtil
				.convertToMapOfMembersByDate(adminMemberList, startDate,
						endDate);

		SessionHelper.setAttribute(request,
				SessionParamConstants.MAP_OF_MEMBERS_BY_DATE,
				mapOfMembersByDate);
	}

	public static SKUBuyerQtyMap convertToSKUBuyerQtyMap(List<OrderItem> orderItems){
		SKUBuyerQtyMap result = new SKUBuyerQtyMap();
		if(!CollectionUtils.isEmpty(orderItems)){
			for(OrderItem oi : orderItems){
				result.put(oi);
			}
		}
		
		return result;
	}
	
	
	/**
	 * Converts an SKU list to a Map
	 * @param skuList
	 * @return
	 */
	public static Map<Integer, SKU> convertToSKUMap(
			List<SKU> skuList) {
		Map<Integer, SKU> skuMap = new HashMap<Integer, SKU>();

		if (CollectionUtils.isEmpty(skuList)) {
			return skuMap;
		}

		for (SKU sku : skuList) {
			if (sku != null) {
				Integer compositeKey =sku.getSkuId();
				skuMap.put(compositeKey, sku);
			}
		}
		return skuMap;
	}

	public static Map<CompositeKey<String>, List<SKU>> convertToSKUExternalIDMap(
			List<SKU> skuList) {
		Map<CompositeKey<String>, List<SKU>> skuMap = new HashMap<CompositeKey<String>,List<SKU>>();

		if (CollectionUtils.isEmpty(skuList)) {
			return skuMap;
		}

		for (SKU sku : skuList) {
			if (sku != null && !StringUtil.isNullOrEmpty(sku.getExternalSkuId())) {
				CompositeKey<String> compositeKey = new CompositeKey<String>(
						sku.getExternalSkuId());
				List<SKU> skus= skuMap.get(compositeKey);
				if(CollectionUtils.isEmpty(skus)){
					skus = new ArrayList<SKU>();
				}
				skus.add(sku);
				skuMap.put(compositeKey, skus);
			}
		}
		return skuMap;
	}
	
	
	/**
	 * Converts a list of orderItems to a Map<keyString,  List<OrderItem>> where
	 * keyString =SKU ID
	 * 
	 * @param orderItems
	 * @return
	 */
	public static Map<Integer,  List<OrderItem>> convertToSKUIDOrderItemsMap(List<OrderItem> orderItems) {
		Map<Integer, List<OrderItem>> orderItemMap = new HashMap<Integer,  List<OrderItem>>();
		
		if (CollectionUtils.isEmpty(orderItems)) {
			return null;
		}
		
		for (OrderItem orderItem : orderItems) {
			Integer key = orderItem.getSKUId();
			List<OrderItem> oiList = orderItemMap.get(key);
			if(CollectionUtils.isEmpty(oiList)){
				oiList = new ArrayList<OrderItem>();
			}
			oiList.add(orderItem);
			orderItemMap.put(key, oiList);
		}
		return orderItemMap;
	}
}
