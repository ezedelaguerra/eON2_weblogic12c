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
 * Mar 30, 2010		nvelasquez		
 * 20120605			Rhoda		Redmine #236 – Allocation Published Rule to restrict only with Quantity
 */
package com.freshremix.webapp.controller.receivedsheet;

import static com.freshremix.constants.SessionParamConstants.ORDER_LOCKED_BUTTON_PARAM;
import static com.freshremix.constants.SessionParamConstants.ORDER_UNLOCKED_BUTTON_PARAM;
import static com.freshremix.constants.SessionParamConstants.SAVE_BUTTON_ENABLED;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.dao.UserPreferenceDao;
import com.freshremix.model.Item;
import com.freshremix.model.LockButtonStatus;
import com.freshremix.model.Order;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.ProfitPreference;
import com.freshremix.model.SKUBA;
import com.freshremix.model.SheetData;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.service.CategoryService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.service.ReceivedSheetService;
import com.freshremix.service.SheetDataService;
import com.freshremix.service.UserPreferenceService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.ui.model.PageInfo;
import com.freshremix.ui.model.ProfitInfo;
import com.freshremix.ui.model.TableParameter;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;
import com.freshremix.util.TaxUtil;

/**
 * @author nvelasquez
 *
 */
public class ReceivedSheetLoadController implements Controller, InitializingBean{
	
	private ReceivedSheetService receivedSheetService;
	private MessageSourceAccessor messageSourceAccessor;
	private EONLocale eonLocale;
	private MessageSource messageSource;
	private OrderUnitService orderUnitService;
	private UsersInformationService userInfoService;
	private SheetDataService sheetDataService;
	private CategoryService categoryService;
	private UserPreferenceDao userPreferenceDao;
	private UserPreferenceService userPreferenceService;


	@Override
	public void afterPropertiesSet() throws Exception {
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}

	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}

	public MessageSourceAccessor getMessageSourceAccessor() {
		return messageSourceAccessor;
	}

	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		this.messageSourceAccessor = messageSourceAccessor;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	
	public void setReceivedSheetService(ReceivedSheetService receivedSheetService) {
		this.receivedSheetService = receivedSheetService;
	}
	public void setOrderUnitService(OrderUnitService orderUnitService) {
		this.orderUnitService = orderUnitService;
	}	
	
	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	public void setSheetDataService(SheetDataService sheetDataService) {
		this.sheetDataService = sheetDataService;
	}
	
	public void setUserPreferenceDao(UserPreferenceDao userPreferenceDao) {
		this.userPreferenceDao = userPreferenceDao;
	}
	
	
	public void setUserPreferenceService(
			UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}


	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);

		String json = request.getParameter("_gt_json");
		Serializer serializer = new JsonSerializer();
		TableParameter tableParam = (TableParameter) serializer.deserialize(json, TableParameter.class);

		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		List<String> dateList = DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate());
		List<Integer> sellerIds = OrderSheetUtil.toList(osParam.getSelectedSellerID());

		
		List<Order> allOrders = getOrderListFromSession(request, SessionParamConstants.ALL_ORDERS_PARAM);
		JSONObject buttonFlags = getButtonFlags(request, osParam, OrderSheetUtil.convertToOrderMap(allOrders));
		
		List<Map<String, Object>> skuOrderList = this.loadOrderItems(request, user, osParam, tableParam.getPageInfo(),buyerIds,sellerIds);
		
		ModelAndView mav = new ModelAndView("json");
		
		String confirmMsg = "";
		if (hasNoPublishedAllocation(allOrders)) {
			confirmMsg = StringUtil.toUTF8String(getMessageSourceAccessor().
				getMessage("receivesheet.buyer.noSkuOrderList", eonLocale.getLocale()));
		}
		
		// set selling uom
		String sellingUom = orderUnitService.getSellingUomList(osParam.getCategoryId());
		SessionHelper.setAttribute(request, SessionParamConstants.SELLING_UOM_LIST_PARAM, sellingUom);
		
