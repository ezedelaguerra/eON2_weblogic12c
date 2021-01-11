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
 * May 20, 2010		Jovino Balunan		
 */
package com.freshremix.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SKUColumnConstantsCSV;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.Category;
import com.freshremix.model.EONHeader;
import com.freshremix.model.Item;
import com.freshremix.model.Order;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUBA;
import com.freshremix.model.SKUTemplate;
import com.freshremix.model.SheetData;
import com.freshremix.model.User;

/**
 * @author Jovino Balunan
 * 
 */
public class DownloadCSVUtil {

	private static String CSV_DELIMETER = ",";

	public static final String[] CSV_COLUMNS_HEADER = {
		SKUColumnConstantsCSV.SKU_ID,  
		SKUColumnConstantsCSV.SKU_NAME,
		SKUColumnConstantsCSV.DATE, 
		SKUColumnConstantsCSV.QUANTITY,
		SKUColumnConstantsCSV.UOM, 
		SKUColumnConstantsCSV.PACKAGE_QTY,
		SKUColumnConstantsCSV.PRICE_WO_TAX,
		SKUColumnConstantsCSV.SKU_GROUP_ID, 
		SKUColumnConstantsCSV.SKU_GROUP_NAME,
		SKUColumnConstantsCSV.BUYER_ID,	
		SKUColumnConstantsCSV.BUYER_NAME,
		SKUColumnConstantsCSV.SELLER_ID,
		SKUColumnConstantsCSV.SELLER_NAME,
		SKUColumnConstantsCSV.MARKET_CONDITION,
		SKUColumnConstantsCSV.AREA_PRODUCTION, 
		SKUColumnConstantsCSV.CLASS1,  
		SKUColumnConstantsCSV.CLASS2,
		SKUColumnConstantsCSV.PACKAGE_TYPE,
		SKUColumnConstantsCSV.PRICE_W_TAX,
		SKUColumnConstantsCSV.CATEGORY,
		SKUColumnConstantsCSV.SKU_ORDER_NUMBER,
		SKUColumnConstantsCSV.BUYER_ORDER_NUMBER,
		SKUColumnConstantsCSV.FINALIZATION_FLAG,
		SKUColumnConstantsCSV.SHEET_TYPE,
		SKUColumnConstantsCSV.EXTERNAL_ID,
		SKUColumnConstantsCSV.OPERATION_FLAG,
		SKUColumnConstantsCSV.DISPLAY_FLAG,
		SKUColumnConstantsCSV.PRICE_1,
		SKUColumnConstantsCSV.PRICE_2,
		SKUColumnConstantsCSV.COMPANY_ID,
		SKUColumnConstantsCSV.COMPANY_NAME,
		SKUColumnConstantsCSV.SKU_MAX_LIMIT,
		SKUColumnConstantsCSV.COMMENTS, 
		SKUColumnConstantsCSV.FINALIZE,
		SKUColumnConstantsCSV.PUBLISH,
		SKUColumnConstantsCSV.LOCK,
		SKUColumnConstantsCSV.APPROVE,
		SKUColumnConstantsCSV.SELLING_PRICE,
		SKUColumnConstantsCSV.PURCHASE_PRICE,
		SKUColumnConstantsCSV.SKU_COMMENT,
		SKUColumnConstantsCSV.SELLING_UOM,
		SKUColumnConstantsCSV.COLUMN_01,
		SKUColumnConstantsCSV.COLUMN_02,
		SKUColumnConstantsCSV.COLUMN_03,
		SKUColumnConstantsCSV.COLUMN_04,
		SKUColumnConstantsCSV.COLUMN_05,
		SKUColumnConstantsCSV.COLUMN_06,
		SKUColumnConstantsCSV.COLUMN_07,
		SKUColumnConstantsCSV.COLUMN_08,
		SKUColumnConstantsCSV.COLUMN_09,
		SKUColumnConstantsCSV.COLUMN_10,
		SKUColumnConstantsCSV.COLUMN_11,
		SKUColumnConstantsCSV.COLUMN_12,
		SKUColumnConstantsCSV.COLUMN_13,
		SKUColumnConstantsCSV.COLUMN_14,
		SKUColumnConstantsCSV.COLUMN_15,
		SKUColumnConstantsCSV.COLUMN_16,
		SKUColumnConstantsCSV.COLUMN_17,
		SKUColumnConstantsCSV.COLUMN_18,
		SKUColumnConstantsCSV.COLUMN_19,
		SKUColumnConstantsCSV.COLUMN_20
	};
	
