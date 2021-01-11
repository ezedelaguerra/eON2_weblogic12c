package com.freshremix.ftp;

public class FTPDetails {

	private String username;
	private String ip;
	private String ftpUser;
	private String ftpPasswd;
	private String sourceFileName;
	private String targetFileName;
	
	public FTPDetails() { }

	public FTPDetails(String username, String ip, String ftpUser,
			String ftpPasswd, String sourceFileName, String targetFileName) {
		super();
		this.username = username;
		this.ip = ip;
		this.ftpUser = ftpUser;
		this.ftpPasswd = ftpPasswd;
		this.sourceFileName = sourceFileName;
		this.targetFileName = targetFileName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public String getFtpPasswd() {
		return ftpPasswd;
	}

	public void setFtpPasswd(String ftpPasswd) {
		this.ftpPasswd = ftpPasswd;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getTargetFileName() {
		return targetFileName;
	}

	public void setTargetFileName(String targetFileName) {
		this.targetFileName = targetFileName;
	}
}
