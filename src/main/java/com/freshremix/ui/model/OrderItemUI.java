package com.freshremix.ui.model;

import java.math.BigDecimal;
import java.util.Map;

import com.freshremix.model.Company;
import com.freshremix.util.TaxUtil;

public class OrderItemUI {

	private Integer skuId;
	private Integer skuBaId;
	private Integer sellerId;
	private String myselect;
	private String companyname;
	private String sellername;
	private Integer skuGroupId;
	private String marketname;
	private String skuname;
	private String home;
	private String grade;
	private String clazzname;
	private BigDecimal price1;
	private BigDecimal price2;
	private BigDecimal pricewotax;
	private BigDecimal pricewtax;
	private BigDecimal packageqty;
	private String packagetype;
	private Integer unitorder;
	private BigDecimal skumaxlimit;
	private Map<String, BigDecimal> qtyMap;
	private Map<String, String> visMap;
	private Map<String, String> apprvMap;
	private Map<String, String> lockMap;
	private BigDecimal rowqty;
	private String billingAkadenSortId;
	private String comments; 
	private Company company;
	private BigDecimal purchasePrice;
	private BigDecimal sellingPrice;
	private Integer sellingUom;
	private String skuComments;
	// 20 new columns
	private Integer column01;
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
	private String column16;
	private String column17;
	private String column18;
	private String column19;
	private String column20;
	private String externalSkuId;

	public OrderItemUI() { }
	
	public Map<String, String> getApprvMap() {
		return apprvMap;
	}
	public void setApprvMap(Map<String, String> apprvMap) {
		this.apprvMap = apprvMap;
	}
	public Map<String, String> getLockMap() {
		return lockMap;
	}

	public void setLockMap(Map<String, String> lockMap) {
		this.lockMap = lockMap;
	}

	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public BigDecimal getSkumaxlimit() {
		return skumaxlimit;
	}
	public void setSkumaxlimit(BigDecimal skumaxlimit) {
		this.skumaxlimit = skumaxlimit;
	}
	public Map<String, String> getVisMap() {
		return visMap;
	}
	public void setVisMap(Map<String, String> visMap) {
		this.visMap = visMap;
	}
	public String getMyselect() {
		return myselect;
	}
	public void setMyselect(String myselect) {
		this.myselect = myselect;
	}
	public String getSellername() {
		return sellername;
	}
	public void setSellername(String sellername) {
		this.sellername = sellername;
	}
	public String getMarketname() {
		return marketname;
	}
	public void setMarketname(String marketname) {
		this.marketname = marketname;
	}
	public String getSkuname() {
		return skuname;
	}
	public void setSkuname(String skuname) {
		this.skuname = skuname;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getClazzname() {
		return clazzname;
	}
	public void setClazzname(String clazzname) {
		this.clazzname = clazzname;
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
	public BigDecimal getPricewotax() {
		return pricewotax;
	}
	public void setPricewotax(BigDecimal pricewotax) {
		this.pricewotax = pricewotax;
		if (pricewotax != null) {
			this.pricewtax = TaxUtil.getPriceWithTax(pricewotax, TaxUtil.ROUND, 2);
		}
	}
	public BigDecimal getPricewtax() {
		return pricewtax;
	}
	public void setPricewtax(BigDecimal pricewtax) {
		this.pricewtax = pricewtax;
	}
	public BigDecimal getPackageqty() {
		return packageqty;
	}
	public void setPackageqty(BigDecimal packageqty) {
		this.packageqty = packageqty;
	}
	public String getPackagetype() {
		return packagetype;
	}
	public void setPackagetype(String packagetype) {
		this.packagetype = packagetype;
	}
	public Integer getUnitorder() {
		return unitorder;
	}
	public void setUnitorder(Integer unitorder) {
		this.unitorder = unitorder;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public Map<String, BigDecimal> getQtyMap() {
		return qtyMap;
	}
	public void setQtyMap(Map<String, BigDecimal> qtyMap) {
		this.qtyMap = qtyMap;
	}

	public BigDecimal getRowqty() {
		return rowqty;
	}

	public void setRowqty(BigDecimal rowqty) {
		this.rowqty = rowqty;
	}

	public String getBillingAkadenSortId() {
		return billingAkadenSortId;
	}

	public void setBillingAkadenSortId(String billingAkadenSortId) {
		this.billingAkadenSortId = billingAkadenSortId;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getSkuBaId() {
		return skuBaId;
	}

	public void setSkuBaId(Integer skuBaId) {
		this.skuBaId = skuBaId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getSkuGroupId() {
		return skuGroupId;
	}

	public void setSkuGroupId(Integer skuGroupId) {
		this.skuGroupId = skuGroupId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Integer getSellingUom() {
		return sellingUom;
	}

	public void setSellingUom(Integer sellingUom) {
		this.sellingUom = sellingUom;
	}

	public String getSkuComments() {
		return skuComments;
	}

	public void setSkuComments(String skuComments) {
		this.skuComments = skuComments;
	}

	public Integer getColumn01() {
		return column01;
	}

	public void setColumn01(Integer column01) {
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

	public String getColumn16() {
		return column16;
	}

	public void setColumn16(String column16) {
		this.column16 = column16;
	}

	public String getColumn17() {
		return column17;
	}

	public void setColumn17(String column17) {
		this.column17 = column17;
	}

	public String getColumn18() {
		return column18;
	}

	public void setColumn18(String column18) {
		this.column18 = column18;
	}

	public String getColumn19() {
		return column19;
	}

	public void setColumn19(String column19) {
		this.column19 = column19;
	}

	public String getColumn20() {
		return column20;
	}

	public void setColumn20(String column20) {
		this.column20 = column20;
	}

	public String getExternalSkuId() {
		return externalSkuId;
	}

	public void setExternalSkuId(String externalSkuId) {
		this.externalSkuId = externalSkuId;
	}
	
}