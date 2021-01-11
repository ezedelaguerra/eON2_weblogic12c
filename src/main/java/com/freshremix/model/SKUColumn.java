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
 * May 31, 2010		nvelasquez		
 */
package com.freshremix.model;

/**
 * @author nvelasquez
 *
 */
public class SKUColumn {
	
	private Integer skuColumnId;
	private String headerKey;
	private String headerSheet;
	private String headerCsv;
	
	public Integer getSkuColumnId() {
		return skuColumnId;
	}
	public void setSkuColumnId(Integer skuColumnId) {
		this.skuColumnId = skuColumnId;
	}
	public String getHeaderKey() {
		return headerKey;
	}
	public void setHeaderKey(String headerKey) {
		this.headerKey = headerKey;
	}
	public String getHeaderSheet() {
		return headerSheet;
	}
	public void setHeaderSheet(String headerSheet) {
		this.headerSheet = headerSheet;
	}
	public String getHeaderCsv() {
		return headerCsv;
	}
	public void setHeaderCsv(String headerCsv) {
		this.headerCsv = headerCsv;
	}
}
