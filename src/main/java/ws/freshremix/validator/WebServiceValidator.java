package ws.freshremix.validator;

import com.freshremix.constants.RoleConstants;
import com.freshremix.dao.CategoryDao;
import com.freshremix.dao.OrderUnitDao;
import com.freshremix.dao.UserDao;
import com.freshremix.exception.WebserviceException;
import com.freshremix.model.Category;
import com.freshremix.model.EONWebServiceResponse;
import com.freshremix.model.Order;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.Role;
import com.freshremix.model.SKUInformationWS;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.model.WSBuyerInformation;
import com.freshremix.model.WSInputDetails;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.LoginService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.util.MessageUtil;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.SpringContext;
import com.freshremix.util.StringUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;

public class WebServiceValidator
{
  private static final String USERS_INFO_DAOS = "usersInfoDaos";
  private static final String ORDER_SHEET_SERVICE = "orderSheetService";
  private static final String DEALING_PATTERN_SERVICE = "dealingPatternService";
  private static final String LOGIN_SERVICE = "loginService";
  private static final String CATEGORY_DAOS = "categoryDaos";
  private static final String ORDERUNIT_DAO = "orderUnitDao";
  private EONWebServiceResponse response = new EONWebServiceResponse();
  private User user = new User();
  private Map<Integer, User> sellerMap = new HashMap();
  private Map<Integer, User> buyerMap = new HashMap();
  private Map<String, Map<String, List<Integer>>> sellerToBuyerDPMap = new HashMap();
  private List<Integer> sellerIds = new ArrayList();
  
  public WebServiceValidator()
  {
    this.response.setErrorCode("0");
    this.response.setErrorMsg(
      MessageUtil.getPropertyMessage("ws.message.success"));
  }
  
  public boolean validateUser(String username, String password)
  {
    Map<String, String> params = new HashMap();
    params.put("username", username);
    params.put("password", password);
    
    LoginService loginService = (LoginService)SpringContext.getApplicationContext().getBean("loginService");
    try
    {
      this.user = CommonWebServiceValidation.validateUser(username, password, loginService);
    }
    catch (WebserviceException e)
    {
      this.response.setErrorCode(e.getErrorCode());
      this.response.setErrorMsg(
        MessageUtil.getPropertyMessage(e.getErrorMsgCode()), params);
      
      return false;
    }
    if (!RolesUtil.isSellerType(this.user.getRole()))
    {
      this.response.setErrorCode("1");
      this.response.setErrorMsg(
        MessageUtil.getPropertyMessage("ws.message.error.user.invalid.seller"));
      return false;
    }
    return true;
  }
  
