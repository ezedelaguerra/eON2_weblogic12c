package com.freshremix.service;

import com.freshremix.exception.ServiceException;

public interface DownloadUserDetailsService {
	
	/**
	 * This service will create the file (e.g. CSV) in the system temp folder.  
	 * 
	 * The file path will be returned to the controller.
	 * 
	 * It would be up to the controller to retrieve the file and set the appropriate response headers.
	 * 
	 * 
	 * Possible exceptions thrown:
	 * 
	 * IOException when problems arise with File manipulation
	 * IllegalArgumentException: related to programming errors
	 * 
	 * @param format (CSV=implemented)
	 * @return the complete file path and file name of the generated file
	 * @throws ServiceException 
	 * 
	 */
	public String downloadUserDetails(String format) throws ServiceException;
}
