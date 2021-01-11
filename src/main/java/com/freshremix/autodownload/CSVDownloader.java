package com.freshremix.autodownload;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.ftp.FTPDetails;
import com.freshremix.ftp.FileTransfer;
import com.freshremix.model.AutoDownloadSchedule;
import com.freshremix.model.Category;
import com.freshremix.model.SheetData;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.service.AppSettingService;
import com.freshremix.service.AutoDownloadService;
import com.freshremix.service.CategoryService;
import com.freshremix.service.SheetDataService;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.DownloadCSVUtil;

public class CSVDownloader {

	private static Logger logger = Logger.getLogger(CSVDownloader.class);
	
	private AutoDownloadService autoDownloadService;
	private SheetDataService sheetDataService;
	private AppSettingService appSettingService;
	private UsersInformationService userInfoService;
	private UserPreferenceService userPreferenceService;
	private CategoryService categoryService;
	
	public void setAutoDownloadService(AutoDownloadService autoDownloadService) {
		this.autoDownloadService = autoDownloadService;
	}
	
	public void setSheetDataService(SheetDataService sheetDataService) {
		this.sheetDataService = sheetDataService;
	}
	
	public void setAppSettingService(AppSettingService appSettingService) {
		this.appSettingService = appSettingService;
	}

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	public void setUserPreferenceService(UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	public void download() {
		
		// create folder
		this.createFolder();
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		final Date date = new Date();
		List<AutoDownloadSchedule> list = 
			autoDownloadService.getDownloadSchedule(sdf.format(date).toString());
		
		// format of folder
		sdf = new SimpleDateFormat("yyyyMMdd");
		final String folder = "autodl\\csv\\" + sdf.format(date) + "\\";
		
		for (final AutoDownloadSchedule ads : list) {
			User user = userInfoService.getUserById(ads.getUserId());
			user.setPreference(userPreferenceService.getUserPreference(user));
			
			// Create filename for FTP copy
			final String targetFilename = user.getUserName() + "_" +
					SheetTypeConstants.getSheetFileNameDesc(ads.getSheetTypeId()) + "_" +
					this.getCategoryName(ads.getCategoryId(), appSettingService.getCategoryMasterList())
					+ ".csv";
			
			// Create filename for Server copy
			final String filename = this.getFinalName(
					user.getUserName(),
					SheetTypeConstants.getSheetFileNameDesc(ads.getSheetTypeId()), 
					this.getCategoryName(ads.getCategoryId(), appSettingService.getCategoryMasterList()), 
					ads.getStartDate(), 
					ads.getEndDate());
			
			// Validate Schedule
			ads.setCategoryDesc(this.getCategoryName(ads.getCategoryId(), appSettingService.getCategoryMasterList()));
			ads.setSheetTypeDesc(SheetTypeConstants.getSheetFileNameDesc(ads.getSheetTypeId()));
			
			if (autoDownloadService.validateSchedule(ads, targetFilename) == false) {
				continue;
			}
			
			Thread thread = new Thread(ads.getUsername() +"("+ads.getUserId()+")")  {
				
				public void run() {
					
					logger.info("Thread started.");
					
					// Retrieve CSV data, should be the same as Web
					User user = userInfoService.getUserById(ads.getUserId());
					user.setPreference(userPreferenceService.getUserPreference(user));

					final FTPDetails ftpDetails = ads.createFTPDetails(); 
					ftpDetails.setTargetFileName(targetFilename);
					FileTransfer ft = new FileTransfer(ftpDetails);
					
					// retrieve categories
					List<Integer> selectedCategoryIds = this.getCategoryList(user, ads.getSellerList(), ads.getCategoryId());
					
					List<Integer> sortedSeller = autoDownloadService.getSortedSellers(user, ads.getSellerList());
					
					SheetData sheetData = sheetDataService.loadSheetData(
							user, 
							ads.getStartDate(), 
							ads.getEndDate(), 
							sortedSeller, 
							autoDownloadService.getSortedBuyers(user, sortedSeller, ads.getBuyerList()), 
							selectedCategoryIds, 
							ads.getSheetTypeId(), 
							(ads.getHasQty().trim().equals("1")) ? true : false, 
							true, 
							null);
					String csvListStr = DownloadCSVUtil.createCSVList(sheetData, user, appSettingService.getEONHeader(), appSettingService.getCategoryMasterList());
					
					// Save CSV locally inside the server machine
					logger.info("AutoDownloadCSV writing file! ");
					File file = null;
					try {
						file = new File(folder + filename);
						if (!file.createNewFile())
							logger.info("AutoDownloadCSV file already exist! " + file.getName());
						BufferedWriter bw = 
							new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"SJIS"));
						bw.write(csvListStr);
						bw.flush();
					} catch (IOException ioexc) {
						ioexc.printStackTrace();
						logger.error("AutoDownloadCSV write error! ");
						logger.error("Exception Message:" + ioexc.getMessage());
						logger.error("Exception Stacktrace:" + ioexc.getStackTrace());
					}
					
					// Transfer file
					//logger.info(this.getName() + ": AutoDownloadCSV transfer file! ");
					ftpDetails.setSourceFileName(file.getAbsolutePath());
					
					// If transfer is successful, update AutoDownload Schedule
//					if (ft.transfer()) {
//						autoDownloadService.updateDateLastDownloaded(ads.getScheduleCsvId(), DateFormatter.toDateObj(ads.getEndDate()), date);
//					} else {
//						// If transfer fails, send email
//						//logger.info(this.getName() + ": AutoDownloadCSV transfer failed! ");
//						String subject = "AutoDownloadCSV transfer Error";
//						String message = "Fail to tranfer file from FTP Server";
//						autoDownloadService.sendMailNotification(subject, message, ads);
//					}

					logger.info("Thread finished!");
				}
				
				private List<Integer> getCategoryList(User user, List<Integer> sellerIds, Integer categoryId){
					
					List<Integer> idList = new ArrayList<Integer>();
					
					// seller user
//					if(user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)) {
//						
//						categoryList = user.getPreference().getSortedCategories();
//						
//					} else 
					if (categoryId.equals(0)) { // 'All' selected
						
						List<UsersCategory> list = 
							categoryService.getCategoryListBySelectedIds(sellerIds);
						
						if(user.getPreference().getSortedCategories().isEmpty()){
							for (UsersCategory category : list) {
								Integer catId = category.getCategoryId();
								if (!idList.contains(catId)) {
									idList.add(catId);									
								}
							}
						}else{						
							// sort according to user preference (category sorting)
							for (UsersCategory uc : user.getPreference().getSortedCategories()) {
								
								for (UsersCategory _uc : list) {
									if (_uc.getCategoryId().equals(uc.getCategoryId())) {
										idList.add(uc.getCategoryId());
										break;
									}
								}
							}
						}
						
					} else { // particular category selected
						// empty
						idList.add(categoryId);
					}
					
					return idList;
				}
				
			};
			thread.start();
		}
		
		
	}
	
	private void createFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String day = sdf.format(new Date());
		
		File autodl = new File("autodl");
		File csv = new File("autodl\\csv");
		File folder = new File("autodl\\csv\\" + day);
		
		if (!autodl.exists())
			autodl.mkdir();
		
		if (!csv.exists())
			csv.mkdir();
		
		if (!folder.exists())
			folder.mkdir();
	}
	
	private String getCategoryName(Integer id, Map<String,Category> mCat) {
		String name = null;
		if (id.equals(0)) {
			name = "all";
		} else {
			for (String key : mCat.keySet()) {
				Category _cat = mCat.get(key);
				if (id.equals(_cat.getCategoryId()))
					name = _cat.getDescription();
			}
		}
		return name;
	}
	
	private String getFinalName(String username, String sheetName, String categoryname, String startDate, String endDate) {
		return username + "_" + sheetName + "_" + categoryname + "_" + startDate + "_" + endDate + ".csv";
	}
}
