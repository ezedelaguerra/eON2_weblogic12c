package com.freshremix.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freshremix.model.Order;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;

public class SheetDataUtil {
	
	public static Map<Integer, Order> getUserOrderMap(List<User> userList){
		Map<Integer, Order> userOrderMap = new HashMap<Integer, Order>();
		for (User user : userList) {
			userOrderMap.put(user.getUserId(), null);
		} 
		return userOrderMap;
	}
	
	public static List<Integer> convertStringToInteger(List list){
		List<Integer> integerList = new ArrayList<Integer>();
		for(Object obj: list){
			integerList.add(new Integer(obj.toString()));
		}
		return integerList;
	}
	
	public static List<Integer> convertSortedCategories(List<?> input, List<UsersCategory> sortedCategories) {
		List<Integer> list = new ArrayList<Integer>();
		
		for (UsersCategory uc : sortedCategories) {
			
			for (Object _input : input) {
				Integer tmp = Integer.parseInt(_input.toString());
				if (tmp.equals(uc.getCategoryId())) {
					list.add(tmp);
				}
			}
			
		}
		
		return list;
	}

}
