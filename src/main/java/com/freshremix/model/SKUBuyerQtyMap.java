package com.freshremix.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.freshremix.util.NumberUtil;

/**
 * Class to store a map of buyers with their respective order quantity. 
 * This map is stored to a parent map referenced by the skuid and deliverydate
 * 
 * 
 * @author melissa
 *
 */
public class SKUBuyerQtyMap {
	
	private Map<CompositeKey<String>, Map<Integer, BigDecimal>> skuMap;
	
	
	public SKUBuyerQtyMap() {
		skuMap = new HashMap<CompositeKey<String>, Map<Integer, BigDecimal>>();
	}
	
	/**
	 * Store buyer order qty to a list for an sku-deliverydate combination.
	 * @param oi
	 */
	public void put(OrderItem oi){
		CompositeKey<String> skuDateKey=new CompositeKey<String>(oi.getDeliveryDate(), oi.getSKUId().toString());
		Map<Integer, BigDecimal> buyerMap = skuMap.get(skuDateKey);
		if(buyerMap == null){
			buyerMap = new HashMap<Integer, BigDecimal>();
		}

		buyerMap.put(oi.getBuyerId(), NumberUtil.nullToZero(oi.getQuantity()));
		if(buyerMap.size()>0){
			skuMap.put(skuDateKey, buyerMap);
		}
	}
	/**
	 * Retrieve List of buyers with their corresponding qty for a certain sku and deliverydate
	 * @param deliveryDate
	 * @param skuId
	 * @return
	 */
	public Map<Integer, BigDecimal> getBuyerQtyList(String deliveryDate, Integer skuId){
		CompositeKey<String> skuDateKey=new CompositeKey<String>(deliveryDate, skuId.toString());
		return skuMap.get(skuDateKey);
		
	}
	
	
	/**
	 * Get quantity for a specific buyer-skuid-deliverydate combination
	 * 
	 * @param deliveryDate
	 * @param skuId
	 * @param buyerId
	 * @return
	 */
	public BigDecimal getQty(String deliveryDate, Integer skuId, Integer buyerId){
		CompositeKey<String> skuDateKey=new CompositeKey<String>(deliveryDate, skuId.toString());
		Map<Integer, BigDecimal> buyerMap = skuMap.get(skuDateKey);
		if(MapUtils.isEmpty(buyerMap)){
			return new BigDecimal(0);
		}
		
		return NumberUtil.nullToZero(buyerMap.get(buyerId));
		
	}
	
	
	
}