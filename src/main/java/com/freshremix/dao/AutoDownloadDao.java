package com.freshremix.dao;

import java.util.Date;
import java.util.List;

import com.freshremix.model.AutoDownloadSchedule;
import com.freshremix.model.User;

public interface AutoDownloadDao {

	List<AutoDownloadSchedule> getAutoDownloadSchedule();
	List<String> getDistinctAutoDownloadSchedule();
	List<AutoDownloadSchedule> getAutoDownloadSchedule(String time);
	AutoDownloadSchedule getAutoDownloadScheduleByUsername(String username);
	List<AutoDownloadSchedule> getAllDownloadScheduleExcludeSchedule(Integer scheduleId);
	List<Date> getAutoDownloadExceptDate(Integer scheduleCsvId);
	List<Integer> getAutoDownloadSellers(Integer scheduleCsvId);
	List<Integer> getAutoDownloadBuyers(Integer scheduleCsvId);
	void insertAutodownloadSchedule(AutoDownloadSchedule ads);
	void insertAutodownloadExceptionDate(Integer scheduleCsvId, List<Date> date);
	void insertAutodownloadSeller(Integer scheduleCsvId, List<Integer> seller);
	void insertAutodownloadBuyer(Integer scheduleCsvId, List<Integer> buyer);
	void updateDateLastDownloaded(Integer scheduleCsvId, Date endDate, Date date);
	void deleteAutodownloadSchedule(Integer scheduleCsvId);
	void deleteAutodownloadExceptionDate(Integer scheduleCsvId);
	void deleteAutodownloadSeller(Integer scheduleCsvId);
	void deleteAutodownloadBuyer(Integer scheduleCsvId);
	AutoDownloadSchedule getAutoDownloadScheduleById(Integer scheduleCsvId);
	List<User> getAutoDownloadUserSellers(Integer scheduleCsvId);
	List<User> getAutoDownloadUserBuyers(Integer scheduleCsvId);
}
