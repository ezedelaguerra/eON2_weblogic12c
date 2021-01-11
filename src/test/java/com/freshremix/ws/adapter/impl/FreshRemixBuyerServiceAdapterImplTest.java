package com.freshremix.ws.adapter.impl;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ws.freshremix.validator.WebServiceBuyerValidator;

import com.freshremix.constants.WebServiceConstants;
import com.freshremix.model.Category;
import com.freshremix.model.Company;
import com.freshremix.model.EONUserPreference;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.Role;
import com.freshremix.model.User;
import com.freshremix.model.WSBuyerAddOrderSheetDetails;
import com.freshremix.model.WSBuyerAddOrderSheetRequest;
import com.freshremix.model.WSBuyerAddOrderSheetResponse;
import com.freshremix.model.WSBuyerLoadOrderRequest;
import com.freshremix.model.WSBuyerLoadOrderResponse;
import com.freshremix.model.WSBuyerResponse;
import com.freshremix.model.WSBuyerSKUAdd;
import com.freshremix.model.WSBuyerSKUUpdate;
import com.freshremix.service.BuyerOrderSheetService;
import com.freshremix.service.CategoryService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.MessageI18NService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.ui.model.TableParameter;

public class FreshRemixBuyerServiceAdapterImplTest{

	
	@Mock
	private BuyerOrderSheetService buyerOrderSheetService;
	@Mock	
	private OrderSheetService orderSheetService;
	@Mock	
	private UserPreferenceService userPreferenceService;
	@Mock	
	private CategoryService categoryService;
	@Mock
	private MessageI18NService messageI18NService;
	FreshRemixBuyerServiceAdapterImpl impl;
	@Mock
	private WebServiceBuyerValidator webServiceBuyerValidator;
	@Mock
	private UsersInformationService userInfoService;
	@Mock
	private DealingPatternService dealingPatternService;


	@BeforeMethod
	public void init(){
		MockitoAnnotations.initMocks(this);
		impl = new FreshRemixBuyerServiceAdapterImpl();
		impl.setBuyerOrderSheetService(buyerOrderSheetService);
		impl.setOrderSheetService(orderSheetService);
		impl.setUserPreferenceService(userPreferenceService);
		impl.setMessageI18NService(messageI18NService);
		impl.setWebServiceBuyerValidator(webServiceBuyerValidator);
		impl.setUserInfoService(userInfoService);
		impl.setDealingPatternService(dealingPatternService);
	}

	@AfterMethod
	public void afterMethod(){
		Mockito.reset(buyerOrderSheetService, orderSheetService,
				userPreferenceService, categoryService, messageI18NService,
				webServiceBuyerValidator, userInfoService);
	}

	public WSBuyerLoadOrderRequest createRequest() throws ParseException {
		WSBuyerLoadOrderRequest request = new WSBuyerLoadOrderRequest();
		request.setUsername("ba_ca01");
		request.setPassword("1");
		//request.setViewAllDates(false);
		request.setPageSize(120);
		request.setPageNum(1);
		List<Integer> buyers = new ArrayList<Integer>();
		buyers.add(14);
		buyers.add(15);
		//buyers.add(16);
		List<Integer> sellers = new ArrayList<Integer>();
		sellers.add(2);
		sellers.add(3);
		sellers.add(4);
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyy");
		request.setCategory("Fruits");
		request.setBuyerIds(buyers);
		request.setSellerIds(sellers);
		request.setOrderDate(sdf.parse("031713"));
		return request;
	}
	
	
	public WSBuyerAddOrderSheetRequest createAddRequest()  throws ParseException {
		WSBuyerAddOrderSheetRequest request = new WSBuyerAddOrderSheetRequest();
		request.setUsername("ba_ca01");
		request.setPassword("1");
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyy");
		request.setOrderDate(sdf.parse("031713"));
		request.setSkuCategoryName("Fruits");
		request.setUpdateSKUList(createWSBuyerSKUUpdate());
		//request.setAddSkuList(createWSBuyerSKUAdd());

		return request;
		
	}
	private List<Order> createOrderList()
	{
		List<Order> list = new ArrayList<Order>();
		list.add(createOrder(15, 2));
		list.add(createOrder(15, 3));
		list.add(createOrder(15, 4));
		list.add(createOrder(14, 2));
		list.add(createOrder(14, 3));
		list.add(createOrder(14, 4));
		return list;
	}
	
	private Order createOrder(Integer buyerId, Integer sellerId){
		Order order = new Order();
		order.setSellerId(sellerId);
		order.setBuyerId(buyerId);
		order.setDeliveryDate("20130401");
		return order;
	}
	
