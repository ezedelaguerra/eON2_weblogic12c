package com.freshremix.dao.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractDBUnitTest;
import com.freshremix.dao.AllocationDao;
import com.freshremix.model.OrderItem;

@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml" })
@AbstractDBUnitTest.FlatXMLDataSet(locations = { //
		"daoTesting/SKUGroupDataset.xml", //
		"daoTesting/SellingUOMDataset.xml", //
		"daoTesting/SKUDataset.xml", //
		"daoTesting/SKUBADataset.xml", //
		"daoTesting/OrderDataset.xml", //
		"daoTesting/OrderAllocationDataset.xml" //
})
public class AllocationDaoImplTest   extends AbstractDBUnitTest{
	@Resource(name="allocationDao")
	private AllocationDao allocationDao;
	
	
	
	
	
	@Test
	public void deleteAllocItemsBatchTest() throws Exception
	{
		List<Integer> orderIds =Arrays.asList(27509843);
		List<OrderItem> orders = allocationDao.getAllocItemsByOrderId(new Integer("27509843"));
		Assert.assertEquals(orders.size(),2);
		allocationDao.deleteAllocItemsBatch(orderIds);
		orders = allocationDao.getAllocItemsByOrderId(new Integer("27509843"));
		Assert.assertEquals(orders.size(),0);
		
	}


}
