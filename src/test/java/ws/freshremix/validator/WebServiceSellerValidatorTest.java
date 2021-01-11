package ws.freshremix.validator;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.constants.WebServiceConstants;
import com.freshremix.model.Category;
import com.freshremix.model.Company;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.Role;
import com.freshremix.model.SKU;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.model.WSSellerBuyerDetails;
import com.freshremix.model.WSSellerSKUCreateRequest;
import com.freshremix.model.WSSellerSKURequest;
import com.freshremix.model.WSSellerRequest;
import com.freshremix.model.WSSellerResponse;
import com.freshremix.service.CategoryService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.LoginService;
import com.freshremix.service.MessageI18NService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.service.UsersInformationService;
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
public class WebServiceSellerValidatorTest {
	
	private static final String LONGSTRING = "Loremipsumdolorsitametconsectetueadipiscingelitseddiamnonummy";

	private WebServiceSellerValidator webServiceSellerValidator = new WebServiceSellerValidator();

	
	private MessageI18NService messageI18NServiceMock = Mockito.mock(MessageI18NService.class);
	private LoginService loginServiceMock = Mockito.mock(LoginService.class);
	private CategoryService categoryServiceMock = Mockito.mock(CategoryService.class);
	private DealingPatternService dealingPatternServiceMock = Mockito.mock(DealingPatternService.class);
	private OrderUnitService orderUnitServiceMock = Mockito.mock(OrderUnitService.class);
	private SKUGroupService skuGroupServiceMock = Mockito.mock(SKUGroupService.class);
	private UsersInformationService userInfoServiceMock = Mockito.mock(UsersInformationService.class);
	
	@BeforeClass
	public void beforeClass() {
		webServiceSellerValidator.setLoginService(loginServiceMock);
		webServiceSellerValidator.setMessageI18NService(messageI18NServiceMock);
		webServiceSellerValidator.setUserInfoService(userInfoServiceMock);
		webServiceSellerValidator.setCategoryService(categoryServiceMock);
		webServiceSellerValidator
				.setDealingPatternService(dealingPatternServiceMock);
		webServiceSellerValidator.setOrderUnitService(orderUnitServiceMock);
		webServiceSellerValidator.setSkuGroupService(skuGroupServiceMock);
	}

	@AfterMethod
	public void afterMethod() {
		Mockito.reset(loginServiceMock, messageI18NServiceMock,
				userInfoServiceMock, categoryServiceMock,
				dealingPatternServiceMock,  orderUnitServiceMock);
	}
	
	private OrderUnit createOUM(String name){
		OrderUnit uom = new OrderUnit();
		uom.setOrderUnitName(name);
		return uom;
	}
	
