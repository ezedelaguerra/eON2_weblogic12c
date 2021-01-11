package com.freshremix.ws.adapter;

import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.model.AllocationInputDetails;
import com.freshremix.model.Category;
import com.freshremix.model.DealingPattern;
import com.freshremix.model.EONWebServiceResponse;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.User;
import com.freshremix.model.WSInputDetails;
import com.freshremix.model.WSSKU;
import com.freshremix.service.AllocationSheetService;
import com.freshremix.service.CategoryService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.WebService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ws.freshremix.validator.FreshRemixServiceValidator;
import ws.freshremix.validator.WebServiceValidator;

public class FreshRemixServiceAdapter
{
  private FreshRemixServiceValidator validator;
  private OrderSheetService orderSheetService;
  private DealingPatternService dealingPatternService;
  private CategoryService categoryService;
  private AllocationSheetService allocationSheetService;
  private WebService webService;
  
  public void setAllocationSheetService(AllocationSheetService allocationSheetService)
  {
    this.allocationSheetService = allocationSheetService;
  }
  
  public void setCategoryService(CategoryService categoryService)
  {
    this.categoryService = categoryService;
  }
  
  public void setDealingPatternService(DealingPatternService dealingPatternService)
  {
    this.dealingPatternService = dealingPatternService;
  }
  
  public void setOrderSheetService(OrderSheetService orderSheetService)
  {
    this.orderSheetService = orderSheetService;
  }
  
  public void setWebService(WebService webService)
  {
    this.webService = webService;
  }
  
  public EONWebServiceResponse createSheet(String username, String password, WSInputDetails[] wsInputDetails, String orderDate, boolean isPublish, boolean isFinalize)
  {
    WebServiceValidator wsValidator = new WebServiceValidator();
    if (!wsValidator.validateUser(username, password)) {
      return wsValidator.getResponse();
    }
    if (!wsValidator.validatesSku(wsInputDetails)) {
      return wsValidator.getResponse();
    }
    if (!wsValidator.validateDealingPattern(wsInputDetails, orderDate)) {
      return wsValidator.getResponse();
    }
    if (!wsValidator.validateSheetFinalizeStatus(wsInputDetails, orderDate, "Order Sheet")) {
      return wsValidator.getResponse();
    }
    if (!wsValidator.validateCategoryUOM(wsInputDetails)) {
      return wsValidator.getResponse();
    }
    User user = wsValidator.getUser();
    Map<Integer, User> sellerMap = wsValidator.getSellerMap();
    Map<Integer, User> buyerMap = wsValidator.getBuyerMap();
    Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap = wsValidator.getSellerToBuyerDPMap();
    
    List<String> dateList = new ArrayList();
    dateList.add(orderDate);
    List<Integer> buyerIdList = new ArrayList(buyerMap.keySet());
    List<Integer> sellerIds = new ArrayList();
    sellerIds.add(user.getUserId());
    try
    {
      this.webService.createSheetWS(user, sellerMap, buyerIdList, orderDate, wsInputDetails, isPublish, isFinalize, sellerToBuyerDPMap);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      EONWebServiceResponse err = new EONWebServiceResponse();
      err.setErrorCode("1");
      err.setErrorMsg(e);
      return err;
    }
    return wsValidator.getResponse();
  }
  
  public List<Integer> getPublishedOrdersList(List<Order> orderList)
  {
    List<Order> orders = this.orderSheetService.getPublishedOrders(orderList);
    return getOrderIdList(orders);
  }
  
  public void publishOrders(Integer sellerId, String orderDate, List<Integer> selectedOrderId)
  {
    for (Integer orderId : selectedOrderId)
    {
      Map<String, Object> param = new HashMap();
      param.put("orderPublishedBy", sellerId);
      param.put("orderId", orderId);
      this.orderSheetService.updatePublishOrder(param);
    }
  }
  
  public void finalizeOder(List<Integer> pubOrderId, Integer sellerId)
  {
    for (Integer orderId : pubOrderId) {
      this.orderSheetService.updateFinalizedOrder(orderId, sellerId, null);
    }
  }
  