//		GrandTotalPrices grandTotalPrices = receivedSheetService.computeTotalPriceOnDisplay(skuOrderList);
//		BigDecimal totalPriceWOTax = grandTotalPrices.getPriceWithoutTax();
//		BigDecimal totalPriceWTax = grandTotalPrices.getPriceWithTax();

		// Total Prices
		List<Integer> categoryList = new ArrayList<Integer>();
		List<String> deliveryDates = null; 
		List<Integer> buyerId = null;
		categoryList.add(osParam.getCategoryId());
		
		if (osParam.isAllDatesView()) {
			deliveryDates = new ArrayList<String>(dateList);
			buyerId = new ArrayList<Integer>();
			buyerId.add(Integer.parseInt(osParam.getDatesViewBuyerID()));
		}
		else {
			deliveryDates = new ArrayList<String>();
			deliveryDates.add(osParam.getSelectedDate());
			buyerId =  new ArrayList<Integer>(buyerIds);
		}
		Map<String, List<Integer>> mapOfMembersByDate = null;
		if (RolesUtil.isUserRoleBuyerAdmin(user)){
			mapOfMembersByDate = getMapOfMembersByDateFromSession(request);
		}

		ProfitInfo pi = receivedSheetService.getBuyerTotalPrices(user,deliveryDates, 
				sellerIds,
				buyerId,
				categoryList,
				TaxUtil.getTAX_RATE().doubleValue(),
				user.getPreference().getProfitPreference().getPriceTaxOption(),mapOfMembersByDate);		

		// Grand Totals
		List<UsersCategory> allCategory = categoryService.getCategoryList(user, osParam);
		List<Integer> categoryId = new ArrayList<Integer>();
		
		for (UsersCategory _cat : allCategory) {
			categoryId.add(_cat.getCategoryId());
		}
		
		ProfitInfo pi2 =receivedSheetService.getBuyerTotalPrices(user,
				dateList, 
				sellerIds,
				buyerIds,
				categoryId,
				TaxUtil.getTAX_RATE().doubleValue(),
				user.getPreference().getProfitPreference().getPriceTaxOption(),mapOfMembersByDate);	
		
		LockButtonStatus lockButtonStatus = userPreferenceService.getLockButtonStatus(user);
		
		mav.addObject("data", skuOrderList);
		mav.addObject("pageInfo", tableParam.getPageInfo());
		mav.addObject("confirmMsg", confirmMsg);
		mav.addObject("buttonFlags", buttonFlags.toString());
		mav.addObject("totals", pi);
		mav.addObject("grandTotals", pi2);
		mav.addObject("profitVisibility", user.getPreference().getProfitPreference());
		mav.addObject("lockButtonStatus", lockButtonStatus.getLockButtonStatus());
		
		return mav;
	}
	
	private JSONObject getButtonFlags(HttpServletRequest request, OrderSheetParam osp,
			Map<String,Order> allOrdersMap)  throws JSONException{
		
		Boolean lockButtonEnabled = Boolean.FALSE;
		Boolean unlockButtonEnabled = Boolean.FALSE;
		List<String> dateList = null;
		List<Integer> buyerIds = null;
		List<Integer> sellerIds = OrderSheetUtil.toList(osp.getSelectedSellerID());
		
		if (!osp.isAllDatesView()) {
			dateList = DateFormatter.getDateList(osp.getSelectedDate(), osp.getSelectedDate());
			buyerIds = OrderSheetUtil.toList(osp.getSelectedBuyerID());
		}
		else {
			dateList = DateFormatter.getDateList(osp.getStartDate(), osp.getEndDate());
			buyerIds = OrderSheetUtil.toList(osp.getDatesViewBuyerID());
		}
		
		List<Integer> selectedOrders = new ArrayList<Integer>();
		List<Order> notFinalizedOrders = new ArrayList<Order>();
		int approveCount = 0;
		for (String thisDate : dateList) {
			for (Integer buyerId : buyerIds) {
				for (Integer sellerId : sellerIds) {
					String key = thisDate + "_" + buyerId.toString() + "_" + sellerId.toString();
					Order thisOrder = allOrdersMap.get(key);
					
					if (thisOrder != null) {
						selectedOrders.add(thisOrder.getOrderId());
						
						if (thisOrder.getReceivedApprovedBy() != null) {
							approveCount++;
						}else{
						
							notFinalizedOrders.add(thisOrder);
							
						}
					}	
				}
			}
		}
		
		if (selectedOrders.size() == 0) { //No orders
			lockButtonEnabled = Boolean.FALSE;
			unlockButtonEnabled = Boolean.FALSE;
		}
		else if (notFinalizedOrders.size() == 0) { //All Finalize
			lockButtonEnabled = Boolean.FALSE;
			unlockButtonEnabled = Boolean.TRUE;
		}
		else if (approveCount == 0) { // all not Finalize
			lockButtonEnabled = Boolean.TRUE;
			unlockButtonEnabled = Boolean.FALSE;
		}
		else if (approveCount > 0 && notFinalizedOrders.size() > 0) { //Some Finalize, some unFinalize
			lockButtonEnabled = Boolean.TRUE;
			unlockButtonEnabled = Boolean.TRUE;
		}
		
		JSONObject flags = new JSONObject();
		request.getSession().setAttribute(SessionParamConstants.NOT_FINALIZED_ORDERS_PARAM, notFinalizedOrders);
		request.getSession().setAttribute(SessionParamConstants.SELECTED_ORDERS_PARAM, selectedOrders);
		flags.put(SAVE_BUTTON_ENABLED, Boolean.TRUE);
		flags.put(ORDER_LOCKED_BUTTON_PARAM, lockButtonEnabled);
		flags.put(ORDER_UNLOCKED_BUTTON_PARAM, unlockButtonEnabled);
		
		return flags;
	}

	private List<Map<String, Object>> loadOrderItems(HttpServletRequest request, User user,
			OrderSheetParam osParam, PageInfo pageInfo,  List<Integer> companyBuyerIds, List<Integer> sellerIds) {
		List<Map<String, Object>> skuOrderMaps = new ArrayList<Map<String, Object>>();
		
		/* get parameters */
		String deliveryDate = osParam.getSelectedDate();
		boolean isAllDatesView = osParam.isAllDatesView();
		String startDate = osParam.getStartDate();
		String endDate = osParam.getEndDate();
		List<Integer> buyerIds = OrderSheetUtil.toList(osParam.getSelectedBuyerID());
		Integer datesViewBuyerId = new Integer(osParam.getDatesViewBuyerID());
		/* get parameters */
		Map<String, List<Integer>> mapOfMembersByDate = null;
		List<Integer> memberList = null;
		if (RolesUtil.isUserRoleBuyerAdmin(user)) {
			mapOfMembersByDate = getMapOfMembersByDateFromSession(request);
		}
		List<String> deliveryDates = new ArrayList<String>();
		if (isAllDatesView){
			deliveryDates = DateFormatter.getDateList(startDate, endDate);
			memberList = new ArrayList<Integer>();
			memberList.add(Integer.parseInt(osParam.getDatesViewBuyerID()));
		}
		else{
			deliveryDates.add(deliveryDate);
			
			
			if (RolesUtil.isUserRoleBuyerAdmin(user)) {
				memberList = mapOfMembersByDate.get(deliveryDate);
			} else
			{
				memberList=buyerIds;
			}
		}
		


		
		List<Integer> selectedOrders = this.getIntegerListFromSession(request, SessionParamConstants.SELECTED_ORDERS_PARAM);
		if (selectedOrders.size() == 0) selectedOrders.add(999999999);
		
//		List<SKUBA> allSkuObjs = receivedSheetService.getDistinctSKUs(selectedOrders, categoryId, null);
		List<Integer> categoryList = new ArrayList<Integer>();
		categoryList.add(osParam.getCategoryId());
		
		// ENHANCEMENT START 20120605: Rhoda Redmine 236 Load SKU with quantity only
		SheetData data = sheetDataService.loadSheetData(
				user, startDate, endDate, 
				sellerIds, 
				memberList, 
				categoryList, 
				osParam.getSheetTypeId(), true, false, selectedOrders);
		// ENHANCEMENT START 20120605: Rhoda Redmine 236 Load SKU with quantity only
		
		List<?> allSkuObjs = data.getSkuList();
		List<SKUBA> skuObjs = new ArrayList<SKUBA>();
		int rowIdxStart = pageInfo.getStartRowNum() - 1;
		int rowIdxEnd = rowIdxStart + pageInfo.getPageSize() - 1;
		for (int i=rowIdxStart; i<=rowIdxEnd; i++) {
			if (i >= allSkuObjs.size()) break;
//			skuObjs.add(allSkuObjs.get(i));
			if (allSkuObjs.get(i) instanceof SKUBA)
				skuObjs.add((SKUBA)allSkuObjs.get(i));
		}
		pageInfo.setTotalRowNum(allSkuObjs.size());
		
//		Map<String, Map<String, Map<Integer, OrderReceived>>> receivedItemMap = 
//			new HashMap<String, Map<String, Map<Integer, OrderReceived>>>();
//		try {
//			if (skuObjs.size() > 0)
//				receivedItemMap = receivedSheetService.getReceivedItemsBulk(selectedOrders,
//						OrderSheetUtil.getSkuIds(skuObjs));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		Map<String, Map<String, Map<Integer, Item>>> receivedItemMap = 
			data.getSkuDateBuyOrderItemMap();
		
		Map<Integer, SKUBA> skuObjMap = new HashMap<Integer, SKUBA>();
		Map<Integer, User> proposedUserCache = new HashMap<Integer, User>();
		
		for (SKUBA skuObj : skuObjs) {
			skuObjMap.put(skuObj.getSkuId(), skuObj);
			Map<String, Object> skuOrderMap = new HashMap<String, Object>();
			
			skuOrderMap.put("skuId", skuObj.getSkuId());
			skuOrderMap.put("skuBaId", skuObj.getSkuBAId());
			skuOrderMap.put("sellerId", skuObj.getUser().getUserId());
			if (skuObj.getProposedBy() != null) {
				User proposedByUser = null;
				if (!proposedUserCache.containsKey(skuObj.getProposedBy().getUserId())) {
					proposedByUser = userInfoService.getUserAndCompanyById(skuObj.getProposedBy().getUserId());
					proposedUserCache.put(skuObj.getProposedBy().getUserId(), proposedByUser);
				} else {
					proposedByUser = proposedUserCache.get(skuObj.getProposedBy().getUserId());
				}
				
				skuOrderMap.put("companyname", proposedByUser.getCompany().getShortName());
				skuOrderMap.put("sellername", proposedByUser.getShortName());
			}
			else {
				skuOrderMap.put("companyname", skuObj.getUser().getCompany().getShortName());
				skuOrderMap.put("sellername", skuObj.getUser().getShortName());
			}
			//skuOrderMap.put("groupdesc", skuObj.getSkuGroup().getDescription());
			skuOrderMap.put("groupname", skuObj.getSkuGroup().getSkuGroupId());
			skuOrderMap.put("marketname", StringUtil.nullToBlank(skuObj.getMarket()));
			skuOrderMap.put("column01", 	StringUtil.nullToBlank(skuObj.getColumn01()));
			skuOrderMap.put("column02", 	StringUtil.nullToBlank(skuObj.getColumn02()));
			skuOrderMap.put("column03", 	StringUtil.nullToBlank(skuObj.getColumn03()));
			skuOrderMap.put("column04", 	StringUtil.nullToBlank(skuObj.getColumn04()));
			skuOrderMap.put("column05", 	StringUtil.nullToBlank(skuObj.getColumn05()));
			skuOrderMap.put("skuname", StringUtil.nullToBlank(skuObj.getSkuName()));
			skuOrderMap.put("home", StringUtil.nullToBlank(skuObj.getLocation()));
			skuOrderMap.put("grade", StringUtil.nullToBlank(skuObj.getGrade()));
			skuOrderMap.put("clazzname", StringUtil.nullToBlank(skuObj.getClazz()));
			skuOrderMap.put("price1", NumberUtil.nullToZero((BigDecimal)skuObj.getPrice1()));
			skuOrderMap.put("price2", NumberUtil.nullToZero((BigDecimal)skuObj.getPrice2()));
			skuOrderMap.put("pricewotax", NumberUtil.nullToZero((BigDecimal)skuObj.getPriceWithoutTax()));
			skuOrderMap.put("pricewtax", skuObj.getPriceWithTax());
			skuOrderMap.put("packageqty", StringUtil.nullToBlank(skuObj.getPackageQuantity()));
			skuOrderMap.put("packagetype", StringUtil.nullToBlank(skuObj.getPackageType()));
			skuOrderMap.put("unitorder", skuObj.getOrderUnit().getOrderUnitId());
			skuOrderMap.put("unitordername", skuObj.getOrderUnit().getOrderUnitName());
			skuOrderMap.put("B_purchasePrice", StringUtil.nullToBlank((BigDecimal)skuObj.getPurchasePrice()));
			skuOrderMap.put("B_sellingPrice", StringUtil.nullToBlank((BigDecimal)skuObj.getSellingPrice()));
			skuOrderMap.put("B_sellingUom", skuObj.getSellingUom() != null ? skuObj.getSellingUom().getOrderUnitId() : "");
			skuOrderMap.put("B_skuComment", StringUtil.nullToBlank(skuObj.getSkuComment()));
			skuOrderMap.put("column06", 	StringUtil.nullToBlank(skuObj.getColumn06()));
			skuOrderMap.put("column07", 	StringUtil.nullToBlank(skuObj.getColumn07()));
			skuOrderMap.put("column08", 	StringUtil.nullToBlank(skuObj.getColumn08()));
			skuOrderMap.put("column09", 	StringUtil.nullToBlank(skuObj.getColumn09()));
			skuOrderMap.put("column10", 	StringUtil.nullToBlank(skuObj.getColumn10()));
			skuOrderMap.put("column11", 	StringUtil.nullToBlank(skuObj.getColumn11()));
			skuOrderMap.put("column12", 	StringUtil.nullToBlank(skuObj.getColumn12()));
			skuOrderMap.put("column13", 	StringUtil.nullToBlank(skuObj.getColumn13()));
			skuOrderMap.put("column14", 	StringUtil.nullToBlank(skuObj.getColumn14()));
			skuOrderMap.put("column15", 	StringUtil.nullToBlank(skuObj.getColumn15()));
			skuOrderMap.put("column16", 	StringUtil.nullToBlank(skuObj.getColumn16()));
			skuOrderMap.put("column17", 	StringUtil.nullToBlank(skuObj.getColumn17()));
			skuOrderMap.put("column18", 	StringUtil.nullToBlank(skuObj.getColumn18()));
			skuOrderMap.put("column19", 	StringUtil.nullToBlank(skuObj.getColumn19()));
			skuOrderMap.put("column20", 	StringUtil.nullToBlank(skuObj.getColumn20()));
			
			if (isAllDatesView)
				//receivedSheetService.loadOrderReceivedQuantitiesAllDates(skuOrderMap, deliveryDates, selectedOrders, skuObj);
				this.prepareOrderAllDates(skuOrderMap, deliveryDates, datesViewBuyerId, skuObj, receivedItemMap, user, mapOfMembersByDate);
			else{
				//receivedSheetService.loadOrderReceivedQuantitiesAllBuyers(skuOrderMap, buyerIds, selectedOrders, skuObj);
				this.prepareOrderAllBuyers(skuOrderMap, buyerIds, deliveryDate, skuObj, receivedItemMap, user);
			}

			// Profit and Profit Percentage computations
			BigDecimal rowQty = (BigDecimal)skuOrderMap.get("rowqty");
			ProfitPreference profitPreference = userPreferenceDao.getProfitPreference(user.getUserId());
			boolean withPackageQuantity = true;
			
			if(profitPreference != null){
				if(profitPreference.getWithPackageQuantity().equalsIgnoreCase("1")){
					withPackageQuantity = true;
				} else {
					withPackageQuantity = false;
				}
			} else {
				withPackageQuantity = true;
			}
			
			ProfitInfo profitInfo = OrderSheetUtil.computeProfitInfo(
					skuObj.getPriceWithoutTax(),
					skuObj.getPriceWithTax(),
					skuObj.getSellingPrice(),
					skuObj.getPackageQuantity(),
					rowQty,
					user.getPreference().getProfitPreference().getPriceTaxOption(),
					withPackageQuantity);
			skuOrderMap.put("B_profitPercentage", profitInfo.getProfitPercentage());
			
			try {
				this.prepareProfitInfoColumn(skuOrderMap, profitInfo);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			skuOrderMaps.add(skuOrderMap);
		}
		
		//(Remove)attribute is not used anywhere after this controller
		SessionHelper.setAttribute(request, SessionParamConstants.SKU_OBJ_MAP_PARAM, skuObjMap);
		
		return skuOrderMaps;
	}
	
	private void prepareOrderAllBuyers(Map<String, Object> skuOrderMap,	List<Integer> buyerIds,
			String deliveryDate, SKUBA skuObj,
			Map<String, Map<String, Map<Integer, Item>>> receivedItemMap, User user) {
		
		String key1 = skuObj.getSkuId() + "_" + skuObj.getSkuBAId();

		String hasLockCol = "0"; //flag if has 1 locked column
		boolean hasNoOrderItem = true;
		StringBuffer lockflag = new StringBuffer("{'sku':'1'");
		BigDecimal rowqty = null;
		for (Integer buyerId : buyerIds) {
			Item orderReceived = null;
			try {
				orderReceived = receivedItemMap.get(key1).get(deliveryDate).get(buyerId);
			} catch (NullPointerException npe) {
				orderReceived = null;
			}
			
			
			
			BigDecimal quantity = null;
			String strQuantity = "";
			String strApprvFlag = "1";
			String strLockFlag = "0"; //unlocked
			
			if (orderReceived != null) {
				quantity = orderReceived.getQuantity();
				hasNoOrderItem = false;
				
				if (orderReceived.getReceivedApprovedBy() != null) //approved
				{
					strLockFlag = "1"; //locked
					hasLockCol = "1";
				}
				
				if (orderReceived.getIsApproved() == null || orderReceived.getIsApproved().equals("0"))
					strApprvFlag = "0";
				
				if (quantity != null) {
					if (rowqty == null) rowqty = new BigDecimal(0);
					rowqty = rowqty.add(quantity);
					strQuantity = quantity.toPlainString();
				}
				
			}
			else {
				//strQuantity = "-999";
				strLockFlag = "1";
			}
			
			skuOrderMap.put("Q_" + buyerId.toString(), strQuantity);
			skuOrderMap.put("A_" + buyerId.toString(), strApprvFlag);
			skuOrderMap.put("L_" + buyerId.toString(), strLockFlag);
			
			lockflag.append(",'Q_");
			lockflag.append(buyerId.toString());
			lockflag.append("':'");
			lockflag.append("1");
			lockflag.append("'");
		}

		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)){
			
			if(hasNoOrderItem) hasLockCol = "1";
			else hasLockCol = "0"; // always enable save
			
			lockflag.append(",'B_purchasePrice");
			lockflag.append("':'");
			lockflag.append(hasLockCol);
			lockflag.append("'");

			lockflag.append(",'B_sellingPrice");
			lockflag.append("':'");
			lockflag.append(hasLockCol);
			lockflag.append("'");

			lockflag.append(",'B_sellingUom");
			lockflag.append("':'");
			lockflag.append(hasLockCol);
			lockflag.append("'");
			
			lockflag.append(",'B_skuComment");
			lockflag.append("':'");
			lockflag.append(hasLockCol);
			lockflag.append("'");
			
			lockflag.append(",'buyerCols");
			lockflag.append("':'");
			lockflag.append(hasLockCol);
			lockflag.append("'");
		}
		
		skuOrderMap.put("rowqty", rowqty);
		lockflag.append("}");
		skuOrderMap.put("lockflag", lockflag);
	}
	
	private void prepareOrderAllDates(Map<String, Object> skuOrderMap,
			List<String> deliveryDates, Integer buyerId, SKUBA skuObj,
			Map<String, Map<String, Map<Integer, Item>>> receivedItemMap, User user, Map<String, List<Integer>> mapOfMembersByDate) {
		
		String key1 = skuObj.getSkuId() + "_" + skuObj.getSkuBAId();
		String hasLockCol = "0"; //flag if has 1 locked column
		boolean hasNoOrderItem = true;
		
		StringBuffer lockflag = new StringBuffer("{'sku':'1'");
		BigDecimal rowqty = null;
		for (String deliveryDate : deliveryDates) {
			
			Item orderReceived = null;
			try {
				orderReceived = receivedItemMap.get(key1).get(deliveryDate).get(buyerId);
			} catch (NullPointerException npe) {
				orderReceived = null;
			}
			if (RolesUtil.isUserRoleBuyerAdmin(user)) {
				
				List<Integer> mapOfMembers = mapOfMembersByDate.get(deliveryDate);
				if (CollectionUtils.isEmpty(mapOfMembers) || !mapOfMembers.contains(buyerId)) {
					orderReceived = null;
				}
			}
			BigDecimal quantity = null;
			String strQuantity = "";
			String strLockFlag = "0"; //unlocked
			 
			if (orderReceived != null) {
				quantity = orderReceived.getQuantity();
				hasNoOrderItem = false;
				
				if (orderReceived.getReceivedApprovedBy() != null) { //approved
						strLockFlag = "1"; //locked
						hasLockCol = "1";
				}
			
				if (quantity != null) {
					if (rowqty == null) rowqty = new BigDecimal(0);
					rowqty = rowqty.add(quantity);
					strQuantity = quantity.toPlainString();
				}
			}
			else {
				//strQuantity = "-999";
				strLockFlag = "1"; //locked
			}
			skuOrderMap.put("Q_" + deliveryDate, strQuantity);
			skuOrderMap.put("L_" + deliveryDate, strLockFlag);
			lockflag.append(",'Q_");
			lockflag.append(deliveryDate);
			lockflag.append("':'");
			lockflag.append("1");
			lockflag.append("'");
		}
		
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)){
			
			if(hasNoOrderItem) hasLockCol = "1";
			else hasLockCol = "0"; // always enable save
			
			lockflag.append(",'B_purchasePrice");
			lockflag.append("':'");
			lockflag.append(hasLockCol);
			lockflag.append("'");

			lockflag.append(",'B_sellingPrice");
			lockflag.append("':'");
			lockflag.append(hasLockCol);
			lockflag.append("'");

			lockflag.append(",'B_sellingUom");
			lockflag.append("':'");
			lockflag.append(hasLockCol);
			lockflag.append("'");
			
			lockflag.append(",'B_skuComment");
			lockflag.append("':'");
			lockflag.append(hasLockCol);
			lockflag.append("'");
			
			lockflag.append(",'buyerCols");
			lockflag.append("':'");
			lockflag.append(hasLockCol);
			lockflag.append("'");
		}
		
		skuOrderMap.put("rowqty", rowqty);
		lockflag.append("}");
		skuOrderMap.put("lockflag", lockflag);
	}

	private void prepareProfitInfoColumn(Map<String, Object> skuOrderMap, ProfitInfo profitInfo) 
		throws JSONException {

		JSONObject json = new JSONObject();
		json.put("priceWithoutTax", profitInfo.getPriceWithoutTax());
		json.put("priceWithTax", profitInfo.getPriceWithTax());
		json.put("sellingPrice", profitInfo.getSellingPrice());
		json.put("profit", profitInfo.getProfit());
		json.put("profitPercentage", profitInfo.getProfitPercentage());
		skuOrderMap.put("profitInfo", json.toString());

	}

	@SuppressWarnings("unchecked")
	private List<Integer> getIntegerListFromSession(HttpServletRequest request, String paramName) {
		return (List<Integer>) SessionHelper.getAttribute(request, paramName);
	}

	@SuppressWarnings("unchecked")
	private List<Order> getOrderListFromSession(HttpServletRequest request, String paramName) {
		return (List<Order>) SessionHelper.getAttribute(request, paramName);
	}
	
	private boolean hasNoPublishedAllocation(List<Order> allOrders) {
		
		boolean hasNoFinalizedOrder = true;
		
		for (Order order: allOrders) {
			if (!NumberUtil.isNullOrZero(order.getAllocationPublishedBy())) {
				hasNoFinalizedOrder = false;
				break;
			}
		}
		
		return hasNoFinalizedOrder;
	}
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	@SuppressWarnings("unchecked")
	private Map<String, List<Integer>> getMapOfMembersByDateFromSession(
			HttpServletRequest request) {
		return (Map<String, List<Integer>>) SessionHelper.getAttribute(request, SessionParamConstants.MAP_OF_MEMBERS_BY_DATE);
	}
}
