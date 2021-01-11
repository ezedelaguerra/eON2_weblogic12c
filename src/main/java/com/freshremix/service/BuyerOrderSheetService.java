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
 * Apr 14, 2010		raquino		
 */
package com.freshremix.service;

import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;

import com.freshremix.exception.ServiceException;
import com.freshremix.model.CompositeKey;
import com.freshremix.model.ConcurrencyResponse;
import com.freshremix.model.GrandTotalPrices;
import com.freshremix.model.Order;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.SKUBA;
import com.freshremix.model.User;
import com.freshremix.model.WSBuyerAddOrderSheetRequest;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.ui.model.OrderItemUI;
import com.freshremix.ui.model.ProfitInfo;
import com.freshremix.ui.model.TableParameter;

/**
 * @author raquino
 *
 */
public interface BuyerOrderSheetService {
	
	List<Order> getPublishedOrders(List<Integer> buyerIds, List<String> deliveryDates, List<Integer> sellerIds, Integer isBuyer, String enableBAPublish);
	void saveQuantityByBuyer(OrderForm orderForm, OrderDetails orderDetails,
			Map<String, Order> allOrdersMap,
			List<OrderItemUI> oiUIforUpdate) throws ServiceException;
	void updateLockOrders(List<Integer> orderIds, Integer userId) throws ServiceException;
	void updateUnlockOrders(List<Integer> orderIds, Integer userId) throws ServiceException;
	void saveOrderByBuyerAdmin(OrderForm orderForm, OrderDetails orderDetails,
			Map<String, Order> allOrdersMap, List<OrderItemUI> oiUIforUpdate)
			throws ServiceException;
	GrandTotalPrices computeTotalPriceOnDisplay(List<Map<String, Object>> skuOrderList) ;
	List<Order> getSelectedOrders (Map<String, Order> allOrdersMap, OrderSheetParam osp);
	public Order combineOrders(List<Order> orders);
	void updatePublishOrderByBA(Integer userId, Integer orderId);
	boolean isBuyerPublished(User user);
	
	//start:r
	Map<String, Map<String, Map<Integer, OrderItem>>> getBuyerOrderItemsBulk(List<Integer> orderIds,
			List<Integer> skuIds);

	List<SKUBA> getPublishedSKUBA (List<Integer> orderId, List<Integer> sellerId, List<Integer> buyerId, List<String> deliveryDate, Integer categoryId, Integer isBuyerUser, String hasQty);
	
	//ProfitInfo getBuyerTotalPrices(List<String> deliveryDate, List<Integer> sellerId, List<Integer> buyerId, List<Integer> categoryId, Double tax, String priceWithTaxOpt);

	/**
	 * Common method being used by loadOrder.json and webservice getBuyerOrder.
	 * 
	 * @param osParam
	 * @param user
	 * @param tableParam
	 * @param selectedOrderIdList
	 * @param allOrders
	 * @param mapOfMembersByDate
	 * @return
	 * @throws Exception
	 * @throws JSONException
	 */
	Map<String, Object> getOrders(OrderSheetParam osParam, User user,
			TableParameter tableParam, List<Integer> selectedOrderIdList,
			List<Order> allOrders,Map<String, List<Integer>> mapOfMembersByDate) throws Exception, JSONException;
	
	/**
	 * Saves the buyer order sheet.
	 * 
	 * Note: Originally intended as common method shared by web and webservice
	 * but discontinued. Method is Left as it is.
	 * 
	 * @param orderForm
	 * @param od
	 * @param orderListForUpdate
	 * @return 
	 * @throws ServiceException 
	 */
	ConcurrencyResponse saveProcess(OrderForm orderForm, OrderDetails od,
			List<Order> orderListForUpdate,
			Map<CompositeKey<Integer>, SKUBA> skuBAMapFromDB,
			Map<CompositeKey<Integer>, SKUBA> skuBAMapFromSession)
			throws ServiceException;
	
	/**
	 * Saves the Buyer order sheet sent via webservice
	 * @param wsBuyerAddOrderSheetRequest
	 */
	void saveWSData(WSBuyerAddOrderSheetRequest wsBuyerAddOrderSheetRequest);
	void updatePublishOrderByBA(User user, List<Order> savedOrders)
			throws Exception;

	
	ProfitInfo getBuyerTotalPrices(User user, List<String> deliveryDate,
			List<Integer> sellerId, List<Integer> buyerId,
			Map<String, List<Integer>> mapOfMembersByDateList,
			List<Integer> categoryId, Double tax, String priceWithTaxOpt);
	
	/**
	 * get list of orderItems using skuids and deliverydates
	 * @param skuIds
	 * @param deliveryDates
	 * @return
	 */
	List<OrderItem> getOrderItemsListOfSkuId(List<Integer> skuIds,
			List<String> deliveryDates);
	
	
	
}
