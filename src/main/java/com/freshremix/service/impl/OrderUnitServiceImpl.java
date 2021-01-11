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
package com.freshremix.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freshremix.constants.CategoryConstants;
import com.freshremix.dao.CategoryDao;
import com.freshremix.dao.OrderUnitDao;
import com.freshremix.model.OrderItem;
import com.freshremix.model.OrderUnit;
import com.freshremix.service.AllocationSheetService;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.OrderUnitService;
import com.freshremix.util.OrderUnitUtility;

/**
 * @author Pammie
 *
 */
public class OrderUnitServiceImpl implements OrderUnitService {
	private OrderUnitDao orderUnitDao;
	private CategoryDao categoryDaos;
	private OrderSheetService orderSheetService;
	private AllocationSheetService allocationSheetService;
	
	

	public void setOrderSheetService(OrderSheetService orderSheetService) {
		this.orderSheetService = orderSheetService;
	}

	public void setAllocationSheetService(
			AllocationSheetService allocationSheetService) {
		this.allocationSheetService = allocationSheetService;
	}

	public void setCategoryDaos(CategoryDao categoryDaos) {
		this.categoryDaos = categoryDaos;
	}

	public void setOrderUnitDao(OrderUnitDao orderUnitDao) {
		this.orderUnitDao = orderUnitDao;
	}
	
	@Override
	public List<OrderUnit> getOrderUnitList(Integer categoryId) {
		return orderUnitDao.getOrderUnitList(categoryId);
	}
	
	@Override
	public void insertOrderUnit(String orderUnitName,Integer categoryId) {
		orderUnitDao.insertOrderUnit(orderUnitName,categoryId);
	}
	
