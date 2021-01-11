package ws.freshremix.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.WebServiceConstants;
import com.freshremix.exception.EONError;
import com.freshremix.exception.WebserviceException;
import com.freshremix.model.Category;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.model.WSBuyerAddOrderSheetDetails;
import com.freshremix.model.WSBuyerAddOrderSheetRequest;
import com.freshremix.model.WSBuyerAddOrderSheetSKU;
import com.freshremix.model.WSBuyerLoadOrderRequest;
import com.freshremix.model.WSBuyerResponse;
import com.freshremix.model.WSBuyerSKUAdd;
import com.freshremix.model.WSBuyerSKUUpdate;
import com.freshremix.service.CategoryService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.LoginService;
import com.freshremix.service.MessageI18NService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.service.SKUService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.OrderUnitUtility;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.StringUtil;

public class WebServiceBuyerValidator {

	private MessageI18NService messageI18NService;
	private LoginService loginService;
	private UsersInformationService userInfoService;
	private CategoryService categoryService;
	private DealingPatternService dealingPatternService;
	private SKUGroupService skuGroupService;
	private OrderUnitService orderUnitService;
	private SKUService skuService;
	private OrderSheetService orderSheetService;

	
	
	
	
	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	public void setSkuService(SKUService skuService) {
		this.skuService = skuService;
	}
	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}
	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public WebServiceBuyerValidator() {
	}

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
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
	 * Returns an instance of WSBuyerResponse if validation failed,
	 * else return null
	 * 
	 * @param inputDate
	 * @return WSBuyerResponse
	 */
	public WSBuyerResponse validateRequest(
			WSBuyerLoadOrderRequest request) {

		if (request == null) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_REQUEST_ERR_MSG));
			return response;
		}
		return null;
	}

	/**
	 * Validates the start date and end dates for the following:
	 * 
	 * -date range more than 7 days
	 * {@link #validateDateRange(DateTime, DateTime)} 
	 * -end date is earlier than start date 
	 * {@link #validateDateRange(DateTime, DateTime)}
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public WSBuyerResponse validateDates(Date startDate, Date endDate) {
		WSBuyerResponse response = new WSBuyerResponse();

		if (!validateDate(startDate)) {
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_ORDERDATE_ERR_MSG));

			return response;
		}

		if (!validateDate(endDate)) {
			response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_ORDERDATE_ERR_MSG));

			return response;

		}

		DateTime startDateTime = (new DateTime(startDate))
				.withTimeAtStartOfDay();
		DateTime endDateTime = (new DateTime(endDate)).withTimeAtStartOfDay();

		response = validateDateRange(startDateTime, endDateTime);
		if (response != null) {
			return response;
		}

		return null;
	}

	/**
	 * Validates if the start date and end date are within 7 days and that end
	 * date is not earlier than the start date
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public WSBuyerResponse validateDateRange(DateTime startDate,
			DateTime endDate) {

		if (!CommonWebServiceValidation.validateDateRange(startDate, endDate)) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_ORDERDATE_ERR_MSG));
			return response;
		}

		return null;
	}

	/**
	 * Validates if date entered is null.
	 * 
	 * @param inputDate
	 * @return false if date is null else true
	 */
	private boolean validateDate(Date inputDate) {

		if (inputDate == null) {
			return false;
		}
		return true;
	}

	/**
	 * Validates the following: 
	 * - username password is not null or empty 
	 * -username password is valid - user is either a Buyer or Buyer Admin
	 * 
	 * Returns a Map containing: response - not null if validation fails user -
	 * not null if validation succeeds
	 * 
	 * @param username
	 * @param password
	 * @return Map
	 */

	public Map<String, Object> validateBuyerUser(String username,
			String password) {
		Map<String, Object> result = new HashMap<String, Object>();
		WSBuyerResponse response = new WSBuyerResponse();
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

		if (!RolesUtil.isBuyerType(user.getRole())) {
			response.setResultCode(WebServiceConstants.USER_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.USER_BUYER_ERR_MSG));
			result.put("response", response);
		

			return result;

		}
		
		result.put("user", user);
		return result;
	}

	/**
	 * This method assumes that the list input has atleast 1 entry.
	 * 
	 * @param list
	 *            - assumes that it has atleast 1 entry
	 * @param user
	 * @return
	 */
	public WSBuyerResponse validateBuyerIdList(List<Integer> list,
			User user) {
		WSBuyerResponse response = null;
		// if user is not a buyer admin
		if (!user.getRole().getBuyerAdminFlag().equals("1")) {

			if (list.size() == 1 && !list.contains(user.getUserId())) {
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_BUYER_ERR_MSG));
				return response;
			} else if (list.size() > 1) {
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_BUYER_ERR_MSG));
				return response;
			}

		}
		// if buyer admin
		else {
			List<Integer> nonMembers = userInfoService
					.getNonCompanyMembersFromList(user.getCompany()
							.getCompanyId(), list);
			if (nonMembers != null && nonMembers.size() > 0) {

				Map<String, String> params = new HashMap<String, String>();
				params.put("buyerList", nonMembers.toString());
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_BUYER_ERR_MSG));

				response.setResultMsg(
						messageI18NService
								.getPropertyMessage(WebServiceConstants.INVALID_BUYER_ERR_MSG),
						params);
				return response;

			}
		}

		return null;
	}

	/**
	 * Validates if the list of Order contains an entry
	 * 
	 * @param list
	 * @return WSBuyerResponse if the map does not have an entry else
	 *         null
	 */
	public WSBuyerResponse validateBuyerPublishedOrders(
			List<Order> list) {
		if (list == null || list.size() == 0) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.ZERO_RESULTS_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.ZERO_RESULTS_MSG));
			return response;
		}

		return null;
	}

	/**
	 * Validates if the map contains an entry
	 * 
	 * @param resultMap
	 * @return WSBuyerResponse if the map does not have an entry else
	 *         null
	 */
	public WSBuyerResponse validateOrderMap(
			Map<String, Object> resultMap) {
		if (resultMap == null || resultMap.size() == 0) {
			WSBuyerResponse response = new WSBuyerResponse();

			response.setResultCode(WebServiceConstants.ZERO_RESULTS_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.ZERO_RESULTS_MSG));
			return response;

		}

		return null;
	}

	/**
	 * Validates if the list contains an entry
	 * 
	 * @param sellerIds
	 * @return WSBuyerResponse if the list does not have an entry else
	 *         null
	 */
	public WSBuyerResponse validateSellerIdList(List<Integer> sellerIds) {
		if (sellerIds == null || sellerIds.size() == 0) {
			WSBuyerResponse response = new WSBuyerResponse();

			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_SELLER_ERR_MSG));
			return response;

		}

		return null;
	}

	/**
	 * Validates if the list of Order map contains an entry
	 * 
	 * @param dataList
	 * @return WsSBuyerLoadOrderResponse if the map does not have an entry else
	 *         null
	 */
	public WSBuyerResponse validateOrderMap(
			List<Map<String, Object>> dataList) {
		if (dataList == null || dataList.size() == 0) {
			WSBuyerResponse response = new WSBuyerResponse();

			response.setResultCode(WebServiceConstants.ZERO_RESULTS_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.ZERO_RESULTS_MSG));
			return response;

		}

		return null;
	}

	/**
	 * Validates the following: 
	 * if BUYER ADMIN validate if at least one list has an entry 
	 * ELSE validate if updatelist has an entry and addlist has no entry
	 * 
	 * Returns an instance of WSBuyerResponse if validation failed,
	 * else return null
	 * 
	 * @param updateList
	 * @param addList
	 * @return WSBuyerResponse
	 */
	public WSBuyerResponse validateAddOrderSheetLists(User user,
			List<WSBuyerSKUUpdate> updateList, List<WSBuyerSKUAdd> addList) {

		// if buyer admin check if both lists has no entry
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)) {
			if ((updateList == null || updateList.size() == 0)
					&& (addList == null || addList.size() == 0)) {
				WSBuyerResponse response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.REQUIRED_ADDUPDATELIST_ERR_MSG));
				return response;
			}
		} else {
			if (addList != null && addList.size() > 0) {
				WSBuyerResponse response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_USER_ADDSKU_ERR_MSG));
				return response;
			}else if (updateList == null || updateList.size() == 0) {
				WSBuyerResponse response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.REQUIRED_UPDATELIST_ERR_MSG));
				return response;
			} 
		}
		
		if(updateList!=null && updateList.size()>500){
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_UPDATELIST_ERR_MSG));
			return response;
		}else if(addList!=null && addList.size()>500){
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_ADDLIST_ERR_MSG));
			return response;
		}
		return null;
	}


	/**
	 * Validates if skuid is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param skuId
	 * @return WSBuyerResponse if validation fails else null
	 */
	public WSBuyerResponse validateSkuId(Integer skuId) {
		if (!CommonWebServiceValidation.validateSKUId(skuId)) {
			WSBuyerResponse response = new WSBuyerResponse();

			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_SKUID_ERR_MSG));
			return response;

		} else if (!CommonWebServiceValidation.validateSKUIdLength(skuId)) {
			WSBuyerResponse response = new WSBuyerResponse();

			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_SKUID_ERR_MSG));
			return response;
		}
		
		return null;
	}
	
	/**
	 * Validates if buyerId is not null or empty
	 * and user is a Buyer or Buyer Admin

	 * Returns a Map containing: response - not null if validation fails 
	 * user - not null if validation succeeds
	 * 
	 * @param buyerId
	 * @return Map
	 */
	public Map<String, Object> validateBuyerId(Integer buyerId) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (StringUtil.isNullOrEmpty(buyerId)) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_BUYER_ERR_MSG));
			result.put("response", response);
			return result;
		}else
		{
			User buyer = userInfoService.getUserById(buyerId);
			if(buyer == null){
				WSBuyerResponse response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.USER_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.USER_BUYER_NOTEXIST_ERR_MSG));
				result.put("response", response);
				return result;
			}else if(!buyer.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN) &&
					!buyer.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER) ){
				WSBuyerResponse response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.USER_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.USER_BUYER_ERR_MSG));
				result.put("response", response);
				return result;
			}
			result.put("buyer", buyer);
		}
		return result;
	}

	
	/**
	 * Validates if sellerId is not null or empty
	 * and user is a Seller or Seller Admin

	 * Returns a Map containing: response - not null if validation fails 
	 * user - not null if validation succeeds
	 * 
	 * @param buyerId
	 * @return Map
	 */
	public Map<String, Object> validateSellerId(Integer sellerId) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (!CommonWebServiceValidation.validateSellerId(sellerId)) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_SELLER_ERR_MSG));

			result.put("response", response);
			return result;
		}else
		{
			User seller = userInfoService.getUserById(sellerId);
			if(seller == null){
				WSBuyerResponse response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.USER_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.USER_SELLER_NOTEXIST_ERR_MSG));
				result.put("response", response);
				return result;
			}else if(!seller.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN) &&
					!seller.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER) ){
				WSBuyerResponse response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.USER_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.USER_SELLER_ERR_MSG));
				result.put("response", response);
				return result;
			}
			result.put("seller", seller);
		}
		return result;
	}
	
	/***
	 * Validates required fields need both by update and insert/save
	 * Fields are the ff:
	 * buyerid
	 * sellerid
	 * 
	 * @param sku
	 * @return WSBuyerResponse if validation fails else null
	 */
	private WSBuyerResponse validateCommonRequiredSKUFields(User user, Date orderDate,
			WSBuyerAddOrderSheetSKU sku) {
		WSBuyerResponse response = new WSBuyerResponse();

		
		
		
		// sellerid
		 Map<String, Object> result = validateSellerId(sku.getSellerId());
		
		
		response =(WSBuyerResponse) result.get("response");
		if (response!= null) {
			return response;
		}
		else{
			sku.setSeller((User)result.get("seller"));
		}
		
		
		
		response =validateSKUBuyerDetails(user, sku, orderDate);
		if (response!= null) {
			return response;
		}
		
		return null;
		
	}
	
	/**
	 * Validates if orderDate is not null 
	 * 
	 * @param orderDate
	 * @return WSBuyerResponse if validation fails else null
	 */
	public WSBuyerResponse validateOrderDate(Date orderDate){
		WSBuyerResponse response = new WSBuyerResponse();

		if (!validateDate(orderDate)) {
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_ORDERDATE_ERR_MSG));

			return response;
		}
		
		return null;
	}
	
	/**
	 * Validates required fields for update 
	 * -buyer: qty is required and should not exceed the maximum limit of 9 digits
	 * -buyer admin: at least one field from the following: 
	 * 				 qty, selling price, purchase price, skucomment and selling uom,
	 *               should not be null
	 * 
	 * @param user
	 * @param wsBuyerSKUUpdate
	 * @return WSBuyerResponse if validation fails else null
	 */
	public WSBuyerResponse validateWSSKUFieldsforUpdate(User user,Date orderDate,
			WSBuyerSKUUpdate wsBuyerSKUUpdate, Integer categoryId) {
		WSBuyerResponse response = new WSBuyerResponse();
		response = validateSkuId(wsBuyerSKUUpdate.getSkuId());
		if (response != null) {
			return response;
		}else{
			SKU skuObj = skuService.selectSKU(wsBuyerSKUUpdate.getSkuId());
			if(skuObj==null){
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.SKU_NOT_EXIST_ERR_MSG));
				return response;
			}
			
			/* sku id is not equal to the category id Redmine 1624*/
			if (!skuObj.getSkuCategoryId().equals(categoryId)) {
				Map<String, String> arguments = new HashMap<String, String>();
				arguments.put("skuid", skuObj.getSkuId().toString());
				String completeMessage = messageI18NService.getPropertyMessage(
						WebServiceConstants.INVALID_UPDATE_SKU_CATEGORY,
						arguments);
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(completeMessage);
				return response;
			}
			
			
			wsBuyerSKUUpdate.setSku(skuObj);
		}
		
		/* moved to this section so that sku obj is already set */
		response = validateCommonRequiredSKUFields(user, orderDate,wsBuyerSKUUpdate);
		if (response != null) {
			return response;
		}

		/*
		 * seller ID of SKU must match the seller ID from ws data. Placed in
		 * this section so that sellerid is already validated
		 */
		if (!wsBuyerSKUUpdate.getSku().getUser().getUserId()
				.equals(wsBuyerSKUUpdate.getSellerId())) {
			response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.SKU_NOT_EXIST_ERR_MSG));
			return response;
		}

		response = validateCommonFields(user, wsBuyerSKUUpdate, categoryId);
		if (response != null) {
			return response;
		}
		
		return null;
		
	}

	/***
	 * Validates nonEmpty fields if they exceed the the maximum length
	 * Fields are the ff:
	 * qty
	 * selling price
	 * purchase price
	 * sku comment
	 * selling uom
	 * 
	 * @param sku
	 * @return WSBuyerResponse if validation fails else null
	 */
	private WSBuyerResponse validateCommonFields(User user, 
			WSBuyerAddOrderSheetSKU sku, Integer categoryId) {
		WSBuyerResponse response = new WSBuyerResponse();

		boolean isUserRoleBuyer = RolesUtil.isUserRoleBuyer(user);

		// selling price
		if (sku.getB_sellingPrice() != null) {
			
			if (isUserRoleBuyer) {
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_UPDATESKU_BA_FIELDS));
				return response;
			} 

			if (NumberUtil.isNegative(sku.getB_sellingPrice())) {
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_SELLING_PRICE_NEGATIVE));

				return response;
			}
			
			if (NumberUtil.isNotWholeNumber(sku.getB_sellingPrice())){
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_SELLING_PRICE_DECIMAL));

				return response;
			}
			
			if (sku.getB_sellingPrice().compareTo(new BigDecimal(999999999)) == 1) {
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.MAXLENGTH_SELLING_PRICE_ERR_MSG));

				return response;
			}
			
		}
			
		// purchase price
		if (sku.getB_purchasePrice() != null) {
			if (isUserRoleBuyer) {
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_UPDATESKU_BA_FIELDS));
				return response;
			} 

			if (NumberUtil.isNegative(sku.getB_purchasePrice())) {
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_PURCHASE_PRICE_NEGATIVE));

				return response;
			}

			if (!NumberUtil.isWholeNumber(sku.getB_purchasePrice())){
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_PURCHASE_PRICE_DECIMAL));

				return response;
			}

			if (sku.getB_purchasePrice().compareTo(new BigDecimal(999999999)) == 1) {
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.MAXLENGTH_PURCHASE_PRICE_ERR_MSG));

				return response;
			}
		}

		
		//sku comment
		if (!StringUtil.isNullOrEmpty(sku.getB_skuComment())) {
			if (isUserRoleBuyer) {
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_UPDATESKU_BA_FIELDS));
				return response;
			}

			if (sku.getB_skuComment().length()>50) {
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.MAXLENGTH_SKUCOMMENT_ERR_MSG));

				return response;
			}
			
		}
		
		//selling uom
		if (!StringUtil.isNullOrEmpty(sku.getB_sellingUomName())) {
			if (isUserRoleBuyer) {
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_UPDATESKU_BA_FIELDS));
				return response;
			}
			
			if(sku.getB_sellingUomName().length()>42){
				response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.MAXLENGTH_SELLINGUOM_NAME_ERR_MSG));

				return response;

			}else{
				OrderUnit orderUnit = orderUnitService.getSellingUom(categoryId, sku.getB_sellingUomName());
				if(orderUnit==null){
					response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
					response.setResultMsg(messageI18NService
							.getPropertyMessage(WebServiceConstants.INVALID_SELLING_UNITOFORDER_ERR_MSG));
					return response;
				}
				sku.setB_sellingUom(orderUnit);
			}
		}
		return null;
	}
	
	public WSBuyerResponse validateWSSKUFieldsforInsert(User user, Date orderDate,
			WSBuyerSKUAdd sku, Category category) {
		Integer categoryId = category.getCategoryId();
		WSBuyerResponse response = new WSBuyerResponse();
		
		response = validateCommonRequiredSKUFields(user,orderDate,sku);
		if (response != null) {
			return response;
		}

		response = validateAddSKURequiredFields(sku, user, category);
		if (response != null) {
			return response;
		}
		response = validateCommonFields(user, sku, categoryId);
		if (response != null) {
			return response;
		}
		
		
		
		//uom
		response = validateUom(sku.getUnitOrderName());
		if (response != null) {
			return response;
		}else{
			OrderUnit orderUnit;
			if( !StringUtil.isNullOrEmpty(sku.getUnitOrderName())){
				orderUnit = orderUnitService.getOrderUnit(categoryId, sku.getUnitOrderName());
				if(orderUnit.getOrderUnitId()==null){


					response = new WSBuyerResponse();
					response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
					response.setResultMsg(messageI18NService
							.getPropertyMessage(WebServiceConstants.INVALID_UNITOFORDER_ERR_MSG));
					return response;
				}
				
				
			}else{
				Integer id = orderUnitService.getDefaultOrderUnit(categoryId);
				orderUnit = orderUnitService.getOrderUnitById(id);
			}
			sku.setUnitOrder(orderUnit);
			sku.setUnitOrderName(orderUnit.getOrderUnitName());

		}
		

		//market
		
		response = validateMarket(sku.getMarket());
		if (response != null) {
			return response;
		}
		
		
		//location/area of production
		
		response = validateLocation(sku.getAreaOfProduction());
		if (response != null) {
			return response;
		}
		
		
		//grade
		response = validateGrade(sku.getGrade());
		if (response != null) {
			return response;
		}
		
		//clazz
		response = validateClazz(sku.getClazz());
		if (response != null) {
			return response;
		}
		
		
		//validate prices
		
		response = validatePrices(sku);
		if (response != null) {
			return response;
		}
		
		//packaging type
		response = validateSKUPackageType(sku.getPackageType());
		if (response!=null){
			return response;
		}
		
		
		return validateAllColumns(sku);
			
	}
	
	/**
 	 * Validates required fields to save new SKU
	 * Fields are the ff:
	 * skuname
	 * packaging qty
	 * groupid
	 * packaging type
	 * delivery
	 * 
	 * @param sku
	 * @return WSBuyerResponse if validation fails else null
	 */
	private WSBuyerResponse validateAddSKURequiredFields(WSBuyerSKUAdd sku, User user, Category category)
	{
		WSBuyerResponse response = new WSBuyerResponse();

		//category 
		/**TODO
		 * check if category exists for seller
		 */
		Category cat = categoryService.getCategoryByName(sku.getSeller().getUserId(), category.getDescription());
		if(cat==null){
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			Map<String, String> arguments = new HashMap<String, String>();
			arguments.put("sellerId", sku.getSeller().getUserId().toString());
			String completeMessage = messageI18NService.getPropertyMessage(
					WebServiceConstants.INVALID_SELLER_CATEGORY,
					arguments);
			response.setResultMsg(completeMessage);
			return response;
		}
		
		//skuname
		if (!CommonWebServiceValidation.validateSKUName(sku.getSkuName())) {
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.REQUIRED_SKUNAME_ERR_MSG));
			return response;
		} else if (!CommonWebServiceValidation.validateSKUNameLength(sku.getSkuName())){
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.MAXLENGTH_SKUNAME_ERR_MSG));
			return response;
		}

		//packaging qty
		if (!CommonWebServiceValidation.validatePackageQty(sku.getPackageQuantity())) {
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.REQUIRED_PACKAGEQUANITY_ERR_MSG));
			return response;
		} else if (!CommonWebServiceValidation.validatePackageQtyLength(sku.getPackageQuantity())) {
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.MAXLENGTH_PACKAGEQUANITY_ERR_MSG));
			return response;
		} else if(NumberUtil.isNotWholeNumber(sku.getPackageQuantity())){
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.INVALID_PACKAGE_QTY_DECIMAL));
			return response;
		} else if(NumberUtil.isNegative(sku.getPackageQuantity())){
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.INVALID_PACKAGE_QTY_NEGATIVE));
			return response;
		}

		//sku groupname
			response = validateSkuGroupName(sku.getSkuGroupName());
			if (response!=null){
				return response;
			}
			else
			{
				SKUGroup skugroup = skuGroupService.getSKUGroupByName(sku.getSellerId(), category.getCategoryId(), sku.getSkuGroupName());
				if(skugroup==null){
					response = new WSBuyerResponse();
					response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
					response.setResultMsg(
							messageI18NService.getPropertyMessage(
									WebServiceConstants.SKU_GROUP_NAME_NOT_EXIST_ERR_MSG));
					return response;
				}
				sku.setSkuGroupId(skugroup.getSkuGroupId());
				
			}
		
	
		return null;
	}
	
	/**
	 * Validates if skugroupName is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param skuGroupName
	 * @return WSBuyerResponse if validation fails else null
	 */
	public WSBuyerResponse validateSkuGroupName(String skugroupName) {
		if (StringUtil.isNullOrEmpty(skugroupName)) {
			WSBuyerResponse response = new WSBuyerResponse();

			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_SKUGROUPNAME_ERR_MSG));
			return response;

		} else if(skugroupName.length()>50){
		
			WSBuyerResponse response = new WSBuyerResponse();

			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_SKUGROUP_ERR_MSG));
			return response;
		}
		return null;
	}

	
	/**
	 * Validates if package type is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param packageType
	 * @return WSBuyerResponse if validation fails else null
	 */
	public WSBuyerResponse validateSKUPackageType(String packageType) {
		if (packageType != null
				&& !CommonWebServiceValidation
						.validatePackageTypeLength(packageType)) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_PACKTYPE_ERR_MSG));
			return response;
		}
		return null;
	}	
	
	
	/**
	 * Validates if class1 is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param class1
	 * @return WSBuyerResponse if validation fails else null
	 */
	public WSBuyerResponse validateGrade(String grade){
		if (!CommonWebServiceValidation.validateClass1(grade)){
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.MAXLENGTH_GRADE_ERR_MSG));
			return response;
		}
		return null;
	}
	
	/**
	 * Validates if class2 is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param class2
	 * @return WSBuyerResponse if validation fails else null
	 */
	public WSBuyerResponse validateClazz(String clazz){
		if (!CommonWebServiceValidation.validateClass2(clazz)){
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.MAXLENGTH_CLAZZ_ERR_MSG));
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
	public WSBuyerResponse validateUom(String uom){
		if (!CommonWebServiceValidation.validateUOM(uom)){
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.MAXLENGTH_UOM_ERR_MSG));
			return response;
		}
		return null;
	}
	
	/**
	 * Validates if price without tax is not null and if it does not exceed the maximum
	 * length.
	 * 
	 * @param pricewtax
	 * @return WSBuyerResponse if validation fails else null
	 */
	public WSBuyerResponse validatePriceWithOutTax(BigDecimal pricewotax){
		if (pricewotax == null) {
			return null;
		}
		//max length 9 digits
		if (!CommonWebServiceValidation.validatePriceWOTax(pricewotax)){
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_PRICEWOTAX_ERR_MSG));
			return response;
		}
		
		//decimal not allowed
		if (NumberUtil.isNotWholeNumber(pricewotax)) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PRICE_WITHOUT_TAX_DECIMAL));
			return response;
			
		}
		
		//negative not allowed
		if (NumberUtil.isNegative(pricewotax)) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_PRICE_WITHOUT_TAX_NEGATIVE));
			return response;
		}

		return null;
	}
	
	/**
	 * Validates the following: 
	 * - categoryName  is not null or empty 
	 * - Fruits - if Category is null or no categories are assigned for the user
	 * 
	 * Returns a Map containing: response - not null if validation fails 
	 * category -  not null if validation succeeds
	 * 
	 * @param categoryName
	 * @param userId
	 * @return Map
	 */

	public Map<String, Object> validateCategoryName(String categoryName, Integer userId){
		Map<String, Object> result = new HashMap<String, Object>();
		
		if (!CommonWebServiceValidation.validateCategoryName(categoryName)) {
			Category category = (categoryService.getCategoryById(Arrays.asList(1))).get(0);
			result.put("category", category);
			return result;
		} else if (!CommonWebServiceValidation.validateCategoryNameLength(categoryName)){
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.MAXLENGTH_CATEGORY_ERR_MSG));
			result.put("response", response);
			return result;
		}else{
			 List<Category> allCategory = categoryService.getAllCategory();
			 Category category = null;
			 for(Category cat : allCategory){
				 if(cat.getDescription().equalsIgnoreCase(categoryName)){
					 category = cat;
					 break;
				 }
			 }
			if (category!=null) {
				result.put("category", category);

			} else {
				WSBuyerResponse response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(
						messageI18NService.getPropertyMessage(
								WebServiceConstants.INVALID_CATEGORY_ERR_MSG));
				result.put("response", response);
			}
			

			return result;
		}
	}
	
	
		
	
	/**
	 * Validates if market  is not null and if it does not exceed the maximum
	 * length.
	 * 
 	 * @param market
	 * @return WSBuyerResponse if validation fails else null
	 */	
	public WSBuyerResponse validateMarket(String market){
		if (!CommonWebServiceValidation.validateMarket(market)){
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.MAXLENGTH_MARKET_ERR_MSG));
			return response;
		}	
		return null;
	}
	
	/**
	 * Validates if location  is not null and if it does not exceed the maximum
	 * length.
	 * 
 	 * @param location
	 * @return WSBuyerResponse if validation fails else null
	 */	
	public WSBuyerResponse validateLocation(String location){
		if (!CommonWebServiceValidation.validateLocation(location)){
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.MAXLENGTH_LOCATION_ERR_MSG));
			return response;
		}
		return null;
	}
	
	/**
	 * Validates if price2  is not null and if it does not exceed the maximum
	 * length.
	 * 
 	 * @param price2
	 * @return WSBuyerResponse if validation fails else null
	 */	
	public WSBuyerResponse validatePrice2(BigDecimal price2){
		
		if (price2 == null) {
			return null;
		}
		//max 9 digits
		if (!CommonWebServiceValidation.validatePrice2(price2)){
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.MAXLENGTH_PRICE2_ERR_MSG));
			return response;
		}
		
		//decimal not allowed
		if (NumberUtil.isNotWholeNumber(price2)) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.INVALID_PRICE2_DECIMAL));
			return response;
		}
		
		//negative not allowed
		if (NumberUtil.isNegative(price2)) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
							WebServiceConstants.INVALID_PRICE2_NEGATIVE));
			return response;
		}
		
		return null;
	}
	
	
	/**
	 * Validates if center is not null and if it does not exceed the maximum
	 * length.
	 * 
 	 * @param center
	 * @return WSBuyerResponse if validation fails else null
	 */	
	public WSBuyerResponse validateCenter(BigDecimal center){
		
		if (center == null){
			return null;
		}
		
		if (center.compareTo(new BigDecimal(999999999)) == 1) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_CENTER_ERR_MSG));
			return response;
		}

		if (NumberUtil.isNegative(center)) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_CENTER_NEGATIVE));
			return response;
		}

		if (NumberUtil.isNotWholeNumber(center)) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_CENTER_DECIMAL));
			return response;
		}
		
		return null;
	}
	
	/**
	 * Validates if sale is not null and if it does not exceed the maximum
	 * length.
	 * 
 	 * @param sale
	 * @return WSBuyerResponse if validation fails else null
	 */	
	public WSBuyerResponse validateSale(String sale){
		if (sale != null && sale.length() > 42) {
			WSBuyerResponse response = new WSBuyerResponse();
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
	 * @return WSBuyerResponse if validation fails else null
	 */	
	public WSBuyerResponse validateJan(String jan){
		if (jan != null && jan.length() > 42) {
			WSBuyerResponse response = new WSBuyerResponse();
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
	 * @return WSBuyerResponse if validation fails else null
	 */	
	public WSBuyerResponse validatePackFee(String packFee){
		if (packFee != null && packFee.length() > 42) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_PACKTYPE_ERR_MSG));
			return response;
		}
		
		return null;
	}
	
	
	/**
	 * Validates if Column is not null and if it does not exceed the maximum
	 * length.
	 * @param columnVal
	 * @param errorCode
	 * @return WSBuyerResponse if validation fails else null
	 */
	public WSBuyerResponse validateColumn(String columnVal, String errorCode){
		if (columnVal != null && columnVal.length() > 42) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(errorCode));
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
	public WSBuyerResponse validateAllColumns(WSBuyerSKUAdd wsBuyerSKUAdd) {
		WSBuyerResponse response = new WSBuyerResponse();
		
		//center in screen = column01 in DB
		response = validateCenter(wsBuyerSKUAdd.getCenter());
		if (response != null) {
			return response;
		}
		
		//delivery in screen = column02 in DB
		response = validateDelivery(wsBuyerSKUAdd.getDelivery());
		if (response != null) {
			return response;
		}
		
		//sale in screen = colum03 in DB
		response = validateSale(wsBuyerSKUAdd.getSale());
		if (response != null) {
			return response;
		}
		
		//JAN in screen = column04 in DB
		response = validateJan(wsBuyerSKUAdd.getJan());
		if (response != null) {
			return response;
		}
		
		//PackFee in screen = column05 in DB
		response = validatePackFee(wsBuyerSKUAdd.getPackFee());
		if (response != null) {
			return response;
		}
		
		//Col1 in screen = column06 in DB
		response = validateColumn(wsBuyerSKUAdd.getColumn01(), WebServiceConstants.MAXLENGTH_COLUMN01_ERR_MSG);
		if (response != null) {
			return response;
		}

		//Col2 in screen = column07 in DB
		response = validateColumn(wsBuyerSKUAdd.getColumn02(), WebServiceConstants.MAXLENGTH_COLUMN02_ERR_MSG);
		if (response != null) {
			return response;
		}

		//Col3 in screen = column08 in DB
		response = validateColumn(wsBuyerSKUAdd.getColumn03(), WebServiceConstants.MAXLENGTH_COLUMN03_ERR_MSG);
		if (response != null) {
			return response;
		}

		//Col4 in screen = column09 in DB
		response = validateColumn(wsBuyerSKUAdd.getColumn04(), WebServiceConstants.MAXLENGTH_COLUMN04_ERR_MSG);
		if (response != null) {
			return response;
		}

		//Col5 in screen = column10 in DB
		response = validateColumn(wsBuyerSKUAdd.getColumn05(), WebServiceConstants.MAXLENGTH_COLUMN05_ERR_MSG);
		if (response != null) {
			return response;
		}

		//Col6 in screen = column11 in DB
		response = validateColumn(wsBuyerSKUAdd.getColumn06(), WebServiceConstants.MAXLENGTH_COLUMN06_ERR_MSG);
		if (response != null) {
			return response;
		}

		//Col7 in screen = column12 in DB
		response = validateColumn(wsBuyerSKUAdd.getColumn07(), WebServiceConstants.MAXLENGTH_COLUMN07_ERR_MSG);
		if (response != null) {
			return response;
		}

		//Col8 in screen = column13 in DB
		response = validateColumn(wsBuyerSKUAdd.getColumn08(), WebServiceConstants.MAXLENGTH_COLUMN08_ERR_MSG);
		if (response != null) {
			return response;
		}

		//Col9 in screen = column14 in DB
		response = validateColumn(wsBuyerSKUAdd.getColumn09(), WebServiceConstants.MAXLENGTH_COLUMN09_ERR_MSG);
		if (response != null) {
			return response;
		}

		//Col10 in screen = column15 in DB
		response = validateColumn(wsBuyerSKUAdd.getColumn10(), WebServiceConstants.MAXLENGTH_COLUMN10_ERR_MSG);
		if (response != null) {
			return response;
		}

		//Col11 in screen = column16 in DB
		response = validateColumn(wsBuyerSKUAdd.getColumn11(), WebServiceConstants.MAXLENGTH_COLUMN11_ERR_MSG);
		if (response != null) {
			return response;
		}

		//Col12 in screen = column17 in DB
		response = validateColumn(wsBuyerSKUAdd.getColumn12(), WebServiceConstants.MAXLENGTH_COLUMN12_ERR_MSG);
		if (response != null) {
			return response;
		}

		//Col13 in screen = column18 in DB
		response = validateColumn(wsBuyerSKUAdd.getColumn13(), WebServiceConstants.MAXLENGTH_COLUMN13_ERR_MSG);
		if (response != null) {
			return response;
		}

		//Col14 in screen = column19 in DB
		response = validateColumn(wsBuyerSKUAdd.getColumn14(), WebServiceConstants.MAXLENGTH_COLUMN14_ERR_MSG);
		if (response != null) {
			return response;
		}

		//Col15 in screen = column20 in DB
		return validateColumn(wsBuyerSKUAdd.getColumn15(), WebServiceConstants.MAXLENGTH_COLUMN15_ERR_MSG);
		
		
	}
	
	/**
	 * Validates if delivery is not null and if it does not exceed the maximum
	 * length.
	 * @param delivery
	 * @return WSBuyerResponse if validation fails else null
	 */
	public WSBuyerResponse validateDelivery(String delivery) {
		if (delivery != null && delivery.length() > 42) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_DELIVERY_ERR_MSG));
			return response;
		}
		return null;
	}	
	
	
	
			
			
	/**
	 * Validates fields related to prices for SKU saving
	 * Fields are the ff: 
	 * price w/o tax 
	 * price with tax 
	 * price 2
	 * 
	 * @param wsBuyerSKUAdd
	 * @return WSBuyerResponse if validation fails else null
	 */
	private WSBuyerResponse validatePrices(WSBuyerSKUAdd wsBuyerSKUAdd) {
		WSBuyerResponse response = new WSBuyerResponse();

		// price w/o tax
		response = validatePriceWithOutTax(wsBuyerSKUAdd.getPriceWithoutTax());
		if (response != null) {
			return response;
		}
		

		// price 2
		response = validatePrice2(wsBuyerSKUAdd.getPrice2());
		if (response != null) {
			return response;
		}
		return null;
	}
	
	
	public WSBuyerResponse validateDealingPattern(List<Integer> buyerIds, List<Integer> sellerIds, String date){
	
		if (sellerIds == null || sellerIds.size() == 0){
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(
					messageI18NService.getPropertyMessage(
						WebServiceConstants.INVALID_BUYER_ERR_MSG));
			return response;
		}
		
		List<User> sellers = dealingPatternService
				.getAllSellerIdsByBuyerIds(buyerIds, date, date);
		List<Integer> sellerIdList = OrderSheetUtil.getUserIdList(sellers);
		
		
		
		for(Integer id:sellerIds){
			
			if(!sellerIdList.contains(id)){
				WSBuyerResponse response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(
						messageI18NService.getPropertyMessage(
								WebServiceConstants.INVALID_DP_ERR_MSG));
				return response;
			}
		}
		
		//sellerToBuyerDPMap = dealingPatternService.getSellerToBuyerDPMap(
		//		this.sellerIds, date, date);
		return null;
	}
	/**
	 * Validates if request obj entered is null.
	 * 
	 * Returns an instance of WSBuyerResponse if validation failed,
	 * else return null
	 * 
	 * @param inputDate
	 * @return WSBuyerResponse
	 */
	public WSBuyerResponse validateInsertRequest(
			WSBuyerAddOrderSheetRequest request) {

		if (request == null) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_REQUEST_ERR_MSG));
			return response;
		}
		return null;
	}
	
	
	public WSBuyerResponse validateSKUBuyerDetails(User user, WSBuyerAddOrderSheetSKU sku, Date orderDate)
	{
		String uomName = null;
		if (sku instanceof WSBuyerSKUUpdate) {
			uomName = ((WSBuyerSKUUpdate)sku).getSku().getOrderUnit().getOrderUnitName();
		} else {
			uomName = ((WSBuyerSKUAdd)sku).getUnitOrderName();
		}
		Integer sellerId= sku.getSellerId();
		List<WSBuyerAddOrderSheetDetails> details = sku.getDetails();	

		List<Integer> buyerIds = new ArrayList<Integer>();
			
		if (CollectionUtils.isEmpty(details)) {
			WSBuyerResponse response = new WSBuyerResponse();
			response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.REQUIRED_DETAILLIST_ERR_MSG));
			return response;
		}	
		
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)) {
			
			WSBuyerAddOrderSheetDetails detail = details.get(0);
			/* if no buyer ID is entered, default to the user id*/
			if (detail.getBuyerId() == null) {
				detail.setBuyerId(user.getUserId());
			}
			
			/* buyer entered details for other buyers */
			if (details.size() > 1
					|| (details.size() == 1 && !user.getUserId().equals(
							detail.getBuyerId()))) {
			
				WSBuyerResponse response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_USER_UPDATESKU_ERR_MSG));
				return response;
			}
			
			/* It is ok to enter zero */
			if (detail.getQty() == null) {
				WSBuyerResponse response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.REQUIRED_QTY_ERR_MSG));
				return response;
			} else {
				
				WSBuyerResponse response = validateQty(uomName, detail.getQty(), detail.getVisibility());
				if (response != null) {
					return response;
				}
			}
			
			if(!StringUtil.isNullOrEmpty(detail.getVisibility()) ){
				WSBuyerResponse response = new WSBuyerResponse();
				response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
				response.setResultMsg(messageI18NService
						.getPropertyMessage(WebServiceConstants.INVALID_UPDATE_VISIBILITY));
				return response;
			}
			buyerIds.add(user.getUserId());
		}else{
			
			
			boolean noFieldsToUpdate = true;
			for(WSBuyerAddOrderSheetDetails detail  : details){
				if (detail.getBuyerId() == null) {
					WSBuyerResponse response = new WSBuyerResponse();
					response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
					response.setResultMsg(messageI18NService
							.getPropertyMessage(WebServiceConstants.REQUIRED_BUYER_ERR_MSG));
					return response;
				}
				
				Integer comId = userInfoService.getCompanyIdByUserId(detail.getBuyerId().toString());
				if(user.getCompany().getCompanyId().intValue()!=comId.intValue()){
					WSBuyerResponse response = new WSBuyerResponse();
					response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
					response.setResultMsg(messageI18NService
							.getPropertyMessage(WebServiceConstants.INVALID_DP_ERR_MSG));
					return response;
				}

				if (detail.getQty() != null) {
					WSBuyerResponse response = validateQty(uomName, detail.getQty(), detail.getVisibility());
					if (response != null) {
						return response;
					}
				}
				
				if(noFieldsToUpdate && (detail.getQty()!=null || !StringUtil.isNullOrEmpty(detail.getVisibility()))){
					noFieldsToUpdate=false;
				}
				
				if (!StringUtil.isNullOrEmpty(detail.getVisibility())
						&& !CommonWebServiceValidation
								.isValidVisibilityFormat(detail.getVisibility())) {
						WSBuyerResponse response = new WSBuyerResponse();
						response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
						response.setResultMsg(messageI18NService
								.getPropertyMessage(WebServiceConstants.INVALID_VIS_FORMAT));
						return response;
				}
				
				buyerIds.add(detail.getBuyerId());

			}
			if (sku instanceof WSBuyerSKUUpdate) {

				if (noFieldsToUpdate
						&& sku.getB_sellingPrice()==null
						&& sku.getB_purchasePrice()==null
						&& StringUtil.isNullOrEmpty(sku.getB_skuComment())
						&& StringUtil.isNullOrEmpty(sku.getB_sellingUomName())) {
					WSBuyerResponse response = new WSBuyerResponse();
					response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
					response.setResultMsg(messageI18NService
							.getPropertyMessage(WebServiceConstants.REQUIRED_FIELDS_UPDATE_ERR_MSG));
					return response;
				}
			}
		}

		WSBuyerResponse response = validateDealingPattern(buyerIds, Arrays.asList(sellerId), new DateTime(orderDate).withTimeAtStartOfDay().toString("yyyyMMdd"));
		if (response!= null) {
			return response;
		}
		
		return null;
	}

	private WSBuyerResponse validateQty(String uomName, BigDecimal qty, String visibility) {

		if (qty == null || StringUtils.isBlank(uomName)) {
			return null;
		}
		
		WSBuyerResponse response = new WSBuyerResponse();
		//max 9 digits
		if (!CommonWebServiceValidation.validateQuantityLength(qty.toString())) {

			response.setResultCode(WebServiceConstants.MAXLENGTH_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.MAXLENGTH_QUANTITY_ERR_MSG));
			return response;

		}

		// check if negative
		if (NumberUtil.isNegative(qty)) {
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_QTY_NEGATIVE));
			return response;

		}

		/* check if decimal is allowed for the given UOM */
		if (!OrderUnitUtility.isValidOrderUnitQty(qty, uomName)) {
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_QTY_DECIMAL));
			return response;
		}
		
		/*
		 * check if the quantity is being set and at the same time visibility is
		 * being set to zero
		 */
		if ("0".equalsIgnoreCase(visibility)  && NumberUtil.isNotNegative(qty))  {
			response.setResultCode(WebServiceConstants.INVALID_ERR_CODE);
			response.setResultMsg(messageI18NService
					.getPropertyMessage(WebServiceConstants.INVALID_QTY_VISIBILITY));
			return response;
		}
		
		return null;
	}
	
	public List<EONError> processConcurrencyCheckForWS(
			WSBuyerAddOrderSheetRequest wsBuyerAddOrderSheetRequest) {
		
		List<EONError> errorResult = new ArrayList<EONError>();
		
		List<EONError> addErrorResult = processConcurencyCheckForAdd(wsBuyerAddOrderSheetRequest);
		if (CollectionUtils.isNotEmpty(addErrorResult)) {
			errorResult.addAll(addErrorResult);
		}

		List<EONError> updateErrorList = processConcurrencyCheckForUpdate(wsBuyerAddOrderSheetRequest);
		if (CollectionUtils.isNotEmpty(updateErrorList)) {
			errorResult.addAll(updateErrorList);
		}
		
		return errorResult;
	}
	
	@SuppressWarnings("unchecked")
	private List<EONError> processConcurencyCheckForAdd(
			WSBuyerAddOrderSheetRequest wsBuyerAddOrderSheetRequest) {
		
		User loginUser = wsBuyerAddOrderSheetRequest.getLoginUser();
		
		if (RolesUtil.isUserRoleBuyer(loginUser)) {
			return Collections.emptyList();
		}
		
		ArrayList<WSBuyerSKUAdd> addSKUList = wsBuyerAddOrderSheetRequest
				.getAddSkuList();				
		
		if (CollectionUtils.isEmpty(addSKUList)) {
			return Collections.emptyList();
		}
		
		Date orderDate = wsBuyerAddOrderSheetRequest.getOrderDate();
		
		/* extract the seller, buyer and ordermapkey*/
		Map<String, Set<?>> extractionResult = extractSellerBuyerCombination(orderDate, addSKUList);
		Set<Integer> sellerSet = (Set<Integer>) extractionResult.get("SELLER");
		Set<Integer> buyerSet = (Set<Integer>) extractionResult.get("BUYER");
		//Set<String> orderMapKey = (Set<String>) extractionResult.get("ORDERMAPKEY");
		List<Integer> sellerIds = new ArrayList<Integer>(sellerSet);
		List<Integer> buyerIds =  new ArrayList<Integer>(buyerSet);
		List<String> dateList = Arrays.asList(DateFormatter.convertToString(orderDate));
		
		/* retrieve the corresponding order records*/
		List<Order> allOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		
		/*
		 * Performs the ff: check for order concurrency 
		 */
		Set<EONError> errorMessages = checkConcurrencyForAdd(loginUser, orderDate,
				addSKUList, allOrdersMap);

		List<EONError> errorList = null;
		
		if (CollectionUtils.isNotEmpty(errorMessages)) {
			errorList = new ArrayList<EONError>(errorMessages);
		}
		return errorList;
	}
	
	private Set<EONError> checkConcurrencyForAdd(User loginUser,
			Date orderDate, ArrayList<WSBuyerSKUAdd> addSKUList,
			Map<String, Order> allOrdersMap) {
		
		if (addSKUList == null) {
			return null;
		}
		String dateString = DateFormatter.convertToString(orderDate);
	
		Set<EONError> errorMessages = new HashSet<EONError>();
	
		for (WSBuyerSKUAdd wsBuyerSKUAdd : addSKUList) {
			
			if (wsBuyerSKUAdd == null) {
				continue;
			}
			Integer sellerId = wsBuyerSKUAdd.getSellerId();
	
			ArrayList<WSBuyerAddOrderSheetDetails> details = wsBuyerSKUAdd
					.getDetails();
			
			for (WSBuyerAddOrderSheetDetails wsBuyerAddOrderSheetDetails : details) {
				
				if (wsBuyerAddOrderSheetDetails == null) {
					continue;
				}
				
				Integer buyerId = wsBuyerAddOrderSheetDetails.getBuyerId();
				String formatOrderMapKey = OrderSheetUtil.formatOrderMapKey(
						orderDate, buyerId, sellerId);
	
				Order order = allOrdersMap.get(formatOrderMapKey);
				wsBuyerAddOrderSheetDetails.setOrder(order);
	
				/*checks for concurrency in order object*/
				EONError errorResult = checkOrderForConcurrency(order, dateString,
						buyerId.toString(), sellerId.toString());
				
				if (errorResult != null){
					errorMessages.add(errorResult);
					continue;
				}
				
			}
		}
		return errorMessages;	}

	@SuppressWarnings("unchecked")
	private List<EONError> processConcurrencyCheckForUpdate(
			WSBuyerAddOrderSheetRequest wsBuyerAddOrderSheetRequest) {
		User loginUser = wsBuyerAddOrderSheetRequest.getLoginUser();
		
		Date orderDate = wsBuyerAddOrderSheetRequest.getOrderDate();
		
		ArrayList<WSBuyerSKUUpdate> updateSKUList = wsBuyerAddOrderSheetRequest.getUpdateSKUList();
		
		if (CollectionUtils.isEmpty(updateSKUList)) {
			return Collections.EMPTY_LIST;
		}
		
		/* extract the seller, buyer */
		Map<String, Set<?>> extractionResult = extractSellerBuyerCombination(orderDate, updateSKUList);
		Set<Integer> sellerSet = (Set<Integer>) extractionResult.get("SELLER");
		Set<Integer> buyerSet = (Set<Integer>) extractionResult.get("BUYER");
		//Set<String> orderMapKey = (Set<String>) extractionResult.get("ORDERMAPKEY");
		List<Integer> sellerIds = new ArrayList<Integer>(sellerSet);
		List<Integer> buyerIds =  new ArrayList<Integer>(buyerSet);
		List<String> dateList = Arrays.asList(DateFormatter.convertToString(orderDate));
		
		/* retrieve the corresponding order records*/
		List<Order> allOrders = orderSheetService.getAllOrders(buyerIds, dateList, sellerIds);
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
		
		/* retrieve the corresponding order item records*/
		List<OrderItem> allOrderItems = orderSheetService.getOrderItemsByOrderIdBulk(OrderSheetUtil.getOrderIdList(allOrders));
		Map<String, OrderItem> allOrderItemsMap = OrderSheetUtil.convertToOrderItemsMap(allOrderItems);

		/*
		 * Performs the ff: check for order concurrency and order item
		 * concurrency; assigns order and orderitem objects returns Set of Errors
		 */
		Set<EONError> errorMessages = checkConcurrencyForUpdate(loginUser, orderDate,
				updateSKUList, allOrdersMap, allOrderItemsMap);

		List<EONError> errorList = null;
		
		if (CollectionUtils.isNotEmpty(errorMessages)) {
			errorList = new ArrayList<EONError>(errorMessages);
		}
		return errorList;
	}

	


	/* assigns Order and OrderItem object to the xmltransient field in WSBuyerAddOrderSheetDetails */
	private Set<EONError> checkConcurrencyForUpdate(User loginUser, Date orderDate,
			ArrayList<WSBuyerSKUUpdate> updateSKUList,
			Map<String, Order> allOrdersMap,
			Map<String, OrderItem> allOrderItemsMap) {
	
		if (updateSKUList == null) {
			return null;
		}
		String dateString = DateFormatter.convertToString(orderDate);
	
		Set<EONError> errorMessages = new HashSet<EONError>();
	
		for (WSBuyerSKUUpdate wsBuyerSKUUpdate : updateSKUList) {
			if (wsBuyerSKUUpdate == null) {
				continue;
			}
			Integer sellerId = wsBuyerSKUUpdate.getSellerId();
			Integer skuId = wsBuyerSKUUpdate.getSkuId();
	
			ArrayList<WSBuyerAddOrderSheetDetails> details = wsBuyerSKUUpdate
					.getDetails();
			for (WSBuyerAddOrderSheetDetails wsBuyerAddOrderSheetDetails : details) {
				if (wsBuyerAddOrderSheetDetails == null) {
					continue;
				}
				Integer buyerId = wsBuyerAddOrderSheetDetails.getBuyerId();
				String formatOrderMapKey = OrderSheetUtil.formatOrderMapKey(
						orderDate, buyerId, sellerId);
	
				Order order = allOrdersMap.get(formatOrderMapKey);
				wsBuyerAddOrderSheetDetails.setOrder(order);
	
				/*checks for concurrency in order object*/
				EONError errorResult = checkOrderForConcurrency(order, dateString,
						buyerId.toString(), sellerId.toString());
				
				if (errorResult != null){
					errorMessages.add(errorResult);
					continue;
				}
	
				OrderItem orderItem = allOrderItemsMap.get(OrderSheetUtil
						.formatOrderItemKey(order.getOrderId(), skuId));
				
				/* trying to update an order item that is not found */
				
				if (orderItem == null) {
					EONError eonError = new EONError(
							WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_NOT_FOUND,
							new Object[] { dateString, buyerId.toString(), sellerId.toString() });
					
					errorMessages.add(eonError);
					continue;
				}
				
				wsBuyerAddOrderSheetDetails.setOrderItem(orderItem);
				String visibility = wsBuyerAddOrderSheetDetails.getVisibility();
				
				if(StringUtil.isNullOrEmpty(visibility)){
					wsBuyerAddOrderSheetDetails.setVisibility(orderItem.getBaosVisFlag());
				}
				
				if(wsBuyerAddOrderSheetDetails.getQty()==null){
					wsBuyerAddOrderSheetDetails.setQty(orderItem.getQuantity());
				}
				/*checks for concurrency in order item object*/
				errorResult = checkOrderItemForConcurrency(loginUser, orderItem, dateString,
						buyerId.toString(), sellerId.toString(), visibility);
				
				if (errorResult != null){
					errorMessages.add(errorResult);
					continue;
				}
				
				
				
			}
		}
		return errorMessages;
	}

	private EONError checkOrderForConcurrency(Order order, String dateString,
			String buyerString, String sellerString) {		
		
		/* order record is missing. it is possible that seller deleted the record */
		if (order == null) {
			EONError eonError = new EONError(
					WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_NOT_FOUND,
					new Object[] { dateString, buyerString, sellerString });
			
			return eonError;
		}
		
		/* if order is not yet published*/
		if (NumberUtil.isNullOrZero(order.getOrderPublishedBy())){
			EONError eonError = new EONError(
					WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_NOT_PUBLISHED,
					new Object[] { dateString, buyerString, sellerString });
			
			return eonError;
		}
		
		/* if order is already locked*/
		if (!NumberUtil.isNullOrZero(order.getOrderLockedBy())){
			EONError eonError = new EONError(
					WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_ALREADY_LOCKED,
					new Object[] { dateString, buyerString, sellerString });
			
			return eonError;
		}
		
		/* if order is already finalized */
		if (!NumberUtil.isNullOrZero(order.getOrderFinalizedBy())){
			EONError eonError = new EONError(
					WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_ALREADY_FINALIZED,
					new Object[] { dateString, buyerString, sellerString });
			
			return eonError;
		}
		
		/*
		 * no need to check other conditions (e.g. allocation published,etc). It is assumed that order needs
		 * to be finalized before proceeding with other statuses
		 */
		return null;
	}

	private EONError checkOrderItemForConcurrency(User loginUser, OrderItem orderItem,
			String dateString, String buyerIdStr, String sellerIdStr, String visibility) {
		
		
		if (orderItem == null) {
			EONError eonError = new EONError(
					WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_NOT_FOUND,
					new Object[] { dateString, buyerIdStr, sellerIdStr });
			
			return eonError;
		}
		
		/* seller admin might have removed visibility for the corresponding seller */
		if ("0".equalsIgnoreCase(orderItem.getSosVisFlag())) {
			EONError eonError = new EONError(
					WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_ITEM_NOT_VISIBLE,
					new Object[] { dateString, buyerIdStr, sellerIdStr, orderItem.getSKUId().toString() });

			return eonError;
		}

		/* Buyer admin might have removed visibility for the corresponding Buyer */
		if ("0".equalsIgnoreCase(orderItem.getBaosVisFlag())) {
			/*
			 * Error cases: 1. login user is a buyer; 2. login user is a buyer
			 * admin and visibility value sent is not being updated to 1
			 */
			if (RolesUtil.isUserRoleBuyer(loginUser)
					|| (StringUtils.isBlank(visibility) && !"1"
							.equalsIgnoreCase(visibility))) {
				EONError eonError = new EONError(
						WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_ITEM_NOT_VISIBLE,
						new Object[] { dateString, buyerIdStr, sellerIdStr,
								orderItem.getSKUId().toString() });

				return eonError;
			}
		}

		/* user sent a request to turn off visibility but there is already a quantity value set*/
		if ("0".equalsIgnoreCase(visibility)) {
			/* quantity already set in DB  */
			if (orderItem.getQuantity() != null
					&& NumberUtil.isNotNegative(orderItem.getQuantity())) {
				EONError eonError = new EONError(
						WebServiceConstants.WS_MESSAGE_ERROR_CONCURRENCY_ADD_ORDER_ITEM_VISIBILITY_NOT_UPDATABLE,
						new Object[] { dateString, buyerIdStr, sellerIdStr,
								orderItem.getSKUId().toString() });

				return eonError;
			}
		}
		
		return null;
	}

	/*
	 * This will extract the seller id, buyer id and Order Key Map
	 */

	private Map<String, Set<?>> extractSellerBuyerCombination(Date deliveryDate,
			ArrayList<? extends WSBuyerAddOrderSheetSKU> skuList) {

		/* Assumes that updateSKUList is not null*/
		Map<String, Set<?>> sellerBuyerMap = new HashMap<String, Set<?>>();
		
		Set<Integer> sellerSet = new HashSet<Integer>();
		Set<Integer> buyerSet = new HashSet<Integer>();
		Set<String> orderMapKeySet = new HashSet<String>();
		
		for (WSBuyerAddOrderSheetSKU wsBuyerAddOrderSheetSKU : skuList) {
			if (wsBuyerAddOrderSheetSKU == null) {
				continue;
			}
			Integer sellerId = wsBuyerAddOrderSheetSKU.getSellerId();

			ArrayList<WSBuyerAddOrderSheetDetails> details = wsBuyerAddOrderSheetSKU.getDetails();
			if (CollectionUtils.isNotEmpty(details)) {
				for (WSBuyerAddOrderSheetDetails wsBuyerAddOrderSheetDetails : details) {
					if (wsBuyerAddOrderSheetDetails == null) {
						continue;
					}
					Integer buyerId = wsBuyerAddOrderSheetDetails.getBuyerId();
					buyerSet.add(buyerId);

					String formatOrderMapKey = OrderSheetUtil
							.formatOrderMapKey(deliveryDate, buyerId, sellerId);
					orderMapKeySet.add(formatOrderMapKey);

				}
			}
			sellerSet.add(sellerId);
		}
		sellerBuyerMap.put("SELLER", sellerSet);
		sellerBuyerMap.put("BUYER", buyerSet);
		sellerBuyerMap.put("ORDERMAPKEY", orderMapKeySet);
		
		
		return sellerBuyerMap;
	}
	
}
