package com.freshremix.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freshremix.model.SKUColumn;

public class EONHeader {

	// key: HEADER_KEY, value: HEADER_SHEET
	private Map<String,String> sheetMap;
	
	// key: SKU_COLUMN_ID, value: HEADER_SHEET
	private Map<String,String> csvMap;
	
	// key: HEADER_KEY, value: SKUColumn
	private Map<String,SKUColumn> skuColumnMap;
	
	private EONHeader () { }
	
	public EONHeader (List<SKUColumn> skuColList) {
		this();
		this.sheetMap = new HashMap<String,String>();
		this.csvMap = new HashMap<String,String>();
		this.skuColumnMap = new HashMap<String,SKUColumn>();
		
		for (SKUColumn col : skuColList) {
			sheetMap.put(col.getHeaderKey(), col.getHeaderSheet());
			csvMap.put(col.getHeaderKey(), col.getHeaderCsv());
			skuColumnMap.put(col.getHeaderKey(), col);
		}
	}
	
	public String getCSVName (String key) {
		// return the key if not found in the collection
		return csvMap.get(key) != null ? csvMap.get(key) : key;
	}
	
	public String getSheetName (String key) {
		// return the key if not found in the collection
		return sheetMap.get(key) != null ? sheetMap.get(key) : key;
	}

	public Map<String, String> getSheetMap() {
		return sheetMap;
	}

	public void setSheetMap(Map<String, String> sheetMap) {
		this.sheetMap = sheetMap;
	}

	public Map<String, String> getCsvMap() {
		return csvMap;
	}

	public void setCsvMap(Map<String, String> csvMap) {
		this.csvMap = csvMap;
	}

	public Map<String, SKUColumn> getSkuColumnMap() {
		return skuColumnMap;
	}

	public void setSkuColumnMap(Map<String, SKUColumn> skuColumnMap) {
		this.skuColumnMap = skuColumnMap;
	}
}
