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
 * Oct 6, 2011		raquino		
 */
package com.freshremix.model;

import java.math.BigDecimal;

/**
 * @author raquino
 *
 */
public interface SKUTemplate {

	Integer getSkuId();
	String getSkuName();
	Integer getOrigSkuId();
	Integer getSheetType();
	Integer getSkuCategoryId();
	SKUGroup getSkuGroup();
	User getUser();
	String getLocation();
	String getMarket();
	String getGrade();
	String getClazz();
	BigDecimal getPrice1();
	BigDecimal getPrice2();
	BigDecimal getPriceWithoutTax();
	BigDecimal getPriceWithTax();
	BigDecimal getPackageQuantity();
	String getPackageType();
	User getProposedBy();
	OrderUnit getOrderUnit();
	String getPmfFlag();
	BigDecimal getSkuMaxLimit();
	String getComments();
	String getExternalSkuId();
	Integer getSkuBAId();
	BigDecimal getPurchasePrice();
	BigDecimal getSellingPrice();
	OrderUnit getSellingUom();
	String getSkuComment();
	String getIsNewSKU();
	String getYypeFlag();
	Integer getAkadenSkuId();
	Integer getSheetTypeId();
	BigDecimal getQuantity();
	
	// 20 new columns
	Integer getColumn01();
	String getColumn02();
	String getColumn03();
	String getColumn04();
	String getColumn05();
	String getColumn06();
	String getColumn07();
	String getColumn08();
	String getColumn09();
	String getColumn10();
	String getColumn11();
	String getColumn12();
	String getColumn13();
	String getColumn14();
	String getColumn15();
	String getColumn16();
	String getColumn17();
	String getColumn18();
	String getColumn19();
	String getColumn20();
	
}
