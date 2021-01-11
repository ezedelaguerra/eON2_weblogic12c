package com.freshremix.model;

import java.util.List;
import java.util.Map;

import com.freshremix.ui.model.FilteredIDs;

/**
 *	Filter marker used by Seller Admin user
 */
public class SAFilterMarker extends AdminFilterMarker {

	public SAFilterMarker(List<FilteredIDs> filterList,
			List<Integer> sellerIdList, List<Integer> buyerIdList,
			List<String> selectedDate, List<Order> allOrder,
			SheetState sheet,  Map<String,Map<String, List<Integer>>> dealingPattern) {
		super(filterList, sellerIdList, buyerIdList, selectedDate, allOrder);
		
		OrderList orderList = new OrderList(allOrder, dealingPattern);
		orderMap = orderList.getOrderMap(new OrderKey [] {OrderKey.SELLER});
		this.sheet = sheet;
		super.setUserList(sellerIdList);
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
		return !sheet.isFinalizedOnOther(order);
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
