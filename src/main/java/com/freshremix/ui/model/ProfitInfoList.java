package com.freshremix.ui.model;

import java.util.List;

import com.freshremix.util.OrderSheetUtil;

public class ProfitInfoList {

	private List<ProfitInfo> list;
	private ProfitInfo totals;
	private String taxOption;
	
	public ProfitInfoList(List<ProfitInfo> list, String taxOption) {
		this.list = list;
		this.taxOption = taxOption;
	}
	
	public List<ProfitInfo> getList() {
		return list;
	}
	public void setList(List<ProfitInfo> list) {
		this.list = list;
	}
	public ProfitInfo getTotals() {
		return totals;
	}
	public void setTotals(ProfitInfo totals) {
		this.totals = totals;
	}
	
	public ProfitInfo computeForTotals (boolean withPackageQuantity) {
		
		ProfitInfo totals = new ProfitInfo();
		for (ProfitInfo tmp : this.list) {
			totals.add(
				OrderSheetUtil.computeProfitInfo(
					tmp.getPriceWithoutTax(), 
					tmp.getPriceWithTax(),
					tmp.getSellingPrice(),
					tmp.getPackageQuantity(),
					tmp.getTotalQuantity(),
					taxOption,
					withPackageQuantity));
		}
		this.totals = totals;
		return totals;
	}
}
