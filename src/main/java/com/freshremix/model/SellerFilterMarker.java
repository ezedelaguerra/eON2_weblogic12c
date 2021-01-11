package com.freshremix.model;

import java.util.List;
import java.util.Map;

import com.freshremix.ui.model.FilteredIDs;
/**
 *	Filter marker used by Seller user
 */
public class SellerFilterMarker extends UserFilterMarker {

	public SellerFilterMarker(List<FilteredIDs> filterList,
			List<Integer> sellerIdList, List<Integer> buyerIdList,
			List<String> selectedDate, List<Order> allOrder,
			SheetState sheet, Map<String,Map<String, List<Integer>>> dealingPattern) {
		super(filterList, sellerIdList, buyerIdList, selectedDate, allOrder);
		OrderList orderList = new OrderList(allOrder, dealingPattern);
		orderMap = orderList.getOrderMap(new OrderKey [] {OrderKey.SELLER, OrderKey.BUYER});
		this.sheet = sheet;
		super.setOuterIdList(sellerIdList);
		super.setInnerIdList(buyerIdList);
	}

	@Override
	public Boolean doNotMarkAsFinalized(Order order) {
		return !sheet.isFinalized(order);
	}

	@Override
	public Boolean doNotMarkAsPublished(Order order) {
		return true;
	}

	@Override
	public void addToMarkFinList(Order order) {
		this.addToMarkFinIdList(order.getBuyerId());
	}
	
	@Override
	public void addToMarkPubList(Order order) {
		this.addToMarkFinIdList(order.getBuyerId());
	}

	@Override
	public Boolean doNotMarkAsOtherFinalized(Order order) {
		return true;
	}

	@Override
	public void removeToMarkFinList(Order order) {
		this.removeToMarkFinIdList(order.getBuyerId());
	}

	@Override
	public void removeToMarkPubList(Order order) {
		this.removeToMarkPubIdList(order.getBuyerId());
	}

}
