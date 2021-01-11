package com.freshremix.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.freshremix.ftp.FTPDetails;
import com.freshremix.util.StringUtil;

public class AutoDownloadSchedule implements Comparable<AutoDownloadSchedule> {

	private Integer scheduleCsvId;
	private Integer userId;
	private String username;
	private String executionTime;
	private Date dateLastDownload;
	private String dateLastDownloadStr;
	private Integer leadTime;
	private Integer sheetTypeId;
	private String sheetTypeDesc;
	private Integer categoryId;
	private String categoryDesc;
	private String hasQty;
	private String hasQtyUI;
	private String ftpIp;
	private String ftpId;
	private String ftpPw;
	private String confirmFtpPw;
	private Date lastRunDate;
	private String seller;
	private String buyer;
	private String exceptDate;
	private List<Integer> sellerList;
	private List<Integer> buyerList;
	private List<Date> exceptDateList;
	private String color;
	
	public Integer getScheduleCsvId() {
		return scheduleCsvId;
	}
	public void setScheduleCsvId(Integer scheduleCsvId) {
		this.scheduleCsvId = scheduleCsvId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}
	public Date getDateLastDownload() {
		return dateLastDownload;
	}
	public void setDateLastDownload(Date dateLastDownload) {
		this.dateLastDownload = dateLastDownload;
	}
	public String getDateLastDownloadStr() {
		return dateLastDownloadStr;
	}
	public void setDateLastDownloadStr(String dateLastDownloadStr) {
		this.dateLastDownloadStr = dateLastDownloadStr;
	}
	public Integer getLeadTime() {
		return leadTime;
	}
	public void setLeadTime(Integer leadTime) {
		this.leadTime = leadTime;
	}
	public Integer getSheetTypeId() {
		return sheetTypeId;
	}
	public void setSheetTypeId(Integer sheetTypeId) {
		this.sheetTypeId = sheetTypeId;
	}
	public String getSheetTypeDesc() {
		return sheetTypeDesc;
	}
	public void setSheetTypeDesc(String sheetTypeDesc) {
		this.sheetTypeDesc = sheetTypeDesc;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	public String getHasQty() {
		return this.hasQty;
	}
	public void setHasQty(String hasQty) {
		this.hasQty = hasQty;
	}
	public String getHasQtyUI() {
		if (!StringUtil.isNullOrEmpty(this.hasQtyUI) && 
			(this.hasQtyUI.equals("on") || this.hasQtyUI.equals("checked")))
			return "checked";
		return "";
	}
	public String getRawHasQtyUI() {
		return this.hasQtyUI;
	}
	public void setHasQtyUI(String hasQtyUI) {
		this.hasQtyUI = hasQtyUI;
	}
	public String getFtpIp() {
		return ftpIp;
	}
	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}
	public String getFtpId() {
		return ftpId;
	}
	public void setFtpId(String ftpId) {
		this.ftpId = ftpId;
	}
	public String getFtpPw() {
		return ftpPw;
	}
	public void setFtpPw(String ftpPw) {
		this.ftpPw = ftpPw;
	}
	public String getConfirmFtpPw() {
		return confirmFtpPw;
	}
	public void setConfirmFtpPw(String confirmFtpPw) {
		this.confirmFtpPw = confirmFtpPw;
	}
	public Date getLastRunDate() {
		return lastRunDate;
	}
	public void setLastRunDate(Date lastRunDate) {
		this.lastRunDate = lastRunDate;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getExceptDate() {
		return exceptDate;
	}
	public void setExceptDate(String exceptDate) {
		this.exceptDate = exceptDate;
	}
	public List<Integer> getSellerList() {
		return sellerList;
	}
	public void setSellerList(List<Integer> sellerList) {
		this.sellerList = sellerList;
	}
	public List<Integer> getBuyerList() {
		return buyerList;
	}
	public void setBuyerList(List<Integer> buyerList) {
		this.buyerList = buyerList;
	}
	public List<Date> getExceptDateList() {
		return exceptDateList;
	}
	public void setExceptDateList(List<Date> exceptDateList) {
		this.exceptDateList = exceptDateList;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public Integer getExecutionHour() {
		StringTokenizer st = new StringTokenizer(this.executionTime, ":");
		while (st.hasMoreTokens()) {
			String tmp = st.nextToken();
			if (!StringUtil.isNullOrEmpty(tmp)) {
				return Integer.parseInt(tmp);
			}
		}
		return null;
	}
	
	public Integer getExecutionMinute() {
		StringTokenizer st = new StringTokenizer(this.executionTime, ":");
		String tmp = null;
		while (st.hasMoreTokens()) {
			tmp = st.nextToken();
		}
		if (!StringUtil.isNullOrEmpty(tmp)) {
			return Integer.parseInt(tmp);
		}
		return null;
	}
	
	public FTPDetails createFTPDetails() {
		FTPDetails tmp = new FTPDetails();
		tmp.setUsername(this.username);
		tmp.setIp(this.ftpIp);
		tmp.setFtpUser(this.ftpId);
		tmp.setFtpPasswd(this.ftpPw);
		return tmp;
	}
	
	public String getStartDate() {
		// Last Download Date + 1
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(this.dateLastDownload);
		c.add(Calendar.DATE, 1);
		return sdf.format(c.getTime());
	}
	
	public String getEndDate() {
		// Date today - Lead time
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -this.leadTime);
		return sdf.format(c.getTime());
	}
	
	@Override
	public int compareTo(AutoDownloadSchedule ads) {
		return this.username.compareTo(ads.getUsername());
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("User: ").append(this.userId).append("\n");
		sb.append("Time: ").append(this.executionTime).append("\n");
		sb.append("Last Download Date: ").append(this.dateLastDownloadStr).append("\n");
		sb.append("Lead Time: ").append(this.leadTime).append("\n");
		sb.append("Exception Date: ").append(this.exceptDateList.toString()).append("\n");
		sb.append("Seller Id: ").append(this.sellerList.toString()).append("\n");
		sb.append("Buyer Id: ").append(this.buyerList.toString()).append("\n");
		sb.append("Sheet Type: ").append(this.sheetTypeId).append("\n");
		sb.append("Category Id: ").append(this.categoryId).append("\n");
		sb.append("Has Category: ").append(this.hasQty).append("\n");
		sb.append("FTP IP: ").append(this.ftpIp).append("\n");
		sb.append("FTP ID: ").append(this.ftpId).append("\n");
		sb.append("FTP Password: ").append(this.ftpPw).append("\n");
		
		return sb.toString();
	}
	
}
