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
 * Feb 17, 2010		jabalunan		
 * 20120601			Rhoda		Redmine #150 – User Dealing Pattern delete
 * 20120906			Lele		Redmine 882 - Company Maintenance: Details does not reflect upon adding user dealing pattern
 */
package com.freshremix.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

import com.freshremix.constants.DealingPatternRelationConstants;
import com.freshremix.constants.RoleConstants;
import com.freshremix.dao.CompanyDealingPatternDao;
import com.freshremix.dao.DealingPatternDao;
import com.freshremix.dao.OrderDao;
import com.freshremix.dao.UserDao;
import com.freshremix.model.AdminCompanyInformation;
import com.freshremix.model.Order;
import com.freshremix.model.User;
import com.freshremix.model.UserDealingPattern;
import com.freshremix.service.CompanyDealingPatternService;
import com.freshremix.service.UsersInformationService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.RelationPatternUtil;
import com.freshremix.util.RolesUtil;
import com.freshremix.util.StringUtil;

public class CompanyDealingPatternServiceImpl implements CompanyDealingPatternService, InitializingBean {
	
	private CompanyDealingPatternDao compDPDao;
	private OrderDao orderDao;
	private UserDao usersInfoDaos;
	private DealingPatternDao dealingPatternDao;
	private UsersInformationService userInfoService;
	private MessageSource messageSource;
	private MessageSourceAccessor messageSourceAccessor;
	private EONLocale eonLocale;
	
	public void afterPropertiesSet() {
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }
	
	public void setCompDPDao(CompanyDealingPatternDao compDPDao) {
		this.compDPDao = compDPDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}

	public void setDealingPatternDao(DealingPatternDao dealingPatternDao) {
		this.dealingPatternDao = dealingPatternDao;
	}

	public void setUserInfoService(UsersInformationService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}
	
	public MessageSourceAccessor getMessageSourceAccessor() {
		return messageSourceAccessor;
	}

	@Override
	public Integer insertCompanyDealingPattern(String companyId, String selectedCompId, Integer relationId) {
		return compDPDao.insertCompanyDealingPattern(companyId, selectedCompId, relationId);
	}

	@Override
	public Integer resetCDPByCompanyId(String companyId, String isActive) {
		return compDPDao.resetCDPByCompanyId(companyId, isActive);	
	}

	@Override
	public List<UserDealingPattern> getAllSelectedUserDealingPattern(String userId, Integer roleId) {
		return compDPDao.getAllSelectedUserDealingPattern(userId.trim(), roleId);
	}

	@Override
	public List<UserDealingPattern> getAllUserDealingPattern(String companyId, Integer role,String userName,String companyType) {
		return compDPDao.getAllUserDealingPattern(companyId, role,userName,companyType);
	}

	@Override
	public Integer insertUserDealingPattern(String cdpId, String user_01, String user_02, String dealingRelationId, String startDate, String endDate) {
		return compDPDao.insertUserDealingPattern(cdpId, user_01, user_02, dealingRelationId, startDate, endDate);
	}

	@Override
	public Integer checkIfSelectedUDP(Integer companyId, Integer selectedCompanyId) {
		return compDPDao.checkIfSelectedUDP(companyId, selectedCompanyId);
	}

	@Override
	public List<Integer> getCDPByCompanyId(String sellerCompanyId,String buyerCompanyId) {
		return compDPDao.getCDPByCompanyId(sellerCompanyId,buyerCompanyId);
	}

	@Override
	public List<AdminCompanyInformation> searchCompDPByName(String companyId,
			String companyName, String companyType) {
		return compDPDao.searchCompDPByName(companyId, companyName.toLowerCase(), companyType.toLowerCase());
	}

	@Override
	public Integer checkIfCDPExist(String companyId, String selectedId) {
		return compDPDao.checkIfCDPExist(companyId, selectedId);
	}

	@Override
	public Integer updateCDPActive(String companyId, String selectedId,
			String isActive) {
		return compDPDao.updateCDPActive(companyId, selectedId, isActive);
	}

