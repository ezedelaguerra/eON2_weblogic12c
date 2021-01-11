package com.freshremix.service;

import org.codehaus.jettison.json.JSONException;

public interface ActivityLogService {

	String getActivityLogJsonString(String username, String dateFrom, String dateTo, String sheetName, String action, Integer endCount,
			String userId, String targetSellerId, String targetBuyerId, String deliveryDate) throws JSONException;
	
}
