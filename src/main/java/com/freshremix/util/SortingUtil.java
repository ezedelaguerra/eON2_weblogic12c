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
 * Jun 2, 2010		nvelasquez		
 */
package com.freshremix.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.freshremix.comparator.AkadenSKUComparator;
import com.freshremix.comparator.SKUComparator;
import com.freshremix.constants.RoleConstants;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.Company;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;
import com.freshremix.service.SKUGroupSortService;
import com.freshremix.service.SKUSortService;
import com.freshremix.ui.model.FilteredIDs;

/**
 * @author nvelasquez
 *
 */
public class SortingUtil {
	
	private static SKUSortService skuSortService;
	private static DealingPatternService dealingPatternService;
	private static SKUGroupSortService skuGroupSortService;
	
	public void setSkuSortService(SKUSortService skuSortService) {
		SortingUtil.skuSortService = skuSortService;
	}
	
	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		SortingUtil.dealingPatternService = dealingPatternService;
	}
	
	public void setSkuGroupSortService(SKUGroupSortService skuGroupSortService) {
		SortingUtil.skuGroupSortService = skuGroupSortService;
	}
	
	public static void sortSKUs (User user, List<?> skuObjs, Integer categoryId) {
		List<Integer> skuSortOrder = user.getPreference().getSkuSort();
		List<Integer> skuGroupSort = getSKUGroupSort(user, categoryId);
		List<Integer> sortedSellerCompanies = FilterIDUtil.toIDList(user.getPreference().getSortedSellerCompanies());
		List<Integer> sortedSellers = FilterIDUtil.toIDList(user.getPreference().getSortedSellers());
		
		if (skuSortOrder == null || skuSortOrder.size() == 0 || !isSortOrderValid(skuSortOrder))
			skuSortOrder = (List<Integer>) skuSortService.getDefaultSortOrder();
		
		SKUComparator comparator = new SKUComparator(skuSortOrder, skuGroupSort, sortedSellerCompanies, sortedSellers);
		Collections.sort(skuObjs, comparator);
	}
	
	public static void sortAkadenSKUs (User user, List<AkadenSKU> skuObjs, Integer categoryId) {
		List<Integer> skuSortOrder = user.getPreference().getSkuSort();
		List<Integer> skuGroupSort = getSKUGroupSort(user, categoryId);
		
		if (skuSortOrder == null || skuSortOrder.size() == 0 || !isSortOrderValid(skuSortOrder))
			skuSortOrder = (List<Integer>) skuSortService.getDefaultSortOrder();
		
		AkadenSKUComparator comparator = new AkadenSKUComparator(skuSortOrder, skuGroupSort);
		Collections.sort(skuObjs, comparator);
	}
	
	private static List<Integer> getSKUGroupSort(User user, Integer categoryId) {
		Long userRoleId = user.getRole().getRoleId();
		List<Integer> sellerIds = new ArrayList<Integer>();
		
		if (userRoleId.equals(RoleConstants.ROLE_SELLER_ADMIN)) {
			sellerIds = dealingPatternService.getSellerIdsOfSellerAdminId(user.getUserId());
		}
		else if (userRoleId.equals(RoleConstants.ROLE_SELLER)) {
			sellerIds.add(user.getUserId());
		}
		else if (userRoleId.equals(RoleConstants.ROLE_BUYER_ADMIN)) {
			sellerIds = dealingPatternService.getSellerIdsOfBuyerAdminId(user.getUserId());
		}
		else if (userRoleId.equals(RoleConstants.ROLE_BUYER)) {
			sellerIds = dealingPatternService.getSellerIdsOfBuyerId(user.getUserId());
		}
		
		return skuGroupSortService.getSKUGroupSortI(sellerIds, categoryId, user.getUserId());
		
	}
	
	public static List<FilteredIDs> sortList(List<FilteredIDs> unsortedList, List<FilteredIDs> sortIds) {

		List<FilteredIDs> sortedList = new ArrayList<FilteredIDs>();

		// if the sortIds is empty return the list without changes
		if (sortIds == null) {
			return unsortedList;
		} else {
			// reading the sortIds
			Iterator iterate = sortIds.iterator();
			while (iterate.hasNext()) {
				FilteredIDs sortedId = (FilteredIDs) iterate.next();

				// arrange the list according to the sort preference
				for (int i = 0; i < unsortedList.size(); i++) {
					FilteredIDs id = (FilteredIDs) unsortedList.get(i);
					if (id.getId().equals(sortedId.getId())) {
						sortedList.add(unsortedList.get(i));
						unsortedList.remove(i);
						break;
					}
				}
			}
			// add remaining values in the unsorted list of objects 
			if (unsortedList.size() > 0)
				sortedList.addAll(unsortedList);

			return sortedList;
		}
	}
	
	public static List<Integer> sortListInteger(List<Integer> unsortedList, List<FilteredIDs> sortIds) {

		List<Integer> sortedList = new ArrayList<Integer>();

		// if the sortIds is empty return the list without changes
		if (sortIds == null) {
			return unsortedList;
		} else {
			// reading the sortIds
			Iterator iterate = sortIds.iterator();
			while (iterate.hasNext()) {
				FilteredIDs sortedId = (FilteredIDs) iterate.next();

				// arrange the list according to the sort preference
				for (int i = 0; i < unsortedList.size(); i++) {
					Integer id = (Integer) unsortedList.get(i);
					if (id.equals(Integer.parseInt(sortedId.getId()))) {
						sortedList.add(unsortedList.get(i));
						unsortedList.remove(i);
						break;
					}
				}
			}
			// add remaining values in the unsorted list of objects 
			if (unsortedList.size() > 0)
				sortedList.addAll(unsortedList);

			return sortedList;
		}
	}

	public static List<FilteredIDs> sort(List<Object> unsortedList,	List<FilteredIDs> sortIds) {

		// convert the List<Object> to List<FilteredIDs>
		// create a list of filteredIds
		List<FilteredIDs> unsortedFilteredIDs = new ArrayList<FilteredIDs>();
		for (Object obj : unsortedList) {
			User _user = new User();
			Company _company = new Company();
			if (obj instanceof User) {
				_user = (User) obj;
				unsortedFilteredIDs.add(new FilteredIDs(_user.getUserId()
						.toString(), _user.getName()));

			} else if (obj instanceof Company) {
				_company = (Company) obj;
				unsortedFilteredIDs.add(new FilteredIDs(_company.getCompanyId()
						.toString(), _company.getCompanyName()));
			}
		}

		List<FilteredIDs> sortedList = new ArrayList<FilteredIDs>();

		// if the sortIds is empty return the list without changes
		if (sortIds == null || sortIds.size() == 0) {
			return unsortedFilteredIDs;
		} else {
			// reading the sortIds
			Iterator<FilteredIDs> iterate = sortIds.iterator();
			while (iterate.hasNext()) {
				FilteredIDs sortedId = (FilteredIDs) iterate.next();

				// arrange the list according to the sort preference
				for (int i = 0; i < unsortedFilteredIDs.size(); i++) {
					FilteredIDs id = (FilteredIDs) unsortedFilteredIDs.get(i);
					if (id.getId().equals(sortedId.getId())) {
						sortedList.add(unsortedFilteredIDs.get(i));
						unsortedFilteredIDs.remove(i);
						break;
					}
				}
			}
			// add remaining values in the unsorted list of objects
			if (unsortedList.size() > 0)
				sortedList.addAll(unsortedFilteredIDs);

			return sortedList;
		}
	}
	
	protected static boolean isSortOrderValid(List<Integer> skuSortOrder) {
		boolean valid = true;
		List<Integer> columnList = skuSortService.getDefaultList();
		for (int i = 0; i < skuSortOrder.size(); i++) {
			if (!columnList.contains(skuSortOrder.get(i))) {
				valid = false;
				break;
			}
		}
		
		return valid;
	}
	
}
