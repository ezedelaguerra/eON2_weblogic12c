package com.freshremix.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.freshremix.dao.DelegateDataRowHandler;
import com.freshremix.dao.UserDao;
import com.freshremix.dao.impl.AbstractCSVFileRowDataHandler;
import com.freshremix.dao.impl.CSVUserRowDataHandler;
import com.freshremix.dao.impl.DelegatingRowHandler;
import com.freshremix.dao.impl.FileRowDataHandler;
import com.freshremix.exception.ServiceException;
import com.freshremix.service.DownloadUserDetailsService;

public class DownloadUserDetailsServiceImpl implements DownloadUserDetailsService{

	private static final String CSV = "CSV";
	private static final Logger LOGGER = Logger.getLogger(DownloadUserDetailsServiceImpl.class);
	private static final List<String> VALID_FORMAT = Arrays.asList(CSV);
	private UserDao userDao;
	
	
	@Required
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.freshremix.service.DownloadUserDetailsService#downloadUserDetails(java.lang.String)
	 */
	@Override
	public String downloadUserDetails(String format) throws ServiceException {
		LOGGER.info("Download of User Details...start");
		
		validateFormat(format);
		
		DelegatingRowHandler<Map<String, String>> delegatingRowHandler = setupDelegateRowHandler(format);
		
		userDao.getAllNonAdminUsers(delegatingRowHandler);
		
		String fileName = retrieveFileName(delegatingRowHandler);
		
		LOGGER.info("Download of User Details...end");
		return fileName;
	}

	private String retrieveFileName(
			DelegatingRowHandler<Map<String, String>> delegatingRowHandler) throws ServiceException {
		List<DelegateDataRowHandler<Map<String, String>>> delegateList = delegatingRowHandler.getDelegateList();
		
		DelegateDataRowHandler<Map<String, String>> delegate = delegateList
				.get(0);
		AbstractCSVFileRowDataHandler<Map<String, String>> fileHandler = (AbstractCSVFileRowDataHandler<Map<String, String>>) delegate;
		File csvFile = fileHandler.getCsvFile();
		String fileName="";
		try {
			fileName = csvFile.getCanonicalPath();
		} catch (IOException e) {
			throw new ServiceException("Unable to get the file name", e);
		}
		return fileName;
	}

	private void validateFormat(String format) {
		if (StringUtils.isBlank(format)){
			throw new IllegalArgumentException("Download format is null");
		}
		
		if (!VALID_FORMAT.contains(format)) {
			throw new IllegalArgumentException("Download format is not supported");
		}
	}

	private DelegatingRowHandler<Map<String, String>> setupDelegateRowHandler(
			String format) throws ServiceException {
		
		//instantiate the delegating row handler
		DelegatingRowHandler<Map<String, String>> delegatingRowHandler = new DelegatingRowHandler<Map<String, String>>();

		FileRowDataHandler<Map<String, String>> delegate = delegateFactory(format);
		try {
			delegate.initializeFile(null);
		} catch (Exception e) {
			LOGGER.info("Exception encountered:"+e.getMessage());
			throw new ServiceException("Unable to initialize the file", e);
		}
		
		delegatingRowHandler.addDelegate(delegate);
		return delegatingRowHandler;
	}
	

	/*
	 * Refactor to another class if this method gets big.
	 */
	private FileRowDataHandler<Map<String, String>> delegateFactory(String format){
		FileRowDataHandler<Map<String, String>> delegate = null;
		if (format.equals(CSV)){
			delegate = new CSVUserRowDataHandler();
		}
		return delegate;
	}
	
}
