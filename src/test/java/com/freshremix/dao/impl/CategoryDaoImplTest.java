package com.freshremix.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractDBUnitTest;
import com.freshremix.dao.CategoryDao;
import com.freshremix.model.Category;

@Transactional
@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml" })
@AbstractDBUnitTest.FlatXMLDataSet(locations = { //
//"daoTesting/UsersDataset.xml",//
//		"daoTesting/CategoryDataset.xml",//
		"daoTesting/UsersCategoryDataset.xml" })
public class CategoryDaoImplTest extends AbstractDBUnitTest {
	
	@Resource(name="categoryDaos")
	private CategoryDao categoryDao;
	
	
	@Test
	public void  getCategoryByNameTestWithData()
	{
		List<String> categoryName = new ArrayList<String>();
		categoryName.add("Fruits");
		List<Category> result = categoryDao.getCategoryByName(17, categoryName);
		Assert.assertEquals(result.size(), 1);
		Assert.assertEquals(result.get(0).getDescription(),"Fruits");
		
		
	
	}
	
	@Test
	public void  getCategoryByNameTestWithoutData()
	{
		List<String> categoryName = new ArrayList<String>();
		categoryName.add("Fruits");
		categoryName.add("Meat");
		List<Category> result = categoryDao.getCategoryByName(16, categoryName);
		Assert.assertEquals(result.size(), 0);
	
	}
	
	@Test
	public void  getCategoryByNameTestWithMultipleRows()
	{
		List<String> categoryName = new ArrayList<String>();
		categoryName.add("Fruits");
		categoryName.add("Meat");
		List<Category> result = categoryDao.getCategoryByName(17, categoryName);
		Assert.assertEquals(result.size(), 2);
		Assert.assertEquals(result.get(0).getDescription(),"Fruits");
		Assert.assertEquals(result.get(1).getDescription(),"Meat");
		
		
	
	}
	
	
	
}
