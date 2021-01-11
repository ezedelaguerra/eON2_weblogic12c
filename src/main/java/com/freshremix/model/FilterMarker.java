package com.freshremix.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.freshremix.ui.model.FilteredIDs;
/**
 * Abstarct marker for Seller, Buyer, SA and BA users
 */
public abstract class FilterMarker {

	protected List<FilteredIDs> filterList; 
	protected List<Integer> sellerIdList; 
	protected List<Integer> buyerIdList; 
	protected List<String> selectedDate;
	protected List<Order> allOrder;
	protected List<Integer> markFinIdList;
	protected List<Integer> markPubIdList;
	protected Map<String, List<Order>> orderMap;
	protected SheetState sheet;
	
	public FilterMarker(
				List<FilteredIDs> filterList, 
				List<Integer> sellerIdList, 
				List<Integer> buyerIdList, 
				List<String> selectedDate,
				List<Order> allOrder) {
		
		markFinIdList = new ArrayList<Integer>();
		markPubIdList = new ArrayList<Integer>();
		this.filterList = filterList;
		this.sellerIdList = sellerIdList;
		this.buyerIdList = buyerIdList;
		this.selectedDate = selectedDate;
		this.allOrder = allOrder;
	}
	
	public List<FilteredIDs> getFilterList() {
		return filterList;
	}
	public List<Integer> getSellerIdList() {
		return sellerIdList;
	}
	public List<Integer> getBuyerIdList() {
		return buyerIdList;
	}
	public List<String> getSelectedDate() {
		return selectedDate;
	}
	public List<Order> getAllOrder() {
		return allOrder;
	}
	
	public List<Integer> getMarkFinIdList() {
		return this.markFinIdList;
	}
	
	public List<Integer> getMarkPubIdList() {
		return this.markPubIdList;
	}
	
	public void addToMarkFinIdList(Integer userId) {
		markFinIdList.add(userId);
	}
	
	public void addToMarkPubIdList(Integer userId) {
		markPubIdList.add(userId);
	}
	
	public void removeToMarkFinIdList(Integer userId) {
		markFinIdList.remove(userId);
	}
	
	public void removeToMarkPubIdList(Integer userId) {
		markPubIdList.remove(userId);
	}
	
	public abstract void setPublishedMarks();
	public abstract void setFinalizedMarks();
	public abstract void setOtherFinalizedMarks();
	public abstract Boolean doNotMarkAsPublished(Order order);
	public abstract Boolean doNotMarkAsFinalized(Order order);
	public abstract Boolean doNotMarkAsOtherFinalized(Order order);
	public abstract void addToMarkFinList(Order order);
	public abstract void addToMarkPubList(Order order);
	public abstract void removeToMarkFinList(Order order);
	public abstract void removeToMarkPubList(Order order);
}