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
 * date		name		version		changes
 * ------------------------------------------------------------------------------
 * 20120725	gilwen		v11			Redmine 131 - Change of display in address bar of Comments
 */

package com.freshremix.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.freshremix.dao.DealingPatternDao;
import com.freshremix.model.AdminMember;
import com.freshremix.model.Company;
import com.freshremix.model.Order;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;
import com.freshremix.util.DateFormatter;

public class DealingPatternServiceImpl implements DealingPatternService {

	private DealingPatternDao dealingPatternDao;
	
	public void setDealingPatternDao(DealingPatternDao dealingPatternDao) {
		this.dealingPatternDao = dealingPatternDao;
	}
	
	/* (non-Javadoc)
	 * @see com.freshremix.service.DealingPatternService#getBuyerCompaniesBySellerCompanyIds(java.lang.Integer)
	 */
	@Override
	public List<Company> getBuyerCompaniesBySellerCompanyIds(
			Integer userCompanyId) {
		return dealingPatternDao.getBuyerCompaniesBySellerCompanyIds(userCompanyId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.DealingPatternService#getBuyerCompaniesByUserSellerIds(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Company> getBuyerCompaniesByUserSellerIds(List<Integer> userId,
			String dateFrom, String dateTo) {
		return dealingPatternDao.getBuyerCompaniesByUserSellerIds(userId, dateFrom, dateTo);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.DealingPatternService#getMembersByAdminId(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<User> getMembersByAdminId(Integer userId,
			Integer dealingRelationId, String dateFrom, String dateTo) {
		return dealingPatternDao.getMembersByAdminId(userId, dealingRelationId, dateFrom, dateTo);
	}

	@Override
	public List<AdminMember> getMembersByAdminIdWithStartEndDates(Integer userId,
			Integer dealingRelationId, String dateFrom, String dateTo) {
		return dealingPatternDao.getMembersByAdminIdWithStartEndDates(userId, dealingRelationId, dateFrom, dateTo);
	}
	
	
	// ENHANCEMENT START 20120725: Lele - Redmine 131
	@Override
	public List<User> getMembersByAdminId2(Integer userId,
			Integer dealingRelationId, String dateFrom, String dateTo) {
		return dealingPatternDao.getMembersByAdminId2(userId, dealingRelationId, dateFrom, dateTo);
	}
	// ENHANCEMENT END 20120725:

	/* (non-Javadoc)
	 * @see com.freshremix.service.DealingPatternService#getSellerCompaniesByBuyerCompanyIds(java.lang.Integer)
	 */
	@Override
	public List<Company> getSellerCompaniesByBuyerCompanyIds(
			Integer userCompanyId) {
		return dealingPatternDao.getSellerCompaniesByBuyerCompanyIds(userCompanyId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.DealingPatternService#getUserBuyersByBuyerCompanyIds(java.lang.Integer, java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public List<User> getUserBuyersByBuyerCompanyIds(Integer userId,
			List<Integer> selectedCompanyIds, String dateFrom, String dateTo) {
		return dealingPatternDao.getUserBuyersByBuyerCompanyIds(userId, selectedCompanyIds, dateFrom, dateTo);
	}
	
	// ENHANCMENT START 20120725: Lele - Redmine 131
	@Override
	public List<User> getUserBuyersByBuyerCompanyIds2(Integer userId,
			List<Integer> selectedCompanyIds, String dateFrom, String dateTo) {
		return dealingPatternDao.getUserBuyersByBuyerCompanyIds2(userId, selectedCompanyIds, dateFrom, dateTo);
	}
	// ENHANCEMENT END 20120725:

	/* (non-Javadoc)
	 * @see com.freshremix.service.DealingPatternService#getUserBuyersByUserSellersAndBuyerCompanyIds(java.util.List, java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public List<User> getUserBuyersByUserSellersAndBuyerCompanyIds(
			List<Integer> selectedSellerIds, List<Integer> selectedCompanyIds,
			String dateFrom, String dateTo) {
		return dealingPatternDao.getUserBuyersByUserSellersAndBuyerCompanyIds(selectedSellerIds, selectedCompanyIds, dateFrom, dateTo);
	}
	
	// ENHANCEMENT START 20120725: Lele - Redmine 131
	@Override
	public List<User> getUserBuyersByUserSellersAndBuyerCompanyIds2(
			List<Integer> selectedSellerIds, List<Integer> selectedCompanyIds,
			String dateFrom, String dateTo) {
		return dealingPatternDao.getUserBuyersByUserSellersAndBuyerCompanyIds2(selectedSellerIds, selectedCompanyIds, dateFrom, dateTo);
	}
	// ENHANCEMENT END 20120725:

	/* (non-Javadoc)
	 * @see com.freshremix.service.DealingPatternService#getUserSellersBySellerCompanyIds(java.lang.Integer, java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public List<User> getUserSellersBySellerCompanyIds(Integer userId,
			List<Integer> selectedCompanyIds, String dateFrom, String dateTo) {
		return dealingPatternDao.getUserSellersBySellerCompanyIds(userId, selectedCompanyIds, dateFrom, dateTo);
	}
	
	// ENHANCEMENT START 20120725: Lele - Redmine 131
	@Override
	public List<User> getUserSellersBySellerCompanyIds2(Integer userId,
			List<Integer> selectedCompanyIds, String dateFrom, String dateTo) {
		return dealingPatternDao.getUserSellersBySellerCompanyIds2(userId, selectedCompanyIds, dateFrom, dateTo);
	}
	// ENHANCEMENT END 20120725:

	/* (non-Javadoc)
	 * @see com.freshremix.service.DealingPatternService#getBuyerToSellerDPMap(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Map<String, List<Integer>>> getBuyerToSellerDPMap(Map<String, Map<String, List<Integer>>> dateDPMap) {
		Map<String, Map<String, List<Integer>>> dateDP = new HashMap<String, Map<String, List<Integer>>>();
		Set<String> dateKeys = dateDPMap.keySet();
		for (String dateKey : dateKeys) {
			Map<String, List<Integer>> buyerToSellerDPMap = new HashMap<String, List<Integer>>();
			Map<String,List<Integer>> sellerToBuyerDPMap = dateDPMap.get(dateKey);
			Set<String> keys = sellerToBuyerDPMap.keySet();
			for (String key : keys) {
				List<Integer> buyerId = sellerToBuyerDPMap.get(key);
				for (Integer _buyerId : buyerId) {
					storeToBuyerToSellerDPMap(buyerToSellerDPMap,key,_buyerId);
				}
			}
			dateDP.put(dateKey, buyerToSellerDPMap);
		}
		return dateDP;
	}
	
	private void storeToBuyerToSellerDPMap(Map<String, List<Integer>> buyerToSellerDPMap, String keySellerId, Integer buyerId) {
		List<Integer> buyerToSeller = buyerToSellerDPMap.get(buyerId.toString());
		if (buyerToSeller == null) {
			buyerToSeller = new ArrayList<Integer>();
			buyerToSeller.add(Integer.valueOf(keySellerId));
		} else {
			buyerToSeller.add(Integer.valueOf(keySellerId));
		}
		buyerToSellerDPMap.put(buyerId.toString(), buyerToSeller);
	}

	@Override
	public Map<String, Map<String, List<Integer>>> getSellerToBuyerDPMap(
			List<Integer> sellerIds, String dateFrom, String dateTo) {
		
		List<String> date = DateFormatter.getDateList(dateFrom, dateTo);
		Map<String, Map<String, List<Integer>>> dateDP = new HashMap<String, Map<String, List<Integer>>>();
		for (String _date : date) {
			Map<String, List<Integer>> sellerToBuyerDPMap = new HashMap<String, List<Integer>>();
			for (Integer sellerId : sellerIds) {
				List<Integer> seller = new ArrayList<Integer>();
				seller.add(sellerId);
				List<Company> companies = this.getBuyerCompaniesByUserSellerIds(seller, _date, null);
				if (companies != null && companies.size() > 0) {
					List<User> users = this.getUserBuyersByBuyerCompanyIds(sellerId, getCompanyIdsFromCompanyList(companies), _date, null);
					List<Integer> buyers = this.getBuyerIdsFromUserList(users);
					sellerToBuyerDPMap.put(sellerId.toString(), buyers);
				}
			}
			dateDP.put(_date, sellerToBuyerDPMap);
		}
		return dateDP;
	}
	
	private List<Integer> getCompanyIdsFromCompanyList(List<Company> companies) {
		List<Integer> companyId = new ArrayList<Integer>();
		for (Company company : companies) {
			companyId.add(company.getCompanyId());
		}
		return companyId;
	}
	
	private List<Integer> getBuyerIdsFromUserList(List<User> users) {
		List<Integer> buyerId = new ArrayList<Integer>();
		for (User user : users) {
			buyerId.add(user.getUserId());
		}
		return buyerId;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.DealingPatternService#getSellerCompaniesByUserBuyerIds(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Company> getSellerCompaniesByUserBuyerIds(
			List<Integer> selectedBuyerIds, String dateFrom, String dateTo) {
		return dealingPatternDao.getSellerCompaniesByUserBuyerIds(selectedBuyerIds, dateFrom, dateTo);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.DealingPatternService#getUserSellersByUserBuyersAndSellerCompanyIds(java.util.List, java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public List<User> getUserSellersByUserBuyersAndSellerCompanyIds(
			List<Integer> selectedBuyerIds, List<Integer> selectedCompanyIds,
			String dateFrom, String dateTo) {
		return dealingPatternDao.getUserSellersByUserBuyersAndSellerCompanyIds(selectedBuyerIds, selectedCompanyIds, dateFrom, dateTo);
	}
	
	// ENHANCEMENT START 20120725: Lele - Redmine 131
	@Override
	public List<User> getUserSellersByUserBuyersAndSellerCompanyIds2(
			List<Integer> selectedBuyerIds, List<Integer> selectedCompanyIds,
			String dateFrom, String dateTo) {
		return dealingPatternDao.getUserSellersByUserBuyersAndSellerCompanyIds2(selectedBuyerIds, selectedCompanyIds, dateFrom, dateTo);
	}
	// ENHANCEMENT END 20120725:

	@Override
	public List<Integer> getSellerIdsOfBuyerAdminId(Integer userId) {
		return dealingPatternDao.getSellerIdsOfBuyerAdminId(userId);
	}

	@Override
	public List<Integer> getSellerIdsOfBuyerId(Integer userId) {
		return dealingPatternDao.getSellerIdsOfBuyerId(userId);
	}

	@Override
	public List<Integer> getSellerIdsOfSellerAdminId(Integer userId) {
		return dealingPatternDao.getSellerIdsOfSellerAdminId(userId);
	}

	@Override
	public List<Integer> getAdminUsersOfMember(Integer userId,Integer relation) {
		return dealingPatternDao.getAdminUsersOfMember(userId,relation);
	}

	@Override
	public List<User> getSellerUsersOfBuyerAdminId(Integer userId) {
		return dealingPatternDao.getSellerUsersOfBuyerAdminId(userId);
	}

	@Override
	public List<User> getAllBuyerIdsBySellerIds(List<Integer> sellerIds, String datefrom, String dateto) {
		return dealingPatternDao.getAllBuyerIdsBySellerIds(sellerIds, datefrom, dateto);
	}
	
	@Override
	public List<User> getAllSellerIdsByBuyerIds(List<Integer> buyerIds, String datefrom, String dateto) {
		return dealingPatternDao.getAllSellerIdsByBuyerIds(buyerIds, datefrom, dateto);
	}

	/**
	 * dealingPatternMap retrieves all seller buyer dealing pattern 
	 * for given dates
	 *  date
	 *   seller - buyer list
	 */
	@Override
	public Map<String, Map<String, List<Integer>>> getDealingPatternMap(
			List<String> date, List<Integer> sellerList, List<Integer> buyerList) {
		
		Map<String, Map<String, List<Integer>>> dealingPatternMap = 
			new HashMap<String, Map<String, List<Integer>>>();
		
		for (String _date : date) {
			Map<String, List<Integer>> sellerToBuyerDPMap = new HashMap<String, List<Integer>>();
			for (Integer sellerId : sellerList) {
				List<Integer> seller = new ArrayList<Integer>();
				seller.add(sellerId);
				List<User> users = dealingPatternDao
						.getAllBuyerIdsBySellerIds(seller, _date, _date);
				if (users != null && users.size() > 0) {
					Map<Integer, Order> buyers = new HashMap<Integer, Order>();
					List<Integer> _buyerList = new ArrayList<Integer>();
					for (User _user : users) {
						buyers.put(_user.getUserId(), null);
						_buyerList.add(_user.getUserId());
					} 
					sellerToBuyerDPMap.put(sellerId.toString(), _buyerList);
				}
			}
			dealingPatternMap.put(_date, sellerToBuyerDPMap);
		}
		
		return dealingPatternMap;
	}
	
}