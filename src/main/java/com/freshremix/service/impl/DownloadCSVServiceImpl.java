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
 * 2010/05/17		Jovino Balunan		
 */
package com.freshremix.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.dao.AllocationDao;
import com.freshremix.dao.CategoryDao;
import com.freshremix.dao.CompanyBuyersSortDao;
import com.freshremix.dao.DealingPatternDao;
import com.freshremix.dao.DownloadCSVDao;
import com.freshremix.dao.OrderAkadenDao;
import com.freshremix.dao.OrderBillingDao;
import com.freshremix.dao.OrderSheetDao;
import com.freshremix.dao.ReceivedSheetDao;
import com.freshremix.model.AkadenItem;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.BillingItem;
import com.freshremix.model.BuyersSort;
import com.freshremix.model.DownloadCSVSettings;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderReceived;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.Sheets;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.service.DownloadCSVService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SortingUtil;
import com.freshremix.util.StringUtil;

/**
 * @author Jovino Balunan
 *
 */
public class DownloadCSVServiceImpl implements DownloadCSVService {

	private DownloadCSVDao downloadCSVDao;
	private OrderAkadenDao orderAkadenDao;
	private OrderSheetDao orderSheetDao;
	private AllocationDao allocationDao;
	private OrderBillingDao orderBillingDao;
	private ReceivedSheetDao receivedSheetDao;
	private CategoryDao categoryDaos;
	private DealingPatternDao dealingPatternDao;
	private CompanyBuyersSortDao companybuyersSortDao;

	
	public void setCompanybuyersSortDao(CompanyBuyersSortDao companybuyersSortDao) {
		this.companybuyersSortDao = companybuyersSortDao;
	}

	public void setReceivedSheetDao(ReceivedSheetDao receivedSheetDao) {
		this.receivedSheetDao = receivedSheetDao;
	}

	public void setOrderBillingDao(OrderBillingDao orderBillingDao) {
		this.orderBillingDao = orderBillingDao;
	}

	public void setAllocationDao(AllocationDao allocationDao) {
		this.allocationDao = allocationDao;
	}

	public void setOrderSheetDao(OrderSheetDao orderSheetDao) {
		this.orderSheetDao = orderSheetDao;
	}

	public void setOrderAkadenDao(OrderAkadenDao orderAkadenDao) {
		this.orderAkadenDao = orderAkadenDao;
	}

	public void setDownloadCSVDao(DownloadCSVDao downloadCSVDao) {
		this.downloadCSVDao = downloadCSVDao;
	}

	public void setCategoryDaos(CategoryDao categoryDaos) {
		this.categoryDaos = categoryDaos;
	}

	public void setDealingPatternDao(DealingPatternDao dealingPatternDao) {
		this.dealingPatternDao = dealingPatternDao;
	}
	
	public Map<String, Object> getOrdersQuantiyMap(Integer buyerId, String deliveryDate,
			Integer skuId, Integer sheetTypeId, boolean isAllDatesView, List<Integer> orderIds) {
		Map<String, Object> skuObject = new HashMap<String, Object>();
		Map<Integer, OrderItem> itemMap = new HashMap<Integer, OrderItem>();
		Map<Integer, OrderReceived> orderReceivedMap = new HashMap<Integer, OrderReceived>();
		boolean isReceived = false;
		if (sheetTypeId.equals(SheetTypeConstants.SELLER_ORDER_SHEET) || 
				sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ORDER_SHEET) || 
				sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.BUYER_ORDER_SHEET)) {
			if (isAllDatesView)
				itemMap = orderSheetDao.getOrderItemsMapOfSkuDates(orderIds, skuId);
			else
				itemMap = orderSheetDao.getOrderItemsMapOfSkuBuyers(orderIds, skuId);			
		}