	private ArrayList<WSBuyerSKUUpdate> createWSBuyerSKUUpdate(){
		WSBuyerSKUUpdate sku = new WSBuyerSKUUpdate();
		sku.setSkuId(12);
		sku.setSellerId(1);
		sku.setB_purchasePrice(new BigDecimal(10));
		sku.setB_sellingPrice(new BigDecimal(12));
		sku.setB_sellingUomName("C/S");
		sku.setB_skuComment("comment");
		sku.setDetails(createSKUDetails());
	 
		
		ArrayList<WSBuyerSKUUpdate> update = new ArrayList<WSBuyerSKUUpdate>();
		update.add(sku);
		return update;
	}
	
	
	
	
	
	
	private	ArrayList< WSBuyerAddOrderSheetDetails> createSKUDetails(){
		ArrayList<WSBuyerAddOrderSheetDetails> list = new ArrayList<WSBuyerAddOrderSheetDetails>();
		WSBuyerAddOrderSheetDetails d = new WSBuyerAddOrderSheetDetails();
		d.setBuyerId(1);
		list.add(d);
		
		
		return list;
	}
	
	private WSBuyerSKUAdd createWSBuyerSKUAdd() {
		WSBuyerSKUAdd sku = new WSBuyerSKUAdd();
		sku.setSellerId(1);
		sku.setB_purchasePrice(new BigDecimal(10));
		sku.setB_sellingPrice(new BigDecimal(12));
		sku.setB_sellingUomName("C/S");
		sku.setB_skuComment("comment");
		sku.setSkuName("testsku");
		sku.setPackageQuantity(new BigDecimal(10));
		sku.setPackageType("packtype");
		sku.setDetails(createSKUDetails());
		sku.setCenter(new BigDecimal("1"));
		sku.setDelivery("Delivery");
		sku.setSale("Sale");
		sku.setJan("JAN");
		sku.setPackFee("packFee");
		sku.setColumn01("1");
		sku.setColumn02("1");
		sku.setColumn03("1");
		sku.setColumn04("1");
		sku.setColumn05("1");
		sku.setColumn06("1");
		sku.setColumn07("1");
		sku.setColumn08("1");
		sku.setColumn09("1");
		sku.setColumn10("1");
		sku.setColumn11("1");
		sku.setColumn12("1");
		sku.setColumn13("1");
		sku.setColumn14("1");
		sku.setColumn15("1");
		return sku;
	}

	private Map<String, Object> createOrderResultMap()
	{
		List<Map<String, Object>> skuList =new  ArrayList<Map<String,Object>>();
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("profitInfo", "{\"priceWithoutTax\":300,\"priceWithTax\":316,\"sellingPrice\":0,\"profit\":0,\"priceTaxOpt\":\"1\"}");
		String lock = "{\"sku\":\"1\",\"Q_14\":\"1\",\"V_14\":\"1\",\"CQ_14\":\"1\",\"Q_15\":\"1\",\"V_15\":\"1\",\"CQ_15\":\"1\",\"B_purchasePrice\":\"1\",\"B_sellingPrice\":\"1\",\"B_sellingUom\":\"1\",\"B_skuComment\":\"1\",\"buyerCols\":\"1\",\"sellername\":\"1\"}";
		res.put("lockflag",lock);
		skuList.add(res);
		skuList.add(res);
		skuList.add(res);
		skuList.add(res);
		skuList.add(res);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", skuList);
		return result;
	}


	private Map<String, Object> createValidateUserResult(String userName, Integer userId,  WSBuyerResponse response)
	{
		Map<String, Object> result = new HashMap<String, Object>(); 
		if(userName!=null){
			result.put("user", createBuyerAdminUser(userName, userId));

		}
		result.put("response", response);
		
		return result;
	}
	
	private User createBuyerAdminUser(String userName, Integer userId){
		return createUser(userName, userId, 4);
	}
	
	private User createBuyerUser(String userName, Integer userId){
		return createUser(userName, userId, 3);
	}
	private User createSellerUser() {
		return createUser("s_ca01", 1, 1);

	}

	private User createSellerAdminUser() {
		return createUser("sa_ca01", 1, 2);

	}
	private User createUser(String userName, Integer userId, Integer roleId) {
		
		Role role = new Role();
		role.setRoleId(new Long(roleId));
		role.setBuyerAdminFlag("1");
		User user = new User();
		user.setUserName(userName);
		Company company = new Company();
		company.setCompanyId(2);
		company.setCompanyName("FRASC_BA");
		user.setCompany(company);
		user.setUserId(userId);
		user.setRole(role);
		
		return user;
	}
	