	public static final String createCSVList(SheetData sheetData, User user, EONHeader eonHeader, Map<String,Category> categoryMap) {

		// CSV List of SKUs is ordered according to
		// 1. selected DeliveryDates
		// 2. selected Categories
		// 3. selected Sellers (user Seller or Sellers of user Seller Admin,
		// Buyer, Buyer Admin)
		// 4. selected Buyers (user Buyer or Buyers of user Seller, Seller Admin, Buyer Admin)
		// 5. SKU List
		// 6. OrderItem List

		StringBuilder csvListStr = new StringBuilder(); 
		// Set Headers
		int cnt=0; 
		for (String header: CSV_COLUMNS_HEADER){
			cnt++;
			if (CSV_COLUMNS_HEADER.length == cnt) {
				if (eonHeader.getCSVName(header) != null)
					csvListStr.append(eonHeader.getCSVName(header)).append("\n");
				else
					csvListStr.append(header).append("\n");
			}else {
				if (eonHeader.getCSVName(header) != null)
					csvListStr.append(eonHeader.getCSVName(header)).append(CSV_DELIMETER);
				else
					csvListStr.append(header).append(CSV_DELIMETER);
			}
		}
		
		int ctrSku, ctrBuyerId;

		Map<Integer, OrderUnit> orderUnitMap = sheetData.getOrderUnitMap(); 
		Map<Integer, OrderUnit> sellingUomMap = sheetData.getSellingUomMap(); 
		
		// 1. selected DeliveryDates
		for (String deliverydate : sheetData.getDeliveryDates()) {

			Map<Integer, Map<Integer, Order>> sellBuyOrderMap = sheetData.getDateSellBuyOrderMap()
					.get(deliverydate);
			if (sellBuyOrderMap == null)
				continue;
	    // 2. selected Categories
			for (Integer categoryId : sheetData.getSelectedCategoryIds()) {
				List<Object> skuList = sheetData.getSkuList(categoryId);
				if(skuList == null) skuList = new ArrayList<Object>();
	    // 3. selected Sellers 
				for (Integer sellerId : sheetData.getSelectedSellerIds()) {

					Map<Integer, Order> buyOrderMap = sellBuyOrderMap.get(sellerId);
					if (buyOrderMap == null)
						continue;

					ctrBuyerId = 0;
		// 4. selected Buyers
					for (Integer buyerId : sheetData.getSelectedBuyerIds()) {
						ctrBuyerId = ctrBuyerId + 1;

						Order order = buyOrderMap.get(buyerId);
						if (order == null)
							continue;

						ctrSku = 1;
		// 5. SKU List
						for (Object obj : skuList) {
							//SKU sku = obj instanceof SKU == true ? (SKU) obj : null; 
							SKUTemplate sku = (SKUTemplate) obj; 
							String skuKey = null;
							
							if (obj instanceof SKUBA){
								if (sku == null) continue;
								skuKey = sku.getSkuId() + "_" + sku.getSkuBAId(); 
							} else if (obj instanceof SKU){
								if (sku == null) continue;
								skuKey = sku.getSkuId().toString();
							}
							
							//String skuKey = sku.getSkuId().toString();
							if (obj instanceof AkadenSKU) {
								AkadenSKU a = (AkadenSKU)obj;
								if (a.getAkadenSkuId() > 0)
									skuKey = "A_" + a.getAkadenSkuId();
							}
							
		// 6. OrderItem 
							// skip if no order item for sku
							Map<String, Map<Integer, Item>> dateBuyOrderItemMap = sheetData
									.getSkuDateBuyOrderItemMap().get(skuKey);
							if (dateBuyOrderItemMap == null)
								continue;

							// skip if no order item for date
							Map<Integer, Item> buyOrderItemMap = dateBuyOrderItemMap
									.get(deliverydate);
							if (buyOrderItemMap == null)
								continue;

							// skip if no order item for buyer
							Item orderItem = buyOrderItemMap.get(buyerId);
							if (orderItem == null)
								continue;

							// skip if no order not equal with order
							if (!orderItem.getOrderId().equals(order.getOrderId()))
								continue;

							Long longRoleId = user.getRole().getRoleId();
							if (longRoleId.equals(RoleConstants.ROLE_BUYER)) { 
								if (!StringUtil.isNullOrEmpty(orderItem.getBaosVisFlag()) &&
									orderItem.getBaosVisFlag().equals("0")) continue;
							}
									
							//get user buyer for buyer name
							User buyer = sheetData.getSelectedBuyers().get(buyerId);

							// Create CSV Body
							csvListStr.append(createCSVBody(user, deliverydate, buyerId, sellerId,
									categoryId, order, sku, sheetData.getSheetTypeId(), orderItem,
									ctrSku, ctrBuyerId, buyer.getName(), orderUnitMap, sellingUomMap, categoryMap));
							ctrSku = ctrSku + 1;
						}
					}
				}
			}
		}

		return csvListStr.toString();
	}