  public EONWebServiceResponse getSellerOrder(String username, String password, Integer[] buyerIds, String orderDate)
  {
    List<String> dateList = new ArrayList();
    dateList.add(orderDate);
    
    WebServiceValidator wsValidator = new WebServiceValidator();
    if (!wsValidator.validateUser(username, password)) {
      return wsValidator.getResponse();
    }
    if (!wsValidator.validateDealingPattern(Arrays.asList(buyerIds), orderDate)) {
      return wsValidator.getResponse();
    }
    List<Integer> sellerIds = wsValidator.getSellerIds();
    Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap = wsValidator.getSellerToBuyerDPMap();
    try
    {
      WSSKU[] skuObjectReturn = this.webService.getSellerOrderSheet(sellerIds, 
        Arrays.asList(buyerIds), orderDate, sellerToBuyerDPMap);
      wsValidator.getResponse().setSkuObjectReturn(skuObjectReturn);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      EONWebServiceResponse err = new EONWebServiceResponse();
      err.setErrorCode("1");
      err.setErrorMsg(e);
      return err;
    }
    return wsValidator.getResponse();
  }
  
  public EONWebServiceResponse addSKUAllocation(String username, String password, WSInputDetails[] wsInputDetails, String orderDate)
  {
    WebServiceValidator wsValidator = new WebServiceValidator();
    if (!wsValidator.validateUser(username, password)) {
      return wsValidator.getResponse();
    }
    if (!wsValidator.validatesSku(wsInputDetails)) {
      return wsValidator.getResponse();
    }
    if (!wsValidator.validateDealingPattern(wsInputDetails, orderDate)) {
      return wsValidator.getResponse();
    }
    if (!wsValidator.validateSheetFinalizeStatus(wsInputDetails, orderDate, "Allocation Sheet")) {
      return wsValidator.getResponse();
    }
    if (!wsValidator.validateCategoryUOM(wsInputDetails)) {
      return wsValidator.getResponse();
    }
    User user = wsValidator.getUser();
    Map<Integer, User> sellerMap = wsValidator.getSellerMap();
    Map<Integer, User> buyerMap = wsValidator.getBuyerMap();
    List<Integer> sellerIds = wsValidator.getSellerIds();
    
    List<String> dateList = new ArrayList();
    dateList.add(orderDate);
    List<Integer> buyerIdList = new ArrayList(buyerMap.keySet());
    List<Order> allOrders = this.orderSheetService.getAllOrders(buyerIdList, dateList, sellerIds);
    try
    {
      this.webService.addSkuAllocation(user, sellerMap, wsInputDetails, buyerIdList, orderDate, allOrders);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      EONWebServiceResponse err = new EONWebServiceResponse();
      err.setErrorCode("1");
      err.setErrorMsg(e);
      return err;
    }
    return wsValidator.getResponse();
  }
  
