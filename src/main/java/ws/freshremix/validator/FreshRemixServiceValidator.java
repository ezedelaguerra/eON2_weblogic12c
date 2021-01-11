package ws.freshremix.validator;

import com.freshremix.model.AllocationBuyerInformation;
import com.freshremix.model.AllocationInputDetails;
import com.freshremix.model.CheckList;
import com.freshremix.model.EONWebServiceResponse;
import com.freshremix.model.Order;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.model.WSBuyerInformation;
import com.freshremix.model.WSInputDetails;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.StringUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class FreshRemixServiceValidator
{
  public EONWebServiceResponse response = new EONWebServiceResponse();
  private EONLocale eonLocale;
  
  public void setEonLocale(EONLocale eonLocale)
  {
    this.eonLocale = eonLocale;
  }
  
  public FreshRemixServiceValidator()
  {
    writeResponse("0", "ws.message.success");
  }
  
  public void writeResponse(String errorCode, String errorMsg)
  {
    Locale locale = new Locale("ja", "JP");
    
    ResourceBundle rb = ResourceBundle.getBundle("com.freshremix.properties.error", locale);
    
    String newErrorMsg = StringUtil.toUTF8String(rb.getString(errorMsg));
    this.response.setErrorCode(errorCode);
    this.response.setErrorMsg(newErrorMsg);
  }
  
  public boolean isValidLogIn(User user)
  {
    boolean status = true;
    if (user == null)
    {
      writeResponse("1", "ws.message.error1");
      status = false;
    }
    return status;
  }
  
  public boolean isDateFinalized(String strDate)
  {
    boolean status = false;
    return status;
  }
  
  public boolean isSheetFinalized(List<Order> finalizedList)
  {
    boolean status = false;
    if ((finalizedList != null) && (!finalizedList.isEmpty())) {
      status = true;
    }
    return status;
  }
  
  public boolean isOrderFinalized(List<Order> finalizedList)
  {
    boolean status = false;
    if (isSheetFinalized(finalizedList))
    {
      status = true;
      writeResponse("13", "ws.message.error13");
    }
    return status;
  }
  
  public boolean isPreviousSheetFinalized(List<Order> finalizedList)
  {
    boolean status = true;
    if (!isSheetFinalized(finalizedList))
    {
      status = false;
      writeResponse("14", "ws.message.error14");
    }
    return status;
  }
  
  public boolean hasDealingPattern(Integer[] buyerIds, List<Integer> existingBuyerIds)
  {
    boolean status = true;
    for (Integer id : buyerIds) {
      if (!existingBuyerIds.contains(id))
      {
        status = false;
        writeResponse("3", "ws.message.error3");
        break;
      }
    }
    return status;
  }
  
  public boolean isFormReady(WSInputDetails[] wsInputDetails, String orderDate, List<Integer> allBuyerIds, List<UsersCategory> categoryList)
  {
    CheckList skuIdCheckList = new CheckList();
    for (WSInputDetails obj : wsInputDetails)
    {
      Map buyerValueMap = getBuyerValues(obj.getWsBuyerInformation());
      CheckList buyerIds = (CheckList)buyerValueMap.get("checkList");
      BigDecimal totalQty = (BigDecimal)buyerValueMap.get("totalQty");
      if (!isMandatoryFieldsReady(obj, categoryList))
      {
        writeResponse("2", "ws.message.error2");
        
        return false;
      }
      if (hasDuplicateEntry(wsInputDetails, obj))
      {
        writeResponse("12", "ws.message.error12");
        
        return false;
      }
      if (buyerIds.isDuplicateValue())
      {
        writeResponse("4", "ws.message.error4");
        
        return false;
      }
      if (!hasDealingPattern(buyerIds, allBuyerIds))
      {
        writeResponse("3", "ws.message.error3");
        
        return false;
      }
      if ((obj.getSkuMaxLimit() != null) && 
        (obj.getSkuMaxLimit().compareTo(totalQty) == -1))
      {
        writeResponse("6", "ws.message.error6");
        
        return false;
      }
      skuIdCheckList.add(obj.getSkuId());
    }
    if (skuIdCheckList.isDuplicateValue()) {
      writeResponse("11", "ws.message.error11");
    }
    return true;
  }
  
  private Map<String, Object> getBuyerValues(WSBuyerInformation[] wsBuyerInformation)
  {
    Map<String, Object> valuesMap = new HashMap();
    CheckList buyerList = new CheckList();
    BigDecimal totalQty = new BigDecimal(0);
    for (WSBuyerInformation buyer : wsBuyerInformation)
    {
      buyerList.add(buyer.getBuyerId());
      if (buyer.getQuantity() != null) {
        totalQty = totalQty.add(buyer.getQuantity());
      }
    }
    valuesMap.put("checkList", buyerList);
    valuesMap.put("totalQty", totalQty);
    
    return valuesMap;
  }
  
  private boolean isMandatoryFieldsReady(WSInputDetails wsInputDetails, List<UsersCategory> categoryList)
  {
    List<String> categories = getCategoryNames(categoryList);
    if (StringUtil.isNullOrEmpty(wsInputDetails.getSkuId())) {
      return false;
    }
    if (StringUtil.isNullOrEmpty(wsInputDetails.getSkuName())) {
      return false;
    }
    if (StringUtil.isNullOrEmpty(wsInputDetails.getSkuGroupName())) {
      return false;
    }
    if (StringUtil.isNullOrEmpty(wsInputDetails.getPackageQuantity())) {
      return false;
    }
    if (StringUtil.isNullOrEmpty(wsInputDetails.getPriceWithoutTax())) {
      return false;
    }
    if (StringUtil.isNullOrEmpty(wsInputDetails.getSkuCategoryName())) {
      return false;
    }
    if (!isCategoryValid(categories, wsInputDetails.getSkuCategoryName())) {
      return false;
    }
    return true;
  }
  
  private List<String> getCategoryNames(List<UsersCategory> categroylist)
  {
    List<String> categoryNames = new ArrayList();
    for (UsersCategory cat : categroylist) {
      categoryNames.add(cat.getCategoryAvailable());
    }
    return categoryNames;
  }
  
  private boolean hasDuplicateEntry(WSInputDetails[] wsInputDetails, WSInputDetails input)
  {
    for (WSInputDetails obj : wsInputDetails)
    {
      int status = 0;
      if (!input.getSkuId().equals(obj.getSkuId()))
      {
        if (!input.getSkuName().equals(obj.getSkuName())) {
          status = 1;
        } else if (!input.getSkuCategoryName().equals(obj.getSkuCategoryName())) {
          status = 1;
        } else if (!input.getLocation().equals(obj.getLocation())) {
          status = 1;
        } else if (!input.getMarket().equals(obj.getMarket())) {
          status = 1;
        } else if (!input.getClass2().equals(obj.getClass2())) {
          status = 1;
        } else if (!input.getClass1().equals(obj.getClass1())) {
          status = 1;
        } else if (!input.getPackageQuantity().equals(obj
          .getPackageQuantity())) {
          status = 1;
        } else if (!input.getPackageType().equals(obj.getPackageType())) {
          status = 1;
        } else if (!input.getPrice1().equals(obj.getPrice1())) {
          status = 1;
        } else if (!input.getPrice2().equals(obj.getPrice2())) {
          status = 1;
        } else if (!input.getPriceWithoutTax().equals(obj
          .getPriceWithoutTax())) {
          status = 1;
        } else if (!input.getUnitOfOrder().equals(obj.getUnitOfOrder())) {
          status = 1;
        }
        if (status == 0) {
          return false;
        }
      }
    }
    return false;
  }
  
  private boolean hasDealingPattern(CheckList buyerIds, List<Integer> allBuyerIds)
  {
    for (Object buyerId : buyerIds) {
      if (!allBuyerIds.contains(buyerId)) {
        return false;
      }
    }
    return true;
  }
  
  public boolean isPublished(List<Order> publishedOrders)
  {
    boolean status = true;
    if ((publishedOrders == null) || (publishedOrders.isEmpty())) {
      status = false;
    }
    return status;
  }
  
  public boolean isValidProcess(List<Order> order, boolean isPublish, boolean isFinalize)
  {
    boolean status = true;
    boolean isAlreadyPublished = false;
    if (isPublish)
    {
      isAlreadyPublished = isPublished(order);
      if (isAlreadyPublished)
      {
        writeResponse("16", "ws.message.error16");
        
        status = false;
      }
    }
    if ((!isPublish) && (isFinalize))
    {
      isAlreadyPublished = isPublished(order);
      if (!isAlreadyPublished)
      {
        writeResponse("17", "ws.message.error17");
        
        status = false;
      }
    }
    return status;
  }
  
  public boolean isAllocationFormReady(AllocationInputDetails[] allocationInputDetails, String orderDate, List<Integer> allBuyerIds, List<UsersCategory> categoryList)
  {
    CheckList skuIdCheckList = new CheckList();
    for (AllocationInputDetails obj : allocationInputDetails)
    {
      Map<String, Object> buyerValueMap = getAllocationBuyerValues(obj
        .getAllocationBuyerInformation());
      CheckList buyerIds = (CheckList)buyerValueMap.get("checkList");
      BigDecimal totalQty = (BigDecimal)buyerValueMap.get("totalQty");
      if (!isMandatoryAllocationFieldsReady(obj, categoryList))
      {
        if (this.response.getErrorCode().equals("0")) {
          writeResponse("2", "ws.message.error2");
        }
        return false;
      }
      if (buyerIds.isDuplicateValue())
      {
        writeResponse("4", "ws.message.error4");
        
        return false;
      }
      if (!hasDealingPattern(buyerIds, allBuyerIds))
      {
        writeResponse("3", "ws.message.error3");
        
        return false;
      }
      if (obj.getSkuMaxLimit().compareTo(totalQty) == -1)
      {
        writeResponse("6", "ws.message.error6");
        
        return false;
      }
      skuIdCheckList.add(obj.getSkuId());
    }
    if (skuIdCheckList.isDuplicateValue()) {
      writeResponse("11", "ws.message.error11");
    }
    return true;
  }
  
  private boolean isMandatoryAllocationFieldsReady(AllocationInputDetails allocationInputDetails, List<UsersCategory> categoryList)
  {
    List<String> categories = getCategoryNames(categoryList);
    if (StringUtil.isNullOrEmpty(allocationInputDetails.getSkuId())) {
      return false;
    }
    if (StringUtil.isNullOrEmpty(allocationInputDetails.getSkuName())) {
      return false;
    }
    if (StringUtil.isNullOrEmpty(allocationInputDetails.getSkuGroupName())) {
      return false;
    }
    if (StringUtil.isNullOrEmpty(allocationInputDetails
      .getPackageQuantity())) {
      return false;
    }
    if (!NumberUtil.isNumeric(allocationInputDetails
      .getPackageQuantity())) {
      return false;
    }
    if (StringUtil.isNullOrEmpty(allocationInputDetails
      .getPriceWithoutTax())) {
      return false;
    }
    if (StringUtil.isNullOrEmpty(allocationInputDetails.getSkuCategoryName())) {
      return false;
    }
    if (!isCategoryValid(categories, allocationInputDetails.getSkuCategoryName())) {
      return false;
    }
    if (StringUtil.isNullOrEmpty(allocationInputDetails.getUnitOfOrder())) {
      return false;
    }
    return true;
  }
  
  private boolean isCategoryValid(List<String> categoryNames, String categoryName)
  {
    boolean status = true;
    if (!categoryNames.contains(categoryName))
    {
      status = false;
      writeResponse("10", "ws.message.error10");
    }
    return status;
  }
  
  private Map<String, Object> getAllocationBuyerValues(AllocationBuyerInformation[] allocationBuyerInformation)
  {
    Map<String, Object> valuesMap = new HashMap();
    CheckList buyerList = new CheckList();
    BigDecimal totalQty = new BigDecimal(0);
    for (AllocationBuyerInformation buyer : allocationBuyerInformation)
    {
      buyerList.add(buyer.getBuyerId());
      totalQty = totalQty.add(buyer.getQuantity());
    }
    valuesMap.put("checkList", buyerList);
    valuesMap.put("totalQty", totalQty);
    
    return valuesMap;
  }
  
  public boolean isAllocationSheetFinalized(List<Order> finalizedList)
  {
    boolean status = false;
    if ((finalizedList != null) && (!finalizedList.isEmpty()))
    {
      status = true;
      writeResponse("13", "ws.message.error13");
    }
    return status;
  }
}