	@Override
	public Integer getCDPIdFromCompany(String companyId, String userId) {
		return compDPDao.getCDPIdFromCompany(companyId, userId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CompanyDealingPatternService#getAllUnderAdminUserDealingPattern(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<UserDealingPattern> getAllUnderAdminUserDealingPattern(
			String companyId, String userId, Integer role) {
		return compDPDao.getAllUnderAdminUserDealingPattern(companyId, userId, role);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CompanyDealingPatternService#insertAdminDealingPattern(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Integer insertAdminDealingPattern(String adminId, String memberId, String dpRelationId, String startDate, String endDate) {
		return compDPDao.insertAdminDealingPattern(adminId, memberId, dpRelationId, startDate, endDate);
	}

	@Override
	public String getLastDealingPatternExpiration(Integer sellerId, Integer buyerId, String expiryDate) {
		
		// Check if no published order
		Order order = orderDao.getLastOrder(sellerId, buyerId);
		
		if (order == null)
			// get last saved order w/ qty
			order = orderDao.getLastOrderWithQuantity(sellerId, buyerId);
		
		if (order != null &&
			Integer.parseInt(expiryDate) < Integer.parseInt(order.getDeliveryDate())) {
			return order.getDeliveryDate();
		}
		
		return null;
	}

	@Override
	public void updateDealingPatternExpiration(Integer udpId, Integer roleId, String expiryDate) {
		
		if (RolesUtil.isSellerByRoleId(roleId) || RolesUtil.isBuyerByRoleId(roleId))
			compDPDao.updateDealingPatternExpiration(udpId, expiryDate);
		else
			compDPDao.updateAdminDealingPatternExpiration(udpId, expiryDate);
	}

	@Override
	public Map<String, Object> saveUserDealingPattern(UserDealingPattern udp, String companyId, String userId,
			String roleName, Integer newRole, Integer roleId, List<List> obj, List<List> removedItems,
			Map<String, Object> model) throws Exception {
		
		Serializer serializer = new JsonSerializer();
		String startDate, endDate, selectedUserId;
		Integer selCompanyId, cdpId, iResult = 1; 
		Integer relationId = RelationPatternUtil.dealingIdByRoleId(roleId);
		
		
		String DATE_FORMAT = "yyyyMMdd";
	    String dateToday = DateFormatter.getDateToday(DATE_FORMAT);

	    if (!RolesUtil.roleAdmin(roleId)) {
			//userInfoService.resetuDPByCompanyId(userId, Integer.valueOf(roleId));
			for (int i = 0; i < removedItems.size();) {
				for (List inner : removedItems) {
					selectedUserId = (String) inner.get(0);
					startDate = (String) inner.get(1);
					endDate = dateToday;
					selCompanyId = userInfoService.getCompanyIdByUserId(selectedUserId);
					cdpId = this.getCDPIdFromCompany(companyId, selCompanyId.toString());
					try {
						boolean isTrue = false;
						isTrue = RolesUtil.isSellerByRoleId(roleId);	
						if(DateFormatter.isDateGreaterThan(endDate, startDate)){
							userInfoService.updateUDPActive((isTrue)?userId.trim():selectedUserId.trim(), 
									(isTrue)?selectedUserId.trim():userId.trim(), startDate, endDate,"0");
						}else{
							userInfoService.deleteUserDealingPattern((isTrue)?userId.trim():selectedUserId.trim(), 
									(isTrue)?selectedUserId.trim():userId.trim(),  startDate);
						}
					
					} catch (Exception ex) {
						throw new Exception(ex);
					}
				}
				break;
			}
			
			for (int i = 0; i < obj.size();) {
				for (List inner : obj) {
					selectedUserId = (String) inner.get(0);
					startDate = (String) inner.get(1);
					if (inner.size() > 2)
						endDate = (String) ((String) inner.get(2) == null ? "" : inner.get(2));
					else
						endDate = "";
					selCompanyId = userInfoService.getCompanyIdByUserId(selectedUserId);
					
					try {
						boolean isTrue = false;
						isTrue = RolesUtil.isSellerByRoleId(roleId);	
						
						if(!isTrue && !roleId.equals(RoleConstants.ROLE_CHOUAI) ){
							cdpId = this.getCDPIdFromCompany(selCompanyId.toString(),companyId);
						}else{
							cdpId = this.getCDPIdFromCompany(companyId, selCompanyId.toString());
						}
						
						Integer iCount = userInfoService.checkIfUDPExist((isTrue)?userId.trim():selectedUserId.trim(), (isTrue)?selectedUserId.trim():userId.trim());
							if (iCount<1) {
								this.insertUserDealingPattern(cdpId.toString(), (isTrue)?userId.trim():selectedUserId.trim(), (isTrue)?selectedUserId.trim():userId.trim(), relationId.toString(), startDate, endDate);
							// DELETION START 20120601: Rhoda – Redmine 150
//							} else {
//								userInfoService.updateUDPActive((isTrue)?userId.trim():selectedUserId.trim(), (isTrue)?selectedUserId.trim():userId.trim(), startDate, endDate,"1");
							// DELETION END 201200601: Rhoda – Redmine 150
							}						
					} catch (Exception ex) {
						throw new Exception(ex);
					}
				}
				break;
			}
		} else {
			//userInfoService.resetAdminDealingPattern(userId);
			//delete
			for (int i = 0; i < removedItems.size();) {
				for (List inner : removedItems) {
					selectedUserId = (String) inner.get(0);
					startDate = (String) inner.get(1);
					endDate = dateToday;
					try {
						
						if(!DateFormatter.isDateGreaterThan(endDate, startDate)){
							userInfoService.deleteAdminDealingPattern(userId.trim(),
									selectedUserId.trim(), startDate);
						}else{
							userInfoService.updateActiveUserAdmin( userId.trim(),
									selectedUserId.trim(), startDate, endDate,"0");
						}
							
					} catch (Exception ex) {
						iResult = 0;
					}
				}
				break;
			}
			
		
			for (int i = 0; i < obj.size();) {
				for (List inner : obj) {
					selectedUserId = (String) inner.get(0);
					startDate = (String) inner.get(1);
					if (inner.size() > 2)
						endDate = (String) ((String) inner.get(2) == null ? "" : inner.get(2));
					else
						endDate = "";
					try {
						if (userInfoService.checkIfUserAdminExist(userId.trim(), selectedUserId.trim()) < 1) {
							this.insertAdminDealingPattern(userId, selectedUserId, RelationPatternUtil.dealingIdByRoleId(roleId).toString(), startDate, endDate);
						// DELETION START 20120601: Rhoda – Redmine 150
//						} else {
//							userInfoService.updateActiveUserAdmin(selectedUserId.trim(), userId.trim(), startDate, endDate,"1");
						// DELETION END 20120601: Rhoda – Redmine 150
						}
					} catch (Exception ex) {
						iResult = 0;
					}
				}
				break;
			}

		}

	    // validiate user dealing pattern expiration
	    List<Map<String,Object>> users = (List<Map<String,Object>>) serializer.deserialize(udp.getUserList());
	    StringBuilder sbMsg = new StringBuilder();
	    for (Map<String,Object> map : users) {
	    	Object _obj = map.get("updated");
	    	if (_obj != null && new Boolean(_obj.toString()) && !StringUtil.isNullOrEmpty(map.get("dateto"))) {
	    		String expiryDate = map.get("dateto").toString();
	    		
	    		String res = null;
	    		
	    		if (DateFormatter.isValidFormat(expiryDate)) {
	    			if (RolesUtil.isSellerByRoleId(roleId))
		    			res = this.getLastDealingPatternExpiration(Integer.parseInt(userId), Integer.parseInt(map.get("id").toString()), expiryDate);
		    		else if (RolesUtil.isBuyerByRoleId(roleId))
		    			res = this.getLastDealingPatternExpiration(Integer.parseInt(map.get("id").toString()), Integer.parseInt(userId), expiryDate);
	    		} else {
	    			res = StringUtil.toUTF8String(getMessageSourceAccessor().
							getMessage("user.dealing.pattern.date.invalid", eonLocale.getLocale()));
	    		}
	    		
	    		if (!StringUtil.isNullOrEmpty(res)) {
	    			sbMsg.append(map.get("caption").toString());
	    			sbMsg.append(" - ");
	    			sbMsg.append(res);
	    			sbMsg.append("\n");
	    		}
	    	}
	    }
	    
	    // If there are no errors, update dealing pattern
	    if (StringUtil.isNullOrEmpty(sbMsg.toString())) {
		    for (Map<String,Object> map : users) {
		    	Object _obj = map.get("updated");
		    	// ENHANCEMENT START 20120906: Lele - Redmine 882
		    	// if (_obj != null && new Boolean(_obj.toString())) {
		    	if (_obj != null && new Boolean(_obj.toString()) && map.get("userdpid") != null) {
		    	// ENHANCEMENT END 20120906:
		    		Integer udpId = Integer.parseInt(map.get("userdpid").toString());
		    		String expiryDate = map.get("dateto").toString();
		    		this.updateDealingPatternExpiration(udpId, roleId, expiryDate);
		    	}
		    }
	    } else {
	    	StringBuilder sbHMsg = new StringBuilder();
	    	sbHMsg.append(StringUtil.toUTF8String(getMessageSourceAccessor().
					getMessage("user.dealing.pattern.error", eonLocale.getLocale())));
	    	sbHMsg.append(sbMsg);
	    	model.put("dpMessage", sbHMsg.toString());
	    	iResult = 0;
	    	model.put("status", iResult);
	    	throw new Exception("Cannot set expiration date");
	    }
	    
		model.put("status", iResult);
		
		return model;
	}
	
	/**
	 *  Get company id
		Get all user id based on company id (date = today onwards)
		After getting all the user id
		  -- get admin > buyer/seller > store
		  -- get user > store
		  -- store check if each user has dealing pattern
		
		if there are any dealing pattern, throw error
	 */
	public Boolean hasAnyDealingPattern(Integer companyId, String dateFrom) {
		List<User> list = usersInfoDaos.getUser(companyId);
		
		
		List<Integer> adminIdList = new LinkedList<Integer>();
		List<User> userList = new LinkedList<User>();
		
		for (User user : list) {
			if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN) || 
				user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
			
				adminIdList.add(user.getUserId());

			} else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER) || 
					   user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)) {
				
				userList.add(user);
				
			}
		}
		
		List<Integer> relationalIdList = new LinkedList<Integer>();
		relationalIdList.add(DealingPatternRelationConstants.SELLER_ADMIN_TO_SELLER);
		relationalIdList.add(DealingPatternRelationConstants.BUYER_ADMIN_TO_BUYER);
		
		// retrieve all Users via admin id
		if (adminIdList.size() > 0)
			userList.addAll(dealingPatternDao.getMembers(adminIdList, relationalIdList, dateFrom, null));
		
		List<Integer> sellerIdList = new LinkedList<Integer>();
		List<Integer> buyerIdList = new LinkedList<Integer>();
		
		for (User user : list) {
			if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER))
				sellerIdList.add(user.getUserId());
			else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER))
				buyerIdList.add(user.getUserId());
		}
		
		Boolean hasAnyDealingPattern = Boolean.FALSE;
		// retrieve buyer/seller dealing patterns
		if (sellerIdList.size() > 0 && 
			dealingPatternDao.getAllBuyerIdsBySellerIds(sellerIdList, dateFrom, null).size() > 0) {
			hasAnyDealingPattern = Boolean.TRUE;
		}
		if (!hasAnyDealingPattern && buyerIdList.size() > 0 &&
			dealingPatternDao.getAllSellerIdsByBuyerIds(buyerIdList, dateFrom, null).size() > 0) {
			hasAnyDealingPattern = Boolean.TRUE;
		}
		
		return hasAnyDealingPattern;
	}
}