  private WSSKU createSKUObject(SKU sku, Integer buyerId, String deliveryDate)
  {
    WSSKU skuObj = new WSSKU();
    skuObj.setSkuId(String.valueOf(sku.getSkuId()));
    skuObj.setSkuName(sku.getSkuName());
    skuObj.setSkuCategoryId(String.valueOf(sku.getSkuCategoryId()));
    skuObj.setSellerName(sku.getUser().getUserName());
    skuObj.setMarket(sku.getMarket());
    skuObj.setBuyerId(String.valueOf(buyerId));
    skuObj.setSellerId(String.valueOf(sku.getUser().getUserId()));
    skuObj.setLocation(sku.getLocation());
    skuObj.setGrade(sku.getGrade());
    skuObj.setClazz(sku.getClazz());
    skuObj.setPrice1(sku.getPrice1());
    skuObj.setPrice2(sku.getPrice2());
    skuObj.setPriceWithoutTax(sku.getPriceWithoutTax());
    skuObj.setPriceWithTax(sku.getPriceWithTax());
    skuObj.setPackageQuantity(sku.getPackageQuantity());
    skuObj.setPackageType(sku.getPackageType());
    skuObj.setUnitOfOrder(sku.getOrderUnit().getOrderUnitName());
    skuObj.setSkuMaxLimit(sku.getSkuMaxLimit());
    try
    {
      this.orderSheetService.wsLoadQuantities(buyerId, deliveryDate, sku, skuObj);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return skuObj;
  }
  
  private WSSKU[] loadOrderSheet(List<Order> savedOrders, Integer[] buyerIds, String deliverydate)
  {
    List<SKU> skuList = this.orderSheetService.wsGetDistinctSKUs(getOrderIds(savedOrders));
    List<WSSKU> skus = new ArrayList();
    int cnt = 0;
    for (SKU sku : skuList) {
      for (Integer id : buyerIds) {
        skus.add(createSKUObject(sku, id, deliverydate));
      }
    }
    return (WSSKU[])skus.toArray(new WSSKU[skus.size()]);
  }
  
  List<Order> getPreviousOrders(Integer[] buyerIds, String deliveryDate, Integer sellerId)
  {
    List<Order> previousOrders = new ArrayList();
    for (Integer id : buyerIds) {
      previousOrders.add(this.orderSheetService.getPreviousOrders(sellerId, id, deliveryDate));
    }
    return previousOrders;
  }
  
  private List<Integer> getUserIds(List<User> userIds)
  {
    List<Integer> userIdList = new ArrayList();
    for (User user : userIds) {
      userIdList.add(user.getUserId());
    }
    return userIdList;
  }
  
  private List<Integer> getOrderIds(List<Order> orders)
  {
    List<Integer> orderIdList = new ArrayList();
    for (Order order : orders) {
      orderIdList.add(order.getOrderId());
    }
    return orderIdList;
  }
  
  public void applySkusToOrderSheet(List<Integer> sellerIds, WSInputDetails[] details, List<Integer> buyerIds, String orderDate, List<Order> orderList)
  {
    OrderSheetParam oP = createParam(orderDate);
    User user = new User();
    DealingPattern dp = createDealingPattern(oP, sellerIds);
    List<SKU> skuList = new ArrayList();
    Map<Integer, SKU> matchedSKUMap = new HashMap();
    Map<Integer, SKU> skuTodeleteMap = new HashMap();
    List<WSInputDetails> insertDetails = new ArrayList();
    List<WSInputDetails> updateList = new ArrayList();
    List<String> dateList = new ArrayList();
    dateList.add(orderDate);
    try
    {
      if ((orderList == null) || (orderList.size() == 0))
      {
        this.orderSheetService.insertDefaultOrders(oP, user, dp);
        orderList = getDefaultOrdersList(buyerIds, dateList, sellerIds);
      }
      skuList = this.orderSheetService.wsGetDistinctSKUs(
        getOrderIdList(orderList));
      
      populateUpdateLists(details, insertDetails, skuList, matchedSKUMap, updateList);
      
      populateToDeleteList(skuList, matchedSKUMap, skuTodeleteMap, dateList);
      
      WSInputDetails[] insertItems = (WSInputDetails[])insertDetails.toArray(new WSInputDetails[insertDetails.size()]);
      
      WSInputDetails[] updateItems = (WSInputDetails[])updateList.toArray(new WSInputDetails[updateList.size()]);
      
      this.orderSheetService.updateOrdersWS(orderDate, buyerIds, sellerIds, orderList);
      
      this.orderSheetService.deleteOrdersWS(getSKUIds(skuList), orderDate, buyerIds, sellerIds, orderList, skuTodeleteMap);
      
      this.orderSheetService.updateSKUWS(orderDate, buyerIds, sellerIds, updateItems, matchedSKUMap, orderList);
      
      this.orderSheetService.insertSKUWS(orderDate, buyerIds, sellerIds, insertItems, orderList);
    }
    catch (Exception e)
    {
      this.validator.writeResponse("8", "ws.message.error8");
    }
  }
  
  public List<Order> getDefaultOrdersList(List<Integer> buyerIds, List<String> dateList, List<Integer> sellerIds)
  {
    return this.orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);
  }
  
  private OrderSheetParam createParam(String orderDate)
  {
    OrderSheetParam param = new OrderSheetParam();
    param.setCheckDBOrder(true);
    param.setSelectedDate(orderDate);
    param.setStartDate(orderDate);
    param.setEndDate(orderDate);
    param.setAllDatesView(false);
    param.setSheetTypeId(SheetTypeConstants.SELLER_ORDER_SHEET);
    
    return param;
  }
  
  private DealingPattern createDealingPattern(OrderSheetParam orderSheetParam, List<Integer> seller)
  {
    Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap = this.dealingPatternService.getSellerToBuyerDPMap(seller, orderSheetParam.getStartDate(), orderSheetParam
      .getEndDate());
    
    Map<String, Map<String, List<Integer>>> buyerToSellerDPMap = this.dealingPatternService.getBuyerToSellerDPMap(sellerToBuyerDPMap);
    DealingPattern dp = new DealingPattern(sellerToBuyerDPMap, buyerToSellerDPMap);
    
    return dp;
  }
  
  public List<Integer> getOrderIdList(List<Order> allOrders)
  {
    List<Integer> orderIds = new ArrayList();
    for (Order order : allOrders) {
      orderIds.add(order.getOrderId());
    }
    return orderIds;
  }
  
