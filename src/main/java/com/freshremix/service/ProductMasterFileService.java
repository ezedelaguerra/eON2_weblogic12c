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
package com.freshremix.service;

import java.util.List;

import com.freshremix.model.PmfList;
import com.freshremix.model.PmfSkuList;
import com.freshremix.model.User;

/**
 * @author pamela
 *
 */
public interface ProductMasterFileService {
	List<PmfSkuList> getPmfSkus(Integer pmfId, Integer pmfUserId, String searchName, Integer categoryId, Integer rowStart, Integer pageSize);
	List<PmfList> getPmfList (List<Integer> pmfUserIds); // (Integer pmfUserId)
	void insertPmfSkus(PmfSkuList pmfSku);
	Integer getMaxPmfSkuId();
	Integer getMaxSkuId();
	void updatePmfSku(PmfSkuList pmf);
	void deletePmfSku(Integer skuId, Integer pmfId);
	Integer getMaxPmfId();
	Integer insertNewPmf(PmfList pmf);
	void deletePmf(Integer pmfId);
	Integer getPmfNameCount(Integer userId, String pmfName);
	Integer getUserIdByPmfId(Integer pmfId);
	Integer deletePmfbyUserId(Integer userId);
}
