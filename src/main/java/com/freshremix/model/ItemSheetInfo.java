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
 * Nov 9, 2010		raquino		
 */
package com.freshremix.model;

/**
 * @author raquino
 *
 */
public class ItemSheetInfo {

	private String skuId;
    private String skuCategory;     // Fruits, Vegetables, FreshFish only
	private String sellerName;
	private String sellerId;
	private String skuGroupId;
	private String skuGroupName;		
	private String desc;		
	private String marketCondition;	
	private String AreaOfProduction;	
	private String Class1;
	private String Class2;
	private String packQuantity;	
	private String packType;
	private String unitPriceWithoutTax;
	private String unitPriceWithTax;	
	private String uom;
    private String altPrice1;
    private String altPrice2;
    private String skuExternalId;
	 

    public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
    public String getSkuCategory() {
        return skuCategory;
    }
    public void setSkuCategory(String skuCategory) {
        this.skuCategory = skuCategory;
    }
    
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
     
    public String getSkuGroupId() {
		return skuGroupId;
	}
	public void setSkuGroupId(String skuGroupId) {
		this.skuGroupId = skuGroupId;
	}
    
    
	public String getSkuGroupName() {
		return skuGroupName;
	}
	public void setSkuGroupName(String skuGroupName) {
		this.skuGroupName = skuGroupName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getMarketCondition() {
		return marketCondition;
	}
	public void setMarketCondition(String marketCondition) {
		this.marketCondition = marketCondition;
	}
	public String getAreaOfProduction() {
		return AreaOfProduction;
	}
	public void setAreaOfProduction(String areaOfProduction) {
		AreaOfProduction = areaOfProduction;
	}
	public String getClass1() {
		return Class1;
	}
	public void setClass1(String class1) {
		Class1 = class1;
	}
	public String getClass2() {
		return Class2;
	}
	public void setClass2(String class2) {
		Class2 = class2;
	}
	public String getPackQuantity() {
		return packQuantity;
	}
	public void setPackQuantity(String packQuantity) {
		this.packQuantity = packQuantity;
	}
	public String getPackType() {
		return packType;
	}
	public void setPackType(String packType) {
		this.packType = packType;
	}
	public String getUnitPriceWithoutTax() {
		return unitPriceWithoutTax;
	}
	public void setUnitPriceWithoutTax(String unitPriceWithoutTax) {
		this.unitPriceWithoutTax = unitPriceWithoutTax;
	}
	public String getUnitPriceWithTax() {
		return unitPriceWithTax;
	}
	public void setUnitPriceWithTax(String unitPriceWithTax) {
		this.unitPriceWithTax = unitPriceWithTax;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getAltPrice1() {
        return altPrice1;
    }
	public void setAltPrice1(String altPrice) {
        this.altPrice1 = altPrice;
    }
    public String getAltPrice2() {
        return altPrice2;
    }
	public void setAltPrice2(String altPrice) {
        this.altPrice2 = altPrice;
    }
    
    public String getSkuExternalId() {
		return skuExternalId;
	}
	public void setSkuExternalId(String skuExternalId) {
		this.skuExternalId = skuExternalId;
	}
    
}
