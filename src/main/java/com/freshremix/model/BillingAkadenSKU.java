package com.freshremix.model;

import java.math.BigDecimal;

public class BillingAkadenSKU {

	private Integer skuId;
	private String billingAkadenSkuId;
	private String skuName;
	private Integer origSkuId;
	private Integer sheetType;
	private Integer skuCategoryId;
	private SKUGroup skuGroup;
	private String status;
	private User user;
	private Company company;
	private String location;
	private String market;
	private String grade;
	private String clazz;
	private BigDecimal price1;
	private BigDecimal price2;
	private BigDecimal priceWithoutTax;
	private BigDecimal priceWithTax;
	private BigDecimal packageQuantity;
	private String packageType;
	private OrderUnit orderUnit;
	
	private String pmfFlag;
	
	public BillingAkadenSKU() { }

	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public Integer getOrigSkuId() {
		return origSkuId;
	}
	public void setOrigSkuId(Integer origSkuId) {
		this.origSkuId = origSkuId;
	}
	public Integer getSheetType() {
		return sheetType;
	}
	public void setSheetType(Integer sheetType) {
		this.sheetType = sheetType;
	}
	public Integer getSkuCategoryId() {
		return skuCategoryId;
	}
	public void setSkuCategoryId(Integer skuCategoryId) {
		this.skuCategoryId = skuCategoryId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
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
	public BigDecimal getPrice1() {
		return price1;
	}
	public void setPrice1(BigDecimal price1) {
		this.price1 = price1;
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
		this.priceWithTax = priceWithoutTax.multiply(new BigDecimal(1.05));
		this.priceWithTax = this.priceWithTax.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public BigDecimal getPackageQuantity() {
		return packageQuantity;
	}
	public void setPackageQuantity(BigDecimal packageQuantity) {
		this.packageQuantity = packageQuantity;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public BigDecimal getPriceWithTax() {
		return priceWithTax;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getPmfFlag() {
		return pmfFlag;
	}
	public void setPmfFlag(String pmfFlag) {
		this.pmfFlag = pmfFlag;
	}

	public String getBillingAkadenSkuId() {
		return billingAkadenSkuId;
	}
	
	public void setBillingAkadenSkuId(String billingAkadenSkuId) {
		this.billingAkadenSkuId = billingAkadenSkuId;
	}

	public SKUGroup getSkuGroup() {
		return skuGroup;
	}

	public void setSkuGroup(SKUGroup skuGroup) {
		this.skuGroup = skuGroup;
	}

	@Override
	public boolean equals(Object obj) {
		BillingAkadenSKU skuObj = (BillingAkadenSKU) obj;
		
		System.out.println("skuId: [" + skuObj.getSkuId() + "] [" + this.skuId + "]");
		System.out.println("billingAkadenSkuId: [" + skuObj.getBillingAkadenSkuId() + "] [" + this.billingAkadenSkuId + "]");
		System.out.println("skuName: [" + skuObj.getSkuName() + "] [" + this.skuName + "]");
		System.out.println("origSkuId: [" + skuObj.getOrigSkuId() + "] [" + this.origSkuId + "]");
		System.out.println("sheetType: [" + skuObj.getSheetType() + "] [" + this.sheetType + "]");
		System.out.println("skuCategory: [" + skuObj.getSkuCategoryId() + "] [" + this.skuCategoryId + "]");
		System.out.println("skuGroup: [" + skuObj.getSkuGroup().getDescription() + "] [" + this.getSkuGroup().getDescription() + "]");
		System.out.println("status: [" + skuObj.getStatus() + "] [" + this.status + "]");
		System.out.println("user: [" + skuObj.getUser().getUserId() + "] [" + this.user.getUserId() + "]");
		System.out.println("company: [" + skuObj.getCompany().getCompanyId() + "] [" + this.company.getCompanyId() + "]");
		System.out.println("location: [" + skuObj.getLocation() + "] [" + this.location + "]");
		System.out.println("market: [" + skuObj.getMarket() + "] [" + this.market + "]");
		System.out.println("grade: [" + skuObj.getGrade() + "] [" + this.grade + "]");
		System.out.println("clazz: [" + skuObj.getClazz() + "] [" + this.clazz + "]");
		System.out.println("price1: [" + skuObj.getPrice1() + "] [" + this.price1 + "]");
		System.out.println("price2: [" + skuObj.getPrice2() + "] [" + this.price2 + "]");
		System.out.println("priceWOTax: [" + skuObj.getPriceWithoutTax() + "] [" + this.priceWithoutTax + "]");
		System.out.println("packageQuantity: [" + skuObj.getPackageQuantity() + "] [" + this.packageQuantity + "]");
		System.out.println("packageType: [" + skuObj.getPackageType() + "] [" + this.packageType + "]");
		System.out.println("unitOfOrder: [" + skuObj.getOrderUnit().getOrderUnitId() + "] [" + this.orderUnit.getOrderUnitId() + "]");
		
		if (
			!skuObj.getSkuId().equals(this.skuId) ||
			!skuObj.getSkuName().equals(this.skuName) ||
			//!skuObj.getOrigSkuId().equals(this.origSkuId) ||
			!skuObj.getSheetType().equals(this.sheetType) ||
			!skuObj.getSkuCategoryId().equals(this.skuCategoryId) ||
			!skuObj.getSkuGroup().getDescription().equals(this.getSkuGroup().getDescription()) ||
			!skuObj.getStatus().equals(this.status) ||
			!skuObj.getUser().getUserId().equals(this.user.getUserId()) ||
			!skuObj.getCompany().getCompanyId().equals(this.company.getCompanyId()) ||
			!skuObj.getLocation().equals(this.location) ||
			!skuObj.getMarket().equals(this.market) ||
			!skuObj.getGrade().equals(this.grade) ||
			!skuObj.getClazz().equals(this.clazz) ||
			!skuObj.getPrice1().equals(this.price1) ||
			!skuObj.getPrice2().equals(this.price2) ||
			!skuObj.getPriceWithoutTax().equals(this.priceWithoutTax) ||
			!skuObj.getPackageQuantity().equals(this.packageQuantity) ||
			!skuObj.getPackageType().equals(this.packageType) ||
			!skuObj.getOrderUnit().getOrderUnitId().equals(this.orderUnit.getOrderUnitId())
		) {	return false; }
		else { return true; }
	}
	
	public OrderUnit getOrderUnit() {
		return orderUnit;
	}

	public void setOrderUnit(OrderUnit orderUnit) {
		this.orderUnit = orderUnit;
	}
}
