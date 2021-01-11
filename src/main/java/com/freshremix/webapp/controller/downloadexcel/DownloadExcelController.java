/**
 * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
 * <BR>
 * <B>Description:</B><BR>
 * Describe class or interface.<BR>
 * <BR>
 * <B>Known Bugs:</B>
 * none<BR>
 * <BR>
 * <B>History:</B>
 * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
 * date       name            changes
 * ------------------------------------------------------------------------------
 * Apr 20, 2010		nvelasquez		
 */
package com.freshremix.webapp.controller.downloadexcel;

import static com.freshremix.constants.SystemConstants.EON_HEADER_NAMES;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.CategoryConstants;
import com.freshremix.constants.SKUColumnConstants;
import com.freshremix.constants.SKUColumnConstantsExcel;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.AkadenSheetParams;
import com.freshremix.model.BillingItem;
import com.freshremix.model.DownloadExcelSheet;
import com.freshremix.model.EONHeader;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderReceived;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.OrderUnit;
import com.freshremix.model.SKU;
import com.freshremix.model.SKUBA;
import com.freshremix.model.User;
import com.freshremix.service.AkadenService;
import com.freshremix.service.AllocationSheetService;
import com.freshremix.service.BillingSheetService;
import com.freshremix.service.BuyerOrderSheetService;
import com.freshremix.service.DownloadExcelService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.ReceivedSheetService;
import com.freshremix.ui.model.DownloadExcelSettingForm;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.DownloadExcelUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.SortingUtil;
import com.freshremix.util.UserUtil;

/**
 * @author nvelasquez
 *
 */
public class DownloadExcelController implements 
	Controller, ServletContextAware {
	
	private Map<Integer, User> validSellerMap = new HashMap<Integer, User>();
	private String sheetOption = "";
	
	private ReceivedSheetService receivedSheetService;
	private OrderSheetService orderSheetService;
	private AllocationSheetService allocationSheetService;
	private AkadenService akadenService;
	private BuyerOrderSheetService buyerOrderSheetService;
	private BillingSheetService billingSheetService;
	private DownloadExcelService downloadExcelService;
	private OrderUnitService orderUnitService;
	private ServletContext servletContext;
	
	public void setReceivedSheetService(ReceivedSheetService receivedSheetService) {
		this.receivedSheetService = receivedSheetService;
	}
	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}
	public void setAllocationSheetService(AllocationSheetService allocationSheetService) {
		this.allocationSheetService = allocationSheetService;
	}
	public void setAkadenService(AkadenService akadenService) {
		this.akadenService = akadenService;
	}
	public void setBuyerOrderSheetService(BuyerOrderSheetService buyerOrderSheetService) {
		this.buyerOrderSheetService = buyerOrderSheetService;
	}
	public void setBillingSheetService(BillingSheetService billingSheetService) {
		this.billingSheetService = billingSheetService;
	}
	public void setDownloadExcelService(DownloadExcelService downloadExcelService) {
		this.downloadExcelService = downloadExcelService;
	}

	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}	
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		System.out.println("downloading excel...");
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		String json = request.getParameter("_dxls_json");
		//System.out.println("json:[" + json + "]");
		Serializer serializer = new JsonSerializer();
		DownloadExcelSettingForm dxls = (DownloadExcelSettingForm) serializer.deserialize(json, DownloadExcelSettingForm.class);
		
//		System.out.println("dxls:[" + dxls.toString() + "]");
		/* session */
		SessionHelper.setAttribute(request, SessionParamConstants.DL_XLS_PARAM, dxls);
		downloadExcelService.insertExcelSetting(user.getUserId(), dxls);
		
		List<Order> allOrders =  getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		Map<String, Order> allOrdersMap = OrderSheetUtil.convertToOrderMap(allOrders);
//		List<Integer> includedOrders = this.getIncludedOrders(request);
		//
		
		/* get parameters */
		String startDate = "";
		String endDate = "";
//		String deliveryDate = "";
//		String selectedSellerCompany = "";
//		List<Integer> sellerIds = null;
//		String selectedBuyerCompany = "";
//		List<Integer> buyerIds = null;
		Integer categoryId = null;
		Integer sheetTypeId = null;
//		boolean isAllDatesView = false;
//		Integer datesViewBuyerId = null;
		OrderSheetParam osParam = new OrderSheetParam();
		AkadenSheetParams akParam = new AkadenSheetParams();
		
		Object obj = request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		
		if (obj instanceof OrderSheetParam) {
			osParam = (OrderSheetParam) obj;
		}
		else if (obj instanceof AkadenSheetParams) {
			akParam = (AkadenSheetParams) obj;
			
			osParam.setStartDate(akParam.getStartDate());
			osParam.setEndDate(akParam.getEndDate());
			osParam.setSelectedDate(akParam.getSelectedDate());
			osParam.setSelectedSellerCompany(akParam.getSelectedSellerCompany());
			osParam.setSelectedSellerID(akParam.getSelectedSellerID());
			osParam.setSelectedBuyerCompany(akParam.getSelectedBuyerCompany());
			osParam.setSelectedBuyerID(akParam.getSelectedBuyerID());
			osParam.setCategoryId(akParam.getCategoryId());
			osParam.setSheetTypeId(akParam.getSheetTypeId());
			osParam.setAllDatesView(akParam.isAllDatesView());
			osParam.setDatesViewBuyerID(akParam.getDatesViewBuyerID());
		}
		
		startDate = osParam.getStartDate();
		endDate = osParam.getEndDate();
//		deliveryDate = osParam.getSelectedDate();
//		selectedSellerCompany = osParam.getSelectedSellerCompany();
//		sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
//		selectedBuyerCompany = osParam.getSelectedBuyerCompany();
//		buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		categoryId = osParam.getCategoryId();
		//sheetTypeId = osParam.getSheetTypeId();
		sheetTypeId = Integer.parseInt(dxls.getSheetTypeId());
		osParam.setSheetTypeId(sheetTypeId);
//		isAllDatesView = osParam.isAllDatesView();
//		datesViewBuyerId = new Integer(osParam.getDatesViewBuyerID());
		
//		System.out.println("startDate:[" + startDate + "]");
//		System.out.println("endDate:[" + endDate + "]");
//		System.out.println("deliveryDate:[" + deliveryDate + "]");
//		System.out.println("selectedSellerCompany:[" + selectedSellerCompany + "]");
//		System.out.println("sellerIds:[" + sellerIds + "]");
//		System.out.println("selectedBuyerCompany:[" + selectedBuyerCompany + "]");
//		System.out.println("buyerIds:[" + buyerIds + "]");
//		System.out.println("categoryId:[" + categoryId + "]");
//		System.out.println("sheetTypeId:[" + sheetTypeId + "]");
//		System.out.println("isAllDatesView:[" + isAllDatesView + "]");
//		System.out.println("datesViewBuyerId:[" + datesViewBuyerId + "]");
		
		/* set sheet option*/
		this.setSheetOption(dxls);
		//
		
		//
		List<DownloadExcelSheet> xlSheets = new ArrayList<DownloadExcelSheet>();
		if (this.sheetOption.equals("D1S1B1"))
			xlSheets = this.D1S1B1(user, osParam, dxls, allOrdersMap);
		else if (this.sheetOption.equals("D1S1B9"))
			xlSheets = this.D1S1B9(user, osParam, dxls, allOrdersMap);
		else if (this.sheetOption.equals("D1S9B1"))
			xlSheets = this.D1S9B1(user, osParam, dxls, allOrdersMap);
		else if (this.sheetOption.equals("D1S9B9"))
			xlSheets = this.D1S9B9(user, osParam, dxls, allOrdersMap);
		else if (this.sheetOption.equals("D9S1B1"))
			xlSheets = this.D9S1B1(user, osParam, dxls, allOrdersMap);
		else if (this.sheetOption.equals("D9S1B9"))
			xlSheets = this.D9S1B9(user, osParam, dxls, allOrdersMap);
		else if (this.sheetOption.equals("D9S9B1"))
			xlSheets = this.D9S9B1(user, osParam, dxls, allOrdersMap);
		else if (this.sheetOption.equals("D9S9B9"))
			xlSheets = this.D9S9B9(user, osParam, dxls, allOrdersMap);
		//
	    
	    Map<String, Object> model = new HashMap<String, Object>();
	    model.put("xlSheets", xlSheets);
		model.put("xlSettings", dxls);
		model.put("eONSheetName", this.getEONSheetName(sheetTypeId));
		model.put("categoryId", categoryId);
		model.put("User", user);
		model.put("startDate", startDate);
		model.put("endDate", endDate);
	    //ModelAndView mav = new ModelAndView(new DownloadExcelView(), model);
	    ModelAndView mav = new ModelAndView("excel", model);
	    //mav.setViewName("excel");
//	    System.out.println("view name:[" + mav.getViewName() + "]");
	    
	    return mav;
	}
	
	private List<Map<String, Object>> getSkuOrderMaps(List<Integer> includedOrders,
			Integer categoryId, Integer sheetTypeId, List<Integer> sellerIds, List<Integer> buyerIds,
			List<String> deliveryDates, boolean isAllDatesView, User user, String hasQty) {
		
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		
		if (includedOrders.size() == 0) includedOrders.add(999999999);
		//System.out.println("sheetTypeId:[" + sheetTypeId + "]");
		if (sheetTypeId.equals(SheetTypeConstants.SELLER_ORDER_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ORDER_SHEET)) {
			skuOrderMaps = this.loadSellerOrderSheetSkuObjMap(includedOrders, categoryId, sheetTypeId,
					sellerIds, buyerIds, deliveryDates, isAllDatesView, user, hasQty);
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ALLOCATION_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ALLOCATION_SHEET)) {
			skuOrderMaps = this.loadAllocSheetSkuObjMap(includedOrders, categoryId, sheetTypeId,
					sellerIds, buyerIds, deliveryDates, isAllDatesView, user, hasQty);
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_AKADEN_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_AKADEN_SHEET)) {
			skuOrderMaps = this.loadAkadenSheetSkuObjMap(includedOrders, categoryId, sheetTypeId,
					buyerIds, deliveryDates, isAllDatesView, user);
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_BILLING_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_BILLING_SHEET)) {
			skuOrderMaps = this.loadBillingSheetSkuObjMap(includedOrders, categoryId, sheetTypeId,
					buyerIds, deliveryDates, isAllDatesView, user);
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET)) {
			skuOrderMaps = this.loadBuyerOrderSheetSkuObjMap(includedOrders, categoryId, sheetTypeId,
					sellerIds, buyerIds, deliveryDates, isAllDatesView, null, user, hasQty);
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ORDER_SHEET)) {
			skuOrderMaps = this.loadBuyerOrderSheetSkuObjMap(includedOrders, categoryId, sheetTypeId,
					sellerIds, buyerIds, deliveryDates, isAllDatesView, user.getUserId(), user, hasQty);
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_RECEIVED_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.BUYER_RECEIVED_SHEET)) {
			skuOrderMaps = this.loadReceivedSheetSkuObjMap(includedOrders, categoryId, sheetTypeId,
					sellerIds, buyerIds, deliveryDates, isAllDatesView, user, hasQty);
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_BILLING_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.BUYER_BILLING_SHEET)) {
			skuOrderMaps = this.loadBuyerBillingSheetSkuObjMap(includedOrders, categoryId, sheetTypeId,
					buyerIds, deliveryDates, isAllDatesView, user);
		}
