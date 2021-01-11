package com.freshremix.model;

import java.util.Date;

public class AuditTrail {
	
	private long id;
	private int user_id;
	private String username;
	private long process_id;
	private String log_type;
	private String ip_address;
	private String url;	
	private String url_referer;
	private String user_agent;
	private String sys_date;
	private Date db_date;
	
	/* Getters and Setters */
	
	public long getLog_id() {
		return id;
	}
	public void setLog_id(long logId) {
		id = logId;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int userId) {
		user_id = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getProcess_id() {
		return process_id;
	}
	public void setProcess_id(long processId) {
		process_id = processId;
	}
	public String getLog_type() {
		return log_type;
	}
	public void setLog_type(String logType) {
		log_type = logType;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ipAddress) {
		ip_address = ipAddress;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl_referer() {
		return url_referer;
	}
	public void setUrl_referer(String urlReferer) {
		url_referer = urlReferer;
	}
	public String getUser_agent() {
		return user_agent;
	}
	public void setUser_agent(String userAgent) {
		user_agent = userAgent;
	}	
	public String getSys_date() {
		return sys_date;
	}
	public void setSys_date(String sysDate) {
		sys_date = sysDate;
	}
	public Date getDb_date() {
		return db_date;
	}
	public void setDb_date(Date dbDate) {
		db_date = dbDate;
	}

}
