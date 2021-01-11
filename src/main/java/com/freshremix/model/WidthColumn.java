package com.freshremix.model;

import static com.freshremix.constants.SKUColumnConstants.AREA_OF_PRODUCTION_UI;
import static com.freshremix.constants.SKUColumnConstants.CLASS1_UI;
import static com.freshremix.constants.SKUColumnConstants.CLASS2_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN01_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN02_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN03_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN04_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN05_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN06_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN07_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN08_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN09_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN10_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN11_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN12_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN13_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN14_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN15_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN16_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN17_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN18_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN19_UI;
import static com.freshremix.constants.SKUColumnConstants.COLUMN20_UI;
import static com.freshremix.constants.SKUColumnConstants.COMPANY_NAME_UI;
import static com.freshremix.constants.SKUColumnConstants.GROUP_NAME_UI;
import static com.freshremix.constants.SKUColumnConstants.MARKET_CONDITION_UI;
import static com.freshremix.constants.SKUColumnConstants.PACKAGE_QTY_UI;
import static com.freshremix.constants.SKUColumnConstants.PACKAGE_TYPE_UI;
import static com.freshremix.constants.SKUColumnConstants.PRICE1_UI;
import static com.freshremix.constants.SKUColumnConstants.PRICE2_UI;
import static com.freshremix.constants.SKUColumnConstants.PRICE_WITHOUT_TAX_UI;
import static com.freshremix.constants.SKUColumnConstants.PRICE_WITH_TAX_UI;
import static com.freshremix.constants.SKUColumnConstants.PURCHASE_PRICE_UI;
import static com.freshremix.constants.SKUColumnConstants.SELLER_NAME_UI;
import static com.freshremix.constants.SKUColumnConstants.SELLING_PRICE_UI;
import static com.freshremix.constants.SKUColumnConstants.SELLING_UOM_UI;
import static com.freshremix.constants.SKUColumnConstants.SKU_COMMENT_UI;
import static com.freshremix.constants.SKUColumnConstants.SKU_NAME_UI;
import static com.freshremix.constants.SKUColumnConstants.UOM_UI;
import static com.freshremix.constants.SKUColumnConstants.ROW_QTY_UI;
import static com.freshremix.constants.SKUColumnConstants.SKU_MAX_LIMIT_UI;

import java.math.BigDecimal;
import java.util.Map;

import com.freshremix.util.NumberUtil;

public class WidthColumn {

