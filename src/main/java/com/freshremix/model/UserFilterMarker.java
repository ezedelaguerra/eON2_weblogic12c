package com.freshremix.model;

import java.util.List;

import com.freshremix.ui.model.FilteredIDs;
import com.freshremix.util.MessageUtil;
import com.freshremix.util.StringUtil;

/**
 *	Abstract filter marker for Seller and Buyer filter
 */
public abstract class UserFilterMarker extends FilterMarker {

	private List<Integer> outerIdList;
	private List<Integer> innerIdList;
	
	public UserFilterMarker(List<FilteredIDs> filterList,
			List<Integer> sellerIdList, List<Integer> buyerIdList,
			List<String> selectedDate, List<Order> allOrder) {
		super(filterList, sellerIdList, buyerIdList, selectedDate, allOrder);
	}

	public List<Integer> getOuterIdList() {
		return outerIdList;
	}

	public void setOuterIdList(List<Integer> outerIdList) {
		this.outerIdList = outerIdList;
	}

	public List<Integer> getInnerIdList() {
		return innerIdList;
	}

	public void setInnerIdList(List<Integer> innerIdList) {
		this.innerIdList = innerIdList;
	}

	public void setPublishedMarks() {
		
		for (Integer outerId : outerIdList) {
			for (Integer innerId : innerIdList) {
				boolean mark = true;
				Order _order = null;
				List<Order> order = orderMap.get(outerId.toString() + innerId.toString());
				if (order == null || order.size() == 0) continue;
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
		}
		String mark = MessageUtil.getPropertyMessage(MessageUtil.publishFilterMark);
		boolean hasMark = false;
		int markCount = 0;
		for (FilteredIDs filter : filterList) {
			if (markPubIdList.contains(Integer.valueOf(filter.getId()))) {
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
	
	public void setFinalizedMarks() {
		
		for (Integer outerId : outerIdList) {
			for (Integer innerId : innerIdList) {
				boolean mark = true;
				Order _order = null;
				List<Order> order = orderMap.get(outerId.toString() + innerId.toString());
				if (order == null || order.size() == 0) continue;
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
		}
		
		String mark = MessageUtil.getPropertyMessage(MessageUtil.finalizeFilterMark);
		boolean hasMark = false;
		int markCount = 0;
		for (FilteredIDs filter : filterList) {
			if (markFinIdList.contains(Integer.valueOf(filter.getId()))) {
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
	
	public void setOtherFinalizedMarks() { }

}
