package com.freshremix.model;

import java.util.List;
import java.util.Map;

import com.freshremix.ui.model.FilteredIDs;

/**
 *	Filter marker used by Buyer Admin user
 */
public class BuyerFilterMarker extends UserFilterMarker {
	
	/**
	 * Constructor. Use this to put seller marks in buyer filter pages
	 * @param filterList - List of FilteredIDs obj that is displayed in filter page
	 * @param sellerIdList - Selected seller id in filter page
	 * @param buyerIdList - Selected buyer id in filter page
	 * @param selectedDate - Date range in filter page
	 * @param allOrder - List of all orders retrieved from DB based on sellerIdList, buyerIdList and selectedDate
	 * @param sheet - Sheet state of the filter page
	 * @param dealingPattern - Collection of all dealing pattern given.
	 */
	public BuyerFilterMarker(List<FilteredIDs> filterList,
			List<Integer> sellerIdList, List<Integer> buyerIdList,
			List<String> selectedDate, List<Order> allOrder,
			SheetState sheet, Map<String,Map<String, List<Integer>>> dealingPattern) {
		super(filterList, sellerIdList, buyerIdList, selectedDate, allOrder);
		OrderList orderList = new OrderList(allOrder, dealingPattern);
		orderMap = orderList.getOrderMap(new OrderKey [] {OrderKey.BUYER, OrderKey.SELLER});
		this.sheet = sheet;
		super.setOuterIdList(buyerIdList);
		super.setInnerIdList(sellerIdList);
	}

	@Override
	public void addToMarkFinList(Order order) {
		this.addToMarkFinIdList(order.getSellerId());
	}
	
	@Override
	public void addToMarkPubList(Order order) {
		this.addToMarkPubIdList(order.getSellerId());
	}

	@Override
	public Boolean doNotMarkAsFinalized(Order order) {
		return !sheet.isFinalized(order);
	}

	@Override
	public Boolean doNotMarkAsPublished(Order order) {
		return !sheet.isPublished(order);
	}

	@Override
	public Boolean doNotMarkAsOtherFinalized(Order order) {
		return true;
	}
	
	@Override
	public void removeToMarkFinList(Order order) {
		this.removeToMarkFinIdList(order.getSellerId());
	}

	@Override
	public void removeToMarkPubList(Order order) {
		this.removeToMarkPubIdList(order.getSellerId());
	}

}
