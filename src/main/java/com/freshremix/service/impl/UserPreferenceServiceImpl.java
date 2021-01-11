package com.freshremix.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.freshremix.constants.RoleConstants;
import com.freshremix.dao.CompanySellersSortDao;
import com.freshremix.dao.SKUSortDao;
import com.freshremix.dao.UserPreferenceDao;
import com.freshremix.model.EONUserPreference;
import com.freshremix.model.HideColumn;
import com.freshremix.model.LockButtonStatus;
import com.freshremix.model.ProfitPreference;
import com.freshremix.model.SKUSort;
import com.freshremix.model.User;
import com.freshremix.model.WidthColumn;
import com.freshremix.service.CategoryService;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.StringUtil;

public class UserPreferenceServiceImpl implements UserPreferenceService {

	private UserPreferenceDao userPreferenceDao;
	private SKUSortDao skuSortDao;
	private CategoryService categoryService;
	private CompanySellersSortDao companySellersSortDao;
	private ProfitPreference profitPreference;

	public void setCompanySellersSortDao(CompanySellersSortDao companySellersSortDao) {
		this.companySellersSortDao = companySellersSortDao;
	}

	public void setUserPreferenceDao(UserPreferenceDao userPreferenceDao) {
		this.userPreferenceDao = userPreferenceDao;
	}
	
	public void setSkuSortDao(SKUSortDao skuSortDao) {
		this.skuSortDao = skuSortDao;
	}
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@Override
	public EONUserPreference getUserPreference(Integer userId) {
		EONUserPreference pref = userPreferenceDao.getUserPreference(userId);
		return pref;
	}
	
	@Override
	public EONUserPreference getUserPreference(User user) {
		EONUserPreference pref = userPreferenceDao.getUserPreference(user.getUserId());
		// create default preference; null contents
		if (pref == null){
			pref = new EONUserPreference();
			pref.setDisplayAllocQty("0");
			pref.setUnfinalizeReceived("0");
		}
		
		pref.setHideColumn(this.getHideColumn(user));
		pref.setSkuSort(this.getSKUSort(user));
		pref.setWidthColumn(this.getWidthColumn(user));
		pref.setSortedCategories(categoryService.getCategoryListByUserId(user.getUserId().toString()));
		pref.setSortedSellerCompanies(this.getSortedSellerCompanies(user));
		pref.setSortedSellers(this.getSortedSellers(user));
		pref.setProfitPreference(this.getProfitPreference(user));
		return pref;
	}

	@Override
	public void insertUserPreference(EONUserPreference preference) {
		userPreferenceDao.insertUserPreference(preference);
	}

	@Override
	public void saveUserPreference(EONUserPreference preference) {

		EONUserPreference savedPreference = userPreferenceDao
				.getUserPreference(preference.getUserId());
		
		if(savedPreference != null){
			preference.setUserPreferenceId(savedPreference.getUserPreferenceId());
			userPreferenceDao.updateUserPreferenceByPreferenceId(preference);
		}else{
			userPreferenceDao.insertUserPreference(preference);
		}
	}

	@Override
	public void updateUserPreferenceByPreferenceId(EONUserPreference preference) {
		userPreferenceDao.updateUserPreferenceByPreferenceId(preference);
	}

	@Override
	public void updateUserPreferenceByUserId(EONUserPreference preference) {
		userPreferenceDao.updateUserPreferenceByUserId(preference);
	}

	@Override
	public void saveHideColumnPreference(HideColumn hideColumn) {
		userPreferenceDao.deleteHideColumn(hideColumn.getUserId());
		userPreferenceDao.insertHideColumn(hideColumn);
	}

	@Override
	public HideColumn getHideColumn(User user) {
		HideColumn hc = userPreferenceDao.getHideColumn(user.getUserId());
		if (hc == null) {
			if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)) {
					
				hc = HideColumn.createBuyerIntance();
				
			} else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)) {
				
				hc = HideColumn.createBuyerAdminIntance();
				
			} else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER) ||
				user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
				
				hc = HideColumn.createSellerInstance();
			}
		}
		
		return hc;
	}
	
	@Override
	public List<Integer> getSKUSort(User user) {
		
		List<Integer> skuSortOrder = new ArrayList<Integer>();
		
		SKUSort skuSort = skuSortDao.getSKUSort(user.getUserId());
		if (skuSort != null &&
				!StringUtil.isNullOrEmpty(skuSort.getSkuColumnIds())) {
			String skuColumnIds = skuSort.getSkuColumnIds();
			if (skuColumnIds.indexOf(",") != -1) {
				StringTokenizer st = new StringTokenizer(skuColumnIds,",");
				while(st.hasMoreTokens()) {
					skuSortOrder.add(Integer.valueOf(st.nextToken().trim()));
				}
			}
			else {
				skuSortOrder.add(Integer.valueOf(skuColumnIds.trim()));
			}
			
		}
		
		return skuSortOrder;
	}

	@Override
	public void saveWidthColumn(String columnId, Integer width, User user) {
		
		if (width < 50) {
			width = 50;
		}
		
		if (userPreferenceDao.updateWidthColumn(columnId, width, user.getUserId()) == 0) {
			userPreferenceDao.insertWidthColumn(columnId, width, user.getUserId());
		}
		user.getPreference().setWidthColumn(this.getWidthColumn(user));
	}

	@Override
	public WidthColumn getWidthColumn(User user) {
		Map<String,BigDecimal> widthMap = 
			userPreferenceDao.getWidthColumn(user.getUserId());
		return new WidthColumn(widthMap);
	}

	@Override
	public List<FilteredIDs> getSortedSellerCompanies(User user) {
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN) ||
				user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)) {
			return companySellersSortDao.getSortedCompany(user.getUserId());
		}
		else return null;
	}
	
	@Override
	public List<FilteredIDs> getSortedSellers(User user) {
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
			return companySellersSortDao.getSortedSellersForSellerAdmin(user.getUserId());
		}
		else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN) ||
				user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)) {
			return companySellersSortDao.getSortedSellers(user.getUserId());
		}
		else return null;
	}

	@Override
	public ProfitPreference getProfitPreference(User user) {
		ProfitPreference profitPref = userPreferenceDao.getProfitPreference(user.getUserId());

		if(profitPref == null){
			profitPref = new ProfitPreference();
			profitPref.setPriceTaxOption("1");
			profitPref.setTotalSellingPrice("0");
			profitPref.setTotalProfit("0");
			profitPref.setTotalProfitPercent("0");
			profitPref.setWithPackageQuantity("1");
		}
		
		return profitPref;
	}

	@Override
	public void saveProfitPreference(ProfitPreference profitPreference) {
		userPreferenceDao.saveProfitPreference(profitPreference);
	}
	
	@Override
	public void saveLockButtonStatus(LockButtonStatus lockButtonStatus) {
		userPreferenceDao.saveLockButtonStatus(lockButtonStatus);
	}
	
	@Override
	public LockButtonStatus getLockButtonStatus(User user) {
		LockButtonStatus lockButtonStatus = userPreferenceDao.getLockButtonStatus(user.getUserId());

		if(lockButtonStatus == null){
			lockButtonStatus = new LockButtonStatus();
			lockButtonStatus.setUserId(user.getUserId());
			lockButtonStatus.setLockButtonStatus(LockButtonStatus.ENABLED);
		}
		
		return lockButtonStatus;
	}
}
