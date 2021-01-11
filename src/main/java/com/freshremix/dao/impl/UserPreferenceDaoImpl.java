package com.freshremix.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.UserPreferenceDao;
import com.freshremix.model.EONUserPreference;
import com.freshremix.model.HideColumn;
import com.freshremix.model.LockButtonStatus;
import com.freshremix.model.ProfitPreference;

public class UserPreferenceDaoImpl extends SqlMapClientDaoSupport implements UserPreferenceDao {

	@Override
	public EONUserPreference getUserPreference(Integer userId) {
		
		Map<String,Integer> param = new HashMap<String,Integer>();
		param.put("userId", userId);
		return (EONUserPreference)getSqlMapClientTemplate().queryForObject("UserPreference.getUserPreference", param);
	}

	@Override
	public void insertUserPreference(EONUserPreference preference) {
		getSqlMapClientTemplate().insert("UserPreference.insertUserPreference", preference);
	}

	@Override
	public void updateUserPreferenceByPreferenceId(EONUserPreference preference) {
		getSqlMapClientTemplate().update("UserPreference.updateUserPreferenceById", preference);
	}

	@Override
	public void updateUserPreferenceByUserId(EONUserPreference preference) {
		getSqlMapClientTemplate().update("UserPreference.updateUserPreferenceByUserId", preference);
	}

	@Override
	public void deleteHideColumn(Integer userId) {
		getSqlMapClientTemplate().insert("UserPreference.deleteHideColumn", userId);
	}

	@Override
	public void insertHideColumn(HideColumn hideColumn) {
		this.deleteHideColumn(hideColumn.getUserId());
		getSqlMapClientTemplate().insert("UserPreference.insertHideColumn", hideColumn);
	}

	@Override
	public HideColumn getHideColumn(Integer userId) {
		return (HideColumn) 
			getSqlMapClientTemplate().queryForObject("UserPreference.getHideColumn", userId);
	}

	@Override
	public void insertWidthColumn(String columnId, Integer width, Integer userId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("columnId", columnId);
		param.put("width", width);
		param.put("userId", userId);
		
		getSqlMapClientTemplate().insert("UserPreference.inesrtWidthColumn", param);
	}
	
	@Override
	public Integer updateWidthColumn(String columnId, Integer width, Integer userId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("columnId", columnId);
		param.put("width", width);
		param.put("userId", userId);
		
		return getSqlMapClientTemplate().update("UserPreference.updateWidthColumn", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,BigDecimal> getWidthColumn(Integer userId) {
		
		return getSqlMapClientTemplate().queryForMap("UserPreference.getWidthColumn", userId, "key", "value");
	}

	@Override
	public ProfitPreference getProfitPreference(Integer userId) {
		return (ProfitPreference)getSqlMapClientTemplate().queryForObject("UserPreference.getProfitPreference", userId);
	}

	@Override
	public void saveProfitPreference(ProfitPreference profitPreference) {
		
		ProfitPreference profitPref = (ProfitPreference)getSqlMapClientTemplate().queryForObject
										("UserPreference.getProfitPreference", profitPreference.getUserId());
		if (profitPref != null){
			getSqlMapClientTemplate().delete("UserPreference.deletetProfitPreference", profitPreference.getUserId());
		}
		getSqlMapClientTemplate().insert("UserPreference.insertProfitPreference", profitPreference);
	}
	
	@Override
	public void saveLockButtonStatus(LockButtonStatus lockButtonStatus) {
		
		LockButtonStatus lockBtnStatus = (LockButtonStatus)getSqlMapClientTemplate().queryForObject
										("UserPreference.getLockButtonStatus", lockButtonStatus.getUserId());
		if (lockBtnStatus != null){
			getSqlMapClientTemplate().update("UserPreference.updateLockButtonStatusByUserId", lockButtonStatus);
		} else {
			getSqlMapClientTemplate().insert("UserPreference.insertLockButtonStatus", lockButtonStatus);
		}	
		
	}
	
	@Override
	public LockButtonStatus getLockButtonStatus(Integer userId) {
		
		return  (LockButtonStatus)getSqlMapClientTemplate().queryForObject
										("UserPreference.getLockButtonStatus", userId);
	}

}
