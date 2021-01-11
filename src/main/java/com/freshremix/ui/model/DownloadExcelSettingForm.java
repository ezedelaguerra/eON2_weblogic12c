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
 * Apr 29, 2010		nvelasquez		
 */
package com.freshremix.ui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author nvelasquez
 *
 */
public class DownloadExcelSettingForm {
	
	private List<String> dateFlag;
	private	String dateOpt;
	private String sellerOpt;
	private String buyerOpt;
	private String hasQty;
	private String priceComputeOpt;
	private String sheetTypeId;
	
	public DownloadExcelSettingForm() {
		this.dateFlag = new ArrayList<String>();
		for (int i=0; i<7; i++)
			this.dateFlag.add("0");
		this.dateOpt = "1";
		this.sellerOpt = "1";
		this.buyerOpt = "1";
		this.hasQty = "0";
		this.priceComputeOpt = "1";
	}
	
	public DownloadExcelSettingForm(String startDate, String endDate,
			String deliveryDate, boolean isAllDatesView) {
		this.dateFlag = new ArrayList<String>();
		/*if (isAllDatesView) {
			int ctr = DateFormatter.getDateDiff(startDate, endDate) + 1;
			for (int i=0; i<7; i++)
				if (i < ctr) this.dateFlag.add("1");
				else this.dateFlag.add("0");
		}
		else {
			int index = DateFormatter.getDateDiff(startDate, deliveryDate);
			for (int i=0; i<7; i++)
				if (index == i) this.dateFlag.add("1");
				else this.dateFlag.add("0");
		}*/
		
		for (int i=0; i<7; i++) {
			if (i == 0)
				this.dateFlag.add("1");
			else
				this.dateFlag.add("0");
		}
		
		this.dateOpt = "1";
		this.sellerOpt = "1";
		this.buyerOpt = "1";
		this.hasQty = "0";
		this.priceComputeOpt = "1";
	}
	
	public List<String> getDateFlag() {
		return dateFlag;
	}
	public void setDateFlag(List<String> dateFlag) {
		this.dateFlag = dateFlag;
	}
	public String getDateOpt() {
		return dateOpt;
	}
	public void setDateOpt(String dateOpt) {
		this.dateOpt = dateOpt;
	}
	public String getSellerOpt() {
		return sellerOpt;
	}
	public void setSellerOpt(String sellerOpt) {
		this.sellerOpt = sellerOpt;
	}
	public String getBuyerOpt() {
		return buyerOpt;
	}
	public void setBuyerOpt(String buyerOpt) {
		this.buyerOpt = buyerOpt;
	}
	public String getHasQty() {
		return hasQty;
	}
	public void setHasQty(String hasQty) {
		this.hasQty = hasQty;
	}
	public String getPriceComputeOpt() {
		return priceComputeOpt;
	}
	public void setPriceComputeOpt(String priceComputeOpt) {
		this.priceComputeOpt = priceComputeOpt;
	}
	public String getSheetTypeId() {
		return sheetTypeId;
	}
	public void setSheetTypeId(String sheetTypeId) {
		this.sheetTypeId = sheetTypeId;
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("{'dateFlag':[");
		Iterator<String> it = this.dateFlag.iterator();
		while (it.hasNext()) {
			String flag = it.next();
			buff.append("'");
			buff.append(flag);
			buff.append("'");
			if (it.hasNext())
				buff.append(",");
		}
		buff.append("], 'dateOpt':'");
		buff.append(this.dateOpt);
		buff.append("', 'sellerOpt':'");
		buff.append(this.sellerOpt);
		buff.append("', 'buyerOpt':'");
		buff.append(this.buyerOpt);
		buff.append("', 'hasQty':'");
		buff.append(this.hasQty);
		buff.append("', 'priceComputeOpt':'");
		buff.append(this.priceComputeOpt);
		buff.append("'}");
		
		return buff.toString();
		//'dateFlag':['1','1','1','1','1','1','1'], 'dateOpt':'9', 'sellerOpt':'9', 'buyerOpt':'9', 'hasQty':'1', 'priceComputeOpt':'4'
	}
	

}
