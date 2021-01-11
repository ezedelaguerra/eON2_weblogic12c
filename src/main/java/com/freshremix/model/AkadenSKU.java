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
 * Mar 31, 2010		Jovino Balunan		
 */
package com.freshremix.model;

import java.math.BigDecimal;

/**
 * @author jabalunan
 *
 */
public class AkadenSKU extends SKUBA {
	
	private String isNewSKU;
	private String typeFlag;
	private Integer akadenSkuId;
	private Integer sheetTypeId;
	private BigDecimal priceWithTax;
	private BigDecimal quantity;
		
	public String getIsNewSKU() {
		return isNewSKU;
	}

	public void setIsNewSKU(String isNewSKU) {
		this.isNewSKU = isNewSKU;
	}

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

	public Integer getAkadenSkuId() {
		return akadenSkuId;
	}

	public void setAkadenSkuId(Integer akadenSkuId) {
		this.akadenSkuId = akadenSkuId;
	}

	public BigDecimal getPriceWithTax() {
		return priceWithTax;
	}

	public void setPriceWithTax(BigDecimal priceWithTax) {
		this.priceWithTax = priceWithTax;
	}

	public Integer getSheetTypeId() {
		return sheetTypeId;
	}

	public void setSheetTypeId(Integer sheetTypeId) {
		this.sheetTypeId = sheetTypeId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
}
