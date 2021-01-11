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

package com.freshremix.dao;

import java.util.List;
import java.util.Map;

import com.freshremix.model.Category;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;

public interface CategoryDao {
	List<UsersCategory> getCategoryListByUserId(Map<String, String> param);
	List<Category> getAllCategory();
	void insertUserCategory(Integer categoryId, Integer userId, Integer sortId);
	void deleteUserCategory(String userId);
	void saveAdminCategoryDao(String categoryName, String imageName);
	List<UsersCategory> getCategoryListBySelectedIds(Map<String, Object> param);
	List<Category> getCategoryById(List<Integer> categoryIds);
	// ENHANCEMENT 20120724: Lele - Redmine 797
	List<User> filterSellerIdByCategory (List<Integer> sellerId, Integer categoryId);
	List<Category> getCategoryByName(Integer userId, List<String> categoryName);
}
