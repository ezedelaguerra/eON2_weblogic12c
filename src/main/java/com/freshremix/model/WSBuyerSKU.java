package com.freshremix.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class WSBuyerSKU extends WSSKU{
	
	private BigDecimal B_purchasePrice;
	private BigDecimal B_profitPercentage;
	//private String visall;
	private String skuBaId;
	private String B_sellingUom;
	private String B_skuComment;
	private BigDecimal B_sellingPrice;
	private String unitOrderName;
	private String companyId;
	private String B_sellingUomName;
	
	private String center;
	private String delivery;
	private String sale;
	private String jan;
	private String packFee;
	private String column01;
	private String column02;
	private String column03;
	private String column04;
	private String column05;
	private String column06;
	private String column07;
	private String column08;
	private String column09;
	private String column10;
	private String column11;
	private String column12;
	private String column13;
	private String column14;
	private String column15;
	
	ArrayList<WSBuyerSKUDetails> buyerSkuDetails;

	
	
	public String getB_sellingUomName() {
		return B_sellingUomName;
	}

	public void setB_sellingUomName(String b_sellingUomName) {
		B_sellingUomName = b_sellingUomName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getB_purchasePrice() {
		return B_purchasePrice;
	}

	public void setB_purchasePrice(BigDecimal b_purchasePrice) {
		B_purchasePrice = b_purchasePrice;
	}

	public BigDecimal getB_profitPercentage() {
		return B_profitPercentage;
	}

	public void setB_profitPercentage(BigDecimal b_profitPercentage) {
		B_profitPercentage = b_profitPercentage;
	}

//	public String getVisall() {
//		return visall;
//	}
//
//	public void setVisall(String visall) {
//		this.visall = visall;
//	}

	public String getSkuBaId() {
		return skuBaId;
	}

	public void setSkuBaId(String skuBaId) {
		this.skuBaId = skuBaId;
	}

	public String getB_sellingUom() {
		return B_sellingUom;
	}

	public void setB_sellingUom(String b_sellingUom) {
		B_sellingUom = b_sellingUom;
	}

	public String getB_skuComment() {
		return B_skuComment;
	}

	public void setB_skuComment(String b_skuComment) {
		B_skuComment = b_skuComment;
	}

	public BigDecimal getB_sellingPrice() {
		return B_sellingPrice;
	}

	public void setB_sellingPrice(BigDecimal b_sellingPrice) {
		B_sellingPrice = b_sellingPrice;
	}

	

	public String getUnitOrderName() {
		return unitOrderName;
	}

	public void setUnitOrderName(String unitOrderName) {
		this.unitOrderName = unitOrderName;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getSale() {
		return sale;
	}

	public void setSale(String sale) {
		this.sale = sale;
	}

	public String getJan() {
		return jan;
	}

	public void setJan(String jan) {
		this.jan = jan;
	}

	public String getPackFee() {
		return packFee;
	}

	public void setPackFee(String packFee) {
		this.packFee = packFee;
	}

	public String getColumn01() {
		return column01;
	}

	public void setColumn01(String column01) {
		this.column01 = column01;
	}

	public String getColumn02() {
		return column02;
	}

	public void setColumn02(String column02) {
		this.column02 = column02;
	}

	public String getColumn03() {
		return column03;
	}

	public void setColumn03(String column03) {
		this.column03 = column03;
	}

	public String getColumn04() {
		return column04;
	}

	public void setColumn04(String column04) {
		this.column04 = column04;
	}

	public String getColumn05() {
		return column05;
	}

	public void setColumn05(String column05) {
		this.column05 = column05;
	}

	public String getColumn06() {
		return column06;
	}

	public void setColumn06(String column06) {
		this.column06 = column06;
	}

	public String getColumn07() {
		return column07;
	}

	public void setColumn07(String column07) {
		this.column07 = column07;
	}

	public String getColumn08() {
		return column08;
	}

	public void setColumn08(String column08) {
		this.column08 = column08;
	}

	public String getColumn09() {
		return column09;
	}

	public void setColumn09(String column09) {
		this.column09 = column09;
	}

	public String getColumn10() {
		return column10;
	}

	public void setColumn10(String column10) {
		this.column10 = column10;
	}

	public String getColumn11() {
		return column11;
	}

	public void setColumn11(String column11) {
		this.column11 = column11;
	}

	public String getColumn12() {
		return column12;
	}

	public void setColumn12(String column12) {
		this.column12 = column12;
	}

	public String getColumn13() {
		return column13;
	}

	public void setColumn13(String column13) {
		this.column13 = column13;
	}

	public String getColumn14() {
		return column14;
	}

	public void setColumn14(String column14) {
		this.column14 = column14;
	}

	public String getColumn15() {
		return column15;
	}

	public void setColumn15(String column15) {
		this.column15 = column15;
	}

	public ArrayList<WSBuyerSKUDetails> getBuyerSkuDetails() {
		return buyerSkuDetails;
	}

	public void setBuyerSkuDetails(ArrayList<WSBuyerSKUDetails> buyerSkuDetails) {
		this.buyerSkuDetails = buyerSkuDetails;
	}
	
	
	
	
	
	

}
