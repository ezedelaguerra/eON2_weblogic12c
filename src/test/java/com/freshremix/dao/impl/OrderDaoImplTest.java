package com.freshremix.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractDBUnitTest;
import com.freshremix.dao.OrderDao;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.Order;

@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml" })
@AbstractDBUnitTest.FlatXMLDataSet(locations = { "daoTesting/OrderDataset.xml" })
public class OrderDaoImplTest extends AbstractDBUnitTest {
	@Resource(name = "orderDao")
	private OrderDao orderDao;
	
	private List<Integer> createList()
	{
		List<Integer> orderIds = new ArrayList<Integer>();
		orderIds.add(27509843);
		orderIds.add(27509845);
		orderIds.add(27509903);
		orderIds.add(27509905);
		
		return orderIds;
		
	}
	
	@Test
	public void finalizeOrderBatchTest() throws Exception
	{
		List<Integer> orderIds = createList();
		orderDao.finalizeOrderBatch(orderIds, 5);
		List<Order> orders = orderDao.getOrdersByOrderId(orderIds);
		for(Order order:orders){
			Assert.assertEquals(order.getOrderFinalizedBy().intValue(), 5);
		}
	}

	
	@Test
	public void unfinalizeOrderBatchTest() throws Exception
	{
		List<Integer> orderIds = createList();
		orderDao.unfinalizeOrderBatch(orderIds, 5);
		List<Order> orders = orderDao.getOrdersByOrderId(orderIds);
		for(Order order:orders){
			Assert.assertEquals(order.getOrderUnfinalizedBy().intValue(), 5);
		}
	}

	@Test
	public void unpublishOrderByBatchTest() throws Exception
	{
		List<Integer> orderIds = createList();
		orderDao.unpublishOrderByBatch(orderIds, 5);
		List<Order> orders = orderDao.getOrdersByOrderId(orderIds);
		for(Order order:orders){
			Assert.assertEquals(order.getOrderPublishedBy(), null);
			Assert.assertEquals(order.getOrderUnfinalizedBy().intValue(), 5);
		}
	}
	
	@Test
	public void publishOrderBatchTest() throws Exception
	{
		List<Integer> orderIds = createList();
		orderDao.publishOrderBatch(orderIds, 5);
		List<Order> orders = orderDao.getOrdersByOrderId(orderIds);
		for(Order order:orders){
			Assert.assertEquals(order.getOrderPublishedBy().intValue(), 5);
		}
	}
	
	@Test
	public void updateSaveOrderBatchTest() throws Exception
	{
		List<Integer> orderIds = createList();
		orderDao.updateSaveOrderBatch(orderIds, 5);
		List<Order> orders = orderDao.getOrdersByOrderId(orderIds);
		for(Order order:orders){
			Assert.assertEquals(order.getOrderSavedBy().intValue(), 5);
		}
	}

	
	
	@Test
	public void updatePublishOrderBatchTest() throws Exception
	{
		List<Integer> orderIds = createList();
		orderDao.updatePublishOrderBatch(orderIds, 5);
		List<Order> orders = orderDao.getOrdersByOrderId(orderIds);
		for(Order order:orders){
			Assert.assertEquals(order.getOrderPublishedBy().intValue(), 5);
		}
	}

	@Test
	public void publishAllocationByBatchTest() throws Exception
	{
		List<Integer> orderIds =Arrays.asList(27509843,27509903);
		orderDao.publishAllocationByBatch(orderIds,5);
		
		List<Order> orders = orderDao.getOrdersByOrderId(orderIds);
		for(Order order:orders){
			Assert.assertEquals(order.getAllocationPublishedBy().intValue(), 5);
		}
		
	}
	
	
	@Test
	public void unpublishAllocationByBatchTest() throws Exception
	{
		List<Integer> orderIds =Arrays.asList(27509843,27509903);
		orderDao.publishAllocationByBatch(orderIds,5);

		List<Order> orders = orderDao.getOrdersByOrderId(orderIds);
		for(Order order:orders){
			Assert.assertEquals(order.getAllocationPublishedBy().intValue(), 5);
		}
		
		orderDao.unpublishAllocationByBatch(orderIds,5);
		
		orders = orderDao.getOrdersByOrderId(orderIds);
		for(Order order:orders){
			Assert.assertEquals(order.getAllocationPublishedBy(),null);
		}
		
	}
	
	@Test
	public void unfinalizeAllocationByBatchTest() throws Exception
	{
		List<Integer> orderIds =Arrays.asList(27509843,27509903);


		orderDao.unfinalizeAllocationByBatch(orderIds,5);
		
		List<Order> orders = orderDao.getOrdersByOrderId(orderIds);
		for(Order order:orders){
			Assert.assertEquals(order.getAllocationFinalizedBy(),null);
			Assert.assertEquals(order.getAllocationUnfinalizedBy().intValue(),5);
		}
		
	}
	
	@Test
	public void updateOrderByOrderIdsTest() throws ServiceException{
		List<Integer> orderIds =Arrays.asList(27509843,27509903);


		orderDao.updateOrderByOrderIds(5,orderIds);
		
		List<Order> orders = orderDao.getOrdersByOrderId(orderIds);
		for(Order order:orders){
			Assert.assertEquals(order.getOrderSavedBy().intValue(),5);
			
		}
		
	}
	

	@Test
	public void updateSaveAllocationBatchTest() throws Exception
	{
		List<Integer> orderIds =Arrays.asList(27509843,27509903);
		orderDao.updateSaveAllocationBatch(5,orderIds);
		
		List<Order> orders = orderDao.getOrdersByOrderId(orderIds);
		for(Order order:orders){
			Assert.assertEquals(order.getAllocationSavedBy().intValue(), 5);
		}
		
	}
	

}
