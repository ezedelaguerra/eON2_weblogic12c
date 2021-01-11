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
 * Nov 5, 2010		raquino		
 */
package com.freshremix.ws;

import java.util.Date;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlRootElement;

import com.freshremix.model.AllocationItemSheetInfo;
import com.freshremix.model.EONWebServiceResponse;
import com.freshremix.model.LoginInfo;
import com.freshremix.model.ProductItemSheetInfo;

/**
 * @author raquino
 *
 */
@XmlRootElement
@HandlerChain(file="handler-chain.xml")
@WebService(targetNamespace = "http://ws.freshremix.com.ph")
public interface OldFreshRemixService {

	@WebMethod
	EONWebServiceResponse createSheet(
			@WebParam(name = "loginInfo")
			final LoginInfo loginInfo,
			@WebParam(name = "productItemSheetInfo")
			final ProductItemSheetInfo[] productItemSheetInfo,
			@WebParam(name = "orderDate")
			final Date orderDate,
			@WebParam(name = "finalizedFlg")
			final String finalizedFlg, 
			@WebParam(name = "systemName")
			final String systemName);
	
	EONWebServiceResponse getSellerOrderSheet(
			@WebParam(name = "loginInfo")
			final LoginInfo loginInfo, 
			@WebParam(name = "orderDate")
			final Date orderDate, 
			@WebParam(name = "buyerId")
			final Integer[] buyerId, 
			@WebParam(name = "systemName")
			final String systemName);
	
	EONWebServiceResponse newAddSKUAllocation(
			@WebParam(name = "loginInfo")
			final LoginInfo loginInfo, 
			@WebParam(name = "allocationItemSheetInfo")
			final AllocationItemSheetInfo[] allocationItemSheetInfo, 
			@WebParam(name = "orderDate")
			final Date orderDate, 
			@WebParam(name = "systemName")
			final String systemName);
	
	
}
