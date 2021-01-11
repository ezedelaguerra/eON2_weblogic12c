package com.freshremix.service.impl;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.freshremix.dao.ActivityLogDao;
import com.freshremix.model.ActivityLog;
import com.freshremix.service.ActivityLogService;

public class ActivityLogServiceImpl implements ActivityLogService {

	ActivityLogDao activityLogDao;
	
	public void setActivityLogDao(ActivityLogDao activityLogDao) {
		this.activityLogDao = activityLogDao;
	}



	@Override
	public String getActivityLogJsonString(String username, String dateFrom, String dateTo,
			String sheetName, String action, Integer endCount, String userId, String targetSellerId, String targetBuyerId, String deliveryDate) throws JSONException {
		
		List<ActivityLog> activityLogList = activityLogDao.getActivityLogList(username, dateFrom, dateTo, sheetName,action,endCount, userId, targetSellerId, targetBuyerId, deliveryDate);
		JSONArray activityListJsonArray = new JSONArray();
		
		
		
		for(ActivityLog aLog : activityLogList){
			JSONObject cells = new JSONObject();
			JSONArray cell = new JSONArray();
			JSONObject c_userId  = new JSONObject();
			JSONObject c_userName  = new JSONObject();
			JSONObject c_date  = new JSONObject();
			JSONObject c_timestamp  = new JSONObject();
			JSONObject c_targetSellerId  = new JSONObject();
			JSONObject c_targetBuyerId  = new JSONObject();
			JSONObject c_deliveryDate  = new JSONObject();
			JSONObject c_sheetName  = new JSONObject();
			JSONObject c_action  = new JSONObject();
			
			c_userId.put("value", aLog.getUserId());
			c_userName.put("value", aLog.getUsername());
			c_date.put("value", aLog.getDate());
			c_timestamp.put("value", aLog.getTimestamp());
			c_targetSellerId.put("value", aLog.getTargetSellerId());
			c_targetBuyerId.put("value", aLog.getTargetBuyerId());
			c_deliveryDate.put("value", aLog.getDeliveryDate());
			c_sheetName.put("value", aLog.getSheetName());
			c_action.put("value", aLog.getAction());
			
			cell.put(c_userId);
			cell.put(c_userName);
			cell.put(c_date);
			cell.put(c_timestamp);
			cell.put(c_targetSellerId);
			cell.put(c_targetBuyerId);
			cell.put(c_deliveryDate);
			cell.put(c_sheetName);
			cell.put(c_action);
			cells.put("cells", cell);
			activityListJsonArray.put(cells);
		}
		
		return activityListJsonArray.toString();
	}

}
