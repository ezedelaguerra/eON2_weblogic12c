package com.freshremix.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WSSellerSKURequest {
	
	@XmlElement(required = true)
	private String sellerId;
	@XmlElement(required = true)
	private String uomName;
	

	
	private String altPrice1;
	private String altPrice2;
	private String areaOfProduction;
	private String class1;
	private String class2;
	private String market;
	@XmlElement(required = true)
	private String packageQty;
	private String packageType;
	@XmlElement(required = true)
	private String skuGroupName;

	@XmlElement(required = true)
	private String skuName;
	private String unitPriceWithoutTax;
	private String maxQty;
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
	
	@XmlElement(required = true)
	private ArrayList<WSSellerBuyerDetails> buyerDetails;

	
	@XmlTransient 
	private WSSellerSKU sellerSKU;
	
	
	public WSSellerSKU getSellerSKU() {
		return sellerSKU;
	}
	public void setSellerSKU(WSSellerSKU sellerSKU) {
		this.sellerSKU = sellerSKU;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	public ArrayList<WSSellerBuyerDetails> getBuyerDetails() {
		return buyerDetails;
	}
	public void setBuyerDetails(ArrayList<WSSellerBuyerDetails> buyerDetails) {
		this.buyerDetails = buyerDetails;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getAltPrice1() {
		return altPrice1;
	}
	public void setAltPrice1(String altPrice1) {
		this.altPrice1 = altPrice1;
	}
	public String getAltPrice2() {
		return altPrice2;
	}
	public void setAltPrice2(String altPrice2) {
		this.altPrice2 = altPrice2;
	}
	public String getAreaOfProduction() {
		return areaOfProduction;
	}
	public void setAreaOfProduction(String areaOfProduction) {
		this.areaOfProduction = areaOfProduction;
	}
	public String getClass1() {
		return class1;
	}
	public void setClass1(String class1) {
		this.class1 = class1;
	}
	public String getClass2() {
		return class2;
	}
	public void setClass2(String class2) {
		this.class2 = class2;
	}
	
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getPackageQty() {
		return packageQty;
	}
	public void setPackageQty(String packageQty) {
		this.packageQty = packageQty;
	}
	
	public String getSkuGroupName() {
		return skuGroupName;
	}
	public void setSkuGroupName(String skuGroupName) {
		this.skuGroupName = skuGroupName;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	
	public String getUnitPriceWithoutTax() {
		return unitPriceWithoutTax;
	}
	public void setUnitPriceWithoutTax(String unitPriceWithoutTax) {
		this.unitPriceWithoutTax = unitPriceWithoutTax;
	}
	public String getMaxQty() {
		return maxQty;
	}
	public void setMaxQty(String maxQty) {
		this.maxQty = maxQty;
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
	
	
	
	
	
}
