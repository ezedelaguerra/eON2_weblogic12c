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

package com.freshremix.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.freshremix.constants.RoleConstants;
import com.freshremix.dao.CategoryDao;
import com.freshremix.model.Category;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.service.CategoryService;
import com.freshremix.util.OrderSheetUtil;

public class CategoryServiceImp implements CategoryService {

	private CategoryDao categoryDao;

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Override
	// FOR SELLER
	public List<UsersCategory> getCategoryListByUserId(String userId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId);

		List<UsersCategory> list = 
			categoryDao.getCategoryListByUserId(param);
		
//		if (list == null || list.size() == 0) {
//			list = 
//				this.getDefaultUserCategories(Integer.parseInt(userId));
//		}
			
		return list;
	}

	@Override
	public List<Category> getAllCategory() {
		
		return categoryDao.getAllCategory();
	}
	
	@Override
	public void deleteUserCategory(String userId) {
		categoryDao.deleteUserCategory(userId);
	}

	@Override
	public void insertUserCategory(Integer categoryId, Integer userId, Integer sortId) {
		categoryDao.insertUserCategory(categoryId, userId, sortId);
	}

	@Override
	public void saveAdminCategoryDao(String categoryName, String imageName) {
		categoryDao.saveAdminCategoryDao(categoryName, imageName);
	}
	
	//start:jr
	@Override
	public List<UsersCategory> getCategoryListBySelectedIds(List<Integer> userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("listUserIds", userId);

		return categoryDao.getCategoryListBySelectedIds(param);
	}

	@Override
	public List<Category> getCategoryById(List<Integer> categoryIds) {
		return categoryDao.getCategoryById(categoryIds);
	}
	@Override
	public List<Category> getCategoryByName(Integer userId, List<String> categoryNames) {
		if (CollectionUtils.isEmpty(categoryNames)) {
			return Collections.emptyList();
		}
		return categoryDao.getCategoryByName(userId, categoryNames);
	}
	@Override
	public Category getCategoryByName(Integer userId, String categoryName) {
		
		if (StringUtils.isBlank(categoryName)) {
			return null;
		}
		List<Category> list = getCategoryByName(userId, Arrays.asList(categoryName));
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	@Override
	public Category getCategory(Integer userId, String categoryName) {
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId.toString());
		List<UsersCategory> categoryList = categoryDao.getCategoryListByUserId(param);
		Category cat =new Category();
		for(UsersCategory usersCategory : categoryList){
			if(usersCategory.getCategoryAvailable().equals(categoryName)){
				cat.setCategoryId(usersCategory.getCategoryId());
				cat.setDescription(usersCategory.getCategoryAvailable());	
			}
		}
		
		return cat;
	}

	@Override
	public List<UsersCategory> getDefaultUserCategories(Integer userId) {
		
		List<UsersCategory> list = new ArrayList<UsersCategory>();
		
		for (Category uc : categoryDao.getAllCategory()) {
			list.add(new UsersCategory(uc.getCategoryId(), userId, uc.getDescription()));
		}
		
		return list;
	}

	@Override
	public Map<String, Category> getCategoryMasterList() {
		
		List<Category> list = this.getAllCategory();
		Map<String,Category> map = new HashMap<String,Category>();
		
		for (Category cat : list) {
			map.put(cat.getCategoryId().toString(), cat);
		}
		
		return map;
	}

	@Override
	// FOR BUYER, BUYER ADMIN, SELLER ADMIN
	public List<UsersCategory> getSortedCategory(Integer userId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId.toString());

		// sorted list
		List<UsersCategory> list = 
			categoryDao.getCategoryListByUserId(param);
		
		if (list == null || list.size() == 0) {
			list = 
				this.getDefaultUserCategories(userId);
		}
		
		// all list of category
		List<Category> allCat = this.getAllCategory();
		List<UsersCategory> toAdd = new ArrayList<UsersCategory>();
		List<UsersCategory> notInList = new ArrayList<UsersCategory>();
		
		if (allCat.size() > list.size()) {
			for (Category cat : allCat) {
				boolean add = false;
				for (UsersCategory uc : list) {
					if(cat.getCategoryId().equals(uc.getCategoryId())) {
						add = true;
						toAdd.add(uc);
					}
				}
				
				if (!add) 
					notInList.add(new UsersCategory(cat.getCategoryId(), userId, cat.getTabName()));
			}
			
			toAdd.addAll(notInList);
		} else {
			toAdd.addAll(list);
		}
		
		return toAdd;
	}
	
	@Override
	public List<UsersCategory> getCategoryList(User user,
			OrderSheetParam osParam) {
		List<UsersCategory> categoryList;
		if(user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)) {
			
			categoryList = user.getPreference().getSortedCategories();
			
		} else {
			
			List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
			List<UsersCategory> list = 
				this.getCategoryListBySelectedIds(sellerIds);
			categoryList = new ArrayList<UsersCategory>();
			
			// Sorting Category List
			if(user.getPreference().getSortedCategories().isEmpty()){
				categoryList = list;
			} else {
				// sort according to user preference (category sorting)
				for (UsersCategory uc : user.getPreference().getSortedCategories()) {
					for (UsersCategory _uc : list) {
						if (_uc.getCategoryId().equals(uc.getCategoryId())) {
							categoryList.add(uc);
							break;
						}
					}				
				}
				
				// add all new categories at the end of preference
				List<UsersCategory> tmp = new ArrayList<UsersCategory>();
				for (UsersCategory uc : list) { // update list from seller
					boolean found = false;
					for (UsersCategory _uc : categoryList) {
						if (uc.getCategoryId().equals(_uc.getCategoryId())) {
							found = true;
							break;
						}
					}
					if (!found) {
						tmp.add(uc);
					}
				}
				
				categoryList.addAll(tmp);
			}			
		}
		
		return categoryList;
	}
	
	// ENHANCEMENT START 20120724: Lele - Redmine 797
	@Override
	public List<User> filterSellerIdByCategory (List<Integer> sellerId, Integer categoryId) {
		
		return categoryDao.filterSellerIdByCategory(sellerId, categoryId);
	}
	// ENHANCEMENT END 20120724:
}