	@Override
	public void deleteOrderUnit(Integer orderUnitId) {
		orderUnitDao.deleteOrderUnit(orderUnitId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderUnitService#getOrderUnitByCategoryId(java.lang.Integer)
	 */
	@Override
	public String getOrderUnitByCategoryId(Integer categoryId) {
		List<OrderUnit> list = orderUnitDao.getOrderUnitByCategoryId(categoryId);
		StringBuffer sb = new StringBuffer();
		sb.append("{'0' : ' '");
		for (int i=0; i<list.size(); i++) {
			OrderUnit orderUnit = list.get(i);
			sb.append(",'" + orderUnit.getOrderUnitId() + "':'" + orderUnit.getOrderUnitName() + "'");
		}
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public Integer getOrderUnitCaseId(Integer categoryId) {
		Integer id = orderUnitDao.getOrderUnitCaseId(categoryId);
		if (id == null) id = 99;
		return id;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderUnitService#getOrderUnitRenderer(java.lang.Integer)
	 */
	@Override
	public String getOrderUnitRenderer(Integer categoryId) {
		StringBuffer sb = new StringBuffer();
		String delimeter = "";
		List<OrderUnit> list = orderUnitDao.getOrderUnitByCategoryId(categoryId);
		sb.append("{ 'unitorderRenderer' : ");
		sb.append("[ { \\\"id\\\" : \\\"0\\\", \\\"caption\\\" : \\\" \\\" },");
		for (int i=0; i<list.size(); i++) {
			OrderUnit ou = list.get(i);
			sb.append(" { \\\"id\\\" : \\\"" + ou.getOrderUnitId() + "\\\", \\\"caption\\\" : \\\"" + ou.getOrderUnitName() + "\\\" }");
			if (i < list.size() -1) sb.append(",");
		}
		
		sb.append(" ]" + delimeter + "}" );
		
		return sb.toString();
	}
	
	public Integer getOrderUnitIdByName(String orderUnitName){
		return orderUnitDao.getOrderUnitIdByName(orderUnitName);
	}
	
	public Integer checkOrderUnitIfExist(String orderUnitName,Integer categoryId){
		return orderUnitDao.checkOrderUnitIfExist(orderUnitName,categoryId);
	}
	
	public Integer checkOrderUnitIfInUse(Integer orderUnitId){
		return orderUnitDao.checkOrderUnitIfInUse(orderUnitId);
	}
	
	public Integer checkOrderUnitInPmfSku(Integer orderUnitId){
		return orderUnitDao.checkOrderUnitInPmfSku(orderUnitId);
	}

	@Override
	public List<OrderUnit> getAllOrderUnit() {
		return orderUnitDao.getAllOrderUnit();
	}

	@Override
	public void insertUOM(String orderUnitName, Integer categoryId,
			OrderUnit uom) {
		Integer uomId = orderUnitDao.insertUOM(orderUnitName, categoryId);
		uom.setCategoryId(categoryId);
		uom.setOrderUnitName(orderUnitName);
		uom.setOrderUnitId(uomId);
	}

	@Override
	public OrderUnit getOrderUnit(Integer categoryId, String unitValue) {
		List<OrderUnit> unit = orderUnitDao.getOrderUnitList(categoryId);
		OrderUnit value = new OrderUnit(); 
		for(OrderUnit uom : unit ){
			if(uom.getOrderUnitName().equals(unitValue)){
				value =uom;
			}
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderUnitService#getSellingUomList(java.lang.Integer)
	 */
	@Override
	public String getSellingUomList(Integer categoryId) {
		List<OrderUnit> list = orderUnitDao.getSellingUomList(categoryId);
		StringBuffer sb = new StringBuffer();
		sb.append("{'0' : ' '");
		for (int i=0; i<list.size(); i++) {
			OrderUnit orderUnit = list.get(i);
			sb.append(",'" + orderUnit.getOrderUnitId() + "':'" + orderUnit.getOrderUnitName() + "'");
		}
		sb.append("}");
		return sb.toString();
		
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderUnitService#getSellingUomRenderer(java.lang.Integer)
	 */
	@Override
	public String getSellingUomRenderer(Integer categoryId) {

		StringBuffer sb = new StringBuffer();
		String delimeter = "";
		List<OrderUnit> list = orderUnitDao.getSellingUomList(categoryId);
		sb.append("{ 'sellingUomRenderer' : ");
		sb.append("[ { \\\"id\\\" : \\\"0\\\", \\\"caption\\\" : \\\" \\\" },");
		for (int i=0; i<list.size(); i++) {
			OrderUnit ou = list.get(i);
			sb.append(" { \\\"id\\\" : \\\"" + ou.getOrderUnitId() + "\\\", \\\"caption\\\" : \\\"" + ou.getOrderUnitName() + "\\\" }");
			if (i < list.size() -1) sb.append(",");
		}
		
		sb.append(" ]" + delimeter + "}" );
		
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderUnitService#getAllSellingUomList()
	 */
	@Override
	public Map<Integer, OrderUnit> getAllSellingUomList() {
		List<OrderUnit> sellingUomList = new ArrayList<OrderUnit>();
		Map<Integer, OrderUnit> sellingUomMap = new HashMap<Integer, OrderUnit>();
		sellingUomList = orderUnitDao.getAllSellingUomList();
		for (OrderUnit sellingUom: sellingUomList){
			sellingUomMap.put(sellingUom.getOrderUnitId(), sellingUom);
		}
		
		return sellingUomMap;
	}

	@Override
	public Integer getDefaultOrderUnit(Integer categoryId) {
		
		Integer id = null;
		List<OrderUnit> list = orderUnitDao.getOrderUnitByCategoryId(categoryId);
		for (int i=0; i<list.size(); i++) {
			OrderUnit orderUnit = list.get(i);
			
			if(categoryId.equals(CategoryConstants.FRUITS) ||
				categoryId.equals(CategoryConstants.VEGETABLES)){
				if (orderUnit.getOrderUnitName().equalsIgnoreCase("C/S")){
					id = orderUnit.getOrderUnitId();
					break;
				}
			} else if(categoryId.equals(CategoryConstants.FISH) ||
					categoryId.equals(CategoryConstants.MEAT) ||
					categoryId.equals(CategoryConstants.DELICA)){
				if (orderUnit.getOrderUnitName().equalsIgnoreCase("KG")){
					id = orderUnit.getOrderUnitId();
					break;
				}
			} else {
				id = orderUnit.getOrderUnitId();
				break;
			}
		}
		
		if (id == null) id = 99;
		return id;
	}
	

	
	@Override
	public OrderUnit getSellingUom(Integer categoryId, String sellinguomName) {
		List<OrderUnit> list = orderUnitDao.getSellingUomList(categoryId);
		for(OrderUnit uom : list ){
			if(uom.getOrderUnitName().equals(sellinguomName)){
				return uom;
			}
		}
		return null;
	}
	
	@Override
	public OrderUnit getOrderUnitById(Integer oumId) {
		List<OrderUnit> list = orderUnitDao.getAllOrderUnit();
		for(OrderUnit uom : list ){
			if(uom.getOrderUnitId().intValue()== oumId.intValue()){
				return uom;
			}
		}
		return null;
	}
	
	/**
	 * Validates if order qty is allowed to have decimal values
	 * 
	 * @param sellerId
	 * @param skuId
	 * @param deliveryDate
	 * @param sBuyer 
	 * @param orderUnit
	 * @param sheet  - order or allocation
	 * 
	 * @return boolean 
	 * 
	 */
	@Override
	public boolean validateQtyForDecimal(String sellerId,String skuId, String deliveryDate,List<Integer> sBuyer,
			String orderUnit, String sheet) {
		boolean isValid = true;
		if (OrderUnitUtility.isDecimalNotAllowed(orderUnit) ) {

			List<OrderItem> oi = new ArrayList<OrderItem>();
			if(sheet.equals("order")){
				oi = orderSheetService.getOrderItem(Integer.valueOf(sellerId), deliveryDate, Integer.valueOf(skuId));
			}else{
				oi = allocationSheetService.getOrderItem(Integer.valueOf(sellerId), deliveryDate, Integer.valueOf(skuId));

			}
			if (oi != null) {
				for (OrderItem _oi : oi) {
					if(_oi.getQuantity() != null){
						String str = _oi.getQuantity().toPlainString();
						if (!sBuyer.contains(_oi.getOrder().getBuyerId()) &&
							str.indexOf(".") > -1) {
							isValid = false;
							break;
						}
					}
				}
			}
		}
		return isValid;
	}
	
	
}
