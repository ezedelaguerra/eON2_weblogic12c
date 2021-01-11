package com.freshremix.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractDBUnitTest;
import com.freshremix.dao.OrderSheetDao;
import com.freshremix.model.OrderItem;

@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml" })
@AbstractDBUnitTest.FlatXMLDataSet(locations = { //
		//"daoTesting/UsersDataset.xml", //
		//"daoTesting/SheetTypeDataset.xml", //
		//"daoTesting/CategoryDataset.xml", //
		//"daoTesting/OrderUnitDataset.xml", //
		"daoTesting/SKUGroupDataset.xml", //
		"daoTesting/SellingUOMDataset.xml", //
		"daoTesting/SKUDataset.xml", //
		"daoTesting/SKUBADataset.xml", //
		"daoTesting/OrderDataset.xml", //
		"daoTesting/OrderItemDataset.xml" //
})
public class OrderSheetDaoImplTest  extends AbstractDBUnitTest {

	@Resource(name = "orderSheetDao")
	private OrderSheetDao orderSheetDao;
	
	
	@Test
	public void testGetOrderItemsByOrderIdBulk(){
		List<Integer> orderIds = Arrays.asList(27509843, 27509845, 27509903, 27509905);
		List<OrderItem> resultList = orderSheetDao.getOrderItemsByOrderIdBulk(orderIds);
		Assert.assertEquals(resultList.size(), 4);
	}

	@DataProvider(name = "emptyOrderIdList")
	public Object[][] emptyOrderIdList() {
		return new Object[][] { { null }, //
				{ Collections.EMPTY_LIST },//
				{ new ArrayList<Integer>() } };
	}

	@Test(dataProvider="emptyOrderIdList")
	public void testGetOrderItemsByOrderIdBulkEmptyList(List<Integer> orderIds){
		List<OrderItem> resultList = orderSheetDao.getOrderItemsByOrderIdBulk(orderIds);
		Assert.assertTrue(CollectionUtils.isEmpty(resultList));
	}

	@Test
	public void testGetOrderItemsByOrderIdBulkNonExisting(){
		List<OrderItem> resultList = orderSheetDao.getOrderItemsByOrderIdBulk(Arrays.asList(1, 2, 3));
		Assert.assertTrue(CollectionUtils.isEmpty(resultList));
	}
}
