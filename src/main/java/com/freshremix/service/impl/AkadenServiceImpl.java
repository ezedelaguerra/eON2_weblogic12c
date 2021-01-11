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
 * Mar 17, 2010		Jovino Balunan		
 */
package com.freshremix.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.freshremix.constants.AkadenSessionConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.dao.AkadenDao;
import com.freshremix.dao.AllocationDao;
import com.freshremix.dao.OrderAkadenDao;
import com.freshremix.dao.SKUDao;
import com.freshremix.dao.SKUGroupDao;
import com.freshremix.dao.UserDao;
import com.freshremix.model.AkadenItem;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.AkadenSheetParams;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.User;
import com.freshremix.service.AkadenService;
import com.freshremix.ui.model.AkadenForm;
import com.freshremix.ui.model.AkadenItemUI;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.ui.model.PageInfo;
import com.freshremix.util.AkadenSheetUtil;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author jabalunan
 * 
 */
public class AkadenServiceImpl implements AkadenService {
	private AkadenDao akadenDao;
	private SKUGroupDao skuGroupDao;
	private SKUDao skuDao;
	private OrderAkadenDao orderAkadenDao;
	private AllocationDao allocationDao;
	private UserDao usersInfoDaos;

	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}

	public void setAllocationDao(AllocationDao allocationDao) {
		this.allocationDao = allocationDao;
	}

	public void setOrderAkadenDao(OrderAkadenDao orderAkadenDao) {
		this.orderAkadenDao = orderAkadenDao;
	}

	public void setAkadenDao(AkadenDao akadenDao) {
		this.akadenDao = akadenDao;
	}

	public void setSkuDao(SKUDao skuDao) {
		this.skuDao = skuDao;
	}

	public void setSkuGroupDao(SKUGroupDao skuGroupDao) {
		this.skuGroupDao = skuGroupDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> loadAkadenItems(HttpServletRequest request, User user,	OrderSheetParam akadenSheetParams, PageInfo pageInfo) {
		Map<String, Order> mapOrderIds = (Map<String, Order>) request.getSession().getAttribute(AkadenSessionConstants.AKADEN_MAP_ORDERS_PARAMS);
		List<Integer> listOfOrderIds = (List<Integer>) request.getSession().getAttribute(AkadenSessionConstants.AKADEN_LIST_ORDER_IDS);
		Boolean isAdmin = (Boolean) request.getSession().getAttribute(SessionParamConstants.IS_ADMIN_PARAM);
		if (listOfOrderIds.size() == 0) listOfOrderIds.add(999999999);
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		List<Integer> buyerIds = OrderSheetUtil.toList(akadenSheetParams.getSelectedBuyerID());
		String deliveryDate = akadenSheetParams.getSelectedDate();
		
		if (akadenSheetParams.getDatesViewBuyerID() == null)
			akadenSheetParams.setDatesViewBuyerID(buyerIds.get(0).toString());
		
		List<Integer> _listOrderId = new ArrayList<Integer>();
		if (!isAdmin) {
			Integer buyerId = Integer.valueOf(akadenSheetParams.getDatesViewBuyerID());
			Integer sellerId = Integer.valueOf(akadenSheetParams.getSelectedSellerID());
			String key = deliveryDate + "_" + buyerId + "_"+ sellerId;
			Order order =  mapOrderIds.get(key);
			if (order != null)
				_listOrderId.add(order.getOrderId());
		}
		
		if (_listOrderId.size() == 0) _listOrderId.add(999999999);
//		String selectedBuyerCompany = akadenSheetParams.getSelectedBuyerCompany();
		Integer categoryId = akadenSheetParams.getCategoryId();
//		Integer sheetTypeId = akadenSheetParams.getSheetTypeId();
		boolean isAllDatesView = akadenSheetParams.isAllDatesView();
		String startDate = akadenSheetParams.getStartDate();
		String endDate = akadenSheetParams.getEndDate();
//		Integer datesViewBuyerId = akadenSheetParams.getDatesViewBuyerID() == null ? 0 : Integer.parseInt(akadenSheetParams.getDatesViewBuyerID());

//		System.out.println("deliveryDate:[" + deliveryDate + "]");
//		System.out.println("selectedBuyerCompany:[" + selectedBuyerCompany + "]");
//		System.out.println("companyBuyerIds:[" + buyerIds + "]");
//		System.out.println("categoryId:[" + categoryId + "]");
//		System.out.println("sheetTypeId:[" + sheetTypeId + "]");
//		System.out.println("allDatesView:[" + isAllDatesView + "]");
//		System.out.println("startDate:[" + startDate + "]");
//		System.out.println("endDate:[" + endDate + "]");
//		System.out.println("datesViewBuyerId:[" + datesViewBuyerId + "]");

		List<String> deliveryDates = new ArrayList<String>();
		if (isAllDatesView)
			deliveryDates = DateFormatter.getDateList(startDate, endDate);
		else
			deliveryDates.add(deliveryDate);

		System.out.println(deliveryDates);

		//pageInfo.setTotalRowNum(countDistinctSKU(deliveryDates, categoryId, sheetTypeId, userId));
		
		// this will fetch whole data to display in the sheet
		List<AkadenSKU> allSkuObjs = null;
		if (isAdmin)
			allSkuObjs = this.getDistinctSKUs(listOfOrderIds, categoryId, pageInfo.getStartRowNum(), pageInfo.getPageSize());
		else
			allSkuObjs = this.getDistinctSKUs(_listOrderId, categoryId, pageInfo.getStartRowNum(), pageInfo.getPageSize());
		
		//SortingUtil.sortAkadenSKUs(user, skuObjs, Integer.valueOf(categoryId));

		//SortingUtil.sortSKUs(user, allSkuObjs, Integer.valueOf(categoryId));
		List<AkadenSKU> skuObjs = new ArrayList<AkadenSKU>();
		int rowIdxStart = pageInfo.getStartRowNum() - 1;
		int rowIdxEnd = rowIdxStart + pageInfo.getPageSize() - 1;
		for (int i=rowIdxStart; i<=rowIdxEnd; i++) {
			if (i == allSkuObjs.size()) break;
			skuObjs.add(allSkuObjs.get(i));
		}
		pageInfo.setTotalRowNum(allSkuObjs.size());
		
		Map<Integer, Object> akadenSkuUpdateParam = new HashMap<Integer, Object>(); 
		Set<String> sellerNameSet = new TreeSet<String>();
		
		for (AkadenSKU skuObj : skuObjs) {
			Map<String, Object> akadenSkuObjMap = new HashMap<String, Object>();
			JSONObject json = new JSONObject();
			
			String skuName = skuObj.getSkuName();
			System.out.println("skuName:[" + skuName + "]");
			boolean isRed = false;
			if(Integer.valueOf(skuObj.getTypeFlag())==2)
				isRed = true;
			
			akadenSkuObjMap.put("isNewSKU", skuObj.getIsNewSKU());
			akadenSkuObjMap.put("typeflag", skuObj.getTypeFlag());
			akadenSkuObjMap.put("akadenSkuId", skuObj.getAkadenSkuId());
			akadenSkuObjMap.put("sellerId", skuObj.getUser().getUserId());
			akadenSkuObjMap.put("skuId", skuObj.getSkuId());
			
			if(skuObj.getProposedBy() != null) {
				//json.put("sku", "1");
				akadenSkuObjMap.put("companyname", skuObj.getProposedBy().getCompany().getShortName());
				akadenSkuObjMap.put("sellername", skuObj.getProposedBy().getShortName());
			}else{
				akadenSkuObjMap.put("companyname", skuObj.getUser().getCompany().getShortName());
				akadenSkuObjMap.put("sellername", skuObj.getUser().getShortName());
			}
			
			akadenSkuObjMap.put("groupname", 	skuObj.getSkuGroup().getSkuGroupId()); 
			akadenSkuObjMap.put("marketname", 	StringUtil.nullToBlank(skuObj.getMarket()));
			akadenSkuObjMap.put("skuname", 		StringUtil.nullToBlank(skuObj.getSkuName()));
			akadenSkuObjMap.put("home", 		StringUtil.nullToBlank(skuObj.getLocation()));
			akadenSkuObjMap.put("grade", 		StringUtil.nullToBlank(skuObj.getGrade()));
			akadenSkuObjMap.put("clazzname", 	StringUtil.nullToBlank(skuObj.getClazz()));
			akadenSkuObjMap.put("price1", 		NumberUtil.nullToZero((BigDecimal)skuObj.getPrice1()));
			akadenSkuObjMap.put("price2", 		NumberUtil.nullToZero((BigDecimal)skuObj.getPrice2()));
			akadenSkuObjMap.put("pricewotax", 	skuObj.getPriceWithoutTax());
			akadenSkuObjMap.put("pricewtax", 	skuObj.getPriceWithTax());
			akadenSkuObjMap.put("packageqty", 	StringUtil.nullToBlank(skuObj.getPackageQuantity()));
			akadenSkuObjMap.put("packagetype", 	StringUtil.nullToBlank(skuObj.getPackageType()));
			akadenSkuObjMap.put("unitorder", 	skuObj.getOrderUnit().getOrderUnitId());	
			
			lockSellerName(user, json);
			akadenSkuObjMap.put("lockflag", json.toString());
			
			
			this.loadOrderItemQuantities(akadenSkuObjMap, buyerIds, deliveryDate, skuObj, isRed, akadenSheetParams);
			
			akadenSkuUpdateParam.put(skuObj.getAkadenSkuId(), skuObj);
			
			sellerNameSet.add(skuObj.getUser().getShortName());
			skuOrderMaps.add(akadenSkuObjMap);
			
			SessionHelper.setAttribute(request, "sellerNameSet", sellerNameSet);
			SessionHelper.setAttribute(request, AkadenSessionConstants.AKADEN_SKU_UPDATE_PARAM, akadenSkuUpdateParam);
			SessionHelper.setAttribute(request, AkadenSessionConstants.AKADEN_SKU_OBJ_MAP_PARAM, akadenSkuObjMap);
		}		
		
		return skuOrderMaps;
	}
	
	private void lockSellerName(User user, JSONObject json) {
		try {
			json.put("sellername", "1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Integer countDistinctSKU(List<String> deliveryDates,
			Integer categoryId, Integer sheetTypeId, Integer sellerId) {
		return akadenDao.countDistinctSKU(deliveryDates, categoryId,
				sheetTypeId, sellerId);
	}

	@Override
	public void saveAkaden(OrderForm orderForm, AkadenForm akadenForm, OrderDetails orderDetails, Map<Integer, AkadenSKU> akadenSkuObjMap, Map<Integer, AkadenSKU> skuObjUpdateMap, OrderSheetParam akadenParams, User users, Map<String, Order> allOrdersMap) {
		String isNewSku = "1"; 
		String isNotNewSku = "0"; 
		Integer buyerId = orderDetails.getDatesViewBuyerID();
		String deliveryDate = akadenParams.getSelectedDate();
		//List<Integer> sellerIds = OrderSheetUtil.toList(akadenParams.getSelectedSellerID());
		List<Integer> buyerIds = OrderSheetUtil.toList(orderDetails.getDatesViewBuyerID().toString());
		List<Integer> sellerIds = OrderSheetUtil.toList(akadenParams.getSelectedSellerID());
		
		// UPDATE ORDERS
		for (Integer buyId : buyerIds) {
			String sBuyerId = buyId.toString();
			for (Integer sellId : sellerIds) {
				String sSellerId = sellId.toString(); 
				Order order = allOrdersMap.get(deliveryDate + "_" + sBuyerId + "_" + sSellerId);
				if (order == null) continue;
				akadenDao.updateAkadenSaveBy(orderDetails.getSellerId(), order.getOrderId());
			}
		}
			
		/* this block will save the newly added sku and this will be
		 * save to EON_SKU and EON_ORDER_AKADEN table
		 * this transaction done thru addsku button
		 */
  
		List<AkadenItemUI> insertItemList = akadenForm.getInsertedOrders();
		Map<Integer,User> sellerUserObjMap = new HashMap<Integer,User>();
		for (AkadenItemUI ai : insertItemList) {
			
			//Order orders = null;
			User user = sellerUserObjMap.get(ai.getSellerId());
			if (user == null) {
				user = usersInfoDaos.getUserById(ai.getSellerId());
				sellerUserObjMap.put(ai.getSellerId(), user);
			}
			SKU sku = AkadenSheetUtil.toSKU(ai, orderDetails, user);
			Integer skuId = skuDao.insertSKU(sku);
			ai.setSkuId(skuId);
			AkadenSKU  aSku = AkadenSheetUtil.toAkadenSKU(ai, orderDetails, user);
			aSku.setTypeFlag("9");
			Integer akadenSkuId = akadenDao.insertAkadenSKU(aSku);
			for (Integer _buyerId : buyerIds) {
				String sBuyerId = _buyerId.toString();
				for (Integer _sellerId : sellerIds) {
					String sSellerId = _sellerId.toString();
					Order order = allOrdersMap.get(deliveryDate + "_" + sBuyerId + "_" + sSellerId);
					
					if (order == null) continue;
					if (!sSellerId.equals(sku.getUser().getUserId().toString())) continue;
					String q_key = "Q_" + buyerId.toString();
				    akadenDao.saveOrderAkaden(order.getOrderId(), null, sku.getUser().getUserId(), ai.getComments(), akadenSkuId, isNewSku,
				    		ai.getQtyMap().get(q_key), ai.getQtyMap().get(q_key), ai.getPrice1()); 
				}
			}
		}

		/* This block will save the changes of the order and this will be
		 * save to EON_AKADEN_SKU AND EON_ORDER_AKADEN table
		 * and this transaction done thru importing data from allocation
		 * */
		List<AkadenItemUI> updateItemList = akadenForm.getUpdatedOrders();
		for (AkadenItemUI ai : updateItemList) {
			User user = usersInfoDaos.getUserById(ai.getSellerId());
			AkadenSKU aSku = AkadenSheetUtil.toAkadenSKU(ai, orderDetails, user);
			//AkadenSKU origSku = akadenSkuObjMap.get(sku.getSkuId());
			if (ai. getIsNewSKU()==0) {
				if (Integer.parseInt(ai.getTypeFlag())==1 || Integer.parseInt(ai.getTypeFlag())==2) {
					continue;
				}
				else if (Integer.parseInt(ai.getTypeFlag())==3) {
					// Update the eon_akaden_sku
					
					this.updateAkadenSKU(aSku);

					/* Update the eon_order_akaden 
					 * */		
					
					Order order = allOrdersMap.get(deliveryDate + "_" + buyerId + "_" + ai.getSellerId());					
					if (order == null) continue;
//					if (!ai.getSellerId().equals(aSku.getUser().getUserId().toString())) continue;
					String q_key = "Q_" + buyerId.toString();
						this.updateOrderAkaden(order.getOrderId(), ai.getAkadenSkuId(), 
								ai.getComments(), ai.getQtyMap().get(q_key), ai.getQtyMap().get(q_key), ai.getPrice1());
					
				}
				else if (Integer.parseInt(ai.getTypeFlag())==9) {
					/* 
					 * If new added sku will be update after saved. 
					 * This block will process the updated record.					
					*/ 
					//orderId = mapOrderId.get(deliveryDate + "_" + buyerId + "_" + ai.getSellerId());
					//SKU skuUpdate = AkadenSheetUtil.toSKUObject(ai, orderDetails, users); 
					this.updateAkadenSKU(aSku);
					Order order = allOrdersMap.get(deliveryDate + "_" + buyerId + "_" + ai.getSellerId());					
					if (order == null) continue;
//					if (!ai.getSellerId().equals(sku.getUser().getUserId().toString())) continue;
					String q_key = "Q_" + buyerId.toString();
					this.updateOrderAkadenByNewSKU(order.getOrderId(), ai.getAkadenSkuId(), AkadenSessionConstants.AKADEN_SAVED_ISNEW, 
							ai.getComments(), ai.getQtyMap().get(q_key), ai.getQtyMap().get(q_key), ai.getPrice1());
				}
				else {
					/* This block will make the row into 3 and will 
					 * update the akaden sku type flag = 0 to 1
					 * */	
					
					AkadenSKU sku2 = skuObjUpdateMap.get(ai.getAkadenSkuId());
					akadenDao.updateAkadenSKUTypeFlag(ai.getAkadenSkuId());
					sku2.setTypeFlag("2");
					Integer akadenSkuId1 = this.insertImportedAllocation(sku2);
					AkadenSKU sku3 = AkadenSheetUtil.toAkadenSKU(ai, orderDetails, users);
					sku3.setTypeFlag("3");
					Integer akadenSkuId2 = this.insertImportedAllocation(sku3);
					
						Order order = allOrdersMap.get(deliveryDate + "_" + buyerId + "_" + ai.getSellerId());					
						if (order == null) continue;
						//if (!ai.getSellerId().equals(sku.getUser().getUserId().toString())) continue;
						String q_key = "Q_" + buyerId.toString();
						//negate original quantity and comments
						//BigDecimal totQty = new BigDecimal("-"+sku2.getQuantity().toString());
						BigDecimal totQty = sku2.getQuantity().multiply(new BigDecimal(-1));
						this.insertImportedOrderAkaden(order.getOrderId(), ai.getSkuId(), users.getUserId(), "", 
								akadenSkuId1, isNotNewSku, sku2.getQuantity().multiply(new BigDecimal(-1)), totQty, ai.getPrice1());	
						//insert new original quantity and comments
						this.insertImportedOrderAkaden(order.getOrderId(), ai.getSkuId(), users.getUserId(), ai.getComments(), 
								akadenSkuId2, isNotNewSku, new BigDecimal(ai.getQtyMap().get(q_key).toString()), ai.getQtyMap().get(q_key), ai.getPrice1());
				
				}				
			} else {
				// If new added sku can be update after saved. This block will process the updated record.
			}
		}
		/*
		 * This block will delete the SKU depending on their flag type 1,2,3,9
		 * 1,2,3 will delete the eon_akaden_sku table
		 * while 9 will delete eon_sku table because this is added thru add sku button
		 * "deleteOrderAkadenById" will delete the eon_order_akaden table all transaction here 
		 * will always be saved to eon_order_akaden table
		 * */		
		List<AkadenItemUI> deleteItemList = akadenForm.getDeletedOrders();
		for (AkadenItemUI ai : deleteItemList) {
			Integer akadenSkuId = Integer.valueOf(ai.getAkadenSkuId());
			Integer typeFlag = Integer.valueOf(ai.getTypeFlag());
			if (typeFlag == 1 || typeFlag == 2 || typeFlag == 3) {
				this.deleteOrderAkadenBySKUId(ai.getSkuId());
				this.deleteAkadenSKUBySKUId(ai.getSkuId());
			}else {
				this.deleteOrderAkadenByAkadenSkuId(akadenSkuId);
				this.deleteAkadenSKUByAkadenSKUId(akadenSkuId);
			}
		}
	}
	@Override
	public List<SKU> selectDistinctSKUsFromAllocation(
			List<String> deliveryDates, Integer categoryId,
			Integer sheetTypeId, Integer sellerId, Integer rowStart,
			Integer pageSize) {
		return akadenDao.selectDistinctSKUsFromAllocation(deliveryDates,
				categoryId, sheetTypeId, sellerId, rowStart, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> loadAllocationItems(User user, AkadenSheetParams akaParam, PageInfo pageInfo, HttpServletRequest request) {
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		Map<Integer, Integer> mapOrderIds = (Map<Integer, Integer>) request.getSession().getAttribute(AkadenSessionConstants.AKADEN_ALLOC_MAP_ORDERS_IDS);
		List<Integer> orderIds = new ArrayList<Integer>();
		Integer buyId = Integer.valueOf(akaParam.getDatesViewBuyerID());
		orderIds.add(mapOrderIds.get(buyId));
		
		String deliveryDate = akaParam.getSelectedDate().toString();
		String selectedBuyerCompany = akaParam.getSelectedBuyerCompany();
		List<Integer> companyBuyerIds = OrderSheetUtil.toList(akaParam.getSelectedBuyerID());
		Integer categoryId = akaParam.getCategoryId();
		Integer sheetTypeId = akaParam.getSheetTypeId();
		boolean isAllDatesView = akaParam.isAllDatesView();
		String startDate = akaParam.getStartDate();
		String endDate = akaParam.getEndDate();

		System.out.println("deliveryDate:[" + deliveryDate + "]");
		System.out.println("selectedBuyerCompany:[" + selectedBuyerCompany + "]");
		System.out.println("companyBuyerIds:[" + companyBuyerIds + "]");
		System.out.println("categoryId:[" + categoryId + "]");
		System.out.println("sheetTypeId:[" + sheetTypeId + "]");
		System.out.println("allDatesView:[" + isAllDatesView + "]");
		System.out.println("startDate:[" + startDate + "]");
		System.out.println("endDate:[" + endDate + "]");

		List<String> deliveryDates = new ArrayList<String>();
		if (isAllDatesView)
			deliveryDates = DateFormatter.getDateList(startDate, endDate);
		else
			deliveryDates.add(deliveryDate);

		System.out.println(deliveryDates);
		
		// this will fetch whole data from allocation to display in the sheet
		List<SKU> allSkuObjs = this.getAllocationData(orderIds, categoryId, sheetTypeId, pageInfo.getStartRowNum(), pageInfo.getPageSize());
		
		int rowIdxStart = pageInfo.getStartRowNum() - 1;
		int rowIdxEnd = rowIdxStart + pageInfo.getPageSize() - 1;
		List<SKU> skuObjs = new ArrayList<SKU>();
		for (int i=rowIdxStart; i<=rowIdxEnd; i++) {
			if (i == allSkuObjs.size()) break;
			skuObjs.add(allSkuObjs.get(i));
		}
		pageInfo.setTotalRowNum(allSkuObjs.size());
		
		Map<Integer, Integer> skuBuyerId = new HashMap<Integer, Integer>();
		
		for (SKU skuObj : skuObjs) {
			Map<String, Object> skuOrderMap = new HashMap<String, Object>();

			String skuName = skuObj.getSkuName();
			System.out.println("skuName:[" + skuName + "]");
			skuOrderMap.put("sellerId", skuObj.getUser().getUserId());
			skuOrderMap.put("skuId", skuObj.getSkuId());
			skuOrderMap.put("companyname", skuObj.getUser().getCompany().getCompanyName());
			skuOrderMap.put("sellername", skuObj.getUser().getShortName());
			skuOrderMap.put("groupname", skuObj.getSkuGroup().getSkuGroupId()); 
			skuOrderMap.put("marketname", skuObj.getMarket());
			skuOrderMap.put("skuname", skuObj.getSkuName());
			skuOrderMap.put("home", skuObj.getLocation());
			skuOrderMap.put("grade", skuObj.getGrade());
			skuOrderMap.put("clazzname", skuObj.getClazz());
			skuOrderMap.put("price1", skuObj.getPrice1());
			skuOrderMap.put("price2", skuObj.getPrice2());
			skuOrderMap.put("pricewotax", skuObj.getPriceWithoutTax());
			skuOrderMap.put("pricewtax", skuObj.getPriceWithTax());
			skuOrderMap.put("packageqty", skuObj.getPackageQuantity());
			skuOrderMap.put("packagetype", skuObj.getPackageType());
			skuOrderMap.put("unitorder", skuObj.getOrderUnit().getOrderUnitId());

			this.loadAllocationQuantities(skuOrderMap, buyId, deliveryDate, skuObj);
			skuOrderMaps.add(skuOrderMap);
			
			for(Integer buyerId : companyBuyerIds) {
				skuBuyerId.put(skuObj.getSkuId(), buyerId);
			}
		}

		SessionHelper.setAttribute(request, AkadenSessionConstants.AKADEN_ALLOCATION_SHEET_SKUIDS, skuBuyerId);	
		return skuOrderMaps;
	}

	private List<SKU> getAllocationData(List<Integer> orderIds, Integer categoryId, Integer sheetTypeId,
			Integer rowStart, Integer pageSize) {
		// return allocationDao.selectDistinctSKUs(orderIds, categoryId, rowStart, pageSize, null);
		return null;
	}

	@Override
	public BigDecimal getGTPriceByOrderId(List<Integer> orderId, List<String> deliveryDates, List<Integer> buyerId) {
		List<SKU> skuObjs = getDistinctSKUs(orderId);
		List<Map<String, Object>> skuOrderMapList = new ArrayList<Map<String, Object>>();
		for (SKU skuObj : skuObjs) {
			Map<String, Object> skuOrderMap = new HashMap<String, Object>();
			skuOrderMap.put("sku", skuObj);
			
			try {
				loadOrderItemQuantities(skuOrderMap, skuObj.getSkuId(), deliveryDates, buyerId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			skuOrderMapList.add(skuOrderMap);
		}
		return computeGTPrice(skuOrderMapList);
	}

	private BigDecimal computeGTPrice(List<Map<String, Object>> skuOrderMapList) {
		BigDecimal total = new BigDecimal(0);
		for (Map<String, Object> skuOrderMap : skuOrderMapList) {
			SKU sku = (SKU)skuOrderMap.get("sku");
			BigDecimal price = sku.getPriceWithoutTax();
			BigDecimal totalQuantity = (BigDecimal)skuOrderMap.get("rowqty");
			if (price != null && totalQuantity != null)
				total = total.add(price.multiply(totalQuantity));
		}
		return total;
	}
	
	private  void loadOrderItemQuantities(Map<String, Object> skuOrderMap, Integer skuId,
			List<String> deliveryDates, List<Integer> buyerIds) {
		
		BigDecimal rowqty = new BigDecimal(0);
		for (Integer buyerId : buyerIds) {
			Map<String, Object> _param = new HashMap<String,Object>();
			_param.put("deliveryDates", deliveryDates);
			_param.put("skuId", skuId);
			_param.put("buyerId", buyerId);
			Map<Integer, AkadenItem> orderItemMap = akadenDao.getOrderItemsMapOfSkuDates(_param);
			for (String deliveryDate : deliveryDates) {
				AkadenItem akadenItem = orderItemMap.get(deliveryDate);
				BigDecimal quantity = null;
				
				if (akadenItem != null) {
					quantity = akadenItem.getQuantity();
					if (quantity != null) 
						rowqty = rowqty.add(quantity);
				}
			}
		}
		skuOrderMap.put("rowqty", rowqty);
	}
	
	@Override
	public String getSKUGroupList(Integer sellerId, Integer categoryId) {
		List<SKUGroup> list = skuGroupDao.getSKUGroupList(sellerId, categoryId);
		StringBuffer sb = new StringBuffer();
		sb.append("{'--商品グループ--' : '--商品グループ--'");
		for (int i=0; i<list.size(); i++) {
			SKUGroup skuGroup = list.get(i);
			sb.append(",'" + skuGroup.getDescription() + "':'" + skuGroup.getDescription() + "'");
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public List<AkadenSKU> getDistinctSKUs(List<Integer> orderIds, Integer categoryId, 
			Integer rowStart, Integer pageSize) {
		return akadenDao.getDistinctSKUs(orderIds, categoryId,
				rowStart, pageSize);
	}

	@Override
	public void insertAkadenSKU(AkadenForm akadenForm, OrderDetails orderDetails, User users) {
		List<AkadenItemUI> orderItemList = akadenForm.getInsertedOrders();
		for (AkadenItemUI oi : orderItemList) {
			AkadenSKU sku = AkadenSheetUtil.toAkadenSKU(oi, orderDetails, users);
			Integer id = akadenDao.insertAkadenSKU(sku);
			sku.setSkuId(id);
		}
	}
	
	@Override
	public void insertAkadenSKU(AkadenSKU sku) {
		akadenDao.insertAkadenSKU(sku);
	}

	@Override
	public List<Order> getSelectedOrdersByBuyer(List<Integer> sellerIds,Integer buyerIds, String startDate, String endDate) {
		List<String> deliveryDates = DateFormatter.getDateList(startDate, endDate);
		return akadenDao.getSelectedOrdersByBuyer(sellerIds, buyerIds, deliveryDates);
	}

	@Override
	public List<Order> getSelectedOrdersByDate(List<Integer> sellerIds, List<Integer> buyerIds, String selectedDate) {
		return akadenDao.getSelectedOrdersByDate(sellerIds, buyerIds, selectedDate);
	}

	@Override
	public void loadOrderItemQuantities(Map<String, Object> skuOrderMap,
			List<Integer> companyBuyerIds, String deliveryDate, AkadenSKU skuObj, boolean isRed, OrderSheetParam osParams) {

		Integer skuId = skuObj.getSkuId();
		BigDecimal rowqty = new BigDecimal(0);
		String comments = "", isNewSKU = "0";
		
		Map<Integer, AkadenItem> AkadenItemMap = new HashMap<Integer, AkadenItem>();

		if (!NumberUtil.isNullOrZero(skuObj.getAkadenSkuId())) {
			AkadenItemMap = orderAkadenDao.getAkadenItemsMapOfSkuDate(
					deliveryDate, skuId, skuObj.getAkadenSkuId());
		} else {
			AkadenItemMap = orderAkadenDao.getAkadenItemsMapOfSkuDate(
					deliveryDate, skuId, null);
		}

//		for (Integer buyerId : companyBuyerIds) {
		Integer buyerId = Integer.parseInt(osParams.getDatesViewBuyerID());
		AkadenItem akadenItem = AkadenItemMap.get(buyerId);
		BigDecimal quantity = null;
		String strQuantity = "";

		if (akadenItem != null) {
			quantity = akadenItem.getQuantity();
			comments = akadenItem.getComments();
			isNewSKU = akadenItem.getIsNewSku()==null?"0":akadenItem.getIsNewSku();
			
			System.out.println("quantity:[" + quantity + "]");
			if (quantity != null) {
				rowqty = rowqty.add(quantity);
				strQuantity = quantity.toPlainString();
			}
		}

		//skuOrderMap.put("Q_" + buyerId.toString(), isRed? strQuantity.equals("")? "" : "-" + strQuantity:strQuantity);
		skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
		
		skuObj.setQuantity(new BigDecimal(strQuantity.equals("")? "0":strQuantity));
		skuOrderMap.put("isNewSKU", isNewSKU);
		skuOrderMap.put("comments", StringUtil.nullToBlank(comments));
		//skuOrderMap.put("rowqty", isRed? "-" + rowqty:rowqty);
		skuOrderMap.put("rowqty", rowqty);
		skuOrderMap.put("Q_Negation" +  buyerId.toString(), rowqty);
	}

	@Override
	public Integer insertImportedAllocation(AkadenSKU sku) {
		return (Integer) akadenDao.insertImportedAllocation(sku);

	}

	@Override
	public void insertImportedOrderAkaden(Integer orderId, Integer skuId,
			Integer updateBy, String comments, Integer akadenSkuId,
			String isnewsku, BigDecimal quantity, BigDecimal totalQuantity,
			BigDecimal unitPrice) {
		akadenDao.insertImportedOrderAkaden(orderId, skuId, updateBy, comments,
				akadenSkuId, isnewsku, quantity, totalQuantity, unitPrice);

	}

	@Override
	public void loadAllocationQuantities(Map<String, Object> skuOrderMap, Integer buyerId, String deliveryDate, SKU skuObj) {
		Integer skuId = skuObj.getSkuId();
		BigDecimal rowqty = new BigDecimal(0);
		Map<Integer, OrderItem> AkadenItemMap = new HashMap<Integer, OrderItem>();
		AkadenItemMap = allocationDao.getAllocItems(deliveryDate, skuId);

		OrderItem allocItem = AkadenItemMap.get(buyerId);
		BigDecimal quantity = null;
		String strQuantity = "";

		if (allocItem != null) {
			quantity = allocItem.getQuantity();

			System.out.println("quantity:[" + quantity + "]");
			if (quantity != null) {
				rowqty = rowqty.add(quantity);
				strQuantity = quantity.toPlainString();
			}
		} else {
			strQuantity = "";
		}

		skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
		skuOrderMap.put("rowqty", rowqty);
	}

	
	@Override
	public Integer insertOrderByAkaden(Integer sellerId, Integer buyerId, String deliveryDate, Integer akadenSavedBy, Integer createdBy) {
		return (Integer) akadenDao.insertOrderByAkaden(sellerId, buyerId, deliveryDate, akadenSavedBy, createdBy);
	}

	@Override
	public Integer updateAkadenSKUTypeFlag(Integer akadenSKUId) {
		return (Integer) akadenDao.updateAkadenSKUTypeFlag(akadenSKUId);
	}

	@Override
	public Integer updateAkadenSKU(AkadenSKU aSku) {
		return (Integer) akadenDao.updateAkadenSKU(aSku);
	}

	@Override
	public Integer updateOrderAkaden(Integer orderId, Integer AkadenSKUId, String comments,
			BigDecimal quantity, BigDecimal totalQuantity, BigDecimal unitPrice) {
		return (Integer) akadenDao.updateOrderAkaden(orderId, AkadenSKUId, comments, quantity, totalQuantity, unitPrice);
	}

	@Override
	public void deleteAkadenSKUByAkadenSKUId(Integer akadenSkuId) {
		akadenDao.deleteAkadenSKUByAkadenSKUId(akadenSkuId);
	}

	@Override
	public void deleteOrderAkadenByAkadenSkuId(Integer akadenSkuId) {
		akadenDao.deleteOrderAkadenByAkadenSkuId(akadenSkuId);
	}

	@Override
	public Integer deleteSKUBySkuId(Integer SkuId) {
		Integer iDelete = akadenDao.deleteSKUBySkuId(SkuId);
		return iDelete;
	}

	@Override
	public Order getOrderByDeliveryDate(Integer sellerId, Integer buyerId,
			String deliveryDate) {
		return akadenDao.getOrderByDeliveryDate(sellerId, buyerId, deliveryDate);
	}
	
	@Override
	public List<Order> getAkadenOrders(List<Integer> buyerIds, List<String> deliveryDates, List<Integer> sellerIds) {
		return akadenDao.getAkadenOrders(sellerIds, buyerIds, deliveryDates);
	}
	
	@Override
	public void loadSumAkadenQuantitiesAllDates(Map<String, Object> skuOrderMap,
			List<String> deliveryDates, List<Integer> orderIds, AkadenSKU skuObj) {
		
		Integer skuId = skuObj.getSkuId();
		
		Map<Integer, AkadenItem> AkadenItemMap = new HashMap<Integer, AkadenItem>();

		if (!NumberUtil.isNullOrZero(skuObj.getAkadenSkuId())) {
			AkadenItemMap = orderAkadenDao.getSumAkadenMapOfSkuDates(orderIds, skuId, skuObj.getAkadenSkuId());
		}
		else {
			AkadenItemMap = orderAkadenDao.getSumAkadenMapOfSkuDates(orderIds, skuId, null);
		}

		for (String deliveryDate : deliveryDates) {
			AkadenItem akadenItem = AkadenItemMap.get(deliveryDate);
			BigDecimal quantity = null;
			String strQuantity = "";
			if (akadenItem != null) {
				quantity = akadenItem.getQuantity();

				System.out.println("quantity:[" + quantity + "]");
				if (quantity != null) {
					strQuantity = quantity.toPlainString();
				}
				
			}
			else {
				strQuantity = "";
			}
			skuOrderMap.put("Q_" + deliveryDate, strQuantity);
		}
		
	}
	
	@Override
	public void loadSumAkadenQuantitiesAllBuyers(Map<String, Object> skuOrderMap,
			List<Integer> buyerIds, List<Integer> orderIds, AkadenSKU skuObj) {

		Integer skuId = skuObj.getSkuId();
		
		Map<Integer, AkadenItem> AkadenItemMap = new HashMap<Integer, AkadenItem>();

		if (!NumberUtil.isNullOrZero(skuObj.getAkadenSkuId())) {
			AkadenItemMap = orderAkadenDao.getSumAkadenMapOfSkuBuyers(orderIds, skuId, skuObj.getAkadenSkuId());
		}
		else {
			AkadenItemMap = orderAkadenDao.getSumAkadenMapOfSkuBuyers(orderIds, skuId, null);
		}
		
		for (Integer buyerId : buyerIds) {
			AkadenItem akadenItem = AkadenItemMap.get(buyerId);
			BigDecimal quantity = null;
			String strQuantity = "";
			
			if (akadenItem != null) {
				quantity = akadenItem.getQuantity();
				
				if (akadenItem.getAkadenSku().getTypeFlag().equals("2"))
					quantity = quantity.multiply(new BigDecimal(-1));
				
				System.out.println("quantity:[" + quantity + "]");
				if (quantity != null) {
					strQuantity = quantity.toPlainString();
				}
			}
			else {
				strQuantity = "";
			}

			skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
			//skuOrderMap.put("comment", StringUtil.nullToBlank(comment));
			//System.out.println("comment:[" + skuOrderMap.get("comment")+ "]");
			
		}
	}

	@Override
	public Integer updateOrderAkadenByNewSKU(Integer orderId, Integer akadenSkuId,
			Integer isNewSKU, String comments, BigDecimal quantity,
			BigDecimal totalQuantity, BigDecimal unitPrice) {
		return akadenDao.updateOrderAkadenByNewSKU(orderId, akadenSkuId, isNewSKU, comments, quantity, totalQuantity, unitPrice);
	}

	@Override
	public List<SKU> getDistinctSKUs(List<Integer> allOrderId) {
		return akadenDao.selectDistinctSKUs(allOrderId);
	}

	@Override
	public Integer checkImportedAllocIfExists(Integer orderId, Integer skuId) {
		return akadenDao.checkImportedAllocIfExists(orderId, skuId);
	}
	
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
	public GrandTotalPrices getGTPriceAllOrders(List<Integer> orderIds) {
		return akadenDao.getGTPriceAllOrders(orderIds);
	}
	
	@Override
	public Map<Integer, AkadenItem> getAkadenItemsMapOfSkuDate(
			String deliveryDate, Integer skuId, Integer akadenSkuId) {
		return orderAkadenDao.getAkadenItemsMapOfSkuDate(deliveryDate, skuId, akadenSkuId);
	}
	
	@Override
	public Map<Integer, AkadenItem> getAkadenItemsMapOfSkuDates(List<String> deliveryDates,
			Integer skuId, Integer buyerId, Integer akadenSkuId) {
		return orderAkadenDao.getAkadenItemsMapOfSkuDates(deliveryDates, skuId, buyerId, akadenSkuId);
	}
	
	@Override
	public void deleteAkadenSKUBySKUId(Integer skuId) {		
		akadenDao.deleteAkadenSKUBySKUId(skuId);
	}

	@Override
	public void deleteOrderAkadenBySKUId(Integer skuId) {
		akadenDao.deleteOrderAkadenBySKUId(skuId);
		
	}
}
