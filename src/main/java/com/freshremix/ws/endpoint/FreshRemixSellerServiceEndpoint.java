package com.freshremix.ws.endpoint;

import com.freshremix.model.WSSellerCreateSheetRequest;
import com.freshremix.model.WSSellerCreateSheetResponse;
import com.freshremix.model.WSSellerDeleteOrderSheetRequest;
import com.freshremix.model.WSSellerDeleteOrderSheetResponse;
import com.freshremix.model.WSSellerGetOrderSheetRequest;
import com.freshremix.model.WSSellerGetOrderSheetResponse;
import com.freshremix.model.WSSellerUpdateOrderSheetRequest;
import com.freshremix.model.WSSellerUpdateOrderSheetResponse;
import com.freshremix.service.WSSellerService;
import com.freshremix.ws.FreshRemixSellerService;
import com.sun.xml.ws.developer.SchemaValidation;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@SchemaValidation
@WebService(endpointInterface="com.freshremix.ws.FreshRemixSellerService", serviceName="FreshRemixSellerService", portName="FreshRemixSellerServicePort", targetNamespace="http://ws.freshremix.com.ph")
public class FreshRemixSellerServiceEndpoint
  extends BaseServiceEndpoint
  implements FreshRemixSellerService
{
  private WSSellerService wsSellerService;
  
  public WSSellerService getWsSellerService()
  {
    if (this.wsSellerService != null) {
      return this.wsSellerService;
    }
    return (WSSellerService)getBean("wsSellerService");
  }
  
  public void setWsSellerService(WSSellerService wsSellerService)
  {
    this.wsSellerService = wsSellerService;
  }
  
  public WSSellerCreateSheetResponse createSheet2(@WebParam(name="wsSellerCreateSheetRequest") WSSellerCreateSheetRequest requestObject)
  {
    WSSellerCreateSheetResponse response = getWsSellerService().createSheet(requestObject);
    
    return response;
  }
  
  @WebMethod
  @WebResult(name="wsSellerGetOrderSheetResponse")
  public WSSellerGetOrderSheetResponse getSellerOrderSheet2(@WebParam(name="wsSellerGetOrderSheetRequest") WSSellerGetOrderSheetRequest requestObject)
  {
    WSSellerGetOrderSheetResponse response = getWsSellerService().getOrderSheet(requestObject);
    return response;
  }
  
  @WebMethod
  @WebResult(name="wsSellerDeleteOrderSheetResponse")
  public WSSellerDeleteOrderSheetResponse deleteOrderSheet(@WebParam(name="wsSellerDeleteOrderSheetRequest") WSSellerDeleteOrderSheetRequest requestObject)
  {
    WSSellerDeleteOrderSheetResponse response = getWsSellerService().deleteOrderSheet(requestObject);
    
    return response;
  }
  
  @WebMethod
  @WebResult(name="wsSellerUpdateOrderSheetResponse")
  public WSSellerUpdateOrderSheetResponse updateOrderSheet(@WebParam(name="wsSellerUpdateOrderSheetRequest") WSSellerUpdateOrderSheetRequest requestObject)
  {
    WSSellerUpdateOrderSheetResponse response = getWsSellerService().updateOrderSheet(requestObject);
    
    return response;
  }
}
