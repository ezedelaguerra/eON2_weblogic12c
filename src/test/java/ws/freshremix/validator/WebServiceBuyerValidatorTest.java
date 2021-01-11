package ws.freshremix.validator;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.constants.WebServiceConstants;
import com.freshremix.model.Category;
import com.freshremix.model.Company;
import com.freshremix.model.Order;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.Role;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.User;
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
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.service.SKUService;
import com.freshremix.service.UsersInformationService;

public class WebServiceBuyerValidatorTest {

	private static final String LONGSTRING = "Loremipsumdolorsitametconsectetueadipiscingelitseddiamnonummy";

	private WebServiceBuyerValidator webServiceBuyerValidator = new WebServiceBuyerValidator();
	private SKUService skuServiceMock= Mockito.mock(SKUService.class);

	private LoginService loginServiceMock = Mockito.mock(LoginService.class);
	private CategoryService categoryServiceMock = Mockito
			.mock(CategoryService.class);;
	private OrderUnitService orderUnitServiceMock = Mockito.mock(OrderUnitService.class);
	private UsersInformationService userInfoServiceMock = Mockito
			.mock(UsersInformationService.class);;
	private DealingPatternService dealingPatternServiceMock = Mockito
			.mock(DealingPatternService.class);
	private SKUGroupService skuGroupServiceMock= Mockito
			.mock(SKUGroupService.class);

	private MessageI18NService messageI18NServiceMock = Mockito
			.mock(MessageI18NService.class);

	@BeforeClass
	public void beforeClass() {
		webServiceBuyerValidator.setLoginService(loginServiceMock);
		webServiceBuyerValidator.setMessageI18NService(messageI18NServiceMock);
		webServiceBuyerValidator.setUserInfoService(userInfoServiceMock);
		webServiceBuyerValidator.setCategoryService(categoryServiceMock);
		webServiceBuyerValidator
				.setDealingPatternService(dealingPatternServiceMock);
		webServiceBuyerValidator.setSkuService(skuServiceMock);
		webServiceBuyerValidator.setOrderUnitService(orderUnitServiceMock);
		webServiceBuyerValidator.setSkuGroupService(skuGroupServiceMock);
	}

	@AfterMethod
	public void afterMethod() {
		Mockito.reset(loginServiceMock, messageI18NServiceMock,
				userInfoServiceMock, categoryServiceMock,
				dealingPatternServiceMock, skuServiceMock, orderUnitServiceMock);
	}

	@DataProvider(name = "validDateRangeDataProvider")
	public Object[][] validDateRangeDataProvider() {
		return new Object[][] {
				/* same day */
				{ new DateMidnight(2013, 1, 1).toDateTime(),
						new DateMidnight(2013, 1, 1).toDateTime() },
				/* middle of 7 days */
				{ new DateMidnight(2013, 1, 1).toDateTime(),
						new DateMidnight(2013, 1, 3).toDateTime() },
				/* 7th day */
				{ new DateMidnight(2013, 1, 1).toDateTime(),
						new DateMidnight(2013, 1, 7).toDateTime() } };
	}

	@Test(dataProvider = "validDateRangeDataProvider")
	public void testValidateDateRange(DateTime startDate, DateTime endDate) {
		WSBuyerResponse result = webServiceBuyerValidator.validateDateRange(
				startDate, endDate);
		Assert.assertNull(result);
	}

	@DataProvider(name = "invalidDateRangeDataProvider")
	public Object[][] invalidDateRangeDataProvider() {
		return new Object[][] {
				/* less than start date */
				{ new DateMidnight(2013, 1, 1).toDateTime(),
						new DateMidnight(2012, 12, 31).toDateTime() },
				/* greater than 7 days */
				{ new DateMidnight(2013, 1, 1).toDateTime(),
						new DateMidnight(2013, 1, 8).toDateTime() } };
	}

