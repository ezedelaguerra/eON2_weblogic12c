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
 * Mar 31, 2010		Jovino Balunan		
 */
package com.freshremix.ui.model;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Jovino Balunan
 *
 */
public class AkadenItemUI {

	private Integer isNewSKU;
	private String typeFlag;
	private Integer akadenSkuId;
	private Integer skuId;
	private String myselect;
	private String sellername;
	private Integer skuGroupId;
	private String groupname;
	private Integer companyId;
	private Integer sellerId;
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
	private BigDecimal rowqty;
	private String comments;
	private Integer sheetTypeId;
	private Integer proposedBy;
	private BigDecimal purchasePrice;
	private BigDecimal sellingPrice;
	private Integer sellingUom;
	private String skuComments;

	
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
	public Map<String, String> getApprvMap() {
		return apprvMap;
	}
	public void setApprvMap(Map<String, String> apprvMap) {
		this.apprvMap = apprvMap;
	}
	public Integer getProposedBy() {
		return proposedBy;
	}
	public void setProposedBy(Integer proposedBy) {
		this.proposedBy = proposedBy;
	}
	public Integer getSheetTypeId() {
		return sheetTypeId;
	}
	public void setSheetTypeId(Integer sheetTypeId) {
		this.sheetTypeId = sheetTypeId;
	}
	public Integer getSkuGroupId() {
		return skuGroupId;
	}
	public void setSkuGroupId(Integer skuGroupId) {
		this.skuGroupId = skuGroupId;
	}
	public Integer getIsNewSKU() {
		return isNewSKU;
	}
	public void setIsNewSKU(Integer isNewSKU) {
		this.isNewSKU = isNewSKU;
	}
	public String getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
	public Integer getAkadenSkuId() {
		return akadenSkuId;
	}
	public void setAkadenSkuId(Integer akadenSkuId) {
		this.akadenSkuId = akadenSkuId;
	}
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public AkadenItemUI() { }
	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
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
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
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
		this.pricewtax = pricewotax.multiply(new BigDecimal(1.05));
		this.pricewtax.setScale(2, BigDecimal.ROUND_HALF_UP);
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
}
