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
 * May 26, 2010		nvelasquez		
 */
package com.freshremix.comparator;

import java.math.BigDecimal;
import java.util.List;

import com.freshremix.constants.SKUColumnConstants;
import com.freshremix.model.AkadenSKU;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.StringUtil;

/**
 * @author nvelasquez
 *
 */
public class AkadenSKUComparator extends SKUComparator {
	
	public AkadenSKUComparator(List<Integer> skuGroupSort) {
		this.skuSortOrder.add(SKUColumnConstants.ID_SKU_ID);
		this.skuGroupSort = skuGroupSort;
	}
	
	public AkadenSKUComparator(List<Integer> skuSortOrder, List<Integer> skuGroupSort) {
		this.skuSortOrder = skuSortOrder;
		
		this.skuSortOrder.add(SKUColumnConstants.ID_SKU_ID);
		
		this.skuGroupSort = skuGroupSort;
	}
	
	public int compare(Object a, Object b) {

		AkadenSKU skuObj1 = (AkadenSKU) a;
		AkadenSKU skuObj2 = (AkadenSKU) b;
		
		int compare = 0;
		for (int i = 0; i < this.skuSortOrder.size(); i++) {
			Integer columnId = this.skuSortOrder.get(i);
			if (columnId.equals(SKUColumnConstants.ID_COMPANY_NAME)) {
				String companyName1 = skuObj1.getUser().getCompany().getShortName();
				String companyName2 = skuObj2.getUser().getCompany().getShortName();

				compare = companyName1.compareToIgnoreCase(companyName2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_SELLER_NAME)) {
				String sellerName1;
				String sellerName2;
				// if SKU is created by Buyer Admin, use Buyer Admin's Name in
				// Sorting
				/*	if (skuObj1.getBuyerAdminEntity() != null) {
					entityA = skuObj1.getBuyerAdminEntity().getShortName()
							.toUpperCase();
				} else */
				
				sellerName1 = skuObj1.getUser().getShortName();
				sellerName2 = skuObj2.getUser().getShortName();
				
				compare = sellerName1.compareToIgnoreCase(sellerName2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_GROUP_NAME)) {
				if (this.skuGroupSort != null && this.skuGroupSort.size() > 0) {
					compare = this.doSKUGroup(skuObj1.getSkuGroup(), skuObj2
							.getSkuGroup());
				} else {
					compare = skuObj1.getSkuGroup().getDescription()
						.compareToIgnoreCase(skuObj2.getSkuGroup().getDescription());
				}
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_SKU_NAME)) {
				String skuName1 = skuObj1.getSkuName();
				String skuName2 = skuObj2.getSkuName();
				
				compare = skuName1.compareToIgnoreCase(skuName2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_MARKET_CONDITION)) {
				String marketName1 = StringUtil.nullToBlank(skuObj1.getMarket());
				String marketName2 = StringUtil.nullToBlank(skuObj2.getMarket());
				
				compare = marketName1.compareToIgnoreCase(marketName2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_AREA_PRODUCTION)) {
				String home1 = StringUtil.nullToBlank(skuObj1.getLocation());
				String home2 = StringUtil.nullToBlank(skuObj2.getLocation());
				
				compare = home1.compareToIgnoreCase(home2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_CLASS1)) {
				String grade1 = StringUtil.nullToBlank(skuObj1.getGrade());
				String grade2 = StringUtil.nullToBlank(skuObj2.getGrade());
				
				compare = grade1.compareToIgnoreCase(grade2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_CLASS2)) {
				String clazzName1 = StringUtil.nullToBlank(skuObj1.getClazz());
				String clazzName2 = StringUtil.nullToBlank(skuObj2.getClazz());
				
				compare = clazzName1.compareToIgnoreCase(clazzName2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_PACKAGE_QTY)) {
				BigDecimal packQty1 = NumberUtil.nullToZero(skuObj1.getPackageQuantity());
				BigDecimal packQty2 = NumberUtil.nullToZero(skuObj2.getPackageQuantity());
				
				compare = packQty1.compareTo(packQty2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_PACKAGE_TYPE)) {
				String packType1 = StringUtil.nullToBlank(skuObj1.getPackageType());
				String packType2 = StringUtil.nullToBlank(skuObj2.getPackageType());
				
				compare = packType1.compareTo(packType2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_PRICE1)) {
				BigDecimal price11 = NumberUtil.nullToZero(skuObj1.getPrice1());
				BigDecimal price12 = NumberUtil.nullToZero(skuObj2.getPrice1());
				
				compare = price11.compareTo(price12);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_PRICE2)) {
				BigDecimal price21 = NumberUtil.nullToZero(skuObj1.getPrice2());
				BigDecimal price22 = NumberUtil.nullToZero(skuObj2.getPrice2());
				
				compare = price21.compareTo(price22);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_PRICE_WO_TAX)) {
				BigDecimal priceWoTax1 = NumberUtil.nullToZero(skuObj1.getPriceWithoutTax());
				BigDecimal priceWoTax2 = NumberUtil.nullToZero(skuObj2.getPriceWithoutTax());
				
				compare = priceWoTax1.compareTo(priceWoTax2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_PRICE_W_TAX)) {
				BigDecimal priceWTax1 = NumberUtil.nullToZero(skuObj1.getPriceWithTax());
				BigDecimal priceWTax2 = NumberUtil.nullToZero(skuObj2.getPriceWithTax());
				
				compare = priceWTax1.compareTo(priceWTax2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_UOM)) {
				String unitOrder1 = StringUtil.nullToBlank(skuObj1.getOrderUnit().getOrderUnitId());
				String unitOrder2 = StringUtil.nullToBlank(skuObj2.getOrderUnit().getOrderUnitId());
				
				compare = unitOrder1.compareTo(unitOrder2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_SKU_ID)) {
				Integer skuId1 = NumberUtil.nullToZero(skuObj1.getSkuId());
				Integer skuId2 = NumberUtil.nullToZero(skuObj2.getSkuId());
				
				compare = skuId1.compareTo(skuId2);
				if (compare != 0)
					return compare;
			}
		}
	
		return 0;
	}
	
}
