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
 * Feb 18, 2010		pamela		
 */
package com.freshremix.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.freshremix.model.PmfSkuList;
import com.freshremix.util.StringUtil;
import com.freshremix.util.TaxUtil;

/**
 * @author Pammie
 *
 */
public class PmfForm {
	private String action;
	private String recordType;

	private Map<String, String> parameters;

	private List<String> fieldsName;
	private List<Map<String, String>> insertedRecords;
	private List<Map<String, String>> updatedRecords;
	private List<Map<String, String>> deletedRecords;

	public String getAction() {
		return action;
	}

	public List<PmfSkuUI> getDeletedPmfSkus() {
		return toPmfSkus(getDeletedRecords());
	}

	public List<Map<String, String>> getDeletedRecords() {
		return deletedRecords;
	}

	public List<String> getFieldsName() {
		return fieldsName;
	}
	
	public List<PmfSkuUI> getInsertedPmfSkus() {
		return toPmfSkus(getInsertedRecords());
	}
	
	public List<Map<String, String>> getInsertedRecords() {
		return insertedRecords;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public String getRecordType() {
		return recordType;
	}

	public List<PmfSkuUI> getUpdatedPmfSkus() {
		return toPmfSkus(getUpdatedRecords());
	}

	public List<Map<String, String>> getUpdatedRecords() {
		return updatedRecords;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setDeletedRecords(List<Map<String, String>> deletedRecords) {
		this.deletedRecords = deletedRecords;
	}

	public void setFieldsName(List<String> fieldsName) {
		this.fieldsName = fieldsName;
	}

	public void setInsertedRecords(List<Map<String, String>> insertedRecords) {
		this.insertedRecords = insertedRecords;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public void setUpdatedRecords(List<Map<String, String>> updatedRecords) {
		this.updatedRecords = updatedRecords;
	}

	public PmfSkuUI toPmfSkuList(Map<String, String> record) {
		
		PmfSkuUI pmfSku = new PmfSkuUI();
		pmfSku.setSellerName(record.get("sellerName"));
		if (!StringUtil.isNullOrEmpty(record.get("skuGroup"))){
			pmfSku.setSkuGroup(new Integer(record.get("skuGroup")));
		}
		pmfSku.setMarket(record.get("market"));
		pmfSku.setSkuName(record.get("skuName"));
		pmfSku.setSellerProdCode(record.get("sellerProdCode"));
		pmfSku.setBuyerProdCode(record.get("buyerProdCode"));
		pmfSku.setLocation(record.get("location"));
		pmfSku.setGrade(record.get("grade"));
		pmfSku.setPmfClass(record.get("pmfClass"));
		if (!StringUtil.isNullOrEmpty(record.get("price1"))){
			pmfSku.setPrice1(new BigDecimal(record.get("price1")));
		}
		if (!StringUtil.isNullOrEmpty(record.get("price2"))){
			pmfSku.setPrice2(new BigDecimal(record.get("price2")));
		}
		if (!StringUtil.isNullOrEmpty(record.get("priceNoTax"))){
			pmfSku.setPriceNoTax(new BigDecimal(record.get("priceNoTax")));
			pmfSku.setPricewTax(TaxUtil.getPriceWithTax(new BigDecimal(Double.parseDouble(record.get("priceNoTax"))), TaxUtil.ROUND));
		}
		if (!StringUtil.isNullOrEmpty(record.get("pkgQuantity"))){
			pmfSku.setPkgQuantity(new BigDecimal(record.get("pkgQuantity")));
		}
		pmfSku.setPkgType(record.get("pkgType"));
		if (!StringUtil.isNullOrEmpty(record.get("unitOrder"))){
			pmfSku.setUnitOrder(new Integer(record.get("unitOrder")));
		}
		pmfSku.setCommentA(record.get("commentA"));
		pmfSku.setCommentB(record.get("commentB"));
		pmfSku.setPmfId(Integer.parseInt(record.get("pmfId")));
		if (!StringUtil.isNullOrEmpty(record.get("skuId"))){
			pmfSku.setSkuId(Integer.parseInt(record.get("skuId")));
		}
		
		return pmfSku;
	}

	public List<PmfSkuUI> toPmfSkus(List<Map<String, String>> records) {
		List<PmfSkuUI> pmfSku = new ArrayList<PmfSkuUI>();

		for (Map<String, String> record : records) {
			pmfSku.add(toPmfSkuList(record));
		}

		return pmfSku;
	}

	@Override
	public String toString() {
		return "PmfForm [action=" + action + ", deletedRecords="
			+ deletedRecords + ", fieldsName=" + fieldsName
			+ ", insertedRecords=" + insertedRecords + ", recordType="
			+ recordType + ", updatedRecords=" + updatedRecords + "]";
	}
}
