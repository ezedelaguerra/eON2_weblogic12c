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
 * Mar 30, 2010		nvelasquez		
 */
package com.freshremix.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.DownloadExcelDao;
import com.freshremix.model.DownloadExcelSetting;

/**
 * @author nvelasquez
 *
 */
public class DownloadExcelDaoImpl extends SqlMapClientDaoSupport 
	implements DownloadExcelDao {

	@Override
	public DownloadExcelSetting getExcelSetting(Integer userId) {
		return (DownloadExcelSetting) getSqlMapClientTemplate().queryForObject("DownloadExcel.getExcelSetting", userId);
	}

	@Override
	public void insertExcelSetting(DownloadExcelSetting excelSetting) {
		getSqlMapClientTemplate().insert("DownloadExcel.insertExcelSetting", excelSetting);
	}
	
	@Override
	public void deleteExcelSetting(Integer userId) {
		getSqlMapClientTemplate().delete("DownloadExcel.deleteExcelSetting", userId);
	}
	
}
