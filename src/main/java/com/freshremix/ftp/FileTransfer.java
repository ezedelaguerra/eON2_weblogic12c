package com.freshremix.ftp;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

public class FileTransfer {

	private static Logger logger = Logger.getLogger(FileTransfer.class);
	
	private FTPDetails ftpDetails;
	
	public FileTransfer (FTPDetails ftpDetails) {
		this.ftpDetails = ftpDetails;
	}
	
	public boolean transfer() {
		
		boolean success = false;
		FTPClient client = new FTPClient();
		FileInputStream fis = null;
		
		try {
			logger.info("File transfer started: " + ftpDetails.getUsername());
			client.connect(ftpDetails.getIp());
			
			boolean login = client.login(ftpDetails.getFtpUser(), ftpDetails.getFtpPasswd());
			if(login == false){
				logger.error("FTP Login ("+ftpDetails.getUsername()+"): Failed!");
				return false;
			}
			
			//
			// Create an InputStream of the file to be uploaded
			//
			String filename = ftpDetails.getSourceFileName();
			fis = new FileInputStream(filename);

			//
			// Store file to server
			//
			for (int transferCnt = 1; transferCnt <= 3; transferCnt++){
				if(client.storeFile(ftpDetails.getTargetFileName(), fis)){
					success = true;
					logger.info("Transfer Try: " + transferCnt + ". Transfer Status: Success");
					break;
				}
				logger.error(transferCnt + ".) Transfer Status: Failed");
				try{
					 Thread.sleep(10000);
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}  
				continue;
			}

			client.logout();
			
		} catch (IOException e) {
			success = false;
			logger.error("Error doing file transfer! - " + ftpDetails.getUsername());
			logger.error("Exception Message:" + e.getMessage());
			logger.error("Exception Stacktrace:" + e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				client.disconnect();
			} catch (IOException e) {
				logger.error("Exception Message:" + e.getMessage());
				logger.error("Exception Stacktrace:" + e.getStackTrace());
				e.printStackTrace();
			}
		}
		
		return success;
	}
	
	public boolean checkFileExist(){

		boolean fileExist = false;
		FTPClient client = new FTPClient();
		
		try{
			client.connect(ftpDetails.getIp());
			client.login(ftpDetails.getFtpUser(), ftpDetails.getFtpPasswd());
			
			int exist = client.listFiles(ftpDetails.getTargetFileName()).length;
			if (exist > 0) fileExist = true;
			
			client.logout();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return fileExist;
	}
	
}