	private Integer userId;
	private Integer companyName;
	private Integer sellerName;
	private Integer groupName;
	private Integer marketCondition;
	private Integer skuName;
	private Integer areaProduction;
	private Integer clazz1;
	private Integer clazz2;
	private Integer price1;
	private Integer price2;
	private Integer priceWOTax;
	private Integer priceWTax;
	private Integer purchasePrice;
	private Integer sellingPrice;
	private Integer profitAmount;
	private Integer sellingUom;
	private Integer profitPercentage;
	private Integer packageQty;
	private Integer packageType;
	private Integer uom;
	private Integer skuComment;
	private Integer rowQty;
	private Integer skuMaxLimit;
	private Integer column01;
	private Integer column02;
	private Integer column03;
	private Integer column04;
	private Integer column05;
	private Integer column06;
	private Integer column07;
	private Integer column08;
	private Integer column09;
	private Integer column10;
	private Integer column11;
	private Integer column12;
	private Integer column13;
	private Integer column14;
	private Integer column15;
	private Integer column16;
	private Integer column17;
	private Integer column18;
	private Integer column19;
	private Integer column20;
	
	
	public WidthColumn (Map<String,BigDecimal> widthMap) {
		
		if (!NumberUtil.isNullOrZero(widthMap.get(COMPANY_NAME_UI)))
			this.companyName = widthMap.get(COMPANY_NAME_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(SELLER_NAME_UI)))
			this.sellerName = widthMap.get(SELLER_NAME_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(GROUP_NAME_UI)))
			this.groupName = widthMap.get(GROUP_NAME_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(SKU_NAME_UI)))
			this.skuName = widthMap.get(SKU_NAME_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(MARKET_CONDITION_UI)))
			this.marketCondition = widthMap.get(MARKET_CONDITION_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(AREA_OF_PRODUCTION_UI)))
			this.areaProduction = widthMap.get(AREA_OF_PRODUCTION_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(CLASS1_UI)))
			this.clazz1 = widthMap.get(CLASS1_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(CLASS2_UI)))
			this.clazz2 = widthMap.get(CLASS2_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(PACKAGE_QTY_UI)))
			this.packageQty = widthMap.get(PACKAGE_QTY_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(PACKAGE_TYPE_UI)))
			this.packageType = widthMap.get(PACKAGE_TYPE_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(PRICE1_UI)))
			this.price1 = widthMap.get(PRICE1_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(PRICE2_UI)))
			this.price2 = widthMap.get(PRICE2_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(PRICE_WITHOUT_TAX_UI)))
			this.priceWOTax = widthMap.get(PRICE_WITHOUT_TAX_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(PRICE_WITH_TAX_UI)))
			this.priceWTax = widthMap.get(PRICE_WITH_TAX_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(UOM_UI)))
			this.uom = widthMap.get(UOM_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(ROW_QTY_UI)))
			this.rowQty = widthMap.get(ROW_QTY_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(SKU_MAX_LIMIT_UI)))
			this.skuMaxLimit = widthMap.get(SKU_MAX_LIMIT_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN01_UI)))
			this.column01 = widthMap.get(COLUMN01_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN02_UI)))
			this.column02 = widthMap.get(COLUMN02_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN03_UI)))
			this.column03 = widthMap.get(COLUMN03_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN04_UI)))
			this.column04 = widthMap.get(COLUMN04_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN05_UI)))
			this.column05 = widthMap.get(COLUMN05_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN06_UI)))
			this.column06 = widthMap.get(COLUMN06_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN07_UI)))
			this.column07 = widthMap.get(COLUMN07_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN08_UI)))
			this.column08 = widthMap.get(COLUMN08_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN09_UI)))
			this.column09 = widthMap.get(COLUMN09_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN10_UI)))
			this.column10 = widthMap.get(COLUMN10_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN11_UI)))
			this.column11 = widthMap.get(COLUMN11_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN12_UI)))
			this.column12 = widthMap.get(COLUMN12_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN13_UI)))
			this.column13 = widthMap.get(COLUMN13_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN14_UI)))
			this.column14 = widthMap.get(COLUMN14_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN15_UI)))
			this.column15 = widthMap.get(COLUMN15_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN16_UI)))
			this.column16 = widthMap.get(COLUMN16_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN17_UI)))
			this.column17 = widthMap.get(COLUMN17_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN18_UI)))
			this.column18 = widthMap.get(COLUMN18_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN19_UI)))
			this.column19 = widthMap.get(COLUMN19_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(COLUMN20_UI)))
			this.column20 = widthMap.get(COLUMN20_UI).intValue();
		
		if (!NumberUtil.isNullOrZero(widthMap.get(SKU_COMMENT_UI)))
			this.skuComment = widthMap.get(SKU_COMMENT_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(PURCHASE_PRICE_UI)))
			this.purchasePrice = widthMap.get(PURCHASE_PRICE_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(SELLING_PRICE_UI)))
			this.sellingPrice = widthMap.get(SELLING_PRICE_UI).intValue();
		if (!NumberUtil.isNullOrZero(widthMap.get(SELLING_UOM_UI)))
			this.sellingUom = widthMap.get(SELLING_UOM_UI).intValue();
	}
	
	public Integer getUserId() {
		return userId;
	}
	public Integer getCompanyName() {
		return companyName == null ? 100 : this.companyName;
	}
	public void setCompanyName(Integer companyName) {
		this.companyName = companyName;
	}
	public Integer getSellerName() {
		return sellerName == null ? 100 : this.sellerName;
	}
	public void setSellerName(Integer sellerName) {
		this.sellerName = sellerName;
	}
	public Integer getGroupName() {
		return groupName == null ? 100 : this.groupName;
	}
	public void setGroupName(Integer groupName) {
		this.groupName = groupName;
	}
	public Integer getMarketCondition() {
		return marketCondition == null ? 100 : this.marketCondition;
	}
	public void setMarketCondition(Integer marketCondition) {
		this.marketCondition = marketCondition;
	}
	public Integer getSkuName() {
		return skuName == null ? 100 : this.skuName;
	}
	public void setSkuName(Integer skuName) {
		this.skuName = skuName;
	}
	public Integer getAreaProduction() {
		return areaProduction == null ? 100 : this.areaProduction;
	}
	public void setAreaProduction(Integer areaProduction) {
		this.areaProduction = areaProduction;
	}
	public Integer getClazz1() {
		return clazz1 == null ? 100 : this.clazz1;
	}
	public void setClazz1(Integer clazz1) {
		this.clazz1 = clazz1;
	}
	public Integer getClazz2() {
		return clazz2 == null ? 100 : this.clazz2;
	}
	public void setClazz2(Integer clazz2) {
		this.clazz2 = clazz2;
	}
	public Integer getPrice1() {
		return price1 == null ? 100 : this.price1;
	}
	public void setPrice1(Integer price1) {
		this.price1 = price1;
	}
	public Integer getPrice2() {
		return price2 == null ? 100 : this.price2;
	}
	public void setPrice2(Integer price2) {
		this.price2 = price2;
	}
	public Integer getPriceWOTax() {
		return priceWOTax == null ? 100 : this.priceWOTax;
	}
	public void setPriceWOTax(Integer priceWOTax) {
		this.priceWOTax = priceWOTax;
	}
	public Integer getPriceWTax() {
		return priceWTax == null ? 100 : this.priceWTax;
	}
	public void setPriceWTax(Integer priceWTax) {
		this.priceWTax = priceWTax;
	}
	public Integer getPurchasePrice() {
		return purchasePrice == null ? 60 : this.purchasePrice;
	}
	public void setPurchasePrice(Integer purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public Integer getSellingPrice() {
		return sellingPrice == null ? 60 : this.sellingPrice;
	}
	public void setSellingPrice(Integer sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public Integer getProfitAmount() {
		return profitAmount == null ? 60 : this.profitAmount;
	}
	public void setProfitAmount(Integer profitAmount) {
		this.profitAmount = profitAmount;
	}
	public Integer getSellingUom() {
		return sellingUom == null ? 60 : this.sellingUom;
	}
	public void setSellingUom(Integer sellingUom) {
		this.sellingUom = sellingUom;
	}
	public Integer getProfitPercentage() {
		return profitPercentage == null ? 60 : this.profitPercentage;
	}

	public void setProfitPercentage(Integer profitPercentage) {
		this.profitPercentage = profitPercentage;
	}

	public Integer getPackageQty() {
		return packageQty == null ? 60 : this.packageQty;
	}
	public void setPackageQty(Integer packageQty) {
		this.packageQty = packageQty;
	}
	public Integer getPackageType() {
		return packageType == null ? 60 : this.packageType;
	}
	public void setPackageType(Integer packageType) {
		this.packageType = packageType;
	}
	public Integer getUom() {
		return uom == null ? 80 : this.uom;
	}
	public void setUom(Integer uom) {
		this.uom = uom;
	}
	public Integer getSkuComment() {
		return skuComment == null ? 80 : this.skuComment;
	}
	public void setSkuComment(Integer skuComment) {
		this.skuComment = skuComment;
	}
	public Integer getRowQty() {
		return rowQty == null ? 60 : this.rowQty;
	}
	public void setRowQty(Integer rowQty) {
		this.rowQty = rowQty;
	}
	public Integer getSkuMaxLimit() {
		return skuMaxLimit == null ? 60 : this.skuMaxLimit;
	}
	public void setSkuMaxLimit(Integer skuMaxLimit) {
		this.skuMaxLimit = skuMaxLimit;
	}
	public Integer getColumn01() {
		return column01 == null ? 60 : this.column01;
	}
	public void setColumn01(Integer column01) {
		this.column01 = column01;
	}
	public Integer getColumn02() {
		return column02 == null ? 60 : this.column02;
	}
	public void setColumn02(Integer column02) {
		this.column02 = column02;
	}
	public Integer getColumn03() {
		return column03 == null ? 60 : this.column03;
	}
	public void setColumn03(Integer column03) {
		this.column03 = column03;
	}
	public Integer getColumn04() {
		return column04 == null ? 60 : this.column04;
	}
	public void setColumn04(Integer column04) {
		this.column04 = column04;
	}
	public Integer getColumn05() {
		return column05 == null ? 60 : this.column05;
	}
	public void setColumn05(Integer column05) {
		this.column05 = column05;
	}
	public Integer getColumn06() {
		return column06 == null ? 60 : this.column06;
	}
	public void setColumn06(Integer column06) {
		this.column06 = column06;
	}
	public Integer getColumn07() {
		return column07 == null ? 60 : this.column07;
	}
	public void setColumn07(Integer column07) {
		this.column07 = column07;
	}
	public Integer getColumn08() {
		return column08 == null ? 60 : this.column08;
	}
	public void setColumn08(Integer column08) {
		this.column08 = column08;
	}
	public Integer getColumn09() {
		return column09 == null ? 60 : this.column09;
	}
	public void setColumn09(Integer column09) {
		this.column09 = column09;
	}
	public Integer getColumn10() {
		return column10 == null ? 60 : this.column10;
	}
	public void setColumn10(Integer column10) {
		this.column10 = column10;
	}
	public Integer getColumn11() {
		return column11 == null ? 60 : this.column11;
	}
	public void setColumn11(Integer column11) {
		this.column11 = column11;
	}
	public Integer getColumn12() {
		return column12 == null ? 60 : this.column12;
	}
	public void setColumn12(Integer column12) {
		this.column12 = column12;
	}
	public Integer getColumn13() {
		return column13 == null ? 60 : this.column13;
	}
	public void setColumn13(Integer column13) {
		this.column13 = column13;
	}
	public Integer getColumn14() {
		return column14 == null ? 60 : this.column14;
	}
	public void setColumn14(Integer column14) {
		this.column14 = column14;
	}
	public Integer getColumn15() {
		return column15 == null ? 60 : this.column15;
	}
	public void setColumn15(Integer column15) {
		this.column15 = column15;
	}
	public Integer getColumn16() {
		return column16 == null ? 60 : this.column16;
	}
	public void setColumn16(Integer column16) {
		this.column16 = column16;
	}
	public Integer getColumn17() {
		return column17 == null ? 60 : this.column17;
	}
	public void setColumn17(Integer column17) {
		this.column17 = column17;
	}
	public Integer getColumn18() {
		return column18 == null ? 60 : this.column18;
	}
	public void setColumn18(Integer column18) {
		this.column18 = column18;
	}
	public Integer getColumn19() {
		return column19 == null ? 60 : this.column19;
	}
	public void setColumn19(Integer column19) {
		this.column19 = column19;
	}
	public Integer getColumn20() {
		return column20 == null ? 60 : this.column20;
	}
	public void setColumn20(Integer column20) {
		this.column20 = column20;
	}	
}