  private void populateUpdateLists(WSInputDetails[] details, List<WSInputDetails> insertDetails, List<SKU> skuList, Map<Integer, SKU> skuMap, List<WSInputDetails> updateDetails)
  {
    boolean isMatch = false;
    for (WSInputDetails input : details)
    {
      SKU newSku = new SKU();
      for (SKU sku : skuList)
      {
        newSku = sku;
        isMatch = isMatch(input, sku);
        if (isMatch) {
          break;
        }
      }
      if (isMatch)
      {
        skuMap.put(input.getSkuId(), newSku);
        updateDetails.add(input);
      }
      else
      {
        insertDetails.add(input);
      }
    }
  }
  
  private void populateToDeleteList(List<SKU> skuList, Map<Integer, SKU> undeletedSKUSMap, Map<Integer, SKU> skuTodeleteMap, List<String> dateList)
  {
    List<Integer> keys = new ArrayList(undeletedSKUSMap.keySet());
    for (SKU sku : skuList)
    {
      boolean toDelete = true;
      for (Integer key : keys)
      {
        SKU checkSKU = (SKU)undeletedSKUSMap.get(key);
        if (checkSKU.getSkuId().equals(sku.getSkuId()))
        {
          toDelete = false;
          break;
        }
      }
      if (toDelete) {
        if (!this.orderSheetService.skuHasQuantity(sku.getSkuId(), dateList)) {
          skuTodeleteMap.put(sku.getSkuId(), sku);
        }
      }
    }
  }
  
  private boolean isMatch(WSInputDetails input, SKU sku)
  {
    if (!input.getSkuName().equals(sku.getSkuName())) {
      return false;
    }
    if (!input.getSkuCategoryName().equals(
      getCategoryName(sku.getSkuCategoryId()))) {
      return false;
    }
    if (!input.getSkuGroupName().equals(sku.getSkuGroup().getDescription())) {
      return false;
    }
    if (!input.getLocation().equals(sku.getLocation())) {
      return false;
    }
    if (!input.getMarket().equals(sku.getMarket())) {
      return false;
    }
    if (!input.getClass2().equals(sku.getGrade())) {
      return false;
    }
    if (!input.getPackageQuantity().toPlainString().equals(sku
      .getPackageQuantity().toPlainString())) {
      return false;
    }
    if (!input.getPackageType().equals(sku.getPackageType())) {
      return false;
    }
    if (!input.getPrice1().toPlainString().equals(sku.getPrice1().toPlainString())) {
      return false;
    }
    if (!input.getPrice2().toPlainString().equals(sku.getPrice2().toPlainString())) {
      return false;
    }
    if (!input.getPriceWithoutTax().toPlainString().equals(sku
      .getPriceWithoutTax().toPlainString())) {
      return false;
    }
    if (!input.getUnitOfOrder().equals(sku.getOrderUnit().getOrderUnitName())) {
      return false;
    }
    return true;
  }
  
  private List<Integer> getSKUIds(List<SKU> skuList)
  {
    List<Integer> skuIds = new ArrayList();
    for (SKU sku : skuList) {
      skuIds.add(sku.getSkuId());
    }
    return skuIds;
  }
  
  private String getCategoryName(Integer categoryId)
  {
    List<Integer> categoryList = new ArrayList();
    categoryList.add(categoryId);
    List<Category> categoryName = this.categoryService.getCategoryById(categoryList);
    Category cat = (Category)categoryName.get(0);
    return cat.getDescription();
  }
  
  public void applyAllocationSKUS(List<Integer> sellerIds, AllocationInputDetails[] allocationInputDetails, List<Integer> buyerIds, String orderDate, List<Order> orderList, User user)
  {
    List<Integer> allOrderId = getOrderIdList(orderList);
    try
    {
      List<SKU> allocationSKUList = this.allocationSheetService.wsGetDistinctSKUs(allOrderId);
      List<SKU> skuDeleteList = new ArrayList();
      
      populateAllocationDeleteList(allocationSKUList, skuDeleteList, buyerIds, orderDate);
      
      this.allocationSheetService.updateAllocationOrdersWS(orderDate, buyerIds, sellerIds, allocationInputDetails, orderList, user);
      
      this.allocationSheetService.deleteAllocationItems(
        getSKUIds(skuDeleteList), orderDate, buyerIds, sellerIds, orderList);
      
      this.allocationSheetService.insertAllocationSKUWS(orderDate, buyerIds, sellerIds, allocationInputDetails, orderList);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  private void populateAllocationDeleteList(List<SKU> skuList, List<SKU> emptyList, List<Integer> buyerIds, String deliveryDate)
  {
    for (SKU obj : skuList) {
      try
      {
        if (!this.allocationSheetService.skuHasQuantities(buyerIds, deliveryDate, obj)) {
          emptyList.add(obj);
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
}
