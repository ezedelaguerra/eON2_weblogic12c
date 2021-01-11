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
 * date       	name            changes
 * ------------------------------------------------------------------------------
 * 20120702		Rhoda		PROD ISSUE – SKU w/ maxlimit doesn't appear on Received after SKU Qty input on a published Alloc.
 */
 package com.freshremix.model;

import java.math.BigDecimal;

import com.freshremix.util.NumberUtil;
import com.freshremix.util.StringUtil;
import com.freshremix.util.TaxUtil;

public class SKU implements SKUTemplate {

	private Integer skuId;
	private String skuName;
	private Integer origSkuId;
	private Integer sheetType;
	private Integer skuCategoryId;
	private SKUGroup skuGroup;
	private User user;
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
	private User proposedBy;
	private OrderUnit orderUnit;
	private String pmfFlag;
	private BigDecimal skuMaxLimit;
	private String comments;
	private String externalSkuId;
	
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
	
	public SKU() { }

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
		this.priceWithTax = priceWithoutTax;
		if(priceWithoutTax != null){
			this.priceWithTax = TaxUtil.getPriceWithTax(priceWithoutTax, TaxUtil.ROUND);

		}
			
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
	public String getPmfFlag() {
		return pmfFlag;
	}
	public void setPmfFlag(String pmfFlag) {
		this.pmfFlag = pmfFlag;
	}
	public SKUGroup getSkuGroup() {
		return skuGroup;
	}

	public void setSkuGroup(SKUGroup skuGroup) {
		this.skuGroup = skuGroup;
	}
	public BigDecimal getSkuMaxLimit() {
		return skuMaxLimit;
	}

