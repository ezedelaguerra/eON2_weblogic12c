package com.freshremix.ws.endpoint;

import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.model.AllocationItemSheetInfo;
import com.freshremix.model.BuyerEntityInfo;
import com.freshremix.model.EONWebServiceResponse;
import com.freshremix.model.ItemInfoVisibility;
import com.freshremix.model.LoginInfo;
import com.freshremix.model.ProductItemSheetInfo;
import com.freshremix.model.SKUInfoWithAltPrice;
import com.freshremix.model.WSBuyerInformation;
import com.freshremix.model.WSInputDetails;
import com.freshremix.model.WSSKU;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.MessageUtil;
import com.freshremix.util.StringUtil;
import com.freshremix.ws.OldFreshRemixService;
import com.freshremix.ws.adapter.FreshRemixServiceAdapter;
import org.apache.commons.lang.ArrayUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;

@WebService(serviceName="FreshRemixService", portName="FreshRemixServicePort", targetNamespace="http://ws.freshremix.com.ph/", endpointInterface="com.freshremix.ws.OldFreshRemixService")
public class OldFreshRemixServiceEndpoint
  extends BaseServiceEndpoint
  implements OldFreshRemixService
{
  private FreshRemixServiceAdapter freshRemixServiceAdapter;
  
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
  
  public EONWebServiceResponse createSheet(LoginInfo loginInfo, ProductItemSheetInfo[] productItemSheetInfo, Date orderDate, String finalizedFlg, String systemName)
  {

    List<WSInputDetails> wsInputDetailsArrayList = new ArrayList<WSInputDetails>();
    for (int i = 0; i < productItemSheetInfo.length; i++)
    {
      WSInputDetails wsInputDetail = new WSInputDetails();
      ProductItemSheetInfo prodItemSheetInfo = productItemSheetInfo[i];
      wsInputDetail.setClass1(prodItemSheetInfo.getClass1());
      wsInputDetail.setClass2(prodItemSheetInfo.getClass2());
      wsInputDetail.setLocation(prodItemSheetInfo.getAreaOfProduction());
      wsInputDetail.setMarket(prodItemSheetInfo.getMarketCondition());
      wsInputDetail.setPackageType(prodItemSheetInfo.getPackType());
      try
      {
        if (StringUtil.isNullOrEmpty(prodItemSheetInfo.getAltPrice1())) {
          wsInputDetail.setPrice1(null);
        } else {
          wsInputDetail.setPrice1(BigDecimal.valueOf(
            Long.parseLong(prodItemSheetInfo.getAltPrice1())));
        }
        if (StringUtil.isNullOrEmpty(prodItemSheetInfo.getAltPrice2())) {
          wsInputDetail.setPrice2(null);
        } else {
          wsInputDetail.setPrice2(BigDecimal.valueOf(
            Long.parseLong(prodItemSheetInfo.getAltPrice2())));
        }
        if (StringUtil.isNullOrEmpty(prodItemSheetInfo.getUnitPriceWithoutTax())) {
          wsInputDetail.setPriceWithoutTax(null);
        } else {
          wsInputDetail.setPriceWithoutTax(BigDecimal.valueOf(
            Long.parseLong(prodItemSheetInfo.getUnitPriceWithoutTax())));
        }
        wsInputDetail.setPackageQuantity(BigDecimal.valueOf(
          Long.parseLong(prodItemSheetInfo.getPackQuantity())));
        wsInputDetail.setSkuId(Integer.valueOf(Integer.parseInt(prodItemSheetInfo
          .getSkuId())));
        if (!StringUtil.isNullOrEmpty(prodItemSheetInfo.getSellerId())) {
          wsInputDetail.setSellerId(Integer.valueOf(Integer.parseInt(prodItemSheetInfo
            .getSellerId())));
        }
      }
      catch (NumberFormatException e)
      {
        e.printStackTrace();
        EONWebServiceResponse err = new EONWebServiceResponse();
        err.setErrorCode("1");
        err.setErrorMsg("Invalid Number Format: " + e.getMessage());
        return err;
      }
      if (!StringUtil.isNullOrEmpty(prodItemSheetInfo.getSellerName())) {
        wsInputDetail.setSellerName(prodItemSheetInfo.getSellerName());
      }
      wsInputDetail.setSkuCategoryName(prodItemSheetInfo.getSkuCategory());
      wsInputDetail.setSkuExternalID(prodItemSheetInfo.getSkuExternalId());
      wsInputDetail.setSkuGroupName(prodItemSheetInfo.getSkuGroupName());
      wsInputDetail.setSkuMaxLimit(null);
      wsInputDetail.setSkuName(prodItemSheetInfo.getDesc());
      wsInputDetail.setUnitOfOrder(prodItemSheetInfo.getUom());
      
      ItemInfoVisibility[] itemInfoVisibilities = prodItemSheetInfo.getItemInfoVisibility();
      WSBuyerInformation[] wsBuyerInformations = new WSBuyerInformation[itemInfoVisibilities.length];
      for (int j = 0; j < itemInfoVisibilities.length; j++)
      {
        WSBuyerInformation wsBuyerInformation = new WSBuyerInformation();
        ItemInfoVisibility itemInfoVisibility = itemInfoVisibilities[j];
        wsBuyerInformation.setBuyerId(itemInfoVisibility.getBuyerId());
        try
        {
          if (StringUtil.isNullOrEmpty(itemInfoVisibility.getQuantity())) {
            wsBuyerInformation.setQuantity(null);
          } else {
            wsBuyerInformation.setQuantity(BigDecimal.valueOf(
              Long.parseLong(itemInfoVisibility.getQuantity())));
          }
        }
        catch (NumberFormatException e)
        {
          e.printStackTrace();
          EONWebServiceResponse err = new EONWebServiceResponse();
          err.setErrorCode("1");
          err.setErrorMsg("Invalid Number Format: " + e.getMessage());
          return err;
        }
        wsBuyerInformation.setVisible(itemInfoVisibility
          .getVisibilityFlag());
        wsBuyerInformations[j] = wsBuyerInformation;
      }
      wsInputDetail.setWsBuyerInformation(wsBuyerInformations);


      if(this.isEmpty(wsInputDetailsArrayList)){
        //wsInputDetails[i] = wsInputDetail;
        wsInputDetailsArrayList.add(wsInputDetail);
      } else{

        boolean isExisting = false;
        for (int x = 0; x < wsInputDetailsArrayList.size(); x++) {
          WSInputDetails existing = wsInputDetailsArrayList.get(x);

          if(existing == null) continue;

          if(existing.equals(wsInputDetail)){
            isExisting = true;
            //b_ca02, b_ca03
            WSBuyerInformation[] existingWbi = existing.getWsBuyerInformation();

            //b_ca01, b_ca02
            WSBuyerInformation[] wbi = wsInputDetail.getWsBuyerInformation();

            WSBuyerInformation[] newWbi = existingWbi;

            // add b_ca01
            // add quantity b_ca02
            // add b_ca03
            for (int wCounter = 0; wCounter < wbi.length; wCounter++) {
              boolean isBuyerExists = false;
              int existingIndex = 0;
              for (int eCounter = 0; eCounter < existingWbi.length; eCounter++) {
                if(existingWbi[eCounter].equals(wbi[wCounter])){
                  isBuyerExists = true;
                  existingIndex = eCounter;
                  break;
                }
              }

              if(isBuyerExists){
                //add quantity
                WSBuyerInformation tempw = new WSBuyerInformation();
                tempw.setBuyerId(wbi[wCounter].getBuyerId());
                tempw.setVisible(wbi[wCounter].isVisible());
                tempw.setQuantity(wbi[wCounter].getQuantity().add(existingWbi[existingIndex].getQuantity()));
                newWbi[existingIndex] = tempw;
              } else {
                //add
                WSBuyerInformation[] tempWbi = {wbi[wCounter]};
                newWbi = (WSBuyerInformation[]) ArrayUtils.addAll(newWbi, tempWbi);
              }
            }

            //TODO wsInput is null
            //wsInputDetails[x].setWsBuyerInformation(newWbi);
            wsInputDetailsArrayList.get(x).setWsBuyerInformation(newWbi);

          }
        }
        if(!isExisting){
          //wsInputDetails[i] = wsInputDetail;
          wsInputDetailsArrayList.add(wsInputDetail);
        }
      }
    }
    WSInputDetails[] wsInputDetails = wsInputDetailsArrayList.toArray(new WSInputDetails[wsInputDetailsArrayList.size()]);
    boolean isPublish = false;
    boolean isFinalize = false;
    String strOrderDate = null;
    if (orderDate != null)
    {
      strOrderDate = DateFormatter.convertToString(orderDate);
    }
    else
    {
      EONWebServiceResponse err = new EONWebServiceResponse();
      err.setErrorCode("3");
      err.setErrorMsg(MessageUtil.getPropertyMessage("ws.message.error.invalid.orderdate"));
      return err;
    }
    if (finalizedFlg.equals("1")) {
      isPublish = true;
    }
    EONWebServiceResponse eONWebServiceResponse = getFreshRemixServiceAdapter().createSheet(loginInfo.getUsername(), loginInfo.getPassword(), wsInputDetails, strOrderDate, isPublish, isFinalize);
    
    return eONWebServiceResponse;
  }
  
  public EONWebServiceResponse newAddSKUAllocation(LoginInfo loginInfo, AllocationItemSheetInfo[] allocationItemSheetInfo, Date orderDate, String systemName)
  {
    List<WSInputDetails> wsInputDetailsArrayList = new ArrayList<WSInputDetails>();
    for (int i = 0; i < allocationItemSheetInfo.length; i++)
    {
      WSInputDetails wsInputDetail = new WSInputDetails();
      AllocationItemSheetInfo allocItemSheetInfo = allocationItemSheetInfo[i];
      wsInputDetail.setClass1(allocItemSheetInfo.getClass1());
      wsInputDetail.setClass2(allocItemSheetInfo.getClass2());
      wsInputDetail.setLocation(allocItemSheetInfo.getAreaOfProduction());
      wsInputDetail.setMarket(allocItemSheetInfo.getMarketCondition());
      wsInputDetail.setPackageType(allocItemSheetInfo.getPackType());
      try
      {
        wsInputDetail.setPackageQuantity(BigDecimal.valueOf(
          Long.parseLong(allocItemSheetInfo.getPackQuantity())));
        if (StringUtil.isNullOrEmpty(allocItemSheetInfo.getAltPrice1())) {
          wsInputDetail.setPrice1(null);
        } else {
          wsInputDetail.setPrice1(BigDecimal.valueOf(
            Long.parseLong(allocItemSheetInfo.getAltPrice1())));
        }
        if (StringUtil.isNullOrEmpty(allocItemSheetInfo.getAltPrice2())) {
          wsInputDetail.setPrice2(null);
        } else {
          wsInputDetail.setPrice2(BigDecimal.valueOf(
            Long.parseLong(allocItemSheetInfo.getAltPrice2())));
        }
        if (StringUtil.isNullOrEmpty(allocItemSheetInfo.getUnitPriceWithoutTax())) {
          wsInputDetail.setPriceWithoutTax(null);
        } else {
          wsInputDetail.setPriceWithoutTax(BigDecimal.valueOf(
            Long.parseLong(allocItemSheetInfo.getUnitPriceWithoutTax())));
        }
        wsInputDetail.setSkuId(Integer.valueOf(Integer.parseInt(allocItemSheetInfo
          .getSkuId())));
        if (!StringUtil.isNullOrEmpty(allocItemSheetInfo.getSellerId())) {
          wsInputDetail.setSellerId(Integer.valueOf(Integer.parseInt(allocItemSheetInfo.getSellerId())));
        }
      }
      catch (NumberFormatException e)
      {
        e.printStackTrace();
        EONWebServiceResponse err = new EONWebServiceResponse();
        err.setErrorCode("1");
        err.setErrorMsg("Invalid Number Format: " + e.getMessage());
        return err;
      }
      if (!StringUtil.isNullOrEmpty(allocItemSheetInfo.getSellerName())) {
        wsInputDetail.setSellerName(allocItemSheetInfo.getSellerName());
      }
      wsInputDetail.setSkuCategoryName(allocItemSheetInfo
        .getSkuCategory());
      wsInputDetail.setSkuExternalID(allocItemSheetInfo.getSkuExternalId());
      wsInputDetail.setSkuGroupName(allocItemSheetInfo.getSkuGroupName());
      wsInputDetail.setSkuMaxLimit(null);
      wsInputDetail.setSkuName(allocItemSheetInfo.getDesc());
      wsInputDetail.setUnitOfOrder(allocItemSheetInfo.getUom());
      
      BuyerEntityInfo[] wsBuyerInfoInputs = allocItemSheetInfo.getBuyerEntityInfo();
      WSBuyerInformation[] wsBuyerInformations = new WSBuyerInformation[wsBuyerInfoInputs.length];
      for (int j = 0; j < wsBuyerInfoInputs.length; j++)
      {
        WSBuyerInformation wsBuyerInformation = new WSBuyerInformation();
        BuyerEntityInfo wsBuyerInfoInput = wsBuyerInfoInputs[j];
        wsBuyerInformation.setBuyerId(wsBuyerInfoInput.getBuyerId());
        try
        {
          if (StringUtil.isNullOrEmpty(wsBuyerInfoInput.getQuantity())) {
            wsBuyerInformation.setQuantity(null);
          } else {
            wsBuyerInformation.setQuantity(BigDecimal.valueOf(
              Long.parseLong(wsBuyerInfoInput.getQuantity())));
          }
        }
        catch (NumberFormatException e)
        {
          e.printStackTrace();
          EONWebServiceResponse err = new EONWebServiceResponse();
          err.setErrorCode("1");
          err.setErrorMsg("Invalid Number Format: " + e.getMessage());
          return err;
        }
        wsBuyerInformations[j] = wsBuyerInformation;
      }
      wsInputDetail.setWsBuyerInformation(wsBuyerInformations);
      if(this.isEmpty(wsInputDetailsArrayList)){
        //wsInputDetails[i] = wsInputDetail;
        wsInputDetailsArrayList.add(wsInputDetail);
      } else{

        boolean isExisting = false;
        for (int x = 0; x < wsInputDetailsArrayList.size(); x++) {
          WSInputDetails existing = wsInputDetailsArrayList.get(x);

          if(existing == null) continue;

          if(existing.equals(wsInputDetail)){
            isExisting = true;
            //b_ca02, b_ca03
            WSBuyerInformation[] existingWbi = existing.getWsBuyerInformation();

            //b_ca01, b_ca02
            WSBuyerInformation[] wbi = wsInputDetail.getWsBuyerInformation();

            WSBuyerInformation[] newWbi = existingWbi;

            // add b_ca01
            // add quantity b_ca02
            // add b_ca03
            for (int wCounter = 0; wCounter < wbi.length; wCounter++) {
              boolean isBuyerExists = false;
              int existingIndex = 0;
              for (int eCounter = 0; eCounter < existingWbi.length; eCounter++) {
                if(existingWbi[eCounter].equals(wbi[wCounter])){
                  isBuyerExists = true;
                  existingIndex = eCounter;
                  break;
                }
              }

              if(isBuyerExists){
                //add quantity
                WSBuyerInformation tempw = new WSBuyerInformation();
                tempw.setBuyerId(wbi[wCounter].getBuyerId());
                tempw.setVisible(wbi[wCounter].isVisible());
                tempw.setQuantity(wbi[wCounter].getQuantity().add(existingWbi[existingIndex].getQuantity()));
                newWbi[existingIndex] = tempw;
              } else {
                //add
                WSBuyerInformation[] tempWbi = {wbi[wCounter]};
                newWbi = (WSBuyerInformation[]) ArrayUtils.addAll(newWbi, tempWbi);
              }
            }

            //TODO wsInput is null
            //wsInputDetails[x].setWsBuyerInformation(newWbi);
            wsInputDetailsArrayList.get(x).setWsBuyerInformation(newWbi);

          }
        }
        if(!isExisting){
          //wsInputDetails[i] = wsInputDetail;
          wsInputDetailsArrayList.add(wsInputDetail);
        }
      }
    }
    WSInputDetails[] wsInputDetails = wsInputDetailsArrayList.toArray(new WSInputDetails[wsInputDetailsArrayList.size()]);
    String strOrderDate = null;
    if (orderDate != null)
    {
      strOrderDate = DateFormatter.convertToString(orderDate);
    }
    else
    {
      EONWebServiceResponse err = new EONWebServiceResponse();
      err.setErrorCode("3");
      err.setErrorMsg(MessageUtil.getPropertyMessage("ws.message.error.required.orderdate"));
      return err;
    }
    EONWebServiceResponse eONWebServiceResponse = getFreshRemixServiceAdapter().addSKUAllocation(loginInfo.getUsername(), loginInfo
      .getPassword(), wsInputDetails, strOrderDate);
    
    return eONWebServiceResponse;
  }
  
  public EONWebServiceResponse getSellerOrderSheet(LoginInfo loginInfo, Date orderDate, Integer[] buyerIds, String systemName)
  {
    EONWebServiceResponse err = new EONWebServiceResponse();
    SKUInfoWithAltPrice[] skuInfoWithAltPrices = new SKUInfoWithAltPrice[1];
    
    String strOrderDate = null;
    if (orderDate != null)
    {
      strOrderDate = DateFormatter.convertToString(orderDate);
    }
    else
    {
      err.setErrorCode("3");
      err.setErrorMsg(MessageUtil.getPropertyMessage("ws.message.error.required.orderdate"));
      
      return err;
    }
    EONWebServiceResponse eONWebServiceResponse = getFreshRemixServiceAdapter().getSellerOrder(loginInfo.getUsername(), loginInfo
      .getPassword(), buyerIds, strOrderDate);
    WSSKU[] wsskus = eONWebServiceResponse.getSkuObjectReturn();
    if ((wsskus != null) && (wsskus.length != 0))
    {
      skuInfoWithAltPrices = new SKUInfoWithAltPrice[wsskus.length];
      for (int i = 0; i < wsskus.length; i++)
      {
        SKUInfoWithAltPrice skuInfoWithAltPrice = new SKUInfoWithAltPrice();
        WSSKU wssku = wsskus[i];
        if (wssku.getPrice1() == null) {
          skuInfoWithAltPrice.setPrice1(null);
        } else {
          skuInfoWithAltPrice.setPrice1(wssku.getPrice1().toString());
        }
        if (wssku.getPrice2() == null) {
          skuInfoWithAltPrice.setPrice2(null);
        } else {
          skuInfoWithAltPrice.setPrice2(wssku.getPrice2().toString());
        }
        skuInfoWithAltPrice.setAreaOfProd(wssku.getLocation());
        skuInfoWithAltPrice
          .setBuyerID(Integer.valueOf(Integer.parseInt(wssku.getBuyerId())));
        skuInfoWithAltPrice.setBuyerName(wssku.getBuyerName());
        skuInfoWithAltPrice.setClass1(wssku.getGrade());
        skuInfoWithAltPrice.setClass2(wssku.getClazz());
        skuInfoWithAltPrice.setDesc(wssku.getSkuName());
        skuInfoWithAltPrice.setFftSkuId(Integer.valueOf(Integer.parseInt(wssku.getSkuId())));
        skuInfoWithAltPrice.setMarketCondition(wssku.getMarket());
        skuInfoWithAltPrice.setOrderDate(orderDate);
        skuInfoWithAltPrice.setPackQuantity(wssku.getPackageQuantity()
          .toString());
        skuInfoWithAltPrice.setPackType(wssku.getPackageType());
        skuInfoWithAltPrice.setQuantity(StringUtil.nullToBlank(wssku.getQty()));
        skuInfoWithAltPrice.setSellerID(Integer.valueOf(Integer.parseInt(wssku
          .getSellerId())));
        skuInfoWithAltPrice.setSellerName(wssku.getSellerName());
        skuInfoWithAltPrice
          .setSheetType(SheetTypeConstants.SELLER_ORDER_SHEET);
        skuInfoWithAltPrice.setCategory(wssku.getSkuCategoryName());
        skuInfoWithAltPrice.setSkuExternalID(wssku.getExternalSkuId());
        if (wssku.getSkuGroupId() != null) {
          skuInfoWithAltPrice.setSkuGroupID(new Integer(wssku.getSkuGroupId()));
        }
        skuInfoWithAltPrice.setSkuGroupName(wssku.getSkuGroupName());
        if (wssku.getPriceWithoutTax() == null) {
          skuInfoWithAltPrice.setUnitPriceWithoutTax(null);
        } else {
          skuInfoWithAltPrice.setUnitPriceWithoutTax(wssku
            .getPriceWithoutTax().toString());
        }
        if (wssku.getPriceWithTax() == null) {
          skuInfoWithAltPrice.setUnitPriceWithTax(null);
        } else {
          skuInfoWithAltPrice.setUnitPriceWithTax(Integer.valueOf(Integer.parseInt(wssku
            .getPriceWithTax().setScale(0, 4).toString())));
        }
        skuInfoWithAltPrice.setUom(wssku.getUnitOfOrder());
        skuInfoWithAltPrice.setSkuVisible(Integer.valueOf(wssku.getVisibilityFlag()).intValue());
        skuInfoWithAltPrices[i] = skuInfoWithAltPrice;
      }
    }
    eONWebServiceResponse.setSku(skuInfoWithAltPrices);
    eONWebServiceResponse.setSkuObjectReturn(null);
    
    return eONWebServiceResponse;
  }

  private boolean isEmpty(List<WSInputDetails> wsInputDetails){
    boolean flag = true;
    for (int x = 0; x < wsInputDetails.size(); x++) {
      if(wsInputDetails.get(x) != null){
        flag = false;
      }
    }
    return flag;
  }
}
