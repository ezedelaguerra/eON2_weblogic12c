package com.freshremix.dao;

import java.util.List;

import com.freshremix.model.ActivityLog;

public interface ActivityLogDao {
	
	void saveActivityLog(ActivityLog activityLog);
	List<ActivityLog> getActivityLogList(String username, String dateFrom, String dateTo, String sheetName, String action, Integer endCount, String userId, String targetSellerId, String targetBuyerId, String deliveryDate);
	Integer getActivityLogCount(String username, String dateFrom, String dateTo, String sheetName, String action, String userId, String targetSellerId, String targetBuyerId, String deliveryDate);

}
