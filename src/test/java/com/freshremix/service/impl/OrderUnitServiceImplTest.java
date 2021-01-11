package com.freshremix.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.freshremix.dao.OrderUnitDao;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderUnit;
import com.freshremix.service.AllocationSheetService;
import com.freshremix.service.OrderSheetService;

public class OrderUnitServiceImplTest {
	private OrderUnitServiceImpl orderUnitService;
	private OrderSheetService orderSheetServiceMock;
	private AllocationSheetService allocationSheetServiceMock;

 
	private OrderUnitDao orderUnitDaoMock;
	
	@BeforeMethod
	public void init(){
		orderUnitDaoMock = mock(OrderUnitDao.class);
		orderUnitService = new OrderUnitServiceImpl();
		orderUnitService.setOrderUnitDao(orderUnitDaoMock);
		orderSheetServiceMock = mock(OrderSheetService.class);
		orderUnitService.setOrderSheetService(orderSheetServiceMock);
		allocationSheetServiceMock= mock(AllocationSheetService.class);
		orderUnitService.setAllocationSheetService(allocationSheetServiceMock);
	}
	
	@AfterMethod
	public void tearDown() {
		reset(orderUnitDaoMock);
	}
	
	@Test
	public void getSellingUomHappyFlow(){
		List<OrderUnit> list = createUOMList();
		when(orderUnitDaoMock.getSellingUomList(anyInt())).thenReturn(list);
		OrderUnit sellingUom = orderUnitService.getSellingUom(1, "C/S");
		Assert.assertNotNull(sellingUom);
		Assert.assertEquals(sellingUom.getOrderUnitName(), "C/S");
		
	}
	
	@Test
	public void getSellingUomReturnNull(){
		List<OrderUnit> list = createUOMList();
		when(orderUnitDaoMock.getSellingUomList(anyInt())).thenReturn(list);
		OrderUnit sellingUom = orderUnitService.getSellingUom(1, "ピース");
		Assert.assertNull(sellingUom);
		
	}
	
	
	@Test
	public void getOrderUnitByIdHappyFlow(){
		List<OrderUnit> list = createUOMList();
		when(orderUnitDaoMock.getAllOrderUnit()).thenReturn(list);
		OrderUnit uom = orderUnitService.getOrderUnitById(1);
		Assert.assertNotNull(uom);
		Assert.assertEquals(uom.getOrderUnitId(), new Integer(1));
		
	}
	
	@Test
	public void getOrderUnitByIdReturnNull(){
		List<OrderUnit> list = createUOMList();
		when(orderUnitDaoMock.getAllOrderUnit()).thenReturn(list);
		OrderUnit uom = orderUnitService.getOrderUnitById(3);
		Assert.assertNull(uom);
		
	}
	 
	
	private List<OrderUnit> createUOMList() {
		OrderUnit oum = new OrderUnit();
		oum.setOrderUnitId(1);
		oum.setOrderUnitName("C/S");
		OrderUnit oum2 = new OrderUnit();
		oum2.setOrderUnitName("KG");
		oum2.setOrderUnitId(2);


		List<OrderUnit> list = Arrays.asList(oum, oum2);
		return list;
	}
	
	@Test
	public void testValidQtyForDecimal(){
		Order o = new Order();
		o.setBuyerId(2);
		OrderItem oi = new OrderItem();
		oi.setQuantity(new BigDecimal(12));
		oi.setOrder(o);
		when(orderSheetServiceMock.getOrderItem(anyInt(), anyString(), anyInt())).thenReturn(Arrays.asList(oi));
		when(allocationSheetServiceMock.getOrderItem(anyInt(), anyString(), anyInt())).thenReturn(Arrays.asList(oi));
		
		boolean isValid = orderUnitService.validateQtyForDecimal("1", "1", "", Arrays.asList(1), "C/S", "order");
		Assert.assertTrue(isValid);
		verify(orderSheetServiceMock).getOrderItem(anyInt(), anyString(), anyInt());
		verify(allocationSheetServiceMock, never()).getOrderItem(anyInt(), anyString(), anyInt());

		isValid = orderUnitService.validateQtyForDecimal("1", "1", "", Arrays.asList(1), "C/S", "allocation");
		Assert.assertTrue(isValid);
		verify(allocationSheetServiceMock).getOrderItem(anyInt(), anyString(), anyInt());
	}
	
	@Test
	public void testInvalidQtyForDecimal(){
		Order o = new Order();
		o.setBuyerId(2);
		OrderItem oi = new OrderItem();
		oi.setQuantity(new BigDecimal(12.32));
		oi.setOrder(o);
		when(orderSheetServiceMock.getOrderItem(anyInt(), anyString(), anyInt())).thenReturn(Arrays.asList(oi));
		when(allocationSheetServiceMock.getOrderItem(anyInt(), anyString(), anyInt())).thenReturn(Arrays.asList(oi));
		
		boolean isValid = orderUnitService.validateQtyForDecimal("1", "1", "", Arrays.asList(1), "C/S", "order");
		Assert.assertFalse(isValid);
		verify(orderSheetServiceMock).getOrderItem(anyInt(), anyString(), anyInt());
		verify(allocationSheetServiceMock, never()).getOrderItem(anyInt(), anyString(), anyInt());

		isValid = orderUnitService.validateQtyForDecimal("1", "1", "", Arrays.asList(1), "C/S", "allocation");
		Assert.assertFalse(isValid);
		verify(allocationSheetServiceMock).getOrderItem(anyInt(), anyString(), anyInt());
	}
	
}
