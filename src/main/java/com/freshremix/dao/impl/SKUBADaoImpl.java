package com.freshremix.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.SKUBADao;
import com.freshremix.model.SKUBA;
import com.freshremix.util.CollectionUtil;
import com.freshremix.util.NumberUtil;

public class SKUBADaoImpl extends SqlMapClientDaoSupport implements SKUBADao {

	private static final Logger LOGGER = Logger.getLogger(SKUBADaoImpl.class);
	@Override
	public SKUBA findSKUBA(Integer skuBuyerId) {
		if (skuBuyerId == null) {
			return null;
		}
		return (SKUBA) getSqlMapClientTemplate().queryForObject("SKUBA.selectSKUBA", skuBuyerId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SKUBA> findSKUBA(List<Integer> skuBAIds) {
		if (CollectionUtils.isEmpty(skuBAIds)) {
			return null;
		}
		List<SKUBA> consolidatedResult = new ArrayList<SKUBA>(skuBAIds.size());
		List<List<?>> batches = CollectionUtil.splitList(skuBAIds, 1000);
		LOGGER.info("SKUBAID Batch size:"+batches.size());
		for (List<?> list : batches) {
			List<SKUBA> batchResult = (List<SKUBA>) getSqlMapClientTemplate().queryForList("SKUBA.selectSKUBAList", list);
			if (CollectionUtils.isNotEmpty(batchResult)){
				consolidatedResult.addAll(batchResult);
			}
		}
		return consolidatedResult;
	}
	
	@Override
	public Integer insertSKUBA(SKUBA skuBuyer) {
		if (skuBuyer == null) {
			return null;
		}
		if (skuBuyer.getSellingUom() != null &&
			NumberUtil.nullToZero(skuBuyer.getSellingUom().getOrderUnitId()) == 0)
			skuBuyer.getSellingUom().setOrderUnitId(null);
		
		Integer ireturn = (Integer) getSqlMapClientTemplate().insert("SKUBA.insertSKUBA", skuBuyer);
		return ireturn;
	}

	@Override
	public void insertSKUBA2(SKUBA skuBuyer) {
		Integer id = insertSKUBA(skuBuyer);
		skuBuyer.setSkuBAId(id);
	}

}
