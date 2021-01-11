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
 * Apr 13, 2010		Pamela		
 */
package com.freshremix.dao;

import java.util.List;

import com.freshremix.model.OrderUnit;

/**
 * @author Pammie
 * 
 */
public interface OrderUnitDao {

	List<OrderUnit> getOrderUnitList(Integer categoryId);
	List<OrderUnit> getOrderUnitByCategoryId(Integer categoryId);
	Integer getOrderUnitCaseId(Integer categoryId);
	void insertOrderUnit(String orderUnitName, Integer categoryId);
	void deleteOrderUnit(Integer orderUnitId);
	Integer getOrderUnitIdByName(String orderUnitName);
	Integer checkOrderUnitIfExist(String orderUnitName,Integer categoryId);
	Integer checkOrderUnitIfInUse(Integer orderUnitId);
	Integer checkOrderUnitInPmfSku(Integer orderUnitId);
	Integer insertUOM(String orderUnitName, Integer categoryId);
	List<OrderUnit> getAllOrderUnit();
	List<OrderUnit> getSellingUomList(Integer categoryId);
	List<OrderUnit> getAllSellingUomList();
}