	private List<User> createBuyerDetailList() {
		return Arrays.asList(createBuyerUser("b_ca01", 14), createBuyerUser("b_ca02", 15));
	}
	
	private Category createCategory() {
		Category category = new Category();
		category.setCategoryId(1);
		category.setDescription("Fruits");
		return category;
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void loadOrdersHappyFlow() throws Exception{

		Map<String, Object> mapresult = new HashMap<String, Object>();
		Category category = new Category();
		category.setCategoryId(1);
		category.setDescription("Fruits");
		mapresult.put("category",category);
		mapresult.put("user",createBuyerAdminUser("b_ca01", 1));
		when(userPreferenceService.getUserPreference(any(User.class))).thenReturn(new EONUserPreference());
		when(buyerOrderSheetService.getPublishedOrders(anyList() ,anyList(),anyList(), anyInt() ,anyString())).thenReturn(createOrderList());
		when(orderSheetService.getSelectedOrderIds(anyMap(), any( OrderSheetParam.class))).thenReturn(new ArrayList<Integer>());
		when(buyerOrderSheetService.getOrders(any(OrderSheetParam.class), any(User.class), any(TableParameter.class), anyList(), anyList(), anyMap())).thenReturn(createOrderResultMap());
		when(webServiceBuyerValidator.validateDates(any(Date.class), any(Date.class))).thenReturn(null);
		when(webServiceBuyerValidator.validateBuyerUser(anyString(), anyString())).thenReturn(createValidateUserResult("ba_ca01", 17,null));
		when(webServiceBuyerValidator.validateCategoryName(anyString(), anyInt())).thenReturn(mapresult);

		when(webServiceBuyerValidator.validateOrderMap(anyMap())).thenReturn(null);
		when(userInfoService.getUserById(anyList())).thenReturn(createBuyerDetailList());
		WSBuyerLoadOrderRequest request = createRequest();
		WSBuyerLoadOrderResponse loadOrders = impl.loadOrders(request);
		Assert.assertEquals(loadOrders.getSkuOrderList().size(), 5);
		Assert.assertEquals(loadOrders.getResult().getResultCode(), WebServiceConstants.WS_ERROR_CODE_0);
		verify(userPreferenceService).getUserPreference(any(User.class));
		verify(buyerOrderSheetService).getPublishedOrders(anyList() ,anyList(),anyList(), anyInt() ,anyString());
		verify(orderSheetService).getSelectedOrderIds(anyMap(), any( OrderSheetParam.class));
		verify(buyerOrderSheetService).getOrders(any(OrderSheetParam.class), any(User.class), any(TableParameter.class), anyList(), anyList(), anyMap());
		verify(webServiceBuyerValidator).validateDates(any(Date.class), any(Date.class));
		verify(webServiceBuyerValidator).validateBuyerUser(anyString(), anyString());
		verify(webServiceBuyerValidator).validateOrderMap(anyMap());

	}
	

	@Test
	public void loadOrderNullRequest(){
		WSBuyerResponse response = new WSBuyerResponse();
		response.setResultCode(WebServiceConstants.REQUIRED_ERR_CODE);
		when(webServiceBuyerValidator.validateRequest(any(WSBuyerLoadOrderRequest.class))).thenReturn(response);
		WSBuyerLoadOrderResponse loadOrders = impl.loadOrders(null);
		Assert.assertEquals(loadOrders.getResult().getResultCode(), WebServiceConstants.REQUIRED_ERR_CODE);
		verify(webServiceBuyerValidator).validateRequest(any(WSBuyerLoadOrderRequest.class));
	}


	@SuppressWarnings("unchecked")
	@Test
	public void loadOrdersWithValidationError()throws Exception{
		when(userPreferenceService.getUserPreference(any(User.class))).thenReturn(new EONUserPreference());
		when(buyerOrderSheetService.getPublishedOrders(anyList() ,anyList(),anyList(), anyInt() ,anyString())).thenReturn(createOrderList());
		when(orderSheetService.getSelectedOrderIds(anyMap(), any( OrderSheetParam.class))).thenReturn(new ArrayList<Integer>());
		when(buyerOrderSheetService.getOrders(any(OrderSheetParam.class), any(User.class), any(TableParameter.class), anyList(), anyList(), anyMap())).thenReturn(createOrderResultMap());
		when(webServiceBuyerValidator.validateDates(any(Date.class), any(Date.class))).thenReturn(null);
		when(webServiceBuyerValidator.validateOrderMap(anyMap())).thenReturn(null);
		WSBuyerResponse wsvalr = new WSBuyerResponse();
		wsvalr.setResultCode(WebServiceConstants.USER_ERR_CODE);
		when(webServiceBuyerValidator.validateBuyerUser(anyString(), anyString())).thenReturn(createValidateUserResult("ba_ca01", 17,wsvalr));
		WSBuyerLoadOrderRequest request = createRequest();
		request.setPassword("2");
		WSBuyerLoadOrderResponse loadOrders = impl.loadOrders(request );
		Assert.assertEquals(loadOrders.getResult().getResultCode(), WebServiceConstants.USER_ERR_CODE);
		verify(webServiceBuyerValidator).validateBuyerUser(anyString(), anyString());
		verify(buyerOrderSheetService, never()).getPublishedOrders(anyList() ,anyList(),anyList(), anyInt() ,anyString());
		verify(buyerOrderSheetService, never()).getOrders(any(OrderSheetParam.class), any(User.class), any(TableParameter.class), anyList(), anyList(), anyMap());
		verify(userPreferenceService, never()).getUserPreference(any(User.class));
		verify(orderSheetService, never()).getSelectedOrderIds(anyMap(), any( OrderSheetParam.class));
		verify(webServiceBuyerValidator, never()).validateDates(any(Date.class), any(Date.class));
		verify(webServiceBuyerValidator, never()).validateOrderMap(anyMap());

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void loadOrdersTestEmptyOrderMap()throws Exception{
		Map<String, Object> mapresult = new HashMap<String, Object>();
		Category category = new Category();
		category.setCategoryId(1);
		category.setDescription("Fruits");
		mapresult.put("category",category);
		mapresult.put("user",createBuyerAdminUser("b_ca01", 1));
		when(userPreferenceService.getUserPreference(any(User.class))).thenReturn(new EONUserPreference());
		when(buyerOrderSheetService.getPublishedOrders(anyList() ,anyList(),anyList(), anyInt() ,anyString())).thenReturn(createOrderList());
		when(orderSheetService.getSelectedOrderIds(anyMap(), any( OrderSheetParam.class))).thenReturn(new ArrayList<Integer>());
		when(buyerOrderSheetService.getOrders(any(OrderSheetParam.class), any(User.class), any(TableParameter.class), anyList(), anyList(), anyMap())).thenReturn(null);
		when(webServiceBuyerValidator.validateDates(any(Date.class), any(Date.class))).thenReturn(null);
		when(webServiceBuyerValidator.validateBuyerUser(anyString(), anyString())).thenReturn(createValidateUserResult("ba_ca01", 17,null));
		when(webServiceBuyerValidator.validateCategoryName(anyString(), anyInt())).thenReturn(mapresult);

		WSBuyerResponse wsvalr = new WSBuyerResponse();
		wsvalr.setResultCode(WebServiceConstants.ZERO_RESULTS_CODE);
		when(webServiceBuyerValidator.validateOrderMap(anyMap())).thenReturn(wsvalr);
		WSBuyerLoadOrderRequest request = createRequest();
		WSBuyerLoadOrderResponse loadOrders = impl.loadOrders(request);
		Assert.assertEquals(loadOrders.getResult().getResultCode(), WebServiceConstants.ZERO_RESULTS_CODE);
		verify(userPreferenceService).getUserPreference(any(User.class));
		verify(buyerOrderSheetService).getPublishedOrders(anyList() ,anyList(),anyList(), anyInt() ,anyString());
		verify(orderSheetService).getSelectedOrderIds(anyMap(), any( OrderSheetParam.class));
		verify(buyerOrderSheetService).getOrders(any(OrderSheetParam.class), any(User.class), any(TableParameter.class), anyList(), anyList(), anyMap());
		verify(webServiceBuyerValidator).validateDates(any(Date.class), any(Date.class));
		verify(webServiceBuyerValidator).validateBuyerUser(anyString(), anyString());
		verify(webServiceBuyerValidator).validateOrderMap(anyMap());

	}
	
	@Test
	public void testLoadOrdersWithDateErrors() throws Exception {
		
		WSBuyerResponse buyerResponse = new WSBuyerResponse();
		when(webServiceBuyerValidator.validateDates(Mockito.any(Date.class), 
				Mockito.any(Date.class))).thenReturn(buyerResponse);
		WSBuyerLoadOrderRequest request = createRequest();
		WSBuyerLoadOrderResponse response = impl.loadOrders(request);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getResult(), buyerResponse);
		verify(webServiceBuyerValidator).validateDates(Mockito.any(Date.class), Mockito.any(Date.class));
		
	}	
	
	@Test
	public void testLoadOrdersWithSellerIdErrors() throws Exception {
		
		WSBuyerResponse buyerResponse = new WSBuyerResponse();
		when(webServiceBuyerValidator.validateSellerIdList(Mockito.anyListOf(Integer.class))).thenReturn(buyerResponse);
		WSBuyerLoadOrderRequest request = createRequest();
		WSBuyerLoadOrderResponse response = impl.loadOrders(request);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getResult(), buyerResponse);
		verify(webServiceBuyerValidator).validateSellerIdList(Mockito.anyListOf(Integer.class));

	}

