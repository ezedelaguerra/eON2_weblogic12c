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
package com.freshremix.dao;

import java.util.List;
import java.util.Map;

import com.freshremix.model.PmfList;
import com.freshremix.model.PmfSkuList;

/**
 * @author Pammie
 *
 */

public interface ProductMasterFileDao {

	List<PmfSkuList> getPmfSkus(Map param);
	List<PmfList> getPmfList(List<Integer> userIds); //(Integer userId)
	void insertPmfSkus(PmfSkuList pmf);
	Integer getMaxPmfSkuId();
	Integer getMaxSkuId();
	void updatePmfSku(PmfSkuList pmf);
	void deletePmfSku(Map param);
	Integer getMaxPmfId();
	void insertNewPmf(PmfList pmf);
	void deletePmf(Map param);
	Integer getPmfNameCount(Map param);
	Integer getUserIdByPmfId(Map param);
	Integer deletePmfbyUserId(Map param);
}
