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
 * Mar 25, 2011		raquino		
 */
package com.freshremix.model;

import java.math.BigDecimal;

import com.freshremix.util.TaxUtil;

/**
 * @author raquino
 *
 */
public class GrandTotalPrices {
	
	private BigDecimal priceWithoutTax;
	private BigDecimal priceWithTax;
	
	public BigDecimal getPriceWithoutTax() {
		return priceWithoutTax;
	}
	public void setPriceWithoutTax(BigDecimal priceWithoutTax) {
		this.priceWithoutTax = priceWithoutTax;
	}
	public BigDecimal getPriceWithTax() {
		
		return TaxUtil.getPriceWithTax(priceWithTax, TaxUtil.ROUND);
	}
	public void setPriceWithTax(BigDecimal priceWithTax) {
		this.priceWithTax = priceWithTax;
	}

}
