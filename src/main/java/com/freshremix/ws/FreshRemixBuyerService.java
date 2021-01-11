package com.freshremix.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.freshremix.model.WSBuyerAddOrderSheetRequest;
import com.freshremix.model.WSBuyerAddOrderSheetResponse;
import com.freshremix.model.WSBuyerLoadOrderRequest;
import com.freshremix.model.WSBuyerLoadOrderResponse;

/**
 * 
 * This class is solely intended for Buyer related webservice.
 * 
 * Note that SchemaValidation is not mandatory for jax-ws. Since we are using
 * just the default jax-ws implementation in Weblogic/Tomcat, JAXB schema
 * validation will not work by default. It would be better to incorporate the
 * validations as part of your service rather than rely on JAXB schema
 * validations.
 * 
 * If you want schema validations for JAXB, try considering migrating to Metro or CXF.
 * 
 * @since v17.00
 */
@WebService
public interface FreshRemixBuyerService {

	@WebMethod
	@WebResult(name = "wsBuyerLoadOrderResponse")
	public WSBuyerLoadOrderResponse getBuyerOrderSheet(
			@WebParam(name = "wsBuyerLoadOrderRequest") WSBuyerLoadOrderRequest requestObject);

	@WebMethod
	@WebResult(name = "wsBuyerAddOrderSheetResponse")
	public WSBuyerAddOrderSheetResponse addBuyerOrderSheet(
			@WebParam(name = "wsBuyerAddOrderSheetRequest") WSBuyerAddOrderSheetRequest requestObject);

}
