package ws.freshremix.validator;

import com.freshremix.exception.WebserviceException;
import com.freshremix.model.User;
import com.freshremix.service.LoginService;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.StringUtil;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class CommonWebServiceValidation
{
  public static boolean validateDateRange(DateTime startDate, DateTime endDate)
  {
    int days = Days.daysBetween(startDate, endDate).getDays();
    if ((days < 0) || (days >= 7)) {
      return false;
    }
    return true;
  }
  
  public static User validateUser(String username, String password, LoginService loginService)
    throws WebserviceException
  {
    if ((StringUtil.isNullOrEmpty(username)) || 
      (StringUtil.isNullOrEmpty(password))) {
      throw new WebserviceException("1", "ws.message.error.username");
    }
    User user = loginService.getUserByUsernameAndPassword(username, password);
    if (user == null) {
      throw new WebserviceException("1", "ws.message.error.username");
    }
    return user;
  }
  
  public static boolean validateQuantity(BigDecimal qty)
  {
    return !StringUtil.isNullOrEmpty(qty);
  }
  
  public static boolean validateQuantityLength(String qty)
  {
    if (qty.length() > 9) {
      return false;
    }
    return true;
  }
  
  public static boolean validateSKUId(Integer skuId)
  {
    return !NumberUtil.isNullOrZero(skuId);
  }
  
  public static boolean validateSKUIdLength(Integer skuId)
  {
    if (skuId.intValue() > new Integer(999999999).intValue()) {
      return false;
    }
    return true;
  }
  
  public static boolean validateSKUIdLength(String skuId)
  {
    if (skuId.length() > 9) {
      return false;
    }
    return true;
  }
  
  public static boolean validateSellerId(Integer sellerId)
  {
    return !NumberUtil.isNullOrZero(sellerId);
  }
  
  public static boolean validateSellerIdLength(String sellerId)
  {
    if (sellerId.length() > 9) {
      return false;
    }
    return true;
  }
  
  public static boolean validateBuyerIdLength(String buyerID)
  {
    if (buyerID.length() > 9) {
      return false;
    }
    return true;
  }
  
  public static boolean validateSellerId(String sellerId)
  {
    return !StringUtil.isNullOrEmpty(sellerId);
  }
  
  public static boolean validateSKUName(String skuName)
  {
    return !StringUtil.isNullOrEmpty(skuName);
  }
  
  public static boolean validateSKUNameLength(String skuName)
  {
    if (skuName.length() > 42) {
      return false;
    }
    return true;
  }
  
  public static boolean validatePackageQty(BigDecimal pqty)
  {
    return !NumberUtil.isNullOrZero(pqty);
  }
  
  public static boolean validatePackageQty(String pqty)
  {
    return !StringUtil.isNullOrEmpty(pqty);
  }
  
  public static boolean validatePackageQtyLength(BigDecimal pqty)
  {
    if (pqty.compareTo(new BigDecimal(999999999)) == 1) {
      return false;
    }
    return true;
  }
  
  public static boolean validatePackageType(String pType)
  {
    return !StringUtil.isNullOrEmpty(pType);
  }
  
  public static boolean validatePackageTypeLength(String pType)
  {
    if (pType.length() > 42) {
      return false;
    }
    return true;
  }
  
  public static boolean validatePriceWOTax(BigDecimal pricewotax)
  {
    if ((pricewotax != null) && 
      (pricewotax.compareTo(new BigDecimal(999999999)) == 1)) {
      return false;
    }
    return true;
  }
  
  public static boolean validateLocation(String location)
  {
    if ((!StringUtil.isNullOrEmpty(location)) && (location.length() > 42)) {
      return false;
    }
    return true;
  }
  
  public static boolean validateUOM(String uom)
  {
    if ((!StringUtil.isNullOrEmpty(uom)) && (uom.length() > 15)) {
      return false;
    }
    return true;
  }
  
  public static boolean validateMarket(String market)
  {
    if ((!StringUtil.isNullOrEmpty(market)) && (market.length() > 42)) {
      return false;
    }
    return true;
  }
  
  public static boolean validateClass1(String class1)
  {
    if ((!StringUtil.isNullOrEmpty(class1)) && (class1.length() > 42)) {
      return false;
    }
    return true;
  }
  
  public static boolean validateClass2(String class2)
  {
    if ((!StringUtil.isNullOrEmpty(class2)) && (class2.length() > 42)) {
      return false;
    }
    return true;
  }
  
  public static boolean validatePriceWTax(BigDecimal priceWTax)
  {
    if ((!StringUtil.isNullOrEmpty(priceWTax)) && 
      (priceWTax.compareTo(new BigDecimal(999999999)) == 1)) {
      return false;
    }
    return true;
  }
  
  public static boolean validateCategoryName(String categoryName)
  {
    return !StringUtil.isNullOrEmpty(categoryName);
  }
  
  public static boolean validateCategoryNameLength(String categoryName)
  {
    if (categoryName.length() > 50) {
      return false;
    }
    return true;
  }
  
  public static boolean validatePrice2(BigDecimal price2)
  {
    if ((!StringUtil.isNullOrEmpty(price2)) && 
      (price2.compareTo(new BigDecimal(999999999)) == 1)) {
      return false;
    }
    return true;
  }
  
  public static boolean isValidVisibilityFormat(String visibility)
  {
    return ("0".equalsIgnoreCase(visibility)) || ("1".equalsIgnoreCase(visibility));
  }
  
  public static boolean isValidFlagFormat(String visibility)
  {
    return ("0".equalsIgnoreCase(visibility)) || ("1".equalsIgnoreCase(visibility));
  }
  
  public static boolean validateExternalSKUIDLength(String exSKUID)
  {
    if ((!StringUtil.isNullOrEmpty(exSKUID)) && 
      (exSKUID.length() > 42)) {
      return false;
    }
    return true;
  }
  
  public static Integer validateWholeNumericString(String str)
  {
    if (NumberUtils.isDigits(str))
    {
      BigDecimal tmp = new BigDecimal(str);
      if (NumberUtil.isWholeNumber(tmp))
      {
        Integer result = Integer.valueOf(tmp.intValue());
        if (result.intValue() > 0) {
          return result;
        }
      }
    }
    return null;
  }
  
  public static Date validateStringDateFormat(String str)
  {
    if (str.length() != 8) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    try
    {
      return sdf.parse(str);
    }
    catch (ParseException e) {}
    return null;
  }
}
