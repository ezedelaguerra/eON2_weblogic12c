package com.freshremix.ws.endpoint;

import com.freshremix.model.WSBuyerAddOrderSheetRequest;
import com.freshremix.model.WSBuyerAddOrderSheetResponse;
import com.freshremix.model.WSBuyerLoadOrderRequest;
import com.freshremix.model.WSBuyerLoadOrderResponse;
import com.freshremix.ws.FreshRemixBuyerService;
import com.freshremix.ws.adapter.FreshRemixBuyerServiceAdapter;
import com.sun.xml.ws.developer.SchemaValidation;
import javax.jws.WebParam;
import javax.jws.WebService;

@SchemaValidation
@WebService(endpointInterface="com.freshremix.ws.FreshRemixBuyerService", serviceName="FreshRemixBuyerService", portName="FreshRemixBuyerServicePort", targetNamespace="http://ws.freshremix.com.ph")
public class FreshRemixBuyerServiceEndpoint
  extends BaseServiceEndpoint
  implements FreshRemixBuyerService
{
  private FreshRemixBuyerServiceAdapter freshRemixBuyerServiceAdapter;
  
  public void setFreshRemixBuyerServiceAdapter(FreshRemixBuyerServiceAdapter freshRemixBuyerServiceAdapter)
  {
    this.freshRemixBuyerServiceAdapter = freshRemixBuyerServiceAdapter;
  }
  
  public FreshRemixBuyerServiceAdapter getFreshRemixServiceAdapter()
  {
    if (this.freshRemixBuyerServiceAdapter != null) {
      return this.freshRemixBuyerServiceAdapter;
    }
    return (FreshRemixBuyerServiceAdapter)getBean("freshRemixBuyerServiceAdapter");
  }
  
  public WSBuyerLoadOrderResponse getBuyerOrderSheet(@WebParam(name="wsBuyerLoadOrderRequest") WSBuyerLoadOrderRequest requestObject)
  {
    WSBuyerLoadOrderResponse response = getFreshRemixServiceAdapter().loadOrders(requestObject);
    return response;
  }
  
  public WSBuyerAddOrderSheetResponse addBuyerOrderSheet(@WebParam(name="wsBuyerAddOrderSheetRequest") WSBuyerAddOrderSheetRequest requestObject)
  {
    WSBuyerAddOrderSheetResponse response = getFreshRemixServiceAdapter().addOrders(requestObject);
    return response;
  }
}
