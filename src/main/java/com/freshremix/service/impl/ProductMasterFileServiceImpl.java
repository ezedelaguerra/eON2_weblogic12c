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
package com.freshremix.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freshremix.dao.ProductMasterFileDao;
import com.freshremix.model.PmfList;
import com.freshremix.model.PmfSkuList;
import com.freshremix.model.User;
import com.freshremix.service.ProductMasterFileService;

/**
 * @author Pammie
 *
 */

public class ProductMasterFileServiceImpl implements ProductMasterFileService{
	ProductMasterFileDao pmfDao;
	
	public List<PmfSkuList> getPmfSkus(Integer pmfId, Integer userId, String searchName, Integer categoryId,
			Integer rowStart, Integer pageSize){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pmfId", pmfId);
		param.put("userId", userId);
		param.put("searchName", searchName);
		param.put("categoryId", categoryId); 
		param.put("rowStart", rowStart);
		param.put("rowEnd", rowStart.intValue() + pageSize.intValue());
		return pmfDao.getPmfSkus(param);
	}
	
//	public List<PmfList> getPmfList(Integer userId){
//		return pmfDao.getPmfList(userId);
//	}
	
	public List<PmfList> getPmfList (List<Integer> userIds){
		return pmfDao.getPmfList(userIds);
	}
	
	public void insertPmfSkus(PmfSkuList pmf){
		Integer pmfSkuid = getMaxPmfSkuId();
		Integer skuId = getMaxSkuId();

		if (pmfSkuid != null){
			if(pmfSkuid > skuId){
				pmf.setSkuId(pmfSkuid + 1);
			} else {
				pmf.setSkuId(skuId + 1);
			}
		} else {
			pmf.setSkuId(skuId + 1);
		}

		pmfDao.insertPmfSkus(pmf);
	}
	
	public Integer getMaxPmfSkuId(){
		return pmfDao.getMaxPmfSkuId();
	}
	
	public Integer getMaxSkuId(){
		return pmfDao.getMaxSkuId();
	};
	
	public void updatePmfSku(PmfSkuList pmf){
		pmfDao.updatePmfSku(pmf);
	}
	
	public void deletePmfSku(Integer skuId, Integer pmfId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pmfId", pmfId);
		param.put("skuId", skuId);
		
		pmfDao.deletePmfSku(param);
	}

	public Integer getMaxPmfId(){
		return pmfDao.getMaxPmfId();
	}
	
	public Integer insertNewPmf(PmfList pmf){
		Integer pmfId = getMaxPmfId();
		if (pmfId != null){
			pmf.setPmfId(pmfId + 1);
		} else {
			pmfId = 0;
			pmf.setPmfId(pmfId + 1);
		}

		pmfDao.insertNewPmf(pmf);
		
		return pmf.getPmfId();
	}

	public void setPmfDao(ProductMasterFileDao pmfDao) {
		this.pmfDao = pmfDao;
	}
	
	public void deletePmf(Integer pmfId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pmfId", pmfId);
		
		pmfDao.deletePmf(param);
	}
	
	public Integer getPmfNameCount(Integer userId, String pmfName){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pmfName", pmfName);
		param.put("userId", userId);
		return pmfDao.getPmfNameCount(param);
	}
	
	public Integer getUserIdByPmfId(Integer pmfId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pmfId", pmfId);
		return pmfDao.getUserIdByPmfId(param);
	}

	@Override
	public Integer deletePmfbyUserId(Integer userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return pmfDao.deletePmfbyUserId(param);
	}
}
