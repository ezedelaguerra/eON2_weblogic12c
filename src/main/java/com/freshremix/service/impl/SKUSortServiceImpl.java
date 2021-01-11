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
package com.freshremix.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SKUColumnConstants;
import com.freshremix.dao.SKUSortDao;
import com.freshremix.model.SKUColumn;
import com.freshremix.model.SKUSort;
import com.freshremix.service.SKUSortService;
import com.freshremix.util.StringUtil;

/**
 * @author nvelasquez
 *
 */
public class SKUSortServiceImpl implements SKUSortService {
	
	private SKUSortDao skuSortDao;

	public void setSkuSortDao(SKUSortDao skuSortDao) {
		this.skuSortDao = skuSortDao;
	}
	
	@Override
	public List<SKUColumn> getDefaultColumns(Long roleId) {
		List<Integer> excludeCols = new ArrayList<Integer>();
		excludeCols.add(SKUColumnConstants.ID_SKU_ID);
		//excludeCols.add(SKUSortColumnNames.commentKey);
		if (roleId.equals(RoleConstants.ROLE_BUYER)) {
			excludeCols.add(SKUColumnConstants.ID_PRICE1);
			excludeCols.add(SKUColumnConstants.ID_PRICE2);
		}
		else if (roleId.equals(RoleConstants.ROLE_BUYER_ADMIN)) {
			excludeCols.add(SKUColumnConstants.ID_PRICE1);
		}else if (roleId.equals(RoleConstants.ROLE_SELLER_ADMIN) ||
				roleId.equals(RoleConstants.ROLE_SELLER)) {
			excludeCols.add(SKUColumnConstants.ID_PURCHASE_PRICE);
			excludeCols.add(SKUColumnConstants.ID_SELLING_PRICE);
			excludeCols.add(SKUColumnConstants.ID_SELLING_UOM);
			excludeCols.add(SKUColumnConstants.ID_SKU_COMMENT);
		}
		List <SKUColumn> defaultCols = skuSortDao.getDefaultColumns(excludeCols);
		return defaultCols;
	}
	
	@Override
	public List<SKUColumn> getSKUSort(Integer userId, List<SKUColumn> defCols) {
		List<Integer> iColumnIds = new ArrayList<Integer>();
		List<SKUColumn> defColsCopy = new ArrayList<SKUColumn>(defCols);
		List<SKUColumn> sortCols = new ArrayList<SKUColumn>();
		SKUSort skuSort = skuSortDao.getSKUSort(userId);
		
		if (skuSort != null &&
				!StringUtil.isNullOrEmpty(skuSort.getSkuColumnIds())) {
			String skuColumnIds = skuSort.getSkuColumnIds();
			if (skuColumnIds.indexOf(",") != -1) {
				StringTokenizer st = new StringTokenizer(skuColumnIds,",");
				while(st.hasMoreTokens()) {
					iColumnIds.add(Integer.valueOf(st.nextToken().trim()));
				}
			}
			else {
				iColumnIds.add(Integer.valueOf(skuColumnIds.trim()));
			}
			
			for (Integer columnId : iColumnIds) {
				SKUColumn sortCol = null;
				for (int i=0; i<defColsCopy.size(); i++) {
					SKUColumn defCol = (SKUColumn) defColsCopy.get(i);
					Integer defColId = defCol.getSkuColumnId();
					if (columnId.equals(defColId)) {
						sortCol = new SKUColumn();
						sortCol.setSkuColumnId(defColId);
						sortCol.setHeaderKey(defCol.getHeaderKey());
						sortCol.setHeaderSheet(defCol.getHeaderSheet());
						defCols.remove(defCol);
						break;
					}
				}
				if (sortCol != null)
					sortCols.add(sortCol);
			}
		}
		
		return sortCols;
	}
	
	@Override
	public List<Integer> getSKUSort(Integer userId) {
		List<Integer> skuSortOrder = new ArrayList<Integer>();
		
		SKUSort skuSort = skuSortDao.getSKUSort(userId);
		if (skuSort != null &&
				!StringUtil.isNullOrEmpty(skuSort.getSkuColumnIds())) {
			String skuColumnIds = skuSort.getSkuColumnIds();
			if (skuColumnIds.indexOf(",") != -1) {
				StringTokenizer st = new StringTokenizer(skuColumnIds,",");
				while(st.hasMoreTokens()) {
					skuSortOrder.add(Integer.valueOf(st.nextToken().trim()));
				}
			}
			else {
				skuSortOrder.add(Integer.valueOf(skuColumnIds.trim()));
			}
			
		}
		
		return skuSortOrder;
	}
	
