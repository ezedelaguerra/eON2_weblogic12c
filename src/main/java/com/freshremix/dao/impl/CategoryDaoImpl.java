/**
 * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
 * <BR>
 * <B>Description:</B><BR>
 * Describe class or interface.<BR>
 * <BR>
 * <B>Known Bugs:</B>
 * none<BR>
 * <BR>
 * <B>History:</B>
 * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
 * date		name		version		changes
 * ------------------------------------------------------------------------------		
 * 20120724	gilwen		v11			Redmine 797 - SellerAdmin can select seller who don't have category
 */

package com.freshremix.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.CategoryDao;
import com.freshremix.model.Category;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;

public class CategoryDaoImpl extends SqlMapClientDaoSupport implements CategoryDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<UsersCategory> getCategoryListByUserId(Map<String, String> param) {
		return getSqlMapClientTemplate().queryForList("UserCategory.getCategoryListByUserId", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAllCategory() {
		return getSqlMapClientTemplate().queryForList("Category.getAllCategory");
	}
	
	@Override
	public void deleteUserCategory(String userId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		getSqlMapClientTemplate().delete("UserCategory.deleteCategoryById", map);
	}

	@Override
	public void insertUserCategory(Integer categoryId, Integer userId, Integer sordId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userId", userId);
		map.put("categoryId", categoryId);
		map.put("sortId", sordId);
		getSqlMapClientTemplate().insert("UserCategory.insertUserCategory", map);
		
	}

	@Override
	public void saveAdminCategoryDao(String categoryName, String imageName) {
		getSqlMapClientTemplate().insert("Category.saveAdminCategoryDao", categoryName);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsersCategory> getCategoryListBySelectedIds(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("UserCategory.getCategoryListBySelectedIds", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoryById(List<Integer> categoryIds) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("categoryId", categoryIds);
		return getSqlMapClientTemplate().queryForList("Category.getCategoryById", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoryByName(Integer userId, List<String> categoryName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("categoryName", categoryName);
		param.put("userId", userId);
		return getSqlMapClientTemplate().queryForList("Category.getCategoryByName", param);
	}
	// ENHANCEMENT START 20120724: Lele - Redmine 797
	@SuppressWarnings("unchecked")
	@Override
	public List<User> filterSellerIdByCategory (List<Integer> sellerId, Integer categoryId) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sellerId", sellerId);
		param.put("categoryId", categoryId);
		return getSqlMapClientTemplate().queryForList("Category.filterSellerIdByCategory", param);
	}
	// ENHANCEMENT END 20120724:
}
