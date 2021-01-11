package com.freshremix.model;

import java.math.BigDecimal;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SKUBuyerQtyMapTest {

	
	@Test
	public void testPutOrderItem() {
		 SKUBuyerQtyMap map = new  SKUBuyerQtyMap();
		 OrderItem oi = new OrderItem();
		 oi.setQuantity(new BigDecimal(12));
		 SKU sku = new SKU();
		 sku.setSkuId(2);
		 Order o = new Order();
		 o.setDeliveryDate("10092013");
		 o.setBuyerId(15);
		 oi.setSku(sku);
		 oi.setOrder(o);
		 
		 map.put(oi);
		 BigDecimal qty = map.getQty("10092013", 2, 15);
		 Assert.assertEquals(qty.intValue(), 12);
		 
		
	}
	
	
	@Test
	public void testPutOrderItemNullQty() {
		 SKUBuyerQtyMap map = new  SKUBuyerQtyMap();
		 OrderItem oi = new OrderItem();
		 oi.setQuantity(null);
		 SKU sku = new SKU();
		 sku.setSkuId(2);
		 Order o = new Order();
		 o.setDeliveryDate("10092013");
		 o.setBuyerId(15);
		 oi.setSku(sku);
		 oi.setOrder(o);
		 
		 map.put(oi);
		 BigDecimal qty = map.getQty("10092013", 2, 15);
		 Assert.assertEquals(qty.intValue(),0);
		 
		
	}
	
	@Test
	public void testGetQty() {
		 SKUBuyerQtyMap map = new  SKUBuyerQtyMap();
		 OrderItem oi = new OrderItem();
		 oi.setQuantity(new BigDecimal(25));
		 SKU sku = new SKU();
		 sku.setSkuId(2);
		 Order o = new Order();
		 o.setDeliveryDate("10092013");
		 o.setBuyerId(15);
		 oi.setSku(sku);
		 oi.setOrder(o);
		 
		 map.put(oi);
		 BigDecimal qty = map.getQty("10092013", 2, 15);
		 Assert.assertEquals(qty.intValue(),25);
		 
		 qty = map.getQty("10092013", 4, 21);
		 Assert.assertEquals(qty.intValue(),0);
		 
		
	}
	
	
}