	private static StringBuilder createCSVBody(User user, String deliverydate, Integer buyerId,
			Integer sellerId, Integer categoryId, Order order, SKUTemplate sku, Integer sheetTypeId,
			Item orderItem, int ctrSku, int ctrBuyerId, String buyerName, 
			Map<Integer, OrderUnit> orderUnitMap,
			Map<Integer, OrderUnit> sellingUomMap, Map<String,Category> categoryMap) {

		StringBuilder sb = new StringBuilder();

		sb.append(StringUtil.nullToZero(sku.getSkuId())).append(CSV_DELIMETER);           // A. SKU ID column
		sb.append(StringUtil.formatCSVText(sku.getSkuName(), "")).append(CSV_DELIMETER); 	// B. SKU Name/Description
		sb.append(deliverydate).append(CSV_DELIMETER); 		                            // C. DELIVERY DATE
		sb.append(StringUtil.nullToBlank(orderItem.getQuantity())).append(CSV_DELIMETER); // D. QUANTITY  
		
		if (sku.getOrderUnit().getOrderUnitName() == null){                          // E. UNIT OF ORDER
			OrderUnit orderUnit = orderUnitMap.get(sku.getOrderUnit().getOrderUnitId());
			sb.append(StringUtil.formatCSVText(orderUnit.getOrderUnitName(),"")).append(CSV_DELIMETER);
		}else{
			sb.append(StringUtil.formatCSVText(sku.getOrderUnit().getOrderUnitName(), "")).append(CSV_DELIMETER);
		}
		
		sb.append(StringUtil.nullToZero(sku.getPackageQuantity())).append(CSV_DELIMETER); // F. PACKAGE QUANTITY
		sb.append(StringUtil.nullToZero(sku.getPriceWithoutTax())).append(CSV_DELIMETER); // G. PRICE WITHOUT TAX
		sb.append(StringUtil.nullToBlank(sku.getSkuGroup().getSkuGroupId())).append(CSV_DELIMETER); // H. SKU GROUP ID
		sb.append(StringUtil.formatCSVText(sku.getSkuGroup().getDescription(), "")).append(CSV_DELIMETER); // I. SKU GROUP NAME
		sb.append(buyerId.toString()).append(CSV_DELIMETER);  							// J. BUYER ID
		sb.append(buyerName).append(CSV_DELIMETER);                                       // K. BUYER NAMES
		sb.append(sku.getUser().getUserId().toString()).append(CSV_DELIMETER);            // L. SELLER ID
		sb.append(sku.getUser().getName()).append(CSV_DELIMETER);                         // M. SELLER NAME
		sb.append(StringUtil.formatCSVText(sku.getMarket(), "")).append(CSV_DELIMETER);   // N. MARKET NAME
		sb.append(StringUtil.formatCSVText(sku.getLocation(), "")).append(CSV_DELIMETER); // O. AREA OF PRODUCTION
		sb.append(StringUtil.formatCSVText(sku.getGrade(), "")).append(CSV_DELIMETER);    // Q. CLASS 1
		sb.append(StringUtil.formatCSVText(sku.getClazz(), "")).append(CSV_DELIMETER);    // P. CLASS 2
		sb.append(StringUtil.formatCSVText(sku.getPackageType(), "")).append(CSV_DELIMETER); // R. PACKAGE TYPE
		if (sku.getPriceWithTax() == null)                                          // S. PRICE WITH TAX
			sb.append(StringUtil.nullToZero(sku.getPriceWithTax())).append(CSV_DELIMETER);
		else
			sb.append(sku.getPriceWithTax().setScale(0, BigDecimal.ROUND_HALF_UP)).append(CSV_DELIMETER);
		sb.append(CategoryUtil.getCategoryDesc(categoryId, categoryMap)).append(CSV_DELIMETER); // T. CATEGORY ID
		sb.append(String.format("%06d", ctrSku)).append(CSV_DELIMETER);                   // U. SKU COUNTER
		sb.append(String.format("%06d", ctrBuyerId)).append(CSV_DELIMETER);               // V. BUYER COUNTER
		sb.append("").append(CSV_DELIMETER);                                              // W. FINALIZATION FLAG
		sb.append(sheetTypeId.toString()).append(CSV_DELIMETER);                          // X. SHEET TYPE ID
		sb.append(StringUtil.formatCSVText(sku.getExternalSkuId(), "")).append(CSV_DELIMETER);  // Y. EXTERNAL SKU ID
		sb.append("").append(CSV_DELIMETER);                                              // Z. OPERATION FLAG
		
		Long longRoleId = user.getRole().getRoleId();
		if (longRoleId.equals(RoleConstants.ROLE_BUYER)) { 
			sb.append(StringUtil.nullToBlank(orderItem.getBaosVisFlag())).append(CSV_DELIMETER); // AA. DISPLAY FLAG
		} else {	
			sb.append(StringUtil.nullToBlank(orderItem.getSosVisFlag())).append(CSV_DELIMETER); 
		}
		
		/**
		 * ROLE        Price1   -   Price2  -  SKU Max Limit 
		 * BUYER :        x           x             x
		 * B.ADMIN:       x           o             x
         * SELLER:        o           o             o
		 * S.ADMIN:       o           o             o
		 */
		if (longRoleId.equals(RoleConstants.ROLE_BUYER)) { 
			sb.append("").append(CSV_DELIMETER);                                         // AB. PRICE 1
			sb.append("").append(CSV_DELIMETER);                                         // AC. PRICE 2
		} else if (longRoleId.equals(RoleConstants.ROLE_BUYER_ADMIN)) {
			sb.append("").append(CSV_DELIMETER);                
			sb.append(StringUtil.nullToZero(sku.getPrice2())).append(CSV_DELIMETER);      
		} else {
			sb.append(StringUtil.nullToZero(sku.getPrice1())).append(CSV_DELIMETER); 
			sb.append(StringUtil.nullToZero(sku.getPrice2())).append(CSV_DELIMETER); 
		}
		
		sb.append(StringUtil.nullToZero(sku.getUser().getCompany().getCompanyId())).append(CSV_DELIMETER); // AD. COMPANY ID
		sb.append(StringUtil.nullToZero(sku.getUser().getCompany().getCompanyName())).append(CSV_DELIMETER); // AE. COMPANY NAME
		
		if (longRoleId.equals(RoleConstants.ROLE_SELLER) ||
			longRoleId.equals(RoleConstants.ROLE_SELLER_ADMIN)) { 
			sb.append(StringUtil.nullToZero(sku.getSkuMaxLimit())).append(CSV_DELIMETER); // AF. SKU Max Limit                                   
		} else {
			sb.append("").append(CSV_DELIMETER); 
		}
		
		/**
		 * Sheets                    COMMENTS   FINALIZE   PUBLISH   LOCK   APPROVE
		 * S/SA Seller Order Sheet      x           o         o        o       x
		 * B/BA Buyer Order Sheet       x           o         o        o       x
		 * S/SA Allocation Sheet        x           o         o        x       o
		 * B/BA Received Sheet          x           o         o        x       o
		 * S/SA Akaden Sheet            o           o         x        x       x
		 * S/SA Billing Sheet           o           o         x        x       x
		 * B/BA Billing Sheet           o           o         x        x       x
		 */
		
		sb.append(StringUtil.formatCSVText(orderItem.getComments(), "")).append(CSV_DELIMETER);			   // AG. COMMENTS
		
		if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ORDER_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_ORDER_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.BUYER_ORDER_SHEET)){
			sb.append(StringUtil.notEmptyOrNotNullToOne(order.getOrderFinalizedBy())).append(CSV_DELIMETER);  // AH. FINALIZE
			sb.append(StringUtil.notEmptyOrNotNullToOne(order.getOrderPublishedBy())).append(CSV_DELIMETER);  // AI. PUBLISH
			sb.append(StringUtil.notEmptyOrNotNullToOne(order.getOrderLockedBy())).append(CSV_DELIMETER);	    // AJ. LOCK 
			sb.append("0").append(CSV_DELIMETER); 			                                                                    // AK. APPROVE	
		}  else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ALLOCATION_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_ALLOCATION_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_RECEIVED_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.BUYER_RECEIVED_SHEET)){
			sb.append(StringUtil.notEmptyOrNotNullToOne(order.getAllocationFinalizedBy())).append(CSV_DELIMETER);  // AH. FINALIZE
			sb.append(StringUtil.notEmptyOrNotNullToOne(order.getAllocationPublishedBy())).append(CSV_DELIMETER);  // AI. PUBLISH
			sb.append(StringUtil.notEmptyOrNotNullToOne(order.getReceivedApprovedBy())).append(CSV_DELIMETER);     // AJ. LOCK 
			sb.append(StringUtil.nullToZero(orderItem.getIsApproved())).append(CSV_DELIMETER );                    // AK. APPROVE
		} else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_AKADEN_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_AKADEN_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_BILLING_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_BILLING_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_BILLING_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.BUYER_BILLING_SHEET)){
			sb.append(StringUtil.notEmptyOrNotNullToOne(order.getBillingFinalizedBy())).append(CSV_DELIMETER);     // AH. FINALIZE
			sb.append("0").append(CSV_DELIMETER);                                                                  // AI. PUBLISH
			sb.append("0").append(CSV_DELIMETER);                                                                  // AJ. LOCK 
			sb.append("0").append(CSV_DELIMETER);			                                                         // AK. APPROVE
		} 
		
		/**
		 * Sheets                 SELLING PRICE  PURCHASE PRICE   SKU COMMENTS   SELLING UOM
		 * S/SA Seller Order Sheet      x              x               x              x
		 * B/BA Buyer Order Sheet       o              o               o              o
		 * S/SA Allocation Sheet        x              x               x              x
		 * B/BA Received Sheet          o              o               o              o
		 * S/SA Akaden Sheet            x              x               x              x
		 * S/SA Billing Sheet           x              x               x              x
		 * B/BA Billing Sheet           o              o               o              o
		 */
		if (longRoleId.equals(RoleConstants.ROLE_BUYER) || 
			longRoleId.equals(RoleConstants.ROLE_BUYER_ADMIN)) { 
			sb.append(StringUtil.nullToZero(sku.getSellingPrice())).append(CSV_DELIMETER);                        // AL. SELLING PRICE
			sb.append(StringUtil.nullToZero(sku.getPurchasePrice())).append(CSV_DELIMETER);                       // AM. PURCHASE PRICE
			sb.append(StringUtil.formatCSVText(sku.getSkuComment(), "")).append(CSV_DELIMETER);			        // AN. SKU COMMENTS
			if (sku.getSellingUom() != null){																// AO. SELLING UOM
				OrderUnit sellingUom = sellingUomMap.get(sku.getSellingUom().getOrderUnitId());
				sb.append(StringUtil.formatCSVText(sellingUom.getOrderUnitName(),"")).append(CSV_DELIMETER);
			}else{
				sb.append("").append(CSV_DELIMETER);
			}
		}else{
			sb.append("").append(CSV_DELIMETER);                                                                  // AL. SELLING PRICE
			sb.append("").append(CSV_DELIMETER);                                                                  // AM. PURCHASE PRICE
			sb.append("").append(CSV_DELIMETER);                                                                  // AN. SKU COMMENTS
			sb.append("").append(CSV_DELIMETER);                                                                  // AO. SELLING UOM
		}

		//20 new columns
		sb.append(StringUtil.formatCSVText(sku.getColumn01(), "")).append(CSV_DELIMETER);   						// AP. COLUMN 01
		sb.append(StringUtil.formatCSVText(sku.getColumn02(), "")).append(CSV_DELIMETER);   						// AQ. COLUMN 02
		sb.append(StringUtil.formatCSVText(sku.getColumn03(), "")).append(CSV_DELIMETER);   						// AR. COLUMN 03
		sb.append(StringUtil.formatCSVText(sku.getColumn04(), "")).append(CSV_DELIMETER);   						// AS. COLUMN 04
		sb.append(StringUtil.formatCSVText(sku.getColumn05(), "")).append(CSV_DELIMETER);   						// AT. COLUMN 05
		sb.append(StringUtil.formatCSVText(sku.getColumn06(), "")).append(CSV_DELIMETER);   						// AU. COLUMN 06
		sb.append(StringUtil.formatCSVText(sku.getColumn07(), "")).append(CSV_DELIMETER);   						// AV. COLUMN 07
		sb.append(StringUtil.formatCSVText(sku.getColumn08(), "")).append(CSV_DELIMETER);   						// AW. COLUMN 08
		sb.append(StringUtil.formatCSVText(sku.getColumn09(), "")).append(CSV_DELIMETER);   						// AX. COLUMN 09
		sb.append(StringUtil.formatCSVText(sku.getColumn10(), "")).append(CSV_DELIMETER);   						// AY. COLUMN 10
		sb.append(StringUtil.formatCSVText(sku.getColumn11(), "")).append(CSV_DELIMETER);   						// AZ. COLUMN 11
		sb.append(StringUtil.formatCSVText(sku.getColumn12(), "")).append(CSV_DELIMETER);   						// BA. COLUMN 12
		sb.append(StringUtil.formatCSVText(sku.getColumn13(), "")).append(CSV_DELIMETER);   						// BB. COLUMN 13
		sb.append(StringUtil.formatCSVText(sku.getColumn14(), "")).append(CSV_DELIMETER);   						// BC. COLUMN 14
		sb.append(StringUtil.formatCSVText(sku.getColumn15(), "")).append(CSV_DELIMETER);   						// BD. COLUMN 15
		sb.append(StringUtil.formatCSVText(sku.getColumn16(), "")).append(CSV_DELIMETER);   						// BE. COLUMN 16
		sb.append(StringUtil.formatCSVText(sku.getColumn17(), "")).append(CSV_DELIMETER);   						// BF. COLUMN 17
		sb.append(StringUtil.formatCSVText(sku.getColumn18(), "")).append(CSV_DELIMETER);   						// BG. COLUMN 18
		sb.append(StringUtil.formatCSVText(sku.getColumn19(), "")).append(CSV_DELIMETER);   						// BH. COLUMN 19
		sb.append(StringUtil.formatCSVText(sku.getColumn20(), ""));					   						// BI. COLUMN 20
		
		sb.append("\n");
		
		return sb;
	}
}
