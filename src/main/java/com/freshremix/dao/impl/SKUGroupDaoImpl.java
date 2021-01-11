package com.freshremix.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.SKUGroupDao;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.SKUGroupList;
import com.freshremix.model.SortSKUGroup;

public class SKUGroupDaoImpl extends SqlMapClientDaoSupport
	implements SKUGroupDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SKUGroup> getAllSKUGroup(Integer sellerId, Integer categoryId) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("sellerId", sellerId);
		params.put("categoryId", categoryId);
		return getSqlMapClientTemplate().queryForList("SKUGroup.getAllSkuGroup", params);
	}

	@Override
	public void saveSKUGroup(SKUGroup skuGroup) {
		Integer ireturn = (Integer) getSqlMapClientTemplate().insert("SKUGroup.insertSkuGroup", skuGroup);
		skuGroup.setSkuGroupId(ireturn);
		getSqlMapClientTemplate().update("SKUGroup.updateOrigSkuGroupID", skuGroup);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.SKUGroupDao#getSKUGroupList(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SKUGroup> getSKUGroupList(Integer sellerId, Integer categoryId) {
		Map<String,Integer> param = new HashMap<String,Integer>();
		param.put("sellerId", sellerId);
		param.put("categoryId", categoryId);
		return getSqlMapClientTemplate().queryForList("SKUGroup.getSKUGroupListBySellerId", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.SKUGroupDao#updateSkuGroup(com.freshremix.model.SKUGroup)
	 */
	@Override
	public void updateSkuGroup(SKUGroup skuGroup) {
		Map<String,Integer> param = new HashMap<String,Integer>();
		param.put("skuGroupId", skuGroup.getSkuGroupId());
		getSqlMapClientTemplate().update("SKUGroup.updateSkuGroupToExpire", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.SKUGroupDao#insertSkuGroupToUpdateName(com.freshremix.model.SKUGroup)
	 */
	@Override
	public void insertSkuGroupToUpdateName(SKUGroup skuGroup) {
		getSqlMapClientTemplate().insert("SKUGroup.insertSkuGroupToUpdateName", skuGroup);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.SKUGroupDao#getSKUGroupList(java.util.List, java.lang.Integer)
	 */
	@Override
	public List<SortSKUGroup> getSKUGroupList(List<Integer> sellerIds,
			Integer categoryId, Integer userId) {

		Map<String, Object> param = new HashMap<String,Object>();
		param.put("sellerIds", sellerIds);
		param.put("skuCategoryId", categoryId);
		param.put("userId", userId);
		return getSqlMapClientTemplate().queryForList("SKUGroup.getSKUGroupListBySellerIds", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.SKUGroupDao#deleteSkuGroup(java.util.List)
	 */
	@Override
	public void deleteSKUGroup(List<Integer> historyIds) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("skuGroupIds", historyIds);
		getSqlMapClientTemplate().delete("SKUGroup.deleteSortSKUGroup", param);
		getSqlMapClientTemplate().delete("SKUGroup.deleteSkuGroup", param);
	}
	
	@SuppressWarnings("unchecked")
	public List getAllSKUGroupList(Integer sellerId) {
		return getSqlMapClientTemplate().queryForList("SKUGroup.getAllSkuGroupList", sellerId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.SKUGroupDao#getSKUGroupNameCount(com.freshremix.model.SKUGroup)
	 */
	@Override
	public Integer getSKUGroupNameCount(SKUGroup skuGroup) {
		return (Integer) getSqlMapClientTemplate().queryForObject("SKUGroup.getSKUGroupNameCount", skuGroup);
	}

	@Override
	public SKUGroup getSKUgroup(Integer sellerId, String grpName,Integer categoryId) {
		 Map<String,Object> param = new HashMap<String, Object>();
		 param.put("sellerId", sellerId);
		 param.put("description", grpName);
		 param.put("categoryId", categoryId);
		 return (SKUGroup)getSqlMapClientTemplate().queryForObject("SKUGroup.getSpecificGroup", param);
	}
}