	@Test(dataProvider = "invalidDateRangeDataProvider")
	public void testValidateDateRangeInvalidData(DateTime startDate,
			DateTime endDate) {
		WSBuyerResponse result = webServiceBuyerValidator.validateDateRange(
				startDate, endDate);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getResultCode(),
				WebServiceConstants.INVALID_ERR_CODE);
		Mockito.verify(messageI18NServiceMock).getPropertyMessage(
				Mockito.anyString());
	}

	@DataProvider(name = "validValidateDatesDataProvider")
	public Object[][] validValidateDatesDataProvider() {
		return new Object[][] {
				/* same day */
				{ new DateMidnight(2013, 1, 1).toDate(),
						new DateMidnight(2013, 1, 1).toDate() },
				/* middle of 7 days */
				{ new DateMidnight(2013, 1, 1).toDate(),
						new DateMidnight(2013, 1, 3).toDate() },
				/* 7th day */
				{ new DateMidnight(2013, 1, 1).toDate(),
						new DateMidnight(2013, 1, 7).toDate() }

		};
	}

	@Test(dataProvider = "validValidateDatesDataProvider")
	public void testValidateDatesValidData(Date startDate, Date endDate) {
		WSBuyerResponse result = webServiceBuyerValidator.validateDates(
				startDate, endDate);
		Assert.assertNull(result);
	}

	@DataProvider(name = "invalidValidateDatesDataProvider")
	public Object[][] invalidValidateDatesDataProvider() {
		return new Object[][] {
				/* null start date */
				{ null, new Date(), WebServiceConstants.REQUIRED_ERR_CODE },
				/* null end date */
				{ new Date(), null, WebServiceConstants.REQUIRED_ERR_CODE },
				/* less than start date */
				{ new DateMidnight(2013, 1, 1).toDate(),
						new DateMidnight(2012, 12, 31).toDate(),
						WebServiceConstants.INVALID_ERR_CODE },
				/* greater than 7 days */
				{ new DateMidnight(2013, 1, 1).toDate(),
						new DateMidnight(2013, 1, 8).toDate(),
						WebServiceConstants.INVALID_ERR_CODE }

		};
	}

	@Test(dataProvider = "invalidValidateDatesDataProvider")
	public void testValidateDatesInvalidData(Date startDate, Date endDate,
			String expectedErrorCode) {
		WSBuyerResponse result = webServiceBuyerValidator.validateDates(
				startDate, endDate);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getResultCode(), expectedErrorCode);
	}

	@DataProvider(name = "validBuyerDP")
	public Object[][] validBuyerDP() {
		return new Object[][] { { createBuyerUser() },
				{ createBuyerAdminUser() } };
	}

	@Test(dataProvider = "validBuyerDP")
	public void testValidateBuyerUser(User user) {
		Mockito.when(
				loginServiceMock.getUserByUsernameAndPassword(
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				user);
		Map<String, Object> resultMap = webServiceBuyerValidator
				.validateBuyerUser(user.getUserName(), user.getPassword());
		Assert.assertNull(resultMap.get("response"));
		Assert.assertEquals(resultMap.get("user"), user);
		Mockito.verify(loginServiceMock).getUserByUsernameAndPassword(
				Mockito.anyString(), Mockito.anyString());
	}

	@DataProvider(name = "invalidBuyerDP")
	public Object[][] invalidBuyerDP() {
		return new Object[][] { { createUser(null, "password") },
				{ createUser("", "password") },
				{ createUser("username", null) },
				{ createUser("username", "") }, { createSellerUser() },
				{ createSellerUser() }, { createSellerAdminUser() } };
	}

	@Test(dataProvider = "invalidBuyerDP")
	public void testValidateBuyerUserInvalidData(User user) {
		Mockito.when(
				loginServiceMock.getUserByUsernameAndPassword(
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				user);
		Map<String, Object> resultMap = webServiceBuyerValidator
				.validateBuyerUser(user.getUserName(), user.getPassword());
		WSBuyerResponse response = (WSBuyerResponse) resultMap.get("response");
		Assert.assertEquals(response.getResultCode(),
				WebServiceConstants.USER_ERR_CODE);
		Assert.assertNull(resultMap.get("user"));
	}

	@DataProvider(name = "validBuyerListUserDP")
	public Object[][] validBuyerListUserDP() {
		return new Object[][] { { createBuyerAdminUser() },
				{ createBuyerUser() } };
	}

	@Test(dataProvider = "validBuyerListUserDP")
	public void testValidateBuyerIdList(User user) {
		Mockito.when(
				userInfoServiceMock.getNonCompanyMembersFromList(
						Mockito.anyInt(), Mockito.anyList())).thenReturn(null);
		List<Integer> list = Arrays.asList(1);

		WSBuyerResponse result = webServiceBuyerValidator.validateBuyerIdList(
				list, user);
		Assert.assertNull(result);
	}

	@DataProvider(name = "invalidBuyerListUserDP")
	public Object[][] invalidBuyerListUserDP() {
		return new Object[][] { { createBuyerUser(), Arrays.asList(2) },
				{ createBuyerUser(), Arrays.asList(1, 2) } };
	}

	@Test(dataProvider = "invalidBuyerListUserDP")
	public void testValidateBuyerIdListInvalidData(User user, List<Integer> list) {
		WSBuyerResponse result = webServiceBuyerValidator.validateBuyerIdList(
				list, user);
		Assert.assertEquals(result.getResultCode(),
				WebServiceConstants.INVALID_ERR_CODE);
	}

	@Test
	public void testValidateBuyerIdListInvalidList() {
		Mockito.when(
				userInfoServiceMock.getNonCompanyMembersFromList(
						Mockito.anyInt(), Mockito.anyList())).thenReturn(
				Arrays.asList(21));

		WSBuyerResponse result = webServiceBuyerValidator.validateBuyerIdList(
				Arrays.asList(15, 21), createBuyerAdminUser());
		Assert.assertEquals(result.getResultCode(),
				WebServiceConstants.INVALID_ERR_CODE);
	}

	@Test
	public void testValidateBuyerIdListValidList() {
		Mockito.when(
				userInfoServiceMock.getNonCompanyMembersFromList(
						Mockito.anyInt(), Mockito.anyList())).thenReturn(null);

		WSBuyerResponse result = webServiceBuyerValidator.validateBuyerIdList(
				Arrays.asList(15, 14), createBuyerAdminUser());
		Assert.assertNull(result);
	}

	@DataProvider(name = "invalidOrderListDP")
	public Object[][] invalidOrderListDP() {
		return new Object[][] { { null }, { new ArrayList<Order>() } };
	}

	@Test
	public void testValidateBuyerPublishedOrders() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateBuyerPublishedOrders(Arrays.asList(new Order()));
		Assert.assertNull(result);
	}

	@Test(dataProvider = "invalidOrderListDP")
	public void testValidateBuyerPublishedOrdersInvalidData(List<Order> list) {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateBuyerPublishedOrders(list);
		Assert.assertEquals(result.getResultCode(),
				WebServiceConstants.ZERO_RESULTS_CODE);
	}

	@Test
	public void testValidateOrderMap() {
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("entry", "value");
		WSBuyerResponse result = webServiceBuyerValidator
				.validateOrderMap(orderMap);
		Assert.assertNull(result);
	}

	@DataProvider(name = "invalidOrderMapDP")
	public Object[][] invalidOrderMap() {
		return new Object[][] { { null }, { new HashMap<String, Object>() } };
	}

	@Test(dataProvider = "invalidOrderMapDP")
	public void testValidateOrderMapInvalidData(Map<String, Object> orderMap) {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateOrderMap(orderMap);
		Assert.assertEquals(result.getResultCode(),
				WebServiceConstants.ZERO_RESULTS_CODE);
	}

	@DataProvider(name = "invalidListOfOrderMapDP")
	public Object[][] invalidListOfOrderMapDP() {
		return new Object[][] { { null },
				{ new ArrayList<Map<String, Object>>() } };
	}

	@Test(dataProvider = "invalidListOfOrderMapDP")
	public void testValidateListOfOrderMapInvalid(
			List<Map<String, Object>> dataList) {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateOrderMap(dataList);
		Assert.assertEquals(result.getResultCode(),
				WebServiceConstants.ZERO_RESULTS_CODE);
	}

	@Test
	public void testValidateListOfOrderMap() {
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("entry", "value");

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		dataList.add(orderMap);

		WSBuyerResponse result = webServiceBuyerValidator
				.validateOrderMap(dataList);
		Assert.assertNull(result);
	}

	@Test
	public void testValidateRequest() {
		// successful validation
		WSBuyerLoadOrderRequest request = new WSBuyerLoadOrderRequest();
		WSBuyerResponse result = webServiceBuyerValidator
				.validateRequest(request);
		Assert.assertNull(result);

		// failed validation
		result = webServiceBuyerValidator.validateRequest(null);
		Assert.assertEquals(result.getResultCode(),
				WebServiceConstants.REQUIRED_ERR_CODE);

	}

	@Test
	public void validateSellerIdListHappyFlow() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateSellerIdList(Arrays.asList(1, 2, 3));
		Assert.assertNull(result);
	}

	@Test
	public void validateSellerIdListEmptyList() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateSellerIdList(null);
		Assert.assertNotNull(result);
	}

	@Test
	public void validateAddOrderSheetListsHappyFlow() {

		WSBuyerResponse result = webServiceBuyerValidator
				.validateAddOrderSheetLists(createBuyerAdminUser(),
						createWSBuyerSKUUpdateList(), createWSBuyerSKUAddList());
		Assert.assertNull(result);
	}

	@DataProvider(name = "addOrderSheetListBuyerList")
	public Object[][] addOrderSheetListBuyerList() {
		return new Object[][] {
				{ createBuyerUser(), createWSBuyerSKUUpdateList(),
						createWSBuyerSKUAddList() },
				{ createBuyerUser(), null, createWSBuyerSKUAddList() },

				{ createBuyerAdminUser(), null, null }

		};
	}

	@Test(dataProvider = "addOrderSheetListBuyerList")
	public void validateAddOrderSheetListsBuyerWithError(User user,
			List<WSBuyerSKUUpdate> updateList, List<WSBuyerSKUAdd> addList) {

		WSBuyerResponse result = webServiceBuyerValidator
				.validateAddOrderSheetLists(user, updateList, addList);
		Assert.assertNotNull(result);
	}

	@Test
	public void validateAddOrderSheetListsBuyerMaxLengthError() {
		List<WSBuyerSKUUpdate> WSBuyerSKUUpdateList = createWSBuyerSKUUpdateList();
		List<WSBuyerSKUAdd> WSBuyerSKUAddList = createWSBuyerSKUAddList();
		for (int i = 0; i < 502; i++) {
			WSBuyerSKUUpdateList.add(new WSBuyerSKUUpdate());
			WSBuyerSKUAddList.add(new WSBuyerSKUAdd());

		}
		WSBuyerResponse result = webServiceBuyerValidator
				.validateAddOrderSheetLists(createBuyerAdminUser(),
						WSBuyerSKUUpdateList, WSBuyerSKUAddList);
		Assert.assertNotNull(result);
		result = webServiceBuyerValidator.validateAddOrderSheetLists(
				createBuyerAdminUser(), createWSBuyerSKUUpdateList(),
				WSBuyerSKUAddList);
		Assert.assertNotNull(result);
	}

