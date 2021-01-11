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
 * Apr 13, 2010		Pammie		
 */
package com.freshremix.model;

import com.freshremix.util.NumberUtil;

/**
 * @author Pammie
 *
 */
public class OrderUnit {
	private Integer orderUnitId;
	private String orderUnitName;
	private Integer categoryId;
	
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getOrderUnitId() {
		return orderUnitId;
	}
	public void setOrderUnitId(Integer orderUnitId) {
		this.orderUnitId = orderUnitId;
	}
	
	public String getOrderUnitName() {
		return orderUnitName;
	}
	public void setOrderUnitName(String orderUnitName) {
		this.orderUnitName = orderUnitName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((orderUnitId == null) ? 0 : orderUnitId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderUnit other = (OrderUnit) obj;
		if (orderUnitId == null) {
			if (other.orderUnitId != null)
				return false;
		} else if (!equally(orderUnitId,other.orderUnitId))
			return false;
		return true;
	}
	
	private boolean equally (Integer value1, Integer value2) {
		return NumberUtil.nullToZero(value1).equals(NumberUtil.nullToZero(value2));
	}
}