	@DataProvider(name="buyerIdDataProvider")
	public Object[][] buyerIdDataProvider(){
		return new Object[][] {
				{null},
				{new ArrayList<Integer>()},
				{Collections.emptyList()}
		};
	}
	@Test(dataProvider="buyerIdDataProvider")
	public void testLoadOrdersWithBuyerIdErrors(List<Integer> buyerIds) throws Exception {
		
		Map<String, Object> userValidationResponse = createValidateUserResult("ba_ca01", 17, null);
		when(webServiceBuyerValidator.validateBuyerUser(anyString(), anyString())).thenReturn(userValidationResponse);

		WSBuyerResponse buyerResponse = new WSBuyerResponse();
		when(webServiceBuyerValidator.validateBuyerIdList(Mockito.anyListOf(Integer.class), Mockito.any(User.class))).thenReturn(buyerResponse);
		
		WSBuyerLoadOrderRequest request = createRequest();
		request.setBuyerIds(buyerIds);
		
		WSBuyerLoadOrderResponse response = impl.loadOrders(request);
		
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getResult(), buyerResponse);

		verify(webServiceBuyerValidator).validateBuyerUser(anyString(), anyString());
		verify(webServiceBuyerValidator).validateBuyerIdList(Mockito.anyListOf(Integer.class),  Mockito.any(User.class));

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void addOrdersHappyFlow() throws Exception{

		Map<String, Object> mapresult = new HashMap<String, Object>();
		mapresult.put("category",new Category());
		mapresult.put("user",createBuyerAdminUser("b_ca01", 1));
		when(webServiceBuyerValidator.validateBuyerUser(anyString(),anyString())).thenReturn(mapresult);
		when(webServiceBuyerValidator.validateCategoryName(anyString(), anyInt())).thenReturn(mapresult);
		when(webServiceBuyerValidator.validateOrderDate(any(Date.class))).thenReturn(null);
		when(webServiceBuyerValidator.validateAddOrderSheetLists(any(User.class), anyList(), anyList())).thenReturn(null);
		when(webServiceBuyerValidator.validateWSSKUFieldsforInsert(any(User.class),any(Date.class), any(WSBuyerSKUAdd.class), any(Category.class))).thenReturn(null);
		when(webServiceBuyerValidator.validateWSSKUFieldsforUpdate(any(User.class),any(Date.class), any(WSBuyerSKUUpdate.class), anyInt())).thenReturn(null);
		when(webServiceBuyerValidator.processConcurrencyCheckForWS(any(WSBuyerAddOrderSheetRequest.class))).thenReturn(null);
		//when(buyerOrderSheetService.saveWSData(any(WSBuyerAddOrderSheetRequest.class)))
		WSBuyerAddOrderSheetRequest request = createAddRequest();

		WSBuyerAddOrderSheetResponse addOrders = impl.addOrders(request);
		Assert.assertEquals(addOrders.getResult().getResultCode(), WebServiceConstants.WS_ERROR_CODE_0);
		verify(webServiceBuyerValidator).validateBuyerUser(anyString(),anyString());
		verify(webServiceBuyerValidator).validateCategoryName(anyString(), anyInt());
		verify(webServiceBuyerValidator).validateOrderDate(any(Date.class));
		verify(webServiceBuyerValidator).validateAddOrderSheetLists(any(User.class), anyList(), anyList());
		//verify(webServiceBuyerValidator).validateWSSKUFieldsforInsert(any(User.class),any(Date.class), any(WSBuyerSKUAdd.class), anyInt());
		verify(webServiceBuyerValidator).validateWSSKUFieldsforUpdate(any(User.class),any(Date.class), any(WSBuyerSKUUpdate.class), anyInt());
		verify(webServiceBuyerValidator).processConcurrencyCheckForWS(any(WSBuyerAddOrderSheetRequest.class));

	}
	

}
