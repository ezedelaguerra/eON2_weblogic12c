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
 */
package com.freshremix.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freshremix.dao.CompanyInformationDao;
import com.freshremix.dao.OrderDao;
import com.freshremix.model.AdminCompanyInformation;
import com.freshremix.model.Company;
import com.freshremix.model.Order;
import com.freshremix.service.CompanyInformationService;
import com.freshremix.util.RolesUtil;

public class CompanyInformationServiceImpl implements CompanyInformationService {
	private CompanyInformationDao companyInfoDao;
	private OrderDao orderDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	@Override
	public AdminCompanyInformation getCompanyDetailsByCompanyId(Integer companyId) {
		Map<String, String> param = new HashMap<String,String>();
		param.put("companyId", companyId.toString());

		/*Create object list*/
		return companyInfoDao.getCompanyInformationByCompanyId(param);
	}

	public void setCompanyInfoDao(CompanyInformationDao companyInfoDao) {
		this.companyInfoDao = companyInfoDao;
	}

	@Override
	public Integer insertNewCompany(AdminCompanyInformation admnCompanyInfo) {
		Map<String, String> param = new HashMap<String,String>();
		//param.put("companyId", admnCompanyInfo.getCompanyId().toString());
		param.put("companyName", admnCompanyInfo.getCompanyName());
		param.put("shortName", admnCompanyInfo.getShortName());
		param.put("companyType", admnCompanyInfo.getCompanyType().getCompanyTypeId().toString());
		param.put("contactPerson", admnCompanyInfo.getContactPerson().trim());
		param.put("address1", admnCompanyInfo.getAddress1().trim());
		param.put("address2", admnCompanyInfo.getAddress2().trim());
		param.put("address3", admnCompanyInfo.getAddress3().trim());
		param.put("telNumber", admnCompanyInfo.getTelNumber().trim());
		param.put("emailAdd", admnCompanyInfo.getEmailAdd().trim());
		param.put("soxFlag", admnCompanyInfo.getSoxFlag()=="true"?"1":"0");
		param.put("comments", admnCompanyInfo.getComments().trim());
		
		return companyInfoDao.insertNewCompany(param);
	}

	@Override
	public List<AdminCompanyInformation> getAllCompanyName() {
		return companyInfoDao.getAllCompanyName();
	}

	@Override
	public List<AdminCompanyInformation> getCompanyNameByWildCard(
			String companyName) {
		return companyInfoDao.getCompanyNameByWildCard(companyName);
	}

	@Override
	public List<AdminCompanyInformation> getAllCompanyNameByDealingPattern(String companyType) {
		return companyInfoDao.getAllCompanyNameByDealingPattern(companyType);
	}

	@Override
	public List<AdminCompanyInformation> getCompanySelectedDealingPattern(String companyId,String roleType) {
		return companyInfoDao.getCompanySelectedDealingPattern(companyId,roleType);
	}

	@Override
	public Integer updateCompany(AdminCompanyInformation admnCompanyInfo) {
		Map<String, String> param = new HashMap<String,String>();
		param.put("companyId", admnCompanyInfo.getCompanyId().toString());
		param.put("companyName", admnCompanyInfo.getCompanyName());
		param.put("shortName", admnCompanyInfo.getShortName());
		param.put("companyType", admnCompanyInfo.getCompanyType().getCompanyTypeId().toString());
		param.put("contactPerson", admnCompanyInfo.getContactPerson().trim());
		param.put("address1", admnCompanyInfo.getAddress1().trim());
		param.put("address2", admnCompanyInfo.getAddress2().trim());
		param.put("address3", admnCompanyInfo.getAddress3().trim());
		param.put("telNumber", admnCompanyInfo.getTelNumber().trim());
		param.put("emailAdd", admnCompanyInfo.getEmailAdd().trim());
		param.put("faxNumber", admnCompanyInfo.getFaxNumber().trim());
		param.put("soxFlag", admnCompanyInfo.getSoxFlag()=="true"?"1":"0");
		param.put("comments", admnCompanyInfo.getComments().trim());
		
		return companyInfoDao.updateCompany(param);
		
	}

	@Override
	public Integer checkCompanyIfExists(String companyName) {
		return companyInfoDao.checkCompanyIfExists(companyName);
	}

	@Override
	public Company getCompanyById(Integer companyId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		return companyInfoDao.getCompanyById(param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CompanyInformationService#getBuyerCompanyById(java.lang.Integer)
	 */
	@Override
	public Company getBuyerCompanyById(Integer companyId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		return companyInfoDao.getBuyerCompanyById(param);
	}

	@Override
	public boolean hasOrder(Integer roleId, Integer userId1, Integer userId2, String dateFrom,
			String dateTo) {
		Integer sellerId = null;
		Integer buyerId = null;
		if (RolesUtil.isSellerByRoleId(roleId)) {
			sellerId = userId1;
			buyerId = userId2;
		} else if (RolesUtil.isBuyerByRoleId(roleId)) {
			sellerId = userId2;
			buyerId = userId1;
		}
		
		if (dateTo.equalsIgnoreCase("undefined")) {
			dateTo = "0";
		}
		
		return this.hasOrder(sellerId, buyerId, dateFrom, dateTo);
	}

	@Override
	public boolean hasOrder(Integer sellerId, Integer buyerId, String dateFrom,
			String dateTo) {
		List<Order> order = orderDao.getOrders(sellerId, buyerId, dateFrom, dateTo);
		return order.size() > 0 ? true : false;
	}
}
