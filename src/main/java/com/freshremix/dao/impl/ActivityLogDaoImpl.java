package com.freshremix.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.ActivityLogDao;
import com.freshremix.model.ActivityLog;

public class ActivityLogDaoImpl extends SqlMapClientDaoSupport implements ActivityLogDao{

	private static final Logger LOGGER = Logger.getLogger(ActivityLogDaoImpl.class);
	
	@Override
	public void saveActivityLog(ActivityLog activityLog) {
		LOGGER.info("Start : saveActivityLog");		
		getSqlMapClientTemplate().insert("ActivityLog.insertActivityLogList", activityLog);
		
		LOGGER.info("End : saveActivityLog");
	}

	@Override
	public List<ActivityLog> getActivityLogList(String username, String dateFrom, String dateTo,
			String sheetName, String action, Integer endCount, String userId, String targetSellerId, 
			String targetBuyerId, String deliveryDate) {
		LOGGER.info("Start : getActivityLogList");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("username", username);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);	
		param.put("sheetName", sheetName);
		param.put("action", action);
		param.put("startCount", endCount-100);
		param.put("endCount", endCount);
		param.put("targetSellerId", targetSellerId);
		param.put("targetBuyerId", targetBuyerId);
		param.put("deliveryDate", deliveryDate);
		
		LOGGER.info("Start : getActivityLogList");


		return (List<ActivityLog>) getSqlMapClientTemplate().queryForList("ActivityLog.selectActivityLogList", param);
	}

	@Override
	public Integer getActivityLogCount(String username, String dateFrom,
			String dateTo, String sheetName, String action, String userId, String targetSellerId, 
			String targetBuyerId, String deliveryDate) {
		LOGGER.info("Start : getActivityLogCount");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("username", username);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);	
		param.put("sheetName", sheetName);
		param.put("action", action);
		param.put("targetSellerId", targetSellerId);
		param.put("targetBuyerId", targetBuyerId);
		param.put("deliveryDate", deliveryDate);
		LOGGER.info("Start : getActivityLogCount");

		return (Integer) getSqlMapClientTemplate().queryForObject("ActivityLog.selectActivityLogCount", param);
	}

}
