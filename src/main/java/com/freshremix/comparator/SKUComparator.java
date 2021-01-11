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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.freshremix.constants.SKUColumnConstants;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.SKUTemplate;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.StringUtil;

/**
 * @author nvelasquez
 *
 */
public class SKUComparator implements Comparator<Object> {
	
	protected List<Integer> skuSortOrder;
	protected List<Integer> skuGroupSort;
	protected List<Integer> sortedCompanies;
	protected List<Integer> sortedSellers;
	
	public SKUComparator() {
		
	}
	
	public SKUComparator(List<Integer> skuGroupSort) {
		this.skuSortOrder.add(SKUColumnConstants.ID_SKU_ID);
		this.skuGroupSort = skuGroupSort;
	}
	
	public SKUComparator(List<Integer> skuSortOrder, List<Integer> skuGroupSort,  
			List<Integer> sortedCompanies, List<Integer> sortedSellers) {
		
		this.skuSortOrder = new ArrayList<Integer>(); 
		this.skuSortOrder.addAll(skuSortOrder);
		this.skuSortOrder.add(SKUColumnConstants.ID_SKU_ID);
		
		this.skuGroupSort = skuGroupSort;
		
		this.sortedCompanies = sortedCompanies;
		
		this.sortedSellers = sortedSellers;		
		
	}
	
