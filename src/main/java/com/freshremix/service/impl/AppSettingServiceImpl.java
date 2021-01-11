package com.freshremix.service.impl;

import static com.freshremix.constants.SystemConstants.CATEGORY_MASTER_LIST;
import static com.freshremix.constants.SystemConstants.EON_HEADER_NAMES;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.freshremix.dao.SKUSortDao;
import com.freshremix.model.Category;
import com.freshremix.model.EONHeader;
import com.freshremix.model.SKUColumn;
import com.freshremix.service.AppSettingService;
import com.freshremix.service.CategoryService;

public class AppSettingServiceImpl implements AppSettingService, 
		ServletContextAware, InitializingBean {

	private ServletContext servletContext;
	private SKUSortDao skuSortDao;
	private CategoryService categoryService;
	
	public void setSkuSortDao(SKUSortDao skuSortDao) {
		this.skuSortDao = skuSortDao;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		List<SKUColumn> skuColList = skuSortDao.getAllColumns();
		Map<String,Category> catList = categoryService.getCategoryMasterList();
		
		this.servletContext.setAttribute(EON_HEADER_NAMES, new EONHeader(skuColList));
		this.servletContext.setAttribute(CATEGORY_MASTER_LIST, catList);
	}

	@Override
	public EONHeader getEONHeader() {
		return (EONHeader) servletContext.getAttribute(EON_HEADER_NAMES);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Category> getCategoryMasterList() {
		return (Map<String, Category>) servletContext.getAttribute(CATEGORY_MASTER_LIST);
	}

}
