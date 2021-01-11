package com.freshremix.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freshremix.util.NumberUtil;
import com.freshremix.util.StringUtil;

public class OrderForm {
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

	public List<OrderItemUI> getDeletedOrders() {
		return toOrders(getDeletedRecords());
	}

	public List<Map<String, Object>> getDeletedRecords() {
		return deletedRecords;
	}

	public List<String> getFieldsName() {
		return fieldsName;
	}
	
	public List<OrderItemUI> getInsertedOrders() {
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

	public List<OrderItemUI> getUpdatedOrders() {
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

	public OrderItemUI toOrderItem(Map<String, Object> record) {
		
		OrderItemUI oi = new OrderItemUI();
		oi.setSellername(StringUtil.nullToBlank((String)record.get("sellername")));
		oi.setCompanyname(StringUtil.nullToBlank((String)record.get("companyname")));
		oi.setSkuGroupId(NumberUtil.nullToZero((String)record.get("groupname")));
		
//		System.out.println(record);
//		System.out.println(record.get("marketname"));
		
		oi.setMarketname(StringUtil.nullToBlank((String)record.get("marketname")));
		
		if (!StringUtil.isNullOrEmpty(record.get("column01")))
			oi.setColumn01(new Integer(record.get("column01").toString()));
		
		oi.setColumn02(StringUtil.nullToBlank((String)record.get("column02")));
		oi.setColumn03(StringUtil.nullToBlank((String)record.get("column03")));
		oi.setColumn04(StringUtil.nullToBlank((String)record.get("column04")));
		oi.setColumn05(StringUtil.nullToBlank((String)record.get("column05")));
		oi.setSkuname(StringUtil.nullToBlank((String)record.get("skuname")));
		oi.setHome(StringUtil.nullToBlank((String)record.get("home")));
		oi.setGrade(StringUtil.nullToBlank((String)record.get("grade")));
		oi.setClazzname(StringUtil.nullToBlank((String)record.get("clazzname")));
		oi.setPackagetype(StringUtil.nullToBlank((String)record.get("packagetype")));
		//comments added by jovsab
		oi.setComments(StringUtil.nullToBlank((String)record.get("comments")));
		oi.setSkuComments(StringUtil.nullToBlank((String)record.get("B_skuComment")));
		
		if (!StringUtil.isNullOrEmpty(record.get("billingAkadenSortId")))
			oi.setBillingAkadenSortId(record.get("billingAkadenSortId").toString());
		
		if (!StringUtil.isNullOrEmpty(record.get("skuId")))
			oi.setSkuId(new Integer(record.get("skuId").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("skuBaId")))
			oi.setSkuBaId(new Integer(record.get("skuBaId").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("sellerId")))
			oi.setSellerId(new Integer(record.get("sellerId").toString()));
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
		
		oi.setColumn06(StringUtil.nullToBlank((String)record.get("column06")));
		oi.setColumn07(StringUtil.nullToBlank((String)record.get("column07")));
		oi.setColumn08(StringUtil.nullToBlank((String)record.get("column08")));
		oi.setColumn09(StringUtil.nullToBlank((String)record.get("column09")));
		oi.setColumn10(StringUtil.nullToBlank((String)record.get("column10")));
		oi.setColumn11(StringUtil.nullToBlank((String)record.get("column11")));
		oi.setColumn12(StringUtil.nullToBlank((String)record.get("column12")));
		oi.setColumn13(StringUtil.nullToBlank((String)record.get("column13")));
		oi.setColumn14(StringUtil.nullToBlank((String)record.get("column14")));
		oi.setColumn15(StringUtil.nullToBlank((String)record.get("column15")));
		oi.setColumn16(StringUtil.nullToBlank((String)record.get("column16")));
		oi.setColumn17(StringUtil.nullToBlank((String)record.get("column17")));
		oi.setColumn18(StringUtil.nullToBlank((String)record.get("column18")));
		oi.setColumn19(StringUtil.nullToBlank((String)record.get("column19")));
		oi.setColumn20(StringUtil.nullToBlank((String)record.get("column20")));
		
		if (!StringUtil.isNullOrEmpty(record.get("skumaxlimit")))
			oi.setSkumaxlimit(new BigDecimal(record.get("skumaxlimit").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("unitorder")))
			oi.setUnitorder(new Integer((String)record.get("unitorder")));
		if (!StringUtil.isNullOrEmpty(record.get("B_purchasePrice")))
			oi.setPurchasePrice(new BigDecimal(record.get("B_purchasePrice").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("B_sellingPrice")))
			oi.setSellingPrice(new BigDecimal(record.get("B_sellingPrice").toString()));
		if (!StringUtil.isNullOrEmpty(record.get("B_sellingUom")))
			oi.setSellingUom(new Integer((String)record.get("B_sellingUom")));
		if (!StringUtil.isNullOrEmpty(record.get("externalSkuId")))
			oi.setExternalSkuId((String)record.get("externalSkuId"));
		
		Map<String, BigDecimal> qtyMap = new HashMap<String, BigDecimal>();
		Map<String, String> visMap = new HashMap<String, String>();
		Map<String, String> apprvMap = new HashMap<String, String>();
		Map<String, String> lockMap = new HashMap<String, String>(); // for receive sheet
		for (int i=0; i<this.columnIds.size(); i++) {
			String columnId = this.columnIds.get(i);
			BigDecimal value = null;
			String visFlag = "1";
			String apprvFlag = "0";
			String lockFlag = "0";
			if (record.get(columnId) != null && !StringUtil.isNullOrEmpty(record.get(columnId).toString())) {
				if (columnId.charAt(0) == 'L') {//lock value
					lockFlag = record.get(columnId).toString();
				}
				if (columnId.charAt(0) == 'V') {//visibility value
					visFlag = record.get(columnId).toString();
				}
				if (columnId.charAt(0) == 'A') {//approve value
					apprvFlag = record.get(columnId).toString();
				}
				else
					value = new BigDecimal(record.get(columnId).toString());
			}
//			System.out.println("columnId:[" + columnId + "] value:[" + value + "] visFlag:[" 
//					+ visFlag + "] apprvFlag:[" + apprvFlag + "]");
			qtyMap.put(columnId, value);
			visMap.put(columnId, visFlag);
			apprvMap.put(columnId, apprvFlag);
			lockMap.put(columnId, lockFlag);
		}
		oi.setQtyMap(qtyMap);
		oi.setVisMap(visMap);
		oi.setApprvMap(apprvMap);
		oi.setLockMap(lockMap);
		
		return oi;
	}

	public List<OrderItemUI> toOrders(List<Map<String, Object>> records) {
		List<OrderItemUI> orders = new ArrayList<OrderItemUI>();

		if(records != null){
			for (Map<String, Object> record : records) {
				orders.add(toOrderItem(record));
			}
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
	
	public void setQuantityApprovalIds(List<Integer> buyerIds) {
		this.columnIds = new ArrayList<String>();
		for (Integer buyerId : buyerIds) {
			this.columnIds.add("Q_" + buyerId.toString());
			this.columnIds.add("A_" + buyerId.toString());
		}
	}
	
	public void setLockQuantity(List<Integer> buyerIdList) {
		if (this.columnIds == null)
			this.columnIds = new ArrayList<String>();
		
		for (Integer buyerId : buyerIdList) {
			this.columnIds.add("L_" + buyerId.toString());
		}	
	}
	
	public void setLockDates(List<String> deliveryDates) {
		if (this.columnIds == null)
			this.columnIds = new ArrayList<String>();
		
		for (String deliveryDate : deliveryDates) {
			this.columnIds.add("L_" + deliveryDate);
		}	
	}

	@Override
	public String toString() {
		return "OrderForm [action=" + action + ", deletedRecords="
			+ deletedRecords + ", fieldsName=" + fieldsName
			+ ", insertedRecords=" + insertedRecords + ", recordType="
			+ recordType + ", updatedRecords=" + updatedRecords + "]";
	}
}