//	@Test
//	public void validateQtyHapplyFlow() {
//		WSBuyerResponse result = webServiceBuyerValidator
//				.validateQty(new BigDecimal(1));
//		Assert.assertNull(result);
//
//	}
//
//	@Test
//	public void validateQtyEmpty() {
//		WSBuyerResponse result = webServiceBuyerValidator.validateQty(null);
//		Assert.assertNotNull(result);
//
//	}

//	@Test
//	public void validateQtyLengthHappyFlow() {
//
//		WSBuyerResponse result = webServiceBuyerValidator
//				.validateQtyLength(new BigDecimal(1));
//		Assert.assertNull(result);
//	}
//
//	@Test
//	public void validateQtyLengthWithError() {
//
//		WSBuyerResponse result = webServiceBuyerValidator
//				.validateQtyLength(new BigDecimal("9999999999"));
//		Assert.assertNotNull(result);
//	}

	@Test
	public void validateSkuIdHappyFlow() {

		WSBuyerResponse result = webServiceBuyerValidator.validateSkuId(1);
		Assert.assertNull(result);
	}

	@Test(dataProvider = "invalidInteger")
	public void validateSkuIdWithErrors(Integer i) {

		WSBuyerResponse result = webServiceBuyerValidator.validateSkuId(i);
		Assert.assertNotNull(result);
	}

	@DataProvider(name = "validBuyerList")
	private Object[][] createvalidBuyerList() {
		return new Object[][] { { createBuyerAdminUser() },
				{ createBuyerUser() }

		};
	}

	@Test(dataProvider = "validBuyerList")
	public void validateBuyerIdHappyFlow(User user) {
		when(userInfoServiceMock.getUserById(Mockito.anyInt()))
				.thenReturn(user);
		Map<String, Object> result = webServiceBuyerValidator
				.validateBuyerId(1);
		Assert.assertNull(result.get("response"));
		Assert.assertNotNull(result.get("buyer"));
	}

	@DataProvider(name = "invalidValidateBuyerId")
	private Object[][] createInvalidValidateBuyerIdList() {
		return new Object[][] { { null, null }, { 1, null },
				{ 1, createSellerUser() }, { 1, createSellerAdminUser() }

		};
	}

	@Test(dataProvider = "invalidValidateBuyerId")
	public void validateBuyerIdwithErrors(Integer buyerId, User buyerResult) {
		when(userInfoServiceMock.getUserById(buyerId)).thenReturn(buyerResult);
		Map<String, Object> result = webServiceBuyerValidator
				.validateBuyerId(buyerId);
		Assert.assertNotNull(result.get("response"));
		Assert.assertNull(result.get("buyer"));
	}

	@DataProvider(name = "validSellerList")
	private Object[][] createvalidSellerList() {
		return new Object[][] { { createSellerAdminUser() },
				{ createSellerUser() }

		};
	}

	@Test(dataProvider = "validSellerList")
	public void validateSellerIdHappyFlow(User user) {
		when(userInfoServiceMock.getUserById(Mockito.anyInt()))
				.thenReturn(user);
		Map<String, Object> result = webServiceBuyerValidator
				.validateSellerId(1);
		Assert.assertNull(result.get("response"));
		Assert.assertNotNull(result.get("seller"));
	}

	@DataProvider(name = "invalidValidateSellerId")
	private Object[][] createInvalidValidateSellerIdList() {
		return new Object[][] { { null, null }, { 1, null },
				{ 1, createBuyerUser() }, { 1, createBuyerAdminUser() }

		};
	}

	@Test(dataProvider = "invalidValidateSellerId")
	public void validateSellerIdwithErrors(Integer sellerId, User sellerResult) {
		when(userInfoServiceMock.getUserById(sellerId))
				.thenReturn(sellerResult);
		Map<String, Object> result = webServiceBuyerValidator
				.validateSellerId(sellerId);
		Assert.assertNotNull(result.get("response"));
		Assert.assertNull(result.get("seller"));
	}

	@Test
	public void validateOrderDateHappyFlow() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateOrderDate(new Date());
		Assert.assertNull(result);
	}

	@Test
	public void validateOrderDateNullDate() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateOrderDate(null);
		Assert.assertNotNull(result);
	}

	@Test
	public void validateSkuGroupNameHappyFlow() {
		WSBuyerResponse result = webServiceBuyerValidator.validateSkuGroupName("asdfasdfas");
		Assert.assertNull(result);
	}

	@DataProvider(name = "invalidInteger")
	private Object[][] createInvalidInteger() {
		return new Object[][] { { null }, { new Integer("1000000009") }

		};
	}

	@Test(dataProvider = "invalidInteger")
	public void validateSkuGroupIdwithErrors(Integer skuGroupId) {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateSkuGroupName(LONGSTRING);
		Assert.assertNotNull(result);
	}

	@Test
	public void validateSKUPackageTypedHappyFlow() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateSKUPackageType("sku");
		Assert.assertNull(result);
	}

	@DataProvider(name = "invalidString")
	private Object[][] createinvalidStringList() {
		return new Object[][] { { LONGSTRING } };
	}

	@DataProvider(name = "validString")
	private Object[][] createvalidStringList() {
		return new Object[][] { { "a" }, { "sdfasdfsd" }

		};
	}

	@Test(dataProvider = "invalidString")
	public void validateSKUPackageTypewithErrors(String skuPackageType) {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateSKUPackageType(skuPackageType);
		Assert.assertNotNull(result);
	}

	@DataProvider(name = "validBigDecimalValues")
	private Object[][] createvalidBigDecimalValuesList() {
		return new Object[][] {//
		{ new BigDecimal(1) },//
				{ new BigDecimal("1") },//
				{ new BigDecimal("1.0") } };
	}

	@Test(dataProvider = "validString")
	public void validateGradeHappyFlow(String grade) {
		WSBuyerResponse result = webServiceBuyerValidator.validateGrade(grade);
		Assert.assertNull(result);
	}

	@Test
	public void validateGradeWithErrors() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateGrade(LONGSTRING);
		Assert.assertNotNull(result);
	}

	@Test(dataProvider = "validString")
	public void validateClazzHappyFlow(String clazz) {
		WSBuyerResponse result = webServiceBuyerValidator.validateClazz(clazz);
		Assert.assertNull(result);
	}

	@Test
	public void validateClazzWithErrors() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateClazz(LONGSTRING);
		Assert.assertNotNull(result);
	}

	@Test(dataProvider = "validString")
	public void validateUomHappyFlow(String uom) {
		WSBuyerResponse result = webServiceBuyerValidator.validateUom(uom);
		Assert.assertNull(result);
	}

	@Test
	public void validateUomWithErrors() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateUom(LONGSTRING);
		Assert.assertNotNull(result);
	}

	@Test(dataProvider = "validBigDecimalValues")
	public void validatePriceWithOutTaxHappyFlow(BigDecimal bd) {
		WSBuyerResponse result = webServiceBuyerValidator
				.validatePriceWithOutTax(bd);
		Assert.assertNull(result);
	}

	@Test
	public void validatePriceWithOutTaxWithErrors() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validatePriceWithOutTax(new BigDecimal("1000000009"));
		Assert.assertNotNull(result);
	}


	@DataProvider(name="emptyCategoryName")
	public Object[][] emptyCategoryName(){
		return new Object[][] {
				{""},
				{"  "},
				{null}
		};
	}

	@Test (dataProvider="emptyCategoryName")
	public void validateCategoryNameBlank(String categoryName) {
		List<Integer> defaultCategoryListId = Arrays.asList(1);
		Category category = new Category();
		when(categoryServiceMock.getCategoryById(defaultCategoryListId)).thenReturn(
				Arrays.asList(category));

		Map<String, Object> result = webServiceBuyerValidator
				.validateCategoryName(categoryName, 1);
		
		Assert.assertNull(result.get("response"));
		Assert.assertNotNull(result.get("category"));
		Assert.assertEquals(result.get("category"), category);
		verify(categoryServiceMock).getCategoryById(defaultCategoryListId);
		verifyNoMoreInteractions(categoryServiceMock);
	}

	
	@Test
	public void validateCategoryNameLongName() {

		Map<String, Object> result = webServiceBuyerValidator
				.validateCategoryName(LONGSTRING, 1);
		Assert.assertNotNull(result.get("response"));
		Assert.assertNull(result.get("category"));
		verifyZeroInteractions(categoryServiceMock);

	}

	@Test
	public void validateCategoryNameInvalidName() {
		String categoryName = "sampleCategoryName";
		Integer userId = 1;
		Category category = new Category();
		category.setDescription("Fruits");
		List<Integer> categoryList = Arrays.asList(1);

		


		when(categoryServiceMock.getAllCategory())
		.thenReturn(Arrays.asList(category, createCategory(2,  "Vegetables")));

		Map<String, Object> result = webServiceBuyerValidator
				.validateCategoryName(categoryName, 1);
		
		Assert.assertNotNull(result.get("response"));
		Assert.assertNull(result.get("category"));
		verify(categoryServiceMock).getAllCategory();
		verifyNoMoreInteractions(categoryServiceMock);
	}
	
	@Test
	public void validateCategoryNameValidName() {
		String categoryName = "Fruits";
		Integer userId = 1;
		Category category =createCategory(1, "Fruits");

		when(categoryServiceMock.getAllCategory())
		.thenReturn(Arrays.asList(category, createCategory(2,  "Vegetables")));

		Map<String, Object> result = webServiceBuyerValidator
				.validateCategoryName(categoryName, 1);
		
		Assert.assertNull(result.get("response"));
		Assert.assertNotNull(result.get("category"));
		Assert.assertEquals(result.get("category"), category);
		
		verify(categoryServiceMock).getAllCategory();
		verifyNoMoreInteractions(categoryServiceMock);
	}	

