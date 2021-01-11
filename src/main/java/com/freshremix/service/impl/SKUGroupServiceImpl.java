package com.freshremix.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

import com.freshremix.dao.SKUGroupDao;
import com.freshremix.model.SKUGroup;
import com.freshremix.model.SortSKUGroup;
import com.freshremix.service.SKUGroupService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.StringUtil;

public class SKUGroupServiceImpl implements SKUGroupService, InitializingBean {

	private SKUGroupDao skuGroupDao;
	private MessageSource messageSource;
	private MessageSourceAccessor messageSourceAccessor;
	private EONLocale eonLocale;
	
	public void afterPropertiesSet() {
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }
	
//	@Override
//	public String getAllSKUGroup(List<Integer> sellerIds, Integer categoryId) {
//		List<SKUGroup> list = skuGroupDao.getAllSKUGroup(sellerIds, categoryId);
//		StringBuffer sb = new StringBuffer();
//		String groupName = getMessageSourceAccessor().getMessage("skugroup.defaultValue", eonLocale.getLocale());
//		groupName = StringUtil.toUTF8String(groupName);
//		sb.append("{'0' : '" + groupName + "'");
//		for (int i=0; i<list.size(); i++) {
//			SKUGroup skuGroup = list.get(i);
//			sb.append(",'" + skuGroup.getDescription() + "':'" + skuGroup.getDescription() + "'");
//		}
//		sb.append("}");
//		return sb.toString();
//	}

	@Override
	public boolean saveSKUGroup(SKUGroup skuGroup) {
		Integer cnt = skuGroupDao.getSKUGroupNameCount(skuGroup);
		if(cnt == 0) {
			skuGroupDao.saveSKUGroup(skuGroup);
			return true;
		}
		return false;
	}

