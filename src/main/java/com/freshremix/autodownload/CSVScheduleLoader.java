package com.freshremix.autodownload;

import org.springframework.beans.factory.InitializingBean;

import com.freshremix.model.AutoDownloadSchedule;
import com.freshremix.service.AutoDownloadService;

public class CSVScheduleLoader implements InitializingBean {

	private AutoDownloadService autoDownloadService;
	
	public void setAutoDownloadService(AutoDownloadService autoDownloadService) {
		this.autoDownloadService = autoDownloadService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		for (String time : autoDownloadService.getDistinctAutoDownloadSchedule()) {
			AutoDownloadSchedule ads = new AutoDownloadSchedule();
			ads.setExecutionTime(time);
			autoDownloadService.scheduleJob(ads);
		}
		
	}

}
