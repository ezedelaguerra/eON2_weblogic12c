package com.freshremix.service;

import com.freshremix.exception.ServiceException;
import com.freshremix.model.TermsOfService;
import com.freshremix.model.TermsOfService.TOSState;

public interface TOSAdminService {
	/**
	 * Retrieves the latest TOS record.  
	 * @return  Null when there is no record retrieved
	 * @throws ServiceException
	 */
	public TermsOfService getLatestTOS() throws ServiceException;
	
	/**
	 * Creates the Terms of Service record
	 * 
	 * @param tos
	 * @return
	 * @throws ServiceException
	 */
	public TermsOfService createTermsOfService(TermsOfService tos) throws ServiceException;
	
	
	/**
	 * Updates the terms of service record.
	 * @param tos
	 * @return
	 * @throws ServiceException
	 */
	public TermsOfService updateTermsOfService(TermsOfService tos) throws ServiceException;

	/**
	 * Determines the state of the TOS whether it is new or old.
	 * 
	 * NEW if Created date is <= MAX number of days as New state  OR if tos create date is null
	 * OLD otherwise
	 * 
	 * @param tos
	 * @return
	 */
	public TOSState getTOSState(TermsOfService tos);
	
}