	@Override
	public Integer insertSortSKU(List<SKUColumn> skuSorts, Integer userId) {
		this.deleteSortSKU(userId);
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<skuSorts.size(); i++) {
			SKUColumn skuSort = skuSorts.get(i);
			Integer skuColumnId = skuSort.getSkuColumnId();
			sb.append(skuColumnId.toString());
			if (i < skuSorts.size()-1)
				sb.append(",");
		}
		return skuSortDao.insertSortSKU(userId, sb.toString());
		
	}

	@Override
	public void deleteSortSKU(Integer userId) {
		skuSortDao.deleteSortSKU(userId);		
	}

	public List<Integer> getDefaultList() {
		List<Integer> defaultList = new ArrayList<Integer>();
		defaultList.add(SKUColumnConstants.ID_COMPANY_NAME);
		defaultList.add(SKUColumnConstants.ID_SELLER_NAME);
		defaultList.add(SKUColumnConstants.ID_GROUP_NAME);
		defaultList.add(SKUColumnConstants.ID_SKU_NAME);
		defaultList.add(SKUColumnConstants.ID_MARKET_CONDITION);
		defaultList.add(SKUColumnConstants.ID_AREA_PRODUCTION);
		defaultList.add(SKUColumnConstants.ID_CLASS1);
		defaultList.add(SKUColumnConstants.ID_CLASS2);
		defaultList.add(SKUColumnConstants.ID_PACKAGE_QTY);
		defaultList.add(SKUColumnConstants.ID_PACKAGE_TYPE);
		defaultList.add(SKUColumnConstants.ID_PRICE1);
		defaultList.add(SKUColumnConstants.ID_PRICE2);
		defaultList.add(SKUColumnConstants.ID_PRICE_WO_TAX);
		defaultList.add(SKUColumnConstants.ID_PRICE_W_TAX);
		defaultList.add(SKUColumnConstants.ID_UOM);
		defaultList.add(SKUColumnConstants.ID_EXTERNAL_ID);
		defaultList.add(SKUColumnConstants.ID_SELLING_PRICE);
		defaultList.add(SKUColumnConstants.ID_PURCHASE_PRICE);
		defaultList.add(SKUColumnConstants.ID_SKU_COMMENT);
		defaultList.add(SKUColumnConstants.ID_SELLING_UOM);
		defaultList.add(SKUColumnConstants.ID_COLUMN_01);
		defaultList.add(SKUColumnConstants.ID_COLUMN_02);
		defaultList.add(SKUColumnConstants.ID_COLUMN_03);
		defaultList.add(SKUColumnConstants.ID_COLUMN_04);
		defaultList.add(SKUColumnConstants.ID_COLUMN_05);
		defaultList.add(SKUColumnConstants.ID_COLUMN_06);
		defaultList.add(SKUColumnConstants.ID_COLUMN_07);
		defaultList.add(SKUColumnConstants.ID_COLUMN_08);
		defaultList.add(SKUColumnConstants.ID_COLUMN_09);
		defaultList.add(SKUColumnConstants.ID_COLUMN_10);
		defaultList.add(SKUColumnConstants.ID_COLUMN_11);
		defaultList.add(SKUColumnConstants.ID_COLUMN_12);
		defaultList.add(SKUColumnConstants.ID_COLUMN_13);
		defaultList.add(SKUColumnConstants.ID_COLUMN_14);
		defaultList.add(SKUColumnConstants.ID_COLUMN_15);
		defaultList.add(SKUColumnConstants.ID_COLUMN_16);
		defaultList.add(SKUColumnConstants.ID_COLUMN_17);
		defaultList.add(SKUColumnConstants.ID_COLUMN_18);
		defaultList.add(SKUColumnConstants.ID_COLUMN_19);
		defaultList.add(SKUColumnConstants.ID_COLUMN_20);
		defaultList.add(SKUColumnConstants.ID_SKU_ID);

		return defaultList;
	}
	
	public List<Integer> getDefaultSortOrder() {
		List<Integer> defaultList = new ArrayList<Integer>();
		defaultList.add(SKUColumnConstants.ID_COMPANY_NAME);
		defaultList.add(SKUColumnConstants.ID_SELLER_NAME);
		defaultList.add(SKUColumnConstants.ID_GROUP_NAME);
		defaultList.add(SKUColumnConstants.ID_SKU_NAME);

		return defaultList;
	}

}
