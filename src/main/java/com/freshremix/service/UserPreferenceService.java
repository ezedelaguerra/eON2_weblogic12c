package com.freshremix.service;

import java.util.List;

import com.freshremix.model.EONUserPreference;
import com.freshremix.model.HideColumn;
import com.freshremix.model.LockButtonStatus;
import com.freshremix.model.ProfitPreference;
import com.freshremix.model.User;
import com.freshremix.model.WidthColumn;
import com.freshremix.ui.model.FilteredIDs;

public interface UserPreferenceService {

	EONUserPreference getUserPreference(Integer userId);
	
	EONUserPreference getUserPreference(User user);

	void insertUserPreference(EONUserPreference preference);

	void updateUserPreferenceByPreferenceId(EONUserPreference preference);

	void updateUserPreferenceByUserId(EONUserPreference preference);
	
	void saveUserPreference(EONUserPreference preference);
	
	void saveHideColumnPreference(HideColumn hideColumn);
	
	HideColumn getHideColumn(User user);
	
	List<Integer> getSKUSort(User user);
	
	void saveWidthColumn (String columnId, Integer width, User user);
	
	WidthColumn getWidthColumn (User user);
	
	List<FilteredIDs> getSortedSellerCompanies(User user);
	
	List<FilteredIDs> getSortedSellers(User user);
	
	ProfitPreference getProfitPreference(User user);
	
	void saveProfitPreference(ProfitPreference profitPreference);
	
	void saveLockButtonStatus(LockButtonStatus lockButtonStatus);
	
	LockButtonStatus getLockButtonStatus(User user);
}
