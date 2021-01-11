package com.freshremix.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractDBUnitTest;
import com.freshremix.dao.OrderItemDao;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.OrderItem;

@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml" })
@AbstractDBUnitTest.FlatXMLDataSet(locations = { //
		"daoTesting/SKUGroupDataset.xml", //
		"daoTesting/SellingUOMDataset.xml", //
		"daoTesting/SKUDataset.xml", //
		"daoTesting/SKUBADataset.xml", //
		"daoTesting/OrderDataset.xml", //
		"daoTesting/OrderItemDataset.xml" //
})
public class OrderItemDaoImplTest extends AbstractDBUnitTest{
	@Resource(name = "orderItemDao")
	private OrderItemDao orderItemDao;
	
	
	@Test
	public void deleteOrderItemNoQuantityBatchTest() throws ServiceException{
		
		List<Integer> orderIds = new ArrayList<Integer>();
		orderIds.add(27509843);
		orderIds.add(27509903);
		List<Integer> skuIds = new ArrayList<Integer>();
		skuIds.add(1320167);
		skuIds.add(43729821);
		
		List<OrderItem> orderItems = orderItemDao.getOrderItemByOrderId(27509843);
		orderItems.addAll( orderItemDao.getOrderItemByOrderId(27509903));		
		Assert.assertEquals(orderItems.size(), 2);
		
		orderItemDao.deleteOrderItemNoQuantityBatch(orderIds, skuIds);
		orderItems = orderItemDao.getOrderItemByOrderId(27509843);
		orderItems.addAll( orderItemDao.getOrderItemByOrderId(27509903));
		
		Assert.assertEquals(orderItems.size(), 1);
	}

}