//	@Test
//	public void validateCategoryNameUseDefault() {
//		String categoryName = "sampleCategoryName";
//		Integer userId = 1;
//		Category category = new Category();
//		category.setDescription(categoryName);
//		List<Integer> categoryList = Arrays.asList(1);
//		
//		when(categoryServiceMock.getCategoryByName(userId, categoryName))
//		.thenReturn(null);
//		when(categoryServiceMock.getCategoryById(categoryList))
//		.thenReturn(Arrays.asList(category));
//
//		Map<String, Object> result = webServiceBuyerValidator
//				.validateCategoryName(categoryName, 1);
//		
//		Assert.assertNull(result.get("response"));
//		Assert.assertNotNull(result.get("category"));
//		Assert.assertEquals(result.get("category"), category);
//		
//		verify(categoryServiceMock).getCategoryByName(userId, categoryName);
//		verify(categoryServiceMock).getCategoryById(categoryList);
//		verifyNoMoreInteractions(categoryServiceMock);
//	}	
	
	
	@Test(dataProvider = "validString")
	public void validateMarketHappyFlow(String market) {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateMarket(market);
		Assert.assertNull(result);
	}

	@Test
	public void validateMarketWithErrors() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateMarket(LONGSTRING);
		Assert.assertNotNull(result);
	}

	@Test(dataProvider = "validString")
	public void validateLocationHappyFlow(String location) {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateLocation(location);
		Assert.assertNull(result);
	}

	@Test
	public void validateLocationWithErrors() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateLocation(LONGSTRING);
		Assert.assertNotNull(result);
	}

	@Test(dataProvider = "validBigDecimalValues")
	public void validatePrice2HappyFlow(BigDecimal bd) {
		WSBuyerResponse result = webServiceBuyerValidator.validatePrice2(bd);
		Assert.assertNull(result);
	}

	@Test
	public void validatePrice2WithErrors() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validatePrice2(new BigDecimal("1000000009"));
		Assert.assertNotNull(result);
	}

//	@Test(dataProvider = "validBigDecimalValues")
//	public void validateSkuMaxLimitHappyFlow(BigDecimal bd) {
//		WSBuyerResponse result = webServiceBuyerValidator
//				.validateSkuMaxLimit(bd);
//		Assert.assertNull(result);
//	}
//
//	@Test
//	public void validateSkuMaxLimitWithErrors() {
//		WSBuyerResponse result = webServiceBuyerValidator
//				.validateSkuMaxLimit(new BigDecimal("1000000009"));
//		Assert.assertNotNull(result);
//	}