//		System.out.println("skuOrderMaps size:[" + skuOrderMaps.size() + "]");
		return skuOrderMaps;
	}
	
	/* start get sku obj maps */
	private List<Map<String, Object>> loadSellerOrderSheetSkuObjMap(List<Integer> includedOrders,
			Integer categoryId, Integer sheetTypeId, List<Integer> sellerIds, List<Integer> buyerIds,
			List<String> deliveryDates, boolean isAllDatesView, User user, String hasQty) {
		
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		if (includedOrders.size() == 0) includedOrders.add(999999999);
		List<SKU> skuObjs = orderSheetService.getDistinctSKUs(includedOrders, sellerIds, buyerIds, deliveryDates, categoryId, sheetTypeId, hasQty);
		SortingUtil.sortSKUs(user, skuObjs, Integer.valueOf(categoryId));
		
		Map<Integer, Map<String, Map<Integer, OrderItem>>> sellerOrderItemMap = 
			new HashMap<Integer,Map<String,Map<Integer,OrderItem>>>();
		try {
			if (skuObjs.size() > 0)
				sellerOrderItemMap = orderSheetService.getSellerOrderItems(OrderSheetUtil.getSkuIds(skuObjs),
						deliveryDates, buyerIds, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<Integer, SKU> skuObjMap = new HashMap<Integer, SKU>();
		for (SKU skuObj : skuObjs) {
			skuObjMap.put(skuObj.getSkuId(), skuObj);
			Map<String, Object> skuOrderMap = this.populateSkuObjMap(skuObj);
			
			if (isAllDatesView)
				//orderSheetService.loadSumOrderItemQuantitiesAllDates(skuOrderMap, deliveryDates, includedOrders, skuObj);
				this.prepareSumOrderItemAllDates(skuOrderMap, buyerIds, deliveryDates, skuObj, sellerOrderItemMap);
			else
				//orderSheetService.loadSumOrderItemQuantitiesAllBuyers(skuOrderMap, buyerIds, includedOrders, skuObj);
				this.prepareSumOrderItemAllBuyers(skuOrderMap, buyerIds, deliveryDates, skuObj, sellerOrderItemMap);
				
			skuOrderMaps.add(skuOrderMap);
		}
		
		return skuOrderMaps;
		
	}
	
	private List<Map<String, Object>> loadAllocSheetSkuObjMap(List<Integer> includedOrders,
			Integer categoryId, Integer sheetTypeId, List<Integer> sellerIds, List<Integer> buyerIds,
			List<String> deliveryDates, boolean isAllDatesView, User user, String hasQty) {
		
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		if (includedOrders.size() == 0) includedOrders.add(999999999);
		List<SKU> skuObjs = allocationSheetService.getDistinctSKUs(includedOrders, sellerIds, buyerIds, deliveryDates, categoryId, hasQty);
		SortingUtil.sortSKUs(user, skuObjs, Integer.valueOf(categoryId));
		
		Map<Integer, Map<String, Map<Integer, OrderItem>>> sellerAllocItemMap = 
			new HashMap<Integer,Map<String,Map<Integer,OrderItem>>>();
		try {
			if (skuObjs.size() > 0)
				sellerAllocItemMap = allocationSheetService.getSellerAllocItemsBulk(includedOrders,
						OrderSheetUtil.getSkuIds(skuObjs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<Integer, SKU> skuObjMap = new HashMap<Integer, SKU>();
		for (SKU skuObj : skuObjs) {
			skuObjMap.put(skuObj.getSkuId(), skuObj);
			Map<String, Object> skuOrderMap = this.populateSkuObjMap(skuObj);
			
			if (isAllDatesView)
				//allocationSheetService.loadSumAllocationQuantitiesAllDates(skuOrderMap, deliveryDates, includedOrders, skuObj);
				this.prepareSumOrderItemAllDates(skuOrderMap, buyerIds, deliveryDates, skuObj, sellerAllocItemMap);
			else
				//allocationSheetService.loadSumAllocationQuantitiesAllBuyers(skuOrderMap, buyerIds, includedOrders, skuObj);
				this.prepareSumOrderItemAllBuyers(skuOrderMap, buyerIds, deliveryDates, skuObj, sellerAllocItemMap);
			
			skuOrderMaps.add(skuOrderMap);
		}
		
		return skuOrderMaps;
		
	}
	
	private List<Map<String, Object>> loadAkadenSheetSkuObjMap(List<Integer> includedOrders,
			Integer categoryId, Integer sheetTypeId, List<Integer> buyerIds,
			List<String> deliveryDates, boolean isAllDatesView, User user) {
		
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		if (includedOrders.size() == 0) includedOrders.add(999999999);
		List<AkadenSKU> skuObjs = akadenService.getDistinctSKUs(includedOrders, categoryId,
				0, 999999);
		//SortingUtil.sortAkadenSKUs(user, skuObjs, Integer.valueOf(categoryId));
		Map<Integer, AkadenSKU> skuObjMap = new HashMap<Integer, AkadenSKU>();
		for (AkadenSKU skuObj : skuObjs) {
			skuObjMap.put(skuObj.getSkuId(), skuObj);
			Map<String, Object> skuOrderMap = this.populateAkadenSkuObjMap(skuObj, null, null);
			
			if (isAllDatesView)
				akadenService.loadSumAkadenQuantitiesAllDates(skuOrderMap, deliveryDates, includedOrders, skuObj);
			else
				akadenService.loadSumAkadenQuantitiesAllBuyers(skuOrderMap, buyerIds, includedOrders, skuObj);
			
			skuOrderMaps.add(skuOrderMap);
		}
		
		return skuOrderMaps;
		
	}
	
	private List<Map<String, Object>> loadBuyerOrderSheetSkuObjMap(List<Integer> includedOrders,
			Integer categoryId, Integer sheetTypeId, List<Integer> sellerIds, List<Integer> buyerIds,
			List<String> deliveryDates, boolean isAllDatesView, Integer isBuyer, User user, String hasQty) {
		
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		if (includedOrders.size() == 0) includedOrders.add(999999999);
		List<SKUBA> skuObjs = buyerOrderSheetService.getPublishedSKUBA(includedOrders, sellerIds, buyerIds, deliveryDates, categoryId, isBuyer, hasQty);
		SortingUtil.sortSKUs(user, skuObjs, Integer.valueOf(categoryId));
		
		Map<String, Map<String, Map<Integer, OrderItem>>> buyerOrderItemMap = 
			new HashMap<String,Map<String,Map<Integer,OrderItem>>>();
		try {
			if (skuObjs.size() > 0)
				buyerOrderItemMap = buyerOrderSheetService.getBuyerOrderItemsBulk(includedOrders,
						OrderSheetUtil.getSkuIds(skuObjs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<OrderUnit> orderUnitList = orderUnitService.getAllOrderUnit();
		Map<Integer, OrderUnit> orderUnitMap = new HashMap<Integer, OrderUnit>();

		for (OrderUnit uom: orderUnitList){
			orderUnitMap.put(uom.getOrderUnitId(), uom);
		}

		Map<Integer, OrderUnit> sellingUomMap = orderUnitService.getAllSellingUomList(); 
		
		Map<Integer, SKUBA> skuObjMap = new HashMap<Integer, SKUBA>();
		for (SKUBA skuObj : skuObjs) {
			skuObjMap.put(skuObj.getSkuId(), skuObj);
			Map<String, Object> skuOrderMap = this.populateSkuObjMap(skuObj, sellingUomMap, orderUnitMap);
			
			if (isAllDatesView)
				//buyerOrderSheetService.loadSumBuyerOrderItemQuantitiesAllDates(skuOrderMap, deliveryDates, includedOrders, skuObj, isBuyer);
				this.prepareSumBuyerOrderItemAllDates(skuOrderMap, buyerIds, deliveryDates, skuObj, buyerOrderItemMap);
			else
				//buyerOrderSheetService.loadSumBuyerOrderItemQuantitiesAllBuyers(skuOrderMap, buyerIds, includedOrders, skuObj, isBuyer);
				this.prepareSumBuyerOrderItemAllBuyers(skuOrderMap, buyerIds, deliveryDates, skuObj, buyerOrderItemMap);
			
			skuOrderMaps.add(skuOrderMap);
		}
		
		return skuOrderMaps;
		
	}
	
	private List<Map<String, Object>> loadReceivedSheetSkuObjMap(List<Integer> includedOrders,
			Integer categoryId, Integer sheetTypeId, List<Integer> sellerIds, List<Integer> buyerIds,
			List<String> deliveryDates, boolean isAllDatesView, User user, String hasQty) {
		
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		if (includedOrders.size() == 0) includedOrders.add(999999999);
		//List<SKU> skuObjs = receivedSheetService.getDistinctSKUs(includedOrders, categoryId, sheetTypeId, 0, 999999, hasQty);
		List<SKUBA> skuObjs = receivedSheetService.getDistinctSKUs(includedOrders, sellerIds, buyerIds, deliveryDates, categoryId, hasQty);
		SortingUtil.sortSKUs(user, skuObjs, Integer.valueOf(categoryId));
		
		Map<String, Map<String, Map<Integer, OrderReceived>>> buyerReceivedItemMap = 
			new HashMap<String,Map<String,Map<Integer,OrderReceived>>>();
		try {
			if (skuObjs.size() > 0)
				buyerReceivedItemMap = receivedSheetService.getReceivedItemsBulk(includedOrders,
						OrderSheetUtil.getSkuIds(skuObjs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<OrderUnit> orderUnitList = orderUnitService.getAllOrderUnit();
		Map<Integer, OrderUnit> orderUnitMap = new HashMap<Integer, OrderUnit>();

		for (OrderUnit uom: orderUnitList){
			orderUnitMap.put(uom.getOrderUnitId(), uom);
		}

		Map<Integer, OrderUnit> sellingUomMap = orderUnitService.getAllSellingUomList(); 
		
		Map<Integer, SKUBA> skuObjMap = new HashMap<Integer, SKUBA>();
		for (SKUBA skuObj : skuObjs) {
			skuObjMap.put(skuObj.getSkuId(), skuObj);
			Map<String, Object> skuOrderMap = this.populateSkuObjMap(skuObj, sellingUomMap, orderUnitMap);
			
			if (isAllDatesView)
				//receivedSheetService.loadSumOrderReceivedQuantitiesAllDates(skuOrderMap, deliveryDates, includedOrders, skuObj);
				this.prepareSumReceivedItemsAllDates(skuOrderMap, buyerIds, deliveryDates, skuObj, buyerReceivedItemMap);
			else
				//receivedSheetService.loadSumOrderReceivedQuantitiesAllBuyers(skuOrderMap, buyerIds, includedOrders, skuObj);
				this.prepareSumReceivedItemsAllBuyers(skuOrderMap, buyerIds, deliveryDates, skuObj, buyerReceivedItemMap);
			
			skuOrderMaps.add(skuOrderMap);
		}
		
		return skuOrderMaps;
		
	}
	
	private List<Map<String, Object>> loadBillingSheetSkuObjMap(List<Integer> includedOrders,
			Integer categoryId, Integer sheetTypeId, List<Integer> buyerIds,
			List<String> deliveryDates, boolean isAllDatesView, User user) {
		
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		if (includedOrders.size() == 0) includedOrders.add(999999999);
		List<AkadenSKU> skuObjs = billingSheetService.getDistinctSKUs(includedOrders, categoryId, sheetTypeId, 0, 999999);
		SortingUtil.sortAkadenSKUs(user, skuObjs, Integer.valueOf(categoryId));
		
		Map<Integer, Map<String, Map<Integer, BillingItem>>> billingItemMap = 
			new HashMap<Integer,Map<String,Map<Integer,BillingItem>>>();
		try {
			if (skuObjs.size() > 0)
				billingItemMap = billingSheetService.getBillingItemsBulk(includedOrders,
						OrderSheetUtil.getAkadenSkuIds(skuObjs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<Integer, AkadenSKU> skuObjMap = new HashMap<Integer, AkadenSKU>();
		for (AkadenSKU skuObj : skuObjs) {
			skuObjMap.put(skuObj.getSkuId(), skuObj);
			Map<String, Object> skuOrderMap = this.populateAkadenSkuObjMap(skuObj, null, null);
			
			if (isAllDatesView)
				//billingSheetService.loadSumOrderBillingQuantitiesAllDates(skuOrderMap, deliveryDates, includedOrders, skuObj);
				this.prepareSumBillingItemsAllDates(skuOrderMap, buyerIds, deliveryDates, skuObj, billingItemMap);
			else
				//billingSheetService.loadSumOrderBillingQuantitiesAllBuyers(skuOrderMap, buyerIds, includedOrders, skuObj);
				this.prepareSumBillingItemsAllBuyers(skuOrderMap, buyerIds, deliveryDates, skuObj, billingItemMap);
			
			skuOrderMaps.add(skuOrderMap);
		}
		
		return skuOrderMaps;
		
	}
	
	private List<Map<String, Object>> loadBuyerBillingSheetSkuObjMap(List<Integer> includedOrders,
			Integer categoryId, Integer sheetTypeId, List<Integer> buyerIds,
			List<String> deliveryDates, boolean isAllDatesView, User user) {
		
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		if (includedOrders.size() == 0) includedOrders.add(999999999);
		List<AkadenSKU> skuObjs = billingSheetService.getDistinctSKUBA(includedOrders, categoryId);
		SortingUtil.sortAkadenSKUs(user, skuObjs, Integer.valueOf(categoryId));
		
		Map<Integer, Map<String, Map<Integer, BillingItem>>> billingItemMap = 
			new HashMap<Integer,Map<String,Map<Integer,BillingItem>>>();
		try {
			if (skuObjs.size() > 0)
				billingItemMap = billingSheetService.getBillingItemsBulk(includedOrders,
						OrderSheetUtil.getAkadenSkuIds(skuObjs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<OrderUnit> orderUnitList = orderUnitService.getAllOrderUnit();
		Map<Integer, OrderUnit> orderUnitMap = new HashMap<Integer, OrderUnit>();

		for (OrderUnit uom: orderUnitList){
			orderUnitMap.put(uom.getOrderUnitId(), uom);
		}

		Map<Integer, OrderUnit> sellingUomMap = orderUnitService.getAllSellingUomList(); 
		
		Map<Integer, AkadenSKU> skuObjMap = new HashMap<Integer, AkadenSKU>();
		for (AkadenSKU skuObj : skuObjs) {
			skuObjMap.put(skuObj.getSkuId(), skuObj);
			Map<String, Object> skuOrderMap = this.populateAkadenSkuObjMap(skuObj, sellingUomMap, orderUnitMap);
			
			if (isAllDatesView)
				//billingSheetService.loadSumOrderBillingQuantitiesAllDates(skuOrderMap, deliveryDates, includedOrders, skuObj);
				this.prepareSumBillingItemsAllDates(skuOrderMap, buyerIds, deliveryDates, skuObj, billingItemMap);
			else
				//billingSheetService.loadSumOrderBillingQuantitiesAllBuyers(skuOrderMap, buyerIds, includedOrders, skuObj);
				this.prepareSumBillingItemsAllBuyers(skuOrderMap, buyerIds, deliveryDates, skuObj, billingItemMap);
			
			skuOrderMaps.add(skuOrderMap);
		}
		
		return skuOrderMaps;
		
	}
	/* end get sku obj maps */
	
	private void prepareSumOrderItemAllBuyers (Map<String, Object> skuOrderMap,
			List<Integer> buyerIds, List<String> deliveryDates, SKU skuObj,
			Map<Integer, Map<String, Map<Integer, OrderItem>>> orderItemMap) {
		
		Integer skuId = skuObj.getSkuId();
		for (Integer buyerId : buyerIds) {
			BigDecimal sumQuantity = new BigDecimal(0);
			String strQuantity = "";
			for (String deliveryDate : deliveryDates) {
				OrderItem orderItem = null;
				try {
					orderItem = orderItemMap.get(skuId).get(deliveryDate).get(buyerId);
				} catch (NullPointerException npe) {
					orderItem = null;
				}
				
				if (orderItem != null) {
					BigDecimal quantity = orderItem.getQuantity();
					
					if (quantity != null) {
						sumQuantity = sumQuantity.add(quantity);
					}
				}
				
			}
			
			strQuantity = sumQuantity.toPlainString();
			skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
		}
		
	}
	
	private void prepareSumBuyerOrderItemAllBuyers (Map<String, Object> skuOrderMap,
			List<Integer> buyerIds, List<String> deliveryDates, SKUBA skuObj,
			Map<String, Map<String, Map<Integer, OrderItem>>> orderItemMap) {
		
		//Integer skuId = skuObj.getSkuId();
		String key1 = skuObj.getSkuId() + "_" + skuObj.getSkuBAId();
		boolean isCellLocked = false;
		boolean exceptionFlag = false;
		for (Integer buyerId : buyerIds) {
			BigDecimal sumQuantity = new BigDecimal(0);
			String strQuantity = "";
			for (String deliveryDate : deliveryDates) {
				OrderItem orderItem = null;
				try {
					orderItem = orderItemMap.get(key1).get(deliveryDate).get(buyerId);
				} catch (NullPointerException npe) {
					orderItem = null;
					if(deliveryDates.size() == 1){
						isCellLocked = true;	
					}
					exceptionFlag = true;
				}
				
				if (orderItem != null) {
					BigDecimal quantity = orderItem.getQuantity();
					
					if (quantity != null) {
						sumQuantity = sumQuantity.add(quantity);
					}
					
					if(orderItem.getOrderLockedBy() != null || //locked
							orderItem.getOrderFinalizedBy() != null && deliveryDates.size() == 1){
						isCellLocked = true;
					}
				} else if(orderItem == null && !exceptionFlag){
					isCellLocked = true;
				}
				
			}
			
			strQuantity = sumQuantity.toPlainString();
			skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
			skuOrderMap.put("Q_" + buyerId.toString() + "_CellLocked", isCellLocked);
			
			isCellLocked = false;
			exceptionFlag = false;
		}
		
	}
	
	private void prepareSumOrderItemAllDates (Map<String, Object> skuOrderMap,
			List<Integer> buyerIds, List<String> deliveryDates, SKU skuObj,
			Map<Integer, Map<String, Map<Integer, OrderItem>>> orderItemMap) {
		
		Integer skuId = skuObj.getSkuId();
		for (String deliveryDate : deliveryDates) {
			BigDecimal sumQuantity = new BigDecimal(0);
			String strQuantity = "";
			for (Integer buyerId : buyerIds) {
				OrderItem orderItem = null;
				try {
					orderItem = orderItemMap.get(skuId).get(deliveryDate).get(buyerId);
				} catch (NullPointerException npe) {
					orderItem = null;
				}
				
				if (orderItem != null) {
					BigDecimal quantity = orderItem.getQuantity();
					
					if (quantity != null) {
						sumQuantity = sumQuantity.add(quantity);
					}
				}
				
			}
			
			strQuantity = sumQuantity.toPlainString();
			skuOrderMap.put("Q_" + deliveryDate, strQuantity);
		}
		
	}
	
	private void prepareSumBuyerOrderItemAllDates (Map<String, Object> skuOrderMap,
			List<Integer> buyerIds, List<String> deliveryDates, SKUBA skuObj,
			Map<String, Map<String, Map<Integer, OrderItem>>> orderItemMap) {
			
		//Integer skuId = skuObj.getSkuId();
		String key1 = skuObj.getSkuId() + "_" + skuObj.getSkuBAId();
		Boolean isCellLocked = false;
		
		for (String deliveryDate : deliveryDates) {
			BigDecimal sumQuantity = new BigDecimal(0);
			String strQuantity = "";
			for (Integer buyerId : buyerIds) {
				OrderItem orderItem = null;
				try {
					orderItem = orderItemMap.get(key1).get(deliveryDate).get(buyerId);
					//orderItem = orderItemMap.get(skuId).get(deliveryDate).get(buyerId);
				} catch (NullPointerException npe) {
					orderItem = null;
					isCellLocked = true;
				}
				
				if (orderItem != null) {
					BigDecimal quantity = orderItem.getQuantity();
					
					if (quantity != null) {
						sumQuantity = sumQuantity.add(quantity);
					}
					if(orderItem.getOrderLockedBy() != null || //locked
							orderItem.getOrderFinalizedBy() != null){
						isCellLocked = true;
					}
				}
				
			}
			
			strQuantity = sumQuantity.toPlainString();
			skuOrderMap.put("Q_" + deliveryDate, strQuantity);
			skuOrderMap.put("Q_" + deliveryDate + "_CellLocked", isCellLocked);

			isCellLocked = false;
		}
		
	}
	
	private void prepareSumReceivedItemsAllBuyers (Map<String, Object> skuOrderMap,
			List<Integer> buyerIds, List<String> deliveryDates, SKUBA skuObj,
			Map<String, Map<String, Map<Integer, OrderReceived>>> receivedItemMap) {
		
		//Integer skuId = skuObj.getSkuId();
		String key1 = skuObj.getSkuId() + "_" + skuObj.getSkuBAId();
		for (Integer buyerId : buyerIds) {
			BigDecimal sumQuantity = new BigDecimal(0);
			String strQuantity = "";
			for (String deliveryDate : deliveryDates) {
				OrderReceived orderReceived = null;
				try {
					orderReceived = receivedItemMap.get(key1).get(deliveryDate).get(buyerId);
				} catch (NullPointerException npe) {
					orderReceived = null;
				}
				
				if (orderReceived != null) {
					BigDecimal quantity = orderReceived.getQuantity();
					
					if (quantity != null) {
						sumQuantity = sumQuantity.add(quantity);
					}
				}
				
			}
			
			strQuantity = sumQuantity.toPlainString();
			skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
		}
		
	}
	
	private void prepareSumReceivedItemsAllDates (Map<String, Object> skuOrderMap,
			List<Integer> buyerIds, List<String> deliveryDates, SKUBA skuObj,
			Map<String, Map<String, Map<Integer, OrderReceived>>> receivedItemMap) {
		
		//Integer skuId = skuObj.getSkuId();
		String key1 = skuObj.getSkuId() + "_" + skuObj.getSkuBAId();
		for (String deliveryDate : deliveryDates) {
			BigDecimal sumQuantity = new BigDecimal(0);
			String strQuantity = "";
			for (Integer buyerId : buyerIds) {
				OrderReceived orderReceived = null;
				try {
					orderReceived = receivedItemMap.get(key1).get(deliveryDate).get(buyerId);
				} catch (NullPointerException npe) {
					orderReceived = null;
				}
				
				if (orderReceived != null) {
					BigDecimal quantity = orderReceived.getQuantity();
					
					if (quantity != null) {
						sumQuantity = sumQuantity.add(quantity);
					}
				}
				
			}
			
			strQuantity = sumQuantity.toPlainString();
			skuOrderMap.put("Q_" + deliveryDate, strQuantity);
		}
		
	}
	
	private void prepareSumBillingItemsAllBuyers (Map<String, Object> skuOrderMap,
			List<Integer> buyerIds, List<String> deliveryDates, AkadenSKU skuObj,
			Map<Integer, Map<String, Map<Integer, BillingItem>>> billingItemMap) {
		
		Integer skuId = skuObj.getSkuId();
		for (Integer buyerId : buyerIds) {
			BigDecimal sumQuantity = new BigDecimal(0);
			String strQuantity = "";
			for (String deliveryDate : deliveryDates) {
				BillingItem billingItem = null;
				try {
					billingItem = billingItemMap.get(skuId).get(deliveryDate).get(buyerId);
				} catch (NullPointerException npe) {
					billingItem = null;
				}
				
				if (billingItem != null) {
					BigDecimal quantity = billingItem.getQuantity();
					
					if (quantity != null) {
						sumQuantity = sumQuantity.add(quantity);
					}
				}
				
			}
			
			strQuantity = sumQuantity.toPlainString();
			skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
		}
		
	}
	
	private void prepareSumBillingItemsAllDates (Map<String, Object> skuOrderMap,
			List<Integer> buyerIds, List<String> deliveryDates, AkadenSKU skuObj,
			Map<Integer, Map<String, Map<Integer, BillingItem>>> billingItemMap) {
		
		Integer skuId = skuObj.getSkuId();
		for (String deliveryDate : deliveryDates) {
			BigDecimal sumQuantity = new BigDecimal(0);
			String strQuantity = "";
			for (Integer buyerId : buyerIds) {
				BillingItem billingItem = null;
				try {
					billingItem = billingItemMap.get(skuId).get(deliveryDate).get(buyerId);
				} catch (NullPointerException npe) {
					billingItem = null;
				}
				
				if (billingItem != null) {
					BigDecimal quantity = billingItem.getQuantity();
					
					if (quantity != null) {
						sumQuantity = sumQuantity.add(quantity);
					}
				}
				
			}
			
			strQuantity = sumQuantity.toPlainString();
			skuOrderMap.put("Q_" + deliveryDate, strQuantity);
		}
		
	}

//	private void prepareSumBuyerBillingItemsAllBuyers (Map<String, Object> skuOrderMap,
//			List<Integer> buyerIds, List<String> deliveryDates, SKUBA skuObj,
//			Map<String, Map<String, Map<Integer, BillingItem>>> billingItemMap) {
//		
//		//Integer skuId = skuObj.getSkuId();
//		String key1 = skuObj.getSkuId() + "_" + skuObj.getSkuBAId();
//		for (Integer buyerId : buyerIds) {
//			BigDecimal sumQuantity = new BigDecimal(0);
//			String strQuantity = "";
//			for (String deliveryDate : deliveryDates) {
//				BillingItem billingItem = null;
//				try {
//					billingItem = billingItemMap.get(key1).get(deliveryDate).get(buyerId);
//				} catch (NullPointerException npe) {
//					billingItem = null;
//				}
//				
//				if (billingItem != null) {
//					BigDecimal quantity = billingItem.getQuantity();
//					
//					if (quantity != null) {
//						sumQuantity = sumQuantity.add(quantity);
//					}
//				}
//				
//			}
//			
//			strQuantity = sumQuantity.toPlainString();
//			skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
//		}
//		
//	}
//	
//	private void prepareSumBuyerBillingItemsAllDates (Map<String, Object> skuOrderMap,
//			List<Integer> buyerIds, List<String> deliveryDates, SKUBA skuObj,
//			Map<Integer, Map<String, Map<Integer, BillingItem>>> billingItemMap) {
//		
//		Integer skuId = skuObj.getSkuId();
//		String key1 = skuObj.getSkuId() + "_" + skuObj.getSkuBAId();
//		for (String deliveryDate : deliveryDates) {
//			BigDecimal sumQuantity = new BigDecimal(0);
//			String strQuantity = "";
//			for (Integer buyerId : buyerIds) {
//				BillingItem billingItem = null;
//				try {
//					billingItem = billingItemMap.get(key1).get(deliveryDate).get(buyerId);
//				} catch (NullPointerException npe) {
//					billingItem = null;
//				}
//				
//				if (billingItem != null) {
//					BigDecimal quantity = billingItem.getQuantity();
//					
//					if (quantity != null) {
//						sumQuantity = sumQuantity.add(quantity);
//					}
//				}
//				
//			}
//			
//			strQuantity = sumQuantity.toPlainString();
//			skuOrderMap.put("Q_" + deliveryDate, strQuantity);
//		}
//		
//	}
	
	private void constructHeader(List<String> skuHeaderKeys, List<String> qtyHeaderKeys,
			List<String> ttlHeaderKeys,	Map<String, String> skuHeaderMap, Map<String, String> qtyHeaderMap,
			Map<String, String> ttlHeaderMap, int[] headerFlag, List<Integer> buyerIds,
			List<String> deliveryDates, boolean isAllDatesView, String priceComputeOpt) {
		
		EONHeader eONHeader = (EONHeader) 
			servletContext.getAttribute(EON_HEADER_NAMES);
		
		String[] skuHKeys = DownloadExcelUtil.skuHeaderKeys;
		String[] skuHNames = DownloadExcelUtil.getSkuHeaderNames(eONHeader);
		for (int i=0; i<skuHKeys.length; i++) {
			//System.out.println("i:[" + i + "] headerFlag:[" + headerFlag[i]+ "]");
			if (headerFlag[i] == 1) {
				String key = skuHKeys[i];
				
				/*If Compute checkbox is not checked, do not display these columns*/
				if (priceComputeOpt.equals("0") &&
					(key.equals(SKUColumnConstantsExcel.PRICE_1) ||
					 key.equals(SKUColumnConstantsExcel.PRICE_2) ||
					 key.equals(SKUColumnConstantsExcel.PRICE_WO_TAX) ||
					 key.equals(SKUColumnConstantsExcel.PRICE_W_TAX) ||
					 key.equals(SKUColumnConstantsExcel.PRICE1_TOTAL) ||
					 key.equals(SKUColumnConstantsExcel.PRICE2_TOTAL) ||
					 key.equals(SKUColumnConstantsExcel.PRICE_TOTAL))){
				//		key.startsWith("price"))
				   continue;
				}
				/* If Compute checkbox is checked and either 1st, 3rd or 4th radiobutton is selected
				 * do not display PRICE WITH TAX column */
				else if ((priceComputeOpt.equals("1") ||
						  priceComputeOpt.equals("3") ||
						  priceComputeOpt.equals("4")) &&
						 key.equals(SKUColumnConstantsExcel.PRICE_W_TAX)){
					continue;
				}
				/* If Compute checkbox is checked and 2nd radiobutton is selected
				 * do not display PRICE WITHOUT TAX column */
				else if (priceComputeOpt.equals("2") &&
						 key.equals(SKUColumnConstantsExcel.PRICE_WO_TAX)){
					continue;
				}
				
				if (i<DownloadExcelUtil.indexAfterDynamic) {
					skuHeaderKeys.add(key);
					skuHeaderMap.put(key, skuHNames[i]);
				}
				else {
					String japValue = skuHNames[i];
					ttlHeaderKeys.add(key);
					ttlHeaderMap.put(key, japValue);
				}
			}
		}
		if (isAllDatesView) {
//			System.out.println("all dates view");
			for (String deliveryDate : deliveryDates) {
				qtyHeaderKeys.add("Q_" + deliveryDate);
				qtyHeaderMap.put("Q_" + deliveryDate, DownloadExcelUtil.getMDLbl(deliveryDate));
			}
		}
		else {
//			System.out.println("all buyers view");
			List<User> buyerObjs = UserUtil.toUserObjs(buyerIds);
			for (User buyerObj : buyerObjs) {
				qtyHeaderKeys.add("Q_" + buyerObj.getUserId().toString());
				qtyHeaderMap.put("Q_" + buyerObj.getUserId().toString(), buyerObj.getShortName());
			}
			
		}
		
	}
	
	private Map<String, Object> populateSkuObjMap(SKU skuObj) {
		
		Map<String, Object> skuOrderMap = new HashMap<String, Object>();
		
//		String skuName = skuObj.getSkuName();
//		System.out.println("skuName:[" + skuName + "]");
		
		skuOrderMap.put(SKUColumnConstants.SKU_ID, skuObj.getSkuId());
		skuOrderMap.put(SKUColumnConstants.COMPANY_NAME, skuObj.getUser().getCompany().getCompanyName());
		skuOrderMap.put(SKUColumnConstants.SELLER_NAME, skuObj.getUser().getShortName());
		this.validSellerMap.put(skuObj.getUser().getUserId(), skuObj.getUser());
		skuOrderMap.put(SKUColumnConstants.SKU_GROUP_NAME, skuObj.getSkuGroup().getDescription());
		skuOrderMap.put(SKUColumnConstants.MARKET_CONDITION, skuObj.getMarket());
		skuOrderMap.put(SKUColumnConstants.SKU_NAME, skuObj.getSkuName());
		skuOrderMap.put(SKUColumnConstants.AREA_PRODUCTION, skuObj.getLocation());
		skuOrderMap.put(SKUColumnConstants.CLASS1, skuObj.getGrade());
		skuOrderMap.put(SKUColumnConstants.CLASS2, skuObj.getClazz());
		skuOrderMap.put(SKUColumnConstants.PRICE_1, skuObj.getPrice1());
		skuOrderMap.put(SKUColumnConstants.PRICE_2, skuObj.getPrice2());
		skuOrderMap.put(SKUColumnConstants.PRICE_WO_TAX, skuObj.getPriceWithoutTax());
		skuOrderMap.put(SKUColumnConstants.PRICE_W_TAX, skuObj.getPriceWithTax());
		skuOrderMap.put(SKUColumnConstants.PACKAGE_QTY, skuObj.getPackageQuantity());
		skuOrderMap.put(SKUColumnConstants.PACKAGE_TYPE, skuObj.getPackageType());
		skuOrderMap.put(SKUColumnConstants.UOM, skuObj.getOrderUnit().getOrderUnitName());
		//skuOrderMap.put("comment", skuObj.getComments());
		
		skuOrderMap.put(SKUColumnConstants.COLUMN_01, skuObj.getColumn01());
		skuOrderMap.put(SKUColumnConstants.COLUMN_02, skuObj.getColumn02());
		skuOrderMap.put(SKUColumnConstants.COLUMN_03, skuObj.getColumn03());
		skuOrderMap.put(SKUColumnConstants.COLUMN_04, skuObj.getColumn04());
		skuOrderMap.put(SKUColumnConstants.COLUMN_05, skuObj.getColumn05());
		skuOrderMap.put(SKUColumnConstants.COLUMN_06, skuObj.getColumn06());
		skuOrderMap.put(SKUColumnConstants.COLUMN_07, skuObj.getColumn07());
		skuOrderMap.put(SKUColumnConstants.COLUMN_08, skuObj.getColumn08());
		skuOrderMap.put(SKUColumnConstants.COLUMN_09, skuObj.getColumn09());
		skuOrderMap.put(SKUColumnConstants.COLUMN_10, skuObj.getColumn10());
		skuOrderMap.put(SKUColumnConstants.COLUMN_11, skuObj.getColumn11());
		skuOrderMap.put(SKUColumnConstants.COLUMN_12, skuObj.getColumn12());
		skuOrderMap.put(SKUColumnConstants.COLUMN_13, skuObj.getColumn13());
		skuOrderMap.put(SKUColumnConstants.COLUMN_14, skuObj.getColumn14());
		skuOrderMap.put(SKUColumnConstants.COLUMN_15, skuObj.getColumn15());
		skuOrderMap.put(SKUColumnConstants.COLUMN_16, skuObj.getColumn16());
		skuOrderMap.put(SKUColumnConstants.COLUMN_17, skuObj.getColumn17());
		skuOrderMap.put(SKUColumnConstants.COLUMN_18, skuObj.getColumn18());
		skuOrderMap.put(SKUColumnConstants.COLUMN_19, skuObj.getColumn19());
		skuOrderMap.put(SKUColumnConstants.COLUMN_20, skuObj.getColumn20());
		
		return skuOrderMap;
	}
	
	private Map<String, Object> populateSkuObjMap(SKUBA skuObj, 
			Map<Integer, OrderUnit> sellingUomMap, Map<Integer, OrderUnit> orderUnitMap) {
		
		Map<String, Object> skuOrderMap = new HashMap<String, Object>();
		
//		String skuName = skuObj.getSkuName();
//		System.out.println("skuName:[" + skuName + "]");
		
		skuOrderMap.put(SKUColumnConstants.SKU_ID, skuObj.getSkuId());
		skuOrderMap.put(SKUColumnConstants.COMPANY_NAME, skuObj.getUser().getCompany().getCompanyName());
		skuOrderMap.put(SKUColumnConstants.SELLER_NAME, skuObj.getUser().getShortName());
		this.validSellerMap.put(skuObj.getUser().getUserId(), skuObj.getUser());
		skuOrderMap.put(SKUColumnConstants.SKU_GROUP_NAME, skuObj.getSkuGroup().getDescription());
		skuOrderMap.put(SKUColumnConstants.MARKET_CONDITION, skuObj.getMarket());
		skuOrderMap.put(SKUColumnConstants.SKU_NAME, skuObj.getSkuName());
		skuOrderMap.put(SKUColumnConstants.AREA_PRODUCTION, skuObj.getLocation());
		skuOrderMap.put(SKUColumnConstants.CLASS1, skuObj.getGrade());
		skuOrderMap.put(SKUColumnConstants.CLASS2, skuObj.getClazz());
		skuOrderMap.put(SKUColumnConstants.PRICE_1, skuObj.getPrice1());
		skuOrderMap.put(SKUColumnConstants.PRICE_2, skuObj.getPrice2());
		skuOrderMap.put(SKUColumnConstants.PRICE_WO_TAX, skuObj.getPriceWithoutTax());
		skuOrderMap.put(SKUColumnConstants.PRICE_W_TAX, skuObj.getPriceWithTax());
		skuOrderMap.put(SKUColumnConstants.PACKAGE_QTY, skuObj.getPackageQuantity());
		skuOrderMap.put(SKUColumnConstants.PACKAGE_TYPE, skuObj.getPackageType());

		OrderUnit orderUnit = orderUnitMap.get(skuObj.getOrderUnit().getOrderUnitId());
		skuOrderMap.put(SKUColumnConstants.UOM, orderUnit.getOrderUnitName());
		
		//skuOrderMap.put("unitorder", skuObj.getOrderUnit().getOrderUnitName());
		//skuOrderMap.put("comment", skuObj.getComments());
		//4 new columns - start
		skuOrderMap.put(SKUColumnConstants.PURCHASE_PRICE, skuObj.getPurchasePrice());
		skuOrderMap.put(SKUColumnConstants.SELLING_PRICE, skuObj.getSellingPrice());

		OrderUnit sellingUom = null;
		if (skuObj.getSellingUom() != null){
			sellingUom = sellingUomMap.get(skuObj.getSellingUom().getOrderUnitId());
			skuOrderMap.put(SKUColumnConstants.SELLING_UOM, sellingUom.getOrderUnitName());
		}else{
			skuOrderMap.put(SKUColumnConstants.SELLING_UOM, skuObj.getSellingUom());
		}
		
		skuOrderMap.put(SKUColumnConstants.SKU_COMMENT, skuObj.getSkuComment());
		//4 new columns - end
		
		skuOrderMap.put(SKUColumnConstants.COLUMN_01, skuObj.getColumn01());
		skuOrderMap.put(SKUColumnConstants.COLUMN_02, skuObj.getColumn02());
		skuOrderMap.put(SKUColumnConstants.COLUMN_03, skuObj.getColumn03());
		skuOrderMap.put(SKUColumnConstants.COLUMN_04, skuObj.getColumn04());
		skuOrderMap.put(SKUColumnConstants.COLUMN_05, skuObj.getColumn05());
		skuOrderMap.put(SKUColumnConstants.COLUMN_06, skuObj.getColumn06());
		skuOrderMap.put(SKUColumnConstants.COLUMN_07, skuObj.getColumn07());
		skuOrderMap.put(SKUColumnConstants.COLUMN_08, skuObj.getColumn08());
		skuOrderMap.put(SKUColumnConstants.COLUMN_09, skuObj.getColumn09());
		skuOrderMap.put(SKUColumnConstants.COLUMN_10, skuObj.getColumn10());
		skuOrderMap.put(SKUColumnConstants.COLUMN_11, skuObj.getColumn11());
		skuOrderMap.put(SKUColumnConstants.COLUMN_12, skuObj.getColumn12());
		skuOrderMap.put(SKUColumnConstants.COLUMN_13, skuObj.getColumn13());
		skuOrderMap.put(SKUColumnConstants.COLUMN_14, skuObj.getColumn14());
		skuOrderMap.put(SKUColumnConstants.COLUMN_15, skuObj.getColumn15());
		skuOrderMap.put(SKUColumnConstants.COLUMN_16, skuObj.getColumn16());
		skuOrderMap.put(SKUColumnConstants.COLUMN_17, skuObj.getColumn17());
		skuOrderMap.put(SKUColumnConstants.COLUMN_18, skuObj.getColumn18());
		skuOrderMap.put(SKUColumnConstants.COLUMN_19, skuObj.getColumn19());
		skuOrderMap.put(SKUColumnConstants.COLUMN_20, skuObj.getColumn20());
		
		return skuOrderMap;
	}
		
	private Map<String, Object> populateAkadenSkuObjMap(AkadenSKU skuObj, 
		Map<Integer, OrderUnit> sellingUomMap, Map<Integer, OrderUnit> orderUnitMap) {
		
		Map<String, Object> skuOrderMap = new HashMap<String, Object>();
		
//		String skuName = skuObj.getSkuName();
//		System.out.println("skuName:[" + skuName + "]");
		
		skuOrderMap.put(SKUColumnConstants.SKU_ID, skuObj.getSkuId());
		skuOrderMap.put(SKUColumnConstants.COMPANY_NAME, skuObj.getUser().getCompany().getCompanyName());
		skuOrderMap.put(SKUColumnConstants.SELLER_NAME, skuObj.getUser().getShortName());
		this.validSellerMap.put(skuObj.getUser().getUserId(), skuObj.getUser());
		skuOrderMap.put(SKUColumnConstants.SKU_GROUP_NAME, skuObj.getSkuGroup().getDescription());
		skuOrderMap.put(SKUColumnConstants.MARKET_CONDITION, skuObj.getMarket());
		skuOrderMap.put(SKUColumnConstants.SKU_NAME, skuObj.getSkuName());
		skuOrderMap.put(SKUColumnConstants.AREA_PRODUCTION, skuObj.getLocation());
		skuOrderMap.put(SKUColumnConstants.CLASS1, skuObj.getGrade());
		skuOrderMap.put(SKUColumnConstants.CLASS2, skuObj.getClazz());
		skuOrderMap.put(SKUColumnConstants.PRICE_1, skuObj.getPrice1());
		skuOrderMap.put(SKUColumnConstants.PRICE_2, skuObj.getPrice2());
		skuOrderMap.put(SKUColumnConstants.PRICE_WO_TAX, skuObj.getPriceWithoutTax());
		skuOrderMap.put(SKUColumnConstants.PRICE_W_TAX, skuObj.getPriceWithTax());
		skuOrderMap.put(SKUColumnConstants.PACKAGE_QTY, skuObj.getPackageQuantity());
		skuOrderMap.put(SKUColumnConstants.PACKAGE_TYPE, skuObj.getPackageType());

		if (orderUnitMap == null) {
			skuOrderMap.put(SKUColumnConstants.UOM, skuObj.getOrderUnit().getOrderUnitName());
		} else {
			OrderUnit orderUnit = orderUnitMap.get(skuObj.getOrderUnit().getOrderUnitId());
			skuOrderMap.put(SKUColumnConstants.UOM, orderUnit.getOrderUnitName());
		}
		
		if (sellingUomMap != null) {
			//skuOrderMap.put("unitorder", skuObj.getOrderUnit().getOrderUnitName());
			//skuOrderMap.put("comment", skuObj.getComments());
			//4 new columns - start
			skuOrderMap.put(SKUColumnConstants.PURCHASE_PRICE, skuObj.getPurchasePrice());
			skuOrderMap.put(SKUColumnConstants.SELLING_PRICE, skuObj.getSellingPrice());
	
			OrderUnit sellingUom = null;
			if (skuObj.getSellingUom() != null){
				sellingUom = sellingUomMap.get(skuObj.getSellingUom().getOrderUnitId());
				skuOrderMap.put(SKUColumnConstants.SELLING_UOM, sellingUom.getOrderUnitName());
			}else{
				skuOrderMap.put(SKUColumnConstants.SELLING_UOM, skuObj.getSellingUom());
			}
			
			skuOrderMap.put(SKUColumnConstants.SKU_COMMENT, skuObj.getComments());
			//4 new columns - end
		}
		
		return skuOrderMap;
	}
	
	
	private String getDateLabel(List<String> deliveryDates, String format) {
		String dateLabel = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date startDateObj = DateFormatter.toDateObj(deliveryDates.get(0));
		Date endDateObj = DateFormatter.toDateObj(deliveryDates.get(deliveryDates.size()-1));
		
		if (deliveryDates.size() > 1) {
			dateLabel = sdf.format(startDateObj) + DownloadExcelUtil.separator +
			sdf.format(endDateObj);
		}
		else {
			dateLabel = sdf.format(startDateObj);
		}
		
		return dateLabel;
	}
	
	private String getDateMDLabel(List<String> deliveryDates) {
		String dateLabel = "";
		String startDate = deliveryDates.get(0);
		String endDate = deliveryDates.get(deliveryDates.size()-1);
		
		if (deliveryDates.size() > 1) {
			dateLabel = DownloadExcelUtil.getMDLbl(startDate) + DownloadExcelUtil.separator +
			DownloadExcelUtil.getMDLbl(endDate);
		}
		else
			dateLabel = DownloadExcelUtil.getMDLbl(startDate);
		
		return dateLabel;
	}
	
	private String getSellerOption() {
		String sellerOption = "";
		if (this.validSellerMap.size() > 1) {
			sellerOption = this.validSellerMap.size() + DownloadExcelUtil.seller;
		}
		else {
			Iterator<Integer> it = this.validSellerMap.keySet().iterator();
			if (it.hasNext()) {
				Integer sellerId = it.next();
				sellerOption = this.validSellerMap.get(sellerId).getShortName();
			}
		}
		return sellerOption;
	}
	
	private String getBuyerOption(List<Integer> buyerIds) {
		String buyerOption = "";
		List<User> buyerObjs = UserUtil.toUserObjs(buyerIds);
		if (buyerObjs.size() > 1) {
			buyerOption = buyerObjs.size() + DownloadExcelUtil.buyer;
		}
		else {
			User buyerObj = buyerObjs.get(0);
			buyerOption = buyerObj.getShortName();
		}
		return buyerOption;
	}
	
	private void setSheetOption(DownloadExcelSettingForm dxls) {
		if (dxls.getDateOpt().equals("1") && dxls.getSellerOpt().equals("1")
				&& dxls.getBuyerOpt().equals("1"))
			this.sheetOption = "D1S1B1";
		else if (dxls.getDateOpt().equals("1") && dxls.getSellerOpt().equals("1")
				&& dxls.getBuyerOpt().equals("9"))
			this.sheetOption = "D1S1B9";
		else if (dxls.getDateOpt().equals("1") && dxls.getSellerOpt().equals("9")
				&& dxls.getBuyerOpt().equals("1"))
			this.sheetOption = "D1S9B1";
		else if (dxls.getDateOpt().equals("1") && dxls.getSellerOpt().equals("9")
				&& dxls.getBuyerOpt().equals("9"))
			this.sheetOption = "D1S9B9";
		else if (dxls.getDateOpt().equals("9") && dxls.getSellerOpt().equals("1")
				&& dxls.getBuyerOpt().equals("1"))
			this.sheetOption = "D9S1B1";
		else if (dxls.getDateOpt().equals("9") && dxls.getSellerOpt().equals("1")
				&& dxls.getBuyerOpt().equals("9"))
			this.sheetOption = "D9S1B9";
		else if (dxls.getDateOpt().equals("9") && dxls.getSellerOpt().equals("9")
				&& dxls.getBuyerOpt().equals("1"))
			this.sheetOption = "D9S9B1";
		else if (dxls.getDateOpt().equals("9") && dxls.getSellerOpt().equals("9")
				&& dxls.getBuyerOpt().equals("9"))
			this.sheetOption = "D9S9B9";
		
	}
	
	private List<Order> getCheckedOrders(List<Integer> buyerIds, List<String> checkedDates,
			List<Integer> sellerIds, Integer sheetTypeId, User user) {
		List<Order> checkedOrders = new ArrayList<Order>();
		
		//Added by rhoda start: this flag is for Buyer Member Sheet to get Orders published by Buyer Admin
		String enableBAPublish = null;
		boolean isBAPublished = buyerOrderSheetService.isBuyerPublished(user);
		if (isBAPublished) 
			enableBAPublish=String.valueOf(isBAPublished);
		//Added by rhoda end
		
		if (sheetTypeId.equals(SheetTypeConstants.SELLER_ORDER_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ORDER_SHEET))
			checkedOrders = orderSheetService.getAllOrders(buyerIds, checkedDates, sellerIds);
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ALLOCATION_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ALLOCATION_SHEET))
			checkedOrders = allocationSheetService.getAllOrders(buyerIds, checkedDates, sellerIds);
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_AKADEN_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_AKADEN_SHEET))
			checkedOrders = orderSheetService.getAllOrders(buyerIds, checkedDates, sellerIds);//wtf
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_BILLING_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.SELLER_BILLING_SHEET))
			checkedOrders = billingSheetService.getBillingOrders(sellerIds, buyerIds, checkedDates, null);
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET))
			checkedOrders = buyerOrderSheetService.getPublishedOrders(buyerIds, checkedDates, sellerIds, null, null);
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ORDER_SHEET))
			checkedOrders = buyerOrderSheetService.getPublishedOrders(buyerIds, checkedDates, sellerIds, user.getUserId(), enableBAPublish);
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_RECEIVED_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.BUYER_RECEIVED_SHEET))
			checkedOrders = receivedSheetService.getPublishedOrdersForReceived(buyerIds, checkedDates, sellerIds);
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_BILLING_SHEET) ||
				sheetTypeId.equals(SheetTypeConstants.BUYER_BILLING_SHEET))
			checkedOrders = billingSheetService.getBillingOrders(sellerIds, buyerIds, checkedDates, user.getUserId());
		
		return checkedOrders;
	}
	
	private String getCategoryName(Integer categoryId) {
		String categoryName = "";
		
		if (categoryId.equals(CategoryConstants.FRUITS))
			categoryName = DownloadExcelUtil.fruitsLabel;
		else if (categoryId.equals(CategoryConstants.VEGETABLES))
			categoryName = DownloadExcelUtil.veggiesLabel;
		else if (categoryId.equals(CategoryConstants.FISH))
			categoryName = DownloadExcelUtil.fishLabel;
		
		return categoryName;
	}
	
	private String getEONSheetName(Integer sheetTypeId) {
		String eONSheetName = "";
		
		if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ORDER_SHEET)) {
			eONSheetName = SheetTypeConstants.sellerAdminOrder;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ORDER_SHEET)) {
			eONSheetName = SheetTypeConstants.sellerOrder;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ALLOCATION_SHEET)) {
			eONSheetName = SheetTypeConstants.sellerAdminAlloc;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ALLOCATION_SHEET)) {
			eONSheetName = SheetTypeConstants.sellerAlloc;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_AKADEN_SHEET)) {
			eONSheetName = SheetTypeConstants.sellerAdminAkaden;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_AKADEN_SHEET)) {
			eONSheetName = SheetTypeConstants.sellerAkaden;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_BILLING_SHEET)) {
			eONSheetName = SheetTypeConstants.sellerAdminBill;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_BILLING_SHEET)) {
			eONSheetName = SheetTypeConstants.sellerBill;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET)) {
			eONSheetName = SheetTypeConstants.buyerAdminOrder;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ORDER_SHEET)) {
			eONSheetName = SheetTypeConstants.buyerOrder;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_RECEIVED_SHEET)) {
			eONSheetName = SheetTypeConstants.buyerAdminRec;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_RECEIVED_SHEET)) {
			eONSheetName = SheetTypeConstants.buyerRec;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_BILLING_SHEET)) {
			eONSheetName = SheetTypeConstants.buyerAdminBill;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_BILLING_SHEET)) {
			eONSheetName = SheetTypeConstants.buyerBill;
		}
		
		return eONSheetName;
	}
	
	private String getEONSheetNameJ(Integer sheetTypeId) {
		String eONSheetName = "";
		
		if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ORDER_SHEET)) {
			eONSheetName = DownloadExcelUtil.sellerAdminOrder;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ORDER_SHEET)) {
			eONSheetName = DownloadExcelUtil.sellerOrder;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ALLOCATION_SHEET)) {
			eONSheetName = DownloadExcelUtil.sellerAdminAlloc;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ALLOCATION_SHEET)) {
			eONSheetName = DownloadExcelUtil.sellerAlloc;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_AKADEN_SHEET)) {
			eONSheetName = DownloadExcelUtil.sellerAdminAkaden;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_AKADEN_SHEET)) {
			eONSheetName = DownloadExcelUtil.sellerAkaden;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_BILLING_SHEET)) {
			eONSheetName = DownloadExcelUtil.sellerAdminBill;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.SELLER_BILLING_SHEET)) {
			eONSheetName = DownloadExcelUtil.sellerBill;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET)) {
			eONSheetName = DownloadExcelUtil.buyerAdminOrder;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ORDER_SHEET)) {
			eONSheetName =DownloadExcelUtil.buyerOrder;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_RECEIVED_SHEET)) {
			eONSheetName = DownloadExcelUtil.buyerAdminRec;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_RECEIVED_SHEET)) {
			eONSheetName = DownloadExcelUtil.buyerRec;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_BILLING_SHEET)) {
			eONSheetName = DownloadExcelUtil.buyerAdminBill;
		}
		else if (sheetTypeId.equals(SheetTypeConstants.BUYER_BILLING_SHEET)) {
			eONSheetName = DownloadExcelUtil.buyerBill;
		}
		
		return eONSheetName;
	}
	
	private String trimExcelSheetName(String excelSheetName) {
		String newExcelSheetName = excelSheetName;
		if (excelSheetName.length() > DownloadExcelUtil.maxLenSheetName)
			newExcelSheetName = excelSheetName.substring(0, DownloadExcelUtil.maxLenSheetName);
		
		return newExcelSheetName;
	}
	
	private void setExcelSheet(DownloadExcelSheet xlSheet, String excelSheetName,
			String userName, List<String> deliveryDates, String dateMD,
			List<Integer> buyers, Integer sheetTypeId, Integer categoryId,
			List<String> skuHeaderKeys,	List<String> qtyHeaderKeys, List<String> ttlHeaderKeys,
			Map<String, String> skuHeaderMap, Map<String, String> qtyHeaderMap,
			Map<String, String> ttlHeaderMap, List<Map<String, Object>> skuOrderMaps ) {
		
		//System.out.println("skuOrderMaps:[" + skuOrderMaps.size() + "]");
		/* set excel sheet values */
		xlSheet.setExcelSheetName(excelSheetName);
		xlSheet.setUserName(userName);
		xlSheet.setDateOption(this.getDateLabel(deliveryDates, "yyyy/M/d"));
		xlSheet.setSellerOption(this.getSellerOption());
		xlSheet.setBuyerOption(this.getBuyerOption(buyers));
		xlSheet.seteONSheetName(this.getEONSheetNameJ(sheetTypeId));
		xlSheet.setDateMDLabel(dateMD);
		xlSheet.setCategoryName(this.getCategoryName(categoryId));
		xlSheet.setSkuHeaderKeys(skuHeaderKeys);
		xlSheet.setQtyHeaderKeys(qtyHeaderKeys);
		xlSheet.setTtlHeaderKeys(ttlHeaderKeys);
		xlSheet.setSkuHeaderMap(skuHeaderMap);
		xlSheet.setQtyHeaderMap(qtyHeaderMap);
		xlSheet.setTtlHeaderMap(ttlHeaderMap);
		xlSheet.setSkuDataMaps(skuOrderMaps);
		//
		
	}
	
	private List<DownloadExcelSheet> D1S1B1(User user, OrderSheetParam osParam, DownloadExcelSettingForm dxls,
			Map<String, Order> allOrdersMap) {
//		System.out.println("D1S1B1");
		List<DownloadExcelSheet> xlSheets = new ArrayList<DownloadExcelSheet>();
		
		boolean isAllDatesView = osParam.isAllDatesView();
		String startDate = osParam.getStartDate();
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		Integer categoryId = osParam.getCategoryId();
		Integer sheetTypeId = osParam.getSheetTypeId();
//		Integer datesViewBuyerId = new Integer(osParam.getDatesViewBuyerID());
		
		/*
		if (isAllDatesView) {
			buyerIds.clear();
			buyerIds.add(datesViewBuyerId);
		}*/
		
		List<String> checkedDates = new ArrayList<String>();
		List<String> deliveryDates = DateFormatter.getDateList(startDate, 7);
		List<String> dateFlags = dxls.getDateFlag();
		
		for (int i=0; i<dateFlags.size(); i++) {
			String dateFlag = dateFlags.get(i);
			if (dateFlag.equals("1")) checkedDates.add(deliveryDates.get(i));
		}
		List<Order> checkedOrders = this.getCheckedOrders(buyerIds, checkedDates,
				sellerIds, sheetTypeId, user);
		Map<String, Order> allOrdersMap_ = OrderSheetUtil.convertToOrderMap(checkedOrders);
		
		List<User> sellerObjs = UserUtil.toUserObjs(sellerIds);
		List<User> buyerObjs = UserUtil.toUserObjs(buyerIds);
		int sheetCtr = 1;
		for (int x=0; x<checkedDates.size(); x++) {
			String sheetDate = checkedDates.get(x);
			for (int y=0; y<sellerIds.size(); y++) {
				Integer sheetSeller = sellerIds.get(y);
				for (int z=0; z<buyerIds.size(); z++) {
					Integer sheetBuyer = buyerIds.get(z);
					this.validSellerMap.clear();
					
					/* get sheet orders */
					Order sheetOrder = allOrdersMap_.get(sheetDate + "_" +
							sheetBuyer + "_" + sheetSeller);
					if (sheetOrder == null) continue; //skip if no order for this sheet
					
					List<Integer> sheetOrders = new ArrayList<Integer>();
					sheetOrders.add(sheetOrder.getOrderId());
					//
					
					List<String> sheetDates = new ArrayList<String>();
					sheetDates.add(sheetDate);
					List<Integer> sheetBuyers = new ArrayList<Integer>();
					sheetBuyers.add(sheetBuyer);
					
					/* excel sheet name */
					User sellerObj = sellerObjs.get(y);
					User buyerObj = buyerObjs.get(z);
					String excelSheetName = "(" + sheetCtr++ + ")" + sheetDate.substring(4) +
					"-" + sellerObj.getShortName() + "-" + buyerObj.getShortName();
					this.trimExcelSheetName(excelSheetName);
					//
					
					/* header */
					int[] headerFlag = DownloadExcelUtil.setColumnVisibility(user.getPreference().getHideColumn(), sheetTypeId);
					
					List<String> skuHeaderKeys = new ArrayList<String>(); 
					List<String> qtyHeaderKeys = new ArrayList<String>();
					List<String> ttlHeaderKeys = new ArrayList<String>();
					Map<String, String> skuHeaderMap = new HashMap<String, String>();
					Map<String, String> qtyHeaderMap = new HashMap<String, String>();
					Map<String, String> ttlHeaderMap = new HashMap<String, String>();
					
					this.constructHeader(skuHeaderKeys, qtyHeaderKeys, ttlHeaderKeys,
							skuHeaderMap, qtyHeaderMap, ttlHeaderMap,
							headerFlag, sheetBuyers, sheetDates, isAllDatesView, dxls.getPriceComputeOpt());
					//
					
					List<Integer> sellerList = new ArrayList<Integer>();
					sellerList.add(sheetSeller);
					
					/* master list of sku and orders */
					List<Map<String, Object>> skuOrderMaps = this.getSkuOrderMaps(sheetOrders,
							categoryId, sheetTypeId, sellerList, sheetBuyers, sheetDates,
							isAllDatesView, user, dxls.getHasQty());
					//
					
					/* construct excel sheet values */
					DownloadExcelSheet xlSheet = new DownloadExcelSheet();
					this.setExcelSheet(xlSheet, excelSheetName, user.getShortName(),
							checkedDates, DownloadExcelUtil.getMDLbl(sheetDate), sheetBuyers,
							sheetTypeId, categoryId, skuHeaderKeys, qtyHeaderKeys,
							ttlHeaderKeys, skuHeaderMap, qtyHeaderMap, ttlHeaderMap, skuOrderMaps);
					//
					
					xlSheets.add(xlSheet);
				}
				
			}
			
		}
		
		return xlSheets;
		
	}
	
	private List<DownloadExcelSheet> D1S1B9(User user, OrderSheetParam osParam, DownloadExcelSettingForm dxls,
			Map<String, Order> allOrdersMap) {
//		System.out.println("D1S1B9");
		List<DownloadExcelSheet> xlSheets = new ArrayList<DownloadExcelSheet>();
		
		boolean isAllDatesView = osParam.isAllDatesView();
		String startDate = osParam.getStartDate();
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		Integer categoryId = osParam.getCategoryId();
		Integer sheetTypeId = osParam.getSheetTypeId();
//		Integer datesViewBuyerId = new Integer(osParam.getDatesViewBuyerID());
		
		/*
		if (isAllDatesView) {
			buyerIds.clear();
			buyerIds.add(datesViewBuyerId);
		}*/
		
		List<String> checkedDates = new ArrayList<String>();
		List<String> deliveryDates = DateFormatter.getDateList(startDate, 7);
		List<String> dateFlags = dxls.getDateFlag();
		
		for (int i=0; i<dateFlags.size(); i++) {
			String dateFlag = dateFlags.get(i);
			if (dateFlag.equals("1")) checkedDates.add(deliveryDates.get(i));
		}
		List<Order> checkedOrders = this.getCheckedOrders(buyerIds, checkedDates,
				sellerIds, sheetTypeId, user);
		Map<String, Order> allOrdersMap_ = OrderSheetUtil.convertToOrderMap(checkedOrders);
		
		List<User> sellerObjs = UserUtil.toUserObjs(sellerIds);
//		List<User> buyerObjs = UserUtil.toUserObjs(buyerIds);
		int sheetCtr = 1;
		for (int x=0; x<checkedDates.size(); x++) {
			String sheetDate = checkedDates.get(x);
			for (int y=0; y<sellerIds.size(); y++) {
				Integer sheetSeller = sellerIds.get(y);
				this.validSellerMap.clear();
					
				/* get sheet orders */
				List<Integer> sheetOrders = new ArrayList<Integer>();
				for (int z=0; z<buyerIds.size(); z++) {
					Integer sheetBuyer = buyerIds.get(z);
					Order sheetOrder = allOrdersMap_.get(sheetDate + "_" +
							sheetBuyer + "_" + sheetSeller);
					if (sheetOrder == null) continue;
					
					sheetOrders.add(sheetOrder.getOrderId());
				}
				if (sheetOrders.size() == 0) continue; //skip if no order for this sheet
				//
				
				List<String> sheetDates = new ArrayList<String>();
				sheetDates.add(sheetDate);
				List<Integer> sheetBuyers = buyerIds;
				
				/* excel sheet name */
				User sellerObj = sellerObjs.get(y);
				String excelSheetName = "(" + sheetCtr++ + ")" + sheetDate.substring(4) +
				"-" + sellerObj.getShortName() + "-" + DownloadExcelUtil.allBuyers;
				this.trimExcelSheetName(excelSheetName);
				//
					
				/* header */
				int[] headerFlag = DownloadExcelUtil.setColumnVisibility(user.getPreference().getHideColumn(), sheetTypeId);;
				
				List<String> skuHeaderKeys = new ArrayList<String>(); 
				List<String> qtyHeaderKeys = new ArrayList<String>();
				List<String> ttlHeaderKeys = new ArrayList<String>();
				Map<String, String> skuHeaderMap = new HashMap<String, String>();
				Map<String, String> qtyHeaderMap = new HashMap<String, String>();
				Map<String, String> ttlHeaderMap = new HashMap<String, String>();
				
				this.constructHeader(skuHeaderKeys, qtyHeaderKeys, ttlHeaderKeys,
						skuHeaderMap, qtyHeaderMap, ttlHeaderMap,
						headerFlag, sheetBuyers, sheetDates, isAllDatesView, dxls.getPriceComputeOpt());
				//
				
				List<Integer> sellerList = new ArrayList<Integer>();
				sellerList.add(sheetSeller);
				
				/* master list of sku and orders */
				List<Map<String, Object>> skuOrderMaps = this.getSkuOrderMaps(sheetOrders,
						categoryId, sheetTypeId, sellerList, sheetBuyers, sheetDates,
						isAllDatesView, user, dxls.getHasQty());
				//
				
				/* construct excel sheet values */
				DownloadExcelSheet xlSheet = new DownloadExcelSheet();
				this.setExcelSheet(xlSheet, excelSheetName, user.getShortName(),
						checkedDates, DownloadExcelUtil.getMDLbl(sheetDate), sheetBuyers,
						sheetTypeId, categoryId, skuHeaderKeys, qtyHeaderKeys,
						ttlHeaderKeys, skuHeaderMap, qtyHeaderMap, ttlHeaderMap, skuOrderMaps);
				//
				
				xlSheets.add(xlSheet);
				
			}
			
		}
		
		return xlSheets;
		
	}
	
	private List<DownloadExcelSheet> D1S9B1(User user, OrderSheetParam osParam, DownloadExcelSettingForm dxls,
			Map<String, Order> allOrdersMap) {
//		System.out.println("D1S9B1");
		List<DownloadExcelSheet> xlSheets = new ArrayList<DownloadExcelSheet>();
		
		boolean isAllDatesView = osParam.isAllDatesView();
		String startDate = osParam.getStartDate();
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		Integer categoryId = osParam.getCategoryId();
		Integer sheetTypeId = osParam.getSheetTypeId();
//		Integer datesViewBuyerId = new Integer(osParam.getDatesViewBuyerID());
		
		/*
		if (isAllDatesView) {
			buyerIds.clear();
			buyerIds.add(datesViewBuyerId);
		}*/
		
		List<String> checkedDates = new ArrayList<String>();
		List<String> deliveryDates = DateFormatter.getDateList(startDate, 7);
		List<String> dateFlags = dxls.getDateFlag();
		
		for (int i=0; i<dateFlags.size(); i++) {
			String dateFlag = dateFlags.get(i);
			if (dateFlag.equals("1")) checkedDates.add(deliveryDates.get(i));
		}
		List<Order> checkedOrders = this.getCheckedOrders(buyerIds, checkedDates,
				sellerIds, sheetTypeId, user);
		Map<String, Order> allOrdersMap_ = OrderSheetUtil.convertToOrderMap(checkedOrders);
		
//		List<User> sellerObjs = UserUtil.toUserObjs(sellerIds);
		List<User> buyerObjs = UserUtil.toUserObjs(buyerIds);
		int sheetCtr = 1;
		for (int x=0; x<checkedDates.size(); x++) {
			String sheetDate = checkedDates.get(x);
			for (int z=0; z<buyerIds.size(); z++) {
				Integer sheetBuyer = buyerIds.get(z);
				this.validSellerMap.clear();
				
				/* get sheet orders */
				List<Integer> sheetOrders = new ArrayList<Integer>();
				for (int y=0; y<sellerIds.size(); y++) {
					Integer sheetSeller = sellerIds.get(y);
					Order sheetOrder = allOrdersMap_.get(sheetDate + "_" +
							sheetBuyer + "_" + sheetSeller);
					if (sheetOrder == null) continue;
					
					sheetOrders.add(sheetOrder.getOrderId());
				}
				if (sheetOrders.size() == 0) continue; //skip if no order for this sheet
				//
				
				List<String> sheetDates = new ArrayList<String>();
				sheetDates.add(sheetDate);
				List<Integer> sheetBuyers = new ArrayList<Integer>();
				sheetBuyers.add(sheetBuyer);
				
				/* excel sheet name */
				User buyerObj = buyerObjs.get(z);
				String excelSheetName = "(" + sheetCtr++ + ")" + sheetDate.substring(4) +
				"-" + DownloadExcelUtil.allSellers + "-" + buyerObj.getShortName();
				this.trimExcelSheetName(excelSheetName);
				//
				
				/* header */
				int[] headerFlag = DownloadExcelUtil.setColumnVisibility(user.getPreference().getHideColumn(), sheetTypeId);;
				
				List<String> skuHeaderKeys = new ArrayList<String>(); 
				List<String> qtyHeaderKeys = new ArrayList<String>();
				List<String> ttlHeaderKeys = new ArrayList<String>();
				Map<String, String> skuHeaderMap = new HashMap<String, String>();
				Map<String, String> qtyHeaderMap = new HashMap<String, String>();
				Map<String, String> ttlHeaderMap = new HashMap<String, String>();
				
				this.constructHeader(skuHeaderKeys, qtyHeaderKeys, ttlHeaderKeys,
						skuHeaderMap, qtyHeaderMap, ttlHeaderMap,
						headerFlag, sheetBuyers, sheetDates, isAllDatesView, dxls.getPriceComputeOpt());
				//
				
				/* master list of sku and orders */
				List<Map<String, Object>> skuOrderMaps = this.getSkuOrderMaps(sheetOrders,
						categoryId, sheetTypeId, sellerIds, sheetBuyers, sheetDates,
						isAllDatesView, user, dxls.getHasQty());
				//
				
				/* construct excel sheet values */
				DownloadExcelSheet xlSheet = new DownloadExcelSheet();
				this.setExcelSheet(xlSheet, excelSheetName, user.getShortName(),
						checkedDates, DownloadExcelUtil.getMDLbl(sheetDate), sheetBuyers,
						sheetTypeId, categoryId, skuHeaderKeys, qtyHeaderKeys,
						ttlHeaderKeys, skuHeaderMap, qtyHeaderMap, ttlHeaderMap, skuOrderMaps);
				//
				
				xlSheets.add(xlSheet);
			}
			
		}
		
		return xlSheets;
		
	}
	
	private List<DownloadExcelSheet> D1S9B9(User user, OrderSheetParam osParam, DownloadExcelSettingForm dxls,
			Map<String, Order> allOrdersMap) {
//		System.out.println("D1S9B9");
		List<DownloadExcelSheet> xlSheets = new ArrayList<DownloadExcelSheet>();
		
		boolean isAllDatesView = osParam.isAllDatesView();
		String startDate = osParam.getStartDate();
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		Integer categoryId = osParam.getCategoryId();
		Integer sheetTypeId = osParam.getSheetTypeId();
//		Integer datesViewBuyerId = new Integer(osParam.getDatesViewBuyerID());
		
		/*
		if (isAllDatesView) {
			buyerIds.clear();
			buyerIds.add(datesViewBuyerId);
		}*/
		
		List<String> checkedDates = new ArrayList<String>();
		List<String> deliveryDates = DateFormatter.getDateList(startDate, 7);
		List<String> dateFlags = dxls.getDateFlag();
		
		for (int i=0; i<dateFlags.size(); i++) {
			String dateFlag = dateFlags.get(i);
			if (dateFlag.equals("1")) checkedDates.add(deliveryDates.get(i));
		}
		List<Order> checkedOrders = this.getCheckedOrders(buyerIds, checkedDates,
				sellerIds, sheetTypeId, user);
		Map<String, Order> allOrdersMap_ = OrderSheetUtil.convertToOrderMap(checkedOrders);
		
//		List<User> sellerObjs = UserUtil.toUserObjs(sellerIds);
//		List<User> buyerObjs = UserUtil.toUserObjs(buyerIds);
		int sheetCtr = 1;
		for (int x=0; x<checkedDates.size(); x++) {
			String sheetDate = checkedDates.get(x);
			this.validSellerMap.clear();
			
			/* get sheet orders */
			List<Integer> sheetOrders = new ArrayList<Integer>();
			for (int y=0; y<sellerIds.size(); y++) {
				Integer sheetSeller = sellerIds.get(y);
				for (int z=0; z<buyerIds.size(); z++) {
					Integer sheetBuyer = buyerIds.get(z);
					Order sheetOrder = allOrdersMap_.get(sheetDate + "_" +
							sheetBuyer + "_" + sheetSeller);
					if (sheetOrder == null) continue;
					
					sheetOrders.add(sheetOrder.getOrderId());
				}
			}
			if (sheetOrders.size() == 0) continue; //skip if no order for this sheet
			//
			
			List<String> sheetDates = new ArrayList<String>();
			sheetDates.add(sheetDate);
			List<Integer> sheetBuyers = buyerIds;
			
			/* excel sheet name */
			String excelSheetName = "(" + sheetCtr++ + ")" + sheetDate.substring(4) +
			"-" + DownloadExcelUtil.allSellers + "-" + DownloadExcelUtil.allBuyers;
			this.trimExcelSheetName(excelSheetName);
			//
			
			/* header */
			int[] headerFlag = DownloadExcelUtil.setColumnVisibility(user.getPreference().getHideColumn(), sheetTypeId);;
			
			List<String> skuHeaderKeys = new ArrayList<String>(); 
			List<String> qtyHeaderKeys = new ArrayList<String>();
			List<String> ttlHeaderKeys = new ArrayList<String>();
			Map<String, String> skuHeaderMap = new HashMap<String, String>();
			Map<String, String> qtyHeaderMap = new HashMap<String, String>();
			Map<String, String> ttlHeaderMap = new HashMap<String, String>();
			
			this.constructHeader(skuHeaderKeys, qtyHeaderKeys, ttlHeaderKeys,
					skuHeaderMap, qtyHeaderMap, ttlHeaderMap,
					headerFlag, sheetBuyers, sheetDates, isAllDatesView, dxls.getPriceComputeOpt());
			//
			
			/* master list of sku and orders */
			List<Map<String, Object>> skuOrderMaps = this.getSkuOrderMaps(sheetOrders,
					categoryId, sheetTypeId, sellerIds, sheetBuyers, sheetDates,
					isAllDatesView, user, dxls.getHasQty());
			//
			
			/* construct excel sheet values */
			DownloadExcelSheet xlSheet = new DownloadExcelSheet();
			this.setExcelSheet(xlSheet, excelSheetName, user.getShortName(),
					checkedDates, DownloadExcelUtil.getMDLbl(sheetDate), sheetBuyers,
					sheetTypeId, categoryId, skuHeaderKeys, qtyHeaderKeys,
					ttlHeaderKeys, skuHeaderMap, qtyHeaderMap, ttlHeaderMap, skuOrderMaps);
			//
			
			xlSheets.add(xlSheet);
		
		}
		
		return xlSheets;
		
	}
	
	private List<DownloadExcelSheet> D9S1B1(User user, OrderSheetParam osParam, DownloadExcelSettingForm dxls,
			Map<String, Order> allOrdersMap) {
//		System.out.println("D9S1B1");
		List<DownloadExcelSheet> xlSheets = new ArrayList<DownloadExcelSheet>();
		
		boolean isAllDatesView = osParam.isAllDatesView();
		String startDate = osParam.getStartDate();
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		Integer categoryId = osParam.getCategoryId();
		Integer sheetTypeId = osParam.getSheetTypeId();
//		Integer datesViewBuyerId = new Integer(osParam.getDatesViewBuyerID());
		
		/*
		if (isAllDatesView) {
			buyerIds.clear();
			buyerIds.add(datesViewBuyerId);
		}*/
		
		List<String> checkedDates = new ArrayList<String>();
		List<String> deliveryDates = DateFormatter.getDateList(startDate, 7);
		List<String> dateFlags = dxls.getDateFlag();
		
		for (int i=0; i<dateFlags.size(); i++) {
			String dateFlag = dateFlags.get(i);
			if (dateFlag.equals("1")) checkedDates.add(deliveryDates.get(i));
		}
		List<Order> checkedOrders = this.getCheckedOrders(buyerIds, checkedDates,
				sellerIds, sheetTypeId, user);
		Map<String, Order> allOrdersMap_ = OrderSheetUtil.convertToOrderMap(checkedOrders);
		
		List<User> sellerObjs = UserUtil.toUserObjs(sellerIds);
		List<User> buyerObjs = UserUtil.toUserObjs(buyerIds);
		int sheetCtr = 1;
		for (int y=0; y<sellerIds.size(); y++) {
			Integer sheetSeller = sellerIds.get(y);
			for (int z=0; z<buyerIds.size(); z++) {
				Integer sheetBuyer = buyerIds.get(z);
				this.validSellerMap.clear();
				
				/* get sheet orders */
				List<Integer> sheetOrders = new ArrayList<Integer>();
				for (int x=0; x<checkedDates.size(); x++) {
					String sheetDate = checkedDates.get(x);
					Order sheetOrder = allOrdersMap_.get(sheetDate + "_" +
							sheetBuyer + "_" + sheetSeller);
					if (sheetOrder == null) continue;
					
					sheetOrders.add(sheetOrder.getOrderId());
				}
				if (sheetOrders.size() == 0) continue; //skip if no order for this sheet
				//
					
				List<String> sheetDates = checkedDates;
				//sheetDates.add(sheetDate);
				List<Integer> sheetBuyers = new ArrayList<Integer>();
				sheetBuyers.add(sheetBuyer);
				
				/* excel sheet name */
				User sellerObj = sellerObjs.get(y);
				User buyerObj = buyerObjs.get(z);
				String excelSheetName = "(" + sheetCtr++ + ")" + this.getDateLabel(checkedDates, "MMdd") +
				"-" + sellerObj.getShortName() + "-" + buyerObj.getShortName();
				this.trimExcelSheetName(excelSheetName);
				//
					
				/* header */
				int[] headerFlag = DownloadExcelUtil.setColumnVisibility(user.getPreference().getHideColumn(), sheetTypeId);;
				
				List<String> skuHeaderKeys = new ArrayList<String>(); 
				List<String> qtyHeaderKeys = new ArrayList<String>();
				List<String> ttlHeaderKeys = new ArrayList<String>();
				Map<String, String> skuHeaderMap = new HashMap<String, String>();
				Map<String, String> qtyHeaderMap = new HashMap<String, String>();
				Map<String, String> ttlHeaderMap = new HashMap<String, String>();
				
				this.constructHeader(skuHeaderKeys, qtyHeaderKeys, ttlHeaderKeys,
						skuHeaderMap, qtyHeaderMap, ttlHeaderMap,
						headerFlag, sheetBuyers, sheetDates, isAllDatesView, dxls.getPriceComputeOpt());
				//
					
				List<Integer> sellerList = new ArrayList<Integer>();
				sellerList.add(sheetSeller);
				
				/* master list of sku and orders */
				List<Map<String, Object>> skuOrderMaps = this.getSkuOrderMaps(sheetOrders,
						categoryId, sheetTypeId, sellerList, sheetBuyers, sheetDates,
						isAllDatesView, user, dxls.getHasQty());
				//
				
				/* construct excel sheet values */
				DownloadExcelSheet xlSheet = new DownloadExcelSheet();
				this.setExcelSheet(xlSheet, excelSheetName, user.getShortName(),
						checkedDates, this.getDateMDLabel(checkedDates), sheetBuyers,
						sheetTypeId, categoryId, skuHeaderKeys, qtyHeaderKeys,
						ttlHeaderKeys, skuHeaderMap, qtyHeaderMap, ttlHeaderMap, skuOrderMaps);
				//
					
				xlSheets.add(xlSheet);
			}
				
		}	
		
		return xlSheets;
		
	}
	
	private List<DownloadExcelSheet> D9S1B9(User user, OrderSheetParam osParam, DownloadExcelSettingForm dxls,
			Map<String, Order> allOrdersMap) {
//		System.out.println("D9S1B9");
		List<DownloadExcelSheet> xlSheets = new ArrayList<DownloadExcelSheet>();
		
		boolean isAllDatesView = osParam.isAllDatesView();
		String startDate = osParam.getStartDate();
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		Integer categoryId = osParam.getCategoryId();
		Integer sheetTypeId = osParam.getSheetTypeId();
//		Integer datesViewBuyerId = new Integer(osParam.getDatesViewBuyerID());
		
		/*
		if (isAllDatesView) {
			buyerIds.clear();
			buyerIds.add(datesViewBuyerId);
		}*/
		
		List<String> checkedDates = new ArrayList<String>();
		List<String> deliveryDates = DateFormatter.getDateList(startDate, 7);
		List<String> dateFlags = dxls.getDateFlag();
		
		for (int i=0; i<dateFlags.size(); i++) {
			String dateFlag = dateFlags.get(i);
			if (dateFlag.equals("1")) checkedDates.add(deliveryDates.get(i));
		}
		List<Order> checkedOrders = this.getCheckedOrders(buyerIds, checkedDates,
				sellerIds, sheetTypeId, user);
		Map<String, Order> allOrdersMap_ = OrderSheetUtil.convertToOrderMap(checkedOrders);
		
		List<User> sellerObjs = UserUtil.toUserObjs(sellerIds);
//		List<User> buyerObjs = UserUtil.toUserObjs(buyerIds);
		int sheetCtr = 1;
		for (int y=0; y<sellerIds.size(); y++) {
			Integer sheetSeller = sellerIds.get(y);
			
			this.validSellerMap.clear();
			
			/* get sheet orders */
			List<Integer> sheetOrders = new ArrayList<Integer>();
			for (int x=0; x<checkedDates.size(); x++) {
				String sheetDate = checkedDates.get(x);
				for (int z=0; z<buyerIds.size(); z++) {
					Integer sheetBuyer = buyerIds.get(z);
				
					Order sheetOrder = allOrdersMap_.get(sheetDate + "_" +
							sheetBuyer + "_" + sheetSeller);
					if (sheetOrder == null) continue;
					
					sheetOrders.add(sheetOrder.getOrderId());
				}
			}
			if (sheetOrders.size() == 0) continue; //skip if no order for this sheet
			//
			
			List<String> sheetDates = checkedDates;
			//sheetDates.add(sheetDate);
			List<Integer> sheetBuyers = new ArrayList<Integer>();
			sheetBuyers = buyerIds;
			
			/* excel sheet name */
			User sellerObj = sellerObjs.get(y);
			//User buyerObj = buyerObjs.get(z);
			String excelSheetName = "(" + sheetCtr++ + ")" + this.getDateLabel(checkedDates, "MMdd") +
			"-" + sellerObj.getShortName() + "-" + DownloadExcelUtil.allBuyers;
			this.trimExcelSheetName(excelSheetName);
			//
			
			/* header */
			int[] headerFlag = DownloadExcelUtil.setColumnVisibility(user.getPreference().getHideColumn(), sheetTypeId);;
			
			List<String> skuHeaderKeys = new ArrayList<String>(); 
			List<String> qtyHeaderKeys = new ArrayList<String>();
			List<String> ttlHeaderKeys = new ArrayList<String>();
			Map<String, String> skuHeaderMap = new HashMap<String, String>();
			Map<String, String> qtyHeaderMap = new HashMap<String, String>();
			Map<String, String> ttlHeaderMap = new HashMap<String, String>();
			
			this.constructHeader(skuHeaderKeys, qtyHeaderKeys, ttlHeaderKeys,
					skuHeaderMap, qtyHeaderMap, ttlHeaderMap,
					headerFlag, sheetBuyers, sheetDates, isAllDatesView, dxls.getPriceComputeOpt());
			//
			
			List<Integer> sellerList = new ArrayList<Integer>();
			sellerList.add(sheetSeller);
			
			/* master list of sku and orders */
			List<Map<String, Object>> skuOrderMaps = this.getSkuOrderMaps(sheetOrders,
					categoryId, sheetTypeId, sellerList, sheetBuyers, sheetDates,
					isAllDatesView, user, dxls.getHasQty());
			//
			
			/* construct excel sheet values */
			DownloadExcelSheet xlSheet = new DownloadExcelSheet();
			this.setExcelSheet(xlSheet, excelSheetName, user.getShortName(),
					checkedDates, this.getDateMDLabel(checkedDates), sheetBuyers,
					sheetTypeId, categoryId, skuHeaderKeys, qtyHeaderKeys,
					ttlHeaderKeys, skuHeaderMap, qtyHeaderMap, ttlHeaderMap, skuOrderMaps);
			//
			
			xlSheets.add(xlSheet);
				
		}
		
		return xlSheets;
		
	}
	
	private List<DownloadExcelSheet> D9S9B1(User user, OrderSheetParam osParam, DownloadExcelSettingForm dxls,
			Map<String, Order> allOrdersMap) {
//		System.out.println("D9S9B1");
		List<DownloadExcelSheet> xlSheets = new ArrayList<DownloadExcelSheet>();
		
		boolean isAllDatesView = osParam.isAllDatesView();
		String startDate = osParam.getStartDate();
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		Integer categoryId = osParam.getCategoryId();
		Integer sheetTypeId = osParam.getSheetTypeId();
//		Integer datesViewBuyerId = new Integer(osParam.getDatesViewBuyerID());
		
		/*
		if (isAllDatesView) {
			buyerIds.clear();
			buyerIds.add(datesViewBuyerId);
		}*/
		
		List<String> checkedDates = new ArrayList<String>();
		List<String> deliveryDates = DateFormatter.getDateList(startDate, 7);
		List<String> dateFlags = dxls.getDateFlag();
		
		for (int i=0; i<dateFlags.size(); i++) {
			String dateFlag = dateFlags.get(i);
			if (dateFlag.equals("1")) checkedDates.add(deliveryDates.get(i));
		}
		List<Order> checkedOrders = this.getCheckedOrders(buyerIds, checkedDates,
				sellerIds, sheetTypeId, user);
		Map<String, Order> allOrdersMap_ = OrderSheetUtil.convertToOrderMap(checkedOrders);
		
//		List<User> sellerObjs = UserUtil.toUserObjs(sellerIds);
		List<User> buyerObjs = UserUtil.toUserObjs(buyerIds);
		int sheetCtr = 1;
		for (int z=0; z<buyerIds.size(); z++) {
			Integer sheetBuyer = buyerIds.get(z);
			this.validSellerMap.clear();
			
			/* get sheet orders */
			List<Integer> sheetOrders = new ArrayList<Integer>();
			for (int x=0; x<checkedDates.size(); x++) {
				String sheetDate = checkedDates.get(x);
				for (int y=0; y<sellerIds.size(); y++) {
					Integer sheetSeller = sellerIds.get(y);
					
					Order sheetOrder = allOrdersMap_.get(sheetDate + "_" +
							sheetBuyer + "_" + sheetSeller);
					if (sheetOrder == null) continue;
					
					sheetOrders.add(sheetOrder.getOrderId());
				}
			}
			if (sheetOrders.size() == 0) continue; //skip if no order for this sheet
			//
			
			List<String> sheetDates = checkedDates;
			//sheetDates.add(sheetDate);
			List<Integer> sheetBuyers = new ArrayList<Integer>();
			sheetBuyers.add(sheetBuyer);
			
			/* excel sheet name */
			//User sellerObj = sellerObjs.get(y);
			User buyerObj = buyerObjs.get(z);
			String excelSheetName = "(" + sheetCtr++ + ")" + this.getDateLabel(checkedDates, "MMdd") +
			"-" + DownloadExcelUtil.allSellers + "-" + buyerObj.getShortName();
			this.trimExcelSheetName(excelSheetName);
			//
			
			/* header */
			int[] headerFlag = DownloadExcelUtil.setColumnVisibility(user.getPreference().getHideColumn(), sheetTypeId);;
			
			List<String> skuHeaderKeys = new ArrayList<String>(); 
			List<String> qtyHeaderKeys = new ArrayList<String>();
			List<String> ttlHeaderKeys = new ArrayList<String>();
			Map<String, String> skuHeaderMap = new HashMap<String, String>();
			Map<String, String> qtyHeaderMap = new HashMap<String, String>();
			Map<String, String> ttlHeaderMap = new HashMap<String, String>();
			
			this.constructHeader(skuHeaderKeys, qtyHeaderKeys, ttlHeaderKeys,
					skuHeaderMap, qtyHeaderMap, ttlHeaderMap,
					headerFlag, sheetBuyers, sheetDates, isAllDatesView, dxls.getPriceComputeOpt());
			//
			
			/* master list of sku and orders */
			List<Map<String, Object>> skuOrderMaps = this.getSkuOrderMaps(sheetOrders,
					categoryId, sheetTypeId, sellerIds, sheetBuyers, sheetDates,
					isAllDatesView, user, dxls.getHasQty());
			//
			
			/* construct excel sheet values */
			DownloadExcelSheet xlSheet = new DownloadExcelSheet();
			this.setExcelSheet(xlSheet, excelSheetName, user.getShortName(),
					checkedDates, this.getDateMDLabel(checkedDates), sheetBuyers,
					sheetTypeId, categoryId, skuHeaderKeys, qtyHeaderKeys,
					ttlHeaderKeys, skuHeaderMap, qtyHeaderMap, ttlHeaderMap, skuOrderMaps);
			//
			
			xlSheets.add(xlSheet);
		}
		
		return xlSheets;
		
	}
	
	private List<DownloadExcelSheet> D9S9B9(User user, OrderSheetParam osParam, DownloadExcelSettingForm dxls,
			Map<String, Order> allOrdersMap) {
//		System.out.println("D9S9B9");
		List<DownloadExcelSheet> xlSheets = new ArrayList<DownloadExcelSheet>();
		
		boolean isAllDatesView = osParam.isAllDatesView();
		String startDate = osParam.getStartDate();
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		Integer categoryId = osParam.getCategoryId();
		Integer sheetTypeId = osParam.getSheetTypeId();
//		Integer datesViewBuyerId = new Integer(osParam.getDatesViewBuyerID());
		
		/*
		if (isAllDatesView) {
			buyerIds.clear();
			buyerIds.add(datesViewBuyerId);
		}*/
		
		List<String> checkedDates = new ArrayList<String>();
		List<String> deliveryDates = DateFormatter.getDateList(startDate, 7);
		List<String> dateFlags = dxls.getDateFlag();
		
		for (int i=0; i<dateFlags.size(); i++) {
			String dateFlag = dateFlags.get(i);
			if (dateFlag.equals("1")) checkedDates.add(deliveryDates.get(i));
		}
		List<Order> checkedOrders = this.getCheckedOrders(buyerIds, checkedDates,
				sellerIds, sheetTypeId, user);
		Map<String, Order> allOrdersMap_ = OrderSheetUtil.convertToOrderMap(checkedOrders);
		
//		List<User> sellerObjs = UserUtil.toUserObjs(sellerIds);
//		List<User> buyerObjs = UserUtil.toUserObjs(buyerIds);
		int sheetCtr = 1;
		
		this.validSellerMap.clear();
		
		/* get sheet orders */
		List<Integer> sheetOrders = new ArrayList<Integer>();
		for (int x=0; x<checkedDates.size(); x++) {
			String sheetDate = checkedDates.get(x);
			for (int y=0; y<sellerIds.size(); y++) {
				Integer sheetSeller = sellerIds.get(y);
				for (int z=0; z<buyerIds.size(); z++) {
					Integer sheetBuyer = buyerIds.get(z);
				
					Order sheetOrder = allOrdersMap_.get(sheetDate + "_" +
							sheetBuyer + "_" + sheetSeller);
					if (sheetOrder == null) continue;
					
					sheetOrders.add(sheetOrder.getOrderId());
					
				}
			}
		}
		//if (sheetOrders.size() == 0) continue; //skip if no order for this sheet
		//
		
		List<String> sheetDates = checkedDates;
		//sheetDates.add(sheetDate);
		List<Integer> sheetBuyers = new ArrayList<Integer>();
		sheetBuyers = buyerIds;
		
		/* excel sheet name */
		//User sellerObj = sellerObjs.get(y);
		//User buyerObj = buyerObjs.get(z);
		String excelSheetName = "(" + sheetCtr++ + ")" + this.getDateLabel(checkedDates, "MMdd") +
		"-" + DownloadExcelUtil.allSellers + "-" + DownloadExcelUtil.allBuyers;
		this.trimExcelSheetName(excelSheetName);
		//
		
		/* header */
		int[] headerFlag = DownloadExcelUtil.setColumnVisibility(user.getPreference().getHideColumn(), sheetTypeId);;
		
		List<String> skuHeaderKeys = new ArrayList<String>(); 
		List<String> qtyHeaderKeys = new ArrayList<String>();
		List<String> ttlHeaderKeys = new ArrayList<String>();
		Map<String, String> skuHeaderMap = new HashMap<String, String>();
		Map<String, String> qtyHeaderMap = new HashMap<String, String>();
		Map<String, String> ttlHeaderMap = new HashMap<String, String>();
		
		this.constructHeader(skuHeaderKeys, qtyHeaderKeys, ttlHeaderKeys,
				skuHeaderMap, qtyHeaderMap, ttlHeaderMap,
				headerFlag, sheetBuyers, sheetDates, isAllDatesView, dxls.getPriceComputeOpt());
		//
		
		/* master list of sku and orders */
		List<Map<String, Object>> skuOrderMaps = this.getSkuOrderMaps(sheetOrders,
				categoryId, sheetTypeId, sellerIds, sheetBuyers, sheetDates,
				isAllDatesView, user, dxls.getHasQty());
		//
		
		/* construct excel sheet values */
		DownloadExcelSheet xlSheet = new DownloadExcelSheet();
		this.setExcelSheet(xlSheet, excelSheetName, user.getShortName(),
				checkedDates, this.getDateMDLabel(checkedDates), sheetBuyers,
				sheetTypeId, categoryId, skuHeaderKeys, qtyHeaderKeys,
				ttlHeaderKeys, skuHeaderMap, qtyHeaderMap, ttlHeaderMap, skuOrderMaps);
		//
		
		xlSheets.add(xlSheet);
		
		return xlSheets;
		
	}
	
	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
	
//	@SuppressWarnings("unchecked")
//	private List<Integer> getIncludedOrders(HttpServletRequest request) {
//		List<Integer> includedOrders = (List<Integer>) SessionHelper.getAttribute(request, SessionParamConstants.SELECTED_ORDERS_PARAM);
//		return includedOrders;
//	}
	
}
