package com.freshremix.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlRootElement;

import com.freshremix.model.AllocationInputDetails;
import com.freshremix.model.EONWebServiceResponse;
import com.freshremix.model.WSInputDetails;

@XmlRootElement
@WebService(targetNamespace = "http://ws.freshremix.com.ph")
public interface FreshRemixService {
	@WebMethod
	EONWebServiceResponse createSheet(@WebParam(name = "username") 
			final String username,@WebParam(name = "password") final String password,@WebParam(name = "SKUInformation")
			final WSInputDetails[] wsInputDetails,@WebParam(name = "orderdate")final String orderDate,
			@WebParam(name = "publish")boolean isPublish,@WebParam(name = "finalize")boolean isFinalized);
			
	
	
	@WebMethod
	EONWebServiceResponse getSellerOrderSheet(@WebParam(name = "username") final String username,
			@WebParam(name = "password")final String password,@WebParam(name = "orderDate")final String orderdate,@WebParam(name = "buyerIds")final Integer[] buyerIds);
	
	@WebMethod
	EONWebServiceResponse addSkuAllocation(@WebParam(name = "username") 
			final String username,@WebParam(name = "password") final String password,@WebParam(name = "SKUInformation")
			final AllocationInputDetails[] allocationInputDetails,@WebParam(name = "orderdate")final String orderDate);
	
}
