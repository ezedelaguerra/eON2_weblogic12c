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
package com.freshremix.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.User;
import com.freshremix.ui.model.AkadenItemUI;
import com.freshremix.ui.model.OrderDetails;

/**
 * @author Jovino Balunan
 *
 */
public class AkadenSheetUtil {
	public static AkadenSKU toAkadenSKU (AkadenItemUI aiu, OrderDetails orderDetails, User users) {
		AkadenSKU sku = new AkadenSKU();
		SKUGroup skuGroup = new SKUGroup();
		OrderUnit orderUnit = new OrderUnit();
		OrderUnit sellingUom = new OrderUnit();
		
		skuGroup.setSkuGroupId(aiu.getSkuGroupId());
		orderUnit.setOrderUnitId(aiu.getUnitorder() != null ? Integer.valueOf(aiu.getUnitorder()) : null);
		users.setUserId(aiu.getSellerId());
		sellingUom.setOrderUnitId(aiu.getSellingUom());
		
		sku.setTypeFlag(aiu.getTypeFlag());
		sku.setAkadenSkuId(aiu.getAkadenSkuId());
		sku.setSkuId(aiu.getSkuId());
		sku.setSkuName(aiu.getSkuname());
		sku.setTypeFlag(aiu.getTypeFlag());
		sku.setUser(users);
		//sku.setCompany(orderDetails.getUser().getCompany());
		sku.setClazz(aiu.getClazzname());
		sku.setGrade(aiu.getGrade());
		sku.setLocation(aiu.getHome());
		sku.setMarket(aiu.getMarketname());
		sku.setPackageQuantity(aiu.getPackageqty());
		sku.setPackageType(aiu.getPackagetype());
		sku.setPrice1(aiu.getPrice1());
		sku.setPrice2(aiu.getPrice2());
		sku.setSheetTypeId(SheetTypeConstants.SELLER_AKADEN_SHEET);
		sku.setPriceWithoutTax(aiu.getPricewotax());
		sku.setSkuCategoryId(orderDetails.getSkuCategoryId());
		sku.setSkuGroup(skuGroup);
		sku.setOrderUnit(orderUnit);
		sku.setComments(aiu.getComments());
		//sku.setProposedBy(proposedBy);
//		sku.setPurchasePrice(aiu.getPurchasePrice());
//		sku.setSellingPrice(aiu.getSellingPrice());
//		sku.setSellingUom(sellingUom);
		sku.setComments(aiu.getSkuComments());
		return sku;
	}
	
	public static SKU toSKU (AkadenItemUI aiu, OrderDetails orderDetails, User users) {
		SKU sku = new SKU();
		User sellerUser = new User();
		SKUGroup skuGroup = new SKUGroup();
		OrderUnit orderUnit = new OrderUnit();
		OrderUnit sellingUom = new OrderUnit();
		
		sellerUser.setUserId(aiu.getSellerId());
		skuGroup.setSkuGroupId(aiu.getSkuGroupId());
		orderUnit.setOrderUnitId(aiu.getUnitorder());
		sellingUom.setOrderUnitId(aiu.getSellingUom());
		
		sku.setSkuId(aiu.getSkuId());
		sku.setClazz(aiu.getClazzname());
		sku.setUser(sellerUser);
		//sku.setCompany(orderDetails.getUser().getCompany());
		sku.setGrade(aiu.getGrade());
		sku.setLocation(aiu.getHome());
		sku.setMarket(aiu.getMarketname());
		sku.setOrigSkuId(aiu.getSkuId());
		sku.setPackageQuantity(aiu.getPackageqty());
		sku.setPackageType(aiu.getPackagetype());
		sku.setPrice1(aiu.getPrice1());
		sku.setPrice2(aiu.getPrice2());
		sku.setPriceWithoutTax(aiu.getPricewotax());
		sku.setSheetType(orderDetails.getSheetType());
		sku.setSkuCategoryId(orderDetails.getSkuCategoryId());
		sku.setSkuName(aiu.getSkuname());
		sku.setSkuGroup(skuGroup);
		sku.setOrderUnit(orderUnit);
		sku.setComments(aiu.getComments());
//		sku.setPurchasePrice(aiu.getPurchasePrice());
//		sku.setSellingPrice(aiu.getSellingPrice());
//		sku.setSellingUom(sellingUom);
		sku.setComments(aiu.getSkuComments());
		
		return sku;
	}
	
	// parse semi-colon delimited string (int)
	public static  List<Integer> toList(String str) {
		List<Integer> list = new ArrayList<Integer>();
		if (!StringUtil.isNullOrEmpty(str)) {
			if (str.indexOf(";") != -1) {
				StringTokenizer st = new StringTokenizer(str,";");
				while(st.hasMoreTokens()) {
					list.add(Integer.valueOf(st.nextToken()));
				}
			} else {
				list.add(Integer.valueOf(str));
			}
		}
		
		return list;
	}
	
}