	public void setSkuMaxLimit(BigDecimal skuMaxLimit) {
		this.skuMaxLimit = skuMaxLimit;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	//start:jr
	public OrderUnit getOrderUnit() {
		return orderUnit;
	}

	public void setOrderUnit(OrderUnit orderUnit) {
		this.orderUnit = orderUnit;
	}
	//end:jr

	public User getProposedBy() {
		return proposedBy;
	}

	public void setProposedBy(User proposedBy) {
		this.proposedBy = proposedBy;
	}

	public String getExternalSkuId() {
		return externalSkuId;
	}

	public void setExternalSkuId(String externalSkuId) {
		this.externalSkuId = externalSkuId;
	}
	
	public Integer getSkuBAId() {
		return null;
	}
	
	public String getSkuComment() {
		return null;
	}

	public BigDecimal getPurchasePrice() {
		return null;
	}

	public BigDecimal getSellingPrice() {
		return null;
	}

	public OrderUnit getSellingUom() {
		return null;
	}

	public Integer getAkadenSkuId() {
		return null;
	}

	public String getIsNewSKU() {
		return null;
	}

	public BigDecimal getQuantity() {
		return null;
	}

	public Integer getSheetTypeId() {
		return null;
	}

	public String getYypeFlag() {
		return null;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result
				+ ((column01 == null) ? 0 : column01.hashCode());
		result = prime * result
				+ ((column02 == null) ? 0 : column02.hashCode());
		result = prime * result
				+ ((column03 == null) ? 0 : column03.hashCode());
		result = prime * result
				+ ((column04 == null) ? 0 : column04.hashCode());
		result = prime * result
				+ ((column05 == null) ? 0 : column05.hashCode());
		result = prime * result
				+ ((column06 == null) ? 0 : column06.hashCode());
		result = prime * result
				+ ((column07 == null) ? 0 : column07.hashCode());
		result = prime * result
				+ ((column08 == null) ? 0 : column08.hashCode());
		result = prime * result
				+ ((column09 == null) ? 0 : column09.hashCode());
		result = prime * result
				+ ((column10 == null) ? 0 : column10.hashCode());
		result = prime * result
				+ ((column11 == null) ? 0 : column11.hashCode());
		result = prime * result
				+ ((column12 == null) ? 0 : column12.hashCode());
		result = prime * result
				+ ((column13 == null) ? 0 : column13.hashCode());
		result = prime * result
				+ ((column14 == null) ? 0 : column14.hashCode());
		result = prime * result
				+ ((column15 == null) ? 0 : column15.hashCode());
		result = prime * result
				+ ((column16 == null) ? 0 : column16.hashCode());
		result = prime * result
				+ ((column17 == null) ? 0 : column17.hashCode());
		result = prime * result
				+ ((column18 == null) ? 0 : column18.hashCode());
		result = prime * result
				+ ((column19 == null) ? 0 : column19.hashCode());
		result = prime * result
				+ ((column20 == null) ? 0 : column20.hashCode());
		result = prime * result
				+ ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((market == null) ? 0 : market.hashCode());
		result = prime * result
				+ ((orderUnit == null) ? 0 : orderUnit.hashCode());
		result = prime * result
				+ ((packageQuantity == null) ? 0 : packageQuantity.hashCode());
		result = prime * result
				+ ((packageType == null) ? 0 : packageType.hashCode());
		result = prime * result + ((price1 == null) ? 0 : price1.hashCode());
		result = prime * result + ((price2 == null) ? 0 : price2.hashCode());
		result = prime * result
				+ ((priceWithoutTax == null) ? 0 : priceWithoutTax.hashCode());
		result = prime * result
				+ ((skuGroup == null) ? 0 : skuGroup.hashCode());
		result = prime * result
				+ ((skuMaxLimit == null) ? 0 : skuMaxLimit.hashCode());
		result = prime * result + ((skuName == null) ? 0 : skuName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SKU other = (SKU) obj;
		if (!equally(clazz,other.clazz))
			return false;
		if (!equally(column01,other.column01))
			return false;
		if (!equally(column02,other.column02))
			return false;
		if (!equally(column03,other.column03))
			return false;
		if (!equally(column04,other.column04))
			return false;
		if (!equally(column05,other.column05))
			return false;
		if (!equally(column06,other.column06))
			return false;
		if (!equally(column07,other.column07))
			return false;
		if (!equally(column08,other.column08))
			return false;
		if (!equally(column09,other.column09))
			return false;
		if (!equally(column10,other.column10))
			return false;
		if (!equally(column11,other.column11))
			return false;
		if (!equally(column12,other.column12))
			return false;
		if (!equally(column13,other.column13))
			return false;
		if (!equally(column14,other.column14))
			return false;
		if (!equally(column15,other.column15))
			return false;
		if (!equally(column16,other.column16))
			return false;
		if (!equally(column17,other.column17))
			return false;
		if (!equally(column18,other.column18))
			return false;
		if (!equally(column19,other.column19))
			return false;
		if (!equally(column20,other.column20))
			return false;
		if (!equally(comments,other.comments))
			return false;
		if (!equally(grade,other.grade))
			return false;
		if (!equally(location,other.location))
			return false;
		if (!equally(market,other.market))
			return false;
		if (!equally(orderUnit.getOrderUnitId(),other.orderUnit.getOrderUnitId()))
			return false;
		if (!equally(packageQuantity,other.packageQuantity))
			return false;
		if (!equally(packageType,other.packageType))
			return false;
		if (!equally(price1,other.price1))
			return false;
		if (!equally(price2,other.price2))
			return false;
		if (!equally(priceWithoutTax,other.priceWithoutTax))
			return false;
		if (!equally(skuGroup.getSkuGroupId(),other.skuGroup.getSkuGroupId()))
			return false;
		if (!equally(skuMaxLimit, other.skuMaxLimit))
			return false;
		if (!equally(skuName,other.skuName))
			return false;
		return true;
	}

//	public boolean equals(Object obj) {
//	SKU skuObj = (SKU) obj;
//		// compare only the fields that can be seen in UI
//		if (
//			!equally(skuObj.getSkuName(),this.skuName) ||
//			!equally(skuObj.getSkuGroup().getSkuGroupId(),this.skuGroup.getSkuGroupId()) ||
//			!equally(skuObj.getLocation(),this.location) ||
//			!equally(skuObj.getMarket(),this.market) ||
//			!equally(skuObj.getGrade(),this.grade) ||
//			!equally(skuObj.getClazz(),this.clazz) ||
//			!equally(skuObj.getPrice1(),this.price1) ||
//			!equally(skuObj.getPrice2(),this.price2) ||
//			!equally(skuObj.getPriceWithoutTax(),this.priceWithoutTax) ||
//			!equally(skuObj.getPackageQuantity(),this.packageQuantity) ||
//			!equally(skuObj.getPackageType(),this.packageType) ||
//			!equally(skuObj.getOrderUnit().getOrderUnitId(),this.orderUnit.getOrderUnitId()) ||
//			!equally(skuObj.getSkuMaxLimit(),this.skuMaxLimit)
//		) {	return false; }
//		else { return true; }
//	}
	
	private boolean equally (String value1, String value2) {
		return StringUtil.nullToBlank(value1).equals(StringUtil.nullToBlank(value2));
	}
	
	private boolean equally (BigDecimal value1, BigDecimal value2) {
		// ENHANCEMENT 20120702: Rhoda PROD ISSUE
//		return NumberUtil.nullToZero(value1).equals(NumberUtil.nullToZero(value2));
		return NumberUtil.nullToZero(value1).compareTo((NumberUtil.nullToZero(value2))) == 0;
	}
	
	private boolean equally (Integer value1, Integer value2) {
		return NumberUtil.nullToZero(value1).equals(NumberUtil.nullToZero(value2));
	}
}
