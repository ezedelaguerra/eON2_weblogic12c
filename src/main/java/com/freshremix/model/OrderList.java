package com.freshremix.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Helper List that will retrieved the orders given a specific key/s.
 *
 */

public class OrderList {

	private List<Order> list;
	
	private OrderList() { }
	
	/**
	 * Constructor. Initializes all the orders.
	 * If no existing order given date - seller - buyer combination, a dummy order is created with no state.
	 * (ie. An order with no savedOrderBy, publishedOrderBy and so on...)
	 * @param list - list of saved orders including those orders with no state (ie. not saved)
	 * @param dealingPatternMap - collection of date - seller - buyer combination. Used to cross check to created Orders with no state.
	 */
	public OrderList(List<Order> list, Map<String,Map<String, List<Integer>>> dealingPatternMap) {
		this();
		this.list = list;
		
		// prepare list, add Order if not saved for a particular seller - buyer - date combination
		OrderKey key[] = new OrderKey[] {OrderKey.SELLER, OrderKey.BUYER, OrderKey.DELIVERY_DATE};
		Map<String,List<Order>> orderMap = this.getOrderMap(key);
		
		for (String dateKey : dealingPatternMap.keySet()) {
			Map<String, List<Integer>> sellerBuyerMap = dealingPatternMap.get(dateKey);
			for(String sellerKey : sellerBuyerMap.keySet()) {
				for (Integer buyer : sellerBuyerMap.get(sellerKey)) {
					Order _order = new Order(dateKey, Integer.valueOf(sellerKey), buyer);
					String key2 = this.formKey(key, _order);
					if (orderMap.get(key2) == null)
						this.list.add(_order);
						
				}
			}
		}
	}
	
	/**
	 * Constructs map using a specified key. Returns a list of Order object
	 * @param key
	 * @return
	 */
	public Map<String,List<Order>> getOrderMap(OrderKey[] key) {
		
		Map<String,List<Order>> orderMap = new HashMap<String,List<Order>>();
		
		for (Order order : this.list) {
			String _key = this.formKey(key, order);
			List<Order> _list = orderMap.get(_key);
			if (_list == null)
				_list = new ArrayList<Order>();
			_list.add(order);
			orderMap.put(_key, _list);
		}
		return orderMap;
	}
	
	/**
	 * Forms the key
	 * @param key
	 * @param order
	 * @return
	 */
	public String formKey(OrderKey[] key, Order order) {
		StringBuffer _key = new StringBuffer();
		
		int ctr = 0;
		while (ctr != key.length) {
			OrderKey tmp = key[ctr];
			
			switch (tmp) {
				case DELIVERY_DATE:
					_key.append(order.getDeliveryDate());
					break;
				case SELLER:
					_key.append(order.getSellerId());
					break;
				case BUYER:
					_key.append(order.getBuyerId());
					break;
			}
			
			ctr++;
		}
		
		return _key.toString();
	}
}
