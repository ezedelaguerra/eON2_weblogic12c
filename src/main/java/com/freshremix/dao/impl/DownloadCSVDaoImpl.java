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
 * 2010/05/17		Jovino Balunan		
 */
package com.freshremix.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.DownloadCSVDao;
import com.freshremix.model.AkadenItem;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.OrderItem;
import com.freshremix.model.Sheets;

/**
 * @author Jovino Balunan
 *
 */
public class DownloadCSVDaoImpl extends SqlMapClientDaoSupport implements DownloadCSVDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AkadenSKU> loadSellerAkadenCSVDownload(List<Integer> orderIds, Integer categoryId,
			Integer sheetTypeId, String hasQuantity) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("categoryId", categoryId);
		param.put("sheetTypeId", sheetTypeId);
		param.put("orderIds", orderIds);
		return getSqlMapClientTemplate().queryForList("downloadCSV.selectSellerAkadenDistinctSKUs", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getBuyerCSVUsersList(List<Integer> userIds) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("userId", userIds);
		return (List<String>) getSqlMapClientTemplate().queryForList("downloadCSV.getBuyerCSVUsersList", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, OrderItem> getSellerOrderItemsMapOfSkuDates(
			String deliveryDates, Integer skuId, Integer buyerId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDates", deliveryDates);
		param.put("skuId", skuId);
		param.put("buyerId", buyerId);
		return getSqlMapClientTemplate().queryForMap("downloadCSV.getSellerOrderItemsMapOfSkuDates", param, "order.buyerId");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, AkadenItem> getSellerAkadenItemsMapOfSkuDate(
			String deliveryDates, Integer buyerId, Integer akadenSKUId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deliveryDate", deliveryDates);
		param.put("buyerId", buyerId);
		param.put("akadenSkuId", akadenSKUId);
		return getSqlMapClientTemplate().queryForMap("downloadCSV.getSellerAkadenItemsMapOfSkuDate", param, "order.buyerId");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Sheets> getSheetType(Integer roleId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("roleId", roleId);
		return getSqlMapClientTemplate().queryForList("sheetType.getSheetType", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sheets> getAllSheetType() {
		return getSqlMapClientTemplate().queryForList("sheetType.getAllSheetType");
	}

	@Override
	public Sheets getSheetType(Long roleId, String desc) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("roleId", roleId);
		param.put("description", desc);
		return (Sheets) getSqlMapClientTemplate().queryForObject("sheetType.getSheetTypeByRoleAndDesc", param);
	}
}
