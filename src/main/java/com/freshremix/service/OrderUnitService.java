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
package com.freshremix.service;

import java.util.List;
import java.util.Map;

import com.freshremix.model.OrderUnit;

/**
 * @author Pammie
 * 
 */
public interface OrderUnitService {

	List<OrderUnit> getOrderUnitList(Integer categoryId);
	String getOrderUnitByCategoryId(Integer categoryId);
	Integer getOrderUnitCaseId(Integer categoryId);
	void insertOrderUnit(String orderUnitName, Integer categoryId);
	void deleteOrderUnit(Integer orderUnitId);
	String getOrderUnitRenderer(Integer categoryId);
	Integer getOrderUnitIdByName(String orderUnitName);
	Integer checkOrderUnitIfExist(String orderUnitName,Integer categoryId);
	Integer checkOrderUnitIfInUse(Integer orderUnitId);
	Integer checkOrderUnitInPmfSku(Integer orderUnitId);
	List<OrderUnit> getAllOrderUnit();
	void insertUOM(String orderUnitName, Integer categoryId,OrderUnit uom);
	OrderUnit getOrderUnit(Integer categoryId,String unitValue);
	String getSellingUomList(Integer categoryId);
	String getSellingUomRenderer(Integer categoryId);
	Map<Integer, OrderUnit> getAllSellingUomList();
	Integer getDefaultOrderUnit(Integer categoryId);
	OrderUnit getSellingUom(Integer categoryId, String sellinguomName);
	OrderUnit getOrderUnitById(Integer oumId);


	boolean validateQtyForDecimal(String sellerId, String skuId,
			String deliveryDate, List<Integer> sBuyer, String orderUnit,
			String sheet);
}
