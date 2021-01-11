package com.freshremix.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.freshremix.model.EONUserPreference;
import com.freshremix.model.HideColumn;
import com.freshremix.model.LockButtonStatus;
import com.freshremix.model.ProfitPreference;
import com.freshremix.model.User;

public interface UserPreferenceDao {
	
	EONUserPreference getUserPreference(Integer userId);

	void insertUserPreference(EONUserPreference preference);

	void updateUserPreferenceByPreferenceId(EONUserPreference preference);

	void updateUserPreferenceByUserId(EONUserPreference preference);
	
	void insertHideColumn(HideColumn hideColumn);
	
	void deleteHideColumn(Integer userId);
	
	HideColumn getHideColumn(Integer userId); 
	
	void insertWidthColumn(String columnId, Integer width, Integer userId);
	
	Integer updateWidthColumn(String columnId, Integer width, Integer userId);
	
	Map<String,BigDecimal> getWidthColumn(Integer userId);
	
	ProfitPreference getProfitPreference(Integer userId);
	
	void saveProfitPreference(ProfitPreference profitPreference);
	
	void saveLockButtonStatus(LockButtonStatus lockButtonStatus);
	
	LockButtonStatus getLockButtonStatus(Integer userId);
}