  public boolean validatesSku(WSInputDetails[] wsInputDetails)
  {
    for (WSInputDetails item : wsInputDetails)
    {
      SKUInformationWS sku = item;
      if ((this.user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) && 
        (!CommonWebServiceValidation.validateSellerId(sku.getSellerId())))
      {
        this.response.setErrorCode("2");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.required.seller"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validateSKUName(sku.getSkuName()))
      {
        this.response.setErrorCode("2");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.required.sku.name"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validateSKUNameLength(sku.getSkuName()))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.sku.name"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validateCategoryName(sku.getSkuCategoryName()))
      {
        this.response.setErrorCode("2");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.required.sku.category"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validateCategoryName(sku.getSkuCategoryName()))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.category.name"));
        
        return false;
      }
      if (StringUtil.isNullOrEmpty(sku.getSkuGroupName()))
      {
        this.response.setErrorCode("2");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.required.sku.group"));
        
        return false;
      }
      if (sku.getSkuGroupName().length() > 50)
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.skugroup.name"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validatePackageQty(sku.getPackageQuantity()))
      {
        this.response.setErrorCode("2");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.required.sku.package.quantity"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validatePackageQtyLength(sku.getPackageQuantity()))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.packageqty"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validatePriceWOTax(sku.getPriceWithoutTax()))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.pricewithouttax"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validatePriceWTax(sku.getPriceWithTax()))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.pricewithtax"));
        
        return false;
      }
      if (StringUtil.isNullOrEmpty(sku.getUnitOfOrder()))
      {
        this.response.setErrorCode("2");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.required.sku.unitoforder"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validateUOM(sku.getUnitOfOrder()))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.uom"));
        
        return false;
      }
      if ((CommonWebServiceValidation.validateSKUId(sku.getSkuId())) && 
        (!CommonWebServiceValidation.validateSKUIdLength(sku.getSkuId())))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.skuid"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validateLocation(sku.getLocation()))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.location"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validateMarket(sku.getMarket()))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.market"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validateClass1(sku.getClass1()))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.class1"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validateClass1(sku.getClass2()))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.class2"));
        
        return false;
      }
      if ((!StringUtil.isNullOrEmpty(sku.getPrice1())) && 
        (sku.getPrice1().compareTo(new BigDecimal(999999999)) == 1))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.price1"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validatePrice2(sku.getPrice2()))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.price2"));
        
        return false;
      }
      if ((CommonWebServiceValidation.validatePackageType(sku.getPackageType())) && 
        (!CommonWebServiceValidation.validatePackageTypeLength(sku.getPackageType())))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.packagetype"));
        
        return false;
      }
      if (!CommonWebServiceValidation.validateExternalSKUIDLength(sku.getSkuExternalID()))
      {
        this.response.setErrorCode("4");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.maxlength.externalid"));
        
        return false;
      }
      WSBuyerInformation[] wsBuyerInformation = item.getWsBuyerInformation();
      for (WSBuyerInformation buyerInfo : wsBuyerInformation) {
        if ((CommonWebServiceValidation.validateQuantity(buyerInfo.getQuantity())) && (!CommonWebServiceValidation.validateQuantityLength(buyerInfo.getQuantity().toString())))
        {
          this.response.setErrorCode("4");
          this.response.setErrorMsg(MessageUtil.getPropertyMessage("ws.message.error.maxlength.quantity"));
          return false;
        }
      }
    }
    return true;
  }
  
  public boolean validateDealingPattern(WSInputDetails[] wsInputDetails, String date)
  {
    DealingPatternService dealingPatternService = (DealingPatternService)SpringContext.getApplicationContext().getBean("dealingPatternService");
    if (this.user.getRole().getSellerAdminFlag().equals("1")) {
      this.sellerIds = dealingPatternService.getSellerIdsOfSellerAdminId(this.user
        .getUserId());
    } else {
      this.sellerIds.add(this.user.getUserId());
    }
    this.sellerToBuyerDPMap = dealingPatternService.getSellerToBuyerDPMap(this.sellerIds, date, date);
    for (WSInputDetails item : wsInputDetails)
    {
      Map<String, String> params;
      User seller;
      if (this.user.getRole().getSellerAdminFlag().equals("1"))
      {
        if (!this.sellerMap.containsKey(item.getSellerName()))
        {
          seller = validateUserById(item.getSellerId(), true);
          if (seller == null) {
            return false;
          }
          if (!this.sellerIds.contains(seller.getUserId()))
          {
            params = new HashMap();
            params.put("username", this.user.getUserName());
            params.put("seller", seller.getUserId().toString());
            this.response
              .setErrorCode("3");
            this.response.setErrorMsg(
              MessageUtil.getPropertyMessage("ws.message.error.invalid.dealingpattern"), params);
            
            return false;
          }
          this.sellerMap.put(item.getSellerId(), seller);
        }
        else
        {
          seller = (User)this.sellerMap.get(item.getSellerId());
        }
      }
      else {
        seller = this.user;
      }
      for (WSBuyerInformation buyerInfo : item.getWsBuyerInformation())
      {
        Map<String, List<Integer>> dpMap = (Map)this.sellerToBuyerDPMap.get(date);
        if (dpMap == null)
        {
          this.response.setErrorCode("3");
          this.response.setErrorMsg(
            MessageUtil.getPropertyMessage("ws.message.error.invalid.dealingpattern.orderdate"));
          
          return false;
        }
        List<Integer> buyers = (List)dpMap.get(seller.getUserId().toString());
        
        User buyer = validateUserById(buyerInfo.getBuyerId(), false);
        if (buyer == null) {
          return false;
        }
        if (!this.buyerMap.containsKey(buyerInfo.getBuyerId()))
        {
          if ((buyers == null) || (!buyers.contains(buyerInfo.getBuyerId())))
          {
            this.response.setErrorCode("3");
            this.response.setErrorMsg(
              MessageUtil.getPropertyMessage("ws.message.error.invalid.dealingpattern"));
            
            return false;
          }
          this.buyerMap.put(buyerInfo.getBuyerId(), buyer);
        }
        else
        {
          buyer = (User)this.buyerMap.get(buyerInfo.getBuyerId());
        }
      }
    }
    return true;
  }
  
  public boolean validateDealingPattern(List<Integer> buyerIds, String date)
  {
    DealingPatternService dealingPatternService = (DealingPatternService)SpringContext.getApplicationContext().getBean("dealingPatternService");
    if (this.user.getRole().getSellerAdminFlag().equals("1")) {
      this.sellerIds = dealingPatternService.getSellerIdsOfSellerAdminId(this.user
        .getUserId());
    } else {
      this.sellerIds.add(this.user.getUserId());
    }
    List<User> buyers = dealingPatternService.getAllBuyerIdsBySellerIds(this.sellerIds, date, date);
    List<Integer> buyerIdList = OrderSheetUtil.getUserIdList(buyers);
    if ((buyerIds == null) || (buyerIds.size() == 0))
    {
      this.response.setErrorCode("3");
      this.response.setErrorMsg(
        MessageUtil.getPropertyMessage("ws.message.error.invalid.buyer"));
      
      return false;
    }
    for (Integer id : buyerIds) {
      if (!buyerIdList.contains(id))
      {
        this.response.setErrorCode("3");
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.invalid.dealingpattern"));
        
        return false;
      }
    }
    this.sellerToBuyerDPMap = dealingPatternService.getSellerToBuyerDPMap(this.sellerIds, date, date);
    
    return true;
  }
  
  public boolean validateSheetFinalizeStatus(WSInputDetails[] wsInputDetails, String date, String sheetType)
  {
    OrderSheetService orderSheetService = (OrderSheetService)SpringContext.getApplicationContext().getBean("orderSheetService");
    for (WSInputDetails item : wsInputDetails)
    {
      User seller = new User();
      if (this.user.getRole().getSellerAdminFlag().equals("1")) {
        seller = (User)this.sellerMap.get(item.getSellerId());
      } else {
        seller = this.user;
      }
      for (WSBuyerInformation buyerInfo : item.getWsBuyerInformation())
      {
        Order order = orderSheetService.getOrderByDeliveryDate(seller
          .getUserId(), buyerInfo.getBuyerId(), date);
        if (sheetType.equals("Order Sheet"))
        {
          if ((order == null) || (order.getOrderFinalizedBy() == null))
          {
            if ((order != null) && (!StringUtil.isNullOrEmpty(order.getOrderPublishedBy())))
            {
              Map<String, String> params = new HashMap();
              if (item.getSellerId() != null) {
                params.put("seller", item.getSellerId().toString());
              }
              params.put("buyer", buyerInfo.getBuyerId().toString());
              params.put("date", date);
              this.response.setErrorCode("3");
              this.response.setErrorMsg(
                MessageUtil.getPropertyMessage("ws.message.error.invalid.published.order"), params);
              
              return false;
            }
          }
          else
          {
            Map<String, String> params = new HashMap();
            if (item.getSellerId() != null) {
              params.put("seller", item.getSellerId().toString());
            }
            params.put("buyer", buyerInfo.getBuyerId().toString());
            params.put("date", date);
            this.response.setErrorCode("3");
            this.response.setErrorMsg(
              MessageUtil.getPropertyMessage("ws.message.error.invalid.finalized.order"), params);
            
            return false;
          }
        }
        else if (sheetType.equals("Allocation Sheet")) {
          if ((order == null) || 
            (NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) || 
            (!NumberUtil.isNullOrZero(order.getAllocationFinalizedBy())))
          {
            Map<String, String> params = new HashMap();
            if (item.getSellerId() != null) {
              params.put("seller", item.getSellerId().toString());
            }
            params.put("buyer", buyerInfo.getBuyerId().toString());
            params.put("date", date);
            this.response.setErrorCode("3");
            this.response.setErrorMsg(
              MessageUtil.getPropertyMessage("ws.message.error.invalid.finalized.allocation"), params);
            
            return false;
          }
        }
      }
    }
    return true;
  }
  
  public boolean validateCategoryUOM(WSInputDetails[] wsInputDetails)
  {
    CategoryDao categoryDaos = (CategoryDao)SpringContext.getApplicationContext().getBean("categoryDaos");
    OrderUnitDao orderUnitDao = (OrderUnitDao)SpringContext.getApplicationContext().getBean("orderUnitDao");
    for (WSInputDetails item : wsInputDetails)
    {
      SKUInformationWS sku = item;
      
      Map<String, String> param = new HashMap();
      User seller = new User();
      if (this.user.getRole().getSellerAdminFlag().equals("1")) {
        seller = (User)this.sellerMap.get(item.getSellerId());
      } else {
        seller = this.user;
      }
      param.put("userId", seller.getUserId().toString());
      List<UsersCategory> categoryList = categoryDaos.getCategoryListByUserId(param);
      Category cat = null;
      for (UsersCategory usersCategory : categoryList) {
        if (usersCategory.getCategoryAvailable().equals(sku.getSkuCategoryName()))
        {
          cat = new Category();
          cat.setCategoryId(usersCategory.getCategoryId());
          cat.setDescription(usersCategory.getCategoryAvailable());
        }
      }
      if (cat == null)
      {
        Object params = new HashMap();
        ((Map)params).put("skuId", item.getSkuId().toString());
        ((Map)params).put("skuCategory", sku.getSkuCategoryName());
        this.response.setErrorCode("3");
        this.response.setErrorMsg(MessageUtil.getPropertyMessage("ws.message.error.invalid.sku.category"), (Map)params);
        
        return false;
      }
      List<OrderUnit> unit = orderUnitDao.getOrderUnitList(cat.getCategoryId());
      OrderUnit orderUnit = null;
      for (OrderUnit uom : unit) {
        if (uom.getOrderUnitName().equals(sku.getUnitOfOrder())) {
          orderUnit = uom;
        }
      }
      if (orderUnit == null)
      {
        Object params = new HashMap();
        ((Map)params).put("skuId", item.getSkuId().toString());
        ((Map)params).put("uom", sku.getUnitOfOrder());
        this.response.setErrorCode("3");
        this.response.setErrorMsg(MessageUtil.getPropertyMessage("ws.message.error.invalid.sku.unitoforder"), (Map)params);
        
        return false;
      }
    }
    return true;
  }
  
  private User validateUserById(Integer userId, boolean isSeller)
  {
    UserDao userDao = (UserDao)SpringContext.getApplicationContext().getBean("usersInfoDaos");
    User user = userDao.getUserById(userId);
    if (user == null)
    {
      this.response.setErrorCode("3");
      if (isSeller) {
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.invalid.seller"));
      } else {
        this.response.setErrorMsg(
          MessageUtil.getPropertyMessage("ws.message.error.invalid.buyer"));
      }
    }
    return user;
  }
  
  public EONWebServiceResponse getResponse()
  {
    return this.response;
  }
  
  public User getUser()
  {
    return this.user;
  }
  
  public Map<Integer, User> getSellerMap()
  {
    return this.sellerMap;
  }
  
  public Map<Integer, User> getBuyerMap()
  {
    return this.buyerMap;
  }
  
  public Map<String, Map<String, List<Integer>>> getSellerToBuyerDPMap()
  {
    return this.sellerToBuyerDPMap;
  }
  
  public List<Integer> getSellerIds()
  {
    return this.sellerIds;
  }
  
  public boolean validateDateRange(DateTime startDate, DateTime endDate)
  {
    if (!CommonWebServiceValidation.validateDateRange(startDate, endDate))
    {
      this.response.setErrorCode("3");
      this.response.setErrorMsg(
        MessageUtil.getPropertyMessage("ws.message.error.invalid.orderdate"));
      
      return false;
    }
    return true;
  }
}
