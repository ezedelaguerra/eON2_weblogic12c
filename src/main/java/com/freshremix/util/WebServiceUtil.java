package com.freshremix.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.model.AllocationBuyerInformation;
import com.freshremix.model.AllocationInputDetails;
import com.freshremix.model.Company;
import com.freshremix.model.Order;
import com.freshremix.model.SKU;
import com.freshremix.model.User;
import com.freshremix.model.WSBuyerInformation;
import com.freshremix.model.WSInputDetails;
import com.freshremix.model.WSSellerBuyerDetails;
import com.freshremix.model.WSSellerSKU;

public class WebServiceUtil {
	/**
	 * 
	 * @param details
	 * @param user
	 * @param isOrderSheet
	 * @return
	 */
	public static SKU wsToSKU(Object details, User user, boolean isOrderSheet) {
		SKU sku = new SKU();
		
		if (isOrderSheet) {
			sku = wstoSKU((WSInputDetails) details, user);
		} else {
			sku = wstoSKU((AllocationInputDetails) details, user.getCompany().getCompanyId());
		}

		sku.setUser(user);
		return sku;
	}

	/**
	 * 
	 * @param details
	 * @param companyId
	 * @return
	 */
	public static SKU wstoSKU(WSInputDetails details, User seller) {
		SKU sku = new SKU();

		sku.setSkuId(Integer.valueOf(details.getSkuId()));
		sku.setClazz(details.getClass2());
		sku.setSkuMaxLimit(details.getSkuMaxLimit());
		sku.setUser(seller);
		sku.setGrade(details.getClass1());
		sku.setLocation(details.getLocation());
		sku.setMarket(details.getMarket());
		sku.setPackageQuantity(details.getPackageQuantity());
		sku.setPackageType(details.getPackageType());
		sku.setPrice1(details.getPrice1());
		sku.setPrice2(details.getPrice2());
		sku.setPriceWithoutTax(details.getPriceWithoutTax());
		sku.setSheetType(SheetTypeConstants.SELLER_ORDER_SHEET);
		sku.setSkuName(details.getSkuName());
		sku.setExternalSkuId(details.getSkuExternalID());
		return sku;
	}

	/**
	 * 
	 * @param details
	 * @param companyId
	 * @return
	 */
	public static SKU wstoSKU(AllocationInputDetails details, Integer companyId) {
		SKU sku = new SKU();
		Company company = new Company();
		company.setCompanyId(companyId);

		sku.setSkuId(Integer.valueOf(details.getSkuId()));
		sku.setClazz(details.getClass1());
		sku.setSkuMaxLimit(details.getSkuMaxLimit());
		sku.setGrade(details.getClass2());
		sku.setLocation(details.getLocation());
		sku.setMarket(details.getMarket());
		sku.setPackageQuantity(new BigDecimal(details.getPackageQuantity()));
		sku.setPackageType(details.getPackageType());
		sku.setPrice1(new BigDecimal(details.getPrice1()));
		sku.setPrice2(new BigDecimal(details.getPrice2()));
		sku.setPriceWithoutTax(new BigDecimal(details.getPriceWithoutTax()));
		sku.setSheetType(SheetTypeConstants.SELLER_ALLOCATION_SHEET);
		sku.setSkuName(details.getSkuName());
		sku.setExternalSkuId(details.getSkuExternalID());
		return sku;
	}
	
	/**
	 * create buyer mapping
	 * 
	 * @param buyerId
	 * @return
	 */
	public static Map<Integer, Object> createBuyerMap(
			WSBuyerInformation[] wsBuyerInformation) {

		Map<Integer, Object> buyerMap = new HashMap<Integer, Object>();
		for (WSBuyerInformation obj : wsBuyerInformation) {
			buyerMap.put(obj.getBuyerId(), obj);
		}

		return buyerMap;
	}
	
	/**
	 * create buyer mapping
	 * 
	 * @param buyerId
	 * @return
	 */
	public static Map<Integer, Object> createBuyerMap(
			AllocationBuyerInformation[] allocationBuyerInformation) {

		Map<Integer, Object> buyerMap = new HashMap<Integer, Object>();
		for (AllocationBuyerInformation obj : allocationBuyerInformation) {
			buyerMap.put(Integer.valueOf(obj.getBuyerId()), obj);
		}

		return buyerMap;
	}
	
	public static  List<Order> extractOrderFromDetails(List<WSSellerBuyerDetails> details){
		List<Order> orders = new ArrayList<Order>();
		for(WSSellerBuyerDetails detail: details){
			if(!orders.contains(detail.getOrder())){
				orders.add(detail.getOrder());
			}
		}
		return orders;
	}
	
	
	public static  List<Integer> extractBuyerIDFromDetails(List<WSSellerBuyerDetails> details){
		List<Integer> buyers = new ArrayList<Integer>();
		for(WSSellerBuyerDetails detail: details){
			if(!buyers.contains(detail.getBuyer().getUserId())){
				buyers.add(detail.getBuyer().getUserId());
			}
		}
		return buyers;
	}
	public static  List<SKU> extractSKUFromSellerSKU(List<WSSellerSKU> wsSkuList){
		List<SKU> skuList = new ArrayList<SKU>();
		for(WSSellerSKU sku: wsSkuList){
			if(!skuList.contains(sku.getSkuDB())){
				skuList.add(sku.getSkuDB());
			}
		}
		return skuList;
	}
}
