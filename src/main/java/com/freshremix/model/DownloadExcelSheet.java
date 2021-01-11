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
package com.freshremix.model;

import java.util.List;
import java.util.Map;

/**
 * @author nvelasquez
 *
 */
public class DownloadExcelSheet {
	
	/* labels */
	private String eONSheetName;
	private String dateMDLabel;
	private String categoryName;
	private String excelSheetName;
	private String userName;
	private String dateOption;
	private String sellerOption;
	private String buyerOption;
	//
	
	/* data */
	private List<String> skuHeaderKeys;
	private List<String> qtyHeaderKeys;
	private List<String> ttlHeaderKeys;
	private Map<String, String> skuHeaderMap;
	private Map<String, String> qtyHeaderMap;
	private Map<String, String> ttlHeaderMap;
	
	private List<Map<String, Object>> skuDataMaps;
	//

	public String geteONSheetName() {
		return eONSheetName;
	}
	public void seteONSheetName(String eONSheetName) {
		this.eONSheetName = eONSheetName;
	}
	public String getDateMDLabel() {
		return dateMDLabel;
	}
	public void setDateMDLabel(String dateMDLabel) {
		this.dateMDLabel = dateMDLabel;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getExcelSheetName() {
		return excelSheetName;
	}
	public void setExcelSheetName(String excelSheetName) {
		this.excelSheetName = excelSheetName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDateOption() {
		return dateOption;
	}
	public void setDateOption(String dateOption) {
		this.dateOption = dateOption;
	}
	public String getSellerOption() {
		return sellerOption;
	}
	public void setSellerOption(String sellerOption) {
		this.sellerOption = sellerOption;
	}
	public String getBuyerOption() {
		return buyerOption;
	}
	public void setBuyerOption(String buyerOption) {
		this.buyerOption = buyerOption;
	}
	public List<String> getSkuHeaderKeys() {
		return skuHeaderKeys;
	}
	public void setSkuHeaderKeys(List<String> skuHeaderKeys) {
		this.skuHeaderKeys = skuHeaderKeys;
	}
	public List<String> getQtyHeaderKeys() {
		return qtyHeaderKeys;
	}
	public void setQtyHeaderKeys(List<String> qtyHeaderKeys) {
		this.qtyHeaderKeys = qtyHeaderKeys;
	}
	public List<String> getTtlHeaderKeys() {
		return ttlHeaderKeys;
	}
	public void setTtlHeaderKeys(List<String> ttlHeaderKeys) {
		this.ttlHeaderKeys = ttlHeaderKeys;
	}
	public Map<String, String> getSkuHeaderMap() {
		return skuHeaderMap;
	}
	public void setSkuHeaderMap(Map<String, String> skuHeaderMap) {
		this.skuHeaderMap = skuHeaderMap;
	}
	public Map<String, String> getQtyHeaderMap() {
		return qtyHeaderMap;
	}
	public void setQtyHeaderMap(Map<String, String> qtyHeaderMap) {
		this.qtyHeaderMap = qtyHeaderMap;
	}
	public Map<String, String> getTtlHeaderMap() {
		return ttlHeaderMap;
	}
	public void setTtlHeaderMap(Map<String, String> ttlHeaderMap) {
		this.ttlHeaderMap = ttlHeaderMap;
	}
	public List<Map<String, Object>> getSkuDataMaps() {
		return skuDataMaps;
	}
	public void setSkuDataMaps(List<Map<String, Object>> skuDataMaps) {
		this.skuDataMaps = skuDataMaps;
	}
	
}
