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
 * Mar 2, 2010		pamela		
 */
package com.freshremix.util;

import com.freshremix.model.PmfSkuList;
import com.freshremix.ui.model.PmfSkuUI;

/**
 * @author Pammie
 *
 */
public class PmfUtil {
	public static PmfSkuList toPmfSku (PmfSkuUI pmf) {
		PmfSkuList sku = new PmfSkuList();
		
		sku.setSkuId(pmf.getSkuId());
		sku.setPmfId(pmf.getPmfId());
		sku.setSkuGroup(pmf.getSkuGroup());
		sku.setSkuName(pmf.getSkuName());
		sku.setSellerProdCode(pmf.getSellerProdCode());
		sku.setBuyerProdCode(pmf.getBuyerProdCode());
		sku.setCommentA(pmf.getCommentA());
		sku.setCommentB(pmf.getCommentB());
		sku.setLocation(pmf.getLocation());
		sku.setMarket(pmf.getMarket());
		sku.setGrade(pmf.getGrade());
		sku.setPmfClass(pmf.getPmfClass());
		sku.setPrice1(pmf.getPrice1());
		sku.setPrice2(pmf.getPrice2());
		sku.setPriceNoTax(pmf.getPriceNoTax());
//		sku.setPricewTax(pmf.getPricewTax());
		sku.setPkgQuantity(pmf.getPkgQuantity());
		sku.setPkgType(pmf.getPkgType());
		sku.setUnitOrder(pmf.getUnitOrder());
		sku.setSellerName(pmf.getSellerName());
		
		return sku;
	}
}