//		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET)) {
//			if (isAllDatesView)
//				itemMap = orderSheetDao.getSumBuyerOrderItemsMapOfSkuDates(orderIds, skuId, null);
//			else
//				itemMap = orderSheetDao.getSumBuyerOrderItemsMapOfSkuBuyers(orderIds, skuId, null);
//		}
//		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ORDER_SHEET)) {
//			if (isAllDatesView)
//				itemMap = orderSheetDao.getSumBuyerOrderItemsMapOfSkuDates(orderIds, skuId, buyerId);
//			else
//				itemMap = orderSheetDao.getSumBuyerOrderItemsMapOfSkuBuyers(orderIds, skuId, buyerId);
//		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ALLOCATION_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ALLOCATION_SHEET)) {
			Map<String, Object> _param = new HashMap<String,Object>();		
			List<String> deliveryDates = new ArrayList<String>();
			deliveryDates.add(deliveryDate);
			if (isAllDatesView) {
				_param.put("deliveryDates", deliveryDate);
				_param.put("skuId", skuId);
				itemMap = allocationDao.getOrderItemsMapOfSkuDate(_param);
			}else{
				_param.put("deliveryDates", deliveryDates);
				_param.put("skuId", skuId);
				_param.put("buyerId", buyerId);
				itemMap = allocationDao.getOrderItemsMapOfSkuDates(_param);
			}
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_RECEIVED_SHEET) || 
				sheetTypeId.equals(SheetTypeConstants.BUYER_RECEIVED_SHEET))  {
			isReceived = true;
			if (isAllDatesView)
				orderReceivedMap = receivedSheetDao.getSumOrderReceivedMapOfSkuDates(orderIds, skuId);
			else
				orderReceivedMap = receivedSheetDao.getSumOrderReceivedMapOfSkuBuyers(orderIds, skuId);
		}
		String strQuantity = "", publishedBy = "", finalizedBy = "", lockedBy = "", approvedBy = "";
		BigDecimal quantity = null;
		
		if (!isReceived) {
			if (isAllDatesView) {
				OrderItem item = itemMap.get(deliveryDate);
				if (item != null) {
					quantity = item.getQuantity();
					publishedBy = StringUtil.notEmptyOrNotNullToOne(item.getOrder().getOrderPublishedBy());
					finalizedBy = StringUtil.notEmptyOrNotNullToOne(item.getOrder().getOrderFinalizedBy());
					lockedBy = StringUtil.notEmptyOrNotNullToOne(item.getOrder().getOrderLockedBy());
					approvedBy = "";
					
					System.out.println("quantity:[" + quantity + "]");
					if (quantity != null) {
						strQuantity = quantity.toPlainString();
					}
				}
				skuObject.put("A_" + deliveryDate, "");
				skuObject.put("P_" + deliveryDate, publishedBy);
				skuObject.put("F_" + deliveryDate, finalizedBy);
				skuObject.put("L_" + deliveryDate, lockedBy);
				skuObject.put("Q_" + deliveryDate, strQuantity);
			} else {
				OrderItem item = itemMap.get(buyerId);				
				if (item != null) {
					quantity = item.getQuantity();
					publishedBy = StringUtil.notEmptyOrNotNullToOne(item.getOrder().getOrderPublishedBy());
					finalizedBy = StringUtil.notEmptyOrNotNullToOne(item.getOrder().getOrderFinalizedBy());
					lockedBy = StringUtil.notEmptyOrNotNullToOne(item.getOrder().getOrderLockedBy());
					
					System.out.println("quantity:[" + quantity + "]");
					if (quantity != null) {
						strQuantity = quantity.toPlainString();
					}
					
				}
				skuObject.put("A_" + buyerId.toString(), "");
				skuObject.put("P_" + buyerId.toString(), publishedBy);
				skuObject.put("F_" + buyerId.toString(), finalizedBy);
				skuObject.put("L_" + buyerId.toString(), lockedBy);
				skuObject.put("Q_" + buyerId.toString(), strQuantity);
			}
		} else {
			if (isAllDatesView) {
				OrderReceived item = orderReceivedMap.get(deliveryDate);				 
				if (item != null) {
					quantity = item.getQuantity();
					approvedBy = StringUtil.notEmptyOrNotNullToOne(item.getIsApproved());
					
					System.out.println("quantity:[" + quantity + "]");
					if (quantity != null) {
						strQuantity = quantity.toPlainString();
					}
				}
				skuObject.put("A_" + deliveryDate, approvedBy);
				skuObject.put("P_" + deliveryDate, publishedBy);
				skuObject.put("F_" + deliveryDate, finalizedBy);
				skuObject.put("L_" + deliveryDate, lockedBy);
				skuObject.put("Q_" + deliveryDate, strQuantity);
				
			} else {				
				OrderReceived item = orderReceivedMap.get(buyerId);
				if (item != null) {
					quantity = item.getQuantity();
					approvedBy = StringUtil.notEmptyOrNotNullToOne(item.getIsApproved());
					
					System.out.println("quantity:[" + quantity + "]");
					if (quantity != null) {
						strQuantity = quantity.toPlainString();
					}
					
				}
				skuObject.put("A_" + buyerId.toString(), approvedBy);
				skuObject.put("P_" + buyerId.toString(), publishedBy);
				skuObject.put("F_" + buyerId.toString(), finalizedBy);
				skuObject.put("L_" + buyerId.toString(), lockedBy);
				skuObject.put("Q_" + buyerId.toString(), strQuantity);
			}
		}
		return skuObject;
	}

	@Override
	public Map<Integer, AkadenItem> getAkadenItemsMapOfSkuDates(List<String> deliveryDates, Integer skuId, Integer buyerId,
			Integer akadenSkuId) {
		return orderAkadenDao.getAkadenItemsMapOfSkuDates(deliveryDates, skuId, buyerId, akadenSkuId);
	}
	
	@Override
	public Map<Integer, AkadenItem> getAkadenItemsMapOfSkuDate(String deliveryDate, Integer skuId, Integer akadenSkuId) {
		return orderAkadenDao.getAkadenItemsMapOfSkuDate(deliveryDate, skuId, akadenSkuId);
	}

	@Override
	public List<String> getBuyerCSVUsersList(List<Integer> userIds) {
		return downloadCSVDao.getBuyerCSVUsersList(userIds);
	}
	
	@Override
	public Map<String, Object> getAkadenQuantiyMap(Integer skuId, List<Integer> orderIds, Integer buyerId, String deliveryDate, Integer akadenSkuId, Integer sheetTypeId, boolean isAllDatesView) {
		
		Map<String, Object> skuObject = new HashMap<String, Object>();
		Map<Integer, AkadenItem> akadenItemMap = new HashMap<Integer, AkadenItem>();
		Map<Integer, BillingItem> orderBillingMap = new HashMap<Integer, BillingItem>();
		boolean isAkaden = false;
		if (sheetTypeId.equals(SheetTypeConstants.SELLER_BILLING_SHEET))  {
			if (isAllDatesView)
				orderBillingMap = orderBillingDao.getSumOrderBillingMapOfSkuDates(orderIds, skuId);
			else
				orderBillingMap = orderBillingDao.getSumOrderBillingMapOfSkuBuyers(orderIds, skuId);
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_BILLING_SHEET) || 
				sheetTypeId.equals(SheetTypeConstants.SELLER_BILLING_SHEET)) {
			if (isAllDatesView)
				orderBillingMap = orderBillingDao.getSumOrderBillingMapOfSkuDates(orderIds, skuId);
			else
				orderBillingMap = orderBillingDao.getSumOrderBillingMapOfSkuBuyers(orderIds, skuId);
		}
		else {
			isAkaden = true;
			akadenItemMap = downloadCSVDao.getSellerAkadenItemsMapOfSkuDate(deliveryDate, buyerId, akadenSkuId); 
		}
		
		if (!isAkaden) {
			if (isAllDatesView) {				
				BillingItem item = orderBillingMap.get(deliveryDate);
				BigDecimal quantity = null;
				String strQuantity = "";
				 
				if (item != null) {
					quantity = item.getQuantity();
					
					System.out.println("quantity:[" + quantity + "]");
					if (quantity != null) {
						strQuantity = quantity.toPlainString();
					}
				}
				else {
					//strQuantity = "-999";
					//strLockFlag = "1"; //locked
				}
				
				skuObject.put("Q_" + deliveryDate, strQuantity);				
			} else {				
				BillingItem item = orderBillingMap.get(buyerId);
				BigDecimal quantity = null;
				String strQuantity = "";
				
				if (item != null) {
					quantity = item.getQuantity();
					
					System.out.println("quantity:[" + quantity + "]");
					if (quantity != null) {
						strQuantity = quantity.toPlainString();
					}
					
				}
				else {
					//strQuantity = "-999";
					//strLockFlag = "1"; //locked
				}
				
				skuObject.put("Q_" + buyerId.toString(), strQuantity);
			}
		} else {
			BigDecimal quantity = null;
			String strQuantity = "0", comments = "";
			
			AkadenItem akadenItem = akadenItemMap.get(buyerId);
			if (akadenItem != null) {
				quantity = akadenItem.getQuantity();
				comments = akadenItem.getComments();
				if (quantity != null) {
					strQuantity = quantity.toPlainString();
					skuObject.put("quantity", strQuantity);
				}else
					skuObject.put("quantity", "");
				
				if (comments != null)
					skuObject.put("comments", comments);
				else
					skuObject.put("comments", "");
			}
		}
		return skuObject;
	}
	
	@Override
	public List<AkadenSKU> loadBillingAndAkadenCSVDownload(List<Integer> orderIds, Integer categoryId, Integer sheetTypeId, String hasQuantity, User user) {
		List<AkadenSKU> csvSkuObj = null;
		if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_BILLING_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_BILLING_SHEET)) 
			csvSkuObj = orderBillingDao.selectDistinctSKUs(orderIds, categoryId, sheetTypeId, 0, 999999);
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_BILLING_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.BUYER_BILLING_SHEET))
			csvSkuObj = orderBillingDao.selectDistinctSKUs(orderIds, categoryId, sheetTypeId, 0, 999999);
		else
			csvSkuObj = downloadCSVDao.loadSellerAkadenCSVDownload(orderIds, categoryId, sheetTypeId, hasQuantity);
		return csvSkuObj;
	}

	@Override
	public List<Sheets> getSheetType(Integer roleId) {
		return downloadCSVDao.getSheetType(roleId);
	}
	
	@Override
	public DownloadCSVSettings initializeDownloadCSV(User user, OrderSheetParam osParam){
		DownloadCSVSettings initCSV = new DownloadCSVSettings();
		
		Integer roleId = Integer.valueOf(user.getRole().getRoleId().toString());
		Long longRoleId = user.getRole().getRoleId();
		Integer userId = user.getUserId();
		
		//1. initialize the list of sheet type 
    	//2. initialize the list of category
		//3. initialize data from the sheet
    	//   3.1. Start date
    	//   3.2. End date   
    	//   3.3. Sheet Type
    	//   3.4. Seller/Buyer list
    	//   3.5. Category
		
		
		//1. initialize the list of sheet type 
		List<Sheets> sheetList = downloadCSVDao.getSheetType(roleId);
		initCSV.setSheetTypeList(sheetList);	
		
		//2. initialize the list of category
		Map<String, Object> param = new HashMap<String, Object>();
		List<Integer> listUserIds = new ArrayList<Integer>();
		
		if(longRoleId.equals(RoleConstants.ROLE_SELLER_ADMIN)){
			listUserIds = dealingPatternDao.getSellerIdsOfSellerAdminId(userId);
		}else if(longRoleId.equals(RoleConstants.ROLE_BUYER_ADMIN)){
			listUserIds = dealingPatternDao.getSellerIdsOfBuyerAdminId(userId);
		}else if(longRoleId.equals(RoleConstants.ROLE_SELLER)){
			listUserIds.add(userId);
		}else if(longRoleId.equals(RoleConstants.ROLE_BUYER)){
			listUserIds = dealingPatternDao.getSellerIdsOfBuyerId(userId);
		}
		param.put("listUserIds", listUserIds);
		List<UsersCategory> categoryList = categoryDaos.getCategoryListBySelectedIds(param);
		
		// Sorting Category List
		if(user.getPreference().getSortedCategories().isEmpty()){
			for (UsersCategory category : categoryList) {
				String categoryId = category.getCategoryId().toString();
				if(!initCSV.getCategoryList().contains(categoryId)){				
					initCSV.getCategoryList().add(categoryId);
				}
			}
		}else{
			for (UsersCategory uc : user.getPreference().getSortedCategories()) {
				for (UsersCategory category : categoryList) {
					if (uc.getCategoryId().equals(category.getCategoryId())) {
						String categoryId = category.getCategoryId().toString();
						if(!initCSV.getCategoryList().contains(categoryId)) {				
							initCSV.getCategoryList().add(categoryId);
						}
					}
				}
			}
		}
		String dateTom = DateFormatter.addDays(DateFormatter.convertToString(new Date()), 1);
		String dateTomorrow = DateFormatter.formatToGUIParameter(dateTom);
		initCSV.setStartDate(dateTomorrow);
		
		// -- no saved parameter from the sheets return default values-- 
		if(osParam == null) return initCSV;
		
		
		//3. initialize data from the sheet
    	//   3.1. Start date
		initCSV.setStartDate(DateFormatter.formatToGUIParameter(osParam.getStartDate()));
		
		//   3.2. End date  
		initCSV.setEndDate(DateFormatter.formatToGUIParameter(osParam.getEndDate()));
		
		//   3.3. Sheet Type
		if(osParam.getCsvSheetTypeID() != null)
			initCSV.setSheetTypeId(osParam.getCsvSheetTypeID().toString());
		
		//   3.4. Seller/Buyer list
		DownloadCSVSettings downloadCSVSettings = new DownloadCSVSettings();
		List<Integer> selectedSellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		List<Integer> selectedBuyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		downloadCSVSettings.setStartDate(osParam.getStartDate());
		downloadCSVSettings.setEndDate(osParam.getEndDate());
		downloadCSVSettings.setSelectedSellerIds(selectedSellerIds);
		downloadCSVSettings.setSelectedBuyerIds(selectedBuyerIds);
		initCSV.setSelectedSellerIds(selectedSellerIds);
		initCSV.setSelectedBuyerIds(selectedBuyerIds);
		
		loadSellerList(user, downloadCSVSettings);
		loadBuyerList(user, downloadCSVSettings);
		initCSV.setSellersList(downloadCSVSettings.getSellersList());
		initCSV.setBuyersList(downloadCSVSettings.getBuyersList());
		
		
		//   3.5. Category
		List<Integer> selectedCategoryId = new ArrayList<Integer>();
		selectedCategoryId.add(osParam.getCategoryId());
		initCSV.setSelectedCategoryIds(selectedCategoryId);
		
		return initCSV;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DownloadCSVSettings loadBuyerList(User user, DownloadCSVSettings downloadCSVSettings){
		
		//Integer roleId = Integer.valueOf(user.getRole().getRoleId().toString());
		Long longRoleId = user.getRole().getRoleId();
		Integer userId = user.getUserId();
		List<Integer> sellerIds = new ArrayList<Integer>();
		String dateFrom = downloadCSVSettings.getStartDate();
		String dateTo = downloadCSVSettings.getEndDate();
		if(StringUtil.isNullOrEmpty(downloadCSVSettings.getEndDate())){
			dateTo = dateFrom;
		}
		List unsortedList = new ArrayList();
		List<FilteredIDs> sortedFilteredIDs = new ArrayList<FilteredIDs>();
		Integer dealingRelationId = null;
		// TODO:[Refactor] when getting dealing pattern always consider the date
		// 
		if(longRoleId.equals(RoleConstants.ROLE_SELLER_ADMIN)){
			List<Integer> selectedSellerIds = downloadCSVSettings.getSelectedSellerIds();
			unsortedList = dealingPatternDao.getAllBuyerIdsBySellerIds(selectedSellerIds, dateFrom, dateTo);
			//Getting the sorting buyer preference by a user 
			sortedFilteredIDs = companybuyersSortDao.getSortedBuyers(userId);
			downloadCSVSettings.setBuyersList(SortingUtil.sort(unsortedList, sortedFilteredIDs));
		}else if(longRoleId.equals(RoleConstants.ROLE_BUYER_ADMIN)){
			dealingRelationId = DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER;
			unsortedList = dealingPatternDao.getMembersByAdminId(userId, dealingRelationId, dateFrom, dateTo);
			List<BuyersSort> sortedBuyers = companybuyersSortDao.getSortedMembersByBuyerAdminId(userId);
			for (BuyersSort _buyersSort : sortedBuyers) {
				sortedFilteredIDs.add(new FilteredIDs(_buyersSort.getBuyer().getUserId().toString(), _buyersSort.getBuyer().getName()));
			}
			downloadCSVSettings.setBuyersList(SortingUtil.sort(unsortedList, sortedFilteredIDs));
		}else if(longRoleId.equals(RoleConstants.ROLE_SELLER)){
			sellerIds.add(userId);
			unsortedList = dealingPatternDao.getAllBuyerIdsBySellerIds(sellerIds, dateFrom, dateTo);
			//Getting the sorting buyer preference by a user 
			sortedFilteredIDs = companybuyersSortDao.getSortedBuyers(userId);
			downloadCSVSettings.setBuyersList(SortingUtil.sort(unsortedList, sortedFilteredIDs));
		}else if(longRoleId.equals(RoleConstants.ROLE_BUYER)){
//			listUserIds = dealingPatternDao.getSellerIdsOfBuyerId(userId);
		}
		
		return downloadCSVSettings;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DownloadCSVSettings loadSellerList(User user, DownloadCSVSettings downloadCSVSettings){
		
		Long longRoleId = user.getRole().getRoleId();
		Integer userId = user.getUserId();
		List<Integer> buyerIds = new ArrayList<Integer>();
		String dateFrom = downloadCSVSettings.getStartDate();
		String dateTo = downloadCSVSettings.getEndDate();
		if(StringUtil.isNullOrEmpty(downloadCSVSettings.getEndDate())){
			dateTo = dateFrom;
		}
		List unsortedList = new ArrayList();
		List<FilteredIDs> sortedFilteredIDs = user.getPreference().getSortedSellers();
		if(longRoleId.equals(RoleConstants.ROLE_SELLER_ADMIN)){
			Integer dealingRelationId = DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER;
			unsortedList = dealingPatternDao.getMembersByAdminId(userId, dealingRelationId, dateFrom, dateTo);
			downloadCSVSettings.setSellersList(SortingUtil.sort(unsortedList, sortedFilteredIDs));
		}else if(longRoleId.equals(RoleConstants.ROLE_BUYER_ADMIN)){
			unsortedList = 
				dealingPatternDao.getAllSellerIdsByBuyerIds(downloadCSVSettings.getSelectedBuyerIds(), dateFrom, dateTo);
			downloadCSVSettings.setSellersList(SortingUtil.sort(unsortedList, sortedFilteredIDs));
		}else if(longRoleId.equals(RoleConstants.ROLE_SELLER)){
			//
		}else if(longRoleId.equals(RoleConstants.ROLE_BUYER)){
			buyerIds.add(userId);
			unsortedList = dealingPatternDao.getAllSellerIdsByBuyerIds(buyerIds, dateFrom, dateTo);
			downloadCSVSettings.setSellersList(SortingUtil.sort(unsortedList, sortedFilteredIDs));
		}
		
		return downloadCSVSettings;
	}
}
