package com.freshremix.service;

import java.util.List;

import com.freshremix.model.SheetData;
import com.freshremix.model.User;

public interface SheetDataService {

	SheetData loadSheetData(
			User user, String startDate, String endDate, 
			List<Integer> selectedSellerIds, List<Integer> selectedBuyerIds, List<Integer> selectedCategoryIds, 
			Integer sheetTypeId, boolean hasQuantity, boolean csvCall, List<Integer> selectedOrderIdUI);

}
