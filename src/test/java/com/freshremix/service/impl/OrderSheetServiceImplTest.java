package com.freshremix.service.impl;


import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.common.test.UserTestUtil;
import com.freshremix.dao.AllocationDao;
import com.freshremix.dao.OrderDao;
import com.freshremix.dao.OrderSheetDao;
import com.freshremix.dao.ReceivedSheetDao;
import com.freshremix.dao.UserDao;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.User;

public class OrderSheetServiceImplTest {

	private OrderSheetServiceImpl orderSheetServiceImpl = new OrderSheetServiceImpl();

	private OrderSheetDao orderSheetDaoMock = Mockito.mock(OrderSheetDao.class);
	private UserDao usersInfoDaosMock = Mockito.mock(UserDao.class);
	private AllocationDao allocationDaoMock = Mockito.mock(AllocationDao.class);
	private ReceivedSheetDao receivedSheetDaoMock = Mockito.mock(ReceivedSheetDao.class);
	private OrderDao orderDaoMock = Mockito.mock(OrderDao.class);

	@BeforeClass
	public void beforeClass() {
		orderSheetServiceImpl.setOrderSheetDao(orderSheetDaoMock);
		orderSheetServiceImpl.setUsersInfoDaos(usersInfoDaosMock);
		orderSheetServiceImpl.setAllocationDao(allocationDaoMock);
		orderSheetServiceImpl.setReceivedSheetDao(receivedSheetDaoMock);
		orderSheetServiceImpl.setOrderDao(orderDaoMock);
	}

	@BeforeMethod
	public void beforeMethod() {
		Mockito.reset(orderSheetDaoMock);
		Mockito.reset(usersInfoDaosMock);
	}

	@Test
	public void testGetOrderItemsByOrderIdBulk() {
		List<Integer> orderIds = Arrays.asList(1);

		OrderItem orderItem = new OrderItem();
		List<OrderItem> value = Arrays.asList(orderItem);

		Mockito.when(orderSheetDaoMock.getOrderItemsByOrderIdBulk(orderIds))
				.thenReturn(value);

		List<OrderItem> resultList = orderSheetServiceImpl
				.getOrderItemsByOrderIdBulk(orderIds);
		Mockito.verify(orderSheetDaoMock).getOrderItemsByOrderIdBulk(orderIds);
		Assert.assertTrue(CollectionUtils.isNotEmpty(resultList));
		Assert.assertEquals(resultList.get(0), orderItem);
	}

	@DataProvider(name = "emptyOrderIdList")
	public Object[][] emptyOrderIdList() {
		return new Object[][] { { null }, //
				{ Collections.EMPTY_LIST },//
				{ new ArrayList<Integer>() } };
	}

	@Test(dataProvider = "emptyOrderIdList", expectedExceptions = IllegalArgumentException.class)
	public void testGetOrderItemsByOrderIdBulkException(List<Integer> orderIdList) {
		List<OrderItem> resultList = orderSheetServiceImpl
				.getOrderItemsByOrderIdBulk(orderIdList);
		Mockito.verifyZeroInteractions(orderSheetDaoMock);
	}
	
	@Test
	public void testUpdateUnFinalizeOrder() throws Exception{
		User user = UserTestUtil.createSellerAdminUser("sa_ca01", 1);
		Order order = new Order();
		order.setBuyerId(3);
		order.setOrderId(24);
		when(usersInfoDaosMock.getUserById(anyInt())).thenReturn(UserTestUtil.createBuyerUser("b_ca01", 3));
		List<Order> unFinalizedOrder = orderSheetServiceImpl.updateUnFinalizeOrder(user, Arrays.asList(order));
		verify(orderDaoMock).unfinalizeOrderBatch(anyList(),anyInt());
		verify(allocationDaoMock).deleteAllocItemsBatch(anyList());
		verify(receivedSheetDaoMock).deleteReceivedItemByBatch(anyList());
		verify(usersInfoDaosMock).getUserById(anyInt());
	}

	@Test
	public void testUpdateUnpublishOrder() throws Exception{
		User user = UserTestUtil.createSellerAdminUser("sa_ca01", 1);
		Order order = new Order();
		order.setBuyerId(3);
		order.setOrderId(24);
		when(usersInfoDaosMock.getUserById(anyInt())).thenReturn(UserTestUtil.createBuyerUser("b_ca01", 3));
		orderSheetServiceImpl.updateUnpublishOrder(user, Arrays.asList(order));
		verify(orderDaoMock).unpublishOrderByBatch(anyList(),anyInt());		
		verify(usersInfoDaosMock).getUserById(anyInt());
	}
	
	
	@Test
	public void testUpdatePublishOrder() throws Exception{
		User user = UserTestUtil.createSellerAdminUser("sa_ca01", 1);
		Order order = new Order();
		order.setBuyerId(3);
		order.setOrderId(24);
		when(usersInfoDaosMock.getUserById(anyInt())).thenReturn(UserTestUtil.createBuyerUser("b_ca01", 3));
		orderSheetServiceImpl.updatePublishOrder(user, Arrays.asList(order));
		verify(orderDaoMock).publishOrderBatch(anyList(),anyInt());		
		verify(usersInfoDaosMock).getUserById(anyInt());
	}
	
	
}
