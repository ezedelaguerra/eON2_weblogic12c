package com.freshremix.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractDBUnitTest;
import com.freshremix.dao.SKUBADao;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.SKUBA;

@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml" })
@AbstractDBUnitTest.FlatXMLDataSet(locations = { //
"daoTesting/SellingUOMDataset.xml", //
		//"daoTesting/SheetTypeDataset.xml", //
		//"daoTesting/CategoryDataset.xml", //
		//"daoTesting/UsersDataset.xml", //
		"daoTesting/SKUGroupDataset.xml", //
		//"daoTesting/OrderUnitDataset.xml", //
		"daoTesting/SKUDataset.xml", //
		"daoTesting/SKUBADataset.xml" //
})
public class SKUBADaoImplTest extends AbstractDBUnitTest {

	@Resource(name = "skuBADao")
	private SKUBADao skuBADao;

	@Test
	public void testFindSKUBA() {
		Integer skuBAId = 105687;
		SKUBA result = skuBADao.findSKUBA(skuBAId);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getSkuBAId(), skuBAId);
	}

	@Test
	public void testFindSKUBANullParam() {
		Integer skuBAId = null;
		SKUBA result = skuBADao.findSKUBA(skuBAId);
		Assert.assertNull(result);
	}

	@Test
	public void testFindSKUBAList() {
		List<Integer> skuBAIds = Arrays.asList(105687, 105686);
		List<SKUBA> resultList = skuBADao.findSKUBA(skuBAIds);
		Assert.assertTrue(CollectionUtils.isNotEmpty(resultList));
		Assert.assertEquals(resultList.size(), 2);
	}

	@DataProvider(name = "findSKUBAEmptyList")
	public Object[][] findSKUBAEmptyList() {
		return new Object[][] { { null }, //
				{ Collections.EMPTY_LIST },//
				{ new ArrayList<Integer>() } };
	}

	@Test(dataProvider = "findSKUBAEmptyList")
	public void testFindSKUBAMNullEmptyList(List<Integer> skuBAIds) {
		List<SKUBA> resultList = skuBADao.findSKUBA(skuBAIds);
		Assert.assertNull(resultList);
	}

	@Test
	public void testFindSKUBABatchFetch() {
		List<Integer> skuBAIds = new ArrayList<Integer>(501);
		for (int i = 1; i <= 499; i++) {
			skuBAIds.add(i);
		}
		skuBAIds.add(105687);
		skuBAIds.add(105686);

		List<SKUBA> resultList = skuBADao.findSKUBA(skuBAIds);
		Assert.assertTrue(CollectionUtils.isNotEmpty(resultList));
		Assert.assertEquals(resultList.size(), 2);
	}

	@Test
	public void testInsertSKUBA() {
		SKUBA skuBA = new SKUBA();
		skuBA.setSkuId(1320166);
		skuBA.setPurchasePrice(new BigDecimal(123));
		skuBA.setSellingPrice(new BigDecimal(456));
		OrderUnit sellingUom = new OrderUnit();
		sellingUom.setOrderUnitId(1);
		skuBA.setSellingUom(sellingUom);
		skuBA.setSkuComment("BSKUComment");
		Integer pk = skuBADao.insertSKUBA(skuBA);

		SKUBA resultSKUBA = skuBADao.findSKUBA(pk);
		Assert.assertNotNull(resultSKUBA);
		Assert.assertEquals(resultSKUBA.getSkuBAId(), pk);

	}

	@Test
	public void testInsertSKUBAZeroSellingUOM() {
		SKUBA skuBA = new SKUBA();
		skuBA.setSkuId(1320166);
		skuBA.setPurchasePrice(new BigDecimal(123));
		skuBA.setSellingPrice(new BigDecimal(456));
		OrderUnit sellingUom = new OrderUnit();
		sellingUom.setOrderUnitId(0);
		skuBA.setSellingUom(sellingUom);
		skuBA.setSkuComment("BSKUComment");
		Integer pk = skuBADao.insertSKUBA(skuBA);

		SKUBA resultSKUBA = skuBADao.findSKUBA(pk);
		Assert.assertNotNull(resultSKUBA);
		Assert.assertEquals(resultSKUBA.getSkuBAId(), pk);

	}

	@Test
	public void testInsertSKUBANull() {
		SKUBA skuBA = null;
		Integer pk = skuBADao.insertSKUBA(skuBA);
		Assert.assertNull(pk);
	}

}
