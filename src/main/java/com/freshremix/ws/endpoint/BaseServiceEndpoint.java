package com.freshremix.ws.endpoint;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class BaseServiceEndpoint
{
  @Resource
  private WebServiceContext context;
  
  public WebServiceContext getContext()
  {
    return this.context;
  }
  
  protected Object getBean(String beanName)
  {
    ServletContext sc = (ServletContext)this.context.getMessageContext().get("javax.xml.ws.servlet.context");
    
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
    
    return ctx.getBean(beanName);
  }
}
