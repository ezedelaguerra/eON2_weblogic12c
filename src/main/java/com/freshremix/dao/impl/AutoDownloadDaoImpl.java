package com.freshremix.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.AutoDownloadDao;
import com.freshremix.model.AutoDownloadSchedule;
import com.freshremix.model.User;

public class AutoDownloadDaoImpl extends SqlMapClientDaoSupport 
	implements AutoDownloadDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAutoDownloadBuyers(Integer scheduleCsvId) {
		return getSqlMapClientTemplate().queryForList("AutoDownload.selectAutoDownloadBuyers", scheduleCsvId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Date> getAutoDownloadExceptDate(Integer scheduleCsvId) {
		return getSqlMapClientTemplate().queryForList("AutoDownload.selectAutoDownloadExceptDate", scheduleCsvId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AutoDownloadSchedule> getAutoDownloadSchedule() {
		return getSqlMapClientTemplate().queryForList("AutoDownload.selectAutoDownloadSchedule");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AutoDownloadSchedule> getAutoDownloadSchedule(String time) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("executionTime", time);
		return getSqlMapClientTemplate().queryForList("AutoDownload.selectAutoDownloadSchedule", param);
	}
	
	@Override
	public AutoDownloadSchedule getAutoDownloadScheduleByUsername(String username) {
		return (AutoDownloadSchedule) 
			getSqlMapClientTemplate()
			.queryForObject("AutoDownload.selectAutoDownloadScheduleByUsername", username);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AutoDownloadSchedule> getAllDownloadScheduleExcludeSchedule(Integer scheduleId) {
		return getSqlMapClientTemplate().queryForList("AutoDownload.selectAutoDownloadScheduleExceptByScheduleId", scheduleId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAutoDownloadSellers(Integer scheduleCsvId) {
		return getSqlMapClientTemplate().queryForList("AutoDownload.selectAutoDownloadSellers", scheduleCsvId);
	}

	@Override
	public void insertAutodownloadSchedule(AutoDownloadSchedule ads) {
		getSqlMapClientTemplate().insert("AutoDownload.insertAutoDownloadSchedule", ads);
	}

	@Override
	public void insertAutodownloadBuyer(Integer scheduleCsvId, List<Integer> buyer) {
		for (Integer b : buyer) {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("scheduleCsvId", scheduleCsvId);
			param.put("buyerId", b);
			getSqlMapClientTemplate().insert("AutoDownload.insertAutoDownloadBuyers", param);
		}
	}

	@Override
	public void insertAutodownloadExceptionDate(Integer scheduleCsvId, List<Date> date) {
		for (Date d : date) {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("scheduleCsvId", scheduleCsvId);
			param.put("exceptDate", d);
			getSqlMapClientTemplate().insert("AutoDownload.insertAutoDownloadExceptDate", param);
		}
	}

	@Override
	public void insertAutodownloadSeller(Integer scheduleCsvId, List<Integer> seller) {
		for (Integer s : seller) {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("scheduleCsvId", scheduleCsvId);
			param.put("sellerId", s);
			getSqlMapClientTemplate().insert("AutoDownload.insertAutoDownloadSellers", param);
		}
	}

	@Override
	public void updateDateLastDownloaded(Integer scheduleCsvId, Date endDate, Date date) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("scheduleCsvId", scheduleCsvId);
		param.put("endDate", endDate);
		param.put("date", date);
		getSqlMapClientTemplate().update("AutoDownload.updateDateLastDownloaded", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDistinctAutoDownloadSchedule() {
		return getSqlMapClientTemplate().queryForList("AutoDownload.selectDistinctAutoDownloadSchedule");
	}
	
	@Override
	public void deleteAutodownloadSchedule(Integer scheduleCsvId) {
		getSqlMapClientTemplate().delete("AutoDownload.deleteAutodownloadSchedule", scheduleCsvId);
	}

	@Override
	public void deleteAutodownloadBuyer(Integer scheduleCsvId) {
		getSqlMapClientTemplate().delete("AutoDownload.deleteAutodownloadBuyer", scheduleCsvId);
	}

	@Override
	public void deleteAutodownloadExceptionDate(Integer scheduleCsvId) {
		getSqlMapClientTemplate().delete("AutoDownload.deleteAutodownloadExceptionDate", scheduleCsvId);
	}

	@Override
	public void deleteAutodownloadSeller(Integer scheduleCsvId) {
		getSqlMapClientTemplate().insert("AutoDownload.deleteAutodownloadSeller", scheduleCsvId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.AutoDownloadDao#getDownloadScheduleDetails(java.lang.Integer)
	 */
	@Override
	public AutoDownloadSchedule getAutoDownloadScheduleById(Integer scheduleCsvId) {
		return (AutoDownloadSchedule) 
		getSqlMapClientTemplate()
		.queryForObject("AutoDownload.getAutoDownloadScheduleById", scheduleCsvId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAutoDownloadUserBuyers(Integer scheduleCsvId) {
		return getSqlMapClientTemplate().queryForList("AutoDownload.getAutoDownloadUserBuyers", scheduleCsvId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAutoDownloadUserSellers(Integer scheduleCsvId) {
		return getSqlMapClientTemplate().queryForList("AutoDownload.getAutoDownloadUserSellers", scheduleCsvId);
	}

}
