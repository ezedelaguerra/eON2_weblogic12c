package ws.freshremix.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.constants.OrderConstants;
import com.freshremix.constants.WebServiceConstants;
import com.freshremix.exception.EONError;
import com.freshremix.exception.WebserviceException;
import com.freshremix.model.Category;
import com.freshremix.model.CompositeKey;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.model.WSSellerBuyerDetails;
import com.freshremix.model.WSSellerDeleteSKU;
import com.freshremix.model.WSSellerRequest;
import com.freshremix.model.WSSellerResponse;
import com.freshremix.model.WSSellerSKU;
import com.freshremix.model.WSSellerSKUCreateRequest;
import com.freshremix.model.WSSellerSKURequest;
import com.freshremix.model.WSSellerSKUUpdateRequest;
import com.freshremix.service.CategoryService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.LoginService;
import com.freshremix.service.MessageI18NService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.OrderUnitUtility;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.StringUtil;
import com.freshremix.util.WebServiceUtil;

public class WebServiceSellerValidator {

	private MessageI18NService messageI18NService;
	private LoginService loginService;
	private CategoryService categoryService;
	private DealingPatternService dealingPatternService;
	private OrderUnitService orderUnitService;
	private SKUGroupService skuGroupService;
	private UsersInformationService userInfoService;
	private OrderSheetService orderSheetService;

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}

	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}

	public void setDealingPatternService(
			DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setMessageI18NService(MessageI18NService messageI18NService) {
		this.messageI18NService = messageI18NService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	/**
	 * Validates if request obj entered is null.
	 * 
	 * Returns an instance of WSBuyerResponse if validation failed, else return
	 * null
	 * 
	 * @param inputDate
	 * @return WSSellerResponse
	 */
	public WSSellerResponse validateRequest(Object request) {

		if (request == null) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_REQUEST_ERR_MSG));
			return response;
		}
		return null;
	}

	/**
	 * Validates the following: - username password is not null or empty
	 * -username password is valid - user is either a Seller or Seller Admin
	 * 
	 * Returns a Map containing: response - not null if validation fails user -
	 * not null if validation succeeds
	 * 
	 * @param username
	 * @param password
	 * @return Map
	 */

	public Map<String, Object> validateSellerUser(String username,
			String password) {
		Map<String, Object> result = new HashMap<String, Object>();
		WSSellerResponse response = new WSSellerResponse();
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);

		User user = null;
		try {
			user = CommonWebServiceValidation.validateUser(username, password,
					loginService);
		} catch (WebserviceException e) {
			response.setResultCode(e.getErrorCode());
			response.setResultMsg(
					messageI18NService.getPropertyMessage(e.getErrorMsgCode()),
					params);
			result.put("response", response);
			return result;
		}

		if (!RolesUtil.isUserRoleSeller(user)
				&& !RolesUtil.isUserRoleSellerAdmin(user)) {
			response.setResultCode(WebServiceConstants.USER_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.USER_SELLER_ERR_MSG));
			result.put("response", response);

			return result;

		}

		result.put("user", user);
		return result;
	}

	/**
	 * Validates the following: - orderDate is not null or empty - orderDate is
	 * in correct format (yyyyMMdd)
	 * 
	 * Returns a Map containing: response - not null if validation fails
	 * orderDate - not null if validation succeeds
	 * 
	 * @param orderDate
	 * @return Map
	 */

	public Map<String, Object> validateOrderDate(String orderDate) {
		Map<String, Object> result = new HashMap<String, Object>();
		WSSellerResponse response = new WSSellerResponse();

		Date date = null;
		if (StringUtil.isNullOrEmpty(orderDate)) {
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_ORDERDATE_ERR_MSG));

			result.put("response", response);
			return result;
		}

		date = CommonWebServiceValidation.validateStringDateFormat(orderDate);
		if (date == null) {
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_ORDERDATE_FORMAT));

			result.put("response", response);
			return result;
		}

		result.put("orderDate", date);
		return result;
	}

	/**
	 * Validates the following: - categoryName is not null or empty - Fruits -
	 * if Category is null or no categories are assigned for the user
	 * 
	 * Returns a Map containing: response - not null if validation fails
	 * category - not null if validation succeeds
	 * 
	 * @param categoryName
	 * @return Map
	 */

	public Map<String, Object> validateCategoryName(String categoryName) {

		Map<String, Object> result = new HashMap<String, Object>();
		// if category is null return fruits
		if (!CommonWebServiceValidation.validateCategoryName(categoryName)) {
			Category category = (categoryService.getCategoryById(Arrays
					.asList(1))).get(0);
			result.put("category", category);
			return result;
		}// else validate length
		else if (!CommonWebServiceValidation
				.validateCategoryNameLength(categoryName)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_CATEGORY_ERR_MSG));
			result.put("response", response);
			return result;
		}

		else {

			// check if category exists
			List<Category> allCategory = categoryService.getAllCategory();
			Category category = null;
			for (Category cat : allCategory) {
				if (cat.getDescription().equalsIgnoreCase(categoryName)) {
					category = cat;

				}
			}
			if (category == null) {
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_CATEGORY_ERR_MSG));
				result.put("response", response);
				return result;
			}

			result.put("category", category);
			return result;

		}
	}

	public WSSellerResponse validateCategoryMembership(Integer catId,
			List<Integer> users) {
		// Category category = categoryService.getCategory(userId,
		// categoryName);;

		List<UsersCategory> list = categoryService
				.getCategoryListBySelectedIds(users);
		if (CollectionUtils.isEmpty(list)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_CATEGORY_ERR_MSG));
			return response;

		} else {
			boolean found = false;
			for (Integer userId : users) {

				for (UsersCategory userCat : list) {
					if (catId.intValue() == userCat.getCategoryId().intValue()
							&& userId.intValue() == userCat.getUserId()
									.intValue()) {
						found = true;
						continue;
					}

				}
				if (!found) {

					WSSellerResponse response = new WSSellerResponse();
					response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
					response.setResultMsg(messageI18NService
							.getPropertyMessage(WebServiceConstants.INVALID_CATEGORY_ERR_MSG));
					return response;
				}

			}

		}
		return null;
	}

	/**
	 * @param user
	 * @param orderDate
	 * @param categoryId
	 * @param wsSKU
	 * @param sellerAdminUsers
	 * @return
	 */
	public WSSellerResponse validateSKUForInsert(User user, String orderDate,
			WSSellerSKUCreateRequest wsSKU,
			List<Integer> sellerAdminUsers,
			Map<Integer, List<UsersCategory>> userCats) {
		WSSellerSKU sellerSku = new WSSellerSKU();

		sellerSku.setLoginUser(user);
		sellerSku.setOrderDate(orderDate);
		Map<String, Object> map = validateCategoryName(wsSKU.getSkuCategoryName());

		WSSellerResponse response = (WSSellerResponse) map.get("response");
		if (response != null) {
			
			return response;
		} else {
			wsSKU.setCategory((Category) map.get("category"));
		}
		Integer categoryId=wsSKU.getCategory().getCategoryId();
		sellerSku.getSku().setSkuCategoryId(categoryId);
		/*
		 * validate required fields for insert sellerID unit of measure sku
		 * group name sku name package quantity
		 */
		response = validateCreateSheetSKURequiredFields(user,
				wsSKU, sellerSku, sellerAdminUsers, userCats);
		if (response != null) {
			return response;
		}

		/**
		 * TODO SKU external id - verify if required
		 */
		response = validateExternalSKUID(wsSKU.getSkuExternalID(), true);
		if (response != null) {
			return response;
		}
		sellerSku.getSku().setExternalSkuId(wsSKU.getSkuExternalID());
		/*
		 * price1 price2 price w/o tax
		 */
		response = validatePrices(wsSKU, sellerSku.getSku());
		if (response != null) {
			return response;
		}

		/*
		 * area of production
		 */
		response = validateLocation(wsSKU.getAreaOfProduction());
		if (response != null) {
			return response;
		}
		sellerSku.getSku().setLocation(wsSKU.getAreaOfProduction());

		/*
		 * market condition
		 */
		response = validateMarket(wsSKU.getMarket());
		if (response != null) {
			return response;
		}
		sellerSku.getSku().setMarket(wsSKU.getMarket());

		/*
		 * packtype
		 */
		response = validateSKUPackageType(wsSKU.getPackageType());
		if (response != null) {
			return response;
		}
		sellerSku.getSku().setPackageType(wsSKU.getPackageType());

		/*
		 * class 1 class 2
		 */
		response = validateClasses(wsSKU, sellerSku.getSku());
		if (response != null) {
			return response;
		}

		/*
		 * maxqty
		 */
		if (!StringUtil.isNullOrEmpty(wsSKU.getMaxQty())) {
			response = validateMaxQtyString(wsSKU.getMaxQty());
			if (response != null) {
				return response;
			}
			BigDecimal qty = new BigDecimal(wsSKU.getMaxQty());
			response = validateMaxQty(qty, sellerSku.getSku().getOrderUnit()
					.getOrderUnitName());
			if (response != null) {
				return response;
			}
			sellerSku.getSku().setSkuMaxLimit(qty);
		}

		// finalizeflag
		response = validateFinalizeFlag(wsSKU.getFinalizeFlag());
		if (response != null) {
			return response;
		}
		if (wsSKU.getFinalizeFlag() == null
				|| wsSKU.getFinalizeFlag().equals("0")) {
			sellerSku.setFinalizeFlag(false);
		} else {
			sellerSku.setFinalizeFlag(true);
		}
		/*
		 * center delivery sale jan packfee cols 01 to 15
		 */

		response = validateAllColumns(wsSKU, sellerSku.getSku());
		if (response != null) {
			return response;
		}

		// orderdetails

		response = validateDetails(sellerSku.getSku(), orderDate, sellerSku
				.getSku().getOrderUnit(), wsSKU.getBuyerDetails());
		if (response != null) {
			return response;
		}
		sellerSku.setDetails(wsSKU.getBuyerDetails());
		wsSKU.setSellerSKU(sellerSku);
		return null;
	}

	/**
	 * Validates if price1 has a value. price 1 should be numeric length less
	 * than 10 digits not negative whole number
	 * 
	 * @param strPrice1
	 * @param sku
	 * @return WSSellerResponse if validation fails otherwise returns null and
	 *         sets the price1 field for the sku object
	 */
	public WSSellerResponse validatePrice1(String strPrice1, SKU sku) {
		BigDecimal price1 = null;
		if (StringUtil.isNullOrEmpty(strPrice1)) {
			return null;
		}
		// numeric
		if (NumberUtils.isNumber(strPrice1)) {
			price1 = new BigDecimal(strPrice1);
		} else {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PRICE1_ERR_MSG));
			return response;
		}

		// max 9 digits
		if (!CommonWebServiceValidation.validatePrice2(price1)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_PRICE1_ERR_MSG));
			return response;
		}

		// decimal not allowed
		if (NumberUtil.isNotWholeNumber(price1)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PRICE1_DECIMAL));
			return response;
		}

		// negative not allowed
		if (NumberUtil.isNegative(price1)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PRICE1_NEGATIVE));
			return response;
		}
		sku.setPrice1(price1);
		return null;
	}

	/**
	 * Validates if price2 has a value. price 2 should be numeric length less
	 * than 10 digits not negative whole number
	 * 
	 * @param strPrice2
	 * @param sku
	 * @return WSSellerResponse if validation fails otherwise returns null and
	 *         sets the price2 field for the sku object
	 */
	public WSSellerResponse validatePrice2(String strPrice2, SKU sku) {
		BigDecimal price2 = null;
		if (StringUtil.isNullOrEmpty(strPrice2)) {
			return null;
		}
		// numeric
		if (NumberUtils.isNumber(strPrice2)) {
			price2 = new BigDecimal(strPrice2);
		} else {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PRICE2_ERR_MSG));
			return response;
		}

		// max 9 digits
		if (!CommonWebServiceValidation.validatePrice2(price2)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_PRICE2_ERR_MSG));
			return response;
		}

		// decimal not allowed
		if (NumberUtil.isNotWholeNumber(price2)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PRICE2_DECIMAL));
			return response;
		}

		// negative not allowed
		if (NumberUtil.isNegative(price2)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PRICE2_NEGATIVE));
			return response;
		}
		sku.setPrice2(price2);
		return null;
	}

	/**
	 * Validates if location is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param location
	 * @return WSBuyerResponse if validation fails else null
	 */
	public WSSellerResponse validateLocation(String location) {
		if (!CommonWebServiceValidation.validateLocation(location)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_LOCATION_ERR_MSG));
			return response;
		}
		return null;
	}

	/**
	 * Validates the required fields when adding a new SKU required fields:
	 * sellerID unit of measure sku group name sku name package quantity
	 * 
	 * 
	 * 
	 * @param user
	 * @param wsSKU
	 * @param sellerSku
	 * @param sellerAdminUsers
	 * @return WSSellerResponse if validation fails otherwise null
	 */
	private WSSellerResponse validateCreateSheetSKURequiredFields(User user,
			WSSellerSKURequest wsSKU, WSSellerSKU sellerSku,
			List<Integer> sellerAdminUsers,
			Map<Integer, List<UsersCategory>> userCats) {

		// seller id
		Map<String, Object> map = validateSellerId(user, wsSKU.getSellerId(),
				sellerAdminUsers);

		WSSellerResponse response = (WSSellerResponse) map.get("response");
		if (response != null) {
			return response;
		}
		User seller = (User) map.get("seller");
		sellerSku.getSku().setUser(seller);
		sellerSku.setSeller(seller);

		// Category Membership.
		List<UsersCategory> list = userCats.get(seller.getUserId());
		if (CollectionUtils.isEmpty(list)) {
			list = categoryService.getCategoryListBySelectedIds(Arrays
					.asList(seller.getUserId()));
			userCats.put(seller.getUserId(), list);
		}
		boolean found = false;
		for (UsersCategory userCat : list) {
			if (sellerSku.getSku().getSkuCategoryId().intValue() == userCat
					.getCategoryId().intValue()
					&& seller.getUserId().intValue() == userCat.getUserId()
							.intValue()) {
				found = true;
				continue;
			}

		}
		if (!found) {

			response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_CATEGORY_ERR_MSG));
			return response;
		}

		/*
		 * Unit of Measure
		 */

		response = validateUom(wsSKU.getUomName());
		if (response != null) {
			return response;
		} else {
			OrderUnit orderUnit;
			if (!StringUtil.isNullOrEmpty(wsSKU.getUomName())) {
				orderUnit = orderUnitService.getOrderUnit(sellerSku.getSku()
						.getSkuCategoryId(), wsSKU.getUomName());
				if (orderUnit.getOrderUnitId() == null) {
					response = new WSSellerResponse();
					response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
					response.setResultMsg(messageI18NService
							.getPropertyMessage(WebServiceConstants.INVALID_UNITOFORDER_ERR_MSG));
					return response;
				}
			} else {
				Integer id = orderUnitService.getDefaultOrderUnit(sellerSku
						.getSku().getSkuCategoryId());
				orderUnit = orderUnitService.getOrderUnitById(id);
			}
			wsSKU.setUomName(orderUnit.getOrderUnitName());
			sellerSku.getSku().setOrderUnit(orderUnit);

		}

		// sku group name
		response = validateSkuGroupName(wsSKU.getSkuGroupName());
		if (response != null) {
			return response;
		} else {
			SKUGroup skugroup = skuGroupService.getSKUGroupByName(sellerSku
					.getSeller().getUserId(), sellerSku.getSku()
					.getSkuCategoryId(), wsSKU.getSkuGroupName());
			if (skugroup == null) {
				skugroup = new SKUGroup();
				skugroup.setCategoryId(sellerSku.getSku().getSkuCategoryId());
				skugroup.setDescription( wsSKU.getSkuGroupName());
				skugroup.setSellerId(sellerSku
						.getSeller().getUserId());
				
				
			}
			sellerSku.getSku().setSkuGroup(skugroup);
		}

		// skuname
		if (!CommonWebServiceValidation.validateSKUName(wsSKU.getSkuName())) {
			response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_SKUNAME_ERR_MSG));
			return response;
		} else if (!CommonWebServiceValidation.validateSKUNameLength(wsSKU
				.getSkuName())) {
			response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_SKUNAME_ERR_MSG));
			return response;
		}
		sellerSku.getSku().setSkuName(wsSKU.getSkuName());

		// packaging qty
		response = validatePackageQty(wsSKU.getPackageQty(), sellerSku.getSku());
		if (response != null) {
			return response;
		}
		return null;
	}

	public WSSellerResponse validatePackageQty(String strPackQty, SKU sku) {
		WSSellerResponse response = null;
		BigDecimal packQty = null;
		if (!CommonWebServiceValidation.validatePackageQty(strPackQty)) {
			response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_PACKAGEQUANITY_ERR_MSG));
			return response;
		}

		if (NumberUtils.isNumber(strPackQty)) {
			packQty = new BigDecimal(strPackQty);
		} else {
			response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PACK_QTY_ERR_MSG));
			return response;
		}

		if (!CommonWebServiceValidation.validatePackageQtyLength(packQty)) {
			response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_PACKAGEQUANITY_ERR_MSG));
			return response;
		} else if (NumberUtil.isNotWholeNumber(packQty)) {
			response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PACKAGE_QTY_DECIMAL));
			return response;
		} else if (NumberUtil.isNegative(packQty)) {
			response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PACKAGE_QTY_NEGATIVE));
			return response;
		}

		sku.setPackageQuantity(packQty);
		return null;
	}

	public Map<String, Object> validateSellerId(User user, String strSellerId,
			List<Integer> sellerAdminUsers) {
		Map<String, Object> result = new HashMap<String, Object>();

		// if seller id is not null
		if (!CommonWebServiceValidation.validateSellerId(strSellerId)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_SELLER_ERR_MSG));
			result.put("response", response);
			return result;
		}

		if (!CommonWebServiceValidation.validateSellerIdLength(strSellerId)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_SELLERID_ERR_MSG));
			result.put("response", response);
			return result;
		}

		Integer sellerId = CommonWebServiceValidation
				.validateWholeNumericString(strSellerId);
		if (sellerId == null) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_SELLER_ERR_MSG));
			result.put("response", response);
			return result;
		}
		User seller = userInfoService.getUserById(sellerId);
		if (seller == null) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.USER_SELLER_NOTEXIST_ERR_MSG));
			result.put("response", response);
			return result;
		} else if (!RolesUtil.isUserRoleSeller(seller)
				&& !RolesUtil.isUserRoleSellerAdmin(seller)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.USER_SELLER_ERR_MSG));
			result.put("response", response);
			return result;
		}

		// dealing pattern if selleradmin
		// seller id = user if seller
		// if user is SELLER, selledId should be equal to user.userid
		if (RolesUtil.isUserRoleSeller(user)) {
			if (!user.getUserId().equals(sellerId)) {
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_DP_ERR_MSG));
				result.put("response", response);
				return result;
			}
		} else {
			WSSellerResponse response = validateSellerMembership(
					Arrays.asList(sellerId), sellerAdminUsers);

			if (response != null) {
				result.put("response", response);
				return result;
			}
		}
		result.put("seller", seller);

		return result;
	}

	public Map<String, Object> validateSellerIdList(User loginUser,
			List<String> sellerIds, String orderDate) {
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuffer errMsg = new StringBuffer(
				messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_SELLER_ERR_MSG)
						+ "\n");
		boolean withErr = false;
		List<Integer> sellerAdminUsers = new ArrayList<Integer>();

		List<Integer> sellerIdList = new ArrayList<Integer>();

		if (RolesUtil.isUserRoleSellerAdmin(loginUser)) {
			if (CollectionUtils.isEmpty(sellerIds)) {
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.REQUIRED_SELLERLIST_ERR_MSG));
				result.put("response", response);
				return result;
			}
			List<User> members = dealingPatternService.getMembersByAdminId(
					loginUser.getUserId(),
					DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER,
					orderDate, orderDate);
			sellerAdminUsers = OrderSheetUtil.getUserIdList(members);
		} else {
			if (CollectionUtils.isEmpty(sellerIds)) {
				sellerIdList.add(loginUser.getUserId());
				result.put("sellerIdList", sellerIdList);

				return result;
			} else if (!sellerIds.contains(loginUser.getUserId().toString())
					|| sellerIds.size() > 1) {
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_SELLER_ERR_MSG));
				result.put("response", response);
				return result;
			}

			sellerAdminUsers.add(loginUser.getUserId());
		}

		for (String strSellerId : sellerIds) {
			
			if (!CommonWebServiceValidation.validateSellerIdLength(strSellerId)) {
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.MAXLENGTH_SELLERID_ERR_MSG));
				result.put("response", response);
				return result;
			}
			Integer sellerId = CommonWebServiceValidation
					.validateWholeNumericString(strSellerId);
			if (sellerId == null) {
				errMsg.append(strSellerId + ", ");
				withErr = true;
				continue;
			}
			if (!sellerIdList.contains(sellerId)) {
				sellerIdList.add(sellerId);
			}

		}

		if (withErr) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(errMsg.toString());
			result.put("response", response);
			return result;
		}

		withErr = false;
		errMsg = new StringBuffer(
				messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_DP_ERR_MSG)
						+ "\n");

		WSSellerResponse response = validateSellerMembership(sellerIdList,
				sellerAdminUsers);

		if (response != null) {
			result.put("response", response);
			return result;
		}

		result.put("sellerIdList", sellerIdList);
		return result;
	}

	/**
	 * Validates dealing pattern between sellers and seller admin in a given
	 * date range
	 * 
	 * @param sellerIds
	 * @return WSSellerResponse if validation fails otherwise null
	 */
	private WSSellerResponse validateSellerMembership(List<Integer> sellerIds,
			List<Integer> sellerAdminUsers) {

		List<Integer> invalidSellers = new ArrayList<Integer>();
		for (Integer sellerId : sellerIds) {
			if (!sellerAdminUsers.contains(sellerId)) {
				invalidSellers.add(sellerId);
			}
		}

		if (CollectionUtils.isNotEmpty(invalidSellers)) {
			StringBuffer errMsg = new StringBuffer(
					messageI18NService
							.getPropertyMessage(WebServiceConstants.INVALID_SELLER_DEALINGPATTERN_ERR_MSG));
			errMsg.append("\n");
			StringBuffer strSellers = new StringBuffer();
			for (Integer invalidSeller : invalidSellers) {
				strSellers.append(invalidSeller.toString() + "   ");
			}
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(errMsg.append(strSellers.toString())
					.toString());

			return response;
		}

		return null;
	}

	/**
	 * Validates if uom is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param class2
	 * @return WSBuyerResponse if validation fails else null
	 */
	public WSSellerResponse validateUom(String uom) {
		if (!CommonWebServiceValidation.validateUOM(uom)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_UOM_ERR_MSG));
			return response;
		}
		return null;
	}

	/**
	 * Validates if skugroupName is not null and if it does not exceed the
	 * maximum length.
	 * 
	 * @param skuGroupName
	 * @return WSSellerResponse if validation fails else null
	 */
	public WSSellerResponse validateSkuGroupName(String skugroupName) {
		if (StringUtil.isNullOrEmpty(skugroupName)) {
			WSSellerResponse response = new WSSellerResponse();

			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_SKUGROUPNAME_ERR_MSG));
			return response;

		} else if (skugroupName.length() > 50) {

			WSSellerResponse response = new WSSellerResponse();

			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_SKUGROUP_ERR_MSG));
			return response;
		}
		return null;
	}

	/**
	 * Validates if class1 is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param class1
	 * @return WSSellerResponse if validation fails else null
	 */
	public WSSellerResponse validateGrade(String grade) {
		if (!CommonWebServiceValidation.validateClass1(grade)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_GRADE_ERR_MSG));
			return response;
		}
		return null;
	}

	/**
	 * Validates if class2 is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param class2
	 * @return WSSellerResponse if validation fails else null
	 */
	public WSSellerResponse validateClazz(String clazz) {
		if (!CommonWebServiceValidation.validateClass2(clazz)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_CLAZZ_ERR_MSG));
			return response;
		}
		return null;
	}

	/**
	 * Validates if market is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param market
	 * @return WSSellerResponse if validation fails else null
	 */
	public WSSellerResponse validateMarket(String market) {
		if (!CommonWebServiceValidation.validateMarket(market)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_MARKET_ERR_MSG));
			return response;
		}
		return null;
	}

	/**
	 * Validates if package type is not null and if it does not exceed the
	 * maximum length.
	 * 
	 * @param packageType
	 * @return WSSellerResponse if validation fails else null
	 */
	public WSSellerResponse validateSKUPackageType(String packageType) {
		if (packageType != null
				&& !CommonWebServiceValidation
						.validatePackageTypeLength(packageType)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_PACKTYPE_ERR_MSG));
			return response;
		}
		return null;
	}

	/**
	 * Validates if price without tax price without tax should be numeric length
	 * less than 10 digits not negative whole number
	 * 
	 * @param pricewotax
	 * @return WSSellerResponse if validation fails else null
	 */
	public WSSellerResponse validatePriceWithOutTax(String strPricewotax,
			SKU sku) {
		BigDecimal pricewotax = null;
		if (StringUtil.isNullOrEmpty(strPricewotax)) {
			return null;
		}

		try {
			pricewotax = new BigDecimal(strPricewotax);

		} catch (Exception e) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PRICE_WITHOUT_TAX_ERR_MSG));
			return response;
		}

		// max length 9 digits
		if (!CommonWebServiceValidation.validatePriceWOTax(pricewotax)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_PRICEWOTAX_ERR_MSG));
			return response;
		}

		// decimal not allowed
		if (NumberUtil.isNotWholeNumber(pricewotax)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PRICE_WITHOUT_TAX_DECIMAL));
			return response;

		}

		// negative not allowed
		if (NumberUtil.isNegative(pricewotax)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PRICE_WITHOUT_TAX_NEGATIVE));
			return response;
		}
		sku.setPriceWithoutTax(pricewotax);

		return null;
	}

	/**
	 * Validates fields related to prices for SKU saving Fields are the ff:
	 * price w/o tax price 1 price 2
	 * 
	 * @param wsSKU
	 * @param sku
	 * @return
	 */
	private WSSellerResponse validatePrices(WSSellerSKURequest wsSKU,
			SKU sku) {
		WSSellerResponse response = new WSSellerResponse();

		// price w/o tax
		response = validatePriceWithOutTax(wsSKU.getUnitPriceWithoutTax(), sku);
		if (response != null) {
			return response;
		}

		// price 1
		response = validatePrice1(wsSKU.getAltPrice1(), sku);
		if (response != null) {
			return response;
		}
		// price 2
		response = validatePrice2(wsSKU.getAltPrice2(), sku);
		if (response != null) {
			return response;
		}
		return null;
	}

	/**
	 * Validates if Column is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param columnVal
	 * @param errorCode
	 * @return WSSellerResponse if validation fails else null
	 */
	public WSSellerResponse validateColumn(String columnVal, String errorCode) {
		if (columnVal != null && columnVal.length() > 42) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(errorCode));
			return response;
		}

		return null;
	}

	/**
	 * Validates if center is numeric
	 * 
	 * @param strCenter
	 * @return WSSellerResponse if validation fails
	 */
	private WSSellerResponse validateCenterString(String strCenter) {
		if (StringUtil.isNullOrEmpty(strCenter)) {
			return null;
		}

		// numeric
		if (!NumberUtils.isNumber(strCenter)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_CENTER_ERR_MSG));
			return response;
		}
		return null;
	}

	/**
	 * Validates if center price without tax should be numeric length less than
	 * 10 digits not negative whole number
	 * 
	 * @param center
	 * @return WSSellerResponse if validation fails else null
	 */
	public WSSellerResponse validateCenter(BigDecimal center) {
		if (NumberUtil.isNullOrZero(center)) {
			return null;
		}

		if (center.compareTo(new BigDecimal(999999999)) == 1) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_CENTER_ERR_MSG));
			return response;
		}

		if (NumberUtil.isNegative(center)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_CENTER_NEGATIVE));
			return response;
		}

		if (NumberUtil.isNotWholeNumber(center)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_CENTER_DECIMAL));
			return response;
		}
		return null;
	}

	/**
	 * Validates if delivery is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param delivery
	 * @return WSSellerResponse if validation fails else null
	 */
	public WSSellerResponse validateDelivery(String delivery) {
		if (delivery != null && delivery.length() > 42) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_DELIVERY_ERR_MSG));
			return response;
		}
		return null;
	}

	/**
	 * Validates if sale is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param sale
	 * @return WSSellerResponse if validation fails else null
	 */
	public WSSellerResponse validateSale(String sale) {
		if (sale != null && sale.length() > 42) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_SALE_ERR_MSG));
			return response;
		}

		return null;
	}

	/**
	 * Validates if jan is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param jan
	 * @return WSSellerResponse if validation fails else null
	 */
	public WSSellerResponse validateJan(String jan) {
		if (jan != null && jan.length() > 42) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_JAN_ERR_MSG));
			return response;
		}

		return null;
	}

	/**
	 * Validates if Pack Fee is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param packFee
	 * @return WSSellerResponse if validation fails else null
	 */
	public WSSellerResponse validatePackFee(String packFee) {
		if (packFee != null && packFee.length() > 42) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_PACKFEE_ERR_MSG));
			return response;
		}

		return null;
	}

	/**
	 * Validates all columns from the sku
	 * 
	 * @param wsBuyerSKUAdd
	 * @return WSBuyerResponse if validation fails else null
	 */
	public WSSellerResponse validateAllColumns(WSSellerSKURequest wsSKU,
			SKU sku) {
		WSSellerResponse response = new WSSellerResponse();

		// center in screen = column01 in DB
		if (!StringUtil.isNullOrEmpty(wsSKU.getCenter())) {
			response = validateCenterString(wsSKU.getCenter());
			if (response != null) {
				return response;
			}

			response = validateCenter(new BigDecimal(wsSKU.getCenter()));
			if (response != null) {
				return response;
			}
			sku.setColumn01(Integer.valueOf(wsSKU.getCenter()));
		}
		// delivery in screen = column02 in DB
		response = validateDelivery(wsSKU.getDelivery());
		if (response != null) {
			return response;
		}
		sku.setColumn02(wsSKU.getDelivery());
		// sale in screen = colum03 in DB
		response = validateSale(wsSKU.getSale());
		if (response != null) {
			return response;
		}
		sku.setColumn03(wsSKU.getSale());
		// JAN in screen = column04 in DB
		response = validateJan(wsSKU.getJan());
		if (response != null) {
			return response;
		}
		sku.setColumn04(wsSKU.getJan());
		// PackFee in screen = column05 in DB
		response = validatePackFee(wsSKU.getPackFee());
		if (response != null) {
			return response;
		}
		sku.setColumn05(wsSKU.getPackFee());
		// Col1 in screen = column06 in DB
		response = validateColumn(wsSKU.getColumn01(),
				WebServiceConstants.MAXLENGTH_COLUMN01_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn06(wsSKU.getColumn01());
		// Col2 in screen = column07 in DB
		response = validateColumn(wsSKU.getColumn02(),
				WebServiceConstants.MAXLENGTH_COLUMN02_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn07(wsSKU.getColumn02());
		// Col3 in screen = column08 in DB
		response = validateColumn(wsSKU.getColumn03(),
				WebServiceConstants.MAXLENGTH_COLUMN03_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn08(wsSKU.getColumn03());

		// Col4 in screen = column09 in DB
		response = validateColumn(wsSKU.getColumn04(),
				WebServiceConstants.MAXLENGTH_COLUMN04_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn09(wsSKU.getColumn04());

		// Col5 in screen = column10 in DB
		response = validateColumn(wsSKU.getColumn05(),
				WebServiceConstants.MAXLENGTH_COLUMN05_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn10(wsSKU.getColumn05());

		// Col6 in screen = column11 in DB
		response = validateColumn(wsSKU.getColumn06(),
				WebServiceConstants.MAXLENGTH_COLUMN06_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn11(wsSKU.getColumn06());

		// Col7 in screen = column12 in DB
		response = validateColumn(wsSKU.getColumn07(),
				WebServiceConstants.MAXLENGTH_COLUMN07_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn12(wsSKU.getColumn07());

		// Col8 in screen = column13 in DB
		response = validateColumn(wsSKU.getColumn08(),
				WebServiceConstants.MAXLENGTH_COLUMN08_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn13(wsSKU.getColumn08());

		// Col9 in screen = column14 in DB
		response = validateColumn(wsSKU.getColumn09(),
				WebServiceConstants.MAXLENGTH_COLUMN09_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn14(wsSKU.getColumn09());

		// Col10 in screen = column15 in DB
		response = validateColumn(wsSKU.getColumn10(),
				WebServiceConstants.MAXLENGTH_COLUMN10_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn15(wsSKU.getColumn10());

		// Col11 in screen = column16 in DB
		response = validateColumn(wsSKU.getColumn11(),
				WebServiceConstants.MAXLENGTH_COLUMN11_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn16(wsSKU.getColumn11());

		// Col12 in screen = column17 in DB
		response = validateColumn(wsSKU.getColumn12(),
				WebServiceConstants.MAXLENGTH_COLUMN12_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn17(wsSKU.getColumn12());

		// Col13 in screen = column18 in DB
		response = validateColumn(wsSKU.getColumn13(),
				WebServiceConstants.MAXLENGTH_COLUMN13_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn18(wsSKU.getColumn13());

		// Col14 in screen = column19 in DB
		response = validateColumn(wsSKU.getColumn14(),
				WebServiceConstants.MAXLENGTH_COLUMN14_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn19(wsSKU.getColumn14());

		// Col15 in screen = column20 in DB
		response = validateColumn(wsSKU.getColumn15(),
				WebServiceConstants.MAXLENGTH_COLUMN15_ERR_MSG);
		if (response != null) {
			return response;
		}
		sku.setColumn20(wsSKU.getColumn15());

		return null;

	}

	public WSSellerResponse validateClasses(WSSellerSKURequest wsSKU,
			SKU sku) {
		WSSellerResponse response = new WSSellerResponse();

		response = validateGrade(wsSKU.getClass1());
		if (response != null) {
			return response;
		}
		sku.setGrade(wsSKU.getClass1());
		response = validateClazz(wsSKU.getClass2());
		if (response != null) {
			return response;
		}
		sku.setClazz(wsSKU.getClass2());
		return null;
	}

	/**
	 * Validates the value of finalize flag if it is null Can only be 0 or 1
	 * 
	 * @param flag
	 * @return WSSellerResponse if validation fails else null
	 */
	public WSSellerResponse validateFinalizeFlag(String flag) {
		if (flag != null && !CommonWebServiceValidation.isValidFlagFormat(flag)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_FINALIZE_FLAG_ERR_MSG));
			return response;
		}

		return null;
	}

	/**
	 * Validates max quantity max quantity should be length less than 10 digits
	 * whole number
	 * 
	 * @param center
	 * @return WSSellerResponse if validation fails else null
	 */
	public WSSellerResponse validateMaxQty(BigDecimal maxQty, String uomName) {
		if (NumberUtil.isNullOrZero(maxQty)) {
			return null;
		}

		if (maxQty.compareTo(new BigDecimal(999999999)) == 1) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_MAXQTY_ERR_MSG));
			return response;
		}

		if (NumberUtil.isNegative(maxQty)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_MAXQTY_NEGATIVE));
			return response;
		}

		/* check if decimal is allowed for the given UOM */
		if (!OrderUnitUtility.isValidOrderUnitQty(maxQty, uomName)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_MAXQTY_DECIMAL));
			return response;
		}
		return null;
	}

	/**
	 * Validates if max qty is numeric
	 * 
	 * @param strQty
	 * @return WSSellerResponse if validation fails
	 */
	public WSSellerResponse validateMaxQtyString(String strQty) {
		if (StringUtil.isNullOrEmpty(strQty)) {
			return null;
		}

		// numeric
		if (!NumberUtils.isNumber(strQty)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_MAX_QTY_ERR_MSG));
			return response;
		}

		return null;
	}

	public WSSellerResponse validateExternalSKUID(String externalSKUID,
			boolean isRequired) {
		if (isRequired) {
			if (StringUtil.isNullOrEmpty(externalSKUID)) {
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.REQUIRED_EXTERNAL_SKUID_ERR_MSG));
				return response;
			}
		}
		if (!StringUtil.isNullOrEmpty(externalSKUID)
				&& !CommonWebServiceValidation
						.validateExternalSKUIDLength(externalSKUID)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_EXTERNALID_ERR_MSG));
			return response;
		}

		return null;
	}

	public WSSellerResponse validateDetails(SKU sku, String orderDate,
			OrderUnit uom, List<WSSellerBuyerDetails> details) {
		User seller = sku.getUser();
		BigDecimal max = sku.getSkuMaxLimit();
		List<User> buyers = dealingPatternService.getAllBuyerIdsBySellerIds(
				Arrays.asList(seller.getUserId()), orderDate, orderDate);
		List<Integer> buyerIdList = OrderSheetUtil.getUserIdList(buyers);

		if (CollectionUtils.isEmpty(details)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_DETAILLIST_ERR_MSG));
			return response;
		}
		BigDecimal qty = new BigDecimal(0);
		for (WSSellerBuyerDetails detail : details) {

			// buyerid
			String strBuyerId = detail.getBuyerId();
			OrderItem oitem = new OrderItem();
			Map<String, Object> result = validateBuyerId(strBuyerId,
					buyerIdList);
			WSSellerResponse response = (WSSellerResponse) result
					.get("response");
			if (response != null) {
				return response;
			}
			detail.setBuyer((User) result.get("buyer"));

			response = validateVisibility(detail);
			if (response != null) {
				return response;
			}

			response = validateQty(detail, uom);
			if (response != null) {
				return response;
			}
			oitem.setQuantity(detail.getQtyDec());
			if (!NumberUtil.isNullOrZero(detail.getQtyDec())) {
				qty = qty.add(detail.getQtyDec());
			}
			/*
			 * if user sets qty>0 and visibility=false return error
			 */
			BigDecimal dQty = detail.getQtyDec();
			String dVisibility = detail.getVisibility();
			if(dQty!=null && dQty.compareTo(new BigDecimal(0))!=0 && dVisibility!=null && "0".equalsIgnoreCase(dVisibility)) {
			
				response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_QTY_VISIBILITY));
				return response;
			}
			
		}
		if (!NumberUtil.isNullOrZero(max) && max.compareTo(qty) < 0) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.WS_ERROR_MSG_6));
			return response;

		}

		return null;
	}

	public WSSellerResponse validateVisibility(WSSellerBuyerDetails detail) {

		if (StringUtil.isNullOrEmpty(detail.getVisibility())) {
			detail.setVisibility("1");
		} else if (!"0".equals(detail.getVisibility())
				&& !"1".equals(detail.getVisibility())) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_VIS_FORMAT));
			return response;
		}
		return null;
	}

	public WSSellerResponse validateQty(WSSellerBuyerDetails detail,
			OrderUnit uom) {
		WSSellerResponse response = new WSSellerResponse();
		if (!StringUtil.isNullOrEmpty(detail.getQuantity())) {
			// numeric
			if (!NumberUtils.isNumber(detail.getQuantity())) {
				response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_QTY_ERR_MSG));
				return response;
			}

			// max 9 digits
			if (!CommonWebServiceValidation.validateQuantityLength(detail
					.getQuantity())) {
				response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.MAXLENGTH_QUANTITY_ERR_MSG));
				return response;

			}
			BigDecimal qty = new BigDecimal(detail.getQuantity());
			// check if negative
			if (NumberUtil.isNegative(qty)) {
				response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_QTY_NEGATIVE));
				return response;

			}

			/* check if decimal is allowed for the given UOM */
			if (!OrderUnitUtility.isValidOrderUnitQty(qty,
					uom.getOrderUnitName())) {
				response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_QTY_DECIMAL));
				return response;
			}

			/*
			 * check if the quantity is being set and at the same time
			 * visibility is being set to zero
			 */
			if ("0".equalsIgnoreCase(detail.getVisibility())
					&& NumberUtil.isNotNegative(qty)) {
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_QTY_VISIBILITY));
				return response;
			}
			detail.setQtyDec(qty);
		}
		return null;
	}

	public Map<String, Object> validateBuyerId(String strBuyerId,
			List<Integer> buyerIdList) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtil.isNullOrEmpty(strBuyerId)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_BUYER_ERR_MSG));
			result.put("response", response);
			return result;
		}

		if (!CommonWebServiceValidation.validateBuyerIdLength(strBuyerId)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_BUYERID_ERR_MSG));
			result.put("response", response);
			return result;
		}

		// numeric

		Integer buyerId = CommonWebServiceValidation
				.validateWholeNumericString(strBuyerId);
		if (buyerId == null) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_BUYER_ID_ERR_MSG));
			result.put("response", response);
			return result;
		}

		// Integer buyer = new Integer(strBuyerId);
		User buyer = userInfoService.getUserById(new Integer(strBuyerId));
		if (buyer == null) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.USER_BUYER_NOTEXIST_ERR_MSG));
			result.put("response", response);
			return result;
		} else if (!RolesUtil.isUserRoleBuyer(buyer)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_BUYER_ERR_MSG));
			result.put("response", response);
			return result;
		}

		if (!buyerIdList.contains(buyer.getUserId())) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_DP_SELLER_BUYER_ERR_MSG));
			result.put("response", response);
			return result;
		}

		result.put("buyer", buyer);
		return result;
	}

	public Map<String, Object> validateBuyerIdList(List<String> buyerIds,
			String orderDate, List<Integer> sellerIds) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (CollectionUtils.isEmpty(buyerIds)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_BUYERLIST_ERR_MSG));
			result.put("response", response);
			return result;
		}

		List<Integer> buyerIdList = new ArrayList<Integer>();
		boolean withErr = false;
		List<User> buyers = dealingPatternService.getAllBuyerIdsBySellerIds(
				sellerIds, orderDate, orderDate);
		List<Integer> buyerMembers = OrderSheetUtil.getUserIdList(buyers);
		StringBuffer errMsg = new StringBuffer(
				messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_BUYER_ID_ERR_MSG)
						+ "\n");

		for (String strBuyerId : buyerIds) {
			
			if (!CommonWebServiceValidation.validateBuyerIdLength(strBuyerId)) {
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.MAXLENGTH_BUYERID_ERR_MSG));
				result.put("response", response);
				return result;
			}

			Integer buyerId = CommonWebServiceValidation
					.validateWholeNumericString(strBuyerId);
			if (buyerId == null) {
				errMsg.append(strBuyerId + ", ");
				withErr = true;
				continue;
			}
			if (!buyerIdList.contains(buyerId)) {
				buyerIdList.add(buyerId);
			}

		}

		if (withErr) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(errMsg.toString());
			result.put("response", response);
			return result;
		}

		withErr = false;
		errMsg = new StringBuffer(
				messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_DP_SELLER_BUYER_ERR_MSG)
						+ "\n");

		for (Integer buyerId : buyerIdList) {
			if (!buyerMembers.contains(buyerId)) {
				errMsg.append(buyerId.toString() + ", ");
				withErr = true;
				continue;
			}
		}
		if (withErr) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(errMsg.toString());
			result.put("response", response);
			return result;
		}

		result.put("buyerIdList", buyerIdList);
		return result;
	}

	public Map<String, Object>  processConcurencyCheckForAdd(
			List<WSSellerSKU> skuList, String strOrderDate,
			List<Integer> sellerIds, List<Integer> buyerIds,
			List<String> dateList) {
		Map<String, Object> result = new HashMap<String, Object>();
		WSSellerResponse response = new WSSellerResponse(); 
		if (CollectionUtils.isEmpty(skuList)) {
			return null;
		}

		/* retrieve the corresponding order records */
		List<Order> allOrders = orderSheetService.getAllOrders(buyerIds,
				dateList, sellerIds);
		Map<String, Order> allOrdersMap = OrderSheetUtil
				.convertToOrderMap(allOrders);
		List<Order> orderList = new ArrayList<Order>();

		/*
		 * Performs the ff: check for order concurrency
		 */
		response = checkOrderConcurrency(skuList,
				allOrdersMap, orderList, strOrderDate, "add");

		if (response!=null) {
			result.put("response", response);
			return result;
		}
		result.put("orderList", orderList);
		return result;
	}

	private WSSellerResponse checkOrderConcurrency(List<WSSellerSKU> skuList,
			Map<String, Order> allOrdersMap, List<Order> orderList, String strOrderDate, String action) {

		if (skuList == null) {
			return null;
		}


		for (WSSellerSKU wsSKU : skuList) {

			if (wsSKU == null) {
				continue;
			}
			Integer sellerId = wsSKU.getSeller().getUserId();

			List<WSSellerBuyerDetails> details = wsSKU.getDetails();
			for (WSSellerBuyerDetails detail : details) {

				if (detail == null) {
					continue;
				}

				Integer buyerId = detail.getBuyer().getUserId();
	
				String formatOrderMapKey = OrderSheetUtil.formatOrderMapKey(
						wsSKU.getOrderDate(), buyerId, sellerId);

				Order order = allOrdersMap.get(formatOrderMapKey);
				if (order == null) {
					if(action.equalsIgnoreCase("Update")){
					
						EONError eonError = new EONError(
								WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_NOT_FOUND,
								new Object[] { strOrderDate,
										detail.getBuyer().getUserName(),
										wsSKU.getSeller().getUserName() });

						WSSellerResponse response = new WSSellerResponse();
						response.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
						response.setResultMsg(messageI18NService.getErrorMessage(eonError));
						
						return response;	
					}
				}else{
					if(!orderList.contains(order)){
						orderList.add(order);
					}
				}
				WSSellerResponse response = checkOrderForFinalizeConcurrency(order, strOrderDate, wsSKU.getSeller(), detail.getBuyer());
				
				if(response!=null){
					return response;
				}
				detail.setOrder(order);
				

			}
		}
		return null;
	}
	
	
	
	
	

	public Map<String, Object> validatePageNum(String str) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (StringUtil.isNullOrEmpty(str)) {
			result.put("pageNum", new Integer(1));
			return result;
		}
		Integer pageNum = CommonWebServiceValidation
				.validateWholeNumericString(str);
		if (NumberUtil.isNullOrZero(pageNum)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PAGENUM_ERR_MSG));
			result.put("response", response);
			return result;
		}
		
		if (pageNum.compareTo(new Integer("999999999")) == 1) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_PAGENUM_ERR_MSG));
			result.put("response", response);

			return result;
		}
		result.put("pageNum", pageNum);
		return result;
	}

	public Map<String, Object> validatePageSize(String str) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtil.isNullOrEmpty(str)) {
			result.put("pageSize", new Integer(120));
			return result;
		}
		Integer pageSize = CommonWebServiceValidation
				.validateWholeNumericString(str);
		if (NumberUtil.isNullOrZero(pageSize)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PAGESIZE_ERR_MSG));
			result.put("response", response);

			return result;
		}
		if (pageSize.intValue() > 200) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_PAGESIZE_ERR_MSG));
			result.put("response", response);

			return result;
		}
		result.put("pageSize", pageSize);

		return result;
	}

	/**
	 * Validates if the map contains an entry
	 * 
	 * @param resultMap
	 * @return WSSellerResponse if the map does not have an entry else null
	 */
	public WSSellerResponse validateOrderMap(Map<String, Object> resultMap) {
		if (resultMap == null || resultMap.size() == 0) {
			WSSellerResponse response = new WSSellerResponse();

			response.setResultCode(WebServiceConstants.ZERO_RESULTS_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.ZERO_RESULTS_MSG));
			return response;

		}

		return null;
	}

	public WSSellerResponse validateOrderList(
			List<Map<String, Object>> skuOrderList) {
		if (CollectionUtils.isEmpty(skuOrderList)) {
			WSSellerResponse response = new WSSellerResponse();

			response.setResultCode(WebServiceConstants.ZERO_RESULTS_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.ZERO_RESULTS_MSG));
			return response;

		}

		return null;
	}

	public WSSellerResponse validateSystemName(String systemName) {
		if (StringUtil.isNullOrEmpty(systemName)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_SYSNAME_ERR_MSG));
			return response;
		}
		if (systemName.length() > 42) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_SYSNAME_ERR_MSG));
			return response;
		}

		return null;
	}

	public Map<String, Object> processConcurencyCheckForDelete(
			List<WSSellerDeleteSKU> skuList, String strOrderDate, List<Order> allOrders,
			Map<String, Order> allOrdersMap ,
			List<String> dateList, Integer categoryId) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (CollectionUtils.isEmpty(skuList)) {
			return result;
		}

		List<Order> orderList = new ArrayList<Order>();
		
		
		WSSellerResponse response = checkConcurrencyForDelete(skuList, strOrderDate, allOrdersMap,orderList);
		
		if(response!=null){
			result.put("response", response);
			return result;
		}
		
		/* retrieve the corresponding order item records */
		List<OrderItem> allOrderItems = orderSheetService
				.getOrderItemsByOrderIdBulk(OrderSheetUtil
						.getOrderIdList(orderList));
		Map<String, OrderItem> allOrderItemsMap = OrderSheetUtil
				.convertToOrderItemsMap(allOrderItems);

		
		
		result = validateOrderItemsToDelete(skuList, categoryId, allOrderItemsMap, strOrderDate);
		if(result.get("response")!=null){
			return result;
		}
		
		
		List<OrderItem> oiList = (List<OrderItem>)result.get("oiToDelete"); 
		response = checkSKUQuantity(oiList);
		if(response!=null){
			result.put("response", response);
			return result;
		}
		
		
		return result;
	 
	}

	private Map<String, Object> validateOrderItemsToDelete(List<WSSellerDeleteSKU> skuList, Integer categoryId, Map<String, OrderItem> allOrderItemsMap, String strOrderDate){
		Map<String, Object> result = new HashMap<String, Object>();
		List<OrderItem> oiListToDelete = new ArrayList<OrderItem>();	
		
		
		for(WSSellerDeleteSKU wsSKU: skuList){
			
			Integer skuid = wsSKU.getSKUID();
			User seller = wsSKU.getSeller();
			Map<CompositeKey<Integer>, Order> orderMap = wsSKU.getOrderMap();
			for(User buyer: wsSKU.getBuyerList()){
				CompositeKey<Integer> orderkey = new CompositeKey<Integer>(seller.getUserId(), buyer.getUserId());
				Order order = orderMap.get(orderkey);
				
				String key = OrderSheetUtil.formatOrderItemKey(order.getOrderId(), skuid);
				OrderItem orderItem = allOrderItemsMap.get(key);
				if (orderItem != null) {
					oiListToDelete.add(orderItem);
				}else{
					WSSellerResponse response = new WSSellerResponse();
					EONError eonError = new EONError(
							WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ORDER_ITEM_NOT_FOUND,
							new Object[] { strOrderDate,
									buyer.getUserName(),
									wsSKU.getSeller().getUserName(),
									StringUtil.nullToBlank(skuid)});
					
					response.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
					response.setResultMsg(messageI18NService.getErrorMessage(eonError));
					result.put("response", response);
					return result;		
					
				}
				
			}
			
			
		}
		
		
		result.put("oiToDelete", oiListToDelete);
		return result;		

		
	}
	
	
	@SuppressWarnings("unchecked")
	public WSSellerResponse validateSKUForDelete(List<WSSellerDeleteSKU> skuList, List<Order> allOrders, Integer categoryId ){
		Map<String, Object> result = new HashMap<String, Object>();
		if(CollectionUtils.isEmpty(allOrders)){
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ORDER_NOT_FOUND));
		
			return response;
		}
		for(WSSellerDeleteSKU wsSKU :skuList ){
			
			result = validateSKU(allOrders, wsSKU.getSKUID(), wsSKU.getExternalSKUId(),  categoryId);
			WSSellerResponse response=(WSSellerResponse) result.get("response");
			if(response!=null){
				return response;
			}
			SKU skuDB = (SKU)result.get("skuDB");
			wsSKU.setSKUID(skuDB.getSkuId());
		}
		return null;	
	}
	
	
	
	public WSSellerResponse validateSKUForUpdate(List<WSSellerSKU> skuList, List<Order> allOrders, Integer categoryId ){
		Map<String, Object> result = new HashMap<String, Object>();
		if(CollectionUtils.isEmpty(allOrders)){
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ORDER_NOT_FOUND));
		
			return response;
		}
		for(WSSellerSKU wsSKU :skuList ){
			
			result = validateSKU(allOrders, wsSKU.getSku().getSkuId(), wsSKU.getSku().getExternalSkuId(), categoryId);
			WSSellerResponse response=(WSSellerResponse) result.get("response");
			if(response!=null){
				return response;
			}
			
			SKU skuDB = (SKU)result.get("skuDB");
			wsSKU.getSku().setSkuId(skuDB.getSkuId());
			wsSKU.getSku().setExternalSkuId(skuDB.getExternalSkuId());
			wsSKU.setSkuDB(skuDB);
		}
		return null;	
	}

	private Map<String, Object> validateSKU(List<Order> orderList, Integer skuid, String externalSKUid, Integer categoryId){
		Map<String, Object> result = new HashMap<String, Object>();
		List<Integer> orderIdList = new ArrayList<Integer>();
		orderIdList = OrderSheetUtil.getOrderIdList(orderList);
		List<SKU> distinctSKUList = new ArrayList<SKU>();

		distinctSKUList = orderSheetService.wsGetDistinctSKUs(orderIdList);
		Map<Integer, SKU> skuMap = OrderSheetUtil.convertToSKUMap(distinctSKUList);
		Map<CompositeKey<String>, List<SKU>> skuExternalIDMap = OrderSheetUtil.convertToSKUExternalIDMap(distinctSKUList);
		
		
		if(!NumberUtil.isNullOrZero(skuid)){
			SKU mapSKU = skuMap.get(skuid);
			if(mapSKU==null || mapSKU.getSkuCategoryId().intValue()!=categoryId.intValue()){
			
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.SKU_NOT_EXIST_ERR_MSG) + "\t[SKUID=" +skuid+"]");
				result.put("response", response);
				return result;
				
			}else if(!StringUtil.isNullOrEmpty(externalSKUid) && !externalSKUid.equals(mapSKU.getExternalSkuId())){
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.SKU_NOT_EXIST_ERR_MSG) + 
						"\t[SKUID="+skuid+",ExternalSKUID="+externalSKUid+"]");
				result.put("response", response);
				return result;
			}

			result.put("skuDB", mapSKU);
		}else{
			List<SKU> mapSKUList = skuExternalIDMap.get(new CompositeKey<String>(externalSKUid));
			if(CollectionUtils.isEmpty(mapSKUList)){
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.SKU_NOT_EXIST_ERR_MSG) + 
						"\t[ExternalSKUID="+externalSKUid+"]");
				result.put("response", response);
				return result;
			}else if(mapSKUList.size()>1){
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_EXTERNALID_NOTUNIQUE_ERR_MSG) + 
						"\t[ExternalSKUID="+externalSKUid+"]");
				result.put("response", response);
				return result;
			}
			SKU sku = mapSKUList.get(0);
			if( sku!=null && sku.getSkuCategoryId().intValue()!=categoryId.intValue()){
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.SKU_NOT_EXIST_ERR_MSG) + 
						"\t[ExternalSKUID="+externalSKUid+"]");
				result.put("response", response);
				return result;
			}
			
			result.put("skuDB", sku);
		}
		
		
		return result;
	}
	
	private  WSSellerResponse checkSKUQuantity(List<OrderItem> oiList) {
		/*
		 * Performs the ff: check if orderitem has quantity 
		 */
		if (oiList == null) {
			return null;
		}

		for (OrderItem oi : oiList) {

			if (oi == null) {
				continue;
			}
			if (NumberUtil.nullToZero(oi.getQuantity()).compareTo(new BigDecimal(0))==1 ) {
				EONError eonError = new EONError(
						WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_SKU_HAS_QTY,
						new Object[] { oi.getOrder().getDeliveryDate(),
								StringUtil.nullToBlank(oi.getSku().getSkuId()) });

				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
				response.setResultMsg(messageI18NService.getErrorMessage(eonError));
				
				
				return response;		
			}
		
		}

		
		return null;
	}
	
	private WSSellerResponse  checkConcurrencyForDelete(List<WSSellerDeleteSKU> skuList,
			String strOrderDate, Map<String, Order> allOrdersMap,List<Order> orderList ) {
		/*
		 * Performs the ff: check for order concurrency
		 */
		if (skuList == null) {
			return null;
		}

		for (WSSellerDeleteSKU wsSKU : skuList) {

			if (wsSKU == null) {
				continue;
			}
			Integer sellerId = wsSKU.getSeller().getUserId();
			Map orderMap = wsSKU.getOrderMap();
			if(orderMap==null){
				orderMap = new HashMap<CompositeKey<Integer>, Order>();
			}
			for(User buyer:wsSKU.getBuyerList()){
				Integer buyerId = buyer.getUserId();
				String formatOrderMapKey = OrderSheetUtil.formatOrderMapKey(
						strOrderDate, buyerId, sellerId);
				
				Order order = allOrdersMap.get(formatOrderMapKey);
				if (order == null) {
					EONError eonError = new EONError(
							WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_NOT_FOUND,
							new Object[] { strOrderDate,
									buyer.getUserName(),
									wsSKU.getSeller().getUserName() });

					WSSellerResponse response = new WSSellerResponse();
					response.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
					response.setResultMsg(messageI18NService.getErrorMessage(eonError));
					
					return response;	
				}
				WSSellerResponse response = checkOrderForFinalizeConcurrency(order, strOrderDate, wsSKU.getSeller(), buyer);
				
				if(response!=null){
					return response;
				}
				CompositeKey<Integer> key = new CompositeKey<Integer>(sellerId, buyerId);
				orderMap.put(key, order);
				
				if(!orderList.contains(order)){
					orderList.add(order);
				}
			}
			wsSKU.setOrderMap(orderMap);
			

			
		}

		
		return null;
	}
	
	
	
	private WSSellerResponse checkOrderForFinalizeConcurrency(Order order, String strOrderDate, User seller, User buyer){
		

		/* checks for concurrency in order object */
		if (order != null
				&& !NumberUtil.isNullOrZero(order.getOrderFinalizedBy())) {
			EONError eonError = new EONError(
					WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_ALREADY_FINALIZED,
					new Object[] { strOrderDate,
							buyer.getUserName(),
							seller.getUserName() });
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
			response.setResultMsg(messageI18NService.getErrorMessage(eonError));
			return response;	

			
		}
		return null;
	}

	
	public WSSellerResponse validateSKUForDelete(User loginUser,
			String orderDate, Integer categoryId, WSSellerDeleteSKU wsSKU,
			List<Integer> sellerAdminUsers,
			Map<Integer, List<UsersCategory>> userCats) {
		// validate sellerID
		Map<String, Object> map = validateSellerId(loginUser,
				wsSKU.getSellerId(), sellerAdminUsers);

		WSSellerResponse response = (WSSellerResponse) map.get("response");
		if (response != null) {
			return response;
		}
		User seller = (User) map.get("seller");
		wsSKU.setSeller(seller);

		// buyerId
		List<User> buyers = dealingPatternService.getAllBuyerIdsBySellerIds(
				Arrays.asList(seller.getUserId()), orderDate, orderDate);
		List<Integer> buyerIdList = OrderSheetUtil.getUserIdList(buyers);
		List<User> buyerList = wsSKU.getBuyerList();
		if(buyerList==null){
			buyerList=new ArrayList<User>();
		}
		for(String buyerId: wsSKU.getBuyerIds()){
			Map<String, Object> result = validateBuyerId(buyerId,
					buyerIdList);
			response = (WSSellerResponse) result.get("response");
			if (response != null) {
				return response;
			}
			User buyer = (User) result.get("buyer");
			if(!buyerList.contains(buyer)){
				buyerList.add(buyer);
			}
			
		}
		
		wsSKU.setBuyerList(buyerList);
		//if skuid and externalskuid are null or empty
		if (StringUtil.isNullOrEmpty(wsSKU.getSkuId()) && StringUtil.isNullOrEmpty(wsSKU.getExternalSKUId())){
			response = new WSSellerResponse();

			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_SKUID_EXTERNALSKUID_ERR_MSG));
			return response;
		}
		
		boolean xSKUReq = true;
		if (!StringUtil.isNullOrEmpty(wsSKU.getSkuId())) {
			map = validateSKUId(wsSKU.getSkuId());
			response = (WSSellerResponse) map.get("response");
			if (response != null) {
				return response;
			}
			wsSKU.setSKUID((Integer) map.get("skuId"));
			xSKUReq = false;
		}

		response = validateExternalSKUID(wsSKU.getExternalSKUId(), xSKUReq);
		if (response != null) {
			return response;
		}

		return null;

	}

	public Map<String, Object> validateSKUId(String strSKUId) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtil.isNullOrEmpty(strSKUId)) {
			WSSellerResponse response = new WSSellerResponse();

			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_SKUID_ERR_MSG));
			result.put("response", response);
			return result;
		}

		if (!CommonWebServiceValidation.validateSKUIdLength(strSKUId)) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_SKUID_ERR_MSG));
			result.put("response", response);
			return result;
		}

		// numeric

		Integer skuId = CommonWebServiceValidation
				.validateWholeNumericString(strSKUId);
		if (skuId == null) {
			WSSellerResponse response = new WSSellerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_SKU_ID_ERR_MSG));
			result.put("response", response);
			return result;
		}

		result.put("skuId", skuId);
		return result;

	}

	
	public WSSellerResponse validateSKUForUpdate(User user, String orderDate,
			Integer categoryId, WSSellerSKUUpdateRequest wsSKU,
			List<Integer> sellerAdminUsers,
			Map<Integer, List<UsersCategory>> userCats) {
		WSSellerSKU sellerSku = new WSSellerSKU();

		sellerSku.setLoginUser(user);
		sellerSku.setOrderDate(orderDate);
		sellerSku.getSku().setSkuCategoryId(categoryId);
		/*
		 * validate required fields for insert sellerID unit of measure sku
		 * group name sku name package quantity
		 */
		WSSellerResponse response = validateCreateSheetSKURequiredFields(user,
				wsSKU, sellerSku, sellerAdminUsers, userCats);
		if (response != null) {
			return response;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		//if skuid and externalskuid are null or empty
		if (StringUtil.isNullOrEmpty(wsSKU.getSkuId()) && StringUtil.isNullOrEmpty(wsSKU.getSkuExternalID())){
			response = new WSSellerResponse();

			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_SKUID_EXTERNALSKUID_ERR_MSG));
			return response;
		}
		
		boolean xSKUReq = true; 
		if (!StringUtil.isNullOrEmpty(wsSKU.getSkuId())) {
			map = validateSKUId(wsSKU.getSkuId());
			response = (WSSellerResponse) map.get("response");
			if (response != null) {
				return response;
			}
			wsSKU.setSKUID((Integer) map.get("skuId"));
			sellerSku.getSku().setSkuId((Integer) map.get("skuId"));
			xSKUReq = false;
		}

		response = validateExternalSKUID(wsSKU.getSkuExternalID(), xSKUReq);
		if (response != null) {
			return response;
		}
		sellerSku.getSku().setExternalSkuId(wsSKU.getSkuExternalID());
		/*
		 * price1 price2 price w/o tax
		 */
		response = validatePrices(wsSKU, sellerSku.getSku());
		if (response != null) {
			return response;
		}

		/*
		 * area of production
		 */
		response = validateLocation(wsSKU.getAreaOfProduction());
		if (response != null) {
			return response;
		}
		sellerSku.getSku().setLocation(wsSKU.getAreaOfProduction());

		/*
		 * market condition
		 */
		response = validateMarket(wsSKU.getMarket());
		if (response != null) {
			return response;
		}
		sellerSku.getSku().setMarket(wsSKU.getMarket());

		/*
		 * packtype
		 */
		response = validateSKUPackageType(wsSKU.getPackageType());
		if (response != null) {
			return response;
		}
		sellerSku.getSku().setPackageType(wsSKU.getPackageType());

		/*
		 * class 1 class 2
		 */
		response = validateClasses(wsSKU, sellerSku.getSku());
		if (response != null) {
			return response;
		}

		/*
		 * maxqty
		 */
		if (!StringUtil.isNullOrEmpty(wsSKU.getMaxQty())) {
			response = validateMaxQtyString(wsSKU.getMaxQty());
			if (response != null) {
				return response;
			}
			BigDecimal qty = new BigDecimal(wsSKU.getMaxQty());
			response = validateMaxQty(qty, sellerSku.getSku().getOrderUnit()
					.getOrderUnitName());
			if (response != null) {
				return response;
			}
			sellerSku.getSku().setSkuMaxLimit(qty);
		}

		
		/*
		 * center delivery sale jan packfee cols 01 to 15
		 */

		response = validateAllColumns(wsSKU, sellerSku.getSku());
		if (response != null) {
			return response;
		}

		// orderdetails

		response = validateDetails(sellerSku.getSku(), orderDate, sellerSku
				.getSku().getOrderUnit(), wsSKU.getBuyerDetails());
		if (response != null) {
			return response;
		}
		sellerSku.setDetails(wsSKU.getBuyerDetails());
		wsSKU.setSellerSKU(sellerSku);
		return null;
	}
	
	public Map<String, Object>  processConcurencyCheckForUpdate(
			List<WSSellerSKU> skuList, String strOrderDate,
			Map<String, Order> allOrdersMap ,
			List<String> dateList, Integer categoryId) {
		Map<String, Object> result = new HashMap<String, Object>();
		WSSellerResponse response = new WSSellerResponse(); 
		if (CollectionUtils.isEmpty(skuList)) {
			return result;
		}

		List<Order> orderList = new ArrayList<Order>();
		
		
		response = checkOrderConcurrency(skuList, allOrdersMap, orderList, strOrderDate, "Update");
		
		if(response!=null){
			result.put("response", response);
			return result;
		}
		
		/* retrieve the corresponding order item records */
		List<OrderItem> allOrderItems = orderSheetService
				.getOrderItemsByOrderIdBulk(OrderSheetUtil
						.getOrderIdList(orderList));
		Map<String, OrderItem> allOrderItemsMap = OrderSheetUtil
				.convertToOrderItemsMap(allOrderItems);
		
		result = validateOrderItems(skuList, categoryId, allOrderItemsMap, strOrderDate);
		response = (WSSellerResponse) result.get("response");
		if(response!=null){
			result.put("response", response);
			return result;
		}
		
		response = processSKUConcurrency(skuList, dateList);
		if(response!=null){
			result.put("response", response);
			return result;
		}
		return result;
	}
	
	private WSSellerResponse processSKUConcurrency(List<WSSellerSKU> skuList,  List<String> dateList ){
		
		WSSellerResponse response = new WSSellerResponse();

		List<SKU> skuDBList = WebServiceUtil.extractSKUFromSellerSKU(skuList);
		List<Integer> skuIdList = OrderSheetUtil.getSKUIds(skuDBList);
		
		List<OrderItem> alloiDBList = orderSheetService.getOrderItemsListOfSkuId(skuIdList, dateList);
		Map<Integer, List<OrderItem>> SKUIDOrderItemsMap = OrderSheetUtil.convertToSKUIDOrderItemsMap(alloiDBList);
		List<SKU> skuToUpdate = new ArrayList<SKU>();
		List<OrderItem> oiToUpdate = new ArrayList<OrderItem>();
		for(WSSellerSKU wsSKU : skuList){
			SKU sku = wsSKU.getSku();
			SKU skuDB = wsSKU.getSkuDB();
			sku.setSkuId(skuDB.getSkuId());
			boolean hasMetaInfoChange = hasSKUchanged(sku, skuDB);
			wsSKU.setHasMetaInfoChanged(hasMetaInfoChange);
			
			List<Order> orderList = WebServiceUtil.extractOrderFromDetails(wsSKU.getDetails());
			List<Integer> orderids = OrderSheetUtil.getOrderIdList(orderList);
			/*
			 * Check if SKU record has qty for deliverydate
			 */
			List<OrderItem> oiDBList = SKUIDOrderItemsMap.get(skuDB.getSkuId());
			if(hasMetaInfoChange){
/*				response = checkHasQtyConcurrency(hasMetaInfoChange, skuDB.getSkuId(),oiDBList , orderids);
			
				if(response!=null){
					
					return response;
				}*/
				skuToUpdate.add(sku);
			}
			
			/*
			 * check qty-visibility
			 * 
			 */
			List<OrderItem> oiRequestList = new ArrayList<OrderItem>();
			for(WSSellerBuyerDetails detail :wsSKU.getDetails()){
				BigDecimal qtyDec = detail.getQtyDec();
				String visibility = detail.getVisibility();
				response = checkOrderItemForConcurrency(detail.getoItem(), wsSKU.getOrderDate(), detail.getBuyer().getUserName(), wsSKU.getSeller().getUserName(), visibility, qtyDec);
				if(response!=null){
					return response;
				}
				if(qtyDec!=null){
					detail.getoItem().setQuantity(qtyDec);
				}
				if(visibility!=null){
					detail.getoItem().setSosVisFlag(visibility);
				}
				OrderItem oitem = detail.getoItem();
				
				oiRequestList.add(oitem);
			}
			
			
			/*
			 * Check max qty
			 */
			response = checkMaxQuantityConcurrency(sku.getSkuMaxLimit(), oiDBList, oiRequestList, orderids, sku.getSkuId(), wsSKU.getOrderDate());
			if(response!=null){
				return response;
			}
		}
		
		
		
		
		return null;
	}
	

	private WSSellerResponse checkHasQtyConcurrency(boolean hasMetaInfoChange, Integer skuId, List<OrderItem> oiDBList, List<Integer> orderIdList ){
		
		 
		for(OrderItem oiDB : oiDBList){
			if(!orderIdList.contains(oiDB.getOrderId()) && 
					NumberUtil.nullToZero(oiDB.getQuantity()).compareTo(new BigDecimal(0))>0){
				EONError eonError = new EONError(
						WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_SKU_HAS_QTY,
						new Object[] { oiDB.getOrder().getDeliveryDate(),
								skuId});
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
				response.setResultMsg(messageI18NService.getErrorMessage(eonError));
				return response;					
			}
		}
		return null;
		
	}
	
	private WSSellerResponse checkMaxQuantityConcurrency(BigDecimal maxQuantity, List<OrderItem> oiDBList, List<OrderItem> oiDetails, List<Integer> orderIdList, Integer skuId, String orderDate){
		if(maxQuantity!=null){
			BigDecimal sum = new BigDecimal(0);
			//get sum for other orders
			for(OrderItem oiDB : oiDBList){
				if(!orderIdList.contains(oiDB.getOrderId()) ){
					sum.add(NumberUtil.nullToZero(oiDB.getQuantity()));
				}
			}
			
			//add qty from details
			for(OrderItem oiDetail: oiDetails){
				sum.add(NumberUtil.nullToZero(oiDetail.getQuantity()));
			}
			
			if(sum.compareTo(maxQuantity)>0){
				EONError eonError = new EONError(
						WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_SKU_EXCEED_MAXLIMIT,
						new Object[] {orderDate,
								skuId});
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
				response.setResultMsg(messageI18NService.getErrorMessage(eonError));
				return response;
			}
		}
		
		return null;
	}
	
	private Map<String, Object> checkSKUForUpdate(List<WSSellerSKU> wsSKUList, List<Order> orderList, Integer categoryId,String strOrderDate, Map<String, OrderItem> allOrderItemsMap){
		List<OrderItem> oiList = new ArrayList<OrderItem>();
		List<SKU> skuList = new ArrayList<SKU>();
		Map<String, Object> result = new HashMap<String, Object>();
		
		
		for(WSSellerSKU wsSKU :wsSKUList ){
			List<SKU> skuListToUpdate = new ArrayList<SKU>();
			result = validateSKU(orderList, wsSKU.getSku().getSkuId(), wsSKU.getSku().getExternalSkuId(), categoryId);
			if(result.get("response")!=null){
				return result;
			}
			skuListToUpdate = (List<SKU>)result.get("skuList");
			//check if sku exist in order
			boolean exists = false;
			if (CollectionUtils.isNotEmpty(skuListToUpdate)) {
				for (SKU sku : skuListToUpdate) {
					for(WSSellerBuyerDetails detail:wsSKU.getDetails()){
						if (detail.getOrder() == null) {
							WSSellerResponse response = new WSSellerResponse();
							response.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
							response.setResultMsg(messageI18NService
									.getPropertyMessage(WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_NOT_FOUND) + 
									"\t"+wsSKU.getOrderDate()+"\t"+detail.getBuyer().getUserName()+"\t"+wsSKU.getSeller().getUserName());
							result = new HashMap<String, Object>();
							result.put("response", response);
							return result;		
						}
						String key = OrderSheetUtil.formatOrderItemKey(detail.getOrder().getOrderId(), sku.getSkuId());
						OrderItem orderItem = allOrderItemsMap.get(key);
						//check if sku is in category
						if (orderItem != null
								&& orderItem.getSku().getSkuCategoryId().intValue() == categoryId
										.intValue()) {
							exists = true;
							if(!skuList.contains(sku)){
								skuList.add(sku);
							}
							oiList.add(orderItem);
							detail.setoItem(orderItem);
							
						}
					}

				}
			}
			if(!exists){
				WSSellerResponse response = new WSSellerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.SKU_NOT_EXIST_ERR_MSG) + 
						"\t"+StringUtil.nullToBlank(wsSKU.getSku().getSkuId())+"\t"+StringUtil.nullToBlank(wsSKU.getSku().getExternalSkuId()));
				result = new HashMap<String, Object>();
				result.put("response", response);
				return result;		
			}
		}
		result = new HashMap<String, Object>();
		result.put("skuList", skuList);
		result.put("oiList", oiList);
		return result;	
	}
	

	
	
	
	private WSSellerResponse checkOrderItemForConcurrency( OrderItem orderItemDB,
			String dateString, String buyername, String sellername, String dVisibility, BigDecimal dQty) {

		//if user requests to add qty only but the order item visibility is turned off
		if(dVisibility==null && "0".equalsIgnoreCase(orderItemDB.getSosVisFlag()) && dQty!=null && dQty.compareTo(new BigDecimal(0))!=0 )
		{
			EONError eonError = new EONError(
					WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_ITEM_NOT_VISIBLE,
					new Object[] { dateString,buyername , sellername, orderItemDB.getSKUId().toString() });
			WSSellerResponse response = new WSSellerResponse();
			
			response.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
			response.setResultMsg(messageI18NService.getErrorMessage(eonError));
			
			return response;	
		}

		/* user sent a request to turn off visibility but there is already a quantity value set*/
		if (dQty==null && dVisibility!=null && "0".equalsIgnoreCase(dVisibility)) {
			/* quantity already set in DB  */
			if (orderItemDB.getQuantity() != null
					&& NumberUtil.isNotNegative(orderItemDB.getQuantity())) {
				EONError eonError = new EONError(
						WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_ITEM_VISIBILITY_NOT_UPDATABLE,
						new Object[] { dateString,buyername , sellername, orderItemDB.getSKUId().toString() });
				WSSellerResponse response = new WSSellerResponse();
				
				response.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
				response.setResultMsg(messageI18NService.getErrorMessage(eonError));
				
				return response;	
			}
		}

		

		return null;
	}
	
	
	


	
	private Map<String, Object> validateOrderItems(List<WSSellerSKU> wsSKUList,  Integer categoryId, Map<String, OrderItem> allOrderItemsMap, String strOrderDate){
		Map<String, Object> result = new HashMap<String, Object>();
		List<OrderItem> oiList = new ArrayList<OrderItem>();
		
		for(WSSellerSKU wsSKU: wsSKUList){
			for(WSSellerBuyerDetails detail : wsSKU.getDetails()){
					String key = OrderSheetUtil.formatOrderItemKey(detail
							.getOrder().getOrderId(),wsSKU.getSku().getSkuId());
					OrderItem orderItem = allOrderItemsMap.get(key);
					//check if sku is in category
					if (orderItem != null
							) {
						detail.setoItem(orderItem);
						oiList.add(orderItem);
					}else{
						WSSellerResponse response = new WSSellerResponse();
						EONError eonError = new EONError(
								WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ORDER_ITEM_NOT_FOUND,
								new Object[] { strOrderDate,
										detail.getBuyer().getUserName(),
										wsSKU.getSeller().getUserName(),
										StringUtil.nullToBlank(wsSKU.getSku().getSkuId())});
						
						response.setResultCode(WebServiceConstants.SERVICE_ERROR_CODE);
						response.setResultMsg(messageI18NService.getErrorMessage(eonError));
						result.put("response", response);
						return result;	
					}
			}
		}
		
		
		result.put("oiList", oiList);
		return result;		

		
	}
	
	private boolean hasSKUchanged(SKU sku, SKU skuDB){
		boolean hasSKUChanged = false;
		sku.setSkuId(skuDB.getSkuId());

		if(sku.getSkuName()==null){
			sku.setSkuName(skuDB.getSkuName());
		}else if(!sku.getSkuName().equals(skuDB.getSkuName())){
			hasSKUChanged = true;
		}
		if(skuDB.getOrigSkuId()==null){
			sku.setOrigSkuId(skuDB.getSkuId());
		}else{
			sku.setOrigSkuId(skuDB.getOrigSkuId());
		}
		sku.setSheetType(skuDB.getSheetType());
		sku.setSkuCategoryId(skuDB.getSkuCategoryId());

		if(sku.getLocation()==null){
			sku.setLocation(skuDB.getLocation());
		}else if(!sku.getLocation().equals(skuDB.getLocation())){
			hasSKUChanged = true;
		}
		
		if(sku.getMarket()==null){
			sku.setMarket(skuDB.getMarket());
		}else if(!sku.getMarket().equals(skuDB.getMarket())){
			hasSKUChanged = true;
		}

		
		if(sku.getGrade()==null){
			sku.setGrade(skuDB.getGrade());
		}else if(!sku.getGrade().equals(skuDB.getGrade())){
			hasSKUChanged = true;
		}
		
		if(sku.getClazz()==null){
			sku.setClazz(skuDB.getClazz());
		}else if(!sku.getClazz().equals(skuDB.getClazz())){
			hasSKUChanged = true;
		}

		if(sku.getPrice1()==null){
			sku.setPrice1(skuDB.getPrice1());
		}else if (sku.getPrice1().compareTo(NumberUtil.nullToZero(skuDB.getPrice1()))!=0){
			hasSKUChanged = true;
		}
		
		if(sku.getPrice2()==null){
			sku.setPrice2(skuDB.getPrice2());
		}else if (sku.getPrice2().compareTo(NumberUtil.nullToZero(skuDB.getPrice2()))!=0){
			hasSKUChanged = true;
		}

		if(sku.getPriceWithoutTax()==null){
			sku.setPriceWithoutTax(skuDB.getPriceWithoutTax());
		}else if (sku.getPriceWithoutTax().compareTo(NumberUtil.nullToZero(skuDB.getPriceWithoutTax()))!=0){
			hasSKUChanged = true;
		}
	
		if(sku.getPackageQuantity()==null){
			sku.setPackageQuantity(skuDB.getPackageQuantity());
		}else if (sku.getPackageQuantity().compareTo(NumberUtil.nullToZero(skuDB.getPackageQuantity()))!=0){
			hasSKUChanged = true;
		}
		
		if(sku.getPackageType()==null){
			sku.setPackageType(skuDB.getClazz());
		}else if(!sku.getPackageType().equals(skuDB.getPackageType())){
			hasSKUChanged = true;
		}
		sku.setPmfFlag(skuDB.getPmfFlag());
		
		if(sku.getSkuMaxLimit()==null){
			sku.setSkuMaxLimit(skuDB.getSkuMaxLimit());
		}else if (sku.getSkuMaxLimit().compareTo(NumberUtil.nullToZero(skuDB.getSkuMaxLimit()))!=0){
			hasSKUChanged = true;
		}
		if(sku.getComments()==null){
			sku.setComments(skuDB.getComments());
		}else if(!sku.getSkuComment().equals(skuDB.getComments())){
			hasSKUChanged = true;
		}
		
		if(sku.getColumn01()==null){
			sku.setColumn01(skuDB.getColumn01());
		}else if(sku.getColumn01().intValue()!=NumberUtil.nullToZero(skuDB.getColumn01()).intValue()){
			hasSKUChanged = true;
		}
		
		if(sku.getColumn02()==null){
			sku.setColumn02(skuDB.getColumn02());
		}else if(!sku.getColumn02().equals(skuDB.getColumn02())){
			hasSKUChanged = true;
		}
		if(sku.getColumn03()==null){
			sku.setColumn03(skuDB.getColumn03());
		}else if(!sku.getColumn03().equals(skuDB.getColumn03())){
			hasSKUChanged = true;
		}
		
		if(sku.getColumn04()==null){
			sku.setColumn04(skuDB.getColumn04());
		}else if(!sku.getColumn04().equals(skuDB.getColumn04())){
			hasSKUChanged = true;
		}
		if(sku.getColumn05()==null){
			sku.setColumn05(skuDB.getColumn05());
		}else if(!sku.getColumn05().equals(skuDB.getColumn05())){
			hasSKUChanged = true;
		}
		if(sku.getColumn06()==null){
			sku.setColumn06(skuDB.getColumn06());
		}else if(!sku.getColumn06().equals(skuDB.getColumn06())){
			hasSKUChanged = true;
		}
		if(sku.getColumn07()==null){
			sku.setColumn07(skuDB.getColumn07());
		}else if(!sku.getColumn07().equals(skuDB.getColumn07())){
			hasSKUChanged = true;
		}
		if(sku.getColumn08()==null){
			sku.setColumn08(skuDB.getColumn08());
		}else if(!sku.getColumn08().equals(skuDB.getColumn08())){
			hasSKUChanged = true;
		}
		if(sku.getColumn09()==null){
			sku.setColumn09(skuDB.getColumn09());
		}else if(!sku.getColumn09().equals(skuDB.getColumn09())){
			hasSKUChanged = true;
		}
		if(sku.getColumn10()==null){
			sku.setColumn10(skuDB.getColumn10());
		}else if(!sku.getColumn10().equals(skuDB.getColumn10())){
			hasSKUChanged = true;
		}
		if(sku.getColumn11()==null){
			sku.setColumn11(skuDB.getColumn11());
		}else if(!sku.getColumn11().equals(skuDB.getColumn11())){
			hasSKUChanged = true;
		}
		if(sku.getColumn12()==null){
			sku.setColumn12(skuDB.getColumn12());
		}else if(!sku.getColumn12().equals(skuDB.getColumn12())){
			hasSKUChanged = true;
		}
		if(sku.getColumn13()==null){
			sku.setColumn13(skuDB.getColumn13());
		}else if(!sku.getColumn13().equals(skuDB.getColumn13())){
			hasSKUChanged = true;
		}
		if(sku.getColumn14()==null){
			sku.setColumn14(skuDB.getColumn14());
		}else if(!sku.getColumn14().equals(skuDB.getColumn14())){
			hasSKUChanged = true;
		}
		if(sku.getColumn15()==null){
			sku.setColumn15(skuDB.getColumn15());
		}else if(!sku.getColumn15().equals(skuDB.getColumn15())){
			hasSKUChanged = true;
		}
		if(sku.getColumn16()==null){
			sku.setColumn16(skuDB.getColumn16());
		}else if(!sku.getColumn16().equals(skuDB.getColumn16())){
			hasSKUChanged = true;
		}
		if(sku.getColumn17()==null){
			sku.setColumn17(skuDB.getColumn17());
		}else if(!sku.getColumn17().equals(skuDB.getColumn17())){
			hasSKUChanged = true;
		}
		if(sku.getColumn18()==null){
			sku.setColumn18(skuDB.getColumn18());
		}else if(!sku.getColumn18().equals(skuDB.getColumn18())){
			hasSKUChanged = true;
		}
		if(sku.getColumn19()==null){
			sku.setColumn19(skuDB.getColumn19());
		}else if(!sku.getColumn19().equals(skuDB.getColumn19())){
			hasSKUChanged = true;
		}
		if(sku.getColumn20()==null){
			sku.setColumn20(skuDB.getColumn20());
		}else if(!sku.getColumn20().equals(skuDB.getColumn20())){
			hasSKUChanged = true;
		}
		
		return hasSKUChanged;
		
	}
	

}
