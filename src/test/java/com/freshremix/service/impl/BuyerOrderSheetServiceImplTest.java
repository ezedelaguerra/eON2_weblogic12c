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
import com.freshremix.dao.OrderDao;
import com.freshremix.dao.OrderSheetDao;
import com.freshremix.dao.ReceivedSheetDao;
import com.freshremix.dao.UserDao;
import com.freshremix.model.Order;
import com.freshremix.model.User;
import com.freshremix.service.OrderSheetService;

public class BuyerOrderSheetServiceImplTest {
	private BuyerOrderSheetServiceImpl buyerOrderSheetServiceImpl = new BuyerOrderSheetServiceImpl();

	private OrderSheetService orderSheetServiceMock = Mockito.mock(OrderSheetService.class);
	private UserDao usersInfoDaosMock = Mockito.mock(UserDao.class);

	private OrderDao orderDaoMock = Mockito.mock(OrderDao.class);

	@BeforeClass
	public void beforeClass() {
		buyerOrderSheetServiceImpl.setOrderSheetService(orderSheetServiceMock);
		buyerOrderSheetServiceImpl.setOrderDao(orderDaoMock);
		buyerOrderSheetServiceImpl.setUsersInfoDaos(usersInfoDaosMock);
	}

	@BeforeMethod
	public void beforeMethod() {
		Mockito.reset(orderSheetServiceMock);
		Mockito.reset(orderDaoMock);
	}
	
	@Test
	public void testUpdatePublishOrderByBA() throws Exception{
		User user = UserTestUtil.createSellerAdminUser("sa_ca01", 1);
		Order order = new Order();
		order.setBuyerId(3);
		order.setOrderId(24);
		when(usersInfoDaosMock.getUserById(anyInt())).thenReturn(UserTestUtil.createBuyerUser("b_ca01", 3));
		buyerOrderSheetServiceImpl.updatePublishOrderByBA(user, Arrays.asList(order));
		verify(orderDaoMock).updatePublishOrderBatch(anyList(),anyInt());		
		verify(usersInfoDaosMock).getUserById(anyInt());
	}


}
