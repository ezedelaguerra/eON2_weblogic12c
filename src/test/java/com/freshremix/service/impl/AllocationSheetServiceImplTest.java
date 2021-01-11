package com.freshremix.service.impl;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.freshremix.common.test.UserTestUtil;
import com.freshremix.dao.AllocationDao;
import com.freshremix.dao.OrderBillingDao;
import com.freshremix.dao.OrderDao;
import com.freshremix.dao.ReceivedSheetDao;
import com.freshremix.dao.UserDao;
import com.freshremix.model.Order;
import com.freshremix.model.User;

public class AllocationSheetServiceImplTest {

	private AllocationSheetServiceImpl allocationSheetService  = new AllocationSheetServiceImpl();
	
	private UserDao usersInfoDaosMock = Mockito.mock(UserDao.class);
	private AllocationDao allocationDaoMock = Mockito.mock(AllocationDao.class);
	private ReceivedSheetDao receivedSheetDaoMock = Mockito.mock(ReceivedSheetDao.class);
	private OrderDao orderDaoMock = Mockito.mock(OrderDao.class);
	private OrderBillingDao orderBillingDaoMock= Mockito.mock(OrderBillingDao.class);
	
	@BeforeClass
	public void beforeClass() {
		allocationSheetService.setUsersInfoDaos(usersInfoDaosMock);
		allocationSheetService.setAllocationDao(allocationDaoMock);
		allocationSheetService.setReceivedSheetDao(receivedSheetDaoMock);
		allocationSheetService.setOrderDao(orderDaoMock);
		allocationSheetService.setOrderBillingDao(orderBillingDaoMock);
	}

	@BeforeMethod
	public void tearDown() {
		Mockito.reset(usersInfoDaosMock);
		Mockito.reset(allocationDaoMock);
		Mockito.reset(receivedSheetDaoMock);
		Mockito.reset(orderDaoMock);
		Mockito.reset(orderBillingDaoMock);
	}

	@Test
	public void updatePublishOrderTest() throws Exception{
		User user = UserTestUtil.createSellerAdminUser("sa_ca01", 1);
		Order order = new Order();
		order.setBuyerId(3);
		order.setOrderId(24);
		when(usersInfoDaosMock.getUserById(anyInt())).thenReturn(UserTestUtil.createBuyerUser("b_ca01", 3));
		allocationSheetService.updatePublishOrder(user, Arrays.asList(order));
		verify(orderDaoMock).publishAllocationByBatch(anyList(),anyInt());	
		verify(receivedSheetDaoMock).bulkInsertReceivedItemFromAllocation(anyList(),anyInt());				
		verify(usersInfoDaosMock).getUserById(anyInt());
	}

	
	
	
	@Test
	public void updateUnpublishOrderTest() throws Exception{
		User user = UserTestUtil.createSellerAdminUser("sa_ca01", 1);
		Order order = new Order();
		order.setBuyerId(3);
		order.setOrderId(24);
		when(usersInfoDaosMock.getUserById(anyInt())).thenReturn(UserTestUtil.createBuyerUser("b_ca01", 3));
		allocationSheetService.updateUnpublishOrder( Arrays.asList(order),user);
		verify(orderDaoMock).unpublishAllocationByBatch(anyList(),anyInt());	
		verify(receivedSheetDaoMock).deleteReceivedItemByBatch(anyList());				
		verify(usersInfoDaosMock).getUserById(anyInt());
	}
	
	
	
	@Test
	public void updateUnfinalizeOrderTest() throws Exception{
		User user = UserTestUtil.createSellerAdminUser("sa_ca01", 1);
		Order order = new Order();
		order.setBuyerId(3);
		order.setOrderId(24);
		when(usersInfoDaosMock.getUserById(anyInt())).thenReturn(UserTestUtil.createBuyerUser("b_ca01", 3));
		allocationSheetService.updateUnfinalizeOrder( Arrays.asList(order),user);
		verify(orderDaoMock).unfinalizeAllocationByBatch(anyList(),anyInt());	
		verify(orderBillingDaoMock).deleteBillingItemsByBatch(anyList());				
		verify(usersInfoDaosMock).getUserById(anyInt());
	}


}
