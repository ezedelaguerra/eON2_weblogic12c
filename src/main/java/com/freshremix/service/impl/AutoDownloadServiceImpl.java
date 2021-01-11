package com.freshremix.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.freshremix.dao.AutoDownloadDao;
import com.freshremix.dao.DownloadCSVDao;
import com.freshremix.dao.UserDao;
import com.freshremix.ftp.FTPDetails;
import com.freshremix.ftp.FileTransfer;
import com.freshremix.model.AutoDownloadSchedule;
import com.freshremix.model.MailSender;
import com.freshremix.model.Sheets;
import com.freshremix.model.User;
import com.freshremix.service.AutoDownloadService;
import com.freshremix.service.CompanyBuyersSortService;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.SpringContext;

public class AutoDownloadServiceImpl implements AutoDownloadService {

	private static Logger logger = Logger.getLogger(AutoDownloadServiceImpl.class);
	
	private AutoDownloadDao autoDownloadDao;
	private DownloadCSVDao downloadCSVDao;
	private UserDao usersInfoDaos;
	private Scheduler csvScheduler;
	private CompanyBuyersSortService companybuyersSortService;
	private UserPreferenceService userPreferenceService;
	private String emailTo;
	
	static private String JOB_NAME = "dlCsvJob";

	public String getEmailTo() {
		return emailTo;
	}
	
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	
	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}

	public void setAutoDownloadDao(AutoDownloadDao autoDownloadDao) {
		this.autoDownloadDao = autoDownloadDao;
	}

	public void setDownloadCSVDao(DownloadCSVDao downloadCSVDao) {
		this.downloadCSVDao = downloadCSVDao;
	}
	
	public void setCsvScheduler(Scheduler csvScheduler) {
		this.csvScheduler = csvScheduler;
	}

	public void setCompanybuyersSortService(
			CompanyBuyersSortService companybuyersSortService) {
		this.companybuyersSortService = companybuyersSortService;
	}
	
	public void setUserPreferenceService(UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}

	@Override
	public List<Sheets> getAllSheetType() {
		return downloadCSVDao.getAllSheetType();
	}

	@Override
	public List<Integer> getUserId(List<String> username) {
		List<Integer> userList = new ArrayList<Integer>();
		for (String str : username) {
			userList.add(usersInfoDaos.getUserByUsername(str).getUserId());
		}
		return userList;
	}
	
	@Override
	public User getUser(String username) {
		return usersInfoDaos.getUserByUsername(username);
	}

	@Override
	public void saveJob(AutoDownloadSchedule ads) {
		//logger.info("Inserting...\n" + ads.toString());
		autoDownloadDao.insertAutodownloadSchedule(ads);
		autoDownloadDao.insertAutodownloadExceptionDate(ads.getScheduleCsvId(), ads.getExceptDateList());
		autoDownloadDao.insertAutodownloadSeller(ads.getScheduleCsvId(), ads.getSellerList());
		autoDownloadDao.insertAutodownloadBuyer(ads.getScheduleCsvId(), ads.getBuyerList());
	}
	
	@Override
	public void scheduleJob(AutoDownloadSchedule ads) throws ParseException, SchedulerException {
		
		if (csvScheduler.getTrigger(ads.getExecutionTime(), Scheduler.DEFAULT_GROUP) == null) {
			
			StringBuilder cronExp = new StringBuilder();
			cronExp.append("0 ");	// 0 seconds
			cronExp.append(ads.getExecutionMinute()).append(" ");	// minute
			cronExp.append(ads.getExecutionHour()).append(" ");		// hour
			cronExp.append("* * ?");
			
			Trigger trigger = new CronTrigger(
					ads.getExecutionTime(), 
					Scheduler.DEFAULT_GROUP, 
					cronExp.toString());
			trigger.setJobName(JOB_NAME);
			
			synchronized (csvScheduler) {
				csvScheduler.scheduleJob(trigger);
			}
			
			logger.info("New time added to scheduler: " + ads.getExecutionTime());
		} else {
			logger.info("Time already exist: " + ads.getExecutionTime());
		}
		
	}
	
	@Override
	public void rescheduleJob(AutoDownloadSchedule ads) throws SchedulerException, ParseException {
		
		Trigger trigger = csvScheduler.getTrigger(ads.getExecutionTime(), Scheduler.DEFAULT_GROUP);
		
		if (trigger == null) {
			this.scheduleJob(ads);
		} else {
			synchronized (csvScheduler) {
				csvScheduler.rescheduleJob(ads.getExecutionTime(), Scheduler.DEFAULT_GROUP, trigger);
			}
		}
		
	}
	
	@Override
	public void rescheduleJobs() throws SchedulerException, ParseException {
		
		boolean deleteJob = 
			csvScheduler.deleteJob(JOB_NAME, Scheduler.DEFAULT_GROUP);
		logger.info("Deleting job... ");
		
		if (!deleteJob) {
			logger.error("Failed to delete the job");
			return;
		}
		
		JobDetail job = (JobDetail) SpringContext.getApplicationContext().getBean("csvJobDetail");
		csvScheduler.addJob(job, false);
		logger.info("Adding job done.");
		
		for (String time : this.getDistinctAutoDownloadSchedule()) {
			AutoDownloadSchedule ads = new AutoDownloadSchedule();
			ads.setExecutionTime(time);
			this.rescheduleJob(ads);
		}
	}
	
	@Override
	public List<AutoDownloadSchedule> getScheduleJobs() throws SchedulerException {
		
		List<AutoDownloadSchedule> list = new ArrayList<AutoDownloadSchedule>();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		for (String tName : csvScheduler.getTriggerNames(Scheduler.DEFAULT_GROUP)) {
			Trigger trigger = csvScheduler.getTrigger(tName, Scheduler.DEFAULT_GROUP);
			String time = sdf.format(trigger.getNextFireTime());
			list.addAll(this.getDownloadSchedule(time));
		}
		
		return list;
	}

	@Override
	public Integer getSheetTypeId(Long roleId, String description) {
		Sheets sheet = downloadCSVDao.getSheetType(roleId, description);
		return sheet.getSheetTypeId();
	}

	@Override
	public List<AutoDownloadSchedule> getAllDownloadSchedule() {
		return autoDownloadDao.getAutoDownloadSchedule();
	}
	
	@Override
	public List<String> getDistinctAutoDownloadSchedule() {
		return autoDownloadDao.getDistinctAutoDownloadSchedule();
	}
	
	@Override
	public List<AutoDownloadSchedule> getAllDownloadScheduleExcludeSchedule(Integer scheduleId) {
		return autoDownloadDao.getAllDownloadScheduleExcludeSchedule(scheduleId);
	}

	@Override
	public List<AutoDownloadSchedule> getDownloadSchedule(String time) {
		List<AutoDownloadSchedule> list = autoDownloadDao.getAutoDownloadSchedule(time);
		
		for (AutoDownloadSchedule ads : list) {
			ads.setExceptDateList(autoDownloadDao.getAutoDownloadExceptDate(ads.getScheduleCsvId()));
			ads.setBuyerList(autoDownloadDao.getAutoDownloadBuyers(ads.getScheduleCsvId()));
			ads.setSellerList(autoDownloadDao.getAutoDownloadSellers(ads.getScheduleCsvId()));
		}
		return list;
	}
	
	@Override
	public AutoDownloadSchedule getDownloadScheduleByUsername(String username) {
		AutoDownloadSchedule ads = autoDownloadDao.getAutoDownloadScheduleByUsername(username);
		if (ads != null) {
			ads.setExceptDateList(autoDownloadDao.getAutoDownloadExceptDate(ads.getScheduleCsvId()));
			ads.setBuyerList(autoDownloadDao.getAutoDownloadBuyers(ads.getScheduleCsvId()));
			ads.setSellerList(autoDownloadDao.getAutoDownloadSellers(ads.getScheduleCsvId()));
		}
		return ads;
	}

	@Override
	public void updateDateLastDownloaded(Integer scheduleCsvId, Date endDate, Date date) {
		autoDownloadDao.updateDateLastDownloaded(scheduleCsvId, endDate, date);
	}

	@Override
	public boolean validateSchedule(AutoDownloadSchedule ads, String targetFilename) {
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String dateStr = sdf.format(date);
		
		for (Date d : ads.getExceptDateList()) {
			if (dateStr.equals(sdf.format(d))) {
				
				StringBuilder sb = new StringBuilder();
				sb.append("Included in exception date list. ");
				sb.append(ads.getUsername()).append(" - ").append(ads.getExecutionTime());
				logger.info(sb.toString());

				return false;
			}
		}
		
		// check last downloaded date; should not be equal or greater than today's date
		Date d = ads.getDateLastDownload();
		try {
			d = sdf.parse(sdf.format(d));
			date = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			logger.error("Exception Message:" + e.getMessage());
			logger.error("Exception Stacktrace:" + e.getStackTrace());
			this.sendMailNotification(e.getMessage(), e.getStackTrace().toString(), ads);
			e.printStackTrace();
			return false;
		}
		if (d.equals(date) || d.after(date)) {
			StringBuilder sb = new StringBuilder();
			sb.append("Downloaded date: Should not be equal or greater than today's date. ").append(d).append(" ");
			sb.append(ads.getUsername()).append(" - ").append(ads.getExecutionTime());
			logger.error(sb.toString());
			this.sendMailNotification("Date should not be equal or greater than today's date.", sb.toString(), ads);
			return false;
		}
		
		// Date Range 45 days maximum		
		int daysCnt= DateFormatter.getDateDiff(ads.getStartDate(), ads.getEndDate());
		if (daysCnt > 45){
			logger.error("Date Range is " + daysCnt + " days." );
			String subject = "Date range reaches more than 45 days";
			String message = "Fail to tranfer file to FTP Server, Date Range is " +
							  daysCnt + " days!" ;
			String toAddress[] = new String[1];
			toAddress[0] = this.emailTo;
			this.sendMailNotification(subject, message, ads);
			return false;
		}
		
		// Validate if file has already exist from FTP Server 
		FTPDetails ftpDetails = ads.createFTPDetails();
		ftpDetails.setTargetFileName(targetFilename);
		FileTransfer ft = new FileTransfer(ftpDetails);
		if(ft.checkFileExist()){
			// Failed! then send email
			logger.error("[" + targetFilename + "] FTP file exists!" );
			String subject = targetFilename + " FTP file exists!";
			String message = "Fail to tranfer file to FTP Server, " + 
							  targetFilename + " file already exists!" ;
			this.sendMailNotification(subject, message, ads);
			return false;
		}
		
		return true;
	}

	@Override
	public String validateAutoDownloadSchedule(
			AutoDownloadSchedule ads) {
		
		boolean isValid = true;
		
		User user = usersInfoDaos.getUserByUsername(ads.getUsername());
		if (user == null) {
			isValid = false;
			return "Invalid user!";
		}
		
		if (isValid) {
			if (this.getDownloadScheduleByUsername(ads.getUsername()) != null) {
				isValid = false;
				return "User already exists!";
			}
		}
		
		return "";
	}

	@Override
	public List<Integer> getSortedBuyers(User user, List<Integer> sortedSeller, List<Integer> selectedBuyer) {

		List<Integer> sortedBuyer = new ArrayList<Integer>();
		List<Integer> companyId = new ArrayList<Integer>();
		
		// get company id
		for (Integer id : sortedSeller) {
			companyId.add(usersInfoDaos.getUserById(id).getCompany().getCompanyId());
		}
		
		List<FilteredIDs> tmp = 
			companybuyersSortService.getSortedBuyers(sortedSeller, user.getUserId(), companyId);
		
		if (tmp != null) {
			// sort seller ids
			for (FilteredIDs filter : tmp) {
				for (Integer id : selectedBuyer) {
					if (id.equals(Integer.parseInt(filter.getId())))
						sortedBuyer.add(id);
				}
			}
		} else {
			sortedBuyer.add(user.getUserId());
		}
		
		return sortedBuyer;
	}

	@Override
	public List<Integer> getSortedSellers(User user, List<Integer> selectedSeller) {
		
		List<FilteredIDs> tmp = userPreferenceService.getSortedSellers(user);
		List<Integer> sortedSeller = new ArrayList<Integer>();
		
		if (tmp != null) {
			// sort seller ids
			for (FilteredIDs filter : tmp) {
				for (Integer id : selectedSeller) {
					if (id.equals(Integer.parseInt(filter.getId())))
						sortedSeller.add(id);
				}
			}
		} else {
			sortedSeller.add(user.getUserId());
		}
		
		return sortedSeller;
	}

	@Override
	public void deleteAutoDownloadSchedule(Integer scheduleCsvId) {
		autoDownloadDao.deleteAutodownloadExceptionDate(scheduleCsvId);
		autoDownloadDao.deleteAutodownloadBuyer(scheduleCsvId);
		autoDownloadDao.deleteAutodownloadSeller(scheduleCsvId);
		autoDownloadDao.deleteAutodownloadSchedule(scheduleCsvId);
	}

	@Override
	public AutoDownloadSchedule getDownloadScheduleDetails(Integer scheduleCsvId) {
		AutoDownloadSchedule schedule = new AutoDownloadSchedule();
		schedule = autoDownloadDao.getAutoDownloadScheduleById(scheduleCsvId);
		schedule.setExceptDateList(autoDownloadDao.getAutoDownloadExceptDate(scheduleCsvId));
		schedule.setSeller(this.convertUserListToStringUsername(
				autoDownloadDao.getAutoDownloadUserSellers(scheduleCsvId)));
		schedule.setBuyer(this.convertUserListToStringUsername(
				autoDownloadDao.getAutoDownloadUserBuyers(scheduleCsvId)));
		return schedule;
	}
	
	private String convertUserListToStringUsername(List<User> userList){
		String usernameStr = "";
		for(User user: userList){
			usernameStr = usernameStr + user.getUserName() + ",";
		}
		return usernameStr;
	}
	
	@Override
	public boolean sendMailNotification(String subject, String message, AutoDownloadSchedule ads) {
		String toAddress[] = new String[1];
		toAddress[0] = this.emailTo;
		
		MailSender ms = new MailSender();
		ms.setMessage(message + getJobDtlsToString(ads));
		ms.setSubject("[new eON Autodownload] " + subject);
		ms.setToAddress(toAddress);
		
		Thread msThread = new Thread(ms);
		msThread.start();
		return ms.isSuccess();
	}	
	
	private String getJobDtlsToString(AutoDownloadSchedule ads){
		
		StringBuffer jobDtlsSB = new StringBuffer();
		jobDtlsSB.append("\n\n");
		jobDtlsSB.append("Auto Download Schedule Object").append("\n");
		jobDtlsSB.append("=============================").append("\n");
		jobDtlsSB.append("FTP IP : ").append(ads.getFtpIp()).append("\n");
		jobDtlsSB.append("Schedule ID: ").append(ads.getScheduleCsvId()).append("\n");
		jobDtlsSB.append("User ID: ").append(ads.getUserId()).append("\n");
		jobDtlsSB.append("User Name: ").append(ads.getUsername()).append("\n");
		jobDtlsSB.append("Sheet Description: ").append(ads.getSheetTypeDesc()).append("\n");
		jobDtlsSB.append("Exection Time : ").append(ads.getExecutionTime()).append("\n");
		jobDtlsSB.append("Date Last Downloaded : ").append(ads.getDateLastDownload()).append("\n");
		jobDtlsSB.append("Lead Time : ").append(ads.getLeadTime()).append("\n");
		jobDtlsSB.append("Start Date : ").append(ads.getStartDate()).append("\n");
		jobDtlsSB.append("End Date : ").append(ads.getEndDate()).append("\n");
		jobDtlsSB.append("Category Description: ").append(ads.getCategoryDesc()).append("\n");
		jobDtlsSB.append("Seller List: ").append(ads.getSellerList()).append("\n");
		jobDtlsSB.append("Buyer List: ").append(ads.getBuyerList()).append("\n");
		
		return jobDtlsSB.toString();
	}

}