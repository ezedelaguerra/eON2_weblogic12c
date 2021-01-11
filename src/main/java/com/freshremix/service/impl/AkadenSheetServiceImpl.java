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
 * Apr 2, 2010		raquino		
 */
package com.freshremix.service.impl;

import java.util.List;
import java.util.Map;

import com.freshremix.model.AkadenItem;
import com.freshremix.model.AkadenSKU;
import com.freshremix.service.AkadenSheetService;

/**
 * @author raquino
 *
 */
public class AkadenSheetServiceImpl implements AkadenSheetService{

	/* (non-Javadoc)
	 * @see com.freshremix.service.AkadenSheetService#getAkadenItemsMapOfSkuDate(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Map<Integer, AkadenItem> getAkadenItemsMapOfSkuDate(
			String deliveryDate, Integer skuId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.AkadenSheetService#getAkadenItemsMapOfSkuDates(java.util.List, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Map<Integer, AkadenItem> getAkadenItemsMapOfSkuDates(
			List<String> deliveryDates, Integer skuId, Integer buyerId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.AkadenSheetService#getSelectedOrdersByBuyer(java.util.List, java.lang.Integer, java.util.List)
	 */
	@Override
	public List<Integer> getSelectedOrdersByBuyer(List<Integer> sellerIds,
			Integer buyerId, List<String> dates) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.AkadenSheetService#getSelectedOrdersByDate(java.util.List, java.util.List, java.lang.String)
	 */
	@Override
	public List<Integer> getSelectedOrdersByDate(List<Integer> sellerIds,
			List<Integer> buyerIds, String selectedDate) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.AkadenSheetService#selectDistinctSKUs(java.util.List, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<AkadenSKU> selectDistinctSKUs(List<Integer> orderIds,
			Integer categoryId, Integer sheetTypeId, Integer rowStart,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