	public int compare(Object a, Object b) {

		SKUTemplate skuObj1 = (SKUTemplate) a;
		SKUTemplate skuObj2 = (SKUTemplate) b;
		int compare = 0;
		for (int i = 0; i < this.skuSortOrder.size(); i++) {
			Integer columnId = this.skuSortOrder.get(i);
			if (columnId.equals(SKUColumnConstants.ID_COMPANY_NAME)) {
				
				if (this.sortedCompanies != null && this.sortedCompanies.size() > 0) {
					Integer companyId1 = skuObj1.getUser().getCompany().getCompanyId();
					Integer companyId2 = skuObj2.getUser().getCompany().getCompanyId();
					compare = this.doCompany(companyId1, companyId2);
				} else {	
					String companyName1 = skuObj1.getUser().getCompany().getShortName();
					String companyName2 = skuObj2.getUser().getCompany().getShortName();
					compare = companyName1.compareToIgnoreCase(companyName2);
				}
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_SELLER_NAME)) {
				if (this.sortedSellers != null && this.sortedSellers.size() > 0) {
					Integer sellerId1 = skuObj1.getUser().getUserId();
					Integer sellerId2 = skuObj2.getUser().getUserId();
					compare = this.doSeller(sellerId1, sellerId2);
				} else {
					String sellerName1 = skuObj1.getUser().getShortName();
					String sellerName2 = skuObj2.getUser().getShortName();
					
					compare = sellerName1.compareToIgnoreCase(sellerName2);
				}
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
			else if (columnId.equals(SKUColumnConstants.ID_EXTERNAL_ID)) {
				String unitOrder1 = StringUtil.nullToBlank(skuObj1.getExternalSkuId());
				String unitOrder2 = StringUtil.nullToBlank(skuObj2.getExternalSkuId());
				
				compare = unitOrder1.compareTo(unitOrder2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_PURCHASE_PRICE)) {
				BigDecimal purchasePrice1 = NumberUtil.nullToZero(skuObj1.getPurchasePrice());
				BigDecimal purchasePrice2 = NumberUtil.nullToZero(skuObj2.getPurchasePrice());
				
				compare = purchasePrice1.compareTo(purchasePrice2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_SELLING_PRICE)) {
				BigDecimal sellingPrice1 = NumberUtil.nullToZero(skuObj1.getSellingPrice());
				BigDecimal sellingPrice2 = NumberUtil.nullToZero(skuObj2.getSellingPrice());
				
				compare = sellingPrice1.compareTo(sellingPrice2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_SELLING_UOM)) {
				String sellingUom1 = skuObj1.getSellingUom() == null ? StringUtil.nullToBlank(skuObj1.getSellingUom()) : skuObj1.getSellingUom().getOrderUnitId().toString();
				String sellingUom2 = skuObj2.getSellingUom() == null ? StringUtil.nullToBlank(skuObj2.getSellingUom()) : skuObj2.getSellingUom().getOrderUnitId().toString();
				
				compare = sellingUom1.compareTo(sellingUom2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_SKU_COMMENT)) {
				String skuComment1 = StringUtil.nullToBlank(skuObj1.getSkuComment());
				String skuComment2 = StringUtil.nullToBlank(skuObj2.getSkuComment());
				
				compare = skuComment1.compareToIgnoreCase(skuComment2);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_01)) {
				String column011 = StringUtil.nullToBlank(skuObj1.getColumn01());
				String column012 = StringUtil.nullToBlank(skuObj2.getColumn01());
				
				compare = column011.compareToIgnoreCase(column012);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_02)) {
				String column021 = StringUtil.nullToBlank(skuObj1.getColumn02());
				String column022 = StringUtil.nullToBlank(skuObj2.getColumn02());
				
				compare = column021.compareToIgnoreCase(column022);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_03)) {
				String column031 = StringUtil.nullToBlank(skuObj1.getColumn03());
				String column032 = StringUtil.nullToBlank(skuObj2.getColumn03());
				
				compare = column031.compareToIgnoreCase(column032);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_04)) {
				String column041 = StringUtil.nullToBlank(skuObj1.getColumn04());
				String column042 = StringUtil.nullToBlank(skuObj2.getColumn04());
				
				compare = column041.compareToIgnoreCase(column042);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_05)) {
				String column051 = StringUtil.nullToBlank(skuObj1.getColumn05());
				String column052 = StringUtil.nullToBlank(skuObj2.getColumn05());
				
				compare = column051.compareToIgnoreCase(column052);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_06)) {
				String column061 = StringUtil.nullToBlank(skuObj1.getColumn06());
				String column062 = StringUtil.nullToBlank(skuObj2.getColumn06());
				
				compare = column061.compareToIgnoreCase(column062);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_07)) {
				String column071 = StringUtil.nullToBlank(skuObj1.getColumn07());
				String column072 = StringUtil.nullToBlank(skuObj2.getColumn07());
				
				compare = column071.compareToIgnoreCase(column072);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_08)) {
				String column081 = StringUtil.nullToBlank(skuObj1.getColumn08());
				String column082 = StringUtil.nullToBlank(skuObj2.getColumn08());
				
				compare = column081.compareToIgnoreCase(column082);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_09)) {
				String column091 = StringUtil.nullToBlank(skuObj1.getColumn09());
				String column092 = StringUtil.nullToBlank(skuObj2.getColumn09());
				
				compare = column091.compareToIgnoreCase(column092);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_10)) {
				String column101 = StringUtil.nullToBlank(skuObj1.getColumn10());
				String column102 = StringUtil.nullToBlank(skuObj2.getColumn10());
				
				compare = column101.compareToIgnoreCase(column102);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_11)) {
				String column111 = StringUtil.nullToBlank(skuObj1.getColumn11());
				String column112 = StringUtil.nullToBlank(skuObj2.getColumn11());
				
				compare = column111.compareToIgnoreCase(column112);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_12)) {
				String column121 = StringUtil.nullToBlank(skuObj1.getColumn12());
				String column122 = StringUtil.nullToBlank(skuObj2.getColumn12());
				
				compare = column121.compareToIgnoreCase(column122);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_13)) {
				String column131 = StringUtil.nullToBlank(skuObj1.getColumn13());
				String column132 = StringUtil.nullToBlank(skuObj2.getColumn13());
				
				compare = column131.compareToIgnoreCase(column132);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_14)) {
				String column141 = StringUtil.nullToBlank(skuObj1.getColumn14());
				String column142 = StringUtil.nullToBlank(skuObj2.getColumn14());
				
				compare = column141.compareToIgnoreCase(column142);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_15)) {
				String column151 = StringUtil.nullToBlank(skuObj1.getColumn15());
				String column152 = StringUtil.nullToBlank(skuObj2.getColumn15());
				
				compare = column151.compareToIgnoreCase(column152);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_16)) {
				String column161 = StringUtil.nullToBlank(skuObj1.getColumn16());
				String column162 = StringUtil.nullToBlank(skuObj2.getColumn16());
				
				compare = column161.compareToIgnoreCase(column162);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_17)) {
				String column171 = StringUtil.nullToBlank(skuObj1.getColumn17());
				String column172 = StringUtil.nullToBlank(skuObj2.getColumn17());
				
				compare = column171.compareToIgnoreCase(column172);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_18)) {
				String column181 = StringUtil.nullToBlank(skuObj1.getColumn18());
				String column182 = StringUtil.nullToBlank(skuObj2.getColumn18());
				
				compare = column181.compareToIgnoreCase(column182);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_19)) {
				String column191 = StringUtil.nullToBlank(skuObj1.getColumn19());
				String column192 = StringUtil.nullToBlank(skuObj2.getColumn19());
				
				compare = column191.compareToIgnoreCase(column192);
				if (compare != 0)
					return compare;
			}
			else if (columnId.equals(SKUColumnConstants.ID_COLUMN_20)) {
				String column201 = StringUtil.nullToBlank(skuObj1.getColumn20());
				String column202 = StringUtil.nullToBlank(skuObj2.getColumn20());
				
				compare = column201.compareToIgnoreCase(column202);
				if (compare != 0)
					return compare;
			}
		}
	
		return 0;
	}
	
	protected int doSKUGroup(Object a, Object b) {
		int result = 0;
		if (a == null || b == null) return result;
		if (this.skuGroupSort != null && this.skuGroupSort.size() > 0) {
			SKUGroup groupA = (SKUGroup) a;
			SKUGroup groupB = (SKUGroup) b;
			Integer intA = (Integer) groupA.getSkuGroupId();
			Integer intB = (Integer) groupB.getSkuGroupId();

			if (this.skuGroupSort.indexOf(intA) == -1
					&& this.skuGroupSort.indexOf(intB) == -1) {
				result = ((groupA.getDescription()).compareToIgnoreCase(groupB
						.getDescription()));
			}
			else if (this.skuGroupSort.indexOf(intA) == -1) {
				result = this.skuGroupSort.size();
			}
			else if (this.skuGroupSort.indexOf(intB) == -1) {
				result = 0 - this.skuGroupSort.size();
			}
			else {
				result = this.skuGroupSort.indexOf(intA)
						- this.skuGroupSort.indexOf(intB);
			}

		}
		return result;
	}

	protected int doCompany(Object a, Object b) {
		int result = 0;
		if (a == null || b == null) return result;
		if (this.sortedCompanies != null && this.sortedCompanies.size() > 0) {
			Integer intA = (Integer) a;
			Integer intB = (Integer) b;

			if (this.sortedCompanies.indexOf(intA) == -1) {
				result = this.sortedCompanies.size();
			}
			else if (this.sortedCompanies.indexOf(intB) == -1) {
				result = 0 - this.sortedCompanies.size();
			}
			else {
				result = this.sortedCompanies.indexOf(intA)
						- this.sortedCompanies.indexOf(intB);
			}

		}
		return result;
	}

	protected int doSeller(Object a, Object b) {
		int result = 0;
		if (a == null || b == null) return result;
		if (this.sortedSellers != null && this.sortedSellers.size() > 0) {
			Integer intA = (Integer) a;
			Integer intB = (Integer) b;

			if (this.sortedSellers.indexOf(intA) == -1) {
				result = this.sortedSellers.size();
			}
			else if (this.sortedSellers.indexOf(intB) == -1) {
				result = 0 - this.sortedSellers.size();
			}
			else {
				result = this.sortedSellers.indexOf(intA)
						- this.sortedSellers.indexOf(intB);
			}

		}
		return result;
	}
}
