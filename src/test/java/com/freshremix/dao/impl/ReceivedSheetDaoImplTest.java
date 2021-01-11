package com.freshremix.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractDBUnitTest;
import com.freshremix.dao.ReceivedSheetDao;
import com.freshremix.model.OrderReceived;

@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml" })
@AbstractDBUnitTest.FlatXMLDataSet(locations = { //
		"daoTesting/SKUGroupDataset.xml", //
		"daoTesting/SellingUOMDataset.xml", //
		"daoTesting/SKUDataset.xml", //
		"daoTesting/SKUBADataset.xml", //
		"daoTesting/OrderDataset.xml", //
		"daoTesting/OrderReceivedDataset.xml" //
})
public class ReceivedSheetDaoImplTest   extends AbstractDBUnitTest{
	@Resource(name="receivedSheetDao")
	private ReceivedSheetDao receivedSheetDao;
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
	public void deleteAllocItemsBatchTest() throws Exception
	{
		List<OrderReceived> orders = receivedSheetDao.getReceivedItemsBulk(createList(), Arrays.asList(1320166,1320167), false);
		Assert.assertEquals(orders.size(),4);
		receivedSheetDao.deleteReceivedItemByBatch(createList());
		orders = receivedSheetDao.getReceivedItemsBulk(createList(), Arrays.asList(1320166,1320167), false);
		Assert.assertEquals(orders.size(),0);
		
	}


}
