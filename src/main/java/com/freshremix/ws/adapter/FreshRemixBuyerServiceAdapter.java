package com.freshremix.ws.adapter;


import com.freshremix.model.WSBuyerAddOrderSheetRequest;
import com.freshremix.model.WSBuyerAddOrderSheetResponse;
import com.freshremix.model.WSBuyerLoadOrderRequest;
import com.freshremix.model.WSBuyerLoadOrderResponse;

public abstract interface FreshRemixBuyerServiceAdapter
{
  public abstract WSBuyerLoadOrderResponse loadOrders(WSBuyerLoadOrderRequest paramWSBuyerLoadOrderRequest);
  
  public abstract WSBuyerAddOrderSheetResponse addOrders(WSBuyerAddOrderSheetRequest paramWSBuyerAddOrderSheetRequest);
}
