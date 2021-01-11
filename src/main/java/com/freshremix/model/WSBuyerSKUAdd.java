package com.freshremix.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WSBuyerSKUAdd extends WSBuyerAddOrderSheetSKU {

	@XmlElement(required = true)
	private String skuName;
	private String unitOrderName;
	@XmlTransient
	private OrderUnit unitOrder;
	@XmlElement(required = true)
	private BigDecimal packageQuantity;
	private BigDecimal price2;
	private BigDecimal priceWithoutTax;
	@XmlElement(required = true)
	private String skuGroupName;
	@XmlTransient
	private Integer skuGroupId;
	private String market;
	private String areaOfProduction;
	private String grade;
	private String clazz;
	private String packageType;

	private BigDecimal center;// column01 in DB
	private String delivery;// column02 in DB
	private String sale; // column03 in DB
	private String jan;// column04 in DB
	private String packFee;// column05 in DB
	private String column01;//column06 in DB
	private String column02;//column07 in DB
	private String column03;//column08 in DB
	private String column04;//column09 in DB
	private String column05;//column10 in DB
	private String column06;//column11 in DB
	private String column07;//column12 in DB
	private String column08;//column13 in DB
	private String column09;//column14 in DB
	private String column10;//column15 in DB
	private String column11;//column16 in DB
	private String column12;//column17 in DB
	private String column13;//column18 in DB
	private String column14;//column19 in DB
	private String column15;//column20 in DB
	
	public Integer getSkuGroupId() {
		return skuGroupId;
	}
	public void setSkuGroupId(Integer skuGroupId) {
		this.skuGroupId = skuGroupId;
	}
	public OrderUnit getUnitOrder() {
		return unitOrder;
	}
	public void setUnitOrder(OrderUnit unitOrder) {
		this.unitOrder = unitOrder;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getUnitOrderName() {
		return unitOrderName;
	}
	public void setUnitOrderName(String unitOrderName) {
		this.unitOrderName = unitOrderName;
	}
	public BigDecimal getPackageQuantity() {
		return packageQuantity;
	}
	public void setPackageQuantity(BigDecimal packageQuantity) {
		this.packageQuantity = packageQuantity;
	}
	public BigDecimal getPrice2() {
		return price2;
	}
	public void setPrice2(BigDecimal price2) {
		this.price2 = price2;
	}
	public BigDecimal getPriceWithoutTax() {
		return priceWithoutTax;
	}
	public void setPriceWithoutTax(BigDecimal priceWithoutTax) {
		this.priceWithoutTax = priceWithoutTax;
	}
	public String getSkuGroupName() {
		return skuGroupName;
	}
	public void setSkuGroupName(String skuGroupName) {
		this.skuGroupName = skuGroupName;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getAreaOfProduction() {
		return areaOfProduction;
	}
	public void setAreaOfProduction(String areaOfProduction) {
		this.areaOfProduction = areaOfProduction;
	}
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	
	public BigDecimal getCenter() {
		return center;
	}
	public void setCenter(BigDecimal center) {
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