//	@Test(dataProvider = "validString")
//	public void validateCommentHappyFlow(String comment) {
//		WSBuyerResponse result = webServiceBuyerValidator
//				.validateComment(comment);
//		Assert.assertNull(result);
//	}
//
//	@Test
//	public void validateCommentWithErrors() {
//		WSBuyerResponse result = webServiceBuyerValidator
//				.validateComment(LONGSTRING);
//		Assert.assertNotNull(result);
//	}

	@Test(dataProvider = "validBigDecimalValues")
	public void validateCenterHappyFlow(BigDecimal center) {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateCenter(center);
		Assert.assertNull(result);
	}

	@Test
	public void validateCenterWithErrors() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateCenter(new BigDecimal("1000000009"));
		Assert.assertNotNull(result);
	}

	@Test(dataProvider = "validString")
	public void validateSaleHappyFlow(String sale) {
		WSBuyerResponse result = webServiceBuyerValidator.validateSale(sale);
		Assert.assertNull(result);
	}

	@Test
	public void validateSaleWithErrors() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateSale(LONGSTRING);
		Assert.assertNotNull(result);
	}

	@Test(dataProvider = "validString")
	public void validateJanHappyFlow(String jan) {
		WSBuyerResponse result = webServiceBuyerValidator.validateJan(jan);
		Assert.assertNull(result);
	}

	@Test
	public void validateJanWithErrors() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateJan(LONGSTRING);
		Assert.assertNotNull(result);
	}

	@Test(dataProvider = "validString")
	public void validatePackFeeHappyFlow(String packFee) {
		WSBuyerResponse result = webServiceBuyerValidator
				.validatePackFee(packFee);
		Assert.assertNull(result);
	}

	@Test
	public void validatePackFeeWithErrors() {
		WSBuyerResponse result = webServiceBuyerValidator
				.validatePackFee(LONGSTRING);
		Assert.assertNotNull(result);
	}

	@Test(dataProvider = "validString")
	public void validateColumnHappyFlow(String cValue) {
		WSBuyerResponse result = webServiceBuyerValidator.validateColumn(
				cValue, "1");
		Assert.assertNull(result);
	}

	@Test
	public void validateColumnWithErrors() {
		WSBuyerResponse result = webServiceBuyerValidator.validateColumn(
				LONGSTRING, "1");
		Assert.assertNotNull(result);
	}

	@Test(dataProvider = "validString")
	public void validateDeliveryHappyFlow(String delivery) {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateDelivery(delivery);
		Assert.assertNull(result);
	}

	@Test(dataProvider = "invalidString")
	public void validateDeliveryWithErrors(String delivery) {
		WSBuyerResponse result = webServiceBuyerValidator
				.validateDelivery(delivery);
		Assert.assertNotNull(result);
	}

	@Test
	public void testValidateInsertRequest() {
		// successful validation
		WSBuyerAddOrderSheetRequest request = new WSBuyerAddOrderSheetRequest();
		WSBuyerResponse result = webServiceBuyerValidator
				.validateInsertRequest(request);
		Assert.assertNull(result);

		// failed validation
		result = webServiceBuyerValidator.validateInsertRequest(null);
		Assert.assertEquals(result.getResultCode(),
				WebServiceConstants.REQUIRED_ERR_CODE);

	}

	@Test
	public void validateDealingPatternHappyFlow() {
		when(
				dealingPatternServiceMock.getAllSellerIdsByBuyerIds(anyList(),
						anyString(), anyString())).thenReturn(
				Arrays.asList(createSellerUser()));
		WSBuyerResponse result = webServiceBuyerValidator
				.validateDealingPattern(Arrays.asList(1), Arrays.asList(1),
						"04132013");
		Assert.assertNull(result);
	}

	@DataProvider(name = "invalidDealingPattern")
	private Object[][] createinvalidDealingPatternList() {
		User user = createSellerUser();
		user.setUserId(3);
		return new Object[][] { { null, null },
				{ Arrays.asList(1), Arrays.asList(user) }

		};
	}

	@Test(dataProvider = "invalidDealingPattern")
	public void validateDealingPatternWithErrors(List<Integer> sellerIds,
			List<User> sellerIdResult) {
		when(
				dealingPatternServiceMock.getAllSellerIdsByBuyerIds(anyList(),
						anyString(), anyString())).thenReturn(sellerIdResult);
		WSBuyerResponse result = webServiceBuyerValidator
				.validateDealingPattern(Arrays.asList(1), sellerIds, "04132013");
		Assert.assertNotNull(result);
	}

	@Test
	public void validateAllColumnsHappyFlow() {

		WSBuyerResponse result = webServiceBuyerValidator
				.validateAllColumns(createWSBuyerSKUAdd());
		Assert.assertNull(result);
	}

	@Test
	public void validateAllColumnsWithErrors() {
		WSBuyerSKUAdd sku = createWSBuyerSKUAdd();
		sku.setCenter(new BigDecimal("1000000000"));
		WSBuyerResponse result = webServiceBuyerValidator
				.validateAllColumns(sku);
		Assert.assertNotNull(result);

		sku.setCenter(new BigDecimal("1"));
		sku.setDelivery(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		
		sku.setDelivery("Delivery");
		sku.setSale(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setSale("1");
		sku.setJan(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setJan("1");
		sku.setPackFee(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setPackFee("1");
		sku.setColumn01(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setColumn01("1");
		sku.setColumn02(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setColumn02("1");
		sku.setColumn03(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setColumn03("1");
		sku.setColumn04(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setColumn04("1");
		sku.setColumn05(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setColumn05("1");
		sku.setColumn06(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setColumn06("1");
		sku.setColumn07(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setColumn07("1");
		sku.setColumn08(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setColumn08("1");
		sku.setColumn09(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setColumn09("1");
		sku.setColumn10(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setColumn10("1");
		sku.setColumn11(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setColumn11("1");
		sku.setColumn12(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setColumn12("1");
		sku.setColumn13(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setColumn13("1");
		sku.setColumn14(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);
		sku.setColumn14("1");
		sku.setColumn15(LONGSTRING);
		result = webServiceBuyerValidator.validateAllColumns(sku);
		Assert.assertNotNull(result);

	}


	
	
	@Test
	public void validateWSSKUFieldsforUpdateHappyFlow() {
		WSBuyerSKUUpdate wsBuyerSKUUpdate = createWSBuyerSKUUpdate();

		when(
				dealingPatternServiceMock.getAllSellerIdsByBuyerIds(anyList(),
						anyString(), anyString())).thenReturn(
				Arrays.asList(createSellerUser()));
		when(userInfoServiceMock.getUserById(anyInt())).thenReturn(createSellerUser());
		when(skuServiceMock.selectSKU(anyInt())).thenReturn(wsBuyerSKUUpdate.getSku());
		when(userInfoServiceMock.getCompanyIdByUserId(anyString())).thenReturn(4);
		when(orderUnitServiceMock.getSellingUom(anyInt(), anyString())).thenReturn(new OrderUnit());
		
		WSBuyerResponse result = webServiceBuyerValidator
				.validateWSSKUFieldsforUpdate(createBuyerAdminUser(), new Date(), createWSBuyerSKUUpdate(),1 );

		Assert.assertNull(result);
	}
	
	
	@Test
	public void validateCommonRequiredSKUFieldsWithErrors() {
		WSBuyerSKUUpdate skuForUpdate = createWSBuyerSKUUpdate();

		when(
				dealingPatternServiceMock.getAllSellerIdsByBuyerIds(anyList(),
						anyString(), anyString())).thenReturn(
				Arrays.asList(createSellerUser()));
		when(userInfoServiceMock.getUserById(anyInt())).thenReturn(createSellerUser());
		when(skuServiceMock.selectSKU(anyInt())).thenReturn(skuForUpdate.getSku());
		when(userInfoServiceMock.getCompanyIdByUserId(anyString())).thenReturn(4);
		when(orderUnitServiceMock.getSellingUom(anyInt(), anyString())).thenReturn(new OrderUnit());
		

		

		
		skuForUpdate.setSellerId(null);
		WSBuyerResponse result = webServiceBuyerValidator
				.validateWSSKUFieldsforUpdate(createBuyerAdminUser(), new Date(), skuForUpdate,1 );
		Assert.assertNotNull(result);
		
		skuForUpdate.setSellerId(new Integer("1000000000"));
		result = webServiceBuyerValidator
				.validateWSSKUFieldsforUpdate(createBuyerAdminUser(), new Date(), skuForUpdate,1 );
		Assert.assertNotNull(result);
	}
	@Test 
	public void validateWSSKUFieldsforUpdateWithSKUErrors(){
		WSBuyerSKUUpdate skuForUpdate = createWSBuyerSKUUpdate();

		when(
				dealingPatternServiceMock.getAllSellerIdsByBuyerIds(anyList(),
						anyString(), anyString())).thenReturn(
				Arrays.asList(createSellerUser()));
		when(userInfoServiceMock.getUserById(anyInt())).thenReturn(createSellerUser());
		when(skuServiceMock.selectSKU(anyInt())).thenReturn(null);
		when(userInfoServiceMock.getCompanyIdByUserId(anyString())).thenReturn(4);
		when(orderUnitServiceMock.getSellingUom(anyInt(), anyString())).thenReturn(new OrderUnit());
		
		WSBuyerResponse result = webServiceBuyerValidator
				.validateWSSKUFieldsforUpdate(createBuyerAdminUser(), new Date(), skuForUpdate,1 );

		Assert.assertNotNull(result);
		skuForUpdate.setSkuId(null);
		result = webServiceBuyerValidator
				.validateWSSKUFieldsforUpdate(createBuyerAdminUser(), new Date(), skuForUpdate,1 );

		Assert.assertNotNull(result);

	}
	
	
	@Test
	public void validateCommonFieldsWithErrors(){
		WSBuyerSKUUpdate skuForUpdate = createWSBuyerSKUUpdate();

		when(
				dealingPatternServiceMock.getAllSellerIdsByBuyerIds(anyList(),
						anyString(), anyString())).thenReturn(
				Arrays.asList(createSellerUser()));
		when(userInfoServiceMock.getUserById(anyInt())).thenReturn(createSellerUser());
		when(skuServiceMock.selectSKU(anyInt())).thenReturn(skuForUpdate.getSku());
		when(userInfoServiceMock.getCompanyIdByUserId(anyString())).thenReturn(4);
		when(orderUnitServiceMock.getSellingUom(anyInt(), anyString())).thenReturn(null);
		WSBuyerResponse result = webServiceBuyerValidator
				.validateWSSKUFieldsforUpdate(createBuyerAdminUser(), new Date(), skuForUpdate,1 );
		Assert.assertNotNull(result);
		
		skuForUpdate.setB_sellingUomName(LONGSTRING);
		result = webServiceBuyerValidator
				.validateWSSKUFieldsforUpdate(createBuyerAdminUser(), new Date(), skuForUpdate,1 );
		Assert.assertNotNull(result);
		
		skuForUpdate.setB_skuComment(LONGSTRING);
		result = webServiceBuyerValidator
				.validateWSSKUFieldsforUpdate(createBuyerAdminUser(), new Date(), skuForUpdate,1 );
		Assert.assertNotNull(result);
		
		skuForUpdate.setB_purchasePrice(new BigDecimal("1000000009.0328432"));
		result = webServiceBuyerValidator
				.validateWSSKUFieldsforUpdate(createBuyerAdminUser(), new Date(), skuForUpdate,1 );
		Assert.assertNotNull(result);
		
		skuForUpdate.setB_sellingPrice(new BigDecimal("1000000009.0328432"));
		result = webServiceBuyerValidator
				.validateWSSKUFieldsforUpdate(createBuyerAdminUser(), new Date(), skuForUpdate,1 );
		Assert.assertNotNull(result);
	}
	
	@Test
	public void validateAddSKUDetailsHappyFlow(){
		when(userInfoServiceMock.getCompanyIdByUserId(anyString())).thenReturn(4);
		when(dealingPatternServiceMock.getAllSellerIdsByBuyerIds(anyList(), anyString(), anyString())).thenReturn(Arrays.asList(createSellerUser()));
		WSBuyerAddOrderSheetSKU  sku = new WSBuyerSKUAdd();
		sku.setSellerId(1);
		
		String uomName = "KG";
		if (sku instanceof WSBuyerSKUUpdate) {
			SKU s = new SKU();
			OrderUnit o = new OrderUnit();
			o.setOrderUnitName(uomName);
			s.setOrderUnit(o);
			((WSBuyerSKUUpdate)sku).setSku(s);
		} else {
			((WSBuyerSKUAdd)sku).setUnitOrderName(uomName);
		}
		sku.setDetails(createSKUDetails());
		WSBuyerResponse result = webServiceBuyerValidator
				.validateSKUBuyerDetails(createBuyerAdminUser(),sku,new Date() );
		Assert.assertNull(result);
		ArrayList<WSBuyerAddOrderSheetDetails> details = createSKUDetails();
		details.get(0).setVisibility(null);
		sku.setDetails(details);
		result = webServiceBuyerValidator
				.validateSKUBuyerDetails(createBuyerUser(),sku,new Date());
		Assert.assertNull(result);

	}
	
	@Test
	public void validateAddSKUDetailsEmptyDetails(){
		WSBuyerAddOrderSheetSKU  sku = new WSBuyerSKUAdd();
		sku.setSellerId(1);
		String uomName = "KG";
		if (sku instanceof WSBuyerSKUUpdate) {
			SKU s = new SKU();
			OrderUnit o = new OrderUnit();
			o.setOrderUnitName(uomName);
			s.setOrderUnit(o);
			((WSBuyerSKUUpdate)sku).setSku(s);
		} else {
			((WSBuyerSKUAdd)sku).setUnitOrderName(uomName);
		}
		sku.setDetails(null);
		WSBuyerResponse result = webServiceBuyerValidator
				.validateSKUBuyerDetails(createBuyerAdminUser(),sku,new Date() );
		Assert.assertNotNull(result);

	
		sku.setDetails(new ArrayList<WSBuyerAddOrderSheetDetails>());
		result = webServiceBuyerValidator
				.validateSKUBuyerDetails(createBuyerUser(),sku,new Date() );
		Assert.assertNotNull(result);

	}
	@Test
	public void validateAddSKUDetailsBuyerWithErrors(){
		ArrayList<WSBuyerAddOrderSheetDetails> details = createSKUDetails();
		details.add(new WSBuyerAddOrderSheetDetails());
		WSBuyerAddOrderSheetSKU  sku = new WSBuyerSKUAdd();
		String uomName = "KG";
		if (sku instanceof WSBuyerSKUUpdate) {
			SKU s = new SKU();
			OrderUnit o = new OrderUnit();
			o.setOrderUnitName(uomName);
			s.setOrderUnit(o);
			((WSBuyerSKUUpdate)sku).setSku(s);
		} else {
			((WSBuyerSKUAdd)sku).setUnitOrderName(uomName);
		}
		sku.setSellerId(1);
		sku.setDetails(details);
		WSBuyerResponse result = webServiceBuyerValidator
				.validateSKUBuyerDetails(createBuyerUser(),sku,new Date());
		Assert.assertNotNull(result);
		
		details.remove(1);
		WSBuyerAddOrderSheetDetails detail = details.get(0);
		detail.setBuyerId(5);
		sku.setDetails(details);
		result = webServiceBuyerValidator
				.validateSKUBuyerDetails(createBuyerUser(),sku,new Date());
		Assert.assertNotNull(result);
		
		detail.setBuyerId(1);
		detail.setQty(null);
		result = webServiceBuyerValidator
				.validateSKUBuyerDetails(createBuyerUser(),sku,new Date());
		Assert.assertNotNull(result);
		
		detail.setQty(new BigDecimal(1));
		detail.setVisibility("1");
		result = webServiceBuyerValidator
				.validateSKUBuyerDetails(createBuyerUser(),sku,new Date());
		Assert.assertNotNull(result);


	}
	
	@Test
	public void validateAddSKUDetailsBuyerAdminWithErrors(){
		WSBuyerAddOrderSheetSKU  sku = new WSBuyerSKUUpdate();
		sku.setSellerId(1);
		String uomName = "KG";
		if (sku instanceof WSBuyerSKUUpdate) {
			SKU s = new SKU();
			OrderUnit o = new OrderUnit();
			o.setOrderUnitName(uomName);
			s.setOrderUnit(o);
			((WSBuyerSKUUpdate)sku).setSku(s);
		} else {
			((WSBuyerSKUAdd)sku).setUnitOrderName(uomName);
		}
		WSBuyerResponse result = webServiceBuyerValidator
				.validateSKUBuyerDetails(createBuyerAdminUser(),sku,new Date());
		Assert.assertNotNull(result);
		
		
		ArrayList<WSBuyerAddOrderSheetDetails> details = createSKUDetails();
		when(userInfoServiceMock.getCompanyIdByUserId(anyString())).thenReturn(6);
		when(dealingPatternServiceMock.getAllSellerIdsByBuyerIds(anyList(), anyString(), anyString())).thenReturn(Arrays.asList(createSellerUser()));

		sku.setDetails(details);
		result = webServiceBuyerValidator
				.validateSKUBuyerDetails(createBuyerAdminUser(),sku,new Date());
		Assert.assertNotNull(result);
		
		WSBuyerAddOrderSheetDetails detail = details.get(0);
		detail.setVisibility("3");
		result = webServiceBuyerValidator
				.validateSKUBuyerDetails(createBuyerAdminUser(),sku,new Date());
		Assert.assertNotNull(result);
		
		detail.setBuyerId(null);
		result = webServiceBuyerValidator
				.validateSKUBuyerDetails(createBuyerAdminUser(),sku,new Date());
		Assert.assertNotNull(result);
		
		
		when(userInfoServiceMock.getCompanyIdByUserId(anyString())).thenReturn(4);

		detail.setBuyerId(1);
		detail.setVisibility(null);
		detail.setQty(null);
		
		result = webServiceBuyerValidator
				.validateSKUBuyerDetails(createBuyerAdminUser(),sku,new Date());
		Assert.assertNotNull(result);
		
		


	}
	
	
	
	@Test
	public void validateWSSKUFieldsforInsertHappyFlow(){
		when(
				dealingPatternServiceMock.getAllSellerIdsByBuyerIds(anyList(),
						anyString(), anyString())).thenReturn(
				Arrays.asList(createSellerUser()));
		when(userInfoServiceMock.getUserById(anyInt())).thenReturn(createSellerUser());
		when(skuServiceMock.selectSKU(anyInt())).thenReturn(new SKU());
		when(userInfoServiceMock.getCompanyIdByUserId(anyString())).thenReturn(4);
		OrderUnit orderUnit = new OrderUnit();
		orderUnit.setOrderUnitId(1);
		orderUnit.setOrderUnitName("C/S");
		when(orderUnitServiceMock.getSellingUom(anyInt(), anyString())).thenReturn(orderUnit);
		when(orderUnitServiceMock.getOrderUnit(anyInt(), anyString())).thenReturn(orderUnit);
		when(orderUnitServiceMock.getOrderUnitById(anyInt())).thenReturn(orderUnit);
		when(categoryServiceMock.getCategoryByName(anyInt(), anyString())).thenReturn(createCategory(1, "Fruits"));
		SKUGroup grp = new SKUGroup();
		grp.setSkuGroupId(1);
		 
		when(skuGroupServiceMock.getSKUGroupByName(anyInt(), anyInt(), anyString())).thenReturn(grp);
		WSBuyerResponse result = webServiceBuyerValidator
				.validateWSSKUFieldsforInsert(createBuyerAdminUser(), new Date(), createWSBuyerSKUAdd(),createCategory(1, "Fruits"));

		Assert.assertNull(result);

		
	}
	
	private Category createCategory(int id, String name)
	{
		Category category = new Category();
		category.setCategoryId(id);
		category.setDescription(name);
		return category;
	}
	
	@Test
	public void validateAddSKURequiredFieldsWithErrors(){
		when(
				dealingPatternServiceMock.getAllSellerIdsByBuyerIds(anyList(),
						anyString(), anyString())).thenReturn(
				Arrays.asList(createSellerUser()));
		when(userInfoServiceMock.getUserById(anyInt())).thenReturn(createSellerUser());
		when(skuServiceMock.selectSKU(anyInt())).thenReturn(new SKU());
		when(userInfoServiceMock.getCompanyIdByUserId(anyString())).thenReturn(4);
		when(orderUnitServiceMock.getSellingUom(anyInt(), anyString())).thenReturn(new OrderUnit());
		
		
		WSBuyerSKUAdd sku = createWSBuyerSKUAdd();
		sku.setPackageType(LONGSTRING);
		WSBuyerResponse result = webServiceBuyerValidator
				.validateWSSKUFieldsforInsert(createBuyerAdminUser(), new Date(), sku,createCategory(1, "Fruits") );
		Assert.assertNotNull(result);
		
		sku.setPackageType(null);
		result = webServiceBuyerValidator
				.validateWSSKUFieldsforInsert(createBuyerAdminUser(), new Date(), sku,createCategory(1, "Fruits") );
		Assert.assertNotNull(result);

		
	
		
		
		
		sku.setPackageQuantity(new BigDecimal("10000000003"));
		result = webServiceBuyerValidator
				.validateWSSKUFieldsforInsert(createBuyerAdminUser(), new Date(), sku,createCategory(1, "Fruits") );
		Assert.assertNotNull(result);
		
		sku.setPackageQuantity(null);
		result = webServiceBuyerValidator
				.validateWSSKUFieldsforInsert(createBuyerAdminUser(), new Date(), sku,createCategory(1, "Fruits") );
		Assert.assertNotNull(result);
		
		sku.setSkuName(LONGSTRING);
		result = webServiceBuyerValidator
				.validateWSSKUFieldsforInsert(createBuyerAdminUser(), new Date(), sku,createCategory(1, "Fruits") );
		Assert.assertNotNull(result);
		
		sku.setSkuName(null);
		result = webServiceBuyerValidator
				.validateWSSKUFieldsforInsert(createBuyerAdminUser(), new Date(), sku,createCategory(1, "Fruits") );
		Assert.assertNotNull(result);
		
	}
	
	private WSBuyerSKUUpdate createWSBuyerSKUUpdate(){
		OrderUnit orderUnit = new OrderUnit();
		orderUnit.setOrderUnitName("KG");
		
		SKU sku = new SKU();
		sku.setOrderUnit(orderUnit);
		sku.setSkuCategoryId(1);
		User user = new User();
		user.setUserId(1);
		sku.setUser(user);
		
		WSBuyerSKUUpdate wsBuyerSKUUpdate = new WSBuyerSKUUpdate();
		wsBuyerSKUUpdate.setSkuId(12);
		wsBuyerSKUUpdate.setSellerId(user.getUserId());
		wsBuyerSKUUpdate.setB_purchasePrice(new BigDecimal(10));
		wsBuyerSKUUpdate.setB_sellingPrice(new BigDecimal(12));
		wsBuyerSKUUpdate.setB_sellingUomName("C/S");
		wsBuyerSKUUpdate.setB_skuComment("comment");
		wsBuyerSKUUpdate.setDetails(createSKUDetails());
		wsBuyerSKUUpdate.setSku(sku);
		return wsBuyerSKUUpdate;
	}
	
	
	
	private	ArrayList< WSBuyerAddOrderSheetDetails> createSKUDetails(){
		ArrayList<WSBuyerAddOrderSheetDetails> list = new ArrayList<WSBuyerAddOrderSheetDetails>();
		WSBuyerAddOrderSheetDetails d = new WSBuyerAddOrderSheetDetails();
		d.setBuyerId(1);
		d.setQty(new BigDecimal(1));
		d.setVisibility("1");
		list.add(d);
		
		
		return list;
	}
	private WSBuyerSKUAdd createWSBuyerSKUAdd() {
		WSBuyerSKUAdd wsBuyerSKUAdd = new WSBuyerSKUAdd();
		wsBuyerSKUAdd.setSellerId(1);
		wsBuyerSKUAdd.setB_purchasePrice(new BigDecimal(10));
		wsBuyerSKUAdd.setB_sellingPrice(new BigDecimal(12));
		wsBuyerSKUAdd.setB_sellingUomName("C/S");
		wsBuyerSKUAdd.setB_skuComment("comment");
		wsBuyerSKUAdd.setSkuGroupName("abc");
		wsBuyerSKUAdd.setSkuName("testsku");
		wsBuyerSKUAdd.setPackageQuantity(new BigDecimal(10));
		wsBuyerSKUAdd.setPackageType("packtype");
		wsBuyerSKUAdd.setDetails(createSKUDetails());
		wsBuyerSKUAdd.setCenter(new BigDecimal("1"));
		wsBuyerSKUAdd.setDelivery("Delivery");
		wsBuyerSKUAdd.setSale("1");
		wsBuyerSKUAdd.setJan("1");
		wsBuyerSKUAdd.setPackFee("1");
		wsBuyerSKUAdd.setColumn01("1");
		wsBuyerSKUAdd.setColumn02("1");
		wsBuyerSKUAdd.setColumn03("1");
		wsBuyerSKUAdd.setColumn04("1");
		wsBuyerSKUAdd.setColumn05("1");
		wsBuyerSKUAdd.setColumn06("1");
		wsBuyerSKUAdd.setColumn07("1");
		wsBuyerSKUAdd.setColumn08("1");
		wsBuyerSKUAdd.setColumn09("1");
		wsBuyerSKUAdd.setColumn10("1");
		wsBuyerSKUAdd.setColumn11("1");
		wsBuyerSKUAdd.setColumn12("1");
		wsBuyerSKUAdd.setColumn13("1");
		wsBuyerSKUAdd.setColumn14("1");
		wsBuyerSKUAdd.setColumn15("1");
		return wsBuyerSKUAdd;
	}

	private User createBuyerUser() {
		User user = createrUser();
		Company co = new Company();
		co.setCompanyId(4);
		user.setCompany(co);
		Role role = new Role();
		role.setBuyerFlag("1");
		role.setBuyerAdminFlag("0");
		role.setRoleId(3L);
		user.setRole(role);
		return user;
	}

	private User createBuyerAdminUser() {
		User user = createrUser();
		Company co = new Company();
		co.setCompanyId(4);
		user.setCompany(co);
		Role role = new Role();
		role.setBuyerFlag("0");
		role.setBuyerAdminFlag("1");
		role.setRoleId(4L);

		user.setRole(role);
		return user;
	}

	private User createSellerUser() {
		User user = createrUser();
		Role role = new Role();
		role.setBuyerFlag("0");
		role.setBuyerAdminFlag("0");
		role.setSellerFlag("1");
		role.setSellerAdminFlag("0");
		user.setRole(role);
		role.setRoleId(1L);

		return user;
	}

	private User createSellerAdminUser() {
		User user = createrUser();
		Role role = new Role();
		role.setBuyerFlag("0");
		role.setBuyerAdminFlag("0");
		role.setSellerFlag("0");
		role.setSellerAdminFlag("1");
		role.setRoleId(2L);

		user.setRole(role);
		return user;
	}

	private User createrUser() {
		return createUser("username", "password");
	}

	private User createUser(String username, String password) {
		User user = new User();
		user.setUserId(1);
		user.setUserName(username);
		user.setPassword(password);
		return user;
	}

	private List<WSBuyerSKUUpdate> createWSBuyerSKUUpdateList() {
		List<WSBuyerSKUUpdate> list = new ArrayList<WSBuyerSKUUpdate>();
		WSBuyerSKUUpdate u = new WSBuyerSKUUpdate();
		list.add(u);
		return list;
	}

	private List<WSBuyerSKUAdd> createWSBuyerSKUAddList() {
		List<WSBuyerSKUAdd> list = new ArrayList<WSBuyerSKUAdd>();
		WSBuyerSKUAdd a = new WSBuyerSKUAdd();
		list.add(a);
		return list;
	}

}
