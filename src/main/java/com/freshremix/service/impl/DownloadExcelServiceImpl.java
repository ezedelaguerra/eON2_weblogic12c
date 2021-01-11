/**
 * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
 * <BR>
 * <B>Description:</B><BR>
 * Describe class or interface.<BR>
 * <BR>
 * <B>Known Bugs:</B>
 * none<BR>
 * <BR>
 * <B>History:</B>
 * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
 * date       name            changes
 * ------------------------------------------------------------------------------
 * Jul 26, 2010		nvelasquez		
 */
package com.freshremix.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.freshremix.dao.DownloadExcelDao;
import com.freshremix.model.DownloadExcelSetting;
import com.freshremix.service.DownloadExcelService;
import com.freshremix.ui.model.DownloadExcelSettingForm;

/**
 * @author nvelasquez
 *
 */
public class DownloadExcelServiceImpl implements DownloadExcelService {
	
	private DownloadExcelDao downloadExcelDao;

	public void setDownloadExcelDao(DownloadExcelDao downloadExcelDao) {
		this.downloadExcelDao = downloadExcelDao;
	}
	
	@Override
	public DownloadExcelSettingForm getExcelSetting(Integer userId) {
		DownloadExcelSettingForm dxls = new DownloadExcelSettingForm();
		DownloadExcelSetting excelSetting = downloadExcelDao.getExcelSetting(userId);
		
		if (excelSetting == null) return null;
		
		List<String> dateFlag = new ArrayList<String>();
		String weeklyFlag = excelSetting.getWeeklyFlag();
		if (weeklyFlag.equals("1")) {
			for (int i=0; i<7; i++)
				dateFlag.add("1");
		}
		else {
			for (int i=0; i<7; i++) {
				if (i == 0)
					dateFlag.add("1");
				else
					dateFlag.add("0");
			}	
		}
		
		dxls.setDateFlag(dateFlag);
		dxls.setDateOpt(excelSetting.getDateOpt());
		dxls.setSellerOpt(excelSetting.getSellerOpt());
		dxls.setBuyerOpt(excelSetting.getBuyerOpt());
		dxls.setHasQty(excelSetting.getHasQty());
		dxls.setPriceComputeOpt(excelSetting.getPriceComputeOpt());
		
		return dxls;
	}
	
	@Override
	public void insertExcelSetting(Integer userId, DownloadExcelSettingForm dxls) {
		DownloadExcelSetting excelSetting = new DownloadExcelSetting();
		
		List<String> dateFlag = dxls.getDateFlag();
		String weeklyFlag = "1";
		for (int i=0; i<dateFlag.size(); i++) {
			if (dateFlag.get(i).equals("0")) {
				weeklyFlag = "0";
				break;
			}
		}
		
		excelSetting.setUserId(userId);
		excelSetting.setWeeklyFlag(weeklyFlag);
		excelSetting.setDateOpt(dxls.getDateOpt());
		excelSetting.setSellerOpt(dxls.getSellerOpt());
		excelSetting.setBuyerOpt(dxls.getBuyerOpt());
		excelSetting.setHasQty(dxls.getHasQty());
		excelSetting.setPriceComputeOpt(dxls.getPriceComputeOpt());

		downloadExcelDao.deleteExcelSetting(userId);
		downloadExcelDao.insertExcelSetting(excelSetting);

	}

}
