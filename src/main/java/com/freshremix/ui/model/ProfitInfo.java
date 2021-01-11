package com.freshremix.ui.model;

import java.math.BigDecimal;

import com.freshremix.util.NumberUtil;


public class ProfitInfo {

	private BigDecimal priceWithoutTax;
	private BigDecimal priceWithTax;
	private BigDecimal sellingPrice;
	private BigDecimal profit;
	private BigDecimal profitPercentage;
	private BigDecimal packageQuantity;
	private BigDecimal totalQuantity;
	
	public ProfitInfo () {
		this.priceWithoutTax = new BigDecimal (0);
		this.priceWithTax = new BigDecimal (0);
		this.sellingPrice = new BigDecimal (0);
		this.profit = new BigDecimal (0);
		this.profitPercentage = new BigDecimal (0);
	}
	
	public BigDecimal getPriceWithoutTax() {
		return priceWithoutTax == null ? new BigDecimal (0) : priceWithoutTax;
	}
	public void setPriceWithoutTax(BigDecimal priceWithoutTax) {
		this.priceWithoutTax = priceWithoutTax;
	}
	public BigDecimal getPriceWithTax() {
		return priceWithTax == null ? new BigDecimal (0) : priceWithTax;
	}
	public void setPriceWithTax(BigDecimal priceWithTax) {
		this.priceWithTax = priceWithTax;
	}
	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public BigDecimal getProfit() {
		return profit;
	}
	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
	public BigDecimal getProfitPercentage() {
		return profitPercentage;
	}
	public void setProfitPercentage(BigDecimal profitPercentage) {
		this.profitPercentage = profitPercentage;
	}
	
	public BigDecimal getPackageQuantity() {
		return packageQuantity;
	}

	public void setPackageQuantity(BigDecimal packageQuantity) {
		this.packageQuantity = packageQuantity;
	}
	
	public BigDecimal getTotalQuantity() {
		return this.totalQuantity;
	}
	
	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	/*
	 * This method is used to get either;
	 * 1. Totals of this object attributes for more than one dates
	 * 2. Grand Total of this object attributes for more than one categories
	 */
	public void add(ProfitInfo pi) {
		this.priceWithoutTax = this.priceWithoutTax.add(pi.getPriceWithoutTax());
		this.priceWithTax = this.priceWithTax.add(pi.getPriceWithTax());
		
		if (pi.getSellingPrice() != null)
			this.sellingPrice = this.sellingPrice.add(pi.getSellingPrice());
		
		if (pi.getProfit() != null)
			this.profit = this.profit.add(pi.getProfit());
		
		this.profitPercentage = this.calculateProfitPercentage();
	}
	
	/*
	 * Calculates Grand Total Profit Percentage
	 * ROUND(ROUND(SUM (PROFIT) / SUM (SELLING_PRICE),4) * 100,1) AS PROFIT_PERCENTAGE
	 */
	public BigDecimal calculateProfitPercentage() {
		
		BigDecimal res = new BigDecimal(0);
		
		if (NumberUtil.isGreaterThan(this.sellingPrice, new BigDecimal(0))) {
			
			if (this.profit == null)
				NumberUtil.nullToZero(this.profit);
			
			res = 
				this.profit.divide(this.sellingPrice, 4, BigDecimal.ROUND_HALF_UP)
				.multiply(new BigDecimal(100))
				.setScale(1,BigDecimal.ROUND_HALF_UP);
			
		}
		
		return res;
	}
	
	public void roundOffTotals(){
		this.setPriceWithoutTax(this.priceWithoutTax.setScale(0,BigDecimal.ROUND_HALF_UP));
		this.setPriceWithTax(this.priceWithTax.setScale(0,BigDecimal.ROUND_HALF_UP));
		this.setSellingPrice(this.sellingPrice.setScale(0,BigDecimal.ROUND_HALF_UP));
		this.setProfit(this.profit.setScale(0,BigDecimal.ROUND_HALF_UP));
	}
}
