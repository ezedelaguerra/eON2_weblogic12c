package com.freshremix.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.freshremix.model.WSSellerCreateSheetRequest;
import com.freshremix.model.WSSellerCreateSheetResponse;
import com.freshremix.model.WSSellerDeleteOrderSheetRequest;
import com.freshremix.model.WSSellerDeleteOrderSheetResponse;
import com.freshremix.model.WSSellerGetOrderSheetRequest;
import com.freshremix.model.WSSellerGetOrderSheetResponse;
import com.freshremix.model.WSSellerUpdateOrderSheetRequest;
import com.freshremix.model.WSSellerUpdateOrderSheetResponse;
/**
 * This class is for updated functions of Seller Webservice
 * 
 * 
 * @author melissa
 *
 */
@WebService
public interface FreshRemixSellerService {

	@WebMethod
	@WebResult(name = "wsSellerCreateSheetResponse")
	public WSSellerCreateSheetResponse createSheet2(
			@WebParam(name = "wsSellerCreateSheetRequest") WSSellerCreateSheetRequest requestObject);

	
	@WebMethod
	@WebResult(name = "wsSellerGetOrderSheetResponse")
	public WSSellerGetOrderSheetResponse getSellerOrderSheet2(
			@WebParam(name = "wsSellerGetOrderSheetRequest") WSSellerGetOrderSheetRequest requestObject);

	@WebMethod
	@WebResult(name = "wsSellerDeleteOrderSheetResponse")
	public WSSellerDeleteOrderSheetResponse deleteOrderSheet(
			@WebParam(name = "wsSellerDeleteOrderSheetRequest") WSSellerDeleteOrderSheetRequest requestObject);

	@WebMethod
	@WebResult(name = "wsSellerUpdateOrderSheetResponse")
	public WSSellerUpdateOrderSheetResponse updateOrderSheet(
			@WebParam(name = "wsSellerUpdateOrderSheetRequest") WSSellerUpdateOrderSheetRequest requestObject);


}
