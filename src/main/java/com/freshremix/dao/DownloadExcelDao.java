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
package com.freshremix.dao;

import com.freshremix.model.DownloadExcelSetting;

/**
 * @author nvelasquez
 *
 */
public interface DownloadExcelDao {
	
	DownloadExcelSetting getExcelSetting(Integer userId);
	
	void insertExcelSetting(DownloadExcelSetting excelSetting);
	
	void deleteExcelSetting(Integer userId);
	
}
