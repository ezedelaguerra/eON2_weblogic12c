package com.freshremix.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.quartz.SchedulerException;

import com.freshremix.model.AutoDownloadSchedule;
import com.freshremix.model.Sheets;
import com.freshremix.model.User;

public interface AutoDownloadService {

	List<Sheets> getAllSheetType();
	List<Integer> getUserId (List<String> usernames);
	User getUser (String username);
	Integer getSheetTypeId (Long roleId, String description);
	void saveJob(AutoDownloadSchedule ads);
	void scheduleJob(AutoDownloadSchedule ads) throws ParseException, SchedulerException;
	void rescheduleJob(AutoDownloadSchedule ads) throws SchedulerException, ParseException;
	void rescheduleJobs() throws SchedulerException, ParseException;
	List<AutoDownloadSchedule> getScheduleJobs() throws SchedulerException;
	List<AutoDownloadSchedule> getAllDownloadSchedule();
	List<String> getDistinctAutoDownloadSchedule();
	List<AutoDownloadSchedule> getAllDownloadScheduleExcludeSchedule(Integer scheduleId);
	List<AutoDownloadSchedule> getDownloadSchedule(String time);
	AutoDownloadSchedule getDownloadScheduleByUsername(String username);
	void updateDateLastDownloaded (Integer scheduleCsvId, Date endDate, Date date);
	boolean validateSchedule(AutoDownloadSchedule ads, String targetFilename);
	String validateAutoDownloadSchedule(AutoDownloadSchedule ads);
	List<Integer> getSortedSellers(User user, List<Integer> selectedSeller);
	List<Integer> getSortedBuyers(User user, List<Integer> sortedSeller, List<Integer> selectedBuyer);
	void deleteAutoDownloadSchedule (Integer scheduleId);
	AutoDownloadSchedule getDownloadScheduleDetails (Integer scheduleCsvId);
	boolean sendMailNotification(String subject, String message, AutoDownloadSchedule ads);
}
