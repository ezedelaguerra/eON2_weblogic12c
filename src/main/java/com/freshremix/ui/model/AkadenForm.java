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
 * Mar 25, 2010		Jovino Balunan		
 */
package com.freshremix.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freshremix.util.StringUtil;

/**
 * @author jabalunan
 *
 */
public class AkadenForm {
	private String action;
	private String recordType;

	private Map<String, Object> parameters;

	private List<String> fieldsName;
	private List<Map<String, Object>> insertedRecords;
	private List<Map<String, Object>> updatedRecords;
	private List<Map<String, Object>> deletedRecords;
	
	private List<String> columnIds;

	public String getAction() {
		return action;
	}

	public List<AkadenItemUI> getDeletedOrders() {
		return toOrders(getDeletedRecords());
	}

	public List<Map<String, Object>> getDeletedRecords() {
		return deletedRecords;
	}

	public List<String> getFieldsName() {
		return fieldsName;
	}
	
	public List<AkadenItemUI> getInsertedOrders() {
		return toOrders(getInsertedRecords());
	}
	
	public List<Map<String, Object>> getInsertedRecords() {
		return insertedRecords;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public String getRecordType() {
		return recordType;
	}

	public List<AkadenItemUI> getUpdatedOrders() {
		return toOrders(getUpdatedRecords());
	}

	public List<Map<String, Object>> getUpdatedRecords() {
		return updatedRecords;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setDeletedRecords(List<Map<String, Object>> deletedRecords) {
		this.deletedRecords = deletedRecords;
	}

	public void setFieldsName(List<String> fieldsName) {
		this.fieldsName = fieldsName;
	}

	public void setInsertedRecords(List<Map<String, Object>> insertedRecords) {
		this.insertedRecords = insertedRecords;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public void setUpdatedRecords(List<Map<String, Object>> updatedRecords) {
		this.updatedRecords = updatedRecords;
	}

	public AkadenItemUI toOrderItem(Map<String, Object> record) {
		
		AkadenItemUI oi = new AkadenItemUI();
		if (!StringUtil.isNullOrEmpty(record.get("sellerId")))
			oi.setSellerId(new Integer(record.get("sellerId").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("sellername")))
			oi.setSellername(record.get("sellername").toString());
		if (!StringUtil.isNullOrEmpty(record.get("groupname")))
			oi.setSkuGroupId(new Integer(record.get("groupname").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("marketname")))
			oi.setMarketname(record.get("marketname").toString());
		if (!StringUtil.isNullOrEmpty(record.get("skuname")))
			oi.setSkuname(record.get("skuname").toString());
		if (!StringUtil.isNullOrEmpty(record.get("home")))
			oi.setHome(record.get("home").toString());
		if (!StringUtil.isNullOrEmpty(record.get("grade")))
			oi.setGrade(record.get("grade").toString());
		if (!StringUtil.isNullOrEmpty(record.get("clazzname")))
			oi.setClazzname(record.get("clazzname").toString());
		if (!StringUtil.isNullOrEmpty(record.get("packagetype")))
			oi.setPackagetype(record.get("packagetype").toString());
		if (!StringUtil.isNullOrEmpty(record.get("unitorder")))
			oi.setUnitorder(new Integer(record.get("unitorder").toString()));		
		if (!StringUtil.isNullOrEmpty(record.get("isNewSKU")))
			oi.setIsNewSKU(new Integer(record.get("isNewSKU").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("comments")))
			oi.setComments(record.get("comments").toString());		
		if (!StringUtil.isNullOrEmpty(record.get("skuId")))
			oi.setSkuId(new Integer(record.get("skuId").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("akadenSkuId")))
			oi.setAkadenSkuId(new Integer(record.get("akadenSkuId").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("typeflag")))
			oi.setTypeFlag(record.get("typeflag").toString());
		if (!StringUtil.isNullOrEmpty(record.get("price1")))
			oi.setPrice1(new BigDecimal(record.get("price1").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("price2")))
			oi.setPrice2(new BigDecimal(record.get("price2").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("pricewotax")))
			oi.setPricewotax(new BigDecimal(record.get("pricewotax").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("pricewtax")))
			oi.setPricewtax(new BigDecimal(record.get("pricewtax").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("packageqty")))
			oi.setPackageqty(new BigDecimal(record.get("packageqty").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("skumaxlimit")))
			oi.setSkumaxlimit(new BigDecimal(record.get("skumaxlimit").toString()));
		
		Map<String, BigDecimal> qtyMap = new HashMap<String, BigDecimal>();
		Map<String, String> visMap = new HashMap<String, String>();
		Map<String, String> apprvMap = new HashMap<String, String>();
		for (int i=0; i<this.columnIds.size(); i++) {
			String columnId = this.columnIds.get(i);
			BigDecimal value = null;
			String visFlag = "1";
			String apprvFlag = "0";
			if (record.get(columnId) != null && !StringUtil.isNullOrEmpty(record.get(columnId).toString())) {
				if (columnId.charAt(0) == 'V') {//visibility value
					visFlag = record.get(columnId).toString();
				}
				if (columnId.charAt(0) == 'A') {//approve value
					apprvFlag = record.get(columnId).toString();
				}
				else
					value = new BigDecimal(record.get(columnId).toString());
			}
			System.out.println("columnId:[" + columnId + "] value:[" + value + "] visFlag:[" 
					+ visFlag + "] apprvFlag:[" + apprvFlag + "]");
			qtyMap.put(columnId, value);
			visMap.put(columnId, visFlag);
			apprvMap.put(columnId, apprvFlag);
		}
		oi.setQtyMap(qtyMap);
		oi.setVisMap(visMap);
		oi.setApprvMap(apprvMap);
		
		return oi;
	}

	public List<AkadenItemUI> toOrders(List<Map<String, Object>> records) {
		List<AkadenItemUI> orders = new ArrayList<AkadenItemUI>();

		for (Map<String, Object> record : records) {
			orders.add(toOrderItem(record));
		}

		return orders;
	}
	
	public void setBuyerColumnIds(List<Integer> buyerIds) {
		this.columnIds = new ArrayList<String>();
		for (Integer buyerId : buyerIds) {
			this.columnIds.add("Q_" + buyerId.toString());
			this.columnIds.add("V_" + buyerId.toString());
		}
	}
	
	public void setDateColumnIds(List<String> deliveryDates) {
		this.columnIds = new ArrayList<String>();
		for (String deliveryDate : deliveryDates) {
			this.columnIds.add("Q_" + deliveryDate);			
		}
	}

	@Override
	public String toString() {
		return "AkadenForm [action=" + action + ", deletedRecords="
			+ deletedRecords + ", fieldsName=" + fieldsName
			+ ", insertedRecords=" + insertedRecords + ", recordType="
			+ recordType + ", updatedRecords=" + updatedRecords + "]";
	}
}
