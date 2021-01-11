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
 * Jun 3, 2010		gilwen		
 */
package com.freshremix.service.impl;

import java.math.BigDecimal;

import com.freshremix.dao.SKUMaxLimitDao;
import com.freshremix.service.SKUMaxLimitService;

/**
 * @author gilwen
 *
 */
public class SKUMaxLimitServiceImpl implements SKUMaxLimitService {

	private SKUMaxLimitDao skuMaxLimitDao;
	
	/* (non-Javadoc)
	 * @see com.freshremix.service.SKULimitService#getSKUMaxLimit(java.lang.Integer, java.lang.String)
	 */
	@Override
	public BigDecimal getSKUMaxLimit(Integer skuId, String deliveryDate) {
		return skuMaxLimitDao.getSKUMaxLimit(skuId, deliveryDate);
	}

	public void setSkuMaxLimitDao(SKUMaxLimitDao skuMaxLimitDao) {
		this.skuMaxLimitDao = skuMaxLimitDao;
	}

}
