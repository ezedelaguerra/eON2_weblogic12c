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
package com.freshremix.service;

import java.math.BigDecimal;

/**
 * @author gilwen
 *
 */
public interface SKUMaxLimitService {

	BigDecimal getSKUMaxLimit(Integer skuId, String deliveryDate);
	
}
