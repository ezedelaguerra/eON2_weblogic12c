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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author raquino
 *
 */
public class SKUInfoWithAltPrice {
	private String skuid;
	private String desc;
    private String uom;
    private String skuGroupName;
    private String buyerName;
    private String sellerName;
    private String marketCondition;
    private String areaOfProd;
    private String class1;
    private String class2;
    private String packType;
    private String packQuantity;
    private String category;
    private String skuExternalID;
    private String unitPriceWithoutTax;
    
    private Integer fftSkuId;
    private Integer buyerID;
    private Integer sellerID;
    private Integer sheetType;
    private Integer skuGroupID;
    private Integer unitPriceWithTax;
    private String price1;
    private String price2;
    private String quantity;
    
    private Date orderDate;
    
    // ENHANCEMENT WS 20090826 LELE
    private int isSkuVisible; //1 = visible; 0 = not visible
    
    public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
    
    public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}
    
    public Integer getSkuGroupID() {
		return skuGroupID;
	}

	public void setSkuGroupID(Integer skuGroupID) {
		this.skuGroupID = skuGroupID;
	}
    
    public Integer getFftSkuId() {
		return fftSkuId;
	}

	public void setFftSkuId(Integer fftSkuId) {
		this.fftSkuId = fftSkuId;
	}
    
    public String getSkuGroupName() {
		return skuGroupName;
	}

	public void setSkuGroupName(String skuGroupName) {
		this.skuGroupName = skuGroupName;
	}
    
    public Integer getBuyerID() {
		return buyerID;
	}

	public void setBuyerID(Integer buyerID) {
		this.buyerID = buyerID;
	}
    
    public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
    
    public Integer getSellerID() {
		return sellerID;
	}

	public void setSellerID(Integer sellerID) {
		this.sellerID = sellerID;
	}
    
    public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
    
    public String getMarketCondition() {
		return marketCondition;
	}

	public void setMarketCondition(String marketCondition) {
		this.marketCondition = marketCondition;
	}
    
    public String getAreaOfProd() {
		return areaOfProd;
	}

	public void setAreaOfProd(String areaOfProd) {
		this.areaOfProd = areaOfProd;
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
    
    public String getPackType() {
		return packType;
	}

	public void setPackType(String packType) {
		this.packType = packType;
	}
    
    public Integer getSheetType() {
		return sheetType;
	}

	public void setSheetType(Integer sheetType) {
		this.sheetType = sheetType;
	}
    
    public String getSkuExternalID() {
		return skuExternalID;
	}

	public void setSkuExternalID(String skuExternalID) {
		this.skuExternalID = skuExternalID;
	}
    
    public final String getQuantity() {
        return quantity;
    }

    public final void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    
    public final String getPackQuantity() {
        return packQuantity;
    }

    public final void setPackQuantity(String packQuantity) {
        this.packQuantity = packQuantity;
    }    
    
    public final String getUnitPriceWithoutTax() {
        return unitPriceWithoutTax;
    }

    public final void setUnitPriceWithoutTax(String unitPriceWithoutTax) {
        this.unitPriceWithoutTax = unitPriceWithoutTax;
    }
    
    public final Integer getUnitPriceWithTax() {
        return unitPriceWithTax;
    }

    public final void setUnitPriceWithTax(Integer unitPriceWithTax) {
        this.unitPriceWithTax = unitPriceWithTax;
    }
    
    public final Date getOrderDate() {
        return orderDate;
    }

    public final void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    

    // ENHANCEMENT WS 20090826 START: LELE
	public int isSkuVisible() {
		return isSkuVisible;
	}

	public void setSkuVisible(int isSkuVisible) {
		this.isSkuVisible = isSkuVisible;
	}
	// ENHANCEMENT WS 20090826 END: LELE

	public String getPrice1() {
		return price1;
	}

	public String getPrice2() {
		return price2;
	}

	public void setPrice1(String price1) {
		this.price1 = price1;
	}

	public void setPrice2(String price2) {
		this.price2 = price2;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
