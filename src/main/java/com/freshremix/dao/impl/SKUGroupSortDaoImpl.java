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

import com.freshremix.dao.SKUGroupSortDao;
import com.freshremix.model.SKUGroupSort;

/**
 * @author nvelasquez
 *
 */
public class SKUGroupSortDaoImpl extends SqlMapClientDaoSupport implements SKUGroupSortDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SKUGroupSort> getSKUGroupSort(List<Integer> sellerIds, Integer skuCategoryId, Integer userId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerIds", sellerIds);
		param.put("skuCategoryId", skuCategoryId);
		param.put("userId", userId);
		return getSqlMapClientTemplate().queryForList("SKUGroupSort.getSortSKUGroup", param);
	}
	
	@Override
	public void insertSortSKUGroup(Integer userId, Integer skuCategoryId,
			Integer skuGroupId, Integer sorting) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("skuCategoryId", skuCategoryId);
		param.put("skuGroupId", skuGroupId);
		param.put("sorting", sorting);
		getSqlMapClientTemplate().insert("SKUGroupSort.insertSortSKUGroup", param);
	}
	
	@Override
	public void deleteSortSKUGroup(Integer userId, Integer skuCategoryId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("skuCategoryId", skuCategoryId);
		getSqlMapClientTemplate().delete("SKUGroupSort.deleteSortSKUGroup", param);
	}

}
