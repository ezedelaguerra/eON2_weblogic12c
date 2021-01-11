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
 * May 31, 2010		nvelasquez		
 */
package com.freshremix.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.SKUSortDao;
import com.freshremix.model.SKUColumn;
import com.freshremix.model.SKUSort;

/**
 * @author nvelasquez
 *
 */
public class SKUSortDaoImpl extends SqlMapClientDaoSupport implements SKUSortDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SKUColumn> getDefaultColumns(List<Integer> excludeCols) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("excludeCols", excludeCols);
		return getSqlMapClientTemplate().queryForList("SKUSort.getDefaultColumns", param);
	}
	
	@Override
	public SKUSort getSKUSort(Integer userId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		return (SKUSort) getSqlMapClientTemplate().queryForObject("SKUSort.getSortSKU", param);
	}
	
	@Override
	public Integer insertSortSKU(Integer userId, String skuColumnIds) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("skuColumnIds", skuColumnIds);
		return (Integer) getSqlMapClientTemplate().insert("SKUSort.insertSortSKU", param);
	}
	
	@Override
	public void deleteSortSKU(Integer userId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		getSqlMapClientTemplate().delete("SKUSort.deleteSortSKU", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SKUColumn> getAllColumns() {
		return getSqlMapClientTemplate().queryForList("SKUSort.getAllColumns");
	}

}