	public void setSkuGroupDao(SKUGroupDao skuGroupDao) {
		this.skuGroupDao = skuGroupDao;
	}
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}
	
	/* (non-Javadoc)
	 * @see com.freshremix.service.OrderSheetService#getSKUGroupList(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String getSKUGroupList(Integer sellerId, Integer categoryId) {
		List<SKUGroup> list = skuGroupDao.getSKUGroupList(sellerId, categoryId);
		StringBuffer sb = new StringBuffer();
		String groupName = getMessageSourceAccessor().getMessage("skugroup.defaultValue", eonLocale.getLocale());
		groupName = StringUtil.toUTF8String(groupName);
		sb.append("{'0' : '" + groupName + "'");
		for (int i=0; i<list.size(); i++) {
			SKUGroup skuGroup = list.get(i);
			sb.append(",'" + skuGroup.getSkuGroupId() + "':'" + skuGroup.getDescription() + "'");
		}
		sb.append("}");
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.SKUGroupService#getSKUGroupList(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<Map<String, Object>> getSKUGroupListViaSellerSelect(Integer sellerId, Integer categoryId) {
		List<SKUGroup> list = skuGroupDao.getSKUGroupList(sellerId, categoryId);
		
		List<Map<String, Object>> skuGroupList = new ArrayList<Map<String,Object>>();
		Map<String, Object> skuGroupMap = new HashMap<String,Object>();
		String groupName = getMessageSourceAccessor().getMessage("skugroup.defaultValue", eonLocale.getLocale());
		try {
			groupName = new String(groupName.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		skuGroupMap.put("id", 0);
		skuGroupMap.put("caption",groupName);
		skuGroupList.add(skuGroupMap);
		for (SKUGroup skuGroup : list) {
			skuGroupMap = new HashMap<String,Object>();
			skuGroupMap.put("id", skuGroup.getSkuGroupId());
			skuGroupMap.put("caption", skuGroup.getDescription());
			skuGroupList.add(skuGroupMap);
		}
		
		return skuGroupList;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.SKUGroupService#updateSKUGroup(com.freshremix.model.SKUGroup)
	 */
	@Override
	public boolean updateSKUGroup(List<SKUGroup> skuGroupList) {
		boolean success = true;
		for (SKUGroup skuGroup : skuGroupList) {
			Integer cnt = skuGroupDao.getSKUGroupNameCount(skuGroup);
			if(cnt == 0) {
				skuGroupDao.updateSkuGroup(skuGroup);
				skuGroupDao.insertSkuGroupToUpdateName(skuGroup);
			}else{
				success = false;
				break;
			}
		}
		return success;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.SKUGroupService#getSKUGroup(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<SortSKUGroup> getSKUGroup(List<Integer> sellerIds, Integer categoryId, Integer userId) {
		List<SortSKUGroup> list = skuGroupDao.getSKUGroupList(sellerIds, categoryId, userId);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.SKUGroupService#deleteSKUGroup(java.util.List)
	 */
	@Override
	public void deleteSKUGroup(List<Integer> historyIds) {
		skuGroupDao.deleteSKUGroup(historyIds);
		
	}

//	/* (non-Javadoc)
//	 * @see com.freshremix.service.SKUGroupService#getSKUGroupListViaSellerSelect2(java.lang.Integer, java.lang.Integer)
//	 */
//	@Override
//	public List<Map<String, Object>> getSKUGroupListViaSellerSelect2(
//			Integer seller, Integer categoryId) {
//		List<Map<String, Object>> skuGroupList = new ArrayList<Map<String,Object>>();
//		
//		Map<String, Object> skuGroupMap = new HashMap<String,Object>();
//		skuGroupMap.put("id", 1);
//		skuGroupMap.put("caption", "CS");
//		skuGroupList.add(skuGroupMap);
//		skuGroupMap = new HashMap<String,Object>();
//		skuGroupMap.put("id", 2);
//		skuGroupMap.put("caption", "F/S");
//		skuGroupList.add(skuGroupMap);
//		return skuGroupList;
//	}
	
	public String getSKUGroupRenderer(List<Integer> sellerIds, Integer categoryId) {
		
		String groupName = getMessageSourceAccessor().getMessage("skugroup.defaultValue", eonLocale.getLocale());
		groupName = StringUtil.toUTF8String(groupName);
		StringBuffer sb = new StringBuffer();
		String delimeter = "";
		sb.append("{");
		for (int j=0; j<sellerIds.size(); j++) {
			Integer sellerId = sellerIds.get(j);
			List<SKUGroup> list = skuGroupDao.getAllSKUGroup(sellerId, categoryId);
			sb.append(" 'S_" + sellerId.toString() + "' : ");
			sb.append("[ { \\\"id\\\" : \\\"0\\\", \\\"caption\\\" : \\\"" + groupName + "\\\" }");
			if (list.size()>0) sb.append(",");
			for (int i=0; i<list.size(); i++) {
				SKUGroup skuGroup = list.get(i);
				sb.append(" { \\\"id\\\" : \\\"" + skuGroup.getSkuGroupId() + "\\\", \\\"caption\\\" : \\\"" + skuGroup.getDescription() + "\\\" }");
				if (i < list.size() -1) sb.append(",");
			}
			
			delimeter = (j < sellerIds.size() - 1) == true ? "," : "";
			
			sb.append(" ]" + delimeter);
		}
		sb.append("}");
		
		return sb.toString();
	}
	
	public List<Map<String, Object>> getAllSKUGroupList(Integer sellerId){
		List allSkuGroup = skuGroupDao.getAllSKUGroupList(sellerId);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> skuGroupList = new ArrayList<Map<String, Object>>();
		
		for(int i=0; i<allSkuGroup.size(); i++){
			SKUGroup skuGroup = (SKUGroup)allSkuGroup.get(i);
			map.put("skuGroupId", skuGroup.getSkuGroupId());
			map.put("skuGroupName", skuGroup.getDescription());
			skuGroupList.add(map);
		}
		
		return skuGroupList;
	}

	public MessageSourceAccessor getMessageSourceAccessor() {
		return messageSourceAccessor;
	}

	@Override
	public SKUGroup getSKUgrouGroup(Integer seller, String grpName,Integer categoryId) {
		return skuGroupDao.getSKUgroup(seller, grpName,categoryId);
	}

	@Override
	public boolean updateSKUGroup(SKUGroup skuGroup) {
		boolean success = true;
		Integer cnt = skuGroupDao.getSKUGroupNameCount(skuGroup);
		if(cnt == 0) {
			skuGroupDao.updateSkuGroup(skuGroup);
			skuGroupDao.insertSkuGroupToUpdateName(skuGroup);
		}else{
			success = false;
		}
		return success;
	}
	
	@Override
	public SKUGroup getSKUGroupByName(Integer sellerId, Integer categoryId, String skuGroupName) {
		List<SKUGroup> list = skuGroupDao.getSKUGroupList(sellerId, categoryId);
	
		for(SKUGroup grp: list){
			if(grp.getDescription().equals(skuGroupName)){
				return grp;
			}
		}
		return null;
	}
}