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

package com.freshremix.service;

import java.util.List;
import java.util.Map;

import com.freshremix.model.Category;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;

public interface CategoryService {
	List<UsersCategory> getCategoryListByUserId(String userId);
	List<Category> getAllCategory();
	void insertUserCategory(Integer categoryId, Integer userId, Integer sortId);
	void deleteUserCategory(String userId);
	void saveAdminCategoryDao(String categoryName, String imageName);
	List<UsersCategory> getCategoryListBySelectedIds(List<Integer> userId);
	List<Category> getCategoryById(List<Integer> categoryIds);
	Category getCategory(Integer userId,String categoryName);
	List<UsersCategory> getDefaultUserCategories(Integer userId);
	Map<String,Category> getCategoryMasterList ();
	List<UsersCategory> getSortedCategory(Integer userId);
	List<UsersCategory> getCategoryList(User user, OrderSheetParam osParam);
	// ENHANCEMENT 20120724: Lele - Redmine 797
	List<User> filterSellerIdByCategory (List<Integer> sellerId, Integer categoryId);
	List<Category> getCategoryByName(Integer userId, List<String> categoryNames);
	Category getCategoryByName(Integer userId, String categoryName);
}
