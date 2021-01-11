package com.freshremix.model;

import java.util.List;

import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.MessageUtil;
import com.freshremix.util.StringUtil;

public abstract class AdminFilterMarker extends FilterMarker {

	/**
	 * User is of type Admin. userList corresponds to dealing pattern
	 * of user.
	 * 
	 * Seller Admin, userList is equal to list of Seller Admin's sellers
	 * Buyer Admin, userList is equal to list of Buyer Admin's sellers
	 */
	private List<Integer> userList;
	
	public AdminFilterMarker(List<FilteredIDs> filterList,
			List<Integer> sellerIdList, List<Integer> buyerIdList,
			List<String> selectedDate, List<Order> allOrder) {
		super(filterList, sellerIdList, buyerIdList, selectedDate, allOrder);
	}

	public void setUserList(List<Integer> userList) {
		this.userList = userList;
	}

	/**
	 * Sets published marks on seller whose order is already published 
	 * to selected buyers
	 */
	@Override
	public void setPublishedMarks() {
		
		for (Integer userId : userList) {
				boolean mark = true;
				Order _order = null;
				List<Order> order = orderMap.get(userId.toString());
				if (order == null) continue;
				for (int x = 0; x < order.size(); x++) {
					_order = order.get(x);
					if (doNotMarkAsPublished(_order)) {
						removeToMarkPubList(_order);
						mark = false;
						_order = null;
						break;
					}
				}
				if (mark && _order != null)
					this.addToMarkPubList(_order);
		}
		
		String mark = MessageUtil.getPropertyMessage(MessageUtil.publishFilterMark);
		boolean hasMark = false;
		int markCount = 0;
		for (FilteredIDs filter : getFilterList()) {
			if (getMarkPubIdList().contains(Integer.valueOf(filter.getId()))) {
				filter.setMark(mark + "&nbsp;");
				hasMark = true;
				markCount++;
			}
		}
		
		if (markCount == filterList.size() - 1) {
			filterList.get(0).setMark(mark + "&nbsp;");
		}
		
		if (hasMark) {
			for (FilteredIDs filter : filterList) {
				if (StringUtil.isNullOrEmpty(filter.getMark()))
					filter.setMark("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
		}
	}
	
	/**
	 * Sets finalized marks on seller whose order is already finalized 
	 * to selected buyers
	 * 
	 * Sets finalized marks on buyer whose order is already locked 
	 * to selected sellers
	 */
	@Override
	public void setFinalizedMarks() {
		
		for (Integer userId : userList) {
				boolean mark = true;
				Order _order = null;
				List<Order> order = orderMap.get(userId.toString());
				if (order == null) continue;
				for (int x = 0; x < order.size(); x++) {
					_order = order.get(x);
					if (doNotMarkAsFinalized(_order)) {
						removeToMarkFinList(_order);
						mark = false;
						_order = null;
						break;
					}
				}
				if (mark && _order != null)
					this.addToMarkFinList(_order);
		}
		
		String mark = MessageUtil.getPropertyMessage(MessageUtil.finalizeFilterMark);
		boolean hasMark = false;
		int markCount = 0;
		for (FilteredIDs filter : getFilterList()) {
			if (getMarkFinIdList().contains(Integer.valueOf(filter.getId()))) {
				filter.setMark(mark + "&nbsp;");
				hasMark = true;
				markCount++;
			}
		}
		
		if (markCount == filterList.size() - 1) {
			filterList.get(0).setMark(mark + "&nbsp;");
		}
		
		if (hasMark) {
			for (FilteredIDs filter : filterList) {
				if (StringUtil.isNullOrEmpty(filter.getMark()))
					filter.setMark("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
		}
	}
	
	/**
	 * Sets finalized marks on seller whose order is already finalized 
	 * to selected buyers
	 * 
	 * Sets finalized marks on buyer whose order is already locked 
	 * to selected sellers
	 */
	@Override
	public void setOtherFinalizedMarks() {
		
		for (Integer userId : userList) {
				boolean mark = true;
				Order _order = null;
				List<Order> order = orderMap.get(userId.toString());
				if (order == null) continue;
				for (int x = 0; x < order.size(); x++) {
					_order = order.get(x);
					if (doNotMarkAsOtherFinalized(_order)) {
						mark = false;
						_order = null;
						break;
					}
				}
				if (mark && _order != null)
					this.addToMarkFinList(_order);
		}
		
		String mark = MessageUtil.getPropertyMessage(MessageUtil.finalizeFilterMark);
		boolean hasMark = false;
		for (FilteredIDs filter : getFilterList()) {
			if (getMarkFinIdList().contains(Integer.valueOf(filter.getId()))) {
				filter.setMark(mark + "&nbsp;");
				hasMark = true;
			}
		}
		
		if (hasMark) {
			for (FilteredIDs filter : filterList) {
				if (StringUtil.isNullOrEmpty(filter.getMark()))
					filter.setMark("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
		}
	}
	
}
