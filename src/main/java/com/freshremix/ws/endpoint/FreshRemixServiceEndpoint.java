package com.freshremix.ws.endpoint;

import com.freshremix.model.AllocationInputDetails;
import com.freshremix.model.EONWebServiceResponse;
import com.freshremix.model.WSInputDetails;
import com.freshremix.ws.FreshRemixService;
import com.freshremix.ws.adapter.FreshRemixServiceAdapter;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class FreshRemixServiceEndpoint
  implements FreshRemixService
{
  @Resource
  private WebServiceContext context;
  private FreshRemixServiceAdapter freshRemixServiceAdapter;
  
  private Object getBean(String beanName)
  {
    ServletContext sc = (ServletContext)this.context.getMessageContext().get("javax.xml.ws.servlet.context");
    
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
    
    return ctx.getBean(beanName);
  }
  
  public FreshRemixServiceAdapter getFreshRemixServiceAdapter()
  {
    if (this.freshRemixServiceAdapter != null) {
      return this.freshRemixServiceAdapter;
    }
    return (FreshRemixServiceAdapter)getBean("freshRemixServiceAdapter");
  }
  
  public void setFreshRemixServiceAdapter(FreshRemixServiceAdapter freshRemixServiceAdapter)
  {
    this.freshRemixServiceAdapter = freshRemixServiceAdapter;
  }
  
  public EONWebServiceResponse createSheet(String username, String password, WSInputDetails[] wsInputDetails, String orderDate, boolean isPublish, boolean isFinalize)
  {
    return 
      getFreshRemixServiceAdapter().createSheet(username, password, wsInputDetails, orderDate, isPublish, isFinalize);
  }
  
  public EONWebServiceResponse addSkuAllocation(String username, String password, AllocationInputDetails[] allocationInputDetails, String orderDate)
  {
    return new EONWebServiceResponse();
  }
  
  public EONWebServiceResponse getSellerOrderSheet(String username, String password, String orderdate, Integer[] buyerIds)
  {
    return getFreshRemixServiceAdapter().getSellerOrder(username, password, buyerIds, orderdate);
  }
}
