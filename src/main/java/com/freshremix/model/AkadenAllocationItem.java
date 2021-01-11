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
 * Apr 15, 2010		Jovino Balunan		
 */
package com.freshremix.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Jovino Balunan
 *
 */
public class AkadenAllocationItem {
	private Integer akadenItemId;
	private Order order;
	private SKU sku;
	private Integer updateBy;
	private Date updateTimestamp; 
	private String comments;
	private AkadenSKU akadenSku;
	private String isNewSku;
	private BigDecimal quantity;
}
