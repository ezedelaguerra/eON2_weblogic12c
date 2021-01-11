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
 * Feb 17, 2010		pamela		
 */
package com.freshremix.dao.impl;

/**
 * @author Pammie
 *
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.ProductMasterFileDao;
import com.freshremix.model.PmfList;
import com.freshremix.model.PmfSkuList;
import com.freshremix.model.User;


public class ProductMasterFileDaoImpl extends SqlMapClientDaoSupport implements ProductMasterFileDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<PmfSkuList> getPmfSkus(Map param) {
		return getSqlMapClientTemplate().queryForList("ProductMasterFile.getPmfSkus", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
//	public List<PmfList> getPmfList(Integer userId){
//		return getSqlMapClientTemplate().queryForList("ProductMasterFile.getPmfList", userId);
//	}
	
	public List<PmfList> getPmfList(List<Integer> userIds){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userIds", userIds.toArray());
		return getSqlMapClientTemplate().queryForList("ProductMasterFile.getPmfList", param);
	}

	@Override
	public void insertPmfSkus(PmfSkuList pmf) {
		getSqlMapClientTemplate().insert("ProductMasterFile.insertPmfSku", pmf);
	}
	
	public Integer getMaxPmfSkuId(){
		return (Integer)getSqlMapClientTemplate().queryForObject("ProductMasterFile.getMaxPmfSkuId");
	}
	
	public Integer getMaxSkuId(){
		return (Integer)getSqlMapClientTemplate().queryForObject("ProductMasterFile.getMaxSkuId");
	}
	
	public void updatePmfSku(PmfSkuList pmf){
		getSqlMapClientTemplate().update("ProductMasterFile.updatePmfSku", pmf);
	}
	
	public void deletePmfSku(Map param){
		getSqlMapClientTemplate().delete("ProductMasterFile.deletePmfSkuById", param);
	}
	
	public Integer getMaxPmfId(){
		return (Integer)getSqlMapClientTemplate().queryForObject("ProductMasterFile.getMaxPmfId");
	}
	
	public void insertNewPmf(PmfList pmf) {
		getSqlMapClientTemplate().insert("ProductMasterFile.insertNewPmf", pmf);
	}
	
	public void deletePmf(Map param){
		getSqlMapClientTemplate().delete("ProductMasterFile.deletePmfById", param);
	}
	
	public Integer getPmfNameCount(Map param){
		return (Integer)getSqlMapClientTemplate().queryForObject("ProductMasterFile.getPmfNameCount", param);
	}
	
	public Integer getUserIdByPmfId(Map param){
		return (Integer)getSqlMapClientTemplate().queryForObject("ProductMasterFile.getUserIdByPmfId", param);
	}

	@Override
	public Integer deletePmfbyUserId(Map param) {
		return (Integer) getSqlMapClientTemplate().delete("ProductMasterFile.deletePmfbyUserId", param);
	}
}
