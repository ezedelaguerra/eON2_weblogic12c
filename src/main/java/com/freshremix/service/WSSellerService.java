package com.freshremix.service;

import com.freshremix.model.WSSellerCreateSheetRequest;
import com.freshremix.model.WSSellerCreateSheetResponse;
import com.freshremix.model.WSSellerDeleteOrderSheetRequest;
import com.freshremix.model.WSSellerDeleteOrderSheetResponse;
import com.freshremix.model.WSSellerGetOrderSheetRequest;
import com.freshremix.model.WSSellerGetOrderSheetResponse;
import com.freshremix.model.WSSellerUpdateOrderSheetRequest;
import com.freshremix.model.WSSellerUpdateOrderSheetResponse;

public interface WSSellerService {

	
	public WSSellerCreateSheetResponse createSheet(WSSellerCreateSheetRequest wsRequest);
	public WSSellerGetOrderSheetResponse getOrderSheet(WSSellerGetOrderSheetRequest wsRequest);
	public WSSellerDeleteOrderSheetResponse deleteOrderSheet(
			WSSellerDeleteOrderSheetRequest request);
	public WSSellerUpdateOrderSheetResponse updateOrderSheet(
			WSSellerUpdateOrderSheetRequest request);

}