	private User createrUser() {
		return createUser("username", "password");
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
	private User createUser(String username, String password) {
		User user = new User();
		user.setUserId(1);
		user.setUserName(username);
		user.setPassword(password);
		return user;
	}
	private Category createCategory(int id, String name)
	{
		Category category = new Category();
		category.setCategoryId(id);
		category.setDescription(name);
		return category;
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
	private WSSellerSKUCreateRequest createWSSellerCreateSheetSKU() {
		WSSellerSKUCreateRequest wsSKU = new WSSellerSKUCreateRequest();
		wsSKU.setUomName("C/S");
		wsSKU.setSkuExternalID("112432");
		wsSKU.setAltPrice1("1233");
		wsSKU.setAltPrice2("1232");
		wsSKU.setAreaOfProduction("tokyo");
		wsSKU.setClass1("class1");
		wsSKU.setClass2("class2");
		wsSKU.setMarket("market");
		wsSKU.setPackageQty("1200");
		wsSKU.setPackageType("crate");
		wsSKU.setSkuGroupName("banana group");
		wsSKU.setSkuName("sku name");
		wsSKU.setUnitPriceWithoutTax("233");
		wsSKU.setMaxQty("10000");
		wsSKU.setFinalizeFlag("0");
		wsSKU.setCenter("88");
		wsSKU.setDelivery("delivery");
		wsSKU.setSale("sale");
		wsSKU.setJan("jan");
		wsSKU.setPackFee("pack fee");
		wsSKU.setColumn01("1");
		wsSKU.setColumn02("2");
		wsSKU.setColumn03("3");
		wsSKU.setColumn04("4");
		wsSKU.setColumn05("5");
		wsSKU.setColumn06("6");
		wsSKU.setColumn07("7");
		wsSKU.setColumn08("8");
		wsSKU.setColumn09("9");
		wsSKU.setColumn10("10");
		wsSKU.setColumn11("11");
		wsSKU.setColumn12("12");
		wsSKU.setColumn13("13");
		wsSKU.setColumn14("14");
		wsSKU.setColumn15("15");
		wsSKU.setBuyerDetails(createSKUDetails());
		
		return wsSKU;
	}
	
	private	ArrayList< WSSellerBuyerDetails> createSKUDetails(){
		ArrayList<WSSellerBuyerDetails> list = new ArrayList<WSSellerBuyerDetails>();
		WSSellerBuyerDetails d = new WSSellerBuyerDetails();
		d.setBuyerId("14");
		d.setQuantity("12");
		d.setVisibility("1");
		list.add(d);
		
		
		return list;
	}

	@Test
	public void validateAllColumnsHappyFlow() {
		SKU sku = new SKU();
		WSSellerResponse result = webServiceSellerValidator
				.validateAllColumns(createWSSellerCreateSheetSKU(), sku);
		Assert.assertNull(result);
		Assert.assertEquals(sku.getColumn01().intValue(),88);
		Assert.assertEquals(sku.getColumn02(),"delivery");
		Assert.assertEquals(sku.getColumn03(),"sale");
		Assert.assertEquals(sku.getColumn04(),"jan");
		Assert.assertEquals(sku.getColumn05(),"pack fee");
		Assert.assertEquals(sku.getColumn06(),"1");
		Assert.assertEquals(sku.getColumn07(),"2");
		Assert.assertEquals(sku.getColumn08(),"3");
		Assert.assertEquals(sku.getColumn09(),"4");
		Assert.assertEquals(sku.getColumn10(),"5");
		Assert.assertEquals(sku.getColumn11(),"6");
		Assert.assertEquals(sku.getColumn12(),"7");
		Assert.assertEquals(sku.getColumn13(),"8");
		Assert.assertEquals(sku.getColumn14(),"9");
		Assert.assertEquals(sku.getColumn15(),"10");
		Assert.assertEquals(sku.getColumn16(),"11");
		Assert.assertEquals(sku.getColumn17(),"12");
		Assert.assertEquals(sku.getColumn18(),"13");
		Assert.assertEquals(sku.getColumn19(),"14");
		Assert.assertEquals(sku.getColumn20(),"15");
				
	}

	
	@Test
	public void validateAllColumnsWithErrors() {
		SKU sku = new SKU();
		WSSellerSKURequest wsSKU = createWSSellerCreateSheetSKU();
		//not numeric
		wsSKU.setCenter("sdfads");
		WSSellerResponse result = webServiceSellerValidator
				.validateAllColumns(wsSKU,sku);
		Assert.assertNotNull(result);
		
		//maxlength
		wsSKU.setCenter("1000000000");
		result = webServiceSellerValidator
				.validateAllColumns(wsSKU,sku);
		Assert.assertNotNull(result);

		//negative
		wsSKU.setCenter("-90");
		result = webServiceSellerValidator
				.validateAllColumns(wsSKU,sku);
		Assert.assertNotNull(result);
		
		//decimal
		wsSKU.setCenter("1.4");
		result = webServiceSellerValidator
				.validateAllColumns(wsSKU,sku);
		Assert.assertNotNull(result);
		
		wsSKU.setCenter("145");
		wsSKU.setDelivery(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		
		wsSKU.setDelivery("Delivery");
		wsSKU.setSale(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setSale("1");
		wsSKU.setJan(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setJan("1");
		wsSKU.setPackFee(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setPackFee("1");
		wsSKU.setColumn01(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setColumn01("1");
		wsSKU.setColumn02(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setColumn02("1");
		wsSKU.setColumn03(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setColumn03("1");
		wsSKU.setColumn04(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setColumn04("1");
		wsSKU.setColumn05(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setColumn05("1");
		wsSKU.setColumn06(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setColumn06("1");
		wsSKU.setColumn07(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setColumn07("1");
		wsSKU.setColumn08(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setColumn08("1");
		wsSKU.setColumn09(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setColumn09("1");
		wsSKU.setColumn10(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setColumn10("1");
		wsSKU.setColumn11(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setColumn11("1");
		wsSKU.setColumn12(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setColumn12("1");
		wsSKU.setColumn13(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setColumn13("1");
		wsSKU.setColumn14(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);
		wsSKU.setColumn14("1");
		wsSKU.setColumn15(LONGSTRING);
		result = webServiceSellerValidator.validateAllColumns(wsSKU, sku);
		Assert.assertNotNull(result);

	}
	
	@Test
	public void testValidateBuyerIdHappyFlow(){
		User buyer = createBuyerUser();
		buyer.setUserId(14);
		Mockito.when(
				userInfoServiceMock.getUserById(anyInt())).thenReturn(buyer);
		Map<String, Object> result = webServiceSellerValidator
				.validateBuyerId("14", Arrays.asList(14,15,16));
		
		WSSellerResponse response = (WSSellerResponse) result.get("response");
		Assert.assertNull(response);
		User buyer1 = (User)result.get("buyer");
		Integer buyerId = buyer1.getUserId();
		Assert.assertNotNull(buyerId);
		Assert.assertEquals(buyerId.intValue(), 14);

	}
	
	@Test
	public void testValidateBuyerIdRequired(){
		
		Map<String, Object> result = webServiceSellerValidator
				.validateBuyerId(null,  Arrays.asList(14,15,16));
		
		WSSellerResponse response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("buyer"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.REQUIRED_ERR_CODE);
		result = webServiceSellerValidator
				.validateBuyerId("",  Arrays.asList(14,15,16));
		
		response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("buyer"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.REQUIRED_ERR_CODE);
	}
	
	@Test
	public void testValidateBuyerIdNonNumeric(){
		
		Map<String, Object> result = webServiceSellerValidator
				.validateBuyerId("gggg", Arrays.asList(14,15,16));
		
		WSSellerResponse response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("buyer"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);
	}
	
	
	
	
	@Test
	public void testValidateBuyerIdInvalidDP(){
		User buyer = createBuyerUser();
		buyer.setUserId(14);
		Mockito.when(
				userInfoServiceMock.getUserById(anyInt())).thenReturn(buyer);
		Map<String, Object> result = webServiceSellerValidator
				.validateBuyerId("14", Arrays.asList(15,16));
		
		WSSellerResponse response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("buyer"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);

	}

	@DataProvider(name = "validVisibility")
	private Object[][] createValidVisibility() {
		return new Object[][] { { "" },{ null }, {"1"}, {"0"}

		};
	}

	
	@Test(dataProvider="validVisibility")
	public void validateVisibilityHappyFlow(String vis){
		WSSellerBuyerDetails d = new WSSellerBuyerDetails();
		d.setVisibility(vis);
		WSSellerResponse response = webServiceSellerValidator.validateVisibility(d);
		Assert.assertNull(response);
		
	}
	@DataProvider(name = "invalidVisibility")
	private Object[][] createInvalidVisibility() {
		return new Object[][] {{"we"}, {"5"}

		};
	}

	
	@Test(dataProvider="invalidVisibility")
	public void validateVisibilityWithError(String vis){
		WSSellerBuyerDetails d = new WSSellerBuyerDetails();
		d.setVisibility(vis);
		WSSellerResponse response = webServiceSellerValidator.validateVisibility(d);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);
	}
	
	
	@DataProvider(name = "validQty")
	private Object[][] createValidQty() {
		return new Object[][] { { "",null },{ null,null }, {"1", createOUM("c/s")}, 
				{"123456789", createOUM("c/s")}, {"34.232", createOUM("c/s*d")}

		};
	}

	
	@Test(dataProvider="validQty")
	public void validateQtyHappyFlow(String qty, OrderUnit uom){
		WSSellerBuyerDetails detail = new WSSellerBuyerDetails();
		detail.setQuantity(qty);
		detail.setVisibility("1");
		WSSellerResponse response = webServiceSellerValidator.validateQty(detail, uom);
		Assert.assertNull(response);
		
	}
	@DataProvider(name = "invalidQty")
	private Object[][] createInvalidQty() {
		return new Object[][] { {"asdf", createOUM("c/s")},  {"-34", createOUM("c/s")}, 
				{"555555555555", createOUM("c/s")} ,{"34.232", createOUM("c/s")}

		};
	}
	
	@Test(dataProvider="createInvalidQty")
	public void validateQtyWithError(String qty, OrderUnit uom){
		WSSellerBuyerDetails detail = new WSSellerBuyerDetails();
		detail.setQuantity(qty);
		detail.setVisibility("1");
		WSSellerResponse response = webServiceSellerValidator.validateQty(detail, uom);
		Assert.assertNotNull(response);

	}
	@Test
	public void validateQtyWithVisibilityError(){
		WSSellerBuyerDetails detail = new WSSellerBuyerDetails();
		detail.setQuantity("12");
		detail.setVisibility("0");
		WSSellerResponse response = webServiceSellerValidator.validateQty(detail, createOUM("c/s"));
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);

	}

	@DataProvider(name = "validExternalSkuId")
	private Object[][] createValidExternalSKUId() {
		return new Object[][] {{"we"}, {"5s3"}, {"jfdshjf3883838"}, {"343204"}

		};
	}
	
	@Test(dataProvider="validExternalSkuId")
	public void validateExteralSKUIdHappyFlow(String externalSKUId)
	{
		WSSellerResponse response = webServiceSellerValidator.validateExternalSKUID(externalSKUId,true);
		Assert.assertNull(response);
	}
	
	@Test
	public void validateExteralSKUIdWithError()
	{
		WSSellerResponse response = webServiceSellerValidator.validateExternalSKUID(null,true);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.REQUIRED_ERR_CODE);
		
		response = webServiceSellerValidator.validateExternalSKUID(LONGSTRING,true);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.MAXLENGTH_ERR_CODE);

	}
	@Test
	public void testValidateClasses(){
		
		WSSellerSKURequest wsSku = createWSSellerCreateSheetSKU();
		SKU sku = new SKU();
		WSSellerResponse response = webServiceSellerValidator.validateClasses(wsSku, sku);
		Assert.assertNull(response);
		Assert.assertEquals(sku.getClazz(), "class2");
		Assert.assertEquals(sku.getGrade(), "class1");


	}
	
	@Test 
	public void testValidateGradeWithError(){

		WSSellerResponse response = webServiceSellerValidator.validateGrade(LONGSTRING);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.MAXLENGTH_ERR_CODE);


	}
	
	@Test 
	public void testValidateClazzWithError(){

		WSSellerResponse response = webServiceSellerValidator.validateClazz(LONGSTRING);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.MAXLENGTH_ERR_CODE);
	}
	
	@Test
	public void testValidateRequest() {
		// successful validation
		WSSellerRequest request = new WSSellerRequest();
		WSSellerResponse result = webServiceSellerValidator
				.validateRequest(request);
		Assert.assertNull(result);

		// failed validation
		result = webServiceSellerValidator.validateRequest(null);
		Assert.assertEquals(result.getResultCode(),
				WebServiceConstants.REQUIRED_ERR_CODE);

	}

	
	
	@DataProvider(name = "validSeller")
	public Object[][] validSeller() {
		return new Object[][] { { createSellerUser() },
				{ createSellerAdminUser() } };
	}

	@Test(dataProvider = "validSeller")
	public void testValidateSellerUser(User user) {
		Mockito.when(
				loginServiceMock.getUserByUsernameAndPassword(
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				user);
		Map<String, Object> resultMap = webServiceSellerValidator
				.validateSellerUser(user.getUserName(), user.getPassword());
		Assert.assertNull(resultMap.get("response"));
		Assert.assertEquals(resultMap.get("user"), user);
		Mockito.verify(loginServiceMock).getUserByUsernameAndPassword(
				Mockito.anyString(), Mockito.anyString());
	}

	@DataProvider(name = "invalidSeller")
	public Object[][] invalidSeller() {
		return new Object[][] {{null}, { createBuyerUser() },
				 { createBuyerAdminUser() } };
	}

	@Test(dataProvider = "invalidSeller")
	public void testValidateSellerUserInvalidData(User user) {
		Mockito.when(
				loginServiceMock.getUserByUsernameAndPassword(
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				user);
		Map<String, Object> resultMap = webServiceSellerValidator
				.validateSellerUser("username", "pass");
		WSSellerResponse response = (WSSellerResponse) resultMap.get("response");
		Assert.assertEquals(response.getResultCode(),
				WebServiceConstants.USER_ERR_CODE);
		Assert.assertNull(resultMap.get("user"));
	}


	@Test
	public void testValidateOrderDateHappyFlow(){
		Map<String, Object> result = webServiceSellerValidator
				.validateOrderDate("20131112");
		WSSellerResponse response = (WSSellerResponse) result.get("response");
		Assert.assertNull(response);
		Assert.assertNotNull(result.get("orderDate"));
	}
	
	@DataProvider(name = "invalidDate")
	public Object[][] invalidDate() {
		return new Object[][] { {"2243212334"}, {"oct. 2, 2013"},{"may 24, 2001"}, {null}};
	}

	@Test(dataProvider = "invalidDate")
	public void testValidateOrderDateWithError(String date) {
		Map<String, Object> result = webServiceSellerValidator
				.validateOrderDate(date);
		WSSellerResponse response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("orderDate"));
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

		Map<String, Object> result = webServiceSellerValidator
				.validateCategoryName(categoryName);
		
		Assert.assertNull(result.get("response"));
		Assert.assertNotNull(result.get("category"));
		Assert.assertEquals(result.get("category"), category);
		verify(categoryServiceMock).getCategoryById(defaultCategoryListId);
		verifyNoMoreInteractions(categoryServiceMock);
	}

	
	@Test
	public void validateCategoryNameLongName() {

		Map<String, Object> result = webServiceSellerValidator
				.validateCategoryName(LONGSTRING);
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
		.thenReturn(Arrays.asList(category, createCategory(2,  "Vegatables")));
		when(categoryServiceMock.getCategory(anyInt(), anyString()))
		.thenReturn(null);
		
		Map<String, Object> result = webServiceSellerValidator
				.validateCategoryName(categoryName);
		
		Assert.assertNotNull(result.get("response"));
		Assert.assertNull(result.get("category"));
		verify(categoryServiceMock).getAllCategory();
	

	}
	
	@Test
	public void validateCategoryNameValidName() {
		String categoryName = "Fruits";
		Integer userId = 1;
		Category category =createCategory(1, "Fruits");
		
		when(categoryServiceMock.getAllCategory())
		.thenReturn(Arrays.asList(category, createCategory(2,  "Vegetables")));
		when(categoryServiceMock.getCategory(anyInt(), anyString()))
		.thenReturn(category);
//		when(categoryServiceMock.getCategoryListBySelectedIds(anyList()))
//			.thenReturn(Arrays.asList(new UsersCategory()));
//		

		
		Map<String, Object> result = webServiceSellerValidator
				.validateCategoryName(categoryName);
		
		Assert.assertNull(result.get("response"));
		Assert.assertNotNull(result.get("category"));
		Assert.assertEquals(result.get("category"), category);
		

		
	}	
	
	@DataProvider(name = "validWholeNumbers")
	private Object[][] validWholeNumbers() {
		return new Object[][] {//
		{ "1" },//
				{ "190928877" },//
				{ "0" } };
	}
	@Test(dataProvider = "validWholeNumbers")
	public void validatePrice1HappyFlow(String bd) {
		SKU sku = new SKU();
		WSSellerResponse result = webServiceSellerValidator.validatePrice1(bd,sku);
		Assert.assertNull(result);
		Assert.assertEquals(sku.getPrice1().toString(), bd);
	}
	
	@Test(dataProvider = "validWholeNumbers")
	public void validatePrice2HappyFlow(String bd) {
		SKU sku = new SKU();
		WSSellerResponse result = webServiceSellerValidator.validatePrice2(bd,sku);
		Assert.assertNull(result);
		Assert.assertEquals(sku.getPrice2().toString(), bd);
	}

//	@Test(dataProvider = "validWholeNumbers")
//	public void validatePriceWOTaxHappyFlow(String bd) {
//		SKU sku = new SKU();
//		WSSellerResponse result = webServiceSellerValidator.validatePriceWithOutTax(bd,sku);
//		Assert.assertNull(result);
//		Assert.assertEquals(sku.getPriceWithoutTax().toString(), bd);
//	}
	
	@DataProvider(name = "invalidWholeNumbers")
	private Object[][] invalidWholeNumbers() {
		return new Object[][] {//
		{ "-1" },//
				{ "1909288771276273" },//
				{ "asdf2323432" } ,{"1.232"}, {"hfjsahdfkjs"}};
	}
	@DataProvider(name = "invalidPrice")
	private Object[][] invalidPrice() {
		return new Object[][] {//
		{ "-1" },//
				{ "1909288771276273" },//
				{ "asdf2323432" } ,{"1.232"}, {"hfjsahdfkjs"}, {"-552"}};
	}
	@Test(dataProvider = "invalidPrice")
	public void validatePrice1WithError(String bd) {
		SKU sku = new SKU();
		WSSellerResponse result = webServiceSellerValidator.validatePrice1(bd,sku);
		Assert.assertNotNull(result);
		Assert.assertNull(sku.getPrice1());
	}
	
	@Test(dataProvider = "invalidPrice")
	public void validatePrice2WithError(String bd) {
		SKU sku = new SKU();
		WSSellerResponse result = webServiceSellerValidator.validatePrice2(bd,sku);
		Assert.assertNotNull(result);
		Assert.assertNull(sku.getPrice2());
	}
	
	@Test(dataProvider = "invalidPrice")
	public void validatePriceWOTaxWithError(String bd) {
		SKU sku = new SKU();
		WSSellerResponse result = webServiceSellerValidator.validatePriceWithOutTax(bd,sku);
		Assert.assertNotNull(result);
		Assert.assertNull(sku.getPriceWithoutTax());
	}
	
	
	@Test(dataProvider = "validWholeNumbers")
	public void validatePackageQtyHappyFlow(String pqty) {
		SKU sku = new SKU();
		WSSellerResponse result = webServiceSellerValidator.validatePackageQty(pqty, sku);
		Assert.assertNull(result);
		Assert.assertEquals(sku.getPackageQuantity().toString(),pqty);
	}
	@Test(dataProvider = "invalidWholeNumbers")
	public void validatePackageQtyWithError(String pqty) {
		SKU sku = new SKU();
		WSSellerResponse result = webServiceSellerValidator.validatePackageQty(pqty, sku);
		Assert.assertNotNull(result);
		Assert.assertNull(sku.getPackageQuantity());
	}
	
	@DataProvider(name = "validSellerList")
	private Object[][] createvalidSellerList() {
		return new Object[][] { { createSellerAdminUser() },
				{ createSellerUser() }

		};
	}
	@Test
	public void validateSellerIdHappyFlow() {
		
		//login user role = seller
		User loginUser = createSellerUser();
		when(userInfoServiceMock.getUserById(Mockito.anyInt()))
				.thenReturn(createSellerUser());
		Map<String, Object> result = webServiceSellerValidator
				.validateSellerId(loginUser, "1",  Arrays.asList(1,3,4));
		Assert.assertNull(result.get("response"));
		Assert.assertNotNull(result.get("seller"));
		
		
		//login user role = seller admin
		loginUser = createSellerAdminUser();
		result = webServiceSellerValidator
				.validateSellerId(loginUser, "2",   Arrays.asList(2,3,4));
		Assert.assertNull(result.get("response"));
		Assert.assertNotNull(result.get("seller"));
		
	}
	
	
	@Test
	public void validateSellerIdInvalidSeller() {

		//sellerid = null
		User loginUser = createSellerUser();
		when(userInfoServiceMock.getUserById(Mockito.anyInt()))
				.thenReturn(createSellerUser());
		Map<String, Object> result = webServiceSellerValidator
				.validateSellerId(loginUser, null,  Arrays.asList(1,3,4));
		WSSellerResponse response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("seller"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.REQUIRED_ERR_CODE);

		//sellerid invalid format
		result = webServiceSellerValidator
				.validateSellerId(loginUser, "-23",  Arrays.asList(1,3,4));
		response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("seller"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);

		result = webServiceSellerValidator
				.validateSellerId(loginUser, "3.23",  Arrays.asList(1,3,4));
		response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("seller"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);

		result = webServiceSellerValidator
				.validateSellerId(loginUser, "sdfa",  Arrays.asList(1,3,4));
		response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("seller"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);

		result = webServiceSellerValidator
				.validateSellerId(loginUser, "111sdfa",  Arrays.asList(1,3,4));
		response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("seller"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);

		
		//seller does not exist
		when(userInfoServiceMock.getUserById(Mockito.anyInt()))
		.thenReturn(null);
		result = webServiceSellerValidator
				.validateSellerId(loginUser, "2",  Arrays.asList(1,3,4));
		response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("seller"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);

		//giver sellerid not seller
		when(userInfoServiceMock.getUserById(Mockito.anyInt()))
		.thenReturn(createBuyerUser());
		result = webServiceSellerValidator
				.validateSellerId(loginUser, "2",  Arrays.asList(1,3,4));
		response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("seller"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);

		when(userInfoServiceMock.getUserById(Mockito.anyInt()))
		.thenReturn(createBuyerAdminUser());
		result = webServiceSellerValidator
				.validateSellerId(loginUser, "2",  Arrays.asList(1,3,4));
		response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("seller"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);

	}
	
	
	@Test
	public void testValidateSellerIdInvalidDP(){
		//login user = seller, sellerid != loginuser
		
		User loginUser = createSellerUser();
		when(userInfoServiceMock.getUserById(Mockito.anyInt()))
				.thenReturn(createSellerUser());
		Map<String, Object> result = webServiceSellerValidator
				.validateSellerId(loginUser, "3",  Arrays.asList(1,3,4));
		WSSellerResponse response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("seller"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);

		
		loginUser = createSellerAdminUser();
		when(userInfoServiceMock.getUserById(Mockito.anyInt()))
				.thenReturn(createSellerUser());
		when(messageI18NServiceMock.getPropertyMessage(anyString()))
		.thenReturn(anyString());
		result = webServiceSellerValidator
				.validateSellerId(loginUser, "3",  Arrays.asList(1,2,4));
		response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("seller"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);

		
		
	}
	@DataProvider(name = "validSKUGroupName")
	private Object[][] createvalidSKUGroupName() {
		return new Object[][] { { "afsfs" }, { "af234s" },{ "1234" }
			
		};
	}
	
	@Test(dataProvider="validSKUGroupName")
	public void validateSkuGroupNameTest(String grpName){
		WSSellerResponse response = webServiceSellerValidator
				.validateSkuGroupName(grpName);
		Assert.assertNull(response);
		
		
	}
	@DataProvider(name = "invalidSKUGroupName")
	private Object[][] createInvalidSKUGroupName() {
		return new Object[][] { {"" }, { null },{ LONGSTRING }
			
		};
	}
	
	@Test(dataProvider="invalidSKUGroupName")
	public void validateSkuGroupNameFailedTest(String grpName){
		WSSellerResponse response = webServiceSellerValidator
				.validateSkuGroupName(grpName);
		Assert.assertNotNull(response);
		
		
	}
	@DataProvider(name = "invalidQtyStr")
	private Object[][] createInvalidQtyStr() {
		return new Object[][] { {"adfs" }, { "dhjk893" }
			
		};
	}
	@Test(dataProvider = "validWholeNumbers")
	public void validateMaxQtyStrHappyFlow(String mqty) {
		WSSellerResponse result = webServiceSellerValidator.validateMaxQtyString(mqty);
		Assert.assertNull(result);
	}
	@Test(dataProvider = "invalidQtyStr")
	public void validateMaxQtyWithError(String mqty) {
		WSSellerResponse result = webServiceSellerValidator.validateMaxQtyString(mqty);
		Assert.assertNotNull(result);
	}
	
	
	@DataProvider(name = "validMaxQty")
	private Object[][] createValidMaxQty() {
		return new Object[][] { {new BigDecimal("21"), "c/S" }, 
				{  new BigDecimal ("190928877"), "c/S"},
				{  new BigDecimal ("0"), "c/S"}
		};
	}
	@Test(dataProvider = "validMaxQty")
	public void validateMaxQtyHappyFlow(BigDecimal mqty, String uom) {
		WSSellerResponse result = webServiceSellerValidator.validateMaxQty(mqty,uom);
		Assert.assertNull(result);
	}
	
	@DataProvider(name = "invalidMaxQty")
	private Object[][] createInvalidMaxQty() {
		return new Object[][] { {new BigDecimal("-21"),"c/s" }, 
				{  new BigDecimal ("19092887723423"), "c/s"}		};
	}
	@Test(dataProvider = "invalidMaxQty")
	public void validateMaxQtyFailed(BigDecimal mqty, String uom) {
		WSSellerResponse result = webServiceSellerValidator.validateMaxQty(mqty,uom);
		Assert.assertNotNull(result);
	}
	
	@DataProvider(name = "validBuyers")
	private Object[][] createValidBuyers() {
		return new Object[][] { {createBuyerAdminUser() }, 
				{  createBuyerUser()}		};
	}

	
	@DataProvider(name = "validSellers")
	private Object[][] createValidSellers() {
		return new Object[][] { {createSellerAdminUser() }, 
				{  createSellerUser()}		};
	}
	
	@Test
	public void validateSellerIdListSellerAdminOK(){
		
		when(dealingPatternServiceMock.getMembersByAdminId(anyInt(),anyInt(), anyString(), anyString())).thenReturn(createSellerUserList(5));
		
		
		
		Map<String, Object>  result = webServiceSellerValidator.validateSellerIdList(createSellerAdminUser(), Arrays.asList("1","4"), null);
		WSSellerResponse response = (WSSellerResponse) result.get("response");
		Assert.assertNull(response);
		Assert.assertNotNull(result.get("sellerIdList"));
		
	}
	
	private List<User> createSellerUserList(int size){
		List<User> users = new ArrayList<User>();
		for(int i = 0; i<size; i++){
			User user = new User();
			user.setUserId(i+1);
			users.add(user);
		}
		
		return users;
		
	}
	
	@Test
	public void validateSellerIdListSellerAdminRequiredSellers(){
		
		when(dealingPatternServiceMock.getMembersByAdminId(anyInt(),anyInt(), anyString(), anyString())).thenReturn(createSellerUserList(5));
		
		
		
		Map<String, Object>  result = webServiceSellerValidator.validateSellerIdList(createSellerAdminUser(), new ArrayList<String>(), null);
		WSSellerResponse response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("sellerIdList"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.REQUIRED_ERR_CODE);
		
		result = webServiceSellerValidator.validateSellerIdList(createSellerAdminUser(),null, null);
		response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("sellerIdList"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.REQUIRED_ERR_CODE);

	}
	
	@Test
	public void validateSellerIdListSellerAdminInvalidSellers(){
		
		when(dealingPatternServiceMock.getMembersByAdminId(anyInt(),anyInt(), anyString(), anyString())).thenReturn(createSellerUserList(5));
		when(messageI18NServiceMock.getPropertyMessage(anyString())).thenReturn(WebServiceConstants.INVALID_SELLER_DEALINGPATTERN_ERR_MSG);
		
		
		Map<String, Object>  result = webServiceSellerValidator.validateSellerIdList(createSellerAdminUser(), Arrays.asList("2", "8"), null);
		WSSellerResponse response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("sellerIdList"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);
		
		result = webServiceSellerValidator.validateSellerIdList(createSellerAdminUser(), Arrays.asList("2", "d8", "dg", "2.3", "-9"), null);
		response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("sellerIdList"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);
		

	}
	
	@Test
	public void validateSellerIdListSellerOK(){
		
		when(dealingPatternServiceMock.getMembersByAdminId(anyInt(),anyInt(), anyString(), anyString())).thenReturn(createSellerUserList(5));
		
		User loginUser = createSellerUser();
		loginUser.setUserId(15);
		
		Map<String, Object>  result = webServiceSellerValidator.validateSellerIdList(loginUser, null, null);
		WSSellerResponse response = (WSSellerResponse) result.get("response");
		Assert.assertNull(response);
		Assert.assertNotNull(result.get("sellerIdList"));

		result = webServiceSellerValidator.validateSellerIdList(loginUser, Arrays.asList("15"), null);
		response = (WSSellerResponse) result.get("response");
		Assert.assertNull(response);
		Assert.assertNotNull(result.get("sellerIdList"));

		
	}
	
	@Test
	public void validateSellerIdListSellerWithError(){
		
		when(dealingPatternServiceMock.getMembersByAdminId(anyInt(),anyInt(), anyString(), anyString())).thenReturn(createSellerUserList(5));
		
		User loginUser = createSellerUser();
		loginUser.setUserId(15);
		
		Map<String, Object>  result = webServiceSellerValidator.validateSellerIdList(loginUser, Arrays.asList( "12"), null);
		WSSellerResponse response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("sellerIdList"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);

		result = webServiceSellerValidator.validateSellerIdList(loginUser, Arrays.asList("15", "12"), null);
		response = (WSSellerResponse) result.get("response");
		Assert.assertNotNull(response);
		Assert.assertNull(result.get("sellerIdList"));
		Assert.assertEquals(response.getResultCode(), WebServiceConstants.INVALID_ERR_CODE);

	}
}
